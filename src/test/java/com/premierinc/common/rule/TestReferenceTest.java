package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
public class TestReferenceTest {

	private Logger log = LoggerFactory.getLogger(TestReferenceTest.class);

	public static final String FIRST_REF_TEST_FILE_NAME = "StringReferenceRule_3_Parter.json";
	public static final String SECOND_REF_TEST_FILE_NAME = "StringReferenceRule_4_WithOtherRefs.json";
	public static final String THIRD_REF_TEST_FILE_NAME = "StringReferenceRule_5_MultiAction.json";
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
		// rule.run();
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
			runner.runRules(master);
			Assert.fail("We should have thrown an error.");
		} catch (Exception e) {
			System.out.println(String.format("We expected this error : '%s'", e.toString()));
		}

		// Note: All macros are turned into UPPER case.
		runner.setValue("nAmE", "Fred");

		// Choose only one rule, from the three, to run.
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
	 * Read in actions - run with Three rule action-ref test.
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

		// Choose only one rule, from the three, to run.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read in actions - run with Three rule action-ref test.
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

		// Choose only one rule, from the three, to run.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read in actions - run with Three rule action-ref test.
	 */
	@Test
	public void testMultiActionWithMacrosRefRuleRefTest() {

		SkActions actions = buildThings(SECOND_ACTION_FILE_NAME, SkActions.class, "actions");
		SkRules rules = buildThings(THIRD_REF_TEST_FILE_NAME, SkRules.class, "rules");
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		// Note: All macros are turned into UPPER case.
		runner.setValue("nAmE", "Fred");

		// Choose only one rule, from the three, to run.
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read Actions from file.
	 */
	private <C> C buildThings(String filePath, Class inClazz, String inRootName) {
		try {
			ObjectMapper objectMapper;
			ClassPathResource resource = new ClassPathResource(filePath);
			File file = resource.getFile();
			if (!file.exists()) {
				throw new IllegalArgumentException(String.format("File '%s' does NOT exist.", file.getAbsolutePath()));
			}
			objectMapper = JsonMapperHelper.jsonMapper();
			C things = (C) objectMapper.readValue(file, inClazz);

			// Now turn the bean to json and back to a second bean.
			// This part is only for the tests.
			// Add 'actions' as the root name.
			String json = JsonMapperHelper.beanToJsonString(things, inRootName);
			C things2 = (C) objectMapper.readValue(json, inClazz);
			return things2;

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
