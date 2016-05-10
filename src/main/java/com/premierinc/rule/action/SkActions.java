package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 */
@JsonRootName("actions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkActions {

	@JsonProperty("action")
	private List<SkAction> actionList;

	@JsonProperty("expressions")
	private SkExpressions expressions;

	/**
	 *
	 */
	@JsonProperty("expression")
	public void addExpression(@NotNull SkExpression inExpression) {
		if (null == this.expressions) {
			this.expressions = new SkExpressions();
		}
		this.expressions.addExpression(inExpression);
	}

	/**
	 *
	 */
	public List<SkAction> getActionList() {
		return actionList;
	}

	/**
	 *
	 */
	public void setActionList(@NotNull List<SkAction> inActionList) {
		actionList = inActionList;
	}

	/**
	 *
	 */
	public void addActionList(@NotNull List<SkAction> inActionList) {
		initList();
		actionList.addAll(inActionList);
	}

	/**
	 *
	 */
	public void addAction(@NotNull SkAction inAction) {
		initList();
		this.actionList.add(inAction);
	}

	/**
	 *
	 */
	private void initList() {
		if (null == actionList) {
			this.actionList = Lists.newArrayList();
		}
	}

	/**
	 *
	 */
	public SkExpressions getExpressions() {
		return expressions;
	}

	/**
	 *
	 */
	public void setExpressions(@NotNull SkExpressions inExpressions) {
		expressions = inExpressions;
	}

	/**
	 *
	 */
	public void run(final SkRuleRunner inRunner) {
		// Actions first - then Expressions.
		if (null != this.actionList) {
			for (SkAction localAction : this.actionList) {
				if (null != localAction) {
					localAction.run(inRunner);
				}
			}
		}
		// Expressions.
		if (null != this.expressions) {
			this.expressions.run(inRunner);
		}
	}
}
