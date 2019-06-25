package com.sai.myjni.db;


import android.widget.TextView;

import com.sai.myjni.MyApplication;
import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.db.table.User;
import com.sai.myjni.db.table.manager.DaoSession;
import com.sai.myjni.db.table.manager.UserDao;

import butterknife.BindView;

public class DBActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_db;
    }

    @Override
    protected void initView() {

        //获取数据库对象
        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();

        //获取表对象
        UserDao userDao = daoSession.getUserDao();

        //新增
        userDao.save(new User());

        //删除
        userDao.deleteByKey(5L);

        //更新
        userDao.update(new User());

        //查看
        userDao.loadAll();
    }


}
