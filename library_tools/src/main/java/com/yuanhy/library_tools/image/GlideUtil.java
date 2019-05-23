package com.yuanhy.library_tools.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/4/9.
 */

public class GlideUtil {
   private static  String TAG="GlideUtil";
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
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imgFileName);
            fos.write(bytes);
            isSuccess =true;
           AppFramentUtil.logCatUtil.v ("SavaImageFilePath","成功--->"+imgFileName);
        } catch (IOException e) {
            AppFramentUtil.logCatUtil.v("SavaImageFilePath","失败--->"+imgFileName);
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
    /**
     * Glide 加载图片保存到本地
     * @param context
     * @param imgUrl 图片地址
     * @param imgFileName 图片文件绝对地址
     */
    public static void savaImage(final Context context,String imgUrl, final String  imgFileName, String directoryPath,
                                 final YCallBack callBack){
       File file= ImageUtil. newImageFile(directoryPath,imgFileName,imgUrl);
      if (file==null||!file.exists()){
          callBack.requestFail(false);
          LogCatUtil.getInstance(context)
                  .i(TAG,  imgFileName+" 图片创建失败：");
          return;
      }
        Glide.with(context).load(imgUrl).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                callBack.requestFail(false);
                LogCatUtil.getInstance(context)
                        .i("savaImage",  imgFileName+" 图片保存失败：");
                super.onLoadFailed(e, errorDrawable);
            }

            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    if (bytes==null||bytes.length==0){
                        callBack.requestFail(false);
                        return;
                    }
                    boolean   isSuccess=   savaBitmap(directoryPath+imgFileName, bytes);
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
}
