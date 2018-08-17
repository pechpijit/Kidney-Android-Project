package com.khiancode.kidney.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;

public class PrefUtils {
    private static final String ADD_REALM = "addrealm";
    private static final String SODIUM_VALUE = "sodiumvalue";
    private SharedPreferences mPreferences;

    public PrefUtils(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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
