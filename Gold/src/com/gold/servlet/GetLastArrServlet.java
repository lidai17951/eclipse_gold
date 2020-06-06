package com.gold.servlet;

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
import com.gold.entity.GoldEntity;
import com.gold.serviceImpl.GoldServiceImpl;

/*
 *  ��ȾshowDetailҳ��� �Ҳ� �м�  ����ͼ
 * */
@WebServlet("/client/GetLastArrServlet")
public class GetLastArrServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//ǰ�˴���howRow,��֪���ݿ���Ҫ��������
		int howRow= Integer.parseInt(request.getParameter("howRow"));
		//��ȡҪ��ѯ�Ĵ���
		String goldVariety = request.getParameter("goldVariety");	
		GoldServiceImpl gs =new GoldServiceImpl();
		List<GoldEntity> lastArr = gs.getLastArr(howRow, goldVariety);
		 String jsonString = JSON.toJSONString(lastArr);
		 JSONArray jObj =JSONObject.parseArray(jsonString);
		 PrintWriter out = response.getWriter();
		 out.print(jObj);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
