const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const bcrypt = require("bcrypt");

const mysql = require("mysql");

const connection = mysql.createConnection({
	host: "localhost",
	user: "cs2340",
	password: "cs2340ratapp",
	database: "ratapp"
});

connection.connect();

app.use(bodyParser.json());

// GET user by id
// Call function in user to get user information
app.get("/user/:id", (req, res) => {
    let id = req.params.id;
    connection.query("SELECT `firstName`,`lastName`,`username`,`userType` FROM users WHERE id = ?", [id], (error, result, fields) => {
        if (error) { throw error; }
        res.json(result[0]);
    });
});

// PUT (update) user by id
// Take params from passed JSON and call function in user to update user information
app.put("/user/:id", (req, res) => {

});

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
    connection.query("SELECT `id`, `password` FROM users WHERE username = ?", [userName], (err, results, fields) => {
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
        }
        bcrypt.compare(password, results[0].password, (err, same) => {
            if (err) { 
                res.json({
                    success: false,
                    message: "An unexpected error occurred."
                }); 
            } 
            if (same) {
                res.json({
                    success: true,
                    message: results[0].id
                });
            } else {
                res.json({
                    success: false,
                    message: "Wrong password."
                });
            }
        });
    });
});

// GET rat data from database by page
// Need to use pages because we cannot pass all 100000+ rows to user
// Take page param and call function in data to get rows
app.get("/data/:lastId/:millis", (req, res) => {
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

app.get("/data/search/:minDate/:maxDate", (req, res) => {
    let minDate = new Date(parseInt(req.params.minDate, 10));
    let maxDate = new Date(parseInt(req.params.maxDate, 10));
    connection.query("SELECT * FROM data WHERE `createdDate` >= ? AND `createdDate` <= ?", [minDate, maxDate], (err, results, fields) => {
        if (err) { throw err; }
        res.json(results);
    })
});

// POST (create) rat data
// Take params from passed JSON and call function in data to create
app.post("/data/add", (req, res) => {
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

// PUT (update) data by id
// Take params from passed JSON and call function in data to update data
app.put("/data/:id", (req, res) => {

});

// DELETE rat data
// Take id param and call function in data to delete rows
app.delete("/data/:id", (req, res) => {
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
