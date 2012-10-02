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

package com.liferay.googlemaps.hook.upgrade;

import com.liferay.googlemaps.hook.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.portal.kernel.test.plugins.LiferayPluginsIntegrationJUnitRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayPluginsIntegrationJUnitRunner.class)
public class UpgradeProcess_1_0_0IntegrationTest {

	@Test
	public void testDoUpgrade() throws Exception {
		_upgradeProcess = new UpgradeProcess_1_0_0();

		Assert.assertEquals(100, _upgradeProcess.getThreshold());

		_upgradeProcess.upgrade(UpgradePortletPreferences.class);

		Assert.assertTrue(true);
	}

	private UpgradeProcess_1_0_0 _upgradeProcess;

}