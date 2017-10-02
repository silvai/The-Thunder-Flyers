process.env.NODE_ENV = 'test';

const user = require("../src/user");

let chai = require("chai");
let chaiHttp = require("chai-http");
let server = require("../src/index");
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
			res.body.firstName.should.equal("a");
			res.body.lastName.should.equal("b");
			res.body.username.should.equal("system");
			res.body.userType.should.equal("admin");
			done();
	    });
    });
});
		       	       


