package com.kiwoong.cmssign;

import java.io.File;

import android.os.Environment;

/**
 * @author jong-hyun.jeong
 * Constant 정의, API 주소, 파라미터 등 변하지 않는 값들이 모여있는 클래스
 */
public class Constant {
	
	
	public static final String EXTRA_IS_AMOBILE = "extra_is_amobile";
	public static final String EXTRA_SELECTED_ITEM = "extra_selected_item";
	public static final String EXTRA_COOKIE = "extra_cookie";
	public static final String EXTRA_USER_INFO = "extra_user_info";
	public static final String PREFERENCE_MENU_ITEMS = "preference_menu_items";
	public static final String PREFERENCE_INCIDENT = "preference_incident";
	public static final String DIRECTORY_PATH = Environment.getExternalStorageDirectory() + File.separator + "upload";
	public static final String DIRECTORY_PHOTO_PATH = DIRECTORY_PATH + File.separator + "photo";
	public static final String DIRECTORY_VOICE_PATH = DIRECTORY_PATH + File.separator + "voice";
	public static final String DIRECTORY_DAMAGE_PATH = DIRECTORY_PATH + File.separator + "damage";
}
