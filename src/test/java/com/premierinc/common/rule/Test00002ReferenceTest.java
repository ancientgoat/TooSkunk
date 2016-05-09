package com.premierinc.common.rule;

import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
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
public class Test00002ReferenceTest {

	private Logger log = LoggerFactory.getLogger(Test00002ReferenceTest.class);

	public static final String FIRST_REF_TEST_FILE_NAME = "StringReferenceRule_3_Parter.json";
	public static final String SECOND_REF_TEST_FILE_NAME = "StringReferenceRule_4_WithOtherRefs.json";
	public static final String THIRD_REF_TEST_FILE_NAME = "StringReferenceRule_5_MultiAction.json";
	public static final String FORTH_REF_TEST_FILE_NAME = "StringReferenceRule_6_MultiActionWithMacros.json";
	public static final String FIRST_ACTION_FILE_NAME = "Test_Actions_001.json";
	public static final String SECOND_ACTION_FILE_NAME = "Test_Actions_002_With_Macros.json";

	/**
	 * Can we read simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {
		SkRules rules = buildThings(FIRST_REF_TEST_FILE_NAME, SkRules.class, "rules");
		String json = JsonMapperHelper.beanToJsonPretty(rules);
		System.out.println("-------------------- xJSON ---------------------");
		System.out.println(json);
		System.out.println("-------------------- xJSON ---------------------");
		// rule.setValue();
	}

	/**
	 * Three rule reference test.
	 */
	@Test
	public void testRefThreeRuleTest() {

		SkRules rules = buildThings(FIRST_REF_TEST_FILE_NAME, SkRules.class, "rules");
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.build();
		SkRuleRunner runner = master.getRuleRunner();
		try {
			runner.setValue("THIS_MACRO_DOES_NOT_EXIST", "X");
			runner.runRule(master.getRule("COKE_RULE_00003"));
			Assert.fail("We should have thrown an error.");
		} catch (Exception e) {
			System.out.println(String.format("We expected this error : '%s'", e.toString()));
		}

		// Note: All macros are turned into UPPER case.
		runner.setValue("nAmE", "Fred");

		// Choose only one rule, from the three, to setValue.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read in actions
	 */
	@Test
	public void testReadActionsTest() {
		SkActions actions = buildThings(FIRST_ACTION_FILE_NAME, SkActions.class, "actions");
	}

	/**
	 * Read in actions - setValue with Three rule action-ref test.
	 */
	@Test
	public void testActionRefRuleRefTest() {

		SkActions actions = buildThings(FIRST_ACTION_FILE_NAME, SkActions.class, "actions");
		SkRules rules = buildThings(SECOND_REF_TEST_FILE_NAME, SkRules.class, "rules");
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		// Note: All macros are turned into UPPER case.
		runner.setValue("nAmE", "Fred");

		// Choose only one rule, from the three, to setValue.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read in actions - setValue with Three rule action-ref test.
	 */
	@Test
	public void testMultiActionRefRuleRefTest() {

		SkActions actions = buildThings(FIRST_ACTION_FILE_NAME, SkActions.class, "actions");
		SkRules rules = buildThings(THIRD_REF_TEST_FILE_NAME, SkRules.class, "rules");
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		// Note: All macros are turned into UPPER case.
		runner.setValue("nAmE", "Fred");

		// Choose only one rule, from the three, to setValue.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read in actions - setValue with Three rule action-ref test.
	 */
	@Test
	public void testMultiActionWithMacrosRefRuleRefTest() {
		SkActions actions = buildThings(SECOND_ACTION_FILE_NAME, SkActions.class, "actions");
		SkRules rules = buildThings(FORTH_REF_TEST_FILE_NAME, SkRules.class, "rules");
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		// Note: All macros are turned into UPPER case.
		runner.setValue("nAmE", "Fred");

		// Choose only one rule, from the three, to setValue.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}
}
