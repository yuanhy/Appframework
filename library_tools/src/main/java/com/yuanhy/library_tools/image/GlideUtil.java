package com.yuanhy.library_tools.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/4/9.
 */

public class GlideUtil {
public static void setImage(Context context, String path, ImageView imageView){
    CornerTransform transformation = new CornerTransform(context, dip2px(context, 10));
//只是绘制左上角和右上角圆角
    transformation.setExceptCorner(false, false, false, false);

    Glide.with(context)
            .load(path)
            .asBitmap()
            .skipMemoryCache(true)
            .placeholder(R.drawable.user_ico)
            .error(R.drawable.icon_errorimg)
            .transform(transformation)
            .into(imageView);
}

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
        /**
         * Glide 加载图片保存到本地
         * @param context
         * @param imgUrl 图片地址
         * @param imgFileName 图片文件绝对地址
         */
    public static void savaImage(final Context context, String imgUrl, final String  imgFileName, final YCallBack callBack){
        Glide.with(context).load(imgUrl).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                callBack.requestFail(false);
                LogCatUtil.getInstance(context)
                        .i("savaImage",  imgFileName+" 图片保存失败："+e.getMessage());
                super.onLoadFailed(e, errorDrawable);
            }

            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    if (bytes==null||bytes.length==0){
                        callBack.requestFail(false);
                        return;
                    }
                    boolean   isSuccess=   savaBitmap(imgFileName, bytes);
                    if (isSuccess){
                        callBack.requestSuccessful(isSuccess);
                    }else {
                        callBack.requestFail(isSuccess);
                    }
                } catch (Exception e) {
                    callBack.requestFail(null);
                    e.printStackTrace();
                }
            }
        });
    }
    // 保存图片到手机指定目录
    private static boolean savaBitmap(String imgFileName, byte[] bytes) {
        boolean   isSuccess = false;
//        DataOutputStream dataOutputStream;
        FileOutputStream fos = null;
        try {
//            dataOutputStream = new DataOutputStream(
//                    new BufferedOutputStream(
//                            new FileOutputStream(imgFileName)
//                    )
//            );

            fos = new FileOutputStream(imgFileName);
//            dataOutputStream.write(bytes);
            fos.write(bytes);
            isSuccess =true;
            Log.v("SavaImageFilePath","成功--->"+imgFileName);
        } catch (IOException e) {
            Log.v("SavaImageFilePath","失败--->"+imgFileName);
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

}
