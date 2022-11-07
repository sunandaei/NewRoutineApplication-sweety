package com.sunanda.newroutine.application.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunanda.newroutine.application.somenath.pojo.LabWorkStatus;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "MyLogin";
	
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
	private static final String KEY_LOGGED_NAME = "logname";
	private static final String KEY_LOGGED_EMAIL = "logEmail";
	private static final String KEY_LOGGED_PHONE = "logPhone";
	private static final String KEY_LOGGED_ID = "logid";
	private static final String KEY_SESSION_ID = "sessionid";
	private static final String KEY_ADDRESS_ACT = "address_activity";

	private static final String KEY_DATE = "0000-00-00";
	private static final String KEY_IS_FIRST_TIME = "is_first";

	private static final String KEY_DIST_CODE = "dist_code";
	private static final String KEY_DIST_NAME = "dist_name";
	private static final String KEY_BLOCK_CODE = "block_code";
	private static final String KEY_BLOCK_NAME = "block_name";
	private static final String KEY_PAN_CODE = "pan_code";
	private static final String KEY_PAN_NAME = "pan_name";
	private static final String KEY_LAB_CODE = "lab_code";
	private static final String KEY_LAB_NAME = "lab_name";

	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setLogin(boolean isLoggedIn, String id, String  name, String email, String phone, String sesID) {
		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
		editor.putString(KEY_LOGGED_ID, id);
		editor.putString(KEY_LOGGED_NAME, name);
		editor.putString(KEY_LOGGED_EMAIL, email);
		editor.putString(KEY_LOGGED_PHONE, phone);
		editor.putString(KEY_SESSION_ID, sesID);

		// commit changes
		editor.commit();
		//Log.d(TAG, "User login session modified!");
	}

	public void destroySession(){
		editor.clear();
		setDate("0000-00-00");
		editor.commit();
	}

	public void setKEY_ADDRESS_ACT(int val){
		editor.putInt(KEY_ADDRESS_ACT, val);
		editor.commit();
	}

	public void setDate(String date){
		editor.putString(KEY_DATE, date);
		editor.commit();
	}

	public String getDate(){
		return pref.getString(KEY_DATE, "0000-00-00");
	}

	public void setIsFIrst(boolean val){
		editor.putBoolean(KEY_IS_FIRST_TIME, val);
		editor.commit();
	}

	public boolean isFirst(){
		return pref.getBoolean(KEY_IS_FIRST_TIME, false);
	}

	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}

	public String getName() { return pref.getString(KEY_LOGGED_NAME, "logname"); }

	public String getEmail(){
		return pref.getString(KEY_LOGGED_EMAIL, "logEmail");
	}

	public String getPhone(){ return pref.getString(KEY_LOGGED_PHONE, "logPhone"); }

	public int getKeyAddressAct() { return pref.getInt(KEY_ADDRESS_ACT, 0); }


	public void saveArrayList(ArrayList<LabWorkStatus> list, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);
		SharedPreferences.Editor editor = prefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		editor.putString(key, json);
		editor.apply();
	}

	public ArrayList<LabWorkStatus> getArrayList(String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);
		Gson gson = new Gson();
		String json = prefs.getString(key, null);
		Type type = new TypeToken<ArrayList<LabWorkStatus>>() {}.getType();
		return gson.fromJson(json, type);
	}

	public void setAllValues(String dist_code, String dist_name, String block_code, String block_name,
							 String pan_code, String pan_name, String lab_code, String lab_name){
		editor.putString(KEY_DIST_CODE, dist_code);
		editor.putString(KEY_DIST_NAME, dist_name);
		editor.putString(KEY_BLOCK_CODE, block_code);
		editor.putString(KEY_BLOCK_NAME, block_name);
		editor.putString(KEY_PAN_CODE, pan_code);
		editor.putString(KEY_PAN_NAME, pan_name);
		editor.putString(KEY_LAB_CODE, lab_code);
		editor.putString(KEY_LAB_NAME, lab_name);
		editor.commit();
	}

	public String getKeyDistCode(){ return pref.getString(KEY_DIST_CODE, "dist_code"); }

	public String getKeyDistName(){ return pref.getString(KEY_DIST_NAME, "dist_name"); }

	public String getKeyBlockCode(){ return pref.getString(KEY_BLOCK_CODE, "block_code"); }

	public String getKeyBlockName(){ return pref.getString(KEY_BLOCK_NAME, "block_name"); }

	public String getKeyPanCode(){ return pref.getString(KEY_PAN_CODE, "pan_code"); }

	public String getKeyPanName(){ return pref.getString(KEY_PAN_NAME, "pan_name"); }

	public String getKeyLabCode(){ return pref.getString(KEY_LAB_CODE, "lab_code"); }

	public String getKeyLabName(){ return pref.getString(KEY_LAB_NAME, "lab_name"); }

}