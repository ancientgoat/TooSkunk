package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.run.SkRuleContext;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;

/**
 *
 */
@JsonRootName("rule")
public class SkRuleBase implements SkRule{
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

	public void run(){
		this.runner = new SkRuleRunner();
		this.runner.runRule(this);
	}

	public void run(SkRuleRunner inRunner){
		this.runner = inRunner;
		inRunner.runRule(this);
	}

	public void existingRun(SkRuleRunner inRunner){
		inRunner.runRule(this);
	}
}
