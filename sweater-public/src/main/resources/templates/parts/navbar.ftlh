<#include "security.ftlh">
<#import "login_page.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">Sweater</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/">Home</a>
        </li>
        <li class="nav-item <#if !user?? && !known>opacity-25</#if>">
          <a class="nav-link" aria-current="page" href="/main">Messages</a>
        </li>
        <#if user?? && known>
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/user-messages/${currentUserId}">My posts</a>
        </li>
        </#if>
        <#if isAdmin>
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/user">User list</a>
        </li>
        </#if>
        <#if user?? && known>
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/user/profile">Profile</a>
        </li>
        </#if>
        <#if user?? && known>
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/channels">Subscriptions</a>
        </li>
        </#if>
        <#if user?? && known>
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/private-messages">Private messages</a>
        </li>
        </#if>
      </ul>
      
      <div class="navbar-text me-3">${name}</div>
      <#if user?? && known><@l.logout /><#else><a class="btn btn-primary" href="/login">Sign in</a></#if>
    </div>
  </div>
</nav>