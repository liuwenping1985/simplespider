package com.lwp.spider.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class UIUtils {

	private static final Gson GSON = new GsonBuilder().create();

	public static void responseJSON(Object data, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control",
				"no-store, max-age=0, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		try {
			PrintWriter out = response.getWriter();
			out.write(GSON.toJson(data));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readContent(String url,String encoding){

		InputStream ins = null;
		StringBuilder stb = new StringBuilder();
		try {
			ins = new URL(url).openConnection().getInputStream();

			byte[] buffer = new byte[2046];
			int read = 0;
			
			while ((read = ins.read(buffer)) > 0) {
				if (read == 2046) {
					stb.append(new String(buffer,encoding));
				} else {
					byte[] real = new byte[read];
					System.arraycopy(buffer, 0, real, 0, read);
					stb.append(new String(real,encoding));
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
		
		return stb.toString();
	
	}
	public static String readContent(String url) {
		
		return readContent(url,"UTF-8");
	}

}
