package com.sai.myjni.socket.socket;

import java.io.IOException;

public interface BaseSocket {

    void initSocket() throws IOException;
    void socketClose() throws IOException;
    void socketSend(String msg) throws IOException;
    String socketReceive() throws IOException;
    void reconnectionSocket() throws IOException;
    boolean socketLive();
}
