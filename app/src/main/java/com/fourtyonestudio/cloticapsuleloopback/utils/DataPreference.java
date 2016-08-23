package com.fourtyonestudio.cloticapsuleloopback.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Riris.
 */
public class DataPreference {
    private static final String PREF_NAME = "Cloticap";
    private static final String TOKEN = "token";
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public DataPreference(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public String getToken() {
        return pref.getString(TOKEN, null);
    }

    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }

}
