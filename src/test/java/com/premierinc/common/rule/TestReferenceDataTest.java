package com.premierinc.common.rule;

import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class TestReferenceDataTest {

	private Logger log = LoggerFactory.getLogger(TestReferenceDataTest.class);

	public static final String FIRST_REF_TEST_FILE_NAME = "StringReferenceRule_3_Parter.json";

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
}
