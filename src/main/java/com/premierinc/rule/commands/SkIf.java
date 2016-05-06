package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.commands.enums.SkConditionType;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressionFactory;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
public class SkIf extends SkCondition<Boolean> {
	// {
	//    "type": "IF",
	// 	  "expression": "MILK > 1.11 * 1"
	// },
	private String inputExpression;
	private SkExpression skExpression;

	private String ruleRef;

	@JsonProperty("ruleref")
	public void setRuleRef(String inRuleRef) {
		ruleRef = inRuleRef;
	}

	public String getRuleRef() {
		return ruleRef;
	}

	/**
	 *
	 */
	@JsonProperty("expression")
	public void setExpression(String inExpression) {
		this.inputExpression = inExpression;
		validateInputExpression();
		this.skExpression = SkExpressionFactory.parseExpression(this.inputExpression);
	}

	public SkExpression getSkExpression() {
		return skExpression;
	}

	/**
	 *
	 */
	private void validateInputExpression() {
		if (null == this.inputExpression) {
			throw new IllegalArgumentException("Input expression can not be null.");
		}
		this.inputExpression = this.inputExpression.trim();

		if (0 == this.inputExpression.length()) {
			throw new IllegalArgumentException("Input expression can not be empty.");
		}
	}

	@Override
	public Boolean execute(SkRuleRunner inRunner) {
		Boolean returnValue = null;

		if (null != ruleRef) {
			returnValue = inRunner.runConditionRef(this);
		} else {
			returnValue = runExpression(inRunner);
		}
		return returnValue;
	}

	/**
	 *
	 */
	private boolean runExpression(SkRuleRunner inRunner) {

		// Expression exp = inSkContext.getParser().parseExpression("['a'] + ['b'] + ['c'] + ['MILK']");
		Object objectAnswer = inRunner.getValue(this.skExpression);

		System.out.println("IF Class : " + objectAnswer.getClass()
				.getName());
		System.out.println("IF Value : " + objectAnswer.toString());

		if (objectAnswer instanceof Boolean) {
			return (Boolean) objectAnswer;
		}

		throw new IllegalArgumentException(String.format("IF condition mast result in a Boolean, not a "
				+ "%s\nOriginal: %s\nSpEL Exp: %s",
				objectAnswer.getClass()
						.getName(), this.skExpression.getOriginalString(), this.skExpression.getExpressionString()));
	}

	@Override
	public SkConditionType getConditionType() {
		return SkConditionType.IF;
	}
}
