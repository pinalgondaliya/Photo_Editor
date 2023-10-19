package com.example.photoeditor.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Interface.AdjustListener;
import com.example.photoeditor.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.ViewHolder> {
    public AdjustListener adjustListener;
    public List<AdjustModel> adjustModelList;
    private Context context;
    public String ADJUST = "@adjust brightness {0} @adjust contrast {1} @adjust saturation {2} @vignette {3} 0.7 @adjust sharpen {4} 1 @adjust whitebalance {5} 1 @adjust hue {6} 1 @adjust exposure {7} 1";
    public int selectedIndex = 0;

    public AdjustAdapter(Context context2, AdjustListener adjustListener2) {
        this.context = context2;
        this.adjustListener = adjustListener2;
        init();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_adjust, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.text_view_adjust_name.setText(this.adjustModelList.get(i).name);
        ImageView imageView = viewHolder.image_view_adjust_icon;
        int i2 = this.selectedIndex;
        imageView.setImageDrawable(this.adjustModelList.get(i).icon);
        if (this.selectedIndex == i) {
            viewHolder.relativeLayoutMain.setBackgroundResource(R.drawable.background_adjust_select);
            viewHolder.text_view_adjust_name.setTextColor(ContextCompat.getColor(this.context, R.color.black));
            viewHolder.image_view_adjust_icon.setColorFilter(ContextCompat.getColor(this.context, R.color.white));
            return;
        }
        viewHolder.relativeLayoutMain.setBackgroundResource(R.drawable.round_radius);
        viewHolder.text_view_adjust_name.setTextColor(ContextCompat.getColor(this.context, R.color.text_color_1));
        viewHolder.image_view_adjust_icon.setColorFilter(ContextCompat.getColor(this.context, R.color.black));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.adjustModelList.size();
    }

    public String getFilterConfig() {
        String str = this.ADJUST;
        return MessageFormat.format(str, this.adjustModelList.get(0).originValue + "", this.adjustModelList.get(1).originValue + "", this.adjustModelList.get(2).originValue + "", this.adjustModelList.get(3).originValue + "", this.adjustModelList.get(4).originValue + "", this.adjustModelList.get(5).originValue + "", this.adjustModelList.get(6).originValue + "", Float.valueOf(this.adjustModelList.get(7).originValue));
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view_adjust_icon;
        RelativeLayout relativeLayoutMain;
        TextView text_view_adjust_name;

        ViewHolder(View view) {
            super(view);
            this.image_view_adjust_icon = (ImageView) view.findViewById(R.id.image_view_adjust_icon);
            this.text_view_adjust_name = (TextView) view.findViewById(R.id.text_view_adjust_name);
            this.relativeLayoutMain = (RelativeLayout) view.findViewById(R.id.relativeLayoutMain);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.AdjustAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    AdjustAdapter.this.selectedIndex = ViewHolder.this.getLayoutPosition();
                    AdjustAdapter.this.adjustListener.onAdjustSelected(AdjustAdapter.this.adjustModelList.get(AdjustAdapter.this.selectedIndex));
                    AdjustAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void lambda$new$0$AdjustAdapter$ViewHolder(View view) {
            AdjustAdapter.this.selectedIndex = getLayoutPosition();
            AdjustAdapter.this.adjustListener.onAdjustSelected(AdjustAdapter.this.adjustModelList.get(AdjustAdapter.this.selectedIndex));
            AdjustAdapter.this.notifyDataSetChanged();
        }
    }

    public AdjustModel getCurrentAdjustModel() {
        return this.adjustModelList.get(this.selectedIndex);
    }

    private void init() {
        ArrayList arrayList = new ArrayList();
        this.adjustModelList = arrayList;
        arrayList.add(new AdjustModel(this.context.getString(R.string.brightness), "brightness", this.context.getDrawable(R.drawable.ic_brightness), -1.0f, 0.0f, 1.0f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.contrast), "contrast", this.context.getDrawable(R.drawable.ic_contrast), 0.5f, 1.0f, 1.5f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.saturation), "saturation", this.context.getDrawable(R.drawable.ic_saturation), 0.0f, 1.0f, 2.0f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.vignette), "vignette", this.context.getDrawable(R.drawable.ic_vignette), 0.0f, 0.6f, 0.6f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.sharpen), "sharpen", this.context.getDrawable(R.drawable.ic_sharpen), 0.0f, 0.0f, 10.0f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.whitebalance), "whitebalance", this.context.getDrawable(R.drawable.ic_white_balance), -1.0f, 0.0f, 1.0f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.hue), "hue", this.context.getDrawable(R.drawable.ic_hue), -2.0f, 0.0f, 2.0f));
        this.adjustModelList.add(new AdjustModel(this.context.getString(R.string.exposure), "exposure", this.context.getDrawable(R.drawable.ic_exposure), -2.0f, 0.0f, 2.0f));
    }

    /* loaded from: classes5.dex */
    public class AdjustModel {
        String code;
        Drawable icon;
        public float maxValue;
        public float minValue;
        String name;
        public float originValue;

        AdjustModel(String str, String str2, Drawable drawable, float f, float f2, float f3) {
            this.name = str;
            this.code = str2;
            this.icon = drawable;
            this.minValue = f;
            this.originValue = f2;
            this.maxValue = f3;
        }
    }
}
