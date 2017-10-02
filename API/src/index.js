const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const user = require("./user");
const data = require("./data");

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
        if (error) { throw err; }
        res.json(result[0]);
    });
});

// PUT (update) user by id
// Take params from passed JSON and call function in user to update user information
app.put("/user/:id", user.update);

// POST (create) user
// Take params from passed JSON and call function in user to create
app.post("/auth/register", user.create);

// POST user details to authenticate
// Take params from passed JSON and call function in user to authenticate
app.post("/auth/login", user.authenticate);

// GET rat data from database by page
// Need to use pages because we cannot pass all 100000+ rows to user
// Take page param and call function in data to get rows
app.get("/data/:page", (req, res) => {
    let page = req.params.page;
    connection.query("SELECT * FROM data WHERE `id` > ? LIMIT 20", [(20 * page) + 11464394], (err, result, fields) => {
        if (err) { throw err; }
        res.json(result);
    });
});

// POST (create) rat data
// Take params from passed JSON and call function in data to create
app.post("/data/add", data.create);

// PUT (update) data by id
// Take params from passed JSON and call function in data to update data
app.put("/data/:id", data.update);

// DELETE rat data
// Take id param and call function in data to delete rows
app.delete("/data/:id", data.ddelete);

app.listen(3000, () => {});

module.exports = app;
