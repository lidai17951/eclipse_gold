package com.gold.service;

import java.util.List;

import com.gold.entity.GoldEntity;

public interface GoldService {
	//��ȡau9999�������е�����
	public List<GoldEntity> getAu9999All(); 
	//��ȡau9999���е�rownum,uptime,volume�С���ʱ��Ľ���
	//howRow ָ�����ؼ���
	public List<GoldEntity> getVolume(int howRow,String goldVariety); 
	public List<GoldEntity> getMetal();
	 
}
