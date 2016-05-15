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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.common.rule.TstFileHelper.buildThingsFromDirectory;

/**
 *
 */
public class Test_00100_AKI_Adult_RuleTest {

	private Logger log = LoggerFactory.getLogger(Test_00100_AKI_Adult_RuleTest.class);

	public static final String AKI_DIR = "/aki/";

	/**
	 * Can we read and run simple JSON without Exception?
	 */
	@Test
	public void testOutputJsonSimpleRuleTest() {

		SkExpressionFactory.TURN_ON_EXPRESSION_PARSING = false;

		// SkRules rules = buildThings(AKI_ONE, SkRules.class, "rules");
		SkRules rules = buildThingsFromDirectory(AKI_DIR);

		// /////////////////////////////////////////////////////////////////////
		// This valueMap grows with each test.
		// /////////////////////////////////////////////////////////////////////
		Map<String, Object> valueMap = Maps.newHashMap();
		runNoNewResult(rules, valueMap);
		runAdultNoAlert(rules, valueMap);
		runAdultStage3_Elevated(rules, valueMap);
		runAdultStage3_300_Increase(rules, valueMap);
		runAdultStage2_200_Increase(rules, valueMap);
		runAdultStage1_RapidRise(rules, valueMap);
		runAdultStage1_50_Increase(rules, valueMap);
	}

	/**
	 *
	 */
	private void runAdultStage1_50_Increase(final SkRules inRules, final Map<String, Object> inValueMap) {
		//Expecting something like - "Stage 1 AKI : based on 50% increase above reference SCr"
		inValueMap.put("SCR.N.SAVED", 1.9);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N.SAVED", true);

		SkBreadcrumbs breadcrumbs = runTest(inRules, inValueMap);
		SkBreadcrumb lastBreadcrumb = breadcrumbs.getLastCrumb();
		Assert.assertTrue("should have an alert", SkBreadcrumbType.BC_ALERT == lastBreadcrumb.getBreadcrumbType());

		String upperDesc = lastBreadcrumb.getDescription()
				.toUpperCase();

		Assert.assertTrue("should have an alert stage 1", upperDesc.contains("STAGE 1"));
		Assert.assertTrue("should have an alert stage 1 Rapid Rise", upperDesc.contains("RAPID RISE"));

	}


	/**
	 *
	 */
	private void runAdultStage1_RapidRise(final SkRules inRules, final Map<String, Object> inValueMap) {
		//Expecting something like - "Stage 1 AKI : based on rapid rise in SCr"
		inValueMap.put("SCR.N.SAVED", 1.5);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N.SAVED", true);

		SkBreadcrumbs breadcrumbs = runTest(inRules, inValueMap);
		SkBreadcrumb lastBreadcrumb = breadcrumbs.getLastCrumb();
		Assert.assertTrue("should have an alert", SkBreadcrumbType.BC_ALERT == lastBreadcrumb.getBreadcrumbType());

		String upperDesc = lastBreadcrumb.getDescription()
				.toUpperCase();

		Assert.assertTrue("should have an alert stage 1", upperDesc.contains("STAGE 1"));
		Assert.assertTrue("should have an alert stage 1 Rapid Rise", upperDesc.contains("RAPID RISE"));
	}

	/**
	 *
	 */
	private void runAdultStage2_200_Increase(final SkRules inRules, final Map<String, Object> inValueMap) {
		//Expecting something like - "Stage 2 AKI : based on 200% increase above reference SCr"
		inValueMap.put("SCR.N.SAVED", 2.5);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N.SAVED", false);

		SkBreadcrumbs breadcrumbs = runTest(inRules, inValueMap);
		SkBreadcrumb lastBreadcrumb = breadcrumbs.getLastCrumb();
		Assert.assertTrue("should have an alert", SkBreadcrumbType.BC_ALERT == lastBreadcrumb.getBreadcrumbType());

		String upperDesc = lastBreadcrumb.getDescription()
				.toUpperCase();

		Assert.assertTrue("should have an alert stage 2", upperDesc.contains("STAGE 2"));
		Assert.assertTrue("should have an alert stage 2 200% increase", upperDesc.contains("200% INCREASE"));
	}

	/**
	 *
	 */
	private void runAdultStage3_300_Increase(final SkRules inRules, final Map<String, Object> inValueMap) {
		//Expecting something like - "Stage 3 AKI : based on 300% increase above reference SCr"
		inValueMap.put("SCR.N.SAVED", 3.9);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N.SAVED", false);

		inValueMap.put("PATIENT.AGE", 21);
		inValueMap.put("PATIENT.BLACK", false);
		inValueMap.put("PATIENT.FEMALE", false);

		SkBreadcrumbs breadcrumbs = runTest(inRules, inValueMap);
		SkBreadcrumb lastBreadcrumb = breadcrumbs.getLastCrumb();
		Assert.assertTrue("should have an alert", SkBreadcrumbType.BC_ALERT == lastBreadcrumb.getBreadcrumbType());

		String upperDesc = lastBreadcrumb.getDescription()
				.toUpperCase();

		Assert.assertTrue("should have an alert stage 3", upperDesc.contains("STAGE 3"));
		Assert.assertTrue("should have an alert stage 3 300% increase", upperDesc.contains("300% INCREASE"));
	}

	/**
	 *
	 */
	private void runAdultStage3_Elevated(final SkRules inRules, final Map<String, Object> inValueMap) {
		//Expecting something like - "Stage 3 AKI : based on elevated SCr"

		inValueMap.put("SCR.N.SAVED", 4.1);
		inValueMap.put("SCR.N-1.48HOURS.FROM.SCR.N.SAVED", true);

		SkBreadcrumbs breadcrumbs = runTest(inRules, inValueMap);
		SkBreadcrumb lastBreadcrumb = breadcrumbs.getLastCrumb();
		Assert.assertTrue("should have an alert", SkBreadcrumbType.BC_ALERT == lastBreadcrumb.getBreadcrumbType());

		String upperDesc = lastBreadcrumb.getDescription()
				.toUpperCase();

		Assert.assertTrue("should have an alert stage 3", upperDesc.contains("STAGE 3"));
		Assert.assertTrue("should have an alert stage 3 elevated", upperDesc.contains("ELEVATED"));
	}

	/**
	 *
	 */
	private void runNoNewResult(SkRules rules, Map<String, Object> valueMap) {

		// SCR.NEW.RESULT
		SkBreadcrumb lastBreadcrumb = runTest(rules, valueMap).getLastCrumb();

		SkExpression expression = lastBreadcrumb.getExpression();
		//System.out.println("===================================");
		//System.out.println(expression.dumpToString());
		//System.out.println("===================================");
		String expressionString = expression.getExpressionString();
		Assert.assertTrue("Should have null PATIENT.AGE & SCR.NEW.RESULT", false == lastBreadcrumb.getBooleanResult());
		Assert.assertTrue("Should have null PATIENT.AGE & SCR.NEW.RESULT",
				expressionString.contains("null != ['PATIENT.AGE'] && null != ['SCR.NEW.RESULT'"));
	}

	/**
	 *
	 */
	private void runAdultNoAlert(SkRules rules, Map<String, Object> valueMap) {

		// SCR.NEW.RESULT
		valueMap.put("PATIENT.AGE", 97);
		valueMap.put("PATIENT.BLACK", true);
		valueMap.put("PATIENT.FEMALE", true);

		valueMap.put("SCR.NEW.RESULT", true);
		valueMap.put("SCR.N.SAVED", 0);
		valueMap.put("SCR.N-1.SAVED", 0);
		valueMap.put("SCR.ICU.SAVED", 0);
		valueMap.put("SCR.ADMIN.SAVED", 0);

		SkBreadcrumb lastBreadcrumb = runTest(rules, valueMap).getLastCrumb();
		Assert.assertTrue("Should NOT have an alert", SkBreadcrumbType.BC_ALERT != lastBreadcrumb.getBreadcrumbType());
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

		System.out.println("======================= Internal Map =======================");
		System.out.println(runner.getRuleContext()
				.getInternalMap());
		System.out.println("======================= Internal Map =======================");

		runner.runRuleRef("AKI_RULE_START_EVALUATION");
		return runner.getBreadcrumbs();
	}
}
