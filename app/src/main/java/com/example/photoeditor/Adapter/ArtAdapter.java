package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Activity.ArtLayout;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ViewHolder> {
    public LayoutItemListener clickListener;
    Context mContext;
    private ArrayList<String> dripItemList = new ArrayList<>();
    public int selectedPos = 0;

    public ArtAdapter(Context context) {
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
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_art, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int i2 = View.VISIBLE;
        View view = viewHolder.mSelectedBorder;
        if (i != this.selectedPos) {
            i2 = View.GONE;
        }
        view.setVisibility(i2);
        viewHolder.card.setCardElevation(0.0f);
        String str = "file:///android_asset/art/" + this.dripItemList.get(i) + "_front.webp";
        Glide.with(this.mContext).load(str).fitCenter().into(viewHolder.imageViewItem1);
        Glide.with(this.mContext).load("file:///android_asset/art/" + this.dripItemList.get(i) + "_back.webp").fitCenter().into(viewHolder.imageViewItem2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dripItemList.size();
    }

    public void setClickListener(ArtLayout artLayout) {
        this.clickListener = artLayout;
    }

    public ArrayList<String> getItemList() {
        return this.dripItemList;
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card;
        ImageView imageViewItem1;
        ImageView imageViewItem2;
        RelativeLayout mSelectedBorder;

        ViewHolder(View view) {
            super(view);
            this.imageViewItem1 = (ImageView) view.findViewById(R.id.imageViewItem1);
            this.imageViewItem2 = (ImageView) view.findViewById(R.id.imageViewItem2);
            this.mSelectedBorder = (RelativeLayout) view.findViewById(R.id.selectedBorder);
            this.card = (CardView) view.findViewById(R.id.card);
            view.setTag(view);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int i = ArtAdapter.this.selectedPos;
            ArtAdapter.this.selectedPos = getAdapterPosition();
            ArtAdapter.this.notifyItemChanged(i);
            ArtAdapter artAdapter = ArtAdapter.this;
            artAdapter.notifyItemChanged(artAdapter.selectedPos);
            ArtAdapter.this.clickListener.onLayoutListClick(view, getAdapterPosition());
        }
    }
}
