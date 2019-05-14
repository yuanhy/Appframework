/*
package com.yuanhy.library_tools.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import app.yuanhy.com.lib_tools.R;

public class GlideImageViewUtil {
 private  static    GlideImageViewUtil glideImageViewUtil;
    private  static    RequestOptions options;
    private  static   RequestOptions optionsUserIco;
    private GlideImageViewUtil(){
          options = new RequestOptions()
                .placeholder(null).error(R.mipmap.icon_errorimg).diskCacheStrategy(DiskCacheStrategy.NONE);
        optionsUserIco = new RequestOptions()
                .circleCropTransform()
                .placeholder(R.drawable.user_ico).error(R.drawable.user_ico).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }
    public static GlideImageViewUtil getGlideImageViewUtil(){
         if (glideImageViewUtil==null){
             glideImageViewUtil= new GlideImageViewUtil();
         }
        return glideImageViewUtil ;
    }
    public void setUserIco(Context context, String urlpath, ImageView imageView){

        Glide.with(context).load(urlpath).apply(optionsUserIco).into(imageView);
    }
    public void setUserIco(Context context, int drawable, ImageView imageView){

        Glide.with(context).load(context.getResources().getDrawable(drawable)).apply(optionsUserIco).into(imageView);
    }
    public void setImageView(Context context, String urlpath, ImageView imageView){
        Glide.with(context).load(urlpath).apply(options).into(imageView);
    }
    public void setImageView(Context context, int drawable, ImageView imageView){
        Glide.with(context).load(context.getResources().getDrawable(drawable)).apply(options).into(imageView);

    }
    public void setGifImageView(Context context, String urlpath, ImageView imageView){
        Glide.with(context).asGif().load(urlpath).apply(options).into(imageView);
    }

    */
/**
     * Gif 加载 可以设置次数，监听播放完成回调
     * @param context  上下文对象
     * @param model 传入的gif地址，可以是网络，也可以是本地，（https://raw.githubusercontent.com/Jay-YaoJie/KotlinDialogs/master/diagram/test.gif）
     * @param imageView 要显示的imageView
     * @param loopCount 播放次数
     * @param gifListener  Gif播放完毕回调
     *//*

    public static void loadOneTimeGif(Context context, Object model, final ImageView imageView , final int loopCount, final GifListener gifListener) {
        Glide.with(context).asGif().load(model).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    Field gifStateField = GifDrawable.class.getDeclaredField("state");
                    gifStateField.setAccessible(true);
                    Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
                    Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
                    gifFrameLoaderField.setAccessible(true);
                    Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
                    Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
                    gifDecoderField.setAccessible(true);
                    Class gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder");
                    Object gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
                    Method getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", int.class);
                    getDelayMethod.setAccessible(true);
                    ////设置播放次数
                    resource.setLoopCount(loopCount);
                    //获得总帧数
                    int count = resource.getFrameCount();
                    int delay = 0;
                    for (int i = 0; i < count; i++) {
                        //计算每一帧所需要的时间进行累加
                        delay += (int) getDelayMethod.invoke(gifDecoder, i);
                    }
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (gifListener != null) {
                                gifListener.gifPlayComplete();
                            }
                        }
                    }, delay);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }).into(imageView);

    }

    */
/**
     * Gif播放完毕回调
     *//*

    public interface GifListener {
        void gifPlayComplete();
    }


    //清除内存缓存
    public void clearMemory(Context context){
        // 必须在UI线程中调用
        Glide.get(context).clearMemory();
    }



  public Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public void saveMyBitmap(Bitmap mBitmap, Context context) {
        File f = new File(Environment.getExternalStorageDirectory() + "/ico.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LogCatUtil.getInstance(context) .v("savaImag", "在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
*/
