package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.example.photoeditor.ModelClass.Submission;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes3.dex */
public class ServiceApi {
    private static final String SERVICE_ENDPOINT = "https://www.deeparteffects.com/service/";
    private static final String TAG = "ServiceApi";
    private static final int TIMEOUT = 30000;
    public static final String WEBSITE_ENDPOINT = "https://www.deeparteffects.com/";
    private static final String APPLICATION_ID = Polish.getApp_id();
    public static final String VersionName = Polish.getVersion_name();
    public static final SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static void authenticate(String str, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.add("u", URLEncoder.encode(str, "UTF-8"));
            try {
                requestParams.add("p", URLEncoder.encode(str2, "UTF-8"));
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(TIMEOUT);
                asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "Authenticate", VersionName));
                asyncHttpClient.get("https://www.deeparteffects.com/service/Authenticate", requestParams, asyncHttpResponseHandler);
            } catch (Exception e) {
                Log.e(TAG, "Error encode password");
            }
        } catch (Exception e2) {
            Log.e(TAG, "Error encode user");
        }
    }

    public static void getSessionTokenV1(AsyncHttpResponseHandler asyncHttpResponseHandler) {
        RequestParams requestParams = new RequestParams();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        requestParams.add("applicationId", APPLICATION_ID);
        asyncHttpClient.setTimeout(TIMEOUT);
        asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "GetSessionTokenV1", VersionName));
        asyncHttpClient.get("https://www.deeparteffects.com/service/GetSessionTokenV1", requestParams, asyncHttpResponseHandler);
    }

    public static void getStyles(AsyncHttpResponseHandler asyncHttpResponseHandler, boolean z) {
        RequestParams requestParams = new RequestParams();
        if (z) {
            requestParams.add("t", "p");
        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(TIMEOUT);
        asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "ListStyles", VersionName));
        asyncHttpClient.get("https://www.deeparteffects.com/service/ListStyles", requestParams, asyncHttpResponseHandler);
    }

    public static void setRenameSubmission(String str, Long l, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.add("title", URLEncoder.encode(str2, "UTF-8"));
            requestParams.add("submissionId", Long.toString(l.longValue()));
            requestParams.add("token", str);
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(TIMEOUT);
            asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "RenameSubmission", VersionName));
            asyncHttpClient.get("https://www.deeparteffects.com/service/RenameSubmission", requestParams, asyncHttpResponseHandler);
        } catch (Exception e) {
            Log.e(TAG, "Error encode title");
        }
    }

    public static void setSubmissionStatus(String str, Long l, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        RequestParams requestParams = new RequestParams();
        requestParams.add(NotificationCompat.CATEGORY_STATUS, str2);
        requestParams.add("submissionId", Long.toString(l.longValue()));
        requestParams.add("token", str);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(TIMEOUT);
        asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "SetSubmissionStatus", VersionName));
        asyncHttpClient.get("https://www.deeparteffects.com/service/SetSubmissionStatus", requestParams, asyncHttpResponseHandler);
    }

    public static void getSubmissionResultV2(String str, Long l, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("submissionId", Long.toString(l.longValue()));
        requestParams.add("sessionId", str);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(TIMEOUT);
        String str2 = VersionName;
        asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "GetSubmissionResultV2", str2));
        Log.d("clientdata", Long.toString(l.longValue()));
        Log.d("clientdata1", str);
        Log.d("clientdata2", str2);
        asyncHttpClient.get("https://www.deeparteffects.com/service/GetSubmissionResultV2", requestParams, asyncHttpResponseHandler);
    }

    public static void getSubmissions(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("l", "99999");
        requestParams.add("t", str);
        requestParams.add("n", "1");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(TIMEOUT);
        asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "ListSubmissions", VersionName));
        asyncHttpClient.get("https://www.deeparteffects.com/service/ListSubmissions", requestParams, asyncHttpResponseHandler);
    }

    public static void uploadV2(Context context, Bitmap bitmap, String str, Uri uri, String str2, Long l, String str3, Long l2, Submission.Type type, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
            String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            RequestParams requestParams = new RequestParams();
            requestParams.add("categoryId", Long.toString(l.longValue()));
            requestParams.add("sessionId", str);
            if (str2 != null) {
                try {
                    requestParams.add("title", URLEncoder.encode(str2, "UTF-8"));
                } catch (Exception e) {
                    Log.e(TAG, "Error encode title");
                    return;
                }
            }
            try {
                try {
                    requestParams.add("filename", URLEncoder.encode(ImageHelper.readFileName(uri, context.getContentResolver()), "UTF-8"));
                    requestParams.add("styleId", Long.toString(l2.longValue()));
                    if (str3 != null) {
                        requestParams.add("token", str3);
                    }
                    if (type != null && !type.equals(Submission.Type.NORMAL)) {
                        requestParams.add("t", type.toString());
                    }
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    asyncHttpClient.setUserAgent(String.format("Android Client - Operation %s - App Version %s", "UploadV2", VersionName));
                    asyncHttpClient.setTimeout(TIMEOUT);
                    StringEntity stringEntity = null;
                    try {
                        stringEntity = new StringEntity(encodeToString);
                    } catch (UnsupportedEncodingException e2) {
                        Log.e(TAG, "Error converting entity");
                    }
                    String str4 = "?" + requestParams.toString();
                    if (stringEntity != null) {
                        asyncHttpClient.post(context, "https://www.deeparteffects.com/service/UploadV2" + str4, stringEntity, "image/generic", asyncHttpResponseHandler);
                    }
                } catch (Exception e3) {
                    Log.e(TAG, "Error encode filename");
                }
            } catch (Exception e4) {
            }
        }
    }

    public static String getPhotoUrl(Submission submission) {
        return "https://www.deeparteffects.com//images/photos/" + submission.photoUrl;
    }

    public static String getImageUrl(Submission submission, boolean z) {
        return z ? "https://www.deeparteffects.com//images/generated/" + submission.imageWatermarkUrl : "https://www.deeparteffects.com//images/generated/" + submission.imageUrl;
    }
}
