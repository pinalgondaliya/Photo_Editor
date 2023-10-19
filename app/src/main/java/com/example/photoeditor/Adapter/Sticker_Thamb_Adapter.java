package com.example.photoeditor.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.photoeditor.ModelClass.stickerlistModel;
import com.example.photoeditor.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/* loaded from: classes5.dex */
public class Sticker_Thamb_Adapter extends RecyclerView.Adapter<Sticker_Thamb_Adapter.Myclass> {
    private Bitmap bitmap;
    Context context;
    private File file1;
    OnClickListener onClickListener;
    List<stickerlistModel> stickerlistModels;

    /* loaded from: classes5.dex */
    public interface OnClickListener {
        void addSticker(Bitmap bitmap);
    }

    public Sticker_Thamb_Adapter(Context context2, List<stickerlistModel> stickerlistModels, OnClickListener onClickListener) {
        this.context = context2;
        this.stickerlistModels = stickerlistModels;
        this.onClickListener = onClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public Myclass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.sticker_thamb_items, parent, false);
        Myclass myclass = new Myclass(view);
        return myclass;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final Myclass holder, int position) {
        final stickerlistModel listData = this.stickerlistModels.get(position);
        Glide.with(this.context).load(this.stickerlistModels.get(position).sticker_thumb).listener(new RequestListener<Drawable>() { // from class: com.example.photoareditor.Adapter.Sticker_Thamb_Adapter.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.loading_view.setVisibility(View.VISIBLE);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.loading_view.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.sticker_thamb);
        String filename = listData.getSticker_thumb().substring(listData.getSticker_thumb().lastIndexOf(47) + 1, listData.getSticker_thumb().lastIndexOf(46));
        Log.e("10", "onBindViewHolder: " + filename);
        File album = new File("/data/user/0/" + this.context.getPackageName() + "/stickers");
        if (!album.exists()) {
            album.mkdirs();
        }
        album.mkdirs();
        Log.e("100", "onBindViewHolder: " + album);
        String uvsss = album.getAbsolutePath() + "/" + filename + ".jpg";
        Log.e("1000", "onBindViewHolder: " + uvsss);
        if (!new File(uvsss).exists()) {
            album.mkdirs();
        }
        this.file1 = new File(uvsss);
        File file2 = new File(String.valueOf(this.file1));
        if (file2.exists()) {
            holder.sticker_save.setVisibility(View.GONE);
            holder.sticker_done.setVisibility(View.GONE);
        } else {
            holder.sticker_save.setVisibility(View.GONE);
            holder.sticker_done.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.Sticker_Thamb_Adapter.2

            /* renamed from: com.example.photoareditor.Adapter.Sticker_Thamb_Adapter$2$DownloadTask */
            /* loaded from: classes5.dex */
            class DownloadTask extends AsyncTask<String, Integer, String> {
                DownloadTask() {
                }

                @Override // android.os.AsyncTask
                protected void onPreExecute() {
                    holder.sticker_save.setVisibility(View.GONE);
                    holder.sticker_done.setVisibility(View.GONE);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public String doInBackground(String... params) {
                    String path = params[0];
                    Log.i("Info: path", path);
                    IOException e;
                    MalformedURLException exception;
                    try {
                        URL url = new URL(path);
                        URLConnection urlConnection = url.openConnection();
                        urlConnection.connect();
                        int file_length = urlConnection.getContentLength();
                        String filename = listData.getSticker_thumb().substring(listData.getSticker_thumb().lastIndexOf(47) + 1, listData.getSticker_thumb().lastIndexOf(46));
                        Log.e("10", "onBindViewHolder: " + filename);
                        File album = new File("/data/user/0/" + Sticker_Thamb_Adapter.this.context.getPackageName() + "/stickers");
                        if (!album.exists()) {
                            try {
                                album.mkdirs();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        album.mkdirs();
                        Log.e("100", "onBindViewHolder: " + album);
                        String uvsss = album.getAbsolutePath() + "/" + filename + ".jpg";
                        Log.e("1000", "onBindViewHolder: " + uvsss);
                        if (!new File(uvsss).exists()) {
                            album.mkdirs();
                        }
                        Sticker_Thamb_Adapter.this.file1 = new File(uvsss);
                        File file2 = new File(String.valueOf(Sticker_Thamb_Adapter.this.file1));
                        Log.e("1000", "onBindViewHolder: " + file2);
                        if (file2.exists()) {
                            Log.v("in if condition", "file is alredy exist");
                            file2.delete();
                        } else {
                            Log.v("file is created in card", "new  dir");
                        }
                        OutputStream outputStream = new FileOutputStream(file2);
                        InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                        byte[] data = new byte[1024];
                        int total = 0;
                        while (true) {
                            int count = inputStream.read(data);
                            if (count != -1) {
                                total += count;
                                outputStream.write(data, 0, count);
                                int progress = (total * 100) / file_length;
                                String path2 = path;
                                URL url2 = url;
                                publishProgress(Integer.valueOf(progress));
                                Log.i("Info", "Progress: " + progress);
                                path = path2;
                                url = url2;
                            } else {
                                inputStream.close();
                                outputStream.close();
                                Log.i("Info", "file_length: " + file_length);
                                return "Download complete.";
                            }
                        }
                    } catch (MalformedURLException e5) {
                        e = e5;
                    } catch (IOException e6) {
                        e = e6;
                    }
                    return path;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(String result) {
                    Sticker_Thamb_Adapter.this.bitmap = Sticker_Thamb_Adapter.this.getBitmap(Sticker_Thamb_Adapter.this.file1.getAbsolutePath());
                    Drawable bitmapDrawable = new BitmapDrawable(Sticker_Thamb_Adapter.this.context.getResources(), Sticker_Thamb_Adapter.this.bitmap);
                    Sticker_Thamb_Adapter.this.onClickListener.addSticker(Sticker_Thamb_Adapter.this.bitmap);
                    Log.e("aaaaaaa", "onPostExecute: " + bitmapDrawable);
                }
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new DownloadTask().execute(listData.getSticker_thumb());
            }
        });
        holder.sticker_save.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.Sticker_Thamb_Adapter.3

            /* renamed from: com.example.photoareditor.Adapter.Sticker_Thamb_Adapter$3$DownloadTask */
            /* loaded from: classes5.dex */
            class DownloadTask extends AsyncTask<String, Integer, String> {
                DownloadTask() {
                }

                @Override // android.os.AsyncTask
                protected void onPreExecute() {
                    holder.sticker_save.setVisibility(View.GONE);
                    holder.sticker_done.setVisibility(View.GONE);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public String doInBackground(String... params) {
                    String path = params[0];
                    Log.i("Info: path", path);
                    try {
                        URL url = new URL(path);
                        URLConnection urlConnection = url.openConnection();
                        urlConnection.connect();
                        int file_length = urlConnection.getContentLength();
                        String filename = listData.getSticker_thumb().substring(listData.getSticker_thumb().lastIndexOf(47) + 1, listData.getSticker_thumb().lastIndexOf(46));
                        Log.e("10", "onBindViewHolder: " + filename);
                        File album = new File("/data/user/0/" + Sticker_Thamb_Adapter.this.context.getPackageName() + "/stickers");
                        if (!album.exists()) {
                            album.mkdirs();
                        }
                        album.mkdirs();
                        Log.e("100", "onBindViewHolder: " + album);
                        String uvsss = album.getAbsolutePath() + "/" + filename + ".jpg";
                        Log.e("1000", "onBindViewHolder: " + uvsss);
                        if (!new File(uvsss).exists()) {
                            album.mkdirs();
                        }
                        Sticker_Thamb_Adapter.this.file1 = new File(uvsss);
                        File file2 = new File(String.valueOf(Sticker_Thamb_Adapter.this.file1));
                        Log.e("1000", "onBindViewHolder: " + file2);
                        if (file2.exists()) {
                            Log.v("in if condition", "file is alredy exist");
                            file2.delete();
                        } else {
                            Log.v("file is created in card", "new  dir");
                        }
                        OutputStream outputStream = new FileOutputStream(file2);
                        InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                        byte[] data = new byte[1024];
                        int total = 0;
                        while (true) {
                            int count = inputStream.read(data);
                            if (count != -1) {
                                total += count;
                                outputStream.write(data, 0, count);
                                int progress = (total * 100) / file_length;
                                String path2 = path;
                                URL url2 = url;
                                try {
                                    publishProgress(Integer.valueOf(progress));
                                    Log.i("Info", "Progress: " + progress);
                                    path = path2;
                                    url = url2;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                inputStream.close();
                                outputStream.close();
                                Log.i("Info", "file_length: " + file_length);
                                return "Download complete.";
                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return path;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(String result) {
                    Sticker_Thamb_Adapter.this.bitmap = Sticker_Thamb_Adapter.this.getBitmap(Sticker_Thamb_Adapter.this.file1.getAbsolutePath());
                    Drawable bitmapDrawable = new BitmapDrawable(Sticker_Thamb_Adapter.this.context.getResources(), Sticker_Thamb_Adapter.this.bitmap);
                    Sticker_Thamb_Adapter.this.onClickListener.addSticker(Sticker_Thamb_Adapter.this.bitmap);
                    Log.e("aaaaaaa", "onPostExecute: " + bitmapDrawable);
                }
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new DownloadTask().execute(listData.getSticker_thumb());
            }
        });
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
            return null;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.stickerlistModels.size();
    }

    /* loaded from: classes5.dex */
    public class Myclass extends RecyclerView.ViewHolder {
        LottieAnimationView loading_view;
        ImageView sticker_done;
        ImageView sticker_save;
        ImageView sticker_thamb;

        public Myclass(View itemView) {
            super(itemView);
            this.sticker_thamb = (ImageView) itemView.findViewById(R.id.sticker_thamb);
            this.sticker_save = (ImageView) itemView.findViewById(R.id.sticker_save);
            this.sticker_done = (ImageView) itemView.findViewById(R.id.sticker_download);
            this.loading_view = (LottieAnimationView) itemView.findViewById(R.id.loading_view);
        }
    }
}
