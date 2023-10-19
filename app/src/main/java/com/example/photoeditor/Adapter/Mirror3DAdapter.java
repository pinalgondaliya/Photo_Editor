package com.example.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class Mirror3DAdapter extends RecyclerView.Adapter<Mirror3DAdapter.ViewHolder> {
    private Context context;
    public Mirror3Listener frameListener;
    public int selectedSquareIndex;
    public List<SquareView> squareViewList;

    /* loaded from: classes5.dex */
    public interface Mirror3Listener {
        void onMirrorSelected(SquareView squareView);
    }

    public Mirror3DAdapter(Context context2, Mirror3Listener mirror3Listener) {
        ArrayList arrayList = new ArrayList();
        this.squareViewList = arrayList;
        this.frameListener = mirror3Listener;
        arrayList.add(new SquareView((int) R.drawable.mirror_3d_1_icon, "3D-1", (int) R.drawable.mirror_3d_1));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_2_icon, "3D-2", (int) R.drawable.mirror_3d_2));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_3_icon, "3D-3", (int) R.drawable.mirror_3d_3));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_4_icon, "3D-4", (int) R.drawable.mirror_3d_4));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_5_icon, "3D-5", (int) R.drawable.mirror_3d_5));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_6_icon, "3D-6", (int) R.drawable.mirror_3d_6));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_7_icon, "3D-7", (int) R.drawable.mirror_3d_7));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_8_icon, "3D-8", (int) R.drawable.mirror_3d_8));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_9_icon, "3D-9", (int) R.drawable.mirror_3d_9));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_10_icon, "3D-10", (int) R.drawable.mirror_3d_10));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_11_icon, "3D-11", (int) R.drawable.mirror_3d_11));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_12_icon, "3D-12", (int) R.drawable.mirror_3d_12));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_11_icon, "3D-13", (int) R.drawable.mirror_3d_11));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_12_icon, "3D-14", (int) R.drawable.mirror_3d_12));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_10_icon, "3D-15", (int) R.drawable.mirror_3d_10));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_11_icon, "3D-16", (int) R.drawable.mirror_3d_11));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_12_icon, "3D-17", (int) R.drawable.mirror_3d_12));
        this.squareViewList.add(new SquareView((int) R.drawable.mirror_3d_10_icon, "3D-18", (int) R.drawable.mirror_3d_10));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mirror, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.squareView.setImageResource(this.squareViewList.get(i).drawableId);
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
        public ImageView squareView;
        View viewSelected;

        public ViewHolder(View view) {
            super(view);
            this.squareView = (ImageView) view.findViewById(R.id.square_view);
            this.viewSelected = view.findViewById(R.id.view_selected);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Mirror3DAdapter.this.selectedSquareIndex = getAdapterPosition();
            Mirror3DAdapter.this.frameListener.onMirrorSelected(Mirror3DAdapter.this.squareViewList.get(Mirror3DAdapter.this.selectedSquareIndex));
            Mirror3DAdapter.this.notifyDataSetChanged();
        }
    }

    /* loaded from: classes5.dex */
    public class SquareView {
        public int drawableId;
        public int mirror;
        public String text;

        SquareView(int i, String str, int i2) {
            this.drawableId = i;
            this.text = str;
            this.mirror = i2;
        }

        SquareView(int i, String str, boolean z) {
            this.drawableId = i;
            this.text = str;
        }
    }
}
