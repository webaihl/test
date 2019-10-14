package com.web.annotation;

import java.lang.annotation.*;

/**
 * @author web
 * @title: TestAnnotation
 * @projectName javacode
 * @description: TODO
 * @date 19-10-14下午7:40
 */
@Target(value = ElementType.FIELD) //作用的元素类型
@Inherited //是否可实现。使用了@Inherited修饰的注解类型被用于一个class时该class的子类也有了该注解。
@Retention(value = RetentionPolicy.RUNTIME) //
//@Deprecated
public @interface TestAnnotation {
    String value() default "web";
}
