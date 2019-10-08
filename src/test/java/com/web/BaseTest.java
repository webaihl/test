package com.web;


import com.web.base.User;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;

/**
 * @author web
 * @title: BaseTest
 * @projectName javacode
 * @description: TODO
 * @date 19-9-12下午5:16
 */


public class BaseTest {

    @Test
    public void strAccessTest() throws NoSuchFieldException, IllegalAccessException {
        String a = "123";
        //这里的 a 和 b  都是同一个对象，指向同一个字符串常量池对象。
        String b = "123" ;
        String c = new String("123") ;

        System.out.println("a=b:" + (a == b));
        System.out.println("a=c:" + (a == c));

        System.out.println("a=" + a);

        a = "456";
        System.out.println("a=" + a);


        //用反射的方式改变字符串的值
        Field value = a.getClass().getDeclaredField("value");
        //改变 value 的访问属性
        value.setAccessible(true) ;

        char[] values = (char[]) value.get(a);
        values[0] = '9' ;

        System.out.println(a);
    }

    @Test
    public void testSer(){
        System.out.println(this.getClass().getResource("/").getPath());
        User user = new User("web",24);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./User")))  {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./User"))) {
            User user1 = (User) objectInputStream.readObject();
            System.out.println(user1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
