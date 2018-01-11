/*
 * 文件名称：          MeetingFileDownload.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 下午5:01:54
 * 版权所有：          
 */

package com.dv.entity.meeting;

import java.io.Serializable;

import com.dv.entity.BaseEntity;

/**
 * 会议文件下载表：记录会议文件的下载详情
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年6月13日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
public class MeetingFileDownload extends BaseEntity implements Serializable
{

    /**
     * 序列化
     */
    private static final long serialVersionUID = -1250537927978496642L;
    
    /**
     * id
     */
    private String id;
    
    /**
     * 下载人id
     */
    private String user_id;
    
    /**
     * 会议id
     */
    private String mid;
    
    /**
     * 下载时间
     */
    private String download_time;
    
    /**
     * 下载人登录名      非数据库字段
     */
    private String user_name;
    
    /**
     * 下载人真实姓名  非数据库字段
     */
    private String real_name;

    public MeetingFileDownload()
    {
        super();
    }
    
    public MeetingFileDownload(String mid)
    {
        super();
        this.mid = mid;
    }

    
    public MeetingFileDownload(String id, String user_id, String mid)
    {
        super();
        this.id = id;
        this.user_id = user_id;
        this.mid = mid;
        
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getMid()
    {
        return mid;
    }

    public void setMid(String mid)
    {
        this.mid = mid;
    }

    public String getDownload_time()
    {
        return download_time;
    }

    public void setDownload_time(String download_time)
    {
        this.download_time = download_time;
    }

    public String getUser_name()
    {
        return user_name;
    }


    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }


    public String getReal_name()
    {
        return real_name;
    }


    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }

}
