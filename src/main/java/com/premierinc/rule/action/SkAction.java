package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkRuleContext;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actiontype")
@JsonSubTypes({//
		@JsonSubTypes.Type(value = SkActionPrint.class, name = "PRINT") //
})
public abstract class SkAction {
	public abstract SkActionType getActionType();
	public abstract void execute(SkRuleRunner inRunner);
}
