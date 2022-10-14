<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<#include "parts/security.ftlh">

<@c.page>
<h1 class="mt-4">Message page</h1>
<p>${message.author.username} post by 2021.10.30</p>
<hr>
<div class="container card shadow my-3">
	<div class="row">
		<div class="col-sm-3 col-md-2 col-lg-2 col-4 py-3 text-center">
			<#if message.author.avatar??>
			<img class="rounded shadow img-fluid" src="/multimedia/${message.author.avatar.image}">
			</#if>
			<div class="card-text mt-2">
				<small class="text-muted">
					<i class="bi bi-calendar-month"></i>
					${message.postedAt}
				</small>
			</div>
		</div>
		<div class="col-sm-8 col-md-9 col-lg-9 col-8 py-3">
			<div class="row">
				<h5 class="card-title">
				<b class="shadow rounded px-1">
					<a class="text-dark" style="text-decoration: none" href="/user-messages/${message.author.id}">${message.authorName}</a>
				</b>
				</h5>
			</div>
			<div class="row">
				<div class="card-text">
				${message.text}
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
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<hr>

<button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">Comment this</button>

<div <#if comment??>aria-modal="true"</#if>
	 style="<#if comment??>visibility: visible</#if>" 
	 class="<#if comment??>show</#if> shadow-lg h-auto bg-primary bg-opacity-75 offcanvas offcanvas-bottom" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel">
	<form class="container p-3 mt-1" method="POST" action="/messages/${message.id}" enctype="multipart/form-data">
		<input type="hidden" name="_csrf" value="${_csrf.token}">
		
		<div class="row justify-content-md-center">
			<div class="bg-white col-12 col-sm-13 col-md-8 col-lg-6 shadow rounded p-3">
			
				<#if error??>
					<#if error.textError??>
			          <div class="alert alert-danger" role="alert">
						  ${error.textError}
					  </div>
			        </#if>
				</#if>
			
				<h5 class="card-title">Enter your comment
					<button type="button" class="text-end btn-close text-reset d-inline-block" data-bs-dismiss="offcanvas" aria-label="Close"></button>
				</h5>
				  <div class="form-floating my-3">
					  <textarea name="text" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px">
					  	<#if comment??>${comment.text}</#if>
					  </textarea>
					  <label for="floatingTextarea2">Type some text</label>
				  </div>
				  <button type="submit" class="mt-1 btn btn-success">Create</button>
			</div>
		</div>
		
	</form> 
</div>

<hr>
<h2 class="mb-5">Comments</h2>
<@p.pager url page />
<hr>
<#list page.content as comment>
<div class="container mb-4">
    <div class="row">
        <div class="col-sm-3 col-md-2 col-lg-1 col-4 text-center">
            <#if comment.author.avatar??>
            	<img class="rounded-circle shadow img-fluid" src="/multimedia/${comment.author.avatar.image}">
            <#else>
            	<i>no image:)</i>
            </#if>
        </div>
        <div class="col">
        	
            <div class="col-md-9 border-bottom py-3 text-break">
	            <div class="card-text">
	                <b class="fs-6">
	                   <a class="text-dark" style="text-decoration: none" href="/user-messages/${comment.author.id}">${comment.author.username}</a>
	                </b>
	            </div>
                ${comment.text}
            </div>
            <div class="col-md-9 text-end p-1">
            	<#if isAdmin>
				<small class="text-muted">
					<a class="text-white rounded px-1 mx-1 bg-danger fw-light fs-6 d-inline-block text-center" style="text-decoration: none" href="/messages/${message.id}/${comment.id}/delete">
			    		<i class="bi bi-trash-fill"></i>
						
			    	</a>
			    </small>
		    	</#if>
				<small class="text-muted">
					<a data-bs-toggle="offcanvas" href="#offcanvasBottom" role="button" aria-controls="offcanvasBottom" style="text-decoration: none">
			    		<i>reply</i>
			    	</a>
			    </small>
                <small class="text-muted"><#if comment.postedAt??>${comment.postedAt}<#else>unknown</#if></small>
            </div>
        </div>
    </div>
</div>
<#else>
	<div class="col-md-4 alert alert-info" role="alert">
	  No comments
	</div>
</#list>
<hr>
<@p.pager url page />
</@c.page>