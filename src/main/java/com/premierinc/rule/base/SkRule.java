package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;

/**
 * Basic methods for interacting with Rules.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SkRule {

	public String getName();
	public String getDescription();
	public SkCondition getCondition();
	public SkActions getActions();
	public SkExpressions getExpressions();
	Boolean run(SkRuleRunner inSkRuleRunner);
}
