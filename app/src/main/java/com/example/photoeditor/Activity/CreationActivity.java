package com.example.photoeditor.Activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.CreationAdapter;
import com.example.photoeditor.ModelClass.GalleryData;
import com.example.photoeditor.ModelClass.pictureFacer;
import com.example.photoeditor.R;

import java.io.File;
import java.util.ArrayList;

/* loaded from: classes7.dex */
public class CreationActivity extends AppCompatActivity {
    public static ArrayList<GalleryData> newList = new ArrayList<>();
    private static LinearLayout no_image;
    private ArrayList<GalleryData> DCIMFolderList;
    ImageView back;
    RecyclerView listview;
    private String str;
    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    File myDir = new File(this.root + "/Photo Art Editor");

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        this.back = (ImageView) findViewById(R.id.back);
        this.listview = (RecyclerView) findViewById(R.id.creationrecyle);
        no_image = (LinearLayout) findViewById(R.id.no_imag);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.listview.setLayoutManager(gridLayoutManager);
        String root_name = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        this.back.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.CreationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CreationActivity.this.onBackPressed();
            }
        });
        String targetPath = root_name + "/Photo Art Editor/";
        this.DCIMFolderList = new ArrayList<>();
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file1 = new File(targetPath);
        System.out.println("file12==" + file1);
        this.DCIMFolderList = imageReader(file1);
        newList.clear();
        newList.addAll(this.DCIMFolderList);
        if (newList.isEmpty()) {
            no_image.setVisibility(View.VISIBLE);
        }
        if (this.str != null) {
            getEdited(this.myDir.getAbsolutePath());
        }
    }

    private ArrayList<GalleryData> imageReader(File root) {
        ArrayList<GalleryData> a = new ArrayList<>();
        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    a.addAll(imageReader(file));
                } else if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) {
                    GalleryData galleryData = new GalleryData();
                    galleryData.setFile(file);
                    galleryData.setPath(file.getPath());
                    this.str = file.getPath();
                    a.add(galleryData);
                }
            }
        }
        return a;
    }

    public ArrayList<pictureFacer> getEdited(String path) {
        ArrayList<pictureFacer> images = new ArrayList<>();
        Uri allVideosuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {"_data", "_display_name", "_size"};
        Cursor cursor = getContentResolver().query(allVideosuri, projection, "_data like ? ", new String[]{"%" + path + "%"}, null);
        try {
            cursor.moveToFirst();
            do {
                pictureFacer pic = new pictureFacer();
                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow("_display_name")));
                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow("_data")));
                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow("_size")));
                images.add(pic);
            } while (cursor.moveToNext());
            cursor.close();
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for (int i = images.size() - 1; i > -1; i--) {
                reSelection.add(images.get(i));
            }
            images = reSelection;
            CreationAdapter creationAdapter = new CreationAdapter(this, images);
            this.listview.setAdapter(creationAdapter);
            creationAdapter.notifyDataSetChanged();
            return images;
        } catch (Exception e) {
            e.printStackTrace();
            return images;
        }
    }

    public static void printlog(ArrayList<pictureFacer> listData) {
        if (listData.isEmpty()) {
            no_image.setVisibility(0);
        }
    }
}
