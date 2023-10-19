package com.example.photoeditor.Classs;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.example.photoeditor.Interface.StickerIconEvent;

/* loaded from: classes3.dex */
public class PolishStickerIcons extends DrawableSticker implements StickerIconEvent {
    public static final String ALIGN = "ALIGN";
    public static final String DELETE = "DELETE";
    public static final String EDIT = "EDIT";
    public static final String FLIP = "FLIP";
    public static final String ROTATE = "ROTATE";
    public static final String SCALE = "SCALE";
    private StickerIconEvent iconEvent;
    private float iconExtraRadius;
    private float iconRadius;
    private int position;
    private String tag;
    private float x;
    private float y;

    public PolishStickerIcons(Drawable drawable, int i, String str) {
        super(drawable);
        this.iconExtraRadius = 10.0f;
        this.iconRadius = 30.0f;
        this.position = 0;
        this.position = i;
        this.tag = str;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(this.x, this.y, this.iconRadius, paint);
        draw(canvas);
    }

    public float getIconRadius() {
        return this.iconRadius;
    }

    public int getPosition() {
        return this.position;
    }

    public String getTag() {
        return this.tag;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override // com.example.photoareditor.Interface.StickerIconEvent
    public void onActionDown(PolishStickerView polishStickerView, MotionEvent motionEvent) {
        StickerIconEvent stickerIconEvent = this.iconEvent;
        if (stickerIconEvent != null) {
            stickerIconEvent.onActionDown(polishStickerView, motionEvent);
        }
    }

    @Override // com.example.photoareditor.Interface.StickerIconEvent
    public void onActionMove(PolishStickerView polishStickerView, MotionEvent motionEvent) {
        StickerIconEvent stickerIconEvent = this.iconEvent;
        if (stickerIconEvent != null) {
            stickerIconEvent.onActionMove(polishStickerView, motionEvent);
        }
    }

    @Override // com.example.photoareditor.Interface.StickerIconEvent
    public void onActionUp(PolishStickerView polishStickerView, MotionEvent motionEvent) {
        StickerIconEvent stickerIconEvent = this.iconEvent;
        if (stickerIconEvent != null) {
            stickerIconEvent.onActionUp(polishStickerView, motionEvent);
        }
    }

    public void setIconEvent(StickerIconEvent stickerIconEvent) {
        this.iconEvent = stickerIconEvent;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }
}
