package com.example.photoeditor.Classs;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class DrawableSticker extends Sticker {
    private Drawable drawable;
    private Rect realBounds;

    public DrawableSticker(Drawable drawable2) {
        this.drawable = drawable2;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.concat(getMatrix());
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        this.realBounds = rect;
        this.drawable.setBounds(rect);
        this.drawable.draw(canvas);
        canvas.restore();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getAlpha() {
        return this.drawable.getAlpha();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public Drawable getDrawable() {
        return this.drawable;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getHeight() {
        return this.drawable.getIntrinsicHeight();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getWidth() {
        return this.drawable.getIntrinsicWidth();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void release() {
        super.release();
        if (this.drawable != null) {
            this.drawable = null;
        }
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setAlpha */
    public DrawableSticker setAlpha(int i) {
        this.drawable.setAlpha(i);
        return this;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setDrawable */
    public DrawableSticker setDrawable(Drawable drawable2) {
        this.drawable = drawable2;
        return this;
    }
}
