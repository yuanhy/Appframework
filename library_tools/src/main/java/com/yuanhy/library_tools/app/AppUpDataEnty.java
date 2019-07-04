package com.yuanhy.library_tools.app;

import java.io.Serializable;

public class AppUpDataEnty implements Serializable {

    String update  ;//  Yes ,
    String new_version  ;//  0.8.3 ,
    String apk_file_url  ;//  https ;////raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk ,
    String update_log  ;//  1，添加删除信用卡接口。\r\n2，添加vip认证。\r\n3，区分自定义消费，一个小时不限制。\r\n4，添加放弃任务接口，小时内不生成。\r\n5，消费任务手动生成。 ,
    String target_size  ;//  5M ,
    String new_md5  ;// b97bea014531123f94c3ba7b7afbaad2 ,
    String constraint  ;// false

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public String getNew_md5() {
        return new_md5;
    }

    public void setNew_md5(String new_md5) {
        this.new_md5 = new_md5;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }
}
