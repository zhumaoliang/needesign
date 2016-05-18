package com.digitalchina.webapp.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.digitalchina.webapp.admin.utils.ContextConstants;



public class LoginInterceptor implements HandlerInterceptor{
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		 HttpSession session = request.getSession(true);  
	        // 从session 里面获取用户名的信息  
	        Object obj = session.getAttribute(ContextConstants.ADMIN_SESSION);  
	        // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆  
	        if (obj == null || "".equals(obj.toString())) {  
	        	 response.sendRedirect(ContextConstants.ADMIN_LOGIN);  
	        	return false;  
	        }  
	        return true;
	}

}
