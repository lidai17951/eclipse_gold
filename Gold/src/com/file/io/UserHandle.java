package com.file.io;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

public class UserHandle {

		
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
		
		/*传入头像路径,默认头像返回true
		 * oldPath: 数据库里存的旧地址
		 * newPath: 前端传的新地址
		 * 
		*/
		public boolean replaceImg(String oldPath,String newPath,FileItem fileIte) throws Exception {
			String defaultImg ="D:/gold/userHeadPhoto/default.jpg";//默认头像
			File newFile = new File(newPath);
			File oldFile = new File(oldPath); 
			if (defaultImg.equals(oldPath)) {
				fileIte.write(newFile);
				return true; //是默认头像,不删除
			}else {
				boolean deleteSuccess = oldFile.delete();
				if (deleteSuccess) {
					fileIte.write(newFile);
					return true; //删除成功
				}else {
					return false; //删除失败
				}
			}
		}
}
