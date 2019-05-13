package com.sai.sailib.anan;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class VehicalInvacationHandler implements InvocationHandler {

    private final HashMap<String, Method> map = new HashMap<>();
    private  Object tig;

    public VehicalInvacationHandler(Object tig) {
this.tig = tig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        method = map.get(name);
        if (method != null) {
            method.invoke(tig,args);
        }
        return null;
    }

    public void add(String name, Method method){
        map.put(name,method);
    }
}
