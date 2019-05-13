package com.sai.sailib.anan;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Bus(listenerSet = "setOnClickListener",listenerType = View.OnClickListener.class,listenerCallback = "onClick")
public @interface OnClick {
    int [] value();//注解参数

}
