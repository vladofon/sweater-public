<#import "parts/common.ftlh" as c>


<@c.page>
	<h1 class="mt-5">${header}</h1>
	<div>${description}</div>
	<hr>
	<#if login??><a class="btn btn-primary" href="/login">Click to relogin</a></#if>
</@c.page>