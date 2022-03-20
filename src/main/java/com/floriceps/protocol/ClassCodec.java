package com.floriceps.protocol;

import com.google.gson.*;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Java 类型与 JSON 类型转换。
 */
public class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {
    @Override
    public Class<?> deserialize(JsonElement jsonElement, Type type,
                                JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        // json -> Class
        try {
            String str = jsonElement.getAsString();
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Class<?> aClass, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        // Class -> json
        return new JsonPrimitive(aClass.getName());
    }
}
