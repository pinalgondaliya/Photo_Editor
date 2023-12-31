package com.example.photoeditor.Adapter;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.ModelClass.RatioModel;
import com.example.photoeditor.R;
import com.steelkiwi.cropiwa.AspectRatio;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
public class AspectAdapter extends RecyclerView.Adapter<AspectAdapter.ViewHolder> {
    public int lastSelectedView;
    public OnNewSelectedListener listener;
    public List<RatioModel> ratios;
    public RatioModel selectedRatio;

    /* loaded from: classes5.dex */
    public interface OnNewSelectedListener {
        void onNewAspectRatioSelected(AspectRatio aspectRatio);
    }

    public AspectAdapter() {
        List<RatioModel> asList = Arrays.asList(new RatioModel(10, 10, R.drawable.ic_crop_free, "Free"), new RatioModel(5, 4, R.drawable.ic_crop_5_4, "5:4"), new RatioModel(1, 1, R.drawable.ic_instagram_4_5, "1:1"), new RatioModel(4, 3, R.drawable.ic_instagram_4_3, "4:3"), new RatioModel(4, 5, R.drawable.ic_instagram_4_5, "4:5"), new RatioModel(1, 2, R.drawable.ic_instagram_1_2, "1:2"), new RatioModel(9, 16, R.drawable.ic_instagram_4_5, "Story"), new RatioModel(16, 7, R.drawable.ic_movie, "Movie"), new RatioModel(2, 3, R.drawable.ic_instagram_2_3, "2:3"), new RatioModel(4, 3, R.drawable.ic_fb_cover, "Post"), new RatioModel(16, 6, R.drawable.ic_fb_cover, "Cover"), new RatioModel(16, 9, R.drawable.ic_instagram_16_9, "16:9"), new RatioModel(3, 2, R.drawable.ic_instagram_3_2, "3:2"), new RatioModel(2, 3, R.drawable.ic_pinterest, "Post"), new RatioModel(16, 9, R.drawable.ic_crop_youtube, "Cover"), new RatioModel(9, 16, R.drawable.ic_instagram_9_16, "9:16"), new RatioModel(3, 4, R.drawable.ic_instagram_3_4, "3:4"), new RatioModel(16, 8, R.drawable.ic_crop_post_twitter, "Post"), new RatioModel(16, 5, R.drawable.ic_crop_post_twitter, "Header"), new RatioModel(10, 16, R.drawable.ic_crop_a4, "A4"), new RatioModel(10, 16, R.drawable.ic_crop_a5, "A5"));
        this.ratios = asList;
        this.selectedRatio = asList.get(0);
    }

    public AspectAdapter(boolean z) {
        List<RatioModel> asList = Arrays.asList(new RatioModel(5, 4, R.drawable.ic_crop_free, "5:4"), new RatioModel(1, 1, R.drawable.ic_instagram_4_5, "1:1"), new RatioModel(4, 3, R.drawable.ic_crop_free, "4:3"), new RatioModel(4, 5, R.drawable.ic_instagram_4_5, "4:5"), new RatioModel(1, 2, R.drawable.ic_crop_free, "1:2"), new RatioModel(9, 16, R.drawable.ic_instagram_4_5, "Story"), new RatioModel(16, 7, R.drawable.ic_movie, "Movie"), new RatioModel(2, 3, R.drawable.ic_crop_free, "2:3"), new RatioModel(4, 3, R.drawable.ic_fb_cover, "Post"), new RatioModel(16, 6, R.drawable.ic_fb_cover, "Cover"), new RatioModel(16, 9, R.drawable.ic_crop_free, "16:9"), new RatioModel(3, 2, R.drawable.ic_crop_free, "3:2"), new RatioModel(2, 3, R.drawable.ic_pinterest, "Post"), new RatioModel(16, 9, R.drawable.ic_crop_youtube, "Cover"), new RatioModel(9, 16, R.drawable.ic_crop_free, "9:16"), new RatioModel(3, 4, R.drawable.ic_crop_free, "3:4"), new RatioModel(16, 8, R.drawable.ic_crop_post_twitter, "Post"), new RatioModel(16, 5, R.drawable.ic_crop_post_twitter, "Header"), new RatioModel(10, 16, R.drawable.ic_crop_free, "A4"), new RatioModel(10, 16, R.drawable.ic_crop_free, "A5"));
        this.ratios = asList;
        this.selectedRatio = asList.get(0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_crop, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RatioModel ratioModel = this.ratios.get(i);
        viewHolder.ratioView.setImageResource(ratioModel.getSelectedIem());
        if (i == this.lastSelectedView) {
            viewHolder.text_view_filter_name.setText(ratioModel.getName());
            viewHolder.relativeLayoutCropper.setCardBackgroundColor(Color.parseColor("#3578FF"));
            viewHolder.relativeLayoutCropper.setCardElevation(5.0f);
            if (Build.VERSION.SDK_INT >= 28) {
                viewHolder.relativeLayoutCropper.setOutlineAmbientShadowColor(Color.parseColor("#3578FF"));
            }
            if (Build.VERSION.SDK_INT >= 28) {
                viewHolder.relativeLayoutCropper.setOutlineSpotShadowColor(Color.parseColor("#3578FF"));
            }
            viewHolder.text_view_filter_name.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.ratioView.setColorFilter(Color.parseColor("#FFFFFF"));
            return;
        }
        viewHolder.text_view_filter_name.setText(ratioModel.getName());
        viewHolder.relativeLayoutCropper.setCardBackgroundColor(Color.parseColor("#F5F5F5"));
        viewHolder.relativeLayoutCropper.setCardElevation(0.0f);
        viewHolder.text_view_filter_name.setTextColor(Color.parseColor("#151515"));
        viewHolder.ratioView.setColorFilter(Color.parseColor("#151515"));
    }

    public void setLastSelectedView(int i) {
        this.lastSelectedView = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.ratios.size();
    }

    public void setListener(OnNewSelectedListener onNewSelectedListener) {
        this.listener = onNewSelectedListener;
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ratioView;
        public RelativeLayout relativeLayoutCrop;
        public CardView relativeLayoutCropper;
        TextView text_view_filter_name;

        public ViewHolder(View view) {
            super(view);
            this.ratioView = (ImageView) view.findViewById(R.id.image_view_aspect_ratio);
            this.text_view_filter_name = (TextView) view.findViewById(R.id.text_view_filter_name);
            this.relativeLayoutCropper = (CardView) view.findViewById(R.id.card);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayoutCropper);
            this.relativeLayoutCrop = relativeLayout;
            relativeLayout.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (AspectAdapter.this.lastSelectedView != getAdapterPosition()) {
                AspectAdapter aspectAdapter = AspectAdapter.this;
                aspectAdapter.selectedRatio = aspectAdapter.ratios.get(getAdapterPosition());
                AspectAdapter.this.lastSelectedView = getAdapterPosition();
                if (AspectAdapter.this.listener != null) {
                    AspectAdapter.this.listener.onNewAspectRatioSelected(AspectAdapter.this.selectedRatio);
                }
                AspectAdapter.this.notifyDataSetChanged();
            }
        }
    }
}
