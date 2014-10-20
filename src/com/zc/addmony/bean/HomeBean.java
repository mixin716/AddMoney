package com.zc.addmony.bean;

import java.util.List;

public class HomeBean {
	private List<PaimingBean> paiming;
	private String beishu;
	private TuijianBean tuijian;
	public List<PaimingBean> getPaiming() {
		return paiming;
	}
	public void setPaiming(List<PaimingBean> paiming) {
		this.paiming = paiming;
	}
	public String getBeishu() {
		return beishu;
	}
	public void setBeishu(String beishu) {
		this.beishu = beishu;
	}
	public TuijianBean getTuijian() {
		return tuijian;
	}
	public void setTuijian(TuijianBean tuijian) {
		this.tuijian = tuijian;
	}

}
