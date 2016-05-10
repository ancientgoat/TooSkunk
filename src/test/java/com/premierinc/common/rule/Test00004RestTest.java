package com.premierinc.common.rule;

import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class Test00004RestTest {

	private Logger log = LoggerFactory.getLogger(Test00004RestTest.class);

	public static final String REST_FILE_NAME = "Test_Actions_Rest_001.json";

	/**
	 * Three rule reference test.
	 */
	@Test
	public void testRestSimpleTest() {
		SkActions actions = buildThings(REST_FILE_NAME, SkActions.class, "actions");
		List<SkAction> actionList = actions.getActionList();
		SkRuleMaster master = new SkRuleMaster.Builder().addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();
		SkAction action = runner.getAction("ACTION_REST");
		try {
			action.run(runner);
		} catch (Exception e) {
			// This test does NOT really fail.  Because we may not have a connection.
			log.warn(e.toString());
		}
	}
}
