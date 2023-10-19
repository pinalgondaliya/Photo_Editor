package com.example.photoeditor.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Classs.BrushColorAsset;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

public class TetxBackgroundAdapter extends RecyclerView.Adapter<TetxBackgroundAdapter.ViewHolder> {
    public BackgroundColorListener backgroundColorListener;
    private Context context;
    public int selectedSquareIndex;
    public List<SquareView> squareViewList = new ArrayList();

    public interface BackgroundColorListener {
        void onBackgroundColorSelected(SquareView squareView);
    }

    public TetxBackgroundAdapter(Context context2, BackgroundColorListener backgroundColorListener2) {
        this.context = context2;
        this.backgroundColorListener = backgroundColorListener2;
        List<String> listColorBrush = BrushColorAsset.listColorBrush();
        for (int i = 0; i < listColorBrush.size() - 2; i++) {
            this.squareViewList.add(new SquareView(Color.parseColor(listColorBrush.get(i)), true));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color_text, viewGroup, false));
    }

    @Override
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

    @Override
    public int getItemCount() {
        return this.squareViewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View squareView;
        ImageView viewSelected;
        public RelativeLayout wrapSquareView;

        public ViewHolder(View view) {
            super(view);
            this.squareView = view.findViewById(R.id.square_view);
            this.viewSelected = (ImageView) view.findViewById(R.id.view_selected);
            this.wrapSquareView = (RelativeLayout) view.findViewById(R.id.constraint_layout_wrapper_square_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TetxBackgroundAdapter.this.selectedSquareIndex = getAdapterPosition();
            TetxBackgroundAdapter.this.backgroundColorListener.onBackgroundColorSelected(TetxBackgroundAdapter.this.squareViewList.get(TetxBackgroundAdapter.this.selectedSquareIndex));
            TetxBackgroundAdapter.this.notifyDataSetChanged();
        }
    }

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
