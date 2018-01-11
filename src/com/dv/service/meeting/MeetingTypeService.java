/*
 * 文件名称：          MeetingIssueService.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 上午9:44:31
 * 版权所有：          
 */

package com.dv.service.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.dao.meeting.MeetingTypeMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.MeetingType;
import com.dv.entity.meeting.SubAppMeeting;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * 会议议题service
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年7月25日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
@Service
public class MeetingTypeService
{

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private MeetingTypeMapper meetingTypeMapper;
    
    public Result addOrModifyMeetingType(MeetingType meetingType, FnfhPageData pgdata)
    {
        int result = 0;
        if (meetingType == null)
        {
            return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
        }
        try
        {
            if (StringUtils.isEmpty(meetingType.getId()))
            {
                //添加
                result = meetingTypeMapper.addMeetingType(meetingType);
            }
            else
            {
                //更新
                result = meetingTypeMapper.update(meetingType);
            }
            if (result < 1 )
            {
                return Result.error(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG); 
            }
        }
        catch(Exception e)
        {
            logger.error(logger.getName() + "/addOrModifyMeetingType: " + e.getMessage());
            return Result.error(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG);
        }
        return Result.success();
    }
    
    public Result findMeetingTyp(MeetingType meeting_type)
    {
        Result result=new Result();
        List<MeetingType> list= meetingTypeMapper.findMeetingType(meeting_type);
        int totalCount = meetingTypeMapper.findMeetingTypeCount(meeting_type);
        result.setRows(list);
        result.setTotal(totalCount);
        return result;  
    }
    
    /**
     * 根据会议类型id获取会议类型信息
     * @param meetingTypeId
     * @return
     */
    public MeetingType getMeetingTypeInfoById(String meetingTypeId)
    {
        if (meetingTypeId == null)
        {
            return null;
        }
        MeetingType meetingType = new MeetingType();
        meetingType.setId(meetingTypeId);
        List<MeetingType>   list = meetingTypeMapper.findMeetingType(meetingType);
        if (!StringUtils.isEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }

    /**
     * 批量删除会议类型
     * @param meetingTypeIdList 会议类型id集合
     * @return
     */
    public Result delMeetingTypeByIds(List meetingTypeIdList)
    {
        if (meetingTypeIdList == null)
        {
            return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
        }
        try
        {
            meetingTypeMapper.deleteMeetingTypeByIdList(meetingTypeIdList);
        }
        /*catch (MySQLIntegrityConstraintViolationException e)
        {
            
        }*/
        catch(Exception e)
        {
            logger.error(logger.getName() + "/delMeetingTypeByIds: " + e.getMessage());
            if (e.getCause().toString().indexOf("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row:") >= 0)
            {
                return new Result(APIConstants.MEETING_TYPE_IN_USE);
            }
            return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
        }
        return Result.success();
    }
}
