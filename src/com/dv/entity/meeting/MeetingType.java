package com.dv.entity.meeting;

import java.io.Serializable;

import com.dv.entity.BaseEntity;

public class MeetingType implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1781300279704617121L;
    //id
    private String id;
    //会议类型编号
    private String identifier;
    //会议类型名称
    private String name;
    //会议类型管理员
    private String manager_id;
    //会议类型序号
    private Integer order;
    //会议类型说明
    private String comment;
    
    //非定义字段
    private String manager_account;//管理员 账号
    private String manager_name;//管理员真实姓名
    
    private int start;//起始位置
    private int limit;//记录数
    private String search;//搜索
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getIdentifier()
    {
        return identifier;
    }
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getManager_id()
    {
        return manager_id;
    }
    public void setManager_id(String manager_id)
    {
        this.manager_id = manager_id;
    }
    public Integer getOrder()
    {
        return order;
    }
    public void setOrder(Integer order)
    {
        this.order = order;
    }
    public String getComment()
    {
        return comment;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public String getManager_account()
    {
        return manager_account;
    }
    public void setManager_account(String manager_account)
    {
        this.manager_account = manager_account;
    }
    public String getManager_name()
    {
        return manager_name;
    }
    public void setManager_name(String manager_name)
    {
        this.manager_name = manager_name;
    }
    public int getStart()
    {
        return start;
    }
    public void setStart(int start)
    {
        this.start = start;
    }
    public int getLimit()
    {
        return limit;
    }
    public void setLimit(int limit)
    {
        this.limit = limit;
    }
    public String getSearch()
    {
        return search;
    }
    public void setSearch(String search)
    {
        this.search = search;
    }
    
}
