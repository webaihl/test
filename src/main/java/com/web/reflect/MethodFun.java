package com.web.reflect;

import java.lang.reflect.Method;

public class MethodFun {
	public static void main(String[] args) {
		A a = new A();
		//获得方法所在类的 类类型
		Class<? extends A> c = a.getClass();
		try {
			//获取指定的方法
			Method m = c.getMethod("print");
			//获取返回值 没有则为Null
			Object returnValue = m.invoke(a);

			// 整数
			Method m1 = c.getMethod("print", int.class, int.class);
			Object o2 = m1.invoke(a, 1, 2);

			//字符串
			Method m2 = c.getMethod("print", String.class, String.class);
			Object o3 = m2.invoke(a, "Hello", "web");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
class A{
	public void print() {
		System.out.println("Hello Word");
	}

	public void print(int a, int b) {
		System.out.println(a+b);
	}

	public void print(String a, String b) {
		System.out.println(a.toLowerCase()+" , " + b.toUpperCase());
	}
}