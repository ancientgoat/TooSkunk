package com.premierinc.rule.commands;

/**
 *
 */

import com.premierinc.rule.commands.enums.SkConditionType;

/**
 * flyweight pattern
 */
public class SkConditionStateMachine {

	// Normal class
	private SkConditionType lastState = SkConditionType.UNKNOWN;

	/**
	 *
	 * @param inCurrentState
	 * @return
	 */
	public boolean checkState(SkConditionType inCurrentState) {

		String error = null;

		switch (inCurrentState) {
		case IF:
			if (lastState == SkConditionType.IF) {
				error = "Can not have two IF conditions is a row.";
			}
			break;

		case THEN:
			if (lastState != SkConditionType.IF) {
				error = String.format("THEN condition must follow an IF, not a '%s'.", lastState);
			}
			break;

		case ELSE:
			if (lastState != SkConditionType.THEN) {
				error = String.format("ELSE condition must follow a THEN, not a '%s'.", lastState);
			}
			break;

		default:
			throw new IllegalArgumentException(
					String.format("SkConditionType of '%s' is not implemented.", inCurrentState));
		}

		if (null != error) {
			throw new IllegalArgumentException(error);
		}
		lastState = inCurrentState;
		return true;
	}
}

