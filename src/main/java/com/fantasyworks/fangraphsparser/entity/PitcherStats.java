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
	
	// Batted ball section (no minor league stats)
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
}
