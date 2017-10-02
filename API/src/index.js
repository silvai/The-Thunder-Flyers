const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const user = require("./user");
const data = require("./data");

app.use(bodyParser.json());

// GET user by id
// Call function in user to get user information
app.get("/user/:id", user.get);

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
app.get("/data/:page", data.get);

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
