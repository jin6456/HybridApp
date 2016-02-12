package com.kiwoong.cmssign;
/*
 *  
 *  커스텀 로그 생성해서 사용할경우 그클레스내에 나오게하거나 숨길수있다.
 *  Static이나 생성 사용할경우 이곳에서 보이기 안보이기 컨트롤가능 
 *  2013 .06 . 18 KMH
 *  
 */
import android.content.Context;
import android.util.Log;

public class CustomLog {
	public static boolean isDebug= true;
	static String TAG  ="";
	public CustomLog(boolean isClassDebug, Context context) {
		CustomLog.TAG = context.getClass().getName();
	}

	public static void i(String format ,Object... params)
	{
		if ( isDebug )
		{	
			StackTraceElement[] trace = new Throwable().getStackTrace();
			StackTraceElement elt = trace[1];
			Log.i(TAG, "at " + elt.toString());
			Log.i(TAG, String.format(format, params));
		}
	}

	public static void e(Exception e)
	{
		if ( isDebug )
		{	
			e.printStackTrace();
		}
	}

	public static void d(String format ,Object... params)
	{
		if ( isDebug )
		{	
			StackTraceElement[] trace = new Throwable().getStackTrace();
			StackTraceElement elt = trace[1];
			Log.d(TAG, "at " + elt.toString());
			Log.d(TAG, String.format(format, params));
		}
	}

	public static void e(String format ,Object... params)
	{
		if ( isDebug )
		{	
			StackTraceElement[] trace = new Throwable().getStackTrace();
			StackTraceElement elt = trace[1];
			Log.e(TAG, "at " + elt.toString());
			Log.e(TAG, String.format(format, params));
		}
	}


	public static void d(String params)
	{
		if ( isDebug )
		{	
			StackTraceElement[] trace = new Throwable().getStackTrace();
			StackTraceElement elt = trace[1];
			Log.d(TAG, params);
			Log.d(TAG, "at " + elt.toString());
		}
	}

	public static void e(String params)
	{
		if ( isDebug )
		{	
			StackTraceElement[] trace = new Throwable().getStackTrace();
			StackTraceElement elt = trace[1];
			Log.e(TAG, params);
			Log.e(TAG, "at " + elt.toString());


		}
	}

	public static void i(String params)
	{
		if ( isDebug )
		{	
			StackTraceElement[] trace = new Throwable().getStackTrace();
			StackTraceElement elt = trace[1];
			Log.i(TAG, params);
			Log.i(TAG, "at " + elt.toString());
		}
	}


}
