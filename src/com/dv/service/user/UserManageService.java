package com.dv.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.dao.meeting.AuthcodeMapper;
import com.dv.dao.meeting.PermissionMapper;
import com.dv.dao.meeting.SubAppMeetingMapper;
import com.dv.dao.meeting.SubAppUserMapper;
import com.dv.dao.user.OrganizeMapper;
import com.dv.dao.user.UserMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.Authcode;
import com.dv.entity.meeting.SubAppUser;
import com.dv.entity.user.Organize;
import com.dv.entity.user.User;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
import com.dv.util.Md5Util;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;

@Service
public class UserManageService {
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private UserMapper userInfoMapper;//用户信息mapper
	
	@Resource
	private OrganizeMapper organizeMapper;//组织mapper
	
	@Resource
	private PlatformTransactionManager ptm;
	
	@Resource
	private PermissionMapper permissionMapper;//权限mapper
	
	@Resource
	private SubAppUserMapper subAppUserMapper;//设备用户mapper
	
	@Resource
	private AuthcodeMapper authCodeMapper;//授权码mapper
	
	@Resource
	private SubAppMeetingMapper subAppMeetApper;//会议资料mapper
	
	@Resource
	private AuthcodeMapper authcodeMapper;
	
	/**
	 * 
	 * @methodDesc:  
	 * 显示用户信息
	 * @param userInfo
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月2日 下午2:47:52
	 */
	public Result showUserInfo(User userInfo,FnfhPageData pgdata) throws FnfhException
	{
		try {
			Result result=new Result();
			int totalCount=0;
			userInfo.setStart(pgdata.getOffset());
			userInfo.setLimit(pgdata.getLimit());
//			System.out.println(userInfo.getStart()+"userInfo="+userInfo.getLimit());
			
			if(!StringUtils.isEmpty(pgdata.getSearch()))
			{
				userInfo.setSearch(pgdata.getSearch());
			}
			String order=pgdata.getOrder();
			String sort=pgdata.getSort();
			String orgId=pgdata.getString("orgId");
			String includeChild=pgdata.getString("includeChild");//是否包含子部门
			String adminType=pgdata.getString("adminType");//是否是普通人员？？用于权限管理选择人员
			if(!StringUtils.isEmpty(orgId))
			{
				int _orgId=CommonMethod.StringToInt(orgId, -1);
				if(_orgId!=-1)
				{
					userInfo.setOrg_id(_orgId);;
				}
			}
			else//如果组织id为空，则返回空数据
			{
				result.setRows(null);
				result.setTotal(totalCount);
				return result;
			}
			
			if(!StringUtils.isEmpty(order))
			{
				userInfo.setOrder(order);
			}
			if(!StringUtils.isEmpty(sort))
			{
				userInfo.setSort(sort);
			}
			if(!StringUtils.isEmpty(adminType))
			{
				userInfo.setAdmin_type(0);
				System.out.println("***********************adminTypeIs:@@@@@:"+adminType);
			}
			if("1".equals(includeChild))//包含子部门
			{
				//根据组织id获取用户信息,即该组织以及children下的用户
				//step1 根据组织id获取组织信息
				Organize organize=new Organize(userInfo.getOrg_id());
				List<Organize> orgList=organizeMapper.findOrganize(organize);
				if(orgList.isEmpty())
				{
					result.setRows(null);
					result.setTotal(totalCount);
					return result;
				}
				organize=orgList.get(0);
				String parentCode=organize.getParent_code();
				if(StringUtils.isEmpty(parentCode))
				{
					parentCode=organize.getOrg_id()+"-";
				}
				else
				{
					parentCode+=organize.getOrg_id()+"-";
				}
				userInfo.setParent_code(parentCode);
			}

			List<User> list=null;
			//step2 查询用户信息
			if ("查询和设备绑定的人员".equals(userInfo.getUser_id())){
                list=userInfoMapper.findUserByOrgIdAndisblinding(userInfo);
			}else {
                list=userInfoMapper.findUserByOrgId(userInfo);
			}
			totalCount = userInfoMapper.findUserCountByOrgId(userInfo);
			result.setRows(list);
			result.setTotal(totalCount);
			return result;
		} catch (Exception e) {
			throw new FnfhException(SystemConst.SYS_ERROR,e.getMessage());
		}
	}
	/**
	 * 
	 * @methodDesc:  
	 * 添加用户信息
	 * @param userInfo
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:25:19
	 */
	public Result addUserInfo(User userInfo,FnfhPageData pgdata) throws FnfhException
	{
		int result=0;
		try
		{
			String userId=CommonMethod.getUID();
			userInfo.setUser_id(userId);
//			userInfo.setSavetime(CommonMethod.getDateFormat(new Date()));
			//密码加密
			String defaultPwd=userInfo.getPassword();
			defaultPwd=Md5Util.md5(defaultPwd);
			userInfo.setPassword(defaultPwd);
			
			result=userInfoMapper.addUser(userInfo);
			if(result<1)
			{
				return Result.error(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/addUserInfo,"+ex.getMessage()+","+ex.toString());
			if(ex.toString().indexOf("Duplicate entry") >= 0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}
			else
			{
				throw new FnfhException(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id删除用户信息
	 * @param jsonStr
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:26:05
	 */
	public Result delUserByIds(String jsonStr) throws FnfhException
	{
		int result=0;
		if(StringUtils.isEmpty(jsonStr))
		{
			return Result.error(SystemConst.NOT_NULL,SystemConst.NOT_NULL_MSG);
		}
		try
		{
			JSONArray jsonArray2= JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			
			
			
			result=userInfoMapper.delUserByIds(idsList);
			if(result<1)
			{
				return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
			}
		}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/delUserByIds,"+ex.getMessage()+","+ex.toString());
			if(ex.toString().indexOf("Cannot delete or update a parent row: a foreign key constraint fails ") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}
			else
			{
				throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据用户id删除所有有关信息
	 * @param jsonStr
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月9日 下午1:32:38
	 */
	public Result delUserAllInfoByIds(String jsonStr) throws FnfhException
	{
		int result=0;
		if(StringUtils.isEmpty(jsonStr))
		{
			return Result.error(SystemConst.NOT_NULL,SystemConst.NOT_NULL_MSG);
		}
		try
		{
			JSONArray jsonArray2= JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
			try {
				//step1 权限表，删除人员权限数据
				result=permissionMapper.delPermissionByUserIds(idsList);
				//step2 设备表，释放授权码，清除用户数据
					//step2.1 将设备表中的注册人员register_id置为空
				result=subAppUserMapper.batchClearRegisterByUserIds(idsList);
					//step2.1 设置授权码状态为未使用
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("status", 0);
				param.put("list", idsList);
				result=authCodeMapper.batchUpdateCodeStatusByUserId(param);
					//step2.3 删除设备信息
				result=subAppUserMapper.delSubAppUserByUserIds(idsList);
				//step3 会议资料表
					//step3.1 设置拟稿人、参会人员 user_id为空
				result=subAppMeetApper.clearUserInfoByUserId(idsList);	

				//step 4删除人员
				result=userInfoMapper.delUserByIds(idsList);
				
				if(result<1)
				{
					ptm.rollback(status);
					return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
				}
				ptm.commit(status);
			} catch (Exception e) {
				ptm.rollback(status);
			}
		}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/delUserByIds,"+ex.getMessage()+","+ex.toString());
			if(ex.toString().indexOf("Cannot delete or update a parent row: a foreign key constraint fails ") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}
			else
			{
				throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改用户信息
	 * @param userInfo
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:30:10
	 */
	public Result modUserById(User userInfo,FnfhPageData pgdata) throws FnfhException
	{
		int result=0;
		try
		{
			if(userInfo==null||StringUtils.isEmpty(userInfo.getUser_id()))
			{
				return Result.error(SystemConst.NO_PARAM,SystemConst.NO_PARAM_MSG);
			}
			//根据id获取用户信息
			User oldUser=new User();
			oldUser.setUser_id(userInfo.getUser_id());
			List<User> list=userInfoMapper.findUser(oldUser);
			if(list!=null&&!list.isEmpty())
			{
				oldUser=list.get(0);
			}
			
			
			if(!StringUtils.isEmpty(userInfo.getPassword()))
			{
				if(!oldUser.getPassword().equals(userInfo.getPassword()))//如果原始密码不相等
				{
					String defaultPwd=userInfo.getPassword();
					if(!StringUtils.isEmpty(defaultPwd))
					{
						defaultPwd=Md5Util.md5(defaultPwd);
						userInfo.setPassword(defaultPwd);
					}
				}
				else
				{
					userInfo.setPassword(null);
				}
				
			}
			else
			{
				return Result.error(SystemConst.NOT_NULL,"密码不能为空");
			}
			
			
			result=userInfoMapper.modUser(userInfo);
			if(result<1)
			{
				return Result.error(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
			}
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/modUserById,"+ex.getMessage()+","+ex.toString());
			if(ex.toString().indexOf("Duplicate entry") >= 0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}
			else
			{
				throw new FnfhException(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param userInfo
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 上午10:33:01
	 */
	public Result getObjInstane(User userInfo) throws FnfhException 
	{
		Result result=new Result();
		if(StringUtils.isEmpty(userInfo.getUser_id()))
		{
			return Result.error(SystemConst.NO_PARAM,SystemConst.NO_PARAM_MSG);
		}
		userInfo.setStart(0);
		userInfo.setLimit(1);
		List<User> list=userInfoMapper.findUser(userInfo);
		if(list!=null&&list.size()>0)
		{
			result.setData(list.get(0));
			return result;
		}
		else
		{
			return Result.error(SystemConst.QUERY_FAIL, SystemConst.QUERY_FAIL_MSG);
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 校验用户名是否存在
	 * @param userInfo
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午1:59:19
	 */
	public Result checkUserNameExist(User userInfo) throws FnfhException 
	{
		if(StringUtils.isEmpty(userInfo.getUser_name()))
		{
			return Result.error(SystemConst.NOT_NULL_USERNAME, SystemConst.NOT_NULL_USERNAME_MSG);
		}
		else
		{
			int count=userInfoMapper.isExistName(userInfo);
			if(count>0)
			{
				return Result.error(SystemConst.USERNAME_EXIST_ERROR, "该账号已经存在");
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 重置密码
	 * @param userInfo
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午2:05:49
	 */
	public Result resetPwd(User userInfo) throws FnfhException
	{
		int result=0;
		try
		{
			if(StringUtils.isEmpty(userInfo.getUser_id()))
			{
				return Result.error(SystemConst.NO_PARAM,SystemConst.NO_PARAM_MSG);
			}
			if(StringUtils.isEmpty(userInfo.getPassword()))
			{
				throw new FnfhException(SystemConst.NOT_NULL_PWD,SystemConst.NOT_NULL_PWD_MSG);
			}
			String defaultPwd=userInfo.getPassword();
			defaultPwd=Md5Util.md5(defaultPwd);
			userInfo.setPassword(defaultPwd);
			result=userInfoMapper.updateUserPwd(userInfo);
			if(result<1)
			{
				throw new FnfhException(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
			}
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/resetPwd,"+ex.getMessage()+","+ex.toString());
			throw new FnfhException(-1,"密码修改出错");
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 导入用户数据,导入某一部门的人员
	 * @param req
	 * @param uploadFiles
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午4:05:07
	 */
	public Result importUserExcel(HttpServletRequest req,MultipartFile uploadFiles) throws FnfhException
	{
		try
		{
			String orgId=req.getParameter("org_id");
			int _orgId=0;
			if(!StringUtils.isEmpty(orgId))
			{
				_orgId=CommonMethod.StringToInt(orgId, -1);
				if(_orgId==-1)
				{
					return Result.error(SystemConst.NOT_NULL, "组织数据错误");
				}
			}
			else
			{
				return importUserExcelAllOrg(req, uploadFiles);//导入所有部门的人员信息
//				return Result.error(SystemConst.NOT_NULL, "组织不能为空");
			}
			if(uploadFiles==null||uploadFiles.getSize()<=0)
			{
				return Result.error(SystemConst.NOT_NULL, "请选择导入的Excel文件");
			}
			String strFile = uploadFiles.getOriginalFilename();
			String suffix=strFile.substring(strFile.lastIndexOf(".") + 1);
			if(!suffix.equalsIgnoreCase("xlsx")&& !suffix.equalsIgnoreCase("xls")){
				 return Result.error(SystemConst.DATA_ERROR, "只能导入excel文件");
			}
			
			//实例化一个工作簿对象
			Workbook workBook=Workbook.getWorkbook(uploadFiles.getInputStream());
			//获取该工作表中的第一个工作表
			Sheet[] sheets=workBook.getSheets();
			List<User> newAddUserList=new ArrayList<User>();//新增的用户
			List<User> newModUserList=new ArrayList<User>();//修改的用户
			for(Sheet sheet:sheets)
			{
//				Sheet sheet=workBook.getSheet(0);
				//获取该工作表的行数，以供下面循环使用
				
				int rowSize=sheet.getRows();
				if(rowSize<=1)
				{
					continue;
				}
				for(int i=1;i<rowSize;i++)
				{
					User userInfo=new User();
					userInfo.setOrg_id(_orgId);
					
					String real_name=sheet.getCell(0,i).getContents();//姓名
					String user_name=sheet.getCell(1,i).getContents();//登陆账号
					String password=sheet.getCell(2,i).getContents();//密码
					String email=sheet.getCell(3,i).getContents();//邮箱
					String mobile=sheet.getCell(4,i).getContents();//移动电话
					String tel=sheet.getCell(5,i).getContents();//固定电话
					String sex=sheet.getCell(6,i).getContents();//性别
					String position=sheet.getCell(7,i).getContents();//职务
					String orgNames=sheet.getCell(8,i).getContents();//所属部门
					String addr=sheet.getCell(9,i).getContents();//住址
					String remark=sheet.getCell(10,i).getContents();//用户说明
					String admin_type=sheet.getCell(11,i).getContents();//是否为管理员
					//如果数据全是空，跳过这一行
					if(StringUtils.isEmpty(real_name)&&StringUtils.isEmpty(user_name)&&
						StringUtils.isEmpty(password)&&StringUtils.isEmpty(email)&&
						StringUtils.isEmpty(mobile)&&StringUtils.isEmpty(tel)&&
						StringUtils.isEmpty(sex)&&StringUtils.isEmpty(position)&&
						StringUtils.isEmpty(addr)&&StringUtils.isEmpty(remark)&&
						StringUtils.isEmpty(admin_type)&&StringUtils.isEmpty(orgNames))
					{
						continue;
					}
					 //校验用户名
					 Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
				     if (!pattern.matcher(user_name).matches())
				     {
				          continue;
				     }
					
					if(StringUtils.isEmpty(real_name))
					{
						return Result.error(SystemConst.NOT_NULL, "姓名不能为空");
					}
					userInfo.setReal_name(real_name);
					
					if(StringUtils.isEmpty(user_name))
					{
						return Result.error(SystemConst.NOT_NULL, "登陆账号不能为空");
					}
					
					
					userInfo.setUser_name(user_name);
					
					if(StringUtils.isEmpty(password))
					{
						password="123456";
					}
					password=password.trim();
					userInfo.setPassword(Md5Util.md5(password));
					
					
					userInfo.setEmail(email);
					
					userInfo.setMobile(mobile);
					
					userInfo.setTel(tel);
					
					if("女".equals(sex))
					{
						userInfo.setSex(1);
					}
					else
					{
						userInfo.setSex(0);
					}
						
					
					userInfo.setPosition(position);
					
					userInfo.setAddr(addr);
					
					userInfo.setRemark(remark);
					
					if("是".equals(admin_type))
					{
						userInfo.setAdmin_type(2);
					}
					else
					{
						userInfo.setAdmin_type(0);
					}
					
					//这里判断用户是否存在 start 20170524
					List<User> existList=userInfoMapper.findUserInfoByName(userInfo);
					if(existList==null||existList.isEmpty())
					{
						String userId=CommonMethod.getUID();
						userInfo.setUser_id(userId);
						userInfo.setExistFlag(true);
						newAddUserList.add(userInfo);
					}
					else
					{
						userInfo.setUser_id(existList.get(0).getUser_id());
						newModUserList.add(userInfo);
					}
					//end 20170524
					
				  }
			}
			
			int addRow=0;
			int modRow=0;
			if(newAddUserList.size()>0)
			{
				addRow=userInfoMapper.batchAddUserInfo(newAddUserList);
			}
			if(newModUserList.size()>0)
			{
				for(User tempUser:newModUserList)
				{
					int j=userInfoMapper.modUser(tempUser);
					modRow=j>0?modRow+1:modRow;
				}
			}
			Result result=new Result(); 
			Map<String,Integer> resultMap=new HashMap<String,Integer>();
			resultMap.put("addRow", addRow);
			resultMap.put("modRow", modRow);
			result.setData(resultMap);
			return result;
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/importUserExcel,"+ex.getMessage()+","+ex.toString());
			if(ex.toString().indexOf("Duplicate entry") >= 0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}
			else
			{
				throw new FnfhException(-1,"导入数据失败");
			}
			
		}
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 导入所有人员信息，部门信息包含在excel中
	 * @param req
	 * @param uploadFiles
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月19日 上午8:50:17
	 */
	public Result importUserExcelAllOrg(HttpServletRequest req,MultipartFile uploadFiles) throws FnfhException
	{
		try
		{
			
			if(uploadFiles==null||uploadFiles.getSize()<=0)
			{
				return Result.error(SystemConst.NOT_NULL, "请选择导入的Excel文件");
			}
			String strFile = uploadFiles.getOriginalFilename();
			String suffix=strFile.substring(strFile.lastIndexOf(".") + 1);
			if(!suffix.equalsIgnoreCase("xlsx")&& !suffix.equalsIgnoreCase("xls")){
				 return Result.error(SystemConst.DATA_ERROR, "只能导入excel文件");
			}
			
			//获取所有部门信息，现在默认只有一个公司信息,将组织信息转换成map集合
			Organize topOrg=new Organize();
			List<Organize> orgList=organizeMapper.findOrganize(new Organize());
			List<Organize> topOrgList=organizeMapper.findTopOrganize(new Organize());//获取第一级组织
			if(!topOrgList.isEmpty())
			{
				topOrg=topOrgList.get(0);
			}
			
			
			//实例化一个工作簿对象
			Workbook workBook=Workbook.getWorkbook(uploadFiles.getInputStream());
			//获取该工作表中的第一个工作表
			Sheet[] sheets=workBook.getSheets();
			List<User> list=new ArrayList<User>();
			for(Sheet sheet:sheets)
			{
//				Sheet sheet=workBook.getSheet(0);
				//获取该工作表的行数，以供下面循环使用
				
				int rowSize=sheet.getRows();
				if(rowSize<=1)
				{
					continue;
				}
				for(int i=1;i<rowSize;i++)
				{
					User userInfo=new User();
					
					
					String real_name=sheet.getCell(0,i).getContents();//姓名
					String user_name=sheet.getCell(1,i).getContents();//登陆账号
					String password=sheet.getCell(2,i).getContents();//密码
					String email=sheet.getCell(3,i).getContents();//邮箱
					String mobile=sheet.getCell(4,i).getContents();//移动电话
					String tel=sheet.getCell(5,i).getContents();//固定电话
					String sex=sheet.getCell(6,i).getContents();//性别
					String position=sheet.getCell(7,i).getContents();//职务
					String orgNames=sheet.getCell(8,i).getContents();//所属部门
					String addr=sheet.getCell(9,i).getContents();//住址
					String remark=sheet.getCell(10,i).getContents();//用户说明
					String admin_type=sheet.getCell(11,i).getContents();//是否为管理员
					String is_temp = sheet.getCell(12,i).getContents();//是否为临时用户
					//如果数据全是空，跳过这一行
					if(StringUtils.isEmpty(real_name)&&StringUtils.isEmpty(user_name)&&
						StringUtils.isEmpty(password)&&StringUtils.isEmpty(email)&&
						StringUtils.isEmpty(mobile)&&StringUtils.isEmpty(tel)&&
						StringUtils.isEmpty(sex)&&StringUtils.isEmpty(position)&&
						StringUtils.isEmpty(addr)&&StringUtils.isEmpty(remark)&&
						StringUtils.isEmpty(admin_type)&&StringUtils.isEmpty(orgNames))
					{
						continue;
					}
					
					
					if(StringUtils.isEmpty(real_name))
					{
						return Result.error(SystemConst.NOT_NULL, "姓名不能为空");
					}
					userInfo.setReal_name(real_name);
					
					if(StringUtils.isEmpty(user_name))
					{
						return Result.error(SystemConst.NOT_NULL, "登陆账号不能为空");
					}
					//组织
					if(StringUtils.isEmpty(orgNames))
					{
						return Result.error(SystemConst.NOT_NULL, "所属部门不能为空");
					}
					//用户名校验不通过，跳过这一行
                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
                    if (!pattern.matcher(user_name).matches())
                    {
                         continue;
                    }
					userInfo.setOrg_name(orgNames);//组织名称集合
					userInfo.setUser_name(user_name);
					
					if(StringUtils.isEmpty(password))
					{
						password="123456";
					}
					if (StringUtils.isEmpty(is_temp))
					{
					    is_temp = "是";
					}
					password=password.trim();
					userInfo.setPassword(Md5Util.md5(password));
					
					userInfo.setEmail(email);
					userInfo.setMobile(mobile);
					userInfo.setTel(tel);
					
					if("女".equals(sex))
					{
						userInfo.setSex(1);
					}
					else
					{
						userInfo.setSex(0);
					}
					userInfo.setPosition(position);
					userInfo.setAddr(addr);
					userInfo.setRemark(remark);
					
					if("是".equals(admin_type))
					{
						userInfo.setAdmin_type(2);
					}
					else
					{
						userInfo.setAdmin_type(0);
					}
					if("是".equals(is_temp))
					{
					    userInfo.setIs_temp(1);
					}
					else
					{
					    userInfo.setIs_temp(0);
					}
					
					//这里判断用户是否存在 start 20170524
					List<User> existList=userInfoMapper.findUserInfoByName(userInfo);
					if(existList==null||existList.isEmpty())
					{
						String userId=CommonMethod.getUID();
						userInfo.setUser_id(userId);
						userInfo.setExistFlag(true);
					}
					else
					{
						userInfo.setUser_id(existList.get(0).getUser_id());
					}
					//end 20170524
					
					
					
					list.add(userInfo);
				  }
			  }
			
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
		    
			try {
				List<User> newAddUserList=new ArrayList<User>();//新增的用户信息集合
				List<User> newModUserList=new ArrayList<User>();//修改的用户信息集合
				//遍历用户信息，设置组织信息
				for(int j=0;j<list.size();j++)
				{
					User user=list.get(j);
					String _orgNames=user.getOrg_name();
					String[] orgListStr=_orgNames.split("-");
					if(orgListStr.length==0)
					{
						return Result.error(SystemConst.NOT_NULL, "部门名称："+_orgNames+"格式错误");
					}
					List<Integer> orgIds=new ArrayList<Integer>();//组织id集合
					int currOrgId=0;//当前组织id
					for(int i=0;i<orgListStr.length;i++)
					{
						String oName=orgListStr[i];
						if(i==0)//匹配第一级组织，
						{
							if(!oName.equals(topOrg.getOrg_name()))//如果匹配不上
							{
								return Result.error(SystemConst.NOT_NULL, "部门名称："+oName+"不能作为公司级别部门");
							}
							currOrgId=topOrg.getOrg_id();
							orgIds.add(currOrgId);
						}
						else
						{
							boolean flag=false;
							for(Organize org:orgList)
							{
								if(oName.equals(org.getOrg_name())&&currOrgId==org.getParent_id())//组织名称匹配的上且上级组织一致
								{
									currOrgId=org.getOrg_id();
									orgIds.add(currOrgId);
									flag=true;
									break;//跳出当前循环
								}
							}
							if(!flag)//没有匹配上，创建组织部门
							{
								int addOrgId=organizeMapper.findMaxOrgId();
								Organize addOrg=new Organize();
								addOrg.setOrg_id(addOrgId);
								addOrg.setOrg_name(oName);
								addOrg.setParent_id(currOrgId);
								addOrg.setParent_code(getParentCode(orgIds));
								int n=organizeMapper.addOrganize(addOrg);
								if(n<1)
								{
									return Result.error(SystemConst.SYS_ERROR, "部门添加失败");
								}
								orgList.add(addOrg);//将新增的部门添加到集合中
								orgIds.add(addOrgId);
								currOrgId=addOrgId;
							}
						}
					}
					if(currOrgId!=0)
					{
						user.setOrg_id(currOrgId);
						if(user.isExistFlag())
						{
							newAddUserList.add(user);
						}
						else
						{
							newModUserList.add(user);
						}
						
					}
					else
					{
						return Result.error(SystemConst.SYS_ERROR, "部门信息添加失败");
					}
					
				}
				int addRow=0;
				int modRow=0;
				if(newAddUserList.size()>0)
				{
					addRow=userInfoMapper.batchAddUserInfo(newAddUserList);
				}
				if(newModUserList.size()>0)
				{
					for(User tempUser:newModUserList)
					{
						int j=userInfoMapper.modUser(tempUser);
						modRow=j>0?modRow+1:modRow;
					}
//					modRow=userInfoMapper.batchModUserInfo(newModUserList);
				}
				ptm.commit(status);//提交
				Result result=new Result(); 
				Map<String,Integer> resultMap=new HashMap<String,Integer>();
				resultMap.put("addRow", addRow);
				resultMap.put("modRow", modRow);
				result.setData(resultMap);
				return result;
			} catch (Exception e) {
				ptm.rollback(status);//回滚
				logger.error(logger.getName()+"/importUserExcelAllOrg,"+e.getMessage()+","+e.toString());
				if(e.toString().indexOf("Duplicate entry") >= 0) {
					return Result.error(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
				}
				else
				{
					return Result.error(SystemConst.SYS_ERROR,"导入数据失败");
				}
				
			}
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/importUserExcelAllOrg,"+ex.getMessage()+","+ex.toString());
			throw new FnfhException(SystemConst.SYS_ERROR,"导入数据失败");
		}
	}
	
	private String getParentCode(List<Integer> orgids)
	{
		String parentCode="";
		for(Integer id:orgids)
		{
			parentCode+=id+"-";
		}
		return parentCode;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 登陆
	 * @param username
	 * @param password
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月5日 上午9:34:07
	 */
	public Result backLogin(String username,String password) throws FnfhException
	{

		if(StringUtils.isEmpty(username))
		{
			throw new FnfhException(SystemConst.NOT_NULL_USERNAME,SystemConst.NOT_NULL_USERNAME_MSG);
		}
		if(StringUtils.isEmpty(password))
		{
			throw new FnfhException(SystemConst.NOT_NULL_PWD,SystemConst.NOT_NULL_PWD_MSG);
		}
		User userInfo=new User();
		userInfo.setUser_name(username);
		List<User> list=userInfoMapper.findUserInfoByName(userInfo);
		if(list.isEmpty())
		{
			throw new FnfhException(SystemConst.USER_NOT_FOUND,SystemConst.USER_NOT_FOUND_MSG);
		}
		else
		{
			userInfo=list.get(0);
			//比对密码
			password=Md5Util.md5(password);
			if(password.equals(userInfo.getPassword()))//相等
			{
				Result result=new Result(SystemConst.SYS_SUCCESS,SystemConst.SYS_SUCCESS_MSG);
				result.setData(userInfo);
				return result;
			}
			else
			{
				throw new FnfhException(SystemConst.USERNAME_PWD_ERROR,SystemConst.USERNAME_PWD_ERROR_MSG);
			}
		}
	}
		
	/**
     * 通过授权码和ipad编码登录系统
     * @param authCode 授权码
     * @param ipadUUID ipad编码
     * @return
     * @throws FnfhException
     */
    public Result authCodeLogin(String authCode, String ipadUUID) throws FnfhException
    {
        //判断授权码非空
        if (StringUtils.isEmpty(authCode))
        {
            throw new FnfhException(APIConstants.AUTH_CODE_NULL.getCode(), APIConstants.AUTH_CODE_NULL.getName());
        }
        
        //判断ipad编码非空
        if (StringUtils.isEmpty(ipadUUID))
        {
            throw new FnfhException(APIConstants.IPAD_NO_NULL.getCode(), APIConstants.IPAD_NO_NULL.getName());
        }
        
        //判断授权码是否存在
        List<Authcode> list = this.authcodeMapper.findAuthcode(new Authcode(authCode));
        if (list != null && list.isEmpty())
        {
            throw new FnfhException(APIConstants.AUTH_CODE_NOT_EXIST.getCode(), APIConstants.AUTH_CODE_NOT_EXIST.getName());
        }
        else
        {
            Authcode ac = list.get(0);
            if (ac.getStatus() == 0)
            {
                throw new FnfhException(APIConstants.AUTHCODE_NOT_AVAILABLE.getCode(), APIConstants.AUTHCODE_NOT_AVAILABLE.getName());
            }
        }
        List<SubAppUser> subAppUserList = this.subAppUserMapper.findSubAppUser(new SubAppUser(authCode));
        if (subAppUserList == null || subAppUserList.isEmpty())
        {
            throw new FnfhException(APIConstants.AUTH_CODE_NOT_BINDING.getCode(), APIConstants.AUTH_CODE_NOT_BINDING.getName());
        }
        //授权码已经绑定了一台设备
        if (subAppUserList.get(0).getIpad_uuid() != null && !ipadUUID.equals(subAppUserList.get(0).getIpad_uuid()))
        {
            throw new FnfhException(APIConstants.AUTHCODE_BE_BOUND.getCode(), APIConstants.AUTHCODE_BE_BOUND.getName());
        }
        else
        {
            Result result = new Result(APIConstants.SUCCESS.getCode(), APIConstants.SUCCESS.getName());
            User user = userInfoMapper.findUserByUserId(((SubAppUser)subAppUserList.get(0)).getUser_id());
            result.setData(user);
            //已经绑定过了，就不要重复绑定了
            if (subAppUserList.get(0).getIpad_uuid() != null && subAppUserList.get(0).getIpad_uuid().equals(ipadUUID) && subAppUserList.get(0).getAuthorationcode().equals(authCode))
            {
                return result;
            }
            //更新is_bing,ipadUUID
            int ret = 0;
            SubAppUser appUser = new SubAppUser();
            appUser.setMid(((SubAppUser)subAppUserList.get(0)).getMid());
            appUser.setIpad_uuid(ipadUUID);
            appUser.setIs_binding(1);
            ret = this.subAppUserMapper.modSubAppUserById(appUser);
            if (ret < 1) {
              throw new FnfhException(Integer.valueOf(-30003), "数据更新失败");
            }
            else
            {
                return result;
            }
        }
    }
	
}
