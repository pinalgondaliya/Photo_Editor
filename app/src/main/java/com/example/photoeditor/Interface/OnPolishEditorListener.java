package com.example.photoeditor.Interface;
import com.example.photoeditor.Classs.Drawing;

/* loaded from: classes7.dex */
public interface OnPolishEditorListener {
    void onAddViewListener(Drawing drawing, int i);

    void onRemoveViewListener(int i);

    void onRemoveViewListener(Drawing drawing, int i);

    void onStartViewChangeListener(Drawing drawing);

    void onStopViewChangeListener(Drawing drawing);
}
