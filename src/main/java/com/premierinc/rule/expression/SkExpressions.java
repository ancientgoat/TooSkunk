package com.premierinc.rule.expression;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SkExpressions {

	@JsonProperty("expression")
	private List<SkExpression> expressions = Lists.newArrayList();

	public void sddExpression(String inExpressionString){
		SkExpression expression = SkExpressionFactory.parseExpression(inExpressionString);
		expressions.add(expression);
	}

	public List<SkExpression> getExpressions() {
		return expressions;
	}
}
