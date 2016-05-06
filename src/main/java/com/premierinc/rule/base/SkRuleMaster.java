package com.premierinc.rule.base;

import com.premierinc.rule.action.SkAction;
import com.premierinc.rule.action.SkActions;
import com.premierinc.rule.commands.SkIf;
import com.premierinc.rule.expression.SkData;
import com.premierinc.rule.expression.SkExpression;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 *
 */
public class SkRuleMaster {

	private SkMasterStats stats;
	private DoubleStream expressions;

	/**
	 *
	 */
	private SkRuleMaster() {
	}

	/**
	 *
	 */
	public SkRuleRunner getRuleRunner() {
		SkRuleRunner runner = new SkRuleRunner();
		runner.setMaster(this);
		return runner;
	}

	/**
	 *
	 */
	public List<SkRuleBase> getRuleList() {
		return this.stats.getRuleList();
	}

	/**
	 *
	 */
	public SkIf findIfFromReference(String inRuleName) {
		return this.stats.findIfFromReference(inRuleName);
	}

	/**
	 *
	 */
	public SkRuleBase getRule(String inRuleName) {
		return this.stats.getRule(inRuleName);
	}

	/**
	 *
	 */
	public SkAction getAction(final String inActionName) {
		return this.stats.getAction(inActionName);
	}

	/**
	 *
	 */
	public boolean needToRunExpressions() {
		return this.stats.needToRunExpressions();
	}

	/**
	 *
	 */
	public List<SkExpression> getExpressions() {
		return this.stats.getExpressions();
	}

	/**
	 *
	 */
	public static class Builder {

		private SkRuleMaster master = new SkRuleMaster();
		private SkMasterStats.Builder statBuilder = new SkMasterStats.Builder();

		/**
		 *
		 */
		public Builder addRules(SkRules inRules) {
			this.statBuilder.addRules(inRules);
			return this;
		}

		/**
		 /**
		 *
		 */
		public Builder addRule(SkRuleBase inRule) {
			this.statBuilder.addRule(inRule);
			return this;
		}

		/**
		 *
		 */
		public Builder addActions(final SkActions inActions) {
			this.statBuilder.addActions(inActions);
			return this;
		}

		/**
		 *
		 */
		public Builder addData(final SkData inData) {
			this.statBuilder.addData(inData);
			return this;
		}

		/**
		 *
		 */
		public SkRuleMaster build() {
			SkMasterStats localStats = statBuilder.build();
			master.stats = localStats;
			return this.master;
		}
	}
}
