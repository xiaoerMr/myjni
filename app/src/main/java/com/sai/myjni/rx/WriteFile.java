package com.sai.myjni.rx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class WriteFile {

    //APK文件类型
    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    //PNG文件类型
    private static String PNG_CONTENTTYPE = "image/png";
    //JPG文件类型
    private static String JPG_CONTENTTYPE = "image/jpg";
    //文件后缀名
    private static String fileSuffix = "";


    public boolean writeResponseBodyToDisk(File file, ResponseBody body) {
        // 获取文件类型
        String type = body.contentType().toString();

        if (type.equals(APK_CONTENTTYPE)) {
            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENTTYPE)) {
            fileSuffix = ".png";
        }else if (type.equals(JPG_CONTENTTYPE)) {
            fileSuffix = ".jpg";
        }

        return writeFile(file, body);
    }

    private boolean writeFile(File file, ResponseBody body) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {

            inputStream = body.byteStream();
//            long length = body.contentLength();

            outputStream = new FileOutputStream(file);

//            long sum = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
//                sum += len;
                //进度
//                int progress = (int) (sum * 1.0f / length * 100);
            }
            outputStream.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
