package com.esperienza.intranetmall.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ThinkPad on 11/05/2016.
 */
public class SessionManager {

    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("intranetmall", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }
    public String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("intranetmall", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}
