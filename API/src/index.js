const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const express_jwt = require("express-jwt");

const mysql = require("mysql");

const connection = mysql.createConnection({
	host: "localhost",
	user: "cs2340",
	password: "cs2340ratapp",
	database: "ratapp"
});

const RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\nMIICXQIBAAKBgQCDgLeSmThFXHbZAB0EGWAE2dtB8DgjdgTCgqc7LnRzbCtbE5xD" +
"H5ByzLPlN8DgyiVp9PZAYdh243DPGO+hml0MpqMtj0csw3UvZWEWFXvvCoHpqeiW" +
"bShBsb6wfetnGjQ8PtoJFB8EsHzq18PQz0jKPc5weXWPfxdxMChTdRrrtQIDAQAB" +
"AoGAZpcsn7nZJIOWVIS6HlCNkDtFypNVuJSB8h1rycfcIY/p4wvRhKxDTMxWLCTq" +
"3HPX7GmnE2NCNL4LbAXQLQr4O5Ee4XMPFjgNy/sUlauB6THw6UbsV/7EGlqQh3Df" +
"+AymgbpbZa9QpITeenvwJTbmYA2ul3wlIoscCaonyRz4IwECQQD9PExu9CJBfhzh" +
"XAqwRMqeNlwREne/WpQmBKQS1HD4/8tJFTqisjtoJwFvucI5oJ3zlmtauaGfTBYO" +
"VxSP/K89AkEAhPA4PTPKa/6Aqo0SceATij8o2y59UQMwBBTi33TPDTlWE/IR4mRl" +
"MbqTl/k6QDLksF6HP9ZpuiYFBSE4yFn12QJBAJkUl1vHJuISW2D749Y0b4t+bt8/" +
"G7ZICiCFU62yUGylLH0MYTqypWjLk3m3gCqX5oO2nUTlzEbglsCqcVqtND0CQDU4" +
"antuCYLUn7QnyBOYzpnREU02Pms5aHap2e31uJKluqU/ixNkd/LBrCbyWvSqq01E" +
"esb+0tL4N5hHJZFoGnkCQQDg5oEn9bbNIsyPCkOjJaA7kfPo2go5oFlGxASvNxPv" +
"wqF3VLVEikmh0f4c+xLjUra8m5ENTkPMDN9CzNPQbeCp\n-----END RSA PRIVATE KEY-----";

const RSA_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDgLeSmThFXHbZAB0EGWAE2dtB" +
"8DgjdgTCgqc7LnRzbCtbE5xDH5ByzLPlN8DgyiVp9PZAYdh243DPGO+hml0MpqMt" +
"j0csw3UvZWEWFXvvCoHpqeiWbShBsb6wfetnGjQ8PtoJFB8EsHzq18PQz0jKPc5w" +
"eXWPfxdxMChTdRrrtQIDAQAB\n-----END PUBLIC KEY-----";

connection.connect();

app.use(bodyParser.json());

// Add headers
app.use(function (req, res, next) {
        
        // Website you wish to allow to connect
        res.setHeader('Access-Control-Allow-Origin', '*');
    
        // Request methods you wish to allow
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    
        // Request headers you wish to allow
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,Authorization');
    
        // Set to true if you need the website to include cookies in the requests sent
        // to the API (e.g. in case you use sessions)
        res.setHeader('Access-Control-Allow-Credentials', true);
    
        // Pass to next layer of middleware
        next();
});

// GET user by id
// Call function in user to get user information
app.get("/user/:id", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    let id = req.params.id;
    connection.query("SELECT `firstName`,`lastName`,`username`,`userType` FROM users WHERE id = ?", [id], (error, result, fields) => {
        if (error) { throw error; }
        res.json(result[0]);
    });
});

app.post("/admin/unban", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    connection.query("UPDATE users SET `lockout` = 0 WHERE `lockout` = 3", (error, result, fields) => {
        if (error) { throw error; }
        res.json({
            success: true,
            message: "All banned users unbanned."
        });
    });
})

// POST (create) user
// Take params from passed JSON and call function in user to create
app.post("/auth/register", (req, res) => {
    let firstName = req.body.firstName;
    let lastName = req.body.lastName;
    let userName = req.body.username;
    let password = req.body.password;
    let userType = req.body.userType;
    bcrypt.hash(password, 10, (err, hashed) => {
        if (err) { 
            res.json({
                success: false,
                message: "Unexpected error creating user."
            });    
        }
        connection.query("SELECT `id` FROM users WHERE username = ?", [userName], 
        (err, results, fields) => {
            if (err) {
                res.json({
                    success: false,
                    message: "An unexpected error occurred."
                });
            } else if (results[0]) {
                res.json({
                    success: false,
                    message: "The username already exists."
                });
            } else {
                connection.query("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, 0)", 
                [firstName, lastName, userName, hashed, userType], 
                (err, results, fields) => {
                    if (err) {
                        res.json({
                            success: false,
                            message: "Could not create user."
                        });
                    } else {
                        res.json({
                            success: true,
                            message: "Successfully created user."
                        });
                    }
                });
            }
        })
    });
});

// POST user details to authenticate
// Take params from passed JSON and call function in user to authenticate
app.post("/auth/login", (req, res) => {
    let userName = req.body.username;
    let password = req.body.password;
    connection.query("SELECT `id`, `password`, `lockout` FROM users WHERE username = ?", [userName], (err, results, fields) => {
        if (err) { 
            res.json({
                success: false,
                message: "An unexpected error occurred."
            }); 
        }
        if (results[0] == null) {
            res.json({
                success: false,
                message: "Wrong username."
            });
        } else if (results[0].lockout >= 3) {
            res.json({
                success: false,
                message: "You have been locked out of your account. Please contact an admin to get back in."
            });
        } else {
            bcrypt.compare(password, results[0].password, (err, same) => {
                if (err) { 
                    res.json({
                        success: false,
                        message: "An unexpected error occurred."
                    }); 
                } else {
                    if (same) {
                        const jsonwebtoken = jwt.sign({}, RSA_PRIVATE_KEY, {
                            algorithm: 'RS256',
                            expiresIn: "2 days",
                            subject: results[0].id.toString()
                        });
                        res.json({
                            success: true,
                            message: jsonwebtoken
                        });
                    } else {
                        connection.query("UPDATE users SET `lockout` = `lockout` + 1 WHERE `id` = ?", [results[0].id], (err3, results2, fields2) => {
                            res.json({
                                success: false,
                                message: "Wrong password."
                            });
                        })
                    }
                }
            });
        }
    });
});

app.get("/data/page/:page", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    let page = req.params.page;
    let offset = (page - 1) * 20;
    connection.query("SELECT * FROM data LIMIT 20 OFFSET ?", [offset], (err, results, fields) => {
        if (err) { throw err; }
        res.json(results);
    });
});

// GET rat data from database by page
// Need to use pages because we cannot pass all 100000+ rows to user
// Take page param and call function in data to get rows
app.get("/data/:lastId/:millis", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    let date = new Date(req.params.millis);
    let lastId = req.params.lastId;
    connection.query("SELECT MAX(`createdDate`) FROM data", (err, result, fields) => {
        if (err) { throw err; }
        if (date.getTime() == 0) {
            date = result[0].createdDate;
        }
        if (lastId == 0) {
            lastId = 4294967295;
        }
        connection.query("SELECT * FROM data WHERE `createdDate` <= ? OR `id` < ? ORDER BY `createdDate` DESC LIMIT 20", [date, lastId], (err, result, fields) => {
            if (err) { throw err; }
            res.json(result);
        });
    });
});

app.get("/data/search/:minDate/:maxDate", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    let minDate = new Date(parseInt(req.params.minDate, 10));
    let maxDate = new Date(parseInt(req.params.maxDate, 10));
    connection.query("SELECT * FROM data WHERE `createdDate` >= ? AND `createdDate` <= ?", [minDate, maxDate], (err, results, fields) => {
        if (err) { throw err; }
        res.json(results);
    })
});

app.get("/data/pagination", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    connection.query("SELECT COUNT(`id`) AS count FROM data", (err, results, fields) => {
        if (err) { throw err; }
        let count = results[0].count;
        let perPage = 20;
        let numPages = Math.ceil(count / perPage);
        res.json({
            count: count,
            perPage: perPage,
            numPages: numPages
        });
    });
});

// POST (create) rat data
// Take params from passed JSON and call function in data to create
app.post("/data/add", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    let locationType = req.body.locationType;
    let incidentZip = req.body.incidentZip;
    let incidentAddress = req.body.incidentAddress;
    let city = req.body.city;
    let borough = req.body.borough;
    let latitude = req.body.latitude;
    let longitude = req.body.longitude;
    let id = req.body.userId;
    connection.query("INSERT INTO data VALUES (NULL, NOW(), ?, ?, ?, ?, ?, ?, ?, (SELECT `id` FROM users WHERE `id` = ?))",
     [locationType, incidentZip, incidentAddress, city, borough, latitude, longitude, id], (error, result, fields) => {
         if (error) {
             res.json({
                 success: false,
                 message: "An unexpected error occurred."
             });
        } else {
            res.json({
                success: true,
                message: "Successfully added rat data."
            });
        }
     });
});

// DELETE rat data
// Take id param and call function in data to delete rows
app.delete("/data/:id", express_jwt({ secret: RSA_PUBLIC_KEY }), (req, res) => {
    let id = req.param.idl
    connection.query("DELETE FROM data WHERE id = ?", [id], (error, result, fields) => {
        if (error) { throw error; }
        res.json({
            success: true,
            message: "Rat report deleted."
        });
    });
});

app.listen(3000, () => {});

module.exports = app;
