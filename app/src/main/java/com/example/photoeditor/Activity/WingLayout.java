package com.example.photoeditor.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.WingsAdapter;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.ImageUtils;
import com.example.photoeditor.Classs.MLCropAsyncTask;
import com.example.photoeditor.Classs.MLOnCropTaskCompleted;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.NeonClass.MultiTouchListener;
import com.example.photoeditor.R;

import io.reactivex.annotations.SchedulerSupport;
import java.util.ArrayList;

/* loaded from: classes7.dex */
public class WingLayout extends AppCompatActivity implements LayoutItemListener {
    private static Bitmap faceBitmap;
    public static Bitmap resultBmp;
    private Context context;
    private Bitmap cutBitmap;
    private ImageView imageViewBackground;
    private ImageView imageViewCover;
    private ImageView imageViewWings;
    private RecyclerView recyclerViewWings;
    private RelativeLayout relativeLayoutRootView;
    private Bitmap selectedBitmap;
    private WingsAdapter wingsAdapter;
    public int count = 0;
    boolean isFirst = true;
    private int wingsCount = 44;
    private ArrayList<String> wingsList = new ArrayList<>();

    public static void setFaceBitmap(Bitmap bitmap) {
        faceBitmap = bitmap;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.layout_wing);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        this.context = this;
        this.selectedBitmap = faceBitmap;
        new Handler().postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.WingLayout.1
            @Override // java.lang.Runnable
            public void run() {
                WingLayout.this.imageViewBackground.post(new Runnable() { // from class: com.example.photoareditor.Activity.WingLayout.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (WingLayout.this.isFirst && WingLayout.this.selectedBitmap != null) {
                            WingLayout.this.isFirst = false;
                            WingLayout.this.initBitmap();
                        }
                    }
                });
            }
        }, 1000L);
        this.wingsList.add(SchedulerSupport.NONE);
        for (int i = 1; i <= this.wingsCount; i++) {
            ArrayList<String> arrayList = this.wingsList;
            arrayList.add("wing_" + i);
        }
        initViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBitmap() {
        ImageView imageView;
        Bitmap bitmap = faceBitmap;
        if (bitmap != null) {
            this.selectedBitmap = ImageUtils.getBitmapResize(this.context, bitmap, this.imageViewBackground.getWidth(), this.imageViewBackground.getHeight());
            this.relativeLayoutRootView.setLayoutParams(new LinearLayout.LayoutParams(this.selectedBitmap.getWidth(), this.selectedBitmap.getHeight()));
            Bitmap bitmap2 = this.selectedBitmap;
            if (bitmap2 != null && (imageView = this.imageViewCover) != null) {
                imageView.setImageBitmap(bitmap2);
            }
            setStartWings();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1024 && (bitmap = resultBmp) != null) {
            this.cutBitmap = bitmap;
            this.imageViewBackground.setImageBitmap(bitmap);
        }
    }

    public void initViews() {
        this.relativeLayoutRootView = (RelativeLayout) findViewById(R.id.relativeLayoutRootView);
        this.imageViewWings = (ImageView) findViewById(R.id.imageViewWings);
        this.imageViewCover = (ImageView) findViewById(R.id.imageViewBackground);
        this.imageViewBackground = (ImageView) findViewById(R.id.imageViewCover);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWings);
        this.recyclerViewWings = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false));
        WingsAdapter wingsAdapter2 = new WingsAdapter(this.context);
        this.wingsAdapter = wingsAdapter2;
        wingsAdapter2.setMenuItemClickLister(this);
        this.recyclerViewWings.setAdapter(this.wingsAdapter);
        this.wingsAdapter.addData(this.wingsList);
        findViewById(R.id.imageViewCloseWings).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.WingLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WingLayout.this.onBackPressed();
            }
        });
        this.imageViewCover.setRotationY(0.0f);
        this.imageViewBackground.post(new Runnable() { // from class: com.example.photoareditor.Activity.WingLayout.3
            @Override // java.lang.Runnable
            public void run() {
                WingLayout.this.initBitmap();
            }
        });
        ((SeekBar) findViewById(R.id.seekbarOpacity)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.WingLayout.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (WingLayout.this.imageViewWings != null) {
                    WingLayout.this.imageViewWings.setAlpha(i * 0.01f);
                }
            }
        });
        findViewById(R.id.imageViewSaveWings).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.WingLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new saveFile().execute(new String[0]);
            }
        });
        findViewById(R.id.image_view_eraser).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.WingLayout.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StickerEraseActivity.b = WingLayout.this.cutBitmap;
                Intent intent = new Intent(WingLayout.this, StickerEraseActivity.class);
                intent.putExtra(Constants.KEY_OPEN_FROM, Constants.VALUE_OPEN_FROM_WING);
                WingLayout.this.startActivityForResult(intent, 1024);
            }
        });
    }

    @Override // com.example.photoareditor.Interface.LayoutItemListener
    public void onLayoutListClick(View view, int i) {
        if (i != 0) {
            Context context2 = this.context;
            StringBuilder sb = new StringBuilder();
            sb.append("wings/");
            sb.append(this.wingsAdapter.getItemList().get(i));
            sb.append(this.wingsAdapter.getItemList().get(i).startsWith("b") ? "_back.webp" : ".webp");
            this.imageViewWings.setImageBitmap(ImageUtils.getBitmapFromAsset(context2, sb.toString()));
        } else {
            this.imageViewWings.setImageResource(0);
        }
        this.imageViewWings.setOnTouchListener(new MultiTouchListener(this, true));
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.example.photoareditor.Activity.WingLayout$7] */
    public void setStartWings() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.crop_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        new CountDownTimer(5000L, 1000L) { // from class: com.example.photoareditor.Activity.WingLayout.7
            @Override // android.os.CountDownTimer
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                WingLayout.this.count++;
                if (progressBar.getProgress() <= 90) {
                    progressBar.setProgress(WingLayout.this.count * 5);
                }
            }
        }.start();
        new MLCropAsyncTask(new MLOnCropTaskCompleted() { // from class: com.example.photoareditor.Activity.WingLayout.8
            @Override // com.example.photoareditor.Classs.MLOnCropTaskCompleted
            public void onTaskCompleted(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
                WingLayout.this.selectedBitmap.getWidth();
                WingLayout.this.selectedBitmap.getHeight();
                int width = WingLayout.this.selectedBitmap.getWidth();
                int height = WingLayout.this.selectedBitmap.getHeight();
                int i3 = width * height;
                WingLayout.this.selectedBitmap.getPixels(new int[i3], 0, width, 0, 0, width, height);
                int[] iArr = new int[i3];
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                WingLayout wingLayout = WingLayout.this;
                wingLayout.cutBitmap = ImageUtils.getMask(wingLayout.context, WingLayout.this.selectedBitmap, createBitmap, width, height);
                WingLayout wingLayout2 = WingLayout.this;
                wingLayout2.cutBitmap = Bitmap.createScaledBitmap(bitmap, wingLayout2.cutBitmap.getWidth(), WingLayout.this.cutBitmap.getHeight(), false);
                WingLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.WingLayout.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Palette.from(WingLayout.this.cutBitmap).generate().getDominantSwatch() == null) {
                            Toast.makeText(WingLayout.this, WingLayout.this.getString(R.string.txt_not_detect_human), Toast.LENGTH_SHORT).show();
                        }
                        WingLayout.this.imageViewBackground.setImageBitmap(WingLayout.this.cutBitmap);
                    }
                });
            }
        }, this, progressBar).execute(new Void[0]);
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
            WingLayout.this.relativeLayoutRootView.setDrawingCacheEnabled(true);
            Bitmap bitmapFromView = getBitmapFromView(WingLayout.this.relativeLayoutRootView);
            WingLayout.this.relativeLayoutRootView.setDrawingCacheEnabled(false);
            return bitmapFromView;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                BitmapTransfer.bitmap = bitmap;
            }
            Intent intent = new Intent(WingLayout.this, EditorActivity.class);
            intent.putExtra("MESSAGE", "done");
            WingLayout.this.setResult(-1, intent);
            WingLayout.this.finish();
        }
    }
}
