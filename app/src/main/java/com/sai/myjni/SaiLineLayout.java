package com.sai.myjni;

import com.sai.myjni.base.BaseActivity;
import com.sai.sailib.log.DLog;

import java.util.ArrayList;
import java.util.List;

public class SaiLineLayout  extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        Student student1 = new Student("学生1");
        Student student2 = new Student("学生2");
        Boss boss = new Boss();
        boss.Add(student1);
        boss.Add(student2);
        boss.update();
    }

    //被观察者 Subscribe
    abstract class  Observer {
        List<Subscribe> ob = new ArrayList<>();
        //通知观察者,要改变了
        public void update(){
            if (ob != null) {
                for (Subscribe subscribe : ob) {
                    subscribe.update();
                }
            }
        }

        //添加 观察者
        public void Add(Subscribe subscribe){
            ob.add(subscribe);
        }
    }
    //观察者
    interface Subscribe{
        void update();
    }
    // 老板  被观察者
    class Boss extends Observer{

    }
    //员工 观察者
    class Student implements Subscribe{
        private String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public void update() {
            DLog.e("老板说来新需求了, "+ name);
        }
    }



}
