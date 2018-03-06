package com.rain.glidedemo.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/3/1 9:59
 * Description:
 */

public class DataUtils {
    public static final String girl_1 = "http://www.mypublic.top/long/1.jpg";
    public static final String girl_1_1 = "http://www.mypublic.top/long/1_1.jpg";
    public static final String girl_2 = "http://www.mypublic.top/long/2.jpg";
    public static final String girl_2_2 = "http://www.mypublic.top/long/2_2.jpg";
    public static final String girl_3 = "http://www.mypublic.top/long/3.jpg";
    public static final String girl_3_3 = "http://www.mypublic.top/long/3_3.jpg";
    public static final String girl_4 = "http://www.mypublic.top/long/4.jpg";
    public static final String girl_4_4 = "http://www.mypublic.top/long/4_4.jpg";
    public static final String girl_5 = "http://www.mypublic.top/long/5.jpg";
    public static final String girl_5_5 = "http://www.mypublic.top/long/5_5.jpg";
    public static final String girl_6 = "http://www.mypublic.top/long/6.jpg";
    public static final String girl_6_6 = "http://www.mypublic.top/long/6_6.jpg";
    public static final String girl_7 = "http://www.mypublic.top/long/7.jpg";
    public static final String girl_7_7 = "http://www.mypublic.top/long/7_7.jpg";
    public static final String girl_8 = "http://www.mypublic.top/long/8.jpg";
    public static final String girl_8_8 = "http://www.mypublic.top/long/8_8.jpg";
    public static final String girl_9 = "http://www.mypublic.top/long/9.jpg";
    public static final String girl_9_9 = "http://www.mypublic.top/long/9_9.jpg";
    public static final String girl_10 = "http://www.mypublic.top/long/10.jpg";
    public static final String girl_10_10 = "http://www.mypublic.top/long/10_10.jpg";
    public static final String girl_11 = "http://www.mypublic.top/long/11.jpg";
    public static final String girl_11_11 = "http://www.mypublic.top/long/11_11.jpg";
    public static final String girl_12 = "http://www.mypublic.top/long/12.jpg";
    public static final String girl_12_12 = "http://www.mypublic.top/long/12_12.jpg";
    public static final String girl_13 = "http://www.mypublic.top/long/13.jpg";
    public static final String girl_13_13 = "http://www.mypublic.top/long/13_13.jpg";
    public static final String girl_14 = "http://www.mypublic.top/long/14.jpg";
    public static final String girl_14_14 = "http://www.mypublic.top/long/14_14.jpg";

    public static ArrayList<ImageData> getImageData() {
        ArrayList<ImageData> data = new ArrayList();

        ImageData imageData1 = new ImageData();
        imageData1.des = "共有" + 1 + "图片";
        imageData1.list = new ArrayList<>();
        ImageDataDetail imageDataDetail = new ImageDataDetail(girl_1,girl_1_1);
        imageData1.list.add(imageDataDetail);
        data.add(imageData1);

        ImageData imageData2 = new ImageData();
        imageData2.des = "共有" + 3 + "图片";
        imageData2.list = new ArrayList<>();
        ImageDataDetail imageDataDetail31 = new ImageDataDetail(girl_2,girl_2_2);
        ImageDataDetail imageDataDetail32 = new ImageDataDetail(girl_3,girl_3_3);
        ImageDataDetail imageDataDetail33 = new ImageDataDetail(girl_4,girl_4_4);
        imageData2.list.add(imageDataDetail31);
        imageData2.list.add(imageDataDetail32);
        imageData2.list.add(imageDataDetail33);
        data.add(imageData2);

        return data;
    }

    public static class ImageData implements Serializable{
        public String des;
        public ArrayList<ImageDataDetail> list;
    }

    public static class ImageDataDetail implements Serializable{
        public ImageDataDetail(String thumb, String source) {
            this.thumb = thumb;
            this.source = source;
        }
        public String thumb;
        public String source;
    }
}
