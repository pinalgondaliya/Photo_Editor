package com.example.photoeditor.ModelClass;

import android.util.Log;

import com.example.photoeditor.Classs.ServiceApi;
import com.example.photoeditor.Classs.Utils;

import java.io.Serializable;
import java.util.Date;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Submission implements Serializable {
    public static final String STATUS_DELETED = "deleted";
    public static final String STATUS_ERROR = "error";
    public Long categoryId;
    public String categoryName;
    public Integer downloads;
    public Long id;
    public Long imageId;
    public String imageUrl;
    public String imageWatermarkUrl;
    public Date insertTime;
    public boolean isPublic;
    public Long photoId;
    public String photoUrl;
    public String status;
    public String title;
    public Date updateTime;
    public Long userId;
    public String username;
    public Integer views;

    /* loaded from: classes6.dex */
    public enum Type {
        NORMAL { // from class: com.example.photoareditor.ModelClass.Submission.Type.1
            @Override // java.lang.Enum
            public String toString() {
                return "n";
            }
        },
        PREMIUM { // from class: com.example.photoareditor.ModelClass.Submission.Type.2
            @Override // java.lang.Enum
            public String toString() {
                return "p";
            }
        },
        PRINT { // from class: com.example.photoareditor.ModelClass.Submission.Type.3
            @Override // java.lang.Enum
            public String toString() {
                return "pt";
            }
        },
        PRINT_HD { // from class: com.example.photoareditor.ModelClass.Submission.Type.4
            @Override // java.lang.Enum
            public String toString() {
                return "pthd";
            }
        }
    }

    public static Submission fromJsonObject(JSONObject jSONObject) throws Exception {
        Submission submission = new Submission();
        submission.id = Long.valueOf(jSONObject.getLong("id"));
        submission.userId = Long.valueOf(jSONObject.getLong("userId"));
        submission.photoId = Long.valueOf(jSONObject.getLong("photoId"));
        if (!jSONObject.isNull("imageId")) {
            submission.imageId = Long.valueOf(jSONObject.getLong("imageId"));
        }
        submission.categoryId = Long.valueOf(jSONObject.getLong("categoryId"));
        if (!jSONObject.isNull("title")) {
            submission.title = jSONObject.getString("title");
            Log.d("mytitle", "yes");
        }
        submission.isPublic = Utils.getBoolean(jSONObject, "isPublic", false).booleanValue();
        submission.downloads = Integer.valueOf(jSONObject.getInt("downloads"));
        submission.views = Integer.valueOf(jSONObject.getInt("views"));
        submission.insertTime = ServiceApi.dateFormate.parse(jSONObject.getString("insertTime"));
        if (!jSONObject.isNull("updateTime")) {
            submission.updateTime = ServiceApi.dateFormate.parse(jSONObject.getString("updateTime"));
        }
        submission.username = jSONObject.getString("username");
        submission.categoryName = jSONObject.getString("categoryName");
        submission.photoUrl = jSONObject.getString("photoUrl");
        if (!jSONObject.isNull("imageUrl")) {
            submission.imageUrl = jSONObject.getString("imageUrl");
        }
        if (!jSONObject.isNull("imageWatermarkUrl")) {
            submission.imageWatermarkUrl = jSONObject.getString("imageWatermarkUrl");
        }
        return submission;
    }
}
