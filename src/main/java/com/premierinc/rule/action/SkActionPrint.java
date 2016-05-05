package com.premierinc.rule.action;

import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkRuleContext;
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
	public SkActionType getActionType() {
		return SkActionType.PRINT;
	}

	@Override
	public void execute(SkRuleRunner inRunner) {
		System.out.println(this.message);
	}
}
