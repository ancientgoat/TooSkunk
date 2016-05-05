package com.premierinc.spel.common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 *
 */
public class ValueMapper {

    private Map<String, Object> valueMap = Maps.newHashMap();

    public <JAVATYPE> JAVATYPE get(String inKey) {
        return (JAVATYPE) valueMap.get(inKey);
    }

    //public <JAVATYPE> void put(String inKey, JAVATYPE inValue) {
    public void put(String inKey, Object inValue) {
        valueMap.put(inKey, inValue);
    }

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }
}
