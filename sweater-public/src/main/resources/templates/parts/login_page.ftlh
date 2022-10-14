<#macro login path isRegisterForm>
<form class="form-floating" action="${path}" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}">
	
	<div class="form-floating mb-3 col-sm-5 shadow-sm">
	  <input value="<#if user??>${user.username!}</#if>" name="username" type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}" id="floatingInput" placeholder="user123">
	  <label for="floatingInput">Username</label>
		<#if usernameError??>
		<div class="alert alert-danger">
	    ${usernameError}
	    </div>
	    </#if>
	</div>
    
	<#if isRegisterForm>
	<div class="form-floating mb-3 col-sm-5 shadow-sm">
	  <input value="<#if user??>${user.email!}</#if>" name="email" type="email" class="form-control ${(emailError??)?string('is-invalid', '')}" id="floatingEmail" placeholder="name@example.com">
	  <label for="floatingPassword">Email</label>
	  	<#if emailError??>
		<div class="alert alert-danger">
	    ${emailError}
	    </div>
	    </#if>
	</div>
	</#if>
	
	<div class="form-floating mb-3 col-sm-5 shadow-sm">
	  <input name="password" type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" id="floatingPassword" placeholder="Some secure password...">
	  <label for="floatingPassword">Password</label>
	  	<#if passwordError??>
		<div class="alert alert-danger">
	    ${passwordError}
	    </div>
	    </#if>
	</div>
    
    <#if isRegisterForm>
    <div class="form-floating mb-3 col-sm-5 shadow-sm">
	  <input name="password2" type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}" id="floatingPassword">
	  <label for="floatingPassword">Enter password again</label>
	  <#if password2Error??>
		<div class="alert alert-danger">
	    ${password2Error}
	    </div>
	  </#if>
	</div>
    </#if>
    
    <#if isRegisterForm>
    <div class="form-floating mb-3 col-sm-5">
    	<div class="g-recaptcha" data-sitekey="6LdhJ8wdAAAAAHBzBA3hWEy2xeXEdvQNXo86HbZp"></div>
	    <br/>
	    <#if captchaError??>
	      <div class="alert alert-danger">
		  ${captchaError}
		  </div>
		</#if>
    </div>
    </#if>

    
	<div class="col-12">
	  <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign in</#if></button>
	</div>
</form>
<hr>
<#if !isRegisterForm>
<div class="col-md-4 alert alert-info" role="alert">
	Not registered yet? Go to the <a href="/registration" data-turbolinks="false">registration page</a>
</div>
<#else>
<div class="col-md-4 alert alert-info" role="alert">
	Return to <a href="/login">login page</a>
</div>
</#if>
</#macro>   

<#macro logout>
<form action="/logout" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="col-12">
	  <button type="submit" class="btn btn-danger">Sign Out</button>
	</div>
</form>
</#macro>     