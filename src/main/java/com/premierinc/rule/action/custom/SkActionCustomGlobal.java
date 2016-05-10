package com.premierinc.rule.action.custom;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 * Store custom global SkActions along with their 'unique' names.
 */
public class SkActionCustomGlobal {

	private static Map<String, SkActionCustom> customActionMap = Maps.newHashMap();

	/**
	 * Singleton
	 */
	private SkActionCustomGlobal() {
	}

	/**
	 * Add a custom action with it's unique name.
	 */
	public static void add(@NotNull String inName, SkActionCustom inAction) {
		if (customActionMap.containsKey(inName)) {
			throw new IllegalArgumentException(String.format("Custom Action with name '%s' already exists.", inName));
		}
		customActionMap.put(inName, inAction);
	}

	/**
	 * Get a custom action.
	 */
	public static SkActionCustom get(@NotNull String inName) {
		if (!customActionMap.containsKey(inName)) {
			throw new IllegalArgumentException(String.format("Custom Action with name '%s' does NOT exist.", inName));
		}
		SkActionCustom actionCustom = customActionMap.get(inName);
		return actionCustom.newInstance();
	}

	/**
	 * Wipe out the custom global actions.
	 */
	public static void clear() {
		customActionMap.clear();
	}
}
