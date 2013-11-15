package com.lwp.spider.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lwp.spider.util.UIUtils;

/**
 * Servlet implementation class SpiderSimpleServlet
 */

public class SpiderSimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SpiderSimpleServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post");
		String name = String.valueOf(request.getParameter("name"));
		String password = String.valueOf(request.getParameter("password"));
		if("admin".equals(name)&&"admin123".equals(password)){
			UIUtils.responseJSON("success", response);
		}else{
			UIUtils.responseJSON("error", response);
		}
		
		
	}

}
