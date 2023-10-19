package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Activity.DripLayout;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class DripAdapter extends RecyclerView.Adapter<DripAdapter.ViewHolder> {
    public LayoutItemListener clickListener;
    Context mContext;
    private ArrayList<String> dripItemList = new ArrayList<>();
    public int selectedPos = 0;

    public DripAdapter(Context context) {
        this.mContext = context;
    }

    public void addData(ArrayList<String> arrayList) {
        this.dripItemList.clear();
        this.dripItemList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drip, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mSelectedBorder.setVisibility(i == this.selectedPos ? View.VISIBLE : View.GONE);
        Glide.with(this.mContext).load("file:///android_asset/drip/style/" + this.dripItemList.get(i) + ".webp").fitCenter().into(viewHolder.imageViewItem);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dripItemList.size();
    }

    public void setClickListener(DripLayout dripLayout) {
        this.clickListener = dripLayout;
    }

    public ArrayList<String> getItemList() {
        return this.dripItemList;
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewItem;
        RelativeLayout mSelectedBorder;

        ViewHolder(View view) {
            super(view);
            this.imageViewItem = (ImageView) view.findViewById(R.id.imageViewItem);
            this.mSelectedBorder = (RelativeLayout) view.findViewById(R.id.selectedBorder);
            view.setTag(view);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int i = DripAdapter.this.selectedPos;
            DripAdapter.this.selectedPos = getAdapterPosition();
            DripAdapter.this.notifyItemChanged(i);
            DripAdapter dripAdapter = DripAdapter.this;
            dripAdapter.notifyItemChanged(dripAdapter.selectedPos);
            DripAdapter.this.clickListener.onLayoutListClick(view, getAdapterPosition());
        }
    }
}
