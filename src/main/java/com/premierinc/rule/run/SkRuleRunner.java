package com.premierinc.rule.run;

import com.premierinc.rule.base.SkRule;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.commands.SkConditionStateMachine;
import com.premierinc.rule.commands.enums.SkConditionType;
import com.premierinc.rule.expression.SkExpression;
import java.util.List;
import org.springframework.expression.spel.SpelCompilerMode;

/**
 *
 */
public class SkRuleRunner {

	private SkRuleContext ruleContext;

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

	/**
	 *
	 */
	public void runRule(SkRule inRule) {
		List<SkCondition> conditionList = inRule.getConditionList();
		runConditions(conditionList);
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
	public void runConditions(List<SkCondition> inConditionList) {

		// State machine
		SkConditionStateMachine machine = new SkConditionStateMachine();
		Boolean ifCondition = true;

		for (SkCondition condition : inConditionList) {

			SkConditionType conditionType = condition.getConditionType();

			machine.checkState(conditionType);

			switch (conditionType) {
			case IF:
				System.out.println("SkRuleRunner : Executing IF ");
				ifCondition = (boolean) condition.execute(this);
				break;

			case THEN:
				if (ifCondition) {
					System.out.println("SkRuleRunner : Executing THEN ");
					condition.execute(this);
				} else {
					System.out.println("SkRuleRunner : Skipping THEN ");
				}
				break;

			case ELSE:
				if (!ifCondition) {
					System.out.println("SkRuleRunner : Executing ELSE ");
					condition.execute(this);
				} else {
					System.out.println("SkRuleRunner : Skipping ELSE ");
				}
				break;

			default:
				throw new IllegalArgumentException(
						String.format("SkConditionType of '%s' is not implemented.", conditionType));
			}
		}
	}

	public void setValue(String inKey, Object inValue) {
		this.ruleContext.setValue(inKey, inValue);
	}

	public Object getValue(String inKey) {
		return this.ruleContext.getValue(inKey);
	}

	public Object getValue(SkExpression inExpression) {
		return this.ruleContext.getValue(inExpression);
	}
}
