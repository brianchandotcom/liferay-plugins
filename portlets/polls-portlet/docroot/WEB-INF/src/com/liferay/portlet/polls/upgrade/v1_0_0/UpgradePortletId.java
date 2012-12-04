/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class UpgradePortletId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String[][] portletIdsArray = getPortletIdsArray();

		for (String[] portletIds : portletIdsArray) {
			String oldRootPortletId = portletIds[0];
			String newRootPortletId = portletIds[1];

			updateLayouts(oldRootPortletId, newRootPortletId);
			updatePortlet(oldRootPortletId, newRootPortletId);
			updatePortletPreferences(oldRootPortletId, newRootPortletId);
		}
	}

	protected List<Long> getPlids() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Long> layoutIds = new ArrayList<Long>();

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from Layout");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");

				layoutIds.add(plid);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layoutIds;
	}

	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				"25", "pollsportlet_WAR_pollsportlet"
			},
			new String[] {
				"59", "pollsdisplayportlet_WAR_pollsportlet"
			}
		};
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateLayout(
			long plid, String oldPortletId, String newPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select typeSettings from Layout where plid = " + plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayouts(String oldPortletId, String newPortletId)
		throws Exception {

		List<Long> plids = getPlids();

		for (long plid : plids) {
			updateLayout(plid, oldPortletId, newPortletId);
		}
	}

	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update Portlet set portletId = '" + newRootPortletId +
				"' where portletId = '" + oldRootPortletId + "'");
	}

	protected void updatePortletPreference(
			String portletPreferencesId, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set portletId = ? where " +
				"portletPreferencesId = " + portletPreferencesId);

			ps.setString(1, portletId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updatePortletPreferences(
			String oldPortletId, String newPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from PortletPreferences");

			rs = ps.executeQuery();

			while (rs.next()) {
				String portletId = rs.getString("portletId");
				String portletPreferencesId = rs.getString(
					"portletPreferencesId");

				if (portletId.contains(oldPortletId)) {
					portletId = StringUtil.replace(
						portletId, oldPortletId, newPortletId);

					updatePortletPreference(portletPreferencesId, portletId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}