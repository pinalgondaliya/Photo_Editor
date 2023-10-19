package com.example.photoeditor.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.photoeditor.Activity.CreationActivity;
import com.example.photoeditor.Activity.PhotoSavedActivity;
import com.example.photoeditor.ModelClass.pictureFacer;
import com.example.photoeditor.R;

import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes5.dex */
public class CreationAdapter extends RecyclerView.Adapter<CreationAdapter.FolderViewHolder> {
    public static String path;
    String abc;
    AppCompatActivity context;
    private File file;
    private String filename;
    ArrayList<pictureFacer> folderlist;
    private long mLastClickTime;
    private String size_kb;

    public CreationAdapter(AppCompatActivity context, ArrayList<pictureFacer> folderlist) {
        this.context = context;
        this.folderlist = folderlist;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public FolderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        FolderViewHolder viewHolder = new FolderViewHolder(v);
        return viewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FolderViewHolder folderViewHolder, @SuppressLint("RecyclerView") final int position) {
        try {
            this.abc = this.folderlist.get(position).getPicturePath();
            Glide.with((FragmentActivity) this.context).load(this.abc).into(folderViewHolder.galleryImage);
            this.file = new File(this.abc);
            String str = this.abc;
            this.filename = str.substring(str.lastIndexOf("/") + 1);
            folderViewHolder.name.setText(this.filename);
            long length = this.file.length();
            this.size_kb = String.valueOf(length / 1024);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(this.file.getAbsolutePath(), options);
            int i = options.outHeight;
            int i2 = options.outWidth;
            folderViewHolder.size.setText(this.size_kb + " KB");
        } catch (Exception e) {
        }
        folderViewHolder.main_rl.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.CreationAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - CreationAdapter.this.mLastClickTime < 800) {
                    return;
                }
                CreationAdapter.this.mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(CreationAdapter.this.context, PhotoSavedActivity.class);
                intent.putExtra(ClientCookie.PATH_ATTR, CreationAdapter.this.folderlist.get(position).getPicturePath());
                CreationAdapter.this.context.startActivity(intent);
            }
        });
        Date date1 = new Date(this.file.lastModified());
        Time time1 = new Time(this.file.lastModified());
        folderViewHolder.date.setText(getDateInString(date1));
        folderViewHolder.time.setText(getTimeInString(time1));
        folderViewHolder.delete.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.CreationAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CreationAdapter.this.context);
                builder1.setMessage("Write your message here.");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.example.photoareditor.Adapter.CreationAdapter.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int id) {
                        CreationAdapter.path = CreationAdapter.this.folderlist.get(position).getPicturePath();
                        File file = new File(CreationAdapter.path);
                        if (CreationAdapter.deleteFile(CreationAdapter.this.context, file)) {
                            CreationAdapter.this.folderlist.remove(position);
                            if (CreationAdapter.this.folderlist.isEmpty()) {
                                CreationActivity.printlog(CreationAdapter.this.folderlist);
                            }
                            CreationAdapter.this.notifyDataSetChanged();
                        }
                        dialog.cancel();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: com.example.photoareditor.Adapter.CreationAdapter.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return position;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.folderlist.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return position;
    }

    /* loaded from: classes5.dex */
    public class FolderViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox;
        TextView date;
        ImageView delete;
        ImageView galleryImage;
        RelativeLayout main_rl;
        TextView name;
        TextView size;
        TextView time;

        public FolderViewHolder(View itemView) {
            super(itemView);
            this.galleryImage = (ImageView) itemView.findViewById(R.id.galleryImage);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.size = (TextView) itemView.findViewById(R.id.size);
            this.main_rl = (RelativeLayout) itemView.findViewById(R.id.main_rl);
            this.delete = (ImageView) itemView.findViewById(R.id.delete);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    public static boolean deleteFile(Context context, File file) {
        path = file.getAbsolutePath();
        String[] selectionArgs = {file.getAbsolutePath()};
        ContentResolver contentResolver = context.getContentResolver();
        Uri filesUri = MediaStore.Files.getContentUri("external");
        contentResolver.delete(filesUri, "_data=?", selectionArgs);
        if (file.exists()) {
            contentResolver.delete(filesUri, "_data=?", selectionArgs);
        }
        return true ^ file.exists();
    }

    public static String getDateInString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(date);
    }

    public static String getTimeInString(Time time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        return sdf1.format((Date) time);
    }
}
