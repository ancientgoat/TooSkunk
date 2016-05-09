package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.premierinc.rule.action.enums.SkActionContext;
import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actiontype")
@JsonSubTypes({//
		@JsonSubTypes.Type(value = SkActionPrint.class, name = "PRINT"),
		@JsonSubTypes.Type(value = SkActionLog.class, name = "LOG"),
		@JsonSubTypes.Type(value = SkActionReference.class, name = "REF"),
		@JsonSubTypes.Type(value = SkActionRest.class, name = "REST"),
		@JsonSubTypes.Type(value = SkActionReadPropertyFile.class, name = "READPROPERTYFILE") //
})
public abstract class SkAction {

	private String name;
	private String actionRef;
	private SkActionContext actionContext = SkActionContext.NORMAL;

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

	public void setActionContext(final SkActionContext inActionContext) {
		actionContext = inActionContext;
	}

	public SkActionContext getActionContext() {
		return actionContext;
	}
}
