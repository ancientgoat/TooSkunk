package com.premierinc.rule.action;

import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
public class SkActionAki extends SkAction {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(final String inMessage) {
		message = inMessage;
	}

	@Override
	public void run(final SkRuleRunner inRunner) {
		String message = String.format("AKI Alert : %s", this.message);
		inRunner.addAlertCrumb(message);
	}
}
