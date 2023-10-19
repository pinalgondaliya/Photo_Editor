package com.example.photoeditor.Classs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.view.ViewCompat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class BitmapUtils {
    private static final int f136a = 15;
    private static final int f137b = 10;
    private static int[] f138c = new int[256];
    private static int[] f139d = new int[256];
    private static int[] f140e = new int[256];
    private static int[] f141f = new int[256];

    public static int estimateSampleSize(String str, int i, int i2) {
        return estimateSampleSize(str, i, i2, 0);
    }

    public static int estimateSampleSize(String str, int i, int i2, int i3) {
        if (str == null || i <= 0 || i2 <= 0) {
            return 0;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e) {
        }
        int i4 = options.outWidth;
        int i5 = options.outHeight;
        if (i3 == 90 || i3 == 270) {
            i4 = options.outHeight;
            i5 = options.outWidth;
        }
        return Math.min(i4 / i, i5 / i2);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        if (i != 0 && bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(i, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
            try {
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (bitmap == createBitmap) {
                    return bitmap;
                }
                bitmap.recycle();
                return createBitmap;
            } catch (OutOfMemoryError e) {
            }
        }
        return bitmap;
    }

    public static Bitmap scaleBitmapRatioLocked(Bitmap bitmap, int i, int i2) {
        int i3;
        if (bitmap == null) {
            return null;
        }
        int min = Math.min(i, i2);
        if (min <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > height) {
            i3 = (height * min) / width;
        } else if (width < height) {
            int i4 = (width * min) / height;
            min = i4;
            i3 = min;
        } else {
            i3 = min;
        }
        return scaleBitmap(bitmap, min, i3);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        if (i <= 0 || i2 <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > i) {
            if (height <= i2) {
                return createClippedBitmap(bitmap, (bitmap.getWidth() - i) / 2, 0, i, height);
            }
            float f = i / width;
            float f2 = i2 / height;
            if (f > f2) {
                Bitmap m48a = m48a(bitmap, f, width, height);
                return m48a != null ? createClippedBitmap(m48a, 0, (m48a.getHeight() - i2) / 2, i, i2) : bitmap;
            }
            Bitmap m48a2 = m48a(bitmap, f2, width, height);
            return m48a2 != null ? createClippedBitmap(m48a2, (m48a2.getWidth() - i) / 2, 0, i, i2) : bitmap;
        } else if (width > i) {
            return bitmap;
        } else {
            if (height > i2) {
                return createClippedBitmap(bitmap, 0, (bitmap.getHeight() - i2) / 2, width, i2);
            }
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, bitmap.getConfig());
            new Canvas(createBitmap).drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, i, i2), new Paint(1));
            return createBitmap;
        }
    }

    private static Bitmap m48a(Bitmap bitmap, float f, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, i, i2, matrix, true);
    }

    public static Bitmap createClippedBitmap(Bitmap bitmap, int i, int i2, int i3, int i4) {
        if (bitmap == null) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, i, i2, i3, i4);
    }

    public static boolean saveBitmap(Bitmap bitmap, String str) {
        return saveBitmap(bitmap, str, 100);
    }

    public static boolean saveBitmap(Bitmap bitmap, String str, int i) {
        if (str == null) {
            return false;
        }
        return saveBitmap(bitmap, new File(str), i);
    }

    public static boolean saveBitmap(Bitmap bitmap, File file) {
        return saveBitmap(bitmap, file, 100);
    }

    public static boolean saveBitmap(Bitmap bitmap, File file, int i) {
        if (bitmap != null && file != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                boolean compress = bitmap.compress(i >= 100 ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, i, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return compress;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static Bitmap createColorFilteredBitmap(Bitmap bitmap, ColorMatrix colorMatrix) {
        if (bitmap != null && colorMatrix != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > 0 && height > 0) {
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
                Paint paint = new Paint();
                paint.setColorFilter(colorMatrixColorFilter);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                return createBitmap;
            }
        }
        return bitmap;
    }

    public static Bitmap createGrayScaledBitmap(Bitmap bitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        return createColorFilteredBitmap(bitmap, colorMatrix);
    }

    public static String bitmapToBase64String(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public static Bitmap bitmapFromBase64String(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] decode = Base64.decode(str, 0);
            if (decode != null && decode.length > 0) {
                return BitmapFactory.decodeByteArray(decode, 0, decode.length);
            }
        } catch (OutOfMemoryError e) {
        }
        return null;
    }

    public static Bitmap compositeDrawableWithMask(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap2 == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int width2 = bitmap2.getWidth();
        int height2 = bitmap2.getHeight();
        if (width != width2 || height != height2) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] iArr = new int[width];
        int[] iArr2 = new int[width];
        int i = 0;
        while (i < height) {
            bitmap.getPixels(iArr, 0, width, 0, i, width, 1);
            int i2 = i;
            bitmap2.getPixels(iArr2, 0, width, 0, i, width, 1);
            for (int i22 = 0; i22 < width; i22++) {
                iArr[i22] = (iArr[i22] & ViewCompat.MEASURED_SIZE_MASK) | ((iArr2[i22] << 8) & ViewCompat.MEASURED_STATE_MASK);
            }
            createBitmap.setPixels(iArr, 0, width, 0, i2, width, 1);
            i = i2 + 1;
        }
        return createBitmap;
    }

    public static Bitmap compositeBitmaps(Bitmap bitmap, Bitmap bitmap2) {
        return compositeBitmaps(false, bitmap, bitmap2);
    }

    public static Bitmap compositeBitmaps(boolean z, Bitmap bitmap, Bitmap bitmap2) {
        return compositeBitmaps(z, bitmap, bitmap2);
    }

    public static Bitmap compositeBitmaps(Bitmap... bitmapArr) {
        return compositeBitmaps(false, bitmapArr);
    }

    public static Bitmap compositeBitmaps(boolean z, Bitmap... bitmapArr) {
        int width;
        Bitmap bitmap;
        int[] findMaxDimension;
        if (bitmapArr == null) {
            return null;
        }
        if (bitmapArr.length == 1) {
            return bitmapArr[0];
        }
        if (bitmapArr[0] == null) {
            return bitmapArr[0];
        }
        int width2 = bitmapArr[0].getWidth();
        int height = bitmapArr[0].getHeight();
        Bitmap.Config config = bitmapArr[0].getConfig();
        if (!z && (findMaxDimension = findMaxDimension(bitmapArr)) != null) {
            int width3 = findMaxDimension[0];
            height = findMaxDimension[1];
            width = width3;
        } else {
            width = width2;
        }
        try {
            bitmap = Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            bitmap = null;
        }
        if (bitmap == null) {
            return bitmapArr[0];
        }
        Canvas canvas = new Canvas(bitmap);
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        for (Bitmap bitmap2 : bitmapArr) {
            if (bitmap2 != null) {
                if (bitmap2.getWidth() != width || bitmap2.getHeight() != height) {
                    if (z) {
                        bitmap2 = scaleBitmap(bitmap2, width, height);
                    } else {
                        int width22 = (width - bitmap2.getWidth()) / 2;
                        int height2 = (height - bitmap2.getHeight()) / 2;
                        rect.set(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
                        rect2.set(width22, height2, bitmap2.getWidth() + width22, bitmap2.getHeight() + height2);
                        canvas.drawBitmap(bitmap2, rect, rect2, (Paint) null);
                    }
                }
                rect.set(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
                rect2.set(0, 0, bitmap2.getWidth() + 0, bitmap2.getHeight() + 0);
                canvas.drawBitmap(bitmap2, rect, rect2, (Paint) null);
            }
        }
        return bitmap;
    }

    public static int[] findMaxDimension(Bitmap... bitmapArr) {
        if (bitmapArr == null) {
            return null;
        }
        int[] iArr = {0, 0};
        if (bitmapArr.length != 1) {
            for (Bitmap bitmap : bitmapArr) {
                if (bitmap != null) {
                    if (bitmap.getWidth() > iArr[0]) {
                        iArr[0] = bitmap.getWidth();
                    }
                    if (bitmap.getHeight() > iArr[1]) {
                        iArr[1] = bitmap.getHeight();
                    }
                }
            }
            return iArr;
        } else if (bitmapArr[0] == null) {
            return iArr;
        } else {
            iArr[0] = bitmapArr[0].getWidth();
            iArr[1] = bitmapArr[0].getHeight();
            return iArr;
        }
    }

    public static Bitmap getRoundBitmap(Bitmap bitmap, int i) {
        if (bitmap.getWidth() != i || bitmap.getHeight() != i) {
            int i2 = i * 2;
            bitmap = scaleBitmap(bitmap, i2, i2);
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static int calculateBrightnessEstimate(Bitmap bitmap, int i) {
        int i2 = 0;
        if (bitmap == null) {
            return 0;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= 0 || height <= 0) {
            return 0;
        }
        int i3 = width * height;
        int[] iArr = new int[i3];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i2 < i3) {
            int i8 = iArr[i2];
            i4 += Color.red(i8);
            i6 += Color.green(i8);
            i5 += Color.blue(i8);
            i7++;
            i2 += i;
        }
        return ((i4 + i5) + i6) / (i7 * 3);
    }

    public static int calculateBrightness(Bitmap bitmap) {
        return calculateBrightnessEstimate(bitmap, 1);
    }

    public static Bitmap extendBitmap(Bitmap bitmap, int i, int i2, int i3) {
        if (bitmap != null && i > 0 && i2 > 0) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (i >= width && i2 >= height) {
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                Paint paint = new Paint(1);
                canvas.drawColor(i3);
                double d = i - width;
                Double.isNaN(d);
                double d2 = i2 - height;
                Double.isNaN(d2);
                canvas.drawBitmap(bitmap, (int) Math.round(d / 2.0d), (int) Math.round(d2 / 2.0d), paint);
                saveBitmap(createBitmap, "/sdcard/newone.png");
                return createBitmap;
            }
        }
        return bitmap;
    }

    public static Bitmap oilPaintBitmap(Bitmap bitmap) {
        return oilPaintBitmap(bitmap, 15, 10);
    }

    public static Bitmap oilPaintBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= 0 || height <= 0 || i <= 0 || i2 <= 0) {
            return bitmap;
        }
        System.currentTimeMillis();
        int i3 = width * height;
        int[] iArr = new int[i3];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int[] copyOf = Arrays.copyOf(iArr, i3);
        for (int i4 = i; i4 < height - i; i4++) {
            int i13 = i;
            while (i13 < width - i) {
                Arrays.fill(f138c, 0);
                Arrays.fill(f139d, 0);
                Arrays.fill(f140e, 0);
                Arrays.fill(f141f, 0);
                int i6 = -i;
                for (int i7 = i6; i7 <= i; i7++) {
                    int i8 = i6;
                    while (i8 <= i) {
                        int i9 = iArr[i13 + i8 + ((i4 + i7) * width)];
                        int i10 = (i9 >> 16) & 255;
                        int i11 = (i9 >> 8) & 255;
                        int i12 = i9 & 255;
                        int i62 = i6;
                        int i32 = i3;
                        int[] iArr2 = iArr;
                        double d = i10 + i11 + i12;
                        Double.isNaN(d);
                        int i5 = i13;
                        double d2 = i2;
                        Double.isNaN(d2);
                        int i132 = (int) (((d / 3.0d) * d2) / 255.0d);
                        int i14 = 255;
                        if (i132 <= 255) {
                            i14 = i132;
                        }
                        int[] iArr22 = f138c;
                        iArr22[i14] = iArr22[i14] + 1;
                        int[] iArr3 = f139d;
                        iArr3[i14] = iArr3[i14] + i10;
                        int[] iArr4 = f140e;
                        iArr4[i14] = iArr4[i14] + i11;
                        int[] iArr5 = f141f;
                        iArr5[i14] = iArr5[i14] + i12;
                        i8++;
                        i13 = i5;
                        i6 = i62;
                        i3 = i32;
                        iArr = iArr2;
                    }
                }
                int i52 = i13;
                int i33 = i3;
                int[] iArr6 = iArr;
                int i15 = 0;
                int i16 = 0;
                for (int i17 = 0; i17 < 256; i17++) {
                    int[] iArr62 = f138c;
                    if (iArr62[i17] > i15) {
                        i15 = iArr62[i17];
                        i16 = i17;
                    }
                }
                int i172 = i4 * width;
                copyOf[i172 + i52] = ((f139d[i16] / i15) << 16) | ViewCompat.MEASURED_STATE_MASK | ((f140e[i16] / i15) << 8) | (f141f[i16] / i15);
                i13 = i52 + 1;
                i3 = i33;
                iArr = iArr6;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(copyOf, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        System.currentTimeMillis();
        return createBitmap;
    }
}
