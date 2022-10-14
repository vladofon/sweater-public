<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<#include "parts/security.ftlh">

<@c.page>
	<h1 class="mt-4">${interlocutor}</h1>
	
<@p.pager url page />
<div class="container justify-content-center">
<div class="col-12 col-sm-12 col-md-10 col-lg-8">
<#list page.content as message>
	
		<div class="row alert bg-opacity-25 bg-<#if message.sender == user>success text-end justify-content-end<#else>primary</#if> mb-3">

				<#if message.sender != user>
					<div class="col-3 col-sm-2 col-md-2 col-lg-2 text-center">
			            <#if message.sender.avatar??>
			            	<img class="rounded-circle shadow img-fluid" src="/multimedia/${message.sender.avatar.image}">
			            <#else>
			            	<i>no image:)</i>
			            </#if>
			        </div>
		        </#if>
		        
		        <div class="col-9 col-sm-10 col-md-10 col-lg-10">
		            <div class="text-break fs-6">
		            	<#if message.sender != user>
			            <div class="card-text">
			                <b class="fs-6">
			                   <a class="text-dark" style="text-decoration: none" href="/user-messages/${message.sender.id}">${message.sender.username}</a>
			                </b>
			            </div>
			            </#if>
		                ${message.text}
		            </div>
		            <div class="p-1">
		                <small class="text-muted">
		                <#if message.postedAt??>
		                	${
		                		message.postedAt?substring(0, message.postedAt?last_index_of(":"))
		                	}
		                <#else>unknown</#if>
		                </small>
		            </div>
		        </div>
		        
		</div>
		
<#else>
	<div class="col-md-4 alert alert-info" role="alert">
	  No messages
	</div>
</#list>
</div>
</div>
<button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">New message</button>
<hr>
<div <#if message??>aria-modal="true"</#if>
	 style="<#if message??>visibility: visible</#if>" 
	 class="<#if message??>show</#if> shadow-lg h-auto bg-primary bg-opacity-75 offcanvas offcanvas-bottom" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel">
	<form class="container p-3 mt-1" method="POST" action="/private-messages/${interlocutorId}" enctype="multipart/form-data">
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
			
				<h5 class="card-title">Enter your message
					<button type="button" class="text-end btn-close text-reset d-inline-block" data-bs-dismiss="offcanvas" aria-label="Close"></button>
				</h5>
				  <div class="form-floating my-3">
					  <textarea name="text" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px">
					  <#if message??>${message.text}</#if>
					  </textarea>
					  <label for="floatingTextarea2">Type some text</label>
				  </div>
				  <button type="submit" class="mt-1 btn btn-success">Create</button>
			</div>
		</div>
		
	</form> 
</div>
<@p.pager url page />
</@c.page>