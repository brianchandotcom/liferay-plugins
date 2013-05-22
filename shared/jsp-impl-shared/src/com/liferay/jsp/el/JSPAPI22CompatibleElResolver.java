/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jsp.el;

import java.beans.FeatureDescriptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ELResolver;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class JSPAPI22CompatibleElResolver extends ELResolver {

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		if (base == null) {
			return String.class;
		}

		return null;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(
		ELContext context, Object base) {

		List<FeatureDescriptor> list = new ArrayList<FeatureDescriptor>();

		PageContext pageContext = (PageContext)context.getContext(
			JspContext.class);

		Enumeration<?> attributes = pageContext.getAttributeNamesInScope(
			PageContext.PAGE_SCOPE);

		while (attributes.hasMoreElements()) {
			String name = (String)attributes.nextElement();
			Object value = pageContext.getAttribute(
				name, PageContext.PAGE_SCOPE);

			FeatureDescriptor descriptor = getFeatureDescriptor(
				name, value, "page");

			list.add(descriptor);
		}

		attributes = pageContext.getAttributeNamesInScope(
			PageContext.REQUEST_SCOPE);

		while (attributes.hasMoreElements()) {
			String name = (String)attributes.nextElement();

			Object value = pageContext.getAttribute(
				name, PageContext.REQUEST_SCOPE);

			FeatureDescriptor descriptor = getFeatureDescriptor(
				name, value, "request");

			list.add(descriptor);
		}

		attributes = pageContext.getAttributeNamesInScope(
			PageContext.SESSION_SCOPE);

		while (attributes.hasMoreElements()) {
			String name = (String)attributes.nextElement();
			Object value = pageContext.getAttribute(
				name, PageContext.SESSION_SCOPE);

			FeatureDescriptor descriptor = getFeatureDescriptor(
				name, value, "session");

			list.add(descriptor);
		}

		attributes = pageContext.getAttributeNamesInScope(
			PageContext.APPLICATION_SCOPE);

		while (attributes.hasMoreElements()) {
			String name = (String)attributes.nextElement();
			Object value = pageContext.getAttribute(
				name, PageContext.APPLICATION_SCOPE);

			FeatureDescriptor descriptor = getFeatureDescriptor(
				name, value, "application");

			list.add(descriptor);
		}

		return list.iterator();
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);

			return Object.class;
		}

		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);

			if (property instanceof String) {
				String attribute = (String)property;
				PageContext ctxt = (PageContext)context.getContext(
					JspContext.class);

				return ctxt.findAttribute(attribute);
			}
		}

		return null;
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);
		}

		return false;
	}

	@Override
	public void setValue(
		ELContext context, Object base, Object property, Object value) {

		if (context == null) {
			throw new NullPointerException();
		}

		if (base != null) {
			return;
		}

		context.setPropertyResolved(true);

		if (property instanceof String) {
			PageContext pageContext = (PageContext)context.getContext(
				JspContext.class);

			String attribute = (String)property;

			if (pageContext.getAttribute(
					attribute, PageContext.REQUEST_SCOPE) != null) {

				pageContext.setAttribute(
					attribute, value, PageContext.REQUEST_SCOPE);
			}
			else if (pageContext.getAttribute(
						attribute, PageContext.SESSION_SCOPE) != null) {

				pageContext.setAttribute(
					attribute, value, PageContext.SESSION_SCOPE);
			}
			else if (pageContext.getAttribute(
						attribute, PageContext.APPLICATION_SCOPE) != null) {

				pageContext.setAttribute(
					attribute, value, PageContext.APPLICATION_SCOPE);
			}
			else {
				pageContext.setAttribute(
					attribute, value, PageContext.PAGE_SCOPE);
			}
		}
	}

	protected FeatureDescriptor getFeatureDescriptor(
		String name, Object value, String scopeName) {

		FeatureDescriptor descriptor = new FeatureDescriptor();
		descriptor.setName(name);
		descriptor.setDisplayName(name);
		descriptor.setShortDescription(scopeName.concat(" scope attribute"));
		descriptor.setExpert(false);
		descriptor.setHidden(false);
		descriptor.setPreferred(true);
		descriptor.setValue("type", value.getClass());
		descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);

		return descriptor;
	}

}