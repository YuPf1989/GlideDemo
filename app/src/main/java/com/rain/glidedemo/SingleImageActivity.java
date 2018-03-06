package com.rain.glidedemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rain.glidedemo.glide.GlideApp;
import com.rain.glidedemo.glide.ProgressListener;
import com.rain.glidedemo.view.CircleProgressView;

/**
 * Author:rain
 * Date:2018/2/24 14:59
 * Description:
 */

public class SingleImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    public final String bigUrl = "http://www.mypublic.top/long/13_13.jpg";
    public final String smallUrl = "http://www.mypublic.top/long/13.jpg";
    private CircleProgressView progressBar;
    private static final String TAG = "SingleImageActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);

        image = findViewById(R.id.image);
        image.setOnClickListener(this);
        progressBar = findViewById(R.id.progressView);
        loadImage2();

    }

    private void loadImage2() {
        GlideApp.with(this)
                .load(bigUrl)
                .thumbnail(Glide.with(this).load(smallUrl))
                .loadImage(bigUrl, new ProgressListener() {

                    @Override
                    public void onProgress(int progress) {
                        progressBar.setProgress(progress);
                    }

                    @Override
                    public void onPrepare() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                })
                .into(image);

    }

    @Override
    public void onClick(View view) {
        // 如果大于>安卓5.0，就以动画形式关闭
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();

    }
}
