<#import "parts/common.ftlh" as c>
<#import "parts/message_edit.ftlh" as me>
<#include "parts/security.ftlh">

<@c.page>
<h1 class="mt-5">${userChannel.username}</h1>
<#if !isCurrentUser>
	<hr>
	<#if !isSubscriber>
		<a href="/user/subscribe/${userChannel.id}" class="btn btn-danger">
		subscribe
		</a>
	<#else>
		<a href="/user/unsubscribe/${userChannel.id}" class="btn btn-secondary">
		unsubscribe
		</a>
	</#if>
	<hr>
</#if>

<div class="container">
	<div class="row">
		<div class="col-md-6 col-12 my-2">
			<div class="card shadow bg-body rounded">
				<div class="card-body">
					<div class="card-title">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16">
					  <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z"/>
					</svg>
					Subscriptions
					</div>
					<h3 class="card-text">
					<span class="me-2 fs-6 text fw-light text-white bg-success rounded bg-opacity-75 p-1">
						total count:
					</span> 
					<#if subscriptionsCount != 0>
					<a class="text-decoration-none text-dark" href="/user/${userChannel.id}/subscriptions/list">${subscriptionsCount}</a>
					<#else>
					<span class="text-secondary">0</span>
					</h3>
					</#if>
					</h3>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-12 my-2">
			<div class="card shadow bg-body rounded">
				<div class="card-body">
					<div class="card-title">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
					  <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
					  <path fill-rule="evenodd" d="M5.216 14A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216z"/>
					  <path d="M4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z"/>
					</svg>
					Subscribers
					</div>
					<h3 class="card-text">
					<span class="me-2 fs-6 text fw-light text-white bg-success rounded bg-opacity-75 p-1">
						total count: 
					</span> 
					<#if subscribersCount != 0>
					<a class="text-decoration-none text-dark" href="/user/${userChannel.id}/subscribers/list">${subscribersCount}</a>
					<#else>
					<span class="text-secondary">0</span>
					</h3>
					</#if>
				</div>
			</div>
		</div>
	</div>
</div>
<hr>

<#if isCurrentUser>
	<@me.messageForm true>
		<#include "parts/message_list.ftlh">
	</@me.messageForm>
<#else>
	<#include "parts/message_list.ftlh">
</#if>
</@c.page>