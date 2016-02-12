package com.kiwoong.cmssign;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.loopj.android.http.RequestParams;

import org.apache.commons.validator.UrlValidator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * GCM 등록 서비스 IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	public static boolean isLogin = false;

	public static Activity mActivity = null;;
	//hueworld
	public static final String SENDER_ID = "137466455861" ;
	public static String token = null;
	private Bitmap bmBigPicture;
	private  String pushMessage = "";
	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */

	private  void generateNotification(final Context context, final Intent intent) {
		final int icon = R.drawable.ic_launcher;
		// notifies user
		final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		int requestID = (int) System.currentTimeMillis();
		String title = context.getString(R.string.app_name) ; 
		CustomLog.e("intent.getExtras() = > "  + intent.getExtras());
		Bundle bundle  = intent.getExtras() ;
		//String message = bundlebundle.getString("data");

		String msg = "" , type  =""  , no = "" ,img="";
		try {
			msg = bundle.getString("message");
			title = bundle.getString("title"); 
			img =bundle.getString("image");

		//	bmBigPicture =  
					
			
//			ImageView imgView= new ImageView(context);
//			ImageLoad.imageLoad(context,img, imgView);
//			imgView.buildDrawingCache();
//			bmBigPicture = imgView.getDrawingCache();
			// BitmapFactory.decodeResource(getResources(), R.drawable.splash);
 			//msg= URLDecoder.decode(bundle.getString("content"), "utf-8");
			//title= URLDecoder.decode(bundle.getString("title"), "utf-8");
//			JSONObject jsonObject  = new JSONObject(message);
//			CustomLog.e("jsonObject "  + jsonObject.toString());
//			title =  jsonObject.isNull("title") ? context.getString(R.string.app_name)  : jsonObject.getString("title");
//			msg = jsonObject.isNull("content") ? "": jsonObject.getString("content");
//			type = jsonObject.isNull("type") ? "": jsonObject.getString("type");
//			no = jsonObject.isNull("no") ? "": jsonObject.getString("no");

		} catch (final Exception e) {
			if ( CustomLog.isDebug)	e.printStackTrace();
		}
		Intent notificationIntent  = new Intent(context, MainActivity.class);
		Bundle agrs = new Bundle();
		UrlValidator urlValidator = new UrlValidator();
		boolean isUrl = urlValidator.isValid(msg);
		//Log.i("msg==>", msg);
		if(isUrl){
			new PrefUtil(context).setPrefData("url", msg);
//			if(MainActivity.instance != null ){
//				pushMessage = msg;//"messageContainsUrl";
//				handlePushMessage(pushMessage);
//			}
		}else{
			new PrefUtil(context).removePref("url");
		}

		notificationIntent.putExtras(agrs);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		final PendingIntent pending_intent = PendingIntent.getActivity(context, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//		
		final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
		.setSmallIcon(icon).setTicker(title)
		.setWhen(System.currentTimeMillis()).setAutoCancel(true)
		.setContentTitle(title )
        .setContentText(msg).setStyle(new NotificationCompat.BigTextStyle().bigText(msg).setSummaryText("더보기"))
        .setVibrate(new long[] { 1000, 1000 })
        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		try {
			bmBigPicture = BitmapFactory.decodeStream((InputStream) new URL(HttpConnect.HOST+img).getContent());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		if( bmBigPicture!=null && !bmBigPicture.equals("")){
			android.support.v4.app.NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle(builder);
			//Bitmap bigPictureBitmap  = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
			bigPictureStyle.bigPicture(bmBigPicture)
			.setBigContentTitle(title )
			.setSummaryText(msg );
			
			builder.setContentIntent(pending_intent);
			
			
			NotificationManager mNotificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(requestID, bigPictureStyle.build());
		}else{
			builder.setContentIntent(pending_intent);
			
			
			NotificationManager mNotificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(requestID, builder.build());
		}

		
	
		
		
		
		context.sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
		context.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

	}



	private void handlePushMessage(String pushMsg){
		Message msgObj = gcmCommandHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putString("pushMsg",pushMsg);
		msgObj.setData(bundle);

		gcmCommandHandler.sendMessage(msgObj);
	}

	private Handler gcmCommandHandler = new Handler() {
		// Create handleMessage function
		public void handleMessage(Message message) {
			if (pushMessage != null && pushMessage.length() > 0) {

					Bundle bundle = new Bundle();
					bundle =	message.getData();
				    String url = bundle.getString("pushMsg");
						Log.i("url==>",url);
					MainActivity.instance.mWebView.loadUrl(url);

			}
		}
	};



	private final Handler handler = new Handler();

	public GCMIntentService() {
		super(GCMIntentService.SENDER_ID);
	}

	@Override
	protected void onDeletedMessages(final Context context, final int total) {
		CustomLog.e("Received deleted messages notification");
	}

	@Override
	public void onError(final Context context, final String errorId) {
		CustomLog.e("Received error: " + errorId);
	}

	@Override
	protected void onMessage(final Context context, final Intent intent) {
		CustomLog.e("Received message");

		// 실행중이 아닐경우 팝업을 띄운다 .. 실행중일 경우 노티 안받음 !!
		//		if (!AcivityRunningUtil.isApplicationRunning(context, context.getPackageName()) || AcivityRunningUtil.isActivityRunning(this, context.getPackageName(), "PushPopup")) {
		generateNotification(context, intent);
		//		}
	}

	@Override
	protected boolean onRecoverableError(final Context context, final String errorId) {
		CustomLog.e("Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	@Override
	protected void onRegistered(final Context context, final String registrationId) {
		CustomLog.e("Device registered: regId = " + registrationId);
		GCMIntentService.token = registrationId;
		handler.post(new Runnable() {

			@Override
			public void run() {
				// https://cmssign.kwic.co.kr/ETC_ETS_U4000A?LGID=id&SVCKN=1&PSID=token
				HttpConnect connect  = new HttpConnect(context);
				RequestParams params = new RequestParams();
				if(!new PrefUtil(context).getPrefDataString(PrefUtil.PREF_USER_ID, "").equals("")){
					params.put("user_id", new PrefUtil(context).getPrefDataString(PrefUtil.PREF_USER_ID, ""));
				}
				params.put("push_token",  token);
				params.put("device_id",  DeviceUtil.getDeviceId(context));
				
				
				connect.onConnect("/api_register_token.php", params, 	new OnConnectHttp() {

					@Override
					public void onConnectOk(byte[] object) {
						// TODO Auto-generated method stub
						CustomLog.e("push 통신 등록 ");
					}

					@Override
					public void onConnectFail(byte[] object) {
						// TODO Auto-generated method stub
						CustomLog.e("push 등록실패 ");
					}

					@Override
					public void onConnectFinesh() {
						// TODO Auto-generated method stub
						CustomLog.e("push 통신 종료");
						
					}
					
				});
			}
		});
	}


	@Override
	protected void onUnregistered(final Context context, final String registrationId) {
		CustomLog.e("Device unregistered");

	}

}
