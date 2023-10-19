package com.example.photoeditor.Classs;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

/* loaded from: classes3.dex */
public class SaveFileUtils {
    private static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static File saveBitmapFileEditor(Context context, Bitmap bitmap, String str, String str2) throws IOException {
        String str3 = null;
        String str5 = null;
        if (Build.VERSION.SDK_INT < 29) {
            String str6 = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_PICTURES;
            File file = new File(str6);
            if (!file.exists()) {
                file.mkdir();
            }
            File file2 = new File(str6, str + ".jpg");
            OutputStream fileOutputStream3 = new FileOutputStream(file2);
            MediaScannerConnection.scanFile(context, new String[]{file2.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() { // from class: com.example.photoareditor.Classs.SaveFileUtils.1
                @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
                public void onMediaScannerConnected() {
                }

                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public void onScanCompleted(String str4, Uri uri) {
                }
            });
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream3);
            fileOutputStream3.flush();
            fileOutputStream3.close();
            return file2;
        } else if (!isExternalStorageWritable()) {
            return null;
        } else {
            if (str2 != null) {
                try {
                    str5 = Environment.DIRECTORY_PICTURES + File.separator + str2;
                } catch (Exception e2) {
                    try {
                        Log.e("File save exception", e2.getMessage());
                        str3 = null;
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                }
            } else {
                str5 = Environment.DIRECTORY_PICTURES;
            }
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str);
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("relative_path", str5);
            Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                str3 = FilePathUtil.getPath(context, insert);
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
            FileOutputStream fileOutputStream = (FileOutputStream) contentResolver.openOutputStream(insert);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e3) {
                String str4 = str3;
                Log.e("File save exception", e3.getMessage());
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                str3 = str4;
            }
            if (str3 == null) {
                return new File(str3);
            }
            return null;
        }
    }

    public static File saveBitmapFileCollage(Context context, Bitmap bitmap, String str, String str2) throws IOException {
        String str3 = null;
        String str5 = null;
        if (Build.VERSION.SDK_INT >= 29) {
            if (isExternalStorageWritable()) {
                if (str2 != null) {
                    try {
                        str5 = Environment.DIRECTORY_PICTURES + File.separator + str2;
                    } catch (Exception e2) {
                        try {
                            e2.getMessage();
                            str3 = null;
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                    }
                } else {
                    str5 = Environment.DIRECTORY_PICTURES;
                }
                ContentResolver contentResolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put("_display_name", str);
                contentValues.put("mime_type", "image/png");
                contentValues.put("relative_path", str5);
                Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    str3 = FilePathUtil.getPath(context, insert);
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
                FileOutputStream fileOutputStream = (FileOutputStream) contentResolver.openOutputStream(insert);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Exception e3) {
                    String str4 = str3;
                    e3.getMessage();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    str3 = str4;
                }
            } else {
                str3 = null;
            }
            if (str3 != null) {
                return null;
            }
            return new File(str3);
        }
        String str6 = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_PICTURES;
        File file = new File(str6);
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(str6, str + ".png");
        OutputStream fileOutputStream3 = new FileOutputStream(file2);
        MediaScannerConnection.scanFile(context, new String[]{file2.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() { // from class: com.example.photoareditor.Classs.SaveFileUtils.2
            @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
            public void onMediaScannerConnected() {
            }

            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String str7, Uri uri) {
            }
        });
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream3);
        fileOutputStream3.flush();
        fileOutputStream3.close();
        return file2;
    }

    public static Bitmap createBitmap(PolishGridView polishGridView, int i) {
        polishGridView.clearHandling();
        polishGridView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(i, (int) (i / (polishGridView.getWidth() / polishGridView.getHeight())), Bitmap.Config.ARGB_8888);
        polishGridView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap createBitmap(PolishGridView polishGridView) {
        polishGridView.clearHandling();
        polishGridView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(polishGridView.getWidth(), polishGridView.getHeight(), Bitmap.Config.ARGB_8888);
        polishGridView.draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
