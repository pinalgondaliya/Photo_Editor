package com.example.photoeditor.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Activity.All_Art_Images;
import com.example.photoeditor.Activity.ViewArtImages;
import com.example.photoeditor.ModelClass.ArtData;
import com.example.photoeditor.R;

import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class NewArtAdapter extends RecyclerView.Adapter<NewArtAdapter.ViewHolder> {
    All_Art_Images all_art_images;
    ArrayList<ArtData> list;

    public NewArtAdapter(All_Art_Images all_art_images, ArrayList<ArtData> list) {
        this.all_art_images = all_art_images;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.all_art_images).inflate(R.layout.item_new_art, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        String thumb = this.list.get(position).getThumb();
        final String image = this.list.get(position).getImage();
        Glide.with((FragmentActivity) this.all_art_images).load(thumb).into(holder.art_image_thumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.NewArtAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(NewArtAdapter.this.all_art_images, ViewArtImages.class);
                intent.putExtra(ClientCookie.PATH_ATTR, image);
                NewArtAdapter.this.all_art_images.startActivity(intent);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView art_image_thumb;

        public ViewHolder(View itemView) {
            super(itemView);
            this.art_image_thumb = (ImageView) itemView.findViewById(R.id.art_image_thumb);
        }
    }
}
