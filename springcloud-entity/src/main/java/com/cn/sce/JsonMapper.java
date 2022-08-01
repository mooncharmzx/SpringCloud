package com.cn.sce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonMapper {
    private static final String ERROR_NOT_PARSE_JSON = "Cannot parse JSON";
    private Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private static final TypeReference<?> MAP_TYPE = new JsonMapper.MapTypeReference();
    private static final TypeReference<?> MAP_STRING_TYPE = new JsonMapper.MapStringTypeReference();
    private static final TypeReference<?> LIST_TYPE = new JsonMapper.ListTypeReference();
    private final ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String toString(T obj) {
        return this.toString(obj, true);
    }

    public <T> String toString(T obj, boolean pretty) {
        try {
            return pretty ? this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : this.objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException var4) {
            this.logger.error("序列化异常", var4);
            return null;
        }
    }

    public <T> T parse(String jsonStr, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(jsonStr, clazz);
        } catch (IOException var4) {
            this.logger.error("反序列化异常", var4);
            return null;
        }
    }

    public <T> T parse(String jsonStr, JavaType javaType) {
        try {
            return this.objectMapper.readValue(jsonStr, javaType);
        } catch (IOException var4) {
            this.logger.error("parse json string error:" + jsonStr, var4);
            return null;
        }
    }

    public <T> T parse(String jsonStr, TypeReference valueTypeRef) {
        try {
            return this.objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (IOException var4) {
            this.logger.error("反序列化异常", var4);
            return null;
        }
    }

    public JavaType getJavaType(TypeReference<?> typeRef) {
        return this.objectMapper.getTypeFactory().constructType(typeRef);
    }

    public JavaType getCollectionJavaType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return this.objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public JavaType getMapJavaType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return this.objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    public JavaType getParametricJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
        return this.objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public JavaType getParametricJavaType(Class<?> rawType, JavaType... parameterTypes) {
        return this.objectMapper.getTypeFactory().constructParametricType(rawType, parameterTypes);
    }

    public Map<String, Object> parseMap(String json) {
        try {
            return (Map)this.objectMapper.readValue(json, MAP_TYPE);
        } catch (Exception var3) {
            this.logger.error("Cannot parse JSON", var3);
            return null;
        }
    }

    public Map<String, String> parseMapString(String json) {
        try {
            return (Map)this.objectMapper.readValue(json, MAP_STRING_TYPE);
        } catch (Exception var3) {
            this.logger.error("Cannot parse JSON", var3);
            return null;
        }
    }

    public List<Object> parseList(String json) {
        try {
            return (List)this.objectMapper.readValue(json, LIST_TYPE);
        } catch (Exception var3) {
            this.logger.error("Cannot parse JSON", var3);
            return Collections.EMPTY_LIST;
        }
    }

    private static class ListTypeReference extends TypeReference<List<Object>> {
        private ListTypeReference() {
        }
    }

    private static class MapStringTypeReference extends TypeReference<Map<String, String>> {
        private MapStringTypeReference() {
        }
    }

    private static class MapTypeReference extends TypeReference<Map<String, Object>> {
        private MapTypeReference() {
        }
    }
}
