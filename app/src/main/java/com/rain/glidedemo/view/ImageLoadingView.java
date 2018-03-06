/*
 *
 *  * Copyright (C) 2015 yinglan sufly0001@gmail.com
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */
package com.rain.glidedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class ImageLoadingView extends View {

    private Paint mPaint1;
    private Paint mPaint2;
    private Context mContext;
    private double percent = 0.083;
    private float interval;
    private float radius;
    private int type = 1;


    public static class ViewType {
        public final static int VIDEO = 0;
        public final static int IMAGE = 1;
    }


    public ImageLoadingView(Context context) {
        this(context, null);
    }

    public ImageLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (null == attrs) {//这里表示是代码new出来的对象，而非布局中find的对象
            //告诉父布局，我的位置要是这样的的参数坐标和大小
            if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dip2Px(50), dip2Px(50), Gravity.CENTER);
                setLayoutParams(layoutParams);
            }
        }
        //创建画笔1，
        mPaint1 = new Paint();
        //设置抗锯齿
        mPaint1.setAntiAlias(true);
        //设置画笔颜色
        mPaint1.setColor(Color.WHITE);
        //创建画笔2
        mPaint2 = new Paint();
        //设置抗锯齿
        mPaint2.setAntiAlias(true);
        //设置为画线模式
        mPaint2.setStyle(Paint.Style.STROKE);
        //设置画笔颜色
        mPaint2.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化圆的半径
        radius = getWidth() >= getHeight() ? getHeight() : getWidth();
        //初始化间隔
        interval = (float) (radius * 0.06);
        //初始化线粗
        mPaint2.setStrokeWidth(interval / 3);

        RectF localRect = new RectF(getWidth() / 2 - radius / 2 + interval, getHeight() / 2 - radius / 2 + interval, getWidth() / 2 + radius / 2 - interval, getHeight() / 2 + radius / 2 - interval);

        float f1 = (float) (percent * 360);
        //开始画椭圆
        canvas.drawArc(localRect, -90, f1, true, mPaint1);
        canvas.save();
        //开始画圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius / 2 - interval / 3, mPaint2);
        canvas.restore();
    }

    public void setProgress(double progress) {
        if (progress == 0) {
            progress = 0.083;
        } else if ((progress < 0 || progress >= 1) && ViewType.IMAGE == type) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        this.percent = progress;
        invalidate();//重新执行onDraw方法,重新绘制图形
    }

    public void loadCompleted() {
        setVisibility(GONE);
    }

    public void loadCompleted(int type) {
        this.type = type;
        setProgress(1.0);
    }

    public void loadFaild() {
        setProgress(1.0);
        setVisibility(GONE);
    }

    public void setOutsideCircleColor(int color) {
        mPaint2.setColor(color);
    }

    public void setInsideCircleColor(int color) {
        mPaint1.setColor(color);
    }

    public void setTargetView(View target) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }

        if (target == null) {
            return;
        }

        if (target.getParent() instanceof FrameLayout) {
            ((FrameLayout) target.getParent()).addView(this);

        } else if (target.getParent() instanceof ViewGroup) {
            ViewGroup parentContainer = (ViewGroup) target.getParent();
            int groupIndex = parentContainer.indexOfChild(target);
            parentContainer.removeView(target);

            FrameLayout badgeContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();

            badgeContainer.setLayoutParams(parentLayoutParams);
            target.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
            badgeContainer.addView(target);

            badgeContainer.addView(this);
        } else if (target.getParent() == null) {

        }

    }

    /*
     * converts dip to px
     */
    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}

