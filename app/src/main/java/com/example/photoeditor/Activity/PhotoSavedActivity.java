package com.example.photoeditor.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.example.photoeditor.R;

import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;

/* loaded from: classes7.dex */
public class PhotoSavedActivity extends AppCompatActivity {
    ImageView back;
    ImageView eyes;
    LinearLayout facebook;
    ImageView home;
    LinearLayout instagram;
    LinearLayout more;
    String path;
    ImageView saveImage;
    TextView save_path;
    private Uri screenshotUri;
    LinearLayout whatsapp;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_saved);
        this.saveImage = (ImageView) findViewById(R.id.saveImage);
        this.save_path = (TextView) findViewById(R.id.save_path);
        this.eyes = (ImageView) findViewById(R.id.eyes);
        this.back = (ImageView) findViewById(R.id.back);
        this.home = (ImageView) findViewById(R.id.home);
        this.whatsapp = (LinearLayout) findViewById(R.id.whatsapp);
        this.facebook = (LinearLayout) findViewById(R.id.facebook);
        this.instagram = (LinearLayout) findViewById(R.id.instagram);
        this.more = (LinearLayout) findViewById(R.id.more);
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/Photo Art Editor");
        this.save_path.setText(myDir.getPath());
        this.save_path.setSelected(true);
        Intent intent1 = getIntent();
        this.path = intent1.getStringExtra(ClientCookie.PATH_ATTR);
        Glide.with((FragmentActivity) this).load(this.path).into(this.saveImage);
        this.back.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PhotoSavedActivity.this.onBackPressed();
            }
        });
        this.home.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PhotoSavedActivity.this.startActivity(new Intent(PhotoSavedActivity.this, MainActivity.class));
            }
        });
        this.saveImage.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PhotoSavedActivity.this.eyes.setVisibility(View.GONE);
                final Dialog imagedialog = new Dialog(PhotoSavedActivity.this, R.style.full_screen_dialog1);
                imagedialog.requestWindowFeature(1);
                imagedialog.setCancelable(false);
                imagedialog.setContentView(R.layout.image_dialog);
                ImageView view_imagee = (ImageView) imagedialog.findViewById(R.id.view_imagee);
                ImageView close = (ImageView) imagedialog.findViewById(R.id.close);
                Glide.with((FragmentActivity) PhotoSavedActivity.this).load(PhotoSavedActivity.this.path).into(view_imagee);
                close.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        PhotoSavedActivity.this.eyes.setVisibility(0);
                        imagedialog.dismiss();
                    }
                });
                imagedialog.show();
            }
        });
        final boolean isfacebookAppInstalled = isAppInstalled(this, "com.facebook.katana");
        final boolean isinstagramAppInstalled = isAppInstalled(this, "com.instagram.android");
        final boolean iswhatsappAppInstalled = isAppInstalled(this, "com.whatsapp");
        this.facebook.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (isfacebookAppInstalled) {
                    PhotoSavedActivity photoSavedActivity = PhotoSavedActivity.this;
                    photoSavedActivity.ShareFile1(photoSavedActivity, "com.facebook.katana", photoSavedActivity.path);
                    return;
                }
                Toast.makeText(PhotoSavedActivity.this, "Facebook not installed", 0).show();
            }
        });
        this.whatsapp.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (iswhatsappAppInstalled) {
                    PhotoSavedActivity photoSavedActivity = PhotoSavedActivity.this;
                    photoSavedActivity.ShareFile1(photoSavedActivity, "com.whatsapp", photoSavedActivity.path);
                    return;
                }
                Toast.makeText(PhotoSavedActivity.this, "Whatsapp not installed", 0).show();
            }
        });
        this.instagram.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (isinstagramAppInstalled) {
                    PhotoSavedActivity photoSavedActivity = PhotoSavedActivity.this;
                    photoSavedActivity.ShareFile1(photoSavedActivity, "com.instagram.android", photoSavedActivity.path);
                    return;
                }
                Toast.makeText(PhotoSavedActivity.this, "Instagram not installed", 0).show();
            }
        });
        this.more.setOnClickListener(new AnonymousClass7());
    }

    /* renamed from: com.example.photoareditor.Activity.PhotoSavedActivity$7  reason: invalid class name */
    /* loaded from: classes7.dex */
    class AnonymousClass7 implements View.OnClickListener {
        AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            PhotoSavedActivity photoSavedActivity = PhotoSavedActivity.this;
            MediaScannerConnection.scanFile(photoSavedActivity, new String[]{photoSavedActivity.path}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.7.1
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public void onScanCompleted(final String path, final Uri uri) {
                    PhotoSavedActivity.this.runOnUiThread(new Runnable() { // from class: com.example.photoareditor.Activity.PhotoSavedActivity.7.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Intent sharingIntent = new Intent("android.intent.action.SEND");
                            sharingIntent.setType("image/jpeg");
                            sharingIntent.putExtra("android.intent.extra.SUBJECT", "Photo Art & Editor");
                            String abc = "Hey, Check it out awesome \"Photo Art & Editor\" App, Download it with using given link \nhttps://play.google.com/store/apps/details?id=" + PhotoSavedActivity.this.getPackageName();
                            sharingIntent.putExtra("android.intent.extra.TEXT", abc);
                            sharingIntent.setFlags(1);
                            Uri uri2 = uri;
                            if (uri2 == null) {
                                sharingIntent.putExtra("android.intent.extra.STREAM", path);
                            } else {
                                sharingIntent.putExtra("android.intent.extra.STREAM", uri2);
                            }
                            PhotoSavedActivity.this.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                        }
                    });
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void ShareFile1(Context context, String pckgname, String path) {
        this.screenshotUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", new File(path));
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("image/*");
        shareIntent.setPackage(pckgname);
        shareIntent.setFlags(1);
        shareIntent.putExtra("android.intent.extra.STREAM", this.screenshotUri);
        context.startActivity(shareIntent);
    }
}
