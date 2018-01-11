package com.dv.controller.meeting;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dv.constants.SystemConst;
import com.dv.controller.FnfhBaseController;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.SubAppMeetingFile;
import com.dv.entity.user.User;
import com.dv.service.meeting.SubAppMeetingFileService;
import com.dv.util.FnfhException;

/**
 * 
 * @classDesc ：
 *	会议附件controller
 *	主要是文件的上传与下载
 * @creater: 杨群山
 * @creationDate:2017年5月3日 上午11:01:32
 */
@Controller
@RequestMapping("/subAppMeetingFile")
public class SubAppMeetingFileController extends FnfhBaseController { 
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private SubAppMeetingFileService subAppMeetingFileService;
	
	
	@RequestMapping(value="getMeetingFile")
	@ResponseBody
	public Result getMeetingFile(HttpServletRequest req,SubAppMeetingFile subAppMeetingFile,
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
        
        FnfhPageData pageData=new FnfhPageData(req);
        try {
            return subAppMeetingFileService.findsubAppMeetingFile(subAppMeetingFile, pageData);
        } catch (FnfhException e) {
            logger.error(logger.getName()+"/showMeeting,"+e.getMessage());
            return Result.error(e.getCode(), e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(logger.getName()+"/showMeeting,"+e.getMessage());
            return Result.errorUnknow();
        }
	}
	
	@RequestMapping (value="deleteMeetingFile")
	@ResponseBody
	public Result deleteMeetingfile(String fileMid)
	{
	    try
        {
            return subAppMeetingFileService.delsubAppMeetingFile("[\"" + fileMid + "\"]");
        }
        catch(FnfhException e)
        {
            return Result.error(e.getCode(), e.getMessage());
        }
	}
	
}
