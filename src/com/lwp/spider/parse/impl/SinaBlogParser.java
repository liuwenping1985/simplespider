package com.lwp.spider.parse.impl;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.lwp.spider.parse.ArticalParser;
import com.lwp.spider.util.UIUtils;

public class SinaBlogParser implements ArticalParser {

	@Override
	public String doParse(String url) {
		// TODO Auto-generated method stub

		try {
			Parser parser = new Parser(UIUtils.readContent(url));
			AndFilter articleBody = new AndFilter(new TagNameFilter("div"),
					new HasAttributeFilter("id", "articlebody"));
	
			StringBuilder stb = new StringBuilder();
			NodeList nodelist = parser.extractAllNodesThatMatch(articleBody);
			if (nodelist.size() == 1) {
				Node node = nodelist.elementAt(0);
				NodeList childlist = node.getChildren();
				NodeList checkingList = childlist.extractAllNodesThatMatch(new TagNameFilter("div"));
				if (checkingList.size()>0&&(node=checkingList.elementAt(0)) instanceof Div) {
					// w600 in sub layer
					if("w600".equals(((Div)node).getAttribute("class"))){
						childlist = node.getChildren();
					}
				}
				for (int k = 0; k < childlist.size(); k++) {
					node = childlist.elementAt(k);
					if (node instanceof Div) {
						Div div = (Div) node;
					
						System.out.println(div.getAttribute("class"));
						if (String.valueOf(div.getAttribute("class")).trim()
								.equals("articalTitle")) {
							stb.append(div.toHtml());
							continue;
						}
						if (String.valueOf(div.getAttribute("class")).trim()
								.equals("articalContent")) {
							System.out.println(div.toHtml(false));
							stb.append(div.toHtml(false));
							break;
						}

					}
				}

			}
			return stb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		String content = UIUtils.readContent("http://lme.cnmc.com.cn/html/lme_metal.html");
		System.out.println(content);
	
		try {
			StringBuilder stb = new StringBuilder();
			Parser parser = new Parser(content);
			NodeList tableList = parser.extractAllNodesThatMatch(new TagNameFilter("table"));
			int tblSize = tableList.size();
			for(int i=0;i<tblSize;i++){
				TableTag table = (TableTag)tableList.elementAt(i);
				NodeList tableRowList = table.getChildren().extractAllNodesThatMatch(new TagNameFilter("tr"));
				if(tableRowList.size()>0){
					for(int j=0;j<tableRowList.size();j++){
						Node row = tableRowList.elementAt(j);
						NodeList tableCellList = row.getChildren();
						for(int k=0;k<tableCellList.size();k++){
							Node cell = tableCellList.elementAt(k);
							//org.htmlparser.tags.TableColumn.class
							if(cell instanceof TableColumn){
								TableColumn col = (TableColumn)cell;
								col.setAttribute("height", "20px");
							}
						}
					}
				}
				stb.append(table.toHtml());
				if(i == 0){
					stb.append("<hr/>");
				}
			}
			System.out.println(stb.toString());
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
