package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class SevenSlantLayout extends NumberSlantLayout {
    @Override // com.example.photoareditor.Classs.NumberSlantLayout
    public int getThemeCount() {
        return 2;
    }

    public SevenSlantLayout(SlantCollageLayout slantCollageLayout, boolean z) {
        super(slantCollageLayout, z);
    }

    public SevenSlantLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.SlantCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        if (this.theme == 0) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
            addLine(1, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.33f, 0.33f);
            addLine(3, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(2, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new SevenSlantLayout((SlantCollageLayout) polishLayout, true);
    }
}
