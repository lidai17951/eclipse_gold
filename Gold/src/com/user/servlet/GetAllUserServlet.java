package com.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.user.entity.UserInfo;
import com.user.entity.UserPage;
import com.user.service.UserService;


@WebServlet("/admin/GetAllUserServlet")
public class GetAllUserServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//每个页面几条数据
		int pageSize =Integer.parseInt(request.getParameter("pageSize")) ;
		//当前第几页
		int currentPage =Integer.parseInt(request.getParameter("currentPage")) ;

		UserService uService = new UserService();
		UserPage uPage = uService.paging(pageSize,currentPage,"createtime","asc");
		 String jsonString = JSON.toJSONString(uPage);
		 String jsonString2 = JSON.toJSONString(uPage.getobjList());
		 JSONArray jObj = JSONArray.parseArray("["+jsonString+jsonString2+"]");
		 PrintWriter out = response.getWriter();
		 out.print(jObj);
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
