package com.gold.service;

import java.util.List;

import com.gold.entity.GoldEntity;

public interface GoldService {
	//获取au9999表中所有的数据
	public List<GoldEntity> getAu9999All(); 
	//获取au9999表中的rownum,uptime,volume列。按时间的降序
	//howRow 指定返回几行
	public List<GoldEntity> getVolume(int howRow,String goldVariety); 
	public List<GoldEntity> getMetal();
	 
}
