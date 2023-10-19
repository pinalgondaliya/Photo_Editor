package com.example.photoeditor.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Classs.NumberSlantLayout;
import com.example.photoeditor.Classs.NumberStraightLayout;
import com.example.photoeditor.Classs.PolishSquareView;
import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    public OnItemClickListener onItemClickListener;
    private List<Bitmap> bitmapData = new ArrayList();
    private List<PolishLayout> layoutData = new ArrayList();
    public int selectedIndex = 0;

    /* loaded from: classes5.dex */
    public interface OnItemClickListener {
        void onItemClick(PolishLayout polishLayout, int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public GridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new GridViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collage, viewGroup, false));
    }

    public void setSelectedIndex(int i) {
        this.selectedIndex = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(GridViewHolder gridViewHolder, @SuppressLint("RecyclerView") final int i) {
        final PolishLayout polishLayout = this.layoutData.get(i);
        gridViewHolder.square_collage_view.setNeedDrawLine(true);
        gridViewHolder.square_collage_view.setNeedDrawOuterLine(true);
        gridViewHolder.square_collage_view.setTouchEnable(false);
        gridViewHolder.square_collage_view.setLineSize(6);
        gridViewHolder.square_collage_view.setLineColor(Color.parseColor("#333333"));
        gridViewHolder.square_collage_view.setQueShotLayout(polishLayout);
        if (this.selectedIndex == i) {
            gridViewHolder.square_collage_view.setBackgroundColor(Color.parseColor("#3578FF"));
        } else {
            gridViewHolder.square_collage_view.setBackgroundColor(0);
        }
        gridViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.GridAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (GridAdapter.this.onItemClickListener != null) {
                    int i2 = 0;
                    PolishLayout polishLayout2 = polishLayout;
                    if (polishLayout2 instanceof NumberSlantLayout) {
                        i2 = ((NumberSlantLayout) polishLayout2).getTheme();
                    } else if (polishLayout2 instanceof NumberStraightLayout) {
                        i2 = ((NumberStraightLayout) polishLayout2).getTheme();
                    }
                    GridAdapter.this.onItemClickListener.onItemClick(polishLayout, i2);
                }
                GridAdapter.this.selectedIndex = i;
                GridAdapter.this.notifyDataSetChanged();
            }
        });
        List<Bitmap> list = this.bitmapData;
        if (list != null) {
            int size = list.size();
            if (polishLayout.getAreaCount() > size) {
                for (int i2 = 0; i2 < polishLayout.getAreaCount(); i2++) {
                    gridViewHolder.square_collage_view.addQuShotCollage(this.bitmapData.get(i2 % size));
                }
                return;
            }
            gridViewHolder.square_collage_view.addPieces(this.bitmapData);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<PolishLayout> list = this.layoutData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void refreshData(List<PolishLayout> list, List<Bitmap> list2) {
        this.layoutData = list;
        this.bitmapData = list2;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    /* loaded from: classes5.dex */
    public static class GridViewHolder extends RecyclerView.ViewHolder {
        PolishSquareView square_collage_view;

        public GridViewHolder(View view) {
            super(view);
            this.square_collage_view = (PolishSquareView) view.findViewById(R.id.squareCollageView);
        }
    }
}
