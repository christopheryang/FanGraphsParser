package com.fantasyworks.fangraphsparser.entity;

import javax.persistence.Entity;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;

@Entity
public class BatterRegularSeasonProjectedStats extends BatterStats {

	private static final long serialVersionUID = 1L;

	public BatterRegularSeasonProjectedStats(){
		this.statsType = StatsTypeEnum.RSPr;
	}
}
