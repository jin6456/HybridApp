package com.kiwoong.cmssign;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity implements AdvancedWebView.Listener {
	private boolean isInitialized = false;
	private WebViewInterface mWebViewInterface;
    public AdvancedWebView mWebView;
    private BackPressCloseHandler backPressCloseHandler;
	private Runnable mRunnable;
	private Handler mHandler;
	private RelativeLayout mContainer;
	private Context mContext;
	private AdvancedWebView mWebviewPop;
    static MainActivity instance =null;
    String url="";

    @Override
    protected void onRestart() {
        super.onRestart();
             Log.e("1111", "11111");
            url = new PrefUtil(mContext).getPrefDataString("url",HttpConnect.HOST);
	        mWebView.loadUrl(url);
            url=null;
   	        new PrefUtil(mContext).removePref();
            mWebView.reload(); // 현재 웹뷰 새로고침
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        //if(android.os.Build.VERSION.SDK_INT >= 19  && android.os.Build.VERSION.SDK_INT <= 20){ //킷캣에서만 
        mWebViewInterface = new WebViewInterface(this, mWebView, getIntent());
		mWebView.addJavascriptInterface(mWebViewInterface, "Android");
		mContainer = (RelativeLayout) findViewById(R.id.mContainer);
		mContext=this.getApplicationContext();
        instance=this;
		//}
     
        
        
        
        /* no 캐시 모드 추가 (개발 테스트용) */
//		mWebView.clearCache(true);
//		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		//String url = new PrefUtil(mContext).getPrefDataString("url",HttpConnect.HOST);
        url = new PrefUtil(mContext).getPrefDataString("url",HttpConnect.HOST);
        mWebView.loadUrl(url);
        url=null;
        new PrefUtil(mContext).removePref();
        mWebView.setWebViewClient(new WebViewClient() {

        	   public void onPageFinished(WebView view, String url) {
        		   mRunnable = new Runnable() {
        	            @Override
        	            public void run() {
        	            	 findViewById(R.id.splash).setVisibility(View.GONE);
        	            	 GCMRegistrar.checkDevice(mContext);
 			    			GCMRegistrar.checkManifest(mContext);
 			    			GCMRegistrar.register(mContext, GCMIntentService.SENDER_ID);
 			    			CustomLog.e("gcm register - non mb_id");

// 			    			String url = new PrefUtil(mContext).getPrefDataString("url",HttpConnect.HOST);
// 			    	        mWebView.loadUrl(url);
// 			    	        new PrefUtil(mContext).removePref("url");
        	            }

        	        };
        	        mHandler = new Handler();
        	        mHandler.postDelayed(mRunnable, 1000);
        	    }

        	});
        
      
        
        

    }
//    private class UriWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            String host = Uri.parse(url).getHost();
//            //Log.d("shouldOverrideUrlLoading", url);
//            if (host.equals(HttpConnect.target_url_prefix)) 
//            {
//                // This is my web site, so do not override; let my WebView load
//                // the page
//                if(mWebviewPop!=null)
//                {
//                    mWebviewPop.setVisibility(View.GONE);
//                    mContainer.removeView(mWebviewPop);
//                    mWebviewPop=null;
//                }
//                return false;
//            }
//
//            if(host.equals("m.facebook.com"))
//            {
//                return false;
//            }
//            // Otherwise, the link is not for a page on my site, so launch
//            // another Activity that handles URLs
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
//        }
//
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler,
//                SslError error) {
//            Log.d("onReceivedSslError", "onReceivedSslError");
//            //super.onReceivedSslError(view, handler, error);
//        }
//    }
//
//    class UriChromeClient extends WebChromeClient {
//
//        @Override
//        public boolean onCreateWindow(WebView view, boolean isDialog,
//                boolean isUserGesture, Message resultMsg) {
//            mWebviewPop = new AdvancedWebView(mContext);
//            mWebviewPop.setVerticalScrollBarEnabled(false);
//            mWebviewPop.setHorizontalScrollBarEnabled(false);
//            mWebviewPop.setWebViewClient(new UriWebViewClient());
//            mWebviewPop.getSettings().setJavaScriptEnabled(true);
//            mWebviewPop.getSettings().setSavePassword(false);
//            mWebviewPop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//            mContainer.addView(mWebviewPop);
//            AdvancedWebView.WebViewTransport transport = (AdvancedWebView.WebViewTransport) resultMsg.obj;
//            transport.setWebView(mWebviewPop);
//            resultMsg.sendToTarget();
//
//            return true;
//        }
//
//        @Override
//        public void onCloseWindow(WebView window) {
//            Log.d("onCloseWindow", "called");
//        }
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        Log.e("2222", "2222");
        url = new PrefUtil(mContext).getPrefDataString("url",HttpConnect.HOST);
        mWebView.loadUrl(url);
        url=null;
        new PrefUtil(mContext).removePref();
        mWebView.reload(); // 현재 웹뷰 새로고침
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        backPressCloseHandler.onBackPressed();
        //super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) { }

    @Override
    public void onExternalPageRequest(String url) { }

}