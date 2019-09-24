package com.vexcellent.saihttplib.exception;

/**
 * @author 店小二
 * @date 2019/9/24.
 * GitHub：
 * email：
 * description：
 */
public class SaiException  extends  RuntimeException{

    private  int mErrorCode;

    public SaiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }
}
