package com.zc.addmony.common;

/** 管理url */
public class Urls {

	public static String url = "http://114.113.227.234";
	public static String common = "http://114.113.227.234/zcapi/index.php/Api/";
	/** 首页 */
	public static String HOME = common + "getIndexFund";
	/** 登录 */
	public static String LOGIN = common + "login?";
	/** 退出登录 */
	public static String LOGOUT = common + "logout";
	/** 发送验证码 */
	public static String CHECK_CODE = common + "sendMessage";
	/** 注册 */
	public static String REGISTER = common + "register";
	/** 找回密码 */
	public static String FIND_PWD = common + "resetPwd";
	/**  产品列表 */
	public static String PRODUCT_LIST = common + "getFundList";
	/**  产品列表 2*/
	public static String PRODUCT_LIST_TWO = common + "getFundlist2";
	/**  产品详情 */
	public static String PRODUCT_DETAIL = common + "getFundInfo";
	/** 获取基金起购金额*/
	public static String GET_PRODUCT_NUMBER = common +"getFundMinValue";
	/** 获取用户信息 */
	public static String GET_USER_INRO = common + "getUserInfo";
	/** 更改密码 */
	public static String CHANGE_PWD = common + "setNewTradePwd";
	/** 获取银行列表 */
	public static String GET_BANK_LIST = common + "getUserBankList"; 
	/** 解绑银行卡*/
	public static String REQUEST_UNBIND_BANK = common +"unbindBank";
	/** 验证小额打款金额 */
	public static String CHECK_TINY_PAY = common + "checkTinyPay";
	/** 设置交易密码 */
	public static String SET_BUY_PWD = common + "meropenacco";
	/** 得到基金支持银行列表 */
	public static String GET_FUND_BANKS = common + "getFundBankList";
	/** 充值 */
	public static String TBUYFUND = common + "tbuyFund";
	/** 购买基金 */
	public static String BUY_PRODUCT = common + "buyFund";
	/** 注册获取银行列表*/
	public static String GET_SUPPORT_BANKS = common +"getBankList";
	/** 银行开户手机号获取验证码*/
	public static String OPEN_BANK_CODE = common +"sendMessage";
	public static String OPEN_BANK_CODE_TWO = common +"sendMessage2";
	/** 建议验证码是否正确*/
	public static String OPEN_BANK_CHECK = common +"checkMessage";
	/** 开户验证*/
	public static String OPEN_CHECK= common +"meropenacco";
	/** 完善信息 */
	public static String PERFECT_INFORMATION = common + "apiDict";
	/** 验证开户银行卡并且进行小额打款 */
	public static String PERFECT_INFORMATION_NEXT = common + "checkAndPay";
	/** 得到资金变动列表 */
	public static String GET_MONEY_CHANGE = common + "getMoneyChageList";
	/** 増财宝详情*/
	public static String GET_ZCB_BUTTOM_INFO = common +"getTransactionList";
	/** 用户基金详情 */
	public static String GET_USER_FUND_INFO = common + "getUserFundInfo";
	/** 赎回 */
	public static String SALE_MONEY = common + "sale";
	/** 赎回获取银行卡*/
	public static String GETFundSharelist =common+ "getFundSharelist";
	/** 获取我的基金列表*/
	public static String GET_MY_BUY_ORDERS = common +"getUserOrderList";
	/** 获取手机类型 */
	public static String GET_PHONES = common + "getHdlist";
	/** 获取号码归属地 */
	public static String GET_ADDRESS = common + "getAreaList";
	/** 获取归属地号码*/
	public static String GET_PHONE_NUM = common + "showNumList";
	/** 提交活动详情*/
	public static String UPDATE_ACTIVITIES  = common +"insertOrder";
	/** 获取订单详情*/
	public static String GET_ORDER = common +"getOrderInfo";
	/** 活动查看基金份额*/
	public static String CHECK_TOTALS = common +"getFundShare";
	/** 注册时判断身份证号是否开过户*/
	public static String CHECK_IDCARD=common +"checkHundsunUser";
	/** 删除银行卡*/
	public static String DELETE_BANK = common+"deleteTradeacco";
	/** 当日交易申请列表*/
	public static String TODAY_DEAL_LIST = common+"getTodayTransactionList";
	/** 増财宝获取基金详情*/
	public static String ZCBINFO = common +"getZCBInfo";
	/** 添加银行卡*/
	public static String REQUEST_ADD_BANK = common +"addUserBank";
	/** 获取银行卡限额*/
	public static String GET_BANK_CODE = common +"getBankQuota";
}
