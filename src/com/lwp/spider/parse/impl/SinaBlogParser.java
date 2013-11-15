package com.lwp.spider.parse.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;

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
		//String content = UIUtils.readContent("http://www.360doc.com/ajax/getreadroomart.ashx?pagenum=25&curnum=1&cid=9&scid=70&iscream=1&sort=1&_="+new Date().getTime());
		/**
		  HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,5000); //设置连接超时为5秒
        HttpClient client = new DefaultHttpClient(httpParams); // 生成一个http客户端发送请求对象
        HttpPost httpPost = new HttpPost(urlString); //设定请求方式
		 */
		
		//HttpUriRequest request = new HttpUriRequest();
		
		HttpClient client = new HttpClient(); 
		HttpMethod method = new GetMethod("http://www.360doc.com/ajax/getreadroomart.ashx?pagenum=25&curnum=1&cid=9&scid=70&iscream=1&sort=1&_="+new Date().getTime());
		try {
			Header header = new Header();
			header.setName("Referer");
			header.setValue("http://www.360doc.com/classarticle70.html");
			method.addRequestHeader(header);
			client.executeMethod(method);
			InputStream ins=null;
			StringBuilder stb = new StringBuilder();
			try {
					
				ins = method.getResponseBodyAsStream();
				

				byte[] buffer = new byte[2046];
				int read = 0;
				
				while ((read = ins.read(buffer)) > 0) {
					if (read == 2046) {
						stb.append(new String(buffer,"UTF-8"));
					} else {
						byte[] real = new byte[read];
						System.arraycopy(buffer, 0, real, 0, read);
						stb.append(new String(real,"UTF-8"));
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(ins!=null){
					try {
						ins.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			System.out.println(stb.toString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//client.execute(arg0)
		
	//	System.out.println(content);
	
	
	
	}

}
