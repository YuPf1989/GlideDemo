package com.rain.glidedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rain.glidedemo.glide.GlideApp;
import com.rain.glidedemo.glide.ProgressInterceptor;
import com.rain.glidedemo.glide.ProgressListenerImpl;

import java.io.File;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_downloadOnly;
    private TextView tv_content;
    private ImageView img_content;
    private static final String img_url = "http://www.mypublic.top/long/13.jpg";
    public static final String bigUrl = "http://www.mypublic.top/long/13_13.jpg";
    private Button btn_loadImg;
    private ContentLoadingProgressBar progressBar;
    private LinearLayout layout_main;
    private ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_main = findViewById(R.id.layout_main);
        btn_downloadOnly = findViewById(R.id.btn_downloadOnly);
        btn_downloadOnly.setOnClickListener(this);
        tv_content = findViewById(R.id.tv_content);
        img_content = findViewById(R.id.img_content);
        img_content.setOnClickListener(this);
        btn_loadImg = findViewById(R.id.btn_loadImg);
        btn_loadImg.setOnClickListener(this);
        findViewById(R.id.btn_customAPI).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btn_addView).setOnClickListener(this);
        img2 = findViewById(R.id.img2);
        findViewById(R.id.btn_setAndLoad).setOnClickListener(this);
        findViewById(R.id.btn_nine_recycler).setOnClickListener(this);


    }

    private void setAndLoadImage() {
        img2.setImageResource(R.mipmap.ic_launcher);
        GlideApp.with(this).load(bigUrl).into(img2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // glide只下载图片不加载
            case R.id.btn_downloadOnly:
                downLoadImage();
                break;
            case R.id.btn_loadImg:
                loadImg();
                break;
            case R.id.btn_customAPI:
                loadImg2();
                break;
            case R.id.img_content:
                Intent intent = new Intent(MainActivity.this, SingleImageActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MainActivity.this, img_content, "shared");
                ActivityCompat.startActivity(MainActivity.this, intent, compat.toBundle());
                break;

            case R.id.btn_addView:
                View view = LayoutInflater.from(this).inflate(R.layout.layout_btn, null, false);
                layout_main.addView(view);
                break;
            case R.id.btn_setAndLoad:
                setAndLoadImage();
                break;

            case R.id.btn_nine_recycler:
                startActivity(new Intent(this,NineRecyclerActivity.class));
                break;
        }
    }

    /**
     * 练习使用自定义api
     */
    private void loadImg2() {
        GlideApp.with(this)
                .load(img_url)
                .circleCrop()
                .cacheSource()
                .into(img_content);
    }

    private void loadImg() {
        ProgressInterceptor.addListener(img_url, new ProgressListenerImpl() {
            @Override
            public void onProgress(final int progress) {
                progressBar.setProgress(progress);
            }
        });

        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(this).load(img_url).apply(options).into(new DrawableImageViewTarget(img_content) {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                super.onResourceReady(resource,transition);
                progressBar.hide();
                ProgressInterceptor.removeListener(img_url);
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                progressBar.show();
            }
        });
    }

    private void downLoadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 使用app生命周期是为了防止activity销毁，但子线程还没执行完这种可能出现
                Context applicationContext = getApplicationContext();
                RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
                FutureTarget<File> fileFutureTarget = Glide.with(applicationContext).asFile().load(img_url).apply(options).submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    File file = fileFutureTarget.get();
                    final String path = file.getPath();
                    setTextOnUI(path);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void setTextOnUI(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_content.setText("path:" + path);
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
