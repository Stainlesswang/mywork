package com.dv.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dv.constants.SystemConst;
import com.dv.entity.FnfhPageData;

/**
 * 
 * @classDesc ：
 *	后台页面跳转
 * @creater: 李梦婷
 * @creationDate:2017年3月1日 上午10:48:16
 */
@Controller
@RequestMapping("/backToUrl")
public class BackToUrlController extends FnfhBaseController{
	private static final String userManage=SystemConst.basePagePath+"user/";
	private static final String meetingManage=SystemConst.basePagePath+"meeting/";
	private static final String systemManage=SystemConst.basePagePath+"system/";
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到页面
	 * @return
	 * @creater: 李梦婷
	 * @creationDate:2016年10月29日 上午11:00:00
	 */
	@RequestMapping("/toDo.action")
	public ModelAndView toDo(HttpServletRequest req,Model model)
	{
		String url=req.getParameter("url");
		String toUrl="";
		FnfhPageData pgData=new FnfhPageData(req);
		model.addAttribute("data",pgData);
		
		if(url!=null&&!"".equals(url))
		{
			switch (url) {
			case "userManage":
				toUrl=userManage+"user-management";//用户管理
				break;
			case "meetingManage":
				toUrl=meetingManage+"meeting-materials-push";//会议材料推送
				break;
//			case "meetingAdd":
//				toUrl=meetingManage+"meeting-materials-push-add";//会议材料推送新建
//				break;	
			case "meetingFileMerge":
				toUrl=meetingManage+"meeting-materials-push-merge";//附件合并
				break;
			case "meetingSetting":
				toUrl=meetingManage+"meeting-materials-push-setting";//会议材料-设置
				break;
			case "deviceManage":
				toUrl=meetingManage+"device-management";//设备管理
				break;
//			case "deviceAdd":
//				toUrl=meetingManage+"device-management-add";//设备新建
//				break;
//			case "deviceSeeting":
//				toUrl=meetingManage+"device-management-setting";//设备设置，同会议材料设置
//				break;
			case "deviceAuth":
				toUrl=meetingManage+"device-management-auth";//设备设置
				break;
			case "systemPermissions":
				toUrl=systemManage+"system-permissions";//系统权限 20170525
				break;
			case "meetingFileDownload":
			    toUrl = meetingManage + "meeting-materials-download";
			    break;
			case "meetingTypeManage":
                toUrl = meetingManage + "meetingTypeManage";
                break;
			default:
				toUrl=SystemConst.basePagePath+"index";//后台首页
				break;
			}
			ModelAndView mv=new ModelAndView();
			mv.setViewName(toUrl);
			return new ModelAndView(toUrl);
		}
		else
		{
			return new ModelAndView(SystemConst.basePagePath+"index");
		}
	}

}
