package com.vexcellent.saihttplib.core;

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
    private String type;
    private int status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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

    @Override
    public String toString() {
        return "ResponseBean{" +
                "data=" + data +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }

    public boolean isRequestSuccess() {
        return status == 200;
    }
}
