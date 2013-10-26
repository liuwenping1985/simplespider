package com.lwp.spider.filter;

public interface SpiderFilter {
	
	boolean acceptUrl(String url); 
	boolean acceptLinkTag(String text);

}
