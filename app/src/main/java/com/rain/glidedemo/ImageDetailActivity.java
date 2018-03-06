package com.rain.glidedemo;

import android.annotation.TargetApi;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.glidedemo.data.DataUtils;
import com.rain.glidedemo.image.ImageFragmentPagerAdapter;
import com.rain.glidedemo.image.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:rain
 * Date:2018/3/1 11:03
 * Description:
 */

public class ImageDetailActivity extends AppCompatActivity {
    private ArrayList<DataUtils.ImageDataDetail> data;
    private static final String TAG = "ImageDetailActivity";
    private ViewPager viewPager;
    private TextView pageText;
    private int size;
    private int currentPosition;
    private ImagePagerAdapter adapter;
    private ImageFragmentPagerAdapter adapter2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_image_detail);
        viewPager = findViewById(R.id.viewPager);
        pageText = findViewById(R.id.pageCount);
        getIntentData();
        initViewPager();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initViewPager() {
//        adapter = new ImagePagerAdapter(this, data);
//        adapter.setOnSingleClickListener(new ImagePagerAdapter.SingleClickListener() {
//            @Override
//            public void onSingleClick() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    finishAfterTransition();
//                } else {
//                    finish();
//                }
//            }
//        });
        adapter2 = new ImageFragmentPagerAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter2);
        viewPager.setCurrentItem(currentPosition);
        // 缓存几张页面
//        viewPager.setOffscreenPageLimit(data.size() - 1);

        if (size == 1) {
            pageText.setVisibility(View.GONE);
        } else {
            pageText.setText((currentPosition + 1) + "/" + size);
        }

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                pageText.setText((position + 1) + "/" + size);
            }
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                Fragment currentFragment = (Fragment) viewPager.getAdapter()
                        .instantiateItem(viewPager, currentPosition);
                View view = currentFragment.getView();
                if (view == null) {
                    return;
                }
                sharedElements.put(data.get(currentPosition).source, view.findViewById(R.id.photoView));
                Log.e(TAG, "onMapSharedElements: data.get(currentPosition).source:" + data.get(currentPosition).source);
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("data") && intent.hasExtra("position")) {
            data = (ArrayList<DataUtils.ImageDataDetail>) intent.getSerializableExtra("data");
            currentPosition = intent.getIntExtra("position", 0);
            size = data.size();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        NineRecyclerActivity.newTransitionName = data.get(currentPosition).source;
        super.supportFinishAfterTransition();
    }
}
