package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.premierinc.rule.action.custom.SkActionCustom;
import com.premierinc.rule.action.custom.SkActionCustomHelper;
import com.premierinc.rule.action.enums.SkActionContext;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actiontype")
@JsonSubTypes({//
		@JsonSubTypes.Type(value = SkActionCustomHelper.class, name = "CUSTOM"),
		@JsonSubTypes.Type(value = SkActionData.class, name = "DATA"),
		@JsonSubTypes.Type(value = SkActionJmsProducer.class, name = "JMSPRODUCER"),
		@JsonSubTypes.Type(value = SkActionJmsConsumer.class, name = "JMSCONSUMER"),
		@JsonSubTypes.Type(value = SkActionLog.class, name = "LOG"),
		@JsonSubTypes.Type(value = SkActionPrint.class, name = "PRINT"),
		@JsonSubTypes.Type(value = SkActionReference.class, name = "REF"),
		@JsonSubTypes.Type(value = SkActionRest.class, name = "REST"),
		@JsonSubTypes.Type(value = SkActionReadPropertyFile.class, name = "READPROPERTYFILE") //
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SkAction {

	private String name;
	private String actionRef;
	private SkActionContext actionContext = SkActionContext.NORMAL;

	public abstract void run(SkRuleRunner inRunner);

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

	@JsonProperty("context")
	public void setActionContext(final SkActionContext inActionContext) {
		actionContext = inActionContext;
	}

	@JsonProperty("context")
	public SkActionContext getActionContext() {
		return actionContext;
	}
}
