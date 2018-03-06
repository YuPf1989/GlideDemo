package com.rain.glidedemo.image;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.rain.glidedemo.R;
import com.rain.glidedemo.data.DataUtils;
import com.rain.glidedemo.glide.GlideApp;
import com.rain.glidedemo.glide.ProgressListener;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/3/1 15:13
 * Description:
 */

public class ImagePagerAdapter extends PagerAdapter {
    private ArrayList<DataUtils.ImageDataDetail> data;
    private Context context;
    private SingleClickListener listener;

    public ImagePagerAdapter(Context context, ArrayList<DataUtils.ImageDataDetail> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }



    /**
     * 实例化view时调用
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_image_photo, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        DataUtils.ImageDataDetail imageDataDetail = data.get(position);
        GlideApp.with(context)
                .load(imageDataDetail.source)
                .thumbnail(Glide.with(context).load(imageDataDetail.thumb))
                .loadImage(imageDataDetail.source, new ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        viewHolder.progressBar.setProgress(progress);
                    }
                    @Override
                    public void onPrepare() {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    @Override
                    public void onComplete() {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                })
                .into(viewHolder.photoView);
        container.addView(view);
        return view;
    }

    class ViewHolder implements View.OnClickListener {
        View rootView;
        PhotoView photoView;
        ProgressBar progressBar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            photoView = rootView.findViewById(R.id.photoView);
            progressBar = rootView.findViewById(R.id.progressView);
            photoView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onSingleClick();
            }
        }
    }

    public interface SingleClickListener{
        void onSingleClick();
    }

    /**
     * 单个图片的点击事件
     * @param listener
     */
    public void setOnSingleClickListener(SingleClickListener listener){
        this.listener = listener;
    }
}
