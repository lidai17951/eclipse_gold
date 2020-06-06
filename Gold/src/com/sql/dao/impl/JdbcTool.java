package com.sql.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTool {
	public static Connection conn = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null; // 加public 和static，方便在另一个类里调用(关闭)
	/*
	 * 把DrvierManaer 和 Connection抽离出来做static类
	 */
	public static Connection getConn() {
		String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(URL, "web_gold", "tiger");
		} catch (SQLException e) {// 注意：SQLException 比 ClassNotFoundException 小
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static PreparedStatement getPreparedStatement(String sql,Object[] params) {
		conn = getConn();
		try {
			pstmt=conn.prepareStatement(sql);
			if (params!=null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i+1, params[i]);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public static ResultSet getResultSet(String sql,Object[] params) {
		try {
			rs = getPreparedStatement(sql, params).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	

	public static void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (psmt != null) {
				psmt.close();
				psmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
