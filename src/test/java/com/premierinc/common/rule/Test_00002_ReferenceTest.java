package com.premierinc.common.rule;

import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.expression.SkExpressionFactory;
import com.premierinc.rule.run.SkRuleRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class Test_00002_ReferenceTest {

	private Logger log = LoggerFactory.getLogger(Test_00002_ReferenceTest.class);

	public static final String FIRST_REF_TEST_FILE_NAME = "StringReferenceRule_3_Parter.json";
	public static final String SECOND_REF_TEST_FILE_NAME = "StringReferenceRule_4_WithOtherRefs.json";
	public static final String THIRD_REF_TEST_FILE_NAME = "StringReferenceRule_5_MultiAction.json";
	public static final String FORTH_REF_TEST_FILE_NAME = "StringReferenceRule_6_MultiActionWithMacros.json";
	public static final String FIRST_ACTION_FILE_NAME = "Test_Actions_001.json";
	public static final String SECOND_ACTION_FILE_NAME = "Test_Actions_002_With_Macros.json";

	/**
	 *
	 */
	@BeforeClass
	public static void beforeClass(){
		SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = true;
	}

	/**
	 * Can we read a several rules simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {
		SkRules rules = buildThings(FIRST_REF_TEST_FILE_NAME, SkRules.class, "rules");
		String json = JsonMapperHelper.beanToJsonPretty(rules);
		System.out.println("-------------------- xJSON ---------------------");
		System.out.println(json);
		System.out.println("-------------------- xJSON ---------------------");
	}

	/**
	 * Three rule reference test.  Just call the 3rd rule, and the other two are referenced.
	 */
	@Test
	public void testRefThreeRuleTest() {

		SkRules rules = buildThings(FIRST_REF_TEST_FILE_NAME, SkRules.class, "rules");
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.build();
		SkRuleRunner runner = master.getRuleRunner();
		try {
			// First, force a fail due to missing macro value.  We are missing setting
			//	of the macro 'NAME'
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
	 * Can we read in an Action file without Exception?
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
	 * Read in actions - setValue with Three rule action-ref test.  Just more actions.
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
	 * Read in actions - setValue with Three rule action-ref test.  Just more actions.
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
