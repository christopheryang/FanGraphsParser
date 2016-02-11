SET foreign_key_checks = 0;

DROP TABLE IF EXISTS Player;
CREATE TABLE Player (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	uid VARCHAR(64) NOT NULL,
	playerType VARCHAR(10) NOT NULL,
	name VARCHAR(128) NOT NULL,
	firstName VARCHAR(64),
	lastName VARCHAR(64),
	birthdate DATE,
	
	batsOn VARCHAR(10),
	throwsWith VARCHAR(10),
	height INT,
	weight INT,
	positions VARCHAR(32),
	
	UNIQUE KEY UC_Player1 (uid),
	INDEX IDX_TRANSACTION1 (uid, playerType, name, firstName, lastName, birthdate, batsOn, throwsWith)

) ENGINE=InnoDB;


SET foreign_key_checks = 1;