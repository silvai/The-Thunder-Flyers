process.env.NODE_ENV = 'test';

const user = require("../src/user");

let chai = require("chai");
let chaiHttp = require("chai-http");
let server = require("../src/index");
let mysql = require("mysql");
let should = chai.should();

chai.use(chaiHttp);

describe("GET /user/:id (user.js get)", () => {
    it("should get a single user", (done) => {
        chai.request(server)
	    .get("/user/1")
	    .end((err, res) => {
			should.not.exist(err);
			should.exist(res);
			should.not.equal(res, null);
	        res.should.have.status(200);
	        res.body.should.be.a("object");
			res.should.be.json;
			res.body.success.should.equal(true);
			res.body.message.should.be.a("object");
			should.exist(res.body.message.firstName);
			res.body.message.firstName.should.equal("a");
			res.body.message.lastName.should.equal("b");
			res.body.message.username.should.equal("system");
			res.body.message.userType.should.equal("ADMIN");
			done();
	    });
    });
});

describe("POST /auth/register (user.js create)", () => {
	it("should create a user", (done) => {
		let u = new user.User("tester", "mctester", "testuser", "testpass", "ADMIN");
		chai.request(server)
		.post("/auth/register")
		.send(u)
		.end((err, res) => {
			should.not.exist(err);
			should.exist(res);
			should.not.equal(res, null);
			res.should.have.status(200);
			res.body.should.be.a("object");
			res.should.be.json;
			res.body.success.should.equal(true);
			res.body.message.should.equal("2");
			done();
		});
	});
});
		       	       


