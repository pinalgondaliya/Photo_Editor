package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class PolishSticker extends Sticker {
    private Drawable drawable;
    private int drawableSizeBoobs;
    private int drawableSizeFace_Height;
    private int drawableSizeFace_Width;
    private int drawableSizeHip1_Height;
    private int drawableSizeHip1_Width;
    private int height_Width;
    private PointF mappedCenterPoint;
    private int radius;
    private Rect realBounds = new Rect(0, 0, getWidth(), getHeight());
    private int type;

    @Override // com.example.photoareditor.Classs.Sticker
    public Drawable getDrawable() {
        return null;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setDrawable */
    public PolishSticker setDrawable(Drawable drawable2) {
        return this;
    }

    public PolishSticker(Context context, int i, Drawable drawable2) {
        this.drawableSizeBoobs = SystemUtil.dpToPx(context, 50);
        this.drawableSizeHip1_Width = SystemUtil.dpToPx(context, 150);
        this.drawableSizeHip1_Height = SystemUtil.dpToPx(context, 75);
        this.drawableSizeFace_Height = SystemUtil.dpToPx(context, 50);
        this.drawableSizeFace_Width = SystemUtil.dpToPx(context, 80);
        this.type = i;
        this.drawable = drawable2;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.concat(getMatrix());
        this.drawable.setBounds(this.realBounds);
        this.drawable.draw(canvas);
        canvas.restore();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getAlpha() {
        return this.drawable.getAlpha();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getHeight() {
        int i = this.type;
        if (i == 1 || i == 0) {
            return this.drawableSizeBoobs;
        }
        if (i == 2) {
            return this.drawableSizeHip1_Height;
        }
        if (i == 4) {
            return this.drawableSizeFace_Height;
        }
        if (i == 10 || i == 11) {
            return this.drawable.getIntrinsicHeight();
        }
        return 0;
    }

    public PointF getMappedCenterPoint2() {
        return this.mappedCenterPoint;
    }

    public int getRadius() {
        return this.radius;
    }

    public int getType() {
        return this.type;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getWidth() {
        int i = this.type;
        if (i == 1 || i == 0) {
            return this.drawableSizeBoobs;
        }
        if (i == 2) {
            return this.drawableSizeHip1_Width;
        }
        if (i == 4) {
            return this.drawableSizeFace_Width;
        }
        if (i == 10 || i == 11) {
            return this.height_Width;
        }
        return 0;
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
    public PolishSticker setAlpha(int i) {
        this.drawable.setAlpha(i);
        return this;
    }

    public void setRadius(int i) {
        this.radius = i;
    }

    public void updateRadius() {
        RectF bound = getBound();
        int i = this.type;
        if (i == 0 || i == 1 || i == 4) {
            this.radius = (int) (bound.left + bound.right);
        } else if (i == 2) {
            this.radius = (int) (bound.top + bound.bottom);
        }
        this.mappedCenterPoint = getMappedCenterPoint();
    }
}
