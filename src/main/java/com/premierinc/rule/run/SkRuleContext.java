package com.premierinc.rule.run;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.expression.SkExpression;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 */
public class SkRuleContext {

	private Logger log = LoggerFactory.getLogger(SkRuleContext.class);

	private Map<String, Object> internalMap = Collections.synchronizedMap(Maps.newHashMap());

	private SpelParserConfiguration spelConfig;
	private ExpressionParser parser;
	private SkRuleRunner runner;

	/**
	 *
	 */
	SkRuleContext() {
		init(null);
	}

	/**
	 *
	 */
	SkRuleContext(SpelCompilerMode inSpelCompilerMode) {
		init(inSpelCompilerMode);
	}

	/**
	 *
	 */
	private void init(SpelCompilerMode inSpelCompilerMode) {

		SpelCompilerMode mode = (null != inSpelCompilerMode ? inSpelCompilerMode : SpelCompilerMode.MIXED);

		this.spelConfig = new SpelParserConfiguration(mode, this.getClass()
				.getClassLoader());

		parser = new SpelExpressionParser(this.spelConfig);
	}

	/**
	 *
	 */
	ExpressionParser getParser() {
		return parser;
	}

	/**
	 *
	 */
	SpelParserConfiguration getSpelConfig() {
		return spelConfig;
	}

	/**
	 *
	 */
	public Map<String, Object> getInternalMap() {
		return internalMap;
	}

	/**
	 *
	 */
	void setRunner(final SkRuleRunner inRunner) {
		this.runner = inRunner;
	}

	/**
	 *
	 */
	void runRule(final SkRuleBase inRule) {
		this.runner.runRule(inRule);
	}

	/**
	 *
	 */
	void setValue(String inKey, Object inValue) {
		internalMap.put(inKey, inValue);
	}

	/**
	 *
	 */
	Object getValue(String inKey) {
		if (null != inKey) {
			String s = inKey.replace("['", "")
					.replace("']", "");
			return internalMap.get(s);
		}
		return null;
	}

	/**
	 *
	 */
	boolean containsMacroKey(String inKey) {
		return this.internalMap.containsKey(inKey);
	}

	/**
	 *
	 */
	public Object setValue(final SkExpression inExpression) {
		String expressionString = inExpression.getExpressionString();
		if (null == expressionString || 0 == expressionString.length()) {
			this.runner.addErrorCrumb(String.format("%s : Missing Expression", expressionString));
			throw new IllegalArgumentException("ExpressionString can not be null, or of length zero (0).");
		}
		try {
			Object answer = runExpression(inExpression);
			this.runner.addDebugCrumb(
					String.format("%s => %s", expressionString, findExpressionMacrosWithValues(expressionString),
							answer));
			return answer;
		} catch (Exception e) {
			throw new IllegalArgumentException(inExpression.dumpToString(), e);
		}
	}

	/**
	 *
	 */
	public Object getValue(SkExpression inExpression) {
		//
		StringBuilder sb = new StringBuilder("\n");

		System.out.println("EXPRESSION MACRO LIST : " + inExpression.getMacroList());
		System.out.println("RUNNER SET MACRO LIST : " + this.internalMap);

		inExpression.getMacroList()
				.forEach(macro -> {
					if (!containsMacroKey(macro) && !SkGlobalContext.containsMacroKey(macro)) {
						sb.append(String.format("Missing macro '%s'\n", macro));
					}
				});

		if (1 < sb.length()) {
			throw new IllegalArgumentException(sb.toString());
		}
		return runExpression(inExpression);
	}

	/**
	 *
	 */
	private Object runExpression(SkExpression inExpression) {
		String expressionString = inExpression.getExpressionString();
		this.runner.addDebugCrumb("Before", inExpression, findExpressionMacrosWithValues(expressionString));
		Expression exp = this.parser.parseExpression(expressionString);

		// Use a combined Global and local map.
		Map<String, Object> globalMap = SkGlobalContext.getGlobalMap();
		Map<String, Object> combinedMap = Maps.newHashMap();
		combinedMap.putAll(globalMap);
		combinedMap.putAll(internalMap);

		// Run the expression
		Object answer = null;
		try {
			answer = exp.getValue(combinedMap);
		} catch (Exception e) {
			IllegalArgumentException e2 = new IllegalArgumentException(String.format("\"%s\"", expressionString), e);
			this.runner.addFatalCrumb(inExpression, e2);
			throw e2;
		}
		// Move all objects that belong in the local map, back to the local map.
		combinedMap.keySet()
				.forEach(k -> {
					if (!globalMap.containsKey(k) || internalMap.containsKey(k)) {
						internalMap.put(k, combinedMap.get(k));
					}
				});
		// Move all objects that belong in the global map, back to the local map.
		combinedMap.keySet()
				.forEach(k -> {
					// Do not update an item found in both internal and global maps
					// 	only the local map changes.
					if (!internalMap.containsKey(k)) {
						globalMap.put(k, combinedMap.get(k));
					}
				});
		String msg = String.format("After: %s", findExpressionMacrosWithValues(expressionString));
		this.runner.addDebugCrumb(msg, inExpression, answer);
		return answer;
	}

	/**
	 *
	 */
	public String expandMacros(final String inMessage) {
		if (null == inMessage) {
			return inMessage;
		}
		Pattern pattern = Pattern.compile("\\$\\{.*?\\}");

		Matcher matcher = pattern.matcher(inMessage);

		StringBuilder sb = new StringBuilder();

		boolean b = matcher.find();
		int prevEnd = 0;

		if (log.isTraceEnabled()) {
			log.trace("Replacing macros (RM)");
		}

		while (b) {
			int start = matcher.start();
			int end = matcher.end();
			String macro = inMessage.substring(start + 2, end - 1);
			sb.append(inMessage.substring(prevEnd, start));

			// Find macro value and replace it into the string
			macro = macro.toUpperCase();
			Object value = this.internalMap.get(macro);
			if (null == value) {
				throw new IllegalArgumentException(String.format("no value for macro '%s'.", macro));
			}
			sb.append(value);
			prevEnd = end;
			if (log.isTraceEnabled()) {
				log.trace("RM: Start/End : " + start + " / " + end + " : " + macro + "'");
			}
			b = matcher.find();
		}
		if (prevEnd < inMessage.length()) {
			sb.append(inMessage.substring(prevEnd));
		}

		return sb.toString();
	}

	/**
	 *
	 */
	public String findExpressionMacrosWithValues(final String inMessage) {

		Set<String> set = findExpressionMacros(inMessage);
		List<String> returnList = Lists.newArrayList();

		set.forEach(lst -> {
			returnList.add(String.format("%s = %s", lst, getValue(lst)));
		});

		return String.join(", ", returnList);
	}

	/**
	 *
	 */
	public Set<String> findExpressionMacros(final String inMessage) {

		Set<String> expressionMacros = Sets.newHashSet();

		if (null == inMessage) {
			return expressionMacros;
		}
		Pattern pattern = Pattern.compile("\\[\\'.*?\\'\\]");

		Matcher matcher = pattern.matcher(inMessage);

		StringBuilder sb = new StringBuilder();

		boolean b = matcher.find();
		int prevEnd = 0;

		if (log.isTraceEnabled()) {
			log.trace("Replacing macros (RM)");
		}

		while (b) {
			int start = matcher.start();
			int end = matcher.end();
			String macro = inMessage.substring(start + 2, end - 2);

			// Find macro value and replace it into the string
			macro = macro.toUpperCase();
			expressionMacros.add(macro);

			if (log.isTraceEnabled()) {
				log.trace("RM: Start/End : " + start + " / " + end + " : " + macro + "'");
			}
			b = matcher.find();
		}
		return expressionMacros;
	}
}
