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


DROP TABLE IF EXISTS PitcherRegularSeasonStats;
CREATE TABLE PitcherRegularSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,
	
	win INT,
	loss INT,
	save INT,
	games INT,
	gs INT,
	ip DECIMAL(10,1),
	kPer9 DECIMAL(10,2),
	bbPer9 DECIMAL(10,2),
	hrPer9 DECIMAL(10,2),
	babip DECIMAL(10,3),
	lobPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3), -- Ground ball per ball in play
	hrPerFb DECIMAL(10,3), -- HR per fly ball
	era DECIMAL(10,2),
	fip DECIMAL(10,2), -- Fielder independent pitching on ERA scale
	xFip DECIMAL(10,2), -- Expected FIP where HR/FB is set to 10.5%
	war DECIMAL(10,1),
	
	UNIQUE KEY UC_PitcherRegularSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;

DROP TABLE IF EXISTS PitcherPostSeasonStats;
CREATE TABLE PitcherPostSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,
	
	win INT,
	loss INT,
	save INT,
	games INT,
	gs INT,
	ip DECIMAL(10,1),
	kPer9 DECIMAL(10,2),
	bbPer9 DECIMAL(10,2),
	hrPer9 DECIMAL(10,2),
	babip DECIMAL(10,3),
	lobPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3), -- Ground ball per ball in play
	hrPerFb DECIMAL(10,3), -- HR per fly ball
	era DECIMAL(10,2),
	fip DECIMAL(10,2), -- Fielder independent pitching on ERA scale
	xFip DECIMAL(10,2), -- Expected FIP where HR/FB is set to 10.5%
	war DECIMAL(10,1),
	
	UNIQUE KEY UC_PitcherPostSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherPostSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherPostSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;

DROP TABLE IF EXISTS PitcherMinorsSeasonStats;
CREATE TABLE PitcherMinorsSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,
	
	win INT,
	loss INT,
	save INT,
	games INT,
	gs INT,
	ip DECIMAL(10,1),
	kPer9 DECIMAL(10,2),
	bbPer9 DECIMAL(10,2),
	hrPer9 DECIMAL(10,2),
	babip DECIMAL(10,3),
	lobPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3), -- Ground ball per ball in play
	hrPerFb DECIMAL(10,3), -- HR per fly ball
	era DECIMAL(10,2),
	fip DECIMAL(10,2), -- Fielder independent pitching on ERA scale
	xFip DECIMAL(10,2), -- Expected FIP where HR/FB is set to 10.5%
	war DECIMAL(10,1),
	
	CONSTRAINT FK_PitcherMinorsSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherMinorsSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;

SET foreign_key_checks = 1;