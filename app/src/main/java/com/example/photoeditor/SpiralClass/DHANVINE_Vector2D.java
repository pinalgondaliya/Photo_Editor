package com.example.photoeditor.SpiralClass;

import android.graphics.PointF;

import com.example.photoeditor.OutSideClass.Vector2D;

/* loaded from: classes5.dex */
public class DHANVINE_Vector2D extends PointF {
    public DHANVINE_Vector2D(float f, float f2) {
        super(f, f2);
    }

    public static float getAngle(DHANVINE_Vector2D dHANVINE_Vector2D, Vector2D dHANVINE_Vector2D2) {
        dHANVINE_Vector2D.normalize();
        dHANVINE_Vector2D2.normalize();
        return (float) ((Math.atan2(dHANVINE_Vector2D2.y, dHANVINE_Vector2D2.x) - Math.atan2(dHANVINE_Vector2D.y, dHANVINE_Vector2D.x)) * 57.29577951308232d);
    }

    public void normalize() {
        float sqrt = (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
        this.x /= sqrt;
        this.y /= sqrt;
    }
}
