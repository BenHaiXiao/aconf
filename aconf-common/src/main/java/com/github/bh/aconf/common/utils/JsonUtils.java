package com.github.bh.aconf.common.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author xiaobenhai
 * Date: 2016/5/12
 * Time: 15:56
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Gson GSON = new Gson();

    public static JavaType getCollectionType(Class collectionClz, Class elementClz) {
        return mapper.getTypeFactory().constructParametricType(collectionClz, elementClz);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        return GSON.fromJson(json, clz);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
