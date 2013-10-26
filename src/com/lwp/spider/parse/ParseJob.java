package com.lwp.spider.parse;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.lwp.spider.mvc.service.PublishService;
import com.lwp.spider.parse.impl.SinaBlogParser;

public class ParseJob implements Job{

	private int index;
	
	private static String ENCODE = "UTF-8";
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			ApplicationContext appctx = (ApplicationContext)context.getScheduler().getContext().get("appctx");
			parse(context,appctx);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void parse(JobExecutionContext jobctx,ApplicationContext appctx){
		
		String url = ""+jobctx.getJobDetail().getJobDataMap().get("url");
		if("".equals(url)||"null".equals(url)){

			return;
		}
		PublishService publishService = appctx.getBean(PublishService.class);
		ArticalParser p  = new SinaBlogParser();
		publishService.publish(url,p.doParse(url));
		
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}
	
	
	

}
