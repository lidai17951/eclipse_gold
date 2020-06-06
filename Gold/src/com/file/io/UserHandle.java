package com.file.io;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

public class UserHandle {

		
		//�Ƿ����ĳ���ļ������ڷ���true
		//fullPath:����·��
		public boolean isExistFile(String fullPath) {
			File dest =new File(fullPath);
			if (dest.exists()) {//�Ƿ����
				if (dest.isFile()) {//�Ƿ����ļ�
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}	
		
		/*����ͷ��·��,Ĭ��ͷ�񷵻�true
		 * oldPath: ���ݿ����ľɵ�ַ
		 * newPath: ǰ�˴����µ�ַ
		 * 
		*/
		public boolean replaceImg(String oldPath,String newPath,FileItem fileIte) throws Exception {
			String defaultImg ="D:/gold/userHeadPhoto/default.jpg";//Ĭ��ͷ��
			File newFile = new File(newPath);
			File oldFile = new File(oldPath); 
			if (defaultImg.equals(oldPath)) {
				fileIte.write(newFile);
				return true; //��Ĭ��ͷ��,��ɾ��
			}else {
				boolean deleteSuccess = oldFile.delete();
				if (deleteSuccess) {
					fileIte.write(newFile);
					return true; //ɾ���ɹ�
				}else {
					return false; //ɾ��ʧ��
				}
			}
		}
}
