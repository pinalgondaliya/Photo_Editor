package com.example.photoeditor.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Classs.StickerFileAsset;
import com.example.photoeditor.R;

import java.util.List;

/* loaded from: classes5.dex */
public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    public Context context;
    public OnClickSplashListener onClickSplashListener;
    public int screenWidth;
    public List<String> stringList;

    /* loaded from: classes5.dex */
    public interface OnClickSplashListener {
        void addSticker(int i, Bitmap bitmap);
    }

    public StickerAdapter(Context context2, List<String> list, int i, OnClickSplashListener onClickSplashListener2) {
        this.context = context2;
        this.stringList = list;
        this.screenWidth = i;
        this.onClickSplashListener = onClickSplashListener2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_sticker, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Glide.with(this.context).load(StickerFileAsset.loadBitmapFromAssets(this.context, this.stringList.get(i))).into(viewHolder.sticker);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.stringList.size();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View lockPro;
        public ImageView sticker;

        public ViewHolder(View view) {
            super(view);
            this.sticker = (ImageView) view.findViewById(R.id.image_view_item_sticker);
            this.lockPro = view.findViewById(R.id.img_lock_pro);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            StickerAdapter.this.onClickSplashListener.addSticker(getAdapterPosition(), StickerFileAsset.loadBitmapFromAssets(StickerAdapter.this.context, StickerAdapter.this.stringList.get(getAdapterPosition())));
        }
    }
}
