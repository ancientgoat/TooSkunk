package com.premierinc.rule.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.L;

/**
 *
 */
public class SkExpression {

	@JsonProperty("expression")
	private String originalString;

	@JsonIgnore
	private String expressionString;

	private Map<String, String> macroMap = Maps.newHashMap();

	public SkExpression() {
	}

	public SkExpression(final List<String> inMacroList, String inOriginalExpression, String inExpressionString) {
		this.expressionString = inExpressionString;
		this.originalString = inOriginalExpression;
		inMacroList.forEach(m -> macroMap.put(m, null));
	}

	@JsonProperty("expression")
	public void setExpression(String inExpressionsString) {
		SkExpression newExpression = SkExpressionFactory.parseExpression(inExpressionsString);
		this.expressionString = newExpression.expressionString;
		this.originalString = newExpression.originalString;
		this.macroMap.clear();
		this.macroMap.putAll(newExpression.macroMap);
	}

	@JsonProperty("expression")
	public String getOriginalString() {
		return originalString;
	}

	public String getExpressionString() {
		return expressionString;
	}

	@JsonIgnore
	public List<String> getMacroList() {
		return Lists.newArrayList(this.macroMap.keySet());
	}
}
