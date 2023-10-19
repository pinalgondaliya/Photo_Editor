package com.example.photoeditor.Classs;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import java.net.URISyntaxException;

/* loaded from: classes3.dex */
public class FilePathUtil {
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if (Build.VERSION.SDK_INT >= 29 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue());
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = split2[0];
                if ("image".equals(str2)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str2)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str2)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                Uri uri2 = uri;
                String[] strArr = {split2[1]};
                if (!"content".equalsIgnoreCase(uri2.getScheme())) {
                    try {
                        Cursor query = context.getContentResolver().query(uri2, new String[]{"_data"}, "_id=?", strArr, null);
                        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                        if (query.moveToFirst()) {
                            return query.getString(columnIndexOrThrow);
                        }
                    } catch (Exception unused) {
                        Log.e("aaaaaaa", "getPath: " + unused.getMessage());
                    }
                } else if ("file".equalsIgnoreCase(uri2.getScheme())) {
                    return uri2.getPath();
                }
                return null;
            }
        }
        "content".equalsIgnoreCase(uri.getScheme());
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
