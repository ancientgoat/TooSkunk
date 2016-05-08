package com.premierinc.rule.run;

import com.google.common.collect.Maps;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SkGlobalContext {

	/**
	 *
	 */
	private Logger log = LoggerFactory.getLogger(SkGlobalContext.class);

	/**
	 *
	 */
	private static Map<String, Object> globalMap = Maps.newHashMap();

	/**
	 *
	 */
	private SkGlobalContext() {
	}

	/**
	 *
	 */
	public static Object getValue(String inKey) {
		Object o = globalMap.get(inKey);
		if (null == o) {
			throw new IllegalArgumentException(String.format("No global value with name/key '%s'.", inKey));
		}
		return o;
	}

	/**
	 *
	 */
	public static void setValues(Map<String, Object> inMap) {
		globalMap.putAll(inMap);
	}

	/**
	 *
	 */
	public static void addValue(String inKey, Object inValue) {
		globalMap.put(inKey, inValue);
	}
}
