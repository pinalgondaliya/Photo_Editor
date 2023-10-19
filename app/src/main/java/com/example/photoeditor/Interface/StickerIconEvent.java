package com.example.photoeditor.Interface;

import android.view.MotionEvent;
import com.example.photoeditor.Classs.PolishStickerView;

/* loaded from: classes7.dex */
public interface StickerIconEvent {
    void onActionDown(PolishStickerView polishStickerView, MotionEvent motionEvent);

    void onActionMove(PolishStickerView polishStickerView, MotionEvent motionEvent);

    void onActionUp(PolishStickerView polishStickerView, MotionEvent motionEvent);
}
