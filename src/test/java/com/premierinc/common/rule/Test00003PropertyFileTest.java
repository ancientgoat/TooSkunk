package com.premierinc.common.rule;

import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.run.SkGlobalContext;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class Test00003PropertyFileTest {

	private Logger log = LoggerFactory.getLogger(Test00003PropertyFileTest.class);

	public static final String PROPERTY_FILE_TEST_NAME = "PropertyFile_Test.json";

	/**
	 * Property File Test
	 */
	@Test
	public void testPropertyFileTest() {
		SkActions actions = buildThings(PROPERTY_FILE_TEST_NAME, SkActions.class, "actions");
		SkRuleMaster master = new SkRuleMaster.Builder().addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		Map<String, Object> internalMap = SkGlobalContext.getGlobalMap();

		if (log.isInfoEnabled()) {
			log.info(internalMap.toString());
		}
		System.out.println(internalMap.toString());

		Assert.assertEquals("We Expected three items in the property file.", 3, internalMap.size());
	}
}
