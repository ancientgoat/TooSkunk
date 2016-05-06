package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actiontype")
@JsonSubTypes({//
		@JsonSubTypes.Type(value = SkActionPrint.class, name = "PRINT"),
		@JsonSubTypes.Type(value = SkActionLog.class, name = "LOG"),
		@JsonSubTypes.Type(value = SkActionReference.class, name = "REF")  //
})
public abstract class SkAction {

	private String name;
	private String actionRef;

	public abstract void execute(SkRuleRunner inRunner);

	@JsonIgnore
	public abstract SkActionType getActionType();

	public String getName() {
		return name;
	}

	public void setName(final String inName) {
		name = inName;
	}

	public String getActionRef() {
		return actionRef;
	}

	public void setActionRef(final String inActionRef) {
		actionRef = inActionRef;
	}
}
