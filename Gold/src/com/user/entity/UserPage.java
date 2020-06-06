package com.user.entity;

import java.util.List;

//分页帮助类
public class UserPage {
//	当前页：currentPage
//	页面大小：pageSize
//	当前页的数据集合 objList
//	总数据(几条)：totalCount
//	总页数：totalPage  
	private int currentPage;
	private int pageSize;
	private List<Object> objList;
	private int totalCount;  //总是第一个赋值
	private int totalPage;
	
	public UserPage() {//无参构造
		}
	
	
	public UserPage(int currentPage, int pageSize, List<Object> objList, int totalCount, int totalPage) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.objList = objList;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	
	//	注：自动生成总页数(注意调用的时候必须先set总数据(几条)。先set页面大小会空指针)
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (this.totalCount%this.pageSize==0) {
			this.totalPage=this.totalCount/this.pageSize;
		}else {
			this.totalPage=this.totalCount/this.pageSize+1;
		}
	}
	public List<Object> getobjList() {
		return objList;
	}
	public void setobjList(List<Object> objList) {
		this.objList = objList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	//注：总页数是根据数据总数和页面大小得出的。无需手动赋值
//	public void setTotalPage(int totalPage) {
//		this.totalPage = totalPage;
//	}



	
}
