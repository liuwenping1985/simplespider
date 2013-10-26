package com.lwp.spider;

import java.util.List;

import org.quartz.Job;

import com.lwp.spider.filter.SpiderFilter;

public interface Spider extends Job{
	
	
	public List<String> crawl(String url,SpiderFilter filter);
	
	
	

}
