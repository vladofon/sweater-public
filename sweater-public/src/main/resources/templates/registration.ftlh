<#import "parts/common.ftlh" as c>
<#import "parts/login_page.ftlh" as l>

<@c.page>
    <h1>Registration</h1>
    <div>Enter some values bellow for be saved in system</div>
    <hr>
	<!--<div class="danger">${message?ifExists}</div>-->
	<@l.login "/registration" true/>
</@c.page>