package com.premierinc.common.rule;

import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.run.SkRuleRunner;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class Test00001SimpleNumericRuleTest {

	private Logger log = LoggerFactory.getLogger(Test00001SimpleNumericRuleTest.class);

	public static final String ONE_RULE_FILE_NAME = "NumericOneRuleTest.json";
	public static final String ONE_RULE_FAIL_FILE_NAME = "NumericOneRuleTestFail.json";
	public static final String TWO_RULE_FILE_NAME = "NumericTwoRuleTest.json";
	public static final String ONE_ACTION_FILE_NAME = "Test_Action_Now_001.json";

	/**
	 * Can we read simple JSON without Exception?
	 */
	@Test
	public void testSimpleRuleTest() {
		SkRuleBase rule = buildThings(ONE_RULE_FILE_NAME, SkRuleBase.class, "rule");
	}

	/**
	 * Can we read simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {
		SkRuleBase rule = buildThings(ONE_RULE_FILE_NAME, SkRuleBase.class, "rule");
		String json = JsonMapperHelper.beanToJsonPretty(rule);

		System.out.println("-------------------- JSON ---------------------");
		System.out.println(json);
		System.out.println("-------------------- JSON ---------------------");
		SkRuleRunner runner = new SkRuleRunner();
		rule.run(runner);
	}

	/**
	 * One rule fail .
	 */
	@Test
	public void testSimpleOneRuleFailExecution() {

		SkRuleBase rule = buildThings(ONE_RULE_FAIL_FILE_NAME, SkRuleBase.class, "rule");

		SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		try {
			runner.setValue("THIS_MACRO_DOES_NOT_EXIST", "X");
			rule.run(runner);
			Assert.fail("We should have thrown an error.");
		} catch (Exception e) {
			System.out.println(String.format("We expected this error : '%s'", e.toString()));
		}
		runner.setValue("MILK.QTY", 2);
		rule.run(runner);
	}

	/**
	 * One rule test.
	 */
	@Test
	public void testSimpleOneRuleExecutionWithExternalDataTest() {
		SkRuleBase rule = buildThings(ONE_RULE_FAIL_FILE_NAME, SkRuleBase.class, "rule");
		SkActions actions = buildThings(ONE_ACTION_FILE_NAME, SkActions.class, "actions");

		SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
				.addActions(actions)
				.build();

		SkRuleRunner runner = master.getRuleRunner();
		rule.run(runner);
	}

	/**
	 * Two rule test.
	 */
	@Test
	public void testSimpleTwoRuleExecutionTest() {

		SkRuleBase rule = buildThings(TWO_RULE_FILE_NAME, SkRuleBase.class, "rule");
		SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule)
				.build();

		SkRuleRunner runner = master.getRuleRunner();

		runner.setValue("MILK", 2);
		rule.run(runner);
		runner.setValue("MILK", 9999);
		runner.runRule(rule);
	}
}
