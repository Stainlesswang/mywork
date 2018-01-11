/*
 * 文件名称：          APIConstants.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 下午3:47:40
 * 版权所有：          
 */

package com.dv.constants;

/**
 * api常量
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  wch
 * <p>
 * @日期：                  2017年5月22日
 * <p>
 * @负责人：              wch
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
public enum APIConstants implements IEnum
{
    SUCCESS(0,"success"),
    
    IPAD_NO_NULL(10001, "设备编码不能为空"),
    
    AUTH_CODE_NULL(10002, "授权码不能为空"),
    
    AUTH_CODE_NOT_EXIST(10003, "授权码不存在"),
    
    AUTH_CODE_NOT_BINDING(10004, "该授权码未绑定用户，请联系管理员"),
    
    UPLOAD_FILE_NULL(10005, "请选择要上传的文件"),
    
    FILE_CONTENT_0(10006, "上传的文件内容不能为空"),
    
    MEETING_NOT_EXIST(10007, "会议不存在"),
    
    MEETING_ID_NULL(10008, "会议Id不能为空"),
    
    PROPERTIES_ERROR(10009, "读取配置文件错误"),
    
    PROPERTIES_NOT_EXIST(10010, "配置文件不存在"),
    
    UPLOAD_FAILED(10011, "上传失败"),
    
    WRITE_FILE_ERROR(10012, "写文件错误"),

    COPY_FILE_ERROR(10013, "复制文件错误"),
    
    USER_ID_NULL(10014, "用户id不能为空"),
    
    USER_NOT_EXIST(10015, "用户id不存在"),
    
    UPLOADER_ID_NULL(10016, "缺少参数:uploader_id"), 
    
    ENERGY_NO_NULL(10017, "电量不能为空"), 
    
    DEVICE_NOT_EXIST(10018, "设备不存在"),
    
    NUMBER_IN_100(10019, "请输入0-100的整数"),
    
    AUTHCODE_BE_BOUND(10020, "此授权码已经被绑定"),
    
    UPLOAD_FILE_CANNOT_GTR_50M(10021, "上传文件大小不得大于50M"),
    
    NO_SDCARD_ERROR(10022, "无网回送前请先插入SD卡"),
    
    CHECK_LICENSE_FAIL(10023, "检测license失败"),
    
    LICENSE_NO_FOUND(10024, "无法获取授权文件"),
    
    LICENSE_CONTENT_NULL(10025, "授权文件内容为空"),
    
    LICENSE_SIGN_INVALID(10026, "授权文件签名校验无效"),
    
    LICENSE_MATCHING_FAIL(10027, "非授权服务器部署"),
    
    LICENSE_TYPE_ERROR(10028, "授权类型错误"),
    
    LICENSE_EXPIRED(10029, "授权已经过期"),
    
    ADD_ELECTRONIC_SEAL_FAIL(10030, "添加电子签章失败"),
    
    MEETING_FILE_NOT_EXIST(10031, "会议文件不存在"),
    
    FILE_MID_NULL(10032, "会议文件id不能为空"),
    
    COPY_FILE_FAIL(10033, "复制文件失败"),
    
    AUTHCODE_NOT_AVAILABLE(10034, "授权码不可用"),
    
    MEETING_TYPE_IN_USE(10035, "此会议类型正在被使用，无法删除"),
    
    NO_WRITE_PERMISSION(10036, "连接的设备无写权限")
    ;
    
    
    /**
     * 代码
     */
    private Integer code;
    
    /*
     * 名称
     */
    private String name;
    
    

    private APIConstants(Integer code, String name)
    {
        this.code = code;
        this.name = name;
    }

    @ Override
    public Integer getCode()
    {
        return code;
    }

    @ Override
    public String getName()
    {
       
        return name;
    }

}
