package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.bean.myproduct.OpenBankBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.ui.buyproduct.PerfectInformationActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;
import com.zc.addmony.utils.PatternUtil;

/** 注册第一步 输入姓名身份证号 */
public class RegisterSecondActivity extends BaseActivity {

	private MApplication mApplication;
	private OpenBankBean obBean;
	private Button btNext, btPopLogin;
	private EditText etName, etIdCard;
	private String strName, strIdCard;
	private PopupWindow pop;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_register_second_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		mApplication = (MApplication) this.getApplication();
		mApplication.addAllActivity(this);
		obBean = mApplication.getObBean();
	}

	/** 初始化popwindow */
	public void PopWindMsg() {
		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.pop_register_second_layout, null);
		// 创建PopupWindow对象
		pop = new PopupWindow(view, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
		pop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

		btPopLogin = (Button) view
				.findViewById(R.id.pop_register_second_bt_login);// 直接登录
		btPopLogin.setOnClickListener(this);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("注册");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etName = (EditText) findViewById(R.id.activity_register_second_et_name);
		etIdCard = (EditText) findViewById(R.id.activity_register_second_et_idcode);
		btNext = (Button) findViewById(R.id.activity_register_second_bt_next);

		btNext.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(RegisterSecondActivity.this);
			break;
		case R.id.activity_register_second_bt_next:
			// PopWindMsg();
			// Intent intent = new Intent(this,RegisterSuccessActivity.class);
			// startActivity(intent);
			KeyBoard.demissKeyBoard(getApplicationContext(), etName);
			strName = etName.getText().toString().trim();
			strIdCard = etIdCard.getText().toString().trim();
			if (TextUtils.isEmpty(strName)) {
				showToast("请输入姓名");
			} else if (TextUtils.isEmpty(strIdCard)) {
				showToast("请输入身份证号");
			} else if (!PatternUtil.patternIdCard(strIdCard)) {
				showToast("请输入合理地身份证号");
			} else {
				obBean.setName(strName);
				obBean.setIdcard(strIdCard);
				mApplication.setObBean(obBean);
				sendCheck();
			}
			break;
		case R.id.pop_register_second_bt_login:
			if (pop != null) {
				pop.dismiss();
			}

			this.finish();
			break;
		}
	}

	/** 检验该账号是否已经注册 */
	public void sendCheck() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("idcard", strIdCard);
		httpRequest.get(Urls.CHECK_IDCARD, params, callBack, 0);
	}

	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			// showToast(baseBean.getMessage());
			PopWindMsg();
			break;
		default:// 请求失败
			intent = new Intent(this, PerfectInformationActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;
		}
	}

}
