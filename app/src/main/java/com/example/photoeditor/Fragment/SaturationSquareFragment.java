package com.example.photoeditor.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.SplashSquareAdapter;
import com.example.photoeditor.Classs.PolishSplashSquareView;
import com.example.photoeditor.Classs.PolishSplashSticker;
import com.example.photoeditor.Classs.StickerFileAsset;
import com.example.photoeditor.R;

/* loaded from: classes7.dex */
public class SaturationSquareFragment extends DialogFragment implements SplashSquareAdapter.SplashChangeListener {
    private static final String TAG = "SaturationSquareFragment";
    private Bitmap SaturationBitmap;
    public Bitmap bitmap;
    private FrameLayout frame_layout;
    private ImageView image_view_background;
    public TextView image_view_shape;
    public boolean isSplashView;
    private PolishSplashSticker polishSplashSticker;
    public PolishSplashSquareView polishSplashView;
    public RecyclerView recycler_view_splash;
    public SplashSaturationListener splashSaturationListener;
    private ViewGroup viewGroup;

    /* loaded from: classes7.dex */
    public interface SplashSaturationListener {
        void onSaveSplash(Bitmap bitmap);
    }

    public void setPolishSplashView(boolean z) {
        this.isSplashView = z;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static SaturationSquareFragment show(AppCompatActivity appCompatActivity, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, SplashSaturationListener splashSaturationListener2, boolean z) {
        SaturationSquareFragment saturationSquareFragment = new SaturationSquareFragment();
        saturationSquareFragment.setBitmap(bitmap2);
        saturationSquareFragment.setSaturationBitmap(bitmap4);
        saturationSquareFragment.setSplashSaturationListener(splashSaturationListener2);
        saturationSquareFragment.setPolishSplashView(z);
        saturationSquareFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return saturationSquareFragment;
    }

    public void setSaturationBitmap(Bitmap bitmap2) {
        this.SaturationBitmap = bitmap2;
    }

    public void setSplashSaturationListener(SplashSaturationListener splashSaturationListener2) {
        this.splashSaturationListener = splashSaturationListener2;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup2, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_square, viewGroup2, false);
        this.viewGroup = viewGroup2;
        this.image_view_background = (ImageView) inflate.findViewById(R.id.imageViewBackground);
        this.polishSplashView = (PolishSplashSquareView) inflate.findViewById(R.id.splashView);
        this.frame_layout = (FrameLayout) inflate.findViewById(R.id.frame_layout);
        this.image_view_background.setImageBitmap(this.SaturationBitmap);
        this.image_view_shape = (TextView) inflate.findViewById(R.id.textViewTitle);
        if (this.isSplashView) {
            this.polishSplashView.setImageBitmap(this.bitmap);
            this.image_view_shape.setText("SPLASH SQ");
        }
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerViewSplashSquare);
        this.recycler_view_splash = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_splash.setHasFixedSize(true);
        this.recycler_view_splash.setAdapter(new SplashSquareAdapter(getContext(), this, this.isSplashView));
        if (this.isSplashView) {
            PolishSplashSticker polishSplashSticker2 = new PolishSplashSticker(StickerFileAsset.loadBitmapFromAssets(getContext(), "frame/image_mask_1.webp"), StickerFileAsset.loadBitmapFromAssets(getContext(), "frame/image_frame_1.webp"));
            this.polishSplashSticker = polishSplashSticker2;
            this.polishSplashView.addSticker(polishSplashSticker2);
        }
        this.polishSplashView.refreshDrawableState();
        this.polishSplashView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        this.image_view_shape.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.SaturationSquareFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SaturationSquareFragment.this.polishSplashView.setcSplashMode(0);
                SaturationSquareFragment.this.recycler_view_splash.setVisibility(View.VISIBLE);
                SaturationSquareFragment.this.polishSplashView.refreshDrawableState();
                SaturationSquareFragment.this.polishSplashView.invalidate();
            }
        });
        inflate.findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.SaturationSquareFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SaturationSquareFragment.this.splashSaturationListener.onSaveSplash(SaturationSquareFragment.this.polishSplashView.getBitmap(SaturationSquareFragment.this.SaturationBitmap));
                SaturationSquareFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.SaturationSquareFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SaturationSquareFragment.this.dismiss();
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
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

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.polishSplashView.getSticker().release();
        Bitmap bitmap2 = this.SaturationBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.SaturationBitmap = null;
        this.bitmap = null;
    }

    @Override // com.example.photoareditor.Adapter.SplashSquareAdapter.SplashChangeListener
    public void onSelected(PolishSplashSticker polishSplashSticker2) {
        this.polishSplashView.addSticker(polishSplashSticker2);
    }
}
