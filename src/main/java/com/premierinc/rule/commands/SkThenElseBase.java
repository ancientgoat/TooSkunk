package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.commands.enums.SkConditionType;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;

/**
 *
 */
public class SkThenElseBase<C extends Object> extends SkCondition<C> {

	@JsonProperty("action")
	private SkAction action;

	@JsonProperty("actions")
	private SkActions actions;

	@JsonProperty("rule")
	private SkRuleBase rule = null;

	public SkActions getActions() {
		return actions;
	}

	public void setActions(final SkActions inActions) {
		actions = inActions;
	}

	public SkAction getAction() {
		return action;
	}

	public void setAction(final SkAction inAction) {
		action = inAction;
	}

	@Override
	public C execute(SkRuleRunner inRunner) {
		if (null != actions) {
			for (SkAction localAction : actions.getActionList()) {
				if (null != localAction) {
					localAction.execute(inRunner);
				}
			}
		}
		if (null != this.action) {
			this.action.execute(inRunner);
		}
		if (null != this.rule) {
			this.rule.existingRun(inRunner);
		}
		return null;
	}

	@Override
	public SkConditionType getConditionType() {
		throw new IllegalArgumentException("Not implemeted, this class needs to be extended.");
	}
}
