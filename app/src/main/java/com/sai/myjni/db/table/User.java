package com.sai.myjni.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true) //自增
    @Unique //唯一约束
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String age;
    @NotNull
    private String sex;
    private String clazz;




    @Generated(hash = 57432095)
    public User(Long id, @NotNull String name, @NotNull String age,
            @NotNull String sex, String clazz) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.clazz = clazz;
    }




    @Generated(hash = 586692638)
    public User() {
    }




    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", clazz='" + clazz + '\'' +
                '}';
    }




    public Long getId() {
        return this.id;
    }




    public void setId(Long id) {
        this.id = id;
    }




    public String getName() {
        return this.name;
    }




    public void setName(String name) {
        this.name = name;
    }




    public String getAge() {
        return this.age;
    }




    public void setAge(String age) {
        this.age = age;
    }




    public String getSex() {
        return this.sex;
    }




    public void setSex(String sex) {
        this.sex = sex;
    }




    public String getClazz() {
        return this.clazz;
    }




    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
