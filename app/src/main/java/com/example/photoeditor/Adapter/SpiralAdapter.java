package com.example.photoeditor.Adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.download.library.DownloadImpl;
import com.download.library.DownloadListenerAdapter;
import com.download.library.Extra;
import com.example.photoeditor.Classs.Utils;
import com.example.photoeditor.ModelClass.MainModel;
import com.example.photoeditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class SpiralAdapter extends RecyclerView.Adapter<SpiralAdapter.MyDemo> {
    String c_name;
    Passposition click;
    ArrayList<MainModel.Datum.Category_Model> list;
    FragmentActivity requireActivity;
    private boolean isDownloading = false;
    private boolean isDownloadingStart = false;
    public int selectedPos = 0;
    private ArrayList<String> downloadLink = new ArrayList<>();

    /* loaded from: classes5.dex */
    public interface Passposition {
        void OnClick(int i);
    }

    public SpiralAdapter(FragmentActivity requireActivity, ArrayList<MainModel.Datum.Category_Model> list, String c_name, Passposition click) {
        this.requireActivity = requireActivity;
        this.list = list;
        this.c_name = c_name;
        this.click = click;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: avoid collision after fix types in other method */
    public MyDemo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.requireActivity).inflate(R.layout.item_spiral, parent, false);
        return new MyDemo(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyDemo holder, @SuppressLint("RecyclerView") final int position) {
        String image = this.list.get(position).thumb;
        Glide.with(this.requireActivity).load(image).placeholder((int) R.drawable.b23).into(holder.imageView);
        String substr = this.list.get(position).front.substring(this.list.get(position).front.lastIndexOf("/"));
        String substr1 = substr.substring(1, substr.length());
        File file = new File("/data/user/0/" + this.requireActivity.getPackageName() + "/" + this.c_name);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file.getAbsolutePath() + "/" + substr1);
        if (file1.exists()) {
            holder.progressBar.setVisibility(View.GONE);
            holder.ivDownload.setVisibility(View.GONE);
            holder.ivDone.setVisibility(View.GONE);
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.ivDownload.setVisibility(View.VISIBLE);
            holder.ivDone.setVisibility(View.GONE);
        }
        if (position == 0) {
            holder.progressBar.setVisibility(View.GONE);
            holder.ivDownload.setVisibility(View.GONE);
            holder.ivDone.setVisibility(View.GONE);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() { // from class: com.example.photoareditor.Adapter.SpiralAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Utils.process) {
                    String substr2 = SpiralAdapter.this.list.get(position).front.substring(SpiralAdapter.this.list.get(position).front.lastIndexOf("/"));
                    String substr12 = substr2.substring(1, substr2.length());
                    File file2 = new File("/data/user/0/" + SpiralAdapter.this.requireActivity.getPackageName() + "/" + SpiralAdapter.this.c_name);
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    File file12 = new File(file2.getAbsolutePath() + "/" + substr12);
                    if (!file12.exists()) {
                        SpiralAdapter.this.isDownloading = true;
                        SpiralAdapter.this.isDownloadingStart = true;
                        holder.progressBar.setVisibility(View.VISIBLE);
                        holder.ivDownload.setVisibility(View.GONE);
                        SpiralAdapter.this.downloadLink.clear();
                        SpiralAdapter.this.downloadLink.addAll(Arrays.asList(SpiralAdapter.this.list.get(position).front, SpiralAdapter.this.list.get(position).back));
                        for (int j = 0; j < SpiralAdapter.this.downloadLink.size(); j++) {
                            SpiralAdapter spiralAdapter = SpiralAdapter.this;
                            spiralAdapter.downloadImage((String) spiralAdapter.downloadLink.get(j), holder, v);
                            Log.e("downloadLink", "onClick: length = " + ((String) SpiralAdapter.this.downloadLink.get(j)).length());
                            Log.e("downloadLink", "onClick: size = " + SpiralAdapter.this.downloadLink.size());
                        }
                        return;
                    }
                    int i = SpiralAdapter.this.selectedPos;
                    SpiralAdapter.this.selectedPos = holder.getAdapterPosition();
                    SpiralAdapter.this.notifyItemChanged(i);
                    SpiralAdapter spiralAdapter2 = SpiralAdapter.this;
                    spiralAdapter2.notifyItemChanged(spiralAdapter2.selectedPos);
                    SpiralAdapter.this.click.OnClick(position);
                    return;
                }
                Toast.makeText(SpiralAdapter.this.requireActivity, "Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes5.dex */
    public class MyDemo extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView ivDone;
        ImageView ivDownload;
        ProgressBar progressBar;

        public MyDemo(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.ivDownload = (ImageView) itemView.findViewById(R.id.ivDownload);
            this.ivDone = (ImageView) itemView.findViewById(R.id.ivDone);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadImage(String back_image, final MyDemo holder, View v) {
        String substr = back_image.substring(back_image.lastIndexOf("/"));
        String substr1 = substr.substring(1, substr.length());
        Log.e("rrrr", "onClick: 16" + substr1);
        File file = new File("/data/user/0/" + this.requireActivity.getPackageName() + "/" + this.c_name);
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.e("download", "onClick: 16" + back_image);
        DownloadImpl.getInstance().with("/data/user/0/" + this.requireActivity.getPackageName() + "/" + this.c_name).target(new File(file, "")).setUniquePath(false).setForceDownload(true).url(String.valueOf(Uri.parse(back_image))).enqueue(new DownloadListenerAdapter() { // from class: com.example.photoareditor.Adapter.SpiralAdapter.2
            @Override // com.download.library.DownloadListenerAdapter, com.download.library.DownloadListener
            public void onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
                super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra);
                SpiralAdapter.this.isDownloading = false;
                SpiralAdapter.this.isDownloadingStart = false;
            }

            @Override // com.download.library.DownloadListenerAdapter, com.download.library.DownloadingListener
            public void onProgress(String url, long downloaded, long length, long usedTime) {
                super.onProgress(url, downloaded, length, usedTime);
                Log.i("TAG", " progress:" + downloaded + " url:" + url);
                int mProgress = (int) ((((float) downloaded) / Float.valueOf((float) length).floatValue()) * 100.0f);
                Log.e("TAG", "onProgress: " + mProgress);
                SpiralAdapter.this.isDownloading = false;
                SpiralAdapter.this.isDownloadingStart = false;
                holder.progressBar.setProgress(mProgress);
            }

            @Override // com.download.library.DownloadListenerAdapter, com.download.library.DownloadListener
            public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                Log.i("TAG", " path:" + path + " url:" + url + " length:" + new File(path.getPath()).length());
                holder.progressBar.setVisibility(View.GONE);
                holder.ivDownload.setVisibility(View.GONE);
                holder.ivDone.setVisibility(View.GONE);
                SpiralAdapter.this.isDownloading = false;
                SpiralAdapter.this.isDownloadingStart = false;
                return super.onResult(throwable, path, url, extra);
            }
        });
    }
}
