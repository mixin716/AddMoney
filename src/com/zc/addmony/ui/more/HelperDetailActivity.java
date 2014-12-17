package com.zc.addmony.ui.more;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

public class HelperDetailActivity extends BaseActivity {
	private WebView webContent;
	private String url;
	private MApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_helper_detail_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		url = getIntent().getStringExtra("url");
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("帮助中心");

	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void setViews() {
		webContent = (WebView) findViewById(R.id.activity_helper_detail_web);
		webContent.getSettings().setJavaScriptEnabled(true);
		webContent.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showLoading();
				super.onPageStarted(view, url, favicon);

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				dismissLoading();
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				dismissLoading();
			}

		});
		webContent.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				return true;
			}

		});
		webContent.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				 Uri uri = Uri.parse(url);
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);

			}
		});
		webContent.loadUrl(url);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(HelperDetailActivity.this);
			break;

		default:
			break;
		}
	}
}
