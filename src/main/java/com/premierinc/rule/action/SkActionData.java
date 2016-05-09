package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;

/**
 *
 */
public class SkActionData extends SkAction {

	private SkExpressions expressions;

	@JsonProperty("expressions")
	public void setExpressionList(List<String> inExpressionList) {
		if (null != inExpressionList) {
			this.expressions = new SkExpressions().addExpressions(inExpressionList);
		}
	}

	@JsonProperty("expressions")
	public List<String> getExpressionList() {
		if (null != expressions) {
			return expressions.getExpressions();
		}
		return null;
	}

	@JsonProperty("expression")
	public void addExpression(String inExpression) {
		this.expressions.addExpression(inExpression);
	}

	@JsonIgnore
	public List<SkExpression> getSkExpressions() {
		return this.expressions.getSkExpressions();
	}

	@Override
	public void run(final SkRuleRunner inRunner) {
		if (null != this.expressions) {
			this.expressions.run(inRunner);
		}
	}
}
