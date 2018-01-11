package com.dv.controller.meeting;

import java.io.IOException;

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
import com.dv.entity.meeting.Authcode;
import com.dv.service.meeting.AuthcodeService;
import com.dv.util.FnfhException;

/**
 * 
 * @classDesc ：
 *	授权码controller
 * @creater: 杨群山
 * @creationDate:2017年5月6日 上午11:01:32
 */
@Controller
@RequestMapping("/authcode")
public class AuthcodeController extends FnfhBaseController {
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private AuthcodeService authcodeService;
	
	/**
	 * 
	 * @methodDesc:  
	 * 查询授权码信息
	 * @param req
	 * @param authcode
	 * @return 
	 * @creater: 杨群山
	 * @creationDate:2017年5月6日 下午3:07:40
	 */
	@RequestMapping(value="/background_showauthcode.action",method={RequestMethod.POST})
	@ResponseBody
	public Result showauthcode(HttpServletRequest req,Authcode authcode){
		FnfhPageData pageData=new FnfhPageData(req);
		try {
			return authcodeService.findauthcode(authcode, pageData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showauthcode,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/showauthcode,"+e.getMessage());
    		return Result.errorUnknow();
		}
	}
	/**
	 * 
	 * @methodDesc:  
	 * 跳转到授权码信息页面
	 * @param req
	 * @return
	 * @return  
	 * @creater: 杨群山
	 * @creationDate:2017年5月8日 下午10:51:01
	 */
	@RequestMapping(value="/background_AuthDetailView.action",method={RequestMethod.GET})
	public ModelAndView AuthDetailView(HttpServletRequest req,Model model){
		FnfhPageData pgData=new FnfhPageData(req);
		model.addAttribute("data",pgData);
		ModelAndView mv=new ModelAndView();
		mv.setViewName(SystemConst.basePagePath+"meeting/device-management-auth");
		return mv;	
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 创建授权码
	 * @param req
	 * @param authcode
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月9日 下午5:13:12
	 */
	@RequestMapping(value="/background_addAuthCode.action",method={RequestMethod.POST})
	@ResponseBody
	public Result addAuthCode(HttpServletRequest req,Authcode authcode){
		FnfhPageData pgData=new FnfhPageData(req);
		try {
			return  authcodeService.addAuthCode(authcode,pgData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/addAuthCode,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/addAuthCode,"+e.getMessage());
    		return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据授权码集合删除授权码信息
	 * @param req
	 * @param authcode
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月10日 上午9:25:48
	 */
	@RequestMapping(value="/background_delAuthCode.action",method={RequestMethod.POST})
	@ResponseBody
	public Result delAuthCodeByCodes(HttpServletRequest req,Authcode authcode){
		FnfhPageData pgData=new FnfhPageData(req);
		try {
			return  authcodeService.delAuthCodeByCodes(pgData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/delAuthCodeByCodes,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/delAuthCodeByCodes,"+e.getMessage());
    		return Result.errorUnknow();
		}
	}
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param req
	 * @param authcode
	 * @return 
	 * @creater: 杨群山
	 * @creationDate:2017年5月8日 下午3:07:40
	 */
	@RequestMapping(value="/background_getObjInstane.action",method={RequestMethod.POST})
	@ResponseBody
	public Result getObjInstane(HttpServletRequest req,Authcode authcode){
		try {
			return authcodeService.getObjInstane(authcode);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/getObjInstane,"+e.getMessage());
    		return Result.errorUnknow();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 查询没有使用的授权码
	 * @param req
	 * @param authcode
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 上午10:33:04
	 */
	@RequestMapping(value="/background_showNoUseCode.action",method={RequestMethod.POST})
	@ResponseBody
	public Result showNoUseCode(HttpServletRequest req,Authcode authcode){
		FnfhPageData pageData=new FnfhPageData(req);
		try {
			return authcodeService.findNoUseCode(authcode, pageData);
		} catch (FnfhException e) {
			logger.error(logger.getName()+"/showNoUseCode,"+e.getMessage());
			return Result.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error(logger.getName()+"/showNoUseCode,"+e.getMessage());
    		return Result.errorUnknow();
		}
	}
	
	/**
     * 判断授权码使用人数是否超过了授权人数
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value="checkUsedAuthcodeNum")
    @ResponseBody
    public Result checkUsedAuthcodeNum(HttpServletRequest req,HttpServletResponse response)
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
        int limitUserNum = (Integer)req.getSession().getAttribute(SystemConst.ATTENDEESLIMIT);
        Authcode ac = new Authcode();
        ac.setStatus(1);
        int usedAuthCodeNum = authcodeService.findAuthcodeNum(ac);
        if (usedAuthCodeNum >= limitUserNum)
        {
            return Result.error(SystemConst.CHECKATTENDEESLIMIT_ERROR, SystemConst.CHECKATTENDEESLIMIT_ERROR_MSG+":"+ limitUserNum);
        }
        return Result.success();
    }
}
