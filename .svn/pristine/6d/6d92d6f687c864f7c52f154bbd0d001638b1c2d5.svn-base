package com.dv.entity.meeting;

import com.dv.entity.BaseEntity;

/**
 * 
 * @classDesc ：
 *	系统权限实体类
 * @creater: 李梦婷
 * @creationDate:2017年5月8日 下午3:16:54
 */
public class Permission extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7614257966382583701L;
	
	public static int perType=1;//会议资料推送和设备管理默认的类型
	public static int perSubType_admin=1;//本模块管理员
	public static int perSubType_ipad=2;//可维护IPAD信息人员,设备管理员
	public static int perSubType_meet=3;//可新建会议资料人员，会议推送
	public static int perSubType_usermanage=4;//用户管理员
	
	private Integer  per_id;//权限id
	private Integer per_type;//权限类型，1会议材料推送，2 其他分类
	private String per_name;//权限类型名称
	private Integer per_subtype;//权限类型中的下级权限类型
	private String user_id;//用户id
	
	private String savetime;//保存时间
	
	//以下非表中字段
	private String user_name;//用户名
	private String real_name;//拥有权限的人的真实姓名
	public Integer getPer_id() {
		return per_id;
	}
	public void setPer_id(Integer per_id) {
		this.per_id = per_id;
	}
	public Integer getPer_type() {
		return per_type;
	}
	public void setPer_type(Integer per_type) {
		this.per_type = per_type;
	}
	public String getPer_name() {
		return per_name;
	}
	public void setPer_name(String per_name) {
		this.per_name = per_name;
	}
	public Integer getPer_subtype() {
		return per_subtype;
	}
	public void setPer_subtype(Integer per_subtype) {
		this.per_subtype = per_subtype;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	@Override
	public String toString() {
		return "Permission [per_id=" + per_id + ", per_type=" + per_type + ", per_name=" + per_name + ", per_subtype="
				+ per_subtype + ", user_id=" + user_id + ", savetime=" + savetime + ", user_name=" + user_name
				+ ", real_name=" + real_name + "]";
	}
	
	
	
	
	
}
