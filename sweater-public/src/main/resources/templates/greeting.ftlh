<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
	<h1 class="mt-5">Hello, <#if known>${name}<#else>anon!</#if></h1>
	<div>Use navbar to navigate between site pages</div>
	<hr>
	Very important link! <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">Click here</a>
</@c.page>


