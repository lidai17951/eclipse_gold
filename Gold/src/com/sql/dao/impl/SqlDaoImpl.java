package com.sql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import com.news.entity.RotateImg;
import com.sql.dao.SqlDao;


public class SqlDaoImpl implements SqlDao {

	
	
	/* 查询某个列是否存在,存在返回true */
	public boolean queryExist(String sql, Object[] params) {
		ResultSet rs = JdbcTool.getResultSet(sql, params);
		try {
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
		}
		return false;
	}
		//增加和更新都可以用
	public int addGoldInfo(String sql, Object[] params) {
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			pstmt = JdbcTool.getPreparedStatement(sql, params);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTool.close(null, pstmt, JdbcTool.conn);
		}
		return count;

	}
	

		//批量插入轮播图
		public int[] batchInsert(String sql,List<RotateImg> rotateImg) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn=JdbcTool.getConn();
				conn.setAutoCommit(false);//关闭自动commit
				pstmt =conn.prepareStatement(sql);
				for (RotateImg imgEienty : rotateImg) {
					pstmt.setObject(1, imgEienty.getNewsid());
					pstmt.setObject(2, imgEienty.getNewstitle());
					pstmt.setObject(3, imgEienty.getImgurl());	
					pstmt.addBatch();
				}
				 int[] executeBatch = pstmt.executeBatch();//批量插入
				conn.commit();
				return executeBatch;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JdbcTool.close(null, pstmt, conn);
			}
			return null;
		}
	
	
	//注意ResultSet到service里抽取数据再关
	public ResultSet queryData(String sql, Object[] params) {
		ResultSet resultSet = JdbcTool.getResultSet(sql, params);
		return resultSet;
	}

	/*查询一个表有几条数据 用于分页
	 * */
	//tabName:表名
	public int getCount(String tabName) {
		int tabCount=0;
		SqlDaoImpl gdi = new SqlDaoImpl();
		String sql ="select count(1) from "+tabName;
		ResultSet rs = gdi.queryData(sql, null);
			try {
				if (rs.next()) {
					tabCount = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return tabCount;	
			}finally {
				JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
			}
		return tabCount;	
	}
	


}
