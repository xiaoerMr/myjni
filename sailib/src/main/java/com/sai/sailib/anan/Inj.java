package com.sai.sailib.anan;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Inj {

  public static void  Inject(Activity activity){

      injectLayout(activity);
      injectView(activity);
      injectMethod(activity);
    }

    private static void injectMethod(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        for (Method method : clazz.getMethods()) {
            //一个方法有多个注解
            Annotation[] onClick = clazz.getAnnotations();
            for (Annotation annotation : onClick) {
                //获取元注解类型
                Class<? extends Annotation> type = annotation.annotationType();
                if (type != null) {
                    Bus bus = type.getAnnotation(Bus.class);

                    if (null != bus) {
                        String listenerSet = bus.listenerSet();
                        Class<?> listenerType = bus.listenerType();
                        String listenerCallback = bus.listenerCallback();

                        try {
                            Method value = type.getDeclaredMethod("value");
                            int [] viewIds = (int[]) value.invoke(annotation);

                            //面向切面  不知道写的对不对????????????
                            VehicalInvacationHandler handler = new VehicalInvacationHandler(activity);
                            handler.add(listenerCallback,method);
                            Object instance = Proxy.newProxyInstance(
                                    listenerType.getClass().getClassLoader(),
                                    listenerType.getInterfaces(),
                                    handler
                                    );

                            for (int id : viewIds) {

                                //获取id 对应 view 对象
                                View view = activity.findViewById(id);
                                //给对象设置监听
                                Method method1 = view.getClass().getMethod(listenerSet, listenerType);
                                method1.invoke(view,instance);
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }


    }


    private static void injectView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        for (Field field : clazz.getDeclaredFields()) {

            InjectView annotation = field.getAnnotation(InjectView.class);
            if (null != annotation) {
                // view id
                int value = annotation.value();
                try {
                    // 执行findViewById
                    Method findViewById = clazz.getMethod("findViewById", int.class);
                    Object viewObject = findViewById.invoke(activity,value);
                    //赋值
                    //设置访问权限 保障 private 属性可以赋值
                    field.setAccessible(true);
                    field.set(activity,viewObject);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();

        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (null != contentView) {

            // view id
            int value = contentView.value();

//            activity.setContentView(value);

            try {
                // setContentView
                Method setContentView = clazz.getMethod("setContentView", int.class);
                setContentView.invoke(activity,value);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
