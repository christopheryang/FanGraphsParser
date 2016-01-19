package com.fantasyworks.fangraphsparser.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;
import com.google.common.base.MoreObjects.ToStringHelper;

@MappedSuperclass
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

	@Override
	public boolean equals(Object obj){
		if(obj==null || obj instanceof PitcherStats == false){
			return false;
		}
		return super.equals(obj);
	}
	public Integer getWin() {
		return win;
	}
	public void setWin(Integer win) {
		this.win = win;
	}
	public Integer getLoss() {
		return loss;
	}
	public void setLoss(Integer loss) {
		this.loss = loss;
	}
	public Integer getSave() {
		return save;
	}
	public void setSave(Integer save) {
		this.save = save;
	}
	public Integer getGames() {
		return games;
	}
	public void setGames(Integer games) {
		this.games = games;
	}
	public Integer getGs() {
		return gs;
	}
	public void setGs(Integer gs) {
		this.gs = gs;
	}
	public BigDecimal getIp() {
		return ip;
	}
	public void setIp(BigDecimal ip) {
		this.ip = ip;
	}
	public BigDecimal getkPer9() {
		return kPer9;
	}
	public void setkPer9(BigDecimal kPer9) {
		this.kPer9 = kPer9;
	}
	public BigDecimal getBbPer9() {
		return bbPer9;
	}
	public void setBbPer9(BigDecimal bbPer9) {
		this.bbPer9 = bbPer9;
	}
	public BigDecimal getHrPer9() {
		return hrPer9;
	}
	public void setHrPer9(BigDecimal hrPer9) {
		this.hrPer9 = hrPer9;
	}
	public BigDecimal getBabip() {
		return babip;
	}
	public void setBabip(BigDecimal babip) {
		this.babip = babip;
	}
	public BigDecimal getLobPerc() {
		return lobPerc;
	}
	public void setLobPerc(BigDecimal lobPerc) {
		this.lobPerc = lobPerc;
	}
	public BigDecimal getGbPerc() {
		return gbPerc;
	}
	public void setGbPerc(BigDecimal gbPerc) {
		this.gbPerc = gbPerc;
	}
	public BigDecimal getHrPerFb() {
		return hrPerFb;
	}
	public void setHrPerFb(BigDecimal hrPerFb) {
		this.hrPerFb = hrPerFb;
	}
	public BigDecimal getEra() {
		return era;
	}
	public void setEra(BigDecimal era) {
		this.era = era;
	}
	public BigDecimal getFip() {
		return fip;
	}
	public void setFip(BigDecimal fip) {
		this.fip = fip;
	}
	public BigDecimal getxFip() {
		return xFip;
	}
	public void setxFip(BigDecimal xFip) {
		this.xFip = xFip;
	}
	public BigDecimal getWar() {
		return war;
	}
	public void setWar(BigDecimal war) {
		this.war = war;
	}
	public Integer getCg() {
		return cg;
	}
	public void setCg(Integer cg) {
		this.cg = cg;
	}
	public Integer getSho() {
		return sho;
	}
	public void setSho(Integer sho) {
		this.sho = sho;
	}
	public Integer getHld() {
		return hld;
	}
	public void setHld(Integer hld) {
		this.hld = hld;
	}
	public Integer getBs() {
		return bs;
	}
	public void setBs(Integer bs) {
		this.bs = bs;
	}
	public Integer getTbf() {
		return tbf;
	}
	public void setTbf(Integer tbf) {
		this.tbf = tbf;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getRuns() {
		return runs;
	}
	public void setRuns(Integer runs) {
		this.runs = runs;
	}
	public Integer getEr() {
		return er;
	}
	public void setEr(Integer er) {
		this.er = er;
	}
	public Integer getHr() {
		return hr;
	}
	public void setHr(Integer hr) {
		this.hr = hr;
	}
	public Integer getBb() {
		return bb;
	}
	public void setBb(Integer bb) {
		this.bb = bb;
	}
	public Integer getIbb() {
		return ibb;
	}
	public void setIbb(Integer ibb) {
		this.ibb = ibb;
	}
	public Integer getHbp() {
		return hbp;
	}
	public void setHbp(Integer hbp) {
		this.hbp = hbp;
	}
	public Integer getWp() {
		return wp;
	}
	public void setWp(Integer wp) {
		this.wp = wp;
	}
	public Integer getBk() {
		return bk;
	}
	public void setBk(Integer bk) {
		this.bk = bk;
	}
	public Integer getSo() {
		return so;
	}
	public void setSo(Integer so) {
		this.so = so;
	}
	public BigDecimal getkPerc() {
		return kPerc;
	}
	public void setkPerc(BigDecimal kPerc) {
		this.kPerc = kPerc;
	}
	public BigDecimal getBbPerc() {
		return bbPerc;
	}
	public void setBbPerc(BigDecimal bbPerc) {
		this.bbPerc = bbPerc;
	}
	public BigDecimal getAvg() {
		return avg;
	}
	public void setAvg(BigDecimal avg) {
		this.avg = avg;
	}
	public BigDecimal getWhip() {
		return whip;
	}
	public void setWhip(BigDecimal whip) {
		this.whip = whip;
	}
	public Integer getEraMinus() {
		return eraMinus;
	}
	public void setEraMinus(Integer eraMinus) {
		this.eraMinus = eraMinus;
	}
	public Integer getFipMinus() {
		return fipMinus;
	}
	public void setFipMinus(Integer fipMinus) {
		this.fipMinus = fipMinus;
	}
	public BigDecimal getGbPerFb() {
		return gbPerFb;
	}
	public void setGbPerFb(BigDecimal gbPerFb) {
		this.gbPerFb = gbPerFb;
	}
	public BigDecimal getLdPerc() {
		return ldPerc;
	}
	public void setLdPerc(BigDecimal ldPerc) {
		this.ldPerc = ldPerc;
	}
	public BigDecimal getFbPerc() {
		return fbPerc;
	}
	public void setFbPerc(BigDecimal fbPerc) {
		this.fbPerc = fbPerc;
	}
	public BigDecimal getIffbPerc() {
		return iffbPerc;
	}
	public void setIffbPerc(BigDecimal iffbPerc) {
		this.iffbPerc = iffbPerc;
	}
	public BigDecimal getIfhPerc() {
		return ifhPerc;
	}
	public void setIfhPerc(BigDecimal ifhPerc) {
		this.ifhPerc = ifhPerc;
	}
	public BigDecimal getBuhPerc() {
		return buhPerc;
	}
	public void setBuhPerc(BigDecimal buhPerc) {
		this.buhPerc = buhPerc;
	}
	public BigDecimal getPullPerc() {
		return pullPerc;
	}
	public void setPullPerc(BigDecimal pullPerc) {
		this.pullPerc = pullPerc;
	}
	public BigDecimal getCentPerc() {
		return centPerc;
	}
	public void setCentPerc(BigDecimal centPerc) {
		this.centPerc = centPerc;
	}
	public BigDecimal getOppoPerc() {
		return oppoPerc;
	}
	public void setOppoPerc(BigDecimal oppoPerc) {
		this.oppoPerc = oppoPerc;
	}
	public BigDecimal getSoftPerc() {
		return softPerc;
	}
	public void setSoftPerc(BigDecimal softPerc) {
		this.softPerc = softPerc;
	}
	public BigDecimal getMedPerc() {
		return medPerc;
	}
	public void setMedPerc(BigDecimal medPerc) {
		this.medPerc = medPerc;
	}
	public BigDecimal getHardPerc() {
		return hardPerc;
	}
	public void setHardPerc(BigDecimal hardPerc) {
		this.hardPerc = hardPerc;
	}
	public BigDecimal getSiera() {
		return siera;
	}
	public void setSiera(BigDecimal siera) {
		this.siera = siera;
	}
	public Integer getxFipMinus() {
		return xFipMinus;
	}
	public void setxFipMinus(Integer xFipMinus) {
		this.xFipMinus = xFipMinus;
	}
	public Integer getGb() {
		return gb;
	}
	public void setGb(Integer gb) {
		this.gb = gb;
	}
	public Integer getFb() {
		return fb;
	}
	public void setFb(Integer fb) {
		this.fb = fb;
	}
	public Integer getLd() {
		return ld;
	}
	public void setLd(Integer ld) {
		this.ld = ld;
	}
	public Integer getIffb() {
		return iffb;
	}
	public void setIffb(Integer iffb) {
		this.iffb = iffb;
	}
	public Integer getIfh() {
		return ifh;
	}
	public void setIfh(Integer ifh) {
		this.ifh = ifh;
	}
	public Integer getBu() {
		return bu;
	}
	public void setBu(Integer bu) {
		this.bu = bu;
	}
	public Integer getBuh() {
		return buh;
	}
	public void setBuh(Integer buh) {
		this.buh = buh;
	}
	public Integer getRs() {
		return rs;
	}
	public void setRs(Integer rs) {
		this.rs = rs;
	}
	public Integer getBalls() {
		return balls;
	}
	public void setBalls(Integer balls) {
		this.balls = balls;
	}
	public Integer getStrikes() {
		return strikes;
	}
	public void setStrikes(Integer strikes) {
		this.strikes = strikes;
	}
	public Integer getPitches() {
		return pitches;
	}
	public void setPitches(Integer pitches) {
		this.pitches = pitches;
	}
	public BigDecimal getWpa() {
		return wpa;
	}
	public void setWpa(BigDecimal wpa) {
		this.wpa = wpa;
	}
	public BigDecimal getWpaMinus() {
		return wpaMinus;
	}
	public void setWpaMinus(BigDecimal wpaMinus) {
		this.wpaMinus = wpaMinus;
	}
	public BigDecimal getWpaPlus() {
		return wpaPlus;
	}
	public void setWpaPlus(BigDecimal wpaPlus) {
		this.wpaPlus = wpaPlus;
	}
	public BigDecimal getRe24() {
		return re24;
	}
	public void setRe24(BigDecimal re24) {
		this.re24 = re24;
	}
	public BigDecimal getRew() {
		return rew;
	}
	public void setRew(BigDecimal rew) {
		this.rew = rew;
	}
	public BigDecimal getPLi() {
		return pLi;
	}
	public void setPLi(BigDecimal pLi) {
		this.pLi = pLi;
	}
	public BigDecimal getInLi() {
		return inLi;
	}
	public void setInLi(BigDecimal inLi) {
		this.inLi = inLi;
	}
	public BigDecimal getGmLi() {
		return gmLi;
	}
	public void setGmLi(BigDecimal gmLi) {
		this.gmLi = gmLi;
	}
	public BigDecimal getExLi() {
		return exLi;
	}
	public void setExLi(BigDecimal exLi) {
		this.exLi = exLi;
	}
	public Integer getPulls() {
		return pulls;
	}
	public void setPulls(Integer pulls) {
		this.pulls = pulls;
	}
	public BigDecimal getWpaPerLi() {
		return wpaPerLi;
	}
	public void setWpaPerLi(BigDecimal wpaPerLi) {
		this.wpaPerLi = wpaPerLi;
	}
	public BigDecimal getClutch() {
		return clutch;
	}
	public void setClutch(BigDecimal clutch) {
		this.clutch = clutch;
	}
	public Integer getSd() {
		return sd;
	}
	public void setSd(Integer sd) {
		this.sd = sd;
	}
	public Integer getMd() {
		return md;
	}
	public void setMd(Integer md) {
		this.md = md;
	}
	public BigDecimal getBisFbPerc() {
		return bisFbPerc;
	}
	public void setBisFbPerc(BigDecimal bisFbPerc) {
		this.bisFbPerc = bisFbPerc;
	}
	public BigDecimal getBisSlPerc() {
		return bisSlPerc;
	}
	public void setBisSlPerc(BigDecimal bisSlPerc) {
		this.bisSlPerc = bisSlPerc;
	}
	public BigDecimal getBisCtPerc() {
		return bisCtPerc;
	}
	public void setBisCtPerc(BigDecimal bisCtPerc) {
		this.bisCtPerc = bisCtPerc;
	}
	public BigDecimal getBisCbPerc() {
		return bisCbPerc;
	}
	public void setBisCbPerc(BigDecimal bisCbPerc) {
		this.bisCbPerc = bisCbPerc;
	}
	public BigDecimal getBisChPerc() {
		return bisChPerc;
	}
	public void setBisChPerc(BigDecimal bisChPerc) {
		this.bisChPerc = bisChPerc;
	}
	public BigDecimal getBisSfPerc() {
		return bisSfPerc;
	}
	public void setBisSfPerc(BigDecimal bisSfPerc) {
		this.bisSfPerc = bisSfPerc;
	}
	public BigDecimal getBisKnPerc() {
		return bisKnPerc;
	}
	public void setBisKnPerc(BigDecimal bisKnPerc) {
		this.bisKnPerc = bisKnPerc;
	}
	public BigDecimal getBisXxPerc() {
		return bisXxPerc;
	}
	public void setBisXxPerc(BigDecimal bisXxPerc) {
		this.bisXxPerc = bisXxPerc;
	}
	public BigDecimal getPfxFaPerc() {
		return pfxFaPerc;
	}
	public void setPfxFaPerc(BigDecimal pfxFaPerc) {
		this.pfxFaPerc = pfxFaPerc;
	}
	public BigDecimal getPfxFtPerc() {
		return pfxFtPerc;
	}
	public void setPfxFtPerc(BigDecimal pfxFtPerc) {
		this.pfxFtPerc = pfxFtPerc;
	}
	public BigDecimal getPfxFcPerc() {
		return pfxFcPerc;
	}
	public void setPfxFcPerc(BigDecimal pfxFcPerc) {
		this.pfxFcPerc = pfxFcPerc;
	}
	public BigDecimal getPfxFsPerc() {
		return pfxFsPerc;
	}
	public void setPfxFsPerc(BigDecimal pfxFsPerc) {
		this.pfxFsPerc = pfxFsPerc;
	}
	public BigDecimal getPfxFoPerc() {
		return pfxFoPerc;
	}
	public void setPfxFoPerc(BigDecimal pfxFoPerc) {
		this.pfxFoPerc = pfxFoPerc;
	}
	public BigDecimal getPfxSiPerc() {
		return pfxSiPerc;
	}
	public void setPfxSiPerc(BigDecimal pfxSiPerc) {
		this.pfxSiPerc = pfxSiPerc;
	}
	public BigDecimal getPfxSlPerc() {
		return pfxSlPerc;
	}
	public void setPfxSlPerc(BigDecimal pfxSlPerc) {
		this.pfxSlPerc = pfxSlPerc;
	}
	public BigDecimal getPfxCuPerc() {
		return pfxCuPerc;
	}
	public void setPfxCuPerc(BigDecimal pfxCuPerc) {
		this.pfxCuPerc = pfxCuPerc;
	}
	public BigDecimal getPfxKcPerc() {
		return pfxKcPerc;
	}
	public void setPfxKcPerc(BigDecimal pfxKcPerc) {
		this.pfxKcPerc = pfxKcPerc;
	}
	public BigDecimal getPfxEpPerc() {
		return pfxEpPerc;
	}
	public void setPfxEpPerc(BigDecimal pfxEpPerc) {
		this.pfxEpPerc = pfxEpPerc;
	}
	public BigDecimal getPfxChPerc() {
		return pfxChPerc;
	}
	public void setPfxChPerc(BigDecimal pfxChPerc) {
		this.pfxChPerc = pfxChPerc;
	}
	public BigDecimal getPfxScPerc() {
		return pfxScPerc;
	}
	public void setPfxScPerc(BigDecimal pfxScPerc) {
		this.pfxScPerc = pfxScPerc;
	}
	public BigDecimal getPfxKnPerc() {
		return pfxKnPerc;
	}
	public void setPfxKnPerc(BigDecimal pfxKnPerc) {
		this.pfxKnPerc = pfxKnPerc;
	}
	public BigDecimal getPfxUnPerc() {
		return pfxUnPerc;
	}
	public void setPfxUnPerc(BigDecimal pfxUnPerc) {
		this.pfxUnPerc = pfxUnPerc;
	}
	public BigDecimal getBisFbVelocity() {
		return bisFbVelocity;
	}
	public void setBisFbVelocity(BigDecimal bisFbVelocity) {
		this.bisFbVelocity = bisFbVelocity;
	}
	public BigDecimal getBisSlVelocity() {
		return bisSlVelocity;
	}
	public void setBisSlVelocity(BigDecimal bisSlVelocity) {
		this.bisSlVelocity = bisSlVelocity;
	}
	public BigDecimal getBisCtVelocity() {
		return bisCtVelocity;
	}
	public void setBisCtVelocity(BigDecimal bisCtVelocity) {
		this.bisCtVelocity = bisCtVelocity;
	}
	public BigDecimal getBisCbVelocity() {
		return bisCbVelocity;
	}
	public void setBisCbVelocity(BigDecimal bisCbVelocity) {
		this.bisCbVelocity = bisCbVelocity;
	}
	public BigDecimal getBisChVelocity() {
		return bisChVelocity;
	}
	public void setBisChVelocity(BigDecimal bisChVelocity) {
		this.bisChVelocity = bisChVelocity;
	}
	public BigDecimal getBisSfVelocity() {
		return bisSfVelocity;
	}
	public void setBisSfVelocity(BigDecimal bisSfVelocity) {
		this.bisSfVelocity = bisSfVelocity;
	}
	public BigDecimal getBisKnVelocity() {
		return bisKnVelocity;
	}
	public void setBisKnVelocity(BigDecimal bisKnVelocity) {
		this.bisKnVelocity = bisKnVelocity;
	}
	public BigDecimal getBisXxVelocity() {
		return bisXxVelocity;
	}
	public void setBisXxVelocity(BigDecimal bisXxVelocity) {
		this.bisXxVelocity = bisXxVelocity;
	}
	public BigDecimal getPfxFaVelocity() {
		return pfxFaVelocity;
	}
	public void setPfxFaVelocity(BigDecimal pfxFaVelocity) {
		this.pfxFaVelocity = pfxFaVelocity;
	}
	public BigDecimal getPfxFtVelocity() {
		return pfxFtVelocity;
	}
	public void setPfxFtVelocity(BigDecimal pfxFtVelocity) {
		this.pfxFtVelocity = pfxFtVelocity;
	}
	public BigDecimal getPfxFcVelocity() {
		return pfxFcVelocity;
	}
	public void setPfxFcVelocity(BigDecimal pfxFcVelocity) {
		this.pfxFcVelocity = pfxFcVelocity;
	}
	public BigDecimal getPfxFsVelocity() {
		return pfxFsVelocity;
	}
	public void setPfxFsVelocity(BigDecimal pfxFsVelocity) {
		this.pfxFsVelocity = pfxFsVelocity;
	}
	public BigDecimal getPfxFoVelocity() {
		return pfxFoVelocity;
	}
	public void setPfxFoVelocity(BigDecimal pfxFoVelocity) {
		this.pfxFoVelocity = pfxFoVelocity;
	}
	public BigDecimal getPfxSiVelocity() {
		return pfxSiVelocity;
	}
	public void setPfxSiVelocity(BigDecimal pfxSiVelocity) {
		this.pfxSiVelocity = pfxSiVelocity;
	}
	public BigDecimal getPfxSlVelocity() {
		return pfxSlVelocity;
	}
	public void setPfxSlVelocity(BigDecimal pfxSlVelocity) {
		this.pfxSlVelocity = pfxSlVelocity;
	}
	public BigDecimal getPfxCuVelocity() {
		return pfxCuVelocity;
	}
	public void setPfxCuVelocity(BigDecimal pfxCuVelocity) {
		this.pfxCuVelocity = pfxCuVelocity;
	}
	public BigDecimal getPfxKcVelocity() {
		return pfxKcVelocity;
	}
	public void setPfxKcVelocity(BigDecimal pfxKcVelocity) {
		this.pfxKcVelocity = pfxKcVelocity;
	}
	public BigDecimal getPfxEpVelocity() {
		return pfxEpVelocity;
	}
	public void setPfxEpVelocity(BigDecimal pfxEpVelocity) {
		this.pfxEpVelocity = pfxEpVelocity;
	}
	public BigDecimal getPfxChVelocity() {
		return pfxChVelocity;
	}
	public void setPfxChVelocity(BigDecimal pfxChVelocity) {
		this.pfxChVelocity = pfxChVelocity;
	}
	public BigDecimal getPfxScVelocity() {
		return pfxScVelocity;
	}
	public void setPfxScVelocity(BigDecimal pfxScVelocity) {
		this.pfxScVelocity = pfxScVelocity;
	}
	public BigDecimal getPfxKnVelocity() {
		return pfxKnVelocity;
	}
	public void setPfxKnVelocity(BigDecimal pfxKnVelocity) {
		this.pfxKnVelocity = pfxKnVelocity;
	}
}
