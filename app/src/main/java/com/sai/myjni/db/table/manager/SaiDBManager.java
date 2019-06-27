package com.sai.myjni.db.table.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

public class SaiDBManager {

    private static final String mDbPassword = "sai_db_pass_word";//加密数据库的 密码
    private Context mContext;
    private String mDbName;//得到数据库的名字的字段
    private DaoSession daoSession, daoSessionEncryption;
    private DaoMaster daoMaster, daoMasterEncryption;

    public void init(Context context, String dbName) {
        mContext = context.getApplicationContext();
        mDbName = dbName;
    }

    private static SaiDBManager manager;

    public static SaiDBManager getInstance() {
        if (manager == null) {
            synchronized (SaiDBManager.class) {
                if (manager == null) {
                    manager = new SaiDBManager();
                }
            }
        }
        return manager;
    }

    /**
     * 正常的数据库
     *
     * @return
     */
    private DaoMaster getDaoMasterNormal() {
        if (null == daoMaster) {
            // 版本管理
            SaiDaoMaster helper = new SaiDaoMaster(mContext, mDbName);
            SQLiteDatabase database = helper.getWritableDatabase();
            daoMaster = new DaoMaster(database);
        }
        return daoMaster;
    }

    public DaoSession getDaoSessionNormal() {
        if (null == daoSession) {
            if (null == daoMaster) {
                daoMaster = getDaoMasterNormal();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    private DaoMaster getDaoMasterEncryption() {
        if (null == daoMasterEncryption) {
            // 版本管理
            SaiDaoMaster helper = new SaiDaoMaster(mContext, mDbName);
            Database readableDb = helper.getEncryptedReadableDb(mDbPassword);
            daoMasterEncryption = new DaoMaster(readableDb);
        }
        return daoMasterEncryption;
    }

    /**
     * 加密的数据库
     *
     * @return
     */
    public DaoSession getDaoSessionEncryption() {

        if (null == daoMasterEncryption) {
            daoMasterEncryption = getDaoMasterEncryption();

            if (null == daoSessionEncryption) {
                daoSessionEncryption = daoMasterEncryption.newSession();
            }
        }
        return daoSessionEncryption;
    }
}
