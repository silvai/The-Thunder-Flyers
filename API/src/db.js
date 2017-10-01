const mysql = require("mysql");
const User = require("./user");
const Data = require("./data");

const connection = mysql.createConnection({
	host: "localhost",
	user: "cs2340",
	password: "cs2340ratapp",
	database: "ratapp"
});

connection.connect();

// @param cb	a callback function

// @param user 		a User object
module.exports.addUserToDatabase = async function addUserToDatabase(user, cb) {
	// STUB
};

// @param username	the username of a User
module.exports.getUserFromDatabaseByUsername = async function getUserFromDatabaseByUsername(username, cb) {
	// STUB
};

// @param id		the id of a User
module.exports.getUserFromDatabaseById = async function getUserFromDatabaseById(id, cb) {
	// STUB
};

// @param id		 	the id of a User
// @param fieldstoChange	a JS object with keys that represent the fields and values that represent the new values of those fields
module.exports.updateUserInDatabase = async function updateUserInDatabase(id, fieldsToChange, cb) {
	// STUB
};

// @param data		a Data object
module.exports.addDataToDatabase = async function addDataToDatabase(data, cb) {
	// STUB
};

// @param page		the page of the data (1st page is 0 - 19th rows, 2nd page is 20 - 39th rows, etc.)
module.exports.getDataFromDatabase = async function getDataFromDatabase(page, cb) {
	// STUB
};

// @param id			the id of a Data
// @param fieldsToChange	a JS object with keys that represent the fields and values that represent the new values of those fields
module.exports.updateDataInDatabase = async function updateDataInDatabase(id, fieldsToChange, cb) {
	// STUB
};

// @param id		the id of a Data
module.exports.deleteDataFromDatabase = async function deleteDataFromDatabase(id, cb) {
	// STUB
};