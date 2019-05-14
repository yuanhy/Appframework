package com.yuanhy.library_tools.util;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yuanhy on 2019/4/10.
 */

public class FileUtil {
   private static String LogFiles=SdCardUtil.getExternalStorageDirectory()+File.separator+"LogFiles";
    public static void writeString(File file,String data,boolean append){
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file,append);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("time:");
            stringBuffer.append(TimeUtil.getDate2());
            stringBuffer.append(":");
            stringBuffer.append(data);
            stringBuffer.append("\n \n");
            fileWritter.write(stringBuffer.toString());
            fileWritter.flush();
            fileWritter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 文件的名字
     * @param fileName
     * @param data
     * @param append
     */
    public static void writeString(String fileName,String data,boolean append){
        try{
            File logfiles= new File(LogFiles);
            boolean isSuerr = false;
            if (!logfiles.exists()){
               isSuerr=   logfiles.mkdirs();
            }
            Log.v("",isSuerr+"");
            String path=logfiles.getAbsolutePath()+File.separator+fileName;
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file,append);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("time:");
            stringBuffer.append(TimeUtil.getDate2());
            stringBuffer.append(":");
            stringBuffer.append(data);
            stringBuffer.append("\n \n");
            fileWritter.write(stringBuffer.toString());
            fileWritter.flush();
            fileWritter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
