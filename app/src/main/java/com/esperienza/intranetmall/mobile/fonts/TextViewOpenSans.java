package com.esperienza.intranetmall.mobile.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ThinkPad on 25/11/2015.
 */
public class TextViewOpenSans extends TextView {

    public TextViewOpenSans(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        alterarFonte(context);

    }

    public TextViewOpenSans(Context context, AttributeSet attrs) {

        super(context, attrs);

        alterarFonte(context);

    }

    public TextViewOpenSans(Context context) {

        super(context);

        alterarFonte(context);

    }

    private void alterarFonte(Context context) {

        if (!isInEditMode()) {

            Typeface type = Typeface.createFromAsset(

                    context.getAssets(),

                    "fonts/OpenSans-Regular.ttf");

            setTypeface(type);

        }

    }

}
