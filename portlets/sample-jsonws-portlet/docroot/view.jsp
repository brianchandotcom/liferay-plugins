<%--
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
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<h1>Sample JSONWS</b></h1>

<h2>API</h2>
<a href="/api/jsonws">JSON Web Service API overview</a> - for Liferay portal.<br>
<a href="/sample-jsonws-portlet/api/jsonws">JSON Web Service API overview</a> - of this portlet.<br>

<h2>Invocation</h2>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/ping">ping</a> - simple call<br>
<a href="/sample-jsonws-portlet/api/secure/jsonws/sampleasset/ping">secure ping</a> - secure simple call<br>

<h3>parameters</h3>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello/full-name/World/user-id/173">hello</a> - call method and pass parameters in URL (notice that order is not important)<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello?fullName=World&userId=173">hello</a> - call method and pass parameters as request parameters<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello/full-name/World?userId=173">hello</a> - mixed mode<br>
<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello2?calendar=1330419334285&longArray=1,2,3&locales=[en,fr]">hello 2</a> - many different parameter types<br>
<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello/full-name/My%20World/user-id/173">hello</a> - encoding parameters in URL<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello?fullName=My+World&userId=173">hello</a> - encoding request parameters<br>
<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/workout-user">workoutUser</a> - invoke method without passing default parameters<br>

<h3>null values</h3>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello/-full-name/user-id/173">hello with null</a> - pass NULL value in in URL <br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/hello?-fullName&userId=173">hello with null</a> - pass NULL value as request parameters<br>

<h3>new instance</h3>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/process/+sampleAsset">process fails</a> - create new instance and calls the method: fails (InstantiationException)<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/process/+sampleAsset:com.liferay.samplejsonws.model.impl.SampleAssetImpl">process new instance</a> - create concrete instance for argument<br>

<h3>inner parameters</h3>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/process/+sampleAsset:com.liferay.samplejsonws.model.impl.SampleAssetImpl/sampleAsset.name/FooName">process new instance and inner params</a> - create instance, populate it and pass it as service method argument<br>

<h3>matching</h3>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/method-one/id/123">methodOne</a> - doesn't match any method<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/method-one/id/123/name/Name">methodOne</a> - match #1, all parameters are provided<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/method-one/id/123/name-id/321">methodOne</a> - match #2 and does not match #3<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/method-one.3/id/123/name-id/321">methodOne</a> - match #3, as indicated by a hint, not specified values are null<br>
<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/method-one/id/123/name/Name/name-id/321">methodOne that matches two methods</a> - match #1, as it is a first registered one;<br>
<a href="/sample-jsonws-portlet/api/jsonws/sampleasset/method-one.2/id/123">methodOne with hint that matches two methods</a> - match #1, again.<br>