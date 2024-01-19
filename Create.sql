CREATE TABLE `Airlines` (
	`Airline_ID` varchar(10) NOT NULL,
	`Airline_Name` varchar(50) NOT NULL UNIQUE,
	`Airport_Code` varchar(20) NOT NULL,
	PRIMARY KEY (`Airline_ID`)
);

CREATE TABLE `Aircraft` (
	`Manufacture` varchar(20) NOT NULL,
	`Model` varchar(30) NOT NULL,
	`Registration` varchar(10) NOT NULL,
	`Year_of_Manufacture` INT(05) NOT NULL,
	`Airline_ID` varchar(10) NOT NULL,
	PRIMARY KEY (`Registration`)
);

CREATE TABLE `Flight_Description` (
	`Flight_Number` varchar(10) NOT NULL,
	`Registration` varchar(10) NOT NULL,
	`Route` varchar(50) NOT NULL,
	`Distance` varchar(10) NOT NULL,
	`Departure_Time` TIME(06) NOT NULL,
	`Arrival_Time` TIME(06) NOT NULL,
	PRIMARY KEY (`Flight_Number`)
);

ALTER TABLE `Aircraft` ADD CONSTRAINT `Aircraft_fk0` FOREIGN KEY (`Airline_ID`) REFERENCES `Airlines`(`Airline_ID`);

ALTER TABLE `Flight_Description` ADD CONSTRAINT `Flight_Description_fk0` FOREIGN KEY (`Registration`) REFERENCES `Aircraft`(`Registration`);




