package com.dv.service.user;

import java.util.ArrayList;
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
import com.dv.dao.meeting.AuthcodeMapper;
import com.dv.dao.meeting.PermissionMapper;
import com.dv.dao.meeting.SubAppMeetingMapper;
import com.dv.dao.meeting.SubAppUserMapper;
import com.dv.dao.user.OrganizeMapper;
import com.dv.dao.user.UserMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.user.Organize;
import com.dv.entity.user.User;
import com.dv.entity.util.VEasyUiTree;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;

import net.sf.json.JSONArray;

/**
 * 
 * @classDesc ：
 *	组织管理service
 * @creater: 李梦婷
 * @creationDate:2017年5月2日 下午2:58:12
 */
@Service
public class OrgManageService {

	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private OrganizeMapper organizeMapper;//组织mapper
	
	@Resource
	private UserMapper userMapper;//用户mapper
	
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
	
	/**
	 * 
	 * @methodDesc:  
	 * 显示组织，树形结构
	 * @param organize
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月2日 下午3:00:43
	 */
	public String showOrganizeTree(Organize organize,FnfhPageData pgdata) throws FnfhException 
	{
		
		try {
			String orgId=pgdata.getString("orgId");//被点击的树形的组织机构
			String currOrgId=pgdata.getString("currOrgId");//被编辑的组织id
			List<VEasyUiTree> treeList=new ArrayList<VEasyUiTree>();
			if(!StringUtils.isEmpty(orgId))//获取下级组织
			{
				int _orgId=CommonMethod.StringToInt(orgId, -1);
				if(_orgId!=-1)
				{
//				organize.setOrg_id(_orgId);
					organize.setParent_id(_orgId);
					List<Organize> list=organizeMapper.findOrganize(organize);
					treeList=ObjToTree(list,currOrgId);
				}
			}
			else//查询顶级组织，现在暂时默认只有一个顶级组织
			{
				List<Organize> topOrgList=organizeMapper.findTopOrganize(new Organize());
				if(!topOrgList.isEmpty())
				{
					Organize topOrganize=topOrgList.get(0);
					VEasyUiTree topTree=new VEasyUiTree();
					topTree.setId(topOrganize.getOrg_id().toString());
					topTree.setText(topOrganize.getOrg_name());
//				topTree.setState("closed");
					topTree.setState("opened");
//					topTree.setIschecked(true);
					organize.setParent_id(topOrganize.getOrg_id());
					
					List<Organize> list=organizeMapper.findOrganize(organize);
					List<VEasyUiTree> childTreeList=ObjToTree(list,currOrgId);
					if(childTreeList!=null&&childTreeList.size()>0)
					{
						topTree.setChildren(childTreeList);
					}
					else
					{
						topTree.setState("closed");
					}
					
					treeList.add(topTree);
				}
			}
			String jsonStr="[]";
			if(!treeList.isEmpty())
			{
				jsonStr = String.valueOf(JSONArray.fromObject(treeList));
			}
//			System.out.println(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			throw new FnfhException(SystemConst.SYS_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取所有组织
	 * @param organize
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午6:07:20
	 */
	public String showAllOrganizeTree(Organize organize,FnfhPageData pgdata) throws FnfhException 
	{
		
		try {
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			List<Organize> orgList=organizeMapper.findOrganize(new Organize());
			for (Organize organize2 : orgList) {
				HashMap<String,Object> hm = new HashMap<String,Object>();   //最外层，父节点             
	            hm.put("id", organize2.getOrg_id());//id属性  ，数据传递    
	            hm.put("name", organize2.getOrg_name()); //name属性，显示节点名称    
	            hm.put("pId", organize2.getParent_id());
	            hm.put("open", true);
	            list.add(hm);  
			}
			String jsonStr="[]";
			if(!list.isEmpty())
			{
				jsonStr = String.valueOf(JSONArray.fromObject(list));
			}
//			System.out.println(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			throw new FnfhException(SystemConst.SYS_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 组织集合转化为树
	 * @param list
	 * @param currOrgId 被排除的组织
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月2日 下午3:47:27
	 */
	private List<VEasyUiTree> ObjToTree(List<Organize> list,String currOrgId)
	{
		if(list.isEmpty())
		{
			return null;
		}
		List<VEasyUiTree> treeList=new ArrayList<VEasyUiTree>();
		for (Organize organize : list) {
			VEasyUiTree tree=new VEasyUiTree();
			if(!StringUtils.isEmpty(currOrgId))
			{
				if(currOrgId.equals(organize.getOrg_id().toString()))//如果id相等
				{
					continue;
				}
			}
			tree.setId(organize.getOrg_id().toString());
			tree.setText(organize.getOrg_name());
			tree.setAttributes(organize.getParent_id()+","+organize.getParent_code());
			tree.setState("closed");
			treeList.add(tree);
		}
		return treeList;
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 添加组织信息
	 * @param organize
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 上午11:11:48
	 */
	public Result addOrgInfo(Organize organize,FnfhPageData pgdata) throws FnfhException
	{
		int result=0;
		try
		{
			if(StringUtils.isEmpty(organize.getParent_id()))
			{
				return Result.error(SystemConst.NOT_NULL,"父组织不能为空");
			}
			if(StringUtils.isEmpty(organize.getOrg_name()))
			{
				return Result.error(SystemConst.NOT_NULL,"组织名称不能为空");
			}
			//根据父级组织获取组织信息
			Organize parentOrg=new Organize(organize.getParent_id());
			List<Organize> parentOrgList=organizeMapper.findOrganize(parentOrg);
			if(parentOrgList.isEmpty())
			{
				return Result.error(SystemConst.DATA_NOT_EXIST_ERROR,"父组织不存在");
			}
			parentOrg=parentOrgList.get(0);
			String parentCode=parentOrg.getParent_code();
			if(StringUtils.isEmpty(parentCode))
			{
				parentCode=parentOrg.getOrg_id().toString()+"-";
			}
			else
			{
				parentCode+=parentOrg.getOrg_id()+"-";
			}
			organize.setParent_code(parentCode);
			
			result=organizeMapper.addOrganize(organize);
			if(result<1)
			{
				return Result.error(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/addOrgInfo,"+ex.getMessage()+","+ex.toString());
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
	 * 根据组织id修改组织信息
	 * @param organize
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午1:55:21
	 */
	public Result modOrgInfoById(Organize organize,FnfhPageData pgdata) throws FnfhException
	{
		int result=0;
		try
		{
			if(organize==null||StringUtils.isEmpty(organize.getOrg_id()))
			{
				return Result.error(SystemConst.NO_PARAM,SystemConst.NO_PARAM_MSG);
			}
			if(StringUtils.isEmpty(organize.getOrg_name()))
			{
				return Result.error(SystemConst.NOT_NULL,"组织名称不能为空");
			}
			//根据id获取当前的组织信息
			Organize currOrg=new Organize(organize.getOrg_id());
			List<Organize> list=organizeMapper.findOrganize(currOrg);
			if(list.isEmpty())
			{
				return Result.error(SystemConst.DATA_NOT_EXIST_ERROR,"该组织不存在");
			}
			currOrg=list.get(0);
			
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
			
			try {
				//匹配parentId，如果不同，就是重新选择了上级组织的，需要修改下级组织信息
				if(organize.getParent_id()!=null&&currOrg.getParent_id()!=null&&
						(organize.getParent_id().intValue()!=currOrg.getParent_id().intValue()))
				{
					//获取新父级组织信息
					Organize parentOrg=new Organize(organize.getParent_id());
					List<Organize> plist=organizeMapper.findOrganize(parentOrg);
					if(plist.isEmpty())
					{
						return Result.error(SystemConst.DATA_NOT_EXIST_ERROR,"父级组织不存在");
					}
					parentOrg=plist.get(0);
					String parentCode=parentOrg.getParent_code();
					String oldParentCode=currOrg.getParent_code();//原始的父级id
					if(StringUtils.isEmpty(parentCode))
					{
						parentCode=parentOrg.getOrg_id()+"-";
//						parentCode=parentOrg.getParent_id()+"-";
					}
					else
					{
						parentCode+=parentOrg.getOrg_id()+"-";
//						parentCode+=parentOrg.getParent_id()+"-";
					}
					organize.setParent_code(parentCode);
					String childsParentCode=parentCode+organize.getOrg_id()+"-";//子级组织的上级组织id集合
					//获取原始子级目录中部分的父级目录pcode
					if(StringUtils.isEmpty(oldParentCode))
					{
						oldParentCode=currOrg.getOrg_id()+"-";
					}
					else
					{
						oldParentCode+=currOrg.getOrg_id()+"-";
					}
					//修改子级组织的pcode
					Map<String,String> param=new HashMap<String,String>();
//					param.put("newParentCode", parentCode);
					param.put("newParentCode", childsParentCode);
					param.put("oldParentCode", oldParentCode);
					result=organizeMapper.modChildOrgPCode(param);
					//修改当前组织信息
					result=organizeMapper.modOrganize(organize);
				}
				else
				{
					result=organizeMapper.modOrganize(organize);
				}
				ptm.commit(status);//提交
			} catch (Exception e) {
				ptm.rollback(status);//回滚
			}
			
			
			if(result<1)
			{
				return Result.error(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
			}
		}catch(Exception ex)
		{
			logger.error(logger.getName()+"/modOrgInfoById,"+ex.getMessage()+","+ex.toString());
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
	 * @param organize
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午2:00:31
	 */
	public Result getObjInstane(Organize organize) throws FnfhException 
	{
		Result result=new Result();
		if(StringUtils.isEmpty(organize.getOrg_id()))
		{
			return Result.error(SystemConst.NO_PARAM,SystemConst.NO_PARAM_MSG);
		}
		organize.setStart(0);
		organize.setLimit(1);
		List<Organize> list=organizeMapper.findOrganize(organize);
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
	 * 根据id集合删除组织以及下级组织信息
	 * @param jsonStr
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月3日 下午2:12:03
	 */
	public Result delOrgsByIds(String jsonStr) throws FnfhException
	{
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
				//查询根据id组织的信息
				List<Organize> delOrgList=organizeMapper.findOrganizeByIds(idsList);
				//遍历组织信息
				for (Organize organize : delOrgList) {
					String parentCode=organize.getParent_code();//下级组织的父级组织集合
					//1-，1-2-
					if(StringUtils.isEmpty(parentCode))
					{
						parentCode=organize.getOrg_id()+"-";
					}
					else
					{
						parentCode+=organize.getOrg_id()+"-";
					}
					organize.setParent_code(parentCode);
					//删除数据
					organizeMapper.delOrganizeById(organize);
				}
				ptm.commit(status);//提交
				
			} catch (Exception e) {
				logger.error(logger.getName()+"/delOrgsByIds,"+e.getMessage()+","+e.toString());
				ptm.rollback(status);//回滚
				if(e.toString().indexOf("Cannot delete or update a parent row: a foreign key constraint fails ") >= 0) {
					return Result.error(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
				}
				else
				{
					return Result.error(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
				}
			}
		}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/delOrgsByIds,"+ex.getMessage()+","+ex.toString());
			throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
		}
		return Result.success();
	}
	
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id集合删除组织信息
	 * 组织删除以后，组织以及下级组织下的人员也要被删除
	 * @param jsonStr
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @deprecated 暂未启用
	 * @creationDate:2017年5月3日 下午2:12:03
	 */
	public Result delOrgsAndUserByIds(FnfhPageData pgdata) throws FnfhException
	{
		String jsonStr=pgdata.getString("ids");
		
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
				//查询根据id组织的信息
				List<Organize> delOrgList=organizeMapper.findOrganizeByIds(idsList);
				//遍历组织信息
				for (Organize organize : delOrgList) {
					String parentCode=organize.getParent_code();//下级组织的父级组织集合
					//1-，1-2-
					if(StringUtils.isEmpty(parentCode))
					{
						parentCode=organize.getOrg_id()+"-";
					}
					else
					{
						parentCode+=organize.getOrg_id()+"-";
					}
					organize.setParent_code(parentCode);
					
					//根据组织id删除人员信息
					List<Organize> orgList=organizeMapper.findOrgAndSubByIds(organize);
					if(orgList!=null&&orgList.isEmpty())
					{
						//根据组织查找人员信息
						List<User> userList=userMapper.findUserByOrgIds(orgList);
						List<String> userIdList=getUserIdList(userList);
						//step1 权限表，删除人员权限数据
						permissionMapper.delPermissionByUserIds(userIdList);
						//step2 设备表，释放授权码，清除用户数据
							//step2.1 将设备表中的注册人员register_id置为空
						subAppUserMapper.batchClearRegisterByUserIds(userIdList);
							//step2.1 设置授权码状态为未使用
						Map<String,Object> param=new HashMap<String,Object>();
						param.put("status", 0);
						param.put("list", userIdList);
						authCodeMapper.batchUpdateCodeStatusByUserId(param);
							//step2.3 删除设备信息
						subAppUserMapper.delSubAppUserByUserIds(userIdList);
						//step3 会议资料表
							//step3.1 设置拟稿人、参会人员 user_id为空
						subAppMeetApper.clearUserInfoByUserId(userIdList);	

						//step 4删除人员
						userMapper.delUserByIds(userIdList);
					}
					
					
					//删除数据
					organizeMapper.delOrganizeById(organize);
					
					
					
				}
				ptm.commit(status);//提交
				
			} catch (Exception e) {
				logger.error(logger.getName()+"/delOrgsByIds,"+e.getMessage()+","+e.toString());
				ptm.rollback(status);//回滚
				if(e.toString().indexOf("Cannot delete or update a parent row: a foreign key constraint fails ") >= 0) {
					return Result.error(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
				}
				else
				{
					return Result.error(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
				}
			}
		}
		catch(Exception ex)
		{
			logger.error(logger.getName()+"/delOrgsByIds,"+ex.getMessage()+","+ex.toString());
			throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取用户id集合
	 * @param userList
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月10日 下午3:10:40
	 */
	private List<String> getUserIdList(List<User> userList)
	{
		List<String> ids=new ArrayList<String>();
		for (User user : userList) {
			ids.add(user.getUser_id());
		}
		return ids;
	}
	
}
