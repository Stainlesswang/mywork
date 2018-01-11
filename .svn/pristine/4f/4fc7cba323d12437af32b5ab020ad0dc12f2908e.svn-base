package com.dv.dao.meeting;

import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.SubAppMeetingFile;
 
/**
 * 
 * @classDesc ：
 *	会议附件存放表 mapper
 * @creater: 杨群山
 * @creationDate:2017年5月2日 下午1:32:18
 */
@MyBatisRepository
public interface SubAppMeetingFileMapper {
	
	//查找所有会议附件
	public List<SubAppMeetingFile> findSubAppMeetingFile(SubAppMeetingFile subAppMeetingFile);
	//查找会议附件总条数
	public int findSubAppMeetingFileCount(SubAppMeetingFile subAppMeetingFile);
	//增加会议附件
	public int addSubAppMeetingFile(SubAppMeetingFile subAppMeetingFile);
	//根据id删除会议附件
	public int delSubAppMeetingFileByIds(List<String> ids);
	//根据会议id删除会议附件信息
	public int delSubAppMeetingFileByMIds(List<String> ids);
	//批量添加附件信息
	public int batchAddMeetFile(List<SubAppMeetingFile> list);
	//更新附件
	public int modSubAppMeetingFileById(SubAppMeetingFile subAppMeetingFile);
}
