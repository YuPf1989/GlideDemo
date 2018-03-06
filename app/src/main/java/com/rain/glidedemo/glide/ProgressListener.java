package com.rain.glidedemo.glide;

/**
 * Author:rain
 * Date:2018/2/23 16:35
 * Description:
 */

public interface ProgressListener {
    void onProgress(int progress);
    void onPrepare();
    void onComplete();
}
