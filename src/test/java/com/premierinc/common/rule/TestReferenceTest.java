package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
public class TestReferenceTest {

	public static final String ONE_RULE_FILE_NAME = "NumericOneRuleTest.json";
	public static final String ONE_RULE_FAIL_FILE_NAME = "NumericOneRuleTestFail.json";
	public static final String TWO_RULE_FILE_NAME = "NumericTwoRuleTest.json";


	/**
	 * Can we read simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {
		SkRuleBase rule = buildRunnerFromFile(ONE_RULE_FILE_NAME);
		String json = JsonMapperHelper.beanToJson(rule);
		System.out.println("-------------------- JSON ---------------------");
		System.out.println(json);
		System.out.println("-------------------- JSON ---------------------");
		rule.run();
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
