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
		//String newMessage = inRunner.expandMacros(this.message);
		String newMessage = (String) inRunner.getValue(this.message, this.message);
		System.out.println(newMessage);
	}
}
