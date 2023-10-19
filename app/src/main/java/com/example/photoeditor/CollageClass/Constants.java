package com.example.photoeditor.CollageClass;

import java.util.ArrayList;

/* loaded from: classes7.dex */
public class Constants {
    public static int BORDER_WIDTH = 4;
    public static ArrayList<String> FORMAT_IMAGE = new ImageTypeList();

    /* loaded from: classes7.dex */
    static class ImageTypeList extends ArrayList<String> {
        ImageTypeList() {
            add(".PNG");
            add(".JPEG");
            add(".jpg");
            add(".png");
            add(".jpeg");
            add(".JPG");
        }
    }
}
