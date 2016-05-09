package com.premierinc.rule.action;

import com.premierinc.rule.run.SkRuleRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Different action - run a reference to an action.
 */
public class SkActionReference extends SkAction {

	private Logger log = LoggerFactory.getLogger(SkActionReference.class);

	private String actionref;

	public String getActionref() {
		return actionref;
	}

	public void setActionref(final String inActionName) {
		actionref = inActionName;
	}

	@Override
	public void run(SkRuleRunner inRunner) {
		inRunner.executeAction(this.actionref);
	}
}
