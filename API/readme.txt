In order for the API to work, the ratapp.sql file must be run as root on mysql when terminal is in this directory:
"mysql -u root -p"
When in mysql, run "source ./ratapp.sql;"
If anything goes wrong, run "drop database ratapp;" then "drop user cs2340@localhost" to undo what the script did.
You should end up with a database called ratapp that has two tables: users and data. Users should contain 1 user, which is a dummy user to allow the CSV import to work.
Data should contain 100000+ records and you should see that 1700+ warnings were thrown. This is alright because some of the data is malformed.

In order to start the API, run "npm start"
