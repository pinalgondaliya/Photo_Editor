package com.example.photoeditor.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.example.photoeditor.Classs.ImageHelper;
import com.example.photoeditor.Classs.ServiceApi;
import com.example.photoeditor.Classs.SessionHandler;
import com.example.photoeditor.Classs.SettingsProvider;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.ModelClass.Style;
import com.example.photoeditor.ModelClass.Submission;
import com.example.photoeditor.ModelClass.SubmissionResult;
import com.example.photoeditor.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import cz.msebera.android.httpclient.Header;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadActivity extends AppCompatActivity {
    static final String TAG = "UploadActivity";
    boolean check = false;
    Dialog dialog;
    TextView dialog_status_text;
    boolean isProcessing;
    MenuItem item;
    Activity mActivity;
    Bitmap mBitmapResult;
    Bitmap mBitmapResultGenerated;
    View mChooseStyleLayout;
    DownloadManager mDownloadManager;
    RelativeLayout mFilterIntensityLayout;
    SeekBar mFilterIntensitySeekBar;
    Bitmap mPhotoBitmap;
    Uri mPhotoUri;
    ImageView mPreviewView;
    RecyclerView mRecyclerView;
    HashMap<Long, SubmissionResult> mResultCache;
    TextView mSaveBtn;
    Long mSelectedStyleId;
    String mSessionToken;
    SettingsProvider mSettingsProvider;
    TextView mStatusText;
    StyleAdapter mStyleImageAdapter;
    LottieAnimationView mStyleProgressView;
    List<Style> mStyles;
    String mToken;
    ProgressBar mUploadProgressView;
    boolean processingStarted;
    SessionHandler sessionHandler;

    class C03591 implements SeekBar.OnSeekBarChangeListener {
        int savedProgress;

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        C03591() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            UploadActivity.this.applyFilter(i);
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    class C03602 implements View.OnClickListener {
        C03602() {
        }

        public void onClick(View view) {
            UploadActivity.this.mFilterIntensityLayout.setVisibility(View.GONE);
            UploadActivity.this.mChooseStyleLayout.setVisibility(View.VISIBLE);
        }
    }

    class C03613 implements View.OnClickListener {
        C03613() {
        }

        public void onClick(View view) {
            UploadActivity.this.applyFilter(100);
            UploadActivity.this.mFilterIntensitySeekBar.setProgress(100);
            UploadActivity.this.mFilterIntensityLayout.setVisibility(View.GONE);
            UploadActivity.this.mChooseStyleLayout.setVisibility(View.VISIBLE);
        }
    }

    class C03676 implements View.OnClickListener {

        class C03661 implements Runnable {
            C03661() {
            }

            public void run() {
                new Bundle();
                Utils.shareSubmission(UploadActivity.this.mActivity, UploadActivity.this.getBitmapResult());
            }
        }

        C03676() {
        }

        public void onClick(View view) {
            if (UploadActivity.this.mBitmapResult != null) {
                new Thread(new C03661()).start();
            }
        }
    }

    class C03697 implements View.OnClickListener {
        final CoordinatorLayout val$coordinatorLayout;

        class C03681 implements Runnable {
            C03681() {
            }

            public void run() {
                new Bundle().putString("action", "save");
                Utils.saveSubmission(UploadActivity.this.mActivity, UploadActivity.this.getBitmapResult());
            }
        }

        C03697(CoordinatorLayout coordinatorLayout) {
            this.val$coordinatorLayout = coordinatorLayout;
        }

        public void onClick(View view) {
            if (UploadActivity.this.mBitmapResult != null) {
                new Thread(new C03681()).start();
            }
        }
    }

    private class CheckForResultTask extends TimerTask {
        private Long mStyleId;
        private Long mSubmissionId;
        private Submission.Type mType;

        public CheckForResultTask(Long l, Long l2, Submission.Type type) {
            this.mSubmissionId = l;
            this.mStyleId = l2;
            this.mType = type;
        }

        public void run() {
            Log.d(UploadActivity.TAG, "checkForResultTask");
            ServiceApi.getSubmissionResultV2(UploadActivity.this.mSessionToken, this.mSubmissionId, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
                /* class com.example.photoareditor.Activity.UploadActivity.CheckForResultTask.AnonymousClass1 */

                @Override // com.loopj.android.http.AsyncHttpResponseHandler
                public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                }

                @Override // com.loopj.android.http.AsyncHttpResponseHandler
                public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                    UploadActivity.this.processingStarted = false;
                    Log.d(UploadActivity.TAG, "getSubmissionResult statusCode: " + i);
                    try {
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        UploadActivity.this.mStatusText.post(new C03701());
                        JSONObject jSONObject = new JSONObject(new String(bArr)).getJSONObject("data");
                        if (!jSONObject.isNull("imageId")) {
                            String str = "https://www.deeparteffects.com//images/generated/" + jSONObject.getString("filepath");
                            String str2 = "https://www.deeparteffects.com//images/generated/" + jSONObject.getString("filePathWaterMark");
                            if (CheckForResultTask.this.mType.equals(Submission.Type.PRINT)) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
                                request.setVisibleInDownloadsUi(false);
                                request.allowScanningByMediaScanner();
                                request.setTitle(UploadActivity.this.getApplicationContext().getString(R.string.app_name));
                                request.setDescription("Artwork - Print Quality Download");
                                request.setNotificationVisibility(1);
                                request.setDestinationUri(Uri.fromFile(Utils.getSaveFile(UploadActivity.this)));
                                UploadActivity.this.mDownloadManager.enqueue(request);
                                UploadActivity.this.runOnUiThread(new C03712());
                                return;
                            }
                            final SubmissionResult submissionResult = new SubmissionResult();
                            submissionResult.filePath = str;
                            submissionResult.filePathWaterMark = str2;
                            UploadActivity.this.mResultCache.put(CheckForResultTask.this.mStyleId, submissionResult);
                            UploadActivity.this.runOnUiThread(new Runnable() {
                                /* class com.example.photoareditor.Activity.UploadActivity.CheckForResultTask.AnonymousClass1.AnonymousClass1 */

                                public void run() {
                                    Picasso.with(UploadActivity.this).load(submissionResult.filePath).into(new Target() {
                                        /* class com.example.photoareditor.Activity.UploadActivity.CheckForResultTask.AnonymousClass1.AnonymousClass1.AnonymousClass1 */

                                        @Override // com.squareup.picasso.Target
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                                            UploadActivity.this.mBitmapResult = bitmap;
                                            if (UploadActivity.this.check) {
                                                UploadActivity.this.mPreviewView.startAnimation(AnimationUtils.loadAnimation(UploadActivity.this.mActivity, R.anim.fade_in));
                                                UploadActivity.this.mPreviewView.setImageBitmap(bitmap);
                                            }
                                            UploadActivity.this.mPreviewView.setImageBitmap(bitmap);
                                            Log.e("aaaaa", "Gaurav 111 ");
                                            UploadActivity.this.check = false;
                                            UploadActivity.this.mStyleImageAdapter.notifyDataSetChanged();
                                            CheckForResultTask.this.finish();
                                        }

                                        @Override
                                        public void onBitmapFailed(Drawable errorDrawable) {

                                        }

                                        @Override // com.squareup.picasso.Target
                                        public void onPrepareLoad(Drawable drawable) {
                                            UploadActivity.this.check = true;
                                            UploadActivity.this.mPreviewView.setVisibility(View.VISIBLE);
                                            Log.e("aaaaa", "Gaurav 222 ");
                                        }
                                    });
                                    countDownLatch.countDown();
                                }
                            });
                            try {
                                countDownLatch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e2) {
                        UploadActivity.this.runOnUiThread(new Runnable() {
                            /* class com.example.photoareditor.Activity.UploadActivity.CheckForResultTask.AnonymousClass1.AnonymousClass2 */

                            public void run() {
                                Toast.makeText(UploadActivity.this.mActivity, "An error occurred while processing your image. Try again later.", Toast.LENGTH_SHORT).show();
                                CheckForResultTask.this.finish();
                            }
                        });
                    }
                }

                /* renamed from: com.example.photoareditor.Activity.UploadActivity$CheckForResultTask$1$C03701 */
                class C03701 implements Runnable {
                    C03701() {
                    }

                    public void run() {
                        if (CheckForResultTask.this.mType.equals(Submission.Type.PREMIUM)) {
                            Log.d("quality", "premium");
                            UploadActivity.this.mStatusText.setText(UploadActivity.this.getText(R.string.processing_download_premium));
                            UploadActivity.this.dialog_status_text.setText(UploadActivity.this.getText(R.string.processing_download_premium));
                        } else if (CheckForResultTask.this.mType.equals(Submission.Type.PRINT)) {
                            Log.d("quality", "print");
                            UploadActivity.this.mStatusText.setText(UploadActivity.this.getText(R.string.processing_download_print));
                            UploadActivity.this.mStatusText.setText(UploadActivity.this.getText(R.string.processing_download_print));
                        } else {
                            UploadActivity.this.mStatusText.setText(UploadActivity.this.getText(R.string.processing_download));
                            UploadActivity.this.dialog_status_text.setText(UploadActivity.this.getText(R.string.processing_download));
                        }
                    }
                }

                /* renamed from: com.example.photoareditor.Activity.UploadActivity$CheckForResultTask$1$C03712 */
                class C03712 implements Runnable {
                    C03712() {
                    }

                    public void run() {
                        Toast.makeText(UploadActivity.this.mActivity, UploadActivity.this.getText(R.string.download_hint), Toast.LENGTH_SHORT).show();
                        CheckForResultTask.this.finish();
                    }
                }

                /* renamed from: com.example.photoareditor.Activity.UploadActivity$CheckForResultTask$1$C03723 */
                class C03723 implements Runnable {
                    C03723() {
                    }

                    public void run() {
                        Log.d("atsettime", "yes");
                    }
                }

                /* renamed from: com.example.photoareditor.Activity.UploadActivity$CheckForResultTask$1$C03734 */
                class C03734 implements Runnable {
                    C03734() {
                    }

                    public void run() {
                        Toast.makeText(UploadActivity.this.mActivity, "An error occurred while processing your image. Try again later.", Toast.LENGTH_SHORT).show();
                        CheckForResultTask.this.finish();
                    }
                }
            });
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void finish() {
            UploadActivity.this.isProcessing = false;
            UploadActivity.this.mSaveBtn.startAnimation(AnimationUtils.loadAnimation(UploadActivity.this.mActivity, R.anim.fade_in));
            UploadActivity.this.mSaveBtn.setVisibility(View.VISIBLE);
            UploadActivity.this.mStatusText.setVisibility(View.GONE);
            UploadActivity.this.mUploadProgressView.setVisibility(View.GONE);
            UploadActivity.this.dialog.cancel();
            cancel();
        }
    }

    class C05718 implements SessionHandler.GetSessionCallback {
        final SessionHandler val$sessionHandler;

        C05718(SessionHandler sessionHandler) {
            this.val$sessionHandler = sessionHandler;
        }

        @Override // com.example.photoareditor.Classs.SessionHandler.GetSessionCallback
        public void onTokenReceived(boolean z) {
            if (z) {
                UploadActivity.this.mSessionToken = this.val$sessionHandler.getSessionToken();
                UploadActivity.this.loadStyles();
                return;
            }
            UploadActivity.this.sessionHandler.getSessionToken(new C05718(UploadActivity.this.sessionHandler));
        }
    }

    public class StyleAdapter extends RecyclerView.Adapter<StyleHolder> {
        private StyleAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public StyleHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new StyleHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_style, viewGroup, false));
        }

        public void onBindViewHolder(StyleHolder styleHolder, int i) {
            styleHolder.bindSubmission(UploadActivity.this.mStyles.get(i));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return UploadActivity.this.mStyles.size();
        }
    }

    public class StyleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Long mLastSelectedStyleId;
        Style mStyle;
        LottieAnimationView progressss;
        ImageView selectorImageView;
        ImageView styleImageView;
        TextView title;
        RelativeLayout transparent;
        private ColorDrawable[] vibrantLightColorList = {new ColorDrawable(Color.parseColor("#EBDED6"))};

        class C03751 implements Runnable {
            final SubmissionResult val$cachedResult;

            class C03741 implements Runnable {
                C03741() {
                }

                public void run() {
                    UploadActivity.this.mPreviewView.setImageBitmap(UploadActivity.this.mBitmapResult);
                    Log.e("aaaaa", "Gaurav 333 ");
                    UploadActivity.this.mUploadProgressView.setVisibility(View.GONE);
                    UploadActivity.this.mPreviewView.setVisibility(View.VISIBLE);
                    UploadActivity.this.dialog.cancel();
                }
            }

            C03751(SubmissionResult submissionResult) {
                this.val$cachedResult = submissionResult;
            }

            public void run() {
                try {
                    UploadActivity.this.mBitmapResult = Picasso.with(UploadActivity.this).load(this.val$cachedResult.filePath).get();
                    UploadActivity.this.mBitmapResultGenerated = UploadActivity.this.mBitmapResult;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UploadActivity.this.runOnUiThread(new C03741());
            }
        }

        public StyleHolder(View view) {
            super(view);
            this.styleImageView = (ImageView) view.findViewById(R.id.style);
            this.progressss = (LottieAnimationView) view.findViewById(R.id.progressss);
            this.selectorImageView = (ImageView) view.findViewById(R.id.selector);
            this.title = (TextView) view.findViewById(R.id.title);
            this.transparent = (RelativeLayout) view.findViewById(R.id.transparent);
            view.setOnClickListener(this);
        }

        public void bindSubmission(Style style) {
            this.mStyle = style;
            SubmissionResult submissionResult = UploadActivity.this.mResultCache.get(style.id);
            this.title.setText(this.mStyle.title);
            Log.d("mytitle", this.mStyle.title);
            if (submissionResult != null) {
                Picasso.with(UploadActivity.this).load(submissionResult.filePath).into(new Target() {
                    /* class com.example.photoareditor.Activity.UploadActivity.StyleHolder.AnonymousClass1 */

                    @Override // com.squareup.picasso.Target
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        StyleHolder.this.styleImageView.setImageBitmap(bitmap);
                        Log.e("aaaaa", "bindSubmission: 1111");
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override // com.squareup.picasso.Target
                    public void onPrepareLoad(Drawable drawable) {
                        StyleHolder.this.styleImageView.setImageBitmap(BitmapFactory.decodeResource(UploadActivity.this.getResources(), R.mipmap.ic_launcher));
                        Log.e("aaaaa", "bindSubmission: 2222");
                    }
                });
            } else {
                this.progressss.setVisibility(View.VISIBLE);
                Glide.with(UploadActivity.this.mActivity).load("https://www.deeparteffects.com//images/styles/" + style.filePath).thumbnail(new RequestBuilder[0]).listener(new RequestListener<Drawable>() {
                    /* class com.example.photoareditor.Activity.UploadActivity.StyleHolder.AnonymousClass2 */

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        StyleHolder.this.progressss.setVisibility(View.GONE);
                        return false;
                    }

                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        StyleHolder.this.progressss.setVisibility(View.GONE);
                        return false;
                    }
                }).into(this.styleImageView);
                Log.e("aaaaa", "bindSubmission: 3333");
            }
            if (UploadActivity.this.mResultCache.containsKey(this.mStyle.id)) {
                this.mStyle.isProcessed = true;
            }
            if (this.mStyle.isSelected) {
                this.selectorImageView.setVisibility(View.VISIBLE);
                this.transparent.setVisibility(View.VISIBLE);
                if (this.mStyle.isProcessed) {
                    this.selectorImageView.setImageResource(R.drawable.ic_art_fader);
                    this.transparent.setVisibility(View.VISIBLE);
                    return;
                }
                this.selectorImageView.setImageResource(R.drawable.ic_art_fader);
                this.transparent.setVisibility(View.VISIBLE);
                return;
            }
            this.selectorImageView.setVisibility(View.GONE);
            this.transparent.setVisibility(View.GONE);
        }

        public ColorDrawable getRandomDrawbleColor() {
            return this.vibrantLightColorList[new Random().nextInt(this.vibrantLightColorList.length)];
        }

        private void setSelection(int i) {
            for (Style style : UploadActivity.this.mStyles) {
                style.isSelected = false;
            }
            UploadActivity.this.mStyles.get(i).isSelected = true;
            UploadActivity.this.mStyleImageAdapter.notifyDataSetChanged();
        }

        public void onClick(View view) {
            UploadActivity.this.mFilterIntensitySeekBar.setVisibility(View.INVISIBLE);
            UploadActivity.this.mFilterIntensitySeekBar.setProgress(100);
            Log.d(UploadActivity.TAG, "onClick");
            if (this.mStyle != null && !UploadActivity.this.isProcessing) {
                this.mLastSelectedStyleId = UploadActivity.this.mSelectedStyleId;
                UploadActivity.this.mSelectedStyleId = this.mStyle.id;
                Long l = this.mStyle.id;
                setSelection(getAdapterPosition());
                SubmissionResult submissionResult = UploadActivity.this.mResultCache.get(this.mStyle.id);
                if (submissionResult != null) {
                    UploadActivity.this.mFilterIntensitySeekBar.setProgress(100);
                    UploadActivity.this.mUploadProgressView.setVisibility(View.GONE);
                    UploadActivity.this.dialog.show();
                    UploadActivity.this.mPreviewView.setVisibility(View.VISIBLE);
                    new Thread(new C03751(submissionResult)).start();
                    if (this.mLastSelectedStyleId == UploadActivity.this.mSelectedStyleId) {
                        UploadActivity.this.mFilterIntensityLayout.setVisibility(View.GONE);
                        UploadActivity.this.mFilterIntensitySeekBar.setVisibility(View.VISIBLE);
                        UploadActivity.this.mChooseStyleLayout.setVisibility(View.VISIBLE);
                    }
                } else if (UploadActivity.this.mSettingsProvider.useHighQuality()) {
                    UploadActivity.this.uploadImage(l, Submission.Type.PREMIUM);
                } else {
                    UploadActivity.this.uploadImage(l, Submission.Type.NORMAL);
                }
            }
        }
    }

    public class AnonymousClass13 extends AsyncHttpResponseHandler {
        final Long val$styleId;
        final Submission.Type val$type;

        AnonymousClass13(Long l, Submission.Type type) {
            this.val$styleId = l;
            this.val$type = type;
        }

        @Override // com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            Log.d(UploadActivity.TAG, "upload statusCode: " + i);
            try {
                new Timer().scheduleAtFixedRate(new CheckForResultTask(Long.valueOf(new JSONObject(new String(bArr)).getLong("data")), this.val$styleId, this.val$type), 2500, 2500);
            } catch (JSONException e) {
            }
        }

        @Override // com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            Log.d(UploadActivity.TAG, "upload statusCode: " + i);
            UploadActivity.this.isProcessing = false;
            UploadActivity.this.mUploadProgressView.setVisibility(View.GONE);
            UploadActivity.this.dialog.cancel();
            UploadActivity.this.mStatusText.setVisibility(View.GONE);
            UploadActivity.this.mPreviewView.setVisibility(View.VISIBLE);
            Toast.makeText(UploadActivity.this.mActivity, UploadActivity.this.getText(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(9);
        getWindow().requestFeature(9);
        setContentView(R.layout.activity_upload);
        getWindow().addFlags(128);
        SettingsProvider settingsProvider = new SettingsProvider(this);
        this.mSettingsProvider = settingsProvider;
        settingsProvider.setPremium(true);
        this.mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        this.mResultCache = new HashMap<>();
        this.mPhotoUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        this.mToken = getIntent().getStringExtra("token");
        Bitmap loadSizeLimitedBitmapFromUri = ImageHelper.loadSizeLimitedBitmapFromUri(this.mPhotoUri, getContentResolver(), 1920);
        this.mPhotoBitmap = loadSizeLimitedBitmapFromUri;
        if (loadSizeLimitedBitmapFromUri == null) {
            Toast.makeText(this, getText(R.string.error_general), Toast.LENGTH_SHORT);
            finish();
        }
        this.mChooseStyleLayout = findViewById(R.id.chooseStyleLayout);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.filter_intensity_layout);
        this.mFilterIntensityLayout = relativeLayout;
        relativeLayout.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.status_text);
        this.mStatusText = textView;
        textView.setVisibility(View.GONE);
        SeekBar seekBar = (SeekBar) findViewById(R.id.filter_intensity_seek_bar);
        this.mFilterIntensitySeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new C03591());
        findViewById(R.id.btnApply).setOnClickListener(new C03602());
        findViewById(R.id.btnCancel).setOnClickListener(new C03613());
        this.mActivity = this;
        ImageView imageView = (ImageView) findViewById(R.id.preview);
        this.mPreviewView = imageView;
        Glide.with((FragmentActivity) this).load(this.mPhotoUri).into(imageView);
        this.mStyleProgressView = (LottieAnimationView) findViewById(R.id.load_style_progress);
        this.mUploadProgressView = (ProgressBar) findViewById(R.id.upload_progress);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mActivity, RecyclerView.HORIZONTAL, false));
        TextView imageView3 = (TextView) findViewById(R.id.btnSave);
        this.mSaveBtn = imageView3;
        imageView3.setVisibility(View.GONE);
        Dialog dialog2 = new Dialog(this, R.style.full_screen_dialog1);
        this.dialog = dialog2;
        dialog2.setContentView(R.layout.art_dialog);
        this.dialog_status_text = (TextView) this.dialog.findViewById(R.id.dialog_status_text);
        this.dialog.setCancelable(false);
        this.mSaveBtn.setOnClickListener(new View.OnClickListener() {
            /* class com.example.photoareditor.Activity.UploadActivity.AnonymousClass1 */

            public void onClick(View view) {
                new Bundle().putString("action", "save");
                Utils.saveSubmission(UploadActivity.this.mActivity, UploadActivity.this.getBitmapResult());
                Toast.makeText(UploadActivity.this.mActivity, UploadActivity.this.getResources().getString(R.string.save_hint), Toast.LENGTH_SHORT).show();
            }
        });
        this.mStyleProgressView.setVisibility(View.VISIBLE);
        SessionHandler sessionHandler2 = new SessionHandler(this);
        this.sessionHandler = sessionHandler2;
        sessionHandler2.getSessionToken(new C05718(sessionHandler2));
    }

    private Bitmap getBitmapResult() {
        if (this.mSettingsProvider.useWatermark()) {
            return Utils.addWatermark(this.mActivity, this.mBitmapResult);
        }
        return this.mBitmapResult;
    }

    private int getImageMaxSideLength() {
        this.mSettingsProvider.useHighQuality();
        return 1920;
    }

    private void applyFilter(int i) {
        if (this.mResultCache.get(this.mSelectedStyleId) != null && this.mBitmapResult != null) {
            Bitmap applyFilter = Utils.applyFilter(this.mPhotoBitmap, this.mBitmapResultGenerated, i);
            this.mBitmapResult = applyFilter;
            this.mPreviewView.setImageBitmap(applyFilter);
            Log.e("aaaaa", "Gaurav 444 ");
        }
    }


    private void loadStyles() {
        this.mStyles = new ArrayList();
        ServiceApi.getStyles(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                try {
                    JSONArray jSONArray = new JSONObject(new String(bArr, "UTF-8")).getJSONArray("data");
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        UploadActivity.this.mStyles.add(Style.fromJsonObject(jSONArray.getJSONObject(i2)));
                    }
                    UploadActivity.this.mStyleImageAdapter = new StyleAdapter();
                    UploadActivity.this.mRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(UploadActivity.this, R.anim.layout_animation_left_to_right));
                    UploadActivity.this.mRecyclerView.setAdapter(UploadActivity.this.mStyleImageAdapter);
                    UploadActivity.this.mStyleProgressView.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                Toast.makeText(UploadActivity.this.mActivity, UploadActivity.this.getText(R.string.error_network), Toast.LENGTH_SHORT).show();
                UploadActivity.this.mStyleProgressView.setVisibility(View.GONE);
            }
        }, this.mSettingsProvider.hasPremium());
    }

    private void uploadImage(Long l, Submission.Type type) {
        Bitmap bitmap;
        this.mUploadProgressView.setVisibility(View.GONE);
        this.dialog.show();
        this.mStatusText.setVisibility(View.GONE);
        this.mStatusText.setText(getText(R.string.processing_hint));
        this.dialog_status_text.setText(getText(R.string.processing_hint));
        this.mPreviewView.setVisibility(View.VISIBLE);
        this.isProcessing = true;
        invalidateOptionsMenu();
        if (type.equals(Submission.Type.PRINT)) {
            bitmap = ImageHelper.loadSizeLimitedBitmapFromUri(this.mPhotoUri, getContentResolver(), 1920);
        } else {
            bitmap = ImageHelper.loadSizeLimitedBitmapFromUri(this.mPhotoUri, getContentResolver(), getImageMaxSideLength());
        }
        ServiceApi.uploadV2(this.mActivity, bitmap, this.mSessionToken, this.mPhotoUri, null, 16L, this.mToken, l, type, new AnonymousClass13(l, type));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_menu, menu);
        this.item = menu.findItem(R.id.menu_item_crop);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            Toast.makeText(this.mActivity, "back", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        } else if (itemId != R.id.menu_item_crop) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            CropImage.activity(this.mPhotoUri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            return true;
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        Log.d(TAG, "onActivityResult(" + i + "," + i2 + "," + intent);
        super.onActivityResult(i, i2, intent);
        if (i == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1) {
                Uri uri = activityResult.getUri();
                this.mPhotoUri = uri;
                this.mPhotoBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(uri, getContentResolver(), 1920);
                this.mPreviewView.setImageURI(this.mPhotoUri);
                Log.e("aaaaa", "Gaurav 555 ");
            }
        }
    }
}