package com.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.user.entity.UserInfo;
import com.user.service.UserService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		UserInfo uInfo;

		String userId = request.getParameter("userid");
		String upwd = request.getParameter("upwd");

		Pattern phoneReg = Pattern.compile("^1\\d{10}$");
		Pattern mailReg = 
		Pattern.compile("^\\w+@\\w{2,}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$");
		Pattern upwdReg = Pattern.compile("^\\w{1,20}$");
		Matcher phoneMatcher = phoneReg.matcher(userId);
		Matcher mailMatcher = mailReg.matcher(userId);
		Matcher upwdMatcher = upwdReg.matcher(upwd);
		PrintWriter out = response.getWriter();
		if ((phoneMatcher.find() || mailMatcher.find()) && upwdMatcher.find()) {
			UserService uService = new UserService();
			uInfo = uService.queryUser(userId, upwd);
			JSONObject jObj = JSONObject.parseObject(JSON.toJSONString(uInfo));
			HttpSession session =request.getSession();
			session.setAttribute("userId", userId);
			session.setAttribute("uname", uInfo.getUname());
			session.setAttribute("upower", uInfo.getUpower());
			session.setAttribute("createTime", uInfo.getCreateTime());
			session.setAttribute("headurl", uInfo.getHeadurl());
			out.print(jObj);
		}else {
			//前端用js的 typeof(result["userId"])也是undefined
			out.write(" {\"userId\":\"undefined\"} "); 
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
