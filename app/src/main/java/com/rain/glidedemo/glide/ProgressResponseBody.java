package com.rain.glidedemo.glide;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Author:rain
 * Date:2018/2/23 16:50
 * Description:
 */

public class ProgressResponseBody extends ResponseBody {

    private static final String TAG = "ProgressResponseBody";

    private BufferedSource bufferedSource;

    private ResponseBody responseBody;

    private ProgressListener listener;

    private String url;

    private boolean startLoad = true;

    private Handler mHandler;


    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.url = url;
        this.responseBody = responseBody;
        listener = ProgressInterceptor.LISTENER_MAP.get(url);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;

        int currentProgress;

        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = responseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            final int progress = (int) (100f * totalBytesRead / fullLength);
            Log.e(TAG, "download progress is " + progress);

            if (listener != null && startLoad) {
                listener.onPrepare();
                startLoad = false;
                // 切换到主线程
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.onPrepare();
//                        startLoad = false;
//                    }
//                });
            }
            if (listener != null && progress != currentProgress) {
                listener.onProgress(progress);
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener.onComplete();
                ProgressInterceptor.removeListener(url);
                listener = null;
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.onComplete();
//                        ProgressInterceptor.removeListener(url);
//                        listener = null;
//                    }
//                });
            }
            currentProgress = progress;
            return bytesRead;
        }
    }
}
