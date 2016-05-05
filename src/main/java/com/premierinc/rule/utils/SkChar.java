package com.premierinc.rule.utils;

/**
 *
 */
public class SkChar {

	private SkChar() {
	}

	public static boolean isAlpha(char c){
		return (c >= 'a' && c <= 'z') ||(c >= 'A' && c <= 'Z');
	}

	public static boolean isNumber(char c){
		return c >= '0' && c <= '9';
	}

	public static boolean isMacro(char c){
		return isAlpha(c) || isNumber(c) || (c == '.');
	}

	public static boolean isSpace(char c){
		return c == 32;
	}
}
