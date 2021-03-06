package com.premierinc.common.rule.aki;

import com.google.common.collect.Maps;
import com.premierinc.rule.base.SkRuleMaster;
import com.premierinc.rule.base.SkRules;
import com.premierinc.rule.breadcrumbs.SkBreadcrumb;
import com.premierinc.rule.breadcrumbs.SkBreadcrumbType;
import com.premierinc.rule.breadcrumbs.SkBreadcrumbs;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.expression.SkExpressionFactory;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThingsFromDirectory;

/**
 *
 */
public class Test_00100_AKI_Pediatric_RuleTest {

	private Logger log = LoggerFactory.getLogger(Test_00100_AKI_Pediatric_RuleTest.class);

	public static final String AKI_DIR = "/aki/";

	/**
	 *
	 */
	@BeforeClass
	public static void beforeClass() {
		SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = false;
	}

	/**
	 * Can we read and run simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {

		// SkRules rules = buildThings(AKI_ONE, SkRules.class, "rules");
		SkRules rules = buildThingsFromDirectory(AKI_DIR);

		// /////////////////////////////////////////////////////////////////////
		// This valueMap grows with each test.
		// /////////////////////////////////////////////////////////////////////
		Map<String, Object> valueMap = Maps.newHashMap();
		runNoNewResult(rules, valueMap);
		runPediatricNoAlert(rules, valueMap);
		runPediatricStage3_Elevated(rules, valueMap);
		runPediatricStage3_300_Increase(rules, valueMap);
		runPediatricStage3_Low_EGFR(rules, valueMap);
		runPediatricStage2_200_Increase(rules, valueMap);
		runPediatricStage1_RapidRise(rules, valueMap);
		runPediatricStage1_50_Increase(rules, valueMap);

	}

	/**
	 *
	 */
	private void runPediatricStage1_50_Increase(final SkRules inRules, final Map<String, Object> inValueMap) {
		inValueMap.put("SCR.N", 1.999);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N", false);
		_runTest(inRules, inValueMap, "Should have an alert stage 1 50% increase", "PEDIATRIC", "STAGE 1",
				"50% INCREASE");
	}

	/**
	 *
	 */
	private void runPediatricStage1_RapidRise(final SkRules inRules, final Map<String, Object> inValueMap) {
		inValueMap.put("SCR.N", 1.5);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N", true);
		_runTest(inRules, inValueMap, "Should have an alert stage 1 Rapid Rise", "PEDIATRIC", "STAGE 1", "RAPID RISE");
	}

	/**
	 *
	 */
	private void runPediatricStage2_200_Increase(final SkRules inRules, final Map<String, Object> inValueMap) {
		//Expecting something like - "Stage 2 AKI : based on 200% increase above reference SCr"
		inValueMap.put("SCR.N", 2.9);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N", false);
		inValueMap.put("PATIENT.HEIGHT.CM", 180);
		_runTest(inRules, inValueMap, "Should have an alert stage 2 200% Increase", "PEDIATRIC", "STAGE 2",
				"200% INCREASE");
	}

	/**
	 *
	 */
	private void runPediatricStage3_300_Increase(final SkRules inRules, final Map<String, Object> inValueMap) {
		inValueMap.put("SCR.N", 3.999);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N", false);

		inValueMap.put("PATIENT.BLACK", false);
		inValueMap.put("PATIENT.FEMALE", false);
		_runTest(inRules, inValueMap, "Should have an alert stage 3 300% Increase", "PEDIATRIC", "STAGE 3",
				"300% INCREASE");
	}

	/**
	 *
	 */
	private void runPediatricStage3_Elevated(final SkRules inRules, final Map<String, Object> inValueMap) {
		inValueMap.put("SCR.N", 4.1);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N", true);
		_runTest(inRules, inValueMap, "Should have an alert stage 3 Elevated", "PEDIATRIC", "STAGE 3", "ELEVATED");
	}

	/**
	 *
	 */
	private void runPediatricStage3_Low_EGFR(final SkRules inRules, final Map<String, Object> inValueMap) {
		inValueMap.put("SCR.N", 3.5);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N", false);

		inValueMap.put("PATIENT.BLACK", false);
		inValueMap.put("PATIENT.FEMALE", false);
		_runTest(inRules, inValueMap, "Should have an alert stage 3 Low eGFR", "PEDIATRIC", "STAGE 3", "LOW EGFR");
	}

	/**
	 *
	 */
	private void runNoNewResult(SkRules rules, Map<String, Object> valueMap) {

		// SCR.NEW.RESULT
		SkBreadcrumb lastBreadcrumb = runTest(rules, valueMap).getLastCrumb();

		SkExpression expression = lastBreadcrumb.getExpression();
		String expressionString = expression.getExpressionString();
		Assert.assertTrue("Should have null PATIENT.AGE & SCR.NEW.RESULT", false == lastBreadcrumb.getBooleanResult());
		Assert.assertTrue("Should have null PATIENT.AGE & SCR.NEW.RESULT",
				expressionString.contains("null != ['PATIENT.AGE'] && null != ['SCR.NEW.RESULT'"));
	}

	/**
	 *
	 */
	private void runPediatricNoAlert(SkRules rules, Map<String, Object> valueMap) {

		// SCR.NEW.RESULT
		valueMap.put("PATIENT.AGE", 13);
		valueMap.put("PATIENT.HEIGHT.CM", 132);

		valueMap.put("PATIENT.BLACK", true);
		valueMap.put("PATIENT.FEMALE", true);

		valueMap.put("SCR.NEW.RESULT", true);
		valueMap.put("SCR.N", 0);
		valueMap.put("SCR.N-1", 0);
		valueMap.put("SCR.ICU", 0);
		valueMap.put("SCR.ADMIN", 0);

		SkBreadcrumb lastBreadcrumb = runTest(rules, valueMap).getLastCrumb();
		Assert.assertTrue("Should NOT have an alert", SkBreadcrumbType.BC_ALERT != lastBreadcrumb.getBreadcrumbType());
	}

	/**
	 *
	 */
	private void _runTest(SkRules inRules, Map<String, Object> inValueMap, String inDesc,
			String... inRequiredDescriptions) {
		SkBreadcrumbs breadcrumbs = runTest(inRules, inValueMap);
		SkBreadcrumb lastBreadcrumb = breadcrumbs.getLastCrumb();
		Assert.assertTrue("Should have an alert", SkBreadcrumbType.BC_ALERT == lastBreadcrumb.getBreadcrumbType());

		String upperDesc = lastBreadcrumb.getDescription()
				.toUpperCase();

		String description = String.format("%s, not '%s'.", inDesc, upperDesc);

		for (String desc : inRequiredDescriptions) {
			Assert.assertTrue(description, upperDesc.contains(desc));
		}
	}

	/**
	 * Start a new Context each time, so we don't share the internalMap between iterations.
	 */
	private SkBreadcrumbs runTest(SkRules inRules, Map<String, Object> inMap) {

		SkRuleMaster master = new SkRuleMaster.Builder().addRules(inRules)
				.build();
		SkRuleRunner runner = master.getRuleRunner();

		Map<String, Object> internalMap = runner.getRuleContext()
				.getInternalMap();
		internalMap.putAll(inMap);

		runner.runRuleRef("AKI_RULE_START_EVALUATION");
		return runner.getBreadcrumbs();
	}
}
