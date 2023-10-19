package com.example.photoeditor.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.photoeditor.Adapter.AdjustAdapter;
import com.example.photoeditor.Adapter.ColorAdapter;
import com.example.photoeditor.Adapter.FilterAdapter;
import com.example.photoeditor.Adapter.MagicBrushAdapter;
import com.example.photoeditor.Adapter.OverlayAdapter;
import com.example.photoeditor.Adapter.Sticker_Category_Adapter;
import com.example.photoeditor.Adapter.Sticker_Thamb_Adapter;
import com.example.photoeditor.Classs.AlignHorizontallyEvent;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.DegreeSeekBar;
import com.example.photoeditor.Classs.DeleteIconEvent;
import com.example.photoeditor.Classs.DrawModel;
import com.example.photoeditor.Classs.DrawableSticker;
import com.example.photoeditor.Classs.Drawing;
import com.example.photoeditor.Classs.EditTextIconEvent;
import com.example.photoeditor.Classs.FilterFileAsset;
import com.example.photoeditor.Classs.FilterUtils;
import com.example.photoeditor.Classs.FlipHorizontallyEvent;
import com.example.photoeditor.Classs.OverlayFileAsset;
import com.example.photoeditor.Classs.PolishEditor;
import com.example.photoeditor.Classs.PolishStickerIcons;
import com.example.photoeditor.Classs.PolishStickerView;
import com.example.photoeditor.Classs.PolishText;
import com.example.photoeditor.Classs.PolishTextView;
import com.example.photoeditor.Classs.PolishView;
import com.example.photoeditor.Classs.Preference;
import com.example.photoeditor.Classs.Sticker;
import com.example.photoeditor.Classs.SystemUtil;
import com.example.photoeditor.Classs.TextFragment;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Classs.ZoomIconEvent;
import com.example.photoeditor.Fragment.CropFragment;
import com.example.photoeditor.Fragment.FrameFragment;
import com.example.photoeditor.Fragment.MirrorFragment;
import com.example.photoeditor.Interface.AdjustListener;
import com.example.photoeditor.Interface.BrushColorListener;
import com.example.photoeditor.Interface.BrushMagicListener;
import com.example.photoeditor.Interface.FilterListener;
import com.example.photoeditor.Interface.OnPolishEditorListener;
import com.example.photoeditor.Interface.OnSaveBitmap;
import com.example.photoeditor.Interface.OverlayListener;
import com.example.photoeditor.ModelClass.Stickermodel;
import com.example.photoeditor.ModelClass.stickerlistModel;
import com.example.photoeditor.R;
import com.hold1.keyboardheightprovider.KeyboardHeightProvider;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wysaid.myUtils.MsgUtil;
import org.wysaid.nativePort.CGENativeLibrary;

/* loaded from: classes7.dex */
public class EditorActivity extends AppCompatActivity implements OnPolishEditorListener, View.OnClickListener, CropFragment.OnCropPhoto, FilterListener, AdjustListener, FrameFragment.RatioSaveListener, OverlayListener, BrushColorListener, BrushMagicListener, Sticker_Thamb_Adapter.OnClickListener {
    public static RecyclerView recycler_tab_layout;
    public static RecyclerView sticker_viewpaper;
    SeekBar adjustFilter;
    DegreeSeekBar adjustSeekBar;
    RelativeLayout adjust_tool;
    RelativeLayout blur_tool;
    HorizontalScrollView bottom_tool;
    ColorAdapter colorAdapter;
    RelativeLayout constraintLayoutAdjust;
    ConstraintLayout constraintLayoutEffects;
    RelativeLayout constraintLayoutFilter;
    ConstraintLayout constraintLayoutMagic;
    ConstraintLayout constraintLayoutMagicTool;
    ConstraintLayout constraintLayoutNeon;
    ConstraintLayout constraintLayoutNeonTool;
    RelativeLayout constraintLayoutOverlay;
    ConstraintLayout constraintLayoutPaint;
    ConstraintLayout constraintLayoutPaintTool;
    RelativeLayout constraintLayoutSave;
    LinearLayout constraintLayoutSaveOverlay;
    ConstraintLayout constraintLayoutView;
    RelativeLayout crop_tool;
    File file;
    RelativeLayout filter_tool;
    Bitmap finalbitmap;
    RelativeLayout frame_tool;
    Guideline guideline;
    Guideline guidelinePaint;
    ImageView imageViewCleanMagic;
    ImageView imageViewCleanPaint;
    ImageView imageViewCompareAdjust;
    public ImageView imageViewCompareFilter;
    ImageView imageViewCompareOverlay;
    ImageView imageViewRedoMagic;
    ImageView imageViewRedoNeon;
    ImageView imageViewRedoPaint;
    ImageView imageViewSelectMagic;
    ImageView imageViewUndoMagic;
    ImageView imageViewUndoNeon;
    ImageView imageViewUndoPaint;
    KeyboardHeightProvider keyboardProvider;
    LinearLayout linearLayoutRedo;
    public AdjustAdapter mAdjustAdapter;
    RelativeLayout magic_tool;
    RelativeLayout mirror_tool;
    RelativeLayout neon_tool;
    RelativeLayout overlay_tool;
    String pass_st1;
    public PolishEditor polishEditor;
    PolishStickerIcons polishStickerIconAlign;
    PolishStickerIcons polishStickerIconClose;
    PolishStickerIcons polishStickerIconEdit;
    PolishStickerIcons polishStickerIconFlip;
    PolishStickerIcons polishStickerIconRotate;
    PolishStickerIcons polishStickerIconScale;
    public PolishView polishView;
    RecyclerView recyclerViewAdjust;
    RecyclerView recyclerViewFilter;
    RecyclerView recyclerViewMagicListColor;
    RecyclerView recyclerViewNeonListColor;
    RecyclerView recyclerViewPaintListColor;
    RecyclerView recyclerViewTools;
    RecyclerView recycler_view_burn_effect;
    RecyclerView recycler_view_color_effect;
    RecyclerView recycler_view_dodge_effect;
    RecyclerView recycler_view_hardmix_effet;
    RecyclerView recycler_view_hue_effect;
    RecyclerView recycler_view_overlay_effect;
    ImageView redo;
    RelativeLayout relativeLayoutLoading;
    RelativeLayout relativeLayoutMagic;
    RelativeLayout relativeLayoutNeon;
    RelativeLayout relativeLayoutPaint;
    RelativeLayout relativeLayoutWrapper;
    TextView save;
    SeekBar seekBarBrush;
    SeekBar seekBarEraser;
    SeekBar seekBarMagicBrush;
    SeekBar seekBarMagicEraser;
    SeekBar seekBarMagicOpacity;
    SeekBar seekBarNeonBrush;
    SeekBar seekBarNeonEraser;
    SeekBar seekBarOpacity;
    SeekBar seekBarOverlay;
    SeekBar seekbarSticker;
    Animation slideDownAnimation;
    Animation slideUpAnimation;
    private ArrayList<Stickermodel.Datum> stickerData;
    RelativeLayout sticker_layout;
    RelativeLayout sticker_tool;
    public TextFragment.TextEditor textEditor;
    public TextFragment textFragment;
    ImageView textViewB;
    TextView textViewBrush;
    TextView textViewBrushM;
    TextView textViewBrushN;
    TextView textViewErase;
    TextView textViewEraseM;
    TextView textViewEraseN;
    TextView textViewMagicBrush;
    TextView textViewMagicEraser;
    TextView textViewMagicOpacity;
    TextView textViewNeonBrush;
    TextView textViewNeonEraser;
    TextView textViewOpacity;
    TextView textViewOpacityM;
    ImageView textViewSelectN;
    TextView textViewValueBrush;
    TextView textViewValueEraser;
    TextView textViewValueOpacity;
    RelativeLayout text_tool;
    TextView text_view_burn;
    TextView text_view_color;
    TextView text_view_dodge;
    TextView text_view_hardmix;
    TextView text_view_hue;
    TextView text_view_overlay;
    ImageView undo;
    View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: com.example.photoareditor.Activity.EditorActivity.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return EditorActivity.this.lambda$new$24$PolishEditorActivity(view, motionEvent);
        }
    };
    public ArrayList listFilter = new ArrayList();
    public ArrayList listBurnEffect = new ArrayList();
    public ArrayList listColorEffect = new ArrayList();
    public ArrayList listDodgeEffect = new ArrayList();
    public ArrayList listHardMixEffect = new ArrayList();
    public ArrayList listHueEffect = new ArrayList();
    public ArrayList listOverlayEffect = new ArrayList();
    public CGENativeLibrary.LoadImageCallback loadImageCallback = new CGENativeLibrary.LoadImageCallback() { // from class: com.example.photoareditor.Activity.EditorActivity.2
        @Override // org.wysaid.nativePort.CGENativeLibrary.LoadImageCallback
        public Bitmap loadImage(String str, Object obj) {
            try {
                return BitmapFactory.decodeStream(EditorActivity.this.getAssets().open(str));
            } catch (IOException e) {
                return null;
            }
        }

        @Override // org.wysaid.nativePort.CGENativeLibrary.LoadImageCallback
        public void loadImageOK(Bitmap bitmap, Object obj) {
            bitmap.recycle();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, null);
        this.polishView = new PolishView(this);
        this.crop_tool = (RelativeLayout) findViewById(R.id.crop_tool);
        this.filter_tool = (RelativeLayout) findViewById(R.id.filter_tool);
        this.adjust_tool = (RelativeLayout) findViewById(R.id.adjust_tool);
        this.frame_tool = (RelativeLayout) findViewById(R.id.frame_tool);
        this.mirror_tool = (RelativeLayout) findViewById(R.id.mirror_tool);
        this.blur_tool = (RelativeLayout) findViewById(R.id.blur_tool);
        this.overlay_tool = (RelativeLayout) findViewById(R.id.overlay_tool);
        this.magic_tool = (RelativeLayout) findViewById(R.id.magic_tool);
        this.neon_tool = (RelativeLayout) findViewById(R.id.neon_tool);
        this.sticker_tool = (RelativeLayout) findViewById(R.id.sticker_tool);
        this.sticker_layout = (RelativeLayout) findViewById(R.id.sticker_layout);
        this.guideline = (Guideline) findViewById(R.id.guideline);
        this.recyclerViewAdjust = (RecyclerView) findViewById(R.id.recyclerViewAdjust);
        this.text_tool = (RelativeLayout) findViewById(R.id.text_tool);
        this.constraintLayoutAdjust = (RelativeLayout) findViewById(R.id.constraintLayoutAdjust);
        this.recyclerViewTools = (RecyclerView) findViewById(R.id.recyclerViewTools);
        this.constraintLayoutFilter = (RelativeLayout) findViewById(R.id.constraint_layout_filter);
        ImageView imageView11 = (ImageView) findViewById(R.id.image_view_compare_filter);
        this.imageViewCompareFilter = imageView11;
        imageView11.setOnTouchListener(this.onTouchListener);
        this.relativeLayoutWrapper = (RelativeLayout) findViewById(R.id.relative_layout_wrapper_photo);
        this.constraintLayoutSave = (RelativeLayout) findViewById(R.id.constraintLayoutSave);
        this.linearLayoutRedo = (LinearLayout) findViewById(R.id.linearLayoutRedo);
        this.recyclerViewFilter = (RecyclerView) findViewById(R.id.recycler_view_filter);
        this.guidelinePaint = (Guideline) findViewById(R.id.guidelinePaint);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_loading);
        this.relativeLayoutLoading = relativeLayout;
        relativeLayout.setVisibility(View.VISIBLE);
        this.constraintLayoutSaveOverlay = (LinearLayout) findViewById(R.id.constraint_layout_confirm_save_overlay);
        this.recycler_view_dodge_effect = (RecyclerView) findViewById(R.id.recycler_view_dodge_effect);
        this.constraintLayoutEffects = (ConstraintLayout) findViewById(R.id.constraint_layout_effects);
        this.constraintLayoutOverlay = (RelativeLayout) findViewById(R.id.constraint_layout_overlay);
        this.recycler_view_overlay_effect = (RecyclerView) findViewById(R.id.recycler_view_overlay_effect);
        this.recycler_view_color_effect = (RecyclerView) findViewById(R.id.recycler_view_color_effect);
        this.recycler_view_hardmix_effet = (RecyclerView) findViewById(R.id.recycler_view_hardmix_effet);
        this.recycler_view_hue_effect = (RecyclerView) findViewById(R.id.recycler_view_hue_effect);
        this.recycler_view_burn_effect = (RecyclerView) findViewById(R.id.recycler_view_burn_effect);
        this.text_view_overlay = (TextView) findViewById(R.id.text_view_overlay);
        this.text_view_color = (TextView) findViewById(R.id.text_view_color);
        this.text_view_dodge = (TextView) findViewById(R.id.text_view_dodge);
        this.text_view_hardmix = (TextView) findViewById(R.id.text_view_hardmix);
        this.text_view_hue = (TextView) findViewById(R.id.text_view_hue);
        this.text_view_burn = (TextView) findViewById(R.id.text_view_burn);
        this.textViewErase = (TextView) findViewById(R.id.textViewErase);
        this.textViewBrush = (TextView) findViewById(R.id.textViewBrush);
        this.textViewOpacity = (TextView) findViewById(R.id.textViewOpacity);
        this.textViewEraseN = (TextView) findViewById(R.id.textViewEraseN);
        this.textViewBrushN = (TextView) findViewById(R.id.textViewBrushN);
        this.textViewBrushM = (TextView) findViewById(R.id.textViewBrushM);
        this.textViewOpacityM = (TextView) findViewById(R.id.textViewOpacityM);
        this.textViewEraseM = (TextView) findViewById(R.id.textViewEraseM);
        this.save = (TextView) findViewById(R.id.save);
        this.relativeLayoutPaint = (RelativeLayout) findViewById(R.id.viewPaint);
        this.recyclerViewPaintListColor = (RecyclerView) findViewById(R.id.recyclerViewColorPaint);
        this.seekBarEraser = (SeekBar) findViewById(R.id.seekbarEraserSize);
        this.seekBarOpacity = (SeekBar) findViewById(R.id.seekbarOpacitySize);
        this.seekBarBrush = (SeekBar) findViewById(R.id.seekbarBrushSize);
        this.textViewValueEraser = (TextView) findViewById(R.id.seekbarEraserValue);
        this.textViewValueBrush = (TextView) findViewById(R.id.seekbarBrushValue);
        this.textViewValueOpacity = (TextView) findViewById(R.id.seekbarOpacityValue);
        this.textViewB = (ImageView) findViewById(R.id.textViewSelectPaint);
        this.relativeLayoutNeon = (RelativeLayout) findViewById(R.id.viewNeon);
        this.recyclerViewNeonListColor = (RecyclerView) findViewById(R.id.recyclerViewColorNeon);
        this.seekBarNeonEraser = (SeekBar) findViewById(R.id.seekbarNeonEraser);
        this.seekBarNeonBrush = (SeekBar) findViewById(R.id.seekbarNeonSize);
        this.textViewNeonEraser = (TextView) findViewById(R.id.seekbarEraserNeon);
        this.textViewNeonBrush = (TextView) findViewById(R.id.seekbarNeonValue);
        this.textViewSelectN = (ImageView) findViewById(R.id.textViewSelectNeon);
        this.relativeLayoutMagic = (RelativeLayout) findViewById(R.id.viewMagic);
        this.seekBarMagicOpacity = (SeekBar) findViewById(R.id.seekbarOpacityMagic);
        this.seekBarMagicEraser = (SeekBar) findViewById(R.id.seekbarEraserMagic);
        this.seekBarMagicBrush = (SeekBar) findViewById(R.id.seekbarMagicSize);
        this.textViewMagicEraser = (TextView) findViewById(R.id.seekbarEraserMagicValue);
        this.textViewMagicBrush = (TextView) findViewById(R.id.seekbarBrushMagicValue);
        this.textViewMagicOpacity = (TextView) findViewById(R.id.seekbarOpacityMagicValue);
        this.imageViewSelectMagic = (ImageView) findViewById(R.id.textViewSelectMagic);
        this.recyclerViewMagicListColor = (RecyclerView) findViewById(R.id.recyclerViewColorMagic);
        ImageView imageView10 = (ImageView) findViewById(R.id.imageViewCompareAdjust);
        this.constraintLayoutMagic = (ConstraintLayout) findViewById(R.id.constraintLayoutMagic);
        this.constraintLayoutMagicTool = (ConstraintLayout) findViewById(R.id.constraintLayoutMagicTool);
        this.constraintLayoutPaint = (ConstraintLayout) findViewById(R.id.constraintLayoutPaint);
        this.constraintLayoutNeon = (ConstraintLayout) findViewById(R.id.constraintLayoutNeon);
        this.constraintLayoutPaintTool = (ConstraintLayout) findViewById(R.id.constraintLayoutPaintTool);
        this.constraintLayoutNeonTool = (ConstraintLayout) findViewById(R.id.constraintLayoutNeonTool);
        this.imageViewCompareAdjust = imageView10;
        imageView10.setOnTouchListener(this.onTouchListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.image_view_undo_Magic);
        this.imageViewUndoMagic = imageView2;
        imageView2.setVisibility(View.GONE);
        ImageView imageView4 = (ImageView) findViewById(R.id.image_view_redo_Magic);
        this.imageViewRedoMagic = imageView4;
        imageView4.setVisibility(View.GONE);
        ImageView imageView6 = (ImageView) findViewById(R.id.image_view_clean_Magic);
        this.imageViewCleanMagic = imageView6;
        imageView6.setVisibility(View.GONE);
        ImageView imageView = (ImageView) findViewById(R.id.image_view_undo);
        this.imageViewUndoPaint = imageView;
        imageView.setVisibility(View.GONE);
        ImageView imageView3 = (ImageView) findViewById(R.id.image_view_redo);
        this.imageViewRedoPaint = imageView3;
        imageView3.setVisibility(View.GONE);
        ImageView imageView5 = (ImageView) findViewById(R.id.image_view_clean);
        this.imageViewCleanPaint = imageView5;
        imageView5.setVisibility(View.GONE);
        ImageView imageView8 = (ImageView) findViewById(R.id.image_view_undo_neon);
        this.imageViewUndoNeon = imageView8;
        imageView8.setVisibility(View.GONE);
        ImageView imageView9 = (ImageView) findViewById(R.id.image_view_redo_neon);
        this.imageViewRedoNeon = imageView9;
        imageView9.setVisibility(View.GONE);
        this.undo = (ImageView) findViewById(R.id.undo);
        this.redo = (ImageView) findViewById(R.id.redo);
        this.bottom_tool = (HorizontalScrollView) findViewById(R.id.bottom_tool);
        ImageView imageView12 = (ImageView) findViewById(R.id.image_view_compare_overlay);
        this.imageViewCompareOverlay = imageView12;
        imageView12.setOnTouchListener(this.onTouchListener);
        onClickListener();
        this.undo.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.setUndo();
            }
        });
        this.redo.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.setRedo();
            }
        });
        PolishView polishView2 = (PolishView) findViewById(R.id.photo_editor_view);
        this.polishView = polishView2;
        polishView2.setVisibility(View.INVISIBLE);
        this.slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        this.slideDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        this.recyclerViewAdjust.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewAdjust.setHasFixedSize(true);
        AdjustAdapter adjustAdapter = new AdjustAdapter(getApplicationContext(), this);
        this.mAdjustAdapter = adjustAdapter;
        this.recyclerViewAdjust.setAdapter(adjustAdapter);
        this.recyclerViewFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewFilter.setHasFixedSize(true);
        this.recycler_view_overlay_effect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_overlay_effect.setHasFixedSize(true);
        this.recycler_view_dodge_effect.setVisibility(View.GONE);
        this.recycler_view_dodge_effect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_dodge_effect.setHasFixedSize(true);
        this.recycler_view_color_effect.setVisibility(View.GONE);
        this.recycler_view_color_effect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_color_effect.setHasFixedSize(true);
        this.recycler_view_hardmix_effet.setVisibility(View.GONE);
        this.recycler_view_hardmix_effet.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_hardmix_effet.setHasFixedSize(true);
        this.recycler_view_hue_effect.setVisibility(View.GONE);
        this.recycler_view_hue_effect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_hue_effect.setHasFixedSize(true);
        this.recycler_view_burn_effect.setVisibility(View.GONE);
        this.recycler_view_burn_effect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_burn_effect.setHasFixedSize(true);
        this.recyclerViewPaintListColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewPaintListColor.setHasFixedSize(true);
        this.recyclerViewPaintListColor.setAdapter(new ColorAdapter(getApplicationContext(), this));
        this.recyclerViewNeonListColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewNeonListColor.setHasFixedSize(true);
        this.recyclerViewNeonListColor.setAdapter(new ColorAdapter(getApplicationContext(), this));
        this.recyclerViewMagicListColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewMagicListColor.setHasFixedSize(true);
        this.recyclerViewMagicListColor.setAdapter(new MagicBrushAdapter(getApplicationContext(), this));
        getStickerData();
        PolishEditor build = new PolishEditor.Builder(this, this.polishView).setPinchTextScalable(true).build();
        this.polishEditor = build;
        build.setOnPhotoEditorListener(this);
        Preference.setKeyboard(getApplicationContext(), 0);
        KeyboardHeightProvider keyboardHeightProvider = new KeyboardHeightProvider(this);
        this.keyboardProvider = keyboardHeightProvider;
        keyboardHeightProvider.addKeyboardListener(new KeyboardHeightProvider.KeyboardListener() { // from class: com.example.photoareditor.Activity.EditorActivity.5
            @Override // com.hold1.keyboardheightprovider.KeyboardHeightProvider.KeyboardListener
            public void onHeightChanged(int i) {
                if (i <= 0) {
                    Preference.setHeightOfNotch(EditorActivity.this.getApplicationContext(), -i);
                    return;
                }
                TextFragment textFragment2 = EditorActivity.this.textFragment;
                if (textFragment2 != null) {
                    textFragment2.updateAddTextBottomToolbarHeight(Preference.getHeightOfNotch(EditorActivity.this.getApplicationContext()) + i);
                    Preference.setKeyboard(EditorActivity.this.getApplicationContext(), Preference.getHeightOfNotch(EditorActivity.this.getApplicationContext()) + i);
                }
            }
        });
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekbarOverlay);
        this.seekBarOverlay = seekBar2;
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishView.setFilterIntensity(i / 100.0f);
            }
        });
        DegreeSeekBar degreeSeekBar = (DegreeSeekBar) findViewById(R.id.seekbarAdjust);
        this.adjustSeekBar = degreeSeekBar;
        degreeSeekBar.setCenterTextColor(getResources().getColor(R.color.mainColor));
        this.adjustSeekBar.setTextColor(getResources().getColor(R.color.text_color));
        this.adjustSeekBar.setPointColor(getResources().getColor(R.color.text_color));
        this.adjustSeekBar.setDegreeRange(-50, 50);
        this.adjustSeekBar.setScrollingListener(new DegreeSeekBar.ScrollingListener() { // from class: com.example.photoareditor.Activity.EditorActivity.7
            @Override // com.example.photoareditor.Classs.DegreeSeekBar.ScrollingListener
            public void onScrollEnd() {
            }

            @Override // com.example.photoareditor.Classs.DegreeSeekBar.ScrollingListener
            public void onScrollStart() {
            }

            @Override // com.example.photoareditor.Classs.DegreeSeekBar.ScrollingListener
            public void onScroll(int i) {
                AdjustAdapter.AdjustModel currentAdjustModel = EditorActivity.this.mAdjustAdapter.getCurrentAdjustModel();
                currentAdjustModel.originValue = (Math.abs(i + 50) * ((currentAdjustModel.maxValue - ((currentAdjustModel.maxValue + currentAdjustModel.minValue) / 2.0f)) / 50.0f)) + currentAdjustModel.minValue;
                EditorActivity.this.polishEditor.setAdjustFilter(EditorActivity.this.mAdjustAdapter.getFilterConfig());
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbarFilter);
        this.adjustFilter = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar3) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar3) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar3, int i, boolean z) {
                EditorActivity.this.polishView.setFilterIntensity(i / 100.0f);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            new loadBitmapUri().execute(extras.getString(Utils.KEY_SELECTED_PHOTOS));
        }
        this.crop_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity editorActivity = EditorActivity.this;
                CropFragment.show(editorActivity, editorActivity, editorActivity.polishView.getCurrentBitmap());
                EditorActivity.this.goneLayout();
            }
        });
        this.filter_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new openFilters().execute(new Void[0]);
            }
        });
        this.adjust_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.setGoneSave();
                EditorActivity.this.setGuideLinePaint();
                EditorActivity.this.reloadingLayout();
                EditorActivity editorActivity = EditorActivity.this;
                editorActivity.viewSlideDown(editorActivity.recyclerViewTools);
                EditorActivity editorActivity2 = EditorActivity.this;
                editorActivity2.viewSlideDown(editorActivity2.linearLayoutRedo);
                EditorActivity editorActivity3 = EditorActivity.this;
                editorActivity3.viewSlideUp(editorActivity3.constraintLayoutAdjust);
                EditorActivity.this.adjustSeekBar.setCurrentDegrees(0);
                AdjustAdapter adjustAdapter2 = new AdjustAdapter(EditorActivity.this.getApplicationContext(), EditorActivity.this);
                EditorActivity.this.mAdjustAdapter = adjustAdapter2;
                EditorActivity.this.recyclerViewAdjust.setAdapter(adjustAdapter2);
                EditorActivity.this.polishEditor.setAdjustFilter(EditorActivity.this.mAdjustAdapter.getFilterConfig());
            }
        });
        this.frame_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new openFrameFragment().execute(new Void[0]);
                EditorActivity.this.goneLayout();
            }
        });
        this.mirror_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new openMirrorFragment().execute(new Void[0]);
                EditorActivity.this.goneLayout();
            }
        });
        this.blur_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.goneLayout();
                BitmapTransfer.bitmap = EditorActivity.this.polishView.getCurrentBitmap();
                EditorActivity.this.startActivityForResult(new Intent(EditorActivity.this, BlurLayout.class), TypedValues.Custom.TYPE_INT);
                EditorActivity.this.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        this.overlay_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.setGoneSave();
                EditorActivity.this.constraintLayoutSaveOverlay.setVisibility(View.VISIBLE);
                new effectOvarlay().execute(new Void[0]);
                new effectColor().execute(new Void[0]);
                new effectHardmix().execute(new Void[0]);
                new effectDodge().execute(new Void[0]);
                new effectDivide().execute(new Void[0]);
                new effectBurn().execute(new Void[0]);
                EditorActivity.this.seekBarOverlay.setVisibility(View.VISIBLE);
            }
        });
        this.neon_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.setColorNeon();
                EditorActivity.this.polishEditor.setBrushDrawingMode(true);
                EditorActivity.this.recyclerViewTools.setVisibility(View.GONE);
                EditorActivity.this.linearLayoutRedo.setVisibility(View.GONE);
                EditorActivity.this.constraintLayoutNeonTool.setVisibility(View.VISIBLE);
                EditorActivity.this.constraintLayoutSave.setVisibility(View.GONE);
                EditorActivity.this.constraintLayoutNeon.setVisibility(View.VISIBLE);
                EditorActivity.this.polishEditor.clearBrushAllViews();
                EditorActivity.this.polishEditor.setBrushDrawingMode(false);
                EditorActivity.this.setGoneSave();
                ConstraintSet constraintSet3 = new ConstraintSet();
                try {
                    constraintSet3.clone(EditorActivity.this.constraintLayoutView);
                } catch (Exception e) {
                    Log.e("aaaaa", "onClick: " + e.getMessage());
                }
                constraintSet3.connect(EditorActivity.this.relativeLayoutWrapper.getId(), 1, EditorActivity.this.constraintLayoutView.getId(), 1, 0);
                constraintSet3.connect(EditorActivity.this.relativeLayoutWrapper.getId(), 4, EditorActivity.this.constraintLayoutNeon.getId(), 3, 0);
                constraintSet3.connect(EditorActivity.this.relativeLayoutWrapper.getId(), 2, EditorActivity.this.constraintLayoutView.getId(), 2, 0);
                constraintSet3.applyTo(EditorActivity.this.constraintLayoutView);
                EditorActivity.this.polishEditor.setBrushMode(2);
                EditorActivity.this.reloadingLayout();
            }
        });
        this.magic_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditorActivity.this.setMagicBrush();
                EditorActivity.this.polishEditor.setBrushDrawingMode(true);
                EditorActivity.this.recyclerViewTools.setVisibility(View.GONE);
                EditorActivity.this.linearLayoutRedo.setVisibility(View.GONE);
                EditorActivity.this.constraintLayoutMagic.setVisibility(View.VISIBLE);
                EditorActivity.this.constraintLayoutMagicTool.setVisibility(View.VISIBLE);
                EditorActivity.this.polishEditor.clearBrushAllViews();
                EditorActivity.this.polishEditor.setBrushDrawingMode(false);
                EditorActivity.this.setGoneSave();
                ConstraintSet constraintSet2 = new ConstraintSet();
                try {
                    constraintSet2.clone(EditorActivity.this.constraintLayoutView);
                } catch (Exception e) {
                    Log.e("aaaaa", "onClick: " + e.getMessage());
                }
                constraintSet2.connect(EditorActivity.this.relativeLayoutWrapper.getId(), 1, EditorActivity.this.constraintLayoutView.getId(), 1, 0);
                constraintSet2.connect(EditorActivity.this.relativeLayoutWrapper.getId(), 4, EditorActivity.this.constraintLayoutMagic.getId(), 3, 0);
                constraintSet2.connect(EditorActivity.this.relativeLayoutWrapper.getId(), 2, EditorActivity.this.constraintLayoutView.getId(), 2, 0);
                constraintSet2.applyTo(EditorActivity.this.constraintLayoutView);
                EditorActivity.this.polishEditor.setBrushMode(3);
                EditorActivity.this.reloadingLayout();
            }
        });
        this.sticker_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (EditorActivity.this.sticker_layout.getVisibility() == View.GONE) {
                    EditorActivity editorActivity = EditorActivity.this;
                    editorActivity.viewSlideUp(editorActivity.sticker_layout);
                    return;
                }
                EditorActivity editorActivity2 = EditorActivity.this;
                editorActivity2.viewSlideDown(editorActivity2.sticker_layout);
            }
        });
        this.text_tool.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.19
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setGoneSave();
                EditorActivity.this.setGuideLine();
                EditorActivity.this.polishView.setLocked(false);
                EditorActivity.this.textFragment();
                EditorActivity editorActivity = EditorActivity.this;
                editorActivity.viewSlideDown(editorActivity.recyclerViewTools);
                EditorActivity editorActivity2 = EditorActivity.this;
                editorActivity2.viewSlideDown(editorActivity2.linearLayoutRedo);
                EditorActivity.this.constraintLayoutEffects.setVisibility(View.GONE);
            }
        });
    }

    private void onClickListener() {
        this.undo.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.20
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setUndo();
            }
        });
        this.redo.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.21
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setRedo();
            }
        });
        this.text_view_overlay.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.22
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setOverlayEffect();
            }
        });
        this.text_view_color.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.23
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setColorEffect();
            }
        });
        this.text_view_dodge.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.24
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setDodgeEffect();
            }
        });
        this.text_view_hardmix.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.25
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setHardMixEffect();
            }
        });
        this.text_view_hue.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.26
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setHueEffect();
            }
        });
        this.text_view_burn.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.27
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setBurnEffect();
            }
        });
        this.textViewErase.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.28
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setErasePaint();
            }
        });
        this.textViewBrush.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.29
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setColorPaint();
            }
        });
        this.textViewOpacity.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.30
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setPaintOpacity();
            }
        });
        this.textViewEraseN.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.31
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setEraseNeon();
            }
        });
        this.textViewBrushN.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.32
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setColorNeon();
            }
        });
        this.textViewEraseM.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.33
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setEraseMagic();
            }
        });
        this.textViewBrushM.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.34
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setMagicBrush();
            }
        });
        this.textViewOpacityM.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.35
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.setMagicOpacity();
            }
        });
        this.seekBarBrush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.36
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setBrushSize(i + 5);
                EditorActivity.this.textViewValueBrush.setText(String.valueOf(i));
            }
        });
        this.seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.37
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setPaintOpacity(i);
                EditorActivity.this.textViewValueOpacity.setText(String.valueOf(i));
            }
        });
        this.seekBarEraser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.38
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setBrushEraserSize(i);
                EditorActivity.this.polishEditor.brushEraser();
                EditorActivity.this.textViewValueEraser.setText(String.valueOf(i));
            }
        });
        this.seekBarMagicBrush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.39
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setBrushSize(i + 5);
                EditorActivity.this.textViewMagicBrush.setText(String.valueOf(i));
            }
        });
        this.seekBarMagicOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.40
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setMagicOpacity(i);
                EditorActivity.this.textViewMagicOpacity.setText(String.valueOf(i));
            }
        });
        this.seekBarMagicEraser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.41
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setBrushEraserSize(i);
                EditorActivity.this.polishEditor.brushEraser();
                EditorActivity.this.textViewMagicEraser.setText(String.valueOf(i));
            }
        });
        this.seekBarNeonBrush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.42
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setBrushSize(i + 5);
                EditorActivity.this.textViewNeonBrush.setText(String.valueOf(i));
            }
        });
        this.seekBarNeonEraser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.43
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity.this.polishEditor.setBrushEraserSize(i);
                EditorActivity.this.polishEditor.brushEraser();
                EditorActivity.this.textViewNeonEraser.setText(String.valueOf(i));
            }
        });
        DegreeSeekBar degreeSeekBar = (DegreeSeekBar) findViewById(R.id.seekbarAdjust);
        this.adjustSeekBar = degreeSeekBar;
        degreeSeekBar.setCenterTextColor(getResources().getColor(R.color.mainColor));
        this.adjustSeekBar.setTextColor(getResources().getColor(R.color.text_color));
        this.adjustSeekBar.setPointColor(getResources().getColor(R.color.text_color));
        this.adjustSeekBar.setDegreeRange(-50, 50);
        this.adjustSeekBar.setScrollingListener(new DegreeSeekBar.ScrollingListener() { // from class: com.example.photoareditor.Activity.EditorActivity.44
            @Override // com.example.photoareditor.Classs.DegreeSeekBar.ScrollingListener
            public void onScrollEnd() {
            }

            @Override // com.example.photoareditor.Classs.DegreeSeekBar.ScrollingListener
            public void onScrollStart() {
            }

            @Override // com.example.photoareditor.Classs.DegreeSeekBar.ScrollingListener
            public void onScroll(int i) {
                AdjustAdapter.AdjustModel currentAdjustModel = EditorActivity.this.mAdjustAdapter.getCurrentAdjustModel();
                currentAdjustModel.originValue = (Math.abs(i + 50) * ((currentAdjustModel.maxValue - ((currentAdjustModel.maxValue + currentAdjustModel.minValue) / 2.0f)) / 50.0f)) + currentAdjustModel.minValue;
                EditorActivity.this.polishEditor.setAdjustFilter(EditorActivity.this.mAdjustAdapter.getFilterConfig());
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbarFilter);
        this.adjustFilter = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.45
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                EditorActivity.this.polishView.setFilterIntensity(i / 100.0f);
            }
        });
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekbarOverlay);
        this.seekBarOverlay = seekBar2;
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.EditorActivity.46
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar3) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar3) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar3, int i, boolean z) {
                EditorActivity.this.polishView.setFilterIntensity(i / 100.0f);
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.EditorActivity.47
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditorActivity.this.saveImageToExternalStorage();
            }
        });
        PolishStickerIcons polishStickerIcons = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_close), 0, "DELETE");
        this.polishStickerIconClose = polishStickerIcons;
        polishStickerIcons.setIconEvent(new DeleteIconEvent());
        PolishStickerIcons polishStickerIcons2 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_scale), 3, PolishStickerIcons.SCALE);
        this.polishStickerIconScale = polishStickerIcons2;
        polishStickerIcons2.setIconEvent(new ZoomIconEvent());
        PolishStickerIcons polishStickerIcons3 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_flip), 1, PolishStickerIcons.FLIP);
        this.polishStickerIconFlip = polishStickerIcons3;
        polishStickerIcons3.setIconEvent(new FlipHorizontallyEvent());
        PolishStickerIcons polishStickerIcons4 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_rotate), 3, PolishStickerIcons.ROTATE);
        this.polishStickerIconRotate = polishStickerIcons4;
        polishStickerIcons4.setIconEvent(new ZoomIconEvent());
        PolishStickerIcons polishStickerIcons5 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_edit), 1, PolishStickerIcons.EDIT);
        this.polishStickerIconEdit = polishStickerIcons5;
        polishStickerIcons5.setIconEvent(new EditTextIconEvent());
        PolishStickerIcons polishStickerIcons6 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_center), 2, PolishStickerIcons.ALIGN);
        this.polishStickerIconAlign = polishStickerIcons6;
        polishStickerIcons6.setIconEvent(new AlignHorizontallyEvent());
        Log.e("aaaaaa", "onClickListener: " + this.polishView);
        this.polishView.setIcons(Arrays.asList(this.polishStickerIconClose, this.polishStickerIconScale, this.polishStickerIconFlip, this.polishStickerIconEdit, this.polishStickerIconRotate, this.polishStickerIconAlign));
        this.polishView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.polishView.setLocked(false);
        this.polishView.setConstrained(true);
        this.polishView.setOnStickerOperationListener(new PolishStickerView.OnStickerOperationListener() { // from class: com.example.photoareditor.Activity.EditorActivity.48
            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerDrag(Sticker sticker) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerFlip(Sticker sticker) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerTouchedDown(Sticker sticker) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerZoom(Sticker sticker) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onTouchDownBeauty(float f, float f2) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onTouchDragBeauty(float f, float f2) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onTouchUpBeauty(float f, float f2) {
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onAddSticker(Sticker sticker) {
                EditorActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
                EditorActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
            }

            @SuppressLint("RestrictedApi")
            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerSelected(Sticker sticker) {
                if (sticker instanceof PolishTextView) {
                    ((PolishTextView) sticker).setTextColor(SupportMenu.CATEGORY_MASK);
                    EditorActivity.this.polishView.replace(sticker);
                    EditorActivity.this.polishView.invalidate();
                }
                EditorActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
                EditorActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerDeleted(Sticker sticker) {
                EditorActivity.this.seekbarSticker.setVisibility(View.GONE);
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerTouchOutside() {
                EditorActivity.this.seekbarSticker.setVisibility(View.GONE);
            }

            @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
            public void onStickerDoubleTap(Sticker sticker) {
                if (sticker instanceof PolishTextView) {
                    sticker.setShow(false);
                    EditorActivity.this.polishView.setHandlingSticker(null);
                    EditorActivity polishEditorActivity = EditorActivity.this;
                    polishEditorActivity.textFragment = TextFragment.show(polishEditorActivity, ((PolishTextView) sticker).getPolishText());
                    EditorActivity.this.textEditor = new TextFragment.TextEditor() { // from class: com.example.photoareditor.Activity.EditorActivity.48.1
                        @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
                        public void onDone(PolishText polishText) {
                            EditorActivity.this.polishView.getStickers().remove(EditorActivity.this.polishView.getLastHandlingSticker());
                            EditorActivity.this.polishView.addSticker(new PolishTextView(EditorActivity.this, polishText));
                        }

                        @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
                        public void onBackButton() {
                            EditorActivity.this.polishView.showLastHandlingSticker();
                        }
                    };
                    EditorActivity.this.textFragment.setOnTextEditorListener(EditorActivity.this.textEditor);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImageToExternalStorage() {
        this.finalbitmap = this.polishView.getCurrentBitmap();
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/Photo Art Editor");
        myDir.mkdirs();
        Random generator = new Random();
        int n = generator.nextInt(1000);
        String fname = "Image-" + n + ".jpg";
        this.file = new File(myDir, fname);
        System.out.println("pathhhh====" + this.file);
        this.pass_st1 = this.file.getAbsolutePath();
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
        MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.EditorActivity.49
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String path2, Uri uri) {
            }
        });
        Intent intent = new Intent(this, PhotoSavedActivity.class);
        intent.putExtra(ClientCookie.PATH_ATTR, path);
        startActivity(intent);
    }

    @Override // com.example.photoareditor.Adapter.Sticker_Thamb_Adapter.OnClickListener
    public void addSticker(Bitmap bitmap) {
        Drawable d = new BitmapDrawable(getResources(), bitmap);
        this.polishView.addSticker(new DrawableSticker(d));
    }

    public void setOverlayEffect() {
        this.recycler_view_overlay_effect.setVisibility(View.VISIBLE);
        this.recycler_view_color_effect.setVisibility(View.GONE);
        this.recycler_view_dodge_effect.setVisibility(View.GONE);
        this.recycler_view_hardmix_effet.setVisibility(View.GONE);
        this.recycler_view_hue_effect.setVisibility(View.GONE);
        this.recycler_view_burn_effect.setVisibility(View.GONE);
        this.text_view_overlay.setTextColor(getResources().getColor(R.color.white));
        this.text_view_overlay.setBackgroundResource(R.drawable.background_selected);
        this.text_view_hue.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hue.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_color.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_color.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_dodge.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_dodge.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hardmix.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hardmix.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_burn.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_burn.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setColorEffect() {
        this.recycler_view_overlay_effect.setVisibility(View.GONE);
        this.recycler_view_color_effect.setVisibility(View.VISIBLE);
        this.recycler_view_dodge_effect.setVisibility(View.GONE);
        this.recycler_view_hardmix_effet.setVisibility(View.GONE);
        this.recycler_view_hue_effect.setVisibility(View.GONE);
        this.recycler_view_burn_effect.setVisibility(View.GONE);
        this.text_view_overlay.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_overlay.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hue.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hue.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_color.setTextColor(getResources().getColor(R.color.white));
        this.text_view_color.setBackgroundResource(R.drawable.background_selected);
        this.text_view_dodge.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_dodge.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hardmix.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hardmix.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_burn.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_burn.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setDodgeEffect() {
        this.recycler_view_overlay_effect.setVisibility(View.GONE);
        this.recycler_view_color_effect.setVisibility(View.GONE);
        this.recycler_view_dodge_effect.setVisibility(View.VISIBLE);
        this.recycler_view_hardmix_effet.setVisibility(View.GONE);
        this.recycler_view_hue_effect.setVisibility(View.GONE);
        this.recycler_view_burn_effect.setVisibility(View.GONE);
        this.text_view_overlay.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_overlay.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hue.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hue.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_color.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_color.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_dodge.setTextColor(getResources().getColor(R.color.white));
        this.text_view_dodge.setBackgroundResource(R.drawable.background_selected);
        this.text_view_hardmix.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hardmix.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_burn.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_burn.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setHardMixEffect() {
        this.recycler_view_overlay_effect.setVisibility(View.GONE);
        this.recycler_view_color_effect.setVisibility(View.GONE);
        this.recycler_view_dodge_effect.setVisibility(View.GONE);
        this.recycler_view_hardmix_effet.setVisibility(View.VISIBLE);
        this.recycler_view_hue_effect.setVisibility(View.GONE);
        this.recycler_view_burn_effect.setVisibility(View.GONE);
        this.text_view_overlay.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_overlay.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hue.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hue.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_color.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_color.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_dodge.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_dodge.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hardmix.setTextColor(getResources().getColor(R.color.white));
        this.text_view_hardmix.setBackgroundResource(R.drawable.background_selected);
        this.text_view_burn.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_burn.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setHueEffect() {
        this.recycler_view_overlay_effect.setVisibility(View.GONE);
        this.recycler_view_color_effect.setVisibility(View.GONE);
        this.recycler_view_dodge_effect.setVisibility(View.GONE);
        this.recycler_view_hardmix_effet.setVisibility(View.GONE);
        this.recycler_view_hue_effect.setVisibility(View.VISIBLE);
        this.recycler_view_burn_effect.setVisibility(View.GONE);
        this.text_view_overlay.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_overlay.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hue.setTextColor(getResources().getColor(R.color.white));
        this.text_view_hue.setBackgroundResource(R.drawable.background_selected);
        this.text_view_color.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_color.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_dodge.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_dodge.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hardmix.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hardmix.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_burn.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_burn.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setBurnEffect() {
        this.recycler_view_overlay_effect.setVisibility(View.GONE);
        this.recycler_view_color_effect.setVisibility(View.GONE);
        this.recycler_view_dodge_effect.setVisibility(View.GONE);
        this.recycler_view_hardmix_effet.setVisibility(View.GONE);
        this.recycler_view_hue_effect.setVisibility(View.GONE);
        this.recycler_view_burn_effect.setVisibility(View.VISIBLE);
        this.text_view_overlay.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_overlay.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hue.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hue.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_color.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_color.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_dodge.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_dodge.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_hardmix.setTextColor(getResources().getColor(R.color.text_color));
        this.text_view_hardmix.setBackgroundResource(R.drawable.background_unslelected);
        this.text_view_burn.setTextColor(getResources().getColor(R.color.white));
        this.text_view_burn.setBackgroundResource(R.drawable.background_selected);
    }

    public void setErasePaint() {
        this.relativeLayoutPaint.setVisibility(View.VISIBLE);
        this.recyclerViewPaintListColor.setVisibility(View.VISIBLE);
        this.polishEditor.brushEraser();
        this.seekBarEraser.setProgress(20);
        this.seekBarEraser.setVisibility(View.VISIBLE);
        this.seekBarOpacity.setVisibility(View.GONE);
        this.seekBarBrush.setVisibility(View.GONE);
        this.textViewValueEraser.setVisibility(View.VISIBLE);
        this.textViewValueBrush.setVisibility(View.GONE);
        this.textViewValueOpacity.setVisibility(View.GONE);
        this.textViewB.setImageResource(R.drawable.ic_eraser);
        this.textViewBrush.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewBrush.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewOpacity.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewOpacity.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewErase.setTextColor(getResources().getColor(R.color.white));
        this.textViewErase.setBackgroundResource(R.drawable.background_selected);
    }

    public void setColorPaint() {
        this.relativeLayoutPaint.setVisibility(View.GONE);
        this.recyclerViewPaintListColor.setVisibility(View.VISIBLE);
        this.recyclerViewPaintListColor.scrollToPosition(0);
        ColorAdapter colorAdapter2 = (ColorAdapter) this.recyclerViewPaintListColor.getAdapter();
        this.colorAdapter = colorAdapter2;
        if (colorAdapter2 != null) {
            colorAdapter2.setSelectedColorIndex(0);
        }
        ColorAdapter colorAdapter3 = this.colorAdapter;
        if (colorAdapter3 != null) {
            colorAdapter3.notifyDataSetChanged();
        }
        this.polishEditor.setBrushMode(1);
        this.polishEditor.setBrushDrawingMode(true);
        this.seekBarBrush.setProgress(20);
        this.seekBarEraser.setVisibility(View.GONE);
        this.seekBarOpacity.setVisibility(View.GONE);
        this.seekBarBrush.setVisibility(View.VISIBLE);
        this.textViewValueEraser.setVisibility(View.GONE);
        this.textViewValueBrush.setVisibility(View.VISIBLE);
        this.textViewValueOpacity.setVisibility(View.GONE);
        this.textViewB.setImageResource(R.drawable.ic_brush);
        this.textViewBrush.setTextColor(getResources().getColor(R.color.white));
        this.textViewBrush.setBackgroundResource(R.drawable.background_selected);
        this.textViewOpacity.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewOpacity.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewErase.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewErase.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setPaintOpacity() {
        this.relativeLayoutPaint.setVisibility(View.GONE);
        this.seekBarOpacity.setProgress(100);
        this.seekBarEraser.setVisibility(View.GONE);
        this.seekBarOpacity.setVisibility(View.VISIBLE);
        this.seekBarBrush.setVisibility(View.GONE);
        this.textViewValueEraser.setVisibility(View.GONE);
        this.textViewValueBrush.setVisibility(View.GONE);
        this.textViewValueOpacity.setVisibility(View.VISIBLE);
        this.textViewB.setImageResource(R.drawable.ic_opacity);
        this.textViewBrush.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewBrush.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewOpacity.setTextColor(getResources().getColor(R.color.white));
        this.textViewOpacity.setBackgroundResource(R.drawable.background_selected);
        this.textViewErase.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewErase.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setEraseNeon() {
        this.relativeLayoutNeon.setVisibility(View.VISIBLE);
        this.recyclerViewNeonListColor.setVisibility(View.VISIBLE);
        this.polishEditor.brushEraser();
        this.seekBarNeonEraser.setProgress(20);
        this.seekBarNeonEraser.setVisibility(View.VISIBLE);
        this.seekBarNeonBrush.setVisibility(View.GONE);
        this.textViewNeonEraser.setVisibility(View.VISIBLE);
        this.textViewNeonBrush.setVisibility(View.GONE);
        this.textViewSelectN.setImageResource(R.drawable.ic_eraser);
        this.textViewEraseN.setTextColor(getResources().getColor(R.color.white));
        this.textViewEraseN.setBackgroundResource(R.drawable.background_selected);
        this.textViewBrushN.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewBrushN.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setColorNeon() {
        this.relativeLayoutNeon.setVisibility(View.GONE);
        this.recyclerViewNeonListColor.setVisibility(View.VISIBLE);
        this.recyclerViewNeonListColor.scrollToPosition(0);
        ColorAdapter colorAdapter2 = (ColorAdapter) this.recyclerViewNeonListColor.getAdapter();
        this.colorAdapter = colorAdapter2;
        if (colorAdapter2 != null) {
            colorAdapter2.setSelectedColorIndex(0);
        }
        ColorAdapter colorAdapter3 = this.colorAdapter;
        if (colorAdapter3 != null) {
            colorAdapter3.notifyDataSetChanged();
        }
        this.polishEditor.setBrushMode(2);
        this.polishEditor.setBrushDrawingMode(true);
        this.seekBarNeonBrush.setProgress(20);
        this.seekBarNeonEraser.setVisibility(View.GONE);
        this.seekBarNeonBrush.setVisibility(View.VISIBLE);
        this.textViewNeonEraser.setVisibility(View.GONE);
        this.textViewNeonBrush.setVisibility(View.VISIBLE);
        this.textViewSelectN.setImageResource(R.drawable.ic_brush);
        this.textViewEraseN.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewEraseN.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewBrushN.setTextColor(getResources().getColor(R.color.white));
        this.textViewBrushN.setBackgroundResource(R.drawable.background_selected);
    }

    public void setEraseMagic() {
        this.relativeLayoutMagic.setVisibility(View.VISIBLE);
        this.recyclerViewMagicListColor.setVisibility(View.VISIBLE);
        this.polishEditor.brushEraser();
        this.seekBarMagicEraser.setProgress(20);
        this.seekBarMagicEraser.setVisibility(View.VISIBLE);
        this.seekBarMagicOpacity.setVisibility(View.GONE);
        this.seekBarMagicBrush.setVisibility(View.GONE);
        this.textViewMagicEraser.setVisibility(View.VISIBLE);
        this.textViewMagicBrush.setVisibility(View.GONE);
        this.textViewMagicOpacity.setVisibility(View.GONE);
        this.imageViewSelectMagic.setImageResource(R.drawable.ic_eraser);
        this.textViewBrushM.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewBrushM.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewOpacityM.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewOpacityM.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewEraseM.setTextColor(getResources().getColor(R.color.white));
        this.textViewEraseM.setBackgroundResource(R.drawable.background_selected);
    }

    public void setMagicBrush() {
        this.relativeLayoutMagic.setVisibility(View.GONE);
        this.recyclerViewMagicListColor.setVisibility(View.VISIBLE);
        this.recyclerViewMagicListColor.scrollToPosition(0);
        this.polishEditor.setBrushMagic(MagicBrushAdapter.lstDrawBitmapModel(getApplicationContext()).get(0));
        MagicBrushAdapter magicBrushAdapter = (MagicBrushAdapter) this.recyclerViewMagicListColor.getAdapter();
        if (magicBrushAdapter != null) {
            magicBrushAdapter.setSelectedColorIndex(0);
        }
        this.recyclerViewMagicListColor.scrollToPosition(0);
        if (magicBrushAdapter != null) {
            magicBrushAdapter.notifyDataSetChanged();
        }
        this.polishEditor.setBrushMode(3);
        this.polishEditor.setBrushDrawingMode(true);
        this.seekBarMagicBrush.setProgress(20);
        this.seekBarMagicEraser.setVisibility(View.GONE);
        this.seekBarMagicOpacity.setVisibility(View.GONE);
        this.seekBarMagicBrush.setVisibility(View.VISIBLE);
        this.textViewMagicEraser.setVisibility(View.GONE);
        this.textViewMagicBrush.setVisibility(View.VISIBLE);
        this.textViewMagicOpacity.setVisibility(View.GONE);
        this.imageViewSelectMagic.setImageResource(R.drawable.ic_brush);
        this.textViewBrushM.setTextColor(getResources().getColor(R.color.white));
        this.textViewBrushM.setBackgroundResource(R.drawable.background_selected);
        this.textViewOpacityM.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewOpacityM.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewEraseM.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewEraseM.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setMagicOpacity() {
        this.relativeLayoutMagic.setVisibility(View.GONE);
        this.polishEditor.setBrushMode(3);
        this.seekBarMagicOpacity.setProgress(100);
        this.polishEditor.setBrushDrawingMode(true);
        this.seekBarMagicEraser.setVisibility(View.GONE);
        this.seekBarMagicOpacity.setVisibility(View.VISIBLE);
        this.seekBarMagicBrush.setVisibility(View.GONE);
        this.textViewMagicEraser.setVisibility(View.GONE);
        this.textViewMagicBrush.setVisibility(View.GONE);
        this.textViewMagicOpacity.setVisibility(View.VISIBLE);
        this.imageViewSelectMagic.setImageResource(R.drawable.ic_opacity);
        this.textViewBrushM.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewBrushM.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewOpacityM.setTextColor(getResources().getColor(R.color.white));
        this.textViewOpacityM.setBackgroundResource(R.drawable.background_selected);
        this.textViewEraseM.setTextColor(getResources().getColor(R.color.text_color));
        this.textViewEraseM.setBackgroundResource(R.drawable.background_unslelected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUndo() {
        this.polishView.undo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRedo() {
        this.polishView.redo();
    }

    public void setGoneSave() {
        this.constraintLayoutSave.setVisibility(View.VISIBLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goneLayout() {
        setVisibleSave();
    }

    public void setVisibleSave() {
        this.constraintLayoutSave.setVisibility(View.VISIBLE);
    }

    @Override // com.example.photoareditor.Fragment.CropFragment.OnCropPhoto
    public void finishCrop(Bitmap bitmap) {
        this.polishView.setImageSource(bitmap);
        reloadingLayout();
    }

    @Override // com.example.photoareditor.Interface.OverlayListener
    public void onOverlaySelected(int i, String str) {
        this.polishEditor.setFilterEffect(str);
        this.seekBarOverlay.setProgress(50);
        this.polishView.getGLSurfaceView().setFilterIntensity(0.5f);
    }

    @Override // com.example.photoareditor.Interface.BrushColorListener
    public void onColorChanged(String str) {
        this.polishEditor.setBrushColor(Color.parseColor(str));
    }

    @Override // com.example.photoareditor.Interface.BrushMagicListener
    public void onMagicChanged(int i, DrawModel drawModel) {
        this.polishEditor.setBrushMagic(drawModel);
    }

    /* loaded from: classes7.dex */
    class effectOvarlay extends AsyncTask<Void, Void, Void> {
        effectOvarlay() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listOverlayEffect.clear();
            EditorActivity.this.listOverlayEffect.addAll(OverlayFileAsset.getListBitmapOverlayEffect(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recycler_view_overlay_effect;
            ArrayList arrayList = EditorActivity.this.listOverlayEffect;
            Log.e("aaaaa", "onPostExecute: " + arrayList.size());
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new OverlayAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(OverlayFileAsset.OVERLAY_EFFECTS)));
            EditorActivity.this.imageViewCompareOverlay.setVisibility(View.VISIBLE);
            EditorActivity.this.constraintLayoutSave.setVisibility(View.VISIBLE);
            EditorActivity.this.seekBarOverlay.setProgress(100);
            EditorActivity.this.showLoading(false);
            EditorActivity polishEditorActivity2 = EditorActivity.this;
            polishEditorActivity2.viewSlideDown(polishEditorActivity2.recyclerViewTools);
            EditorActivity polishEditorActivity3 = EditorActivity.this;
            polishEditorActivity3.viewSlideDown(polishEditorActivity3.linearLayoutRedo);
            EditorActivity polishEditorActivity4 = EditorActivity.this;
            polishEditorActivity4.viewSlideDown(polishEditorActivity4.constraintLayoutEffects);
            EditorActivity polishEditorActivity5 = EditorActivity.this;
            polishEditorActivity5.viewSlideUp(polishEditorActivity5.constraintLayoutOverlay);
            EditorActivity.this.setGuideLinePaint();
            EditorActivity.this.reloadingLayout();
        }
    }

    /* loaded from: classes7.dex */
    class effectDodge extends AsyncTask<Void, Void, Void> {
        effectDodge() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listDodgeEffect.clear();
            EditorActivity.this.listDodgeEffect.addAll(OverlayFileAsset.getListBitmapDodgeEffect(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recycler_view_dodge_effect;
            ArrayList arrayList = EditorActivity.this.listDodgeEffect;
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new OverlayAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(OverlayFileAsset.DODGE_EFFECTS)));
            EditorActivity.this.seekBarOverlay.setProgress(100);
        }
    }

    /* loaded from: classes7.dex */
    class effectColor extends AsyncTask<Void, Void, Void> {
        effectColor() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listColorEffect.clear();
            EditorActivity.this.listColorEffect.addAll(OverlayFileAsset.getListBitmapColorEffect(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recycler_view_color_effect;
            ArrayList arrayList = EditorActivity.this.listColorEffect;
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new OverlayAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(OverlayFileAsset.COLOR_EFFECTS)));
            EditorActivity.this.seekBarOverlay.setProgress(100);
        }
    }

    /* loaded from: classes7.dex */
    class effectHardmix extends AsyncTask<Void, Void, Void> {
        effectHardmix() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listHardMixEffect.clear();
            EditorActivity.this.listHardMixEffect.addAll(OverlayFileAsset.getListBitmapHardmixEffect(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recycler_view_hardmix_effet;
            ArrayList arrayList = EditorActivity.this.listHardMixEffect;
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new OverlayAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(OverlayFileAsset.HARDMIX_EFFECTS)));
            EditorActivity.this.seekBarOverlay.setProgress(100);
        }
    }

    /* loaded from: classes7.dex */
    class effectDivide extends AsyncTask<Void, Void, Void> {
        effectDivide() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listHueEffect.clear();
            EditorActivity.this.listHueEffect.addAll(OverlayFileAsset.getListBitmapHueEffect(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recycler_view_hue_effect;
            ArrayList arrayList = EditorActivity.this.listHueEffect;
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new OverlayAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(OverlayFileAsset.HUE_EFFECTS)));
            EditorActivity.this.seekBarOverlay.setProgress(100);
        }
    }

    /* loaded from: classes7.dex */
    class effectBurn extends AsyncTask<Void, Void, Void> {
        effectBurn() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listBurnEffect.clear();
            EditorActivity.this.listBurnEffect.addAll(OverlayFileAsset.getListBitmapBurnEffect(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recycler_view_burn_effect;
            ArrayList arrayList = EditorActivity.this.listBurnEffect;
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new OverlayAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(OverlayFileAsset.BURN_EFFECTS)));
            EditorActivity.this.seekBarOverlay.setProgress(100);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewCloseAdjust /* 2131362231 */:
                this.polishEditor.setFilterEffect("");
                setGuideLine();
                setVisibleSave();
                reloadingLayout();
                viewSlideDown(this.constraintLayoutAdjust);
                viewSlideUp(this.linearLayoutRedo);
                return;
            case R.id.imageViewCloseFilter /* 2131362235 */:
                this.polishEditor.setFilterEffect("");
                setGuideLine();
                setVisibleSave();
                reloadingLayout();
                viewSlideUp(this.bottom_tool);
                viewSlideUp(this.linearLayoutRedo);
                viewSlideDown(this.imageViewCompareFilter);
                viewSlideDown(this.constraintLayoutFilter);
                return;
            case R.id.imageViewCloseMagic /* 2131362237 */:
            case R.id.imageViewCloseNeon /* 2131362240 */:
            case R.id.imageViewClosePaint /* 2131362242 */:
                setVisibleSave();
                reloadingLayout();
                viewSlideUp(this.bottom_tool);
                viewSlideDown(this.imageViewCompareFilter);
                viewSlideDown(this.constraintLayoutFilter);
                return;
            case R.id.imageViewCloseOverlay /* 2131362241 */:
                this.polishEditor.setFilterEffect("");
                setGuideLine();
//                set Guide
                setVisibleSave();
                reloadingLayout();
                viewSlideDown(this.constraintLayoutOverlay);
                viewSlideUp(this.linearLayoutRedo);
                return;
            case R.id.imageViewSaveAdjust /* 2131362260 */:
                new SaveFilter().execute(new Void[0]);
                this.constraintLayoutAdjust.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSave.setVisibility(View.VISIBLE);
                viewSlideUp(this.linearLayoutRedo);
                setGuideLine();
                setVisibleSave();
                reloadingLayout();
                viewSlideUp(this.bottom_tool);
                viewSlideDown(this.constraintLayoutAdjust);
                return;
            case R.id.imageViewSaveFilter /* 2131362264 */:
                new SaveFilter().execute(new Void[0]);
                this.imageViewCompareFilter.setVisibility(View.GONE);
                viewSlideUp(this.linearLayoutRedo);
                setGuideLine();
                setVisibleSave();
                reloadingLayout();
                viewSlideUp(this.bottom_tool);
                viewSlideDown(this.constraintLayoutFilter);
                return;
            case R.id.imageViewSaveMagic /* 2131362266 */:
                showLoading(true);
                runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.EditorActivity.50
                    @Override // java.lang.Runnable
                    public void run() {
                        EditorActivity.this.imageViewUndoMagic.setVisibility(View.GONE);
                        EditorActivity.this.imageViewRedoMagic.setVisibility(View.GONE);
                        EditorActivity.this.imageViewCleanMagic.setVisibility(View.GONE);
                        EditorActivity editorActivity = EditorActivity.this;
                        editorActivity.viewSlideUp(editorActivity.recyclerViewTools);
                        EditorActivity editorActivity2 = EditorActivity.this;
                        editorActivity2.viewSlideUp(editorActivity2.linearLayoutRedo);
                        EditorActivity editorActivity3 = EditorActivity.this;
                        editorActivity3.viewSlideDown(editorActivity3.constraintLayoutMagic);
                        EditorActivity.this.constraintLayoutMagicTool.setVisibility(View.GONE);
                        EditorActivity.this.constraintLayoutSave.setVisibility(View.VISIBLE);
                        EditorActivity.this.setGuideLine();
                        EditorActivity.this.polishView.setImageSource(EditorActivity.this.polishEditor.getBrushDrawingView().getDrawBitmap(EditorActivity.this.polishView.getCurrentBitmap()));
                        EditorActivity.this.polishEditor.clearBrushAllViews();
                        EditorActivity.this.showLoading(false);
                        EditorActivity.this.reloadingLayout();
                    }
                });
                setVisibleSave();
                return;
            case R.id.imageViewSaveNeon /* 2131362269 */:
                showLoading(true);
                runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.EditorActivity.52
                    @Override // java.lang.Runnable
                    public void run() {
                        EditorActivity.this.polishEditor.setBrushDrawingMode(false);
                        EditorActivity.this.imageViewUndoNeon.setVisibility(View.GONE);
                        EditorActivity.this.imageViewRedoNeon.setVisibility(View.GONE);
                        EditorActivity.this.constraintLayoutNeonTool.setVisibility(View.GONE);
                        EditorActivity.this.constraintLayoutSave.setVisibility(View.VISIBLE);
                        EditorActivity editorActivity = EditorActivity.this;
                        editorActivity.viewSlideUp(editorActivity.recyclerViewTools);
                        EditorActivity editorActivity2 = EditorActivity.this;
                        editorActivity2.viewSlideUp(editorActivity2.linearLayoutRedo);
                        EditorActivity editorActivity3 = EditorActivity.this;
                        editorActivity3.viewSlideDown(editorActivity3.constraintLayoutNeon);
                        EditorActivity.this.setGuideLine();
                        EditorActivity.this.polishView.setImageSource(EditorActivity.this.polishEditor.getBrushDrawingView().getDrawBitmap(EditorActivity.this.polishView.getCurrentBitmap()));
                        EditorActivity.this.polishEditor.clearBrushAllViews();
                        EditorActivity.this.showLoading(false);
                        EditorActivity.this.reloadingLayout();
                    }
                });
                setVisibleSave();
                return;
            case R.id.imageViewSaveOverlay /* 2131362270 */:
                new SaveFilter().execute(new Void[0]);
                this.imageViewCompareOverlay.setVisibility(View.GONE);
                this.constraintLayoutSaveOverlay.setVisibility(View.GONE);
                this.seekBarOverlay.setVisibility(View.GONE);
                viewSlideUp(this.linearLayoutRedo);
                viewSlideUp(this.bottom_tool);
                viewSlideDown(this.constraintLayoutOverlay);
                setGuideLine();
                setVisibleSave();
                reloadingLayout();
                return;
            case R.id.imageViewSavePaint /* 2131362271 */:
                showLoading(true);
                runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.EditorActivity.51
                    @Override // java.lang.Runnable
                    public void run() {
                        EditorActivity.this.polishEditor.setBrushDrawingMode(false);
                        EditorActivity.this.imageViewUndoPaint.setVisibility(View.GONE);
                        EditorActivity.this.imageViewRedoPaint.setVisibility(View.GONE);
                        EditorActivity.this.imageViewCleanPaint.setVisibility(View.GONE);
                        EditorActivity.this.constraintLayoutPaintTool.setVisibility(View.GONE);
                        EditorActivity.this.constraintLayoutSave.setVisibility(View.VISIBLE);
                        EditorActivity editorActivity = EditorActivity.this;
                        editorActivity.viewSlideUp(editorActivity.recyclerViewTools);
                        EditorActivity editorActivity2 = EditorActivity.this;
                        editorActivity2.viewSlideUp(editorActivity2.linearLayoutRedo);
                        EditorActivity editorActivity3 = EditorActivity.this;
                        editorActivity3.viewSlideDown(editorActivity3.constraintLayoutPaint);
                        EditorActivity.this.setGuideLine();
                        EditorActivity.this.polishView.setImageSource(EditorActivity.this.polishEditor.getBrushDrawingView().getDrawBitmap(EditorActivity.this.polishView.getCurrentBitmap()));
                        EditorActivity.this.polishEditor.clearBrushAllViews();
                        EditorActivity.this.showLoading(false);
                        EditorActivity.this.reloadingLayout();
                    }
                });
                setVisibleSave();
                return;
            default:
                return;
        }
    }

    public void setGuideLine() {
        ConstraintSet constraintSet = new ConstraintSet();
        try {
            constraintSet.clone(this.constraintLayoutView);
            constraintSet.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
            constraintSet.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
            constraintSet.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
            constraintSet.applyTo(this.constraintLayoutView);
        } catch (Exception e) {
        }
    }

    public void viewSlideUp(View view) {
        view.setVisibility(View.VISIBLE);
        Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        this.slideUpAnimation = loadAnimation;
        view.startAnimation(loadAnimation);
        this.slideUpAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.example.photoareditor.Activity.EditorActivity.53
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    public void viewSlideDown(View view) {
        view.setVisibility(View.GONE);
        view.startAnimation(this.slideDownAnimation);
        this.slideDownAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.example.photoareditor.Activity.EditorActivity.54
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    @Override // com.example.photoareditor.Interface.OnPolishEditorListener
    public void onAddViewListener(Drawing drawing, int i) {
    }

    @Override // com.example.photoareditor.Interface.OnPolishEditorListener
    public void onRemoveViewListener(int i) {
    }

    @Override // com.example.photoareditor.Interface.OnPolishEditorListener
    public void onRemoveViewListener(Drawing drawing, int i) {
    }

    @Override // com.example.photoareditor.Interface.OnPolishEditorListener
    public void onStartViewChangeListener(Drawing drawing) {
    }

    @Override // com.example.photoareditor.Interface.OnPolishEditorListener
    public void onStopViewChangeListener(Drawing drawing) {
    }

    @Override // com.example.photoareditor.Fragment.FrameFragment.RatioSaveListener
    public void ratioSavedBitmap(Bitmap bitmap) {
        this.polishView.setImageSource(bitmap);
        reloadingLayout();
    }

    /* loaded from: classes7.dex */
    class SaveFilter extends AsyncTask<Void, Void, Bitmap> {
        SaveFilter() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            final Bitmap[] bitmapArr = {null};
            EditorActivity.this.polishView.saveGLSurfaceViewAsBitmap(new OnSaveBitmap() { // from class: com.example.photoareditor.Activity.EditorActivity.SaveFilter.1
                @Override // com.example.photoareditor.Interface.OnSaveBitmap
                public void onBitmapReady(Bitmap bitmap) {
                    bitmapArr[0] = bitmap;
                }
            });
            while (bitmapArr[0] == null) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bitmapArr[0];
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            EditorActivity.this.polishView.setImageSource(bitmap);
            EditorActivity.this.polishView.setFilterEffect("");
            EditorActivity.this.showLoading(false);
        }
    }

    /* loaded from: classes7.dex */
    class openMirrorFragment extends AsyncTask<Void, Bitmap, Bitmap> {
        openMirrorFragment() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            return FilterUtils.getBlurImageFromBitmap(EditorActivity.this.polishView.getCurrentBitmap(), 5.0f);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            EditorActivity.this.showLoading(false);
            EditorActivity polishEditorActivity = EditorActivity.this;
            MirrorFragment.show(polishEditorActivity, polishEditorActivity, polishEditorActivity.polishView.getCurrentBitmap(), bitmap);
        }
    }

    /* loaded from: classes7.dex */
    public class loadBitmapUri extends AsyncTask<String, Bitmap, Bitmap> {
        loadBitmapUri() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditorActivity.this.getContentResolver(), fromFile);
                float width = bitmap.getWidth();
                float height = bitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                if (max > 1.0f) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false);
                }
                Bitmap rotateBitmap = SystemUtil.rotateBitmap(bitmap, new ExifInterface(EditorActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
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
            EditorActivity.this.polishView.setImageSource(bitmap);
            EditorActivity.this.reloadingLayout();
        }
    }

    /* loaded from: classes7.dex */
    class openFilters extends AsyncTask<Void, Void, Void> {
        openFilters() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            EditorActivity.this.listFilter.clear();
            EditorActivity.this.listFilter.addAll(FilterFileAsset.getListBitmapFilter(ThumbnailUtils.extractThumbnail(EditorActivity.this.polishView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "allFilters " + EditorActivity.this.listFilter.size());
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = EditorActivity.this.recyclerViewFilter;
            ArrayList arrayList = EditorActivity.this.listFilter;
            EditorActivity polishEditorActivity = EditorActivity.this;
            recyclerView.setAdapter(new FilterAdapter(arrayList, polishEditorActivity, polishEditorActivity.getApplicationContext(), Arrays.asList(FilterFileAsset.FILTERS)));
            EditorActivity.this.constraintLayoutSave.setVisibility(View.VISIBLE);
            EditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            EditorActivity.this.adjustFilter.setProgress(50);
            EditorActivity.this.showLoading(false);
            EditorActivity editorActivity = EditorActivity.this;
            editorActivity.viewSlideDown(editorActivity.linearLayoutRedo);
            EditorActivity editorActivity2 = EditorActivity.this;
            editorActivity2.viewSlideDown(editorActivity2.recyclerViewTools);
            EditorActivity editorActivity3 = EditorActivity.this;
            editorActivity3.viewSlideUp(editorActivity3.constraintLayoutFilter);
            EditorActivity editorActivity4 = EditorActivity.this;
            editorActivity4.viewSlideUp(editorActivity4.imageViewCompareFilter);
            EditorActivity.this.setGuideLinePaint();
            EditorActivity.this.reloadingLayout();
        }
    }

    /* loaded from: classes7.dex */
    class openFrameFragment extends AsyncTask<Void, Bitmap, Bitmap> {
        openFrameFragment() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            EditorActivity.this.showLoading(true);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            return FilterUtils.getBlurImageFromBitmap(EditorActivity.this.polishView.getCurrentBitmap(), 5.0f);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            EditorActivity.this.showLoading(false);
            EditorActivity polishEditorActivity = EditorActivity.this;
            FrameFragment.show(polishEditorActivity, polishEditorActivity, polishEditorActivity.polishView.getCurrentBitmap(), bitmap);
        }
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
            EditorActivity.this.showLoading(true);
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
            EditorActivity.this.polishView.setImageSource(bitmap);
            EditorActivity.this.reloadingLayout();
        }
    }

    public void reloadingLayout() {
        this.polishView.postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.EditorActivity.55
            @Override // java.lang.Runnable
            public void run() {
                EditorActivity.this.lambda$reloadingLayout$28$PolishEditorActivity();
            }
        }, 300L);
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

    public void lambda$reloadingLayout$28$PolishEditorActivity() {
        try {
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            int i = point.x;
            int height = this.relativeLayoutWrapper.getHeight();
            int i2 =100;
            float f =2.0f;
//            int i2 = this.polishView.getGLSurfaceView().getRenderViewport().width;
//            float f = this.polishView.getGLSurfaceView().getRenderViewport().height;
            float f2 = i2;
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

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        this.listFilter.clear();
        this.listOverlayEffect.clear();
        this.imageViewUndoMagic.setVisibility(View.GONE);
        this.imageViewCleanMagic.setVisibility(View.GONE);
        this.imageViewRedoMagic.setVisibility(View.GONE);
        this.imageViewUndoPaint.setVisibility(View.GONE);
        this.imageViewCompareOverlay.setVisibility(View.GONE);
        this.constraintLayoutPaintTool.setVisibility(View.GONE);
        this.constraintLayoutMagicTool.setVisibility(View.GONE);
        if (this.recyclerViewFilter.getAdapter() != null) {
            this.recyclerViewFilter.getAdapter().notifyDataSetChanged();
        }
        if (this.recycler_view_overlay_effect.getAdapter() != null) {
            this.recycler_view_overlay_effect.getAdapter().notifyDataSetChanged();
        }
    }

    public void setGuideLinePaint() {
        ConstraintSet constraintSet = new ConstraintSet();
        try {
            constraintSet.clone(this.constraintLayoutView);
            constraintSet.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
            constraintSet.connect(this.relativeLayoutWrapper.getId(), 4, this.guidelinePaint.getId(), 3, 0);
            constraintSet.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
            constraintSet.applyTo(this.constraintLayoutView);
        } catch (Exception e) {
        }
    }

    @Override // com.example.photoareditor.Interface.FilterListener
    public void onFilterSelected(int i, String str) {
        this.polishEditor.setFilterEffect(str);
        this.adjustFilter.setProgress(50);
        this.polishView.getGLSurfaceView().setFilterIntensity(0.5f);
    }

    @Override // com.example.photoareditor.Interface.AdjustListener
    public void onAdjustSelected(AdjustAdapter.AdjustModel adjustModel) {
        this.adjustSeekBar.setCurrentDegrees(((int) ((adjustModel.originValue - adjustModel.minValue) / ((adjustModel.maxValue - ((adjustModel.maxValue + adjustModel.minValue) / 2.0f)) / 50.0f))) - 50);
    }

    public boolean lambda$new$24$PolishEditorActivity(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.polishView.getGLSurfaceView().setAlpha(0.0f);
            return true;
        } else if (action != 1) {
            return true;
        } else {
            this.polishView.getGLSurfaceView().setAlpha(1.0f);
            return false;
        }
    }

    public void textFragment() {
        this.textFragment = TextFragment.show(this);
        TextFragment.TextEditor r0 = new TextFragment.TextEditor() { // from class: com.example.photoareditor.Activity.EditorActivity.56
            @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
            public void onDone(PolishText polishText) {
                EditorActivity.this.polishView.addSticker(new PolishTextView(EditorActivity.this.getApplicationContext(), polishText));
            }

            @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
            public void onBackButton() {
                if (EditorActivity.this.polishView.getStickers().isEmpty()) {
                    EditorActivity.this.onBackPressed();
                }
            }
        };
        this.textEditor = r0;
        this.textFragment.setOnTextEditorListener(r0);
    }

    public void getStickerData() {
        this.stickerData = new ArrayList<>();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(1, Utils.Url, new Response.Listener<String>() { // from class: com.example.photoareditor.Activity.EditorActivity.57
            @Override // com.android.volley.Response.Listener
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray optJSONArray = obj.getJSONArray("data");
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject jsonObject = optJSONArray.getJSONObject(i);
                        Stickermodel.Datum dataList = new Stickermodel.Datum();
                        dataList.catid = jsonObject.getString("cat_id");
                        dataList.catthumb = Utils.Catthumb + jsonObject.getString("cat_thumb");
                        dataList.stickers = new ArrayList();
                        JSONArray arraylist = jsonObject.getJSONArray("stickers");
                        for (int j = 0; j < arraylist.length(); j++) {
                            stickerlistModel sublist = new stickerlistModel();
                            JSONObject jsonObjectsub = arraylist.getJSONObject(j);
                            sublist.sticker_id = jsonObjectsub.getString("sticker_id");
                            sublist.sticker_thumb = Utils.Sticker_thumb + jsonObjectsub.getString("sticker_thumb");
                            dataList.stickers.add(sublist);
                        }
                        EditorActivity.this.stickerData.add(dataList);
                    }
                    EditorActivity.this.setRectViews();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.example.photoareditor.Activity.EditorActivity.58
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditorActivity.this, "Please Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
                EditorActivity.this.getStickerData1();
            }
        }) { // from class: com.example.photoareditor.Activity.EditorActivity.59
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("appkey", "dj420dv134hk4273");
                MyData.put("ver", "7.5");
                MyData.put("os", "android");
                MyData.put("device", "redmi");
                MyData.put("category", "all");
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getStickerData1() {
        this.stickerData = new ArrayList<>();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(1, Utils.Url, new Response.Listener<String>() { // from class: com.example.photoareditor.Activity.EditorActivity.60
            @Override // com.android.volley.Response.Listener
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray optJSONArray = obj.getJSONArray("data");
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject jsonObject = optJSONArray.getJSONObject(i);
                        Stickermodel.Datum dataList = new Stickermodel.Datum();
                        dataList.catid = jsonObject.getString("cat_id");
                        dataList.catthumb = Utils.Catthumb + jsonObject.getString("cat_thumb");
                        dataList.stickers = new ArrayList();
                        JSONArray arraylist = jsonObject.getJSONArray("stickers");
                        for (int j = 0; j < arraylist.length(); j++) {
                            stickerlistModel sublist = new stickerlistModel();
                            JSONObject jsonObjectsub = arraylist.getJSONObject(j);
                            sublist.sticker_id = jsonObjectsub.getString("sticker_id");
                            sublist.sticker_thumb = Utils.Sticker_thumb + jsonObjectsub.getString("sticker_thumb");
                            dataList.stickers.add(sublist);
                        }
                        EditorActivity.this.stickerData.add(dataList);
                    }
                    EditorActivity.this.setRectViews();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.example.photoareditor.Activity.EditorActivity.61
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditorActivity.this, "Please Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
            }
        }) { // from class: com.example.photoareditor.Activity.EditorActivity.62
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("appkey", "dj420dv134hk4273");
                MyData.put("ver", "7.5");
                MyData.put("os", "android");
                MyData.put("device", "redmi");
                MyData.put("category", "all");
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.keyboardProvider.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.keyboardProvider.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRectViews() {
        sticker_viewpaper = (RecyclerView) findViewById(R.id.sticker_viewpaper);
        recycler_tab_layout = (RecyclerView) findViewById(R.id.recycler_tab_layout);
        sticker_viewpaper.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        Log.e("aaaa", "setRectViews: " + this.stickerData.size());
        Sticker_Category_Adapter sticker_category_adapter = new Sticker_Category_Adapter(this, this.stickerData);
        sticker_viewpaper.setAdapter(sticker_category_adapter);
    }
}
