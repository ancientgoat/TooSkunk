package com.premierinc.rule.run;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
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
	private static Map<String, Object> globalMap = Collections.synchronizedMap(Maps.newHashMap());

	/**
	 *
	 */
	private SkGlobalContext() {
	}

	/**
	 *
	 */
	public static Object getValue(@NotNull String inKey) {
		String upperKey = inKey.toUpperCase();
		Object o = globalMap.get(upperKey);
		if (null == o) {
			throw new IllegalArgumentException(String.format("No global value with name/key '%s'.", upperKey));
		}
		return o;
	}

	/**
	 *
	 */
	public static Object getValue(@NotNull String inKey, Object inDefautValue) {
		String upperKey = inKey.toUpperCase();
		Object o = globalMap.get(upperKey);
		if (null == o) {
			return inDefautValue;
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
	public static void setValue(String inKey, Object inValue) {
		globalMap.put(inKey, inValue);
	}

	/**
	 *
	 */
	public static Map<String, Object> getGlobalMap() {
		return globalMap;
	}

	/**
	 *
	 */
	public static Boolean containsMacroKey(String inKey) {
		return globalMap.containsKey(inKey);
	}
}
