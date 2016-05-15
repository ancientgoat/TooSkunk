package com.premierinc.rule.breadcrumbs;

import org.apache.log4j.Level;

/**
 *
 */
public enum SkBreadcrumbType {
	BC_FATAL(Level.FATAL), BC_ERROR(Level.ERROR), BC_WARN(Level.WARN), BC_INFO(Level.INFO), BC_DEBUG(Level.DEBUG),
	BC_TRACE(Level.TRACE), BC_ALERT(Level.FATAL);

	private Level log4jLevel;

	SkBreadcrumbType(Level inLevel) {
		this.log4jLevel = inLevel;
	}

	public Level getLoggerLevel() {
		return log4jLevel;
	}
}
