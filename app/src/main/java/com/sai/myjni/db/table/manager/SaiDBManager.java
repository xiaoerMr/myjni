package com.sai.myjni.db.table.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SaiDBManager {

    private  Context mContext;
    private  String mDbName;//得到数据库的名字的字段
    private  DaoSession daoSession;
    private  DaoMaster daoMaster;

    public void init(Context context, String dbName){
        mContext = context.getApplicationContext();
        mDbName = dbName;
    }

    private static SaiDBManager manager;

    public static SaiDBManager getInstance(){
        if (manager == null) {
            synchronized (SaiDBManager.class){
                if (manager == null) {
                    manager = new SaiDBManager();
                }
            }
        }
        return manager;
    }

    /**
     * 正常的数据库
     * @return
     */
    private  DaoMaster getDaoMasterNormal(){
        if (null == daoMaster) {
            // 版本管理
            SaiDaoMaster helper = new SaiDaoMaster(mContext, mDbName);
            SQLiteDatabase database = helper.getWritableDatabase();
            daoMaster = new DaoMaster(database);
        }
        return daoMaster;
    }

    public  DaoSession getDaoSessionNormal(){
        if (null == daoSession ) {
            if (null == daoMaster) {
                daoMaster = getDaoMasterNormal();
            }
            daoSession = daoMaster.newSession();
        }
        return  daoSession;
    }

    private  DaoMaster getDaoMasterEncryption(){
        return null;
    }

    /**
     * 加密的数据库
     * @return
     */
    public  DaoSession getDaoSessionEncryption(){
        return null;
    }
}
