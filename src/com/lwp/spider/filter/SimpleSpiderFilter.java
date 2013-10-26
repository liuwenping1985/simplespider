package com.lwp.spider.filter;

import java.util.ArrayList;
import java.util.List;

public  class SimpleSpiderFilter implements SpiderFilter{

	@Override
	public boolean acceptUrl(String url) {
		if(url.startsWith("http://blog.sina.com.cn/s/blog")){
			return true;
		}
		return false;
	}

	@Override
	public boolean acceptLinkTag(String text) {
		// TODO Auto-generated method stub
		for(String str:KEY_WORD_LIST){
			if(text.contains(str)){
				return true;
			}
		}
		return false;
	}
	private static final List<String> KEY_WORD_LIST = new ArrayList<String>();
	//http://blog.sina.com.cn/lm/top/cul/day.html
	static {
		KEY_WORD_LIST.add("好");
		KEY_WORD_LIST.add("最");
		KEY_WORD_LIST.add("金融");
		KEY_WORD_LIST.add("火");
		KEY_WORD_LIST.add("流行");
		KEY_WORD_LIST.add("公务员");
		KEY_WORD_LIST.add("房");
		KEY_WORD_LIST.add("物理");
		KEY_WORD_LIST.add("数学");
		KEY_WORD_LIST.add("金融");
		KEY_WORD_LIST.add("股票");
		KEY_WORD_LIST.add("育儿");
		
		
	}
	
//	
//	public static void main(String[] rags){
//		
//		System.out.println("http://blog.sina.com.cn/s/blog_7159859d0102f10e.html?tj=1".startsWith("http://blog.sina.com.cn/s/blog"));
//	}

}
