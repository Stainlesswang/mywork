package com.dv.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.dv.entity.user.Organize;
import com.dv.service.user.OrgManageService;
import com.dv.util.FnfhException;

/**
 * 
 * @classDesc ：
 *	组织管理controller
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午2:38:21
 */
@Controller
@RequestMapping("/orginfo")
public class OrgManageController extends FnfhBaseController{
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private OrgManageService orgService;
	
	/**
	 * 
	 * @methodDesc:  
	 * 显示组织机构，树形
	 * @param req
	 * @param organize
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月2日 下午3:07:40
	 */
	@RequestMapping(value="/background_showOrgTree.action", produces = "text/html;charset=UTF-8",method = {RequestMethod.POST})
	@ResponseBody
	public String showOrgTree(HttpServletRequest req,Organize organize)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try {
			return orgService.showOrganizeTree(organize, pgdata);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showOrgTree,"+e.getMessage());
			return "[]";
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/showOrgTree,"+e.getMessage());
    		return "[]";
    	}
	}
	
	@RequestMapping(value="/background_showAllOrgTree.action", produces = "text/html;charset=UTF-8",method = {RequestMethod.POST})
	@ResponseBody
	public String showAllOrgTree(HttpServletRequest req,Organize organize)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try {
			return orgService.showAllOrganizeTree(organize, pgdata);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showOrgTree,"+e.getMessage());
			return "[]";
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/showOrgTree,"+e.getMessage());
    		return "[]";
    	}
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 组织新增或者修改弹出框
	 * @param req
	 * @param model
	 * @return 
	 * @creater: 李梦婷
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月3日 上午9:33:02
	 */
	@RequestMapping("/background_orgAddOrModifyView.action")
	public ModelAndView orgAddOrModifyView(HttpServletRequest req,Model model)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		model.addAttribute("data",pgdata);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"user/organize-add-modify");
		return mv;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 添加组织信息
	 * @param req
	 * @param organize
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 上午11:10:12
	 */
	@RequestMapping(value = "/background_addOrgInfo.action", method = {RequestMethod.POST})
	@ResponseBody
	public Object addOrgInfo(HttpServletRequest req, Organize organize)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try {
			return orgService.addOrgInfo(organize,pgdata);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/addOrgInfo,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/addOrgInfo,"+e.getMessage());
    		return Result.errorUnknow();
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改组织信息
	 * @param req
	 * @param organize
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午1:53:41
	 */
	@RequestMapping(value = "/background_modOrgInfo.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result modOrgInfoById(HttpServletRequest req,Organize organize)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try
		{
			return orgService.modOrgInfoById(organize,pgdata);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/modOrgInfoById,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/modOrgInfoById,"+ex.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param req
	 * @param organize
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午1:59:35
	 */
	@RequestMapping(value = "/background_getObjInstane.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result getObjInstane(HttpServletRequest req,Organize organize)
	{
		try
		{
			return orgService.getObjInstane(organize);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id集合删除组织信息
	 * @param req
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午2:09:34
	 */
	@RequestMapping(value = "/background_delOrgs.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result delOrgsByIds(HttpServletRequest req)
	{
		String ids=req.getParameter("ids");
		try
		{
			return orgService.delOrgsByIds(ids);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/delOrgsByIds,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/delOrgsByIds,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 删除组织信息以及人员信息
	 * @param req
	 * @return 
	 * @creater: 李梦婷
	 * @deprecated 暂未启用
	 * @creationDate:2017年5月12日 上午10:19:42
	 */
	@RequestMapping(value = "/background_delOrgsAndUsers.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result delOrgsAndUsers(HttpServletRequest req)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try
		{
			return orgService.delOrgsAndUserByIds(pgdata);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/delOrgsAndUserByIds,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/delOrgsAndUserByIds,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	
}
