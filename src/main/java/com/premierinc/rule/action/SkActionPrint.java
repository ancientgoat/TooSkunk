package com.premierinc.rule.action;

import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
public class SkActionPrint extends SkAction {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(final String inMessage) {
		message = inMessage;
	}

	@Override
	public void run(SkRuleRunner inRunner) {
		// Is this a macro?
		String newMessage = (String) inRunner.getValue(this.message, this.message);
		// Does this have embedded macros in the format '${macro}'
		newMessage = inRunner.expandMacros(newMessage);
		System.out.println(newMessage);
	}
}
