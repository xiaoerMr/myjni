package com.vexcellent.saihttplib.core;

import android.text.TextUtils;

/**
 * @author 店小二
 * @date 2019/9/20.
 * GitHub：
 * email：
 * description：
 */
public class ResponseBean<T> {

    /**
     * data :
     * type : json
     * status : 500
     */

    private T data;
    private String errorMsg ;
    private int errorCode;
    private String type;
    private int status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        if (TextUtils.isEmpty(errorMsg)) {
            return type;
        }
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        if (errorCode == 0) {
            return getStatus();
        }
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }

    public boolean isRequestSuccess() {
        return errorCode == 200;
    }
}
