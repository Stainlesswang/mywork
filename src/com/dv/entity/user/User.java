package com.dv.entity.user;

import com.dv.entity.BaseEntity;

public class User extends BaseEntity {
	/**
	 * 
	 * @classDesc ： 人员信息实体类
	 * @creater: 杨群山
	 * @creationDate:2017年5月2日 下午
	 */
	private static final long serialVersionUID = 1L;
	private String user_id;// 用户id
	private String user_name;// 账号,用户名
	private String real_name;//真实姓名
	private String password;// 密码
	private Integer sex;// 性别，0男1女2未知
	private String position;// 职务
	private Integer org_id;// 所属组织(部门)
	private String mobile;// 手机号
	private String email;// 邮箱
	private Integer admin_type;// 管理员类型，0普通人员，1超级管理员，2普通管理员
	private String savetime;// 保存时间
	private String last_update_time;//最近更新时间
	
	private String tel;//固定电话
	private String addr;//住址
	private String remark;//用户说明
	private Integer is_super;//是否是超级管理员，0否1是
	private Integer is_temp;//是否是临时用户，0否1是
	// 非定义字段
	private String org_name;// 组织名称
	private String parent_code;//上级组织id集合，使用-隔开
	private boolean existFlag;//是否是新增用户，true是，false表示修改用户
	

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAdmin_type() {
		return admin_type;
	}

	public void setAdmin_type(Integer admin_type) {
		this.admin_type = admin_type;
	}

	public String getSavetime() {
		return savetime;
	}

	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	

	

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}
	

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	
	public Integer getIs_super() {
		return is_super;
	}

	public void setIs_super(Integer is_super) {
		this.is_super = is_super;
	}
	

	

	

	public boolean isExistFlag() {
		return existFlag;
	}

	public void setExistFlag(boolean existFlag) {
		this.existFlag = existFlag;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", real_name=" + real_name + ", password="
				+ password + ", sex=" + sex + ", position=" + position + ", org_id=" + org_id + ", mobile=" + mobile
				+ ", email=" + email + ", admin_type=" + admin_type + ", savetime=" + savetime + ", org_name="
				+ org_name + "]";
	}

    public Integer getIs_temp()
    {
        return is_temp;
    }

    public void setIs_temp(Integer is_temp)
    {
        this.is_temp = is_temp;
    }

	

}
