package com.fantasyworks.fangraphsparser.service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonProjectedStats;
import com.fantasyworks.fangraphsparser.entity.BatterRegularSeasonStats;
import com.fantasyworks.fangraphsparser.entity.BatterStats;
import com.fantasyworks.fangraphsparser.entity.Player;
import com.fantasyworks.fangraphsparser.repo.BatterRegularSeasonProjectedStatsRepo;
import com.fantasyworks.fangraphsparser.repo.BatterRegularSeasonStatsRepo;
import com.fantasyworks.util.ConversionUtil;
import com.google.common.collect.Lists;
import com.opencsv.CSVWriter;

@Service
public class BatterStatsOutputService {

	private static Logger logger = LoggerFactory.getLogger(BatterStatsOutputService.class);
	
	@Autowired
	protected BatterRegularSeasonStatsRepo rsStatsRepo;
	
	@Autowired
	protected BatterRegularSeasonProjectedStatsRepo projRsStatsRepo;
	
	/**
	 * Find players who have projected stats that meet certain criteria.
	 * 
	 * @return
	 */
	protected Set<Player> findProjectedBatters(){
		List<BatterRegularSeasonProjectedStats> allProjectedStats = Lists.newArrayList(projRsStatsRepo.findAll());
		return allProjectedStats.stream()
				.filter(s->s.getAb()>400) // Projected to exceed the minimum number of AB
				.map(s->s.getPlayer())
				.collect(Collectors.toSet());
	}
	
	/**
	 * 
	 */
	public void produceBatterRegularSeasonStatsCSV(){
		Set<Player> projectedPlayers = findProjectedBatters();
		List<BatterRegularSeasonStats> rsStats = projectedPlayers.stream()
				.map(p->rsStatsRepo.findBySeasonAndPlayer(2015, p)) // find last season's stats
				.filter(s->s!=null) // some players may exist in projections but not the previous season
				.collect(Collectors.toList());
		try{
			CSVWriter cw = new CSVWriter(new FileWriter("./output/batters2015.csv"));
			cw.writeNext(getHeaderRow());
			for(BatterStats stats: rsStats){
				cw.writeNext(toTuple(stats));
			}
			cw.close();
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected String[] getHeaderRow(){
		return new String[]{
			"Name",
			"Age",
			"Team",
			"R",
			"HR",
			"RBI",
			"SB",
			"AVG",
			"Gm",
			"AB",
			"PA",
			"H",
			"2B",
			"BB",
			"SO",
			"CS",
			"BB%",
			"K%",
			"BB/K",
			"OBP",
			"SLG",
			"OPS",
			"ISO",
			"BABIP",
			"GB/FB",
			"FB%",
			"HR/FB",
			"GB%",
			"IFFB%",
			"LD%",
			"Pull%",
			"Cent%",
			"Oppo%",
			"Soft%",
			"Med%",
			"Hard%",
			"Balls",
			"Balls/PA",
			"Strikes",
			"Strikes/PA",
			"Pitches",
			"Pitches/PA",
			"O-Swing%",
			"Z-Swing%",
			"Swing%",
			"O-Contact%",
			"Z-Contact%",
			"Contact%",
			"Zone%",
			"F-Strike%",
			"SwStr%"
		};
	}
	
	protected String[] toTuple(BatterStats stats){
		List<Object> tuple = Lists.newArrayList();
		tuple.add(stats.getName());
		tuple.add(stats.getAge().add(BigDecimal.ONE));
		tuple.add(stats.getTeam());
		tuple.add(stats.getRuns());
		tuple.add(stats.getHr());
		tuple.add(stats.getRbi());
		tuple.add(stats.getSb());
		tuple.add(stats.getAvg());
		tuple.add(stats.getGames());
		tuple.add(stats.getAb());
		tuple.add(stats.getPa());
		tuple.add(stats.getHits());
		tuple.add(stats.getDoubles());
		tuple.add(stats.getBb());
		tuple.add(stats.getSo());
		tuple.add(stats.getCs());
		tuple.add(ConversionUtil.toPercentageStr(stats.getBbPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getKPerc()));
		tuple.add(stats.getBbPerK());
		tuple.add(stats.getObp());
		tuple.add(stats.getSlg());
		tuple.add(stats.getOps());
		tuple.add(stats.getIso());
		tuple.add(stats.getBabip());
		tuple.add(stats.getGbPerFb());
		tuple.add(ConversionUtil.toPercentageStr(stats.getFbPerc()));
		tuple.add(stats.getHrPerFb());
		tuple.add(ConversionUtil.toPercentageStr(stats.getGbPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getIffbPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getLdPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getPullPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getCentPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getOppoPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getSoftPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getMedPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getHardPerc()));
		tuple.add(stats.getBalls());
		tuple.add(new BigDecimal(stats.getBalls()*1.0/stats.getPa()).setScale(2, RoundingMode.HALF_UP));
		tuple.add(stats.getStrikes());
		tuple.add(new BigDecimal(stats.getStrikes()*1.0/stats.getPa()).setScale(2, RoundingMode.HALF_UP));
		tuple.add(stats.getPitches());
		tuple.add(new BigDecimal(stats.getPitches()*1.0/stats.getPa()).setScale(2, RoundingMode.HALF_UP));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisOSwingPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisZSwingPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisSwingPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisOContactPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisZContactPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisContactPerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisZonePerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisFStrikePerc()));
		tuple.add(ConversionUtil.toPercentageStr(stats.getBisSwStrPerc()));

		return tuple.stream()
				.map(o -> (o == null)? null: o.toString()) // Don't want to print out "null"
				.collect(Collectors.toList())
				.toArray(new String[tuple.size()]);
	}
	

}
