package com.dv.controller.user;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dv.constants.SystemConst;
import com.dv.controller.FnfhBaseController;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.user.Organize;
import com.dv.entity.user.User;
import com.dv.service.user.OrgManageService;
import com.dv.service.user.UserManageService;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;

import net.sf.json.JSONObject;

/**
 * 
 * @classDesc ：
 *	用户管理Controller
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午2:37:37
 */
@Controller
@RequestMapping("/userinfo")
public class UserManageController extends FnfhBaseController{
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private UserManageService userService;
	
	@Resource
    private OrgManageService orgService;
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据组织id获取用户列表信息
	 * @param req
	 * @param userInfo
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月2日 下午5:30:25
	 */
	@RequestMapping(value="/background_showUserInfo.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result showUserInfo(HttpServletRequest req,User userInfo)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try {
			return userService.showUserInfo(userInfo, pgdata);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showUserInfo,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/showUserInfo,"+e.getMessage());
    		return Result.errorUnknow();
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到用户新增或修改界面
	 * @param req
	 * @param model
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午3:43:05
	 */
	@RequestMapping("/background_userAddOrModifyView.action")
	public ModelAndView userAddOrModifyView(HttpServletRequest req,Model model)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		String selectOrgId=pgdata.getString("selectOrgId");
        //如果选中的组织id不为空，查询组织名称，直接传中文的组织名称IE可能会报400的错
        if(!StringUtils.isEmpty(selectOrgId))
        {
            int _selectOrgId=CommonMethod.StringToInt(selectOrgId, -1);
            if(_selectOrgId!=-1)
            {
                //获取组织信息
                Organize organize=new Organize();
                organize.setOrg_id(_selectOrgId);
                try {
                    Result result=orgService.getObjInstane(organize);
                    if(result.getData()!=null)
                    {
                        organize=(Organize)result.getData();
                        pgdata.put("selectOrgName", organize.getOrg_name());
                    }
                } catch (FnfhException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
		model.addAttribute("data",pgdata);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"user/user-add-modify");
		return mv;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 编辑用户信息
	 * @param req
	 * @param model
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月24日 上午10:36:48
	 */
	@RequestMapping("/background_editAccountView")
	public ModelAndView editAccountView(HttpServletRequest req,Model model,
			HttpServletResponse response)
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
		
		FnfhPageData pgdata=new FnfhPageData(req);
		pgdata.put("user_id", loginUser.getUser_id());
		model.addAttribute("data",pgdata);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"user/edit-account");
		return mv;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 添加用户信息
	 * @param req
	 * @param user
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:23:44
	 */
	@RequestMapping(value = "/background_addUserInfo.action", method = {RequestMethod.POST})
	@ResponseBody
	public Object addUserInfo(HttpServletRequest req, User userInfo)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try {
			return userService.addUserInfo(userInfo,pgdata);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/addUserInfo,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	} catch (Exception e) {
    		logger.error(logger.getName()+"/addUserInfo,"+e.getMessage());
    		return Result.errorUnknow();
    	}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 删除用户信息
	 * @param req
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:26:43
	 */
	@RequestMapping(value = "/background_delUsers.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result delUserByIds(HttpServletRequest req)
	{
		String ids=req.getParameter("ids");
		try
		{
//			return userService.delUserByIds(ids);
			return userService.delUserAllInfoByIds(ids);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/delUserByIds,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/delUserByIds,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改角色信息
	 * @param role
	 * @return
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年3月13日 下午2:05:11
	 */
	@RequestMapping(value = "/background_modUserInfo.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result modUserById(User userInfo,HttpServletRequest req)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		try
		{
			return userService.modUserById(userInfo,pgdata);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/modUserById,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/modUserById,"+ex.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param req
	 * @param userInfo
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:32:29
	 */
	@RequestMapping(value = "/background_getObjInstane.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result getObjInstane(HttpServletRequest req,User userInfo)
	{
		try
		{
			return userService.getObjInstane(userInfo);
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
	 * 校验用户名是否存在
	 * @param req
	 * @param userInfo
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午1:58:40
	 */
	@RequestMapping(value = "/background_isExistName.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result checkUserNameExist(HttpServletRequest req,User userInfo)
	{
		try
		{
			return userService.checkUserNameExist(userInfo);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/checkUserNameExist,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/checkUserNameExist,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改密码
	 * @param req
	 * @param userInfo
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午2:04:35
	 */
	@RequestMapping(value = "/background_resetPwd.action", method = {RequestMethod.POST})
	@ResponseBody
	public Result resetPwd(HttpServletRequest req,User userInfo)
	{
		try
		{
			return userService.resetPwd(userInfo);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/resetPwd,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/resetPwd,"+e.getMessage());
			return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到导入用户Excel view
	 * @param req
	 * @param model
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午4:19:21
	 */
	@RequestMapping("/background_importUserView.action")
	public ModelAndView importUserView(HttpServletRequest req,Model model)
	{
		FnfhPageData pgdata=new FnfhPageData(req);
		model.addAttribute("data",pgdata);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"user/user-import");
		return mv;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 导入用户数据
	 * @param req
	 * @param resp
	 * @param uploadFiles
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午4:05:35
	 */
	@RequestMapping(value="/background_importUserExcel.action",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String importUserExcel(HttpServletRequest req,
			HttpServletResponse resp,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFiles){
		Result result=new Result();
		try
		{
			result=userService.importUserExcel(req,uploadFiles);
		}
		catch (FnfhException e) {
			logger.error(logger.getName()+"/importUserExcel,"+e.getMessage());
			result=Result.error(e.getCode(), e.getMessage());
    	}
		catch (Exception e) {
			logger.error(logger.getName()+"/importUserExcel,"+e.getMessage());
			result=Result.errorUnknow();
		}
		String jsonStr="[]";
		jsonStr = String.valueOf(JSONObject.fromObject(result));
		return jsonStr;
	}
	
	
	
}
