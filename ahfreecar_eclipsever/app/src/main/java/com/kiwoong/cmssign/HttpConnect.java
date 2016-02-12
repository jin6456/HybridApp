package com.kiwoong.cmssign;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.google.android.gcm.GCMRegistrar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.kiwoong.cmssign.CustomLog;
import com.kiwoong.cmssign.GCMIntentService;


public class HttpConnect {
  
	 //mWebView.loadUrl("https://cmssign.kwic.co.kr");
    //mWebView.loadUrl("http://121.189.16.124");
    //mWebView.loadUrl("http://192.168.0.33:8080/payinfo/web/recruiter/condition.html");
	
	//public final static String HOST = "http://192.168.0.33:8080/payinfo/web/recruiter/main.html";   
	public final static String HOST = "http://www.ah-freecar.com";   
	public final static String target_url_prefix = "www.ah-freecar.com";   
	
	
	
	//public final static String SERVICE = "hworld";
	
	final int DEFAULT_TIMEOUT = 20 * 1000;
	

	public AsyncHttpClient asyncHttpClient;
	public Context mContext;

	// 통신 유틸 들어갈 예정
	public static boolean isWifiNetwork(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
		// Wifi 를 사용하는지 확인힌다.
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
			.isConnectedOrConnecting();
		return isWifi;
	}

	public boolean isOnline(Context context) {
		try {
			ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

			State wifi = conMan.getNetworkInfo(1).getState(); // wifi
			if (wifi == State.CONNECTED
				|| wifi == State.CONNECTING) {
				return true;
			}

			State mobile = conMan.getNetworkInfo(0).getState(); // mobile
			// ConnectivityManager.TYPE_MOBILE
			if (mobile == State.CONNECTED
				|| mobile == State.CONNECTING) {
				return true;
			}

		} catch (NullPointerException e) {
			return false;
		}

		return false;
	}

	public HttpConnect(Context context) {
		mContext = context;
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setTimeout(DEFAULT_TIMEOUT);

	}



	public void onConnect(String path, RequestParams params, OnConnectHttp connectHttp) {
		onBasicConnect(path, params, connectHttp, false, true);
	}

	public void onConnect(String path, RequestParams params, Boolean isProgressBar, OnConnectHttp connectHttp) {
		onBasicConnect(path, params, connectHttp, isProgressBar, true);
	}

	public void onConnectPager(String path, RequestParams params, Boolean isProgressBar, OnConnectHttp connectHttp) {
		onBasicConnect(path, params, connectHttp, false, false);
	}

	private void onBasicConnect( String path, RequestParams params, final OnConnectHttp connectHttp, final Boolean isProgressBar, final Boolean isAlert) {
		// AsyncHttpCookie();
		if (!path.contains("http")) {
			path = HOST + path;
		}
		if (!isOnline(mContext)) {

			AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
			alert.setPositiveButton("확인",
				new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss(); // 닫기
					((Activity) mContext).finish();
					android.os.Process.killProcess(android.os.Process
						.myPid());

				}
			});
			alert.setMessage("네트워크가 불안정합니다. 잠시 후 다시 시도해주십시오.");
			alert.show();

		}

		if (isProgressBar) {
			AlertUtile.showLoadingDialog(mContext);
		}

		final String mpath = path ;
		AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

			/* (non-Javadoc)
			 * @see com.loopj.android.http.AsyncHttpResponseHandler#onFinish()
			 */
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if ( connectHttp != null)
					connectHttp.onConnectFinesh();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,Throwable arg3) {
				CustomLog.e("headers statusCode" + arg0);
				
				CustomLog.e("headers = > " + arg1.toString());
				CustomLog.e("headers = > " + arg3.getLocalizedMessage());
				CustomLog.e("headers = > " + arg3.getMessage());
				
				if ( connectHttp != null)
					connectHttp.onConnectFail(arg2);
				
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if ( connectHttp != null)
					connectHttp.onConnectOk(arg2);
				
			}

			
		};
		if (asyncHttpClient != null) {
			CustomLog.e(path + "?" + params);
			asyncHttpClient.post(mContext, path, params, handler);
		}

	}
	//

}
