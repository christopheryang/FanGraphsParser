package com.fantasyworks.fangraphsparser.entity;

import javax.persistence.Entity;

import com.fantasyworks.fangraphsparser.enumeration.StatsTypeEnum;

@Entity
public class BatterRegularSeasonPartialStats extends BatterStats {

	private static final long serialVersionUID = 1L;

	public BatterRegularSeasonPartialStats(){
		this.statsType = StatsTypeEnum.RSPa;
	}

}
