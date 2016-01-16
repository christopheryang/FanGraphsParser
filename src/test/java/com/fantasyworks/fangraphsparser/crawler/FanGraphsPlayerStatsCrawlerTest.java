package com.fantasyworks.fangraphsparser.crawler;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class FanGraphsPlayerStatsCrawlerTest {

	protected FanGraphsPlayerStatsCrawler crawler = new FanGraphsPlayerStatsCrawler();
	
	@Test
	public void testCrawlPlayerIndexPages(){
		List<String> files = crawler.crawlPlayerIndexPages();
		assertThat(files, hasSize(greaterThan(300)));
	}
	
	@Test
	public void testCrawlPlayerSeasonStatsPages(){
		List<String> files = crawler.crawlPlayerSeasonStatsPages();
		assertThat(files, hasSize(greaterThan(1000)));
	}
}
