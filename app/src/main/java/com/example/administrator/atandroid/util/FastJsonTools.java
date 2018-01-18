package com.example.administrator.atandroid.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastJsonTools {
	
	/**
	 * 将Json字符串反序列化为Bean对象
	 * @param jsonString : Json字符�?
	 * @param cls : Bean�?
	 * @return Bean对象
	 */
    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
	 * 将Json字符串反序列化为List<Bean>对象
	 * @param jsonString : Json字符�?
	 * @param cls : Bean�?
	 * @return List<Bean>对象
	 */
    public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
        List<T> list = null;
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 将Json字符串反序列化为Map<String, Object>对象
     * @param jsonString
     * @return
     */
    public static Map<String, Object> getMap(String jsonString) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = JSON.parseObject(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将Json字符串反序列化为List<Map<String, Object>>对象
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getMaps(String jsonString) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        try {
            maps = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maps;
    }
}