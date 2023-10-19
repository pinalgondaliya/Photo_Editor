package com.example.photoeditor.Interface;

import com.example.photoeditor.Classs.BrushDrawingView;

/* loaded from: classes7.dex */
public interface BrushColorChangeListener {
    void onStartDrawing();

    void onStopDrawing();

    void onViewAdd(BrushDrawingView brushDrawingView);

    void onViewRemoved(BrushDrawingView brushDrawingView);
}
