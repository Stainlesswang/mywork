package com.dv.entity.meeting;

import java.util.List;

import org.springframework.util.StringUtils;

import com.dv.entity.BaseEntity;
import com.dv.entity.user.User;

/**
 * 
 * @classDesc ： 会议主表
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午1:12:52
 */
public class SubAppMeeting extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6300962283296294532L;

	private String mid;// 会议唯一ID
	private String meet_time;// '会议开始时间
	private String meet_name;// 会议标题
	private String meet_type;//会议类型
	private String meet_addr;// 会议地点
	private String meet_attend;// 参会领导
	private String meet_attend_ids;//参会领导id集合
	private String remark;// 备注信息、
	private String filename;// 附件名称,暂未使用
	private String attach_path;// 附件存放路径,暂未使用
	private String last_update_time;// 最后修改时间
	private Integer is_ipad;// 是否推送ipad
	private String create_time;//创建时间
//	private Integer meet_status;//会议状态:1.未推送文件,2.已推送文件,3.全部文件,4.业务类型,5.查看配置
	private String user_id;//用户id,拟稿人id
	private String draft_time;//拟稿时间
	private String push_time;//推送时间
	private Integer status;//会议状态，0未启用1已启用2已停用
	private String master;//主持人
	private String end_time;//会议结束时间
	//非定义字段
	private String user_name;//拟稿人 账号
	private String real_name;//拟稿人真实姓名
	private String meet_type_name;//会议类型名称
	
	
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		if(!StringUtils.isEmpty(end_time))
		{
			end_time=end_time.replace(".0", "");
		}
		this.end_time = end_time;
	}

	private List<User> attendUserList;//参会人员集合
	
	
	private List<SubAppMeetingFile> fileList;//附件数据
	
	public SubAppMeeting()
    {
        super();
    }
    
    

    public SubAppMeeting(String mid)
    {
        super();
        this.mid = mid;
    }
	
	public String getPush_time() {
		return push_time;
	}

	public void setPush_time(String push_time) {
		this.push_time = push_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDraft_time() {
		return draft_time;
	}

	public void setDraft_time(String draft_time) {
		if(!StringUtils.isEmpty(draft_time))
		{
			draft_time=draft_time.replace(".0", "");
		}
		this.draft_time = draft_time;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

//	public Integer getMeet_status() {
//		return meet_status;
//	}
//
//	public void setMeet_status(Integer meet_status) {
//		this.meet_status = meet_status;
//	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		if(!StringUtils.isEmpty(create_time))
		{
			create_time=create_time.replace(".0", "");
		}
		this.create_time = create_time;
	}

	public Integer getIs_ipad() {
		return is_ipad;
	}

	public void setIs_ipad(Integer is_ipad) {
		this.is_ipad = is_ipad;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMeet_time() {
		return meet_time;
	}

	public void setMeet_time(String meet_time) {
		if(!StringUtils.isEmpty(meet_time))
		{
			meet_time=meet_time.replace(".0", "");
		}
		this.meet_time = meet_time;
	}

	public String getMeet_name() {
		return meet_name;
	}

	public void setMeet_name(String meet_name) {
		this.meet_name = meet_name;
	}

	public String getMeet_addr() {
		return meet_addr;
	}

	public void setMeet_addr(String meet_addr) {
		this.meet_addr = meet_addr;
	}

	public String getMeet_attend() {
		return meet_attend;
	}

	public void setMeet_attend(String meet_attend) {
		this.meet_attend = meet_attend;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAttach_path() {
		return attach_path;
	}

	public void setAttach_path(String attach_path) {
		this.attach_path = attach_path;
	}

	public String getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(String last_update_time) {
		if(!StringUtils.isEmpty(last_update_time))
		{
			last_update_time=last_update_time.replace(".0", "");
		}
		this.last_update_time = last_update_time;
	}
	

	public String getMeet_attend_ids() {
		return meet_attend_ids;
	}

	public void setMeet_attend_ids(String meet_attend_ids) {
		this.meet_attend_ids = meet_attend_ids;
	}

	
	public List<SubAppMeetingFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<SubAppMeetingFile> fileList) {
		this.fileList = fileList;
	}
	

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	public List<User> getAttendUserList() {
		return attendUserList;
	}

	public void setAttendUserList(List<User> attendUserList) {
		this.attendUserList = attendUserList;
	}

	@Override
	public String toString() {
		return "SubAppMeeting [mid=" + mid + ", meet_time=" + meet_time + ", meet_name=" + meet_name + ", meet_addr="
				+ meet_addr + ", meet_attend=" + meet_attend + ", meet_attend_ids=" + meet_attend_ids + ", remark="
				+ remark + ", filename=" + filename + ", attach_path=" + attach_path + ", last_update_time="
				+ last_update_time + ", is_ipad=" + is_ipad + ", create_time=" + create_time + ", user_id=" + user_id
				+ ", draft_time=" + draft_time + ", push_time=" + push_time + ", status=" + status + ", master="
				+ master + ", end_time=" + end_time + ", user_name=" + user_name + ", real_name=" + real_name
				+ ", attendUserList=" + attendUserList + ", fileList=" + fileList + "]";
	}

    public String getMeet_type()
    {
        return meet_type;
    }

    public void setMeet_type(String meet_type)
    {
        this.meet_type = meet_type;
    }

    public String getMeet_type_name()
    {
        return meet_type_name;
    }

    public void setMeet_type_name(String meet_type_name)
    {
        this.meet_type_name = meet_type_name;
    }

   

 
	

 
}
