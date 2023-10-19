package com.example.photoeditor.Classs;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class ImageHelper {
    public static String readFileName(Uri uri, ContentResolver contentResolver) {
        if (uri.toString().startsWith("file:")) {
            return uri.getPath();
        }
        String[] strArr = {"_display_name"};
        Cursor query = contentResolver.query(uri, strArr, null, null, null);
        if (query.moveToFirst()) {
            return query.getString(query.getColumnIndex(strArr[0]));
        }
        query.close();
        return null;
    }

    public static Bitmap loadSizeLimitedBitmapFromUri(Uri uri, ContentResolver contentResolver, int i) {
        try {
            InputStream openInputStream = contentResolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Rect rect = new Rect();
            BitmapFactory.decodeStream(openInputStream, rect, options);
            int i2 = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
            options.inSampleSize = 1;
            options.inSampleSize = calculateSampleSize(i2, i);
            options.inJustDecodeBounds = false;
            openInputStream.close();
            Bitmap decodeStream = BitmapFactory.decodeStream(contentResolver.openInputStream(uri), rect, options);
            double d = i;
            double width = decodeStream.getWidth() > decodeStream.getHeight() ? decodeStream.getWidth() : decodeStream.getHeight();
            Double.isNaN(d);
            Double.isNaN(width);
            double d2 = d / width;
            if (d2 < 1.0d) {
                double width2 = decodeStream.getWidth();
                Double.isNaN(width2);
                int i3 = (int) (width2 * d2);
                double height = decodeStream.getHeight();
                Double.isNaN(height);
                decodeStream = Bitmap.createScaledBitmap(decodeStream, i3, (int) (height * d2), false);
            }
            return rotateBitmap(decodeStream, getImageRotationAngle(uri, contentResolver));
        } catch (Exception unused) {
            Log.e("aaaaaa", "loadSizeLimitedBitmapFromUri: " + unused.getMessage());
            return null;
        }
    }

    private static int calculateSampleSize(int i, int i2) {
        int i3 = 1;
        while (i > i2 * 2) {
            i /= 2;
            i3 *= 2;
        }
        return i3;
    }

    private static int getImageRotationAngle(Uri uri, ContentResolver contentResolver) throws IOException {
        Cursor query = contentResolver.query(uri, new String[]{"orientation"}, null, null, null);
        int i = 0;
        if (query != null) {
            if (query.getCount() == 1) {
                query.moveToFirst();
                i = query.getInt(0);
            }
            query.close();
            return i;
        }
        int attributeInt = new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return 180;
        }
        if (attributeInt == 6) {
            return 90;
        }
        if (attributeInt != 8) {
            return 0;
        }
        return 270;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        if (i == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
