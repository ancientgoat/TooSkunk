package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.commands.enums.SkConditionType;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
public class SkThen extends SkCondition<Object> {

	@JsonProperty("action")
	private SkAction action = null;

	@JsonProperty("rule")
	private SkRuleBase rule = null;

	public SkAction getAction() {
		return action;
	}

	public void setAction(final SkAction inAction) {
		action = inAction;
	}

	@Override
	public Object execute(SkRuleRunner inRunner) {
		if(null != this.action) {
			action.execute(inRunner);
		}
		if (null != this.rule) {
			this.rule.existingRun(inRunner);
		}
		return null;
	}

	@Override
	public SkConditionType getConditionType() {
		return SkConditionType.THEN;
	}
}
