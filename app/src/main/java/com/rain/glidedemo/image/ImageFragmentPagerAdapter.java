package com.rain.glidedemo.image;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rain.glidedemo.data.DataUtils;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/3/5 11:51
 * Description:
 */

public class ImageFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<DataUtils.ImageDataDetail> data;

    public ImageFragmentPagerAdapter(FragmentManager fm, ArrayList<DataUtils.ImageDataDetail> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
