package com.file.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class FileHandle {
	// 一切操作都在D:/gold/文件夹下
	final static String SRC = "D:/gold/";

	// 传完整路径path,返回file对象.不用每次都new
	public static File fileObj(String path) {
		return new File(path);
	}

	// 返回"D:/gold/"下的所有文件夹或文件 名
	public static String[] getFolderName(String dest) {
		File src = fileObj(dest);
		return src.list();
	}

	// "D:/gold/"下是否存在指定 种类 文件 夹,有则返回true
	//完整格式应该如: D:/gold/au9999/2020/03-14.txt
	public static boolean existVarietyFolder(String variety) {
		 File fileObj = fileObj(SRC+variety);
		return fileObj.isDirectory();
	}
	
	// "D:/gold/"下是否存在指定 年份 文件 夹,有则返回true
	//完整格式应该如: D:/gold/au9999/2020/03-14.txt
	public static boolean existYearFolder(String variety,String year) {
		File fileObj = fileObj(SRC+variety+"/"+year);
		return fileObj.isDirectory();
	}



	// 创建txt文件 。
	//year文件夹名
	// monthAndDay文件名.成功返回true
	//packJson要写入文件的json数据
	//完整格式 如 D:/gold/2020/03-12.txt
	public static boolean createFile(String variety,String year,String monthAndDay,String hhmmss,JSONObject packJson) {
		boolean isDone = false;
		BufferedWriter bWriter=null;
		try {
			File dest = fileObj(SRC +variety+"/"+year+"/"+ monthAndDay + ".txt");
			 bWriter = new BufferedWriter(new FileWriter(dest,true));
			 	bWriter.write("---------"+hhmmss+"---------");
			 	bWriter.newLine();
				bWriter.write(packJson.toString());
				bWriter.newLine();
				bWriter.write("---------"+hhmmss+"---------");
			 	bWriter.newLine();
			 	bWriter.newLine();
				bWriter.flush();
				isDone = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (bWriter!=null) {
				try {
					bWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bWriter=null;
			}
		}
		return isDone;
	}

	// 在D:/gold/下创建以 种类 命名的文件夹.失败返回false(即已有同名目录)
	public static boolean createFolder(String variety,String year) {
		File folder = fileObj(SRC + variety+"/"+year);
		boolean isDone = folder.mkdirs();//创建目录，如果父目录不存在则一同创建。
		return isDone;
	}


}
