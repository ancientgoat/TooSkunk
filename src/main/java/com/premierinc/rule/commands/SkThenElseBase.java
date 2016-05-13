package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import javax.validation.constraints.NotNull;

/**
 * Base class for a 'SkThen' and a 'SkElse', as they do the exact same thing.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkThenElseBase {

	@JsonProperty("expressions")
	private SkExpressions expressions;

	@JsonProperty("actions")
	private SkActions actions = new SkActions();

	@JsonProperty("rule")
	private SkRuleBase rule = null;

	public SkActions getActions() {
		return actions;
	}

	public void setActions(@NotNull SkActions inActions) {
		actions = inActions;
	}

	@JsonProperty("action")
	public void setAction(@NotNull SkAction inAction) {
		this.actions.addAction(inAction);
	}

	@JsonProperty("expression")
	public void addExpression(@NotNull SkExpression inExpression) {
		if (null == this.expressions) {
			this.expressions = new SkExpressions();
		}
		this.expressions.addExpression(inExpression);
	}

	/**
	 * Run this 'Then' or 'Else'.
	 */
	public void run(@NotNull SkRuleRunner inRunner) {
		if (null != expressions) {
			this.expressions.run(inRunner);
		}
		if (null != actions) {
			this.actions.run(inRunner);
		}
		if (null != this.rule) {
			inRunner.runRule(this.rule);
		}
	}
}
