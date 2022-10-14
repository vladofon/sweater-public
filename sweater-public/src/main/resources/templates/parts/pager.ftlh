<#macro pager url page>
<#if page.getTotalPages() gt 7>
	<#assign
		totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
	>
<#else>
<#if page.getTotalPages() != 0>
	<#assign body = 1..page.getTotalPages()>
<#else>
	<#assign body = [0]>
</#if>
</#if>

<nav aria-label="..." class="d-inline-block" style="margin-right: 20px">
  <ul class="pagination">
    <li class="page-item disabled">
      <a class="page-link" href="#" tabindex="-1">
	      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text" viewBox="0 0 16 16">
			  <path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>
			  <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>
		  </svg>
	      Pages
      </a>
    </li>
    <#list body as p>
    	<#if (p - 1) == page.getNumber()>
	    <li class="page-item active">
	      <a class="page-link" href="#" tabindex="-1">${p}</a>
	    </li>
    	<#elseif p == -1>
	    <li class="page-item disabled">
	      <a class="page-link" href="#" tabindex="-1">...</a>
	    </li>
	    <#elseif p == 0>
	    <li class="page-item disabled">
	      <a class="page-link" href="#" tabindex="-1">0</a>
	    </li>
    	<#else>
	    <li class="page-item">
	      <a class="page-link" href="${url}?page=${p - 1}&size=${page.getSize()}<#if filter??>&filter=${filter}</#if>" tabindex="-1">${p}</a>
	    </li>
	    </#if>
    </#list>
  </ul>
</nav>

<p class="d-inline-block">
  <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Choose range
  </a>
</p>
<div class="collapse" id="collapseExample">
  <div aria-label="..." class="d-inline-block">
    <ul class="pagination">
    <li class="page-item disabled">
      <a class="page-link" href="#" tabindex="-1">
	      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-files" viewBox="0 0 16 16">
		  	<path d="M13 0H6a2 2 0 0 0-2 2 2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h7a2 2 0 0 0 2-2 2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm0 13V4a2 2 0 0 0-2-2H5a1 1 0 0 1 1-1h7a1 1 0 0 1 1 1v10a1 1 0 0 1-1 1zM3 4a1 1 0 0 1 1-1h7a1 1 0 0 1 1 1v10a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V4z"/>
		  </svg>
	      Range
      </a>
    </li>
    <#list [5, 10, 20, 50] as c>
    	<#if c == page.getSize()>
	    <li class="page-item active">
	      <a class="page-link" href="#" tabindex="-1">${c}</a>
	    </li>
    	<#else>
	    <li class="page-item">
	      <a class="page-link" href="${url}?page=0&size=${c}<#if filter??>&filter=${filter}</#if>" tabindex="-1">${c}</a>
	    </li>
	    </#if>
    </#list>
	</ul>
  </div>
</div>


</#macro>