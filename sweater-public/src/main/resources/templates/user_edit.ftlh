<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
<h1 class="mt-4">User editor ${qwerty!}</h1>
<hr>
<div class="container">
	<div class="row justify-content-center">
		<div class="col-sm-11 col-md-5 col-11 card p-0 m-3 shadow">
			<div class="row pt-3 justify-content-center">
				<fieldset class="col-4 col-md-7 text-center mr-3">
					<#if userToEdit.avatar??>
						<img class="img-fluid rounded shadow" src="/multimedia/${userToEdit.avatar.image}">
					</#if>
					<legend>${userToEdit.username}</legend>
				</fieldset>
				<div class="col-5 col-md-4">
					<h5>UID-${userToEdit.id}</h5>
					<ul class="p-0">
						<li>Is active: ${userToEdit.isActive()?string('yes', 'no')}</li>
						<li>Is muted: ${userToEdit.isMuted()?string('yes', 'no')}</li>
						<li>Is admin: ${userToEdit.isAdmin()?string('yes', 'no')}</li>
					</ul>
				</div>
			</div>
			<div class="card-footer text-center">
				<h3 class="text-muted">
					User info
				</h3>
			</div>
		</div>
		<div class="col-sm-11 col-md-5 col-11 card m-3 p-0 shadow">
				<div class="col pt-3 px-3">
					<form action="/user" method="post" enctype="multipart/form-data">
						<input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}">
						<input class="form-control" type="hidden" name="userId" value="${userToEdit.id}">
						
						<div class="input-group flex-nowrap mb-3">
						  <span class="shadow opacity-75 bg-primary text-white input-group-text" id="addon-wrapping">Username</span>
						  <input type="text" class="form-control" name="username" value="${userToEdit.username}" placeholder="Username" aria-label="Username" aria-describedby="addon-wrapping">
						</div>
						<div class="input-group flex-nowrap mb-4">
						  <span class="shadow opacity-75 bg-primary text-white input-group-text" id="addon-wrapping">Avatar</span>
						  <input type="file" class="form-control" name="file" placeholder="Username" aria-label="Username" aria-describedby="addon-wrapping">
						</div>
						
						<div class="">
							<button class="col-12 col-sm-4 col-md-4 mb-2 shadow btn btn-success" type="submit">Save changes</button>
							<a href="/user/${userToEdit.id}/default" class="col-12 col-sm-4 col-md-4 mb-2 shadow btn btn-warning text-white" type="submit">Drop to default</a>
						</div>
					</form>
				</div>
			<div class="card-footer text-center mt-2">
				<h3 class="text-muted">
					Change data
				</h3>
			</div>
		</div>
	</div>
</div>
<hr>
<h2 class="text-muted my-3">Admin tooling</h2>
<a class="btn btn-primary" href="/user/${userToEdit.id}/advertisement">Advertisement</a>
<#if !userToEdit.muted>
	<a class="btn btn-danger text-white" href="/user/${userToEdit.id}/mute">Mute</a>
<#elseif !userToEdit.activationCode??>
	<a class="btn btn-warning text-white" href="/user/${userToEdit.id}/unmute">Unmute</a>
</#if>
<hr>
<#if isFounder>
	<h2 class="text-muted my-3">Founder tooling</h2>
	<a class="btn btn-primary" href="/user/${userToEdit.id}/roles">Rise the role</a>

	<#if userToEdit.active>
		<a class="btn btn-danger text-white" href="/user/${userToEdit.id}/ban">Ban</a>
	<#elseif !userToEdit.activationCode??>
		<a class="btn btn-warning text-white" href="/user/${userToEdit.id}/unban">Unban</a>
	</#if>
	
</#if>
</@c.page>