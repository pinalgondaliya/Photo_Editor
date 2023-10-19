package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class OneSlantLayout extends NumberSlantLayout {
    @Override // com.example.photoareditor.Classs.NumberSlantLayout
    public int getThemeCount() {
        return 4;
    }

    public OneSlantLayout(SlantCollageLayout slantCollageLayout, boolean z) {
        super(slantCollageLayout, z);
    }

    public OneSlantLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.SlantCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        int i = this.theme;
        if (i == 0) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.56f, 0.44f);
        } else if (i == 1) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.56f, 0.44f);
        } else if (i == 2) {
            addCross(0, 0.56f, 0.44f, 0.56f, 0.44f);
        } else if (i == 3) {
            cutArea(0, 1, 2);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new OneSlantLayout((SlantCollageLayout) polishLayout, true);
    }
}
