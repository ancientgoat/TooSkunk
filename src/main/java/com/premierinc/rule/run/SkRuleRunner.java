package com.premierinc.rule.run;

import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.base.SkRule;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.expression.SkExpression;
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
	public void runRule(SkRule inRule) {
		inRule.run(this);
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

	public void setValue(SkExpression inExpression) {
		this.ruleContext.setValue(inExpression);
	}

	public Object getValue(String inKey) {
		return this.ruleContext.getValue(inKey);
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

	public String expandMacros(final String inMessage) {
		return this.ruleContext.expandMacros(inMessage);
	}
}
