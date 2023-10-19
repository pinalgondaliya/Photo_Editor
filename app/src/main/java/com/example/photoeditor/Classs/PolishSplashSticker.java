package com.example.photoeditor.Classs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class PolishSplashSticker extends Sticker {
    private Bitmap bitmapOver;
    private Bitmap bitmapXor;
    private Paint over;
    private Paint xor;

    @Override // com.example.photoareditor.Classs.Sticker
    public int getAlpha() {
        return 1;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public Drawable getDrawable() {
        return null;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setAlpha */
    public PolishSplashSticker setAlpha(int i) {
        return this;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setDrawable */
    public PolishSplashSticker setDrawable(Drawable drawable) {
        return this;
    }

    public PolishSplashSticker(Bitmap bitmap, Bitmap bitmap2) {
        Paint paint = new Paint();
        this.xor = paint;
        paint.setDither(true);
        this.xor.setAntiAlias(true);
        this.xor.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.over = paint2;
        paint2.setDither(true);
        this.over.setAntiAlias(true);
        this.over.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.bitmapXor = bitmap;
        this.bitmapOver = bitmap2;
    }

    private Bitmap getBitmapOver() {
        return this.bitmapOver;
    }

    private Bitmap getBitmapXor() {
        return this.bitmapXor;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmapXor(), getMatrix(), this.xor);
        canvas.drawBitmap(getBitmapOver(), getMatrix(), this.over);
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getHeight() {
        return this.bitmapOver.getHeight();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getWidth() {
        return this.bitmapXor.getWidth();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void release() {
        super.release();
        this.xor = null;
        this.over = null;
        Bitmap bitmap = this.bitmapXor;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.bitmapXor = null;
        Bitmap bitmap2 = this.bitmapOver;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.bitmapOver = null;
    }
}
