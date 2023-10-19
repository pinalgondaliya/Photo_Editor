package com.example.photoeditor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Adapter.DripColorAdapter;
import com.example.photoeditor.Adapter.PixLabAdapters;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.DripFrameLayout;
import com.example.photoeditor.Classs.DripUtils;
import com.example.photoeditor.Classs.ImageUtils;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Classs.PolishDripView;
import com.example.photoeditor.Classs.TouchListener;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import io.reactivex.common.annotations.SchedulerSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes7.dex */
public class PixLabLayout extends AppCompatActivity implements LayoutItemListener, DripColorAdapter.ColorListener {
    private static Bitmap faceBitmap;
    public static Bitmap resultBmp;
    private Bitmap bitmap2;
    Bitmap cutBitmap;
    PixLabAdapters dripItemAdapter;
    PolishDripView dripViewBack;
    PolishDripView dripViewColor;
    PolishDripView dripViewStyle;
    private File file;
    DripFrameLayout frameLayoutBackground;
    RecyclerView recyclerViewColor;
    RecyclerView recyclerViewStyle;
    TextView save;
    Bitmap selectedBitmap;
    Bitmap OverLayBackground = null;
    Bitmap bitmapColor = null;
    ArrayList<String> dripEffectList = new ArrayList<>();
    boolean isFirst = true;
    boolean isReady = false;
    Bitmap mainBitmap = null;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.layout_pixlab);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        this.dripViewColor = (PolishDripView) findViewById(R.id.dripViewColor);
        this.dripViewStyle = (PolishDripView) findViewById(R.id.dripViewStyle);
        this.dripViewBack = (PolishDripView) findViewById(R.id.dripViewBack);
        this.save = (TextView) findViewById(R.id.save);
        this.frameLayoutBackground = (DripFrameLayout) findViewById(R.id.frameLayoutBackground);
        this.dripViewBack.setOnTouchListenerCustom(new TouchListener());
        new Handler().postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.PixLabLayout.1
            @Override // java.lang.Runnable
            public void run() {
                PixLabLayout.this.dripViewBack.post(new Runnable() { // from class: com.example.photoareditor.Activity.PixLabLayout.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PixLabLayout.this.isFirst) {
                            PixLabLayout.this.isFirst = false;
                            PixLabLayout.this.initBitmap();
                        }
                    }
                });
            }
        }, 1000L);
        findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PixLabLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PixLabLayout.this.onBackPressed();
            }
        });
        findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PixLabLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new saveFile().execute(new String[0]);
            }
        });
        for (int i = 1; i <= 90; i++) {
            ArrayList<String> arrayList = this.dripEffectList;
            arrayList.add("style_" + i);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewColor);
        this.recyclerViewColor = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewColor.setAdapter(new DripColorAdapter(this, this));
        this.recyclerViewColor.setVisibility(View.VISIBLE);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewStyle);
        this.recyclerViewStyle = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        setDripList();
        this.dripViewBack.post(new Runnable() { // from class: com.example.photoareditor.Activity.PixLabLayout.4
            @Override // java.lang.Runnable
            public void run() {
                PixLabLayout.this.initBitmap();
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PixLabLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PixLabLayout.this.saveImage();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImage() {
        this.frameLayoutBackground.setDrawingCacheEnabled(true);
        this.frameLayoutBackground.buildDrawingCache();
        this.bitmap2 = this.frameLayoutBackground.getDrawingCache();
        Log.e("gauravvvvv", "saveImage: " + this.bitmap2);
        Log.e("gauravvvvv", "saveImage:1111111111 " + this.bitmap2);
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
            try {
                FileOutputStream out = new FileOutputStream(this.file);
                this.bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                this.bitmap2 = BitmapFactory.decodeFile(this.file.getAbsolutePath());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.frameLayoutBackground.destroyDrawingCache();
            MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.PixLabLayout.6
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public void onScanCompleted(String path2, Uri uri) {
                    PixLabLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.PixLabLayout.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                        }
                    });
                    Log.i("ExternalStorage", "Scanned " + path2 + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
            Intent intent = new Intent(this, PhotoSavedActivity.class);
            intent.putExtra(ClientCookie.PATH_ATTR, path);
            startActivity(intent);
        } catch (Throwable th) {
            this.frameLayoutBackground.destroyDrawingCache();
            throw th;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1024 && (bitmap = resultBmp) != null) {
            this.cutBitmap = bitmap;
            this.dripViewBack.setImageBitmap(bitmap);
            this.isReady = true;
            Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(this, "lab/" + this.dripItemAdapter.getItemList().get(this.dripItemAdapter.selectedPos) + ".webp");
            if (!SchedulerSupport.NONE.equals(this.dripItemAdapter.getItemList().get(0))) {
                this.OverLayBackground = bitmapFromAsset;
            }
        }
    }

    public static void setFaceBitmap(Bitmap bitmap) {
        faceBitmap = bitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBitmap() {
        Bitmap bitmap = faceBitmap;
        if (bitmap != null) {
            this.selectedBitmap = ImageUtils.getBitmapResize(this, bitmap, 1024, 1024);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(DripUtils.getBitmapFromAsset(this, "lab/white.webp"), this.selectedBitmap.getWidth(), this.selectedBitmap.getHeight(), true);
            this.mainBitmap = createScaledBitmap;
            this.bitmapColor = createScaledBitmap;
            Glide.with((FragmentActivity) this).load(Integer.valueOf((int) R.drawable.style_1)).into(this.dripViewStyle);
            this.dripViewBack.setImageBitmap(this.selectedBitmap);
        }
    }

    @Override
    public void onLayoutListClick(View view, int i) {
        Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(this, "lab/" + this.dripItemAdapter.getItemList().get(i) + ".webp");
        if (!SchedulerSupport.NONE.equals(this.dripItemAdapter.getItemList().get(i))) {
            this.OverLayBackground = bitmapFromAsset;
            this.dripViewStyle.setImageBitmap(bitmapFromAsset);
            return;
        }
        this.OverLayBackground = null;
    }

    public void setDripList() {
        PixLabAdapters pixLabAdapters = new PixLabAdapters(this);
        this.dripItemAdapter = pixLabAdapters;
        pixLabAdapters.setClickListener(this);
        this.recyclerViewStyle.setAdapter(this.dripItemAdapter);
        this.dripItemAdapter.addData(this.dripEffectList);
    }

    @Override // com.example.photoareditor.Adapter.DripColorAdapter.ColorListener
    public void onColorSelected(DripColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            Bitmap changeBitmapColor = DripUtils.changeBitmapColor(this.mainBitmap, squareView.drawableId);
            this.bitmapColor = changeBitmapColor;
            this.dripViewColor.setImageBitmap(changeBitmapColor);
            this.frameLayoutBackground.setBackgroundColor(squareView.drawableId);
            this.dripViewColor.setBackgroundColor(squareView.drawableId);
            this.dripViewStyle.setColorFilter(squareView.drawableId);
            return;
        }
        Bitmap changeBitmapColor2 = DripUtils.changeBitmapColor(this.mainBitmap, squareView.drawableId);
        this.bitmapColor = changeBitmapColor2;
        this.dripViewColor.setImageBitmap(changeBitmapColor2);
        this.frameLayoutBackground.setBackgroundColor(squareView.drawableId);
        this.dripViewColor.setBackgroundColor(squareView.drawableId);
        this.dripViewStyle.setColorFilter(squareView.drawableId);
    }

    /* loaded from: classes7.dex */
    private class saveFile extends AsyncTask<String, Bitmap, Bitmap> {
        private saveFile() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            super.onPreExecute();
        }

        public Bitmap getBitmapFromView(View view) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            return createBitmap;
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            Bitmap bitmap;
            PixLabLayout.this.frameLayoutBackground.setDrawingCacheEnabled(true);
            try {
                bitmap = getBitmapFromView(PixLabLayout.this.frameLayoutBackground);
            } catch (Exception e) {
                bitmap = null;
            } catch (Throwable th) {
                PixLabLayout.this.frameLayoutBackground.setDrawingCacheEnabled(false);
                throw th;
            }
            PixLabLayout.this.frameLayoutBackground.setDrawingCacheEnabled(false);
            return bitmap;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                BitmapTransfer.bitmap = bitmap;
            }
            Intent intent = new Intent(PixLabLayout.this, PixLabLayout.class);
            intent.putExtra("MESSAGE", "done");
            PixLabLayout.this.setResult(-1, intent);
            PixLabLayout.this.finish();
        }
    }
}
