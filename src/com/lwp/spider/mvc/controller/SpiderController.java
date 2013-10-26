package com.lwp.spider.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lwp.spider.engine.SpiderEngine;
import com.lwp.spider.mvc.service.PublishService;
import com.lwp.spider.util.UIUtils;

@Controller
@RequestMapping("api/")
public class SpiderController {
	@Autowired
	@Qualifier("spider.service.engine")
	private SpiderEngine engine;

	@Autowired
	@Qualifier("spider.service.publish")
	private PublishService publishService;

	@RequestMapping("start.do")
	public void start(String url, HttpServletResponse response) {

		System.out.println("url:" + url);

		engine.startCrawlTask(url);

		UIUtils.responseJSON("success", response);

	}

	@RequestMapping("fetch.do")
	public void fetch(String url,HttpServletResponse response) {

		UIUtils.responseJSON(engine.getContent(url), response);

	}

	@RequestMapping("parse.do")
	public void parse(int num, HttpServletResponse response) {
		List<String> urls =new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			String url = publishService.pop();
			if(url == null){
				break;
			}
			engine.startParseTask(url);
			urls.add(url);
			
		}
		UIUtils.responseJSON(urls, response);

	}

}
