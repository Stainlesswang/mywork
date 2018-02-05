package com.dv.controller.meeting;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dv.controller.FnfhBaseController;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.Permission;
import com.dv.service.meeting.PermissionService;
import com.dv.util.FnfhException;

@Controller
@RequestMapping("/permission")
public class PermissionController extends FnfhBaseController{
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	
	@Resource
	private PermissionService permissionService;
	
	/**
	 * 
	 * @methodDesc:  
	 * 查看权限信息
	 * @param req
	 * @param permission
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午4:47:00
	 */
	@RequestMapping(value="/background_showPermission.action",method={RequestMethod.POST})
	@ResponseBody
	public Result showPermission(HttpServletRequest req,Permission permission){
		FnfhPageData pageData=new FnfhPageData(req);
		try {
			return permissionService.findPermission(permission, pageData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showPermission,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/showPermission,"+e.getMessage());
    		return Result.errorUnknow();
		}
	} 
	
	/**
	 * 
	 * @methodDesc:  
	 * 设置权限信息
	 * @param req
	 * @param permission
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午4:48:14
	 */
	@RequestMapping(value="/background_setPermission.action",method={RequestMethod.POST})
	@ResponseBody
	public Result setPermission(HttpServletRequest req,Permission permission){
		FnfhPageData pageData=new FnfhPageData(req);
		try {
			return permissionService.setPermission(permission, pageData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/setPermission,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/setPermission,"+e.getMessage());
    		return Result.errorUnknow();
		}
	} 
	

}
