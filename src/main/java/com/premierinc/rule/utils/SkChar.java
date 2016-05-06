package com.premierinc.rule.utils;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SkChar {

	public static final Set<Character> ALLOWED_MACRO_CHARS = new HashSet<Character>() {{
		add('.');
		add('_');
	}};
	public static final Set<Character> BAD_MACRO_CHARS = new HashSet<Character>() {{
		add('\'');
		add(')');
		add('(');
	}};

	private SkChar() {
	}

	public static boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isMacro(char c) {
		return isAlpha(c) || isNumber(c) || ALLOWED_MACRO_CHARS.contains(c);
	}

	public static boolean isSpace(char c) {
		return c == 32;
	}

	public static boolean isBadMacroChar(final char c) {
		return BAD_MACRO_CHARS.contains(c);
	}
}
