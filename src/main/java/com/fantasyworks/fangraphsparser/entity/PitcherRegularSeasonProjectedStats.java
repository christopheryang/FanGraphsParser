package com.fantasyworks.fangraphsparser.entity;

import javax.persistence.Entity;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;

@Entity
public class PitcherRegularSeasonProjectedStats extends PitcherStats {

	private static final long serialVersionUID = 1L;

	public PitcherRegularSeasonProjectedStats(){
		this.statsType = StatsTypeEnum.REGULAR_SEASON_PARTIAL;
	}
}
