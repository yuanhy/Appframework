package com.yuanhy.library_tools.demo.rxjavademo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2019/4/2.
 */

public class SyncStudentInfoEnty2 implements Serializable {
    /**
     * 1成功
     */
    int status;
    boolean all;
    String reason;
    ArrayList<SyncStudentInfoEnty2> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ArrayList<SyncStudentInfoEnty2> getData() {
        return data;
    }

    public void setData(ArrayList<SyncStudentInfoEnty2> data) {
        this.data = data;
    }
}
