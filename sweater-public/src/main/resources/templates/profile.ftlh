<#import "parts/common.ftlh" as c>

<@c.page>
	<h1 class="mt-4">${username}</h1>
	<div>User profile page</div>
	<hr>
	<div class="container">
		<div class="row">
			<div class="col-12 col-sm-12 col-md-6 shadow rounded mb-3">
				<h2 class="text-center text-white bg-secondary py-1 shadow-sm">Change personal data</h2><hr>
				<form class="form-floating p-3 " method="post">
					<input type="hidden" name="_csrf" value="${_csrf.token}">
					<div class="form-floating mb-3 col-sm-8 col-md-10 shadow">
					  <input name="email" type="email" class="form-control" id="floatingEmail" placeholder="Password" value="${email!''}">
					  <label for="floatingPassword">Email</label>
					</div>
					<div class="form-floating mb-3 col-sm-8 col-md-10 shadow">
					  <input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
					  <label for="floatingPassword">Password</label>
					</div>
					<div class="col-12">
					  <button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form>
			</div>
			<div class="col-12 col-sm-12 col-md-6 mb-3 shadow rounded">
			<h2 class="text-center text-white bg-secondary py-1 shadow-sm">Change profile image</h2><hr>
			
				<form class="p-3 row" action="/user/profile/change-avatar" method="post" enctype="multipart/form-data">
					<input type="hidden" name="_csrf" value="${_csrf.token}">
					
					<#if avatar??>
					<div class="col-5 col-lg-4">
						<img class="border border-2 shadow img-fluid" src="/multimedia/${avatar.image}">
					</div>
					</#if>
					<div class="col-6 col-lg-8">
					  <input name="file" type="file" name="file" class="form-control shadow"><hr>
					  <button type="submit" class="btn btn-primary">Save</button>
					</div>
					
				</form>
			</div>
		</div>
	</div>
<hr>
<div class="text-muted">All existing changes will apply after relogin!</div>
</@c.page>