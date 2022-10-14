<#import "parts/common.ftlh" as p>
<#include "parts/security.ftlh">


<@p.page>
<h1 class="mt-4">Your subscriptions</h1>
<hr>

<div id="carouselExampleControls" class="carousel slide col-12 bg-secondary mx-auto my-3 shadow" data-bs-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active row align-items-end">
      <div class="d-inline-block col-5">
        <img src="/multimedia/${userAvatar}" class="img-fluid shadow" alt="...">
      </div>
      <div class="d-inline-block col-7 position-absolute top-50 start-50 translate-middle bg-black bg-opacity-25 shadow">
        <h3 class="text-white">
        	<a class="text-white text-decoration-none" href="/user/${user.id}/subscriptions/list">
        		Check your subscriptions!
        	</a>
        </h3>
      </div>
    </div>
    <#list subscriptions.content as channel>
    <div class="carousel-item row align-items-end">
      <div class="d-inline-block col-5">
        <img src="/multimedia/${channel.avatar.image}" class="img-fluid shadow" alt="...">
      </div>
      <div class="d-inline-block col-7 position-absolute top-50 start-50 translate-middle bg-black bg-opacity-25 shadow">
        <h3 class=""><a class="text-white text-decoration-none" href="/user-messages/${channel.id}">${channel.username}</a></h3>
      </div>
    </div>
    </#list>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>

<#include "parts/message_list.ftlh">
</@p.page>