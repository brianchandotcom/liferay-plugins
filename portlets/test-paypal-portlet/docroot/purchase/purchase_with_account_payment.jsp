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

<div class="test-paypal purchase-with-account-payment">
	<h2>Purchase With Account Payment</h2>

	<p>
		The Account Payments method relies on PayPal to do the collection of all
		sensitive data. The billing information, such as credit card numbers,
		never pass through the portlet.
	</p>

	<p>
		When using this method, the user is redirected to a PayPal site where
		they may choose to pay using their credit card or through their PayPal
		account. Once they've agreed to the order, they will be redirected back
		to the portlet where they must confirm their order. Payment will not
		happen until they confirm.
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

	<portlet:actionURL name="purchaseWithAccountPayment" var="purchaseWithAccountPaymentURL" />

	<aui:form action="<%= purchaseWithAccountPaymentURL %>" method="post" name="fm">
		<aui:button type="submit" value="Purchase" />
	</aui:form>
</div>