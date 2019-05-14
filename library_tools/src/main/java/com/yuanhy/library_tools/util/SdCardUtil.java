package com.yuanhy.library_tools.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SdCardUtil {
    //File.separator  斜杠
    /**
     * 获取APP 存储路径
     * data/data/com..../
     * @param context
     * @return
     */
    public static String getAppDataPath(Context context){
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取手机根目录
     * @return
     */
    public static String getExternalStorageDirectory(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */

    private static ArrayList<String> getDevMountList() {
        String[] toSearch = readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    private static String readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder sBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                sBuilder.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		/*
		 * if(sBuilder.toString().length()==0){ Log.i("tag",
		 * "没有读取到文件=========="); }
		 */
        return sBuilder.toString();
    }

    /**
     *
     * 获取扩展SD卡存储目录 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     *
     * 否则：返回内置SD卡目录
     *
     * @return
     */

    public static String getExternalSdCardPath() {
        if (isMounted()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getAbsolutePath();
        }
        String path = null;
        File sdCardFile = null;
        ArrayList<String> devMountList = getDevMountList();
        for (String devMount : devMountList) {
            File file = new File(devMount);
            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();
                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date(0));
                File testWritable = new File(path, "test_" + timeStamp);
                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }
        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }
        return null;
    }

    private static boolean isMounted() {
        if ("removed".equals(Environment.getExternalStorageState())) {
            // Log.i("tag", "sd卡卸载了");
            return false;
        } else if ("mounted".equals(Environment.getExternalStorageState())) {
            // Log.i("tag", "sd卡挂载了");
            return true;
        }
        return false;
    }
}
