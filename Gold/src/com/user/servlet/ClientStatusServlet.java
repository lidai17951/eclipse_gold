package com.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.user.entity.UserInfo;

/*
 * client文件夹下的登录状态
 */
@WebServlet("/client/ClientStatusServlet")
public class ClientStatusServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		HttpSession session = request.getSession();
		String uname = (String) session.getAttribute("uname");
		String userId = (String) session.getAttribute("userId");
		String upower = (String) session.getAttribute("upower");
		String headurl = (String) session.getAttribute("headurl");
			if (uname != null || !"".equals(uname)) {
				UserInfo uInfo = new UserInfo();
				uInfo.setUname(uname);
				uInfo.setUserId(userId);
				uInfo.setUpower(upower);
				uInfo.setHeadurl(headurl);
				JSONObject jObj = JSONObject.parseObject(JSON.toJSONString(uInfo));
				PrintWriter out = response.getWriter();
				out.print(jObj);
			}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
