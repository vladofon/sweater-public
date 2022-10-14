<#import "parts/common.ftlh" as c>
<#import "parts/login_page.ftlh" as l>

<@c.page>
	<h1 class="mt-4">Login</h1>
	<div>Enter your info bellow for enter the app</div>
	<hr>
	<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
      <div class="alert alert-danger" role="alert">
		  ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
	  </div>
	</#if>
	<#if message??>
      <div class="alert alert-${type}" role="alert">
		  ${message}
	  </div>
	</#if>
	<@l.login "/login" false/>
</@c.page>