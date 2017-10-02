CREATE USER 'cs2340'@'localhost';
SET PASSWORD FOR 'cs2340'@'localhost' = 'cs2340ratapp';
GRANT ALL PRIVILEGES ON *.* TO 'cs2340'@'localhost' WITH GRANT OPTION;
CREATE DATABASE ratapp;
USE ratapp;
CREATE TABLE users (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
	firstName VARCHAR(20) NOT NULL,
	lastName VARCHAR(40) NOT NULL,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(40) NOT NULL, 
        userType ENUM('USER', 'ADMIN') NOT NULL,
	lockout TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY (id)
);
INSERT INTO users VALUES ('1', 'a', 'b', 'system', 'password', 'ADMIN', '0');
CREATE TABLE data (
	id INT UNSIGNED NOT NULL,
        createdDate DATETIME NOT NULL,
        locationType ENUM('3+ Family Mixed Use Building', 'Commercial Building', '1-2 Family Dwelling', '3+ Family Apt. Building', 'Public Stairs', 'Other (Explain Below)', 'Hospital', 'Construction Site', 'Vacant Lot', 'Vacant Building', 'Parking Lot/Garage', 'Public Garden', '1-2 Family Mixed Use Building', 'Catch Basin/Sewer', 'Day Care/Nursery', 'Government Building', 'Office Building', 'School/Pre-School', 'Single Room Occupancy (SRO)'),
	incidentZip SMALLINT UNSIGNED NOT NULL,
        incidentAddress TEXT NOT NULL,
        city TEXT NOT NULL,
        borough ENUM('MANHATTAN', 'STATEN ISLAND', 'QUEENS', 'BROOKLYN', 'BRONX') NOT NULL,
        latitude DECIMAL(20, 17) NOT NULL,
        longitude DECIMAL(20, 17) NOT NULL,
	PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'Rat_Sightings.csv' INTO TABLE data
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
IGNORE 1 LINES
(id, @datetimevar, @dummy, @dummy, @dummy, @dummy, @dummy, locationType, incidentZip, incidentAddress, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, city, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, borough, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy,@dummy, @dummy, @dummy, @dummy, @dummy, @dummy,@dummy, @dummy, @dummy, @dummy, @dummy, @dummy,@dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, latitude, longitude, @dummy)
SET createdDate = STR_TO_DATE(@datetimevar, '%c/%e/%Y %r');
ALTER TABLE data ADD userId MEDIUMINT NOT NULL;
UPDATE data SET userId = 1;
ALTER TABLE data ADD CONSTRAINT fk_userId FOREIGN KEY (userId) REFERENCES users(id);