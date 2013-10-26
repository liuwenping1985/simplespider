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
		KEY_WORD_LIST.add("��");
		KEY_WORD_LIST.add("��");
		KEY_WORD_LIST.add("����");
		KEY_WORD_LIST.add("��");
		KEY_WORD_LIST.add("����");
		KEY_WORD_LIST.add("����Ա");
		KEY_WORD_LIST.add("��");
		KEY_WORD_LIST.add("����");
		KEY_WORD_LIST.add("��ѧ");
		KEY_WORD_LIST.add("����");
		KEY_WORD_LIST.add("��Ʊ");
		KEY_WORD_LIST.add("����");
		
		
	}
	
//	
//	public static void main(String[] rags){
//		
//		System.out.println("http://blog.sina.com.cn/s/blog_7159859d0102f10e.html?tj=1".startsWith("http://blog.sina.com.cn/s/blog"));
//	}

}
