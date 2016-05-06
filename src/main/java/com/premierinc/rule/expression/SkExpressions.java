package com.premierinc.rule.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
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
		this.skExpressions.add(expression);
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
}
