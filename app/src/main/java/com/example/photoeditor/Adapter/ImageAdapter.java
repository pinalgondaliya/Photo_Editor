package com.example.photoeditor.Adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.photoeditor.Activity.Image_GalleryActivity;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    Image_GalleryActivity context;
    ImageAdapterInterface imageAdapterInterface;
    ArrayList<String> list;
    private long mLastClickTime;

    /* loaded from: classes5.dex */
    public interface ImageAdapterInterface {
        void item_click(String str);
    }

    public ImageAdapter(Image_GalleryActivity galleryActivity, ArrayList<String> imageList, ImageAdapterInterface imageAdapterInterface) {
        this.list = imageList;
        this.context = galleryActivity;
        this.imageAdapterInterface = imageAdapterInterface;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_list, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int device_width = displaymetrics.widthPixels / 3;
        holder.relativeLayout.getLayoutParams().width = device_width;
        holder.relativeLayout.getLayoutParams().height = device_width;
        String string = this.list.get(position);
        Glide.with((FragmentActivity) this.context).load(string).listener(new RequestListener<Drawable>() { // from class: com.example.photoareditor.Adapter.ImageAdapter.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.no_ImageView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.no_ImageView.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.iv_photo);
        holder.iv_photo.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.ImageAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - ImageAdapter.this.mLastClickTime < 800) {
                    return;
                }
                ImageAdapter.this.mLastClickTime = SystemClock.elapsedRealtime();
                String imageModel = ImageAdapter.this.list.get(position);
                Log.e("fghj", "onClick: " + imageModel);
                if (ImageAdapter.this.imageAdapterInterface != null) {
                    ImageAdapter.this.imageAdapterInterface.item_click(imageModel);
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes5.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;
        ImageView no_ImageView;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            this.iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            this.no_ImageView = (ImageView) view.findViewById(R.id.no_ImageView);
        }
    }
}
