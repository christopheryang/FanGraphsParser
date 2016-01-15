package com.fantasyworks.fangraphsparser.crawler;

import org.junit.Test;

public class FanGraphsPitchersCrawlerTest {

	protected FanGraphsPitchersCrawler crawler = new FanGraphsPitchersCrawler();
	
	@Test
	public void testDownloadPlayerIndexPage(){
		crawler.crawlPlayerIndexPages();
	}
	
	@Test
	public void testDownloadPlayerPages(){
		crawler.crawlPlayerPages();
	}
}
