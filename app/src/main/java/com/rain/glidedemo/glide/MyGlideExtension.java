package com.rain.glidedemo.glide;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rain.glidedemo.R;

import java.util.concurrent.ExecutionException;

/**
 * Author:rain
 * Date:2018/2/24 10:36
 * Description:自定义链式api
 */
@GlideExtension
public class MyGlideExtension {

    // 固定写法，私有化构造
    private MyGlideExtension() {

    }

    /**
     * 第一个参数必须为RequestOptions，后边可以追加参数
     *
     * @param options
     */
    @GlideOption
    public static void cacheSource(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
    }

    @GlideOption
    public static void loadImage(final RequestOptions options,  final String url2, final ProgressListener listener) {
        ProgressInterceptor.addListener(url2, listener);
    }
}
