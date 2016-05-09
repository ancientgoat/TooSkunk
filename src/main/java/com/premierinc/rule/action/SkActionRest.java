package com.premierinc.rule.action;

import com.google.common.collect.Lists;
import com.premierinc.rule.run.SkGlobalContext;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Make a rest call and return some data - very crude.
 */
public class SkActionRest extends SkAction {

	private Logger log = LoggerFactory.getLogger(SkActionRest.class);

	private String host;
	private String port;
	private String prefix;
	private String tablename;
	private String where;

	@Override
	public void run(SkRuleRunner inRunner) {

		if (log.isDebugEnabled()) {
			log.debug(String.format("Before :\n%s", dumpToString()));
		}

		host = (String) inRunner.getValue(host, host);
		port = (String) inRunner.getValue(port, port);
		prefix = (String) inRunner.getValue(prefix, prefix);
		tablename = (String) inRunner.getValue(tablename, tablename);
		where = (String) inRunner.getValue(where, where);

		if (log.isDebugEnabled()) {
			log.debug(String.format("After :\n%s", dumpToString()));
		}

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

	public String dumpToString() {
		return new StringBuilder()//
				.append(String.format("host      : %s \n", host))
				.append(String.format("port      : %s \n", port))
				.append(String.format("prefix    : %s \n", prefix))
				.append(String.format("tablename : %s \n", tablename))
				.append(String.format("where     : %s \n", where))
				.toString();

	}
}
