package com.rain.glidedemo.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.glidedemo.R;
import com.rain.glidedemo.data.DataUtils;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/3/1 9:37
 * Description:
 */
public class MyRecyclerAdapter extends BaseQuickAdapter<DataUtils.ImageData, BaseViewHolder> {
    private Activity activity;

    public MyRecyclerAdapter(int layoutResId, @Nullable ArrayList<DataUtils.ImageData> data, Activity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataUtils.ImageData item) {
        helper.setText(R.id.text, item.des);
        RecyclerView recycler = helper.getView(R.id.nine_recycler);
        recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycler.addItemDecoration(new DividerGridItemDecoration());
        recycler.setAdapter(new NineImageAdapter(R.layout.layout_item_nine_image, item.list, activity));
    }

}
