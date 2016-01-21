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
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,
	-- Dashboard section
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
	-- Standard section
	cg INT,
	sho INT,
	hld INT,
	bs INT,
	tbf INT,
	hits INT,
	runs INT,
	er INT,
	hr INT,
	bb INT,
	ibb INT,
	hbp INT,
	wp INT,
	bk INT,
	so INT,
	-- Advanced section
	kPerc DECIMAL(10,3),
	bbPerc DECIMAL(10,3),
	avg DECIMAL(10,3),
	whip DECIMAL(10,2),
	eraMinus INT,
	fipMinus INT,
	-- Batted Ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3), -- In field fly ball %
	ifhPerc DECIMAL(10,3), -- In field hit %
	buhPerc DECIMAL(10,3), -- Bunt hit %
	pullPerc DECIMAL(10,3), -- % of pull direction
	centPerc DECIMAL(10,3), -- % of center direction
	oppoPerc DECIMAL(10,3), -- % of opposite direction
	softPerc DECIMAL(10,3), -- % of soft contact
	medPerc DECIMAL(10,3), -- % of medium contact
	hardPerc DECIMAL(10,3), -- % of hard contact
	siera DECIMAL(10,2), -- Skill Interactive ERA
	xFipMinus INT, -- xFIP adjusted for park
	-- More Batted Ball secion
	gb INT,
	fb INT, 
	ld INT, -- line drive
	iffb INT, -- in field fly ball
	ifh INT, -- in field hits
	bu INT, -- bunts
	buh INT, -- bunt singles
	rs INT, -- run support
	balls INT,
	strikes INT,
	pitches INT,	
	-- Win Probability section
	wpa DECIMAL(10,2), -- win probability added
	wpaMinus DECIMAL(10,2), -- loss advancement
	wpaPlus DECIMAL(10,2), -- win advancement
	re24 DECIMAL(10,2), -- run above average base on 24 base/out states
	rew DECIMAL(10,2), -- win above average base on 24 base/out states
	pLi DECIMAL(10,2), -- above leverage index
	inLi DECIMAL(10,2), -- average leverage index at the start of the inning
	gmLi DECIMAL(10,2), -- average leverage index when entering the game
	exLi DECIMAL(10,2), -- average leverage index when existing the game
	pulls INT, -- number of times pulled from the game
	wpaPerLi DECIMAL(10,2), -- situational wins
	clutch DECIMAL(10,2), -- how much better or worse a player perform under high leverage situation. 0 is neutral.
	sd INT, -- shutdowns
	md INT, -- meltdowns
	-- Pitch Type section
	bisFbPerc DECIMAL(10,3), -- Fast ball %
	bisFbVelocity DECIMAL(10,1),
	bisSlPerc DECIMAL(10,3), -- slider %
	bisSlVelocity DECIMAL(10,1),
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCtVelocity DECIMAL(10,1),
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisCbVelocity DECIMAL(10,1),
	bisChPerc DECIMAL(10,3), -- changeup %
	bisChVelocity DECIMAL(10,1),
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisSfVelocity DECIMAL(10,1),
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisKnVelocity DECIMAL(10,1),
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type and Velocity (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFaVelocity DECIMAL(10,1),
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFtVelocity DECIMAL(10,1),
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFcVelocity DECIMAL(10,1),
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFsVelocity DECIMAL(10,1),
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxFoVelocity DECIMAL(10,1),
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSiVelocity DECIMAL(10,1),
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxSlVelocity DECIMAL(10,1),
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxCuVelocity DECIMAL(10,1),
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxKcVelocity DECIMAL(10,1),
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxEpVelocity DECIMAL(10,1),
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxChVelocity DECIMAL(10,1),
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxScVelocity DECIMAL(10,1),
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxKnVelocity DECIMAL(10,1),
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitch Values
	bisWFb DECIMAL(10,1), -- Fast ball runs above average
	bisWFbPer100 DECIMAL(10,2), -- Fast ball runs above average per 100 pitches
	bisWSl DECIMAL(10,1),
	bisWSlPer100 DECIMAL(10,2),
	bisWCt DECIMAL(10,1),
	bisWCtPer100 DECIMAL(10,2),
	bisWCb DECIMAL(10,1),
	bisWCbPer100 DECIMAL(10,2),
	bisWCh DECIMAL(10,1),
	bisWChPer100 DECIMAL(10,2),
	bisWSf DECIMAL(10,1),
	bisWSfPer100 DECIMAL(10,2),
	bisWKn DECIMAL(10,1),
	bisWKnPer100 DECIMAL(10,2),
	-- Pitchf/x Pitch Values
	pfxWFa DECIMAL(10,1), -- Fast ball runs above average
	pfxWFaPer100 DECIMAL(10,2),
	pfxWFt DECIMAL(10,1),
	pfxWFtPer100 DECIMAL(10,2),
	pfxWFc DECIMAL(10,1),
	pfxWFcPer100 DECIMAL(10,2),
	pfxWFs DECIMAL(10,1),
	pfxWFsPer100 DECIMAL(10,2),
	pfxWFo DECIMAL(10,1),
	pfxWFoPer100 DECIMAL(10,2),
	pfxWSi DECIMAL(10,1),
	pfxWSiPer100 DECIMAL(10,2),
	pfxWSl DECIMAL(10,1),
	pfxWSlPer100 DECIMAL(10,2),
	pfxWCu DECIMAL(10,1),
	pfxWCuPer100 DECIMAL(10,2),
	pfxWKc DECIMAL(10,1),
	pfxWKcPer100 DECIMAL(10,2),
	pfxWEp DECIMAL(10,1),
	pfxWEpPer100 DECIMAL(10,2),
	pfxWCh DECIMAL(10,1),
	pfxWChPer100 DECIMAL(10,2),
	pfxWSc DECIMAL(10,1),
	pfxWScPer100 DECIMAL(10,2),
	pfxWKn DECIMAL(10,1),
	pfxWKnPer100 DECIMAL(10,2),
	-- Plate Discipline
	bisOSwingPerc DECIMAL(10,3),
	bisZSwingPerc DECIMAL(10,3),
	bisSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	bisOContactPerc DECIMAL(10,3),
	bisZContactPerc DECIMAL(10,3),
	bisContactPerc DECIMAL(10,3),
	bisZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	bisFStrikePerc DECIMAL(10,3),
	bisSwStrPerc DECIMAL(10,3), -- % of strikes swung at and missed
	-- Pitchf/x Plate Discipline
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,1), -- average time between pitches

	
	UNIQUE KEY UC_PitcherRegularSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;


DROP TABLE IF EXISTS PitcherRegularSeasonPartialStats;
CREATE TABLE PitcherRegularSeasonPartialStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,
	-- Dashboard section
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
	-- Standard section
	cg INT,
	sho INT,
	hld INT,
	bs INT,
	tbf INT,
	hits INT,
	runs INT,
	er INT,
	hr INT,
	bb INT,
	ibb INT,
	hbp INT,
	wp INT,
	bk INT,
	so INT,
	-- Advanced section
	kPerc DECIMAL(10,3),
	bbPerc DECIMAL(10,3),
	avg DECIMAL(10,3),
	whip DECIMAL(10,2),
	eraMinus INT,
	fipMinus INT,
	-- Batted Ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3), -- In field fly ball %
	ifhPerc DECIMAL(10,3), -- In field hit %
	buhPerc DECIMAL(10,3), -- Bunt hit %
	pullPerc DECIMAL(10,3), -- % of pull direction
	centPerc DECIMAL(10,3), -- % of center direction
	oppoPerc DECIMAL(10,3), -- % of opposite direction
	softPerc DECIMAL(10,3), -- % of soft contact
	medPerc DECIMAL(10,3), -- % of medium contact
	hardPerc DECIMAL(10,3), -- % of hard contact
	siera DECIMAL(10,2), -- Skill Interactive ERA
	xFipMinus INT, -- xFIP adjusted for park
	-- More Batted Ball secion
	gb INT,
	fb INT, 
	ld INT, -- line drive
	iffb INT, -- in field fly ball
	ifh INT, -- in field hits
	bu INT, -- bunts
	buh INT, -- bunt singles
	rs INT, -- run support
	balls INT,
	strikes INT,
	pitches INT,
	-- Win Probability section
	wpa DECIMAL(10,2), -- win probability added
	wpaMinus DECIMAL(10,2), -- loss advancement
	wpaPlus DECIMAL(10,2), -- win advancement
	re24 DECIMAL(10,2), -- run above average base on 24 base/out states
	rew DECIMAL(10,2), -- win above average base on 24 base/out states
	pLi DECIMAL(10,2), -- above leverage index
	inLi DECIMAL(10,2), -- average leverage index at the start of the inning
	gmLi DECIMAL(10,2), -- average leverage index when entering the game
	exLi DECIMAL(10,2), -- average leverage index when existing the game
	pulls INT, -- number of times pulled from the game
	wpaPerLi DECIMAL(10,2), -- situational wins
	clutch DECIMAL(10,2), -- how much better or worse a player perform under high leverage situation. 0 is neutral.
	sd INT, -- shutdowns
	md INT, -- meltdowns
	-- Pitch Type section
	bisFbPerc DECIMAL(10,3), -- Fast ball %
	bisFbVelocity DECIMAL(10,1),
	bisSlPerc DECIMAL(10,3), -- slider %
	bisSlVelocity DECIMAL(10,1),
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCtVelocity DECIMAL(10,1),
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisCbVelocity DECIMAL(10,1),
	bisChPerc DECIMAL(10,3), -- changeup %
	bisChVelocity DECIMAL(10,1),
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisSfVelocity DECIMAL(10,1),
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisKnVelocity DECIMAL(10,1),
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type and Velocity (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFaVelocity DECIMAL(10,1),
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFtVelocity DECIMAL(10,1),
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFcVelocity DECIMAL(10,1),
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFsVelocity DECIMAL(10,1),
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxFoVelocity DECIMAL(10,1),
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSiVelocity DECIMAL(10,1),
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxSlVelocity DECIMAL(10,1),
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxCuVelocity DECIMAL(10,1),
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxKcVelocity DECIMAL(10,1),
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxEpVelocity DECIMAL(10,1),
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxChVelocity DECIMAL(10,1),
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxScVelocity DECIMAL(10,1),
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxKnVelocity DECIMAL(10,1),
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitch Values
	bisWFb DECIMAL(10,1), -- Fast ball runs above average
	bisWFbPer100 DECIMAL(10,2), -- Fast ball runs above average per 100 pitches
	bisWSl DECIMAL(10,1),
	bisWSlPer100 DECIMAL(10,2),
	bisWCt DECIMAL(10,1),
	bisWCtPer100 DECIMAL(10,2),
	bisWCb DECIMAL(10,1),
	bisWCbPer100 DECIMAL(10,2),
	bisWCh DECIMAL(10,1),
	bisWChPer100 DECIMAL(10,2),
	bisWSf DECIMAL(10,1),
	bisWSfPer100 DECIMAL(10,2),
	bisWKn DECIMAL(10,1),
	bisWKnPer100 DECIMAL(10,2),
	-- Pitchf/x Pitch Values
	pfxWFa DECIMAL(10,1), -- Fast ball runs above average
	pfxWFaPer100 DECIMAL(10,2),
	pfxWFt DECIMAL(10,1),
	pfxWFtPer100 DECIMAL(10,2),
	pfxWFc DECIMAL(10,1),
	pfxWFcPer100 DECIMAL(10,2),
	pfxWFs DECIMAL(10,1),
	pfxWFsPer100 DECIMAL(10,2),
	pfxWFo DECIMAL(10,1),
	pfxWFoPer100 DECIMAL(10,2),
	pfxWSi DECIMAL(10,1),
	pfxWSiPer100 DECIMAL(10,2),
	pfxWSl DECIMAL(10,1),
	pfxWSlPer100 DECIMAL(10,2),
	pfxWCu DECIMAL(10,1),
	pfxWCuPer100 DECIMAL(10,2),
	pfxWKc DECIMAL(10,1),
	pfxWKcPer100 DECIMAL(10,2),
	pfxWEp DECIMAL(10,1),
	pfxWEpPer100 DECIMAL(10,2),
	pfxWCh DECIMAL(10,1),
	pfxWChPer100 DECIMAL(10,2),
	pfxWSc DECIMAL(10,1),
	pfxWScPer100 DECIMAL(10,2),
	pfxWKn DECIMAL(10,1),
	pfxWKnPer100 DECIMAL(10,2),
	-- Plate Discipline
	bisOSwingPerc DECIMAL(10,3),
	bisZSwingPerc DECIMAL(10,3),
	bisSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	bisOContactPerc DECIMAL(10,3),
	bisZContactPerc DECIMAL(10,3),
	bisContactPerc DECIMAL(10,3),
	bisZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	bisFStrikePerc DECIMAL(10,3),
	bisSwStrPerc DECIMAL(10,3), -- % of strikes swung at and missed
	-- Pitchf/x Plate Discipline
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,1), -- average time between pitches

	
	UNIQUE KEY UC_PitcherRegularSeasonPartialStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonPartialStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonPartialStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;


DROP TABLE IF EXISTS PitcherRegularSeasonProjectedStats;
CREATE TABLE PitcherRegularSeasonProjectedStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,
	-- Dashboard section
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
	-- Standard section
	cg INT,
	sho INT,
	hld INT,
	bs INT,
	tbf INT,
	hits INT,
	runs INT,
	er INT,
	hr INT,
	bb INT,
	ibb INT,
	hbp INT,
	wp INT,
	bk INT,
	so INT,
	-- Advanced section
	kPerc DECIMAL(10,3),
	bbPerc DECIMAL(10,3),
	avg DECIMAL(10,3),
	whip DECIMAL(10,2),
	eraMinus INT,
	fipMinus INT,
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3), -- In field fly ball %
	ifhPerc DECIMAL(10,3), -- In field hit %
	buhPerc DECIMAL(10,3), -- Bunt hit %
	pullPerc DECIMAL(10,3), -- % of pull direction
	centPerc DECIMAL(10,3), -- % of center direction
	oppoPerc DECIMAL(10,3), -- % of opposite direction
	softPerc DECIMAL(10,3), -- % of soft contact
	medPerc DECIMAL(10,3), -- % of medium contact
	hardPerc DECIMAL(10,3), -- % of hard contact
	siera DECIMAL(10,2), -- Skill Interactive ERA
	xFipMinus INT, -- xFIP adjusted for park
	-- More Batted Ball secion
	gb INT,
	fb INT, 
	ld INT, -- line drive
	iffb INT, -- in field fly ball
	ifh INT, -- in field hits
	bu INT, -- bunts
	buh INT, -- bunt singles
	rs INT, -- run support
	balls INT,
	strikes INT,
	pitches INT,
	-- Win Probability section
	wpa DECIMAL(10,2), -- win probability added
	wpaMinus DECIMAL(10,2), -- loss advancement
	wpaPlus DECIMAL(10,2), -- win advancement
	re24 DECIMAL(10,2), -- run above average base on 24 base/out states
	rew DECIMAL(10,2), -- win above average base on 24 base/out states
	pLi DECIMAL(10,2), -- above leverage index
	inLi DECIMAL(10,2), -- average leverage index at the start of the inning
	gmLi DECIMAL(10,2), -- average leverage index when entering the game
	exLi DECIMAL(10,2), -- average leverage index when existing the game
	pulls INT, -- number of times pulled from the game
	wpaPerLi DECIMAL(10,2), -- situational wins
	clutch DECIMAL(10,2), -- how much better or worse a player perform under high leverage situation. 0 is neutral.
	sd INT, -- shutdowns
	md INT, -- meltdowns
	-- Pitch Type section
	bisFbPerc DECIMAL(10,3), -- Fast ball %
	bisFbVelocity DECIMAL(10,1),
	bisSlPerc DECIMAL(10,3), -- slider %
	bisSlVelocity DECIMAL(10,1),
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCtVelocity DECIMAL(10,1),
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisCbVelocity DECIMAL(10,1),
	bisChPerc DECIMAL(10,3), -- changeup %
	bisChVelocity DECIMAL(10,1),
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisSfVelocity DECIMAL(10,1),
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisKnVelocity DECIMAL(10,1),
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type and Velocity (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFaVelocity DECIMAL(10,1),
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFtVelocity DECIMAL(10,1),
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFcVelocity DECIMAL(10,1),
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFsVelocity DECIMAL(10,1),
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxFoVelocity DECIMAL(10,1),
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSiVelocity DECIMAL(10,1),
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxSlVelocity DECIMAL(10,1),
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxCuVelocity DECIMAL(10,1),
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxKcVelocity DECIMAL(10,1),
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxEpVelocity DECIMAL(10,1),
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxChVelocity DECIMAL(10,1),
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxScVelocity DECIMAL(10,1),
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxKnVelocity DECIMAL(10,1),
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitch Values
	bisWFb DECIMAL(10,1), -- Fast ball runs above average
	bisWFbPer100 DECIMAL(10,2), -- Fast ball runs above average per 100 pitches
	bisWSl DECIMAL(10,1),
	bisWSlPer100 DECIMAL(10,2),
	bisWCt DECIMAL(10,1),
	bisWCtPer100 DECIMAL(10,2),
	bisWCb DECIMAL(10,1),
	bisWCbPer100 DECIMAL(10,2),
	bisWCh DECIMAL(10,1),
	bisWChPer100 DECIMAL(10,2),
	bisWSf DECIMAL(10,1),
	bisWSfPer100 DECIMAL(10,2),
	bisWKn DECIMAL(10,1),
	bisWKnPer100 DECIMAL(10,2),
	-- Pitchf/x Pitch Values
	pfxWFa DECIMAL(10,1), -- Fast ball runs above average
	pfxWFaPer100 DECIMAL(10,2),
	pfxWFt DECIMAL(10,1),
	pfxWFtPer100 DECIMAL(10,2),
	pfxWFc DECIMAL(10,1),
	pfxWFcPer100 DECIMAL(10,2),
	pfxWFs DECIMAL(10,1),
	pfxWFsPer100 DECIMAL(10,2),
	pfxWFo DECIMAL(10,1),
	pfxWFoPer100 DECIMAL(10,2),
	pfxWSi DECIMAL(10,1),
	pfxWSiPer100 DECIMAL(10,2),
	pfxWSl DECIMAL(10,1),
	pfxWSlPer100 DECIMAL(10,2),
	pfxWCu DECIMAL(10,1),
	pfxWCuPer100 DECIMAL(10,2),
	pfxWKc DECIMAL(10,1),
	pfxWKcPer100 DECIMAL(10,2),
	pfxWEp DECIMAL(10,1),
	pfxWEpPer100 DECIMAL(10,2),
	pfxWCh DECIMAL(10,1),
	pfxWChPer100 DECIMAL(10,2),
	pfxWSc DECIMAL(10,1),
	pfxWScPer100 DECIMAL(10,2),
	pfxWKn DECIMAL(10,1),
	pfxWKnPer100 DECIMAL(10,2),
	-- Plate Discipline
	bisOSwingPerc DECIMAL(10,3),
	bisZSwingPerc DECIMAL(10,3),
	bisSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	bisOContactPerc DECIMAL(10,3),
	bisZContactPerc DECIMAL(10,3),
	bisContactPerc DECIMAL(10,3),
	bisZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	bisFStrikePerc DECIMAL(10,3),
	bisSwStrPerc DECIMAL(10,3), -- % of strikes swung at and missed
	-- Pitchf/x Plate Discipline
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,1), -- average time between pitches

	
	UNIQUE KEY UC_PitcherRegularSeasonProjectedStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonProjectedStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonProjectedStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;


DROP TABLE IF EXISTS PitcherPostSeasonStats;
CREATE TABLE PitcherPostSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
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
	-- Standard section
	cg INT,
	sho INT,
	hld INT,
	bs INT,
	tbf INT,
	hits INT,
	runs INT,
	er INT,
	hr INT,
	bb INT,
	ibb INT,
	hbp INT,
	wp INT,
	bk INT,
	so INT,
	-- Advanced section
	kPerc DECIMAL(10,3),
	bbPerc DECIMAL(10,3),
	avg DECIMAL(10,3),
	whip DECIMAL(10,2),
	eraMinus INT,
	fipMinus INT,
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3), -- In field fly ball %
	ifhPerc DECIMAL(10,3), -- In field hit %
	buhPerc DECIMAL(10,3), -- Bunt hit %
	pullPerc DECIMAL(10,3), -- % of pull direction
	centPerc DECIMAL(10,3), -- % of center direction
	oppoPerc DECIMAL(10,3), -- % of opposite direction
	softPerc DECIMAL(10,3), -- % of soft contact
	medPerc DECIMAL(10,3), -- % of medium contact
	hardPerc DECIMAL(10,3), -- % of hard contact
	siera DECIMAL(10,2), -- Skill Interactive ERA
	xFipMinus INT, -- xFIP adjusted for park
	-- More Batted Ball secion
	gb INT,
	fb INT, 
	ld INT, -- line drive
	iffb INT, -- in field fly ball
	ifh INT, -- in field hits
	bu INT, -- bunts
	buh INT, -- bunt singles
	rs INT, -- run support
	balls INT,
	strikes INT,
	pitches INT,
	-- Win Probability section
	wpa DECIMAL(10,2), -- win probability added
	wpaMinus DECIMAL(10,2), -- loss advancement
	wpaPlus DECIMAL(10,2), -- win advancement
	re24 DECIMAL(10,2), -- run above average base on 24 base/out states
	rew DECIMAL(10,2), -- win above average base on 24 base/out states
	pLi DECIMAL(10,2), -- above leverage index
	inLi DECIMAL(10,2), -- average leverage index at the start of the inning
	gmLi DECIMAL(10,2), -- average leverage index when entering the game
	exLi DECIMAL(10,2), -- average leverage index when existing the game
	pulls INT, -- number of times pulled from the game
	wpaPerLi DECIMAL(10,2), -- situational wins
	clutch DECIMAL(10,2), -- how much better or worse a player perform under high leverage situation. 0 is neutral.
	sd INT, -- shutdowns
	md INT, -- meltdowns
	-- Pitch Type section
	bisFbPerc DECIMAL(10,3), -- Fast ball %
	bisFbVelocity DECIMAL(10,1),
	bisSlPerc DECIMAL(10,3), -- slider %
	bisSlVelocity DECIMAL(10,1),
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCtVelocity DECIMAL(10,1),
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisCbVelocity DECIMAL(10,1),
	bisChPerc DECIMAL(10,3), -- changeup %
	bisChVelocity DECIMAL(10,1),
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisSfVelocity DECIMAL(10,1),
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisKnVelocity DECIMAL(10,1),
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type and Velocity (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFaVelocity DECIMAL(10,1),
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFtVelocity DECIMAL(10,1),
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFcVelocity DECIMAL(10,1),
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFsVelocity DECIMAL(10,1),
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxFoVelocity DECIMAL(10,1),
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSiVelocity DECIMAL(10,1),
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxSlVelocity DECIMAL(10,1),
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxCuVelocity DECIMAL(10,1),
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxKcVelocity DECIMAL(10,1),
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxEpVelocity DECIMAL(10,1),
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxChVelocity DECIMAL(10,1),
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxScVelocity DECIMAL(10,1),
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxKnVelocity DECIMAL(10,1),
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitch Values
	bisWFb DECIMAL(10,1), -- Fast ball runs above average
	bisWFbPer100 DECIMAL(10,2), -- Fast ball runs above average per 100 pitches
	bisWSl DECIMAL(10,1),
	bisWSlPer100 DECIMAL(10,2),
	bisWCt DECIMAL(10,1),
	bisWCtPer100 DECIMAL(10,2),
	bisWCb DECIMAL(10,1),
	bisWCbPer100 DECIMAL(10,2),
	bisWCh DECIMAL(10,1),
	bisWChPer100 DECIMAL(10,2),
	bisWSf DECIMAL(10,1),
	bisWSfPer100 DECIMAL(10,2),
	bisWKn DECIMAL(10,1),
	bisWKnPer100 DECIMAL(10,2),
	-- Pitchf/x Pitch Values
	pfxWFa DECIMAL(10,1), -- Fast ball runs above average
	pfxWFaPer100 DECIMAL(10,2),
	pfxWFt DECIMAL(10,1),
	pfxWFtPer100 DECIMAL(10,2),
	pfxWFc DECIMAL(10,1),
	pfxWFcPer100 DECIMAL(10,2),
	pfxWFs DECIMAL(10,1),
	pfxWFsPer100 DECIMAL(10,2),
	pfxWFo DECIMAL(10,1),
	pfxWFoPer100 DECIMAL(10,2),
	pfxWSi DECIMAL(10,1),
	pfxWSiPer100 DECIMAL(10,2),
	pfxWSl DECIMAL(10,1),
	pfxWSlPer100 DECIMAL(10,2),
	pfxWCu DECIMAL(10,1),
	pfxWCuPer100 DECIMAL(10,2),
	pfxWKc DECIMAL(10,1),
	pfxWKcPer100 DECIMAL(10,2),
	pfxWEp DECIMAL(10,1),
	pfxWEpPer100 DECIMAL(10,2),
	pfxWCh DECIMAL(10,1),
	pfxWChPer100 DECIMAL(10,2),
	pfxWSc DECIMAL(10,1),
	pfxWScPer100 DECIMAL(10,2),
	pfxWKn DECIMAL(10,1),
	pfxWKnPer100 DECIMAL(10,2),
	-- Plate Discipline
	bisOSwingPerc DECIMAL(10,3),
	bisZSwingPerc DECIMAL(10,3),
	bisSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	bisOContactPerc DECIMAL(10,3),
	bisZContactPerc DECIMAL(10,3),
	bisContactPerc DECIMAL(10,3),
	bisZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	bisFStrikePerc DECIMAL(10,3),
	bisSwStrPerc DECIMAL(10,3), -- % of strikes swung at and missed
	-- Pitchf/x Plate Discipline
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,1), -- average time between pitches
	
	
	UNIQUE KEY UC_PitcherPostSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherPostSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherPostSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;

DROP TABLE IF EXISTS PitcherMinorsSeasonStats;
CREATE TABLE PitcherMinorsSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
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
	-- Standard section
	cg INT,
	sho INT,
	hld INT,
	bs INT,
	tbf INT,
	hits INT,
	runs INT,
	er INT,
	hr INT,
	bb INT,
	ibb INT,
	hbp INT,
	wp INT,
	bk INT,
	so INT,
	-- Advanced section
	kPerc DECIMAL(10,3),
	bbPerc DECIMAL(10,3),
	avg DECIMAL(10,3),
	whip DECIMAL(10,2),
	eraMinus INT,
	fipMinus INT,
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3), -- In field fly ball %
	ifhPerc DECIMAL(10,3), -- In field hit %
	buhPerc DECIMAL(10,3), -- Bunt hit %
	pullPerc DECIMAL(10,3), -- % of pull direction
	centPerc DECIMAL(10,3), -- % of center direction
	oppoPerc DECIMAL(10,3), -- % of opposite direction
	softPerc DECIMAL(10,3), -- % of soft contact
	medPerc DECIMAL(10,3), -- % of medium contact
	hardPerc DECIMAL(10,3), -- % of hard contact
	siera DECIMAL(10,2), -- Skill Interactive ERA
	xFipMinus INT, -- xFIP adjusted for park
	-- More Batted Ball secion
	gb INT,
	fb INT, 
	ld INT, -- line drive
	iffb INT, -- in field fly ball
	ifh INT, -- in field hits
	bu INT, -- bunts
	buh INT, -- bunt singles
	rs INT, -- run support
	balls INT,
	strikes INT,
	pitches INT,
	-- Win Probability section
	wpa DECIMAL(10,2), -- win probability added
	wpaMinus DECIMAL(10,2), -- loss advancement
	wpaPlus DECIMAL(10,2), -- win advancement
	re24 DECIMAL(10,2), -- run above average base on 24 base/out states
	rew DECIMAL(10,2), -- win above average base on 24 base/out states
	pLi DECIMAL(10,2), -- above leverage index
	inLi DECIMAL(10,2), -- average leverage index at the start of the inning
	gmLi DECIMAL(10,2), -- average leverage index when entering the game
	exLi DECIMAL(10,2), -- average leverage index when existing the game
	pulls INT, -- number of times pulled from the game
	wpaPerLi DECIMAL(10,2), -- situational wins
	clutch DECIMAL(10,2), -- how much better or worse a player perform under high leverage situation. 0 is neutral.
	sd INT, -- shutdowns
	md INT, -- meltdowns
	-- Pitch Type section
	bisFbPerc DECIMAL(10,3), -- Fast ball %
	bisFbVelocity DECIMAL(10,1),
	bisSlPerc DECIMAL(10,3), -- slider %
	bisSlVelocity DECIMAL(10,1),
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCtVelocity DECIMAL(10,1),
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisCbVelocity DECIMAL(10,1),
	bisChPerc DECIMAL(10,3), -- changeup %
	bisChVelocity DECIMAL(10,1),
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisSfVelocity DECIMAL(10,1),
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisKnVelocity DECIMAL(10,1),
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type and Velocity (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFaVelocity DECIMAL(10,1),
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFtVelocity DECIMAL(10,1),
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFcVelocity DECIMAL(10,1),
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFsVelocity DECIMAL(10,1),
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxFoVelocity DECIMAL(10,1),
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSiVelocity DECIMAL(10,1),
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxSlVelocity DECIMAL(10,1),
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxCuVelocity DECIMAL(10,1),
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxKcVelocity DECIMAL(10,1),
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxEpVelocity DECIMAL(10,1),
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxChVelocity DECIMAL(10,1),
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxScVelocity DECIMAL(10,1),
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxKnVelocity DECIMAL(10,1),
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitch Values
	bisWFb DECIMAL(10,1), -- Fast ball runs above average
	bisWFbPer100 DECIMAL(10,2), -- Fast ball runs above average per 100 pitches
	bisWSl DECIMAL(10,1),
	bisWSlPer100 DECIMAL(10,2),
	bisWCt DECIMAL(10,1),
	bisWCtPer100 DECIMAL(10,2),
	bisWCb DECIMAL(10,1),
	bisWCbPer100 DECIMAL(10,2),
	bisWCh DECIMAL(10,1),
	bisWChPer100 DECIMAL(10,2),
	bisWSf DECIMAL(10,1),
	bisWSfPer100 DECIMAL(10,2),
	bisWKn DECIMAL(10,1),
	bisWKnPer100 DECIMAL(10,2),
	-- Pitchf/x Pitch Values
	pfxWFa DECIMAL(10,1), -- Fast ball runs above average
	pfxWFaPer100 DECIMAL(10,2),
	pfxWFt DECIMAL(10,1),
	pfxWFtPer100 DECIMAL(10,2),
	pfxWFc DECIMAL(10,1),
	pfxWFcPer100 DECIMAL(10,2),
	pfxWFs DECIMAL(10,1),
	pfxWFsPer100 DECIMAL(10,2),
	pfxWFo DECIMAL(10,1),
	pfxWFoPer100 DECIMAL(10,2),
	pfxWSi DECIMAL(10,1),
	pfxWSiPer100 DECIMAL(10,2),
	pfxWSl DECIMAL(10,1),
	pfxWSlPer100 DECIMAL(10,2),
	pfxWCu DECIMAL(10,1),
	pfxWCuPer100 DECIMAL(10,2),
	pfxWKc DECIMAL(10,1),
	pfxWKcPer100 DECIMAL(10,2),
	pfxWEp DECIMAL(10,1),
	pfxWEpPer100 DECIMAL(10,2),
	pfxWCh DECIMAL(10,1),
	pfxWChPer100 DECIMAL(10,2),
	pfxWSc DECIMAL(10,1),
	pfxWScPer100 DECIMAL(10,2),
	pfxWKn DECIMAL(10,1),
	pfxWKnPer100 DECIMAL(10,2),
	-- Plate Discipline
	bisOSwingPerc DECIMAL(10,3),
	bisZSwingPerc DECIMAL(10,3),
	bisSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	bisOContactPerc DECIMAL(10,3),
	bisZContactPerc DECIMAL(10,3),
	bisContactPerc DECIMAL(10,3),
	bisZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	bisFStrikePerc DECIMAL(10,3),
	bisSwStrPerc DECIMAL(10,3), -- % of strikes swung at and missed
	-- Pitchf/x Plate Discipline
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,1), -- average time between pitches

	
	CONSTRAINT FK_PitcherMinorsSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherMinorsSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;

SET foreign_key_checks = 1;