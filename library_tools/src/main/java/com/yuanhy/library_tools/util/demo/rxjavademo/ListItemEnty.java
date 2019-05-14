package com.yuanhy.library_tools.util.demo.rxjavademo;

import java.io.Serializable;

public class ListItemEnty implements Serializable {
   String  data  ;//Object{...},
    String        errorCode  ;//0,
    String         errorMsg  ;//

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
