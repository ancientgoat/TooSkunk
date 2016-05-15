package com.premierinc.rule.run;

import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.base.SkRule;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.breadcrumbs.SkBreadcrumbs;
import com.premierinc.rule.expression.SkExpression;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.SpelCompilerMode;

/**
 *
 */
public class SkRuleRunner {

	private Logger log = LoggerFactory.getLogger(SkRuleRunner.class);

	private SkRuleContext ruleContext;
	private SkRuleMaster ruleMaster;
	private SkBreadcrumbs breadcrumbs = new SkBreadcrumbs();

	/**
	 *
	 */
	public SkRuleRunner() {
		init(null);
	}

	/**
	 *
	 */
	public SkRuleRunner(SpelCompilerMode inSpelCompilerMode) {
		init(inSpelCompilerMode);
	}

	/**
	 *
	 */
	private void init(SpelCompilerMode inSpelCompilerMode) {
		SpelCompilerMode mode = (null != inSpelCompilerMode ? inSpelCompilerMode : SpelCompilerMode.MIXED);
		this.ruleContext = new SkRuleContext(mode);
		this.ruleContext.setRunner(this);
	}

	//	/**
	//	 *
	//	 */
	//	public void runRules(SkRuleMaster inMaster) {
	//		if (null != inMaster) {
	//			this.setMaster(inMaster);
	//			for (SkRule rule : inMaster.getRuleList()) {
	//				runRule(rule);
	//			}
	//		} else {
	//			throw new IllegalArgumentException("InMaster was null!");
	//		}
	//	}

	public SkRuleContext getRuleContext() {
		return ruleContext;
	}

	public SkRuleMaster getRuleMaster() {
		return ruleMaster;
	}

	/**
	 *
	 */
	public void runRules(List<SkRule> inRuleList) {
		for (SkRule rule : inRuleList) {
			runRule(rule);
		}
	}

	/**
	 *
	 */
	public Boolean runRule(SkRule inRule) {
		return inRule.run(this);
		//		List<SkCondition> conditionList = inRule.getConditionList();
		//		runConditions(conditionList);
	}

	/**
	 *
	 */
	public SkAction getAction(String inActionName) {
		return this.ruleMaster.getAction(inActionName);
	}

	/**
	 *
	 */
	public void setValue(String inKey, Object inValue) {
		if (null != inKey) {
			this.ruleContext.setValue(inKey.toUpperCase(), inValue);
		}
	}

	/**
	 *
	 */
	public void setGlobalValue(String inKey, Object inValue) {
		if (null != inKey) {
			SkGlobalContext.setValue(inKey.toUpperCase(), inValue);
		}
	}

	public void setValue(SkExpression inExpression) {
		this.ruleContext.setValue(inExpression);
	}

	public Object getValue(String inKey) {
		Object value = this.ruleContext.getValue(inKey);
		if (null == value && SkGlobalContext.containsMacroKey(inKey)) {
			value = SkGlobalContext.getValue(inKey);
		}
		return value;
	}

	public Object getValue(String inKey, Object inDefaultValue) {
		Object value = this.ruleContext.getValue(inKey);
		if (null == value && SkGlobalContext.containsMacroKey(inKey)) {
			value = SkGlobalContext.getValue(inKey);
		}
		if (null != value) {
			return value;
		}
		return inDefaultValue;
	}

	public Object getValue(SkExpression inExpression) {
		return this.ruleContext.getValue(inExpression);
	}

	/**
	 *
	 */
	public boolean containsMacroKey(String inKey) {
		return this.ruleContext.containsMacroKey(inKey);
	}

	/**
	 *
	 */
	public void setMaster(final SkRuleMaster inMaster) {
		this.ruleMaster = inMaster;
	}

	/**
	 *
	 */
	public void setupMaster(final SkRuleBase inRule) {
		if (null == this.ruleMaster) {
			this.ruleMaster = new SkRuleMaster.Builder().addRule(inRule)
					.build();
		}
	}

	/**
	 *
	 */
	public SkRule getRule(String inRuleName) {
		return this.ruleMaster.getRule(inRuleName);
	}

	/**
	 *
	 */
	public void executeAction(final String inActionName) {
		SkAction action = this.ruleMaster.getAction(inActionName);
		action.run(this);
	}

	/**
	 *
	 */
	public String expandMacros(final String inMessage) {
		return this.ruleContext.expandMacros(inMessage);
	}

	/**
	 *
	 */
	public Boolean runRuleRef(final String inRuleRef) {
		SkRule rule = this.ruleMaster.getRule(inRuleRef);
		return runRule(rule);
	}

	/**
	 *
	 */
	public void addAlertCrumb(final String inError, Exception inException) {
		this.breadcrumbs.addAlertCrumb(inError, inException);
	}

	public void addFatalCrumb(final String inError, Exception inException) {
		this.breadcrumbs.addFatalCrumb(inError, inException);
	}

	public void addErrorCrumb(final String inError, Exception inException) {
		this.breadcrumbs.addErrorCrumb(inError, inException);
	}

	public void addWarnCrumb(final String inError, Exception inException) {
		this.breadcrumbs.addWarnCrumb(inError, inException);
	}

	public void addDebugCrumb(final String inError, Exception inException) {
		this.breadcrumbs.addDebugCrumb(inError, inException);
	}

	public void addTraceCrumb(final String inError, Exception inException) {
		this.breadcrumbs.addTraceCrumb(inError, inException);
	}

	public void addAlertCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addAlertCrumb(inDescription, inResult);
	}

	public void addFatalCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addFatalCrumb(inDescription, inResult);
	}

	public void addErrorCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addErrorCrumb(inDescription, inResult);
	}

	public void addWarnCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addWarnCrumb(inDescription, inResult);
	}

	public void addInfoCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addInfoCrumb(inDescription, inResult);
	}

	public void addDebugCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addDebugCrumb(inDescription, inResult);
	}

	public void addTraceCrumb(final String inDescription, Object inResult) {
		this.breadcrumbs.addTraceCrumb(inDescription, inResult);
	}

	public void addAlertCrumb(final String inDescription) {
		this.breadcrumbs.addAlertCrumb(inDescription, (Object) null);
	}

	public void addFatalCrumb(final String inDescription) {
		this.breadcrumbs.addFatalCrumb(inDescription, (Object) null);
	}

	public void addErrorCrumb(final String inDescription) {
		this.breadcrumbs.addErrorCrumb(inDescription, (Object) null);
	}

	public void addWarnCrumb(final String inDescription) {
		this.breadcrumbs.addWarnCrumb(inDescription, (Object) null);
	}

	public void addInfoCrumb(final String inDescription) {
		this.breadcrumbs.addInfoCrumb(inDescription, (Object) null);
	}

	public void addDebugCrumb(final String inDescription) {
		this.breadcrumbs.addDebugCrumb(inDescription, (Object) null);
	}

	public void addTraceCrumb(final String inDescription) {
		this.breadcrumbs.addTraceCrumb(inDescription, (Object) null);
	}
}
