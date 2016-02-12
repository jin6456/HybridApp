package com.kiwoong.cmssign;

import java.util.Set;

import  com.kiwoong.cmssign.CustomLog;
import  com.kiwoong.cmssign.PrefUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtil {

	private final SharedPreferences.Editor editor;
	private final SharedPreferences pref;
	public static String PREF_INQUIRY_NUMBER= "PREF_INQUIRY_NUMBER";
	public static String PREF_USER_ID= "PREF_USER_ID";
	public static String PREF_USER_PW= "PREF_USER_PW";
	public static String PREF_AUTH_CODE= "PREF_AUTH_CODE";
	public static String PREF_VERSION_CODE= "PREF_VERSION_CODE";

	public static String PREF_CURRENT_TAB_NO= "pref_current_tab_no";
	public static String PREF_USER_NAME= "pref_user_name";
	public static String PREF_OPEN_COUNT= "pref_open_count";
	public static String PREF_USER_NO= "pref_user_no";
	public static String PREF_TOKEN = "pref_token";
	public static String PREF_USER_INFO= "PREF_USER_INFO";
	public static String PREF_QR_CODE= "PREF_QR_CODE";
	public static String PREF_BUSINESS_CARD_INFO= "PREF_BUSINESS_CARD_INFO";

	
	public PrefUtil(final Context context) {
		
		pref = context.getSharedPreferences(context.getPackageName().replace(".", ""), Context.MODE_WORLD_READABLE);
		CustomLog.i("Pref_name"+"_"+context.getPackageName().replace(".", ""));
		editor = pref.edit();
	}

	public int getPrefData(String keydata,int data)
	{
		return pref.getInt(keydata,data);
	}
	public void setPrefData(String keydatam,int data)
	{
		editor.putInt(keydatam, data);
		editor.commit();
	}

	

	public String getPrefDataString(String keydata,String data)
	{
		return 	pref.getString(keydata,data);
	}

	public Boolean getPrefData(String keydata,Boolean data)
	{
		return pref.getBoolean(keydata,data);
	}
	public void setPrefData(String keydatam,Boolean data)
	{
		editor.putBoolean(keydatam, data);
		editor.commit();
	}

	public long setPrefData(String keydata,long data)
	{
		return pref.getLong(keydata,data);
	}
	public void getPrefData(String keydatam,long data)
	{
		editor.putLong(keydatam, data);
		editor.commit();
	}
	public String getPrefData(String keydata,String data)
	{
		return 	pref.getString(keydata,data);
	}
	public void setPrefData(String keydatam,String data)
	{
		editor.putString(keydatam, data);
		editor.commit();
	}

	public void removePref(String keyData){
		editor.remove(keyData);
		editor.commit();
	}
	public void removePref(){

		try {
			Set<String> allKeys = 	pref.getAll().keySet() ; 
			// 버전정보 , 배너정보는 뺴고 삭제 
			if ( allKeys.contains(PrefUtil.PREF_AUTH_CODE))
				allKeys.remove(PrefUtil.PREF_AUTH_CODE);
			CustomLog.e("allKeys = > "  + allKeys.toString() );
			for (String key : allKeys) {
				CustomLog.e("key = > "  + key );
				editor.remove(key);
			}
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean contains(String str) {
		return pref.contains(str);
	}

	


}
