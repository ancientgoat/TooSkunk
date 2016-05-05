package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.run.SkRuleContext;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

/**
 *
 */
public class TestSimpleNumericRuleTest {

	public static final String ONE_RULE_FILE_NAME = "NumericOneRuleTest.json";
	public static final String TWO_RULE_FILE_NAME = "NumericTwoRuleTest.json";

	/**
	 * Can we read simple JSON without Exception?
	 */
	@Test
	public void testSimpleRuleTest() {
		SkRuleBase rule = buildRunnerFromFile(ONE_RULE_FILE_NAME);
	}

	/**
	 * One rule test.
	 */
	@Test
	public void testSimpleOneRuleExecutionTest() {
		SkRuleBase rule = buildRunnerFromFile(ONE_RULE_FILE_NAME);
		SkRuleRunner runner = new SkRuleRunner();
		runner.setValue("MILK.QTY", 2);

		rule.run(runner);
	}

	/**
	 * Two rule test.
	 */
	@Test
	public void testSimpleTwoRuleExecutionTest() {
		SkRuleBase rule = buildRunnerFromFile(TWO_RULE_FILE_NAME);
		SkRuleRunner runner = new SkRuleRunner();
		runner.setValue("MILK", 2);
		rule.run(runner);
		runner.setValue("MILK", 9999);
		runner.runRule(rule);
	}

	/**
	 * Read an input file, and return a DecisionRunner.
	 */
	private SkRuleBase buildRunnerFromFile(final String filePath) {

		try {
			ObjectMapper objectMapper;
			ClassPathResource resource = new ClassPathResource(filePath);
			File file = resource.getFile();
			if (!file.exists()) {
				throw new IllegalArgumentException(String.format("File '%s' does NOT exist.", file.getAbsolutePath()));
			}

			objectMapper = JsonMapperHelper.newInstanceJson();
			SkRuleBase rule = objectMapper.readValue(file, SkRuleBase.class);
			return rule;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
