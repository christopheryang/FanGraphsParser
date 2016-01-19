package com.fantasyworks.fangraphsparser.entity;

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
public class PlayerStats extends IdEntity {

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
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || obj instanceof PlayerStats == false){
			return false;
		}
		if(this == obj){
			return true;
		}
		PlayerStats instance = (PlayerStats) obj;
		return Objects.equals(this.getSeason(), instance.getSeason()) &&
				Objects.equals(this.getTeam(), instance.getTeam()) &&
				Objects.equals(this.getStatsType(), instance.getStatsType()) &&
				Objects.equals(this.getPlayer(), instance.getPlayer());
	}
	
	@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
				.add("player", player)
				.add("season", season)
				.add("team", team)
				;
	}

	public String getUid(){
		return getPlayer().getName()+"_"+getPlayer().getUid()+"_"+getStatsType()+"_"+getSeason()+"_"+getTeam();
	}

	
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
	public StatsTypeEnum getStatsType() {
		return statsType;
	}
	public void setStatsType(StatsTypeEnum statsType) {
		this.statsType = statsType;
	}

}
