package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes3.dex */
public class ImageUtils {
    public static Bitmap getBitmapResize(Context context, Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width >= height) {
            int i3 = (height * i) / width;
            if (i3 > i2) {
                i = (i * i2) / i3;
            } else {
                i2 = i3;
            }
        } else {
            int i4 = (width * i2) / height;
            if (i4 > i) {
                i2 = (i2 * i) / i4;
            } else {
                i = i4;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }

    public static BitmapFactory.Options getResampling(int i, int i2, int i3) {
        float f;
        float f2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i > i2 || i2 <= i) {
            f = i3;
            f2 = i;
        } else {
            f = i3;
            f2 = i2;
        }
        float f3 = f / f2;
        options.outWidth = (int) ((i * f3) + 0.5f);
        options.outHeight = (int) ((i2 * f3) + 0.5f);
        return options;
    }

    public static int getClosestResampleSize(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }

    public static Bitmap getBitmapFromAsset(Context context, String str) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str));
        } catch (IOException e) {
            Log.e("", "");
            return null;
        }
    }

    public static Bitmap getMask(Context context, Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, i, i2, true);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap2.getWidth(), createScaledBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    public static int dpToPx(Context context, float f) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }
}
