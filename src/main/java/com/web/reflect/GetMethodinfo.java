package com.web.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;

public class GetMethodinfo {
	public static void printMethodInfo(Object obj) {
		//得到传递类的 类类型
		Class<Object> c = (Class<Object>) obj.getClass();

		//获得类的名称
		System.out.println("类的名称: "+c.getName());



		/***
		 * getMethods() 获得所有的方法，以及继承的public方法
		 * getDeclaredMethods() 获得自身定义的方法，不问访问修饰符
		 */

		Method[] methods = c.getMethods();
		for (Method method : methods) {
			//获得方法的返回值类型,返回的是 返回类型的 类类型
			Class retureType = method.getReturnType();
			System.out.print(retureType.getSimpleName()+":");
			//得到方法的名称
			System.out.print(method.getName()+"(");

			//得到方法的参数类型, 参数列表的 类类型
			TypeVariable<Method>[] paramType = method.getTypeParameters();
			for (TypeVariable<Method> typeVariable : paramType) {
				System.out.print(typeVariable.getName()+",");
			}
			System.out.println(")");
		}
	}

	public static void printFieldMsg(Object obj) {

		Class c = obj.getClass();
		/***
		 * 获取成员变量
		 * getFields() 获得public的成员变量
		 * getDeclaredFields() 获得自身定义的所有成员变量
		 */

		Field[] fields = c.getDeclaredFields();//c.getFields();
		for (Field field : fields) {
			//获得变量类型的 类类型
			Class type = field.getType();
			String typeName = type.getName();
			//获取成员变量的名字
			String fieldName = field.getName();
			System.out.println(typeName +" "+fieldName);
		}

	}


	public static void printConMsg(Object obj) {
		Class c = obj.getClass();
		/***
		 * 获得构造函数的信息
		 * getDeclaredConstructors()获得所有的构造函数
		 * 构造函数没有返回值类型
		 */
		Constructor[] cs = c.getDeclaredConstructors();
		for (Constructor constructor : cs) {
			System.out.print(constructor.getName()+ "(");
			//获得构造函数的参数----->类类型
			Parameter[] params = constructor.getParameters();
			for (Parameter param : params) {

				System.out.print(param.getName()+ ",");
			}
			System.out.println(")");
		}
	}
}
