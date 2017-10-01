const db = require("./db");

/* Contains the User constructor function and methods called in index.js related to data.
 * Each method will end up calling some method in db.js, probably. req and res are Express objects,
 * more information is available on the Express website.
 */

module.exports.User = function User(/* params */) {
	// STUB
};

module.exports.get = async function get(req, res) {
	// STUB
	res.send("Not implemented yet.");
};

module.exports.update = async function update(req, res) {
	// STUB	
	res.send("Not implemented yet.");
};

module.exports.create = async function create(req, res) {
	// STUB
	res.send("Not implemented yet.");	
};

module.exports.authenticate = async function authenticate(req, res) {
	// STUB
        res.send("Not implemented yet.");
};
