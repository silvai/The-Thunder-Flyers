const express = require("express");
const app = express();
const bodyParser = require("body-parser");

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

});

// PUT (update) user by id
// Take params from passed JSON and call function in user to update user information
app.put("/user/:id", (req, res) => {

});

// POST (create) user
// Take params from passed JSON and call function in user to create
app.post("/auth/register", (req, res) => {

});

// POST user details to authenticate
// Take params from passed JSON and call function in user to authenticate
app.post("/auth/login", (req, res) => {

});

// GET rat data from database by page
// Need to use pages because we cannot pass all 100000+ rows to user
// Take page param and call function in data to get rows
app.get("/data/:page", (req, res) => {
    let page = req.params.page;
    connection.query("SELECT * FROM data LIMIT 20 OFFSET ?", [20 * page], (err, result, fields) => {
        if (err) { throw err; }
        res.json(result);
    });
});

// POST (create) rat data
// Take params from passed JSON and call function in data to create
app.post("/data/add", (req, res) => {

});

// PUT (update) data by id
// Take params from passed JSON and call function in data to update data
app.put("/data/:id", (req, res) => {

});

// DELETE rat data
// Take id param and call function in data to delete rows
app.delete("/data/:id", (req, res) => {
    
});

app.listen(3000, () => {});

module.exports = app;
