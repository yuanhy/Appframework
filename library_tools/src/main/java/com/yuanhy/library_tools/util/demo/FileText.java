package com.yuanhy.library_tools.util.demo;

import com.yuanhy.library_tools.util.FileUtil;
import com.yuanhy.library_tools.util.SdCardUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2019/4/11.
 */

public class FileText {
    public static void main (String[] arg    ){
        File file =new File("c://text.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       FileUtil.writeString(file,"ceshi",true);
        FileUtil.writeString(file,"ceshi222",true);
        FileUtil.writeString(file,"ceshi3333",true);
    }
}
