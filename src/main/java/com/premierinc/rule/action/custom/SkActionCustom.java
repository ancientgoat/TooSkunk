package com.premierinc.rule.action.custom;

import com.premierinc.rule.run.SkRuleRunner;
import java.util.Map;

/**
 * Any custom Action must implement this interface.
 */
public interface SkActionCustom<A extends SkActionCustom> {

	/**
	 *
	 */
	public void setMap(Map<String, Object> inMap);

	/**
	 *
	 */
	public void run(SkRuleRunner inRunner);

	/**
	 *
	 */
	A newInstance();
}
