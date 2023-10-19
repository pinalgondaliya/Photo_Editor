package com.example.photoeditor.DripClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.example.photoeditor.R;

import java.io.IOException;

/* loaded from: classes4.dex */
public class ImageUtils {
    public static Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        float f = i2;
        float width = bitmap.getWidth();
        float f2 = i;
        float height = bitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        float f3 = width * max;
        float f4 = max * height;
        float f5 = (f - f3) / 2.0f;
        float f6 = (f2 - f4) / 2.0f;
        RectF rectF = new RectF(f5, f6, f3 + f5, f4 + f6);
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
        return createBitmap;
    }

    public static Bitmap getResampleImageBitmap(Uri uri, Context context) throws IOException {
        String realPathFromURI = getRealPathFromURI(uri, context);
        try {
            return resampleImage(realPathFromURI, 800);
        } catch (Exception e) {
            e.printStackTrace();
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(realPathFromURI));
        }
    }

    public static Bitmap getResampleImageBitmap(Uri uri, Context context, int i) throws IOException {
        try {
            return resampleImage(getRealPathFromURI(uri, context), i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap resampleImage(String str, int i) throws Exception {
        int exifRotation;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = getClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
            if (decodeFile == null) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (decodeFile.getWidth() > i || decodeFile.getHeight() > i) {
                BitmapFactory.Options resampling = getResampling(decodeFile.getWidth(), decodeFile.getHeight(), i);
                matrix.postScale(resampling.outWidth / decodeFile.getWidth(), resampling.outHeight / decodeFile.getHeight());
            }
            if (new Integer(Build.VERSION.SDK).intValue() > 4 && (exifRotation = getExifRotation(str)) != 0) {
                matrix.postRotate(exifRotation);
            }
            return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getExifRotation(String str) {
        try {
            String attribute = new ExifInterface(str).getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION);
            if (TextUtils.isEmpty(attribute)) {
                return 0;
            }
            int parseInt = Integer.parseInt(attribute);
            if (parseInt == 3) {
                return 180;
            }
            if (parseInt == 6) {
                return 90;
            }
            if (parseInt != 8) {
                return 0;
            }
            return 270;
        } catch (Exception e) {
            return 0;
        }
    }

    static BitmapFactory.Options getResampling(int i, int i2, int i3) {
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

    public static BitmapFactory.Options getBitmapDims(Uri uri, Context context) {
        String realPathFromURI = getRealPathFromURI(uri, context);
        Log.i("texting", "Path " + realPathFromURI);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(realPathFromURI, options);
        return options;
    }

    public static String getRealPathFromURI(Uri uri, Context context) {
        try {
            Cursor query = context.getContentResolver().query(uri, null, null, null, null);
            if (query == null) {
                return uri.getPath();
            }
            query.moveToFirst();
            @SuppressLint("Range") String string = query.getString(query.getColumnIndex("_data"));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return uri.toString();
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        float f = i;
        float f2 = i2;
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Log.i("testings", f + "  " + f2 + "  and  " + width + "  " + height);
        float f3 = width / height;
        float f4 = height / width;
        if (width > f) {
            float f5 = f4 * f;
            Log.i("testings", "if (wd > wr) " + f + "  " + f5);
            if (f5 > f2) {
                float f6 = f3 * f2;
                Log.i("testings", "  if (he > hr) " + f6 + "  " + f2);
                return Bitmap.createScaledBitmap(bitmap, (int) f6, (int) f2, false);
            }
            Log.i("testings", " in else " + f + "  " + f5);
            return Bitmap.createScaledBitmap(bitmap, (int) f, (int) f5, false);
        }
        if (height > f2) {
            float f7 = f3 * f2;
            Log.i("testings", "  if (he > hr) " + f7 + "  " + f2);
            if (f7 <= f) {
                Log.i("testings", " in else " + f7 + "  " + f2);
                return Bitmap.createScaledBitmap(bitmap, (int) f7, (int) f2, false);
            }
        } else if (f3 > 0.75f) {
            float f8 = f * f4;
            Log.i("testings", " if (rat1 > .75f) ");
            if (f8 > f2) {
                float f9 = f3 * f2;
                Log.i("testings", "  if (he > hr) " + f9 + "  " + f2);
                return Bitmap.createScaledBitmap(bitmap, (int) f9, (int) f2, false);
            }
            Log.i("testings", " in else " + f + "  " + f8);
        } else if (f4 > 1.5f) {
            float f10 = f3 * f2;
            Log.i("testings", " if (rat2 > 1.5f) ");
            if (f10 <= f) {
                Log.i("testings", " in else " + f10 + "  " + f2);
                return Bitmap.createScaledBitmap(bitmap, (int) f10, (int) f2, false);
            }
        } else {
            float f11 = f * f4;
            Log.i("testings", " in else ");
            if (f11 > f2) {
                float f12 = f3 * f2;
                Log.i("testings", "  if (he > hr) " + f12 + "  " + f2);
                return Bitmap.createScaledBitmap(bitmap, (int) f12, (int) f2, false);
            }
            Log.i("testings", " in else " + f + "  " + f11);
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) (f4 * f), false);
    }

    public static int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(Context context, float f) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    public static Bitmap CropBitmapTransparency(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < bitmap.getHeight(); i3++) {
            for (int i4 = 0; i4 < bitmap.getWidth(); i4++) {
                if (((bitmap.getPixel(i4, i3) >> 24) & 255) > 0) {
                    if (i4 < width) {
                        width = i4;
                    }
                    if (i4 > i) {
                        i = i4;
                    }
                    if (i3 < height) {
                        height = i3;
                    }
                    if (i3 > i2) {
                        i2 = i3;
                    }
                }
            }
        }
        if (i < width || i2 < height) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, width, height, (i - width) + 1, (i2 - height) + 1);
    }

    public static Bitmap bitmapmasking(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static Bitmap getBgCircleBit(Context context, int i) {
        int dpToPx = dpToPx(context, 150);
        return bitmapmasking1(getTiledBitmap(context, i, dpToPx, dpToPx), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.circle1), dpToPx, dpToPx, true));
    }

    public static Bitmap bitmapmasking1(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }
}
