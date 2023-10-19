package com.example.photoeditor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.R;

import cz.msebera.android.httpclient.cookie.ClientCookie;

/* loaded from: classes7.dex */
public class ViewArtImages extends AppCompatActivity {
    ImageView imageView;
    CardView trynowbutton;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_art_images);
        this.imageView = (ImageView) findViewById(R.id.image);
        this.trynowbutton = (CardView) findViewById(R.id.trynowbutton);
        String path = getIntent().getStringExtra(ClientCookie.PATH_ATTR);
        Glide.with((FragmentActivity) this).load(path).thumbnail(0.2f).into(this.imageView);
        this.trynowbutton.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.ViewArtImages.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Utils.art = 1;
                ViewArtImages.this.startActivity(new Intent(ViewArtImages.this, Image_GalleryActivity.class));
            }
        });
    }
}
