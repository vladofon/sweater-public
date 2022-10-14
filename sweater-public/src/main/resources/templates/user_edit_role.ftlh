<#import "parts/common.ftlh" as c>

<@c.page>
<h1 class="mt-4">Assign role</h1>
<hr>
<div class="col-lg-6 card shadow p-3">
	<form action="/user/roles" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}">
		<input type="hidden" name="userId" value="${user.id}">
		<div>
		<#list roles as role>
			<div class="input-group mb-3 shadow">
			  <span class="bg-${adminRoles?seq_contains(role)?string("danger", "secondary")} text-white justify-content-center border-secondary opacity-75 input-group-text col-2" id="addon-wrapping">
			   	  <#if adminRoles?seq_contains(role)>
			  	  	<i class="fas fa-crown"></i>
			  	  <#else>
			  	  	<i class="bi bi-person-fill"></i>
			  	  </#if>
			  </span>
			  <div class="m-0 col-10 alert alert-${adminRoles?seq_contains(role)?string("danger", "secondary")}" role="alert">
				  <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}> ${role}
				</div>
			</div>
		</#list>
		</div>
		<button class="mt-2 btn btn-success" type="submit">Confirm</button>
	</form>
</div>
<hr>
<a class="btn btn-secondary" href="/user/${user.id}">Return to user editor</a>
</@c.page>