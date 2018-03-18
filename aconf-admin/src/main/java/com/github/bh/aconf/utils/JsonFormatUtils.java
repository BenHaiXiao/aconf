package com.github.bh.aconf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Json格式化工具类
 * Created by Shildon on 2017/3/24.
 */
public class JsonFormatUtils {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    /**
     * 将符合json格式的字符串格式化
     * @param str 格式化前的字符串
     * @return 格式化后的字符串
     */
    public static String format(String str) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(str);
        return gson.toJson(jsonElement);

    }

}
