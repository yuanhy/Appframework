package com.yuanhy.appframework.rxjavademo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class PrBean implements Parcelable {
    String errorCode;
    String errorMsg;
    ArrayList<info> data;

    @Override
    public String toString() {
        return "PrBean{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    protected PrBean(Parcel in) {
        errorCode = in.readString();
        errorMsg = in.readString();
        data = in.createTypedArrayList(info.CREATOR);
    }

    public static final Creator<PrBean> CREATOR = new Creator<PrBean>() {
        @Override
        public PrBean createFromParcel(Parcel in) {
            return new PrBean(in);
        }

        @Override
        public PrBean[] newArray(int size) {
            return new PrBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(errorCode);
        dest.writeString(errorMsg);
        dest.writeTypedList(data);
    }

    public static class info implements Parcelable{
        String  desc  ;// 一起来做个App吧 ,
        String        id  ;//10,
        String         imagePath  ;// https ;////www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png ,
        String            isVisible  ;//1,
        String            order  ;//1,
        String         title  ;// 一起来做个App吧 ,
        String           type  ;//0,
        String           url  ;// http://www.wanandroid.com/blog/show/2

        @Override
        public String toString() {
            return "info{" +
                    "desc='" + desc + '\'' +
                    ", id='" + id + '\'' +
                    ", imagePath='" + imagePath + '\'' +
                    ", isVisible='" + isVisible + '\'' +
                    ", order='" + order + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        protected info(Parcel in) {
            desc = in.readString();
            id = in.readString();
            imagePath = in.readString();
            isVisible = in.readString();
            order = in.readString();
            title = in.readString();
            type = in.readString();
            url = in.readString();
        }

        public static final Creator<info> CREATOR = new Creator<info>() {
            @Override
            public info createFromParcel(Parcel in) {
                return new info(in);
            }

            @Override
            public info[] newArray(int size) {
                return new info[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(desc);
            dest.writeString(id);
            dest.writeString(imagePath);
            dest.writeString(isVisible);
            dest.writeString(order);
            dest.writeString(title);
            dest.writeString(type);
            dest.writeString(url);
        }
    }
}
