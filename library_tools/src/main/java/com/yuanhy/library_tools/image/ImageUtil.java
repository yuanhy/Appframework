package com.yuanhy.library_tools.image;

import android.util.Log;

import com.yuanhy.library_tools.util.SdCardUtil;
import com.yuanhy.library_tools.util.StringUtil;

import java.io.File;
import java.io.IOException;

public class ImageUtil {
    /**
     *
     * @param directoryPath 文件夹路径
     * @param imageName 文件名字  不要携带后缀 ，自动补充URL中的后缀，默认 .jpg
     * @param url  图片的URL 通过
     * @return
     */
    protected static File newImageFile(String directoryPath,String imageName, String url) {
        String suffix = ".jpg";
        if (StringUtil.isNotNull(url)) {
            String[] str = url.split("\\.");
            if (str != null) {
                if (str[str.length - 1].length()<5){
                    suffix = "." + str[str.length - 1];
                }
            }
        }
        File fileDirectoryPath = new File(directoryPath );
        if (!fileDirectoryPath.exists()) {
            fileDirectoryPath.mkdirs();
        }
        File file = new File(directoryPath + "/" + imageName + suffix);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
