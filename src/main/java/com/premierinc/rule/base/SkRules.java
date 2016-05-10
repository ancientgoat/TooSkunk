package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * Small shell around more than on rule.
 */
@JsonRootName("rules")
public class SkRules {

	@JsonProperty("rule")
	private List<SkRuleBase> ruleList = null;

	/**
	 *
	 */
	public List<SkRuleBase> getRuleList() {
		return ruleList;
	}

	/**
	 *
	 */
	public void setRuleList(final List<SkRuleBase> inRuleList) {
		ruleList = inRuleList;
	}

	/**
	 *
	 */
	public void addRuleList(final List<SkRuleBase> inRuleList) {
		initList();
		ruleList.addAll(inRuleList);
	}

	/**
	 *
	 */
	public void addRule(final SkRuleBase inRule) {
		initList();
		ruleList.add(inRule);
	}

	/**
	 *
	 */
	private void initList() {
		if (null == this.ruleList) {
			ruleList = Lists.newArrayList();
		}
	}
}
