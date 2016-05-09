package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.exception.SkRuleNotFoundException;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressionFactory;
import com.premierinc.rule.run.SkRuleRunner;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkIf {

	private Logger log = LoggerFactory.getLogger(SkIf.class);

	private String inputExpression;
	private SkExpression skExpression;

	private String ruleRef;

	public SkIf() {
	}

	public SkIf(@NotNull String inIfExpression) {
		setExpression(inIfExpression);
	}

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
		if (null != inExpression) {
			this.inputExpression = inExpression;
			validateInputExpression();
			this.skExpression = SkExpressionFactory.parseExpression(this.inputExpression);
		}
	}

	@JsonIgnore
	public SkExpression getSkExpression() {
		return skExpression;
	}

	/**
	 *
	 */
	private void validateInputExpression() {
		if (null == this.inputExpression && null == ruleRef) {
			throw new IllegalArgumentException("Input expression AND ruleRef can not be null.");
		}
		if (null != this.inputExpression) {
			this.inputExpression = this.inputExpression.trim();

			if (0 == this.inputExpression.length()) {
				throw new IllegalArgumentException("Input expression can not be empty.");
			}
		}
	}

	public Boolean run(SkRuleRunner inRunner) {
		Boolean returnValue = null;

		if (null != ruleRef) {
			returnValue = inRunner.getRule(this.ruleRef)
					.run(inRunner);
		} else {
			returnValue = runThis(inRunner);
		}
		return returnValue;
	}

	/**
	 *
	 */
	private Boolean runThis(SkRuleRunner inRunner) {

		String warning = null;

		Object objectAnswer = inRunner.getValue(this.skExpression);

		if (objectAnswer instanceof Boolean) {
			return (Boolean) objectAnswer;
		} else {
			warning = String.format("IF condition mast result in a Boolean, not a " + "%s\nOriginal: %s\nSpEL Exp: %s",
					(null != objectAnswer ?
							objectAnswer.getClass()
									.getName() :
							"null"), this.skExpression.getOriginalString(), this.skExpression.getExpressionString());

		}

		if (null == objectAnswer) {
			// Maybe this is a rule reference
			try {
				objectAnswer = inRunner.getRule(this.skExpression.getOriginalString())
						.run(inRunner);

				if (objectAnswer instanceof Boolean) {
					// Fix the rule for the author, it is not an 'if', it is an 'ifRef'.
					this.ruleRef = this.skExpression.getOriginalString();
					this.inputExpression = null;
					this.skExpression = null;
					return (Boolean) objectAnswer;
				}
			} catch (SkRuleNotFoundException e) {
				// Will fall to exception below
				objectAnswer = null;
			}
		}

		if (log.isDebugEnabled() && null != objectAnswer) {
			log.debug(String.format("IF Class : %s", objectAnswer.getClass()
					.getName()));
			log.debug(String.format("IF Value : %s", objectAnswer.toString()));
		}

		throw new IllegalArgumentException(
				String.format("IF condition mast result in a Boolean, not a " + "%s\nOriginal: %s\nSpEL Exp: %s",
						(null != objectAnswer ?
								objectAnswer.getClass()
										.getName() :
								"null"), this.skExpression.getOriginalString(),
						this.skExpression.getExpressionString()));
	}
}
