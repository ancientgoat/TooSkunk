package com.premierinc.rule.base;

import com.premierinc.rule.commands.SkIf;
import java.util.List;

/**
 *
 */
public class SkRuleMaster {

	private SkMasterStats stats;

	/**
	 *
	 */
	private SkRuleMaster() {
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
	public SkRuleBase getRule(String inRuleName){
		return this.stats.getRule(inRuleName);
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
		public SkRuleMaster build() {
			SkMasterStats localStats = statBuilder.build();
			master.stats = localStats;
			return this.master;
		}
	}
}
