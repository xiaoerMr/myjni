package com.sai.myjni.db.table.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

public class SaiDaoMaster extends DaoMaster.OpenHelper {

    public SaiDaoMaster(Context context, String name) {
        super(context, name);
    }

    public SaiDaoMaster(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        SaiDBMigrationHelper.ReCreateAllTableListener listener = new SaiDBMigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        };

        SaiDBMigrationHelper.migrate(db, listener ,UserDao.class);

        Log.e("SaiDaoMaster", "onUpgrade: " + oldVersion + " newVersion = " + newVersion);

    }
}
