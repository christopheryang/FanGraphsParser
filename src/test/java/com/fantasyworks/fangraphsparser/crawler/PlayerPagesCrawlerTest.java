package com.fantasyworks.fangraphsparser.crawler;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class PlayerPagesCrawlerTest {

	protected PlayerPagesCrawler crawler = new PlayerPagesCrawler();
	
	@Test
	public void testCrawlPlayerIndexPages(){
		List<String> files = crawler.crawlPlayerIndexPages();
		assertThat(files, hasSize(greaterThan(300)));
	}
	
	@Test
	public void testCrawlPlayerSeasonStatsPages(){
		Set<String> files = crawler.crawlPlayerSeasonStatsPages();
		assertThat(files, hasSize(greaterThan(1000)));
	}
}
