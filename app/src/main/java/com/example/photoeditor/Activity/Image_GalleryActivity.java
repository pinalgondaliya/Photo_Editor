package com.example.photoeditor.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Adapter.FolderAdapter;
import com.example.photoeditor.Adapter.ImageAdapter;
import com.example.photoeditor.Classs.BitmapTransfer;
import com.example.photoeditor.Classs.StoreManager;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.ModelClass.BeacketBean;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: classes7.dex */
public class Image_GalleryActivity extends AppCompatActivity {
    public static int screenHeight;
    public static int screenWidth;
    ImageView closeScreen;
    TextView folder;
    FolderAdapter folderAdapter;
    ImageAdapter imageAdapter;
    long mLastClickTime;
    RecyclerView recycler_folders;
    RecyclerView recycler_photos;
    private ArrayList<BeacketBean> folderList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();
    String bucketId = "";
    boolean isfirst = true;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        viewBind();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels - Utils.dpToPx(this, 4);
        screenHeight = displayMetrics.heightPixels - Utils.dpToPx(this, 109);
    }

    private void viewBind() {
        this.recycler_photos = (RecyclerView) findViewById(R.id.gridViewImage);
        this.recycler_folders = (RecyclerView) findViewById(R.id.gridViewListAlbum);
        this.folder = (TextView) findViewById(R.id.title);
        this.closeScreen = (ImageView) findViewById(R.id.closeScreen);
        ArrayList<BeacketBean> imageBuckets = getImageBuckets(this);
        this.folderList = imageBuckets;
        if (imageBuckets == null) {
            this.folderList = new ArrayList<>();
        }
        Log.e("uvsss=", "viewBind: " + this.folderList.size());
        this.folderAdapter = new FolderAdapter(this, this.folderList, new FolderAdapter.FolderAdapterInterface() { // from class: com.example.photoareditor.Activity.Image_GalleryActivity.1
            @Override // com.example.photoareditor.Adapter.FolderAdapter.FolderAdapterInterface
            public void itemClick(int pos) {
                if (SystemClock.elapsedRealtime() - Image_GalleryActivity.this.mLastClickTime < 800) {
                    return;
                }
                Image_GalleryActivity.this.mLastClickTime = SystemClock.elapsedRealtime();
                Image_GalleryActivity image_GalleryActivity = Image_GalleryActivity.this;
                image_GalleryActivity.bucketId = ((BeacketBean) image_GalleryActivity.folderList.get(pos)).id;
                Image_GalleryActivity.this.imageList.clear();
                Image_GalleryActivity.this.recycler_folders.setVisibility(View.GONE);
                Image_GalleryActivity.this.recycler_photos.setVisibility(View.VISIBLE);
                Image_GalleryActivity.this.folder.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                Image_GalleryActivity.this.folder.setSingleLine(true);
                Image_GalleryActivity.this.folder.setMarqueeRepeatLimit(-1);
                Image_GalleryActivity.this.folder.setSelected(true);
                Image_GalleryActivity.this.folder.setText("" + ((BeacketBean) Image_GalleryActivity.this.folderList.get(pos)).name);
                new ImageRefresh().execute(new Void[0]);
            }
        });
        this.recycler_folders.setLayoutManager(new GridLayoutManager(this, 2));
        this.recycler_folders.setAdapter(this.folderAdapter);
        new FolderRefresh().execute(new Void[0]);
        this.closeScreen.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.Image_GalleryActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Image_GalleryActivity.this.onBackPressed();
            }
        });
    }

    private ArrayList<BeacketBean> getImageBuckets(Context context) {
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {"_data", "bucket_id", "bucket_display_name", "_id"};
        Cursor cursor = context.getContentResolver().query(images, projection, null, null, null);
        ArrayList<BeacketBean> buckets = new ArrayList<>();
        if (cursor == null) {
            return buckets;
        }
        while (cursor.moveToNext()) {
            if (this.isfirst) {
                this.isfirst = false;
                int DATAs = cursor.getColumnIndex("_data");
                BeacketBean beacketBean1 = new BeacketBean();
                beacketBean1.id = "";
                beacketBean1.name = "All Images";
                beacketBean1.cover = cursor.getString(DATAs);
                beacketBean1.imageId = "";
                beacketBean1.count = cursor.getCount();
                buckets.add(beacketBean1);
            }
            int bucketIdColumnIndex = cursor.getColumnIndex("bucket_id");
            int bucketColumnIndex = cursor.getColumnIndex("bucket_display_name");
            int DATA = cursor.getColumnIndex("_data");
            BeacketBean beacketBean = new BeacketBean();
            beacketBean.id = cursor.getString(bucketIdColumnIndex);
            beacketBean.name = cursor.getString(bucketColumnIndex);
            beacketBean.cover = cursor.getString(DATA);
            beacketBean.count = 0;
            beacketBean.imageId = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            if (beacketBean.name != null) {
                beacketBean.isCameraBucket = false;
                beacketBean.isImages = "IMAGES";
                if (beacketBean.name != null) {
                    buckets.add(beacketBean);
                }
            }
        }
        cursor.close();
        LinkedHashSet<BeacketBean> bucketsSet = new LinkedHashSet<>(buckets.size());
        bucketsSet.addAll(buckets);
        ArrayList<BeacketBean> tempBuckets = new ArrayList<>(bucketsSet.size());
        Iterator iterator = bucketsSet.iterator();
        while (iterator.hasNext()) {
            BeacketBean bucket = (BeacketBean) iterator.next();
            tempBuckets.add(bucket);
        }
        return tempBuckets;
    }

    /* loaded from: classes7.dex */
    public class ImageRefresh extends AsyncTask<Void, Void, Void> {
        public ImageRefresh() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            Image_GalleryActivity.this.imageList = new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voids) {
            Image_GalleryActivity image_GalleryActivity = Image_GalleryActivity.this;
            image_GalleryActivity.imageList = image_GalleryActivity.getAllImages();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Image_GalleryActivity image_GalleryActivity = Image_GalleryActivity.this;
            Image_GalleryActivity image_GalleryActivity2 = Image_GalleryActivity.this;
            image_GalleryActivity.imageAdapter = new ImageAdapter(image_GalleryActivity2, image_GalleryActivity2.imageList, new ImageAdapter.ImageAdapterInterface() { // from class: com.example.photoareditor.Activity.Image_GalleryActivity.ImageRefresh.1
                @Override // com.example.photoareditor.Adapter.ImageAdapter.ImageAdapterInterface
                public void item_click(String path) {
                    Bitmap bitmap1 = BitmapFactory.decodeFile(path);
                    if (bitmap1 == null) {
                        Toast.makeText(Image_GalleryActivity.this.getApplicationContext(), "Invalid Image", Toast.LENGTH_SHORT).show();
                    } else if (Utils.art == 1) {
                        MediaScannerConnection.scanFile(Image_GalleryActivity.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.example.photoareditor.Activity.Image_GalleryActivity.ImageRefresh.1.1
                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                            public void onScanCompleted(String path2, Uri uri) {
                                Intent intent2 = new Intent(Image_GalleryActivity.this, UploadActivity.class);
                                intent2.putExtra("imageUri", uri.toString());
                                Image_GalleryActivity.this.startActivity(intent2);
                            }
                        });
                    } else if (Utils.editor == 2) {
                        Intent intent = new Intent(Image_GalleryActivity.this, EditorActivity.class);
                        intent.putExtra(Utils.KEY_SELECTED_PHOTOS, path);
                        Image_GalleryActivity.this.startActivity(intent);
                    } else if (Utils.drip == 3) {
                        Bitmap bitmapqqqqq = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq.getHeight() > 1500 || bitmapqqqqq.getWidth() > 1500) {
                            Bitmap bitmap1213 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq, 800);
                            DripLayout.setFaceBitmap(bitmap1213);
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap1213);
                            Intent intent2 = new Intent(Image_GalleryActivity.this, DripLayout.class);
                            Image_GalleryActivity.this.startActivity(intent2);
                            return;
                        }
                        DripLayout.setFaceBitmap(bitmapqqqqq);
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq);
                        Intent intent3 = new Intent(Image_GalleryActivity.this, DripLayout.class);
                        Image_GalleryActivity.this.startActivity(intent3);
                    } else if (Utils.art_1 == 4) {
                        Bitmap bitmapqqqqq2 = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq2.getHeight() > 1500 || bitmapqqqqq2.getWidth() > 1500) {
                            Bitmap bitmap12132 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq2, 800);
                            ArtLayout.setFaceBitmap(bitmap12132);
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap12132);
                            Intent intent4 = new Intent(Image_GalleryActivity.this, ArtLayout.class);
                            Image_GalleryActivity.this.startActivity(intent4);
                            return;
                        }
                        ArtLayout.setFaceBitmap(bitmapqqqqq2);
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq2);
                        Intent intent5 = new Intent(Image_GalleryActivity.this, ArtLayout.class);
                        Image_GalleryActivity.this.startActivity(intent5);
                    } else if (Utils.splash == 5) {
                        Bitmap bitmapqqqqq3 = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq3.getHeight() > 1500 || bitmapqqqqq3.getWidth() > 1500) {
                            Bitmap bitmap12133 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq3, 800);
                            BitmapTransfer.bitmap = bitmap12133;
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap12133);
                            Intent intent6 = new Intent(Image_GalleryActivity.this, SplashLayout.class);
                            Image_GalleryActivity.this.startActivity(intent6);
                            return;
                        }
                        BitmapTransfer.bitmap = bitmapqqqqq3;
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq3);
                        Intent intent7 = new Intent(Image_GalleryActivity.this, SplashLayout.class);
                        Image_GalleryActivity.this.startActivity(intent7);
                    } else if (Utils.motion == 6) {
                        Bitmap bitmapqqqqq4 = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq4.getHeight() > 1500 || bitmapqqqqq4.getWidth() > 1500) {
                            Bitmap bitmap12134 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq4, 800);
                            MotionLayout.setFaceBitmap(bitmap12134);
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap12134);
                            Intent intent8 = new Intent(Image_GalleryActivity.this, MotionLayout.class);
                            Image_GalleryActivity.this.startActivity(intent8);
                            return;
                        }
                        MotionLayout.setFaceBitmap(bitmapqqqqq4);
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq4);
                        Intent intent9 = new Intent(Image_GalleryActivity.this, MotionLayout.class);
                        Image_GalleryActivity.this.startActivity(intent9);
                    } else if (Utils.pixlab == 7) {
                        Bitmap bitmapqqqqq5 = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq5.getHeight() > 1500 || bitmapqqqqq5.getWidth() > 1500) {
                            Bitmap bitmap12135 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq5, 800);
                            PixLabLayout.setFaceBitmap(bitmap12135);
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap12135);
                            Intent intent10 = new Intent(Image_GalleryActivity.this, PixLabLayout.class);
                            Image_GalleryActivity.this.startActivity(intent10);
                            return;
                        }
                        PixLabLayout.setFaceBitmap(bitmapqqqqq5);
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq5);
                        Intent intent11 = new Intent(Image_GalleryActivity.this, PixLabLayout.class);
                        Image_GalleryActivity.this.startActivity(intent11);
                    } else if (Utils.collage == 8) {
                        PolishCollageActivity.getQueShotGridActivityInstance().replaceCurrentPiece(path);
                        Image_GalleryActivity.this.finish();
                    } else if (Utils.spiral == 9) {
                        Utils.process = false;
                        Bitmap bitmapqqqqq6 = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq6.getHeight() > 1500 || bitmapqqqqq6.getWidth() > 1500) {
                            Bitmap bitmap12136 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq6, 800);
                            NeonLayout.setFaceBitmap(bitmap12136);
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap12136);
                            Intent intent12 = new Intent(Image_GalleryActivity.this, NeonLayout.class);
                            Image_GalleryActivity.this.startActivity(intent12);
                            return;
                        }
                        NeonLayout.setFaceBitmap(bitmapqqqqq6);
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq6);
                        Intent intent13 = new Intent(Image_GalleryActivity.this, NeonLayout.class);
                        Image_GalleryActivity.this.startActivity(intent13);
                    } else if (Utils.sketch == 10) {
                        Bitmap bitmapqqqqq7 = BitmapFactory.decodeFile(path);
                        StoreManager.setCurrentCropedBitmap(Image_GalleryActivity.this, null);
                        StoreManager.setCurrentCroppedMaskBitmap(Image_GalleryActivity.this, null);
                        if (bitmapqqqqq7.getHeight() > 1500 || bitmapqqqqq7.getWidth() > 1500) {
                            Bitmap bitmap12137 = Image_GalleryActivity.this.getResizedBitmap(bitmapqqqqq7, 800);
                            SketchActivity.setFaceBitmap(bitmap12137);
                            StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmap12137);
                            Intent intent14 = new Intent(Image_GalleryActivity.this, SketchActivity.class);
                            Image_GalleryActivity.this.startActivity(intent14);
                            return;
                        }
                        SketchActivity.setFaceBitmap(bitmapqqqqq7);
                        StoreManager.setCurrentOriginalBitmap(Image_GalleryActivity.this, bitmapqqqqq7);
                        Intent intent15 = new Intent(Image_GalleryActivity.this, SketchActivity.class);
                        Image_GalleryActivity.this.startActivity(intent15);
                    }
                }
            });
            Image_GalleryActivity.this.recycler_photos.setLayoutManager(new GridLayoutManager(Image_GalleryActivity.this, 3));
            Image_GalleryActivity.this.recycler_photos.setAdapter(Image_GalleryActivity.this.imageAdapter);
        }
    }

    /* loaded from: classes7.dex */
    public class FolderRefresh extends AsyncTask<Void, Void, Void> {
        public FolderRefresh() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voids) {
            String[] projection = {"_data", "bucket_id", "bucket_display_name", "_id"};
            for (int i = 1; i < Image_GalleryActivity.this.folderList.size(); i++) {
                Cursor c = Image_GalleryActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, "bucket_id =?", new String[]{((BeacketBean) Image_GalleryActivity.this.folderList.get(i)).id}, "date_modified DESC");
                if (c != null && c.getCount() > 0) {
                    BeacketBean bm = (BeacketBean) Image_GalleryActivity.this.folderList.get(i);
                    bm.count = c.getCount();
                    Image_GalleryActivity.this.folderList.set(i, bm);
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (Image_GalleryActivity.this.folderAdapter != null) {
                Image_GalleryActivity.this.folderAdapter.notifyDataSetChanged();
            }
        }
    }

    public ArrayList<String> getAllImages() {
        ArrayList<String> imageList = new ArrayList<>();
        String[] projection = {"_data"};
        if (this.bucketId.equals("")) {
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, "date_modified DESC");
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String absolutePathOfImage = cursor.getString(cursor.getColumnIndex("_data"));
                new String();
                imageList.add(absolutePathOfImage);
            }
            cursor.close();
        } else {
            Cursor cursor2 = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, "bucket_id =?", new String[]{this.bucketId}, "date_modified DESC");
            while (cursor2.moveToNext()) {
                @SuppressLint("Range") String absolutePathOfImage2 = cursor2.getString(cursor2.getColumnIndex("_data"));
                new String();
                imageList.add(absolutePathOfImage2);
            }
            cursor2.close();
        }
        return imageList;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.recycler_photos.getVisibility() == View.VISIBLE) {
            Utils.spiral = 0;
            Utils.art = 0;
            this.recycler_photos.setVisibility(View.GONE);
            this.recycler_folders.setVisibility(View.VISIBLE);
            this.folder.setText("Select Image");
            return;
        }
        Utils.art = 0;
        Utils.spiral = 0;
        Utils.editor = 0;
        Utils.drip = 0;
        Utils.art_1 = 0;
        Utils.splash = 0;
        Utils.motion = 0;
        Utils.pixlab = 0;
        Utils.collage = 0;
        Utils.sketch = 0;
        super.onBackPressed();
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        if (bitmap == null || maxSize <= 0) {
            return null; // Handle invalid inputs gracefully
        }

        int height;
        int width;
        int width2 = bitmap.getWidth();
        int height2 = bitmap.getHeight();

        if (width2 <= 0 || height2 <= 0) {
            return null; // Handle invalid bitmap dimensions
        }

        float bitmapRatio = (float) width2 / (float) height2;

        if (bitmapRatio > 1.0f) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }


//    public Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
//        int height;
//        int width;
//        int width2 = bitmap.getWidth();
//        float bitmapRatio = width2 / bitmap.getHeight();
//        if (bitmapRatio > 1.0f) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(bitmap, width, height, true);
//    }
}
