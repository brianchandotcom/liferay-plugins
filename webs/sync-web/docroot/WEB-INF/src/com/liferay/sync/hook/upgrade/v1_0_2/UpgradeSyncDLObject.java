/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.hook.upgrade.v1_0_2;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.sync.model.SyncConstants;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalServiceUtil;
import com.liferay.sync.service.SyncDLObjectLocalServiceUtil;

import java.util.List;

/**
 * @author Dennis Ju
 */
public class UpgradeSyncDLObject extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DynamicQuery dynamicQuery = SyncDLObjectLocalServiceUtil.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("event", SyncConstants.EVENT_TRASH));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("type", SyncConstants.TYPE_FOLDER));

		List<SyncDLObject> syncDLObjects =
			SyncDLObjectLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (SyncDLObject syncDLObject : syncDLObjects) {
			updateSyncDLObjects(syncDLObject);
		}
	}

	protected void updateSyncDLObjects(SyncDLObject parentSyncDLObject)
		throws Exception {

		DynamicQuery dynamicQuery = SyncDLObjectLocalServiceUtil.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.ne("event", parentSyncDLObject.getEvent()));

		String treePath = parentSyncDLObject.getTreePath();

		dynamicQuery.add(
			RestrictionsFactoryUtil.like("treePath", treePath + "%"));

		List<SyncDLObject> syncDLObjects =
			SyncDLObjectLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (SyncDLObject syncDLObject : syncDLObjects) {
			syncDLObject.setUserId(parentSyncDLObject.getUserId());
			syncDLObject.setUserName(parentSyncDLObject.getUserName());
			syncDLObject.setModifiedTime(parentSyncDLObject.getModifiedTime());
			syncDLObject.setEvent(parentSyncDLObject.getEvent());

			SyncDLObjectLocalServiceUtil.updateSyncDLObject(syncDLObject);

			String type = syncDLObject.getType();

			if (type.equals(SyncConstants.TYPE_FOLDER)) {
				continue;
			}

			try {
				SyncDLFileVersionDiffLocalServiceUtil.
					deleteSyncDLFileVersionDiffs(syncDLObject.getTypePK());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeSyncDLObject.class);

}