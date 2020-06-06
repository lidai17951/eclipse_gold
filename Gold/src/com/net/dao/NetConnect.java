package com.net.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import com.alibaba.fastjson.JSONObject;
/*	app	string	是	finance.shgold
	goldid	string	是	查询品种，多个用逗号隔开(1051,1052,1053,1054),包含: 
					1051:黄金T+D 
					1052:白银T+D 
					1053:黄金9999 
					1054:黄金9995 
					1055:白银9999 
					1056:铂金9995 
					1057:白银999 
					1058:金条100g 
					1059:黄金T+N1 
					1060:黄金T+N2 
	(注:单次查询多个会按多个扣量)
	version	int	是	版本号 1:旧版(默认) 3:最新版
	appkey	string	是	使用API的唯一凭证 获取
	sign	string	是	md5后的32位密文,登陆用. 获取
	format	{json|xml}	否	返回数据格式
	jsoncallback	string	否	js跨域使用jsonp时可使用此参数
	我的获取信息:
	接口网址
		http://api.k780.com
		https://sapi.k780.com
		
		(共享服务器 SLA98.00%)
		AppKey
		46441
		
		Sign
		f2e5b387bd8f3f84bbfd8be8401f8736
		
		警告! 重新生成后sign立即生效，请注意修改线上代码.
格式: 
http://api.k780.com/?app=finance.shgold&goldid=1051&version=3&appkey=APPKEY&sign=SIGN&format=json

我的:
http://api.k780.com/?app=finance.shgold&goldid=1053&version=3&appkey=46441&sign=f2e5b387bd8f3f84bbfd8be8401f8736&format=json

*/

public class NetConnect {

	
	public static String getData(String goldType) {
		int goldid=0;
		switch(goldType) {
		case"au9999":goldid =1053;break; //黄金9999
		case"au9995":goldid =1054;break; //黄金9995
		case"au100g":goldid =1058;break; //黄金100克
		case"ag9999":goldid =1055;break; //白银9999(上金交易所不更新了)
		case"ag999": goldid =1057;break; //白银999(上金交易所不更新了)
		case"pt9995": goldid =1056;break; //铂金99.95 (PT9995)
		case"autd": goldid =1051;break; //黄金T+D (AuT+D)数据库表名autd
		}
		String urlAddress="http://api.k780.com/?app=finance.shgold&goldid="+goldid+"&version=3&appkey=46441&sign=f2e5b387bd8f3f84bbfd8be8401f8736&format=json";
		URL url=null;
		String str=null;
		try {
			url = new URL(urlAddress);
		
		InputStream in =url.openStream();
		ByteArrayOutputStream bout =new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len=-1;
		while ((len=in.read(buf))!=-1) {
			bout.write(buf,0,len);
		}
			bout.flush();
			if (in!=null) {
				in.close();
			}
			byte[] b=bout.toByteArray();
			str = new String(b,"utf-8");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
		return str;
	}
	

}
