package com.fantasyworks.fangraphsparser.entity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain=true)
public class BatterStats extends PlayerStats {

	private static final long serialVersionUID = 1L;
	
	// Dashboard section
	protected Integer games;
	protected Integer pa; // plate appearance
	protected Integer hr;
	protected Integer runs;
	protected Integer rbi;
	protected Integer sb;
	protected BigDecimal bbPerc;
	protected BigDecimal kPerc;
	protected BigDecimal iso; // isolated power
	protected BigDecimal babip;
	protected BigDecimal avg;
	protected BigDecimal obp;
	protected BigDecimal slg;
	protected BigDecimal wOBA; // weighted on base average
	protected Integer wRCPlus; // runs per PA scaled where 100 is average. League and ballpark adjusted.
	protected BigDecimal bsr; // base running runs above average
	protected BigDecimal off; // offensive score - batting and base running combined
	protected BigDecimal def; // defensive score
	protected BigDecimal war;
	
	// Standard section
	protected Integer ab;
	protected Integer hits;
	protected Integer singles;
	protected Integer doubles;
	protected Integer triples;
	protected Integer bb;
	protected Integer ibb;
	protected Integer so;
	protected Integer hbp;
	protected Integer sf;
	protected Integer sh;
	protected Integer gdp;
	protected Integer cs;
	
	// Advanced section
	protected BigDecimal bbPerK;
	protected BigDecimal ops;
	protected BigDecimal spd; // Speed component.
	protected BigDecimal ubr; // ultimate base running WAR score
	protected BigDecimal wGdp; // GDP WAR score
	protected BigDecimal wSb; // SB WAR score
	protected Integer wRc; // Runs created WAR score
	protected BigDecimal wRaa; // Runs above average WAR score
	
	// Batted ball section
	protected BigDecimal gbPerFb;
	protected BigDecimal ldPerc;
	protected BigDecimal gbPerc;
	protected BigDecimal fbPerc;
	protected BigDecimal iffbPerc;
	protected BigDecimal hrPerFb;
	protected BigDecimal ifhPerc; // infield hit %
	protected BigDecimal buhPerc; // bunt hit %
	protected BigDecimal pullPerc;
	protected BigDecimal centPerc;
	protected BigDecimal oppoPerc;
	protected BigDecimal softPerc;
	protected BigDecimal medPerc;
	protected BigDecimal hardPerc;
	
	// More batted ball section
	protected Integer gb;
	protected Integer fb;
	protected Integer ld;
	protected Integer iffb;
	protected Integer ifh;
	protected Integer bu; // bunts
	protected Integer buh; // bunt hits
	protected Integer balls;
	protected Integer strikes;
	protected Integer pitches;
	
	// Win probability section
	protected BigDecimal wpa; // win probability added. wpa = wpaPlus + wpaMinus
	protected BigDecimal wpaMinus; // loss advancement
	protected BigDecimal wpaPlus; // win advancement
	protected BigDecimal re24;
	protected BigDecimal rew;
	protected BigDecimal pLi;
	protected BigDecimal phLi;
	protected BigDecimal ph;
	protected BigDecimal wpaPerLi;
	protected BigDecimal clutch;
	
	// Pitch type section
	protected BigDecimal bisFbPerc;
	protected BigDecimal bisSlPerc;
	protected BigDecimal bisCtPerc;
	protected BigDecimal bisCbPerc;
	protected BigDecimal bisChPerc;
	protected BigDecimal bisSfPerc;
	protected BigDecimal bisKnPerc;
	protected BigDecimal bisXxPerc;
	
	// Pitch Values section
	protected BigDecimal bisWFb; // Fast ball runs above average
	protected BigDecimal bisWSl;
	protected BigDecimal bisWCt;
	protected BigDecimal bisWCb;
	protected BigDecimal bisWCh;
	protected BigDecimal bisWSf;
	protected BigDecimal bisWKn;
	protected BigDecimal bisWFbPer100; // Fast ball runs above average per 100 pitches
	protected BigDecimal bisWSlPer100;
	protected BigDecimal bisWCtPer100;
	protected BigDecimal bisWCbPer100;
	protected BigDecimal bisWChPer100;
	protected BigDecimal bisWSfPer100;
	protected BigDecimal bisWKnPer100;

	
	// Pitchf/x pitch type section
	protected BigDecimal pfxFaPerc;
	protected BigDecimal pfxFtPerc;
	protected BigDecimal pfxFcPerc;
	protected BigDecimal pfxFsPerc;
	protected BigDecimal pfxFoPerc;
	protected BigDecimal pfxSiPerc;
	protected BigDecimal pfxSlPerc;
	protected BigDecimal pfxCuPerc;
	protected BigDecimal pfxKcPerc;
	protected BigDecimal pfxEpPerc;
	protected BigDecimal pfxChPerc;
	protected BigDecimal pfxScPerc;
	protected BigDecimal pfxKnPerc;
	protected BigDecimal pfxUnPerc;
	
	// PitchF/X pitch velocity section
	protected BigDecimal pfxFaVelocity; // all fastballs
	protected BigDecimal pfxFtVelocity; // two-seam
	protected BigDecimal pfxFcVelocity; // cutter
	protected BigDecimal pfxFsVelocity; // splitter
	protected BigDecimal pfxFoVelocity; // forkball
	protected BigDecimal pfxSiVelocity; // sinker
	protected BigDecimal pfxSlVelocity; // slider
	protected BigDecimal pfxCuVelocity; // curveball
	protected BigDecimal pfxKcVelocity; // knuckle curve
	protected BigDecimal pfxEpVelocity; // Eephus
	protected BigDecimal pfxChVelocity; // changeup
	protected BigDecimal pfxScVelocity; // screwball
	protected BigDecimal pfxKnVelocity; // knuckleball
	
	// PitchF/X pitch values section
	protected BigDecimal pfxWFa; // Fast ball runs above average
	protected BigDecimal pfxWFt;
	protected BigDecimal pfxWFc;
	protected BigDecimal pfxWFs;
	protected BigDecimal pfxWFo;
	protected BigDecimal pfxWSi;
	protected BigDecimal pfxWSl;
	protected BigDecimal pfxWCu;
	protected BigDecimal pfxWKc;
	protected BigDecimal pfxWEp;
	protected BigDecimal pfxWCh;
	protected BigDecimal pfxWSc;
	protected BigDecimal pfxWKn;
	
	// Pitchf/x Pitch Values Per 100 pitches section
	protected BigDecimal pfxWFaPer100;
	protected BigDecimal pfxWFtPer100;
	protected BigDecimal pfxWFcPer100;
	protected BigDecimal pfxWFsPer100;
	protected BigDecimal pfxWFoPer100;
	protected BigDecimal pfxWSiPer100;
	protected BigDecimal pfxWSlPer100;
	protected BigDecimal pfxWCuPer100;
	protected BigDecimal pfxWKcPer100;
	protected BigDecimal pfxWEpPer100;
	protected BigDecimal pfxWChPer100;
	protected BigDecimal pfxWScPer100;
	protected BigDecimal pfxWKnPer100;

	// Pitchf/x Plate Discipline section
	protected BigDecimal pfxOSwingPerc;
	protected BigDecimal pfxZSwingPerc;
	protected BigDecimal pfxSwingPerc; // total percentage of pitches swung at
	protected BigDecimal pfxOContactPerc;
	protected BigDecimal pfxZContactPerc;
	protected BigDecimal pfxContactPerc;
	protected BigDecimal pfxZonePerc; // % of pitches seen inside the strike zone
	protected BigDecimal pfxPace; // average time between pitches
	
	// Fielding section
	protected String pos;
	protected Integer gs; // games started
	protected BigDecimal inn; // innings fielded
	protected Integer po; //putouts
	protected Integer assists;
	protected Integer errors;
	protected Integer fe; // fielding errors
	protected Integer te; // throwing errors
	protected Integer dp; // double play
	protected Integer dps; // dp started
	protected Integer dpt; // dp turned
	protected Integer dpf; // dp finished
	protected Integer scp; // first baseman scoops
	protected Integer sbAgainst;
	protected Integer csAgainst;
	protected Integer pb; // pass balls
	protected Integer wp; // wild pitches seen
	protected BigDecimal fp; // fielding %
	protected BigDecimal tz; // total zone runs above average
	
	// Advanced fielding section
	
	
	
}
