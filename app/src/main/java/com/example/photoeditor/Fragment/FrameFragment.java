package com.example.photoeditor.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Adapter.FrameColorAdapter;
import com.example.photoeditor.Adapter.FrameGradientAdapter;
import com.example.photoeditor.Classs.FilterUtils;
import com.example.photoeditor.Classs.SystemUtil;
import com.example.photoeditor.R;

/* loaded from: classes7.dex */
public class FrameFragment extends DialogFragment implements FrameGradientAdapter.FrameListener, FrameColorAdapter.FrameListener {
    private static final String TAG = "FrameFragment";
    private SeekBar adjustSeekBar;
    private Bitmap bitmap;
    private Bitmap blurBitmap;
    private ConstraintLayout constraint_layout_ratio;
    public FrameLayout frame_layout_wrapper;
    public ImageView image_view_ratio;
    public RatioSaveListener ratioSaveListener;
    public RecyclerView recycler_view_color;
    public RecyclerView recycler_view_gradient;
    private RelativeLayout relative_layout_loading;
    private TextView textViewColor;
    private TextView textViewGradient;

    /* loaded from: classes7.dex */
    public interface RatioSaveListener {
        void ratioSavedBitmap(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static FrameFragment show(AppCompatActivity appCompatActivity, RatioSaveListener ratioSaveListener2, Bitmap bitmap2, Bitmap bitmap3) {
        FrameFragment frameFragment = new FrameFragment();
        frameFragment.setBitmap(bitmap2);
        frameFragment.setBlurBitmap(bitmap3);
        frameFragment.setRatioSaveListener(ratioSaveListener2);
        frameFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return frameFragment;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void setRatioSaveListener(RatioSaveListener ratioSaveListener2) {
        this.ratioSaveListener = ratioSaveListener2;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_frame, viewGroup, false);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading = relativeLayout;
        relativeLayout.setVisibility(View.GONE);
        this.textViewColor = (TextView) inflate.findViewById(R.id.textViewColor);
        this.textViewGradient = (TextView) inflate.findViewById(R.id.textViewGradient);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerViewGradient);
        this.recycler_view_gradient = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_gradient.setAdapter(new FrameGradientAdapter(getContext(), this));
        this.recycler_view_gradient.setVisibility(View.GONE);
        RecyclerView recyclerView2 = (RecyclerView) inflate.findViewById(R.id.recyclerViewColor);
        this.recycler_view_color = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_color.setAdapter(new FrameColorAdapter(getContext(), this));
        this.recycler_view_color.setVisibility(View.VISIBLE);
        inflate.findViewById(R.id.textViewColor).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.FrameFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FrameFragment.this.recycler_view_color.setVisibility(View.VISIBLE);
                FrameFragment.this.recycler_view_gradient.setVisibility(View.GONE);
                FrameFragment.this.textViewColor.setTextColor(FrameFragment.this.getResources().getColor(R.color.white));
                FrameFragment.this.textViewColor.setBackgroundResource(R.drawable.background_selected);
                FrameFragment.this.textViewGradient.setTextColor(FrameFragment.this.getResources().getColor(R.color.black));
                FrameFragment.this.textViewGradient.setBackgroundResource(R.drawable.background_unslelected);
            }
        });
        inflate.findViewById(R.id.textViewGradient).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.FrameFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FrameFragment.this.recycler_view_color.setVisibility(View.GONE);
                FrameFragment.this.recycler_view_gradient.setVisibility(View.VISIBLE);
                FrameFragment.this.textViewColor.setTextColor(FrameFragment.this.getResources().getColor(R.color.black));
                FrameFragment.this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                FrameFragment.this.textViewGradient.setTextColor(FrameFragment.this.getResources().getColor(R.color.white));
                FrameFragment.this.textViewGradient.setBackgroundResource(R.drawable.background_selected);
            }
        });
        SeekBar seekBar = (SeekBar) inflate.findViewById(R.id.seekbarOverlay);
        this.adjustSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Fragment.FrameFragment.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                int dpToPx = SystemUtil.dpToPx(FrameFragment.this.getContext(), i);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) FrameFragment.this.image_view_ratio.getLayoutParams();
                layoutParams.setMargins(dpToPx, dpToPx, dpToPx, dpToPx);
                FrameFragment.this.image_view_ratio.setLayoutParams(layoutParams);
            }
        });
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imageViewFrame);
        this.image_view_ratio = imageView;
        imageView.setImageBitmap(this.bitmap);
        this.image_view_ratio.setAdjustViewBounds(true);
        getActivity().getWindowManager().getDefaultDisplay().getSize(new Point());
        this.constraint_layout_ratio = (ConstraintLayout) inflate.findViewById(R.id.constraintLayoutFrame);
        this.frame_layout_wrapper = (FrameLayout) inflate.findViewById(R.id.frameLayoutWrapper);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_ratio);
        constraintSet.connect(this.frame_layout_wrapper.getId(), 3, this.constraint_layout_ratio.getId(), 3, 0);
        constraintSet.connect(this.frame_layout_wrapper.getId(), 1, this.constraint_layout_ratio.getId(), 1, 0);
        constraintSet.connect(this.frame_layout_wrapper.getId(), 4, this.constraint_layout_ratio.getId(), 4, 0);
        constraintSet.connect(this.frame_layout_wrapper.getId(), 2, this.constraint_layout_ratio.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_ratio);
        inflate.findViewById(R.id.imageViewCloseBlur).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.FrameFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FrameFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewSaveBlur).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.FrameFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SaveRatioView saveRatioView = new SaveRatioView();
                FrameFragment frameFragment = FrameFragment.this;
                saveRatioView.execute(frameFragment.getBitmapFromView(frameFragment.frame_layout_wrapper));
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
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

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    /* loaded from: classes7.dex */
    class SaveRatioView extends AsyncTask<Bitmap, Bitmap, Bitmap> {
        SaveRatioView() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            FrameFragment.this.mLoading(true);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Bitmap... bitmapArr) {
            Bitmap cloneBitmap = FilterUtils.cloneBitmap(bitmapArr[0]);
            bitmapArr[0].recycle();
            bitmapArr[0] = null;
            return cloneBitmap;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            FrameFragment.this.mLoading(false);
            FrameFragment.this.ratioSaveListener.ratioSavedBitmap(bitmap);
            FrameFragment.this.dismiss();
        }
    }

    @Override // com.example.photoareditor.Adapter.FrameColorAdapter.FrameListener
    public void onGradientSelected(FrameColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.frame_layout_wrapper.setBackgroundColor(squareView.drawableId);
        } else {
            this.frame_layout_wrapper.setBackgroundResource(squareView.drawableId);
        }
        this.frame_layout_wrapper.invalidate();
    }

    @Override // com.example.photoareditor.Adapter.FrameGradientAdapter.FrameListener
    public void onBackgroundSelected(FrameGradientAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.frame_layout_wrapper.setBackgroundColor(squareView.drawableId);
        } else {
            this.frame_layout_wrapper.setBackgroundResource(squareView.drawableId);
        }
        this.frame_layout_wrapper.invalidate();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Bitmap bitmap2 = this.blurBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.blurBitmap = null;
        }
        this.bitmap = null;
    }

    public void onColorChanged(String str) {
        this.image_view_ratio.setBackgroundColor(Color.parseColor(str));
        if (!str.equals("#00000000")) {
            int dpToPx = SystemUtil.dpToPx(getContext(), 35);
            this.image_view_ratio.setPadding(dpToPx, dpToPx, dpToPx, dpToPx);
            return;
        }
        this.image_view_ratio.setPadding(0, 0, 0, 0);
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public void mLoading(boolean z) {
        if (z) {
            getActivity().getWindow().setFlags(16, 16);
            this.relative_layout_loading.setVisibility(View.VISIBLE);
            return;
        }
        getActivity().getWindow().clearFlags(16);
        this.relative_layout_loading.setVisibility(View.GONE);
    }
}
