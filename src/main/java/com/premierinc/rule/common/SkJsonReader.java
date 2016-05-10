package com.premierinc.rule.common;

import java.util.jar.JarEntry;
import java.util.stream.Stream;

/**
 *
 */
public interface SkJsonReader<C> {
	String entryToString(C inentry);
	Integer size();
	Stream<C> stream();
}
