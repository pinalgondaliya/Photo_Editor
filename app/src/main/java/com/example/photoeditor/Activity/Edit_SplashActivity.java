package com.example.photoeditor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;

import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.PolishView;
import com.example.photoeditor.Classs.SystemUtil;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.R;

import java.io.File;
import java.io.InputStream;
import org.wysaid.myUtils.MsgUtil;

/* loaded from: classes7.dex */
public class Edit_SplashActivity extends AppCompatActivity {
    public PolishView polishView;
    RelativeLayout relativeLayoutLoading;
    RelativeLayout relativeLayoutWrapper;
    RelativeLayout splash_tool;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_splash);
        this.splash_tool = (RelativeLayout) findViewById(R.id.splash_tool);
        this.relativeLayoutWrapper = (RelativeLayout) findViewById(R.id.relative_layout_wrapper_photo);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_loading);
        this.relativeLayoutLoading = relativeLayout;
        relativeLayout.setVisibility(View.VISIBLE);
        PolishView polishView2 = (PolishView) findViewById(R.id.photo_editor_view);
        this.polishView = polishView2;
        polishView2.setVisibility(View.INVISIBLE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            new loadBitmapUri().execute(extras.getString(Utils.KEY_SELECTED_PHOTOS));
        }
        this.splash_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.Edit_SplashActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BitmapTransfer.bitmap = Edit_SplashActivity.this.polishView.getCurrentBitmap();
                Edit_SplashActivity.this.startActivityForResult(new Intent(Edit_SplashActivity.this, SplashLayout.class), TypedValues.Custom.TYPE_INT);
                Edit_SplashActivity.this.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    /* loaded from: classes7.dex */
    public class loadBitmapUri extends AsyncTask<String, Bitmap, Bitmap> {
        loadBitmapUri() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            Edit_SplashActivity.this.showLoading(true);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Edit_SplashActivity.this.getContentResolver(), fromFile);
                float width = bitmap.getWidth();
                float height = bitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                if (max > 1.0f) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false);
                }
                Bitmap rotateBitmap = SystemUtil.rotateBitmap(bitmap, new ExifInterface(Edit_SplashActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
                if (rotateBitmap != bitmap) {
                    bitmap.recycle();
                }

                return rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            Edit_SplashActivity.this.polishView.setImageSource(bitmap);
            Edit_SplashActivity.this.reloadingLayout();
        }
    }

    public void reloadingLayout() {
        this.polishView.postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.Edit_SplashActivity.2
            @Override // java.lang.Runnable
            public void run() {
                Edit_SplashActivity.this.lambda$reloadingLayout$28$PolishEditorActivity();
            }
        }, 300L);
    }

    public void lambda$reloadingLayout$28$PolishEditorActivity() {
        try {
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            int i = point.x;
            int height = this.relativeLayoutWrapper.getHeight();
//            int i2 = this.polishView.getGLSurfaceView().getRenderViewport().width;
//            float f = this.polishView.getGLSurfaceView().getRenderViewport().height;
            float i2 =0.1f;
            float f2 = i2;
            float f =0.1f;
            if (((int) ((i * f) / f2)) <= height) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
                layoutParams.addRule(13);
                this.polishView.setLayoutParams(layoutParams);
                this.polishView.setVisibility(View.VISIBLE);
            } else {
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) ((height * f2) / f), -1);
                layoutParams2.addRule(13);
                this.polishView.setLayoutParams(layoutParams2);
                this.polishView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading(false);
    }

    public void showLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.relativeLayoutLoading.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.relativeLayoutLoading.setVisibility(View.GONE);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap = null;
        if (i != 13337) {
            super.onActivityResult(i, i2, intent);
            if (i == 123) {
                if (i2 == -1) {
                    try {
                        InputStream openInputStream = getContentResolver().openInputStream(intent.getData());
                        Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                        float width = decodeStream.getWidth();
                        float height = decodeStream.getHeight();
                        float max = Math.max(width / 1280.0f, height / 1280.0f);
                        if (max > 1.0f) {
                            decodeStream = Bitmap.createScaledBitmap(decodeStream, (int) (width / max), (int) (height / max), false);
                        }
                        if (SystemUtil.rotateBitmap(decodeStream, new androidx.exifinterface.media.ExifInterface(openInputStream).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1)) != decodeStream) {
                            decodeStream.recycle();
                        } else {
                            bitmap = decodeStream;
                        }
                        this.polishView.setImageSource(bitmap);
                        reloadingLayout();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgUtil.toastMsg(this, "Error: Can not open image");
                        return;
                    }
                }
                finish();
            } else if (i == 900 && intent != null && intent.getStringExtra("MESSAGE").equals("done") && BitmapTransfer.bitmap != null) {
                new loadBitmap().execute(BitmapTransfer.bitmap);
            }
        }
    }

    /* loaded from: classes7.dex */
    class loadBitmap extends AsyncTask<Bitmap, Bitmap, Bitmap> {
        loadBitmap() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            Edit_SplashActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Bitmap... bitmapArr) {
            try {
                Bitmap bitmap = bitmapArr[0];
                float width = bitmap.getWidth();
                float height = bitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                return max > 1.0f ? Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false) : bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            Edit_SplashActivity.this.polishView.setImageSource(bitmap);
            Edit_SplashActivity.this.reloadingLayout();
        }
    }
}
