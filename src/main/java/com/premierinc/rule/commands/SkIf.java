package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.run.SkRuleContext;
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

	/**
	 *
	 */
	@JsonProperty("expression")
	public void setExpression(String inExpression) {
		this.inputExpression = inExpression;
		validateInputExpression();
		this.skExpression = SkExpressionFactory.parseExpression(this.inputExpression);
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

		// Expression exp = inSkContext.getParser().parseExpression("['a'] + ['b'] + ['c'] + ['MILK']");
		Object answer = inRunner.getValue(this.skExpression);

		System.out.println("IF Class : " + answer.getClass()
				.getName());
		System.out.println("IF Value : " + answer.toString());

		if (answer instanceof Boolean) {
			return (Boolean) answer;
		}

		throw new IllegalArgumentException(String.format("IF condition mast result in a Boolean, not a %s",
				answer.getClass()
						.getName()));
	}

	@Override
	public SkConditionType getConditionType() {
		return SkConditionType.IF;
	}
}
