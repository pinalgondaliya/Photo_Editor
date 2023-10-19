package com.example.photoeditor.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Classs.FilterFileAsset;
import com.example.photoeditor.Interface.FilterListener;
import com.example.photoeditor.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import java.util.List;

/* loaded from: classes5.dex */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private List<Bitmap> bitmaps;
    private Context context;
    public List<FilterFileAsset.FiltersCode> filterBeanList;
    public FilterListener filterListener;
    public int selectedIndex = 0;

    public FilterAdapter(List<Bitmap> list, FilterListener filterListener2, Context context2, List<FilterFileAsset.FiltersCode> list2) {
        this.filterListener = filterListener2;
        this.bitmaps = list;
        this.context = context2;
        this.filterBeanList = list2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filter, viewGroup, false));
    }

    public void reset() {
        this.selectedIndex = 0;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.round_image_view_filter_item.setImageBitmap(this.bitmaps.get(i));
        if (this.selectedIndex == i) {
            viewHolder.viewSelected.setVisibility(View.VISIBLE);
            viewHolder.cardView.setCardElevation(5.0f);
            if (Build.VERSION.SDK_INT >= 28) {
                viewHolder.cardView.setOutlineAmbientShadowColor(ContextCompat.getColor(this.context, R.color.mainColor));
            }
            if (Build.VERSION.SDK_INT >= 28) {
                viewHolder.cardView.setOutlineSpotShadowColor(ContextCompat.getColor(this.context, R.color.mainColor));
                return;
            }
            return;
        }
        viewHolder.viewSelected.setVisibility(View.GONE);
        viewHolder.cardView.setCardElevation(0.0f);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.bitmaps.size();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View lockPro;
        RoundedImageView round_image_view_filter_item;
        View viewSelected;

        ViewHolder(View view) {
            super(view);
            this.round_image_view_filter_item = (RoundedImageView) view.findViewById(R.id.round_image_view_filter_item);
            this.viewSelected = view.findViewById(R.id.view_selected);
            this.lockPro = view.findViewById(R.id.img_lock_pro);
            this.cardView = (CardView) view.findViewById(R.id.card);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.FilterAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    FilterAdapter.this.selectedIndex = ViewHolder.this.getLayoutPosition();
                    FilterAdapter.this.filterListener.onFilterSelected(FilterAdapter.this.selectedIndex, FilterAdapter.this.filterBeanList.get(FilterAdapter.this.selectedIndex).getCode());
                    FilterAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }
}
