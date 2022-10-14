<#import "parts/common.ftlh" as c>
<#import "parts/message_edit.ftlh" as me>
<#include "parts/security.ftlh">

<@c.page>
	<h1 class="mt-5">All posts in a row</h1>
	<hr>
	<h2>Messages</h2>
	<form class="form-floating col-md-3" method="get" action="/main">
		<div class="input-group mb-3 ">
		  <input name="filter" type="text" value="${filter?ifExists}" class="form-control" placeholder="Enter tag name">
		  <button class="btn btn-outline-secondary" type="submit" id="button-addon2">
		  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
			 <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
		  </svg>
		  Find
		  </button>
		</div>
	</form>
	<@me.messageForm false>
	<#include "parts/message_list.ftlh">
	</@me.messageForm>
</@c.page>