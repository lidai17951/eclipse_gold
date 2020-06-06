package com.news.entity;

import org.apache.commons.fileupload.FileItem;

public class RotateImg {
	private int rownum;
	private String newsid ;
	private String newstitle  ;
	private String imgurl   ;
	private FileItem fileItem;
	public RotateImg() {}
	public RotateImg(int rownum,String newsid, String newstitle, String imgurl,FileItem fileItem) {
		this.rownum=rownum;
		this.newsid = newsid;
		this.newstitle = newstitle;
		this.imgurl = imgurl;
		this.fileItem=fileItem;
	}
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getNewstitle() {
		return newstitle;
	}
	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public FileItem getFileItem() {
		return fileItem;
	}
	public void setFileItem(FileItem fileItem) {
		this.fileItem = fileItem;
	}
	
	
}
