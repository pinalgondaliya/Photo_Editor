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
public class BlurSquareBgFragment extends DialogFragment implements SplashSquareAdapter.SplashChangeListener {
    private static final String TAG = "BlurSquareBgFragment";
    private Bitmap BlurBitmap;
    public Bitmap bitmap;
    public BlurSquareBgListener blurSquareBgListener;
    private FrameLayout frameLayout;
    private ImageView imageViewBackground;
    public boolean isSplashView;
    private PolishSplashSticker polishSplashSticker;
    public PolishSplashSquareView polishSplashView;
    public RecyclerView recyclerViewBlur;
    public TextView textVewTitle;
    private ViewGroup viewGroup;

    /* loaded from: classes7.dex */
    public interface BlurSquareBgListener {
        void onSaveBlurBackground(Bitmap bitmap);
    }

    public void setPolishSplashView(boolean z) {
        this.isSplashView = z;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static BlurSquareBgFragment show(AppCompatActivity appCompatActivity, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, BlurSquareBgListener blurSquareBgListener2, boolean z) {
        BlurSquareBgFragment blurSquareBgFragment = new BlurSquareBgFragment();
        blurSquareBgFragment.setBitmap(bitmap2);
        blurSquareBgFragment.setBlurBitmap(bitmap4);
        blurSquareBgFragment.setBlurSquareBgListener(blurSquareBgListener2);
        blurSquareBgFragment.setPolishSplashView(z);
        blurSquareBgFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return blurSquareBgFragment;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.BlurBitmap = bitmap2;
    }

    public void setBlurSquareBgListener(BlurSquareBgListener blurSquareBgListener2) {
        this.blurSquareBgListener = blurSquareBgListener2;
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
        this.imageViewBackground = (ImageView) inflate.findViewById(R.id.imageViewBackground);
        this.polishSplashView = (PolishSplashSquareView) inflate.findViewById(R.id.splashView);
        this.frameLayout = (FrameLayout) inflate.findViewById(R.id.frame_layout);
        this.imageViewBackground.setImageBitmap(this.bitmap);
        this.textVewTitle = (TextView) inflate.findViewById(R.id.textViewTitle);
        if (this.isSplashView) {
            this.polishSplashView.setImageBitmap(this.BlurBitmap);
            this.textVewTitle.setText("BLUR BG");
        }
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerViewSplashSquare);
        this.recyclerViewBlur = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.recyclerViewBlur.setHasFixedSize(true);
        this.recyclerViewBlur.setAdapter(new SplashSquareAdapter(getContext(), this, this.isSplashView));
        if (this.isSplashView) {
            PolishSplashSticker polishSplashSticker2 = new PolishSplashSticker(StickerFileAsset.loadBitmapFromAssets(getContext(), "frame/image_mask_1.webp"), StickerFileAsset.loadBitmapFromAssets(getContext(), "frame/image_frame_1.webp"));
            this.polishSplashSticker = polishSplashSticker2;
            this.polishSplashView.addSticker(polishSplashSticker2);
        }
        this.polishSplashView.refreshDrawableState();
        this.polishSplashView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        this.textVewTitle.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.BlurSquareBgFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurSquareBgFragment.this.polishSplashView.setcSplashMode(0);
                BlurSquareBgFragment.this.recyclerViewBlur.setVisibility(View.VISIBLE);
                BlurSquareBgFragment.this.polishSplashView.refreshDrawableState();
                BlurSquareBgFragment.this.polishSplashView.invalidate();
            }
        });
        inflate.findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.BlurSquareBgFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurSquareBgFragment.this.blurSquareBgListener.onSaveBlurBackground(BlurSquareBgFragment.this.polishSplashView.getBitmap(BlurSquareBgFragment.this.bitmap));
                BlurSquareBgFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.BlurSquareBgFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurSquareBgFragment.this.dismiss();
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
        Bitmap bitmap2 = this.BlurBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.BlurBitmap = null;
        this.bitmap = null;
    }

    @Override // com.example.photoareditor.Adapter.SplashSquareAdapter.SplashChangeListener
    public void onSelected(PolishSplashSticker polishSplashSticker2) {
        this.polishSplashView.addSticker(polishSplashSticker2);
    }
}
