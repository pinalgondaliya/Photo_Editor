package com.example.photoeditor.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.Mirror2DAdapter;
import com.example.photoeditor.Adapter.Mirror3DAdapter;
import com.example.photoeditor.Classs.FilterUtils;
import com.example.photoeditor.Classs.Mirror2D_2Layer;
import com.example.photoeditor.Classs.Mirror2D_3L;
import com.example.photoeditor.Classs.Mirror2D_3Layer;
import com.example.photoeditor.Classs.Mirror3D_2Layer;
import com.example.photoeditor.Classs.Mirror3D_4Layer;
import com.example.photoeditor.Classs.SquareLayout;
import com.example.photoeditor.R;
import com.github.flipzeus.FlipDirection;
import com.github.flipzeus.ImageFlipper;

/* loaded from: classes7.dex */
public class MirrorFragment extends DialogFragment implements Mirror3DAdapter.Mirror3Listener, Mirror2DAdapter.Mirror2Listener {
    private static final String TAG = "MirrorFragment";
    private Bitmap bitmap;
    private Bitmap blurBitmap;
    Mirror3D_2Layer dragLayout2D_1;
    Mirror2D_3Layer dragLayout2D_10;
    Mirror2D_3L dragLayout2D_11;
    Mirror2D_3L dragLayout2D_12;
    Mirror2D_3L dragLayout2D_13;
    Mirror3D_2Layer dragLayout2D_2;
    Mirror2D_2Layer dragLayout2D_3;
    Mirror2D_2Layer dragLayout2D_4;
    Mirror3D_2Layer dragLayout2D_5;
    Mirror3D_2Layer dragLayout2D_6;
    Mirror2D_3Layer dragLayout2D_7;
    Mirror2D_3Layer dragLayout2D_8;
    Mirror2D_3Layer dragLayout2D_9;
    Mirror3D_2Layer dragLayout3D_1;
    Mirror3D_2Layer dragLayout3D_2;
    Mirror3D_4Layer dragLayout3D_3;
    Mirror3D_4Layer dragLayout3D_4;
    Mirror3D_4Layer dragLayout3D_5;
    Mirror3D_4Layer dragLayout3D_6;
    public FrameLayout frame;
    public FrameLayout frame_layout_wrapper;
    ImageView imageView2D_1;
    ImageView imageView2D_10;
    ImageView imageView2D_11;
    ImageView imageView2D_12;
    ImageView imageView2D_13;
    ImageView imageView2D_2;
    ImageView imageView2D_3;
    ImageView imageView2D_4;
    ImageView imageView2D_5;
    ImageView imageView2D_6;
    ImageView imageView2D_7;
    ImageView imageView2D_8;
    ImageView imageView2D_9;
    ImageView imageView3D_1;
    ImageView imageView3D_2;
    ImageView imageView3D_3;
    ImageView imageView3D_4;
    ImageView imageView3D_5;
    ImageView imageView3D_6;
    public FrameFragment.RatioSaveListener ratioSaveListener;
    public RecyclerView recycler_view_background;
    private RelativeLayout relative_layout_loading;
    private SquareLayout squareLayout2D_1;
    private SquareLayout squareLayout2D_11;
    private SquareLayout squareLayout2D_3;
    private SquareLayout squareLayout2D_5;
    private SquareLayout squareLayout2D_7;
    private SquareLayout squareLayout2D_9;
    private SquareLayout squareLayout3D_1;
    private SquareLayout squareLayout3D_3;

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static MirrorFragment show(AppCompatActivity appCompatActivity, FrameFragment.RatioSaveListener ratioSaveListener2, Bitmap bitmap2, Bitmap bitmap3) {
        MirrorFragment mirrorFragment = new MirrorFragment();
        mirrorFragment.setBitmap(bitmap2);
        mirrorFragment.setBlurBitmap(bitmap3);
        mirrorFragment.setRatioSaveListener(ratioSaveListener2);
        mirrorFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return mirrorFragment;
    }

    public void setRatioSaveListener(FrameFragment.RatioSaveListener ratioSaveListener2) {
        this.ratioSaveListener = ratioSaveListener2;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_mirrors, viewGroup, false);
        initViews(inflate);
        return inflate;
    }

    @SuppressLint("WrongConstant")
    private void initViews(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading = relativeLayout;
        relativeLayout.setVisibility(View.GONE);
        getActivity().getWindowManager().getDefaultDisplay().getSize(new Point());
        this.frame_layout_wrapper = (FrameLayout) view.findViewById(R.id.frameLayoutWrapper);
        this.frame = (FrameLayout) view.findViewById(R.id.frameLayout3D_1);
        view.findViewById(R.id.imageViewCloseMirror).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.MirrorFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                MirrorFragment.this.dismiss();
            }
        });
        view.findViewById(R.id.imageViewSaveMirror).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Fragment.MirrorFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                SaveRatioView saveRatioView = new SaveRatioView();
                MirrorFragment mirrorFragment = MirrorFragment.this;
                saveRatioView.execute(mirrorFragment.getBitmapFromView(mirrorFragment.frame_layout_wrapper));
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMirror);
        this.recycler_view_background = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recycler_view_background.setAdapter(new Mirror3DAdapter(getContext(), this));
        this.recycler_view_background.setVisibility(0);
        SquareLayout squareLayout = (SquareLayout) view.findViewById(R.id.squareLayout2d_1);
        this.squareLayout2D_1 = squareLayout;
        squareLayout.setVisibility(8);
        SquareLayout squareLayout2 = (SquareLayout) view.findViewById(R.id.squareLayout2d_3);
        this.squareLayout2D_3 = squareLayout2;
        squareLayout2.setVisibility(8);
        SquareLayout squareLayout3 = (SquareLayout) view.findViewById(R.id.squareLayout2d_5);
        this.squareLayout2D_5 = squareLayout3;
        squareLayout3.setVisibility(8);
        SquareLayout squareLayout4 = (SquareLayout) view.findViewById(R.id.squareLayout2d_7);
        this.squareLayout2D_7 = squareLayout4;
        squareLayout4.setVisibility(8);
        SquareLayout squareLayout5 = (SquareLayout) view.findViewById(R.id.squareLayout2d_9);
        this.squareLayout2D_9 = squareLayout5;
        squareLayout5.setVisibility(8);
        SquareLayout squareLayout6 = (SquareLayout) view.findViewById(R.id.squareLayout2d_11);
        this.squareLayout2D_11 = squareLayout6;
        squareLayout6.setVisibility(8);
        SquareLayout squareLayout7 = (SquareLayout) view.findViewById(R.id.squareLayout3d_1);
        this.squareLayout3D_1 = squareLayout7;
        squareLayout7.setVisibility(0);
        SquareLayout squareLayout8 = (SquareLayout) view.findViewById(R.id.squareLayout3d_3);
        this.squareLayout3D_3 = squareLayout8;
        squareLayout8.setVisibility(8);
        this.frame.setVisibility(0);
        this.dragLayout2D_1 = (Mirror3D_2Layer) view.findViewById(R.id.drag2D_1);
        this.dragLayout2D_2 = (Mirror3D_2Layer) view.findViewById(R.id.drag2D_2);
        this.dragLayout2D_1.init(getContext(), this.dragLayout2D_2);
        this.dragLayout2D_2.init(getContext(), this.dragLayout2D_1);
        this.dragLayout2D_1.applyScaleAndTranslation();
        this.dragLayout2D_2.applyScaleAndTranslation();
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2D_1);
        this.imageView2D_1 = imageView;
        imageView.setImageBitmap(this.bitmap);
        this.imageView2D_1.setAdjustViewBounds(true);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2D_2);
        this.imageView2D_2 = imageView2;
        imageView2.setImageBitmap(this.bitmap);
        this.imageView2D_2.setAdjustViewBounds(true);
        this.dragLayout2D_3 = (Mirror2D_2Layer) view.findViewById(R.id.drag2D_3);
        this.dragLayout2D_4 = (Mirror2D_2Layer) view.findViewById(R.id.drag2D_4);
        this.dragLayout2D_3.init(getContext(), this.dragLayout2D_4);
        this.dragLayout2D_4.init(getContext(), this.dragLayout2D_3);
        this.dragLayout2D_3.applyScaleAndTranslation();
        this.dragLayout2D_4.applyScaleAndTranslation();
        ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView2D_3);
        this.imageView2D_3 = imageView3;
        imageView3.setImageBitmap(this.bitmap);
        this.imageView2D_3.setAdjustViewBounds(true);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.imageView2D_4);
        this.imageView2D_4 = imageView4;
        imageView4.setImageBitmap(this.bitmap);
        this.imageView2D_4.setAdjustViewBounds(true);
        ImageFlipper.flip(this.imageView2D_4, FlipDirection.HORIZONTAL);
        this.dragLayout2D_5 = (Mirror3D_2Layer) view.findViewById(R.id.drag2D_5);
        this.dragLayout2D_6 = (Mirror3D_2Layer) view.findViewById(R.id.drag2D_6);
        this.dragLayout2D_5.init(getContext(), this.dragLayout2D_6);
        this.dragLayout2D_6.init(getContext(), this.dragLayout2D_5);
        this.dragLayout2D_5.applyScaleAndTranslation();
        this.dragLayout2D_6.applyScaleAndTranslation();
        ImageView imageView5 = (ImageView) view.findViewById(R.id.imageView2D_5);
        this.imageView2D_5 = imageView5;
        imageView5.setImageBitmap(this.bitmap);
        this.imageView2D_5.setAdjustViewBounds(true);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.imageView2D_6);
        this.imageView2D_6 = imageView6;
        imageView6.setImageBitmap(this.bitmap);
        this.imageView2D_6.setAdjustViewBounds(true);
        ImageFlipper.flip(this.imageView2D_6, FlipDirection.VERTICAL);
        this.dragLayout2D_7 = (Mirror2D_3Layer) view.findViewById(R.id.drag2D_7);
        this.dragLayout2D_8 = (Mirror2D_3Layer) view.findViewById(R.id.drag2D_8);
        this.dragLayout2D_7.init(getContext(), this.dragLayout2D_8, true);
        this.dragLayout2D_8.init(getContext(), this.dragLayout2D_7, true);
        this.dragLayout2D_7.applyScaleAndTranslation();
        this.dragLayout2D_8.applyScaleAndTranslation();
        ImageView imageView7 = (ImageView) view.findViewById(R.id.imageView2D_7);
        this.imageView2D_7 = imageView7;
        imageView7.setImageBitmap(this.bitmap);
        this.imageView2D_7.setAdjustViewBounds(true);
        ImageView imageView8 = (ImageView) view.findViewById(R.id.imageView2D_8);
        this.imageView2D_8 = imageView8;
        imageView8.setImageBitmap(this.bitmap);
        this.imageView2D_8.setAdjustViewBounds(true);
        this.dragLayout2D_9 = (Mirror2D_3Layer) view.findViewById(R.id.drag2D_9);
        this.dragLayout2D_10 = (Mirror2D_3Layer) view.findViewById(R.id.drag2D_10);
        this.dragLayout2D_9.init(getContext(), this.dragLayout2D_10, true);
        this.dragLayout2D_10.init(getContext(), this.dragLayout2D_9, true);
        this.dragLayout2D_9.applyScaleAndTranslation();
        this.dragLayout2D_10.applyScaleAndTranslation();
        ImageView imageView9 = (ImageView) view.findViewById(R.id.imageView2D_9);
        this.imageView2D_9 = imageView9;
        imageView9.setImageBitmap(this.bitmap);
        this.imageView2D_9.setAdjustViewBounds(true);
        ImageView imageView10 = (ImageView) view.findViewById(R.id.imageView2D_10);
        this.imageView2D_10 = imageView10;
        imageView10.setImageBitmap(this.bitmap);
        this.imageView2D_10.setAdjustViewBounds(true);
        this.dragLayout2D_11 = (Mirror2D_3L) view.findViewById(R.id.drag2D_11);
        this.dragLayout2D_12 = (Mirror2D_3L) view.findViewById(R.id.drag2D_12);
        this.dragLayout2D_13 = (Mirror2D_3L) view.findViewById(R.id.drag2D_13);
        this.dragLayout2D_11.init(getContext(), this.dragLayout2D_12, this.dragLayout2D_13);
        this.dragLayout2D_12.init(getContext(), this.dragLayout2D_13, this.dragLayout2D_11);
        this.dragLayout2D_13.init(getContext(), this.dragLayout2D_11, this.dragLayout2D_12);
        this.dragLayout2D_11.applyScaleAndTranslation();
        this.dragLayout2D_12.applyScaleAndTranslation();
        this.dragLayout2D_13.applyScaleAndTranslation();
        ImageView imageView11 = (ImageView) view.findViewById(R.id.imageView2D_11);
        this.imageView2D_11 = imageView11;
        imageView11.setImageBitmap(this.bitmap);
        this.imageView2D_11.setAdjustViewBounds(true);
        ImageView imageView12 = (ImageView) view.findViewById(R.id.imageView2D_12);
        this.imageView2D_12 = imageView12;
        imageView12.setImageBitmap(this.bitmap);
        this.imageView2D_12.setAdjustViewBounds(true);
        ImageView imageView13 = (ImageView) view.findViewById(R.id.imageView2D_13);
        this.imageView2D_13 = imageView13;
        imageView13.setImageBitmap(this.bitmap);
        this.imageView2D_13.setAdjustViewBounds(true);
        this.dragLayout3D_1 = (Mirror3D_2Layer) view.findViewById(R.id.drag3D_1);
        this.dragLayout3D_2 = (Mirror3D_2Layer) view.findViewById(R.id.drag3D_2);
        this.dragLayout3D_1.init(getContext(), this.dragLayout3D_2);
        this.dragLayout3D_2.init(getContext(), this.dragLayout3D_1);
        this.dragLayout3D_1.applyScaleAndTranslation();
        this.dragLayout3D_2.applyScaleAndTranslation();
        ImageView imageView14 = (ImageView) view.findViewById(R.id.imageView3D_1);
        this.imageView3D_1 = imageView14;
        imageView14.setImageBitmap(this.bitmap);
        this.imageView3D_1.setAdjustViewBounds(true);
        ImageView imageView15 = (ImageView) view.findViewById(R.id.imageView3D_2);
        this.imageView3D_2 = imageView15;
        imageView15.setImageBitmap(this.bitmap);
        this.imageView3D_2.setAdjustViewBounds(true);
        this.dragLayout3D_3 = (Mirror3D_4Layer) view.findViewById(R.id.drag3D_3);
        this.dragLayout3D_4 = (Mirror3D_4Layer) view.findViewById(R.id.drag3D_4);
        this.dragLayout3D_5 = (Mirror3D_4Layer) view.findViewById(R.id.drag3D_5);
        this.dragLayout3D_6 = (Mirror3D_4Layer) view.findViewById(R.id.drag3D_6);
        this.dragLayout3D_3.init(getContext(), this.dragLayout3D_4, this.dragLayout3D_5, this.dragLayout3D_6);
        this.dragLayout3D_4.init(getContext(), this.dragLayout3D_5, this.dragLayout3D_6, this.dragLayout3D_3);
        this.dragLayout3D_5.init(getContext(), this.dragLayout3D_6, this.dragLayout3D_3, this.dragLayout3D_4);
        this.dragLayout3D_6.init(getContext(), this.dragLayout3D_3, this.dragLayout3D_4, this.dragLayout3D_5);
        this.dragLayout3D_3.applyScaleAndTranslation();
        this.dragLayout3D_4.applyScaleAndTranslation();
        this.dragLayout3D_5.applyScaleAndTranslation();
        this.dragLayout3D_6.applyScaleAndTranslation();
        ImageView imageView16 = (ImageView) view.findViewById(R.id.imageView3D_3);
        this.imageView3D_3 = imageView16;
        imageView16.setImageBitmap(this.bitmap);
        this.imageView3D_3.setAdjustViewBounds(true);
        ImageView imageView17 = (ImageView) view.findViewById(R.id.imageView3D_4);
        this.imageView3D_4 = imageView17;
        imageView17.setImageBitmap(this.bitmap);
        this.imageView3D_4.setAdjustViewBounds(true);
        ImageView imageView18 = (ImageView) view.findViewById(R.id.imageView3D_5);
        this.imageView3D_5 = imageView18;
        imageView18.setImageBitmap(this.bitmap);
        this.imageView3D_5.setAdjustViewBounds(true);
        ImageView imageView19 = (ImageView) view.findViewById(R.id.imageView3D_6);
        this.imageView3D_6 = imageView19;
        imageView19.setImageBitmap(this.bitmap);
        this.imageView3D_6.setAdjustViewBounds(true);
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

    @Override // com.example.photoareditor.Adapter.Mirror3DAdapter.Mirror3Listener
    public void onMirrorSelected(Mirror3DAdapter.SquareView squareView) {
        this.frame.setBackgroundResource(squareView.mirror);
        this.frame.setVisibility(View.VISIBLE);
        this.squareLayout2D_1.setVisibility(View.GONE);
        this.squareLayout2D_3.setVisibility(View.GONE);
        this.squareLayout2D_5.setVisibility(View.GONE);
        if (squareView.text.equals("3D-1") || squareView.text.equals("3D-2") || squareView.text.equals("3D-3") || squareView.text.equals("3D-4") || squareView.text.equals("3D-5") || squareView.text.equals("3D-6") || squareView.text.equals("3D-7") || squareView.text.equals("3D-8") || squareView.text.equals("3D-9") || squareView.text.equals("3D-10") || squareView.text.equals("3D-11") || squareView.text.equals("3D-12")) {
            this.squareLayout3D_1.setVisibility(View.VISIBLE);
            this.squareLayout3D_3.setVisibility(View.GONE);
        } else if (squareView.text.equals("3D-13") || squareView.text.equals("3D-14") || squareView.text.equals("3D-15")) {
            this.squareLayout3D_1.setVisibility(View.GONE);
            this.squareLayout3D_3.setVisibility(View.VISIBLE);
        }
        this.frame_layout_wrapper.invalidate();
    }

    @Override // com.example.photoareditor.Adapter.Mirror2DAdapter.Mirror2Listener
    public void onBackgroundSelected(Mirror2DAdapter.SquareView squareView) {
        this.frame.setVisibility(View.GONE);
        this.squareLayout3D_1.setVisibility(View.GONE);
        this.squareLayout3D_3.setVisibility(View.GONE);
        if (squareView.text.equals("2D-1")) {
            this.squareLayout2D_1.setVisibility(View.VISIBLE);
            this.squareLayout2D_3.setVisibility(View.GONE);
            this.squareLayout2D_5.setVisibility(View.GONE);
            this.squareLayout2D_7.setVisibility(View.GONE);
            this.squareLayout2D_9.setVisibility(View.GONE);
            this.squareLayout2D_11.setVisibility(View.GONE);
        } else if (squareView.text.equals("2D-2")) {
            this.squareLayout2D_1.setVisibility(View.GONE);
            this.squareLayout2D_3.setVisibility(View.VISIBLE);
            this.squareLayout2D_5.setVisibility(View.GONE);
            this.squareLayout2D_7.setVisibility(View.GONE);
            this.squareLayout2D_9.setVisibility(View.GONE);
            this.squareLayout2D_11.setVisibility(View.GONE);
        } else if (squareView.text.equals("2D-3")) {
            this.squareLayout2D_1.setVisibility(View.GONE);
            this.squareLayout2D_3.setVisibility(View.GONE);
            this.squareLayout2D_5.setVisibility(View.VISIBLE);
            this.squareLayout2D_7.setVisibility(View.GONE);
            this.squareLayout2D_9.setVisibility(View.GONE);
            this.squareLayout2D_11.setVisibility(View.GONE);
        } else if (squareView.text.equals("2D-4")) {
            this.squareLayout2D_1.setVisibility(View.GONE);
            this.squareLayout2D_3.setVisibility(View.GONE);
            this.squareLayout2D_5.setVisibility(View.GONE);
            this.squareLayout2D_7.setVisibility(View.VISIBLE);
            this.squareLayout2D_9.setVisibility(View.GONE);
            this.squareLayout2D_11.setVisibility(View.GONE);
        } else if (squareView.text.equals("2D-5")) {
            this.squareLayout2D_1.setVisibility(View.GONE);
            this.squareLayout2D_3.setVisibility(View.GONE);
            this.squareLayout2D_5.setVisibility(View.GONE);
            this.squareLayout2D_7.setVisibility(View.GONE);
            this.squareLayout2D_9.setVisibility(View.VISIBLE);
            this.squareLayout2D_11.setVisibility(View.GONE);
        } else if (squareView.text.equals("2D-6")) {
            this.squareLayout2D_1.setVisibility(View.GONE);
            this.squareLayout2D_3.setVisibility(View.GONE);
            this.squareLayout2D_5.setVisibility(View.GONE);
            this.squareLayout2D_7.setVisibility(View.GONE);
            this.squareLayout2D_9.setVisibility(View.GONE);
            this.squareLayout2D_11.setVisibility(View.VISIBLE);
        }
        this.frame_layout_wrapper.invalidate();
    }

    /* loaded from: classes7.dex */
    class SaveRatioView extends AsyncTask<Bitmap, Bitmap, Bitmap> {
        SaveRatioView() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            MirrorFragment.this.mLoading(true);
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
            MirrorFragment.this.mLoading(false);
            MirrorFragment.this.ratioSaveListener.ratioSavedBitmap(bitmap);
            MirrorFragment.this.dismiss();
        }
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
