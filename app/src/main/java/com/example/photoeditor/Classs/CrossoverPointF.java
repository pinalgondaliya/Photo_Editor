package com.example.photoeditor.Classs;

import android.graphics.PointF;

/* loaded from: classes3.dex */
public class CrossoverPointF extends PointF {
    SlantLine horizontal;
    SlantLine vertical;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CrossoverPointF() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CrossoverPointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CrossoverPointF(SlantLine slantLine, SlantLine slantLine2) {
        this.horizontal = slantLine;
        this.vertical = slantLine2;
    }
}
