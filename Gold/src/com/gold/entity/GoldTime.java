package com.gold.entity;

public class GoldTime {

	private String uptime; //yyyy-mm-dd hh:MM:ss
	private String year;
	private String monthAndDay;
	private String hhmmss;
	
	public GoldTime() {

	}
	public GoldTime(String uptime, String year, String monthAndDay, String hhmmss) {
		this.uptime = uptime;
		this.year = year;
		this.monthAndDay = monthAndDay;
		this.hhmmss = hhmmss;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonthAndDay() {
		return monthAndDay;
	}
	public void setMonthAndDay(String monthAndDay) {
		this.monthAndDay = monthAndDay;
	}
	public String getHhmmss() {
		return hhmmss;
	}
	public void setHhmmss(String hhmmss) {
		this.hhmmss = hhmmss;
	}
	@Override
	public String toString() {
		return "GoldTime [uptime=" + uptime + ", year=" + year + ", monthAndDay=" + monthAndDay + ", hhmmss=" + hhmmss
				+ "]";
	}

	
	
	
}
