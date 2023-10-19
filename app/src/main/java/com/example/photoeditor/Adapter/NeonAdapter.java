package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class NeonAdapter extends RecyclerView.Adapter<NeonAdapter.ViewHolder> {
    Context context;
    public LayoutItemListener layoutItenListener;
    private ArrayList<String> neonIcons = new ArrayList<>();
    public int selectedItem = 0;

    public NeonAdapter(Context context2) {
        this.context = context2;
    }

    public void addData(ArrayList<String> arrayList) {
        this.neonIcons.clear();
        this.neonIcons.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_neon, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int i2 = View.VISIBLE;
        View view = viewHolder.mSelectedBorder;
        if (i != this.selectedItem) {
            i2 = View.GONE;
        }
        view.setVisibility(i2);
        Glide.with(this.context).load("file:///android_asset/neon/icon/" + this.neonIcons.get(i) + ".webp").fitCenter().into(viewHolder.imageViewItem1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.neonIcons.size();
    }

    public ArrayList<String> getItemList() {
        return this.neonIcons;
    }

    public void setLayoutItenListener(LayoutItemListener layoutItemListener) {
        this.layoutItenListener = layoutItemListener;
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewItem1;
        View lockPro;
        View mSelectedBorder;

        ViewHolder(View view) {
            super(view);
            this.imageViewItem1 = (ImageView) view.findViewById(R.id.imageViewItem1);
            this.mSelectedBorder = view.findViewById(R.id.selectedBorder);
            this.lockPro = view.findViewById(R.id.img_lock_pro);
            view.setTag(view);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int i = NeonAdapter.this.selectedItem;
            NeonAdapter.this.selectedItem = getAdapterPosition();
            NeonAdapter.this.notifyItemChanged(i);
            NeonAdapter neonAdapter = NeonAdapter.this;
            neonAdapter.notifyItemChanged(neonAdapter.selectedItem);
            NeonAdapter.this.layoutItenListener.onLayoutListClick(view, getAdapterPosition());
        }
    }
}
