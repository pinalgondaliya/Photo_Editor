package com.example.photoeditor.ModelClass;

import android.util.Log;

import com.example.photoeditor.Classs.ServiceApi;

import java.util.Date;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Style {
    public String filePath;
    public Long id;
    public Date insertTime;
    public boolean isProcessed;
    public boolean isSelected;
    public String title;

    public static Style fromJsonObject(JSONObject jSONObject) throws Exception {
        Style style = new Style();
        style.id = Long.valueOf(jSONObject.getLong("id"));
        style.filePath = jSONObject.getString("filePath");
        style.title = jSONObject.getString("title");
        Log.d("mydata", jSONObject.getString("title"));
        style.insertTime = ServiceApi.dateFormate.parse(jSONObject.getString("insertTime"));
        return style;
    }
}
