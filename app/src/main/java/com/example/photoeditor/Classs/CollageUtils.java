package com.example.photoeditor.Classs;

import com.example.photoeditor.ModelClass.PolishLayout;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class CollageUtils {
    private CollageUtils() {
    }

    public static List<PolishLayout> getCollageLayouts(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(SlantLayoutHelper.getAllThemeLayout(i));
        arrayList.addAll(StraightLayoutHelper.getAllThemeLayout(i));
        return arrayList;
    }
}
