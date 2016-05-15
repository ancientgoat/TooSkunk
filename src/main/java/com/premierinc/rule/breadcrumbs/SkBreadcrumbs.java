package com.premierinc.rule.breadcrumbs;

import com.google.common.collect.Lists;
import com.premierinc.rule.expression.SkExpression;
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

	public SkBreadcrumb addCrumb(final SkBreadcrumb inCrumb) {
		crumbs.add(inCrumb);
		this.lastCrumb = inCrumb;
		return inCrumb;
	}

	public SkBreadcrumb addAlertCrumb(final SkExpression inExpression, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inException, BC_ALERT);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addFatalCrumb(final SkExpression inExpression, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inException, BC_FATAL);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addErrorCrumb(final SkExpression inExpression, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inException, BC_ERROR);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addWarnCrumb(final SkExpression inExpression, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inException, BC_WARN);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addDebugCrumb(final SkExpression inExpression, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inException, BC_DEBUG);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addTraceCrumb(final SkExpression inExpression, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inException, BC_TRACE);
		addCrumb(crumb);
		return crumb;
	}
	/////////////////////////////////////////////////////////////////////////////////

	public SkBreadcrumb addAlertCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_ALERT);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addFatalCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_FATAL);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addErrorCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_ERROR);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addWarnCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_WARN);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addDebugCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_DEBUG);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addTraceCrumb(final String inError, Exception inException) {
		SkBreadcrumb crumb = new SkBreadcrumb(inError, inException, BC_TRACE);
		addCrumb(crumb);
		return crumb;
	}
	/////////////////////////////////////////////////////////////////////////////////

	public SkBreadcrumb addAlertCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_ALERT);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addFatalCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_FATAL);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addErrorCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_ERROR);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addWarnCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_WARN);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addInfoCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_INFO);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addDebugCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_DEBUG);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addTraceCrumb(final String inDescription, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDescription, inResult, BC_TRACE);
		addCrumb(crumb);
		return crumb;
	}
	/////////////////////////////////////////////////////////////////////////////////
	public SkBreadcrumb addAlertCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_ALERT);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addFatalCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_FATAL);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addErrorCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_ERROR);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addWarnCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_WARN);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addInfoCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_INFO);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addDebugCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_DEBUG);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addTraceCrumb(final SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inExpression, inResult, BC_TRACE);
		addCrumb(crumb);
		return crumb;
	}
	/////////////////////////////////////////////////////////////////////////////////
	public SkBreadcrumb addAlertCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_ALERT);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addFatalCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_FATAL);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addErrorCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_ERROR);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addWarnCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_WARN);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addInfoCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_INFO);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addDebugCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_DEBUG);
		addCrumb(crumb);
		return crumb;
	}

	public SkBreadcrumb addTraceCrumb(String inDesc, SkExpression inExpression, Object inResult) {
		SkBreadcrumb crumb = new SkBreadcrumb(inDesc, inExpression, inResult, BC_TRACE);
		addCrumb(crumb);
		return crumb;
	}

	public Boolean getLastBooleanResult() {
		if (null != this.lastCrumb) {
			return this.lastCrumb.getBooleanResult();
		}
		return null;
	}

	public SkBreadcrumb getLastCrumb() {
		return lastCrumb;
	}
}
