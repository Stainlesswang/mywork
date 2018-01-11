package com.dv.dao.meeting;

import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.MeetingType;
@MyBatisRepository
public interface MeetingTypeMapper {
    //添加会议类型
    public int addMeetingType(MeetingType meetingType);
    //查询会议类型
    public List<MeetingType> findMeetingType(MeetingType meetingType);
    
    //查询会议类型的总数
    public int findMeetingTypeCount(MeetingType meetingType);
    
    //删除类型
    public int deleteIssue(MeetingType meetingType);
    
    //更新会议类型
    public int update(MeetingType meetingType);
    //批量删除会议类型
    public void deleteMeetingTypeByIdList(List meetingTypeIdList);
}
