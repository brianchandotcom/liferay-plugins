/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.vldap.server.directory.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.vldap.server.directory.ldap.CompanyDirectory;
import com.liferay.vldap.server.directory.FilterConstraint;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;

/**
 * @author Jonathan Potter
 */
public class CompanyBuilder extends DirectoryBuilder {

	@Override
	public List<LdapDirectory> buildThisLevel(
		Set<FilterConstraint> constraints, SearchBase base)
		throws Exception {

		List<Company> companies = new ArrayList<Company>();
		
		if (constraints == null) {
			// No constraints so find all companies
			companies = CompanyLocalServiceUtil.getCompanies();
		}
		else {
			for (FilterConstraint constraint : constraints) {
				if (!isValidConstraint(constraint)) {
					continue;
				}
				
				String companyWebId = constraint.getValue("ou");
				
				if (companyWebId == null) {
					companies.addAll(
						CompanyLocalServiceUtil.getCompanies(false));
				}
				else {
					companies.add(CompanyLocalServiceUtil
						.getCompanyByWebId(companyWebId));
				}
			}
		}
		
		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();
		
		for (Company c: companies) {
			directories.add(new CompanyDirectory(base.getTop(), c));
		}
		
		return directories;
	}
	
	@Override
	public boolean isValidAttribute (String attribute, String value) {
		if (attribute.equalsIgnoreCase("objectClass")) {
			if (value.equalsIgnoreCase("organizationalUnit")
				|| value.equalsIgnoreCase("top")
				|| value.equalsIgnoreCase("*")) {

				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("ou")) {
			return true;
		}


		return false;
	}
	
}