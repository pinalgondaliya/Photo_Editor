package com.example.photoeditor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Adapter.DripAdapter;
import com.example.photoeditor.Adapter.DripBackgroundAdapter;
import com.example.photoeditor.Adapter.DripColorAdapter;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.BrushColorAsset;
import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.DripFrameLayout;
import com.example.photoeditor.Classs.DripUtils;
import com.example.photoeditor.Classs.ImageUtils;
import com.example.photoeditor.Classs.MLCropAsyncTask;
import com.example.photoeditor.Classs.MLOnCropTaskCompleted;
import com.example.photoeditor.Classs.MultiTouchListener;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Classs.PolishDripView;
import com.example.photoeditor.Classs.TouchListener;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Interface.BackgroundItemListener;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import io.reactivex.common.annotations.SchedulerSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes7.dex */
public class DripLayout extends AppCompatActivity implements LayoutItemListener, BackgroundItemListener, DripColorAdapter.ColorListener {
    private static Bitmap faceBitmap;
    public static Bitmap resultBmp;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap cutBitmap;
    private DripBackgroundAdapter dripBackgroundAdapter;
    private DripAdapter dripItemAdapter;
    private PolishDripView dripViewBackground;
    private PolishDripView dripViewColor;
    private PolishDripView dripViewImage;
    private PolishDripView dripViewStyle;
    private File file;
    private DripFrameLayout frameLayoutBackground;
    private LinearLayout linearLayoutStyle;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewBackground;
    private RecyclerView recyclerViewColor;
    private RecyclerView recyclerViewStyle;
    private TextView save;
    private Bitmap selectedBitmap;
    private TextView textViewColor;
    private TextView textViewGray;
    private Bitmap OverLayBackground = null;
    private Bitmap bitmapColor = null;
    public int count = 0;
    private ArrayList<String> dripBackgroundList = new ArrayList<>();
    private List<String> dripColorList = BrushColorAsset.listColorBrush();
    private ArrayList<String> dripEffectList = new ArrayList<>();
    private boolean isFirst = true;
    private boolean isReady = false;
    private Bitmap mainBitmap = null;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.layout_drip);
        this.progressBar = (ProgressBar) findViewById(R.id.crop_progress_bar);
        this.save = (TextView) findViewById(R.id.save);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        this.dripViewColor = (PolishDripView) findViewById(R.id.dripViewColor);
        this.dripViewStyle = (PolishDripView) findViewById(R.id.dripViewStyle);
        this.dripViewImage = (PolishDripView) findViewById(R.id.dripViewImage);
        this.dripViewBackground = (PolishDripView) findViewById(R.id.dripViewBackground);
        this.frameLayoutBackground = (DripFrameLayout) findViewById(R.id.frameLayoutBackground);
        this.textViewColor = (TextView) findViewById(R.id.textViewStyle);
        this.textViewGray = (TextView) findViewById(R.id.textViewBg);
        this.dripViewStyle.setOnTouchListenerCustom(new TouchListener());
        this.dripViewImage.setOnTouchListenerCustom(new TouchListener());
        new Handler().postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.DripLayout.1
            @Override // java.lang.Runnable
            public void run() {
                DripLayout.this.dripViewImage.post(new Runnable() { // from class: com.example.photoareditor.Activity.DripLayout.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DripLayout.this.isFirst) {
                            DripLayout.this.isFirst = false;
                            DripLayout.this.initBitmap();
                        }
                    }
                });
            }
        }, 1000L);
        findViewById(R.id.imageViewCloseDrip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.DripLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DripLayout.this.onBackPressed();
            }
        });
        findViewById(R.id.imageViewSaveDrip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.DripLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new saveFile().execute(new String[0]);
            }
        });
        for (int i = 1; i <= 20; i++) {
            ArrayList<String> arrayList = this.dripEffectList;
            arrayList.add("drip_" + i);
        }
        for (int i2 = 1; i2 <= 60; i2++) {
            ArrayList<String> arrayList2 = this.dripBackgroundList;
            arrayList2.add("background_" + i2);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewColor);
        this.recyclerViewColor = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewColor.setAdapter(new DripColorAdapter(this, this));
        this.recyclerViewColor.setVisibility(View.VISIBLE);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewStyle);
        this.recyclerViewStyle = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recyclerViewBackground);
        this.recyclerViewBackground = recyclerView3;
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        setDripList();
        setBgList();
        this.dripViewImage.post(new Runnable() { // from class: com.example.photoareditor.Activity.DripLayout.4
            @Override // java.lang.Runnable
            public void run() {
                DripLayout.this.initBitmap();
            }
        });
        findViewById(R.id.textViewStyle).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.DripLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DripLayout.this.recyclerViewStyle.setVisibility(View.VISIBLE);
                DripLayout.this.recyclerViewBackground.setVisibility(View.GONE);
                DripLayout.this.textViewColor.setTextColor(ContextCompat.getColor(DripLayout.this, R.color.white));
                DripLayout.this.textViewColor.setBackgroundResource(R.drawable.background_selected);
                DripLayout.this.textViewGray.setTextColor(ContextCompat.getColor(DripLayout.this, R.color.text_color));
                DripLayout.this.textViewGray.setBackgroundResource(R.drawable.background_unslelected);
            }
        });
        findViewById(R.id.textViewEraser).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.DripLayout.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StickerEraseActivity.b = DripLayout.this.selectedBitmap;
                Intent intent = new Intent(DripLayout.this, StickerEraseActivity.class);
                intent.putExtra(Constants.KEY_OPEN_FROM, Constants.VALUE_OPEN_FROM_DRIP);
                DripLayout.this.startActivityForResult(intent, 1024);
            }
        });
        findViewById(R.id.textViewBg).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.DripLayout.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DripLayout.this.recyclerViewStyle.setVisibility(View.GONE);
                DripLayout.this.recyclerViewBackground.setVisibility(View.VISIBLE);
                DripLayout.this.textViewColor.setTextColor(ContextCompat.getColor(DripLayout.this, R.color.text_color));
                DripLayout.this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                DripLayout.this.textViewGray.setTextColor(ContextCompat.getColor(DripLayout.this, R.color.white));
                DripLayout.this.textViewGray.setBackgroundResource(R.drawable.background_selected);
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.DripLayout.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Utils.process) {
                    DripLayout.this.saveImage();
                } else {
                    Toast.makeText(DripLayout.this, "Please Wait...", Toast.LENGTH_SHORT).show();
                }
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
            MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.DripLayout.9
                @Override
                public void onScanCompleted(String path2, Uri uri) {
                    DripLayout.this.runOnUiThread(new Runnable() {
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

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1024 && (bitmap = resultBmp) != null) {
            this.cutBitmap = bitmap;
            this.dripViewImage.setImageBitmap(bitmap);
            this.isReady = true;
            Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(this, "drip/style/" + this.dripItemAdapter.getItemList().get(this.dripItemAdapter.selectedPos) + ".webp");
            if (!SchedulerSupport.NONE.equals(this.dripItemAdapter.getItemList().get(0))) {
                this.OverLayBackground = bitmapFromAsset;
            }
        }
    }

    public static void setFaceBitmap(Bitmap bitmap) {
        faceBitmap = bitmap;
        Log.e("aaaaaa", "setFaceBitmap: " + bitmap);
        Log.e("aaaaaa", "setFaceBitmap: " + faceBitmap);
    }

    public void initBitmap() {
        Bitmap bitmap = faceBitmap;
        if (bitmap != null) {
            this.selectedBitmap = ImageUtils.getBitmapResize(this, bitmap, 1024, 1024);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(DripUtils.getBitmapFromAsset(this, "drip/style/white.webp"), this.selectedBitmap.getWidth(), this.selectedBitmap.getHeight(), true);
            this.mainBitmap = createScaledBitmap;
            this.bitmapColor = createScaledBitmap;
            Glide.with((FragmentActivity) this).load(Integer.valueOf((int) R.drawable.drip_1)).into(this.dripViewStyle);
            setStartDrip();
        }
    }

    public void setStartDrip() {
        Utils.process = false;
        this.progressBar.setVisibility(View.VISIBLE);
        new CountDownTimer(5000L, 1000L) {
            @Override
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                DripLayout.this.count++;
                if (DripLayout.this.progressBar.getProgress() <= 90) {
                    DripLayout.this.progressBar.setProgress(DripLayout.this.count * 5);
                }
            }
        }.start();
        new MLCropAsyncTask(new MLOnCropTaskCompleted() {
            @Override
            public void onTaskCompleted(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
                Utils.process = true;
                DripLayout.this.selectedBitmap.getWidth();
                DripLayout.this.selectedBitmap.getHeight();
                int width = DripLayout.this.selectedBitmap.getWidth();
                int height = DripLayout.this.selectedBitmap.getHeight();
                int i3 = width * height;
                DripLayout.this.selectedBitmap.getPixels(new int[i3], 0, width, 0, 0, width, height);
                int[] iArr = new int[i3];
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                DripLayout dripLayout = DripLayout.this;
                dripLayout.cutBitmap = ImageUtils.getMask(dripLayout, dripLayout.selectedBitmap, createBitmap, width, height);
                DripLayout dripLayout2 = DripLayout.this;
                dripLayout2.cutBitmap = Bitmap.createScaledBitmap(bitmap, dripLayout2.cutBitmap.getWidth(), DripLayout.this.cutBitmap.getHeight(), false);
                DripLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.DripLayout.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Palette.from(DripLayout.this.cutBitmap).generate().getDominantSwatch() == null) {
                            Toast.makeText(DripLayout.this, DripLayout.this.getString(R.string.txt_not_detect_human), Toast.LENGTH_SHORT).show();
                        }
                        DripLayout.this.dripViewImage.setImageBitmap(DripLayout.this.cutBitmap);
                        DripLayout.this.isReady = true;
                        DripLayout dripLayout3 = DripLayout.this;
                        Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(dripLayout3, "drip/style/" + DripLayout.this.dripItemAdapter.getItemList().get(0) + ".webp");
                        if (!SchedulerSupport.NONE.equals(DripLayout.this.dripItemAdapter.getItemList().get(0))) {
                            DripLayout.this.OverLayBackground = bitmapFromAsset;
                        }
                    }
                });
            }
        }, this, this.progressBar).execute(new Void[0]);
    }

    @Override
    public void onLayoutListClick(View view, int i) {
        Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(this, "drip/style/" + this.dripItemAdapter.getItemList().get(i) + ".webp");
        if (!SchedulerSupport.NONE.equals(this.dripItemAdapter.getItemList().get(i))) {
            this.OverLayBackground = bitmapFromAsset;
            this.dripViewStyle.setImageBitmap(bitmapFromAsset);
            return;
        }
        this.OverLayBackground = null;
    }

    @Override
    public void onBackgroundListClick(View view, int i) {
        if (i != 0) {
            this.dripViewBackground.setImageBitmap(ImageUtils.getBitmapFromAsset(this, "drip/background/" + this.dripBackgroundAdapter.getItemList().get(i) + ".webp"));
        } else {
            this.dripViewBackground.setImageResource(0);
        }
        this.dripViewBackground.setOnTouchListener(new MultiTouchListener());
    }

    public void setDripList() {
        DripAdapter dripAdapter = new DripAdapter(this);
        this.dripItemAdapter = dripAdapter;
        dripAdapter.setClickListener(this);
        this.recyclerViewStyle.setAdapter(this.dripItemAdapter);
        this.dripItemAdapter.addData(this.dripEffectList);
    }

    public void setBgList() {
        DripBackgroundAdapter dripBackgroundAdapter2 = new DripBackgroundAdapter(this);
        this.dripBackgroundAdapter = dripBackgroundAdapter2;
        dripBackgroundAdapter2.setClickListener(this);
        this.recyclerViewBackground.setAdapter(this.dripBackgroundAdapter);
        this.dripBackgroundAdapter.addData(this.dripBackgroundList);
    }

    @Override
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

    private class saveFile extends AsyncTask<String, Bitmap, Bitmap> {
        private saveFile() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        public Bitmap getBitmapFromView(View view) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            return createBitmap;
        }

        @Override
        public Bitmap doInBackground(String... strArr) {
            Bitmap bitmap;
            DripLayout.this.frameLayoutBackground.setDrawingCacheEnabled(true);
            try {
                bitmap = getBitmapFromView(DripLayout.this.frameLayoutBackground);
            } catch (Exception e) {
                bitmap = null;
            } catch (Throwable th) {
                DripLayout.this.frameLayoutBackground.setDrawingCacheEnabled(false);
                throw th;
            }
            DripLayout.this.frameLayoutBackground.setDrawingCacheEnabled(false);
            return bitmap;
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                BitmapTransfer.bitmap = bitmap;
            }
            Intent intent = new Intent(DripLayout.this, EditorActivity.class);
            intent.putExtra("MESSAGE", "done");
            DripLayout.this.setResult(-1, intent);
            DripLayout.this.finish();
        }
    }

    public void showLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.progressBar.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (Utils.process) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Please Wait...", Toast.LENGTH_SHORT).show();
        }
    }
}