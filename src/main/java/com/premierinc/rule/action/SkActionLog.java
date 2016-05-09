package com.premierinc.rule.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierinc.rule.action.enums.SkLogLevel;
import com.premierinc.rule.run.SkRuleRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Different action - write to standard java logging.
 */
public class SkActionLog extends SkAction {

	private Logger log = LoggerFactory.getLogger(SkActionLog.class);

	private String message;

	@JsonProperty("level")
	private SkLogLevel logLevel = SkLogLevel.INFO;

	public String getMessage() {
		return message;
	}

	public void setMessage(final String inMessage) {
		message = inMessage;
	}

	public SkLogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(final SkLogLevel inLogLevel) {
		logLevel = inLogLevel;
	}

	@Override
	public void run(SkRuleRunner inRunner) {

		// Is this a macro?
		String newMessage = (String) inRunner.getValue(this.message, this.message);
		// Does this have embedded macros in the format '${macro}'
		newMessage = inRunner.expandMacros(newMessage);

		switch (logLevel) {
		case ERROR:
			if (log.isErrorEnabled())
				log.error(newMessage);
			break;
		case WARN:
			if (log.isWarnEnabled())
				log.warn(newMessage);
			break;
		case INFO:
			if (log.isInfoEnabled())
				log.info(newMessage);
			break;
		case DEBUG:
			if (log.isDebugEnabled())
				log.debug(newMessage);
			break;
		case TRACE:
			if (log.isTraceEnabled())
				log.trace(newMessage);
			break;
		default:
			throw new IllegalArgumentException(String.format("Not implemented log level '%s'.", logLevel));
		}
	}
}
