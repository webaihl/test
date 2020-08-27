package com.web.base;

/***
 * final--类上不能被继承
 *        在方法上不能重写,但可以被重载
 *        在变量上不能改变值或者引用
 */
public /*final*/ class FinalScope {

    public final int filed_A = 100;
    public final String filed_B = "";

    public final void method_A(){
        System.out.println("empty");
    }

    public final void method_A(int a){
        //filed_A = 10;
        //filed_B = new String("");
    }
}

class Class_A extends FinalScope {
   /* public final void method_A(int a){
        System.out.println(a);
    }*/
}
