package com.example.photoeditor.Adapter;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.photoeditor.Activity.EditorActivity;
import com.example.photoeditor.ModelClass.Stickermodel;
import com.example.photoeditor.ModelClass.stickerlistModel;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class Sticker_Category_Adapter extends RecyclerView.Adapter<Sticker_Category_Adapter.Myclass> {
    final String PREFS_NAME = "MyPrefsFile";
    EditorActivity context;
    private int newpos;
    private final SharedPreferences settings;
    ArrayList<Stickermodel.Datum> stickerData;

    public Sticker_Category_Adapter(EditorActivity context2, ArrayList<Stickermodel.Datum> stickerData) {
        this.context = context2;
        this.stickerData = stickerData;
        this.settings = context2.getSharedPreferences("MyPrefsFile", 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.settings.edit().putBoolean("my_first_time", true).apply();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(Myclass holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public Myclass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.sticker_category_items, parent, false);
        Myclass myclass = new Myclass(view);
        return myclass;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final Myclass holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with((FragmentActivity) this.context).load(this.stickerData.get(position).catthumb).listener(new RequestListener<Drawable>() { // from class: com.example.photoareditor.Adapter.Sticker_Category_Adapter.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.loading_view.setVisibility(View.VISIBLE);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.loading_view.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.sticker_category);
        if (this.settings.getBoolean("my_first_time", true)) {
            List<stickerlistModel> stickerlistModels = this.stickerData.get(position).stickers;
            EditorActivity editorActivity = this.context;
            Sticker_Thamb_Adapter sticker_thamb_adapter = new Sticker_Thamb_Adapter(editorActivity, stickerlistModels, editorActivity);
            EditorActivity.recycler_tab_layout.setAdapter(sticker_thamb_adapter);
            this.settings.edit().putBoolean("my_first_time", false).apply();
        }
        if (this.newpos == position) {
            holder.stroke.setVisibility(View.VISIBLE);
        } else {
            holder.stroke.setVisibility(View.GONE);
        }
        holder.sticker_category.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.Sticker_Category_Adapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                List<stickerlistModel> stickerlistModels2 = Sticker_Category_Adapter.this.stickerData.get(position).stickers;
                Sticker_Thamb_Adapter sticker_thamb_adapter2 = new Sticker_Thamb_Adapter(Sticker_Category_Adapter.this.context, stickerlistModels2, Sticker_Category_Adapter.this.context);
                EditorActivity.recycler_tab_layout.setAdapter(sticker_thamb_adapter2);
                Sticker_Category_Adapter.this.newpos = position;
                Sticker_Category_Adapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.stickerData.size();
    }

    /* loaded from: classes5.dex */
    public class Myclass extends RecyclerView.ViewHolder {
        LottieAnimationView loading_view;
        ImageView sticker_category;
        RelativeLayout stroke;

        public Myclass(View itemView) {
            super(itemView);
            this.sticker_category = (ImageView) itemView.findViewById(R.id.sticker_category);
            this.stroke = (RelativeLayout) itemView.findViewById(R.id.stroke);
            this.loading_view = (LottieAnimationView) itemView.findViewById(R.id.loading_view);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
