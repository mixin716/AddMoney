package com.zc.addmony.ui.myproduct;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 忘记密码---设置新密码*/
public class SetNewPwdActivity extends BaseActivity{

	private EditText etFirst,etSecond;
	private Button btSure,btPopLogin;
	private TextView tvPop;
	private ImageView imgPop;
	private String pwdFirst,pwdSecond;
	private PopupWindow pop;
	private String banknum,idcard,phone,name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_set_new_pwd_layout);
		setViews();
	}
	
	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		banknum = this.getIntent().getStringExtra("banknum");
		idcard = this.getIntent().getStringExtra("idcard");
		phone = this.getIntent().getStringExtra("phone");
		name = this.getIntent().getStringExtra("name");
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("忘记密码");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etFirst = (EditText) findViewById(R.id.activity_set_new_pwd_et_first);
		etSecond = (EditText) findViewById(R.id.activity_set_new_pwd_et_second);
		btSure = (Button) findViewById(R.id.activity_set_new_pwd_bt_sure);
		
		btSure.setOnClickListener(this);
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
		pop.setOutsideTouchable(false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
		pop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

		btPopLogin = (Button) view
				.findViewById(R.id.pop_register_second_bt_login);// 直接登录
		btPopLogin.setOnClickListener(this);
		tvPop = (TextView) view.findViewById(R.id.pop_register_second_tv);
		imgPop = (ImageView) view.findViewById(R.id.pop_register_second_img);
		tvPop.setText("恭喜,密码修改成功,请重新登录");
		imgPop.setVisibility(View.VISIBLE);
	}

	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_set_new_pwd_bt_sure:
			KeyBoard.demissKeyBoard(getApplicationContext(), etFirst);
			pwdFirst = etFirst.getText().toString().trim();
			pwdSecond = etSecond.getText().toString().trim();
			if(checkTextEmpty(pwdFirst)){
				showToast("请输入正确密码");
			}else if(checkTextEmpty(pwdSecond)){
				showToast("请输入正确密码");
			}else if(!pwdFirst.equals(pwdSecond)){
				showToast("两次密码输入不一致");
			}else{
				sendPwd();
			}
			break;
		case R.id.pop_register_second_bt_login:
			this.setResult(101);
			this.finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		}
	}
	/** 判断字符串是否为空以及长度*/
	public boolean checkTextEmpty(String str){
		if(!TextUtils.isEmpty(str)){
			if(str.length() >=6 && str.length() <=8){
				return false;
			}else{
				return true;
			}
		}
		return true;
	}
	
	/** 修改密码*/
	public void sendPwd(){
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("newpwd", pwdFirst);
		params.put("banknum", banknum);
		params.put("idcard", idcard);
		params.put("phone", phone);
		params.put("name", name);
		httpRequest.post(Urls.CHANGE_PWD, params, callBack, 0);
	}
	
	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		PopWindMsg();
	}
}
