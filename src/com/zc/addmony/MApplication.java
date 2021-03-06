package com.zc.addmony;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.zc.addmony.bean.FundBean;
import com.zc.addmony.bean.activities.ActivitiesPhoneBean;
import com.zc.addmony.bean.activities.PhoneBean;
import com.zc.addmony.bean.myproduct.BuyProductsBean;
import com.zc.addmony.bean.myproduct.OpenBankBean;
import com.zc.addmony.bean.productlist.ProductListBean;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.ui.lock.GestureActivity;
import com.zc.addmony.view.lockview.LockPatternUtils;

/**
 * Application
 * 
 * @File_name: MApplication.java
 * @Package_name:
 * @Author teffy
 * @Date : 2013-04-10下午12:07:09
 * @Version 1.0
 */
public class MApplication extends Application {

	private String TAG = "MApplication";
	public FundBean fundBean = new FundBean();
	/** 活动保存的数据 */
	public ActivitiesPhoneBean apBean;
	/** 活动保存所选择的手机 */
	public PhoneBean pBean;
	/** 开户bean */
	public OpenBankBean obBean;
	/** 购买基金bean*/
	public BuyProductsBean bpBean;
	/** 保存活动打开的activity */
	private List<Activity> activitys;
	/** 打开的activity*/
	private List<Activity> allActivitys;
	/** 货币bean */
	private ProductListBean pdBean;
	private UserSharedData userShare;
	public String zcbCode;
	public String zcbShareType;
	public String zcbFundtypecode;
	public static int width;
	public static int height;
	public static float density;
	public static int densityDpi;

	@Override
	public void onCreate() {
		super.onCreate();
		getDensity();
		apBean = new ActivitiesPhoneBean();
		obBean = new OpenBankBean();
		bpBean = new BuyProductsBean();
		activitys = new ArrayList<Activity>();
		allActivitys = new ArrayList<Activity>();
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	/**
	 * 根据构造函数获得当前手机的屏幕系数
	 */
	public void getDensity() {
		// 获取当前屏幕
		DisplayMetrics dm = new DisplayMetrics();
		dm = getApplicationContext().getResources().getDisplayMetrics();

		width = dm.widthPixels;
		height = dm.heightPixels;
		density = dm.density;
		densityDpi = dm.densityDpi;

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	// 上一次触碰时间
	long lastTimeMillis = 0;

	public void setLastTouchTime() {
		Log.e(TAG, LockPatternUtils.getInstance(getApplicationContext())
				.getLockPaternString("user_key") + "");
		if (TextUtils.isEmpty(LockPatternUtils.getInstance(
				getApplicationContext()).getLockPaternString("user_key"))
				|| !userShare.GetFlag()) {
			return;
		}

		// 最新的触碰时间
		long currentTimeMillis = System.currentTimeMillis();
		if (lastTimeMillis == 0) {
			// 第一次触碰
			lastTimeMillis = currentTimeMillis;
			// 开启监听
			startVerify();
		} else {
			// 时间差
			long temp = currentTimeMillis - lastTimeMillis;
			// 如果时间差小于5分钟，就先停掉前一次的监听，再重新开启
			if (temp < 1000 * 60 * 500) {
				stopVerify();
				startVerify();
			}
			// else 如果大于,那么上一次的监听在运行着，5分钟之后自然会锁定
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.e("handler", "handler");
			Log.e(TAG, LockPatternUtils.getInstance(getApplicationContext())
					.getLockPaternString("user_key") + "");
			if (TextUtils.isEmpty(LockPatternUtils.getInstance(
					getApplicationContext()).getLockPaternString("user_key"))
					|| !userShare.GetFlag()) {
				return;
			}
			verify();
		}
	};

	/**
	 * 开启检测界面
	 * 
	 * @param
	 * @return void
	 * @throws
	 */
	public void verify() {
		if (TextUtils.isEmpty(LockPatternUtils.getInstance(
				getApplicationContext()).getLockPaternString("user_key"))) {
			return;
		}
		boolean isTopRunning = isRunningForeground(getApplicationContext());
		if (isTopRunning) {
			// 判断检测界面是否已经运行
			if (!GestureActivity.IS_SHOW) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), GestureActivity.class);
				intent.putExtra(GestureActivity.INTENT_MODE,
						GestureActivity.GESTURE_MODE_VERIFY);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		}
	}

	private static Timer timer;
	private static TimerTask timerTask;
	private static boolean isRunning;

	/**
	 * 5分钟一次弹出
	 * 
	 * @param
	 * @return void
	 * @throws
	 */
	public void startVerify() {
		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {
				@Override
				public void run() {
					mHandler.sendEmptyMessage(0);
				}
			};
		}
		if (!isRunning) {
			timer.schedule(timerTask, 500 * 60 * 1000, 500 * 60 * 1000);
			isRunning = true;
		}
	}

	/**
	 * 停止检测
	 * 
	 * @param
	 * @return void
	 * @throws
	 */
	public static void stopVerify() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		isRunning = false;
	}

	/**
	 * 是否在前台运行
	 * 
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean isRunningForeground(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		String currentPackageName = cn.getPackageName();
		// ComponentInfo{com.android.systemui/com.android.systemui.recent.RecentsActivity}
		// ComponentInfo{com.android.systemui/com.android.systemui.recent.RecentAppFxActivity}
		if (!TextUtils.isEmpty(currentPackageName)
				&& currentPackageName.equals(getPackageName())) {
			return true;
		}
		return false;
	}

	/*************************************** Activity管理 *************************************/
	/**
	 * 正在运行的Activity
	 */
	public static List<Activity> runingActivities = new ArrayList<Activity>();

	/**
	 * 添加Activity
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		runingActivities.add(activity);
	}

	/**
	 * 移除Activity
	 * 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		runingActivities.remove(activity);
	}

	/**
	 * 退出应用
	 * 
	 * @param context
	 */
	public static void exitAllActivity(Context context) {
		if (runingActivities != null) {
			for (int i = 0; i < runingActivities.size(); i++) {
				Activity item = runingActivities.get(i);
				item.finish();
				runingActivities.remove(item);
				i--;
			}
		}
		stopVerify();
		System.exit(0);
	}

	/**
	 * 清空activity栈,出了当前activity
	 * 
	 * @param context
	 */
	public static void clearAllActivity(Activity context) {
		for (int i = 0; i < runingActivities.size(); i++) {
			Activity item = runingActivities.get(i);
			if (context.getClass().getSimpleName()
					.equals(item.getClass().getSimpleName())) {
				continue;
			}
			item.finish();
			runingActivities.remove(item);
		}
	}

	public ActivitiesPhoneBean getApBean() {
		return apBean;
	}

	public void setApBean(ActivitiesPhoneBean apBean) {
		this.apBean = apBean;
	}

	/** 获取开户bean */
	public OpenBankBean getObBean() {
		return obBean;
	}

	/** 保存活动bean */
	public void setObBean(OpenBankBean obBean) {
		this.obBean = obBean;
	}

	public PhoneBean getpBean() {
		return pBean;
	}

	public void setpBean(PhoneBean pBean) {
		this.pBean = pBean;
	}

	public ProductListBean getPdBean() {
		return pdBean;
	}

	public void setPdBean(ProductListBean pdBean) {
		this.pdBean = pdBean;
	}
	
	

	public BuyProductsBean getBpBean() {
		return bpBean;
	}

	public void setBpBean(BuyProductsBean bpBean) {
		this.bpBean = bpBean;
	}

	/** 添加activity*/
	public void addAllActivity(Activity activity){
		if(activity != null){
			allActivitys.add(activity);
		}
	}
	
	/** 退出*/
	public void clearAllActivity(){
		for(Activity a: allActivitys){
			a.finish();
		}
	}
	
	/** 添加活动activity */
	public void addActivitys(Activity activity) {
		if (activity != null) {
			activitys.add(activity);
		}
	}

	/** 退出活动activity */
	public void outActivitys() {
		for (Activity activity : activitys) {
			if (activity != null) {
				activity.finish();
			}
		}
	}

}
