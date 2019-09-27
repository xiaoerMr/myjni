package com.vexcellent.saihttplib.down;

/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：
 */
public class DownInfo {
    /**
     * 存储位置
     */
    private String savePath;
    /**
     * 回调监听
     */
    private SaiDownListener listener;


    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public void setListener(SaiDownListener listener) {
        this.listener = listener;
    }

    public String getSavePath() {
        return savePath;
    }

    public SaiDownListener getListener() {
        return listener;
    }


}
