package com.premierinc.rule.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.premierinc.rule.commands.SkCondition;
import java.util.List;

/**
 *
 */
public interface SkRule {
	public String getName();
	public String getDescription();
	public List<SkCondition> getConditionList();
}
