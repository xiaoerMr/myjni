package com.sai.myjni.socket.socket;

public class SocketErrorCode {
    public final static int code_connect_ing  = 0x60;
    public final static int code_connect_no   = 0x61;
    public final static int code_connect_die  = 0x62;
    public final static int code_send_ing     = 0x63;
    public final static int code_send_fail    = 0x64;
    public final static int code_receive_ing  = 0x65;
    public final static int code_receive_fail = 0x66;
    public final static int code_close_fail= 0x67;


    public static String getErrorMsg(int code){
        String msg = null;
        switch (code) {
            case code_connect_ing:
                msg = "连接服务器中"; break;
            case code_connect_no:
                msg = "服务器没连接"; break;
            case code_connect_die:
                msg = "服务器主动断开连接"; break;
            case code_send_ing:
                msg = "给服务器发送消息中"; break;
            case code_send_fail:
                msg = "给服务器发送消息失败"; break;
            case code_receive_ing:
                msg = "等待服务器消息"; break;
            case code_receive_fail:
                msg = "接受服务器消息异常"; break;
            case code_close_fail:
                msg = "关闭socket失败"; break;
            default:
                msg = "没有对应的错误信息";
        }
        return msg;
    }
}
