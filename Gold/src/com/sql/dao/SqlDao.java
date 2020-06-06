package com.sql.dao;

import java.sql.ResultSet;

public interface  SqlDao {

	public  boolean queryExist(String sql,Object[] params);
	public int addGoldInfo(String sql,Object[] params);
	public ResultSet queryData(String sql, Object[] params);
	public int getCount(String tabName);
}
