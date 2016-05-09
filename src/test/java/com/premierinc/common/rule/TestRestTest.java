package com.premierinc.common.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.common.JsonMapperHelper;
import com.premierinc.rule.run.SkRuleRunner;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
public class TestRestTest {

	private Logger log = LoggerFactory.getLogger(TestRestTest.class);

	public static final String REST_FILE_NAME = "Test_Actions_Rest_001.json";

	/**
	 * Three rule reference test.
	 */
	@Test
	public void testRefThreeRuleTest() {
		SkActions actions = buildThings(REST_FILE_NAME, SkActions.class, "actions");
		List<SkAction> actionList = actions.getActionList();
		SkRuleMaster master = new SkRuleMaster.Builder().addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();
		for (SkAction action : actionList) {
			action.execute(runner);
		}
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
