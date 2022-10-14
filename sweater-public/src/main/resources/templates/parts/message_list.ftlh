<#include "security.ftlh">
<#import "pager.ftlh" as p>


<@p.pager url page />
<#list page.content as message>
<div class="container card shadow my-3">
	<div class="row">
		<div class="col-sm-3 col-md-2 col-lg-2 col-4 py-3 text-center">
			<#if message.author.avatar??>
			<img class="rounded shadow img-fluid" src="/multimedia/${message.author.avatar.image}">
			<#else>
			<i>no image:)</i>
			</#if>
			<div class="card-text mt-2">
				<small class="text-muted">
					<#if message.postedAt??>${message.postedAt}<#else>unknown</#if>
				</small>
			</div>
		</div>
		<div class="col-sm-8 col-md-9 col-lg-9 col-8 py-3">
			<div class="row">
				<h5 class="card-title">
				<b class="shadow rounded bg-secondary bg-opacity-25 px-1">
					<a class="text-dark" style="text-decoration: none" href="/user-messages/${message.author.id}">${message.authorName}</a>
				</b>
				</h5>
			</div>
			<div class="row">
				<div class="card-text">
				${message.text?truncate(150)}
				</div>
				<div class="row mt-2">
					<div class="col">
						<div>
							<small class="text-muted">
								Tag: ${message.tag}
							</small>
						</div>
						<div>
							<#if !message.meLiked>
								<a href="/messages/${message.id}/like"><i class="bi bi-heart"></i></a>
								<#if message.likes != 0>${message.likes}</#if>
							<#else>
								<a href="/messages/${message.id}/like"><i class="bi bi-heart-fill"></i></a>
								${message.likes}
							</#if>
							
							<#if message.author.id == currentUserId>
								<small class="text-muted ">
								<a class="text-white rounded px-1 mx-1 bg-warning fw-light fs-6" style="text-decoration: none" href="/user-messages/${message.author.id}?message=${message.id}">
						    		<i class="bi bi-pencil"></i>
									edit
						    	</a>
						    	</small>
					    	</#if>
							<#if isAdmin>
								<small class="text-muted">
								<a class="text-white rounded px-1 mx-1 bg-danger fw-light fs-6 d-inline-block text-center" style="text-decoration: none" href="/messages/${message.id}/delete">
						    		<i class="bi bi-trash-fill"></i>
									
						    	</a>
						    	</small>
					    	</#if>
						</div>
						<div class="mt-2">
							<a class="btn btn-primary btn-sm" href="/messages/${message.id}">Comments</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<#else>
	<div class="col-md-4 alert alert-info" role="alert">
	  No messages
	</div>
</#list>
<@p.pager url page />