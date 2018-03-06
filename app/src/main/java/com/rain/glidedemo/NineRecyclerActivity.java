package com.rain.glidedemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.rain.glidedemo.adapter.MyRecyclerAdapter;
import com.rain.glidedemo.data.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:rain
 * Date:2018/3/1 9:16
 * Description:
 */

public class NineRecyclerActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ArrayList<DataUtils.ImageData> imageData;
    private static final String TAG  = "NineRecyclerActivity";
    public static String newTransitionName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_nine_recycler);
        recycler = findViewById(R.id.recycler);
        initData();
        initRecycler();
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.e(TAG, "onMapSharedElements: NineRecyclerActivity：newTransitionName："+newTransitionName);
                ImageView newSharedView = recycler.findViewWithTag(newTransitionName);
                if (newSharedView != null) {
                    Log.e(TAG, "onMapSharedElements: newSharedView");
                    sharedElements.put(newSharedView.getTransitionName(), newSharedView);
                    // 与上面代码效果一样
//                    sharedElements.put(newTransitionName, newSharedView);
                }
            }
        });
    }

    private void initData() {
        imageData = DataUtils.getImageData();
//        Gson gson = new Gson();
//        String s = gson.toJson(imageData);
//        Log.e(TAG, "initData:json: "+s);
    }

    private void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(R.layout.layout_item_recycler, imageData,this);
        recycler.setAdapter(adapter);
    }
}
