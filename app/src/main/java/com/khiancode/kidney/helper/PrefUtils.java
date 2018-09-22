package com.khiancode.kidney.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;

public class PrefUtils {
    private static final String ADD_LOGIN = "login";
    private static final String ADD_REALM = "addrealm";
    private static final String ADD_TOKEN = "access_token";
    private static final String ADD_NAME = "add_name";
    private static final String ADD_PHONE = "add_phone";
    private static final String ADD_DAY = "add_day";
    private static final String SODIUM_VALUE = "sodiumvalue";
    private SharedPreferences mPreferences;

    public PrefUtils(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getLoginStatus() {
        return mPreferences.getBoolean(ADD_LOGIN, false);
    }

    public void setLoginStatus(boolean login) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(ADD_LOGIN, login);
        editor.apply();
    }

    public String getAccessToken() {
        return mPreferences.getString(ADD_TOKEN,"n");
    }

    public void setAccessToken(String access_token) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(ADD_TOKEN, access_token);
        editor.apply();
    }

    public String getName() {
        return mPreferences.getString(ADD_NAME,"n");
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(ADD_NAME, name);
        editor.apply();
    }

    public String getDay() {
        return mPreferences.getString(ADD_DAY,"n");
    }

    public void setDay(String day) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(ADD_DAY, day);
        editor.apply();
    }

    public String getPhone() {
        return mPreferences.getString(ADD_PHONE,"n");
    }

    public void setPhone(String phone) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(ADD_PHONE, phone);
        editor.apply();
    }

    public boolean getRealmStatus() {
        return mPreferences.getBoolean(ADD_REALM, false);
    }

    public void setRealmStatus(boolean login) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(ADD_REALM, login);
        editor.apply();
    }

    public float getSodiumValue() {
        return mPreferences.getFloat(ADD_REALM, 0.0f);
    }

    public void setSodiumValue(float value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putFloat(ADD_REALM, value);
        editor.apply();
    }
}
