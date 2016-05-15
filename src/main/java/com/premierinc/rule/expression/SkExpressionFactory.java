package com.premierinc.rule.expression;

import com.google.common.collect.Lists;
import com.premierinc.rule.utils.SkChar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.premierinc.rule.utils.SkChar.isBadMacroChar;
import static com.premierinc.rule.utils.SkChar.isQuote;

/**
 *
 */
public class SkExpressionFactory {

	private static Logger log = LoggerFactory.getLogger(SkExpressionFactory.class);

	public static Boolean TURN_ON_EXPRESSION_PARSING = true;

	public static final Set<Character> NON_MACRO_CHARS = new HashSet<Character>() {{
		add('(');
		add('[');
	}};

	/**
	 * Attempt to turn this expression into a valid SpEL Stringed expression.  Find all (Macros)
	 * 	strings of characters that ...
	 * 		- Start with a letter
	 * 		- Contain only letters and digits
	 * 		- Is not followed by any one of '(', '['
	 */
	public static SkExpression parseExpression(final String inInputExpression) {

		StringBuilder sb = new StringBuilder();
		boolean haveMacro = false;
		boolean haveQuote = false;
		String macro = "";
		List<String> macroList = Lists.newArrayList();

		String spelExpressionString = inInputExpression;

		if (!spelExpressionString.startsWith("//")) {
			// do convert
			//~~  Commented out for now :
			if(TURN_ON_EXPRESSION_PARSING) {
				spelExpressionString = convertExpression(inInputExpression, sb, haveMacro, haveQuote, macro, macroList);
			}
		} else {
			// do NOT convert
		}

		if (log.isTraceEnabled()) {
			log.trace(String.format("Parsing Expression:\nOriginal: %s\nSpEL Exp: %s", inInputExpression,
					spelExpressionString));
		}
		return new SkExpression(macroList, inInputExpression, spelExpressionString);
	}

	/**
	 * Saved - but not used.
	 */
	private static String convertExpression(final String inInputExpression, final StringBuilder inSb,
			boolean inHaveMacro, boolean inHaveQuote, String inMacro, final List<String> inMacroList) {

		// Old style for loops have their uses.
		for (int i = 0; i < inInputExpression.length(); i++) {

			char c = inInputExpression.charAt(i);

			if (!inHaveMacro) {
				if (SkChar.isAlpha(c)) {
					inHaveMacro = true;
				}
			}

			if (inHaveMacro) {
				if (isBadMacroChar(c)) {
					inSb.append(inMacro);
					inMacro = "";
					inHaveMacro = false;
				}
			}

			if (isQuote(c)) {
				inHaveQuote = !inHaveQuote;
			}

			if (inHaveMacro && !inHaveQuote) {
				if (SkChar.isMacro(c)) {
					inMacro += (char) c;
				} else {

					// Must be the end of a macro - insures next char is not a 'nonMacroString'
					if (isNonMacro(inInputExpression, i) || !SkChar.isMacro(inMacro)) {
						// This - what we thought was a macro - is really a function.
						inSb.append(inMacro);
						inMacro = "";
						inHaveMacro = false;
					} else {
						// We now assume this is a Macro
						if (!inMacro.isEmpty()) {
							inMacro = inMacro.toUpperCase();
							inSb.append(String.format("['%s'] ", inMacro));
						}
						inMacroList.add(inMacro);
						inMacro = "";
						inHaveMacro = false;
					}
					inSb.append((char) c);
				}
			} else {
				inSb.append((char) c);
			}
		}

		if (inHaveMacro && 0 < inMacro.length()) {
			inMacro = inMacro.toUpperCase();
			inSb.append(String.format("['%s'] ", inMacro));
		}

		return inSb.toString();
	}

	/**
	 * Insure the macro we thought we just read in is not the start of a function, or
	 * 	some other non-macro indicator.
	 */
	private static boolean isNonMacro(String inInputExpression, int inIndex) {

		boolean foundBadMacroChar = false;

		for (int i = inIndex; i < inInputExpression.length(); i++) {
			char c = inInputExpression.charAt(i);
			//if (NON_MACRO_CHARS.contains(c)) {
			if (SkChar.isBadMacroChar(c)) {
				foundBadMacroChar = true;
			} else if (SkChar.isSpace(c)) {
				continue;
			}
			break;
		}
		return foundBadMacroChar;
	}
}
