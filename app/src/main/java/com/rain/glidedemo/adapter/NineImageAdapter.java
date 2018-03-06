package com.rain.glidedemo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.glidedemo.ImageDetailActivity;
import com.rain.glidedemo.NineRecyclerActivity;
import com.rain.glidedemo.R;
import com.rain.glidedemo.data.DataUtils;
import com.rain.glidedemo.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:rain
 * Date:2018/3/1 10:46
 * Description:
 */

public class NineImageAdapter extends BaseQuickAdapter<DataUtils.ImageDataDetail, BaseViewHolder> {
    private Activity activity;

    public NineImageAdapter(int layoutResId, @Nullable List data, Activity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DataUtils.ImageDataDetail item) {
        final ArrayList<DataUtils.ImageDataDetail> data = (ArrayList<DataUtils.ImageDataDetail>) getData();
        final int position = helper.getAdapterPosition();
        ImageView view = helper.getView(R.id.image);
        GlideApp.with(view)
                .load(item.thumb)
                .into(view);
        // 为view绑定tag
        view.setTag(item.source);
        // 为每个view设置动画名
        ViewCompat.setTransitionName(view, item.source);
        // todo 有没有办法将回调设置在activity中？就不需要传activity参数了
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击时需要重新设置newTransitionName
                NineRecyclerActivity.newTransitionName = item.source;
                Intent intent = new Intent(activity, ImageDetailActivity.class);
                intent.putExtra("data", (data));
                intent.putExtra("position", position);
                if (Build.VERSION.SDK_INT >= 22) {
                    // todo data.get(position).source共享元素名换成了url
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(activity, view, data.get(position).source);
                    ActivityCompat.startActivity(activity, intent, compat.toBundle());

                } else {
                    activity.startActivity(intent);
                }
            }
        });
    }
}
