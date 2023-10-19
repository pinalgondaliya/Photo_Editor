package com.example.photoeditor.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class MainModel implements Serializable {
    @SerializedName("data")
    @Expose
    public ArrayList<Datum> data = null;

    /* loaded from: classes6.dex */
    public static class Datum implements Serializable {
        @SerializedName("cat_array")
        @Expose
        public ArrayList<Category_Model> cat_array = null;
        @SerializedName("category_name")
        @Expose
        public String category_name;

        /* loaded from: classes6.dex */
        public static class Category_Model implements Serializable {
            @SerializedName("back")
            @Expose
            public String back;
            @SerializedName("front")
            @Expose
            public String front;
            @SerializedName("id")
            @Expose
            public String id;
            @SerializedName("thumb")
            @Expose
            public String thumb;
        }
    }
}
