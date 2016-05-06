package com.premierinc.rule.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.premierinc.rule.commands.enums.SkConditionType;
import com.premierinc.rule.run.SkRuleRunner;

/**
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = SkIf.class, name = "IF"),
		@JsonSubTypes.Type(value = SkThen.class, name = "THEN"),
		@JsonSubTypes.Type(value = SkElse.class, name = "ELSE"),
})
public abstract class SkCondition<OBJ> {
	public abstract OBJ execute(SkRuleRunner inRunner);

	@JsonIgnore
	public abstract SkConditionType getConditionType();
}
