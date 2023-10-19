package com.example.photoeditor.Classs;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.example.photoeditor.BuildConfig;

/* loaded from: classes3.dex */
public class Polish extends Application {
    public static String app_id;
    private static Polish queShot;
    public static String version_name;

    static {
        System.loadLibrary("arteffect");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setVersion_name("VERSION 3.10.2");
        setApp_id(BuildConfig.APPLICATION_ID);
        queShot = this;
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke(null, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getVersion_name() {
        return version_name;
    }

    public static void setVersion_name(String str) {
        version_name = str;
    }

    public static String getApp_id() {
        return app_id;
    }

    public static void setApp_id(String str) {
        app_id = str;
    }

    public static Context getContext() {
        return getContext();
    }
}
