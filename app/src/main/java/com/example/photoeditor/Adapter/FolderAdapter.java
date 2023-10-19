package com.example.photoeditor.Adapter;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.photoeditor.Activity.Image_GalleryActivity;
import com.example.photoeditor.ModelClass.BeacketBean;
import com.example.photoeditor.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {
    Image_GalleryActivity context;
    FolderAdapterInterface folderAdapterInterface;
    ArrayList<BeacketBean> list;
    private long mLastClickTime;

    /* loaded from: classes5.dex */
    public interface FolderAdapterInterface {
        void itemClick(int i);
    }

    public FolderAdapter(Image_GalleryActivity galleryActivity, ArrayList<BeacketBean> folderList, FolderAdapterInterface folderAdapterInterface) {
        this.context = galleryActivity;
        this.list = folderList;
        this.folderAdapterInterface = folderAdapterInterface;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item_list, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int device_width = displaymetrics.widthPixels / 2;
        holder.relativeLayout.getLayoutParams().width = device_width;
        holder.relativeLayout.getLayoutParams().height = device_width;
        Glide.with((FragmentActivity) this.context).load(this.list.get(position).cover).listener(new RequestListener<Drawable>() { // from class: com.example.photoareditor.Adapter.FolderAdapter.1
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
        }).into(holder.iv_dir_cover);
        holder.tv_dir_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tv_dir_name.setSingleLine(true);
        holder.tv_dir_name.setMarqueeRepeatLimit(-1);
        holder.tv_dir_name.setSelected(true);
        holder.tv_dir_name.setText("" + this.list.get(position).name + " (" + this.list.get(position).count + " )");
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.FolderAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - FolderAdapter.this.mLastClickTime < 800) {
                    return;
                }
                FolderAdapter.this.mLastClickTime = SystemClock.elapsedRealtime();
                if (FolderAdapter.this.folderAdapterInterface != null) {
                    FolderAdapter.this.folderAdapterInterface.itemClick(holder.getAdapterPosition());
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
        ImageView iv_dir_cover;
        ImageView no_ImageView;
        RelativeLayout relativeLayout;
        TextView tv_dir_name;

        public MyViewHolder(View view) {
            super(view);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            this.iv_dir_cover = (ImageView) view.findViewById(R.id.iv_dir_cover);
            this.tv_dir_name = (TextView) view.findViewById(R.id.tv_dir_name);
            this.no_ImageView = (ImageView) view.findViewById(R.id.no_ImageView);
        }
    }
}
