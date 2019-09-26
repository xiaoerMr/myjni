package com.vexcellent.saihttplib.core;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author 店小二
 * @date 2019/9/26.
 * GitHub：
 * email：
 * description：
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody body) {
        responseBody = body;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(sourceResponse(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source sourceResponse(BufferedSource source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
//                currentListener.doCurrent(bytesReaded,contentLength());
                //实时发送当前已读取的字节和总字节
//                SaiLiveDateBus.SingletonHolder().with("downProgress",Object.class).setValue(new long[]{bytesReaded,contentLength()});
//                RxBus.getInstance().post(new FileLoadEvent(, ));
                Log.e("--ProgressResponseBody-下载--", "--" + bytesReaded + "-->" + contentLength());
                return bytesRead;
            }
        };
    }

//    private CurrentListener currentListener;
//
//    public void setCurrentListener(CurrentListener currentListener) {
//        this.currentListener = currentListener;
//    }
//
//    public interface CurrentListener{
//
//        void doCurrent(long current, long totle);
//    }
}
