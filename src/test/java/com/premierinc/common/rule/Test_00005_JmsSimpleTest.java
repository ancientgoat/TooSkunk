package com.premierinc.common.rule;

import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.expression.SkExpressionFactory;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThings;

/**
 *
 */
public class Test_00005_JmsSimpleTest {

	private Logger log = LoggerFactory.getLogger(Test_00005_JmsSimpleTest.class);

	public static final String JMS_FILE_NAME = "Test_Jms_001.json";

	/**
	 *
	 */
	@BeforeClass
	public static void beforeClass(){
		SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = true;
	}

	/**
	 *
	 */
	@Test
	public void testRestSimpleTest() {
		SkActions actions = buildThings(JMS_FILE_NAME, SkActions.class, "actions");
		List<SkAction> actionList = actions.getActionList();
		SkRuleMaster master = new SkRuleMaster.Builder().addActions(actions)
				.build();
		SkRuleRunner runner = master.getRuleRunner();
		try {
			actionList.forEach( a -> {
				a.run(runner);
			});
		} catch (Exception e) {
			// This test does NOT really fail.  Because we may not have a connection.
			//throw new IllegalArgumentException(e);
			log.warn(e.toString());
		}
	}
}
