package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.commands.SkElse;
import com.premierinc.rule.commands.SkIf;
import com.premierinc.rule.commands.SkThen;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;

/**
 *
 */
@JsonRootName("rule")
public class SkRuleBase implements SkRule {

	private String name;
	private String description;

	@JsonProperty("condition")
	private List<SkCondition> conditionList = Lists.newArrayList();

	private SkExpressions expressions;

	@JsonIgnore
	SkRuleRunner runner = new SkRuleRunner();

	@JsonIgnore
	private SkIf skIf;

	@JsonIgnore
	private SkThen skThen;

	@JsonIgnore
	private SkElse skElse;

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String inName) {
		name = inName;
	}

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

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(final String inDescription) {
		description = inDescription;
	}

	public List<SkCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(final List<SkCondition> inConditionList) {
		conditionList = inConditionList;
	}

	public SkExpressions getExpressions() {
		return expressions;
	}

	public List<SkExpression> getSkExpressions() {
		if (null != this.expressions) {
			return this.expressions.getSkExpressions();
		}
		return Lists.newArrayList();
	}

	public void setExpressions(final SkExpressions inExpressions) {
		expressions = inExpressions;
	}

	public SkIf getSkIf() {
		return skIf;
	}

	public SkThen getSkThen() {
		return skThen;
	}

	public SkElse getSkElse() {
		return skElse;
	}

	public void run() {
		setupMaster();
		this.runner.runRule(this);
	}

	/**
	 *
	 */
	private void setupMaster() {
		if (null != this.runner) {
			this.runner.setupMaster(this);
		}
	}

	public void run(SkRuleRunner inRunner) {
		this.runner = inRunner;
		inRunner.runRule(this);
	}

	public void existingRun(SkRuleRunner inRunner) {
		inRunner.runRule(this);
	}

	/**
	 * Do setup things for this Rule
	 * - Populate the skIf, skThen, skElse attributes from the conditions.
	 */
	void setUp() {
		this.conditionList.forEach(c -> {
			if (c instanceof SkIf) {
				this.skIf = (SkIf) c;
			} else if (c instanceof SkThen) {
				this.skThen = (SkThen) c;
			} else if (c instanceof SkElse) {
				this.skElse = (SkElse) c;
			}
		});
	}
}
