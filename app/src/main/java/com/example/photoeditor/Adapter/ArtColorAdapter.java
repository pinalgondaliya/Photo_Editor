package com.example.photoeditor.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Classs.BrushColorAsset;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ArtColorAdapter extends RecyclerView.Adapter<ArtColorAdapter.ViewHolder> {
    public ArtColorListener artColorListener;
    private Context context;
    public int selectedSquareIndex;
    public List<SquareView> squareViewList = new ArrayList();

    /* loaded from: classes5.dex */
    public interface ArtColorListener {
        void onArtColorSelected(SquareView squareView);
    }

    public ArtColorAdapter(Context context2, ArtColorListener artColorListener2) {
        this.context = context2;
        this.artColorListener = artColorListener2;
        List<String> listColorBrush = BrushColorAsset.listColorBrush();
        for (int i = 0; i < listColorBrush.size() - 2; i++) {
            this.squareViewList.add(new SquareView(Color.parseColor(listColorBrush.get(i)), true));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color_drip, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        SquareView squareView = this.squareViewList.get(i);
        if (squareView.isColor) {
            viewHolder.squareView.setBackgroundColor(squareView.drawableId);
        } else {
            viewHolder.squareView.setBackgroundResource(squareView.drawableId);
        }
        if (this.selectedSquareIndex == i) {
            viewHolder.viewSelected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.viewSelected.setVisibility(View.GONE);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.squareViewList.size();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View squareView;
        ImageView viewSelected;

        public ViewHolder(View view) {
            super(view);
            this.squareView = view.findViewById(R.id.square_view);
            this.viewSelected = (ImageView) view.findViewById(R.id.view_selected);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ArtColorAdapter.this.selectedSquareIndex = getAdapterPosition();
            ArtColorAdapter.this.artColorListener.onArtColorSelected(ArtColorAdapter.this.squareViewList.get(ArtColorAdapter.this.selectedSquareIndex));
            ArtColorAdapter.this.notifyDataSetChanged();
        }
    }

    /* loaded from: classes5.dex */
    public class SquareView {
        public int drawableId;
        public boolean isColor;

        SquareView(int i) {
            this.drawableId = i;
        }

        SquareView(int i, boolean z) {
            this.drawableId = i;
            this.isColor = z;
        }
    }
}
