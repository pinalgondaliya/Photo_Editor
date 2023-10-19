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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photoeditor.Adapter.ArtAdapter;
import com.example.photoeditor.Adapter.ArtColorAdapter;
import com.example.photoeditor.Adapter.DripColorAdapter;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.DripFrameLayout;
import com.example.photoeditor.Classs.DripUtils;
import com.example.photoeditor.Classs.ImageUtils;
import com.example.photoeditor.Classs.MLCropAsyncTask;
import com.example.photoeditor.Classs.MLOnCropTaskCompleted;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.Classs.PolishDripView;
import com.example.photoeditor.Classs.TouchListener;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.R;
import com.github.flipzeus.FlipDirection;
import com.github.flipzeus.ImageFlipper;

import cz.msebera.android.httpclient.cookie.ClientCookie;
import io.reactivex.annotations.SchedulerSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes7.dex */
public class ArtLayout extends AppCompatActivity implements LayoutItemListener, DripColorAdapter.ColorListener, ArtColorAdapter.ArtColorListener {
    static Bitmap faceBitmap;
    public static Bitmap resultBitmap;
    ArtAdapter artAdapter;
    private Bitmap bitmap2;
    Bitmap cutBitmap;
    PolishDripView dripViewBack;
    PolishDripView dripViewBackground;
    PolishDripView dripViewFront;
    PolishDripView dripViewImage;
    private File file;
    DripFrameLayout frameLayoutBackground;
    LinearLayout linearLayoutBg;
    LinearLayout linearLayoutStyle;
    RecyclerView recyclerViewBack;
    RecyclerView recyclerViewFront;
    RecyclerView recyclerViewStyle;
    TextView save;
    SeekBar seekBarZoom;
    Bitmap selectedBitmap;
    TextView textViewColor;
    TextView textViewGray;
    Bitmap OverLayBack = null;
    Bitmap OverLayFront = null;
    ArrayList<String> artEffectList = new ArrayList<>();
    Bitmap bitmapColorBack = null;
    public int count = 0;
    boolean isFirst = true;
    boolean isReady = false;
    Bitmap mainBitmap = null;

    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.layout_art);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        this.dripViewFront = (PolishDripView) findViewById(R.id.dripViewFront);
        this.dripViewBack = (PolishDripView) findViewById(R.id.dripViewBack);
        this.dripViewImage = (PolishDripView) findViewById(R.id.dripViewImage);
        this.dripViewBackground = (PolishDripView) findViewById(R.id.dripViewBackground);
        this.frameLayoutBackground = (DripFrameLayout) findViewById(R.id.frameLayoutBackground);
        this.seekBarZoom = (SeekBar) findViewById(R.id.seekbarZoom);
        this.textViewColor = (TextView) findViewById(R.id.textViewStyle);
        this.textViewGray = (TextView) findViewById(R.id.textViewBg);
        this.save = (TextView) findViewById(R.id.save);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutStyle);
        this.linearLayoutStyle = linearLayout;
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.linearLayoutBg);
        this.linearLayoutBg = linearLayout2;
        linearLayout2.setVisibility(View.GONE);
        this.dripViewImage.setOnTouchListenerCustom(new TouchListener());
        this.seekBarZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.ArtLayout.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Float valueOf = Float.valueOf((i / 50.0f) + 1.0f);
                ArtLayout.this.dripViewBack.setScaleX(valueOf.floatValue());
                ArtLayout.this.dripViewFront.setScaleX(valueOf.floatValue());
                ArtLayout.this.dripViewBack.setScaleY(valueOf.floatValue());
                ArtLayout.this.dripViewFront.setScaleY(valueOf.floatValue());
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.ArtLayout.2
            @Override // java.lang.Runnable
            public void run() {
                ArtLayout.this.dripViewImage.post(new Runnable() { // from class: com.example.photoareditor.Activity.ArtLayout.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ArtLayout.this.isFirst) {
                            ArtLayout.this.isFirst = false;
                            ArtLayout.this.initBitmap();
                        }
                    }
                });
            }
        }, 1000L);
        findViewById(R.id.imageViewCloseDrip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ArtLayout.this.onBackPressed();
            }
        });
        findViewById(R.id.imageViewSaveDrip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new saveFile().execute(new String[0]);
            }
        });
        findViewById(R.id.image_view_eraser).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StickerEraseActivity.b = ArtLayout.this.selectedBitmap;
                Intent intent = new Intent(ArtLayout.this, StickerEraseActivity.class);
                intent.putExtra(Constants.KEY_OPEN_FROM, Constants.VALUE_OPEN_FROM_ART);
                ArtLayout.this.startActivityForResult(intent, 1024);
            }
        });
        findViewById(R.id.textViewStyle).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ArtLayout.this.linearLayoutStyle.setVisibility(View.VISIBLE);
                ArtLayout.this.linearLayoutBg.setVisibility(View.GONE);
                ArtLayout.this.textViewColor.setTextColor(ContextCompat.getColor(ArtLayout.this, R.color.white));
                ArtLayout.this.textViewColor.setBackgroundResource(R.drawable.background_selected);
                ArtLayout.this.textViewGray.setTextColor(ContextCompat.getColor(ArtLayout.this, R.color.black));
                ArtLayout.this.textViewGray.setBackgroundResource(R.drawable.background_unslelected);
            }
        });
        findViewById(R.id.textViewBg).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ArtLayout.this.linearLayoutStyle.setVisibility(View.GONE);
                ArtLayout.this.linearLayoutBg.setVisibility(View.VISIBLE);
                ArtLayout.this.textViewColor.setTextColor(ContextCompat.getColor(ArtLayout.this, R.color.black));
                ArtLayout.this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                ArtLayout.this.textViewGray.setTextColor(ContextCompat.getColor(ArtLayout.this, R.color.white));
                ArtLayout.this.textViewGray.setBackgroundResource(R.drawable.background_selected);
            }
        });
        findViewById(R.id.textViewFlip).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageFlipper.flip(ArtLayout.this.dripViewFront, FlipDirection.HORIZONTAL);
                ImageFlipper.flip(ArtLayout.this.dripViewBack, FlipDirection.HORIZONTAL);
            }
        });
        for (int i = 1; i <= 16; i++) {
            ArrayList<String> arrayList = this.artEffectList;
            arrayList.add("art_" + i);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFront);
        this.recyclerViewFront = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewFront.setAdapter(new DripColorAdapter(this, this));
        this.recyclerViewFront.setVisibility(View.VISIBLE);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewBack);
        this.recyclerViewBack = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewBack.setAdapter(new ArtColorAdapter(this, this));
        this.recyclerViewBack.setVisibility(View.VISIBLE);
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recyclerViewStyle);
        this.recyclerViewStyle = recyclerView3;
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        setDripList();
        this.dripViewImage.post(new Runnable() { // from class: com.example.photoareditor.Activity.ArtLayout.9
            @Override // java.lang.Runnable
            public void run() {
                ArtLayout.this.initBitmap();
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ArtLayout.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Utils.process) {
                    ArtLayout.this.saveImage();
                } else {
                    Toast.makeText(ArtLayout.this, "Please Wait...", Toast.LENGTH_SHORT).show();
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
        int n = generator.nextInt(10000);
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
            MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.ArtLayout.11
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public void onScanCompleted(String path2, Uri uri) {
                    ArtLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.ArtLayout.11.1
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
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1024 && (bitmap = resultBitmap) != null) {
            this.cutBitmap = bitmap;
            this.dripViewImage.setImageBitmap(bitmap);
            this.isReady = true;
            Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(this, "art/" + this.artAdapter.getItemList().get(this.artAdapter.selectedPos) + "_front.webp");
            Bitmap bitmapFromAsset2 = DripUtils.getBitmapFromAsset(this, "art/" + this.artAdapter.getItemList().get(this.artAdapter.selectedPos) + "_back.webp");
            if (!SchedulerSupport.NONE.equals(this.artAdapter.getItemList().get(0))) {
                this.OverLayFront = bitmapFromAsset;
                this.OverLayBack = bitmapFromAsset2;
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
            this.mainBitmap = Bitmap.createScaledBitmap(DripUtils.getBitmapFromAsset(this, "art/white.webp"), this.selectedBitmap.getWidth(), this.selectedBitmap.getHeight(), true);
            Glide.with((FragmentActivity) this).load(Integer.valueOf((int) R.drawable.art_1_front)).into(this.dripViewFront);
            Glide.with((FragmentActivity) this).load(Integer.valueOf((int) R.drawable.art_1_back)).into(this.dripViewBack);
            setStartDrip();
        }
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.example.photoareditor.Activity.ArtLayout$12] */
    public void setStartDrip() {
        Utils.process = false;
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.crop_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        new CountDownTimer(5000L, 1000L) { // from class: com.example.photoareditor.Activity.ArtLayout.12
            @Override // android.os.CountDownTimer
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                ArtLayout.this.count++;
                if (progressBar.getProgress() <= 90) {
                    progressBar.setProgress(ArtLayout.this.count * 5);
                }
            }
        }.start();
        new MLCropAsyncTask(new MLOnCropTaskCompleted() { // from class: com.example.photoareditor.Activity.ArtLayout.13
            @Override // com.example.photoareditor.Classs.MLOnCropTaskCompleted
            public void onTaskCompleted(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
                Utils.process = true;
                ArtLayout.this.selectedBitmap.getWidth();
                ArtLayout.this.selectedBitmap.getHeight();
                int width = ArtLayout.this.selectedBitmap.getWidth();
                int height = ArtLayout.this.selectedBitmap.getHeight();
                int i3 = width * height;
                ArtLayout.this.selectedBitmap.getPixels(new int[i3], 0, width, 0, 0, width, height);
                int[] iArr = new int[i3];
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                ArtLayout artLayout = ArtLayout.this;
                artLayout.cutBitmap = ImageUtils.getMask(artLayout, artLayout.selectedBitmap, createBitmap, width, height);
                ArtLayout artLayout2 = ArtLayout.this;
                artLayout2.cutBitmap = Bitmap.createScaledBitmap(bitmap, artLayout2.cutBitmap.getWidth(), ArtLayout.this.cutBitmap.getHeight(), false);
                ArtLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.ArtLayout.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Palette.from(ArtLayout.this.cutBitmap).generate().getDominantSwatch() == null) {
                            Toast.makeText(ArtLayout.this, ArtLayout.this.getString(R.string.txt_not_detect_human), Toast.LENGTH_SHORT).show();
                        }
                        ArtLayout.this.dripViewImage.setImageBitmap(ArtLayout.this.cutBitmap);
                        ArtLayout.this.isReady = true;
                        ArtLayout artLayout3 = ArtLayout.this;
                        Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(artLayout3, "art/" + ArtLayout.this.artAdapter.getItemList().get(0) + "_front.webp");
                        ArtLayout artLayout22 = ArtLayout.this;
                        Bitmap bitmapFromAsset2 = DripUtils.getBitmapFromAsset(artLayout22, "art/" + ArtLayout.this.artAdapter.getItemList().get(0) + "_back.webp");
                        if (!SchedulerSupport.NONE.equals(ArtLayout.this.artAdapter.getItemList().get(0))) {
                            ArtLayout.this.OverLayFront = bitmapFromAsset;
                            ArtLayout.this.OverLayBack = bitmapFromAsset2;
                        }
                    }
                });
            }
        }, this, progressBar).execute(new Void[0]);
    }

    @Override // com.example.photoareditor.Interface.LayoutItemListener
    public void onLayoutListClick(View view, int i) {
        Bitmap bitmapFromAsset = DripUtils.getBitmapFromAsset(this, "art/" + this.artAdapter.getItemList().get(i) + "_front.webp");
        Bitmap bitmapFromAsset2 = DripUtils.getBitmapFromAsset(this, "art/" + this.artAdapter.getItemList().get(i) + "_back.webp");
        if (!SchedulerSupport.NONE.equals(this.artAdapter.getItemList().get(i))) {
            this.OverLayFront = bitmapFromAsset;
            this.OverLayBack = bitmapFromAsset2;
            this.dripViewFront.setImageBitmap(bitmapFromAsset);
            this.dripViewBack.setImageBitmap(this.OverLayBack);
            return;
        }
        this.OverLayFront = null;
        this.OverLayBack = null;
    }

    public void setDripList() {
        ArtAdapter artAdapter2 = new ArtAdapter(this);
        this.artAdapter = artAdapter2;
        artAdapter2.setClickListener(this);
        this.recyclerViewStyle.setAdapter(this.artAdapter);
        this.artAdapter.addData(this.artEffectList);
    }

    @Override // com.example.photoareditor.Adapter.DripColorAdapter.ColorListener
    public void onColorSelected(DripColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.dripViewFront.setColorFilter(squareView.drawableId);
            this.dripViewBack.setColorFilter(squareView.drawableId);
            return;
        }
        this.dripViewFront.setColorFilter(squareView.drawableId);
        this.dripViewBack.setColorFilter(squareView.drawableId);
    }

    @Override // com.example.photoareditor.Adapter.ArtColorAdapter.ArtColorListener
    public void onArtColorSelected(ArtColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.dripViewBackground.setBackgroundColor(squareView.drawableId);
            this.dripViewBackground.setImageBitmap(this.bitmapColorBack);
            return;
        }
        this.dripViewBackground.setBackgroundColor(squareView.drawableId);
        this.dripViewBackground.setImageBitmap(this.bitmapColorBack);
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
            ArtLayout.this.frameLayoutBackground.setDrawingCacheEnabled(true);
            try {
                bitmap = getBitmapFromView(ArtLayout.this.frameLayoutBackground);
            } catch (Exception e) {
                bitmap = null;
            } catch (Throwable th) {
                ArtLayout.this.frameLayoutBackground.setDrawingCacheEnabled(false);
                throw th;
            }
            ArtLayout.this.frameLayoutBackground.setDrawingCacheEnabled(false);
            return bitmap;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                BitmapTransfer.bitmap = bitmap;
            }
            Intent intent = new Intent(ArtLayout.this, EditorActivity.class);
            intent.putExtra("MESSAGE", "done");
            ArtLayout.this.setResult(-1, intent);
            ArtLayout.this.finish();
        }
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