package com.web.annotation;

import java.lang.reflect.Field;

/**
 * @author web
 * @title: TestAnnotationParser
 * @projectName javacode
 * @description: TODO
 * @date 19-10-14下午7:44
 */
public class TestAnnotationParser{

    @TestAnnotation
    private String name ;

    public static void main(String[] args) {
        //获取所有的属性
        Field[] fields = TestAnnotationParser.class.getDeclaredFields();
        for (Field field: fields){
            //使用了@TestAnnotation的字段
            if (field.isAnnotationPresent(TestAnnotation.class)){
                //通过getAnnotation可以获取注解对象
                TestAnnotation testAnnotation = field.getAnnotation(TestAnnotation.class);
                String value = testAnnotation.value();
                System.out.println(value);
            }
        }

    }

}
