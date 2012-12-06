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

package com.liferay.opensocial.shindig.oauth;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.liferay.opensocial.shindig.config.LiferayJsonContainerConfig;

import org.apache.shindig.common.crypto.BlobCrypter;
import org.apache.shindig.config.ContainerConfig;
import org.apache.shindig.gadgets.LockedDomainService;
import org.apache.shindig.gadgets.http.HttpRequest;
import org.apache.shindig.gadgets.oauth.GadgetOAuthCallbackGenerator;
import org.apache.shindig.gadgets.oauth.OAuthFetcherConfig;
import org.apache.shindig.gadgets.oauth.OAuthRequestException;
import org.apache.shindig.gadgets.oauth.OAuthResponseParams;
import org.apache.shindig.gadgets.process.Processor;
import org.apache.shindig.gadgets.uri.OAuthUriManager;

/**
 * @author Raymond Augé
 */
public class LiferayOAuthCallbackGenerator
	extends GadgetOAuthCallbackGenerator {

	@Inject
	public LiferayOAuthCallbackGenerator(
		ContainerConfig containerConfig, Processor processor,
		LockedDomainService lockedDomainService,
		OAuthUriManager oauthUriManager,
		@Named(OAuthFetcherConfig.OAUTH_STATE_CRYPTER)
			BlobCrypter stateCrypter) {

		super(processor, lockedDomainService, oauthUriManager, stateCrypter);

		_containerConfig = containerConfig;
	}

	@Override
	public String generateCallback(
			OAuthFetcherConfig fetcherConfig, String baseCallback,
			HttpRequest request, OAuthResponseParams responseParams)
		throws OAuthRequestException {

		if (_containerConfig instanceof LiferayJsonContainerConfig) {
			LiferayJsonContainerConfig liferayContainerConfig =
				(LiferayJsonContainerConfig)_containerConfig;

			baseCallback = liferayContainerConfig.replaceTokens(baseCallback);
		}

		return super.generateCallback(
			fetcherConfig, baseCallback, request, responseParams);
	}

	protected final ContainerConfig _containerConfig;

}