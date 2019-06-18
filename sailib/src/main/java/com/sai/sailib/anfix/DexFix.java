package com.sai.sailib.anfix;

import android.content.Context;

import com.sai.sailib.log.DLog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 *  阿里热修复 原理
 */
public class DexFix {

    private Context context;

    /**
     * 手动打包 dex  sdk/build-tools/de.bat
     *  命令:  dx --dex --outpath lll 目标目录
     * 加载下载好的 dex 包
     * @param dex 下载好的 dex 包
     */
    public void loadDex(File dex) throws IOException {
        //上一个dex缓存文件,如果存在则删除
        File optFile = new File(context.getCacheDir(), dex.getName());
        if (optFile.exists()) {
            optFile.delete();
        }

        //加载 dex
        DexFile dexFile = DexFile.loadDex(dex.getAbsolutePath(), optFile.getAbsolutePath(),Context.MODE_PRIVATE);

        //遍历dex中的class
        Enumeration<String> entries = dexFile.entries();
        while (entries.hasMoreElements()) {

            String className = entries.nextElement();
            Class realClass = dexFile.loadClass(className, context.getClassLoader());
            DLog.w("需要替换的类=>"+realClass);

            //替换报错的方法
            replaceMethod(realClass);
        }


    }

    private void replaceMethod(Class realClass) {
        for (Method realMethod : realClass.getDeclaredMethods()) {
            //注解
            FixReplace replace = realMethod.getAnnotation(FixReplace.class);

            if (replace == null) {
                return;
            }
            //错误的clazz
            String clazzWrongName = replace.clazz();
            String methodWrongName = replace.method();

            try {
                //错误的class
                Class classProper = Class.forName(clazzWrongName);
                //得到错误的method对象
                Method wrongMethod = classProper.getMethod(methodWrongName, realMethod.getParameterTypes());

                //进行方法的替换
                replace(wrongMethod,realMethod);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    //通过 jni 替换
    private void replace(Method wrongMethod,Method realMethod) {

    }
}
