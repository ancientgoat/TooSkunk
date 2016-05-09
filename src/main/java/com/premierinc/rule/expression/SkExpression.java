package com.premierinc.rule.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to hold a Stringed expression to be run under Spring SpEL.
 */
public class SkExpression {

	private Logger log = LoggerFactory.getLogger(SkExpression.class);

	@JsonProperty("expression")
	private String originalString;

	@JsonIgnore
	private String expressionString;

	/**
	 * We keep a list on macros contained inside each expression.
	 */
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

	/**
	 *
	 */
	public String getExpressionString() {
		return expressionString;
	}

	/**
	 *
	 */
	@JsonIgnore
	public List<String> getMacroList() {
		return Lists.newArrayList(this.macroMap.keySet());
	}

	/**
	 * This method actually will set a value in the local context, based on this expression.
	 */
	public void setValue(@NotNull SkRuleRunner inRunner) {
		inRunner.setValue(this);
	}

	/**
	 * This method actually will return a value from the local context, based on this expression.
	 * Most often used to get a true or false for an 'if' condition.
	 */
	public Object getValue(@NotNull SkRuleRunner inRunner) {
		return inRunner.getValue(this);
	}
}
