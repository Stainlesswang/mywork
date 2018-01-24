package com.dv.entity;

/**
 * 
 * @classDesc ：
 *	 文件实体类
 * @creater: 李梦婷
 * @creationDate:2017年1月13日 下午15:15:31
 */
public class FileAttach extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2867093957121368333L;
	private String attach_path;   //附件地址
	private String attach_name;  //附件名称
	private String attach_size;  //附件大小
	private String attach_realname;//附件实际名字
	private int download_num;   //附件下载次数
	
	private String prefix;//文件前缀，即类型前名字
	private String suffix;//文件后缀，类型名
	private Integer attach_type;  //附件类型,0普通，1合并后
	
	private String token;//用来区分文件的
	
	private String uploader_id;//上传者id
	
	private String last_update_time;// 最后修改时间
	
	
	
	public String getAttach_path() {
		return attach_path;
	}
	public void setAttach_path(String attach_path) {
		this.attach_path = attach_path;
	}
	public String getAttach_name() {
		return attach_name;
	}
	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}
	public String getAttach_size() {
		return attach_size;
	}
	public void setAttach_size(String attach_size) {
		this.attach_size = attach_size;
	}
	public int getDownload_num() {
		return download_num;
	}
	public void setDownload_num(int download_num) {
		this.download_num = download_num;
	}
	public String getAttach_realname() {
		return attach_realname;
	}
	public void setAttach_realname(String attach_realname) {
		this.attach_realname = attach_realname;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public Integer getAttach_type() {
		return attach_type;
	}
	public void setAttach_type(Integer attach_type) {
		this.attach_type = attach_type;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "FileAttach [attach_path=" + attach_path + ", attach_name=" + attach_name + ", attach_size="
				+ attach_size + ", attach_realname=" + attach_realname + ", download_num=" + download_num + ", prefix="
				+ prefix + ", suffix=" + suffix + ", attach_type=" + attach_type + "]";
	}
    public String getUploader_id()
    {
        return uploader_id;
    }
    public void setUploader_id(String uploader_id)
    {
        this.uploader_id = uploader_id;
    }
    public String getLast_update_time()
    {
        return last_update_time;
    }
    public void setLast_update_time(String last_update_time)
    {
        this.last_update_time = last_update_time;
    }
	
	
	

}
