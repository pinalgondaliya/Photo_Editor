package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.photoeditor.Classs.RecyclerTabLayout;
import com.example.photoeditor.R;

/* loaded from: classes5.dex */
public class StickersTabAdapter extends RecyclerTabLayout.Adapter<StickersTabAdapter.ViewHolder> {
    private Context context;
    private PagerAdapter mAdapater;

    public StickersTabAdapter(ViewPager viewPager, Context context2) {
        super(viewPager);
        this.mAdapater = this.mViewPager.getAdapter();
        this.context = context2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab_sticker, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (i) {
            case 0:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 1:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 2:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 3:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 4:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 5:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 6:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 7:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 8:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 9:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 10:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 11:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 12:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 13:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 14:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 15:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 16:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 17:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 18:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 19:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 20:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
            case 21:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
                break;
            case 22:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
                break;
        }
        viewHolder.imageView.setSelected(i == getCurrentIndicatorPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mAdapater.getCount();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.StickersTabAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    StickersTabAdapter.this.getViewPager().setCurrentItem(ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }
}
