package com.user.entity;

public class UserInfo {
	private int rownum;
	private String userId;
	private String uname;
	private String upwd;
	private String upower;
	private String createTime ;  //用户创建时间,只能查询,不可插入
	private String headurl;
	
	public UserInfo(int rownum,String userId, String uname, String upwd, String upower,String headurl, String createTime) {
		this.rownum =rownum;
		this.userId = userId;
		this.uname = uname;
		this.upwd = upwd;
		this.upower = upower;
		this.headurl=headurl;
		this.createTime = createTime;
	}
	
	public UserInfo() {

	}

	
	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getUpower() {
		return upower;
	}

	public void setUpower(String upower) {
		this.upower = upower;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
