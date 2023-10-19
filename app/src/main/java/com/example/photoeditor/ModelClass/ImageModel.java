package com.example.photoeditor.ModelClass;

/* loaded from: classes6.dex */
public class ImageModel implements Comparable<ImageModel> {
    int id;
    String name;
    String pathFile;
    String pathFolder;

    public ImageModel(String str, String str2, String str3) {
        this.name = str;
        this.pathFile = str2;
        this.pathFolder = str3;
    }

    public String getPathFile() {
        return this.pathFile;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPathFolder() {
        return this.pathFolder;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    @Override // java.lang.Comparable
    public int compareTo(ImageModel imageModel) {
        return this.pathFolder.compareTo(imageModel.getPathFolder());
    }
}
