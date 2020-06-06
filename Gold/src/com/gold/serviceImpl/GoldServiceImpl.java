package com.gold.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.file.io.JsonHandl;
import com.gold.entity.GoldEntity;
import com.gold.entity.GoldTime;
import com.gold.service.GoldService;
import com.sql.dao.impl.JdbcTool;
import com.sql.dao.impl.SqlDaoImpl;
import com.sun.org.apache.bcel.internal.generic.Select;

public class GoldServiceImpl implements GoldService {

	// ��ȡau9999�������е�����
	public List<GoldEntity> getAu9999All() {
		String sql = "select * from au9999 order by uptime";
		SqlDaoImpl goldDao = new SqlDaoImpl();
		ResultSet rs = goldDao.queryData(sql, null);
		return dbaToBean(rs);
	}

	/*��ȡgoldVariety���е�rownum,uptime,volume�С�
	 * ��ʱ��Ľ���
	 * howRow ָ�����ؼ���*/	
	public List<GoldEntity> getVolume(int howRow,String goldVariety) {
		String sql = "select rownum,a.uptime,a.volume from"
				+ "(select uptime,volume from "+goldVariety+" order by uptime desc) a "
				+ "where rownum<=?";
		 Object[] params = {howRow};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		ResultSet rs = goldDao.queryData(sql, params);
		return dbaToBean(rs);
	}
	
	/*��ȡ���������۰�ʱ��Ľ���
	  howRow ָ�����ؼ���*/
	public List<GoldEntity> getDeal(int howRow,String goldVariety) {
		String sql = "select rownum,a.uptime,a.buy_price,a.sell_price from"
				+ "(select uptime,buy_price,sell_price from "+goldVariety+" order by uptime desc) a "
				+ "where rownum<=?";
		 Object[] params = {howRow};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		ResultSet rs = goldDao.queryData(sql, params);
		return dbaToBean(rs);
	}
	
	
	/*��ȡMetal������±���*/
	public List<GoldEntity> getMetal() {
		String sql ="select * from metal ";
		//Object[] params = {goldVariety};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		ResultSet rs = goldDao.queryData(sql, null);
		return dbaToBean(rs);
	}

	
	/*��ȡgoldVariety���howRow�е�last_price��uptime��change_margin(�ǵ���)*/
	public List<GoldEntity> getLastArr(int howRow,String goldVariety){
		
		String sql = "select rownum,a.uptime,a.last_price,a.change_margin from"
				+ "(select uptime,last_price,change_margin from "+goldVariety+" order by uptime desc) a "
				+ "where rownum<=?";
		 Object[] params = {howRow};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		goldDao.queryData(sql, params);
		ResultSet rs = goldDao.queryData(sql, params);
		return dbaToBean(rs);
	}
	
	
	// (����) ����resultsetת��bean,����list
	public static List<GoldEntity> dbaToBean(ResultSet rs) {
		List<GoldEntity> glist = new ArrayList<GoldEntity>();
		try {
			while (rs.next()) {
				GoldEntity gEntity = new GoldEntity();
				GoldTime gTime = new GoldTime();
				if (isExistColumn(rs, "variety")) {
					gEntity.setVariety(rs.getString("variety"));
				}
				if (isExistColumn(rs, "last_price")) {
					gEntity.setLast_price(rs.getString("last_price"));
				}
				if (isExistColumn(rs, "buy_price")) {
					gEntity.setBuy_price(rs.getString("buy_price"));
				}
				if (isExistColumn(rs, "sell_price")) {
					gEntity.setSell_price(rs.getString("sell_price"));
				}
				if (isExistColumn(rs, "volume")) {
					gEntity.setVolume(rs.getString("volume"));
				}
				if (isExistColumn(rs, "change_price")) {
					gEntity.setChange_price(rs.getString("change_price"));
				}
				if (isExistColumn(rs, "change_margin")) {
					gEntity.setChange_margin(rs.getString("change_margin"));
				}
				if (isExistColumn(rs, "high_price")) {
					gEntity.setHigh_price(rs.getString("high_price"));
				}
				if (isExistColumn(rs, "low_price")) {
					gEntity.setLow_price(rs.getString("low_price"));
				}
				if (isExistColumn(rs, "open_price")) {
					gEntity.setOpen_price(rs.getString("open_price"));
				}
				if (isExistColumn(rs, "yesy_price")) {
					gEntity.setYesy_price(rs.getString("yesy_price"));
				}
				if (isExistColumn(rs, "uptime")) {
					String uptime = rs.getString("uptime");// ʱ�����������
					gTime.setUptime(uptime);
					String year = JsonHandl.getYear(uptime);
					String monthAndDay = JsonHandl.getMonthAndDay(uptime);
					String hhmmss = JsonHandl.gethhmmss(uptime);
					gTime.setYear(year);
					gTime.setMonthAndDay(monthAndDay);
					gTime.setHhmmss(hhmmss);
					gEntity.setGoldTime(gTime);
				}

				glist.add(gEntity);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
		}

		return glist;
	}
	

	// ����dbaToBean,�ж�rs�Ƿ����ָ����,���򷵻�true
	public static Boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			if (rs.findColumn(columnName) > 0) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/* 
	public static void main(String[] args) {
		GoldServiceImpl gs = new GoldServiceImpl();
		List<GoldEntity> au9999All = gs.getAu9999All();
		List<GoldEntity> volume = gs.getVolume(6);
		for (GoldEntity goldEntity : volume) {
			// System.out.println(++i+"/"+goldEntity.getGoldTime().getUptime());

			// javabeanתjson�ַ���
			String str = JSON.toJSONString(goldEntity);
			JSONObject jObj = (JSONObject) JSONObject.parse(str);
			JSONObject jTime = (JSONObject) jObj.get("goldTime");
			System.out.println( jObj);
			
		}
		System.out.println(volume.get(0));//��ȡlist�е�һ������
		System.out.println(volume.size()); //�鿴list���м���Ԫ��
	}*/
	
}
