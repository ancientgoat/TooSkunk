package com.premierinc.rule.action.enums;

import static java.util.Locale.UK;

/**
 * This may be not needed, as one can deduce the Action Type from the class name.
 * 	Or we could assign various types of interfaces depending on the
 * 	needed pre and/or post Action 'execute()' needs.
 */
public enum SkActionType {
	PRINT, LOG, REFERENCE;
}
