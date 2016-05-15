package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkConditionRefs {

	private Logger log = LoggerFactory.getLogger(SkConditionRefs.class);

	List<String> conditionRefs = Lists.newArrayList();

	public List<String> getConditionRefs() {
		return conditionRefs;
	}

	public SkConditionRefs setConditionRefs(final List<String> inConditionRefs) {
		conditionRefs = inConditionRefs;
		return this;
	}
}
