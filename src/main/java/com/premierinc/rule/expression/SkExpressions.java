package com.premierinc.rule.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;

/**
 *
 */
@JsonRootName("expressions")
public class SkExpressions {

	private List<String> expressions = Lists.newArrayList();

	@JsonIgnore
	private List<SkExpression> skExpressions = Lists.newArrayList();

	public void addExpression(String inExpressionString) {
		SkExpression expression = SkExpressionFactory.parseExpression(inExpressionString);
		this.expressions.add(inExpressionString);
		addExpression(expression);
	}

	public void addExpression(SkExpression inExpression) {
		this.skExpressions.add(inExpression);
	}

	public List<SkExpression> getSkExpressions() {
		return this.skExpressions;
	}

	public List<String> getExpressions() {
		return expressions;
	}

	/**
	 *
	 */
	public SkExpressions addExpressions(final List<String> inExpressionList) {
		if (null != inExpressionList)
			inExpressionList.forEach(e -> {
				expressions.add(e);
				skExpressions.add(SkExpressionFactory.parseExpression(e));
			});
		return this;
	}

	/**
	 *
	 */
	public void run(SkRuleRunner inRunner) {
		if (null != this.skExpressions && 0 < this.skExpressions.size()) {
			this.skExpressions.forEach(e -> {
				e.setValue(inRunner);
			});
		}
	}
}
