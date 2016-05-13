package com.premierinc.rule.action;

import com.premierinc.rule.run.SkGlobalContext;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Read ONE record from TooRest, via a named query, and place all
 * 	values into the global namespace that is used for expressions.
 */
public class SkActionDbGlobalRead extends SkActionRest {

	private Logger log = LoggerFactory.getLogger(SkActionDbGlobalRead.class);

	/**
	 *
	 * param inRunner
	 */
	@Override
	public void run(final SkRuleRunner inRunner) {
		String url = super.runRest(inRunner, "one");
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> returnMap = restTemplate.getForObject(url, Map.class);
		if (null != returnMap && 0 < returnMap.size())
			SkGlobalContext.setValues(returnMap);

		System.out.println(returnMap);
	}
}
