package com.example.photoeditor.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Interface.OnAlbum;
import com.example.photoeditor.ModelClass.ImageModel;
import com.example.photoeditor.R;

import java.io.File;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class AlbumAdapter extends ArrayAdapter<ImageModel> {
    Context context;
    ArrayList<ImageModel> data;
    int layoutResourceId;
    OnAlbum onItem;
    int pHeightItem;

    /* loaded from: classes5.dex */
    static class RecordHolder {
        ImageView image_view_album;
        LinearLayout relative_layout_root;
        TextView text_view_name_album;

        RecordHolder() {
        }
    }

    public AlbumAdapter(Context context2, int i, ArrayList<ImageModel> arrayList) {
        super(context2, i, arrayList);
        this.pHeightItem = 0;
        this.layoutResourceId = i;
        this.context = context2;
        this.data = arrayList;
        this.pHeightItem = getDisplayInfo((Activity) context2).widthPixels / 6;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        RecordHolder recordHolder;
        if (view == null) {
            view = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, viewGroup, false);
            recordHolder = new RecordHolder();
            recordHolder.text_view_name_album = (TextView) view.findViewById(R.id.text_view_name_album);
            recordHolder.image_view_album = (ImageView) view.findViewById(R.id.image_view_album);
            recordHolder.relative_layout_root = (LinearLayout) view.findViewById(R.id.relative_layout_root);
            view.setTag(recordHolder);
        } else {
            recordHolder = (RecordHolder) view.getTag();
        }
        ImageModel imageModel = this.data.get(i);
        recordHolder.text_view_name_album.setText(imageModel.getName());
        Glide.with(this.context).load(new File(imageModel.getPathFile())).placeholder((int) R.drawable.image_show).into(recordHolder.image_view_album);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.AlbumAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AlbumAdapter.this.lambda$getView$0$AlbumAdapter(i, view2);
            }
        });
        return view;
    }

    public void lambda$getView$0$AlbumAdapter(int i, View view) {
        OnAlbum onAlbum = this.onItem;
        if (onAlbum != null) {
            onAlbum.OnItemAlbumClick(i);
        }
    }

    public void setOnItem(OnAlbum onAlbum) {
        this.onItem = onAlbum;
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
