package com.sai.myjni.rx;

public class BaseResponse<T> {
    public int code;
    public String msg;
    public T data;

    public boolean isSuccess(){
       return code == 200;
    }
}
