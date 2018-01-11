package com.dv.dao.meeting;

import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.SubAppMeeting;

/**
 * 
 * @classDesc ：
 *	会议主表 mapper
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午1:32:18
 */
@MyBatisRepository
public interface SubAppMeetingMapper {
	
	//查找所有会议
	public List<SubAppMeeting> findSubAppMeeting(SubAppMeeting subAppMeeting);
	//查找会议总条数
	public int findSubAppMeetingCount(SubAppMeeting subAppMeeting);
	//增加会议
	public int addSubAppMeeting(SubAppMeeting subAppMeeting);
	//根据id删除会议
	public int delSubAppMeetingByIds(List<String> ids);
	//修改会议
	public int modSubAppMeetingById(SubAppMeeting subAppMeeting);
	//根据用户id集合清空一些拟稿人、参会人信息
	public int clearUserInfoByUserId(List<String> ids);
}
