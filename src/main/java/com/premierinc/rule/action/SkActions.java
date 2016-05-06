package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 */
@JsonRootName("actions")
public class SkActions {

	@JsonProperty("action")
	private List<SkAction> actionList = Lists.newArrayList();

	public List<SkAction> getActionList() {
		return actionList;
	}

	public void setActionList(final List<SkAction> inActionList) {
		actionList = inActionList;
	}
}
