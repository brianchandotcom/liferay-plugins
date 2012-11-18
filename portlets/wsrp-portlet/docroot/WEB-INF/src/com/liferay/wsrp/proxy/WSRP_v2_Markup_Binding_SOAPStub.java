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

package com.liferay.wsrp.proxy;

import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;

/**
 * @author Michael Young
 */
public class WSRP_v2_Markup_Binding_SOAPStub
	extends oasis.names.tc.wsrp.v2.bind.WSRP_v2_Markup_Binding_SOAPStub
	implements Stub {

	public WSRP_v2_Markup_Binding_SOAPStub() throws AxisFault {
		super();
	}

	public WSRP_v2_Markup_Binding_SOAPStub(Service service) throws AxisFault {
		super(service);
	}

	public WSRP_v2_Markup_Binding_SOAPStub(
		URL endpointURL, Service service, String characterEncoding)
		throws AxisFault {

		super(endpointURL, service);

		_characterEncoding = characterEncoding;
	}

	@Override
	public Call _createCall() throws ServiceException {
		Call call = super._createCall();

		if (Validator.isNotNull(_characterEncoding)) {
			call.setProperty(
				SOAPMessage.CHARACTER_SET_ENCODING, _characterEncoding);
		}

		return call;
	}

	private String _characterEncoding;
}