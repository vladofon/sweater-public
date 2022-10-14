package com.bootexample.spring.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@SuppressWarnings("deprecation")
public class RedirectInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String args = request.getQueryString() != null ? request.getQueryString() : "";
			String queryOperator;
			if (!args.equals("")) {
				queryOperator = "?";
			} else {
				queryOperator = "";
			}
			String url = request.getRequestURI() + queryOperator + args;
			response.setHeader("Turbolinks-Location", url);
		}
	}

}
