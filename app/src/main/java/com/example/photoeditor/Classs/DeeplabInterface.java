package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public interface DeeplabInterface {
    int getInputSize();

    boolean initialize(Context context);

    boolean isInitialized();

    Bitmap segment(Bitmap bitmap);
}
