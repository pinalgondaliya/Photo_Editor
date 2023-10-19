package com.example.photoeditor.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.photoeditor.Adapter.Art_Main_Adapter;
import com.example.photoeditor.Adapter.MyPagerAdapter;
import com.example.photoeditor.BackdropViewAnimation;
import com.example.photoeditor.Classs.AutoScrollViewPager;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.ModelClass.ArtData;
import com.example.photoeditor.R;
import com.google.gson.Gson;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes7.dex */
public class MainActivity extends AppCompatActivity {
    RelativeLayout animation_rl;
    RelativeLayout art_effect;
    BackdropViewAnimation backdropAnimation;
    private NetworkChangeReceiver brd;
    ArrayList<ArtData> list = new ArrayList<>();
    RelativeLayout no_internet_rl;
    ImageView photo_arttt;
    RelativeLayout photo_collage;
    RelativeLayout photo_creation;
    RelativeLayout photo_drip;
    RelativeLayout photo_edit;
    RelativeLayout photo_motion;
    RelativeLayout photo_pixlab;
    LottieAnimationView photo_sketch;
    RelativeLayout photo_spiral;
    LottieAnimationView photo_splash;
    RecyclerView recyclerview;
    TextView setting_text;
    Toolbar toolbar;
    TextView txt_art_see_all;
    TextView version_code;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.art_effect = (RelativeLayout) findViewById(R.id.art_effect);
        this.photo_pixlab = (RelativeLayout) findViewById(R.id.photo_pixlab);
        this.photo_splash = (LottieAnimationView) findViewById(R.id.photo_splash);
        this.photo_edit = (RelativeLayout) findViewById(R.id.photo_edit);
        this.photo_motion = (RelativeLayout) findViewById(R.id.photo_motion);
        this.photo_arttt = (ImageView) findViewById(R.id.photo_arttt);
        this.photo_drip = (RelativeLayout) findViewById(R.id.photo_drip);
        this.photo_spiral = (RelativeLayout) findViewById(R.id.photo_spiral);
        this.photo_collage = (RelativeLayout) findViewById(R.id.photo_collage);
        this.photo_sketch = (LottieAnimationView) findViewById(R.id.photo_sketch);
        this.photo_creation = (RelativeLayout) findViewById(R.id.photo_creation);
        this.animation_rl = (RelativeLayout) findViewById(R.id.animation_rl);
        this.setting_text = (TextView) findViewById(R.id.setting_text);
        this.version_code = (TextView) findViewById(R.id.version_code);
        this.txt_art_see_all = (TextView) findViewById(R.id.txt_art_see_all);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.no_internet_rl = (RelativeLayout) findViewById(R.id.no_internet_rl);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        this.toolbar = toolbar;
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        this.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
//        setSupportActionBar(this.toolbar);
//        getSupportActionBar().setTitle("Photo Art Effect: Magic Editor");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().getCustomView();
        setSystemBarColor(this, R.color.white);
        setSystemBarLight(this);
        this.backdropAnimation = new BackdropViewAnimation(this, findViewById(R.id.backdrop_view), findViewById(R.id.product_grid), Integer.valueOf((int) R.drawable.ic_menu), Integer.valueOf((int) R.drawable.ic_close), Integer.valueOf((int) R.color.black));
//        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.1
//            @Override // android.view.View.OnClickListener
//            public void onClick(View view) {
//                MainActivity.this.animation_rl.setVisibility(View.VISIBLE);
//                MainActivity.this.animation_rl.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_down_to_up));
//                MainActivity.this.backdropAnimation.toggle(view);
//            }
//        });
        if (Utils.isOnline(this)) {
            getneondata();
        } else {
            try {
                if (!Utils.isOnline(this)) {
                    brodCarst(this);
                    this.no_internet_rl.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e("gauravvv", "Error" + e.getMessage());
            }
        }
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            this.version_code.setText("Version : " + version);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        this.setting_text.setSelected(true);
        AutoScrollViewPager vpPager = (AutoScrollViewPager) findViewById(R.id.viewpager);
        vpPager.startAutoScroll();
        vpPager.setInterval(3000L);
        vpPager.setCycle(true);
        vpPager.setStopScrollWhenTouch(true);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        DotsIndicator dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(vpPager);
        this.art_effect.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Utils.art = 1;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_edit.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Utils.editor = 2;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_drip.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.drip = 3;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_arttt.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.art_1 = 4;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_splash.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.splash = 5;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_motion.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.motion = 6;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_pixlab.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.pixlab = 7;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_collage.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GridPickerActivity.class);
                intent.putExtra(GridPickerActivity.KEY_LIMIT_MAX_IMAGE, 9);
                intent.putExtra(GridPickerActivity.KEY_LIMIT_MIN_IMAGE, 2);
                MainActivity.this.startActivityForResult(intent, 1001);
            }
        });
        this.photo_spiral.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.spiral = 9;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_sketch.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.sketch = 10;
                MainActivity.this.startActivity(new Intent(MainActivity.this, Image_GalleryActivity.class));
            }
        });
        this.photo_creation.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CreationActivity.class));
            }
        });
        this.txt_art_see_all.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_pressed));
        this.txt_art_see_all.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.MainActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent ii = new Intent(MainActivity.this, All_Art_Images.class);
                Gson gson = new Gson();
                String jData = gson.toJson(MainActivity.this.list);
                ii.putExtra("videoList", jData);
                MainActivity.this.startActivity(ii);
            }
        });
    }

    public static void setSystemBarColor(Activity activity, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(activity.getResources().getColor(i));
        }
    }

    public static void setSystemBarLight(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            @SuppressLint("ResourceType") View findViewById = activity.findViewById(16908290);
            findViewById.setSystemUiVisibility(findViewById.getSystemUiVisibility() | 8192);
        }
    }

    public void brodCarst(Context ctx) {
        try {
            this.brd = new NetworkChangeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            ctx.registerReceiver(this.brd, filter);
        } catch (Exception e) {
        }
    }

    /* loaded from: classes7.dex */
    public class NetworkChangeReceiver extends BroadcastReceiver {
        boolean c = true;

        public NetworkChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                if (this.c) {
                    this.c = false;
                }
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connMgr.getNetworkInfo(1);
                NetworkInfo mobile = connMgr.getNetworkInfo(0);
                if ((wifi.isAvailable() || mobile.isAvailable()) && Utils.isOnline(context)) {
                    MainActivity.this.no_internet_rl.setVisibility(View.GONE);
                    MainActivity.this.getneondata();
                }
            } catch (Exception e) {
                Log.e("kamlesh", "Error" + e.getMessage());
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.backdropAnimation.isBackdropShown()) {
            this.backdropAnimation.close();
        } else {
            super.onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getneondata() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(1, "https://www.photovideomakerwithmusic.com/photo_art_effect/wallpaper.php", new Response.Listener<String>() { // from class: com.example.photoareditor.Activity.MainActivity.14
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("Status");
                    if (status == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("wallpaper");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String art_thumb = Utils.main_activity_thumb + jsonObject1.getString("thumb");
                            String art_image = Utils.main_activity_thumb + jsonObject1.getString("image");
                            ArtData artData = new ArtData(art_thumb, art_image);
                            MainActivity.this.list.add(artData);
                        }
                        MainActivity.this.setRecyclerView();
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.example.photoareditor.Activity.MainActivity.15
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError error) {
            }
        }) { // from class: com.example.photoareditor.Activity.MainActivity.16
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("appkey", "nvn72Ktbcd");
                MyData.put("device", "oppo");
                MyData.put("ver", "7.7");
                return MyData;
            }
        };
        queue.add(stringRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRecyclerView() {
        Art_Main_Adapter art_main_adapter = new Art_Main_Adapter(this, this.list);
        this.recyclerview.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_left_to_right));
        this.recyclerview.setAdapter(art_main_adapter);
    }
}
