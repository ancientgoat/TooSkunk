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

		// Old style for loops have their uses.
		for (int i = 0; i < inInputExpression.length(); i++) {

			char c = inInputExpression.charAt(i);

			if (!haveMacro) {
				if (SkChar.isAlpha(c)) {
					haveMacro = true;
				}
			}

			if (haveMacro) {
				if (isBadMacroChar(c)) {
					sb.append(macro);
					macro = "";
					haveMacro = false;
				}
			}

			if (isQuote(c)) {
				haveQuote = !haveQuote;
			}

			if (haveMacro && !haveQuote) {
				if (SkChar.isMacro(c)) {
					macro += (char) c;
				} else {

					// Must be the end of a macro - insures next char is not a 'nonMacroString'
					if (isNonMacro(inInputExpression, i)) {
						// This - what we thought was a macro - is really a function.
						sb.append(macro);
						macro = "";
						haveMacro = false;
					} else {
						// We now assume this is a Macro
						if (!macro.isEmpty()) {
							macro = macro.toUpperCase();
							sb.append(String.format("['%s'] ", macro));
						}
						macroList.add(macro);
						macro = "";
						haveMacro = false;
					}
				}
			} else {
				sb.append((char) c);
			}
		}

		if (haveMacro && 0 < macro.length()) {
			macro = macro.toUpperCase();
			sb.append(String.format("['%s'] ", macro));
		}

		String spelExpressionString = sb.toString();

		if (log.isTraceEnabled()) {
			log.trace(String.format("Parsing Expression:\nOriginal: %s\nSpEL Exp: %s", inInputExpression,
					spelExpressionString));
		}
		return new SkExpression(macroList, inInputExpression, spelExpressionString);
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
