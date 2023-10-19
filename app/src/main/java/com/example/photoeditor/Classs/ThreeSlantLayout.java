package com.example.photoeditor.Classs;
import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

/* loaded from: classes3.dex */
public class ThreeSlantLayout extends NumberSlantLayout {
    @Override // com.example.photoareditor.Classs.NumberSlantLayout
    public int getThemeCount() {
        return 6;
    }

    public ThreeSlantLayout(SlantCollageLayout slantCollageLayout, boolean z) {
        super(slantCollageLayout, z);
        this.theme = ((NumberSlantLayout) slantCollageLayout).getTheme();
    }

    public ThreeSlantLayout(int i) {
        super(i);
    }

    @Override // com.example.photoareditor.Classs.SlantCollageLayout, com.example.photoareditor.ModelClass.PolishLayout
    public void layout() {
        int i = this.theme;
        if (i == 0) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
            addLine(0, PolishLine.Direction.VERTICAL, 0.56f, 0.44f);
        } else if (i == 1) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f);
            addLine(1, PolishLine.Direction.VERTICAL, 0.56f, 0.44f);
        } else if (i == 2) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.56f, 0.44f);
        } else if (i == 3) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.56f, 0.44f);
        } else if (i == 4) {
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.44f, 0.56f);
            addLine(0, PolishLine.Direction.VERTICAL, 0.56f, 0.44f);
        } else if (i == 5) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.56f, 0.44f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.44f, 0.56f);
        }
    }

    @Override // com.example.photoareditor.ModelClass.PolishLayout
    public PolishLayout clone(PolishLayout polishLayout) {
        return new ThreeSlantLayout((SlantCollageLayout) polishLayout, true);
    }
}
