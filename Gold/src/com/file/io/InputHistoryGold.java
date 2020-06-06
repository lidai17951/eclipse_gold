package com.file.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class InputHistoryGold {
	/**获取某一年的所有历史数据
	 * @param variery 黄金品种
	 * @param year 要查询的年份
	 */
	public StringBuilder getAllYearData(String variery,String year) {
		File src = new File("D:/gold/"+variery+"/"+year);
		File[] fileArr = src.listFiles();
		BufferedReader bfr = null;
		StringBuilder sBuilder = new StringBuilder();
		try {
			Pattern timestamp =Pattern.compile("-+\\d{2}:{1}\\d{2}:{1}\\d{2}-+");  
			for (File file : fileArr) {
				bfr=new BufferedReader(new FileReader(file));
				String str =null;
				int evenNum=1;
				while ((str=bfr.readLine())!=null) {
				Matcher tMatcher =timestamp.matcher(str);
					if (tMatcher.find()) {
						if (evenNum%2==0) {
							sBuilder.append(",");
							evenNum++;
						}else {
							evenNum++;
							continue;
						}
					}else {
						sBuilder.append(str);
					}
				}
			}
			sBuilder.insert(0, "[");
			sBuilder.append("]");
			

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if (bfr!=null) {
				try {
					bfr.close();
					bfr=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sBuilder;		
	}
	

	
	public static void main(String[] args) {
		InputHistoryGold i = new InputHistoryGold();
		//i.formatData("au9999", "2020");
		 StringBuilder allYearData = i.getAllYearData("au9999", "2020");
		 JSONArray jArr = JSONArray.parseArray(allYearData.toString());
		 JSONArray jResult = new JSONArray();
		for (int j = 0; j < jArr.size(); j++) {
			JSONObject jObj = (JSONObject) jArr.get(j);
			jResult.add(jObj.get("result"));	
		}
		for (Object jObj : jResult) {
			System.out.println(jObj);
		}
	}
}
