package com.dv.controller.meeting;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dv.constants.SystemConst;
import com.dv.controller.FnfhBaseController;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.SubAppUser;
import com.dv.entity.user.User;
import com.dv.service.meeting.SubAppUserService;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
/**
 * 
 * @classDesc ：
 *	设置管理controller
 * @creater: 杨群山
 * @creationDate:2017年5月3日 上午11:01:32
 */
@Controller
@RequestMapping("/subAppUser")
public class SubAppUserController extends FnfhBaseController{

	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource 
	private SubAppUserService subAppUserService;
	/**
	 * 
	 * @methodDesc:  
	 * 查询设置管理
	 * @param req
	 * @param subAppUser
	 * @return 
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 下午3:07:40
	 */
	@RequestMapping(value="/background_showSubAppUser.action",method={RequestMethod.POST})
	@ResponseBody
	public Result showSubAppUser(HttpServletRequest req,SubAppUser subAppUser){
		FnfhPageData pageData=new FnfhPageData(req);
		try {
			return  subAppUserService.findsubAppUser(subAppUser, pageData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showSubAppUser,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/showSubAppUser,"+e.getMessage());
    		return Result.errorUnknow();
		}		
	} 
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到IPAD信息维护单页面
	 * @param req
	 * @return
	 * @return  
	 * @creater: 杨群山
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月5日 下午10:51:01
	 */
	@RequestMapping(value="/background_UserDetailView.action",method={RequestMethod.GET})
	public ModelAndView UserDetailView(HttpServletRequest req,Model model){
		FnfhPageData pgData=new FnfhPageData(req);
		model.addAttribute("data",pgData);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"meeting/device-management-modify");
		return mv;	
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 添加设置管理
	 * 添加或修改设备信息维护单
	 * @param req
	 * @param subAppUser
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 上午9:24:57
	 */
	@RequestMapping(value = "/background_addOrModifySubAppUser.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result addOrModifySubAppUser(HttpServletRequest req,HttpServletResponse response,
			SubAppUser subAppUser){
		try {
			validateAdminAction(req.getSession());
		}catch (Exception e) {
			String context = req.getContextPath();
			try {
				response.getWriter().write("<script>window.top.location.href=\""+context+"/login/toLogin.action\";</script>");
				return null;
			} catch (IOException e1) {
				
			}
		}
		
		User loginUser=(User)req.getSession().getAttribute(SystemConst.LOGIN_USER);
		int limitUserNum = (Integer)req.getSession().getAttribute(SystemConst.ATTENDEESLIMIT);
		
		FnfhPageData pgData=new FnfhPageData(req);
		try {
			return subAppUserService.addOrModifySubAppUser(subAppUser,pgData,loginUser,limitUserNum);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/addOrModifySubAppUser,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/addOrModifySubAppUser,"+e.getMessage());
    		return Result.errorUnknow();
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id删除设备绑定信息，需要释放授权码
	 * @param req
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午1:04:23
	 */
	@RequestMapping(value = "/background_delSubAppUserByIds.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result delSubAppUserByIds(HttpServletRequest req){
		FnfhPageData pgData=new FnfhPageData(req);
		try {
			return subAppUserService.delSubAppUserByIds(pgData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/delSubAppUserByIds,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}catch (Exception e) {
			logger.error(logger.getName()+"/delSubAppUserByIds,"+e.getMessage());
			return Result.errorUnknow();
		}		
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id清除设备绑定信息，释放授权码
	 * @param req
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午1:24:56
	 */
	@RequestMapping(value = "/background_clearSubAppUserByIds.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result clearSubAppUserByIds(HttpServletRequest req){
		FnfhPageData pgData=new FnfhPageData(req);
		try {
			return subAppUserService.clearSubAppUserByIds(pgData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/clearSubAppUserByIds,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}catch (Exception e) {
			logger.error(logger.getName()+"/clearSubAppUserByIds,"+e.getMessage());
			return Result.errorUnknow();
		}		
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改设置管理
	 * @param req
	 * @param subAppUser
	 * @return 
	 * @creater: 杨群山
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月5日 下午3:07:40
	 */
	@RequestMapping(value = "/background_modsubAppUser.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result modsubAppUser(HttpServletRequest req,SubAppUser subAppUser){
		try {
			return subAppUserService.modsubAppUser(subAppUser);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/modsubAppUser,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/modsubAppUser,"+e.getMessage());
    		return Result.errorUnknow();
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param req
	 * @param subAppUser
	 * @return 
	 * @creater: 杨群山
	 * @creationDate:2017年5月5日 下午3:07:40
	 */
	@RequestMapping(value = "/background_getObjInstane.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result getObjInstane(HttpServletRequest req,SubAppUser subAppUser){
		try {
			return subAppUserService.getObjInstane(subAppUser);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
    		return Result.errorUnknow();
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到新增修改IPAD维护单页面
	 * @param req
	 * @param model
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月6日 下午3:28:54
	 */
	@RequestMapping(value="/background_addOrModifySubAppUserView.action",method={RequestMethod.GET})
	public ModelAndView addOrModifySubAppUserView(HttpServletRequest req,HttpServletResponse response,
			Model model){
		try {
			validateAdminAction(req.getSession());
		}catch (Exception e) {
			String context = req.getContextPath();
			try {
				response.getWriter().write("<script>window.top.location.href=\""+context+"/login/toLogin.action\";</script>");
				return null;
			} catch (IOException e1) {
				
			}
		}
		
		FnfhPageData pgData=new FnfhPageData(req);
		//设置当前系统时间
		String dateNow=CommonMethod.getDateFormaty_M_d(new Date());
		pgData.put("create_time", dateNow);
		model.addAttribute("data",pgData);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"meeting/device-management-add");
		return mv;	
	}
	
	
	
	
	
}
