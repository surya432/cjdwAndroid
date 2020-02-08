package com.suryaheho.projectb.Helper;


/**
 * Created by rizky on 03/09/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // Shared preferences file name
    public static final String PREF_NAME = "simpleRunningAssistant";
    public static final String IS_LOGGEDIN = "isLoggedIn";
    public static final String MEMBERCODE = "MemberCode";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ROLE = "role";
    public static final String KEY_TOKEN_GCM = "token_gcm";
    private static final String KEY_ID_USER = "key";
    private static final String KEY_PID = "pid";
    private static final String KEY_USERID = "id_user";
    private static final String KEY_BANNER = "false";
    private static final String KEY_TOKEN = "Bearer";

    private static final String KEY_MEMBERID = "memberoid";
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void setLogin(boolean isLoggedIn, String pid, String name, String email, String token) {

        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_PID, pid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_TOKEN, token);
        // commit changes
        editor.apply();

        Log.d(TAG, "User login session modified!");
    }

    public void setLoginVendor(boolean isLoggedIn, String pid, String name, int memberoid) {

        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_PID, pid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MEMBERID, String.valueOf(memberoid));
        // commit changes
        editor.apply();

        Log.d(TAG, "User login session modified!");
    }

    public void setJumlah(String jml) {
        editor.putString(KEY_NAME, jml);
        editor.commit();
    }

    public void setToken(String token) {
        editor.putString(KEY_TOKEN_GCM, token);
        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGEDIN, false);
    }

    public String getName() {
        return pref.getString(KEY_NAME, "null");
    }

    public String getPID() {
        return pref.getString(KEY_PID, "null");
    }

    public String getKey() {
        return pref.getString(KEY_ID_USER, "null");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "null");
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "null");
    }

    public String getRole() {
        return pref.getString(KEY_ROLE, "null");
    }

    public String getBanner() {
        return pref.getString(KEY_BANNER, "true");
    }


    public String getKeyMemberid() {
        return pref.getString(KEY_MEMBERID, "null");
    }

    public void destroySession() {


        editor.putString(KEY_PID, "");
        editor.putString(KEY_NAME, "");
        editor.putBoolean(IS_LOGGEDIN, false);
        editor.putString(MEMBERCODE, "");
        editor.putString(KEY_TOKEN_GCM, "");
        editor.putString(KEY_TOKEN, "");
        editor.putString(KEY_USERID, "");
        editor.putString(KEY_BANNER, "true");
        editor.putString(KEY_MEMBERID, "");
        editor.clear();
        editor.apply();
        Log.d(TAG, "User login session destroyed!");
//        Intent intent = new Intent(_context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        _context.startActivity(intent);

    }

    public void updateData(String codemember) {
        editor.putString(MEMBERCODE, codemember);
        editor.commit();
    }

    public void InsertSession(String codemember) {
        editor.putString(MEMBERCODE, codemember);
        editor.commit();
    }

    public void updateDataSession(String Key, String Value) {
        editor.putString(Key, Value);
        editor.commit();
    }

    public void bannerUpdate(String Value) {
        editor.putString(KEY_BANNER, Value);
        editor.commit();
    }


    public String getData(String key) {
        return pref.getString(key, "");
    }

    public String getMemberCode() {
        return pref.getString(MEMBERCODE, "");
    }

    public String getKeyTokenGcm() {
        return pref.getString(KEY_TOKEN_GCM, "");
    }

    public String getKeyToken() {
        return pref.getString(KEY_TOKEN, "");
    }
}

