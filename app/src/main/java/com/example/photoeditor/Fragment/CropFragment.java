package com.example.photoeditor.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Adapter.AspectAdapter;
import com.example.photoeditor.R;
import com.github.flipzeus.FlipDirection;
import com.github.flipzeus.ImageFlipper;
import com.isseiaoki.simplecropview.CropImageView;
import com.steelkiwi.cropiwa.AspectRatio;

public class CropFragment extends DialogFragment implements AspectAdapter.OnNewSelectedListener {
    private static final String TAG = "CropFragment";
    private Bitmap bitmap;
    public CropImageView crop_image_view;
    public OnCropPhoto onCropPhoto;
    private RelativeLayout relative_layout_loading;

    /* loaded from: classes7.dex */
    public interface OnCropPhoto {
        void finishCrop(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static CropFragment show(AppCompatActivity appCompatActivity, OnCropPhoto onCropPhoto2, Bitmap bitmap2) {
        CropFragment cropFragment = new CropFragment();
        cropFragment.setBitmap(bitmap2);
        cropFragment.setOnCropPhoto(onCropPhoto2);
        cropFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return cropFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void setOnCropPhoto(OnCropPhoto onCropPhoto2) {
        this.onCropPhoto = onCropPhoto2;
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

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_crop, viewGroup, false);
        AspectAdapter aspectAdapter = new AspectAdapter();
        aspectAdapter.setListener(this);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view_ratio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(aspectAdapter);
        CropImageView cropImageView = (CropImageView) inflate.findViewById(R.id.crop_image_view);
        this.crop_image_view = cropImageView;
        cropImageView.setCropMode(CropImageView.CropMode.FREE);
        inflate.findViewById(R.id.relativeLayoutRotate).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.CropFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CropFragment.this.crop_image_view.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
            }
        });
        inflate.findViewById(R.id.relativeLayouRotate90).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.CropFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CropFragment.this.crop_image_view.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });
        inflate.findViewById(R.id.relativeLayoutVFlip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.CropFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageFlipper.flip(CropFragment.this.crop_image_view, FlipDirection.VERTICAL);
            }
        });
        inflate.findViewById(R.id.relativeLayoutHFlip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.CropFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageFlipper.flip(CropFragment.this.crop_image_view, FlipDirection.HORIZONTAL);
            }
        });
        inflate.findViewById(R.id.imageViewSaveCrop).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.CropFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new OnSaveCrop().execute(new Void[0]);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading = relativeLayout;
        relativeLayout.setVisibility(8);
        inflate.findViewById(R.id.imageViewCloseCrop).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.CropFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CropFragment.this.dismiss();
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        CropImageView cropImageView = (CropImageView) view.findViewById(R.id.crop_image_view);
        this.crop_image_view = cropImageView;
        cropImageView.setImageBitmap(this.bitmap);
    }

    @Override // com.example.photoareditor.Adapter.AspectAdapter.OnNewSelectedListener
    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        if (aspectRatio.getWidth() == 10 && aspectRatio.getHeight() == 10) {
            this.crop_image_view.setCropMode(CropImageView.CropMode.FREE);
        } else {
            this.crop_image_view.setCustomRatio(aspectRatio.getWidth(), aspectRatio.getHeight());
        }
    }

    /* loaded from: classes7.dex */
    class OnSaveCrop extends AsyncTask<Void, Bitmap, Bitmap> {
        OnSaveCrop() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            CropFragment.this.mLoading(true);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            return CropFragment.this.crop_image_view.getCroppedBitmap();
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            CropFragment.this.mLoading(false);
            CropFragment.this.onCropPhoto.finishCrop(bitmap);
            CropFragment.this.dismiss();
        }
    }

    public void mLoading(boolean z) {
        if (z) {
            getActivity().getWindow().setFlags(16, 16);
            this.relative_layout_loading.setVisibility(0);
            return;
        }
        getActivity().getWindow().clearFlags(16);
        this.relative_layout_loading.setVisibility(8);
    }
}
