package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
public class StickerUtils {
    public static void notifySystemGallery(Context context, File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("bmp should not be null");
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), (String) null);
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File couldn't be found");
        }
    }

    public static File saveImageToGallery(File file, Bitmap bitmap) {
        if (bitmap != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("StickerView", "saveImageToGallery: the path of bmp is " + file.getAbsolutePath());
            return file;
        }
        throw new IllegalArgumentException("bmp should not be null");
    }

    public static void trapToRect(RectF rectF, float[] fArr) {
        float f;
        float f2;
        rectF.set(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (int i = 1; i < fArr.length; i += 2) {
            float round = Math.round(fArr[i - 1] * 10.0f) / 10.0f;
            float round2 = Math.round(fArr[i] * 10.0f) / 10.0f;
            if (round < rectF.left) {
                f = round;
            } else {
                f = rectF.left;
            }
            rectF.left = f;
            if (round2 < rectF.top) {
                f2 = round2;
            } else {
                f2 = rectF.top;
            }
            rectF.top = f2;
            if (round <= rectF.right) {
                round = rectF.right;
            }
            rectF.right = round;
            if (round2 <= rectF.bottom) {
                round2 = rectF.bottom;
            }
            rectF.bottom = round2;
        }
        rectF.sort();
    }
}
