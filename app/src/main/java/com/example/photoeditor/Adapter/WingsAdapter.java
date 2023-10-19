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
public class WingsAdapter extends RecyclerView.Adapter<WingsAdapter.ViewHolder> {
    Context context;
    public LayoutItemListener menuItemClickLister;
    public int selectedItem = 0;
    private ArrayList<String> wingsIcons = new ArrayList<>();

    public WingsAdapter(Context context2) {
        this.context = context2;
    }

    public void addData(ArrayList<String> arrayList) {
        this.wingsIcons.clear();
        this.wingsIcons.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
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
        Glide.with(this.context).load("file:///android_asset/wings/" + this.wingsIcons.get(i) + ".webp").fitCenter().into(viewHolder.imageViewItem1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.wingsIcons.size();
    }

    public ArrayList<String> getItemList() {
        return this.wingsIcons;
    }

    public void setMenuItemClickLister(LayoutItemListener layoutItemListener) {
        this.menuItemClickLister = layoutItemListener;
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
            int i = WingsAdapter.this.selectedItem;
            WingsAdapter.this.selectedItem = getAdapterPosition();
            WingsAdapter.this.notifyItemChanged(i);
            WingsAdapter wingsAdapter = WingsAdapter.this;
            wingsAdapter.notifyItemChanged(wingsAdapter.selectedItem);
            WingsAdapter.this.menuItemClickLister.onLayoutListClick(view, getAdapterPosition());
        }
    }
}
