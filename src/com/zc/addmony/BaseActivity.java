package com.zc.addmony;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.jky.struct2.http.FinalHttp;
import com.jky.struct2.http.core.AjaxCallBack;
import com.jky.struct2.http.entityhandle.HttpExceptionResult;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.common.Constants;
import com.zc.addmony.logic.LogicBase;

public abstract class BaseActivity extends Activity implements OnClickListener {

	protected ImageView titleIvLeft;
	protected ImageView titleIvRight;
	protected TextView titleText;
	protected ViewGroup titleLayout;
	protected ViewGroup topLayout;
	protected ViewGroup contentLayout;
	protected ViewGroup bottomLayout;
	protected ViewGroup curContent;
	protected ViewGroup netErrorLayout;
	protected ViewGroup loadingLayout;

	protected ImageView ivNetError;
	protected TextView tvNetError;
	protected Button btnNetError;
	protected TextView tvHint;
	protected Toast toast;
	protected FinalHttp httpRequest;
	protected boolean[] isRequesting = { false, false, false, false, false,
			false };
	protected LayoutInflater mInflater;
	private static int width, height;
	private MarginLayoutParams mp;
	private LinearLayout.LayoutParams lp;
	public MApplication mApplication;

	private BroadcastReceiver receiverFinish = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			onReceiverFinish(intent);

		}

	};

	protected AjaxCallBack<HttpResult> callBack = new AjaxCallBack<HttpResult>() {
		@Override
		public void onSuccess(HttpResult httpResult) {
			dismissLoading();
			System.out.println("---------请求成功---------->>");
			handleResult(httpResult.which, httpResult);
		};

		@Override
		public void onFailure(int which, HttpExceptionResult result) {
			dismissLoading();
			System.out.println("---------请求失败-------->>");
			handleNetErr(which, result.code);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base_layout);
		registerReceiver(receiverFinish, new IntentFilter(
				Constants.INTENT_ACTION_FINISH_ALL));
		httpRequest = new FinalHttp(this);
		getWindowHW();
		initVariable();
		initViews();
		setTitleViews();
		mApplication = (MApplication) getApplication();
	};

	/**
	 * 获取手机分辨率
	 */
	public void getWindowHW() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	private void initViews() {
		titleLayout = (ViewGroup) findViewById(R.id.page_title);
		mp = new MarginLayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				(int) (height * 0.0875));
		lp = new LinearLayout.LayoutParams(mp);
		titleLayout.setLayoutParams(lp);
		titleIvLeft = (ImageView) titleLayout.findViewById(R.id.title_iv_left);
		titleIvRight = (ImageView) titleLayout
				.findViewById(R.id.title_iv_right);
		titleText = (TextView) titleLayout.findViewById(R.id.title_tv_text);
		topLayout = (ViewGroup) findViewById(R.id.page_top);
		bottomLayout = (ViewGroup) findViewById(R.id.page_bottom);
		contentLayout = (ViewGroup) findViewById(R.id.page_content);
		netErrorLayout = (ViewGroup) findViewById(R.id.page_net_error);
		loadingLayout = (ViewGroup) findViewById(R.id.page_loading);

		ivNetError = (ImageView) findViewById(R.id.activity_net_error_img);
		tvNetError = (TextView) findViewById(R.id.activity_net_error_text);
		btnNetError = (Button) findViewById(R.id.activity_net_error_btn);

		tvHint = (TextView) findViewById(R.id.page_tv_hint);

		titleIvLeft.setOnClickListener(this);
		titleIvRight.setOnClickListener(this);
		btnNetError.setOnClickListener(this);
		netErrorLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.GONE);
		tvHint.setVisibility(View.GONE);

		mInflater = LayoutInflater.from(this);
	}

	protected abstract void initVariable();

	protected void setTopViewRes(int layoutRes) {
		topLayout.removeAllViews();
		ViewGroup topView = (ViewGroup) mInflater.inflate(layoutRes, null);
		topLayout.addView(topView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	/**
	 * 取消标题
	 */
	protected void dismissTop() {
		if (titleLayout != null) {
			titleLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置布局
	 * 
	 * @param layoutRes
	 */
	protected void setContentViewRes(int layoutRes) {
		contentLayout.removeAllViews();
		curContent = (ViewGroup) mInflater.inflate(layoutRes, null);
		contentLayout.addView(curContent, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	protected void setContentViewRes(ViewGroup viewGroup) {
		contentLayout.removeAllViews();
		curContent = viewGroup;
	}

	/**
	 * 设置底部布局
	 * 
	 * @param layoutRes
	 */
	protected void setBottomViewRes(int layoutRes) {
		bottomLayout.removeAllViews();
		ViewGroup bottomView = (ViewGroup) mInflater.inflate(layoutRes, null);
		bottomLayout.addView(bottomView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	protected abstract void setTitleViews();

	protected abstract void setViews();

	protected void handleResult(int requestCode, HttpResult result) {
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			handleJson(requestCode, baseBean.getContent(),
					baseBean.getMessage());
			System.out.println("-----baseBean.getData():------"
					+ baseBean.getContent());
			break;
		default:// 请求失败
			showToast(baseBean.getMessage());
			break;
		// case 400:// ���Դ�����ʾ
		// handleResult400(requestCode, baseBean.getMsg());
		// break;
		// case 5000:// ��Ҫ����
		// // showToast(baseBean.getMsg());
		// tvHint.setVisibility(View.VISIBLE);
		// tvHint.setText(baseBean.getMsg());
		// contentLayout.setVisibility(View.GONE);
		// break;
		// case 5001:// �ӿڷ���
		// tvHint.setVisibility(View.VISIBLE);
		// tvHint.setText(baseBean.getMsg());
		// contentLayout.setVisibility(View.GONE);
		// break;
		// case -1:// json��ݴ��󣬽���ʧ��
		// tvHint.setVisibility(View.VISIBLE);
		// tvHint.setText("");
		// contentLayout.setVisibility(View.GONE);
		//
		// // String url = String.valueOf(result.arg1);
		// // HttpRequestParamManager requestParams = (HttpRequestParamManager)
		// // result.arg2;
		// // url = UrlUtil.constructUrl(url,
		// // UrlUtil.parameters2String(requestParams));
		// // sendErrorLog(url, baseJson);
		// break;
		// default:
		// showToast(baseBean.getMsg());
		// // ToastUtils.showToastShort(this, baseBean.getMsg());
		// break;
		}

	}

	/**
	 * 返回数据进行处理
	 * 
	 * @param reqeustCode
	 *            :�����ָʾ
	 * @param jsonString
	 */
	protected void handleJson(int reqeustCode, String jsonString, String message) {

	}

	/**
	 * 请求失败
	 * 
	 * @param reqeustCode
	 *            ����
	 * @param errorCode
	 *            1:����δ���� 2��
	 */
	public void handleNetErr(int requestCode, int errorCode) {

	}

	protected void handleResult400(int index, String msg) {
		tvHint.setVisibility(View.VISIBLE);
		tvHint.setText(msg);
		contentLayout.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		doClickAction(v.getId());
	}

	/**
	 * 按钮点击事件
	 * 
	 * @param viewId
	 */
	protected void doClickAction(int viewId) {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			doBackAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 显示等待dialog
	 */
	protected void showLoading() {
		if (loadingLayout != null) {
			loadingLayout.bringToFront();
			loadingLayout.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 取消等待dialog
	 */
	protected void dismissLoading() {
		if (loadingLayout != null) {
			loadingLayout.setVisibility(View.GONE);
		}

	}

	protected void doBackAction() {
		finish();
	}

	/**
	 * toast 通知
	 * 
	 * @param msg
	 */
	protected void showToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
			toast.setDuration(2200);
			toast.show();
		} else {
			toast.setText(msg);
			toast.setDuration(2200);
			toast.show();
		}
	}

	/**
	 * toast通知 *
	 * 
	 * @param resId
	 */
	protected void showToast(int resId) {
		if (toast == null) {
			toast = Toast.makeText(getApplicationContext(), resId,
					Toast.LENGTH_SHORT);
			toast.setDuration(2200);
			toast.show();
		} else {
			toast.setText(resId);
			toast.setDuration(2200);
			toast.show();
		}
	}

	protected void onReceiverFinish(Intent intent) {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiverFinish);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		mApplication.setLastTouchTime();
		return super.dispatchTouchEvent(ev);
	}

}
