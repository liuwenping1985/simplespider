package com.lwp.spider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author liuwening
 * 
 */
public class SpiderConfiguration {

	private String maxCrawThread = "3";

	private String maxParseThread = "3";

	private Properties config;

	public Properties getConfiguartion() {
		return config;
	}

	public int getMaxCrawThreadCount() {
		return Integer.parseInt(System.getProperty("spider.maxCrawThreadCount",
				maxCrawThread));
	}

	public void setMaxCrawThreadCount(String maxCrawThread) {
		this.maxCrawThread = maxCrawThread;
	}

	public int getMaxParseThreadCount() {
		return Integer.parseInt(System.getProperty(
				"spider.maxParseThreadCount", maxParseThread));
	}

	public void setMaxParseThreadCount(String maxParseThread) {
		this.maxParseThread = maxParseThread;
	}

	private int level = 1;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public SpiderConfiguration() {

		InputStream in = null;
		try {
			String path = this.getClass().getClassLoader()
					.getResource("spider.properties").getPath();
			
			Properties p = new Properties();
			
			in = new FileInputStream(new File(path));
			
			p.load(new FileInputStream(new File(path)));

			config = p;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}