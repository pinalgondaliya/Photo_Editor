package com.example.photoeditor.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.ConstantsApp;
import com.example.photoeditor.Classs.EraseView;
import com.example.photoeditor.Classs.MultiTouchListener;
import com.example.photoeditor.Classs.MyExceptionHandlerPix;
import com.example.photoeditor.DripClass.ImageUtils;
import com.example.photoeditor.R;

/* loaded from: classes7.dex */
public class StickerEraseActivity extends AppCompatActivity implements View.OnClickListener {
    public static Bitmap b = null;
    public static Bitmap bgCircleBit = null;
    public static Bitmap bitmap = null;
    public static int curBgType = 1;
    public static int orgBitHeight;
    public static int orgBitWidth;
    public static BitmapShader patternBMPshader;
    public Animation animSlideDown;
    public Animation animSlideUp;
    public ImageView back_btn;
    public EraseView dv;
    public ImageView dv1;
    public int height;
    public ImageView imageViewBackgroundCover;
    public RelativeLayout inside_cut_lay;
    private LinearLayout lay_lasso_cut;
    private LinearLayout linearLayoutAuto;
    private LinearLayout linearLayoutEraser;
    public RelativeLayout main_rel;
    private String openFrom;
    public Bitmap orgBitmap;
    public RelativeLayout outside_cut_lay;
    private SeekBar radius_seekbar;
    ImageView redo_btn;
    public RelativeLayout relativeLayoutAuto;
    RelativeLayout relativeLayoutBackground;
    public RelativeLayout relativeLayoutEraser;
    public RelativeLayout relativeLayoutExtract;
    public RelativeLayout relativeLayoutRestore;
    public RelativeLayout relativeLayoutSeekBar;
    public RelativeLayout relativeLayoutZoom;
    public ImageView save_btn;
    public Animation scale_anim;
    private SeekBar seekBarBrushOffset;
    private SeekBar seekBarExtractOffset;
    private SeekBar seekBarOffset;
    private SeekBar seekBarThreshold;
    private TextView textViewAutoOffset;
    private TextView textViewBrushOffset;
    private TextView textViewBrushSize;
    private TextView textViewExtractOffset;
    ImageView undo_btn;
    public int width;
    public boolean isTutOpen = true;
    public boolean showDialog = false;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_polish_eraser);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlerPix(this));
        this.openFrom = getIntent().getStringExtra(Constants.KEY_OPEN_FROM);
        this.animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_up);
        this.animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_down);
        this.scale_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale_anim);
        initUI();
        this.isTutOpen = false;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        this.width = displayMetrics.widthPixels;
        this.height = i - ImageUtils.dpToPx((Context) this, 120.0f);
        curBgType = 1;
        this.main_rel.postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (StickerEraseActivity.this.isTutOpen) {
                    ImageView imageView = StickerEraseActivity.this.imageViewBackgroundCover;
                    StickerEraseActivity stickerEraseActivity = StickerEraseActivity.this;
                    imageView.setImageBitmap(com.example.photoeditor.DripClass.ImageUtils.getTiledBitmap(stickerEraseActivity, R.drawable.tbg3, stickerEraseActivity.width, StickerEraseActivity.this.height));
                    StickerEraseActivity.bgCircleBit = ImageUtils.getBgCircleBit(StickerEraseActivity.this, R.drawable.tbg3);
                } else {
                    ImageView imageView2 = StickerEraseActivity.this.imageViewBackgroundCover;
                    StickerEraseActivity stickerEraseActivity2 = StickerEraseActivity.this;
                    imageView2.setImageBitmap(ImageUtils.getTiledBitmap(stickerEraseActivity2, R.drawable.tbg, stickerEraseActivity2.width, StickerEraseActivity.this.height));
                    StickerEraseActivity.bgCircleBit = ImageUtils.getBgCircleBit(StickerEraseActivity.this, R.drawable.tbg);
                }
                StickerEraseActivity.this.importImageFromUri();
            }
        }, 1000L);
    }

    private void initUI() {
        this.relativeLayoutSeekBar = (RelativeLayout) findViewById(R.id.relativeLayoutSeekBar);
        this.relativeLayoutAuto = (RelativeLayout) findViewById(R.id.relativeLayoutAuto);
        this.relativeLayoutEraser = (RelativeLayout) findViewById(R.id.relativeLayoutEraser);
        this.relativeLayoutRestore = (RelativeLayout) findViewById(R.id.relativeLayoutRestore);
        this.relativeLayoutExtract = (RelativeLayout) findViewById(R.id.relativeLayoutExtract);
        this.relativeLayoutZoom = (RelativeLayout) findViewById(R.id.relativeLayoutZoom);
        this.main_rel = (RelativeLayout) findViewById(R.id.main_rel);
        this.linearLayoutAuto = (LinearLayout) findViewById(R.id.linearLayoutAuto);
        this.linearLayoutEraser = (LinearLayout) findViewById(R.id.linearLayoutEraser);
        this.lay_lasso_cut = (LinearLayout) findViewById(R.id.lay_lasso_cut);
        this.inside_cut_lay = (RelativeLayout) findViewById(R.id.inside_cut_lay);
        this.outside_cut_lay = (RelativeLayout) findViewById(R.id.outside_cut_lay);
        this.undo_btn = (ImageView) findViewById(R.id.imageViewUndo);
        this.redo_btn = (ImageView) findViewById(R.id.imageViewRedo);
        this.back_btn = (ImageView) findViewById(R.id.btn_back);
        this.save_btn = (ImageView) findViewById(R.id.save_image_btn);
        this.relativeLayoutBackground = (RelativeLayout) findViewById(R.id.relativeLayoutBackground);
        this.imageViewBackgroundCover = (ImageView) findViewById(R.id.imageViewBackgroundCover);
        this.textViewBrushSize = (TextView) findViewById(R.id.textViewBrushSize);
        this.textViewBrushOffset = (TextView) findViewById(R.id.textViewBrushOffset);
        this.textViewAutoOffset = (TextView) findViewById(R.id.textViewOffset);
        this.textViewExtractOffset = (TextView) findViewById(R.id.textViewExtractOffset);
        this.back_btn.setOnClickListener(this);
        this.undo_btn.setOnClickListener(this);
        this.redo_btn.setOnClickListener(this);
        this.undo_btn.setEnabled(false);
        this.redo_btn.setEnabled(false);
        this.save_btn.setOnClickListener(this);
        this.relativeLayoutBackground.setOnClickListener(this);
        this.relativeLayoutEraser.setOnClickListener(this);
        this.relativeLayoutAuto.setOnClickListener(this);
        this.relativeLayoutExtract.setOnClickListener(this);
        this.relativeLayoutZoom.setOnClickListener(this);
        this.relativeLayoutRestore.setOnClickListener(this);
        this.inside_cut_lay.setOnClickListener(this);
        this.outside_cut_lay.setOnClickListener(this);
        this.seekBarBrushOffset = (SeekBar) findViewById(R.id.seekBarBrushOffset);
        this.seekBarOffset = (SeekBar) findViewById(R.id.seekBarOffset);
        this.seekBarExtractOffset = (SeekBar) findViewById(R.id.seekBarExtractOffset);
        this.seekBarBrushOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (StickerEraseActivity.this.dv != null) {
                    StickerEraseActivity.this.dv.setOffset(i - 150);
                    StickerEraseActivity.this.dv.invalidate();
                    StickerEraseActivity.this.textViewBrushOffset.setText(String.valueOf(i));
                }
            }
        });
        this.seekBarOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (StickerEraseActivity.this.dv != null) {
                    StickerEraseActivity.this.dv.setOffset(i - 150);
                    StickerEraseActivity.this.dv.invalidate();
                    StickerEraseActivity.this.textViewAutoOffset.setText(String.valueOf(i));
                }
            }
        });
        this.seekBarExtractOffset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (StickerEraseActivity.this.dv != null) {
                    StickerEraseActivity.this.dv.setOffset(i - 150);
                    StickerEraseActivity.this.dv.invalidate();
                    StickerEraseActivity.this.textViewExtractOffset.setText(String.valueOf(i));
                }
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarSize);
        this.radius_seekbar = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.5
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                if (StickerEraseActivity.this.dv != null) {
                    StickerEraseActivity.this.dv.setRadius(i + 2);
                    StickerEraseActivity.this.dv.invalidate();
                    StickerEraseActivity.this.textViewBrushSize.setText(String.valueOf(i));
                }
            }
        });
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBarThreshold);
        this.seekBarThreshold = seekBar2;
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar3, int i, boolean z) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar3) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar3) {
                if (StickerEraseActivity.this.dv != null) {
                    StickerEraseActivity.this.dv.setThreshold(seekBar3.getProgress() + 10);
                    StickerEraseActivity.this.dv.updateThreshHold();
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.dv != null || view.getId() == R.id.btn_back) {
            switch (view.getId()) {
                case R.id.btn_back /* 2131361925 */:
                    onBackPressed();
                    return;
                case R.id.imageViewRedo /* 2131362257 */:
                    final ProgressDialog show = ProgressDialog.show(this, "", getString(R.string.redoing) + "...", true);
                    show.setCancelable(false);
                    new Thread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.7
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                StickerEraseActivity.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.7.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        StickerEraseActivity.this.dv.redoChange();
                                    }
                                });
                                Thread.sleep(500L);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            show.dismiss();
                        }
                    }).start();
                    return;
                case R.id.imageViewUndo /* 2131362277 */:
                    final ProgressDialog show2 = ProgressDialog.show(this, "", getString(R.string.undoing) + "...", true);
                    show2.setCancelable(false);
                    new Thread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.8
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                StickerEraseActivity.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.8.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        StickerEraseActivity.this.dv.undoChange();
                                    }
                                });
                                Thread.sleep(500L);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            show2.dismiss();
                        }
                    }).start();
                    return;
                case R.id.inside_cut_lay /* 2131362345 */:
                    this.dv.enableInsideCut(true);
                    this.inside_cut_lay.clearAnimation();
                    this.outside_cut_lay.clearAnimation();
                    return;
                case R.id.outside_cut_lay /* 2131362513 */:
                    this.dv.enableInsideCut(false);
                    this.inside_cut_lay.clearAnimation();
                    this.outside_cut_lay.clearAnimation();
                    return;
                case R.id.relativeLayoutAuto /* 2131362613 */:
                    setSelected(R.id.relativeLayoutAuto);
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(2);
                    this.dv.invalidate();
                    return;
                case R.id.relativeLayoutBackground /* 2131362614 */:
                    changeBG();
                    return;
                case R.id.relativeLayoutEraser /* 2131362619 */:
                    setSelected(R.id.relativeLayoutEraser);
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(1);
                    this.dv.invalidate();
                    return;
                case R.id.relativeLayoutExtract /* 2131362620 */:
                    setSelected(R.id.relativeLayoutExtract);
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(3);
                    this.dv.invalidate();
                    return;
                case R.id.relativeLayoutRestore /* 2131362623 */:
                    setSelected(R.id.relativeLayoutRestore);
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(4);
                    this.dv.invalidate();
                    return;
                case R.id.relativeLayoutZoom /* 2131362632 */:
                    this.dv.enableTouchClear(false);
                    this.main_rel.setOnTouchListener(new MultiTouchListener());
                    setSelected(R.id.relativeLayoutZoom);
                    this.dv.setMODE(0);
                    this.dv.invalidate();
                    return;
                case R.id.save_image_btn /* 2131362655 */:
                    Bitmap finalBitmap = this.dv.getFinalBitmap();
                    bitmap = finalBitmap;
                    if (finalBitmap != null) {
                        try {
                            int dpToPx = ImageUtils.dpToPx((Context) this, 42.0f);
                            Bitmap resizeBitmap = ImageUtils.resizeBitmap(bitmap, orgBitWidth + dpToPx + dpToPx, orgBitHeight + dpToPx + dpToPx);
                            bitmap = resizeBitmap;
                            int i = dpToPx + dpToPx;
                            Bitmap createBitmap = Bitmap.createBitmap(resizeBitmap, dpToPx, dpToPx, resizeBitmap.getWidth() - i, bitmap.getHeight() - i);
                            bitmap = createBitmap;
                            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(createBitmap, orgBitWidth, orgBitHeight, true);
                            bitmap = createScaledBitmap;
                            bitmap = ImageUtils.bitmapmasking(this.orgBitmap, createScaledBitmap);
                            if (this.openFrom.equalsIgnoreCase(Constants.VALUE_OPEN_FROM_NEON)) {
                                NeonLayout.resultBitmap = bitmap;
                            } else if (this.openFrom.equalsIgnoreCase(Constants.VALUE_OPEN_FROM_DRIP)) {
                                DripLayout.resultBmp = bitmap;
                            } else if (this.openFrom.equalsIgnoreCase(Constants.VALUE_OPEN_FROM_MOTION)) {
                                MotionLayout.resultBmp = bitmap;
                            } else if (this.openFrom.equalsIgnoreCase(Constants.VALUE_OPEN_FROM_ART)) {
                                ArtLayout.resultBitmap = bitmap;
                            } else if (this.openFrom.equalsIgnoreCase(Constants.VALUE_OPEN_FROM_WING)) {
                                WingLayout.resultBmp = bitmap;
                            }
                            setResult(-1);
                            finish();
                            return;
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    finish();
                    return;
                default:
                    return;
            }
        }
        Toast.makeText(this, getResources().getString(R.string.import_img_warning), Toast.LENGTH_SHORT).show();
    }

    private void changeBG() {
        int i = curBgType;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            curBgType = 6;
                            this.imageViewBackgroundCover.setImageBitmap(null);
                            this.imageViewBackgroundCover.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg5, this.width, this.height));
                            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg5);
                            return;
                        } else if (i == 6) {
                            curBgType = 1;
                            this.imageViewBackgroundCover.setImageBitmap(null);
                            this.imageViewBackgroundCover.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg, this.width, this.height));
                            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg);
                            return;
                        } else {
                            return;
                        }
                    }
                    curBgType = 5;
                    this.imageViewBackgroundCover.setImageBitmap(null);
                    this.imageViewBackgroundCover.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg4, this.width, this.height));
                    bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg4);
                    return;
                }
                curBgType = 4;
                this.imageViewBackgroundCover.setImageBitmap(null);
                this.imageViewBackgroundCover.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg3, this.width, this.height));
                bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg3);
                return;
            }
            curBgType = 3;
            this.imageViewBackgroundCover.setImageBitmap(null);
            this.imageViewBackgroundCover.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg2, this.width, this.height));
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg2);
            return;
        }
        curBgType = 2;
        this.imageViewBackgroundCover.setImageBitmap(null);
        this.imageViewBackgroundCover.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg1, this.width, this.height));
        bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg1);
    }

    public void importImageFromUri() {
        this.showDialog = false;
        final ProgressDialog show = ProgressDialog.show(this, "", getResources().getString(R.string.importing_image), true);
        show.setCancelable(false);
        new Thread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (StickerEraseActivity.b == null) {
                        StickerEraseActivity.this.showDialog = true;
                    } else {
                        StickerEraseActivity.this.orgBitmap = StickerEraseActivity.b.copy(StickerEraseActivity.b.getConfig(), true);
                        int dpToPx = ImageUtils.dpToPx((Context) StickerEraseActivity.this, 42.0f);
                        StickerEraseActivity.orgBitWidth = StickerEraseActivity.b.getWidth();
                        StickerEraseActivity.orgBitHeight = StickerEraseActivity.b.getHeight();
                        Bitmap createBitmap = Bitmap.createBitmap(StickerEraseActivity.b.getWidth() + dpToPx + dpToPx, StickerEraseActivity.b.getHeight() + dpToPx + dpToPx, StickerEraseActivity.b.getConfig());
                        Canvas canvas = new Canvas(createBitmap);
                        canvas.drawColor(0);
                        float f = dpToPx;
                        canvas.drawBitmap(StickerEraseActivity.b, f, f, (Paint) null);
                        StickerEraseActivity.b = createBitmap;
                        if (StickerEraseActivity.b.getWidth() > StickerEraseActivity.this.width || StickerEraseActivity.b.getHeight() > StickerEraseActivity.this.height || (StickerEraseActivity.b.getWidth() < StickerEraseActivity.this.width && StickerEraseActivity.b.getHeight() < StickerEraseActivity.this.height)) {
                            StickerEraseActivity.b = ImageUtils.resizeBitmap(StickerEraseActivity.b, StickerEraseActivity.this.width, StickerEraseActivity.this.height);
                        }
                    }
                    Thread.sleep(1000L);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    StickerEraseActivity.this.showDialog = true;
                    show.dismiss();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    StickerEraseActivity.this.showDialog = true;
                    show.dismiss();
                }
                show.dismiss();
            }
        }).start();
        show.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.10
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (StickerEraseActivity.this.showDialog) {
                    StickerEraseActivity stickerEraseActivity = StickerEraseActivity.this;
                    Toast.makeText(stickerEraseActivity, stickerEraseActivity.getResources().getString(R.string.import_error), Toast.LENGTH_SHORT).show();
                    StickerEraseActivity.this.finish();
                    return;
                }
                ConstantsApp.rewid = "";
                ConstantsApp.uri = "";
                ConstantsApp.bitmapSticker = null;
                StickerEraseActivity.this.setImageBitmap();
            }
        });
    }

    public void setImageBitmap() {
        this.dv = new EraseView(this);
        this.dv1 = new ImageView(this);
        this.dv.setImageBitmap(b);
        this.dv1.setImageBitmap(getGreenLayerBitmap(b));
        this.dv.enableTouchClear(false);
        this.dv.setMODE(0);
        this.dv.invalidate();
        this.seekBarBrushOffset.setProgress(225);
        this.radius_seekbar.setProgress(18);
        this.seekBarThreshold.setProgress(20);
        this.main_rel.removeAllViews();
        this.main_rel.setScaleX(1.0f);
        this.main_rel.setScaleY(1.0f);
        this.main_rel.addView(this.dv1);
        this.main_rel.addView(this.dv);
        this.dv.invalidate();
        this.dv1.setVisibility(View.GONE);
        this.dv.setUndoRedoListener(new EraseView.UndoRedoListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.11
            @Override // com.example.photoareditor.Classs.EraseView.UndoRedoListener
            public void enableUndo(boolean z, int i) {
                if (z) {
                    StickerEraseActivity stickerEraseActivity = StickerEraseActivity.this;
                    stickerEraseActivity.setBGDrawable(i, stickerEraseActivity.undo_btn, R.drawable.ic_undo, z);
                    return;
                }
                StickerEraseActivity stickerEraseActivity2 = StickerEraseActivity.this;
                stickerEraseActivity2.setBGDrawable(i, stickerEraseActivity2.undo_btn, R.drawable.ic_undo, z);
            }

            @Override // com.example.photoareditor.Classs.EraseView.UndoRedoListener
            public void enableRedo(boolean z, int i) {
                if (z) {
                    StickerEraseActivity stickerEraseActivity = StickerEraseActivity.this;
                    stickerEraseActivity.setBGDrawable(i, stickerEraseActivity.redo_btn, R.drawable.ic_redo, z);
                    return;
                }
                StickerEraseActivity stickerEraseActivity2 = StickerEraseActivity.this;
                stickerEraseActivity2.setBGDrawable(i, stickerEraseActivity2.redo_btn, R.drawable.ic_redo, z);
            }
        });
        b.recycle();
        this.dv.setActionListener(new EraseView.ActionListener() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.12
            @Override // com.example.photoareditor.Classs.EraseView.ActionListener
            public void onActionCompleted(int i) {
                StickerEraseActivity.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.12.1
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                });
            }

            @Override // com.example.photoareditor.Classs.EraseView.ActionListener
            public void onAction(final int i) {
                StickerEraseActivity.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.12.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (i == 0) {
                            StickerEraseActivity.this.relativeLayoutSeekBar.setVisibility(View.GONE);
                        }
                        if (i == 1) {
                            StickerEraseActivity.this.relativeLayoutSeekBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    public void setBGDrawable(int i, final ImageView imageView, final int i2, final boolean z) {
        runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.StickerEraseActivity.13
            @Override // java.lang.Runnable
            public void run() {
                imageView.setImageResource(i2);
                imageView.setEnabled(z);
            }
        });
    }

    public Bitmap getGreenLayerBitmap(Bitmap bitmap2) {
        Paint paint = new Paint();
        paint.setColor(-16711936);
        paint.setAlpha(80);
        int dpToPx = ImageUtils.dpToPx((Context) this, 42.0f);
        Bitmap createBitmap = Bitmap.createBitmap(orgBitWidth + dpToPx + dpToPx, orgBitHeight + dpToPx + dpToPx, bitmap2.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(0);
        float f = dpToPx;
        Paint paint2 = null;
        canvas.drawBitmap(this.orgBitmap, f, f, paint2);
        canvas.drawRect(f, f, orgBitWidth + dpToPx, orgBitHeight + dpToPx, paint);
        Bitmap createBitmap2 = Bitmap.createBitmap(orgBitWidth + dpToPx + dpToPx, orgBitHeight + dpToPx + dpToPx, bitmap2.getConfig());
        Canvas canvas2 = new Canvas(createBitmap2);
        canvas2.drawColor(0);
        canvas2.drawBitmap(this.orgBitmap, f, f, paint2);
        patternBMPshader = new BitmapShader(ImageUtils.resizeBitmap(createBitmap2, this.width, this.height), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return ImageUtils.resizeBitmap(createBitmap, this.width, this.height);
    }

    public void setSelected(int i) {
        if (i == R.id.relativeLayoutEraser) {
            this.seekBarBrushOffset.setProgress(this.dv.getOffset() + 150);
            this.linearLayoutEraser.setVisibility(View.VISIBLE);
            this.linearLayoutAuto.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.GONE);
        }
        if (i == R.id.relativeLayoutAuto) {
            this.seekBarOffset.setProgress(this.dv.getOffset() + 150);
            this.linearLayoutEraser.setVisibility(View.GONE);
            this.linearLayoutAuto.setVisibility(View.VISIBLE);
            this.lay_lasso_cut.setVisibility(View.GONE);
        }
        if (i == R.id.relativeLayoutExtract) {
            this.seekBarExtractOffset.setProgress(this.dv.getOffset() + 150);
            this.linearLayoutEraser.setVisibility(View.GONE);
            this.linearLayoutAuto.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.VISIBLE);
        }
        if (i == R.id.relativeLayoutRestore) {
            this.seekBarBrushOffset.setProgress(this.dv.getOffset() + 150);
            this.linearLayoutEraser.setVisibility(View.VISIBLE);
            this.linearLayoutAuto.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.GONE);
        }
        if (i == R.id.relativeLayoutZoom) {
            this.linearLayoutEraser.setVisibility(View.GONE);
            this.linearLayoutAuto.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.GONE);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Bitmap bitmap2 = b;
        if (bitmap2 != null) {
            bitmap2.recycle();
            b = null;
        }
        try {
            if (!isFinishing() && this.dv.pd != null && this.dv.pd.isShowing()) {
                this.dv.pd.dismiss();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onDestroy();
    }
}
