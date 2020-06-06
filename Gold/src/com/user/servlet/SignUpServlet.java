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

import com.user.service.UserService;


@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userId = request.getParameter("signupUserId");
		String uname = request.getParameter("signupUname");
		String upwd = request.getParameter("signupUpwd");
		
		Pattern phoneReg = Pattern.compile("^1\\d{10}$");
		Pattern mailReg = 
		Pattern.compile("^\\w+@\\w{2,}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$");
		Pattern unameReg =Pattern.compile("^.{1,10}$");
		Pattern upwdReg = Pattern.compile("^\\w{1,20}$");
		Matcher phoneMatcher = phoneReg.matcher(userId);
		Matcher mailMatcher = mailReg.matcher(userId);
		Matcher unameMatcher = unameReg.matcher(uname);
		Matcher upwdMatcher = upwdReg.matcher(upwd);
		PrintWriter out = response.getWriter();

		if ((phoneMatcher.find() || mailMatcher.find()) && upwdMatcher.find()&&unameMatcher.find()) {
			UserService uService = new UserService();
			String addUserMsg = uService.addUser(userId, uname, upwd);
			if ("注册成功".equals(addUserMsg)) {
				HttpSession session = request.getSession();
				session.setAttribute("userId", userId);
				session.setAttribute("uname", uname);
				session.setAttribute("upower", "client");
				//初始化头像，即默认头像
				session.setAttribute("headurl", "/golddisk/userHeadPhoto/default.jpg");
			}
			out.write(addUserMsg);
		}else {
			out.write("注册失败");
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
