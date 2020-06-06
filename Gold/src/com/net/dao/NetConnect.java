package com.net.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import com.alibaba.fastjson.JSONObject;
/*	app	string	��	finance.shgold
	goldid	string	��	��ѯƷ�֣�����ö��Ÿ���(1051,1052,1053,1054),����: 
					1051:�ƽ�T+D 
					1052:����T+D 
					1053:�ƽ�9999 
					1054:�ƽ�9995 
					1055:����9999 
					1056:����9995 
					1057:����999 
					1058:����100g 
					1059:�ƽ�T+N1 
					1060:�ƽ�T+N2 
	(ע:���β�ѯ����ᰴ�������)
	version	int	��	�汾�� 1:�ɰ�(Ĭ��) 3:���°�
	appkey	string	��	ʹ��API��Ψһƾ֤ ��ȡ
	sign	string	��	md5���32λ����,��½��. ��ȡ
	format	{json|xml}	��	�������ݸ�ʽ
	jsoncallback	string	��	js����ʹ��jsonpʱ��ʹ�ô˲���
	�ҵĻ�ȡ��Ϣ:
	�ӿ���ַ
		http://api.k780.com
		https://sapi.k780.com
		
		(��������� SLA98.00%)
		AppKey
		46441
		
		Sign
		f2e5b387bd8f3f84bbfd8be8401f8736
		
		����! �������ɺ�sign������Ч����ע���޸����ϴ���.
��ʽ: 
http://api.k780.com/?app=finance.shgold&goldid=1051&version=3&appkey=APPKEY&sign=SIGN&format=json

�ҵ�:
http://api.k780.com/?app=finance.shgold&goldid=1053&version=3&appkey=46441&sign=f2e5b387bd8f3f84bbfd8be8401f8736&format=json

*/

public class NetConnect {

	
	public static String getData(String goldType) {
		int goldid=0;
		switch(goldType) {
		case"au9999":goldid =1053;break; //�ƽ�9999
		case"au9995":goldid =1054;break; //�ƽ�9995
		case"au100g":goldid =1058;break; //�ƽ�100��
		case"ag9999":goldid =1055;break; //����9999(�Ͻ�������������)
		case"ag999": goldid =1057;break; //����999(�Ͻ�������������)
		case"pt9995": goldid =1056;break; //����99.95 (PT9995)
		case"autd": goldid =1051;break; //�ƽ�T+D (AuT+D)���ݿ����autd
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
