package com.fantasyworks.fangraphsparser.entity;

import java.math.BigDecimal;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data @EqualsAndHashCode(callSuper=true)
public class PitcherStats extends PlayerStats {

	private static final long serialVersionUID = 1L;
	
	// Dashboard section
	protected Integer win;
	protected Integer loss;
	protected Integer save;
	protected Integer games;
	protected Integer gs; // games started
	protected BigDecimal ip;
	protected BigDecimal kPer9;
	protected BigDecimal bbPer9;
	protected BigDecimal hrPer9;
	protected BigDecimal babip; // batting average balls in play
	protected BigDecimal lobPerc; // % runners LOB
	protected BigDecimal gbPerc; // Ground ball per ball in play
	protected BigDecimal hrPerFb; // HR per fly ball
	protected BigDecimal era;
	protected BigDecimal fip; // Fielder independent pitching on ERA scale
	protected BigDecimal xFip; // Expected FIP where HR/FB is set to 10.5%  
	protected BigDecimal war;
	
	// Standard section
	protected Integer cg; // complete games
	protected Integer sho; // shutouts
	protected Integer hld; // holds
	protected Integer bs; // blown saves
	protected Integer tbf; // total batters faced
	protected Integer hits;
	protected Integer runs;
	protected Integer er; // earned runs
	protected Integer hr;
	protected Integer bb;
	protected Integer ibb; // intentional BB
	protected Integer hbp;
	protected Integer wp; // wild pitches
	protected Integer bk; // balks
	protected Integer so; // strikeouts
	
	// Advanced section
	protected BigDecimal kPerc; // % of batters struck out
	protected BigDecimal bbPerc; // % of batters walked
	protected BigDecimal avg; // batting average against
	protected BigDecimal whip;
	protected Integer eraMinus; // ERA adjusted for park and league. 100 is the average. Lower is better.
	protected Integer fipMinus; // FIP adjusted for park and league. 100 is the average. Lower is better.
	
	// Batted ball section (no minor league, partial season or projected season stats)
	protected BigDecimal gbPerFb;
	protected BigDecimal ldPerc;
	protected BigDecimal fbPerc;
	protected BigDecimal iffbPerc; // In field fly ball %
	protected BigDecimal ifhPerc; // In field hit %
	protected BigDecimal buhPerc; // Bunt hit %
	protected BigDecimal pullPerc; // % of pull direction
	protected BigDecimal centPerc; // % of center direction
	protected BigDecimal oppoPerc; // % of opposite direction
	protected BigDecimal softPerc; // % of soft contact
	protected BigDecimal medPerc; // % of medium contact
	protected BigDecimal hardPerc; // % of hard contact
	protected BigDecimal siera; // Skill Interactive ERA
	protected Integer xFipMinus; // xFIP adjusted by league where average is 100. Lower is better.
	
	// More Batted Ball section (no minor league, partial season or projected season stats)
	protected Integer gb;
	protected Integer fb;
	protected Integer ld; // line drive
	protected Integer iffb; // in field fly ball
	protected Integer ifh; // in field hits
	protected Integer bu; // bunts
	protected Integer buh; // bunt singles
	protected Integer rs; // run support
	protected Integer balls;
	protected Integer strikes;
	protected Integer pitches;
	
	// Win Probability section
	protected BigDecimal wpa; // win probability added
	protected BigDecimal wpaMinus; // loss advancement
	protected BigDecimal wpaPlus; // win advancement
	protected BigDecimal re24; // run above average base on 24 base/out states
	protected BigDecimal rew; // win above average base on 24 base/out states
	protected BigDecimal pLi; // above leverage index
	protected BigDecimal inLi; // average leverage index at the start of the inning
	protected BigDecimal gmLi; // average leverage index when entering the game
	protected BigDecimal exLi; // average leverage index when existing the game
	protected Integer pulls; // number of times pulled from the game
	protected BigDecimal wpaPerLi; // situational wins
	protected BigDecimal clutch; // how much better or worse a player perform under high leverage situation. 0 is neutral.
	protected Integer sd; // shutdowns
	protected Integer md; // meltdowns
	
	// Pitch Type section (based on Baseball Info Solution's data)
	protected BigDecimal bisFbPerc; // Fast ball %
	protected BigDecimal bisSlPerc; // slider %
	protected BigDecimal bisCtPerc; // cutter %
	protected BigDecimal bisCbPerc; // curve ball %
	protected BigDecimal bisChPerc; // changeup %
	protected BigDecimal bisSfPerc; // split finger %
	protected BigDecimal bisKnPerc; // knuckle ball %
	protected BigDecimal bisXxPerc; // unknown pitch type %
	protected BigDecimal bisFbVelocity;
	protected BigDecimal bisSlVelocity;
	protected BigDecimal bisCtVelocity;
	protected BigDecimal bisCbVelocity;
	protected BigDecimal bisChVelocity;
	protected BigDecimal bisSfVelocity;
	protected BigDecimal bisKnVelocity;
	protected BigDecimal bisXxVelocity;
	
	// Pitchf/x Pitch Type section (based on Pitchf/x data)
	protected BigDecimal pfxFaPerc; // fast ball all types %
	protected BigDecimal pfxFtPerc; // two-seam fast ball %
	protected BigDecimal pfxFcPerc; // fast ball cutter %
	protected BigDecimal pfxFsPerc; // fast ball splitter %
	protected BigDecimal pfxFoPerc; // fork ball %
	protected BigDecimal pfxSiPerc; // sinker %
	protected BigDecimal pfxSlPerc; // slider %
	protected BigDecimal pfxCuPerc; // curve ball %
	protected BigDecimal pfxKcPerc; // knucke curve ball %
	protected BigDecimal pfxEpPerc; // eephus %
	protected BigDecimal pfxChPerc; // changeup %
	protected BigDecimal pfxScPerc; // screwball %
	protected BigDecimal pfxKnPerc; // knuckleball %
	protected BigDecimal pfxUnPerc; // unknown pitch type %
	
	// Pitchf/x Pitch Velocity section
	protected BigDecimal pfxFaVelocity;
	protected BigDecimal pfxFtVelocity;
	protected BigDecimal pfxFcVelocity;
	protected BigDecimal pfxFsVelocity;
	protected BigDecimal pfxFoVelocity;
	protected BigDecimal pfxSiVelocity;
	protected BigDecimal pfxSlVelocity;
	protected BigDecimal pfxCuVelocity;
	protected BigDecimal pfxKcVelocity;
	protected BigDecimal pfxEpVelocity;
	protected BigDecimal pfxChVelocity;
	protected BigDecimal pfxScVelocity;
	protected BigDecimal pfxKnVelocity;
	
	// Pitch Values
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

	// Pitchf/x Pitch Values
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
	
	// Pitchf/x Pitch Values Per 100 pitches
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
	
	// Plate Discipline
	protected BigDecimal bisOSwingPerc;
	protected BigDecimal bisZSwingPerc;
	protected BigDecimal bisSwingPerc; // total percentage of pitches swung at
	protected BigDecimal bisOContactPerc;
	protected BigDecimal bisZContactPerc;
	protected BigDecimal bisContactPerc;
	protected BigDecimal bisZonePerc; // % of pitches seen inside the strike zone
	protected BigDecimal bisFStrikePerc;
	protected BigDecimal bisSwStrPerc; // % of strikes swung at and missed
	
	// Pitchf/x Plate Discipline
	protected BigDecimal pfxOSwingPerc;
	protected BigDecimal pfxZSwingPerc;
	protected BigDecimal pfxSwingPerc; // total percentage of pitches swung at
	protected BigDecimal pfxOContactPerc;
	protected BigDecimal pfxZContactPerc;
	protected BigDecimal pfxContactPerc;
	protected BigDecimal pfxZonePerc; // % of pitches seen inside the strike zone
	protected BigDecimal pfxPace; // average time between pitches
	
	
}
