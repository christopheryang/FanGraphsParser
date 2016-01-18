package com.fantasyworks.fangraphsparser.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"playerId", "season", "team"}))
public class PitcherRegularSeasonStats extends PitcherStats {

	private static final long serialVersionUID = 1L;

	public PitcherRegularSeasonStats(){
		statsType = StatsTypeEnum.REGULAR_SEASON;
	}
	
}
