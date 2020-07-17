package com.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.web.comm.User;

import java.io.IOException;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserSerializer.java
 * @Description
 * @createTime 2020年07月17日 10:49:00
 */
public class UserSerializer  extends StdSerializer<User> {

    protected UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("nicename",user.getName());
        jsonGenerator.writeNumberField("age",user.getAge()+100);
        jsonGenerator.writeEndObject();
    }
}
