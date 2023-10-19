package com.example.photoeditor.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Adapter.AlbumAdapter;
import com.example.photoeditor.Adapter.ListAlbumAdapter;
import com.example.photoeditor.CollageClass.Constants;
import com.example.photoeditor.Interface.OnAlbum;
import com.example.photoeditor.Interface.OnListAlbum;
import com.example.photoeditor.ModelClass.ImageModel;
import com.example.photoeditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GridPickerActivity extends AppCompatActivity implements View.OnClickListener, OnAlbum, OnListAlbum {
    public static final String KEY_DATA_RESULT = "KEY_DATA_RESULT";
    public static final String KEY_LIMIT_MAX_IMAGE = "KEY_LIMIT_MAX_IMAGE";
    public static final String KEY_LIMIT_MIN_IMAGE = "KEY_LIMIT_MIN_IMAGE";
    AlbumAdapter albumAdapter;
    GridView gridViewAlbum;
    GridView gridViewPhotos;
    LinearLayout linearLayoutSelected;
    ListAlbumAdapter listAlbumAdapter;
    private Handler mHandler;
    HorizontalScrollView scrollViewSelected;
    AlertDialog sortDialog;
    TextView textViewTitle;
    TextView txtTotalImage;
    ArrayList<ImageModel> dataAlbum = new ArrayList<>();
    ArrayList<ImageModel> dataListPhoto = new ArrayList<>();
    int limitImageMax = 9;
    int limitImageMin = 2;
    ArrayList<ImageModel> listItemSelect = new ArrayList<>();
    ArrayList<String> pathList = new ArrayList<>();
    public int position = 0;

    /* loaded from: classes7.dex */
    private class GetItemAlbum extends AsyncTask<Void, Void, String> {
        @Override // android.os.AsyncTask
        public void onPreExecute() {
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
        }

        private GetItemAlbum() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Void... voidArr) {
            Cursor query = GridPickerActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, null, null, null);
            if (query == null) {
                return "";
            }
            int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
            while (query.moveToNext()) {
                String string = query.getString(columnIndexOrThrow);
                File file = new File(string);
                if (file.exists()) {
                    boolean checkFile = GridPickerActivity.this.checkFile(file);
                    if (!GridPickerActivity.this.Check(file.getParent(), GridPickerActivity.this.pathList) && checkFile) {
                        GridPickerActivity.this.pathList.add(file.getParent());
                        GridPickerActivity.this.dataAlbum.add(new ImageModel(file.getParentFile().getName(), string, file.getParent()));
                    }
                }
            }
            Collections.sort(GridPickerActivity.this.dataAlbum);
            query.close();
            return "";
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            GridPickerActivity.this.gridViewAlbum.setAdapter((ListAdapter) GridPickerActivity.this.albumAdapter);
        }
    }

    /* loaded from: classes7.dex */
    public class GetItemListAlbum extends AsyncTask<Void, Void, String> {
        String pathAlbum;

        @Override // android.os.AsyncTask
        public void onPreExecute() {
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Void... voidArr) {
        }

        GetItemListAlbum(String str) {
            this.pathAlbum = str;
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Void... voidArr) {
            File file = new File(this.pathAlbum);
            if (!file.isDirectory()) {
                return "";
            }
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                if (file2.exists()) {
                    boolean checkFile = GridPickerActivity.this.checkFile(file2);
                    if (!file2.isDirectory() && checkFile) {
                        GridPickerActivity.this.dataListPhoto.add(new ImageModel(file2.getName(), file2.getAbsolutePath(), file2.getAbsolutePath()));
                        publishProgress(new Void[0]);
                    }
                }
            }
            return "";
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            try {
                Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.GetItemListAlbum.1
                    @Override // java.util.Comparator
                    public int compare(ImageModel imageModel, ImageModel imageModel2) {
                        File file = new File(imageModel.getPathFolder());
                        File file2 = new File(imageModel2.getPathFolder());
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
            } catch (Exception e) {
            }
            GridPickerActivity.this.listAlbumAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_picker);
        findViewById(R.id.image_view_back).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.GridPickerActivity.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GridPickerActivity.this.onBackPressed();
            }
        });
        findViewById(R.id.image_view_sort).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.GridPickerActivity.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (GridPickerActivity.this.gridViewPhotos.getVisibility() == View.GONE) {
                    Log.d("tag", "1");
                    GridPickerActivity.this.showDialogSortAlbum();
                    return;
                }
                GridPickerActivity.this.showDialogSortListAlbum();
                Log.d("tag", ExifInterface.GPS_MEASUREMENT_2D);
            }
        });
        this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.limitImageMax = extras.getInt(KEY_LIMIT_MAX_IMAGE, 9);
            int i = extras.getInt(KEY_LIMIT_MIN_IMAGE, 2);
            this.limitImageMin = i;
            if (i > this.limitImageMax) {
                finish();
            }
            if (this.limitImageMin < 1) {
                finish();
            }
        }
        this.gridViewPhotos = (GridView) findViewById(R.id.gridViewPhotos);
        this.txtTotalImage = (TextView) findViewById(R.id.txtTotalImage);
        findViewById(R.id.textViewDone).setOnClickListener(this);
        this.linearLayoutSelected = (LinearLayout) findViewById(R.id.linearLayoutSelected);
        this.scrollViewSelected = (HorizontalScrollView) findViewById(R.id.scrollViewSelected);
        this.gridViewAlbum = (GridView) findViewById(R.id.gridViewAlbum);
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.example.photoareditor.Activity.GridPickerActivity.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        };
        try {
            Collections.sort(this.dataAlbum, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.4
                @Override // java.util.Comparator
                public int compare(ImageModel imageModel, ImageModel imageModel2) {
                    return imageModel.getName().compareToIgnoreCase(imageModel2.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        AlbumAdapter albumAdapter2 = new AlbumAdapter(this, R.layout.item_album, this.dataAlbum);
        this.albumAdapter = albumAdapter2;
        albumAdapter2.setOnItem(this);
        if (!isPermissionGranted("android.permission.READ_EXTERNAL_STORAGE")) {
            requestPermission("android.permission.READ_EXTERNAL_STORAGE", 1001);
        } else {
            new GetItemAlbum().execute(new Void[0]);
        }
        updateTxtTotalImage();
    }

    private boolean isPermissionGranted(String str) {
        return ContextCompat.checkSelfPermission(this, str) == 0;
    }

    private void requestPermission(String str, int i) {
        ActivityCompat.shouldShowRequestPermissionRationale(this, str);
        ActivityCompat.requestPermissions(this, new String[]{str}, i);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1001) {
            if (iArr.length > 0 && iArr[0] == 0) {
                new GetItemAlbum().execute(new Void[0]);
            } else {
                finish();
            }
        } else if (i == 1002 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }

    public boolean Check(String str, ArrayList<String> arrayList) {
        return !arrayList.isEmpty() && arrayList.contains(str);
    }

    public void showDialogSortAlbum() {
        String[] stringArray = getResources().getStringArray(R.array.array_sort_value);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.text_title_dialog_sort_by_album));
        builder.setSingleChoiceItems(stringArray, this.position, new DialogInterface.OnClickListener() { // from class: com.example.photoareditor.Activity.GridPickerActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    GridPickerActivity.this.position = i;
                    Collections.sort(GridPickerActivity.this.dataAlbum, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.5.1
                        @Override // java.util.Comparator
                        public int compare(ImageModel imageModel, ImageModel imageModel2) {
                            return imageModel.getName().compareToIgnoreCase(imageModel2.getName());
                        }
                    });
                    GridPickerActivity.this.refreshGridViewAlbum();
                    Log.e("TAG", "showDialogSortAlbum by NAME");
                } else if (i == 1) {
                    GridPickerActivity.this.position = i;
                    GridPickerActivity.this.doinBackground();
                    Log.e("TAG", "showDialogSortAlbum by Size");
                } else if (i == 2) {
                    GridPickerActivity.this.position = i;
                    Collections.sort(GridPickerActivity.this.dataAlbum, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.5.2
                        @Override // java.util.Comparator
                        public int compare(ImageModel imageModel, ImageModel imageModel2) {
                            char c = GridPickerActivity.getFolderSize(new File(imageModel.getPathFolder())) > GridPickerActivity.getFolderSize(new File(imageModel2.getPathFolder())) ? (char) 1 : GridPickerActivity.getFolderSize(new File(imageModel.getPathFolder())) == GridPickerActivity.getFolderSize(new File(imageModel2.getPathFolder())) ? (char) 0 : 65535;
                            if (c > 0) {
                                return -1;
                            }
                            return c < 0 ? 1 : 0;
                        }
                    });
                    GridPickerActivity.this.refreshGridViewAlbum();
                    Log.e("TAG", "showDialogSortAlbum by Date");
                }
                GridPickerActivity.this.sortDialog.dismiss();
            }
        });
        AlertDialog create = builder.create();
        this.sortDialog = create;
        create.show();
    }

    public void refreshGridViewAlbum() {
        AlbumAdapter albumAdapter2 = new AlbumAdapter(this, R.layout.item_album, this.dataAlbum);
        this.albumAdapter = albumAdapter2;
        albumAdapter2.setOnItem(this);
        this.gridViewAlbum.setAdapter((ListAdapter) this.albumAdapter);
        this.gridViewAlbum.setVisibility(View.GONE);
        this.gridViewAlbum.setVisibility(View.VISIBLE);
    }

    public void showDialogSortListAlbum() {
        String[] stringArray = getResources().getStringArray(R.array.array_sort_value);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.text_title_dialog_sort_by_photo));
        builder.setSingleChoiceItems(stringArray, this.position, new DialogInterface.OnClickListener() { // from class: com.example.photoareditor.Activity.GridPickerActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    GridPickerActivity.this.position = i;
                    GridPickerActivity.this.doinBackgroundPhoto(i);
                }
            }
        });
        AlertDialog create = builder.create();
        this.sortDialog = create;
        create.show();
    }

    public void refreshGridViewListAlbum() {
        ListAlbumAdapter listAlbumAdapter2 = new ListAlbumAdapter(this, R.layout.item_list_album, this.dataListPhoto);
        this.listAlbumAdapter = listAlbumAdapter2;
        listAlbumAdapter2.setOnListAlbum(this);
        this.gridViewPhotos.setAdapter((ListAdapter) this.listAlbumAdapter);
        this.gridViewPhotos.setVisibility(View.GONE);
        this.gridViewPhotos.setVisibility(View.VISIBLE);
    }

    public static long getFolderSize(File file) {
        File[] listFiles;
        boolean z;
        long j = 0;
        if (file != null && file.exists() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (file2.isFile()) {
                    int i = 0;
                    while (true) {
                        if (i >= Constants.FORMAT_IMAGE.size()) {
                            z = false;
                            break;
                        } else if (file2.getName().endsWith(Constants.FORMAT_IMAGE.get(i))) {
                            z = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (z) {
                        j++;
                    }
                }
            }
        }
        return j;
    }

    public void addItemSelect(final ImageModel imageModel) {
        imageModel.setId(this.listItemSelect.size());
        this.listItemSelect.add(imageModel);
        updateTxtTotalImage();
        final View inflate = View.inflate(this, R.layout.item_album_selected, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.image_view_item);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with((Activity) this).load(imageModel.getPathFile()).placeholder((int) R.drawable.image_show).into(imageView);
        ((ImageView) inflate.findViewById(R.id.image_view_remove)).setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Activity.GridPickerActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GridPickerActivity.this.linearLayoutSelected.removeView(inflate);
                GridPickerActivity.this.listItemSelect.remove(imageModel);
                GridPickerActivity.this.updateTxtTotalImage();
            }
        });
        this.linearLayoutSelected.addView(inflate);
        inflate.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
        sendScroll();
    }

    @SuppressLint("StringFormatInvalid")
    public void updateTxtTotalImage() {
        this.txtTotalImage.setText(String.format(getResources().getString(R.string.text_images), Integer.valueOf(this.listItemSelect.size())));
    }

    private void sendScroll() {
        final Handler handler = new Handler();
        new Thread(new Runnable() { // from class: com.example.photoareditor.Activity.GridPickerActivity.8
            @Override // java.lang.Runnable
            public void run() {
                handler.post(new Runnable() { // from class: com.example.photoareditor.Activity.GridPickerActivity.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        GridPickerActivity.this.scrollViewSelected.fullScroll(66);
                    }
                });
            }
        }).start();
    }

    public void showListAlbum(String str) {
        this.textViewTitle.setText(new File(str).getName());
        ListAlbumAdapter listAlbumAdapter2 = new ListAlbumAdapter(this, R.layout.item_list_album, this.dataListPhoto);
        this.listAlbumAdapter = listAlbumAdapter2;
        listAlbumAdapter2.setOnListAlbum(this);
        this.gridViewPhotos.setAdapter((ListAdapter) this.listAlbumAdapter);
        this.gridViewPhotos.setVisibility(View.VISIBLE);
        new GetItemListAlbum(str).execute(new Void[0]);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.textViewDone) {
            ArrayList<String> listString = getListString(this.listItemSelect);
            if (listString.size() >= this.limitImageMin) {
                done(listString);
            } else {
                Toast.makeText(this, "Please select at lease " + this.limitImageMin + " images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void done(ArrayList<String> arrayList) {
        Intent intent = new Intent(this, PolishCollageActivity.class);
        intent.putStringArrayListExtra(KEY_DATA_RESULT, arrayList);
        startActivity(intent);
    }

    public ArrayList<String> getListString(ArrayList<ImageModel> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(arrayList.get(i).getPathFile());
        }
        return arrayList2;
    }

    public boolean checkFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isFile()) {
            return true;
        }
        String name = file.getName();
        if (!name.startsWith(".") && file.length() != 0) {
            for (int i = 0; i < Constants.FORMAT_IMAGE.size(); i++) {
                if (name.endsWith(Constants.FORMAT_IMAGE.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.gridViewPhotos.getVisibility() == View.VISIBLE) {
            this.dataListPhoto.clear();
            this.listAlbumAdapter.notifyDataSetChanged();
            this.gridViewPhotos.setVisibility(View.GONE);
            this.textViewTitle.setText(getResources().getString(R.string.text_title_activity_album));
            return;
        }
        super.onBackPressed();
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.example.photoareditor.Activity.GridPickerActivity$9] */
    public void doinBackgroundPhoto(int i) {
        new AsyncTask<String, String, Void>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.9
            @Override // android.os.AsyncTask
            public void onPreExecute() {
                super.onPreExecute();
            }

            @Override // android.os.AsyncTask
            public Void doInBackground(String... strArr) {
                if (0 != 0) {
                    if (0 != 1) {
                        if (0 != 2) {
                            return null;
                        }
                        Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.9.3
                            @Override // java.util.Comparator
                            public int compare(ImageModel imageModel, ImageModel imageModel2) {
                                File file = new File(imageModel.getPathFolder());
                                File file2 = new File(imageModel2.getPathFolder());
                                if (file.lastModified() > file2.lastModified()) {
                                    return -1;
                                }
                                return file.lastModified() < file2.lastModified() ? 1 : 0;
                            }
                        });
                        return null;
                    }
                    Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.9.2
                        @Override // java.util.Comparator
                        public int compare(ImageModel imageModel, ImageModel imageModel2) {
                            char c = GridPickerActivity.getFolderSize(new File(imageModel.getPathFolder())) > GridPickerActivity.getFolderSize(new File(imageModel2.getPathFolder())) ? (char) 1 : GridPickerActivity.getFolderSize(new File(imageModel.getPathFolder())) == GridPickerActivity.getFolderSize(new File(imageModel2.getPathFolder())) ? (char) 0 :  65535;
                            if (c > 0) {
                                return -1;
                            }
                            return c < 0 ? 1 : 0;
                        }
                    });
                    return null;
                }
                try {
                    Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.9.1
                        @Override // java.util.Comparator
                        public int compare(ImageModel imageModel, ImageModel imageModel2) {
                            return imageModel.getName().compareToIgnoreCase(imageModel2.getName());
                        }
                    });
                } catch (Exception e) {
                }
                return null;
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Void r1) {
                super.onPostExecute(r1);
                GridPickerActivity.this.refreshGridViewListAlbum();
            }
        }.execute(new String[0]);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.example.photoareditor.Activity.GridPickerActivity$10] */
    public void doinBackground() {
        new AsyncTask<String, String, Void>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.10
            @Override // android.os.AsyncTask
            public void onPreExecute() {
                super.onPreExecute();
            }

            @Override // android.os.AsyncTask
            public Void doInBackground(String... strArr) {
                Collections.sort(GridPickerActivity.this.dataAlbum, new Comparator<ImageModel>() { // from class: com.example.photoareditor.Activity.GridPickerActivity.10.1
                    @Override // java.util.Comparator
                    public int compare(ImageModel imageModel, ImageModel imageModel2) {
                        File file = new File(imageModel.getPathFolder());
                        File file2 = new File(imageModel2.getPathFolder());
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
                return null;
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Void r1) {
                super.onPostExecute(r1);
                GridPickerActivity.this.refreshGridViewAlbum();
            }
        }.execute(new String[0]);
    }

    @Override // com.example.photoareditor.Interface.OnAlbum
    public void OnItemAlbumClick(int i) {
        showListAlbum(this.dataAlbum.get(i).getPathFolder());
    }

    @Override // com.example.photoareditor.Interface.OnListAlbum
    public void OnItemListAlbumClick(ImageModel imageModel) {
        if (this.listItemSelect.size() < this.limitImageMax) {
            addItemSelect(imageModel);
        } else {
            Toast.makeText(this, "Limit " + this.limitImageMax + " images", Toast.LENGTH_SHORT).show();
        }
    }
}
