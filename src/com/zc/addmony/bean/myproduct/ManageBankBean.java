package com.zc.addmony.bean.myproduct;

import java.util.List;

/** 银行列表bean */
public class ManageBankBean {

	int flag;// 是否有银行卡 0没有
	String bankName;// 银行名字
	String bankFlag;// 银行标示
	String bandLimit;// 限额
	String bank_num;// 银行卡号
	String bankfullname;// 支行名字
	String bankacconame;// 开户名字
	String idno;// 身份证号
	String bankserial;// 银行编号
	String tradeacco;// 支付用到
	String quota;// 限额
	List<String> limits;

	public String getBankserial() {
		return bankserial;
	}

	public void setBankserial(String bankserial) {
		this.bankserial = bankserial;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}

	public String getBandLimit() {
		return bandLimit;
	}

	public void setBandLimit(String bandLimit) {
		this.bandLimit = bandLimit;
	}

	public String getBank_num() {
		return bank_num;
	}

	public void setBank_num(String bank_num) {
		this.bank_num = bank_num;
	}

	public String getBankfullname() {
		return bankfullname;
	}

	public void setBankfullname(String bankfullname) {
		this.bankfullname = bankfullname;
	}

	public String getBankacconame() {
		return bankacconame;
	}

	public void setBankacconame(String bankacconame) {
		this.bankacconame = bankacconame;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getTradeacco() {
		return tradeacco;
	}

	public void setTradeacco(String tradeacco) {
		this.tradeacco = tradeacco;
	}

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

	public List<String> getLimits() {
		return limits;
	}

	public void setLimits(List<String> limits) {
		this.limits = limits;
	}

}
