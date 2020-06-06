package com.file.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class FileHandle {
	// һ�в�������D:/gold/�ļ�����
	final static String SRC = "D:/gold/";

	// ������·��path,����file����.����ÿ�ζ�new
	public static File fileObj(String path) {
		return new File(path);
	}

	// ����"D:/gold/"�µ������ļ��л��ļ� ��
	public static String[] getFolderName(String dest) {
		File src = fileObj(dest);
		return src.list();
	}

	// "D:/gold/"���Ƿ����ָ�� ���� �ļ� ��,���򷵻�true
	//������ʽӦ����: D:/gold/au9999/2020/03-14.txt
	public static boolean existVarietyFolder(String variety) {
		 File fileObj = fileObj(SRC+variety);
		return fileObj.isDirectory();
	}
	
	// "D:/gold/"���Ƿ����ָ�� ��� �ļ� ��,���򷵻�true
	//������ʽӦ����: D:/gold/au9999/2020/03-14.txt
	public static boolean existYearFolder(String variety,String year) {
		File fileObj = fileObj(SRC+variety+"/"+year);
		return fileObj.isDirectory();
	}



	// ����txt�ļ� ��
	//year�ļ�����
	// monthAndDay�ļ���.�ɹ�����true
	//packJsonҪд���ļ���json����
	//������ʽ �� D:/gold/2020/03-12.txt
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

	// ��D:/gold/�´����� ���� �������ļ���.ʧ�ܷ���false(������ͬ��Ŀ¼)
	public static boolean createFolder(String variety,String year) {
		File folder = fileObj(SRC + variety+"/"+year);
		boolean isDone = folder.mkdirs();//����Ŀ¼�������Ŀ¼��������һͬ������
		return isDone;
	}


}
