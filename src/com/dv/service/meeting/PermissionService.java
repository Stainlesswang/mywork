package com.dv.service.meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import com.dv.constants.SystemConst;
import com.dv.dao.meeting.PermissionMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.Permission;
import com.dv.util.FnfhException;
/**
 * 
 * @classDesc ：
 *	系统权限service
 * @creater: 李梦婷
 * @creationDate:2017年5月8日 下午3:43:40
 */
@Service
public class PermissionService {
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private PermissionMapper permissionMapper;
	
	@Resource
	private PlatformTransactionManager ptm;
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取权限信息
	 * @param permission
	 * @param pageData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午4:05:20
	 */
	public Result findPermission(Permission permission,FnfhPageData pageData)throws FnfhException{
		Result result=new Result();
		permission.setPer_type(Permission.perType);
		permission.setPer_subtype(Permission.perSubType_admin);
		List<Permission> adminlist=permissionMapper.findPermission(permission);//这个暂未使用
		permission.setPer_subtype(Permission.perSubType_ipad);
		List<Permission> ipadlist=permissionMapper.findPermission(permission);
		permission.setPer_subtype(Permission.perSubType_meet);
		List<Permission> meetlist=permissionMapper.findPermission(permission);
		permission.setPer_subtype(Permission.perSubType_usermanage);
		List<Permission> usermanagelist=permissionMapper.findPermission(permission);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("adminlist", adminlist);
		resultMap.put("ipadlist", ipadlist);
		resultMap.put("meetlist", meetlist);
		resultMap.put("usermanagelist", usermanagelist);
		result.setData(resultMap);
		return result;	
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取用户权限
	 * @param permission
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月9日 上午9:32:40
	 */
	public Map<String,Object> findUserPermission(Permission permission)throws FnfhException{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isEmpty(permission.getUser_id()))
		{
			return resultMap;
		}
		
		List<Permission> permissionList=permissionMapper.findPermission(permission);
		for (Permission per : permissionList) {
			resultMap.put(per.getPer_type()+"-"+per.getPer_subtype(), "1");
			//1-1 本模块；1-2 ipad信息维护；1-3 会议资料新建
		}
		
//		permission.setPer_type(Permission.perType);
//		permission.setPer_subtype(Permission.perSubType_admin);
//		List<Permission> adminlist=permissionMapper.findPermission(permission);
//		if(!adminlist.isEmpty())
//		{
//			resultMap.put("per_admin", "all");//拥有本模块权限
//			return resultMap;
//		}
//		permission.setPer_subtype(Permission.perSubType_ipad);
//		List<Permission> ipadlist=permissionMapper.findPermission(permission);
//		if(!ipadlist.isEmpty())
//		{
//			resultMap.put("per_ipad", "all");//拥有ipad信息维护权限
//		}
//		permission.setPer_subtype(Permission.perSubType_meet);
//		List<Permission> meetlist=permissionMapper.findPermission(permission);
//		if(!meetlist.isEmpty())
//		{
//			resultMap.put("per_meet", "all");//拥有会议资料创建权限
//		}
		return resultMap;	
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 设置权限信息
	 * @param permission
	 * @param fnfhPageData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午4:06:13
	 */
	public Result setPermission(Permission permission,FnfhPageData fnfhPageData) throws FnfhException{
		int result=0;
		try {
			if(StringUtils.isEmpty(permission.getPer_type()))
			{
				return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
			}
			String adminStr=fnfhPageData.getString("adminIds");//本模块管理员权限
			String ipadStr=fnfhPageData.getString("ipadIds");//ipad信息维护权限
			String meetStr=fnfhPageData.getString("meetIds");//会议新建权限
			String usermanageStr=fnfhPageData.getString("usermanageIds");//用户管理员
			List<Permission> list=new ArrayList<Permission>();
			if(!StringUtils.isEmpty(adminStr))
			{
				String[] ids=adminStr.split(";");
				List<String> idsList=Arrays.asList(ids);
				List<Permission> adminPerList=getPerList(idsList,permission.getPer_type(),Permission.perSubType_admin);
				list.addAll(adminPerList);
			}
			if(!StringUtils.isEmpty(ipadStr))
			{
				String[] ids=ipadStr.split(";");
				List<String> idsList=Arrays.asList(ids);
				List<Permission> ipadPerList=getPerList(idsList,permission.getPer_type(),Permission.perSubType_ipad);
				list.addAll(ipadPerList);
			}
			if(!StringUtils.isEmpty(meetStr))
			{
				String[] ids=meetStr.split(";");
				List<String> idsList=Arrays.asList(ids);
				List<Permission> meetPerList=getPerList(idsList,permission.getPer_type(),Permission.perSubType_meet);
				list.addAll(meetPerList);
			}
			if(!StringUtils.isEmpty(usermanageStr))
			{
				String[] ids=usermanageStr.split(";");
				List<String> idsList=Arrays.asList(ids);
				List<Permission> usermanagePerList=getPerList(idsList,permission.getPer_type(),Permission.perSubType_usermanage);
				list.addAll(usermanagePerList);
			}
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
		    try {
				//添加之前，需要将之前的数据删除
				result=permissionMapper.delPermissionByType(permission);
//				System.out.println("111111111权限:"+list.size());
//				for(Permission s:list)
//				{
//					System.out.println(s.toString());
//				}
				result=permissionMapper.batchAddPermission(list);
				ptm.commit(status);
			} catch (Exception e) {
				ptm.rollback(status);
				return Result.error(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
			}
			
		} catch (Exception e) {
			logger.error(logger.getName()+"/permissionMapper,"+e.getMessage()+","+e.toString());
			if (e.toString().indexOf("Duplicate entry")>=0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}else {
				throw new FnfhException(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}	
		return Result.success();
	}
	
	private List<Permission> getPerList(List<String> userIds,int perType,int subPerType)
	{
		List<Permission> perList=new ArrayList<Permission>();
		for (String userId : userIds) {
			Permission per=new Permission();
			per.setPer_type(perType);
			per.setPer_subtype(subPerType);
			per.setUser_id(userId);
			perList.add(per);
		}
		return perList;
	}
	
	
	
}
