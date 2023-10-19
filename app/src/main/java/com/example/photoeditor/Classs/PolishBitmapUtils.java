package com.example.photoeditor.Classs;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class PolishBitmapUtils {
    PolishBitmapUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Bitmap removeTransparency(Bitmap bitmap) {
        int i;
        int i2;
        int i3;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int i4 = 0;
        loop0: while (true) {
            if (i4 >= bitmap.getWidth()) {
                i = 0;
                break;
            }
            for (int i5 = 0; i5 < bitmap.getHeight(); i5++) {
                if (iArr[(bitmap.getWidth() * i5) + i4] != 0) {
                    i = i4;
                    break loop0;
                }
            }
            i4++;
        }
        int i6 = 0;
        loop2: while (true) {
            if (i6 >= bitmap.getHeight()) {
                i2 = 0;
                break;
            }
            for (int i7 = i; i7 < bitmap.getHeight(); i7++) {
                if (iArr[(bitmap.getWidth() * i6) + i7] != 0) {
                    i2 = i6;
                    break loop2;
                }
            }
            i6++;
        }
        int i72 = bitmap.getWidth();
        int width2 = i72 - 1;
        loop4: while (true) {
            if (width2 < i) {
                i3 = width;
                break;
            }
            int i32 = bitmap.getHeight();
            for (int height2 = i32 - 1; height2 >= i2; height2--) {
                if (iArr[(bitmap.getWidth() * height2) + width2] != 0) {
                    i3 = width2;
                    break loop4;
                }
            }
            width2--;
        }
        int height22 = bitmap.getHeight();
        int height3 = height22 - 1;
        loop6: while (true) {
            if (height3 < i2) {
                break;
            }
            for (int width3 = bitmap.getWidth() - 1; width3 >= i; width3--) {
                if (iArr[(bitmap.getWidth() * height3) + width3] != 0) {
                    height = height3;
                    break loop6;
                }
            }
            height3--;
        }
        int width32 = i3 - i;
        return Bitmap.createBitmap(bitmap, i, i2, width32, height - i2);
    }
}
