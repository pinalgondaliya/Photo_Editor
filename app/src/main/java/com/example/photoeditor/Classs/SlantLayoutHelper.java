package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SlantLayoutHelper {
    private SlantLayoutHelper() {
    }

    public static List<PolishLayout> getAllThemeLayout(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        if (i == 1) {
            while (i2 < 4) {
                arrayList.add(new OneSlantLayout(i2));
                i2++;
            }
        } else if (i == 2) {
            while (i2 < 2) {
                arrayList.add(new TwoSlantLayout(i2));
                i2++;
            }
        } else if (i != 3) {
            if (i == 4) {
                while (i2 < 7) {
                    arrayList.add(new FourSlantLayout(i2));
                    i2++;
                }
            } else if (i == 6) {
                while (i2 < 2) {
                    arrayList.add(new SixSlantLayout(i2));
                    i2++;
                }
            } else if (i == 7) {
                while (i2 < 1) {
                    arrayList.add(new SevenSlantLayout(i2));
                    i2++;
                }
            }
        } else {
            while (i2 < 6) {
                arrayList.add(new ThreeSlantLayout(i2));
                i2++;
            }
        }
        return arrayList;
    }
}
