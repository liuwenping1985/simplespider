package com.lwp.spider;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.lwp.spider.engine.SpiderEngine;
import com.lwp.spider.filter.SpiderFilter;
import com.lwp.spider.mvc.service.PublishService;

public abstract class AbstractSpider implements Spider {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String url = String.valueOf(dataMap.get("url"));
		SpiderFilter filter = (SpiderFilter) dataMap.get("filter");
		ApplicationContext appctx = null;
		try {
			appctx = (ApplicationContext) context.getScheduler()
					.getContext().get("appctx");
			PublishService publishService = appctx.getBean(PublishService.class);
			publishService.publishCrawlResult(crawl(url, filter));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			SpiderEngine engine = appctx.getBean(SpiderEngine.class);
			engine.destoryCrawlTask(url);
			
		}
		

	}

}
