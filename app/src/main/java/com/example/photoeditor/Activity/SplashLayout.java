package com.example.photoeditor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.TetxColorAdapter;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Classs.PolishSplashView;
import com.example.photoeditor.Classs.SplashBrushView;
import com.example.photoeditor.Classs.SupportedClass;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.R;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/* loaded from: classes7.dex */
public class SplashLayout extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, TetxColorAdapter.ColorListener {
    public static SplashBrushView brushView;
    public static Bitmap colorBitmap;
    public static int displayHight;
    public static int displayWidth;
    public static String drawPath;
    public static Bitmap grayBitmap;
    public static SeekBar seekBarOpacity;
    public static SeekBar seekBarSize;
    public static PolishSplashView splashView;
    public static Vector vector;
    private Bitmap bitmap2;
    private File file;
    RecyclerView recyclerViewColor;
    RelativeLayout relativeLayoutContainer;
    RelativeLayout relativeLayoutView;
    Runnable runnableCode;
    TextView save;
    public String selectedImagePath;
    public Uri selectedImageUri;
    public String selectedOutputPath;
    List<TetxColorAdapter.SquareView> squareViewListSaved = new ArrayList();
    TetxColorAdapter tetxColorAdapter;
    TextView textViewColor;
    TextView textViewGray;
    TextView textViewManual;

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.layout_splash);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        this.relativeLayoutContainer = (RelativeLayout) findViewById(R.id.relativeLayoutContainer);
        brushView = (SplashBrushView) findViewById(R.id.brushView);
        vector = new Vector();
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        displayWidth = point.x;
        displayHight = point.y;
        splashView = (PolishSplashView) findViewById(R.id.drawingImageView);
        if (BitmapTransfer.bitmap != null) {
            colorBitmap = BitmapTransfer.bitmap;
        }
        grayBitmap = grayScaleBitmap(colorBitmap);
        this.textViewColor = (TextView) findViewById(R.id.textViewColor);
        this.textViewGray = (TextView) findViewById(R.id.textViewGray);
        this.textViewManual = (TextView) findViewById(R.id.textViewManual);
        this.save = (TextView) findViewById(R.id.save);
        this.relativeLayoutView = (RelativeLayout) findViewById(R.id.relativeLayoutView);
        seekBarSize = (SeekBar) findViewById(R.id.seekBarSize);
        seekBarOpacity = (SeekBar) findViewById(R.id.seekBarOpacity);
        seekBarSize.setMax(100);
        seekBarOpacity.setMax(240);
        seekBarSize.setProgress((int) splashView.radius);
        seekBarOpacity.setProgress(splashView.opacity);
        seekBarSize.setOnSeekBarChangeListener(this);
        seekBarOpacity.setOnSeekBarChangeListener(this);
        splashView.initDrawing();
        final Handler handler = new Handler();
        Runnable r3 = new Runnable() { // from class: com.example.photoareditor.Activity.SplashLayout.1
            @Override // java.lang.Runnable
            public void run() {
                handler.postDelayed(SplashLayout.this.runnableCode, 2000L);
            }
        };
        this.runnableCode = r3;
        handler.post(r3);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewColor);
        this.recyclerViewColor = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewColor.setAdapter(new TetxColorAdapter(this, this));
        this.recyclerViewColor.setVisibility(View.VISIBLE);
        findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SplashLayout.splashView.drawingBitmap != null) {
                    BitmapTransfer.bitmap = SplashLayout.splashView.drawingBitmap;
                }
                Intent intent = new Intent(SplashLayout.this, Edit_SplashActivity.class);
                intent.putExtra("MESSAGE", "done");
                SplashLayout.this.setResult(-1, intent);
                SplashLayout.this.finish();
            }
        });
        findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SplashLayout.this.onBackPressed();
                SplashLayout.this.finish();
            }
        });
        findViewById(R.id.textViewManual).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SplashLayout.this.textViewManual.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.white));
                SplashLayout.this.textViewManual.setBackgroundResource(R.drawable.background_selected);
                SplashLayout.this.textViewColor.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.black));
                SplashLayout.this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                SplashLayout.this.textViewGray.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.black));
                SplashLayout.this.textViewGray.setBackgroundResource(R.drawable.background_unslelected);
                SplashLayout.this.relativeLayoutView.setVisibility(View.GONE);
                SplashLayout.splashView.mode = 0;
                SplashLayout.splashView.splashBitmap = SplashLayout.this.grayScaleBitmap(SplashLayout.colorBitmap);
                SplashLayout.splashView.updateRefMetrix();
                SplashLayout.splashView.changeShaderBitmap();
                SplashLayout.splashView.coloring = -2;
                SplashLayout splashLayout = SplashLayout.this;
                splashLayout.tetxColorAdapter = (TetxColorAdapter) splashLayout.recyclerViewColor.getAdapter();
                if (SplashLayout.this.tetxColorAdapter != null) {
                    SplashLayout.this.tetxColorAdapter.setSelectedColorIndex(0);
                }
                if (SplashLayout.this.tetxColorAdapter != null) {
                    SplashLayout.this.tetxColorAdapter.notifyDataSetChanged();
                }
            }
        });
        findViewById(R.id.textViewColor).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SplashLayout.this.textViewColor.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.white));
                SplashLayout.this.textViewColor.setBackgroundResource(R.drawable.background_selected);
                SplashLayout.this.textViewManual.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.black));
                SplashLayout.this.textViewManual.setBackgroundResource(R.drawable.background_unslelected);
                SplashLayout.this.textViewGray.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.black));
                SplashLayout.this.textViewGray.setBackgroundResource(R.drawable.background_unslelected);
                SplashLayout.this.relativeLayoutView.setVisibility(View.VISIBLE);
                SplashLayout.splashView.mode = 0;
                PolishSplashView polishSplashView = SplashLayout.splashView;
                polishSplashView.splashBitmap = SplashLayout.colorBitmap;
                polishSplashView.updateRefMetrix();
                SplashLayout.splashView.changeShaderBitmap();
                SplashLayout.splashView.coloring = -1;
            }
        });
        findViewById(R.id.textViewGray).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SplashLayout.this.textViewGray.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.white));
                SplashLayout.this.textViewGray.setBackgroundResource(R.drawable.background_selected);
                SplashLayout.this.textViewColor.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.black));
                SplashLayout.this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                SplashLayout.this.textViewManual.setTextColor(ContextCompat.getColor(SplashLayout.this, R.color.black));
                SplashLayout.this.textViewManual.setBackgroundResource(R.drawable.background_unslelected);
                SplashLayout.this.relativeLayoutView.setVisibility(View.VISIBLE);
                SplashLayout.splashView.mode = 0;
                SplashLayout.splashView.splashBitmap = SplashLayout.this.grayScaleBitmap(SplashLayout.colorBitmap);
                SplashLayout.splashView.updateRefMetrix();
                SplashLayout.splashView.changeShaderBitmap();
                SplashLayout.splashView.coloring = -2;
            }
        });
        findViewById(R.id.imageViewReset).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SplashLayout.this.lambda$onCreate$0$SplashLayout(view);
            }
        });
        findViewById(R.id.imageViewFit).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PolishSplashView polishSplashView = SplashLayout.splashView;
                polishSplashView.saveScale = 1.0f;
                polishSplashView.radius = (SplashLayout.seekBarSize.getProgress() + 10) / SplashLayout.splashView.saveScale;
                SplashLayout.splashView.fitScreen();
                SplashLayout.splashView.updatePreviewPaint();
            }
        });
        findViewById(R.id.imageViewZoom).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SplashLayout.splashView.mode = 1;
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.SplashLayout.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SplashLayout.this.saveImage();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImage() {
        this.bitmap2 = splashView.drawingBitmap;
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
        MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.SplashLayout.11
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String path2, Uri uri) {
                SplashLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.SplashLayout.11.1
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
    }

    public void lambda$onCreate$0$SplashLayout(View view) {
        this.textViewColor.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewColor.setBackgroundResource(R.drawable.background_selected);
        this.textViewManual.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewManual.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewGray.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewGray.setBackgroundResource(R.drawable.background_unslelected);
        this.relativeLayoutView.setVisibility(View.VISIBLE);
        grayBitmap = grayScaleBitmap(colorBitmap);
        splashView.initDrawing();
        splashView.saveScale = 1.0f;
        splashView.fitScreen();
        splashView.updatePreviewPaint();
        splashView.updatePaintBrush();
        vector.clear();
    }

    @Override // com.example.photoareditor.Adapter.TetxColorAdapter.ColorListener
    public void onColorSelected(TetxColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            grayBitmap = grayScaleBitmap(colorBitmap);
            Canvas canvas = new Canvas(grayBitmap);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(new float[]{((squareView.drawableId >> 16) & 255) / 256.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, ((squareView.drawableId >> 8) & 255) / 256.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (squareView.drawableId & 255) / 256.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, ((squareView.drawableId >> 24) & 255) / 256.0f, 0.0f}));
            canvas.drawBitmap(grayBitmap, 0.0f, 0.0f, paint);
            splashView.splashBitmap = grayBitmap;
            splashView.updateRefMetrix();
            splashView.changeShaderBitmap();
            splashView.coloring = squareView.drawableId;
            this.squareViewListSaved.add(squareView);
            return;
        }
        grayBitmap = grayScaleBitmap(colorBitmap);
        Canvas canvas2 = new Canvas(grayBitmap);
        Paint paint2 = new Paint();
        paint2.setColorFilter(new ColorMatrixColorFilter(new float[]{((squareView.drawableId >> 16) & 255) / 256.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, ((squareView.drawableId >> 8) & 255) / 256.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (squareView.drawableId & 255) / 256.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, ((squareView.drawableId >> 24) & 255) / 256.0f, 0.0f}));
        canvas2.drawBitmap(grayBitmap, 0.0f, 0.0f, paint2);
        splashView.splashBitmap = grayBitmap;
        splashView.updateRefMetrix();
        splashView.changeShaderBitmap();
        splashView.coloring = squareView.drawableId;
        this.squareViewListSaved.add(squareView);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Bitmap grayScaleBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int id = seekBar.getId();
        if (id == R.id.seekBarOpacity) {
            SplashBrushView splashBrushView = brushView;
            splashBrushView.isBrushSize = false;
            splashBrushView.setShapeRadiusRatio(splashView.radius);
            brushView.brushSize.setPaintOpacity(seekBarOpacity.getProgress());
            brushView.invalidate();
            PolishSplashView polishSplashView = splashView;
            polishSplashView.opacity = i + 15;
            polishSplashView.updatePaintBrush();
        } else if (id == R.id.seekBarSize) {
            Log.wtf("radious :", seekBarSize.getProgress() + "");
            splashView.radius = (seekBarSize.getProgress() + 10) / splashView.saveScale;
            splashView.updatePaintBrush();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1 || i != 2) {
            if (intent == null || intent.getData() == null) {
                Log.e("TAG", "");
                return;
            }
            if (i == 3) {
                this.selectedImageUri = intent.getData();
            } else {
                this.selectedImagePath = this.selectedOutputPath;
            }
            if (SupportedClass.stringIsNotEmpty(this.selectedImagePath)) {
                onPhotoTakenApp();
                return;
            }
            return;
        }
        String str = this.selectedOutputPath;
        this.selectedImagePath = str;
        if (SupportedClass.stringIsNotEmpty(str)) {
            File file = new File(this.selectedImagePath);
            if (file.exists()) {
                if (Build.VERSION.SDK_INT < 24) {
                    this.selectedImageUri = Uri.fromFile(file);
                } else {
                    this.selectedImageUri = FileProvider.getUriForFile(this, "com.example.metaphotoeditor.provider", file);
                }
                onPhotoTakenApp();
            }
        }
    }

    public void onPhotoTakenApp() {
        this.relativeLayoutContainer.post(new Runnable() { // from class: com.example.photoareditor.Activity.SplashLayout.12
            @Override // java.lang.Runnable
            public void run() {
                SplashLayout.grayBitmap = SplashLayout.this.grayScaleBitmap(SplashLayout.colorBitmap);
                SplashLayout.splashView.initDrawing();
                SplashLayout.splashView.saveScale = 1.0f;
                SplashLayout.splashView.fitScreen();
                SplashLayout.splashView.updatePreviewPaint();
                SplashLayout.splashView.updatePaintBrush();
                SplashLayout.vector.clear();
            }
        });
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        Bitmap.createBitmap(HttpStatus.SC_MULTIPLE_CHOICES, HttpStatus.SC_MULTIPLE_CHOICES, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
        new Paint(1).setColor(-16711936);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
