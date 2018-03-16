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
import com.dv.entity.meeting.SubAppMeeting;
import com.dv.entity.user.User;
import com.dv.service.meeting.SubAppMeetingService;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
 

/**
 * 
 * @classDesc ：
 *	会议controller
 * @creater: 杨群山
 * @creationDate:2017年5月3日 上午11:01:32
 */
@Controller
@RequestMapping("/subAppMeeting")
public class SubAppMeetingController extends FnfhBaseController {
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource 
	private SubAppMeetingService subAppMeetingService;
	/**
	 * 
	 * @methodDesc:  
	 * 查询会议信息
	 * @param req
	 * @param subAppMeeting
	 * @return 
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 下午3:07:40
	 */
	@RequestMapping(value="/background_showMeeting.action",method={RequestMethod.POST})
	@ResponseBody
	public Result showMeeting(HttpServletRequest req,SubAppMeeting subAppMeeting,
			HttpServletResponse response){
		
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
		
		FnfhPageData pageData=new FnfhPageData(req);
		try {
			return subAppMeetingService.findsubAppMeeting(subAppMeeting, pageData,loginUser);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showMeeting,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/showMeeting,"+e.getMessage());
    		return Result.errorUnknow();
		}
		
	} 
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到会议资料维护单页面
	 * @param req
	 * @return
	 * @return  
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 下午10:51:01
	 */
	@RequestMapping(value="/background_meetDetailView.action",method={RequestMethod.GET})
	public ModelAndView meetDetailView(HttpServletRequest req,Model model){
		FnfhPageData pgData=new FnfhPageData(req);
		model.addAttribute("data",pgData);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"meeting/meeting-materials-push-modify");
		return mv;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 添加或修改会议信息
	 * @param req
	 * @param response
	 * @param subAppMeeting
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月5日 下午6:21:21
	 */
	@RequestMapping(value = "/background_addOrModifySubAppMeeting.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result addOrModifySubAppMeeting(HttpServletRequest req, HttpServletResponse response,
			SubAppMeeting subAppMeeting)
	{
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
		Integer attendeesLimit = (Integer)req.getSession().getAttribute(SystemConst.ATTENDEESLIMIT);
		FnfhPageData pgdata=new FnfhPageData(req);
		try {
			return subAppMeetingService.addOrModifySubAppMeeting(subAppMeeting,pgdata,loginUser,attendeesLimit);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/addSubAppMeeting,"+e.getMessage(), e);
			System.out.println("-------->"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id集合删除会议信息
	 * @param req
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月6日 下午1:25:38
	 */
	@RequestMapping(value = "/background_delSubAppMeetingByIds.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result delSubAppMeetingByIds(HttpServletRequest req)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try
		{
			return subAppMeetingService.delSubAppMeeting(pgdata);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/delSubAppMeetingByIds,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/delSubAppMeetingByIds,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改会议信息
	 * @param subAppMeeting
	 * @return
	 * @return  
	 * @creater: 杨群山
	 * @deprecated 暂不使用
	 * @creationDate:2017年5月4日 下午4:31:39
	 */
	@RequestMapping(value = "/background_modsubAppMeeting.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result modsubAppMeeting(SubAppMeeting subAppMeeting)
	{
		try
		{
			return  subAppMeetingService.modsubAppMeeting(subAppMeeting);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/modsubAppMeeting,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/modsubAppMeeting,"+ex.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param req
	 * @param subAppMeeting
	 * @return
	 * @return  
	 * @creater: 杨群山
	 * @creationDate:2017年5月4日 下午4:31:12
	 */
	@RequestMapping(value = "/background_getObjInstane.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result getObjInstane(HttpServletRequest req,SubAppMeeting subAppMeeting)
	{
		try
		{
			return subAppMeetingService.getObjInstane(subAppMeeting);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
		    e.printStackTrace();
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 会议资料维护单-新增
	 * @param req
	 * @param model
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月5日 上午9:13:47
	 */
	@RequestMapping(value="/background_meetAddView.action",method={RequestMethod.GET})
	public ModelAndView meetAddView(HttpServletRequest req,HttpServletResponse response,Model model){
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
		String dateNow=CommonMethod.getDateFormat(new Date());
		pgData.put("draft_time", dateNow);
		model.addAttribute("data",pgData);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"meeting/meeting-materials-push-add");
		return mv;
	}
	
	
}
