package com.lwp.spider.engine;

public interface SpiderEngine {
	
	
	public void startParseTask(String url);
	
	public void destoryParseTask(String url);
	
	public void startCrawlTask(String url);
	
	public void destoryCrawlTask(String url);
	
	
	
	
	public String getContent(String url);
	
	
	

}
