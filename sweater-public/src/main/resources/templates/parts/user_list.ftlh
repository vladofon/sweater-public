<#import "pager.ftlh" as p>
<#include "security.ftlh">

<hr>
<h3 class="mb-3">${type}</h3>

<@p.pager url page />
<#list page.content as user>
  <div class="card mb-3 shadow rounded <#if !user.active> opacity-50<#elseif user.muted> bg-warning bg-opacity-25<#else> bg-body</#if>">
    <div class="row">
	    <div class="col-3 col-sm-2 col-md-2 col-lg-1 p-1">
	    	<#if user.avatar??><img class="rounded shadow-lg img-fluid border border-3" src="/multimedia/${user.avatar.image}"></#if>
	    </div>
	    <div class="col-9 col-sm-10 col-md-10 col-lg-11 align-self-center">
	      <div class="card-body">
	        <h5 class="card-title">
	        <#if user.muted || !user.active>
	        	<i class="bi bi-exclamation-triangle-fill"></i>
	        <#else>
	        	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
				  <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
				</svg>
			</#if>
	        	<a class="text-dark" style="text-decoration: none" href="/user-messages/${user.id}">${user.username}</a>
	        </h5>
	        <p class="card-text">
	        	<small class="text-muted">
	        		<#if type == "correspondences">
	        			<a href="/private-messages/${user.id}" class="btn btn-primary">Send message</a> | 
	        		</#if>
	        		Info: ${description}
	        		<#if type == "users">
	        		 | 
	        		<#list user.roles as role>
	        			<span class="text-white bg-success rounded bg-opacity-75 p-1">${role}</span> <#sep>  
	        		</#list>
	        			<#if !user.isAdmin() || !user.isImmutable() && isFounder>
			        		
			        		| <a class="text-white rounded p-1 bg-warning fw-light fs-6 mr-1" style="text-decoration: none" href="/user/${user.id}">
			        		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
							  <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
							</svg>
			        		Edit
			        		</a> 
			        	</#if>
	        			<span class="mx-1">| created at ${user.postedAt}</span>
	        		</#if>
	        	</small>
	        </p>
	      </div>
	    </div>
	  </div>
	</div>
<#else>
	<div class="col-md-4 alert alert-info" role="alert">
	  No people
	</div>
</#list>
<@p.pager url page />