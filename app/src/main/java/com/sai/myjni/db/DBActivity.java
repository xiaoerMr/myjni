package com.sai.myjni.db;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.db.table.User;
import com.sai.myjni.db.table.manager.DaoSession;
import com.sai.myjni.db.table.manager.SaiDBManager;
import com.sai.myjni.db.table.manager.UserDao;
import com.sai.sailib.toutiao.VerificationUtils;


import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class DBActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView text;
    private UserDao userDao;
    private User user;
    private Random random;
    private boolean Added = false;
    private DaoSession daoSession;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_db;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        //获取数据库对象
        daoSession =  SaiDBManager.getInstance().getDaoSessionEncryption();
        //获取表对象
        userDao = daoSession.getUserDao();
        random = new Random();
        user = getUser();
    }

    private User getUser() {
        String age = ""+ random.nextInt(100);
        String sex = random.nextInt(10) % 2 == 0 ? "男" : "女";
        return new User(null, "张三", age, sex,"3年级");
    }

    @OnClick({R.id.add, R.id.find, R.id.update, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:

                long insert;
                if (!Added) {
                    Added =true;
                //新增
//                userDao.save(user);
                    insert = userDao.insert(user);
//                daoSession.insert(user);
//                daoSession.insertOrReplace(user);//数据存在则替换，数据不存在则插入
                }else {
                    User user = getUser();
                    insert = userDao.insert(user);
                }


                if (insert >0) {
                    text.setText("新增成功,=>"+insert);
                }else {
                    text.setText("新增失败");
                }

                break;
            case R.id.find:
                //查看
                List<User> users = userDao.loadAll();
                if (VerificationUtils.isEmptyList(users)) {
                    text.setText("表为空");
                }else {
                    text.setText(users.toString());
                }

                break;
            case R.id.update:
                //更新  当删除id=1的数据后再更新id=1则没有效果
//                User load = userDao.load(1L);
//                if (load == null || load.getId() < 0) {
//                    user.setId(1L);
//                    userDao.insert(user);
//                    text.setText("没有改条数据,已经添加该条数据");
//                }else {
//                    User user1 = getUser();
//                    user1.setId(1L);
//                    userDao.update(user1);
//                    text.setText("更新完成");
//                }
                User user1 = getUser();
                user1.setId(1L);
//                userDao.update(user1);
                long l = daoSession.insertOrReplace(user1);//数据存在则替换，数据不存在则插入
                text.setText("更新完成="+l);
                break;
            case R.id.delete:
                //删除
                userDao.deleteByKey(1L);
                text.setText("删除完成");
                break;
        }
    }
}
