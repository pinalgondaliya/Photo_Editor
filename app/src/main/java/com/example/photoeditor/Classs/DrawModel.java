package com.example.photoeditor.Classs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DrawModel {
    private Context context;
    private int from;
    private boolean isLoadBitmap;
    private boolean keepExactPosition;
    private List<Bitmap> lstBitmaps;
    private List<Integer> lstIconWhenDrawing;
    private List<BrushDrawingView.Vector2> mPositions = new ArrayList(100);
    private int mainIcon;
    private int to;

    public DrawModel(int i, List<Integer> list, boolean z, Context context2) {
        this.mainIcon = i;
        this.lstIconWhenDrawing = list;
        this.keepExactPosition = z;
        this.context = context2;
    }

    public void clearBitmap() {
        List<Bitmap> list = this.lstBitmaps;
        if (list != null && !list.isEmpty()) {
            this.lstBitmaps.clear();
        }
    }

    public int getMainIcon() {
        return this.mainIcon;
    }

    public List<Integer> getLstIconWhenDrawing() {
        return this.lstIconWhenDrawing;
    }

    public boolean isLoadBitmap() {
        return this.isLoadBitmap;
    }

    public void setLoadBitmap(boolean z) {
        this.isLoadBitmap = z;
    }

    public List<BrushDrawingView.Vector2> getmPositions() {
        return this.mPositions;
    }

    public Bitmap getBitmapByIndex(int i) {
        List<Bitmap> list = this.lstBitmaps;
        if (list == null || list.isEmpty()) {
            init();
        }
        return this.lstBitmaps.get(i);
    }

    public void init() {
        List<Bitmap> list = this.lstBitmaps;
        if (list == null || list.isEmpty()) {
            this.lstBitmaps = new ArrayList();
            for (Integer num : this.lstIconWhenDrawing) {
                this.lstBitmaps.add(BitmapFactory.decodeResource(this.context.getResources(), num.intValue()));
            }
        }
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int i) {
        this.from = i;
    }

    public int getTo() {
        return this.to;
    }

    public void setTo(int i) {
        this.to = i;
    }
}
