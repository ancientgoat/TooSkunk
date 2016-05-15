package com.premierinc.rule.breadcrumbs;

import com.google.common.collect.Lists;
import java.util.List;

import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_ALERT;
import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_DEBUG;
import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_ERROR;
import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_FATAL;
import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_INFO;
import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_TRACE;
import static com.premierinc.rule.breadcrumbs.SkBreadcrumbType.BC_WARN;

/**
 *
 */
public class SkBreadcrumbs {

	private List<SkBreadcrumb> crumbs = Lists.newArrayList();

	private SkBreadcrumb lastCrumb;

	public List<SkBreadcrumb> getCrumbs() {
		return crumbs;
	}

	public void addCrumb(final SkBreadcrumb inCrumb) {
		crumbs.add(inCrumb);
		this.lastCrumb = inCrumb;
	}

	public void addAlertCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_ALERT);
		addCrumb(crumb);
	}

	public void addFatalCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_FATAL);
		addCrumb(crumb);
	}

	public void addErrorCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_ERROR);
		addCrumb(crumb);
	}

	public void addWarnCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_WARN);
		addCrumb(crumb);
	}

	public void addDebugCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_DEBUG);
		addCrumb(crumb);
	}

	public void addTraceCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_TRACE);
		addCrumb(crumb);
	}

	public void addAlertCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_ALERT);
		addCrumb(crumb);
	}

	public void addFatalCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_FATAL);
		addCrumb(crumb);
	}

	public void addErrorCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_ERROR);
		addCrumb(crumb);
	}

	public void addWarnCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_WARN);
		addCrumb(crumb);
	}

	public void addInfoCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_INFO);
		addCrumb(crumb);
	}

	public void addDebugCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_DEBUG);
		addCrumb(crumb);
	}

	public void addTraceCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_TRACE);
		addCrumb(crumb);
	}

	public Boolean getLastBooleanResult() {
		if (null != this.lastCrumb) {
			return this.lastCrumb.getBooleanResult();
		}
		return null;
	}
}
