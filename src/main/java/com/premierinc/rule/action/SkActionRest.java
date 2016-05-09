package com.premierinc.rule.action;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkRuleRunner;
import com.sun.istack.internal.logging.Logger;
import java.util.List;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.apache.naming.SelectorContext.prefix;

/**
 *
 */
public class SkActionRest extends SkAction {

	private Logger log = Logger.getLogger(SkActionRest.class);

	private String host;
	private String port;
	private String prefix;
	private String tablename;
	private String where;

	@Override
	public SkActionType getActionType() {
		return SkActionType.PRINT;
	}

	@Override
	public void execute(SkRuleRunner inRunner) {

		RestTemplate restTemplate = new RestTemplate();
		List<Map<String, Object>> list = Lists.newArrayList();

		String url = String.format("http://%s:%s/%s/%s", host, port, prefix, tablename);
		List<Map<String, Object>> returnList = restTemplate.getForObject(url, List.class);

		System.out.println(returnList);
		System.out.println(returnList.toArray());

	}

	public String getHost() {
		return host;
	}

	public void setHost(final String inHost) {
		host = inHost;
	}

	public String getPort() {
		return port;
	}

	public void setPort(final String inPort) {
		port = inPort;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(final String inPrefix) {
		prefix = inPrefix;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(final String inTablename) {
		tablename = inTablename;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(final String inWhere) {
		where = inWhere;
	}
}
