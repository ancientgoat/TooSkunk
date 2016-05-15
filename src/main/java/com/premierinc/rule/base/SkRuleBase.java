package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.commands.SkConditionRefs;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * The first simple SkRule implementation.
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

	@JsonProperty("conditionrefs")
	private SkConditionRefs conditionRefs;

	private SkExpressions expressions;
	private SkExpressions postExpressions;

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
	@JsonProperty("postexpressions")
	public void setPostExpressionList(List<String> inPostExpressionList) {
		if (null != inPostExpressionList) {
			this.postExpressions = new SkExpressions().addExpressions(inPostExpressionList);
		}
	}

	/**
	 *
	 */
	@JsonProperty("conditionrefs")
	public void setConditionRefs(List<String> inConditionRefs) {
		if (null != inConditionRefs) {
			this.conditionRefs = new SkConditionRefs().setConditionRefs(inConditionRefs);
		}
	}

	/**
	 *
	 */
	@JsonProperty("conditionrefs")
	public List<String> getConditionRefs() {
		if (null != conditionRefs) {
			return conditionRefs.getConditionRefs();
		}
		return null;
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
	@JsonProperty("postexpressions")
	public List<String> getPostExpressionList() {
		if (null != postExpressions) {
			return this.postExpressions.getExpressions();
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
		// Every rule must have a condition, otherwise just use an SkAction.
		if (null == this.condition) {
			throw new IllegalArgumentException("condition seems to be missing?");
		}
		// Run any expressions, before the rule.
		if (null != expressions) {
			this.expressions.run(inRunner);
		}
		// Run any actions, before the rules.
		if (null != this.actions) {
			this.actions.run(inRunner);
		}
		// Finally run the if/then/else
		Boolean answer = this.condition.run(inRunner);

		if (null != this.postExpressions) {
			this.postExpressions.run(inRunner);
		}

		// Finally return the answer
		return answer;
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
