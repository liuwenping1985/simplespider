package com.lwp.spider.mvc.service;

import java.util.List;

public interface PublishService {
	
	
	public void publish(String url,String content);
	public void publishCrawlResult(List<String> content);
	
	public String pop();
	
	public String popContent(String url);

}
