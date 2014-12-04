package com.zc.addmony.bean.myproduct;

/** 交易支持银行bean*/
public class FundBankListBean {

	String bankname;// 银行名
	String tradeacco;// 交易账号
	String bankacco;// 银行卡号

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getTradeacco() {
		return tradeacco;
	}

	public void setTradeacco(String tradeacco) {
		this.tradeacco = tradeacco;
	}

	public String getBankacco() {
		return bankacco;
	}

	public void setBankacco(String bankacco) {
		this.bankacco = bankacco;
	}

}
