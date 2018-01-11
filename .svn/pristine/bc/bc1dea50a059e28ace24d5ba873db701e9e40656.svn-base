package com.dv.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dv.constants.SystemConst;

/**
 * 
 * @classDesc ：
 * 系统首页
 * @creater: 李梦婷
 * @creationDate:2017年3月1日 上午9:34:44
 */
@Controller
@RequestMapping("/index")
public class IndexController extends FnfhBaseController{
	
	@RequestMapping("/index.action")
	public ModelAndView index(HttpServletRequest req,HttpServletResponse response)
	{
		try {
			validateAdminAction(req.getSession());
		}catch (Exception e) {
			String context = req.getContextPath();
			try {
//				response.sendRedirect(context + "/login/toLogin.action");
				response.getWriter().write("<script>window.top.location.href=\""+context+"/login/toLogin.action\";</script>");
				return null;
			} catch (IOException e1) {
				
			}
		}
		return new ModelAndView(SystemConst.basePagePath+"index");
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 欢迎页面
	 * @param req
	 * @param response
	 * @return
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年3月1日 上午10:21:22
	 */
	@RequestMapping("/welcomeIndex.action")
	public ModelAndView welcomeIndex(HttpServletRequest req,HttpServletResponse response)
	{
		return new ModelAndView(SystemConst.basePagePath+"welcomeIndex");
	}
	
	
	

}
