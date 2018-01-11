package com.dv.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.controller.FnfhBaseController;
import com.dv.entity.Result;
import com.dv.entity.meeting.Permission;
import com.dv.entity.user.User;
import com.dv.license.LicenseChecker;
import com.dv.service.meeting.PermissionService;
import com.dv.service.user.UserManageService;
import com.dv.util.FnfhException;

@Controller
@RequestMapping("/login")
public class LoginController extends FnfhBaseController{
	
	@Resource
	private UserManageService userInfoService;//管理员信息service
	
	@Resource
	private PermissionService permissionService;//权限service
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志

	/******************************后台用户操作**********************************************/
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到后台登陆页面
	 * @param req
	 * @param response
	 * @return
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年3月10日 上午10:32:24
	 */
	@RequestMapping("/toLogin.action")
	public ModelAndView toLogin(HttpServletRequest req,HttpServletResponse response)
	{
		return new ModelAndView(SystemConst.basePagePath+"login");
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 后台登陆
	 * @param req
	 * @return
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年3月10日 下午2:26:51
	 */
	@RequestMapping("/backLogin.action")
	@ResponseBody
	public Result backLogin(HttpServletRequest req) {
		String username = req.getParameter("username");// 账号
		String password = req.getParameter("password");// 密码
//		String code=req.getParameter("code");//验证码
		HttpSession session = req.getSession();
//		if(StringUtils.isEmpty(code))
//		{
//			return new Result(SystemConst.NOT_NULL_CHECKCODE,SystemConst.NOT_NULL_CHECKCODE_MSG);
//		}
//		String sessionCode=(String)session.getAttribute("validateCode");
//		if(!code.equalsIgnoreCase(sessionCode))
//		{
//			return new Result(SystemConst.CHECKCODE_ERROR,SystemConst.CHECKCODE_ERROR_MSG);
//		}
		//检测license
		int attendeesLimit = -1; //参会人数限制
		try
		{
		    /**  0：正常；
		     *  1：无法获取授权文件；
		     *  2：授权文件内容为空；
		     *  3：授权文件签名校验无效；
		     *  4：非授权服务器部署；
		     *  5：授权类型错误；
		     *  6：授权已经过期；*/
		    LicenseChecker checker = new LicenseChecker();
		    int checkResult = checker.checkLicenseFile(req.getServletContext().getRealPath("/") + "WEB-INF/classes/license");
		    switch(checkResult){
		        case 1: 
		            return new Result(APIConstants.LICENSE_NO_FOUND);
		        case 2:
		            return new Result(APIConstants.LICENSE_CONTENT_NULL);
		        case 3:
		            return new Result(APIConstants.LICENSE_SIGN_INVALID);
		        case 4:
		            return new Result(APIConstants.LICENSE_MATCHING_FAIL);
		        case 5:
		            return new Result(APIConstants.LICENSE_TYPE_ERROR);
		        case 6:
		            return new Result(APIConstants.LICENSE_EXPIRED);
		    }
		    attendeesLimit = checker.getAttendeesLimit();
		}
		catch (Exception e)
		{
		    logger.error(logger.getName()+"/backLogin,"+e.getMessage());
            return new Result(APIConstants.CHECK_LICENSE_FAIL);
		}
		try {
			Result result= userInfoService.backLogin(username, password);
			if(result.getStatus()==0)
			{
				User userInfo = (User) result.getData();
				String token = session.getId() + System.currentTimeMillis(); // 暂时以sessionId值作为认证的token值。
				if(userInfo.getIs_super()==0&&userInfo.getAdmin_type()==0)//如果不是超级管理员也不是普通管理员
				{
					//获取用户权限 20170509,会议这一块的权限
					Permission permission=new Permission();
					permission.setUser_id(userInfo.getUser_id());
					Map<String,Object> perMap=permissionService.findUserPermission(permission);
					if(perMap.isEmpty())
					{
						//非管理员没有权限进入系统
						return Result.error(SystemConst.RESTRICTED_ERROR, "非管理员没有权限进入系统！");
					}
					session.setAttribute(SystemConst.FUNCTION_MAP, perMap);
				}
				
				session.setAttribute(SystemConst.LOGIN_USER, userInfo);
				session.setAttribute(SystemConst.TOKEN, token);
				session.setAttribute(SystemConst.ATTENDEESLIMIT, attendeesLimit);
				
			}
			return result;
		}catch (FnfhException e) {
			logger.error(logger.getName()+"/backLogin,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/backLogin,"+e.getMessage());
    		return Result.errorUnknow();
    	}
		
	}	
	
	/**
	 * 
	 * @methodDesc:  
	 * 系统后台退出
	 * @param req
	 * @return
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年3月10日 下午3:19:48
	 */
	@RequestMapping("/backLoginOut.action")
	@ResponseBody
	public Object backLoginOut(HttpServletRequest req) {
		HttpSession session = req.getSession();
		try
		{
			session.removeAttribute(SystemConst.LOGIN_USER);
			session.removeAttribute(SystemConst.TOKEN);
			session.removeAttribute("validateCode");
			session.removeAttribute(SystemConst.FUNCTION_MAP);
			session.invalidate();
			return Result.success();
		}catch(Exception ex)
		{
			return Result.errorUnknow();
		}
	}
	
	@RequestMapping("/noperimission.action")
	public ModelAndView noperimission(HttpServletRequest req,HttpServletResponse response)
	{
		return new ModelAndView("error/no_per");
	}
	
/********************************前台用户操作****************************************************/	
	
	
	
	

}
