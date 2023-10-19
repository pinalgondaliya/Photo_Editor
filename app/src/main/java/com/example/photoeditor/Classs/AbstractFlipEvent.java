package com.example.photoeditor.Classs;

import android.view.MotionEvent;
import com.example.photoeditor.Interface.StickerIconEvent;

/* loaded from: classes3.dex */
public abstract class AbstractFlipEvent implements StickerIconEvent {
    public abstract int getFlipDirection();

    @Override // com.example.photoareditor.Interface.StickerIconEvent
    public void onActionDown(PolishStickerView polishStickerView, MotionEvent motionEvent) {
    }

    @Override // com.example.photoareditor.Interface.StickerIconEvent
    public void onActionMove(PolishStickerView polishStickerView, MotionEvent motionEvent) {
    }

    @Override // com.example.photoareditor.Interface.StickerIconEvent
    public void onActionUp(PolishStickerView polishStickerView, MotionEvent motionEvent) {
        polishStickerView.flipCurrentSticker(getFlipDirection());
    }
}
