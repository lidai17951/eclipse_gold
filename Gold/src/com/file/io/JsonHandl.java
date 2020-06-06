package com.file.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.gold.entity.GoldEntity;
import com.gold.entity.GoldTime;


public class JsonHandl {

	/*�ⲿ����StringBuilder,���в���*/
	
	//�ַ�����װ��json
	public static JSONObject packJson(String str) {
		JSONObject jObject = (JSONObject) JSONObject.parse(str);
		return jObject;
	}
	
	
	public static GoldEntity jsonToBean(JSONObject jObject) {
		JSONObject goldInfo =jObject.getJSONObject("result");
	//	JSONObject goldInfo = resultJson.getJSONObject("1051");
		
		GoldEntity gEntity =new GoldEntity();
		GoldTime gTime = new GoldTime();
		gEntity.setGoldid((String)goldInfo.get("goldid"));
		gEntity.setVariety((String)goldInfo.get("variety"));
		gEntity.setVarietynm((String)goldInfo.get("varietynm"));
		gEntity.setLast_price((String)goldInfo.get("last_price"));
		gEntity.setBuy_price((String)goldInfo.get("buy_price"));
		gEntity.setSell_price((String)goldInfo.get("sell_price"));
		gEntity.setVolume((String)goldInfo.get("volume"));
		gEntity.setChange_price((String)goldInfo.get("change_price"));
		gEntity.setChange_margin((String)goldInfo.get("change_margin"));
		gEntity.setHigh_price((String)goldInfo.get("high_price"));
		gEntity.setLow_price((String)goldInfo.get("low_price"));
		gEntity.setOpen_price((String)goldInfo.get("open_price"));
		gEntity.setYesy_price((String)goldInfo.get("yesy_price"));
		//�ֽ�json��ʱ��
		String uptime = (String) goldInfo.get("uptime");
		String year = getYear(uptime);
		String monthAndDay = getMonthAndDay(uptime);
		String gethhmmss = gethhmmss(uptime);
		gTime.setUptime(uptime);
		gTime.setYear(year);
		gTime.setMonthAndDay(monthAndDay);
		gTime.setHhmmss(gethhmmss);
		gEntity.setGoldTime(gTime);
		 return gEntity;
	}
	
	//����������uptime,��:String str ="2020-03-12 02:29:53";
	public static String getYear(String uptime) {
		Pattern pattern1 = Pattern.compile("^\\d{4}");
		Matcher matcher1 =pattern1.matcher(uptime);
		String year =null;
		if (matcher1.find()) {
			year = matcher1.group();
		}
		return year; //ʹ��ǰ��Ҫ�п�,����2020��ʽ
	}

	//����������uptime,��:String str ="2020-03-12 02:29:53";
	public static String getMonthAndDay(String uptime) {
		Pattern pattern2 = Pattern.compile("\\d{2}[-]\\d{2}\\s{1}");
		Matcher matcher2 =pattern2.matcher(uptime);
		String monthAndDay=null;
		if (matcher2.find()) {
			monthAndDay = matcher2.group();
		}
		return monthAndDay;//ʹ��ǰ��Ҫ�п�,����03-12��ʽ
	}
	
	//����������uptime,��:String str ="2020-03-12 02:29:53";
	public static String gethhmmss(String uptime) {
		Pattern pattern3 = Pattern.compile("\\d{2}[:]\\d{2}[:]\\d{2}$");
		String hhmmss=null;
		Matcher matcher3 =pattern3.matcher(uptime);
		if (matcher3.find()) {
			hhmmss = matcher3.group();
		}
		return hhmmss;//ʹ��ǰ��Ҫ�п�,����02:29:53��ʽ
	}
}
