package com.example.photoeditor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.CropAsyncTask;
import com.example.photoeditor.Classs.CropTaskCompleted;
import com.example.photoeditor.Classs.MotionUtils;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Classs.PolishMotionView;
import com.example.photoeditor.Classs.PolishMotionViewTouchBase;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.R;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/* loaded from: classes7.dex */
public class MotionLayout extends AppCompatActivity {
    static final LinearLayout.LayoutParams LAYOUT_PARAMS;
    public static Bitmap btimapOraginal;
    public static Bitmap resultBmp;
    Bitmap bitmap2;
    File file;
    PolishMotionView imageViewCenter;
    ImageView imageViewCover;
    ImageView imageViewSave;
    ImageView iv_erase;
    public TextView save;
    public TextView textViewValueCount;
    public TextView textViewValueOpacity;
    public TextView textViewValueRotate;
    public int CountCurrentProgress = 2;
    public int OpacityCurrentProgress = 200;
    double Rotation = 0.0d;
    double motionDirection = 30.0d;
    double alpha = Math.toRadians(this.motionDirection);
    public int count = 0;
    Bitmap cropped = null;
    int currentColor = -1;
    int i = 0;
    public Bitmap imageBitmap = null;
    public boolean isReady = false;
    int left;
    float leftX = this.left;
    private Bitmap mainBitmap = null;
    public Matrix matrix = null;
    Matrix matrixCenter = null;
    private SeekBar seekbarCount = null;
    private SeekBar seekbarOpacity = null;
    private SeekBar seekbarRotate = null;
    int top;
    float topY = this.top;

    static {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MotionUtils.dpToPx(30), MotionUtils.dpToPx(30));
        LAYOUT_PARAMS = layoutParams;
        int dpToPx = MotionUtils.dpToPx(5);
        layoutParams.setMargins(dpToPx, dpToPx, dpToPx, dpToPx);
    }

    public static void setFaceBitmap(Bitmap bitmap) {
        btimapOraginal = bitmap;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.layout_motion);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.crop_progress_bar);
        this.imageViewCenter = (PolishMotionView) findViewById(R.id.imageViewTouch);
        this.imageViewCover = (ImageView) findViewById(R.id.imageViewCover);
        this.iv_erase = (ImageView) findViewById(R.id.image_view_compare_eraser);
        this.imageViewSave = (ImageView) findViewById(R.id.imageViewSaveMotion);
        this.save = (TextView) findViewById(R.id.save);
        this.imageViewCenter.setImageBitmap(btimapOraginal);
        this.imageViewCenter.setDisplayType(PolishMotionViewTouchBase.DisplayType.FIT_TO_SCREEN);
        this.imageViewCenter.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass1(progressBar));
        setRotate();
        setCount();
        setOpacity();
        this.imageViewSave.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MotionLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MotionLayout.this.mainBitmap != null) {
                    BitmapTransfer.bitmap = MotionLayout.this.mainBitmap;
                    Intent intent = new Intent(MotionLayout.this, EditorActivity.class);
                    intent.putExtra("MESSAGE", "done");
                    MotionLayout.this.setResult(-1, intent);
                    MotionLayout.this.finish();
                }
            }
        });
        findViewById(R.id.image_view_compare_eraser).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MotionLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StickerEraseActivity.b = MotionLayout.this.imageBitmap;
                Intent intent = new Intent(MotionLayout.this, StickerEraseActivity.class);
                intent.putExtra(Constants.KEY_OPEN_FROM, Constants.VALUE_OPEN_FROM_MOTION);
                MotionLayout.this.startActivityForResult(intent, 1024);
            }
        });
        findViewById(R.id.imageViewCloseMotion).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MotionLayout.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MotionLayout.this.onBackPressed();
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MotionLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Utils.process) {
                    MotionLayout.this.saveImage();
                } else {
                    Toast.makeText(MotionLayout.this, "Please Wait...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ ProgressBar val$progressBar;

        AnonymousClass1(ProgressBar progressBar) {
            this.val$progressBar = progressBar;
        }

        /* JADX WARN: Type inference failed for: r2v7, types: [com.example.photoareditor.Activity.MotionLayout$1$1] */
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= 16) {
                MotionLayout.this.imageViewCenter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                MotionLayout.this.imageViewCenter.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
            if (MotionLayout.this.i == 0) {
                MotionLayout.this.imageBitmap = Bitmap.createScaledBitmap(MotionLayout.btimapOraginal, MotionLayout.btimapOraginal.getWidth(), MotionLayout.btimapOraginal.getHeight(), true);
                MotionLayout.this.imageViewCover.setImageBitmap(MotionLayout.this.imageBitmap);
                MotionLayout motionLayout = MotionLayout.this;
                motionLayout.matrixCenter = motionLayout.imageViewCenter.getImageViewMatrix();
                MotionLayout.this.i++;
                MotionLayout.this.imageViewCover.setImageMatrix(MotionLayout.this.matrixCenter);
                if (MotionLayout.this.matrix == null) {
                    MotionLayout motionLayout2 = MotionLayout.this;
                    motionLayout2.matrix = motionLayout2.matrixCenter;
                }
                Utils.process = false;
                this.val$progressBar.setVisibility(View.VISIBLE);
                new CountDownTimer(21000L, 1000L) { // from class: com.example.photoareditor.Activity.MotionLayout.1.1
                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                    }

                    @Override // android.os.CountDownTimer
                    public void onTick(long j) {
                        MotionLayout.this.count++;
                        if (AnonymousClass1.this.val$progressBar.getProgress() <= 90) {
                            AnonymousClass1.this.val$progressBar.setProgress(MotionLayout.this.count * 5);
                        }
                    }
                }.start();
                new CropAsyncTask(new CropTaskCompleted() { // from class: com.example.photoareditor.Activity.MotionLayout.1.2
                    @Override // com.example.photoareditor.Classs.CropTaskCompleted
                    public void onTaskCompleted(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
                        AnonymousClass1.this.lambda$onGlobalLayout$0$MotionLayout$1(bitmap, bitmap2, i, i2);
                    }
                }, MotionLayout.this, this.val$progressBar).execute(new Void[0]);
            }
        }

        public void lambda$onGlobalLayout$0$MotionLayout$1(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
            Utils.process = true;
            if (bitmap != null) {
                MotionLayout.this.cropped = bitmap;
                MotionLayout.this.left = i;
                MotionLayout.this.top = i2;
                MotionLayout motionLayout = MotionLayout.this;
                motionLayout.leftX = motionLayout.left;
                MotionLayout motionLayout2 = MotionLayout.this;
                motionLayout2.topY = motionLayout2.top;
                MotionLayout.this.isReady = true;
                int i3 = MotionLayout.this.currentColor;
                Color.parseColor("#FFFFFF");
                MotionLayout.this.methodCropAsyncTaskPaint(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImage() {
        this.bitmap2 = this.mainBitmap;
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
        MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.MotionLayout.6
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String path2, Uri uri) {
                MotionLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.MotionLayout.6.1
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

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1 && i2 == 1024 && (bitmap = resultBmp) != null) {
            this.cropped = bitmap;
            this.imageViewCover.setImageBitmap(this.mainBitmap);
            this.isReady = true;
            methodCropAsyncTaskPaint(0.0f, 0.0f, 0.0f, 0.0f);
        }
    }

    public void setRotate() {
        if (this.seekbarRotate == null) {
            this.seekbarRotate = (SeekBar) findViewById(R.id.seekbarRotate);
            this.textViewValueRotate = (TextView) findViewById(R.id.textViewValueRotate);
            this.seekbarRotate.setMax(360);
            this.seekbarRotate.setProgress(0);
            this.textViewValueRotate.setText("0");
            this.seekbarRotate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.MotionLayout.7
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    TextView textView = MotionLayout.this.textViewValueRotate;
                    textView.setText("" + i);
                    MotionLayout.this.Rotation = Math.toRadians(i);
                    MotionLayout.this.methodCropAsyncTaskPaint(0.0f, 0.0f, 0.0f, 0.0f);
                }
            });
        }
    }

    public void setCount() {
        if (this.seekbarCount == null) {
            this.seekbarCount = (SeekBar) findViewById(R.id.seekbarCount);
            this.textViewValueCount = (TextView) findViewById(R.id.textViewValueCount);
            this.seekbarCount.setMax(50);
            this.seekbarCount.setProgress(2);
            this.textViewValueCount.setText(ExifInterface.GPS_MEASUREMENT_2D);
            this.seekbarCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.MotionLayout.8
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    MotionLayout.this.CountCurrentProgress = i;
                    if (MotionLayout.this.CountCurrentProgress < 0) {
                        MotionLayout.this.CountCurrentProgress = 0;
                    }
                    TextView textView = MotionLayout.this.textViewValueCount;
                    textView.setText("" + i);
                    MotionLayout.this.methodCropAsyncTaskPaint(0.0f, 0.0f, 0.0f, 0.0f);
                }
            });
        }
    }

    public void setOpacity() {
        if (this.seekbarOpacity == null) {
            this.seekbarOpacity = (SeekBar) findViewById(R.id.seekbarOpacity);
            this.textViewValueOpacity = (TextView) findViewById(R.id.textViewValueOpacity);
            this.seekbarOpacity.setMax(100);
            int i2 = (this.OpacityCurrentProgress * 100) / 255;
            TextView textView = this.textViewValueOpacity;
            textView.setText("" + i2);
            this.seekbarOpacity.setProgress(i2);
            this.seekbarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.MotionLayout.9
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    MotionLayout.this.OpacityCurrentProgress = (i * 255) / 100;
                    if (MotionLayout.this.OpacityCurrentProgress < 0) {
                        MotionLayout.this.OpacityCurrentProgress = 0;
                    }
                    TextView textView2 = MotionLayout.this.textViewValueOpacity;
                    textView2.setText("" + i);
                    MotionLayout.this.methodCropAsyncTaskPaint(0.0f, 0.0f, 0.0f, 0.0f);
                }
            });
        }
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        methodCropAsyncTaskPaint(motionEvent.getX(), motionEvent.getY(), 0.0f, 0.0f);
        return true;
    }

    public void methodCropAsyncTaskPaint(float f, float f2, float f3, float f4) {
        this.alpha = this.Rotation;
        paintBitmaps();
        this.leftX += f;
        this.topY += f2;
    }

    private Bitmap paintBitmaps() {
        if (!this.isReady) {
            return null;
        }
        Log.d("alpha=", "" + this.alpha);
        Bitmap copy = btimapOraginal.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copy);
        Bitmap changeAlpha = MotionUtils.changeAlpha(this.cropped, this.OpacityCurrentProgress);
        double cos = Math.cos(this.alpha);
        double sin = Math.sin(this.alpha);
        Log.d("alphacos=", "" + cos);
        Log.d("alphasin=", "" + sin);
        int i2 = this.CountCurrentProgress;
        if (i2 > 0) {
            int dpToPx = MotionUtils.dpToPx(180 / i2);
            int i3 = this.CountCurrentProgress;
            while (i3 > 0) {
                double d = this.left;
                double d2 = dpToPx * i3;
                Double.isNaN(d2);
                Double.isNaN(d);
                double d3 = this.top;
                Double.isNaN(d2);
                Double.isNaN(d3);
                canvas.drawBitmap(changeAlpha, (int) (d + (d2 * cos)), (int) (d3 - (d2 * sin)), (Paint) null);
                i3--;
                i2 = i2;
                cos = cos;
                sin = sin;
            }
        }
        canvas.drawBitmap(this.cropped, this.left, this.top, (Paint) null);
        this.mainBitmap = copy;
        this.imageViewCover.setImageBitmap(copy);
        return copy;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (Utils.process) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Please Wait...", Toast.LENGTH_SHORT).show();
        }
    }
}
