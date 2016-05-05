package com.premierinc.rule.expression;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.L;

/**
 *
 */
public class SkExpression {

	private String expressionString;
	private Map<String, String> macroMap = Maps.newHashMap();

	public SkExpression(final List<String> inMacroList, String inExpressionString) {
		this.expressionString = inExpressionString;
		inMacroList.forEach(m -> macroMap.put(m, null));
	}

	public String getExpressionString() {
		return expressionString;
	}

	public List<String> getMacroList() {
		return Lists.newArrayList(this.macroMap.values());
	}
}
