package com.news.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.file.io.NewsHandle;
import com.news.entity.RotateImg;
import com.sql.dao.impl.JdbcTool;
import com.sql.dao.impl.SqlDaoImpl;

public class NewsService {

	/*
	 * �ϴ��ֲ�ͼ ���10�� ʹ����������
	 */
	public String updateRotaleImg(List<RotateImg> imgEntityList) {
		SqlDaoImpl sqldDao = new SqlDaoImpl();
		int MaxCount = sqldDao.getCount("rotate_img");// �������10��ͼƬ

		if (MaxCount + imgEntityList.size() <= 10) {
			try {
				String insertSQL = "insert into rotate_img(newsid,newstitle,imgurl) values(?,?,?) ";
				int[] count = sqldDao.batchInsert(insertSQL, imgEntityList);
				for (int i : count) {
					if (i != -2) {// -2����sqlִ�гɹ�
						return "�ϴ�ʧ��";
					} else {
						for (RotateImg entity : imgEntityList) {
							NewsHandle newsHandle = new NewsHandle();
							newsHandle.addRotate(entity.getImgurl(), entity.getFileItem());
						}
						return "�ϴ��ɹ�";
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				return "�����쳣";
			}
		} else {
			return "���10��ͼƬ,ɾ��һЩ���ϴ�";
		}
		return "�ϴ�ʧ��";

	}

	// ��ȡ���е��ֲ�ͼ
	public List<RotateImg> getAllRotateImg() {
		String sql = "select rownum,r.* from rotate_img r";
		SqlDaoImpl sqlDao = new SqlDaoImpl();
		ResultSet rs = sqlDao.queryData(sql, null);
		List<RotateImg> rList = new ArrayList<RotateImg>();
		try {
			while (rs.next()) {
				RotateImg imgEntity = new RotateImg();
				imgEntity.setRownum(rs.getInt("rownum"));
				imgEntity.setNewsid(rs.getString("newsid"));
				imgEntity.setNewstitle(rs.getString("newstitle"));
				String imgurl = rs.getString("imgurl");
				imgurl = imgurl.replaceAll("D:/gold", "/golddisk");
				imgEntity.setImgurl(imgurl);
				rList.add(imgEntity);
			}
			return rList;
		} catch (SQLException e) {
			e.printStackTrace();
			return rList = null;
		} catch (Exception e) {
			e.printStackTrace();
			return rList = null;
		} finally {
			JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
		}
	}

	// ����ϴ���ͼƬ�Ƿ�Ϸ�
	public boolean checkImg(List<FileItem> items) {
		for (FileItem fileItem : items) {
			if (fileItem.isFormField()) {
				return false;
			} else {
				String imgName = fileItem.getName();// �����ȡ���ļ������к�׺��
				imgName = imgName.replaceAll("\\s*", "");// ȥ��ͼƬ�����еĿո�
				if (imgName.length() > 8) {// ͼƬ����̫����ض�
					imgName = imgName.substring(imgName.lastIndexOf(".") - 2, imgName.length());
				}
				String suffix = imgName.substring(imgName.lastIndexOf(".") + 1, imgName.length()).toUpperCase();
				if (!("PNG".equals(suffix) || !"JPG".equals(suffix) || !"JPEG".equals(suffix))) {
					return false;
				}

			}
		}
		return true;
	}

	public String deleteRotate(List<String> newsIdlList) {
		ResultSet rs =null;
		try {
			String selectSql = "select newsid,imgurl from rotate_img where newsid=?";
			String deleteSql ="delete from rotate_img where newsid=?";
			for (int i = 0; i < newsIdlList.size(); i++) {
				if (i == 0) {
					continue;
				}
				deleteSql = deleteSql+ " or newsid=?";
				selectSql = selectSql + " or newsid=?";
			}
			Object[] params = newsIdlList.toArray();// listת��ͨ����
			SqlDaoImpl sqlDao = new SqlDaoImpl();
			 rs = sqlDao.queryData(selectSql, params);
			 List<String> imgUrl =new ArrayList<String>(); //�������ݿ�鵽��ͼƬ·��
			while (rs.next()) {
				imgUrl.add(rs.getString("imgurl"));
			}
			int deleteCount = sqlDao.addGoldInfo(deleteSql, params);
			if (deleteCount== newsIdlList.size()) {
				NewsHandle newsHandle =new NewsHandle();
				newsHandle.deleteFile(imgUrl);
				return "ɾ����"+deleteCount+"��";
			}else {
				return "ɾ����"+deleteCount+"��";
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}finally {
			JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
		}
	}


	
	// ģ�������10λ,����newsid��newstitle
	public String getRadom() {
		// �����
		String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz";
		StringBuffer value = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			value.append(chars.charAt((int) (Math.random() * 62)));
		}
		return value.toString();
	}
}
