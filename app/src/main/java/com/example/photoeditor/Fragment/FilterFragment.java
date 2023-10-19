package com.example.photoeditor.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.FilterAdapter;
import com.example.photoeditor.Classs.FilterFileAsset;
import com.example.photoeditor.Classs.FilterUtils;
import com.example.photoeditor.Interface.FilterListener;
import com.example.photoeditor.R;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes7.dex */
public class FilterFragment extends DialogFragment implements FilterListener {
    private static final String TAG = "FilterFragment";
    public Bitmap bitmap;
    public ImageView image_view_preview;
    private List<Bitmap> lstFilterBitmap;
    public OnFilterSavePhoto onFilterSavePhoto;
    private RecyclerView recycler_view_filter;
    public TextView textViewTitle;

    /* loaded from: classes7.dex */
    public interface OnFilterSavePhoto {
        void onSaveFilter(Bitmap bitmap);
    }

    public void setOnFilterSavePhoto(OnFilterSavePhoto onFilterSavePhoto2) {
        this.onFilterSavePhoto = onFilterSavePhoto2;
    }

    public void setLstFilterBitmap(List<Bitmap> list) {
        this.lstFilterBitmap = list;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static FilterFragment show(AppCompatActivity appCompatActivity, OnFilterSavePhoto onFilterSavePhoto2, Bitmap bitmap2, List<Bitmap> list) {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setBitmap(bitmap2);
        filterFragment.setOnFilterSavePhoto(onFilterSavePhoto2);
        filterFragment.setLstFilterBitmap(list);
        filterFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return filterFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.lstFilterBitmap.clear();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_filter, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view_filter_all);
        this.recycler_view_filter = recyclerView;
        recyclerView.setAdapter(new FilterAdapter(this.lstFilterBitmap, this, getContext(), Arrays.asList(FilterFileAsset.FILTERS)));
        ImageView imageView = (ImageView) inflate.findViewById(R.id.image_view_preview);
        this.image_view_preview = imageView;
        imageView.setImageBitmap(this.bitmap);
        TextView textView = (TextView) inflate.findViewById(R.id.textViewTitle);
        this.textViewTitle = textView;
        textView.setText("FILTER");
        inflate.findViewById(R.id.imageViewSaveFilter).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.FilterFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FilterFragment.this.onFilterSavePhoto.onSaveFilter(((BitmapDrawable) FilterFragment.this.image_view_preview.getDrawable()).getBitmap());
                FilterFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewCloseFilter).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.FilterFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FilterFragment.this.dismiss();
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ViewCompat.MEASURED_STATE_MASK));
        }
    }

    @Override // com.example.photoareditor.Interface.FilterListener
    public void onFilterSelected(int i, String str) {
        new LoadBitmapWithFilter().execute(str);
    }

    /* loaded from: classes7.dex */
    class LoadBitmapWithFilter extends AsyncTask<String, Bitmap, Bitmap> {
        LoadBitmapWithFilter() {
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            return FilterUtils.getBitmapWithFilter(FilterFragment.this.bitmap, strArr[0]);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            FilterFragment.this.image_view_preview.setImageBitmap(bitmap);
        }
    }
}
