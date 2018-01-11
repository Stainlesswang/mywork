package com.dv.dao.meeting;

import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.MeetAttend;

/**
 * 
 * @classDesc ：
 *	参会人员mapper
 * @creater: 李梦婷
 * @deprecated 暂未使用
 * @creationDate:2017年5月9日 下午2:15:49
 */
@MyBatisRepository
public interface MeetAttendMapper {
	//查找参会人员
	public List<MeetAttend> findMeetAttend(MeetAttend meetAttend);
	//根据会议id集合删除参会人员信息
	public int delMeetAttendByMids(List<String> list);
	//批量添加权限信息
	public int batchAddMeetAttend(List<MeetAttend> list);
	//根据用户id删除参会人员信息
	public int delMeetAttendByUserIds(List<String> list);
	
	
}
