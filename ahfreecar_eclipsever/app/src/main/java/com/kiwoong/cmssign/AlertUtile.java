package com.kiwoong.cmssign;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class AlertUtile {

	public static int CAMERA_INTENT =3939 ;
	public static int GALLERY_INTENT =4949 ;


	public static String ALERT_TITLE_DEFAULT = "알림";
	public static void showAlertMeg(Context context ,String title ,String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	static ProgressDialog progressDialog ; 
	public static void showLoadingDialog(Context context)
	{
		if  ( progressDialog!= null)
		{
			if ( progressDialog.isShowing())
			{
				progressDialog.dismiss();
			}
		}
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("처리중입니다..");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	public static void dissmissLoadingDialog()
	{
		if  ( progressDialog!= null)
		{
			if ( progressDialog .isShowing())
				progressDialog.dismiss();
		}
	}

	static ProgressDialog mProgressDialog ; 
	public static void mProgressDialogShow(Context context,String message)
	{
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	public static void mProgressDialogDissmiss()
	{
		if  ( mProgressDialog!= null)
		{
			mProgressDialog.dismiss();
		}
	}
}
