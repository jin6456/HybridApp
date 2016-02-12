package com.kiwoong.cmssign;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class BackPressCloseHandler {

	private long backKeyPressedTime = 0;
	private Toast toast;

	private Activity activity;

	public BackPressCloseHandler(Activity context) {
		this.activity = context;
	}

	public void getBannerInfo() {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				HttpConnect connect  = new HttpConnect(activity);
				RequestParams params = new RequestParams();
				params.put("test",  "");

				connect.onConnect("/api_get_banner.php", params, new OnConnectHttp() {

					@Override
					public void onConnectOk(byte[] object) {
						// TODO Auto-generated method stub

						String str = new String(object);
						try {
							JSONObject json = new JSONObject(str);
							if( json.getInt("code") == 200 ) {
								JSONObject data = json.getJSONObject("data");
								String bannerImg = data.getString("img");
								String bannerUrl = data.getString("url");
								showBanner("앱을 종료하시겠습니까?", bannerImg, bannerUrl);
							}
							else {
								showGuide();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							showGuide();
						}
						CustomLog.e("onConnectOk : "+str);
					}

					@Override
					public void onConnectFail(byte[] object) {
						// TODO Auto-generated method stub
						CustomLog.e("onConnectFail : "+object.toString());
						showGuide();
					}

					@Override
					public void onConnectFinesh() {
						// TODO Auto-generated method stub
						CustomLog.e("onConnectFinesh");

					}

				});
			}
		});
	}

	public void onBackPressed() {
		if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
			backKeyPressedTime = System.currentTimeMillis();

			getBannerInfo();
			return;
		}
//		if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//			activity.finish();
//			toast.cancel();
//			System.exit(0);
//		}
	}

	public void showGuide() {
//		toast = Toast.makeText(activity,
//				"\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
//		toast.show();
		
		AdvancedWebView mWebView =(AdvancedWebView) activity.findViewById(R.id.webview);
		String webUrl = mWebView.getUrl();
		
		//if(webUrl.equals(HttpConnect.HOST)){
		
				AlertDialog.Builder alert = new AlertDialog.Builder(activity);
				alert.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // 닫기
						((Activity) activity).finish();
						android.os.Process.killProcess(android.os.Process
							.myPid());
						System.exit(0);
		
					}
				});
				alert.setNegativeButton("취소",
						new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss(); // 닫기
						}
					});
				alert.setMessage("앱을 종료하시겠습니까?");
				alert.show();
//		}else{
//			mWebView.loadUrl(HttpConnect.HOST);
//		}
	}

	BannerDialog mCustomDialog;
	public void showBanner(String title, String image, String url) {
	    mCustomDialog = new BannerDialog(activity,
				title, image, url, leftClickListener, rightClickListener);
		mCustomDialog.show();
	}
	private View.OnClickListener leftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			((Activity) activity).finish();
			android.os.Process.killProcess(android.os.Process
					.myPid());
			System.exit(0);
		}
	};

	private View.OnClickListener rightClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCustomDialog.dismiss();
		}
	};
}