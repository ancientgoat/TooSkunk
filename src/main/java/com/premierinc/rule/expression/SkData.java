package com.premierinc.rule.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;

/**
 *
 */
@JsonRootName("data")
public class SkData {

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

	@JsonIgnore
	public List<SkExpression> getSkExpressions() {
		return this.expressions.getSkExpressions();
	}
}
