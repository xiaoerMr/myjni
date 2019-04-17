package com.sai.sailib.file;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class FileUtils {


    String filePath = Environment.getExternalStorageDirectory().getPath() + "/AAAA/";

    /**
     * 获取存储卡文件根目录
     * @return 一般是/storage/emulated/0/
     */
    public static String getSDCardPath(){
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 从 assets 文件中读取  mprespons.txt doReadFileFromAss
     * @param context 文件
     * @param fileName 文件
     * @return text
     */
    public static String doReadFileFromAss(Context context, String fileName) {
        InputStream is = null;
        String msg = null;
        try {
            is = context.getResources().getAssets().open(fileName);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            msg = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    /**
     *  文件中读取
     * @param filePath 要读的文件路径
     * @return 文件内容
     */
    public static String doReadFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        //开始读取文件
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(0);// 读取时，将指针重置到文件的开始位置。
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = raf.read(buf)) > 0) {
                buffer.append(new String(buf, 0, len));
            }
            raf.close();


        return buffer.toString();
    }


    /**
     * 将字符串写入到文件
     * @param strcontent 要写入的字符串
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    public static void doWriteFile(String strcontent, String filePath, String fileName) throws Exception {
        //创建文件夹之后，再生成目标文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n" + "\r\n" + "\r\n";
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();

    }

    private static File makeFilePath(String filePath, String fileName) throws IOException {
        File file = null;
        makeRootDirectory(filePath);
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        return file;
    }

    // 生成文件夹
    private static void makeRootDirectory(String filePath) {
        File file = null;
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
    }

}
