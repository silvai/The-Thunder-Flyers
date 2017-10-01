process.env.NODE_ENV = 'test';

const user = require("../src/user");

let chai = require("chai");
let chaiHttp = require("chai-http");
let server = require("../src/index");
let should = chai.should();

chai.use(chaiHttp);

describe("/GET user by id (user.js get)", () => {
       it("should get a single user", (done) => {
               chai.request(server)
	       .get("/user/1")
	       .end((err, res) => {
		       err.should.equal.null;
		       res.should.not.equal.null;
	               res.should.have.status(200);
	               res.body.should.be.a("object");
	               res.should.be.json;
		       res.body.username.should.equal("system");
	       });
       });
});
		       	       


