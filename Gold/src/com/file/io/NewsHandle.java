package com.file.io;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class NewsHandle {

	//把轮播图放入硬盘,成功返回true
	//dest:目的地,
	public boolean addRotate(String dest,FileItem fileIte) throws Exception {
		NewsHandle nHandle =new NewsHandle();
		boolean existFile = nHandle.isExistFile(dest);
		if (existFile) {//存在同名文件
			return false;
		}else {
			File destFile =new File(dest);
			fileIte.write(destFile);
			return true;
		}
	}
	
	//传入完整路径删除文件
	public void deleteFile(List<String>fileDest) {
		for (String destStr : fileDest) {
				File file =new File(destStr);
				if (this.isExistFile(destStr)) {
					file.delete();
				}
		}
	}
	
	//是否存在某个文件，存在返回true
	//fullPath:完整路径
	public boolean isExistFile(String fullPath) {
		File dest =new File(fullPath);
		if (dest.exists()) {//是否存在
			if (dest.isFile()) {//是否是文件
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}	
}
