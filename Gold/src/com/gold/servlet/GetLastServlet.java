package com.gold.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gold.entity.GoldEntity;
import com.gold.service.GoldService;
import com.gold.serviceImpl.GoldServiceImpl;
import com.sql.dao.impl.SqlDaoImpl;

import listener.OnlineEntity;

/*
 *  ��Ⱦindex.htmlҳ��
 * */
@WebServlet("/GetLastServlet")
public class GetLastServlet extends HttpServlet {

	/*
	 * ͨ��ǰ�˴����Ĵ��ţ���ȡmetal���е�����(�˱�ÿ������Ľ���ֻ��һ������Ϊֻ����������) metal���е�variety(��Сд����)Au9999
	 * Au9995 PT9995 Au100g AuT+D ϸ�ֱ����(��Сд������)��au9999 au9995 pt9995 au100g autd
	 * (ע�����)
	 * 
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		GoldService gs = new GoldServiceImpl();
		List<GoldEntity> metal = gs.getMetal();
		String jsonString = JSON.toJSONString(metal);
		JSONArray jObj = JSONObject.parseArray(jsonString);

		PrintWriter out = response.getWriter();
		out.print(jObj);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
