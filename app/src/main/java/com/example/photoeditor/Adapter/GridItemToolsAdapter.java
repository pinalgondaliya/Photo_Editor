package com.example.photoeditor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Classs.Module;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GridItemToolsAdapter extends RecyclerView.Adapter<GridItemToolsAdapter.ViewHolder> {
    public List<ToolModel> mToolList;
    public OnPieceFuncItemSelected onPieceFuncItemSelected;

    /* loaded from: classes5.dex */
    public interface OnPieceFuncItemSelected {
        void onPieceFuncSelected(Module module);
    }

    public GridItemToolsAdapter(OnPieceFuncItemSelected onPieceFuncItemSelected2) {
        ArrayList arrayList = new ArrayList();
        this.mToolList = arrayList;
        this.onPieceFuncItemSelected = onPieceFuncItemSelected2;
        arrayList.add(new ToolModel("Change", R.drawable.ic_replce, Module.REPLACE));
        this.mToolList.add(new ToolModel("Vertical", R.drawable.ic_flip_vertical, Module.V_FLIP));
        this.mToolList.add(new ToolModel("Horizontal", R.drawable.ic_flip_horizontal, Module.H_FLIP));
        this.mToolList.add(new ToolModel("Rotate", R.drawable.ic_rotate_right, Module.ROTATE));
        this.mToolList.add(new ToolModel("Crop", R.drawable.ic_crop, Module.CROP));
    }

    /* loaded from: classes5.dex */
    public class ToolModel {
        public int mToolIcon;
        public String mToolName;
        public Module mToolType;

        ToolModel(String str, int i, Module module) {
            this.mToolName = str;
            this.mToolIcon = i;
            this.mToolType = module;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_editing_tool, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ToolModel toolModel = this.mToolList.get(i);
        viewHolder.text_view_tool_name.setText(toolModel.mToolName);
        viewHolder.image_view_tool_icon.setImageResource(toolModel.mToolIcon);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mToolList.size();
    }

    /* loaded from: classes5.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view_tool_icon;
        TextView text_view_tool_name;

        ViewHolder(View view) {
            super(view);
            this.image_view_tool_icon = (ImageView) view.findViewById(R.id.image_view_tool_icon);
            this.text_view_tool_name = (TextView) view.findViewById(R.id.text_view_tool_name);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.GridItemToolsAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    GridItemToolsAdapter.this.onPieceFuncItemSelected.onPieceFuncSelected(GridItemToolsAdapter.this.mToolList.get(ViewHolder.this.getLayoutPosition()).mToolType);
                }
            });
        }
    }
}
