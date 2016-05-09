package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.commands.SkElse;
import com.premierinc.rule.commands.SkIf;
import com.premierinc.rule.commands.SkThen;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 */
@JsonRootName("rule")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkRuleBase implements SkRule {

	private String name;
	private String description;

	@JsonProperty("actions")
	private SkActions actions;

	@JsonProperty("condition")
	private SkCondition condition;

	private SkExpressions expressions;

	//	@JsonIgnore
	//	SkRuleRunner runner = new SkRuleRunner();

	@JsonIgnore
	private SkIf skIf;

	@JsonIgnore
	private SkThen skThen;

	@JsonIgnore
	private SkElse skElse;

	/**
	 *
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 *
	 */
	public void setName(final String inName) {
		name = inName;
	}

	/**
	 *
	 */
	@JsonProperty("expressions")
	public void setExpressionList(List<String> inExpressionList) {
		if (null != inExpressionList) {
			this.expressions = new SkExpressions().addExpressions(inExpressionList);
		}
	}

	/**
	 *
	 */
	@JsonProperty("expressions")
	public List<String> getExpressionList() {
		if (null != expressions) {
			return expressions.getExpressions();
		}
		return null;
	}

	/**
	 *
	 */
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public SkCondition getCondition() {
		return this.condition;
	}

	/**
	 *
	 */
	public void setDescription(final String inDescription) {
		description = inDescription;
	}

	/**
	 *
	 */
	public SkExpressions getExpressions() {
		return expressions;
	}

	@Override
	public Boolean run(final SkRuleRunner inRunner) {
		if (null == this.condition) {
			throw new IllegalArgumentException("condition seems to be missing?");
		}
		if (null != expressions) {
			this.expressions.run(inRunner);
		}
		if (null != this.actions) {
			this.actions.run(inRunner);
		}
		return this.condition.run(inRunner);
	}

	/**
	 *
	 */
	public void setExpressions(final SkExpressions inExpressions) {
		expressions = inExpressions;
	}

	/**
	 *
	 */
	public SkElse getSkElse() {
		return skElse;
	}

	/**
	 *
	 */
	public SkActions getActions() {
		return actions;
	}

	/**
	 *
	 */
	public void setActions(@NotNull SkActions inActions) {
		actions = inActions;
	}

	/**
	 *
	 */
	@JsonProperty("action")
	public void setAction(@NotNull SkAction inAction) {
		if (null == this.actions) {
			this.actions = new SkActions();
		}
		this.actions.addAction(inAction);
	}
}
