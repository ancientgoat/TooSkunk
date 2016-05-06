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

	@JsonProperty("expressions")
	private List<SkExpression> expressions = Lists.newArrayList();

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

	public List<SkExpression> getExpressions() {
		return expressions;
	}

	public void setExpressions(final List<SkExpression> inExpressions) {
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
		this.runner.runExpressions(this.expressions);
		this.runner.runRule(this);
	}

	public void run(SkRuleRunner inRunner) {
		this.runner = inRunner;
		this.runner.runExpressions(this.expressions);
		inRunner.runRule(this);
	}

	public void existingRun(SkRuleRunner inRunner) {
		inRunner.runExpressions(this.expressions);
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
