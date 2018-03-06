package com.rain.glidedemo.image;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 为何要自定义viewpager？
 */
public class ImageDetailViewPager extends ViewPager {


    public ImageDetailViewPager(Context context) {
        super(context);
    }


    public ImageDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO: 2018/3/1  为何要自定义viewpager？
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

}
