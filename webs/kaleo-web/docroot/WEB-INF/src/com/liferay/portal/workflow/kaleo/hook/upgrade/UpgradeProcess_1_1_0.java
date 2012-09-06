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

package com.liferay.portal.workflow.kaleo.hook.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoAction;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoCondition;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoDefinition;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoLog;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoNode;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoNotification;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoTask;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoTimer;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeKaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.UpgradeWorkflowContext;

/**
 * @author Janghyun Kim
 * @author Michael C. Han
 */
public class UpgradeProcess_1_1_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 110;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeKaleoAction.class);
		upgrade(UpgradeKaleoCondition.class);
		upgrade(UpgradeKaleoDefinition.class);
		upgrade(UpgradeKaleoLog.class);
		upgrade(UpgradeKaleoNode.class);
		upgrade(UpgradeKaleoNotification.class);
		upgrade(UpgradeKaleoTask.class);
		upgrade(UpgradeKaleoTaskAssignment.class);
		upgrade(UpgradeKaleoTaskInstanceToken.class);
		upgrade(UpgradeKaleoTimer.class);
		upgrade(UpgradeKaleoTimerInstanceToken.class);
		upgrade(UpgradeWorkflowContext.class);

	}

}