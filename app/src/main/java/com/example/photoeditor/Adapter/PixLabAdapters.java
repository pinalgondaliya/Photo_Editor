package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Activity.PixLabLayout;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class PixLabAdapters extends RecyclerView.Adapter<PixLabAdapters.ViewHolder> {
    public LayoutItemListener clickListener;
    Context mContext;
    private ArrayList<String> pixLabItemList = new ArrayList<>();
    public int selectedPos = 0;

    public PixLabAdapters(Context context) {
        this.mContext = context;
    }

    public void addData(ArrayList<String> arrayList) {
        this.pixLabItemList.clear();
        this.pixLabItemList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pixlab, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int i2 = View.VISIBLE;
        View view = viewHolder.mSelectedBorder;
        if (i != this.selectedPos) {
            i2 = View.GONE;
        }
        view.setVisibility(i2);
        Glide.with(this.mContext).load("file:///android_asset/lab/" + this.pixLabItemList.get(i) + ".webp").fitCenter().into(viewHolder.mIvImage);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.pixLabItemList.size();
    }

    public void setClickListener(PixLabLayout pixLabLayout) {
        this.clickListener = pixLabLayout;
    }

    public ArrayList<String> getItemList() {
        return this.pixLabItemList;
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mIvImage;
        RelativeLayout mSelectedBorder;

        ViewHolder(View view) {
            super(view);
            this.mIvImage = (ImageView) view.findViewById(R.id.imageViewItem);
            this.mSelectedBorder = (RelativeLayout) view.findViewById(R.id.selectedBorder);
            view.setTag(view);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int i = PixLabAdapters.this.selectedPos;
            PixLabAdapters.this.selectedPos = getAdapterPosition();
            PixLabAdapters.this.notifyItemChanged(i);
            PixLabAdapters pixLabAdapters = PixLabAdapters.this;
            pixLabAdapters.notifyItemChanged(pixLabAdapters.selectedPos);
            PixLabAdapters.this.clickListener.onLayoutListClick(view, getAdapterPosition());
        }
    }
}
