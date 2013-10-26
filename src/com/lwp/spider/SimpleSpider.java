package com.lwp.spider;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.lwp.spider.filter.SpiderFilter;
import com.lwp.spider.util.UIUtils;

public class SimpleSpider extends AbstractSpider {

	@Override
	public List<String> crawl(String url, SpiderFilter filter) {
		// TODO Auto-generated method stub
		List<String> retList = new ArrayList<String>();
		try {

			
			Parser parser = new Parser(UIUtils.readContent(url,"gb2312"));
			parser.setEncoding("gb2312");
			// parser.vi
			NodeList nodelist = parser
					.extractAllNodesThatMatch(new TagNameFilter("a"));
			System.out.println("urlTotalList:" + nodelist.size());
			for (int i = 0; i < nodelist.size(); i++) {
				Node node = nodelist.elementAt(i);
				if (node instanceof LinkTag) {
					LinkTag linkTag = (LinkTag) nodelist.elementAt(i);
					String text = linkTag.getLink();
					
						System.out.println(linkTag.getLinkText());
					
					if (filter.acceptLinkTag(linkTag.getLinkText())&&filter.acceptUrl(text)) {
						retList.add(text);
					}
				}
			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println("ResultList:" + retList.size());
		return retList;
	}

	

}
