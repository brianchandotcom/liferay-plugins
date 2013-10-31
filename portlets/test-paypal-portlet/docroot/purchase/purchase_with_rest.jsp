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

<div class="test-paypal purchase-with-rest">
	<h2>Purchase With REST</h2>

	<p>
		The REST method uses PayPal's REST APIs so that the portlet can use
		PayPal to process the transactions without ever letting the user know
		which service is being used underneath. This requires the portlet to
		collect all the billing information and would require the most security.
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
			<td class="quantity">3</td>
			<td class="price">$12.00</td>
		</tr>
		<tr>
			<td>Caramel Macchiato</td>
			<td class="price">$4.25</td>
			<td class="quantity">5</td>
			<td class="price">$4.25</td>
		</tr>
		<tr>
			<td>Pumpkin Spice Latte</td>
			<td class="price">$4.50</td>
			<td class="quantity">1</td>
			<td class="price">$9.00</td>
		</tr>
		<tr class="total">
			<td>Total</td>
			<td></td>
			<td></td>
			<td class="price">$34.75</td>
		</tr>
		</table>
	</div>

	<div class="payment-info">
		<h3>Payment Information</h3>

		<table class="payer-info">
		<tr>
			<th>First Name</th>
			<td>Joe</td>
		</tr>
		<tr>
			<th>Last Name</th>
			<td>Shopper</td>
		</tr>
		<tr>
			<th>Address</th>
			<td>1400 Montefino Ave</td>
		</tr>
		<tr>
			<th>City</th>
			<td>Diamond Bar</td>
		</tr>
		<tr>
			<th>Country Code</th>
			<td>US</td>
		</tr>
		<tr>
			<th>Postal Code</th>
			<td>91765</td>
		</tr>
		<tr>
			<th>State</th>
			<td>CA</td>
		</tr>
		</table>

		<table class="cc-info">
		<tr>
			<th>Credit Card Type</th>
			<td>Visa</td>
		</tr>
		<tr>
			<th>Number</th>
			<td>4417119669820331</td>
		</tr>
		<tr>
			<th>Expiration Month</th>
			<td>11</td>
		</tr>
		<tr>
			<th>Expiration Year</th>
			<td>2018</td>
		</tr>
		<tr>
			<th>CVV</th>
			<td>874</td>
		</tr>
		</table>
	</div>

	<portlet:actionURL name="purchaseWithRest" var="purchaseWithRestURL" />

	<aui:form action="<%= purchaseWithRestURL %>" method="post" name="fm">
		<aui:button type="submit" value="Purchase" />
	</aui:form>
</div>