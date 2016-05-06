package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.base.SkRule;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.expression.SkData;
import com.premierinc.rule.expression.SkExpressions;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class TestSimpleNumericRuleTest {

	private Logger log = LoggerFactory.getLogger(TestSimpleNumericRuleTest.class);

	public static final String ONE_RULE_FILE_NAME = "NumericOneRuleTest.json";
	public static final String ONE_RULE_FAIL_FILE_NAME = "NumericOneRuleTestFail.json";
	public static final String TWO_RULE_FILE_NAME = "NumericTwoRuleTest.json";
	public static final String ONE_DATA_FILE_NAME = "Test_Data_001.json";

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
		rule.run();
	}

	/**
	 * One rule test.
	 */
	@Test
	public void testSimpleOneRuleExecutionTest() {
		SkRuleBase rule = buildThings(ONE_RULE_FILE_NAME, SkRuleBase.class, "rule");
		SkRuleRunner runner = new SkRuleRunner();

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
		SkData data = buildThings(ONE_DATA_FILE_NAME, SkData.class, "data");

		SkRuleMaster master = new SkRuleMaster.Builder().addRule(rule).addData(data)
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
	 * Two rule test.
	 */
	@Test
	public void testSimpleTwoRuleExecutionTest() {
		SkRuleBase rule = buildThings(TWO_RULE_FILE_NAME, SkRuleBase.class, "rule");
		SkRuleRunner runner = new SkRuleRunner();
		runner.setValue("MILK", 2);
		rule.run(runner);
		runner.setValue("MILK", 9999);
		runner.runRule(rule);
	}
}
