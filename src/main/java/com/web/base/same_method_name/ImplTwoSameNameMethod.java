package com.web.base.same_method_name;

/**
 * 实现两个接口的同名方法
 */
public class ImplTwoSameNameMethod implements InterfaceOne, InterfaceTwo{

    @Override
    public void sayHello(String name) {
        System.out.println("hello "+name);
    }

    public static void main(String[] args) {
        InterfaceOne one = new ImplTwoSameNameMethod();
        one.sayHello("one");

        InterfaceTwo two = new ImplTwoSameNameMethod();
        two.sayHello("two");

    }
}
