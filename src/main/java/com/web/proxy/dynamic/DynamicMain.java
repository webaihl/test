package com.web.proxy.dynamic;

import com.web.proxy.jdk.InterFace;
import com.web.proxy.jdk.RealObj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class DynamicMain {

    public static void main(String[] args) {

        //1. 创建被代理的对象，UserService接口的实现类
       InterFace realObj = new RealObj();
        // 2. 获取对应的 ClassLoader
        ClassLoader classLoader = InterFace.class.getClassLoader();
        // 3. 获取所有接口的Class，这里的只实现了一个接口InterFace，
        Class<?>[] interfaceclass = new Class[]{InterFace.class};
        // 4. 创建一个将 传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler handler = new DynamicProxyHandler(realObj);
        /**
         *   5.根据上面提供的信息，创建代理对象 在这个过程中，
         *         a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
         *         b.然后根据相应的字节码转换成对应的class，
         *         c.然后调用newInstance()创建代理实例
          */
        InterFace proxy = (InterFace) Proxy.newProxyInstance(classLoader, interfaceclass, handler);
        // 调用代理的方法
        proxy.getNameById("444");
        consume(proxy);

        CGLibProxy cgLibProxy = new CGLibProxy();
        RealObj interFace = (RealObj) cgLibProxy.getProxy(RealObj.class);
        interFace.getNameById("100");
    }

    public static void consume(InterFace interFace) {
        interFace.getName();
        String res = interFace.getNameById("100");
        System.out.println("name = " + res);
    }

}
