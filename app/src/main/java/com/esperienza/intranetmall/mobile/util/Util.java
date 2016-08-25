package com.esperienza.intranetmall.mobile.util;

/**
 * Created by ThinkPad on 23/11/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Andre on 30/09/2014.
 */
public class Util {

    public static void hideKeyboard(Activity a) {
        InputMethodManager inputManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = a.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public static void setInsets(Activity context, View view) {
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            return;
        }*/
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
    }
}