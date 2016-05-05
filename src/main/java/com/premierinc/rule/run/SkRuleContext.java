package com.premierinc.rule.run;

import com.google.common.collect.Maps;
import com.premierinc.rule.base.SkRuleBase;
import com.premierinc.rule.expression.SkExpression;
import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 */
public class SkRuleContext {

	private Map<String, Object> internalMap = Maps.newHashMap();

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
	Map<String, Object> getInternalMap() {
		return internalMap;
	}

	/**
	 *
	 */
	Object getValue(SkExpression inExpression) {
		Expression exp = this.parser.parseExpression(inExpression.getExpressionString());
		return exp.getValue(internalMap);
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
		return internalMap.get(inKey);
	}

	/**
	 *
	 */
	boolean containsMacroKey(String inKey) {
		return this.internalMap.containsKey(inKey);
	}

	public void setValue(final SkExpression inExpression) {
		String expressionString = inExpression.getExpressionString();
		if (null == expressionString) {
			throw new IllegalArgumentException("ExpressionString can not be null.");
		}
		System.out.println("__________________________");
		System.out.println(expressionString);
		System.out.println(expressionString);
		System.out.println(expressionString);
		System.out.println(expressionString);
		System.out.println("__________________________");
		Expression exp = this.parser.parseExpression(expressionString);
		Object value = exp.getValue(internalMap);
	}
}
