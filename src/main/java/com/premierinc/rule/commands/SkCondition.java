package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.run.SkRuleRunner;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holder and executor of the if/then/else sequence.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkCondition {

	private Logger log = LoggerFactory.getLogger(SkCondition.class);

	private SkIf skIf;

	@JsonProperty("then")
	private SkThen skThen;

	@JsonProperty("else")
	private SkElse skElse;

	@JsonProperty("finally")
	private SkElse skFinally;

	/**
	 *
	 */
	public SkIf getSkIf() {
		return skIf;
	}

	/**
	 *
	 */
	public void setSkIf(@NotNull SkIf inSkIf) {
		skIf = inSkIf;
	}

	/**
	 * setter interacting with the json
	 */
	@JsonProperty("if")
	public void setSkIf(@NotNull String inIfString) {
		this.skIf = new SkIf(inIfString);
	}

	/**
	 * getter interacting with the json
	 */
	@JsonProperty("if")
	public String getIf() {
		SkExpression skExpression = this.skIf.getSkExpression();
		return (null != skExpression ? skExpression.getOriginalString() : null);
	}

	/**
	 * Rule reference setter
	 */
	@JsonProperty("ruleref")
	public void setIfRef(@NotNull String inIfRef) {
		if (null != inIfRef) {
			if (null == this.skIf) {
				this.skIf = new SkIf();
			}
			this.skIf.setRuleRef(inIfRef);
		}
	}

	/**
	 * Rule reference getter
	 */
	@JsonProperty("ruleref")
	public String getIfRef() {
		return (null != this.skIf ? this.skIf.getRuleRef() : null);
	}

	public SkThen getSkThen() {
		return skThen;
	}

	public void setSkThen(final SkThen inSkThen) {
		skThen = inSkThen;
	}

	public SkElse getSkElse() {
		return skElse;
	}

	public void setSkElse(final SkElse inSkElse) {
		skElse = inSkElse;
	}

	public SkElse getSkFinally() {
		return skFinally;
	}

	public void setSkFinally(final SkElse inSkFinally) {
		skFinally = inSkFinally;
	}

	/**
	 * Run the if/then/else
	 */
	public Boolean run(SkRuleRunner inRunner) {
		Boolean returnAnswer = null;
		if (null != this.skIf) {
			returnAnswer = this.skIf.run(inRunner);
			if (returnAnswer) {
				if (null != this.skThen) {
					this.skThen.run(inRunner);
				}
			} else {
				if (null != this.skElse) {
					this.skElse.run(inRunner);
				}
			}
		}
		if (null != this.skFinally) {
			this.skFinally.run(inRunner);
		}
		return returnAnswer;
	}
}
