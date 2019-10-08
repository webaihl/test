package com.web.proxy.dynamic;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CGLibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        //设置目标类为父类，cglib是通过继承来实现的
        enhancer.setSuperclass(clazz);
        //设置回调。需实现MethodInterceptor，可用于过滤拦截
        // 设置回调接口对象 注意，之所以在setCallback()方法中可以写上this，
        // 是因为MethodInterceptor接口继承自Callback，是其子接口
        enhancer.setCallback(this);
        enhancer.setCallback(new RealObjInterceptor());
        return enhancer.create();
    }

    /**
     * @param realObj     表示要进行增强的对象
     * @param method      表示拦截的方法
     * @param args        数组表示参数列表，基本数据类型需要传入其包装类型，如int-->Integer、long-Long、double-->Double
     * @param methodProxy 表示对方法的代理，invokeSuper方法表示对被代理对象方法的调用
     * @return 执行结果
     * @throws Throwable
     */
    @Override
    public Object intercept(Object realObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("dynamic proxy name: " + realObj.getClass());
        System.out.println("method: " + method.getName());
        System.out.println("args: " + Arrays.toString(args));
        // 注意这里是调用 invokeSuper 而不是 invoke，否则死循环，
        // methodProxy.invokeSuper执行的是原始类的方法，method.invoke执行的是子类的方法
        Object obj = methodProxy.invokeSuper(realObj, args);
        return obj;
    }
}