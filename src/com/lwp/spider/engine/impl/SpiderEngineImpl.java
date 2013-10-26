package com.lwp.spider.engine.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lwp.spider.SimpleSpider;
import com.lwp.spider.SpiderConfiguration;
import com.lwp.spider.engine.SpiderEngine;
import com.lwp.spider.filter.SimpleSpiderFilter;
import com.lwp.spider.filter.SpiderFilter;
import com.lwp.spider.mvc.service.PublishService;
import com.lwp.spider.parse.ParseJob;

@Service("spider.service.engine")
public class SpiderEngineImpl implements SpiderEngine {

	@Autowired
	@Qualifier("spring.scheduler")
	private Scheduler scheduler;
	
	//"spider.service.publish"
	@Autowired
	@Qualifier("spider.service.publish")
	private PublishService publishService;

	private SpiderConfiguration spiderConfig;

	@PostConstruct
	public void start() {
		try {
			spiderConfig = new SpiderConfiguration();
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void shutdown() {
		try {
			scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int curParseThreadCount = 0;

	private int curCrawlThreadCount = 0;

	public SpiderConfiguration getSpiderConfig() {
		return spiderConfig;
	}

	public void setSpiderConfig(SpiderConfiguration spiderConfig) {
		this.spiderConfig = spiderConfig;
	}

	public int getCurParseThreadCount() {
		return curParseThreadCount;
	}

	public void setCurParseThreadCount(int curThreadCount) {
		this.curParseThreadCount = curThreadCount;
	}

	public synchronized void consumeCrawThread() {

		curCrawlThreadCount++;
	}

	public synchronized void relaseCrawThread() {
		curCrawlThreadCount--;
	}

	public synchronized void consumeParseThread() {

		curParseThreadCount++;
	}

	public synchronized void relaseParseThread() {
		curParseThreadCount--;
	}

	@Override
	public void startParseTask(String url) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (curParseThreadCount > spiderConfig.getMaxParseThreadCount()) {
				// TODO :Put the job into wait Queue
				return;
			}
			// scheduler.s
			JobDetail jd = JobBuilder
					.newJob(ParseJob.class)
					.withIdentity("parseJob" + curParseThreadCount,
							"parse_job_group").build();
			parseJobPool.put(url, jd.getKey());
			jd.getJobDataMap().put("url", url);
			jd.getJobDataMap().put("index", curParseThreadCount);
			Trigger t = TriggerBuilder
					.newTrigger()
					.withIdentity("parseJobTrigger" + curParseThreadCount,
							"Trigger_Group")
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withRepeatCount(0)).startNow().build();
			parseTriggerPool.put(url, t.getKey());
			try {

				scheduler.scheduleJob(jd, t);
				this.consumeParseThread();

			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				parseJobPool.remove(url);
				parseTriggerPool.remove(url);
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getContent(String url) {
		// TODO Auto-generated method stub
		return publishService.popContent(url);
	}

	@Override
	public void startCrawlTask(String url) {
		// TODO Auto-generated method stub
		// crawlJobPool.put(url, )
		if (curCrawlThreadCount > this.getSpiderConfig()
				.getMaxCrawThreadCount()) {
			return;
		}
		// TODO Auto-generated method stub
		synchronized (this) {
			if (curParseThreadCount > spiderConfig.getMaxParseThreadCount()) {
				// TODO :Put the job into wait Queue
				return;
			}
			// scheduler.s
			JobDetail jd = JobBuilder
					.newJob(SimpleSpider.class)
					.withIdentity("crawJob" + curCrawlThreadCount,
							"craw_job_group").build();
			crawlJobPool.put(url, jd.getKey());
			JobDataMap data = jd.getJobDataMap();
			data.put("url", url);
			data.put("index", curCrawlThreadCount);
			data.put("filter", spiderFilter);
			Trigger t = TriggerBuilder
					.newTrigger()
					.withIdentity("crawJobTrigger" + curCrawlThreadCount,
							"craw_trigger_Group")
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withRepeatCount(0)).startNow().build();
			crawlTriggerPool.put(url, t.getKey());
			try {

				scheduler.scheduleJob(jd, t);
				this.consumeParseThread();

			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				crawlJobPool.remove(url);
				crawlTriggerPool.remove(url);
				e.printStackTrace();
			}
		}
	
		

	}

	@Override
	public void destoryCrawlTask(String url) {
		this.relaseCrawThread();
		try {
			scheduler.unscheduleJob(crawlTriggerPool.remove(url));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			scheduler.deleteJob(crawlJobPool.remove(url));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String, JobKey> crawlJobPool = new HashMap<String, JobKey>();
	private Map<String, TriggerKey> crawlTriggerPool = new HashMap<String, TriggerKey>();

	private Map<String, JobKey> parseJobPool = new HashMap<String, JobKey>();
	private Map<String, TriggerKey> parseTriggerPool = new HashMap<String, TriggerKey>();
	
	private SpiderFilter spiderFilter = new SimpleSpiderFilter();

	@Override
	public void destoryParseTask(String url) {

		TriggerKey tk = parseTriggerPool.remove(url);
		if (tk != null) {
			try {
				scheduler.unscheduleJob(tk);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JobKey jk = parseJobPool.remove(url);
		try {
			scheduler.deleteJob(jk);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.relaseParseThread();

		// scheduler

	}

}
