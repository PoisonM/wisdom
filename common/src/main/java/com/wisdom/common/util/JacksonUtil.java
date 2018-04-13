package com.wisdom.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * <b>类说明：</b>Jackson工具类
 * <p>
 * <p>
 * <b>详细描述：</b>
 *
 * @author 赵得良
 * @since 2014-5-5
 */
public class JacksonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {

        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private static final JsonFactory JSONFACTORY = new JsonFactory();

    /**
     * 转换Java Bean 为 json
     */
    public static String beanToJson(Object o) throws JsonParseException {
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGenerator = null;

        try {
            jsonGenerator = JSONFACTORY.createJsonGenerator(sw);
            MAPPER.writeValue(jsonGenerator, o);
            return sw.toString();

        } catch (Exception e) {
            throw new RuntimeException("转换Java Bean 为 json错误", e);

        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return obj.toString();
        }
        String json = null;
        try {
            json = MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("转换Java Bean 为 json错误", e);
        }
        return json;
    }

    /**
     * json 转 javabean
     *
     * @param json
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object jsonToBean(String json, Class clazz) throws JsonParseException {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("json 转 javabean错误", e);
        }
    }

    /**
     * 转换Java Bean 为 HashMap
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> beanToMap(Object o) throws JsonParseException {
        try {
            return MAPPER.readValue(beanToJson(o), HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException("转换Java Bean 为 HashMap错误", e);
        }
    }

    /**
     * 转换Json String 为 HashMap
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json, boolean collToString) throws JsonParseException {
        Map<String, Object> map = null;
        try {
            map = MAPPER.readValue(json, HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException("转换Java Bean 为 HashMap错误", e);
        }
        if (collToString) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Collection || entry.getValue() instanceof Map) {
                    entry.setValue(beanToJson(entry.getValue()));
                }
            }
        }
        return map;

    }

    /**
     * List 转换成json
     *
     * @param list
     * @return
     */
    public static String listToJson(List<Map<String, String>> list) throws JsonParseException {
        JsonGenerator jsonGenerator = null;
        StringWriter sw = new StringWriter();
        try {
            jsonGenerator = JSONFACTORY.createJsonGenerator(sw);
            new ObjectMapper().writeValue(jsonGenerator, list);
            jsonGenerator.flush();
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("List 转换成json错误", e);
        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.flush();
                    jsonGenerator.close();
                } catch (Exception e) {
                    throw new RuntimeException("List 转换成json错误", e);
                }
            }
        }
    }

    /**
     * json 转List
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, String>> jsonToList(String json) throws JsonParseException {
        try {
            if (json != null && !"".equals(json.trim())) {
                JsonParser jsonParse = JSONFACTORY.createJsonParser(new StringReader(json));

                return (List<Map<String, String>>) new ObjectMapper().readValue(jsonParse, ArrayList.class);
            } else {
                throw new RuntimeException("json 转List错误");
            }
        } catch (Exception e) {
            throw new RuntimeException("json 转List错误", e);
        }
    }


    /**
     * json 转List
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonToObjList(String json) throws JsonParseException {
        try {
            if (json != null && !"".equals(json.trim())) {
                JsonParser jsonParse = JSONFACTORY.createJsonParser(new StringReader(json));

                return (List<Map<String, Object>>) new ObjectMapper().readValue(jsonParse, ArrayList.class);
            } else {
                throw new RuntimeException("json 转List错误");
            }
        } catch (Exception e) {
            throw new RuntimeException("json 转List错误", e);
        }
    }
}
