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
	 * 上传轮播图 最多10张 使用批量插入
	 */
	public String updateRotaleImg(List<RotateImg> imgEntityList) {
		SqlDaoImpl sqldDao = new SqlDaoImpl();
		int MaxCount = sqldDao.getCount("rotate_img");// 表里最多10张图片

		if (MaxCount + imgEntityList.size() <= 10) {
			try {
				String insertSQL = "insert into rotate_img(newsid,newstitle,imgurl) values(?,?,?) ";
				int[] count = sqldDao.batchInsert(insertSQL, imgEntityList);
				for (int i : count) {
					if (i != -2) {// -2代表sql执行成功
						return "上传失败";
					} else {
						for (RotateImg entity : imgEntityList) {
							NewsHandle newsHandle = new NewsHandle();
							newsHandle.addRotate(entity.getImgurl(), entity.getFileItem());
						}
						return "上传成功";
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				return "程序异常";
			}
		} else {
			return "最多10张图片,删除一些再上传";
		}
		return "上传失败";

	}

	// 获取所有的轮播图
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

	// 检查上传的图片是否合法
	public boolean checkImg(List<FileItem> items) {
		for (FileItem fileItem : items) {
			if (fileItem.isFormField()) {
				return false;
			} else {
				String imgName = fileItem.getName();// 这个获取的文件名是有后缀的
				imgName = imgName.replaceAll("\\s*", "");// 去除图片名称中的空格
				if (imgName.length() > 8) {// 图片名称太长则截短
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
			Object[] params = newsIdlList.toArray();// list转普通数组
			SqlDaoImpl sqlDao = new SqlDaoImpl();
			 rs = sqlDao.queryData(selectSql, params);
			 List<String> imgUrl =new ArrayList<String>(); //放入数据库查到的图片路径
			while (rs.next()) {
				imgUrl.add(rs.getString("imgurl"));
			}
			int deleteCount = sqlDao.addGoldInfo(deleteSql, params);
			if (deleteCount== newsIdlList.size()) {
				NewsHandle newsHandle =new NewsHandle();
				newsHandle.deleteFile(imgUrl);
				return "删除了"+deleteCount+"个";
			}else {
				return "删除了"+deleteCount+"个";
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


	
	// 模拟随机数10位,用来newsid和newstitle
	public String getRadom() {
		// 随机数
		String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz";
		StringBuffer value = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			value.append(chars.charAt((int) (Math.random() * 62)));
		}
		return value.toString();
	}
}
