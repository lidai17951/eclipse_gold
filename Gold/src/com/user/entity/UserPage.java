package com.user.entity;

import java.util.List;

//��ҳ������
public class UserPage {
//	��ǰҳ��currentPage
//	ҳ���С��pageSize
//	��ǰҳ�����ݼ��� objList
//	������(����)��totalCount
//	��ҳ����totalPage  
	private int currentPage;
	private int pageSize;
	private List<Object> objList;
	private int totalCount;  //���ǵ�һ����ֵ
	private int totalPage;
	
	public UserPage() {//�޲ι���
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
	
	//	ע���Զ�������ҳ��(ע����õ�ʱ�������set������(����)����setҳ���С���ָ��)
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
	//ע����ҳ���Ǹ�������������ҳ���С�ó��ġ������ֶ���ֵ
//	public void setTotalPage(int totalPage) {
//		this.totalPage = totalPage;
//	}



	
}
