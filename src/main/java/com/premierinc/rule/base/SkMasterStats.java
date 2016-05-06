package com.premierinc.rule.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.premierinc.rule.commands.SkCondition;
import com.premierinc.rule.commands.SkIf;
import com.premierinc.rule.expression.SkExpression;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class SkMasterStats {

	public static final String DEFAULT_RULE_NAME_PREFIX = "*!RULE!*-";

	/**
	 * Map of RuleName/Rule
	 */
	private Map<String, SkRuleBase> ruleNameMap = Maps.newHashMap();

	/**
	 * Map of Macro/Rule
	 */
	private Map<String, List<SkRuleBase>> macroRuleMap = Maps.newHashMap();

	/**
	 * Map of Rule/Macro
	 */
	private Map<String, List<String>> ruleMacroMap = Maps.newHashMap();

	/**
	 * Map of SkIf/Rule
	 */
	private Map<SkIf, SkRuleBase> ifRuleMap = Maps.newHashMap();

	/**
	 * Map of Macro/SkIf
	 */
	private Map<String, List<SkIf>> macroIfMap = Maps.newHashMap();

	/**
	 * List of rules, Order matters.
	 */
	private List<SkRuleBase> ruleList = Lists.newArrayList();

	/**
	 *
	 */
	private SkMasterStats() {
	}

	/**
	 *
	 */
	public SkRuleBase getRule(String inRuleName) {
		if (null != inRuleName) {
			SkRuleBase rule = this.ruleNameMap.get(inRuleName);
			if (null == rule) {
				throw new IllegalArgumentException(String.format("Rule name '%s', not found.", inRuleName));
			}
			return rule;
		} else {
			throw new IllegalArgumentException("Rule name can not be null");
		}
	}

	/**
	 *
	 */
	private void buildMaps() {
		clearMaps();

		AtomicInteger index = new AtomicInteger(0);
		for (final SkRuleBase rule : this.ruleList) {
			rule.setUp();
			mapsFromRule(index, rule);
		}
	}

	/**
	 *
	 */
	List<SkRuleBase> getRuleList() {
		return ruleList;
	}

	/**
	 *
	 */
	SkIf findIfFromReference(String inRuleName) {
		if (null != inRuleName) {
			SkRuleBase rule = this.ruleNameMap.get(inRuleName.toUpperCase());
			if (null != rule) {
				return rule.getSkIf();
			}
		}
		return null;
	}

	/**
	 *
	 */
	private void mapsFromRule(final AtomicInteger inIndex, final SkRuleBase rule) {

		// Name/Rule Map
		String name = getRuleNameForMaps(rule, inIndex);
		this.ruleNameMap.put(name, rule);

		// Macro/Rule Map
		// Rule/Macro Map
		// Macro/SkIf Map
		// SkIf/Rule Map
		List<SkIf> ifList = Lists.newArrayList();
		List<SkCondition> conditionList = rule.getConditionList();
		conditionList.stream()
				.filter(c -> c instanceof SkIf)
				.forEach(c -> ifList.add((SkIf) c));

		for (SkIf skIf : ifList) {
			this.ifRuleMap.put(skIf, rule);
			mapsFromRuleIfs(rule, name, skIf);
		}
	}

	/**
	 *
	 */
	private void mapsFromRuleIfs(final SkRuleBase rule, final String inName, final SkIf skIf) {
		// Macro/Rule Map
		// Rule/Macro Map
		// Macro/SkIf Map
		SkExpression exp = skIf.getSkExpression();

		if (null != exp) {
			List<String> macroList = exp.getMacroList();
			for (String macro : macroList) {

				// Macro/Rules Map
				List<SkRuleBase> rules = this.macroRuleMap.get(macro);
				if (null == rules) {
					rules = Lists.newArrayList();
				}
				rules.add(rule);
				this.macroRuleMap.put(macro, rules);

				// Rule/Macros Map
				List<String> macros = this.ruleMacroMap.get(inName);
				if (null == macros) {
					macros = Lists.newArrayList();
				}
				macros.add(macro);
				this.ruleMacroMap.put(inName, macros);

				// Macro/SkIf Map
				List<SkIf> listIfs = this.macroIfMap.get(macro);
				if (null == listIfs) {
					listIfs = Lists.newArrayList();
				}
				listIfs.add(skIf);
				this.macroIfMap.put(macro, listIfs);
			}
		}
	}

	/**
	 *
	 * @param inRule
	 * @param inIndex
	 * @return
	 */
	private String getRuleNameForMaps(final SkRuleBase inRule, AtomicInteger inIndex) {
		String name = inRule.getName();
		if (null == name || 0 == name.length()) {
			int i = inIndex.incrementAndGet();
			name = String.format("%s%9d", DEFAULT_RULE_NAME_PREFIX, i);
		}
		if (this.ruleNameMap.containsKey(name)) {
			throw new IllegalArgumentException(String.format("A rule with name '%s' already exists.", name));
		}
		return name;
	}

	/**
	 *
	 */
	private void clearMaps() {
		this.ruleNameMap.clear();
		this.macroRuleMap.clear();
		this.ifRuleMap.clear();
		this.macroIfMap.clear();
		this.ruleMacroMap.clear();
	}

	/**
	 *
	 */
	public static class Builder {

		private SkMasterStats master = new SkMasterStats();

		/**
		 *
		 */
		public Builder addRules(SkRules inRules) {
			this.master.ruleList.addAll(inRules.getRuleList());
			return this;
		}

		/**
		 /**
		 *
		 */
		public Builder addRule(SkRuleBase inRule) {
			this.master.ruleList.add(inRule);
			return this;
		}

		/**
		 *
		 */
		public SkMasterStats build() {
			this.master.buildMaps();
			return this.master;
		}
	}
}
