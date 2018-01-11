package com.dv.entity;

import org.springframework.util.StringUtils;


/**
 * 
 * @classDesc ： 系统文件实体类
 * @creater: 杨群山
 * @creationDate:2017年3月21日 上午
 */
public class SysFile extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String file_id;// 文件id
	private String admin_id;// 管理员id
	private String file_type;// 文件类型,doc,txt,xls,xlsx,获取文件的后缀名
	private String savetime;// 保存时间
	private String file_name;// 文件名称,在前端显示的名称
	private String file_realname;// 文件真实名称，保存的文件名
	private String file_path;// 文件保存路径
	private String content;// 备注
	private String parent_path;//上级目录
	
	private String creater_id;//创建者、修改者，记录创建人的id
 
	//以下非表中字段
	private String username;
	private long fileSize;//文件大小
	private String parentFileName;//上一级文件名
	private String spaceUID;//用户空间的根目录名：建立用户时候的名字+系统时间，即是name_systemtime，以后用户修改name后，该名字始终不变。

	private Integer isFolder;//是否是文件夹
	
	private String oldParentPath;//原始的父级目录
	
	private String dateStart;//开始时间
	private String dateEnd;//结束时间
	private String pdfSavePath;//pdf保存路径，预览转换文件时使用
	private String pdfSaveName;//pdf保存的文件名，预览转换文件时使用
	private String relativeFilePath;//文件的相对路径
	
	private String createrName;//创建者名字
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getSavetime() {
		return savetime;
	}

	public void setSavetime(String savetime) {
		if(!StringUtils.isEmpty(savetime))
		{
			this.savetime = savetime.replace(".0", "");
		}
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_realname() {
		return file_realname;
	}

	public void setFile_realname(String file_realname) {
		this.file_realname = file_realname;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	
	public String getParentFileName() {
		return parentFileName;
	}

	public void setParentFileName(String parentFileName) {
		this.parentFileName = parentFileName;
	}
	
	public String getSpaceUID() {
		return spaceUID;
	}

	public void setSpaceUID(String spaceUID) {
		this.spaceUID = spaceUID;
	}

	public String getParent_path() {
		return parent_path;
	}

	public void setParent_path(String parent_path) {
		this.parent_path = parent_path;
	}
	

	public Integer getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(Integer isFolder) {
		this.isFolder = isFolder;
	}
	

	public String getOldParentPath() {
		return oldParentPath;
	}

	public void setOldParentPath(String oldParentPath) {
		this.oldParentPath = oldParentPath;
	}
	

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	

	public String getPdfSavePath() {
		return pdfSavePath;
	}

	public void setPdfSavePath(String pdfSavePath) {
		this.pdfSavePath = pdfSavePath;
	}

	
	public String getPdfSaveName() {
		return pdfSaveName;
	}

	public void setPdfSaveName(String pdfSaveName) {
		this.pdfSaveName = pdfSaveName;
	}

	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}
	

	

	public String getCreater_id() {
		return creater_id;
	}

	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}
	

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	@Override
	public String toString() {
		return "SysFile [file_id=" + file_id + ", admin_id=" + admin_id + ", file_type=" + file_type + ", savetime="
				+ savetime + ", file_name=" + file_name + ", file_realname=" + file_realname + ", file_path="
				+ file_path + ", content=" + content + "]";
	}

}
