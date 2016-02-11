package com.fantasyworks.fangraphsparser.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;
import com.google.common.base.MoreObjects.ToStringHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false, of={"season", "team", "statsType", "player"})
public class PlayerStats extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
	
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
	
	// ======= Derived stats ======
	// Player's age on 7/1 of each year i.e. mid-season age
	protected BigDecimal age;
	
	// Player's name
	protected String name;
	// ============================
	
	@PrePersist
	public void populateDerivedStats(){
		if(player!=null){
			if(name == null){
				name = player.getName();
			}
			if(age == null){
				DateTime midSeason = ISO_DATE_FORMATTER.parseDateTime(season+"-07-01");
				DateTime birthdate = new DateTime(player.getBirthdate());
				Duration ageAsDuration = new Duration(birthdate, midSeason);
				age = new BigDecimal(ageAsDuration.getStandardDays()/365.25).setScale(1, RoundingMode.HALF_UP);
			}
		}
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

	

}
