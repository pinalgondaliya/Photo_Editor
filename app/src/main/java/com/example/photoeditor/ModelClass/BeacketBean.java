package com.example.photoeditor.ModelClass;

/* loaded from: classes6.dex */
public class BeacketBean {
    public int count;
    public String cover;
    public String id;
    public String imageId;
    public int image_id = -1;
    public boolean isCameraBucket;
    public String isImages;
    public String name;

    public boolean equals(Object obj) {
        String str;
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        BeacketBean a = (BeacketBean) obj;
        String str2 = a.name;
        if (str2 == null || (str = this.name) == null) {
            return false;
        }
        return str2.equals(str);
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
