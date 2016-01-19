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
	bisSlPerc DECIMAL(10,3), -- slider %
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisChPerc DECIMAL(10,3), -- changeup %
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type section (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitchf/x Pitch Velocity section
	pfxFaVelocity DECIMAL(10,1),
	pfxFtVelocity DECIMAL(10,1),
	pfxFcVelocity DECIMAL(10,1),
	pfxFsVelocity DECIMAL(10,1),
	pfxFoVelocity DECIMAL(10,1),
	pfxSiVelocity DECIMAL(10,1),
	pfxSlVelocity DECIMAL(10,1),
	pfxCuVelocity DECIMAL(10,1),
	pfxKcVelocity DECIMAL(10,1),
	pfxEpVelocity DECIMAL(10,1),
	pfxChVelocity DECIMAL(10,1),
	pfxScVelocity DECIMAL(10,1),
	pfxKnVelocity DECIMAL(10,1),

	
	UNIQUE KEY UC_PitcherRegularSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;


DROP TABLE IF EXISTS PitcherRegularSeasonPartialStats;
CREATE TABLE PitcherRegularSeasonPartialStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
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
	bisSlPerc DECIMAL(10,3), -- slider %
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisChPerc DECIMAL(10,3), -- changeup %
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type section (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitchf/x Pitch Velocity section
	pfxFaVelocity DECIMAL(10,1),
	pfxFtVelocity DECIMAL(10,1),
	pfxFcVelocity DECIMAL(10,1),
	pfxFsVelocity DECIMAL(10,1),
	pfxFoVelocity DECIMAL(10,1),
	pfxSiVelocity DECIMAL(10,1),
	pfxSlVelocity DECIMAL(10,1),
	pfxCuVelocity DECIMAL(10,1),
	pfxKcVelocity DECIMAL(10,1),
	pfxEpVelocity DECIMAL(10,1),
	pfxChVelocity DECIMAL(10,1),
	pfxScVelocity DECIMAL(10,1),
	pfxKnVelocity DECIMAL(10,1),

	
	UNIQUE KEY UC_PitcherRegularSeasonPartialStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonPartialStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonPartialStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;


DROP TABLE IF EXISTS PitcherRegularSeasonProjectedStats;
CREATE TABLE PitcherRegularSeasonProjectedStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
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
	bisSlPerc DECIMAL(10,3), -- slider %
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisChPerc DECIMAL(10,3), -- changeup %
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type section (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitchf/x Pitch Velocity section
	pfxFaVelocity DECIMAL(10,1),
	pfxFtVelocity DECIMAL(10,1),
	pfxFcVelocity DECIMAL(10,1),
	pfxFsVelocity DECIMAL(10,1),
	pfxFoVelocity DECIMAL(10,1),
	pfxSiVelocity DECIMAL(10,1),
	pfxSlVelocity DECIMAL(10,1),
	pfxCuVelocity DECIMAL(10,1),
	pfxKcVelocity DECIMAL(10,1),
	pfxEpVelocity DECIMAL(10,1),
	pfxChVelocity DECIMAL(10,1),
	pfxScVelocity DECIMAL(10,1),
	pfxKnVelocity DECIMAL(10,1),

	
	UNIQUE KEY UC_PitcherRegularSeasonProjectedStats1 (playerId, season, team),
	CONSTRAINT FK_PitcherRegularSeasonProjectedStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherRegularSeasonProjectedStats1 (season, team, win, save, games, gs, ip, kper9, era)

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
	bisSlPerc DECIMAL(10,3), -- slider %
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisChPerc DECIMAL(10,3), -- changeup %
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type section (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitchf/x Pitch Velocity section
	pfxFaVelocity DECIMAL(10,1),
	pfxFtVelocity DECIMAL(10,1),
	pfxFcVelocity DECIMAL(10,1),
	pfxFsVelocity DECIMAL(10,1),
	pfxFoVelocity DECIMAL(10,1),
	pfxSiVelocity DECIMAL(10,1),
	pfxSlVelocity DECIMAL(10,1),
	pfxCuVelocity DECIMAL(10,1),
	pfxKcVelocity DECIMAL(10,1),
	pfxEpVelocity DECIMAL(10,1),
	pfxChVelocity DECIMAL(10,1),
	pfxScVelocity DECIMAL(10,1),
	pfxKnVelocity DECIMAL(10,1),
	
	
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
	bisSlPerc DECIMAL(10,3), -- slider %
	bisCtPerc DECIMAL(10,3), -- cutter %
	bisCbPerc DECIMAL(10,3), -- curve ball %
	bisChPerc DECIMAL(10,3), -- changeup %
	bisSfPerc DECIMAL(10,3), -- split finger %
	bisKnPerc DECIMAL(10,3), -- knuckle ball %
	bisXxPerc DECIMAL(10,3), -- unknown pitch type %
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	-- Pitchf/x Pitch Type section (based on Pitchf/x data)
	pfxFaPerc DECIMAL(10,3), -- fast ball all types %
	pfxFtPerc DECIMAL(10,3), -- two-seam fast ball %
	pfxFcPerc DECIMAL(10,3), -- fast ball cutter %
	pfxFsPerc DECIMAL(10,3), -- fast ball splitter %
	pfxFoPerc DECIMAL(10,3), -- fork ball %
	pfxSiPerc DECIMAL(10,3), -- sinker %
	pfxSlPerc DECIMAL(10,3), -- slider %
	pfxCuPerc DECIMAL(10,3), -- curve ball %
	pfxKcPerc DECIMAL(10,3), -- knucke curve ball %
	pfxEpPerc DECIMAL(10,3), -- eephus %
	pfxChPerc DECIMAL(10,3), -- changeup %
	pfxScPerc DECIMAL(10,3), -- screwball %
	pfxKnPerc DECIMAL(10,3), -- knuckleball %
	pfxUnPerc DECIMAL(10,3), -- unknown pitch type %
	-- Pitchf/x Pitch Velocity section
	pfxFaVelocity DECIMAL(10,1),
	pfxFtVelocity DECIMAL(10,1),
	pfxFcVelocity DECIMAL(10,1),
	pfxFsVelocity DECIMAL(10,1),
	pfxFoVelocity DECIMAL(10,1),
	pfxSiVelocity DECIMAL(10,1),
	pfxSlVelocity DECIMAL(10,1),
	pfxCuVelocity DECIMAL(10,1),
	pfxKcVelocity DECIMAL(10,1),
	pfxEpVelocity DECIMAL(10,1),
	pfxChVelocity DECIMAL(10,1),
	pfxScVelocity DECIMAL(10,1),
	pfxKnVelocity DECIMAL(10,1),

	
	CONSTRAINT FK_PitcherMinorsSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_PitcherMinorsSeasonStats1 (season, team, win, save, games, gs, ip, kper9, era)

) ENGINE=InnoDB;

SET foreign_key_checks = 1;