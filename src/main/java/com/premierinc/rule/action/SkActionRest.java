package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.premierinc.rule.run.SkRuleRunner;
import java.util.List;
import java.util.Map;
import org.saul.gradle.datadefinition.helper.SkWhereCondition;
import org.saul.gradle.datadefinition.helper.SkWherePacket;
import org.saul.gradle.datadefinition.helper.SkWherePacketHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Make a rest call and return some data - very crude.
 */
public class SkActionRest extends SkAction {

	private Logger log = LoggerFactory.getLogger(SkActionRest.class);

	protected String host;
	protected String port;
	protected String prefix;
	protected String tablename;

	@JsonProperty("where")
	protected List<SkWherePacket> whereList;

	@Override
	public void run(SkRuleRunner inRunner) {

		String url = runRest(inRunner, "many");

		RestTemplate restTemplate = new RestTemplate();
		List<Map<String, Object>> returnList = restTemplate.getForObject(url, List.class);

		System.out.println(returnList);
		System.out.println(returnList.toArray());
	}

	/**
	 *
	 */
	protected String runRest(SkRuleRunner inRunner, String inOneOrMany) {

		if (log.isDebugEnabled()) {
			log.debug(String.format("Before :\n%s", dumpToString()));
		}

		host = (String) inRunner.getValue(host, host);
		port = (String) inRunner.getValue(port, port);
		prefix = (String) inRunner.getValue(prefix, prefix);
		tablename = (String) inRunner.getValue(tablename, tablename);
		String whereString = SkWherePacketHelper.buildWhereClause(adjustWhereList(inRunner));

		if (log.isDebugEnabled()) {
			log.debug(String.format("After :\n%s", dumpToString()));
		}

		List<Map<String, Object>> list = Lists.newArrayList();

		return String.format("http://%s:%s/%s/%s/%s%s", host, port, prefix, inOneOrMany, tablename, whereString);

	}

	public String getHost() {
		return host;
	}

	public void setHost(String inHost) {
		host = inHost;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String inPort) {
		port = inPort;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String inPrefix) {
		prefix = inPrefix;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String inTablename) {
		tablename = inTablename;
	}

	public List<SkWherePacket> getWhereList() {
		return whereList;
	}

	public void setWhereList(List<SkWherePacket> inWhereList) {
		whereList = inWhereList;
	}

	public void addWhere(String inName, Object inValue) {
		addWhere(inName, SkWhereCondition.EQ, inValue);
	}

	public void addWhere(String inName, SkWhereCondition inCondition, Object inValue) {
		whereList.add(new SkWherePacket(inName, inCondition, inValue));
	}

	public String dumpToString() {
		StringBuilder sb = new StringBuilder()//
				.append(String.format("host      : %s \n", host))
				.append(String.format("port      : %s \n", port))
				.append(String.format("prefix    : %s \n", prefix))
				.append(String.format("tablename : %s \n", tablename));

		int i = 0;
		for (SkWherePacket packet : whereList) {
			if (0 == i) {
				i++;
				sb.append(String.format("WHERE     : %s \n", packet.getSql()));
			} else
				sb.append(String.format("AND       : %s \n", packet.getSql()));
		}
		return sb.toString();
	}

	/**
	 *
	 */
	protected List<SkWherePacket> adjustWhereList(SkRuleRunner inRunner) {

		List<SkWherePacket> newList = Lists.newArrayList();

		for (SkWherePacket packet : this.whereList) {
			String name = packet.getName();
			Object value = packet.getValue();

			SkWherePacket.Builder builder = new SkWherePacket.Builder()//
					.setName((String) inRunner.getValue(name, name))
					.setCondition(packet.getCondition());

			if (null != value) {
				builder.setValue((Object) inRunner.getValue(value.toString(), value));
			}
			newList.add(builder.build());
		}
		return newList;
	}
}
