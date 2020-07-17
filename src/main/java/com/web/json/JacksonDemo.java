package com.web.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.web.comm.User;

import java.io.IOException;


/**
 * @author admin
 * @version 1.0.0
 * @ClassName JacksonDemo.java
 * @Description 参考 https://juejin.im/post/5ec940ace51d45784c52d607
 * @createTime 2020年07月17日 10:04:00
 */
public class JacksonDemo {

    public static void deTest(){

        String uerJson = "{ \"name\" : \"web\", \"age\" : 6 }";
        SimpleModule simpleModule = new SimpleModule("UserDeserializer", new Version(3,1,8,null,null,null));
        simpleModule.addDeserializer(User.class, new UserDeserializer(User.class));
        ObjectMapper objectMapper = new ObjectMapper();
        //注册到objectMapper
        objectMapper.registerModule(simpleModule);
        try {
            User user = objectMapper.readValue(uerJson, User.class);
            System.out.println(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enTest(){

        SimpleModule module = new SimpleModule("UserSerializer", new Version(3,1,8,null,null,null));
        module.addSerializer(User.class, new UserSerializer(User.class));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        try {
            String userStr = objectMapper.writeValueAsString(new User("web", 25));
            System.out.println(userStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        deTest();
//        enTest();
    }
}
