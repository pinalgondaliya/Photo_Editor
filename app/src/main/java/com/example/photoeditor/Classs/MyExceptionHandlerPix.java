package com.example.photoeditor.Classs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.app.NotificationCompat;

import com.example.photoeditor.Activity.MainActivity;

import java.lang.Thread;

/* loaded from: classes3.dex */
public class MyExceptionHandlerPix implements Thread.UncaughtExceptionHandler {
    private Activity activity;

    public MyExceptionHandlerPix(Activity activity2) {
        this.activity = activity2;
    }

    @SuppressLint("WrongConstant")
    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Intent intent;
        if (this.activity != null) {
            intent = new Intent(this.activity, MainActivity.class);
        } else {
            intent = Polish.getContext() != null ? new Intent(Polish.getContext(), MainActivity.class) : null;
        }
        intent.putExtra("crash", true);
        intent.addFlags(335577088);
        ((AlarmManager) Polish.getContext().getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + 10, PendingIntent.getActivity(Polish.getContext(), 0, intent, BasicMeasure.EXACTLY));
        System.exit(2);
    }
}
