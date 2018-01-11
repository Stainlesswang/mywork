package com.dv.servlet.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dv.constants.SystemConst;
import com.dv.entity.Result;
import com.dv.entity.meeting.Permission;
import com.dv.entity.user.User;


public class UserFilter implements Filter{

	private FilterConfig filterConfig;
	public static String redirectURL;
	private String backURL;
	public static String[] notfilter;
	public static String[] needfilter;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String context = request.getContextPath();
		String uri = request.getRequestURI();
		String reqUrl=uri.replace(context, "");
//		//可以在这边过滤一些需要登录才能访问的请求
//		chain.doFilter(req, res);
		boolean isfilter=false;
		if (notfilter!=null)
		{
			for (int i=0;i<notfilter.length;i++)
			{
				if (uri.indexOf("/".concat(notfilter[i].trim()))!=-1)
				{
					isfilter=true;
					break;
				}
			}
		}
		if (isfilter)
		{
			chain.doFilter(req, res);
		}
		else
		{
			if(uri.contains("background_")||uri.endsWith("toDo.action"))//后台请求
			{
				HttpSession session=request.getSession();
				//校验用户是否登陆
				if(session.getAttribute(SystemConst.LOGIN_USER)!=null)
				{
					Object obj=request.getSession().getAttribute(SystemConst.LOGIN_USER);
					if(obj instanceof User)
					{
						//判断是否有权限
						User userInfo=(User)obj;
						if(userInfo.getIs_super()==0&&userInfo.getAdmin_type()==0)//如果不是超级管理员也不是普通管理员
						{
							Object functionMap=session.getAttribute(SystemConst.FUNCTION_MAP);
							if(!(functionMap instanceof Map))
							{
								//跳转没有权限提示页
								response.getWriter().write("<script>window.top.location.href=\""+context+"/login/noperimission.action\";</script>");
								return;
							}
						}
						chain.doFilter(req, res);
					}
					else
					{
						//跳转到登陆界面
						response.getWriter().write("<script>window.top.location.href=\""+context+"/login/toLogin.action\";</script>");
						return;
					}
				}
				else
				{
					//跳转到登陆界面
					response.getWriter().write("<script>window.top.location.href=\""+context+"/login/toLogin.action\";</script>");
					return;
				}
			}
			else//前端请求
			{
				chain.doFilter(req, res);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String notf=filterConfig.getInitParameter("notfilter");
		if (notf!=null)
		{
			notfilter=notf.split(";");
		}
		String needf=filterConfig.getInitParameter("needfilter");
		if (needf!=null)
		{
			needfilter=needf.split(";");
		}
		backURL = filterConfig.getInitParameter("backURL");
		redirectURL = filterConfig.getInitParameter("redirectURL");
	}

}
