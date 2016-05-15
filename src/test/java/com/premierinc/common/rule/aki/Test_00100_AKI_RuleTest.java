package com.premierinc.common.rule.aki;

import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.Map;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThingsFromDirectory;

/**
 *
 */
public class Test_00100_AKI_RuleTest {

	private Logger log = LoggerFactory.getLogger(Test_00100_AKI_RuleTest.class);

	public static final String AKI_ONE = "aki/Test_AKI_PArt_001.rules.json";
	public static final String AKI_DIR = "/aki/";

	/**
	 * Can we read and run simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {
		// SkRules rules = buildThings(AKI_ONE, SkRules.class, "rules");
		SkRules rules = buildThingsFromDirectory(AKI_DIR);
		SkRuleMaster master = new SkRuleMaster.Builder().addRules(rules)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		runner.setGlobalValue("AKI_PROPERTY_FILE", "aki/AKI_001.properties");

		runner.setValue("AKI_PROPERTY_FILE", "/aki/AKI_001.properties");

		Map<String, Object> internalMap = runner.getRuleContext()
				.getInternalMap();
		System.out.println("------------------------------------");
		System.out.println(internalMap);
		System.out.println("------------------------------------");

		runner.runRuleRef("AKI_RULE_START_EVALUATION");
	}
}
