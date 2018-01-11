package com.dv.entity.meeting;

import com.dv.entity.BaseEntity;

/**
 * 
 * @classDesc ：
 *	授权码表
 * @creater: 杨群山
 * @creationDate:2017年5月6日 下午1:15:07
 */
public class Authcode extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mid;//授权码id
	private String authcode;//授权码
	private String createtime;//创建时间
	private Integer status;//使用状态，0未使用1已使用
	private String prefix;
	
	public Authcode()
    {
        super();
    }
    public Authcode (String authcode)
    {
        this.authcode = authcode;
    }
	
	//以下非表中字段
	private String maxcode;
	
	
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	public String getMaxcode() {
		return maxcode;
	}
	public void setMaxcode(String maxcode) {
		this.maxcode = maxcode;
	}
	@Override
	public String toString() {
		return "Authcode [mid=" + mid + ", authcode=" + authcode + ", createtime=" + createtime + "]";
	}
	
	
}
