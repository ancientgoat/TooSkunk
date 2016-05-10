package com.premierinc.rule.common;

import javax.validation.constraints.NotNull;

/**
 *
 */
public interface SkJsonFilenameFilter {

	static final String JSON_FILE_EXTENSION = ".json";

	default boolean accept(@NotNull String inAbsolutePath) {
		return inAbsolutePath.toLowerCase().endsWith(JSON_FILE_EXTENSION);
	}
}
