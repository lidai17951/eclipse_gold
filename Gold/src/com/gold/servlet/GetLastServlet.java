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
 *  渲染index.html页面
 * */
@WebServlet("/GetLastServlet")
public class GetLastServlet extends HttpServlet {

	/*
	 * 通过前端传来的代号，获取metal表中的数据(此表每个种类的金属只有一条，因为只放最新数据) metal表中的variety(大小写敏感)Au9999
	 * Au9995 PT9995 Au100g AuT+D 细分表表名(大小写不敏感)：au9999 au9995 pt9995 au100g autd
	 * (注意这个)
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
