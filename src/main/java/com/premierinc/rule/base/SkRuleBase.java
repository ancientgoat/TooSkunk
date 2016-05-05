package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressionFactory;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;

/**
 *
 */
@JsonRootName("rule")
public class SkRuleBase implements SkRule {
	// /////////////////////////////////////////////////////////////////////////
	// {
	// 		"Rule": {
	// 			"name": "XXXXXXXX",
	// 			"description": "yyyyyyyyyyyyyy",
	// 			"condition": [
	// 				{
	// 					"type": "IF",
	// 					"expression": "MILK > 1.11 * 1"
	// 				},
	// 				{
	// 					"type": "THEN",
	// 					"action": {
	// 				  		"actiontype": "print",
	// 				  		"message": "xTHENx"
	// 					}
	// 				},
	// 				{
	// 					"type": "ELSE",
	// 					"action": {
	// 				  		"actiontype": "print",
	// 				  		"message": "xELSEx"
	// 					}
	// 				}
	// 			]
	//    	}
	// }
	// /////////////////////////////////////////////////////////////////////////
	private String name;
	private String description;

	@JsonProperty("condition")
	private List<SkCondition> conditionList = Lists.newArrayList();

	@JsonProperty("expressions")
	private List<SkExpression> expressions = Lists.newArrayList();

	//	@JsonProperty("expressions")
//	private SkExpressions expressions;

	@JsonIgnore
	SkRuleRunner runner = new SkRuleRunner();

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

	@Override
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

	public void run() {
		this.runner = new SkRuleRunner();
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
}
