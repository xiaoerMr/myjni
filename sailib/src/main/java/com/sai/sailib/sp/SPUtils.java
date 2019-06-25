package com.sai.sailib.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2018/7/26.
 * 要在 application 里调用 init() 方法
 */

public class SPUtils {
    /**
     * 上下文
     */
    private static Context mContext;
    private static String mFileName;
    private static SharedPreferences sp;

    /**
     * 初始化SharedPreferences，必须使用该方法
     */
    public static void init(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
        sp = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
    }

    /**
     * 抛出异常
     */
    private static void throwInit(){
        if (mContext == null) {
            throw new NullPointerException("在使用该方法前，需要使用init()方法，推荐将init()放入Application中");
        }
    }

    /**
     * 插入字符串
     * @param key key
     * @param value 值
     */
    public static void putString(String key, String value) {
        throwInit();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }


    public static void putSPValue(Map<String, Object> map) {
        throwInit();

        SharedPreferences.Editor edit = sp.edit();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getValue() instanceof String) {
                edit.putString(entry.getKey(), (String)entry.getValue());
            }

            if (entry.getValue() instanceof Integer) {
                edit.putInt( entry.getKey(), (int)entry.getValue());
            }

            if (entry.getValue() instanceof Boolean) {
                edit.putBoolean( entry.getKey(), (boolean)entry.getValue());
            }

            if (entry.getValue() instanceof Float) {
                edit.putFloat( entry.getKey(), (float)entry.getValue());
            }

            if (entry.getValue() instanceof Long) {
                edit.putLong( entry.getKey(), (long)entry.getValue());
            }
        }

        edit.apply();
    }
    public static Map<String, Object> getSPValue( Map<String, Object> map) {
        throwInit();
        Map<String, Object>  objectMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getValue() instanceof String) {
                String string = sp.getString(entry.getKey(), (String) entry.getValue());
                objectMap.put(entry.getKey(),string);
            }

            if (entry.getValue() instanceof Integer) {
                int anInt = sp.getInt(entry.getKey(), (int) entry.getValue());
                objectMap.put(entry.getKey(),anInt);
            }

            if (entry.getValue() instanceof Boolean) {
                boolean aBoolean = sp.getBoolean(entry.getKey(), (boolean) entry.getValue());
                objectMap.put(entry.getKey(),aBoolean);
            }

            if (entry.getValue() instanceof Float) {
                float aFloat = sp.getFloat(entry.getKey(), (float) entry.getValue());
                objectMap.put(entry.getKey(),aFloat);
            }

            if (entry.getValue() instanceof Long) {
                long aLong = sp.getLong(entry.getKey(), (long) entry.getValue());
                objectMap.put(entry.getKey(),aLong);
            }
        }
        return objectMap;
    }

    /**
     * 获取字符串
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 字符串
     */
    public static String getString( String key, String defaultValue) {
        throwInit();

        return sp.getString(key, defaultValue);
    }

    /**
     * 插入整型
     * @param key key
     * @param value 值
     */
    public static void putInt( String key, int value) {
        throwInit();

        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    /**
     * 获取整型
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 整型
     */
    public static int getInt(String key, int defaultValue) {
        throwInit();

        return sp.getInt(key, defaultValue);
    }

    /**
     * 插入浮点型
     * @param key key
     * @param value 值
     */
    public static void putFloat( String key, float value) {
        throwInit();

        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    /**
     * 获取浮点型
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 浮点型
     */
    public static float getFloat( String key, float defaultValue) {
        throwInit();

        return sp.getFloat(key, defaultValue);
    }

    /**
     * 插入Long型
     * @param key key
     * @param value 值
     */
    public static void putLong(String key, long value) {
        throwInit();

        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.apply();
    }


    /**
     * 获取长整型
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 长整型
     */
    public static Long getLong( String key, long defaultValue) {
        throwInit();

        return sp.getLong(key, defaultValue);
    }

    /**
     * 插入boolean型
     * @param key key
     * @param value 值
     */
    public static void putBoolean( String key, boolean value) {
        throwInit();

        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * 获取布尔型
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 布尔型
     */
    public static boolean getBoolean( String key, boolean defaultValue) {
        throwInit();

        return sp.getBoolean(key, defaultValue);
    }

    /**
     *  清除所有 sp
     */
    public static void clearAllSP(){
        throwInit();

        SharedPreferences.Editor clear = sp.edit().clear();
        clear.apply();
    }

    /**
     * 清除 key 的值
     * @param key 要清除的 key
     */
    public static void clearKeySP(String key){
        throwInit();

        SharedPreferences.Editor clear = sp.edit().remove(key);
        clear.apply();
    }
}
