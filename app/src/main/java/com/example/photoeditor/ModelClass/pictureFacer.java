package com.example.photoeditor.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class pictureFacer implements Parcelable {
    public static final Creator<pictureFacer> CREATOR = new Creator<pictureFacer>() { // from class: com.example.photoareditor.ModelClass.pictureFacer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public pictureFacer createFromParcel(Parcel in) {
            return new pictureFacer(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public pictureFacer[] newArray(int size) {
            return new pictureFacer[size];
        }
    };
    private String imageUri;
    private String picturName;
    private String picturePath;
    private String pictureSize;
    private Boolean selected;

    public pictureFacer() {
        this.selected = false;
    }

    public pictureFacer(String picturName, String picturePath, String pictureSize, String imageUri) {
        this.selected = false;
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.pictureSize = pictureSize;
        this.imageUri = imageUri;
    }

    protected pictureFacer(Parcel in) {
        Boolean valueOf;
        boolean z = false;
        this.selected = false;
        this.picturName = in.readString();
        this.picturePath = in.readString();
        this.pictureSize = in.readString();
        this.imageUri = in.readString();
        byte tmpSelected = in.readByte();
        if (tmpSelected == 0) {
            valueOf = null;
        } else {
            valueOf = Boolean.valueOf(tmpSelected == 1 ? true : z);
        }
        this.selected = valueOf;
    }

    public String getPicturName() {
        return this.picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureSize() {
        return this.pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Boolean getSelected() {
        return this.selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picturName);
        dest.writeString(this.picturePath);
        dest.writeString(this.pictureSize);
        dest.writeString(this.imageUri);
        Boolean bool = this.selected;
        dest.writeByte((byte) (bool == null ? 0 : bool.booleanValue() ? 1 : 2));
    }
}
