package com.example.photoeditor.Activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.photoeditor.Adapter.CollageBackgroundAdapter;
import com.example.photoeditor.Adapter.CollageColorAdapter;
import com.example.photoeditor.Adapter.FilterAdapter;
import com.example.photoeditor.Adapter.GridAdapter;
import com.example.photoeditor.Adapter.GridItemToolsAdapter;
import com.example.photoeditor.Adapter.GridToolsAdapter;
import com.example.photoeditor.Adapter.StickerAdapter;
import com.example.photoeditor.Adapter.StickersTabAdapter;
import com.example.photoeditor.Classs.AlignHorizontallyEvent;
import com.example.photoeditor.Classs.CollageUtils;
import com.example.photoeditor.Classs.DeleteIconEvent;
import com.example.photoeditor.Classs.DrawableSticker;
import com.example.photoeditor.Classs.EditTextIconEvent;
import com.example.photoeditor.Classs.FilterFileAsset;
import com.example.photoeditor.Classs.FilterUtils;
import com.example.photoeditor.Classs.FlipHorizontallyEvent;
import com.example.photoeditor.Classs.Module;
import com.example.photoeditor.Classs.PolishGrid;
import com.example.photoeditor.Classs.PolishGridView;
import com.example.photoeditor.Classs.PolishLayoutParser;
import com.example.photoeditor.Classs.PolishStickerIcons;
import com.example.photoeditor.Classs.PolishStickerView;
import com.example.photoeditor.Classs.PolishText;
import com.example.photoeditor.Classs.PolishTextView;
import com.example.photoeditor.Classs.Preference;
import com.example.photoeditor.Classs.RecyclerTabLayout;
import com.example.photoeditor.Classs.SaveFileUtils;
import com.example.photoeditor.Classs.Sticker;
import com.example.photoeditor.Classs.StickerFileAsset;
import com.example.photoeditor.Classs.SystemUtil;
import com.example.photoeditor.Classs.TextFragment;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Classs.ZoomIconEvent;
import com.example.photoeditor.CollageClass.AspectAdapter;
import com.example.photoeditor.CollageClass.PermissionsUtils;
import com.example.photoeditor.Fragment.CropFragment;
import com.example.photoeditor.Fragment.FilterFragment;
import com.example.photoeditor.Interface.FilterListener;
import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.R;
import com.hold1.keyboardheightprovider.KeyboardHeightProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.steelkiwi.cropiwa.AspectRatio;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.wysaid.nativePort.CGENativeLibrary;

public class PolishCollageActivity extends PolishBaseActivity implements GridToolsAdapter.OnItemSelected, AspectAdapter.OnNewSelectedListener, StickerAdapter.OnClickSplashListener, CollageBackgroundAdapter.BackgroundGridListener, CropFragment.OnCropPhoto, CollageColorAdapter.BackgroundColorListener, FilterFragment.OnFilterSavePhoto, GridItemToolsAdapter.OnPieceFuncItemSelected, GridAdapter.OnItemClickListener, FilterListener {
    public static PolishCollageActivity QueShotGridActivityCollage;
    private static PolishCollageActivity QueShotGridActivityInstance;
    public float BorderRadius;
    public float Padding;
    public TextFragment addTextFragment;
    public AspectRatio aspectRatio;
    private ConstraintLayout constraintLayoutAddText;
    private ConstraintLayout constraintLayoutSaveSticker;
    private ConstraintLayout constraintLayoutSaveText;
    public ConstraintLayout constraint_layout_change_background;
    private ConstraintLayout constraint_layout_collage_layout;
    public ConstraintLayout constraint_layout_filter_layout;
    public ConstraintLayout constraint_layout_sticker;
    private ConstraintLayout constraint_layout_wrapper_collage_view;
    private ConstraintLayout constraint_save_control;
    public ConstraintLayout constrant_layout_change_Layout;
    public CollageBackgroundAdapter.SquareView currentBackgroundState;
    private int deviceHeight = 0;
    public int deviceWidth = 0;
    public List<Drawable> drawableList = new ArrayList();
    private File file;
    private GridItemToolsAdapter gridItemToolsAdapter = new GridItemToolsAdapter(this);
    public GridToolsAdapter gridToolsAdapter = new GridToolsAdapter(this, true);
    private Guideline guideline;
    private Guideline guidelineTools;
    public ImageView imageViewAddSticker;
    private KeyboardHeightProvider keyboardHeightProvider;
    private LinearLayout linearLayoutBorder;
    public LinearLayout linear_layout_wrapper_sticker_list;
    public ArrayList listFilterAll = new ArrayList();
    public CGENativeLibrary.LoadImageCallback loadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass1 */

        @Override // org.wysaid.nativePort.CGENativeLibrary.LoadImageCallback
        public Bitmap loadImage(String str, Object obj) {
            try {
                return BitmapFactory.decodeStream(PolishCollageActivity.this.getAssets().open(str));
            } catch (IOException e) {
                return null;
            }
        }

        @Override // org.wysaid.nativePort.CGENativeLibrary.LoadImageCallback
        public void loadImageOK(Bitmap bitmap, Object obj) {
            bitmap.recycle();
        }
    };
    public Module moduleToolsId;
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass2 */

        public void onClick(View view) {
            PolishCollageActivity.this.lambda$new$17$PolishCollageActivity(view);
        }
    };
    public SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass3 */

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            int id = seekBar.getId();
            if (id == R.id.seekbar_border) {
                PolishCollageActivity.this.queShotGridView.setCollagePadding((float) i);
                PolishCollageActivity.this.textViewSeekBarPadding.setText(String.valueOf(i));
            } else if (id == R.id.seekbar_radius) {
                PolishCollageActivity.this.queShotGridView.setCollageRadian((float) i);
                PolishCollageActivity.this.textViewSeekBarRadius.setText(String.valueOf(i));
            }
            PolishCollageActivity.this.queShotGridView.invalidate();
        }
    };
    PolishStickerView.OnStickerOperationListener onStickerOperationListener = new PolishStickerView.OnStickerOperationListener() {
        /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass4 */

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
            PolishCollageActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
            PolishCollageActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
        }

        @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
        public void onStickerSelected(Sticker sticker) {
            PolishCollageActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
            PolishCollageActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
        }

        @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
        public void onStickerDeleted(Sticker sticker) {
            PolishCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
        }

        @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
        public void onStickerTouchOutside() {
            PolishCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
        }

        @Override // com.example.photoareditor.Classs.PolishStickerView.OnStickerOperationListener
        public void onStickerDoubleTap(Sticker sticker) {
            if (sticker instanceof PolishTextView) {
                sticker.setShow(false);
                PolishCollageActivity.this.queShotGridView.setHandlingSticker(null);
                PolishCollageActivity polishCollageActivity = PolishCollageActivity.this;
                polishCollageActivity.addTextFragment = TextFragment.show(polishCollageActivity, ((PolishTextView) sticker).getPolishText());
                PolishCollageActivity.this.textEditor = new TextFragment.TextEditor() {
                    /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass4.AnonymousClass1 */

                    @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
                    public void onDone(PolishText polishText) {
                        PolishCollageActivity.this.queShotGridView.getStickers().remove(PolishCollageActivity.this.queShotGridView.getLastHandlingSticker());
                        PolishCollageActivity.this.queShotGridView.addSticker(new PolishTextView(PolishCollageActivity.this, polishText));
                    }

                    @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
                    public void onBackButton() {
                        PolishCollageActivity.this.queShotGridView.showLastHandlingSticker();
                    }
                };
                PolishCollageActivity.this.addTextFragment.setOnTextEditorListener(PolishCollageActivity.this.textEditor);
            }
        }
    };
    ActivityResultLauncher<Intent> paymentResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass5 */

        public void onActivityResult(ActivityResult result) {
            PolishCollageActivity.this.lambda$new$16$PolishCollageActivity(result);
        }
    });
    public PolishGridView queShotGridView;
    public PolishLayout queShotLayout;
    public RecyclerView recyclerViewFilter;
    public RecyclerView recyclerViewTools;
    public RecyclerView recyclerViewToolsCollage;
    private RecyclerView recycler_view_blur;
    private RecyclerView recycler_view_collage;
    private RecyclerView recycler_view_color;
    private RecyclerView recycler_view_gradient;
    private RecyclerView recycler_view_ratio;
    private RelativeLayout relativeLayoutAddText;
    private RelativeLayout relativeLayoutLoading;
    private SeekBar seekBarPadding;
    private SeekBar seekBarRadius;
    public SeekBar seekbarSticker;
    private Animation slideDownAnimation;
    private Animation slideUpAnimation;
    public List<String> stringList;
    public List<Target> targets = new ArrayList();
    public TextFragment.TextEditor textEditor;
    public TextView textViewCancel;
    public TextView textViewDiscard;
    private TextView textViewListBlur;
    private TextView textViewListBorder;
    private TextView textViewListColor;
    private TextView textViewListGradient;
    private TextView textViewListLayer;
    private TextView textViewListRatio;
    private TextView textViewSeekBarPadding;
    private TextView textViewSeekBarRadius;
    private TextView textViewTitle;
    private TextView text_view_save;

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_polish_collage);
        if (Build.VERSION.SDK_INT < 30) {
            getWindow().setSoftInputMode(72);
        }
        this.deviceWidth = getResources().getDisplayMetrics().widthPixels;
        this.deviceHeight = getResources().getDisplayMetrics().heightPixels;
        findViewById(R.id.image_view_exit).setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass6 */

            public void onClick(View view) {
                PolishCollageActivity.this.onBackPressed();
            }
        });
        this.queShotGridView = (PolishGridView) findViewById(R.id.collage_view);
        this.constraintLayoutSaveText = (ConstraintLayout) findViewById(R.id.constraint_layout_confirm_save_text);
        this.constraintLayoutSaveSticker = (ConstraintLayout) findViewById(R.id.constraint_layout_confirm_save_sticker);
        this.constraint_layout_wrapper_collage_view = (ConstraintLayout) findViewById(R.id.constraint_layout_wrapper_collage_view);
        this.constraint_layout_filter_layout = (ConstraintLayout) findViewById(R.id.constraint_layout_filter_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tools);
        this.recyclerViewTools = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewTools.setAdapter(this.gridToolsAdapter);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_tools_collage);
        this.recyclerViewToolsCollage = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewToolsCollage.setAdapter(this.gridItemToolsAdapter);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_border);
        this.seekBarPadding = seekBar;
        seekBar.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekbar_radius);
        this.seekBarRadius = seekBar2;
        seekBar2.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.stringList = getIntent().getStringArrayListExtra(GridPickerActivity.KEY_DATA_RESULT);
        this.relativeLayoutLoading = (RelativeLayout) findViewById(R.id.relative_layout_loading);
        this.recyclerViewFilter = (RecyclerView) findViewById(R.id.recycler_view_filter);
        this.linearLayoutBorder = (LinearLayout) findViewById(R.id.linearLayoutPadding);
        this.guidelineTools = (Guideline) findViewById(R.id.guidelineTools);
        this.guideline = (Guideline) findViewById(R.id.guideline);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_add_text);
        this.relativeLayoutAddText = relativeLayout;
        relativeLayout.setVisibility(View.GONE);
        this.constraintLayoutAddText = (ConstraintLayout) findViewById(R.id.constraint_layout_confirm_text);
        PolishLayout polishLayout = CollageUtils.getCollageLayouts(this.stringList.size()).get(0);
        this.queShotLayout = polishLayout;
        this.queShotGridView.setQueShotLayout(polishLayout);
        this.queShotGridView.setTouchEnable(true);
        this.queShotGridView.setNeedDrawLine(false);
        this.queShotGridView.setNeedDrawOuterLine(false);
        this.queShotGridView.setLineSize(4);
        this.queShotGridView.setCollagePadding(6.0f);
        this.queShotGridView.setCollageRadian(15.0f);
        this.queShotGridView.setLineColor(ContextCompat.getColor(this, R.color.white));
        this.queShotGridView.setSelectedLineColor(ContextCompat.getColor(this, R.color.mainColor));
        this.queShotGridView.setHandleBarColor(ContextCompat.getColor(this, R.color.mainColor));
        this.queShotGridView.setAnimateDuration(HttpStatus.SC_MULTIPLE_CHOICES);
        this.queShotGridView.setOnQueShotSelectedListener(new PolishGridView.onQueShotSelectedListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass7 */

            @Override // com.example.photoareditor.Classs.PolishGridView.onQueShotSelectedListener
            public void onQuShotSelected(PolishGrid polishGrid, int i) {
                PolishCollageActivity.this.lambda$onCreate$1$PolishCollageActivity(polishGrid, i);
            }
        });
        this.queShotGridView.setOnQueShotUnSelectedListener(new PolishGridView.onQueShotUnSelectedListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass8 */

            @Override // com.example.photoareditor.Classs.PolishGridView.onQueShotUnSelectedListener
            public void onQuShotUnSelected() {
                PolishCollageActivity.this.lambda$onCreate$2$PolishCollageActivity();
            }
        });
        this.constraint_save_control = (ConstraintLayout) findViewById(R.id.constraint_save_control);
        this.queShotGridView.post(new Runnable() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass9 */

            public void run() {
                PolishCollageActivity.this.lambda$onCreate$3$PolishCollageActivity();
            }
        });
        findViewById(R.id.imageViewSaveLayer).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewCloseLayer).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewSaveText).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewCloseText).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewClosebackground).setOnClickListener(this.onClickListener);
        findViewById(R.id.image_view_close_sticker).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewSaveFilter).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewSavebackground).setOnClickListener(this.onClickListener);
        findViewById(R.id.image_view_save_sticker).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewCloseFilter).setOnClickListener(this.onClickListener);
        this.textViewListLayer = (TextView) findViewById(R.id.text_view_collage);
        this.textViewListBorder = (TextView) findViewById(R.id.text_view_border);
        this.textViewListRatio = (TextView) findViewById(R.id.text_view_ratio);
        this.textViewListLayer.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass10 */

            public void onClick(View view) {
                PolishCollageActivity.this.setLayer();
            }
        });
        this.textViewListBorder.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass11 */

            public void onClick(View view) {
                PolishCollageActivity.this.setBorder();
            }
        });
        this.textViewListRatio.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass12 */

            public void onClick(View view) {
                PolishCollageActivity.this.setRatio();
            }
        });
        this.textViewListColor = (TextView) findViewById(R.id.text_view_color);
        this.textViewListGradient = (TextView) findViewById(R.id.text_view_gradient);
        this.textViewListBlur = (TextView) findViewById(R.id.text_view_blur);
        this.textViewListColor.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass13 */

            public void onClick(View view) {
                PolishCollageActivity.this.setBackgroundColor();
            }
        });
        this.textViewListGradient.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass14 */

            public void onClick(View view) {
                PolishCollageActivity.this.setBackgroundGradient();
            }
        });
        this.textViewListBlur.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass15 */

            public void onClick(View view) {
                PolishCollageActivity.this.selectBackgroundBlur();
            }
        });
        this.constrant_layout_change_Layout = (ConstraintLayout) findViewById(R.id.constrant_layout_change_Layout);
        this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        this.textViewSeekBarPadding = (TextView) findViewById(R.id.seekbarPadding);
        this.textViewSeekBarRadius = (TextView) findViewById(R.id.seekbarRadius);
        GridAdapter gridAdapter = new GridAdapter();
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recycler_view_collage);
        this.recycler_view_collage = recyclerView3;
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_collage.setAdapter(gridAdapter);
        gridAdapter.refreshData(CollageUtils.getCollageLayouts(this.stringList.size()), null);
        gridAdapter.setOnItemClickListener(this);
        AspectAdapter aspectAdapter = new AspectAdapter(true);
        aspectAdapter.setListener(this);
        RecyclerView recyclerView4 = (RecyclerView) findViewById(R.id.recycler_view_ratio);
        this.recycler_view_ratio = recyclerView4;
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_ratio.setAdapter(aspectAdapter);
        this.linear_layout_wrapper_sticker_list = (LinearLayout) findViewById(R.id.linear_layout_wrapper_sticker_list);
        ViewPager viewPager = (ViewPager) findViewById(R.id.stickerViewpaper);
        this.constraint_layout_sticker = (ConstraintLayout) findViewById(R.id.constraint_layout_sticker);
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.seekbarStickerAlpha);
        this.seekbarSticker = seekBar3;
        seekBar3.setVisibility(View.GONE);
        this.seekbarSticker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass16 */

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = PolishCollageActivity.this.queShotGridView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.relativeLayoutAddText.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass17 */

            public void onClick(View view) {
                PolishCollageActivity.this.queShotGridView.setHandlingSticker(null);
                PolishCollageActivity.this.openTextFragment();
            }
        });
        TextView textView = (TextView) findViewById(R.id.text_view_save);
        this.text_view_save = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass18 */

            public void onClick(View view) {
                PolishCollageActivity.this.SaveView();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.imageViewAddSticker);
        this.imageViewAddSticker = imageView;
        imageView.setVisibility(View.GONE);
        this.imageViewAddSticker.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass19 */

            public void onClick(View view) {
                PolishCollageActivity.this.lambda$onCreate$12$PolishCollageActivity(view);
            }
        });
        PolishStickerIcons polishStickerIcons = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_close), 0, "DELETE");
        polishStickerIcons.setIconEvent(new DeleteIconEvent());
        PolishStickerIcons polishStickerIcons2 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_scale), 3, PolishStickerIcons.SCALE);
        polishStickerIcons2.setIconEvent(new ZoomIconEvent());
        PolishStickerIcons polishStickerIcons3 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_flip), 1, PolishStickerIcons.FLIP);
        polishStickerIcons3.setIconEvent(new FlipHorizontallyEvent());
        PolishStickerIcons polishStickerIcons4 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_center), 2, PolishStickerIcons.ALIGN);
        polishStickerIcons4.setIconEvent(new AlignHorizontallyEvent());
        PolishStickerIcons polishStickerIcons5 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_rotate), 3, PolishStickerIcons.ROTATE);
        polishStickerIcons5.setIconEvent(new ZoomIconEvent());
        PolishStickerIcons polishStickerIcons6 = new PolishStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_edit), 1, PolishStickerIcons.EDIT);
        polishStickerIcons6.setIconEvent(new EditTextIconEvent());
        this.queShotGridView.setIcons(Arrays.asList(polishStickerIcons, polishStickerIcons2, polishStickerIcons3, polishStickerIcons6, polishStickerIcons5, polishStickerIcons4));
        this.queShotGridView.setConstrained(true);
        this.queShotGridView.setOnStickerOperationListener(this.onStickerOperationListener);
        viewPager.setAdapter(new PagerAdapter() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass20 */

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return 23;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(View view, Object obj) {
                return view.equals(obj);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                viewGroup.removeView((View) obj);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup viewGroup, int i) {
                View inflate = LayoutInflater.from(PolishCollageActivity.this.getBaseContext()).inflate(R.layout.list_sticker, (ViewGroup) null, false);
                RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerViewSticker);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(PolishCollageActivity.this.getApplicationContext(), 6));
                switch (i) {
                    case 0:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.bubbleList(), i, PolishCollageActivity.this));
                        break;
                    case 1:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.cartoonList(), i, PolishCollageActivity.this));
                        break;
                    case 2:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.childList(), i, PolishCollageActivity.this));
                        break;
                    case 3:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.flowerList(), i, PolishCollageActivity.this));
                        break;
                    case 4:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.amojiList(), i, PolishCollageActivity.this));
                        break;
                    case 5:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.deliciousList(), i, PolishCollageActivity.this));
                        break;
                    case 6:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.newyearList(), i, PolishCollageActivity.this));
                        break;
                    case 7:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.popularList(), i, PolishCollageActivity.this));
                        break;
                    case 8:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.emojList(), i, PolishCollageActivity.this));
                        break;
                    case 9:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.rageList(), i, PolishCollageActivity.this));
                        break;
                    case 10:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.christmasList(), i, PolishCollageActivity.this));
                        break;
                    case 11:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.unicornList(), i, PolishCollageActivity.this));
                        break;
                    case 12:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.stickerList(), i, PolishCollageActivity.this));
                        break;
                    case 13:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.plantList(), i, PolishCollageActivity.this));
                        break;
                    case 14:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.birthdayList(), i, PolishCollageActivity.this));
                        break;
                    case 15:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.lovedayList(), i, PolishCollageActivity.this));
                        break;
                    case 16:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.chickenList(), i, PolishCollageActivity.this));
                        break;
                    case 17:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.textneonList(), i, PolishCollageActivity.this));
                        break;
                    case 18:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.thuglifeList(), i, PolishCollageActivity.this));
                        break;
                    case 19:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.sweetList(), i, PolishCollageActivity.this));
                        break;
                    case 20:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.celebrateList(), i, PolishCollageActivity.this));
                        break;
                    case 21:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.happyList(), i, PolishCollageActivity.this));
                        break;
                    case 22:
                        recyclerView.setAdapter(new StickerAdapter(PolishCollageActivity.this.getApplicationContext(), StickerFileAsset.textcolorList(), i, PolishCollageActivity.this));
                        break;
                }
                viewGroup.addView(inflate);
                return inflate;
            }
        });
        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new StickersTabAdapter(viewPager, getApplicationContext()));
        recyclerTabLayout.setPositionThreshold(0.5f);
        recyclerTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.BackgroundColor));
        Preference.setKeyboard(getApplicationContext(), 0);
        KeyboardHeightProvider keyboardHeightProvider2 = new KeyboardHeightProvider(this);
        this.keyboardHeightProvider = keyboardHeightProvider2;
        keyboardHeightProvider2.addKeyboardListener(new KeyboardHeightProvider.KeyboardListener() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass21 */

            @Override // com.hold1.keyboardheightprovider.KeyboardHeightProvider.KeyboardListener
            public void onHeightChanged(int i) {
                PolishCollageActivity.this.lambda$onCreate$13$PolishCollageActivity(i);
            }
        });
        setLoading(false);
        this.constraint_layout_change_background = (ConstraintLayout) findViewById(R.id.constrant_layout_change_background);
        this.constraint_layout_collage_layout = (ConstraintLayout) findViewById(R.id.constraint_layout_collage_layout);
        this.currentBackgroundState = new CollageBackgroundAdapter.SquareView(Color.parseColor("#ffffff"), "", true);
        RecyclerView recyclerView5 = (RecyclerView) findViewById(R.id.recycler_view_color);
        this.recycler_view_color = recyclerView5;
        recyclerView5.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_color.setHasFixedSize(true);
        this.recycler_view_color.setAdapter(new CollageColorAdapter(getApplicationContext(), this));
        RecyclerView recyclerView6 = (RecyclerView) findViewById(R.id.recycler_view_gradient);
        this.recycler_view_gradient = recyclerView6;
        recyclerView6.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_gradient.setHasFixedSize(true);
        this.recycler_view_gradient.setAdapter(new CollageBackgroundAdapter(getApplicationContext(), (CollageBackgroundAdapter.BackgroundGridListener) this, true));
        RecyclerView recyclerView7 = (RecyclerView) findViewById(R.id.recycler_view_blur);
        this.recycler_view_blur = recyclerView7;
        recyclerView7.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_blur.setHasFixedSize(true);
        this.recycler_view_blur.setAdapter(new CollageBackgroundAdapter(getApplicationContext(), (CollageBackgroundAdapter.BackgroundGridListener) this, true));
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.queShotGridView.getLayoutParams();
        layoutParams.height = point.x;
        layoutParams.width = point.x;
        this.queShotGridView.setLayoutParams(layoutParams);
        this.aspectRatio = new AspectRatio(1, 1);
        this.queShotGridView.setAspectRatio(new AspectRatio(1, 1));
        QueShotGridActivityCollage = this;
        this.moduleToolsId = Module.NONE;
        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, null);
        QueShotGridActivityInstance = this;
        this.recyclerViewToolsCollage.setAlpha(0.0f);
        this.constraint_layout_collage_layout.post(new Runnable() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass22 */

            public void run() {
                PolishCollageActivity.this.lambda$onCreate$14$PolishCollageActivity();
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                PolishCollageActivity.this.lambda$onCreate$15$PolishCollageActivity();
            }
        }, 1000);
    }

    public void lambda$onCreate$0$PolishCollageActivity(View view) {
        onBackPressed();
    }

    public void lambda$onCreate$1$PolishCollageActivity(PolishGrid polishGrid, int i) {
        this.recyclerViewTools.setVisibility(View.GONE);
        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
        slideUp(this.recyclerViewToolsCollage);
        setGoneSave();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.recyclerViewToolsCollage.getLayoutParams();
        layoutParams.bottomMargin = SystemUtil.dpToPx(getApplicationContext(), 0);
        this.recyclerViewToolsCollage.setLayoutParams(layoutParams);
        this.moduleToolsId = Module.COLLAGE;
    }

    public void lambda$onCreate$2$PolishCollageActivity() {
        this.recyclerViewToolsCollage.setVisibility(View.GONE);
        this.recyclerViewTools.setVisibility(View.VISIBLE);
        setVisibleSave();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.recyclerViewToolsCollage.getLayoutParams();
        layoutParams.bottomMargin = 0;
        this.recyclerViewToolsCollage.setLayoutParams(layoutParams);
        this.moduleToolsId = Module.NONE;
    }

    public void lambda$onCreate$4$PolishCollageActivity(View view) {
        setLayer();
    }

    public void lambda$onCreate$5$PolishCollageActivity(View view) {
        setBorder();
    }

    public void lambda$onCreate$6$PolishCollageActivity(View view) {
        setRatio();
    }

    public void lambda$onCreate$7$PolishCollageActivity(View view) {
        setBackgroundColor();
    }

    public void lambda$onCreate$8$PolishCollageActivity(View view) {
        setBackgroundGradient();
    }

    public void lambda$onCreate$9$PolishCollageActivity(View view) {
        selectBackgroundBlur();
    }

    public void lambda$onCreate$10$PolishCollageActivity(View view) {
        this.queShotGridView.setHandlingSticker(null);
        openTextFragment();
    }

    public void lambda$onCreate$12$PolishCollageActivity(View view) {
        this.imageViewAddSticker.setVisibility(View.GONE);
        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
    }

    public void lambda$onCreate$13$PolishCollageActivity(int i) {
        if (i <= 0) {
            Preference.setHeightOfNotch(getApplicationContext(), -i);
            return;
        }
        TextFragment textFragment = this.addTextFragment;
        if (textFragment != null) {
            textFragment.updateAddTextBottomToolbarHeight(Preference.getHeightOfNotch(getApplicationContext()) + i);
            Preference.setKeyboard(getApplicationContext(), Preference.getHeightOfNotch(getApplicationContext()) + i);
        }
    }

    public void lambda$onCreate$14$PolishCollageActivity() {
        slideDown(this.recyclerViewToolsCollage);
    }

    public void lambda$onCreate$15$PolishCollageActivity() {
        this.recyclerViewToolsCollage.setAlpha(1.0f);
    }

    public void lambda$new$16$PolishCollageActivity(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            this.recyclerViewTools.setVisibility(View.VISIBLE);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void SaveView() {
        if (PermissionsUtils.checkWriteStoragePermission(this)) {
            SaveFileUtils.createBitmap(this.queShotGridView, 1920);
            Bitmap bitmap2 = this.queShotGridView.createBitmap();
            Log.e("gauravvvvv", "saveImage: " + bitmap2);
            Log.e("gauravvvvv", "saveImage:1111111111 " + bitmap2);
            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Photo Art Editor");
            myDir.mkdirs();
            this.file = new File(myDir, "Image-" + new Random().nextInt(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT) + ".jpg");
            System.out.println("pathhhh====" + this.file);
            Utils.pass_st1 = this.file.getAbsolutePath();
            String path = this.file.getPath();
            if (this.file.exists()) {
                this.file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(this.file);
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                BitmapFactory.decodeFile(this.file.getAbsolutePath());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass24 */

                public void onScanCompleted(String path, Uri uri) {
                    PolishCollageActivity.this.runOnUiThread(new Runnable() {
                        /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass24.AnonymousClass1 */

                        public void run() {
                        }
                    });
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
            Intent intent = new Intent(this, PhotoSavedActivity.class);
            intent.putExtra(ClientCookie.PATH_ATTR, path);
            startActivity(intent);
            return;
        }
        return;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        try {
            this.queShotGridView.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onPause() {
        super.onPause();
        this.keyboardHeightProvider.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        super.onResume();
        this.keyboardHeightProvider.onResume();
    }

    public void slideDown(View view) {
        ObjectAnimator.ofFloat(view, "translationY", 0.0f, (float) view.getHeight()).start();
    }

    public void slideUp(View view) {
        ObjectAnimator.ofFloat(view, "translationY", (float) view.getHeight(), 0.0f).start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void openTextFragment() {
        this.addTextFragment = TextFragment.show(this);
        TextFragment.TextEditor r0 = new TextFragment.TextEditor() {
            /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass25 */

            @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
            public void onDone(PolishText polishText) {
                PolishCollageActivity.this.queShotGridView.addSticker(new PolishTextView(PolishCollageActivity.this.getApplicationContext(), polishText));
            }

            @Override // com.example.photoareditor.Classs.TextFragment.TextEditor
            public void onBackButton() {
                if (PolishCollageActivity.this.queShotGridView.getStickers().isEmpty()) {
                    PolishCollageActivity.this.onBackPressed();
                }
            }
        };
        this.textEditor = r0;
        this.addTextFragment.setOnTextEditorListener(r0);
    }

    public void lambda$new$17$PolishCollageActivity(View view) {
        switch (view.getId()) {
            case R.id.imageViewCloseFilter /*{ENCODED_INT: 2131362235}*/:
            case R.id.imageViewCloseLayer /*{ENCODED_INT: 2131362236}*/:
            case R.id.imageViewCloseText /*{ENCODED_INT: 2131362244}*/:
            case R.id.imageViewClosebackground /*{ENCODED_INT: 2131362246}*/:
            case R.id.image_view_close_sticker /*{ENCODED_INT: 2131362288}*/:
                setVisibleSave();
                onBackPressed();
                return;
            case R.id.imageViewSaveFilter /*{ENCODED_INT: 2131362264}*/:
                setGuideLineTools();
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.constraint_layout_filter_layout.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.imageViewSaveLayer /*{ENCODED_INT: 2131362265}*/:
                setGuideLineTools();
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.constrant_layout_change_Layout.setVisibility(View.GONE);
                setVisibleSave();
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.queShotGridView.setLocked(true);
                this.queShotGridView.setTouchEnable(true);
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.imageViewSaveText /*{ENCODED_INT: 2131362273}*/:
                setGuideLineTools();
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutSaveText.setVisibility(View.GONE);
                this.queShotGridView.setHandlingSticker(null);
                this.queShotGridView.setLocked(true);
                this.relativeLayoutAddText.setVisibility(View.GONE);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.imageViewSavebackground /*{ENCODED_INT: 2131362275}*/:
                setGuideLineTools();
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.constraint_layout_change_background.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                setVisibleSave();
                this.queShotGridView.setLocked(true);
                this.queShotGridView.setTouchEnable(true);
                if (this.queShotGridView.getBackgroundResourceMode() == 0) {
                    this.currentBackgroundState.isColor = true;
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.drawableId = ((ColorDrawable) this.queShotGridView.getBackground()).getColor();
                    this.currentBackgroundState.drawable = null;
                } else if (this.queShotGridView.getBackgroundResourceMode() == 1) {
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                } else {
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.isBitmap = true;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                }
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_sticker /*{ENCODED_INT: 2131362306}*/:
                setGuideLineTools();
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.constraint_layout_sticker.setVisibility(View.GONE);
                this.constraintLayoutSaveSticker.setVisibility(View.GONE);
                this.queShotGridView.setHandlingSticker(null);
                this.seekbarSticker.setVisibility(View.GONE);
                this.imageViewAddSticker.setVisibility(View.GONE);
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                setVisibleSave();
                this.queShotGridView.setLocked(true);
                this.queShotGridView.setTouchEnable(true);
                this.moduleToolsId = Module.NONE;
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                setVisibleSave();
                return;
            default:
                return;
        }
    }

    public static PolishCollageActivity getQueShotGridActivityInstance() {
        return QueShotGridActivityInstance;
    }

    @Override // com.example.photoareditor.Activity.PolishBaseActivity
    public void isPermissionGranted(boolean z, String str) {
        if (z) {
            Bitmap createBitmap = SaveFileUtils.createBitmap(this.queShotGridView, 1920);
            Bitmap createBitmap2 = this.queShotGridView.createBitmap();
            new SaveCollageAsFile().execute(createBitmap, createBitmap2);
        }
    }

    public void setBackgroundColor() {
        this.recycler_view_color.scrollToPosition(0);
        ((CollageColorAdapter) this.recycler_view_color.getAdapter()).setSelectedIndex(-1);
        this.recycler_view_color.getAdapter().notifyDataSetChanged();
        this.recycler_view_color.setVisibility(View.VISIBLE);
        this.recycler_view_gradient.setVisibility(View.GONE);
        this.recycler_view_blur.setVisibility(View.GONE);
        this.textViewListGradient.setTextColor(getResources().getColor(R.color.black));
        this.textViewListGradient.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListBlur.setTextColor(getResources().getColor(R.color.black));
        this.textViewListBlur.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListColor.setTextColor(getResources().getColor(R.color.white));
        this.textViewListColor.setBackgroundResource(R.drawable.background_selected);
    }

    public void setBackgroundGradient() {
        this.recycler_view_gradient.scrollToPosition(0);
        ((CollageBackgroundAdapter) this.recycler_view_gradient.getAdapter()).setSelectedIndex(-1);
        this.recycler_view_gradient.getAdapter().notifyDataSetChanged();
        this.recycler_view_color.setVisibility(View.GONE);
        this.recycler_view_gradient.setVisibility(View.VISIBLE);
        this.recycler_view_blur.setVisibility(View.GONE);
        this.textViewListColor.setTextColor(getResources().getColor(R.color.black));
        this.textViewListColor.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListBlur.setTextColor(getResources().getColor(R.color.black));
        this.textViewListBlur.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListGradient.setTextColor(getResources().getColor(R.color.white));
        this.textViewListGradient.setBackgroundResource(R.drawable.background_selected);
    }

    public void selectBackgroundBlur() {
        ArrayList arrayList = new ArrayList();
        for (PolishGrid polishGrid : this.queShotGridView.getQueShotGrids()) {
            arrayList.add(polishGrid.getDrawable());
        }
        CollageBackgroundAdapter collageBackgroundAdapter = new CollageBackgroundAdapter(getApplicationContext(), this, arrayList);
        collageBackgroundAdapter.setSelectedIndex(-1);
        this.recycler_view_blur.setAdapter(collageBackgroundAdapter);
        this.recycler_view_color.setVisibility(View.GONE);
        this.recycler_view_gradient.setVisibility(View.GONE);
        this.recycler_view_blur.setVisibility(View.VISIBLE);
        this.textViewListColor.setTextColor(getResources().getColor(R.color.black));
        this.textViewListColor.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListGradient.setTextColor(getResources().getColor(R.color.black));
        this.textViewListGradient.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListBlur.setTextColor(getResources().getColor(R.color.white));
        this.textViewListBlur.setBackgroundResource(R.drawable.background_selected);
    }

    public void setLayer() {
        this.recycler_view_collage.setVisibility(View.VISIBLE);
        this.recycler_view_ratio.setVisibility(View.GONE);
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.textViewListLayer.setTextColor(getResources().getColor(R.color.white));
        this.textViewListLayer.setBackgroundResource(R.drawable.background_selected);
        this.textViewListRatio.setTextColor(getResources().getColor(R.color.black));
        this.textViewListRatio.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListBorder.setTextColor(getResources().getColor(R.color.black));
        this.textViewListBorder.setBackgroundResource(R.drawable.background_unslelected);
    }

    public void setBorder() {
        this.recycler_view_collage.setVisibility(View.GONE);
        this.recycler_view_ratio.setVisibility(View.GONE);
        this.linearLayoutBorder.setVisibility(View.VISIBLE);
        this.textViewListLayer.setTextColor(getResources().getColor(R.color.black));
        this.textViewListLayer.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListBorder.setTextColor(getResources().getColor(R.color.white));
        this.textViewListBorder.setBackgroundResource(R.drawable.background_selected);
        this.textViewListRatio.setTextColor(getResources().getColor(R.color.black));
        this.textViewListRatio.setBackgroundResource(R.drawable.background_unslelected);
        this.seekBarPadding.setProgress((int) this.queShotGridView.getCollagePadding());
        this.seekBarRadius.setProgress((int) this.queShotGridView.getCollageRadian());
    }

    public void setRatio() {
        this.recycler_view_collage.setVisibility(View.GONE);
        this.recycler_view_ratio.setVisibility(View.VISIBLE);
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.textViewListRatio.setTextColor(getResources().getColor(R.color.white));
        this.textViewListRatio.setBackgroundResource(R.drawable.background_selected);
        this.textViewListLayer.setTextColor(getResources().getColor(R.color.black));
        this.textViewListLayer.setBackgroundResource(R.drawable.background_unslelected);
        this.textViewListBorder.setTextColor(getResources().getColor(R.color.black));
        this.textViewListBorder.setBackgroundResource(R.drawable.background_unslelected);
    }

    /* renamed from: com.example.photoareditor.Activity.PolishCollageActivity$AnonymousClass12  reason: case insensitive filesystem */
    public static class C0000AnonymousClass12 {
        static final int[] $SwitchMap$com$meta$photoeditor$pro$module$Module;

        static {
            int[] iArr = new int[Module.values().length];
            $SwitchMap$com$meta$photoeditor$pro$module$Module = iArr;
            iArr[Module.LAYER.ordinal()] = 1;
            iArr[Module.PADDING.ordinal()] = 2;
            iArr[Module.RATIO.ordinal()] = 3;
            iArr[Module.FILTER.ordinal()] = 4;
            iArr[Module.TEXT.ordinal()] = 5;
            iArr[Module.STICKER.ordinal()] = 6;
            iArr[Module.GRADIENT.ordinal()] = 7;
            iArr[Module.COLLAGE.ordinal()] = 8;
            iArr[Module.NONE.ordinal()] = 9;
            iArr[Module.REPLACE.ordinal()] = 10;
            iArr[Module.H_FLIP.ordinal()] = 11;
            iArr[Module.V_FLIP.ordinal()] = 12;
            iArr[Module.ROTATE.ordinal()] = 13;
            try {
                iArr[Module.CROP.ordinal()] = 14;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    @Override // com.example.photoareditor.Adapter.GridToolsAdapter.OnItemSelected
    public void onToolSelected(Module module) {
        this.moduleToolsId = module;
        switch (C0000AnonymousClass12.$SwitchMap$com$meta$photoeditor$pro$module$Module[module.ordinal()]) {
            case 1:
                setLayer();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case 2:
                setBorder();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case 3:
                setRatio();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case 4:
                if (this.drawableList.isEmpty()) {
                    for (PolishGrid polishGrid : this.queShotGridView.getQueShotGrids()) {
                        this.drawableList.add(polishGrid.getDrawable());
                    }
                }
                new allFilters().execute(new Void[0]);
                setGoneSave();
                return;
            case 5:
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                setGuideLine();
                this.queShotGridView.setLocked(false);
                openTextFragment();
                this.constraintLayoutAddText.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSaveText.setVisibility(View.VISIBLE);
                this.relativeLayoutAddText.setVisibility(View.VISIBLE);
                return;
            case 6:
                setGuideLine();
                this.constraint_layout_sticker.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSaveSticker.setVisibility(View.VISIBLE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotGridView.updateLayout(this.queShotLayout);
                this.queShotGridView.setCollagePadding(this.Padding);
                this.queShotGridView.setCollageRadian(this.BorderRadius);
                getWindowManager().getDefaultDisplay().getSize(new Point());
                onNewAspectRatioSelected(this.aspectRatio);
                this.queShotGridView.setAspectRatio(this.aspectRatio);
                for (int i = 0; i < this.drawableList.size(); i++) {
                    this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
                }
                this.queShotGridView.invalidate();
                if (this.currentBackgroundState.isColor) {
                    this.queShotGridView.setBackgroundResourceMode(0);
                    this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
                } else {
                    this.queShotGridView.setBackgroundResourceMode(1);
                    if (this.currentBackgroundState.drawable != null) {
                        this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
                    }
                }
                setGoneSave();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                return;
            case 7:
                setGuideLine();
                this.constraint_layout_change_background.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                setBackgroundColor();
                if (this.queShotGridView.getBackgroundResourceMode() == 0) {
                    this.currentBackgroundState.isColor = true;
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.drawableId = ((ColorDrawable) this.queShotGridView.getBackground()).getColor();
                    return;
                } else if (this.queShotGridView.getBackgroundResourceMode() == 2 || (this.queShotGridView.getBackground() instanceof ColorDrawable)) {
                    this.currentBackgroundState.isBitmap = true;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                    return;
                } else if (this.queShotGridView.getBackground() instanceof GradientDrawable) {
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public void lambda$onCreate$3$PolishCollageActivity() {
        final int i;
        final ArrayList arrayList = new ArrayList();
        if (this.stringList.size() > this.queShotLayout.getAreaCount()) {
            i = this.queShotLayout.getAreaCount();
        } else {
            i = this.stringList.size();
        }
        for (int i2 = 0; i2 < i; i2++) {
            Target r3 = new Target() {
                /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass26 */

                @Override // com.squareup.picasso.Target
                public void onPrepareLoad(Drawable drawable) {
                }

                @Override // com.squareup.picasso.Target
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    float width = (float) bitmap.getWidth();
                    float height = (float) bitmap.getHeight();
                    float max = Math.max(width / width, height / width);
                    if (max > 1.0f) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false);
                    }
                    arrayList.add(bitmap);
                    if (arrayList.size() == i) {
                        if (PolishCollageActivity.this.stringList.size() < PolishCollageActivity.this.queShotLayout.getAreaCount()) {
                            for (int i = 0; i < PolishCollageActivity.this.queShotLayout.getAreaCount(); i++) {
                                PolishCollageActivity.this.queShotGridView.addQuShotCollage((Bitmap) arrayList.get(i % i));
                            }
                        } else {
                            PolishCollageActivity.this.queShotGridView.addPieces(arrayList);
                        }
                    }
                    PolishCollageActivity.this.targets.remove(this);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }
            };
            RequestCreator load = Picasso.with(this).load("file:///" + this.stringList.get(i2));
            int i3 = this.deviceWidth;
            load.resize(i3, i3).centerInside().config(Bitmap.Config.RGB_565).into(r3);
            this.targets.add(r3);
        }
    }

    private void setOnBackPressDialog() {
        final Dialog dialog = new Dialog(this, R.style.UploadDialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(true);
        dialog.show();
        this.textViewCancel = (TextView) dialog.findViewById(R.id.textViewCancel);
        TextView textView = (TextView) dialog.findViewById(R.id.textViewDiscard);
        this.textViewDiscard = textView;

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PolishCollageActivity.this.lambda$setOnBackPressDialog$18$PolishCollageActivity(dialog, view);
            }
        });

        this.textViewCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void lambda$setOnBackPressDialog$18$PolishCollageActivity(Dialog dialog, View view) {
        dialog.dismiss();
        this.moduleToolsId = null;
        finish();
        finish();
    }

    public void setGuideLineTools() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_collage_layout);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 1, this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 4, this.guidelineTools.getId(), 3, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 2, this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_collage_layout);
    }

    public void setGuideLine() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_collage_layout);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 1, this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 4, this.guideline.getId(), 3, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 2, this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_collage_layout);
    }

    public void setGoneSave() {
        this.constraint_save_control.setVisibility(View.GONE);
    }

    public void setVisibleSave() {
        this.constraint_save_control.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (this.moduleToolsId == null) {
            super.onBackPressed();
            return;
        }
        try {
            switch (C0000AnonymousClass12.$SwitchMap$com$meta$photoeditor$pro$module$Module[this.moduleToolsId.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constrant_layout_change_Layout.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    setVisibleSave();
                    this.queShotGridView.updateLayout(this.queShotLayout);
                    this.queShotGridView.setCollagePadding(this.Padding);
                    this.queShotGridView.setCollageRadian(this.BorderRadius);
                    this.moduleToolsId = Module.NONE;
                    getWindowManager().getDefaultDisplay().getSize(new Point());
                    onNewAspectRatioSelected(this.aspectRatio);
                    this.queShotGridView.setAspectRatio(this.aspectRatio);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    return;
                case 4:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_filter_layout.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    for (int i = 0; i < this.drawableList.size(); i++) {
                        this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
                    }
                    this.queShotGridView.invalidate();
                    setVisibleSave();
                    this.moduleToolsId = Module.NONE;
                    return;
                case 5:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraintLayoutAddText.setVisibility(View.GONE);
                    this.constraintLayoutSaveText.setVisibility(View.GONE);
                    if (!this.queShotGridView.getStickers().isEmpty()) {
                        this.queShotGridView.getStickers().clear();
                        this.queShotGridView.setHandlingSticker(null);
                    }
                    this.moduleToolsId = Module.NONE;
                    this.relativeLayoutAddText.setVisibility(View.GONE);
                    this.queShotGridView.setHandlingSticker(null);
                    setVisibleSave();
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    return;
                case 6:
                    setGuideLineTools();
                    this.constraintLayoutSaveSticker.setVisibility(View.GONE);
                    if (this.queShotGridView.getStickers().size() <= 0) {
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.imageViewAddSticker.setVisibility(View.GONE);
                        this.queShotGridView.setHandlingSticker(null);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraint_layout_sticker.setVisibility(View.GONE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.queShotGridView.setLocked(true);
                        this.moduleToolsId = Module.NONE;
                    } else if (this.imageViewAddSticker.getVisibility() == View.VISIBLE) {
                        this.queShotGridView.getStickers().clear();
                        this.imageViewAddSticker.setVisibility(View.GONE);
                        this.queShotGridView.setHandlingSticker(null);
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraint_layout_sticker.setVisibility(View.GONE);
                        this.queShotGridView.setLocked(true);
                        this.queShotGridView.setTouchEnable(true);
                        this.moduleToolsId = Module.NONE;
                    } else {
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
                        this.imageViewAddSticker.setVisibility(View.VISIBLE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                    }
                    this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_sticker.setVisibility(View.GONE);
                    setVisibleSave();
                    return;
                case 7:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_change_background.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    if (this.currentBackgroundState.isColor) {
                        this.queShotGridView.setBackgroundResourceMode(0);
                        this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
                    } else if (this.currentBackgroundState.isBitmap) {
                        this.queShotGridView.setBackgroundResourceMode(2);
                        this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.queShotGridView.setBackgroundResourceMode(1);
                        if (this.currentBackgroundState.drawable != null) {
                            this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                        } else {
                            this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
                        }
                    }
                    setVisibleSave();
                    this.moduleToolsId = Module.NONE;
                    return;
                case 8:
                    setVisibleSave();
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.recyclerViewToolsCollage.setVisibility(View.GONE);
                    this.moduleToolsId = Module.NONE;
                    this.queShotGridView.setQueShotGrid(null);
                    this.queShotGridView.setPrevHandlingQueShotGrid(null);
                    this.queShotGridView.invalidate();
                    this.moduleToolsId = Module.NONE;
                    return;
                case 9:
                    setOnBackPressDialog();
                    return;
                default:
                    super.onBackPressed();
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.example.photoareditor.Adapter.GridAdapter.OnItemClickListener
    public void onItemClick(PolishLayout polishLayout, int i) {
        PolishLayout parse = PolishLayoutParser.parse(polishLayout.generateInfo());
        polishLayout.setRadian(this.queShotGridView.getCollageRadian());
        polishLayout.setPadding(this.queShotGridView.getCollagePadding());
        this.queShotGridView.updateLayout(parse);
    }

    @Override // com.example.photoareditor.CollageClass.AspectAdapter.OnNewSelectedListener
    public void onNewAspectRatioSelected(AspectRatio aspectRatio2) {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int[] calculateWidthAndHeight = calculateWidthAndHeight(aspectRatio2, point);
        this.queShotGridView.setLayoutParams(new ConstraintLayout.LayoutParams(calculateWidthAndHeight[0], calculateWidthAndHeight[1]));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_wrapper_collage_view);
        constraintSet.connect(this.queShotGridView.getId(), 3, this.constraint_layout_wrapper_collage_view.getId(), 3, 0);
        constraintSet.connect(this.queShotGridView.getId(), 1, this.constraint_layout_wrapper_collage_view.getId(), 1, 0);
        constraintSet.connect(this.queShotGridView.getId(), 4, this.constraint_layout_wrapper_collage_view.getId(), 4, 0);
        constraintSet.connect(this.queShotGridView.getId(), 2, this.constraint_layout_wrapper_collage_view.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_wrapper_collage_view);
        this.queShotGridView.setAspectRatio(aspectRatio2);
    }

    public void replaceCurrentPiece(String str) {
        new OnLoadBitmapFromUri().execute(str);
    }

    private int[] calculateWidthAndHeight(AspectRatio aspectRatio2, Point point) {
        int height = this.constraint_layout_wrapper_collage_view.getHeight();
        if (aspectRatio2.getHeight() > aspectRatio2.getWidth()) {
            int ratio = (int) (aspectRatio2.getRatio() * ((float) height));
            if (ratio < point.x) {
                return new int[]{ratio, height};
            }
            return new int[]{point.x, (int) (((float) point.x) / aspectRatio2.getRatio())};
        }
        int ratio2 = (int) (((float) point.x) / aspectRatio2.getRatio());
        if (ratio2 > height) {
            return new int[]{(int) (((float) height) * aspectRatio2.getRatio()), height};
        }
        return new int[]{point.x, ratio2};
    }

    @Override // com.example.photoareditor.Adapter.StickerAdapter.OnClickSplashListener
    public void addSticker(int i, Bitmap bitmap) {
        Log.e("aaaaaa", "addSticker: " + bitmap);
        this.queShotGridView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
        this.imageViewAddSticker.setVisibility(View.VISIBLE);
    }

    @Override // com.example.photoareditor.Adapter.CollageBackgroundAdapter.BackgroundGridListener
    public void onBackgroundSelected(int i, final CollageBackgroundAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.queShotGridView.setBackgroundColor(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(0);
        } else if (squareView.drawable != null) {
            this.queShotGridView.setBackgroundResourceMode(2);
            new AsyncTask<Void, Bitmap, Bitmap>() {
                /* class com.example.photoareditor.Activity.PolishCollageActivity.AnonymousClass29 */

                public Bitmap doInBackground(Void... voidArr) {
                    return FilterUtils.getBlurImageFromBitmap(((BitmapDrawable) squareView.drawable).getBitmap(), 5.0f);
                }

                public void onPostExecute(Bitmap bitmap) {
                    PolishCollageActivity.this.queShotGridView.setBackground(new BitmapDrawable(PolishCollageActivity.this.getResources(), bitmap));
                }
            }.execute(new Void[0]);
        } else {
            this.queShotGridView.setBackgroundResource(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(1);
        }
    }

    @Override // com.example.photoareditor.Adapter.CollageColorAdapter.BackgroundColorListener
    public void onBackgroundColorSelected(int i, CollageColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.queShotGridView.setBackgroundColor(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(0);
        }
    }

    @Override // com.example.photoareditor.Interface.FilterListener
    public void onFilterSelected(int i, String str) {
        new LoadBitmapWithFilter().execute(str);
    }

    @Override // com.example.photoareditor.Fragment.CropFragment.OnCropPhoto
    public void finishCrop(Bitmap bitmap) {
        this.queShotGridView.replace(bitmap, "");
    }

    @Override // com.example.photoareditor.Fragment.FilterFragment.OnFilterSavePhoto
    public void onSaveFilter(Bitmap bitmap) {
        this.queShotGridView.replace(bitmap, "");
    }

    @Override // com.example.photoareditor.Adapter.GridItemToolsAdapter.OnPieceFuncItemSelected
    public void onPieceFuncSelected(Module module) {
        int i = C0000AnonymousClass12.$SwitchMap$com$meta$photoeditor$pro$module$Module[module.ordinal()];
        if (i != 4) {
            switch (i) {
                case 10:
                    Utils.collage = 8;
                    startActivity(new Intent(this, Image_GalleryActivity.class));
                    return;
                case 11:
                    this.queShotGridView.flipHorizontally();
                    return;
                case 12:
                    this.queShotGridView.flipVertically();
                    return;
                case 13:
                    this.queShotGridView.rotate(90.0f);
                    return;
                case 14:
                    CropFragment.show(this, this, ((BitmapDrawable) this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap());
                    return;
                default:
                    return;
            }
        } else {
            new LoadFilterBitmapForCurrentPiece().execute(new Void[0]);
        }
    }

    class allFilters extends AsyncTask<Void, Void, Void> {
        allFilters() {
        }

        public void onPreExecute() {
            PolishCollageActivity.this.setLoading(true);
        }

        public Void doInBackground(Void... voidArr) {
            PolishCollageActivity.this.listFilterAll.clear();
            PolishCollageActivity.this.listFilterAll.addAll(FilterFileAsset.getListBitmapFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) PolishCollageActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void r6) {
            RecyclerView recyclerView = PolishCollageActivity.this.recyclerViewFilter;
            ArrayList arrayList = PolishCollageActivity.this.listFilterAll;
            PolishCollageActivity polishCollageActivity = PolishCollageActivity.this;
            recyclerView.setAdapter(new FilterAdapter(arrayList, polishCollageActivity, polishCollageActivity.getApplicationContext(), Arrays.asList(FilterFileAsset.FILTERS)));
            PolishCollageActivity.this.recyclerViewToolsCollage.setVisibility(View.GONE);
            PolishCollageActivity.this.queShotGridView.setLocked(false);
            PolishCollageActivity.this.queShotGridView.setTouchEnable(false);
            PolishCollageActivity.this.setGuideLine();
            PolishCollageActivity.this.constraint_layout_filter_layout.setVisibility(View.VISIBLE);
            PolishCollageActivity.this.recyclerViewTools.setVisibility(View.GONE);
            PolishCollageActivity.this.setLoading(false);
        }
    }

    class LoadFilterBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadFilterBitmapForCurrentPiece() {
        }

        public void onPreExecute() {
            PolishCollageActivity.this.setLoading(true);
        }

        public List<Bitmap> doInBackground(Void... voidArr) {
            return FilterFileAsset.getListBitmapFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) PolishCollageActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), 100, 100));
        }

        public void onPostExecute(List<Bitmap> list) {
            PolishCollageActivity.this.setLoading(false);
            if (PolishCollageActivity.this.queShotGridView.getQueShotGrid() != null) {
                PolishCollageActivity polishCollageActivity = PolishCollageActivity.this;
                FilterFragment.show(polishCollageActivity, polishCollageActivity, ((BitmapDrawable) polishCollageActivity.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), list);
            }
        }
    }

    class LoadBitmapWithFilter extends AsyncTask<String, List<Bitmap>, List<Bitmap>> {
        LoadBitmapWithFilter() {
        }

        public void onPreExecute() {
            PolishCollageActivity.this.setLoading(true);
        }

        public List<Bitmap> doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Iterator<Drawable> it = PolishCollageActivity.this.drawableList.iterator();
            while (it.hasNext()) {
                arrayList.add(FilterUtils.getBitmapWithFilter(((BitmapDrawable) it.next()).getBitmap(), strArr[0]));
            }
            return arrayList;
        }

        public void onPostExecute(List<Bitmap> list) {
            for (int i = 0; i < list.size(); i++) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(PolishCollageActivity.this.getResources(), list.get(i));
                bitmapDrawable.setAntiAlias(true);
                bitmapDrawable.setFilterBitmap(true);
                PolishCollageActivity.this.queShotGridView.getQueShotGrids().get(i).setDrawable(bitmapDrawable);
            }
            PolishCollageActivity.this.queShotGridView.invalidate();
            PolishCollageActivity.this.setLoading(false);
        }
    }

    class OnLoadBitmapFromUri extends AsyncTask<String, Bitmap, Bitmap> {
        OnLoadBitmapFromUri() {
        }

        public void onPreExecute() {
            PolishCollageActivity.this.setLoading(true);
        }

        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));
                Bitmap rotateBitmap = SystemUtil.rotateBitmap(MediaStore.Images.Media.getBitmap(PolishCollageActivity.this.getContentResolver(), fromFile), new ExifInterface(PolishCollageActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
                float width = (float) rotateBitmap.getWidth();
                float height = (float) rotateBitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                return max > 1.0f ? Bitmap.createScaledBitmap(rotateBitmap, (int) (width / max), (int) (height / max), false) : rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Bitmap bitmap) {
            PolishCollageActivity.this.setLoading(false);
            PolishCollageActivity.this.queShotGridView.replace(bitmap, "");
        }
    }

    public class SaveCollageAsFile extends AsyncTask<Bitmap, String, String> {
        SaveCollageAsFile() {
        }

        public void onPreExecute() {
            PolishCollageActivity.this.setLoading(true);
        }

        public String doInBackground(Bitmap... bitmapArr) {
            Bitmap bitmap = bitmapArr[0];
            Bitmap bitmap2 = bitmapArr[1];
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = null;
            canvas.drawBitmap(bitmap, (Rect) null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), paint);
            canvas.drawBitmap(bitmap2, (Rect) null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), paint);
            bitmap.recycle();
            bitmap2.recycle();
            try {
                File saveBitmapFileCollage = SaveFileUtils.saveBitmapFileCollage(PolishCollageActivity.this, createBitmap, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()), null);
                createBitmap.recycle();
                return saveBitmapFileCollage.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String str) {
            PolishCollageActivity.this.setLoading(false);
        }
    }

    public void setLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.relativeLayoutLoading.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.relativeLayoutLoading.setVisibility(View.GONE);
    }
}