package com.example.photoeditor.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.BlurBrushView;
import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Classs.PolishBlurView;
import com.example.photoeditor.Classs.SupportedClass;
import com.example.photoeditor.R;

import cz.msebera.android.httpclient.HttpStatus;
import java.io.File;
import java.io.IOException;

public class BlurLayout extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_GALLERY = 3;
    public static Bitmap bitmapBlur;
    public static Bitmap bitmapClear;
    static PolishBlurView blurView;
    public static BlurBrushView brushView;
    static int displayHight;
    public static int displayWidth;
    public static SeekBar seekBarBlur;
    public static SeekBar seekBarSize;
    private boolean erase;
    RelativeLayout imageViewContainer;
    public String mSelectedImagePath;
    public Uri mSelectedImageUri;
    public String mSelectedOutputPath;
    private ProgressDialog progressBlurring;
    private int startBlurSeekbarPosition;
    private TextView textViewBlur;
    private TextView textViewEraser;

    public static Bitmap blur(Context context, Bitmap bitmap, int i) {
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap createBitmap = Bitmap.createBitmap(copy);
        RenderScript create = RenderScript.create(context);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, copy);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius(i);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        return createBitmap;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_layout);
        getWindow().setFlags(1024, 1024);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        displayWidth = point.x;
        displayHight = point.y;
        this.imageViewContainer = (RelativeLayout) findViewById(R.id.relativeLayoutContainer);
        blurView = (PolishBlurView) findViewById(R.id.drawingImageView);
        if (BitmapTransfer.bitmap != null) {
            bitmapClear = BitmapTransfer.bitmap;
        }
        bitmapBlur = blur(this, bitmapClear, blurView.opacity);
        this.textViewEraser = (TextView) findViewById(R.id.textViewEraser);
        this.textViewBlur = (TextView) findViewById(R.id.textViewBlur);
        seekBarSize = (SeekBar) findViewById(R.id.seekBarSize);
        seekBarBlur = (SeekBar) findViewById(R.id.seekBarBlur);
        BlurBrushView blurBrushView = (BlurBrushView) findViewById(R.id.brushView);
        brushView = blurBrushView;
        blurBrushView.setShapeRadiusRatio(seekBarSize.getProgress() / seekBarSize.getMax());
        seekBarSize.setMax(100);
        seekBarSize.setProgress((int) blurView.radius);
        seekBarBlur.setMax(24);
        seekBarBlur.setProgress(blurView.opacity);
        new Canvas(Bitmap.createBitmap(HttpStatus.SC_MULTIPLE_CHOICES, HttpStatus.SC_MULTIPLE_CHOICES, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true));
        new Paint(1).setColor(-16711936);
        seekBarSize.setOnSeekBarChangeListener(this);
        seekBarBlur.setOnSeekBarChangeListener(this);
        blurView.initDrawing();
        this.progressBlurring = new ProgressDialog(this);
        findViewById(R.id.imageViewSaveBlur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BlurLayout.blurView.drawingBitmap != null) {
                    BitmapTransfer.bitmap = BlurLayout.blurView.drawingBitmap;
                }
                Intent intent = new Intent(BlurLayout.this, EditorActivity.class);
                intent.putExtra("MESSAGE", "done");
                BlurLayout.this.setResult(-1, intent);
                BlurLayout.this.finish();
            }
        });
        findViewById(R.id.imageViewCloseBlur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlurLayout.this.onBackPressed();
                BlurLayout.this.finish();
            }
        });
        findViewById(R.id.textViewEraser).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurLayout.this.textViewEraser.setTextColor(ContextCompat.getColor(BlurLayout.this, R.color.white));
                BlurLayout.this.textViewEraser.setBackgroundResource(R.drawable.background_selected);
                BlurLayout.this.textViewBlur.setTextColor(ContextCompat.getColor(BlurLayout.this, R.color.text_color));
                BlurLayout.this.textViewBlur.setBackgroundResource(R.drawable.background_unslelected);
                BlurLayout.this.erase = true;
                BlurLayout.blurView.mode = 0;
                PolishBlurView polishBlurView = BlurLayout.blurView;
                polishBlurView.splashBitmap = BlurLayout.bitmapClear;
                polishBlurView.updateRefMetrix();
                BlurLayout.blurView.changeShaderBitmap();
                BlurLayout.blurView.coloring = true;
            }
        });
        findViewById(R.id.textViewBlur).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurLayout.this.textViewEraser.setTextColor(ContextCompat.getColor(BlurLayout.this, R.color.text_color));
                BlurLayout.this.textViewEraser.setBackgroundResource(R.drawable.background_unslelected);
                BlurLayout.this.textViewBlur.setTextColor(ContextCompat.getColor(BlurLayout.this, R.color.white));
                BlurLayout.this.textViewBlur.setBackgroundResource(R.drawable.background_selected);
                BlurLayout.this.erase = false;
                BlurLayout.blurView.mode = 0;
                PolishBlurView polishBlurView = BlurLayout.blurView;
                polishBlurView.splashBitmap = BlurLayout.bitmapBlur;
                polishBlurView.updateRefMetrix();
                BlurLayout.blurView.changeShaderBitmap();
                BlurLayout.blurView.coloring = false;
            }
        });
        findViewById(R.id.imageViewReset).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurLayout.blurView.initDrawing();
                BlurLayout.blurView.saveScale = 1.0f;
                BlurLayout.blurView.fitScreen();
                BlurLayout.blurView.updatePreviewPaint();
                BlurLayout.blurView.updatePaintBrush();
            }
        });
        findViewById(R.id.imageViewFit).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PolishBlurView polishBlurView = BlurLayout.blurView;
                polishBlurView.saveScale = 1.0f;
                polishBlurView.radius = (BlurLayout.seekBarSize.getProgress() + 10) / BlurLayout.blurView.saveScale;
                BlurLayout.brushView.setShapeRadiusRatio((BlurLayout.seekBarSize.getProgress() + 10) / BlurLayout.blurView.saveScale);
                BlurLayout.blurView.fitScreen();
                BlurLayout.blurView.updatePreviewPaint();
            }
        });
        findViewById(R.id.imageViewZoom).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BlurLayout.blurView.mode = 1;
            }
        });
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int id = seekBar.getId();
        if (id == R.id.seekBarBlur) {
            BlurBrushView blurBrushView = brushView;
            blurBrushView.isBrushSize = false;
            blurBrushView.setShapeRadiusRatio(blurView.radius);
            brushView.brushSize.setPaintOpacity(seekBarBlur.getProgress());
            brushView.invalidate();
            PolishBlurView polishBlurView = blurView;
            polishBlurView.opacity = i + 1;
            polishBlurView.updatePaintBrush();
        } else if (id == R.id.seekBarSize) {
            BlurBrushView blurBrushView2 = brushView;
            blurBrushView2.isBrushSize = true;
            blurBrushView2.brushSize.setPaintOpacity(255);
            brushView.setShapeRadiusRatio((seekBarSize.getProgress() + 10) / blurView.saveScale);
            brushView.invalidate();
            blurView.radius = (seekBarSize.getProgress() + 10) / blurView.saveScale;
            blurView.updatePaintBrush();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 2) {
            String str = this.mSelectedOutputPath;
            this.mSelectedImagePath = str;
            if (SupportedClass.stringIsNotEmpty(str)) {
                File file = new File(this.mSelectedImagePath);
                if (file.exists()) {
                    if (Build.VERSION.SDK_INT < 24) {
                        this.mSelectedImageUri = Uri.fromFile(file);
                    } else {
                        this.mSelectedImageUri = FileProvider.getUriForFile(this, "com.example.metaphotoeditor.provider", file);
                    }
                    onPhotoTakenApp();
                }
            }
        } else if (intent == null || intent.getData() == null) {
            Log.e("TAG", "");
        } else {
            if (i == 3) {
                Uri data = intent.getData();
                this.mSelectedImageUri = data;
                if (data != null) {
                    this.mSelectedImagePath = Constants.convertMediaUriToPath(this, data);
                } else {
                    Toast.makeText(this, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                }
            } else {
                this.mSelectedImagePath = this.mSelectedOutputPath;
            }
            if (SupportedClass.stringIsNotEmpty(this.mSelectedImagePath)) {
                onPhotoTakenApp();
            }
        }
    }

    public void onPhotoTakenApp() {
        this.imageViewContainer.post(new Runnable() { // from class: com.example.photoareditor.Activity.BlurLayout.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    BlurLayout blurLayout = BlurLayout.this;
                    BlurLayout.bitmapClear = Constants.getBitmapFromUri(blurLayout, blurLayout.mSelectedImageUri, BlurLayout.this.imageViewContainer.getMeasuredWidth(), BlurLayout.this.imageViewContainer.getMeasuredHeight());
                    BlurLayout.bitmapBlur = BlurLayout.blur(BlurLayout.this.getApplicationContext(), BlurLayout.bitmapClear, BlurLayout.blurView.opacity);
                    BlurLayout.blurView.initDrawing();
                    BlurLayout.blurView.saveScale = 1.0f;
                    BlurLayout.blurView.fitScreen();
                    BlurLayout.blurView.updatePreviewPaint();
                    BlurLayout.blurView.updatePaintBrush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seekBarBlur) {
            this.startBlurSeekbarPosition = seekBarBlur.getProgress();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seekBarBlur) {
            AlertDialog create = new AlertDialog.Builder(this).create();
            create.setTitle("Warning");
            create.setMessage("Changing Bluriness will lose your current drawing progress!");
            create.setButton(-1, "Continue", new DialogInterface.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.9
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    new BlurUpdater().execute(new String[0]);
                }
            });
            create.setButton(-2, "Cancel", new DialogInterface.OnClickListener() { // from class: com.example.photoareditor.Activity.BlurLayout.10
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    BlurLayout.seekBarBlur.setProgress(BlurLayout.this.startBlurSeekbarPosition);
                }
            });
            create.show();
        }
    }

    /* loaded from: classes7.dex */
    private class BlurUpdater extends AsyncTask<String, Integer, Bitmap> {
        private BlurUpdater() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            super.onPreExecute();
            BlurLayout.this.progressBlurring.setMessage("Blurring...");
            BlurLayout.this.progressBlurring.setIndeterminate(true);
            BlurLayout.this.progressBlurring.setCancelable(false);
            BlurLayout.this.progressBlurring.show();
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            BlurLayout.bitmapBlur = BlurLayout.blur(BlurLayout.this.getApplicationContext(), BlurLayout.bitmapClear, BlurLayout.blurView.opacity);
            return BlurLayout.bitmapBlur;
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (!BlurLayout.this.erase) {
                BlurLayout.blurView.splashBitmap = BlurLayout.bitmapBlur;
                BlurLayout.blurView.updateRefMetrix();
                BlurLayout.blurView.changeShaderBitmap();
            }
            BlurLayout.blurView.initDrawing();
            BlurLayout.blurView.saveScale = 1.0f;
            BlurLayout.blurView.fitScreen();
            BlurLayout.blurView.updatePreviewPaint();
            BlurLayout.blurView.updatePaintBrush();
            if (BlurLayout.this.progressBlurring.isShowing()) {
                BlurLayout.this.progressBlurring.dismiss();
            }
        }
    }
}
