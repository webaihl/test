package com.web.reflect;

public class ClassType {
	public static void main(String[] args) {
		Foo f1 = new Foo();
		// 通过class静态成员变量 获得Foo的 类类型
		Class<Foo> c1 = Foo.class;

		// 已知对象名获得 Foo的类类型
		Class<? extends Foo> c2 = f1.getClass();


		//通过Clas.forName
		Class c3 = null;
		try {
			//参数 ： 所要反射类的 包名.类名
			c3 = Class.forName("reflect.Foo");

			//通过类类型创建类的实例对象
			Foo f2 = (Foo)c3.newInstance();
			f2.print();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(c1==c2);
		System.out.println(c2==c3);
	}
}

class Foo{
	void print(){
		System.out.println("Foo");
	}
}