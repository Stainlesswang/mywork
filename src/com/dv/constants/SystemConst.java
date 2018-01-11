package com.dv.constants;

/**
 * 
 * @classDesc ：
 *	系统常量
 * @creater: 李梦婷
 * @creationDate:2017年3月9日 上午10:42:02
 */
public  class SystemConst {
	
	public static final String basePagePath="pages/back/";//后台页面访问路径
	
	public static final String LOGIN_USER="loginUser";
	
	public static final String ATTENDEESLIMIT = "ATTENDEESLIMIT";
	
	public static final String FUNCTION_MAP="functionMap";
	
	public static final String TOKEN="token";
	
	public static final String ERROR_PAGE_404="error/404";
	public static final String ERROR_PAGE_404_TIPS="/error/404_tips.jsp";
	public static final String ERROR_PAGE_500="error/500";
	
	public static final String SYS_FILE_PATH="uploadFile/";
	public static final String SYS_FILE_UPLOAD_USER_EXCEL_PATH=SYS_FILE_PATH+"userExcel/";
	public static final String SYS_FILE_MEET_FILE_PATH=SYS_FILE_PATH+"meetingFile/";
	public static final String SYS_FILE_MERGE_FILE_PATH=SYS_FILE_PATH+"mergeFile/";
	
	public static final int SYS_SUCCESS=0;
	public static final String SYS_SUCCESS_MSG="成功。";
	
	public static final int SYS_ERROR=-1;
	public static final String SYS_ERROR_MSG="服务处理不成功。";
	
	/********************用户登陆*************************/
	
	public static final int OFFLINE=-10001;
	public static final String OFFLINE_MSG="尚未登陆";
	
	public static final int USERNAME_PWD_ERROR=-10002;
	public static final String USERNAME_PWD_ERROR_MSG="用户名或密码错误";
	
	public static final int USER_NOT_FOUND=-10003;
	public static final String USER_NOT_FOUND_MSG="用户不存在";
	
	/****************数据校验类错误**********************/
	public static final int DATA_ERROR=-20000;
	public static final String DATA_ERROR_MSG="数据错误";
	
	public static final int NO_PARAM=-20001;
	public static final String NO_PARAM_MSG="缺少参数";
	
	public static final int NOT_NULL=-20002;
	public static final String NOT_NULL_MSG="数据不能为空";
	
	public static final int NOT_NULL_PWD=-20003;
	public static final String NOT_NULL_PWD_MSG="密码不能为空";
	
	public static final int NOT_NULL_USERNAME=-20004;
	public static final String NOT_NULL_USERNAME_MSG="用户名不能为空";
	
	public static final int NOT_NULL_CHECKCODE=-20005;
	public static final String NOT_NULL_CHECKCODE_MSG="验证码不能为空";
	
	public static final int CHECKCODE_ERROR=-20005;
	public static final String CHECKCODE_ERROR_MSG="验证码错误";
	
	public static final int USERNAME_EXIST_ERROR=-20006;
	public static final String USERNAME_EXIST_ERROR_MSG="用户名已经存在";
	
	public static final int DATA_EXIST_ERROR=-20007;
	public static final String DATA_EXIST_ERROR_MSG="数据已经存在";
	
	public static final int DATA_NOT_EXIST_ERROR=-20008;
	public static final String DATA_NOT_EXIST_ERROR_MSG="数据不存在";
	
	public static final int CHECKATTENDEESLIMIT_ERROR=-20009;
    public static final String CHECKATTENDEESLIMIT_ERROR_MSG="超过授权数量";
	
	/*****************保存数据出现的错误*******************/
	
	public static final int DUP_KEY=-30001;
	public static final String DUP_KEY_MSG="存在重复的值";
	
	public static final int SAVE_FAIL=-30002;
	public static final String SAVE_FAIL_MSG="数据保存失败";
	
	public static final int MODIFY_FAIL=-30003;
	public static final String MODIFY_FAIL_MSG="数据更新失败";
	
	public static final int DELETE_FAIL=-30004;
	public static final String DELETE_FAIL_MSG="数据删除失败";
	
	public static final int DATA_INUSE=-30005;
	public static final String DATA_INUSE_MSG="数据被占用";
	
	public static final int QUERY_FAIL=-30006;
	public static final String QUERY_FAIL_MSG="数据查询失败";
	
/**************************文件操作类错误****************************/
	
	public static final int FILE_CREATE_ERROR = -40001;
	public static final String FILE_CREATE_ERROR_MSG = "文件创建失败";
	public static final int FILE_NOTEXIST_ERROR = -40002;
	public static final String FILE_NOTEXIST_ERROR_MSG = "文件不存在";
	public static final int FILE_EXIST_ERROR = -40003;
	public static final String FILE_EXIST_ERROR_MSG = "文件已经存在";
	public static final int FILE_UPLOAD_ERROR = -40004;
	public static final String FILE_UPLOAD_ERROR_MSG = "文件上传失败";
	
	public static final int FILE_TYPE_ERROR = -40005;
	public static final String FILE_TYPE_ERROR_MSG = "文件类型错误";
	
	public static final int FILE_MODIFY_ERROR = -40006;
	public static final String FILE_MODIFY_ERROR_MSG = "文件修改失败";
	
	public static final int FILENAME_SAME_ERROR = -40007;
	public static final String FILENAME_SAME_ERROR_MSG = "新文件名和旧文件名相同";
	
	public static final int FILE_RENAME_ERROR = -40008;
	public static final String FILE_RENAME_ERROR_MSG = "文件重命名失败";
	
	public static final int FILE_SAME_OR_CHILD_ERROR = -40009;
	public static final String FILE_SAME_OR_CHILDE_ERROR_MSG = "不能选择同一文件夹或者子文件夹";
	
	public static final int FILE_MOVE_ERROR = -40010;
	public static final String FILE_MOVE_ERROR_MSG = "文件移动失败";
	
	public static final int FILE_VIEW_ERROR = -40011;
	public static final String FILE_VIEW_ERROR_MSG = "文件不能被预览";
	
	public static final int FILE_CONVERT_ERROR = -40012;
	public static final String FILE_CONVERT_ERROR_MSG = "文件转换失败";
	
	/*************************其他**************************/
	
	public static final int RESTRICTED_ERROR = -999998;
	public static final String RESTRICTED_ERROR_MSG = "无权限使用此功能";
	
	//验证码大小常量，宽和高，字符数，干扰线条数
	public static final int CODE_WIDTH=80;
	public static final int CODE_HEIGHT=28;
	public static final int CODE_SIZE=4;
	public static final int CODE_LINES=6;
	
	//用户操作类型
	public static final int CODE_QUERY=1;//查询
	public static final int CODE_ADD=2;//增加
	public static final int CODE_MOFIFY=3;//修改
	public static final int CODE_DELETE=4;//删除
	public static final int CODE_OTHER=5;//其他
	
	
	
	//最大上传的文件大小（单位MB)
	public static final long MAX_UPLOAD_FILE_SIZE = 50;
	
	/**
	 * 禁止将类进行实例化
	 */
	private SystemConst() {
		
	}
	

}
