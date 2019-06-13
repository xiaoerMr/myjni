package com.sai.myjni.socket.socket;

public interface SocketListener {
    void onSuccess(String data);
    void onFail(int failcode);
}
