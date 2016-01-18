package com.fantasyworks.fangraphsparser.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;

@MappedSuperclass
public class PitcherStats extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="playerId", nullable=false)
	protected Player player;
	
	@Column(nullable=false)
	protected Integer season;
	
	@Column(nullable=false)
	protected String team;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	protected StatsTypeEnum statsType;
	
	protected Integer win;
	protected Integer loss;
	protected Integer save;
	protected Integer games;
	protected Integer gs;
	protected BigDecimal ip;
	protected BigDecimal kPer9;
	protected BigDecimal bbPer9;
	protected BigDecimal hrPer9;
	protected BigDecimal babip;
	protected BigDecimal lobPerc;
	protected BigDecimal gbPerc; // Ground ball per ball in play
	protected BigDecimal hrPerFb; // HR per fly ball
	protected BigDecimal era;
	protected BigDecimal fip; // Fielder independent pitching on ERA scale
	protected BigDecimal xFip; // Expected FIP where HR/FB is set to 10.5%
	protected BigDecimal war;
	
	
	
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer year) {
		this.season = year;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
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
	public StatsTypeEnum getStatsType() {
		return statsType;
	}
	public void setStatsType(StatsTypeEnum statsType) {
		this.statsType = statsType;
	}
}
