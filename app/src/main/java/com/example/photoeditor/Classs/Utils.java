package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.core.content.FileProvider;

import com.example.photoeditor.Activity.PhotoSavedActivity;
import com.example.photoeditor.R;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class Utils {
    public static final String KEY_SELECTED_PHOTOS = "SELECTED_PHOTOS";
    private static File file;
    public static String Url = "https://thevidmix.com/photo_blender/photo_blender_sticker.php";
    public static String Catthumb = "https://thevidmix.com/photo_blender/cat_thumb/";
    public static String Sticker_thumb = "https://thevidmix.com/photo_blender/all/";
    public static String spiral_path = "https://photovideomakerwithmusic.com/photo_art_editor";
    public static String main_activity_thumb = "https://www.photovideomakerwithmusic.com/photo_art_effect";
    public static int art = 0;
    public static int editor = 0;
    public static int drip = 0;
    public static int art_1 = 0;
    public static int splash = 0;
    public static int motion = 0;
    public static int pixlab = 0;
    public static int collage = 0;
    public static int spiral = 0;
    public static int sketch = 0;
    public static boolean process = false;
    public static String pass_st1 = "";

    public static int dpToPx(Context context, int i) {
        return (int) (i * context.getResources().getDisplayMetrics().density);
    }

    public static boolean isOnline(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= 29) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))) {
                    return true;
                }
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    Log.i("update_statut", "Network is available : true");
                    return true;
                }
            }
        }
        Log.i("update_statut", "Network is available : FALSE ");
        return false;
    }

    public static boolean isNetworkConnectionAvailable(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                if (activeNetworkInfo.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean getBoolean(JSONObject jSONObject, String str, Boolean bool) {
        if (!jSONObject.has(str) || !jSONObject.optString(str, "BuildConfig.VERSION_NAME").equals("1")) {
            return bool;
        }
        return true;
    }

    public static void saveSubmission(Context context, Bitmap bitmap2) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/Photo Art Editor");
        myDir.mkdirs();
        Random generator = new Random();
        int n = generator.nextInt(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
        String fname = "Image-" + n + ".jpg";
        file = new File(myDir, fname);
        System.out.println("pathhhh====" + file);
        pass_st1 = file.getAbsolutePath();
        String path = file.getPath();
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            BitmapFactory.decodeFile(file.getAbsolutePath());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Classs.Utils.1
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String path2, Uri uri) {
            }
        });
        Intent intent = new Intent(context, PhotoSavedActivity.class);
        intent.putExtra(ClientCookie.PATH_ATTR, path);
        context.startActivity(intent);
    }

    public static void shareSubmission(Context context, Bitmap bitmap) {
        File saveImageToExternalStorage = saveImageToExternalStorage(context, bitmap);
        if (saveImageToExternalStorage != null) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/jpeg");
            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(context, context.getPackageName() + ".provider", saveImageToExternalStorage));
            intent.putExtra("android.intent.extra.TEXT", context.getString(R.string.share_img) + "\n" + context.getString(R.string.app_name) + "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName() + "");
            context.startActivity(Intent.createChooser(intent, "Share Image"));
        }
    }

    private static File saveImageToExternalStorage(Context context, Bitmap bitmap) {
        File saveFile = getSaveFile(context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(saveFile));
            context.sendBroadcast(intent);
            return saveFile;
        } catch (Exception e) {
            return null;
        }
    }

    public static File getSaveFile(Context context) {
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + context.getString(R.string.app_name));
        file2.mkdirs();
        File file22 = new File(file2, "Img_Art-" + ((int) System.currentTimeMillis()) + ".jpg");
        if (file22.exists()) {
            file22.delete();
        }
        return file22;
    }

    public static Bitmap addWatermark(Context context, Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(7);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.watermark);
        int min = Math.min(bitmap.getHeight() / decodeResource.getHeight(), bitmap.getWidth() / decodeResource.getWidth());
        int width = (decodeResource.getWidth() * min) / 3;
        int height = (decodeResource.getHeight() * min) / 3;
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeResource, width, height, true);
        Matrix matrix = new Matrix();
        matrix.mapRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()));
        matrix.postTranslate(bitmap.getWidth() - width, bitmap.getHeight() - height);
        paint.setAlpha(153);
        canvas.drawBitmap(createScaledBitmap, matrix, paint);
        createScaledBitmap.recycle();
        return createBitmap;
    }

    public static Bitmap applyFilter(Bitmap bitmap, Bitmap bitmap2, int i) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap2.getWidth(), bitmap2.getHeight(), true);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), createScaledBitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        Paint paint = new Paint();
        paint.setAlpha((int) (i * 2.55f));
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        return createBitmap;
    }
}
