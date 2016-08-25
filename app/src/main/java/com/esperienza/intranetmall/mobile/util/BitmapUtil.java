package com.esperienza.intranetmall.mobile.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;

import java.io.InputStream;


/**
 * Created by ThinkPad on 15/12/2015.
 */
public class BitmapUtil {

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bm.getWidth();
        float ratioY = newHeight / (float) bm.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bm, middleX - bm.getWidth() / 2, middleY - bm.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }


}
