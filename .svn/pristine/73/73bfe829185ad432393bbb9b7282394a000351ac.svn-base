
package com.dv.entity.meeting;

import org.springframework.util.StringUtils;

import com.dv.entity.BaseEntity;

/**
 * 
 * @classDesc ：
 *	设备使用人表
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午1:15:07
 */
public class SubAppUser extends BaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -8140986380223241432L;

    private String mid;//设备的唯一标识
    private String register_id;//设备登记人id
    private String job_number;//工号
    private String passwd_flag;//密码标示 默认为1 暂未使用
    private String passwd;//初始密码
    private String ipad_uuid;//Ipad编号
    private String create_time;//文档创建时间
    private String authorationcode;//授权码
    private String status;//状态   默认为1 暂未使用
    private Integer is_binding;//设置是否绑定
    private String user_id;//设备使用人id
    private String remark;//备注信息
    private Integer energy;//设备电量
    //非定义字段
    private String user_name;//设备使用人
    private String real_name;//设备使用人真实姓名
    private String register;//登记人真实姓名

    public SubAppUser()
    {
        super();
    }

    public SubAppUser(String authorationcode)
    {
        this.authorationcode = authorationcode;
    }

    public String getRegister_id()
    {
        return register_id;
    }

    public void setRegister_id(String register_id)
    {
        this.register_id = register_id;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getRegister()
    {
        return register;
    }

    public void setRegister(String register)
    {
        this.register = register;
    }

    public Integer getIs_binding()
    {
        return is_binding;
    }

    public void setIs_binding(Integer is_binding)
    {
        this.is_binding = is_binding;
    }

    public String getMid()
    {
        return mid;
    }

    public void setMid(String mid)
    {
        this.mid = mid;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getJob_number()
    {
        return job_number;
    }

    public void setJob_number(String job_number)
    {
        this.job_number = job_number;
    }

    public String getPasswd_flag()
    {
        return passwd_flag;
    }

    public void setPasswd_flag(String passwd_flag)
    {
        this.passwd_flag = passwd_flag;
    }

    public String getPasswd()
    {
        return passwd;
    }

    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }

    public String getIpad_uuid()
    {
        return ipad_uuid;
    }

    public void setIpad_uuid(String ipad_uuid)
    {
        this.ipad_uuid = ipad_uuid;
    }

    public String getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(String create_time)
    {
        if (!StringUtils.isEmpty(create_time))
        {
            create_time = create_time.replace(".0", "");
        }
        this.create_time = create_time;
    }

    public String getAuthorationcode()
    {
        return authorationcode;
    }

    public void setAuthorationcode(String authorationcode)
    {
        this.authorationcode = authorationcode;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getReal_name()
    {
        return real_name;
    }

    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }

    @ Override
    public String toString()
    {
        return "SubAppUser [mid=" + mid + ", register_id=" + register_id + ", job_number="
            + job_number + ", passwd_flag=" + passwd_flag + ", passwd=" + passwd + ", ipad_uuid="
            + ipad_uuid + ", create_time=" + create_time + ", authorationcode=" + authorationcode
            + ", status=" + status + ", is_binding=" + is_binding + ", user_id=" + user_id
            + ", remark=" + remark + ", user_name=" + user_name + ", register=" + register + "]";
    }

    public Integer getEnergy()
    {
        return energy;
    }

    public void setEnergy(Integer energy)
    {
        this.energy = energy;
    }

}
