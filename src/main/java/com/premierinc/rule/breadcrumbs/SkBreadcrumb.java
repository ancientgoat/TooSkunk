package com.premierinc.rule.breadcrumbs;

import org.saul.gradle.util.ExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SkBreadcrumb {

	private Logger log = LoggerFactory.getLogger(SkBreadcrumb.class);

	private String description;
	private Object result;
	private String error;
	private Exception exception;
	private SkBreadcrumbType breadcrumbType;

	public SkBreadcrumb(final String inDescription, final Object inResult, SkBreadcrumbType inBreadcrumbType) {
		this.description = inDescription;
		this.result = inResult;
		this.breadcrumbType = inBreadcrumbType;
		logThis();
	}

	public SkBreadcrumb(final String inError, Exception inException, SkBreadcrumbType inBreadcrumbType) {
		this.error = inError;
		this.exception = inException;
		this.breadcrumbType = inBreadcrumbType;
		logThis();
	}

	public SkBreadcrumbType getBreadcrumbType() {
		return breadcrumbType;
	}

	public String getDescription() {
		return description;
	}

	public Object getResult() {
		return result;
	}

	public Boolean getBooleanResult() {
		if (null != this.result && this.result instanceof Boolean) {
			return (Boolean) result;
		}
		return null;
	}

	public String getError() {
		return error;
	}

	public Exception getException() {
		return exception;
	}

	public String getExceptionString() {
		return ExceptionHelper.toString(exception);
	}

	private void logThis() {
		switch (this.breadcrumbType) {
		case BC_FATAL:
		case BC_ERROR:
			if (log.isErrorEnabled()) {
				log.error(getLogMessage());
			}
			break;
		case BC_WARN:
			if (log.isWarnEnabled()) {
				log.warn(getLogMessage());
			}
			break;
		case BC_INFO:
			if (log.isWarnEnabled()) {
				log.warn(getLogMessage());
			}
			break;
		case BC_DEBUG:
			if (log.isDebugEnabled()) {
				log.debug(getLogMessage());
			}
			break;
		case BC_TRACE:
			if (log.isTraceEnabled()) {
				log.trace(getLogMessage());
			}
			break;

		default:
			throw new IllegalArgumentException(
					String.format("BC Log Level '%s' is not implemented.", this.breadcrumbType));
		}
	}

	/*8

	 */
	public String getLogMessage() {
		// String description;
		// Object result;
		// String error;
		// Exception exception;

		StringBuilder sb = new StringBuilder();

		if (null != this.description || null != this.result || null != this.error || null != this.exception) {
			if (null != this.description) {
				sb.append(String.format("Description : %s", this.description));
			}
			if (null != this.result) {
				sb.append(String.format(" : Result : %s", this.result));
			}
			if (null != this.error) {
				sb.append(String.format("Error : %s", this.error));
			}
			if (null != this.exception) {
				sb.append(String.format("\nException   : %s", this.exception.toString()));
				sb.append(String.format("\n%s", ExceptionHelper.toString(this.exception)));
			}
			return sb.toString();
		}
		return null;
	}
}
