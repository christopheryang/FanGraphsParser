SET foreign_key_checks = 0;

DROP TABLE IF EXISTS BatterRegularSeasonStats;
CREATE TABLE BatterRegularSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,

	-- Dashboard section
	games INT,
	pa INT, -- plate appearance
	hr INT,
	runs INT,
	rbi INT,
	sb INT,
	bbPerc DECIMAL(10,3),
	kPerc DECIMAL(10,3),
	iso DECIMAL(10,3), -- isolated power
	babip DECIMAL(10,3),
	avg DECIMAL(10,3),
	obp DECIMAL(10,3),
	slg DECIMAL(10,3),
	wOBA DECIMAL(10,3), -- weighted on base average
	wRCPlus INT, -- runs per PA scaled where 100 is average. League and ballpark adjusted.
	bsr DECIMAL(10,3), -- base running runs above average
	off DECIMAL(10,3), -- offensive score - batting and base running combined
	def DECIMAL(10,3), -- defensive score
	war DECIMAL(10,3),
	
	-- Standard section
	ab INT,
	hits INT,
	singles INT,
	doubles INT,
	triples INT,
	bb INT,
	ibb INT,
	so INT,
	hbp INT,
	sf INT,
	sh INT,
	gdp INT,
	cs INT,
	
	-- Advanced section
	bbPerK DECIMAL(10,3),
	ops DECIMAL(10,3),
	spd DECIMAL(10,3), -- Speed component.
	ubr DECIMAL(10,3), -- ultimate base running WAR score
	wGdp DECIMAL(10,3), -- GDP WAR score
	wSb DECIMAL(10,3), -- SB WAR score
	wRc INT, -- Runs created WAR score
	wRaa DECIMAL(10,3), -- Runs above average WAR score
	
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3),
	hrPerFb DECIMAL(10,3),
	ifhPerc DECIMAL(10,3), -- infield hit %
	buhPerc DECIMAL(10,3), -- bunt hit %
	pullPerc DECIMAL(10,3),
	centPerc DECIMAL(10,3),
	oppoPerc DECIMAL(10,3),
	softPerc DECIMAL(10,3),
	medPerc DECIMAL(10,3),
	hardPerc DECIMAL(10,3),
	
	-- More batted ball section
	gb INT,
	fb INT,
	ld INT,
	iffb INT,
	ifh INT,
	bu INT, -- bunts
	buh INT, -- bunt hits
	balls INT,
	strikes INT,
	pitches INT,
	
	-- Win probability section
	wpa DECIMAL(10,3), -- win probability added. wpa = wpaPlus + wpaMinus
	wpaMinus DECIMAL(10,3), -- loss advancement
	wpaPlus DECIMAL(10,3), -- win advancement
	re24 DECIMAL(10,3),
	rew DECIMAL(10,3),
	pLi DECIMAL(10,3),
	phLi DECIMAL(10,3),
	ph INT,
	wpaPerLi DECIMAL(10,3),
	clutch DECIMAL(10,3),
	
	-- Pitch type section
	bisFbPerc DECIMAL(10,3),
	bisSlPerc DECIMAL(10,3),
	bisCtPerc DECIMAL(10,3),
	bisCbPerc DECIMAL(10,3),
	bisChPerc DECIMAL(10,3),
	bisSfPerc DECIMAL(10,3),
	bisKnPerc DECIMAL(10,3),
	bisXxPerc DECIMAL(10,3),
	
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	
	-- Pitch Values section
	bisWFb DECIMAL(10,3), -- Fast ball runs above average
	bisWSl DECIMAL(10,3),
	bisWCt DECIMAL(10,3),
	bisWCb DECIMAL(10,3),
	bisWCh DECIMAL(10,3),
	bisWSf DECIMAL(10,3),
	bisWKn DECIMAL(10,3),
	bisWFbPer100 DECIMAL(10,3), -- Fast ball runs above average per 100 pitches
	bisWSlPer100 DECIMAL(10,3),
	bisWCtPer100 DECIMAL(10,3),
	bisWCbPer100 DECIMAL(10,3),
	bisWChPer100 DECIMAL(10,3),
	bisWSfPer100 DECIMAL(10,3),
	bisWKnPer100 DECIMAL(10,3),

	
	-- Pitchf/x pitch type section
	pfxFaPerc DECIMAL(10,3),
	pfxFtPerc DECIMAL(10,3),
	pfxFcPerc DECIMAL(10,3),
	pfxFsPerc DECIMAL(10,3),
	pfxFoPerc DECIMAL(10,3),
	pfxSiPerc DECIMAL(10,3),
	pfxSlPerc DECIMAL(10,3),
	pfxCuPerc DECIMAL(10,3),
	pfxKcPerc DECIMAL(10,3),
	pfxEpPerc DECIMAL(10,3),
	pfxChPerc DECIMAL(10,3),
	pfxScPerc DECIMAL(10,3),
	pfxKnPerc DECIMAL(10,3),
	pfxUnPerc DECIMAL(10,3),
	
	-- PitchF/X pitch velocity section
	pfxFaVelocity DECIMAL(10,3), -- all fastballs
	pfxFtVelocity DECIMAL(10,3), -- two-seam
	pfxFcVelocity DECIMAL(10,3), -- cutter
	pfxFsVelocity DECIMAL(10,3), -- splitter
	pfxFoVelocity DECIMAL(10,3), -- forkball
	pfxSiVelocity DECIMAL(10,3), -- sinker
	pfxSlVelocity DECIMAL(10,3), -- slider
	pfxCuVelocity DECIMAL(10,3), -- curveball
	pfxKcVelocity DECIMAL(10,3), -- knuckle curve
	pfxEpVelocity DECIMAL(10,3), -- Eephus
	pfxChVelocity DECIMAL(10,3), -- changeup
	pfxScVelocity DECIMAL(10,3), -- screwball
	pfxKnVelocity DECIMAL(10,3), -- knuckleball
	
	-- PitchF/X pitch values section
	pfxWFa DECIMAL(10,3), -- Fast ball runs above average
	pfxWFt DECIMAL(10,3),
	pfxWFc DECIMAL(10,3),
	pfxWFs DECIMAL(10,3),
	pfxWFo DECIMAL(10,3),
	pfxWSi DECIMAL(10,3),
	pfxWSl DECIMAL(10,3),
	pfxWCu DECIMAL(10,3),
	pfxWKc DECIMAL(10,3),
	pfxWEp DECIMAL(10,3),
	pfxWCh DECIMAL(10,3),
	pfxWSc DECIMAL(10,3),
	pfxWKn DECIMAL(10,3),
	
	-- Pitchf/x Pitch Values Per 100 pitches section
	pfxWFaPer100 DECIMAL(10,3),
	pfxWFtPer100 DECIMAL(10,3),
	pfxWFcPer100 DECIMAL(10,3),
	pfxWFsPer100 DECIMAL(10,3),
	pfxWFoPer100 DECIMAL(10,3),
	pfxWSiPer100 DECIMAL(10,3),
	pfxWSlPer100 DECIMAL(10,3),
	pfxWCuPer100 DECIMAL(10,3),
	pfxWKcPer100 DECIMAL(10,3),
	pfxWEpPer100 DECIMAL(10,3),
	pfxWChPer100 DECIMAL(10,3),
	pfxWScPer100 DECIMAL(10,3),
	pfxWKnPer100 DECIMAL(10,3),

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

	-- Pitchf/x Plate Discipline section
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,3), -- average time between pitches
	
	-- Fielding section
	pos VARCHAR(16),
	gs INT, -- games started
	inn DECIMAL(10,3), -- innings fielded
	po INT, -- putouts
	assists INT,
	errors INT,
	fe INT, -- fielding errors
	te INT, -- throwing errors
	dp INT, -- double play
	dps INT, -- dp started
	dpt INT, -- dp turned
	dpf INT, -- dp finished
	scp INT, -- first baseman scoops
	sbAgainst INT,
	csAgainst INT,
	pb INT, -- pass balls
	wp INT, -- wild pitches seen
	fp DECIMAL(10,3), -- fielding %
	tz DECIMAL(10,3), -- total zone runs above average
	
	UNIQUE KEY UC_BatterRegularSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_BatterRegularSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_BatterRegularSeasonStats1 (season, team, games, avg, rbi, hr, runs, sb)

) ENGINE=InnoDB;


DROP TABLE IF EXISTS BatterRegularSeasonPartialStats;
CREATE TABLE BatterRegularSeasonPartialStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,

	-- Dashboard section
	games INT,
	pa INT, -- plate appearance
	hr INT,
	runs INT,
	rbi INT,
	sb INT,
	bbPerc DECIMAL(10,3),
	kPerc DECIMAL(10,3),
	iso DECIMAL(10,3), -- isolated power
	babip DECIMAL(10,3),
	avg DECIMAL(10,3),
	obp DECIMAL(10,3),
	slg DECIMAL(10,3),
	wOBA DECIMAL(10,3), -- weighted on base average
	wRCPlus INT, -- runs per PA scaled where 100 is average. League and ballpark adjusted.
	bsr DECIMAL(10,3), -- base running runs above average
	off DECIMAL(10,3), -- offensive score - batting and base running combined
	def DECIMAL(10,3), -- defensive score
	war DECIMAL(10,3),
	
	-- Standard section
	ab INT,
	hits INT,
	singles INT,
	doubles INT,
	triples INT,
	bb INT,
	ibb INT,
	so INT,
	hbp INT,
	sf INT,
	sh INT,
	gdp INT,
	cs INT,
	
	-- Advanced section
	bbPerK DECIMAL(10,3),
	ops DECIMAL(10,3),
	spd DECIMAL(10,3), -- Speed component.
	ubr DECIMAL(10,3), -- ultimate base running WAR score
	wGdp DECIMAL(10,3), -- GDP WAR score
	wSb DECIMAL(10,3), -- SB WAR score
	wRc INT, -- Runs created WAR score
	wRaa DECIMAL(10,3), -- Runs above average WAR score
	
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3),
	hrPerFb DECIMAL(10,3),
	ifhPerc DECIMAL(10,3), -- infield hit %
	buhPerc DECIMAL(10,3), -- bunt hit %
	pullPerc DECIMAL(10,3),
	centPerc DECIMAL(10,3),
	oppoPerc DECIMAL(10,3),
	softPerc DECIMAL(10,3),
	medPerc DECIMAL(10,3),
	hardPerc DECIMAL(10,3),
	
	-- More batted ball section
	gb INT,
	fb INT,
	ld INT,
	iffb INT,
	ifh INT,
	bu INT, -- bunts
	buh INT, -- bunt hits
	balls INT,
	strikes INT,
	pitches INT,
	
	-- Win probability section
	wpa DECIMAL(10,3), -- win probability added. wpa = wpaPlus + wpaMinus
	wpaMinus DECIMAL(10,3), -- loss advancement
	wpaPlus DECIMAL(10,3), -- win advancement
	re24 DECIMAL(10,3),
	rew DECIMAL(10,3),
	pLi DECIMAL(10,3),
	phLi DECIMAL(10,3),
	ph INT,
	wpaPerLi DECIMAL(10,3),
	clutch DECIMAL(10,3),
	
	-- Pitch type section
	bisFbPerc DECIMAL(10,3),
	bisSlPerc DECIMAL(10,3),
	bisCtPerc DECIMAL(10,3),
	bisCbPerc DECIMAL(10,3),
	bisChPerc DECIMAL(10,3),
	bisSfPerc DECIMAL(10,3),
	bisKnPerc DECIMAL(10,3),
	bisXxPerc DECIMAL(10,3),
	
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	
	-- Pitch Values section
	bisWFb DECIMAL(10,3), -- Fast ball runs above average
	bisWSl DECIMAL(10,3),
	bisWCt DECIMAL(10,3),
	bisWCb DECIMAL(10,3),
	bisWCh DECIMAL(10,3),
	bisWSf DECIMAL(10,3),
	bisWKn DECIMAL(10,3),
	bisWFbPer100 DECIMAL(10,3), -- Fast ball runs above average per 100 pitches
	bisWSlPer100 DECIMAL(10,3),
	bisWCtPer100 DECIMAL(10,3),
	bisWCbPer100 DECIMAL(10,3),
	bisWChPer100 DECIMAL(10,3),
	bisWSfPer100 DECIMAL(10,3),
	bisWKnPer100 DECIMAL(10,3),

	
	-- Pitchf/x pitch type section
	pfxFaPerc DECIMAL(10,3),
	pfxFtPerc DECIMAL(10,3),
	pfxFcPerc DECIMAL(10,3),
	pfxFsPerc DECIMAL(10,3),
	pfxFoPerc DECIMAL(10,3),
	pfxSiPerc DECIMAL(10,3),
	pfxSlPerc DECIMAL(10,3),
	pfxCuPerc DECIMAL(10,3),
	pfxKcPerc DECIMAL(10,3),
	pfxEpPerc DECIMAL(10,3),
	pfxChPerc DECIMAL(10,3),
	pfxScPerc DECIMAL(10,3),
	pfxKnPerc DECIMAL(10,3),
	pfxUnPerc DECIMAL(10,3),
	
	-- PitchF/X pitch velocity section
	pfxFaVelocity DECIMAL(10,3), -- all fastballs
	pfxFtVelocity DECIMAL(10,3), -- two-seam
	pfxFcVelocity DECIMAL(10,3), -- cutter
	pfxFsVelocity DECIMAL(10,3), -- splitter
	pfxFoVelocity DECIMAL(10,3), -- forkball
	pfxSiVelocity DECIMAL(10,3), -- sinker
	pfxSlVelocity DECIMAL(10,3), -- slider
	pfxCuVelocity DECIMAL(10,3), -- curveball
	pfxKcVelocity DECIMAL(10,3), -- knuckle curve
	pfxEpVelocity DECIMAL(10,3), -- Eephus
	pfxChVelocity DECIMAL(10,3), -- changeup
	pfxScVelocity DECIMAL(10,3), -- screwball
	pfxKnVelocity DECIMAL(10,3), -- knuckleball
	
	-- PitchF/X pitch values section
	pfxWFa DECIMAL(10,3), -- Fast ball runs above average
	pfxWFt DECIMAL(10,3),
	pfxWFc DECIMAL(10,3),
	pfxWFs DECIMAL(10,3),
	pfxWFo DECIMAL(10,3),
	pfxWSi DECIMAL(10,3),
	pfxWSl DECIMAL(10,3),
	pfxWCu DECIMAL(10,3),
	pfxWKc DECIMAL(10,3),
	pfxWEp DECIMAL(10,3),
	pfxWCh DECIMAL(10,3),
	pfxWSc DECIMAL(10,3),
	pfxWKn DECIMAL(10,3),
	
	-- Pitchf/x Pitch Values Per 100 pitches section
	pfxWFaPer100 DECIMAL(10,3),
	pfxWFtPer100 DECIMAL(10,3),
	pfxWFcPer100 DECIMAL(10,3),
	pfxWFsPer100 DECIMAL(10,3),
	pfxWFoPer100 DECIMAL(10,3),
	pfxWSiPer100 DECIMAL(10,3),
	pfxWSlPer100 DECIMAL(10,3),
	pfxWCuPer100 DECIMAL(10,3),
	pfxWKcPer100 DECIMAL(10,3),
	pfxWEpPer100 DECIMAL(10,3),
	pfxWChPer100 DECIMAL(10,3),
	pfxWScPer100 DECIMAL(10,3),
	pfxWKnPer100 DECIMAL(10,3),

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

	-- Pitchf/x Plate Discipline section
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,3), -- average time between pitches
	
	-- Fielding section
	pos VARCHAR(16),
	gs INT, -- games started
	inn DECIMAL(10,3), -- innings fielded
	po INT, -- putouts
	assists INT,
	errors INT,
	fe INT, -- fielding errors
	te INT, -- throwing errors
	dp INT, -- double play
	dps INT, -- dp started
	dpt INT, -- dp turned
	dpf INT, -- dp finished
	scp INT, -- first baseman scoops
	sbAgainst INT,
	csAgainst INT,
	pb INT, -- pass balls
	wp INT, -- wild pitches seen
	fp DECIMAL(10,3), -- fielding %
	tz DECIMAL(10,3), -- total zone runs above average
	
	UNIQUE KEY UC_BatterRegularSeasonPartialStats1 (playerId, season, team),
	CONSTRAINT FK_BatterRegularSeasonPartialStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_BatterRegularSeasonPartialStats1 (season, team, games, avg, rbi, hr, runs, sb)

) ENGINE=InnoDB;

DROP TABLE IF EXISTS BatterRegularSeasonProjectedStats;
CREATE TABLE BatterRegularSeasonProjectedStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,

	-- Dashboard section
	games INT,
	pa INT, -- plate appearance
	hr INT,
	runs INT,
	rbi INT,
	sb INT,
	bbPerc DECIMAL(10,3),
	kPerc DECIMAL(10,3),
	iso DECIMAL(10,3), -- isolated power
	babip DECIMAL(10,3),
	avg DECIMAL(10,3),
	obp DECIMAL(10,3),
	slg DECIMAL(10,3),
	wOBA DECIMAL(10,3), -- weighted on base average
	wRCPlus INT, -- runs per PA scaled where 100 is average. League and ballpark adjusted.
	bsr DECIMAL(10,3), -- base running runs above average
	off DECIMAL(10,3), -- offensive score - batting and base running combined
	def DECIMAL(10,3), -- defensive score
	war DECIMAL(10,3),
	
	-- Standard section
	ab INT,
	hits INT,
	singles INT,
	doubles INT,
	triples INT,
	bb INT,
	ibb INT,
	so INT,
	hbp INT,
	sf INT,
	sh INT,
	gdp INT,
	cs INT,
	
	-- Advanced section
	bbPerK DECIMAL(10,3),
	ops DECIMAL(10,3),
	spd DECIMAL(10,3), -- Speed component.
	ubr DECIMAL(10,3), -- ultimate base running WAR score
	wGdp DECIMAL(10,3), -- GDP WAR score
	wSb DECIMAL(10,3), -- SB WAR score
	wRc INT, -- Runs created WAR score
	wRaa DECIMAL(10,3), -- Runs above average WAR score
	
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3),
	hrPerFb DECIMAL(10,3),
	ifhPerc DECIMAL(10,3), -- infield hit %
	buhPerc DECIMAL(10,3), -- bunt hit %
	pullPerc DECIMAL(10,3),
	centPerc DECIMAL(10,3),
	oppoPerc DECIMAL(10,3),
	softPerc DECIMAL(10,3),
	medPerc DECIMAL(10,3),
	hardPerc DECIMAL(10,3),
	
	-- More batted ball section
	gb INT,
	fb INT,
	ld INT,
	iffb INT,
	ifh INT,
	bu INT, -- bunts
	buh INT, -- bunt hits
	balls INT,
	strikes INT,
	pitches INT,
	
	-- Win probability section
	wpa DECIMAL(10,3), -- win probability added. wpa = wpaPlus + wpaMinus
	wpaMinus DECIMAL(10,3), -- loss advancement
	wpaPlus DECIMAL(10,3), -- win advancement
	re24 DECIMAL(10,3),
	rew DECIMAL(10,3),
	pLi DECIMAL(10,3),
	phLi DECIMAL(10,3),
	ph INT,
	wpaPerLi DECIMAL(10,3),
	clutch DECIMAL(10,3),
	
	-- Pitch type section
	bisFbPerc DECIMAL(10,3),
	bisSlPerc DECIMAL(10,3),
	bisCtPerc DECIMAL(10,3),
	bisCbPerc DECIMAL(10,3),
	bisChPerc DECIMAL(10,3),
	bisSfPerc DECIMAL(10,3),
	bisKnPerc DECIMAL(10,3),
	bisXxPerc DECIMAL(10,3),
	
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	
	-- Pitch Values section
	bisWFb DECIMAL(10,3), -- Fast ball runs above average
	bisWSl DECIMAL(10,3),
	bisWCt DECIMAL(10,3),
	bisWCb DECIMAL(10,3),
	bisWCh DECIMAL(10,3),
	bisWSf DECIMAL(10,3),
	bisWKn DECIMAL(10,3),
	bisWFbPer100 DECIMAL(10,3), -- Fast ball runs above average per 100 pitches
	bisWSlPer100 DECIMAL(10,3),
	bisWCtPer100 DECIMAL(10,3),
	bisWCbPer100 DECIMAL(10,3),
	bisWChPer100 DECIMAL(10,3),
	bisWSfPer100 DECIMAL(10,3),
	bisWKnPer100 DECIMAL(10,3),

	
	-- Pitchf/x pitch type section
	pfxFaPerc DECIMAL(10,3),
	pfxFtPerc DECIMAL(10,3),
	pfxFcPerc DECIMAL(10,3),
	pfxFsPerc DECIMAL(10,3),
	pfxFoPerc DECIMAL(10,3),
	pfxSiPerc DECIMAL(10,3),
	pfxSlPerc DECIMAL(10,3),
	pfxCuPerc DECIMAL(10,3),
	pfxKcPerc DECIMAL(10,3),
	pfxEpPerc DECIMAL(10,3),
	pfxChPerc DECIMAL(10,3),
	pfxScPerc DECIMAL(10,3),
	pfxKnPerc DECIMAL(10,3),
	pfxUnPerc DECIMAL(10,3),
	
	-- PitchF/X pitch velocity section
	pfxFaVelocity DECIMAL(10,3), -- all fastballs
	pfxFtVelocity DECIMAL(10,3), -- two-seam
	pfxFcVelocity DECIMAL(10,3), -- cutter
	pfxFsVelocity DECIMAL(10,3), -- splitter
	pfxFoVelocity DECIMAL(10,3), -- forkball
	pfxSiVelocity DECIMAL(10,3), -- sinker
	pfxSlVelocity DECIMAL(10,3), -- slider
	pfxCuVelocity DECIMAL(10,3), -- curveball
	pfxKcVelocity DECIMAL(10,3), -- knuckle curve
	pfxEpVelocity DECIMAL(10,3), -- Eephus
	pfxChVelocity DECIMAL(10,3), -- changeup
	pfxScVelocity DECIMAL(10,3), -- screwball
	pfxKnVelocity DECIMAL(10,3), -- knuckleball
	
	-- PitchF/X pitch values section
	pfxWFa DECIMAL(10,3), -- Fast ball runs above average
	pfxWFt DECIMAL(10,3),
	pfxWFc DECIMAL(10,3),
	pfxWFs DECIMAL(10,3),
	pfxWFo DECIMAL(10,3),
	pfxWSi DECIMAL(10,3),
	pfxWSl DECIMAL(10,3),
	pfxWCu DECIMAL(10,3),
	pfxWKc DECIMAL(10,3),
	pfxWEp DECIMAL(10,3),
	pfxWCh DECIMAL(10,3),
	pfxWSc DECIMAL(10,3),
	pfxWKn DECIMAL(10,3),
	
	-- Pitchf/x Pitch Values Per 100 pitches section
	pfxWFaPer100 DECIMAL(10,3),
	pfxWFtPer100 DECIMAL(10,3),
	pfxWFcPer100 DECIMAL(10,3),
	pfxWFsPer100 DECIMAL(10,3),
	pfxWFoPer100 DECIMAL(10,3),
	pfxWSiPer100 DECIMAL(10,3),
	pfxWSlPer100 DECIMAL(10,3),
	pfxWCuPer100 DECIMAL(10,3),
	pfxWKcPer100 DECIMAL(10,3),
	pfxWEpPer100 DECIMAL(10,3),
	pfxWChPer100 DECIMAL(10,3),
	pfxWScPer100 DECIMAL(10,3),
	pfxWKnPer100 DECIMAL(10,3),

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

	-- Pitchf/x Plate Discipline section
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,3), -- average time between pitches
	
	-- Fielding section
	pos VARCHAR(16),
	gs INT, -- games started
	inn DECIMAL(10,3), -- innings fielded
	po INT, -- putouts
	assists INT,
	errors INT,
	fe INT, -- fielding errors
	te INT, -- throwing errors
	dp INT, -- double play
	dps INT, -- dp started
	dpt INT, -- dp turned
	dpf INT, -- dp finished
	scp INT, -- first baseman scoops
	sbAgainst INT,
	csAgainst INT,
	pb INT, -- pass balls
	wp INT, -- wild pitches seen
	fp DECIMAL(10,3), -- fielding %
	tz DECIMAL(10,3), -- total zone runs above average
	
	UNIQUE KEY UC_BatterRegularSeasonProjectedStats1 (playerId, season, team),
	CONSTRAINT FK_BatterRegularSeasonProjectedStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_BatterRegularSeasonProjectedStats1 (season, team, games, avg, rbi, hr, runs, sb)

) ENGINE=InnoDB;

DROP TABLE IF EXISTS BatterPostSeasonStats;
CREATE TABLE BatterPostSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,

	-- Dashboard section
	games INT,
	pa INT, -- plate appearance
	hr INT,
	runs INT,
	rbi INT,
	sb INT,
	bbPerc DECIMAL(10,3),
	kPerc DECIMAL(10,3),
	iso DECIMAL(10,3), -- isolated power
	babip DECIMAL(10,3),
	avg DECIMAL(10,3),
	obp DECIMAL(10,3),
	slg DECIMAL(10,3),
	wOBA DECIMAL(10,3), -- weighted on base average
	wRCPlus INT, -- runs per PA scaled where 100 is average. League and ballpark adjusted.
	bsr DECIMAL(10,3), -- base running runs above average
	off DECIMAL(10,3), -- offensive score - batting and base running combined
	def DECIMAL(10,3), -- defensive score
	war DECIMAL(10,3),
	
	-- Standard section
	ab INT,
	hits INT,
	singles INT,
	doubles INT,
	triples INT,
	bb INT,
	ibb INT,
	so INT,
	hbp INT,
	sf INT,
	sh INT,
	gdp INT,
	cs INT,
	
	-- Advanced section
	bbPerK DECIMAL(10,3),
	ops DECIMAL(10,3),
	spd DECIMAL(10,3), -- Speed component.
	ubr DECIMAL(10,3), -- ultimate base running WAR score
	wGdp DECIMAL(10,3), -- GDP WAR score
	wSb DECIMAL(10,3), -- SB WAR score
	wRc INT, -- Runs created WAR score
	wRaa DECIMAL(10,3), -- Runs above average WAR score
	
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3),
	hrPerFb DECIMAL(10,3),
	ifhPerc DECIMAL(10,3), -- infield hit %
	buhPerc DECIMAL(10,3), -- bunt hit %
	pullPerc DECIMAL(10,3),
	centPerc DECIMAL(10,3),
	oppoPerc DECIMAL(10,3),
	softPerc DECIMAL(10,3),
	medPerc DECIMAL(10,3),
	hardPerc DECIMAL(10,3),
	
	-- More batted ball section
	gb INT,
	fb INT,
	ld INT,
	iffb INT,
	ifh INT,
	bu INT, -- bunts
	buh INT, -- bunt hits
	balls INT,
	strikes INT,
	pitches INT,
	
	-- Win probability section
	wpa DECIMAL(10,3), -- win probability added. wpa = wpaPlus + wpaMinus
	wpaMinus DECIMAL(10,3), -- loss advancement
	wpaPlus DECIMAL(10,3), -- win advancement
	re24 DECIMAL(10,3),
	rew DECIMAL(10,3),
	pLi DECIMAL(10,3),
	phLi DECIMAL(10,3),
	ph INT,
	wpaPerLi DECIMAL(10,3),
	clutch DECIMAL(10,3),
	
	-- Pitch type section
	bisFbPerc DECIMAL(10,3),
	bisSlPerc DECIMAL(10,3),
	bisCtPerc DECIMAL(10,3),
	bisCbPerc DECIMAL(10,3),
	bisChPerc DECIMAL(10,3),
	bisSfPerc DECIMAL(10,3),
	bisKnPerc DECIMAL(10,3),
	bisXxPerc DECIMAL(10,3),
	
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	
	-- Pitch Values section
	bisWFb DECIMAL(10,3), -- Fast ball runs above average
	bisWSl DECIMAL(10,3),
	bisWCt DECIMAL(10,3),
	bisWCb DECIMAL(10,3),
	bisWCh DECIMAL(10,3),
	bisWSf DECIMAL(10,3),
	bisWKn DECIMAL(10,3),
	bisWFbPer100 DECIMAL(10,3), -- Fast ball runs above average per 100 pitches
	bisWSlPer100 DECIMAL(10,3),
	bisWCtPer100 DECIMAL(10,3),
	bisWCbPer100 DECIMAL(10,3),
	bisWChPer100 DECIMAL(10,3),
	bisWSfPer100 DECIMAL(10,3),
	bisWKnPer100 DECIMAL(10,3),

	
	-- Pitchf/x pitch type section
	pfxFaPerc DECIMAL(10,3),
	pfxFtPerc DECIMAL(10,3),
	pfxFcPerc DECIMAL(10,3),
	pfxFsPerc DECIMAL(10,3),
	pfxFoPerc DECIMAL(10,3),
	pfxSiPerc DECIMAL(10,3),
	pfxSlPerc DECIMAL(10,3),
	pfxCuPerc DECIMAL(10,3),
	pfxKcPerc DECIMAL(10,3),
	pfxEpPerc DECIMAL(10,3),
	pfxChPerc DECIMAL(10,3),
	pfxScPerc DECIMAL(10,3),
	pfxKnPerc DECIMAL(10,3),
	pfxUnPerc DECIMAL(10,3),
	
	-- PitchF/X pitch velocity section
	pfxFaVelocity DECIMAL(10,3), -- all fastballs
	pfxFtVelocity DECIMAL(10,3), -- two-seam
	pfxFcVelocity DECIMAL(10,3), -- cutter
	pfxFsVelocity DECIMAL(10,3), -- splitter
	pfxFoVelocity DECIMAL(10,3), -- forkball
	pfxSiVelocity DECIMAL(10,3), -- sinker
	pfxSlVelocity DECIMAL(10,3), -- slider
	pfxCuVelocity DECIMAL(10,3), -- curveball
	pfxKcVelocity DECIMAL(10,3), -- knuckle curve
	pfxEpVelocity DECIMAL(10,3), -- Eephus
	pfxChVelocity DECIMAL(10,3), -- changeup
	pfxScVelocity DECIMAL(10,3), -- screwball
	pfxKnVelocity DECIMAL(10,3), -- knuckleball
	
	-- PitchF/X pitch values section
	pfxWFa DECIMAL(10,3), -- Fast ball runs above average
	pfxWFt DECIMAL(10,3),
	pfxWFc DECIMAL(10,3),
	pfxWFs DECIMAL(10,3),
	pfxWFo DECIMAL(10,3),
	pfxWSi DECIMAL(10,3),
	pfxWSl DECIMAL(10,3),
	pfxWCu DECIMAL(10,3),
	pfxWKc DECIMAL(10,3),
	pfxWEp DECIMAL(10,3),
	pfxWCh DECIMAL(10,3),
	pfxWSc DECIMAL(10,3),
	pfxWKn DECIMAL(10,3),
	
	-- Pitchf/x Pitch Values Per 100 pitches section
	pfxWFaPer100 DECIMAL(10,3),
	pfxWFtPer100 DECIMAL(10,3),
	pfxWFcPer100 DECIMAL(10,3),
	pfxWFsPer100 DECIMAL(10,3),
	pfxWFoPer100 DECIMAL(10,3),
	pfxWSiPer100 DECIMAL(10,3),
	pfxWSlPer100 DECIMAL(10,3),
	pfxWCuPer100 DECIMAL(10,3),
	pfxWKcPer100 DECIMAL(10,3),
	pfxWEpPer100 DECIMAL(10,3),
	pfxWChPer100 DECIMAL(10,3),
	pfxWScPer100 DECIMAL(10,3),
	pfxWKnPer100 DECIMAL(10,3),

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

	-- Pitchf/x Plate Discipline section
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,3), -- average time between pitches
	
	-- Fielding section
	pos VARCHAR(16),
	gs INT, -- games started
	inn DECIMAL(10,3), -- innings fielded
	po INT, -- putouts
	assists INT,
	errors INT,
	fe INT, -- fielding errors
	te INT, -- throwing errors
	dp INT, -- double play
	dps INT, -- dp started
	dpt INT, -- dp turned
	dpf INT, -- dp finished
	scp INT, -- first baseman scoops
	sbAgainst INT,
	csAgainst INT,
	pb INT, -- pass balls
	wp INT, -- wild pitches seen
	fp DECIMAL(10,3), -- fielding %
	tz DECIMAL(10,3), -- total zone runs above average
	
	UNIQUE KEY UC_BatterPostSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_BatterPostSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_BatterPostSeasonStats1 (season, team, games, avg, rbi, hr, runs, sb)

) ENGINE=InnoDB;

DROP TABLE IF EXISTS BatterMinorsSeasonStats;
CREATE TABLE BatterMinorsSeasonStats (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	
	playerId INT(11) NOT NULL,
	name VARCHAR(128),
	age DECIMAL(10,1),
	season INT NOT NULL,
	team VARCHAR(64) NOT NULL,
	statsType VARCHAR(32) NOT NULL,

	-- Dashboard section
	games INT,
	pa INT, -- plate appearance
	hr INT,
	runs INT,
	rbi INT,
	sb INT,
	bbPerc DECIMAL(10,3),
	kPerc DECIMAL(10,3),
	iso DECIMAL(10,3), -- isolated power
	babip DECIMAL(10,3),
	avg DECIMAL(10,3),
	obp DECIMAL(10,3),
	slg DECIMAL(10,3),
	wOBA DECIMAL(10,3), -- weighted on base average
	wRCPlus INT, -- runs per PA scaled where 100 is average. League and ballpark adjusted.
	bsr DECIMAL(10,3), -- base running runs above average
	off DECIMAL(10,3), -- offensive score - batting and base running combined
	def DECIMAL(10,3), -- defensive score
	war DECIMAL(10,3),
	
	-- Standard section
	ab INT,
	hits INT,
	singles INT,
	doubles INT,
	triples INT,
	bb INT,
	ibb INT,
	so INT,
	hbp INT,
	sf INT,
	sh INT,
	gdp INT,
	cs INT,
	
	-- Advanced section
	bbPerK DECIMAL(10,3),
	ops DECIMAL(10,3),
	spd DECIMAL(10,3), -- Speed component.
	ubr DECIMAL(10,3), -- ultimate base running WAR score
	wGdp DECIMAL(10,3), -- GDP WAR score
	wSb DECIMAL(10,3), -- SB WAR score
	wRc INT, -- Runs created WAR score
	wRaa DECIMAL(10,3), -- Runs above average WAR score
	
	-- Batted ball section
	gbPerFb DECIMAL(10,3),
	ldPerc DECIMAL(10,3),
	gbPerc DECIMAL(10,3),
	fbPerc DECIMAL(10,3),
	iffbPerc DECIMAL(10,3),
	hrPerFb DECIMAL(10,3),
	ifhPerc DECIMAL(10,3), -- infield hit %
	buhPerc DECIMAL(10,3), -- bunt hit %
	pullPerc DECIMAL(10,3),
	centPerc DECIMAL(10,3),
	oppoPerc DECIMAL(10,3),
	softPerc DECIMAL(10,3),
	medPerc DECIMAL(10,3),
	hardPerc DECIMAL(10,3),
	
	-- More batted ball section
	gb INT,
	fb INT,
	ld INT,
	iffb INT,
	ifh INT,
	bu INT, -- bunts
	buh INT, -- bunt hits
	balls INT,
	strikes INT,
	pitches INT,
	
	-- Win probability section
	wpa DECIMAL(10,3), -- win probability added. wpa = wpaPlus + wpaMinus
	wpaMinus DECIMAL(10,3), -- loss advancement
	wpaPlus DECIMAL(10,3), -- win advancement
	re24 DECIMAL(10,3),
	rew DECIMAL(10,3),
	pLi DECIMAL(10,3),
	phLi DECIMAL(10,3),
	ph INT,
	wpaPerLi DECIMAL(10,3),
	clutch DECIMAL(10,3),
	
	-- Pitch type section
	bisFbPerc DECIMAL(10,3),
	bisSlPerc DECIMAL(10,3),
	bisCtPerc DECIMAL(10,3),
	bisCbPerc DECIMAL(10,3),
	bisChPerc DECIMAL(10,3),
	bisSfPerc DECIMAL(10,3),
	bisKnPerc DECIMAL(10,3),
	bisXxPerc DECIMAL(10,3),
	
	bisFbVelocity DECIMAL(10,1),
	bisSlVelocity DECIMAL(10,1),
	bisCtVelocity DECIMAL(10,1),
	bisCbVelocity DECIMAL(10,1),
	bisChVelocity DECIMAL(10,1),
	bisSfVelocity DECIMAL(10,1),
	bisKnVelocity DECIMAL(10,1),
	bisXxVelocity DECIMAL(10,1),
	
	-- Pitch Values section
	bisWFb DECIMAL(10,3), -- Fast ball runs above average
	bisWSl DECIMAL(10,3),
	bisWCt DECIMAL(10,3),
	bisWCb DECIMAL(10,3),
	bisWCh DECIMAL(10,3),
	bisWSf DECIMAL(10,3),
	bisWKn DECIMAL(10,3),
	bisWFbPer100 DECIMAL(10,3), -- Fast ball runs above average per 100 pitches
	bisWSlPer100 DECIMAL(10,3),
	bisWCtPer100 DECIMAL(10,3),
	bisWCbPer100 DECIMAL(10,3),
	bisWChPer100 DECIMAL(10,3),
	bisWSfPer100 DECIMAL(10,3),
	bisWKnPer100 DECIMAL(10,3),

	
	-- Pitchf/x pitch type section
	pfxFaPerc DECIMAL(10,3),
	pfxFtPerc DECIMAL(10,3),
	pfxFcPerc DECIMAL(10,3),
	pfxFsPerc DECIMAL(10,3),
	pfxFoPerc DECIMAL(10,3),
	pfxSiPerc DECIMAL(10,3),
	pfxSlPerc DECIMAL(10,3),
	pfxCuPerc DECIMAL(10,3),
	pfxKcPerc DECIMAL(10,3),
	pfxEpPerc DECIMAL(10,3),
	pfxChPerc DECIMAL(10,3),
	pfxScPerc DECIMAL(10,3),
	pfxKnPerc DECIMAL(10,3),
	pfxUnPerc DECIMAL(10,3),
	
	-- PitchF/X pitch velocity section
	pfxFaVelocity DECIMAL(10,3), -- all fastballs
	pfxFtVelocity DECIMAL(10,3), -- two-seam
	pfxFcVelocity DECIMAL(10,3), -- cutter
	pfxFsVelocity DECIMAL(10,3), -- splitter
	pfxFoVelocity DECIMAL(10,3), -- forkball
	pfxSiVelocity DECIMAL(10,3), -- sinker
	pfxSlVelocity DECIMAL(10,3), -- slider
	pfxCuVelocity DECIMAL(10,3), -- curveball
	pfxKcVelocity DECIMAL(10,3), -- knuckle curve
	pfxEpVelocity DECIMAL(10,3), -- Eephus
	pfxChVelocity DECIMAL(10,3), -- changeup
	pfxScVelocity DECIMAL(10,3), -- screwball
	pfxKnVelocity DECIMAL(10,3), -- knuckleball
	
	-- PitchF/X pitch values section
	pfxWFa DECIMAL(10,3), -- Fast ball runs above average
	pfxWFt DECIMAL(10,3),
	pfxWFc DECIMAL(10,3),
	pfxWFs DECIMAL(10,3),
	pfxWFo DECIMAL(10,3),
	pfxWSi DECIMAL(10,3),
	pfxWSl DECIMAL(10,3),
	pfxWCu DECIMAL(10,3),
	pfxWKc DECIMAL(10,3),
	pfxWEp DECIMAL(10,3),
	pfxWCh DECIMAL(10,3),
	pfxWSc DECIMAL(10,3),
	pfxWKn DECIMAL(10,3),
	
	-- Pitchf/x Pitch Values Per 100 pitches section
	pfxWFaPer100 DECIMAL(10,3),
	pfxWFtPer100 DECIMAL(10,3),
	pfxWFcPer100 DECIMAL(10,3),
	pfxWFsPer100 DECIMAL(10,3),
	pfxWFoPer100 DECIMAL(10,3),
	pfxWSiPer100 DECIMAL(10,3),
	pfxWSlPer100 DECIMAL(10,3),
	pfxWCuPer100 DECIMAL(10,3),
	pfxWKcPer100 DECIMAL(10,3),
	pfxWEpPer100 DECIMAL(10,3),
	pfxWChPer100 DECIMAL(10,3),
	pfxWScPer100 DECIMAL(10,3),
	pfxWKnPer100 DECIMAL(10,3),

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

	-- Pitchf/x Plate Discipline section
	pfxOSwingPerc DECIMAL(10,3),
	pfxZSwingPerc DECIMAL(10,3),
	pfxSwingPerc DECIMAL(10,3), -- total percentage of pitches swung at
	pfxOContactPerc DECIMAL(10,3),
	pfxZContactPerc DECIMAL(10,3),
	pfxContactPerc DECIMAL(10,3),
	pfxZonePerc DECIMAL(10,3), -- % of pitches seen inside the strike zone
	pfxPace DECIMAL(10,3), -- average time between pitches
	
	-- Fielding section
	pos VARCHAR(16),
	gs INT, -- games started
	inn DECIMAL(10,3), -- innings fielded
	po INT, -- putouts
	assists INT,
	errors INT,
	fe INT, -- fielding errors
	te INT, -- throwing errors
	dp INT, -- double play
	dps INT, -- dp started
	dpt INT, -- dp turned
	dpf INT, -- dp finished
	scp INT, -- first baseman scoops
	sbAgainst INT,
	csAgainst INT,
	pb INT, -- pass balls
	wp INT, -- wild pitches seen
	fp DECIMAL(10,3), -- fielding %
	tz DECIMAL(10,3), -- total zone runs above average
	
--	UNIQUE KEY UC_BatterMinorsSeasonStats1 (playerId, season, team),
	CONSTRAINT FK_BatterMinorsSeasonStats_Player FOREIGN KEY (playerId) REFERENCES Player (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	INDEX IDX_BatterMinorsSeasonStats1 (season, team, games, avg, rbi, hr, runs, sb)

) ENGINE=InnoDB;

SET foreign_key_checks = 0;
