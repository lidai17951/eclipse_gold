package com.news.servlet;

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
import com.news.entity.RotateImg;
import com.news.service.NewsService;

/* 加载adminNewsControl.html的tbody
 * 获取rotate_img 里所有的轮播图(最多10张)信息
 * */
@WebServlet("/admin/GetAllRotateImgServlet")
public class GetAllRotateImgServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		NewsService newsService =new NewsService();
		List<RotateImg> RotateImgList = newsService.getAllRotateImg();
			if (RotateImgList!=null) {
				 String jsonString = JSON.toJSONString(RotateImgList);
				 JSONArray jArray =JSONObject.parseArray(jsonString);
				 out.print(jArray);
			}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
