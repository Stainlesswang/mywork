package com.dv.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 *	登录检查拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

	//@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

//	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	//@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object arg2) throws Exception {
//		Object user=request.getSession().getAttribute("userKey");
//		if(user == null||!(user instanceof User)) {
//			String context = request.getContextPath();
//			response.getWriter().write("<script>window.top.location.href=\""+context+"/userLogin/loginOut.action\";</script>");
//			return false;
//		} else {
//			return true;
//		}
		//测试阶段，可直接返回true。
//		System.out.println("进入拦截器");
		return true;
	}

}
