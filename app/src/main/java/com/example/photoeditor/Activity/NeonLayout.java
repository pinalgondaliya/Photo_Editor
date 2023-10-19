package com.example.photoeditor.Activity;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.photoeditor.Adapter.NeonAdapter;
import com.example.photoeditor.Adapter.ViewPagerAdapter;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.Constants;
import com.example.photoeditor.Classs.ImageUtils;
import com.example.photoeditor.Classs.MLCropAsyncTask;
import com.example.photoeditor.Classs.MLOnCropTaskCompleted;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.Fragment.SpiralFragment;
import com.example.photoeditor.Interface.LayoutItemListener;
import com.example.photoeditor.ModelClass.MainModel;
import com.example.photoeditor.NeonClass.MultiTouchListener;
import com.example.photoeditor.R;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import io.reactivex.common.annotations.SchedulerSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes7.dex */
public class NeonLayout extends AppCompatActivity implements LayoutItemListener, SpiralFragment.onFragmentListener {
    private static Bitmap faceBitmap;
    public static ImageView imageViewFont;
    public static Bitmap resultBitmap;
    private Bitmap bitmap2;
    ArrayList<MainModel.Datum> category;
    private Context context;
    private Bitmap cutBitmap;
    private File file;
    ImageView imageViewBack;
    ImageView imageViewBackground;
    ImageView imageViewCover;
    NeonAdapter neonAdapter;
    RecyclerView recyclerViewNeon;
    RelativeLayout relativeLayoutRootView;
    TextView save;
    SeekBar seekBarOpacity;
    Bitmap selectedBitmap;
    TabLayout tabs;
    TextView textViewFrame;
    TextView textViewShape;
    TextView textViewSpiral;
    ViewPager viewpager;
    public int count = 0;
    private int frameCount = 23;
    ArrayList<String> frameEffectList = new ArrayList<>();
    ArrayList<String> shapeEffectList = new ArrayList<>();
    ArrayList<String> neonEffectList = new ArrayList<>();
    boolean isFirst = true;
    int neonCount = 31;
    int shapeCount = 45;
    int pos = 0;

    public static void setFaceBitmap(Bitmap bitmap) {
        faceBitmap = bitmap;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.layout_neon);
        this.context = this;
        this.selectedBitmap = faceBitmap;
        this.tabs = (TabLayout) findViewById(R.id.tabs);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.save = (TextView) findViewById(R.id.save);
        initViews();
        getneondata();
        this.viewpager.setOffscreenPageLimit(5);
        this.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabs));
        this.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.example.photoareditor.Activity.NeonLayout.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                NeonLayout.this.viewpager.setCurrentItem(tab.getPosition());
                NeonLayout.this.pos = tab.getPosition();
                Log.e("aaaaaaaa", "onTabSelected: tab = " + tab.getPosition());
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: com.example.photoareditor.Activity.NeonLayout.2
            @Override // java.lang.Runnable
            public void run() {
                NeonLayout.this.imageViewCover.post(new Runnable() { // from class: com.example.photoareditor.Activity.NeonLayout.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (NeonLayout.this.isFirst && NeonLayout.this.selectedBitmap != null) {
                            NeonLayout.this.isFirst = false;
                            NeonLayout.this.initBitmap();
                        }
                    }
                });
            }
        }, 1000L);
        this.neonEffectList.add(SchedulerSupport.NONE);
        this.shapeEffectList.add(SchedulerSupport.NONE);
        this.frameEffectList.add(SchedulerSupport.NONE);
        for (int i = 1; i <= this.neonCount; i++) {
            ArrayList<String> arrayList = this.neonEffectList;
            arrayList.add("line_" + i);
        }
        for (int i2 = 1; i2 <= this.shapeCount; i2++) {
            ArrayList<String> arrayList2 = this.shapeEffectList;
            arrayList2.add("shape_" + i2);
        }
        for (int i3 = 1; i3 <= this.frameCount; i3++) {
            ArrayList<String> arrayList3 = this.frameEffectList;
            arrayList3.add("frame_" + i3);
        }
        this.save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Utils.process) {
                    NeonLayout.this.saveImage();
                } else {
                    Toast.makeText(NeonLayout.this.context, "Please Wait...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImage() {
        this.relativeLayoutRootView.setDrawingCacheEnabled(true);
        this.relativeLayoutRootView.buildDrawingCache();
        this.bitmap2 = this.relativeLayoutRootView.getDrawingCache();
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
            this.relativeLayoutRootView.destroyDrawingCache();
            MediaScannerConnection.scanFile(this, new String[]{this.file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.NeonLayout.4
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public void onScanCompleted(String path2, Uri uri) {
                    NeonLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.NeonLayout.4.1
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
            this.relativeLayoutRootView.destroyDrawingCache();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBitmap() {
        ImageView imageView;
        Bitmap bitmap = faceBitmap;
        if (bitmap != null) {
            this.selectedBitmap = ImageUtils.getBitmapResize(this.context, bitmap, this.imageViewCover.getWidth(), this.imageViewCover.getHeight());
            this.relativeLayoutRootView.setLayoutParams(new LinearLayout.LayoutParams(this.selectedBitmap.getWidth(), this.selectedBitmap.getHeight()));
            Bitmap bitmap2 = this.selectedBitmap;
            if (bitmap2 != null && (imageView = this.imageViewBackground) != null) {
                imageView.setImageBitmap(bitmap2);
            }
            setStartNeon();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1024 && (bitmap = resultBitmap) != null) {
            this.cutBitmap = bitmap;
            this.imageViewCover.setImageBitmap(bitmap);
        }
    }

    public void initViews() {
        this.relativeLayoutRootView = (RelativeLayout) findViewById(R.id.mContentRootView);
        imageViewFont = (ImageView) findViewById(R.id.imageViewFont);
        this.imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        this.imageViewBackground = (ImageView) findViewById(R.id.imageViewBackground);
        this.imageViewCover = (ImageView) findViewById(R.id.imageViewCover);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLine);
        this.recyclerViewNeon = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false));
        seAdapterList();
        this.textViewSpiral = (TextView) findViewById(R.id.text_view_spiral);
        this.textViewShape = (TextView) findViewById(R.id.text_view_shape);
        this.textViewFrame = (TextView) findViewById(R.id.text_view_frame);
        this.seekBarOpacity = (SeekBar) findViewById(R.id.seekbarOpacity);
        findViewById(R.id.text_view_spiral).performClick();
        this.imageViewBackground.setRotationY(0.0f);
        this.imageViewCover.post(new Runnable() { // from class: com.example.photoareditor.Activity.NeonLayout.5
            @Override // java.lang.Runnable
            public void run() {
                NeonLayout.this.initBitmap();
            }
        });
        this.seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Activity.NeonLayout.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (NeonLayout.this.imageViewBack != null && NeonLayout.imageViewFont != null) {
                    float f = i * 0.01f;
                    NeonLayout.this.imageViewBack.setAlpha(f);
                    NeonLayout.imageViewFont.setAlpha(f);
                }
            }
        });
        findViewById(R.id.imageViewCloseNeon).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NeonLayout.this.onBackPressed();
            }
        });
        findViewById(R.id.imageViewSaveNeon).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new saveFile().execute(new String[0]);
            }
        });
        findViewById(R.id.text_view_spiral).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NeonLayout.this.neonAdapter.addData(NeonLayout.this.neonEffectList);
                NeonLayout.this.textViewSpiral.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.white));
                NeonLayout.this.textViewSpiral.setBackgroundResource(R.drawable.background_selected);
                NeonLayout.this.textViewShape.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.black));
                NeonLayout.this.textViewShape.setBackgroundResource(R.drawable.background_unslelected);
                NeonLayout.this.textViewFrame.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.black));
                NeonLayout.this.textViewFrame.setBackgroundResource(R.drawable.background_unslelected);
            }
        });
        findViewById(R.id.text_view_shape).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NeonLayout.this.neonAdapter.addData(NeonLayout.this.shapeEffectList);
                NeonLayout.this.textViewShape.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.white));
                NeonLayout.this.textViewShape.setBackgroundResource(R.drawable.background_selected);
                NeonLayout.this.textViewSpiral.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.black));
                NeonLayout.this.textViewSpiral.setBackgroundResource(R.drawable.background_unslelected);
                NeonLayout.this.textViewFrame.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.black));
                NeonLayout.this.textViewFrame.setBackgroundResource(R.drawable.background_unslelected);
            }
        });
        findViewById(R.id.text_view_frame).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NeonLayout.this.neonAdapter.addData(NeonLayout.this.frameEffectList);
                NeonLayout.this.textViewFrame.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.white));
                NeonLayout.this.textViewFrame.setBackgroundResource(R.drawable.background_selected);
                NeonLayout.this.textViewShape.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.black));
                NeonLayout.this.textViewShape.setBackgroundResource(R.drawable.background_unslelected);
                NeonLayout.this.textViewSpiral.setTextColor(ContextCompat.getColor(NeonLayout.this, R.color.black));
                NeonLayout.this.textViewSpiral.setBackgroundResource(R.drawable.background_unslelected);
            }
        });
        findViewById(R.id.image_view_eraser).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.NeonLayout.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StickerEraseActivity.b = NeonLayout.this.selectedBitmap;
                Intent intent = new Intent(NeonLayout.this, StickerEraseActivity.class);
                intent.putExtra(Constants.KEY_OPEN_FROM, Constants.VALUE_OPEN_FROM_NEON);
                NeonLayout.this.startActivityForResult(intent, 1024);
            }
        });
    }

    public void seAdapterList() {
        NeonAdapter neonAdapter2 = new NeonAdapter(this.context);
        this.neonAdapter = neonAdapter2;
        neonAdapter2.setLayoutItenListener(this);
        this.recyclerViewNeon.setAdapter(this.neonAdapter);
        this.neonAdapter.addData(this.neonEffectList);
    }

    @Override // com.example.photoareditor.Interface.LayoutItemListener
    public void onLayoutListClick(View view, int i) {
        if (i != 0) {
            Context context2 = this.context;
            Bitmap bitmapFromAsset = ImageUtils.getBitmapFromAsset(context2, "neon/back/" + this.neonAdapter.getItemList().get(i) + "_back.webp");
            Context context3 = this.context;
            Bitmap bitmapFromAsset2 = ImageUtils.getBitmapFromAsset(context3, "neon/front/" + this.neonAdapter.getItemList().get(i) + "_front.webp");
            this.imageViewBack.setImageBitmap(bitmapFromAsset);
            imageViewFont.setImageBitmap(bitmapFromAsset2);
        } else {
            this.imageViewBack.setImageResource(0);
            imageViewFont.setImageResource(0);
        }
        this.imageViewBack.setOnTouchListener(new MultiTouchListener(this, true));
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.example.photoareditor.Activity.NeonLayout$13] */
    public void setStartNeon() {
        Utils.process = false;
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.crop_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        new CountDownTimer(5000L, 1000L) { // from class: com.example.photoareditor.Activity.NeonLayout.13
            @Override // android.os.CountDownTimer
            public void onFinish() {
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                NeonLayout.this.count++;
                if (progressBar.getProgress() <= 90) {
                    progressBar.setProgress(NeonLayout.this.count * 5);
                }
            }
        }.start();
        new MLCropAsyncTask(new MLOnCropTaskCompleted() { // from class: com.example.photoareditor.Activity.NeonLayout.14
            @Override // com.example.photoareditor.Classs.MLOnCropTaskCompleted
            public void onTaskCompleted(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
                Utils.process = true;
                NeonLayout.this.selectedBitmap.getWidth();
                NeonLayout.this.selectedBitmap.getHeight();
                int width = NeonLayout.this.selectedBitmap.getWidth();
                int height = NeonLayout.this.selectedBitmap.getHeight();
                int i3 = width * height;
                NeonLayout.this.selectedBitmap.getPixels(new int[i3], 0, width, 0, 0, width, height);
                int[] iArr = new int[i3];
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                NeonLayout neonLayout = NeonLayout.this;
                neonLayout.cutBitmap = ImageUtils.getMask(neonLayout.context, NeonLayout.this.selectedBitmap, createBitmap, width, height);
                NeonLayout neonLayout2 = NeonLayout.this;
                neonLayout2.cutBitmap = Bitmap.createScaledBitmap(bitmap, neonLayout2.cutBitmap.getWidth(), NeonLayout.this.cutBitmap.getHeight(), false);
                NeonLayout.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.NeonLayout.14.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Palette.from(NeonLayout.this.cutBitmap).generate().getDominantSwatch() == null) {
                            Toast.makeText(NeonLayout.this, NeonLayout.this.getString(R.string.txt_not_detect_human), Toast.LENGTH_SHORT).show();
                        }
                        NeonLayout.this.imageViewCover.setImageBitmap(NeonLayout.this.cutBitmap);
                    }
                });
            }
        }, this, progressBar).execute(new Void[0]);
    }

    @Override // com.example.photoareditor.Fragment.SpiralFragment.onFragmentListener
    public void fragmentClick(int position) {
        List<MainModel.Datum.Category_Model> list = this.category.get(this.pos).cat_array;
        String substr_front = list.get(position).front.substring(list.get(position).front.lastIndexOf("/"));
        String substr_1 = substr_front.substring(1, substr_front.length());
        String substr_back = list.get(position).back.substring(list.get(position).back.lastIndexOf("/"));
        String substr_2 = substr_back.substring(1, substr_back.length());
        File file = new File("/data/user/0/" + getPackageName() + "/" + this.category.get(this.pos).category_name);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file.getAbsolutePath() + "/" + substr_1);
        File file2 = new File(file.getAbsolutePath() + "/" + substr_2);
        Bitmap bitmapFromAsset = getBitmap(file1.getAbsolutePath());
        Bitmap bitmapFromAsset2 = getBitmap(file2.getAbsolutePath());
        Log.e("aaaaaa", "fragmentClick: bitmapFromAsset = " + bitmapFromAsset);
        Log.e("aaaaaa", "fragmentClick: bitmapFromAsset2 =  " + bitmapFromAsset2);
        this.imageViewBack.setImageBitmap(bitmapFromAsset2);
        imageViewFont.setImageBitmap(bitmapFromAsset);
        this.imageViewBack.setOnTouchListener(new MultiTouchListener(this, true));
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
            NeonLayout.this.relativeLayoutRootView.setDrawingCacheEnabled(true);
            Bitmap bitmapFromView = getBitmapFromView(NeonLayout.this.relativeLayoutRootView);
            NeonLayout.this.relativeLayoutRootView.setDrawingCacheEnabled(false);
            return bitmapFromView;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                BitmapTransfer.bitmap = bitmap;
            }
            Intent intent = new Intent(NeonLayout.this, EditorActivity.class);
            intent.putExtra("MESSAGE", "done");
            NeonLayout.this.setResult(-1, intent);
            NeonLayout.this.finish();
        }
    }

    private void getneondata() {
        this.category = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(1, "https://photovideomakerwithmusic.com/photo_art_editor/data.php", new Response.Listener<String>() { // from class: com.example.photoareditor.Activity.NeonLayout.15
            @Override // com.android.volley.Response.Listener
            public void onResponse(String response) {
                Log.e("gaurav", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt(NotificationCompat.CATEGORY_STATUS);
                    if (status == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        MainModel mainModel = new MainModel();
                        mainModel.data = new ArrayList<>();
                        int i = 0;
                        while (i < jsonArray.length()) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            MainModel.Datum datum = new MainModel.Datum();
                            datum.category_name = jsonObject1.getString("category_name");
                            NeonLayout.this.tabs.addTab(NeonLayout.this.tabs.newTab().setText(datum.category_name));
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("cat_array");
                            mainModel.data.add(datum);
                            datum.cat_array = new ArrayList<>();
                            int j = 0;
                            while (j < jsonArray1.length()) {
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                MainModel.Datum.Category_Model category_model = new MainModel.Datum.Category_Model();
                                category_model.id = jsonObject2.getString("id");
                                category_model.thumb = Utils.spiral_path + jsonObject2.getString("thumb");
                                category_model.front = Utils.spiral_path + jsonObject2.getString("front");
                                category_model.back = Utils.spiral_path + jsonObject2.getString("back");
                                datum.cat_array.add(category_model);
                                j++;
                                jsonObject = jsonObject;
                            }
                            NeonLayout.this.category.add(datum);
                            Log.e("gaurav", "onResponse: " + NeonLayout.this.category.size());
                            i++;
                            jsonObject = jsonObject;
                        }
                        Log.e("aaaa", "onResponse: tabs = " + NeonLayout.this.tabs.getTabCount());
                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(NeonLayout.this.getSupportFragmentManager(), NeonLayout.this.tabs.getTabCount(), NeonLayout.this.category);
                        NeonLayout.this.viewpager.setAdapter(viewPagerAdapter);
                        NeonLayout.this.viewpager.setOffscreenPageLimit(5);
                        NeonLayout.this.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(NeonLayout.this.tabs));
                        NeonLayout.this.viewpager.setCurrentItem(0);
                    }
                } catch (JSONException jsonException) {
                    Log.e("gaurav", "onResponse: " + jsonException.getMessage());
                    jsonException.printStackTrace();
                }
                Log.e("error404", response);
            }
        }, new Response.ErrorListener() { // from class: com.example.photoareditor.Activity.NeonLayout.16
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError error) {
                Log.e("gaurav", "VolleyError = " + error);
            }
        }) { // from class: com.example.photoareditor.Activity.NeonLayout.17
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("appkey", "GanTzIOadi707");
                MyData.put("ver", "9.1");
                MyData.put("device", "onepiece");
                MyData.put("category", "all");
                return MyData;
            }
        };
        queue.add(stringRequest);
    }

    public Bitmap getBitmap(String path) {
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("rrr", "getBitmap: " + e.getMessage());
            return null;
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (Utils.process) {
            super.onBackPressed();
        } else {
            Toast.makeText(this.context, "Please Wait...", Toast.LENGTH_SHORT).show();
        }
    }
}
