package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.premierinc.common.rule.TestSimpleNumericRuleTest.ONE_RULE_FAIL_FILE_NAME;

/**
 *
 */
public class TestReferenceTest {

	public static final String FIRST_REF_TEST_FILE_NAME = "StringReferenceRule_3_Parter.json";

	/**
	 * Can we read simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {
		SkRules rules = buildRunnerFromFile(FIRST_REF_TEST_FILE_NAME);
		String json = JsonMapperHelper.beanToJsonPretty(rules);
		System.out.println("-------------------- xJSON ---------------------");
		System.out.println(json);
		System.out.println("-------------------- xJSON ---------------------");
		//rule.run();
	}

	/**
	 * One rule test.
	 */
	@Test
	public void testRefThreeRuleTest() {
		SkRules rules = buildRunnerFromFile(FIRST_REF_TEST_FILE_NAME);
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.build();
		SkRuleRunner runner = new SkRuleRunner();
		try {
			runner.setValue("THIS_MACRO_DOES_NOT_EXIST", "X");
			runner.runRules(master);
			Assert.fail("We should have thrown an error.");
		} catch (Exception e) {
			System.out.println(String.format("We expected this error : '%s'", e.toString()));
		}
		runner.setValue("nAME", "Fred");
		runner.runRule(master.getRule("COKE_RULE_00003"));
	}

	/**
	 * Read an input JSON into SkRules.  To insure everything is correct,
	 * 	take that SkRules object turn it back into a JSON string, and then
	 * 	read a second SkRules object using that intermediate JSON string.
	 *
	 * This insures we can serialize and deserialize the SkRules object properly.
	 */
	private SkRules buildRunnerFromFile(final String filePath) {
		try {
			ObjectMapper objectMapper;
			ClassPathResource resource = new ClassPathResource(filePath);
			File file = resource.getFile();
			if (!file.exists()) {
				throw new IllegalArgumentException(String.format("File '%s' does NOT exist.", file.getAbsolutePath()));
			}
			objectMapper = JsonMapperHelper.jsonMapper();
			SkRules rules = objectMapper.readValue(file, SkRules.class);

			// Now turn the bean to json and back to a second bean.
			// This part is only for the tests.
			// Add 'rules' as the root name.
			String json = JsonMapperHelper.beanToJsonString(rules, "rules");
			SkRules rules2 = objectMapper.readValue(json, SkRules.class);
			return rules2;

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
