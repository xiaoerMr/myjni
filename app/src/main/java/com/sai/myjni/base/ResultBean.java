package com.sai.myjni.base;

import com.vexcellent.saihttplib.core.ResponseBean;

/**
 * @author 店小二
 * @date 2019/9/25.
 * GitHub：
 * email：
 * description：
 */
public class ResultBean<T> extends ResponseBean<T> {
    private String type;
    private int status;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getErrorMsg() {
        return type;
    }

    @Override
    public int getErrorCode() {
        return status;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "type='" + type + '\'' +
                ", status=" + status +
                '}';
    }
}
