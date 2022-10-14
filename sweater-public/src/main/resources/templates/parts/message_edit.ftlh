<#macro messageForm isEditForm>
  <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">
    <#if isEditForm>
    	edit message
    <#else>
    	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send" viewBox="0 0 16 16">
		  <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.494-7.493Z"/>
		</svg>
    	create new message
    </#if>
  </button>

	<div <#if message??>aria-modal="true"</#if>
	 style="<#if message??>visibility: visible</#if>" 
	 class="<#if message??>show</#if> shadow-lg h-auto bg-primary bg-opacity-75 offcanvas offcanvas-bottom" 
	 data-bs-scroll="true" 
	 data-bs-backdrop="false" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel">
		  
		<form class="container p-3 mt-1" method="POST" action="<#if isEditForm && message??>/user-messages/${message.author.id}?message=${message.id}<#else>/create</#if>" enctype="multipart/form-data">
			<input type="hidden" name="_csrf" value="${_csrf.token}">
			<input type="hidden" name="id" value="<#if message??>${message.id}<#else>-1</#if>">
			
			
			
			<div class="row justify-content-md-center">
				<div class="bg-white col-12 col-sm-13 col-md-8 col-lg-6 shadow rounded p-3">
					<h5 class="card-title">Enter your post
						<button type="button" class="text-end btn-close text-reset d-inline-block" data-bs-dismiss="offcanvas" aria-label="Close"></button>
					</h5>
					
					<#if error??>
						<#if error.textError??>
				          <div class="alert alert-danger" role="alert">
							  ${error.textError}
						  </div>
				        </#if>
					    <#if error.tagError??>
				          <div class="alert alert-danger" role="alert">
							  ${error.tagError}
						  </div>
				        </#if>
					    <#if mutedError??>
				          <div class="alert alert-danger" role="alert">
							  ${mutedError}
						  </div>
				        </#if>
					</#if>
					  <div class="form-floating my-3">
						  <textarea name="text" class="form-control ${(textError??)?string('is-invalid', '')}" 
		  					 placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px"><#if message??>${message.text}</#if></textarea>
						  <label for="floatingTextarea2">Type some text</label>
					  </div>
					  <input name="tag" type="text" aria-label="Tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
		  				value="<#if message??>${message.tag}</#if>" placeholder="Tag">
					  <button type="submit" class="mt-3 btn btn-success">
					  <#if isEditForm>
				    	Edit
				      <#else>
				    	Create
				      </#if>
					  </button>
				</div>
			</div>
			
		</form> 
	</div>
<hr>
<#nested>
<hr>
	
</div>
</#macro>