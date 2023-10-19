package com.example.photoeditor.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Adapter.NewArtAdapter;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.ModelClass.ArtData;
import com.example.photoeditor.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class All_Art_Images extends AppCompatActivity {
    private NetworkChangeReceiver brd;
    ArrayList<ArtData> list = new ArrayList<>();
    NewArtAdapter newArtAdapter;
    RelativeLayout no_internet_rl;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_art_images);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.no_internet_rl = (RelativeLayout) findViewById(R.id.no_internet_rl);
        String str = getIntent().getStringExtra("videoList");
        Type typeOfObjectsList = new TypeToken<ArrayList<ArtData>>() { // from class: com.example.photoareditor.Activity.All_Art_Images.1
        }.getType();
        this.list = (ArrayList) new Gson().fromJson(str, typeOfObjectsList);
        Log.e("aaaaa", "onCreate: list size = " + this.list.size());
        if (Utils.isOnline(this)) {
            setRecyclerView();
            return;
        }
        try {
            if (!Utils.isOnline(this)) {
                brodCarst(this);
                this.no_internet_rl.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("gauravvv", "Error" + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRecyclerView() {
        if (this.list.size() == 0) {
            onBackPressed();
            return;
        }
        this.newArtAdapter = new NewArtAdapter(this, this.list);
        this.recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation));
        this.recyclerView.setAdapter(this.newArtAdapter);
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
                    All_Art_Images.this.no_internet_rl.setVisibility(View.GONE);
                    All_Art_Images.this.setRecyclerView();
                }
            } catch (Exception e) {
                Log.e("kamlesh", "Error" + e.getMessage());
            }
        }
    }
}
