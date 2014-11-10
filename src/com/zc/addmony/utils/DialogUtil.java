package com.zc.addmony.utils;



import com.zc.addmony.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * 
 * @author adrainy 自定义弹出框
 * 
 */
public class DialogUtil {

	private static Dialog dialog = null;

	// 用来控制防止点击出多个dialog
	private static boolean isShowing;

	/**
	 * 快捷提示
	 * 
	 * @param c
	 * @param msg
	 *            展示的内容id
	 * @动作 确定按钮，没有动作
	 */
	public static void showDialog(Activity c, int resId) {
		showDialog(c, c.getString(resId));
	}

	/**
	 * 快捷提示
	 * 
	 * @param c
	 * @param msg
	 *            展示的内容
	 * @动作 确定按钮，没有动作
	 */
	public static void showDialog(Activity c, String msg) {
		showDialog(c, msg, "确定", null, null);
	}

	/**
	 * 提示框 资源id
	 * 
	 * @param c
	 * @param msg
	 *            展示的信息
	 * @param ok
	 * @param cancel
	 *            如果不显示取消按钮，值 null
	 * @param listener
	 *            点击确定或者取消，执行的listener
	 */
	public static void showDialog(Activity c, int msg, int ok, int cancel,
			OnClickListener listener) {
		try {
			showDialog(c, c.getString(msg), c.getString(ok),
					c.getString(cancel), listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog(Activity c, String msg, String ok,
			String cancel, final OnClickListener listener) {
		showDialog(c, "", msg, ok, cancel, listener, true, false);
	}

	/**
	 * 提示框
	 * 
	 * @param c
	 * @param title
	 *            展示信息的title
	 * @param msg
	 *            展示的信息
	 * @param ok
	 * @param listener
	 *            点击确定或者取消，执行的listener
	 */
	public static void showDialog(Activity c, String msg, String ok,
			final OnClickListener listener) {
		showDialog(c, "", msg, ok, null, listener, true, false);
	}

	/**
	 * 提示框
	 * 
	 * @param c
	 * @param title
	 *            展示信息的title
	 * @param msg
	 *            展示的信息
	 * @param ok
	 * @param cancel
	 *            如果不显示取消按钮，值 null
	 * @param listener
	 *            点击确定或者取消，执行的listener
	 */
	public static void showDialog(Activity c, String title, String msg,
			String ok, String cancel, final OnClickListener listener) {
		showDialog(c, title, msg, ok, cancel, listener, true, false);
	}

	/**
	 * 提示框
	 * 
	 * @param c
	 * @param title
	 *            展示信息的title
	 * @param msg
	 *            展示的信息
	 * @param ok
	 * @param cancel
	 *            如果不显示取消按钮，值 null
	 * @param listener
	 *            点击确定或者取消，执行的listener
	 * @param cancelable
	 *            dialog是不是可以取消掉
	 */
	public static void showDialog(Activity c, String title, String msg,
			String ok, String cancel, final OnClickListener listener,
			boolean cancelable) {
		showDialog(c, title, msg, ok, cancel, listener, cancelable, false);
	}

	/**
	 * 提示框
	 * 
	 * @param c
	 * @param title
	 *            展示信息的title
	 * @param msg
	 *            展示的信息
	 * @param ok
	 * @param cancel
	 *            如果不显示取消按钮，值 null
	 * @param listener
	 *            点击确定或者取消，执行的listener
	 * @param cancelable
	 *            dialog是不是可以取消掉
	 * @param alignMsgLeft
	 *            对显示内容的对齐方式进行设定，true为左对齐，默认居中对齐
	 */
	public static void showDialog(Activity c, String title, String msg,
			String ok, String cancel, final OnClickListener listener,
			boolean cancelable, boolean alignMsgLeft) {
		if (isShowing)
			return;
		isShowing = true;

		if (msg == null)
			msg = "";
		try {
			dialog = new Dialog(c, R.style.CustomDialogStyle);
			LayoutInflater mInflater = LayoutInflater.from(c);
			View view = mInflater.inflate(R.layout.dialog_prompt_layout, null);

			TextView tvMsg = (TextView) view
					.findViewById(R.id.dialog_prompt_tv_msg);
			Button btnOK = (Button) view
					.findViewById(R.id.dialog_prompt_btn_ok);
			Button btnCancel = (Button) view
					.findViewById(R.id.dialog_prompt_btn_cancel);

			if (!TextUtils.isEmpty(title)) {
				TextView tvTitle = (TextView) view
						.findViewById(R.id.dialog_prompt_tv_title);
				tvTitle.setVisibility(View.VISIBLE);
				tvTitle.setText(title);
			}

			if (alignMsgLeft)
				tvMsg.setGravity(Gravity.LEFT);

			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cancelDialog();
					if (listener != null) {
						listener.onClick(v);
					}
				}
			});

			tvMsg.setText(msg);
			btnOK.setText(ok);
			if (TextUtils.isEmpty(cancel)) {
				// LinearLayout layout = (LinearLayout) view
				// .findViewById(R.id.dialog_prompt_ll_cancel);
				// layout.setVisibility(View.GONE);
				TextView tvLine = (TextView) view
						.findViewById(R.id.dialog_prompt_tv_line);
				tvLine.setVisibility(View.INVISIBLE);
				btnCancel.setVisibility(View.GONE);
			} else {
				btnCancel.setText(cancel);
				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						cancelDialog();
						if (listener != null) {
							listener.onClick(v);
						}
					}
				});
			}
			dialog.setContentView(view);
			dialog.setCancelable(cancelable);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					isShowing = false;
				}
			});

			// Window window = dialog.getWindow();
			//
			// window.setWindowAnimations(R.style.AnimationBottonInBottomOut);
			// WindowManager.LayoutParams wml = window.getAttributes();
			// wml.width = LayoutParams.FILL_PARENT;
			// wml.height = LayoutParams.WRAP_CONTENT;
			// wml.width = (int) (SystemParams.getInstance((Activity)
			// c).screenWidth
			// * 9 / 10.0);
			// wml.height = (int) (SystemParams.getInstance((Activity)
			// c).screenHeight / 3.0);
			if (!c.isFinishing()) {
				dialog.show();
			} else {
				isShowing = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cancelDialog() {
		try {
			if (dialog != null && dialog.isShowing())
				dialog.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}