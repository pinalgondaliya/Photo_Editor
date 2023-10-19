package com.example.photoeditor.ModelClass;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes6.dex */
public class Stickermodel implements Serializable {
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    @Expose
    public String status;

    /* loaded from: classes6.dex */
    public static class Datum {
        @SerializedName("cat_id")
        @Expose
        public String catid;
        @SerializedName("cat_thumb")
        @Expose
        public String catthumb;
        public List<stickerlistModel> stickers;
    }
}
