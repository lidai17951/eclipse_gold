package com.net.service;

import com.alibaba.fastjson.JSONObject;
import com.file.io.FileHandle;
import com.file.io.JsonHandl;
import com.gold.entity.GoldEntity;
import com.net.dao.NetConnect;
import com.sql.dao.SqlDao;
import com.sql.dao.impl.SqlDaoImpl;

public class NetService {

	/* 把数据存入oracle,并放入数据仓库 */
	//goldType:传入黄金种类，如 au99.99
	public boolean distributeData(String goldType) {
		String data = NetConnect.getData(goldType);// 从网络中 获取字符串形式的json
		JSONObject packJson = JsonHandl.packJson(data);// 字符串打包成json
		if ("1".equals(packJson.get("success"))) {
			GoldEntity goldEntity = JsonHandl.jsonToBean(packJson);
			boolean addDBAsucceed = addDataBase(goldEntity,goldType);
			// 加入数据库成功再把json放入数据仓库
			if (addDBAsucceed) {
				boolean addStoreSucceed = addStore(packJson, goldEntity);
				if (addDBAsucceed&&addStoreSucceed) {
					return true; //加入数据库 和 数据仓库 都成功
				}else {
					return false; //加入数据仓库失败
				}
			}else {
				return false;//加入数据失败
			}
		} 
		else {
			return false; // json的success不是1
		}

	}

	// 加入数据库 成功返回true
	public boolean addDataBase(GoldEntity goldEntity,String goldType) {

		String querySql = "select count(1) from "+goldType+" where uptime=?";
		Object[] queryParams = { goldEntity.getGoldTime().getUptime() };
		SqlDao goldDao = new SqlDaoImpl();
		boolean isExist = goldDao.queryExist(querySql, queryParams);
		if (!isExist) {// 根据uptime判断数据库是否已有同样的数据
			String addSql = "insert into "+goldType+"\r\n" + "(uptime,\r\n" + "last_price,\r\n" + "buy_price,\r\n"
					+ "sell_price,\r\n" + "volume,\r\n" + "change_price,\r\n" + "change_margin,\r\n" + "high_price,\r\n"
					+ "low_price,\r\n" + "open_price,\r\n" + "yesy_price,\r\n" + "variety)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?)";

			Object[] addParams = { goldEntity.getGoldTime().getUptime(), goldEntity.getLast_price(),
					goldEntity.getBuy_price(), goldEntity.getSell_price(), goldEntity.getVolume(),
					goldEntity.getChange_price(), goldEntity.getChange_margin(), goldEntity.getHigh_price(),
					goldEntity.getLow_price(), goldEntity.getOpen_price(), goldEntity.getYesy_price(),
					goldEntity.getVariety() };

			int addSucceed = goldDao.addGoldInfo(addSql, addParams);
			if (addSucceed > 0) {
				return true;
			}
		}

		return false;
	}

	// 把json加入数据仓库 成功返回true
	public boolean addStore(JSONObject packJson, GoldEntity goldEntity) {
		boolean isDone = false;
		String year = JsonHandl.getYear(goldEntity.getGoldTime().getUptime());
		String monthAndDay = JsonHandl.getMonthAndDay(goldEntity.getGoldTime().getUptime());
		String hhmmss = JsonHandl.gethhmmss(goldEntity.getGoldTime().getUptime());
		String variety =goldEntity.getVariety();
		if (FileHandle.existVarietyFolder(variety)&&FileHandle.existYearFolder(variety, year)) {
			isDone = FileHandle.createFile(variety,year, monthAndDay,hhmmss, packJson);
		} else {
			FileHandle.createFolder(variety,year);
			isDone = FileHandle.createFile(variety,year, monthAndDay,hhmmss, packJson);
		}
		return isDone;
	}

	
	public static void main(String[] args) {
		NetService netService = new NetService();
		boolean isdone1 = netService.distributeData("au9999");
		boolean isdone2 = netService.distributeData("au9995");
		boolean isdone3 = netService.distributeData("au100g");
		boolean isdone4 = netService.distributeData("autd");
		boolean isdone5 = netService.distributeData("pt9995");
		//boolean isdone = netService.distributeData("autd");
		System.out.println("au9999:"+isdone1); 
		System.out.println("au9995:"+isdone2); 
		System.out.println("au100g:"+isdone3); 
		System.out.println("autd:"+isdone4); 
		System.out.println("pt9995:"+isdone5); 
	}

}
