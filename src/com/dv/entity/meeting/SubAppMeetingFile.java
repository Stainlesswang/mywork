
package com.dv.entity.meeting;

/**
 * 
 * @classDesc ：
 *	会议附件存放表
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午1:12:38
 */
public class SubAppMeetingFile extends SubAppMeeting
{
    private static final long serialVersionUID = -7002980014559510178L;

    private String meeting_mid;//会议唯一ID
    private String file_mid;//附件编码名称

    private String attach_name;//附件名称
    private String attach_size; //附件大小
    private String attach_realname;//附件实际名字

    private String prefix;//文件前缀，即类型前名字
    private String suffix;//文件后缀，类型名
    private Integer attach_type;//文档类型，0普通文档，1合并后的文档, 3回送的文件
    private String last_update_time;//最后更新时间

    private String token;//文件token
    
    private String uploader_id; //文件上传者id
    private String uploader_name; //文件上传者姓名

    public SubAppMeetingFile()
    {
        super();
    }

    public SubAppMeetingFile(String meeting_mid, String file_mid, String attach_name,
        String attach_size, String attach_realname, String prefix, String suffix,
        Integer attach_type, String token, String attach_path, String uploader_id)
    {
        super();
        this.meeting_mid = meeting_mid;
        this.file_mid = file_mid;
        this.attach_name = attach_name;
        this.attach_size = attach_size;
        this.attach_realname = attach_realname;
        this.prefix = prefix;
        this.suffix = suffix;
        this.attach_type = attach_type;
        this.token = token;
        this.setAttach_path(attach_path);
        this.uploader_id = uploader_id;
    }
    
    public SubAppMeetingFile(String meeting_mid)
    {
        super();
        this.meeting_mid = meeting_mid;
    }

    public SubAppMeetingFile(String meeting_mid, Integer attach_type)
    {
        super();
        this.meeting_mid = meeting_mid;
        this.attach_type = attach_type;
    }
    
    public SubAppMeetingFile(String meeting_mid, String uploader_id)
    {
        super();
        this.meeting_mid = meeting_mid;
        this.uploader_id = uploader_id;
    }

    public String getMeeting_mid()
    {
        return meeting_mid;
    }

    public void setMeeting_mid(String meeting_mid)
    {
        this.meeting_mid = meeting_mid;
    }

    public String getFile_mid()
    {
        return file_mid;
    }

    public void setFile_mid(String file_mid)
    {
        this.file_mid = file_mid;
    }

    public String getAttach_size()
    {
        return attach_size;
    }

    public void setAttach_size(String attach_size)
    {
        this.attach_size = attach_size;
    }

    public String getAttach_realname()
    {
        return attach_realname;
    }

    public void setAttach_realname(String attach_realname)
    {
        this.attach_realname = attach_realname;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public String getAttach_name()
    {
        return attach_name;
    }

    public void setAttach_name(String attach_name)
    {
        this.attach_name = attach_name;
    }

    public Integer getAttach_type()
    {
        return attach_type;
    }

    public void setAttach_type(Integer attach_type)
    {
        this.attach_type = attach_type;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Override
    public String getLast_update_time() {
        return last_update_time;
    }

    @Override
    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    @ Override
    public String toString()
    {
        return "SubAppMeetingFile [meeting_mid=" + meeting_mid + ", file_mid=" + file_mid
            + ", attach_name=" + attach_name + ", attach_size=" + attach_size + ", attach_realname="
            + attach_realname + ", prefix=" + prefix + ",last_update_time"+last_update_time+", suffix=" + suffix + ", attach_type="
            + attach_type + "]";
    }

    public String getUploader_id()
    {
        return uploader_id;
    }

    public void setUploader_id(String uploader_id)
    {
        this.uploader_id = uploader_id;
    }

    public String getUploader_name()
    {
        return uploader_name;
    }

    public void setUploader_name(String uploader_name)
    {
        this.uploader_name = uploader_name;
    }

}
