package com.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.web.comm.User;


import java.io.IOException;
import java.util.Objects;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserDeserializer.java
 * @Description 自定义反序列化
 * @createTime 2020年07月17日 10:06:00
 */
public class UserDeserializer extends StdDeserializer<User> {

    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        User user = new User();
        while (!jsonParser.isClosed()){
            JsonToken jsonToken = jsonParser.nextToken();
            if (Objects.equals(JsonToken.FIELD_NAME, jsonToken)){
                String fieldName = jsonParser.getCurrentName();
                jsonToken = jsonParser.nextToken();
                if (Objects.equals("name", fieldName)){
                    user.setName(jsonParser.getValueAsString()+" 测试");
                }else if(Objects.equals("age",fieldName)){
                    user.setAge(jsonParser.getValueAsInt());
                }
            }
        }
        return user;
    }
}
