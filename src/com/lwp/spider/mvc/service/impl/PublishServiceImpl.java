package com.lwp.spider.mvc.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.lwp.spider.mvc.service.PublishService;

@Service("spider.service.publish")
public class PublishServiceImpl implements PublishService{
	
	public  Map<String,String> context =new HashMap<String,String>();
	
	private Set<String> urlHashSet = new HashSet<String>();
	
	
	public void publish(String url,String content){
		
		context.put(url, content);
		
	}

	@Override
	public void publishCrawlResult(List<String> contentList) {
		// TODO Auto-generated method stub
		urlHashSet.addAll(contentList);
		
	}

	@Override
	public String pop() {
		// TODO Auto-generated method stub
		if(urlHashSet.isEmpty()){
			return "";
		}
		String url =  urlHashSet.iterator().next();
		urlHashSet.remove(url);
		return url;
	}

	@Override
	public String popContent(String url) {
		// TODO Auto-generated method stub
		return context.get(url);
	}
	

}
