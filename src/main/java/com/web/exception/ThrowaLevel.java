package com.web.exception;

/**
 * @author web
 * @title: ThrowaLevel
 * @projectName javacode
 * @description: TODO
 * @date 19-9-18下午2:13
 */
public class ThrowaLevel {

    public static void main(String[] args) {
        try {
            System.out.println(test());
        }catch (Exception e){
            System.out.println("主异常方法位置。。。。");

        }finally {
            System.out.println("主方法的finally。。。。");
        }
    }

    public static String test(){

        try {

            int a = 0/0;
            return "被调方法中的返回";

        }catch (Exception e){
            System.out.println("被调异常方法位置。。。。");
            return "被调方法异常中的返回";
        }finally {
            System.out.println("被调方法的finally。。。。");
            return "finally方法中的返回";
        }
    }
}
