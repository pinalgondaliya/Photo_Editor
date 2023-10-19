package com.example.photoeditor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photoeditor.Classs.FilterUtils;
import com.example.photoeditor.Classs.PolishView;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Fragment.BlurSquareBgFragment;
import com.example.photoeditor.Fragment.SaturationSquareBackgroundFragment;
import com.example.photoeditor.Fragment.SaturationSquareFragment;
import com.example.photoeditor.Fragment.SketchSquareBackgroundFragment;
import com.example.photoeditor.Fragment.SketchSquareFragment;
import com.example.photoeditor.R;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes7.dex */
public class SketchActivity extends AppCompatActivity implements SaturationSquareBackgroundFragment.SplashSaturationBackgrundListener, SketchSquareBackgroundFragment.SketchBackgroundListener, BlurSquareBgFragment.BlurSquareBgListener, SaturationSquareFragment.SplashSaturationListener, SketchSquareFragment.SketchListener {
    static Bitmap bitmap;
    RelativeLayout blur_bg_tool;
    private File file;
    private Bitmap finalbitmap;
    PolishView photo_editor_view_1;
    ImageView polishView;
    RelativeLayout relative_layout_loading;
    TextView save;
    RelativeLayout sketch_sq_tool;
    RelativeLayout sketch_tool;
    RelativeLayout splasg_bg_tool;
    RelativeLayout splash_sq_tool;

    public static void setFaceBitmap(Bitmap bitmappp) {
        bitmap = bitmappp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);
        this.polishView = (ImageView) findViewById(R.id.photo_editor_view);
        this.photo_editor_view_1 = (PolishView) findViewById(R.id.photo_editor_view_1);
        this.splasg_bg_tool = (RelativeLayout) findViewById(R.id.splasg_bg_tool);
        this.sketch_tool = (RelativeLayout) findViewById(R.id.sketch_tool);
        this.blur_bg_tool = (RelativeLayout) findViewById(R.id.blur_bg_tool);
        this.relative_layout_loading = (RelativeLayout) findViewById(R.id.relative_layout_loading);
        this.splash_sq_tool = (RelativeLayout) findViewById(R.id.splash_sq_tool);
        this.sketch_sq_tool = (RelativeLayout) findViewById(R.id.sketch_sq_tool);
        this.save = (TextView) findViewById(R.id.save);
        this.polishView.setImageBitmap(bitmap);
        this.photo_editor_view_1.setImageSource(bitmap);
        this.splasg_bg_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SketchActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new openSplashSquareBackgroundFragment(true).execute(new Void[0]);
            }
        });
        this.sketch_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SketchActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new openSketchBackgroundFragment(true).execute(new Void[0]);
            }
        });
        this.blur_bg_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SketchActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new openBlurSquareBackgroundFragment(true).execute(new Void[0]);
            }
        });
        this.splash_sq_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SketchActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new openSplashFragment(true).execute(new Void[0]);
            }
        });
        this.sketch_sq_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SketchActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new openSketchFragment(true).execute(new Void[0]);
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SketchActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SketchActivity.this.saveImageToExternalStorage();
            }
        });
    }

    @Override // com.example.photoareditor.Fragment.SaturationSquareBackgroundFragment.SplashSaturationBackgrundListener
    public void onSaveSplashBackground(Bitmap bitmap2) {
        this.polishView.setImageBitmap(bitmap2);
        this.photo_editor_view_1.setImageSource(bitmap2);
    }

    @Override // com.example.photoareditor.Fragment.SketchSquareBackgroundFragment.SketchBackgroundListener
    public void onSaveSketchBackground(Bitmap bitmap2) {
        this.polishView.setImageBitmap(bitmap2);
        this.photo_editor_view_1.setImageSource(bitmap2);
    }

    @Override // com.example.photoareditor.Fragment.BlurSquareBgFragment.BlurSquareBgListener
    public void onSaveBlurBackground(Bitmap bitmap2) {
        this.polishView.setImageBitmap(bitmap2);
        this.photo_editor_view_1.setImageSource(bitmap2);
    }

    @Override // com.example.photoareditor.Fragment.SaturationSquareFragment.SplashSaturationListener
    public void onSaveSplash(Bitmap bitmap2) {
        this.polishView.setImageBitmap(bitmap2);
        this.photo_editor_view_1.setImageSource(bitmap2);
    }

    @Override // com.example.photoareditor.Fragment.SketchSquareFragment.SketchListener
    public void onSaveSketch(Bitmap bitmap2) {
        this.polishView.setImageBitmap(bitmap2);
        this.photo_editor_view_1.setImageSource(bitmap2);
    }

    /* loaded from: classes7.dex */
    class openSplashSquareBackgroundFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplashSquared;

        public openSplashSquareBackgroundFragment(boolean z) {
            this.isSplashSquared = z;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            SketchActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = SketchActivity.this.photo_editor_view_1.getCurrentBitmap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentBitmap);
            if (this.isSplashSquared) {
                arrayList.add(FilterUtils.getBlackAndWhiteImageFromBitmap(currentBitmap));
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplashSquared) {
                SaturationSquareBackgroundFragment.show(SketchActivity.this, list.get(0), null, list.get(1), SketchActivity.this, true);
            }
            SketchActivity.this.showLoading(false);
        }
    }

    /* loaded from: classes7.dex */
    class openSketchBackgroundFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSketchBackgroundSquared;

        public openSketchBackgroundFragment(boolean z) {
            this.isSketchBackgroundSquared = z;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            SketchActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = SketchActivity.this.photo_editor_view_1.getCurrentBitmap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentBitmap);
            if (this.isSketchBackgroundSquared) {
                arrayList.add(FilterUtils.getSketchImageFromBitmap(currentBitmap, 0.8f));
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(List<Bitmap> list) {
            if (this.isSketchBackgroundSquared) {
                SketchSquareBackgroundFragment.show(SketchActivity.this, list.get(0), null, list.get(1), SketchActivity.this, true);
            }
            SketchActivity.this.showLoading(false);
        }
    }

    /* loaded from: classes7.dex */
    class openBlurSquareBackgroundFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplashSquared;

        public openBlurSquareBackgroundFragment(boolean z) {
            this.isSplashSquared = z;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            SketchActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = SketchActivity.this.photo_editor_view_1.getCurrentBitmap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentBitmap);
            if (this.isSplashSquared) {
                arrayList.add(FilterUtils.getBlurImageFromBitmap(currentBitmap, 2.5f));
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplashSquared) {
                BlurSquareBgFragment.show(SketchActivity.this, list.get(0), null, list.get(1), SketchActivity.this, true);
            }
            SketchActivity.this.showLoading(false);
        }
    }

    /* loaded from: classes7.dex */
    class openSplashFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplashSquared;

        public openSplashFragment(boolean z) {
            this.isSplashSquared = z;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            SketchActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = SketchActivity.this.photo_editor_view_1.getCurrentBitmap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentBitmap);
            if (this.isSplashSquared) {
                arrayList.add(FilterUtils.getBlackAndWhiteImageFromBitmap(currentBitmap));
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplashSquared) {
                SaturationSquareFragment.show(SketchActivity.this, list.get(0), null, list.get(1), SketchActivity.this, true);
            }
            SketchActivity.this.showLoading(false);
        }
    }

    /* loaded from: classes7.dex */
    class openSketchFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplashSquared;

        public openSketchFragment(boolean z) {
            this.isSplashSquared = z;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            SketchActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = SketchActivity.this.photo_editor_view_1.getCurrentBitmap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentBitmap);
            if (this.isSplashSquared) {
                arrayList.add(FilterUtils.getSketchImageFromBitmap(currentBitmap, 0.8f));
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplashSquared) {
                SketchSquareFragment.show(SketchActivity.this, list.get(0), null, list.get(1), SketchActivity.this, true);
            }
            SketchActivity.this.showLoading(false);
        }
    }

    public void showLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.relative_layout_loading.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.relative_layout_loading.setVisibility(View.GONE);
    }

    public Bitmap getResizedBitmap(Bitmap bitmap2, int maxSize) {
        int height;
        int width;
        int width2 = bitmap2.getWidth();
        float bitmapRatio = width2 / bitmap2.getHeight();
        if (bitmapRatio > 1.0f) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(bitmap2, width, height, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImageToExternalStorage() {
        this.finalbitmap = this.photo_editor_view_1.getCurrentBitmap();
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/Photo Art Editor");
        myDir.mkdirs();
        Random generator = new Random();
        int n = generator.nextInt(1000);
        String fname = "Image-" + n + ".jpg";
        this.file = new File(myDir, fname);
        System.out.println("pathhhh====" + this.file);
        Utils.pass_st1 = this.file.getAbsolutePath();
        String path = this.file.getPath();
        if (this.file.exists()) {
            this.file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(this.file);
            this.finalbitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            this.finalbitmap = BitmapFactory.decodeFile(this.file.getAbsolutePath());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.SketchActivity.7
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String path2, Uri uri) {
            }
        });
        Intent intent = new Intent(this, PhotoSavedActivity.class);
        intent.putExtra(ClientCookie.PATH_ATTR, path);
        startActivity(intent);
    }
}
