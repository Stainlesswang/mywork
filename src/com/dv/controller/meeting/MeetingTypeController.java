/*
 * 文件名称：          MeetingTypeController.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 上午9:46:12
 * 版权所有：          
 */

package com.dv.controller.meeting;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dv.constants.SystemConst;
import com.dv.controller.FnfhBaseController;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.MeetingType;
import com.dv.service.meeting.MeetingTypeService;

/**
 * 会议类型控制
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年9月27日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
@Controller
@RequestMapping("/meetingType")
public class MeetingTypeController extends FnfhBaseController
{

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource 
    private MeetingTypeService meetingTypeService;
    
    /**
     * 获取会议类型
     * @param req
     * @param meeting_type
     * @return
     */
    @RequestMapping(value="/getMeetingType")
    @ResponseBody
    public Result findMeetingTyp(HttpServletRequest req, MeetingType meeting_type)
    {
        FnfhPageData pgData = new FnfhPageData(req);
        meeting_type.setStart(pgData.getOffset());
        meeting_type.setLimit(pgData.getLimit());
        if (!StringUtils.isEmpty(pgData.getSearch()))
        {
            meeting_type.setSearch(pgData.getSearch());
        }
        return meetingTypeService.findMeetingTyp(meeting_type);
    }
    
  //添加会议类型页面
    @RequestMapping(value="/meetTypeAddView",method={RequestMethod.GET})
    public ModelAndView meetTypeAddView(HttpServletRequest req,HttpServletResponse response,Model model){
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
        String meetingTypeId = pgData.getString("meetingTypeId");
        if (meetingTypeId != null)
        {
            MeetingType meetingType = meetingTypeService.getMeetingTypeInfoById(meetingTypeId);
            model.addAttribute("data", meetingType);
        }
        //model.addAttribute("data",pgData);
        ModelAndView mv=new ModelAndView();
        mv.setViewName(SystemConst.basePagePath+"meeting/meeting-type-add");
        return mv;
    }
    
    
    /**
     * 添加或者修改会议类型
     * @param meetingType
     * @return
     */
    @RequestMapping(value="/addorModifyMeetingType")
    @ResponseBody
    public Result addOrModifyMeetingType(MeetingType meetingType)
    {
        return meetingTypeService.addOrModifyMeetingType(meetingType, null);
    }
    
    /**
     * 删除会议类型
     * @param ids
     * @return
     */
    @RequestMapping(value="/delMeetingType")
    @ResponseBody
    public Result delMeetingType(@RequestParam(value="ids[]", required = false) String[] ids)
    {
        return meetingTypeService.delMeetingTypeByIds(ids == null ? null : Arrays.asList(ids));
    }
}
