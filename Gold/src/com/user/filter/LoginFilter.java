package com.user.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//不登录不能跳转
@WebFilter(urlPatterns="/client/*")
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("过滤器启动");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest =(HttpServletRequest) request;
		HttpSession session=httpRequest.getSession();
		String userId = (String) session.getAttribute("userId");
//		String uname = (String) session.getAttribute("uname");
//		String upower = (String) session.getAttribute("upower");
//		String createTime = (String) session.getAttribute("createTime");
		if (userId ==null || userId.length()<=0) {
			HttpServletResponse httpResponse =(HttpServletResponse) response;
			//request.getRequestDispatcher("/Gold/index.html").forward(httpRequest, httpResponse);
			httpResponse.sendRedirect("/Gold/unlogin.html");
			System.out.println("过滤器信息：请登录");
		}else {

			chain.doFilter(httpRequest, response);
		}
	}

	@Override
	public void destroy() {
		System.out.println("过滤器销毁");
	}

}
