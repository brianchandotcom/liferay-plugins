<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
HttpServletRequest originalServletRequest = PortalUtil.getOriginalServletRequest(request);

String payPalPayerId = ParamUtil.getString(originalServletRequest, "PayerID");
%>

<div class="test-paypal confirm-account-payment">
	<h2>Confirm Account Payment</h2>

	<p>
		The purchase will not be processed until the "Confirm Purchase" button
		has been pressed.
	</p>

	<div class="shopping-cart">
		<h3>Order</h3>

		<table>
		<tr>
			<th>Item</th>
			<th>Price</th>
			<th>Quantity</th>
			<th></th>
		</tr>
		<tr>
			<td>Boba Milk Tea</td>
			<td class="price">$3.00</td>
			<td class="quantity">4</td>
			<td class="price">$12.00</td>
		</tr>
		<tr>
			<td>Caramel Macchiato</td>
			<td class="price">$4.25</td>
			<td class="quantity">1</td>
			<td class="price">$4.25</td>
		</tr>
		<tr>
			<td>Pumpkin Spice Latte</td>
			<td class="price">$4.50</td>
			<td class="quantity">2</td>
			<td class="price">$9.00</td>
		</tr>
		<tr class="total">
			<td>Total</td>
			<td></td>
			<td></td>
			<td class="price">$25.25</td>
		</tr>
		</table>
	</div>

	<portlet:actionURL name="confirmAccountPayment" var="confirmAccountPaymentURL" />

	<aui:form action="<%= confirmAccountPaymentURL %>" method="post" name="fm">
		<aui:input name="payPalPayerId" type="hidden" value="<%= payPalPayerId %>" />

		<aui:button type="submit" value="Confirm Purchase" />
	</aui:form>
</div>