package com.example.photoeditor.Classs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import com.example.photoeditor.R;

import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes3.dex */
public class StoreManager {
    private static String BITMAP_CROPED_FILE_NAME = "temp_croped_bitmap.png";
    private static String BITMAP_CROPED_MASK_FILE_NAME = "temp_croped_mask_bitmap.png";
    private static String BITMAP_FILE_NAME = "temp_bitmap.png";
    private static String BITMAP_ORIGINAL_FILE_NAME = "temp_original_bitmap.png";
    public static int croppedLeft = 0;
    public static int croppedTop = 0;
    public static boolean isNull = false;

    public static Bitmap getCurrentCroppedMaskBitmap(Activity activity) {
        if (isNull) {
            return null;
        }
        return getBitmapByFileName(activity, BITMAP_CROPED_MASK_FILE_NAME);
    }

    public static Bitmap getCurrentCropedBitmap(Activity activity) {
        if (isNull) {
            return null;
        }
        return getBitmapByFileName(activity, BITMAP_CROPED_FILE_NAME);
    }

    public static Bitmap getCurrentOriginalBitmap(Activity activity) {
        return getBitmapByFileName(activity, BITMAP_ORIGINAL_FILE_NAME);
    }

    private static Bitmap getBitmapByFileName(Activity activity, String str) {
        return BitmapFactory.decodeFile(getWorkspaceDirPath(activity) + "/" + str);
    }

    private static String getWorkspaceDirPath(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath() + context.getResources().getString(R.string.directory);
    }

    public static void saveFile(Context context, Bitmap bitmap, String str) {
        if (bitmap != null) {
            File file = new File(getWorkspaceDirPath(context));
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str);
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    fileOutputStream2.flush();
                    try {
                        fileOutputStream2.close();
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public static void deleteFile(Context context, String str) {
        File file = new File(getWorkspaceDirPath(context) + "/" + str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void setCurrentCropedBitmap(Activity activity, Bitmap bitmap) {
        if (bitmap == null) {
            deleteFile(activity, BITMAP_CROPED_FILE_NAME);
            isNull = true;
        } else {
            isNull = false;
        }
        saveFile(activity, bitmap, BITMAP_CROPED_FILE_NAME);
    }

    public static void setCurrentCroppedMaskBitmap(Activity activity, Bitmap bitmap) {
        if (bitmap == null) {
            deleteFile(activity, BITMAP_CROPED_MASK_FILE_NAME);
        }
        saveFile(activity, bitmap, BITMAP_CROPED_MASK_FILE_NAME);
    }

    public static void setCurrentOriginalBitmap(Activity activity, Bitmap bitmap) {
        if (bitmap == null) {
            deleteFile(activity, BITMAP_ORIGINAL_FILE_NAME);
        }
        saveFile(activity, bitmap, BITMAP_ORIGINAL_FILE_NAME);
    }
}
