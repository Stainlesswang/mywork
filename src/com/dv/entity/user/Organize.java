package com.dv.entity.user;

import com.dv.entity.BaseEntity;

public class Organize extends BaseEntity {
	/**
	 * 
	 * @classDesc ： 组织实体类
	 * @creater: 杨群山
	 * @creationDate:2017年5月2日 下午
	 */
	private static final long serialVersionUID = 1L;
	private Integer org_id;// 组织id
	private String org_name;// 组织名称
	private String parent_code;// 上级组织id集合
	private Integer parent_id;// 上级组织id
	private String remark;//备注
	//非定义字段
	private String parent_name;//组织名称(上级组织关联)
	
	
	
	public Organize() {
		
	}

	public Organize(Integer org_id) {
		this.org_id = org_id;
	}

	public Organize(Integer org_id, String org_name, String parent_code, Integer parent_id) {
		this.org_id = org_id;
		this.org_name = org_name;
		this.parent_code = parent_code;
		this.parent_id = parent_id;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Organize [org_id=" + org_id + ", org_name=" + org_name + ", parent_code=" + parent_code + ", parent_id="
				+ parent_id + "]";
	}

}
