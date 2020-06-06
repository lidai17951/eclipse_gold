package com.file.io;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class NewsHandle {

	//���ֲ�ͼ����Ӳ��,�ɹ�����true
	//dest:Ŀ�ĵ�,
	public boolean addRotate(String dest,FileItem fileIte) throws Exception {
		NewsHandle nHandle =new NewsHandle();
		boolean existFile = nHandle.isExistFile(dest);
		if (existFile) {//����ͬ���ļ�
			return false;
		}else {
			File destFile =new File(dest);
			fileIte.write(destFile);
			return true;
		}
	}
	
	//��������·��ɾ���ļ�
	public void deleteFile(List<String>fileDest) {
		for (String destStr : fileDest) {
				File file =new File(destStr);
				if (this.isExistFile(destStr)) {
					file.delete();
				}
		}
	}
	
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
}
