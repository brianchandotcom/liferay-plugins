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

package com.liferay.testpaypal.util;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.liferay.testpaypal.util.PortletPropsValues;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.PaymentHistory;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.QueryParameters;

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Douglas Wong
 */

public class PayPalUtil {

	public static void executeAccountPayment(String paymentId, String payerId)
		throws Exception {

		APIContext apiContext = getAPIContext();

		Payment payment = new Payment();

		payment.setId(paymentId);

		PaymentExecution paymentExecute = new PaymentExecution();

		paymentExecute.setPayerId(payerId);

		payment.execute(apiContext, paymentExecute);
	}

	public static Payment getAccountPayment(
			List<Item> items, String amountTotal, String currencyCode,
			String returnURL, String cancelURL)
		throws Exception {

		Payment payment = new Payment();

		payment.setIntent("sale");

		Payer payer = new Payer();

		payer.setPaymentMethod("paypal");

		payment.setPayer(payer);

		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setCancelUrl(cancelURL);
		redirectUrls.setReturnUrl(returnURL);

		payment.setRedirectUrls(redirectUrls);

		List<Transaction> transactions = getTransactions(
			"Liferay Purchase Through Account Payment", items, amountTotal,
			currencyCode);

		payment.setTransactions(transactions);

		APIContext apiContext = getAPIContext();

		return payment.create(apiContext);
	}

	public static String getApprovalURL(Payment payment)
		throws UnsupportedEncodingException {

		String redirectUrl = null;

		List<Links> linksList = payment.getLinks();

		for (Links links : linksList) {
			String rel = links.getRel();

			if (StringUtil.equalsIgnoreCase(rel, "approval_url")) {
				redirectUrl = URLDecoder.decode(links.getHref(), "UTF-8");

				break;
			}
		}

		return redirectUrl;
	}

	protected static APIContext getAPIContext() throws PayPalRESTException {
		OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(
			PortletPropsValues.PAYPAL_CLIENT_ID,
			PortletPropsValues.PAYPAL_CLIENT_SECRET);

		String accessToken = oAuthTokenCredential.getAccessToken();

		APIContext apiContext = new APIContext(accessToken);

		Map<String, String> sdkConfig = new HashMap<String, String>();

		sdkConfig.put("mode", "sandbox");

		apiContext.setConfigurationMap(sdkConfig);

		return apiContext;
	}

	protected static List<Transaction> getTransactions(
		String description, List<Item> items, String amountTotal,
		String currencyCode) {

		List<Transaction> transactions = new ArrayList<Transaction>();

		Transaction transaction = new Transaction();

		Amount amount = new Amount();

		amount.setCurrency(currencyCode);
		amount.setTotal(amountTotal);

		transaction.setAmount(amount);
		transaction.setDescription(description);

		ItemList itemList = new ItemList();

		itemList.setItems(items);

		transaction.setItemList(itemList);

		transactions.add(transaction);

		return transactions;
	}

}