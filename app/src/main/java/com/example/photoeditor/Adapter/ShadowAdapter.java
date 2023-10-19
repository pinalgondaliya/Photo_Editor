package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Classs.PolishText;
import com.example.photoeditor.R;

import java.util.List;

/* loaded from: classes5.dex */
public class ShadowAdapter extends RecyclerView.Adapter<ShadowAdapter.ViewHolder> {
    private Context context;
    private List<PolishText.TextShadow> lstTextShadows;
    public ShadowItemClickListener mClickListener;
    private LayoutInflater mInflater;
    public int selectedItem = 0;

    /* loaded from: classes5.dex */
    public interface ShadowItemClickListener {
        void onShadowItemClick(View view, int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    public ShadowAdapter(Context context2, List<PolishText.TextShadow> list) {
        this.mInflater = LayoutInflater.from(context2);
        this.context = context2;
        this.lstTextShadows = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.item_font, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PolishText.TextShadow textShadow = this.lstTextShadows.get(i);
        viewHolder.textShadow.setShadowLayer(textShadow.getRadius(), textShadow.getDx(), textShadow.getDy(), textShadow.getColorShadow());
        viewHolder.wrapperFontItems.setBackground(ContextCompat.getDrawable(this.context, this.selectedItem != i ? R.drawable.border_view : R.drawable.border_black_view));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.lstTextShadows.size();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textShadow;
        ConstraintLayout wrapperFontItems;

        ViewHolder(View view) {
            super(view);
            this.textShadow = (TextView) view.findViewById(R.id.text_view_font_item);
            this.wrapperFontItems = (ConstraintLayout) view.findViewById(R.id.constraint_layout_wrapper_font_item);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (ShadowAdapter.this.mClickListener != null) {
                ShadowAdapter.this.mClickListener.onShadowItemClick(view, getAdapterPosition());
            }
            ShadowAdapter.this.selectedItem = getAdapterPosition();
            ShadowAdapter.this.notifyDataSetChanged();
        }
    }

    public void setSelectedItem(int i) {
        this.selectedItem = i;
    }

    public void setClickListener(ShadowItemClickListener shadowItemClickListener) {
        this.mClickListener = shadowItemClickListener;
    }
}
