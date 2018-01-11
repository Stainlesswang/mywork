package com.dv.service.meeting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.dao.meeting.AuthcodeMapper;
import com.dv.dao.meeting.SubAppUserMapper;
import com.dv.dao.user.UserMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.Authcode;
import com.dv.entity.meeting.SubAppMeeting;
import com.dv.entity.meeting.SubAppUser;
import com.dv.entity.user.User;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
import com.dv.util.MTPFileManager;

import be.derycke.pieter.com.COMException;
import jmtp.PortableDevice;
import jmtp.PortableDeviceManager;
import jmtp.PortableDeviceObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @classDesc ：
 *	设置管理service
 * @creater: 杨群山
 * @creationDate:2017年5月3日 上午10:51:45
 */
@Service
public class SubAppUserService {

	@Resource
	private  SubAppUserMapper subAppUserMapper;
	
	@Resource
	private  AuthcodeMapper authCodeMapper;//授权码mapper
	
	@Resource
	private PlatformTransactionManager ptm;
	
	@Resource
	private UserMapper userMapper;
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	/**
	 * 
	 * @methodDesc:  
	 * 查询设置管理
	 * @param subAppUser
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 下午3:00:43
	 */
	public Result findsubAppUser(SubAppUser subAppUser,FnfhPageData fnfhPageData) throws FnfhException{
		Result result=new Result();
		int totalCount=0;
		subAppUser.setStart(fnfhPageData.getOffset());
		subAppUser.setLimit(fnfhPageData.getLimit());
		String search=fnfhPageData.getSearch();
		String order=fnfhPageData.getOrder();
		String sort=fnfhPageData.getSort();
		String is_binding=fnfhPageData.getString("is_binding");
		
		if (!StringUtils.isEmpty(order)) {
			subAppUser.setOrder(order);
		}
		if (!StringUtils.isEmpty(sort)) {
			subAppUser.setSort(sort);
		}
		if (!StringUtils.isEmpty(search)) {
			subAppUser.setSearch(search);
		}	
		if (!StringUtils.isEmpty(is_binding)) {
			subAppUser.setIs_binding(CommonMethod.StringToInt(is_binding, -1));
		}
		List<SubAppUser> list=subAppUserMapper.findSubAppUser(subAppUser);
		totalCount=subAppUserMapper.findSubAppUserCount(subAppUser);
		result.setRows(list);
		result.setTotal(totalCount);
		return result;	
		}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 增加或者修改pad信息维护单
	 * @param subAppUser
	 * @param pgData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 上午9:37:03
	 */
	public Result addOrModifySubAppUser(SubAppUser subAppUser,FnfhPageData pgData,User loginUser, int limitUserNum) throws FnfhException{
		int result=0;
		try {
			subAppUser.setRegister_id(loginUser.getUser_id());//登记人
			
			if(StringUtils.isEmpty(subAppUser.getUser_id()))
			{
				return Result.error(SystemConst.NOT_NULL, "设备使用人不能为空");
			}
			/*if(StringUtils.isEmpty(subAppUser.getAuthorationcode()))
			{
				return Result.error(SystemConst.NOT_NULL, "授权码不能为空");
			}*/
			//判断授权码使用人数是否超过授权人数
            Authcode ac = new Authcode();
            ac.setStatus(1);
            int usedAuthCodeNum = authCodeMapper.findAuthcodeCount(ac);
            if (usedAuthCodeNum >= limitUserNum)
            {
                return Result.error(SystemConst.CHECKATTENDEESLIMIT_ERROR, SystemConst.CHECKATTENDEESLIMIT_ERROR_MSG+":"+ limitUserNum);
            }
            
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
			
			if(StringUtils.isEmpty(subAppUser.getMid()))//id为空，新增
			{
				try {
					String mid=CommonMethod.getUID();
					subAppUser.setMid(mid);
					//如果指定了设备编码,表示已经绑定了设备
					if (!StringUtils.isEmpty(subAppUser.getIpad_uuid()))
					{
					    subAppUser.setIs_binding(1);
					    //获取绑定的用户信息
					    User user = userMapper.findUserByUserId(subAppUser.getUser_id());
					    //将实体类转换成json字符串
					    String userInfoJsonStr = JSONObject.fromObject(user).toString();
					    //将json字符串写入临时的josn文件
					    File tmpJsonFile = new File(System.getProperty("shenhua.root") + File.separator + "tmp"
		                    + File.separator + System.currentTimeMillis() + File.separator + "userinfo.json");
					    FileUtils.writeStringToFile(tmpJsonFile, userInfoJsonStr, "UTF-8");
					    //将json文件发送到设备上
					    PortableDeviceManager manager = new PortableDeviceManager();
				        PortableDevice[] devices = manager.getDevices();
				        for (int i = 0; i < devices.length; i++)
				        {
				            MTPFileManager fileManager = new MTPFileManager();
				            fileManager.openDevice(devices[i]);
				            //判断会议是否推送到此设备
				            if (isDeviceCodeMatch(fileManager, devices[i], subAppUser.getIpad_uuid()))
				            {
				                fileManager.addFile(tmpJsonFile, "\\shenhua");
				            }
				            fileManager.closeDevice();
				        }
					    //删除掉临时的json文件及其上一层文件夹
                        FileUtils.deleteDirectory(tmpJsonFile.getParentFile());
					}
					//保存设备信息
					result=subAppUserMapper.addSubAppUser(subAppUser);
					//如果授权码不为空，设置授权码表中的字段为已使用
					if(result>0&&!StringUtils.isEmpty(subAppUser.getAuthorationcode()))
					{
						Authcode authCode=new Authcode();
						authCode.setAuthcode(subAppUser.getAuthorationcode());
						authCode.setStatus(1);
						result=authCodeMapper.modAuthcodeStatusByCode(authCode);
					}
					if(result<1)
					{
						ptm.rollback(status);//回滚
						return Result.error(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
					}
					else
					{
						ptm.commit(status);//提交
					}
				} catch (Exception e) {
				    logger.error(logger.getName() + ":addOrModifySubAppUser", e);
					ptm.rollback(status);//回滚
					return Result.error(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
				}
			}
			else //修改
			{
				try {
					
					//step1 根据id获取设备信息
					SubAppUser _subAppUser=new SubAppUser();
					_subAppUser.setMid(subAppUser.getMid());
					List<SubAppUser> list=subAppUserMapper.findSubAppUser(_subAppUser);
					if(list.isEmpty())
					{
						return Result.error(SystemConst.DATA_NOT_EXIST_ERROR, "设备信息不存在");
					}
					_subAppUser=list.get(0);
					//修改设备信息
					result=subAppUserMapper.modSubAppUserById(subAppUser);
					String oldAuthCode=_subAppUser.getAuthorationcode();//原始的授权码
					String newAuthCode=subAppUser.getAuthorationcode();//新的授权码
					//step2 判断授权码是否一致，如果不一致，进行更新授权码状态操作
					if(result>0)
					{
						if(!StringUtils.isEmpty(newAuthCode)&&!newAuthCode.equals(oldAuthCode))
						{
							//step2.1 修改新的授权码状态为1，使用
							Authcode authCode=new Authcode();
							authCode.setAuthcode(newAuthCode);
							authCode.setStatus(1);
							result=authCodeMapper.modAuthcodeStatusByCode(authCode);
							//step2.2 如果原始授权码不为空,将原始授权码状态设置为0，未使用
							if(result>0&&!StringUtils.isEmpty(oldAuthCode))
							{
								authCode.setAuthcode(oldAuthCode);
								authCode.setStatus(0);
								result=authCodeMapper.modAuthcodeStatusByCode(authCode);
							}
						}
						else if(StringUtils.isEmpty(newAuthCode)&&!StringUtils.isEmpty(oldAuthCode))
						{
							//step2.2 如果原始授权码不为空,将原始授权码状态设置为0，未使用
							if(result>0)
							{
								Authcode authCode=new Authcode();
								authCode.setAuthcode(oldAuthCode);
								authCode.setStatus(0);
								result=authCodeMapper.modAuthcodeStatusByCode(authCode);
							}
						}
						
					}
					if(result<1)
					{
						ptm.rollback(status);//回滚
						return Result.error(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
					}
					else
					{
						ptm.commit(status);//提交
					}
				} catch (Exception e) {
					ptm.rollback(status);//回滚
					return Result.error(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
				}
				
			}
			
			
		} catch (Exception e) {
			logger.error(logger.getName()+"/addOrModifySubAppUser,"+e.getMessage()+","+e.toString());
			if (e.toString().indexOf("Duplicate entry")>=0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}else {
				throw new FnfhException(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}	
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id删除设备绑定信息，需要释放授权码
	 * @param pgData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午1:05:57
	 */
	public Result delSubAppUserByIds(FnfhPageData pgData) throws FnfhException{
		int result=0;
		String jsonStr=pgData.getString("ids");
		if (StringUtils.isEmpty(jsonStr)) {
			return Result.error(SystemConst.NOT_NULL, SystemConst.NOT_NULL_MSG);
		}
		try {
			JSONArray jsonArray2=JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
			try {
				//step1 设置授权码状态为未使用
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("status", 0);
				param.put("list", idsList);
				result=authCodeMapper.batchUpdateCodeStatusByDeviceId(param);
				//step2 删除设备信息
				result=subAppUserMapper.delSubAppUserByIds(idsList);
				if (result<1) {
					ptm.rollback(status);//回滚
					return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
				}
				ptm.commit(status);//提交
				
			} catch (Exception e) {
				ptm.rollback(status);//回滚
				return Result.error(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
			}
		} catch (Exception ex) {
			logger.error(logger.getName()+"/delSubAppUserByIds,"+ex.getMessage()+","+ex.toString());
			if (ex.toString().indexOf("Cannot delete or update a parent row:a foreign key constraint fails") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}else {
				throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id集合清除设备绑定信息，释放授权码
	 * @param pgData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 下午1:25:36
	 */
	public Result clearSubAppUserByIds(FnfhPageData pgData) throws FnfhException{
		int result=0;
		String jsonStr=pgData.getString("ids");
		if (StringUtils.isEmpty(jsonStr)) {
			return Result.error(SystemConst.NOT_NULL, SystemConst.NOT_NULL_MSG);
		}
		try {
			JSONArray jsonArray2=JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
			try {
				//step1 设置授权码状态为未使用
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("status", 0);
				param.put("list", idsList);
				result=authCodeMapper.batchUpdateCodeStatusByDeviceId(param);
				//step2 设置设备信息中的授权码以及pad编号置为空
				result=subAppUserMapper.clearBindInfoByIds(idsList);
				if (result<1) {
					ptm.rollback(status);//回滚
					return Result.error(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG);
				}
				ptm.commit(status);//提交
				
			} catch (Exception e) {
				ptm.rollback(status);//回滚
				return Result.error(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
			}
		} catch (Exception ex) {
			logger.error(logger.getName()+"/delSubAppUserByIds,"+ex.getMessage()+","+ex.toString());
			if (ex.toString().indexOf("Cannot delete or update a parent row:a foreign key constraint fails") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}else {
				throw new FnfhException(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 修改设置管理 
	 * @param subAppUser
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 杨群山
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月5日 下午3:00:43
	 */
	public Result modsubAppUser(SubAppUser subAppUser)throws FnfhException{
		int result=0;
		try {
			if (subAppUser==null||StringUtils.isEmpty(subAppUser.getMid())) {
				return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
			}
			result=subAppUserMapper.modSubAppUserById(subAppUser);
			if (result<1) {
				return Result.error(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
			}
		} catch (Exception ex) {
			logger.error(logger.getName()+"/modsubAppUser,"+ex.getMessage()+","+ex.toString());
			if (ex.toString().indexOf("Dupilcate entry") >= 0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}else {
				throw new FnfhException(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
			}
		}	
		return Result.success();
	}
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param subAppUser
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 杨群山
	 * @creationDate:2017年5月5日 下午3:00:43
	 */
	public Result getObjInstane(SubAppUser subAppUser)throws FnfhException{
		Result result =new Result();
		if (StringUtils.isEmpty(subAppUser.getMid())) {
			return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
		}
		subAppUser.setStart(0);
		subAppUser.setLimit(1);
		List<SubAppUser> list=subAppUserMapper.findSubAppUser(subAppUser);
		if (list!=null&&list.size()>0) {
			result.setData(list.get(0));
			return result;
		}else{
			return Result.error(SystemConst.QUERY_FAIL, SystemConst.QUERY_FAIL_MSG);
		}
	}


    /**
     * 更新设备电量
     * @param ipadUUID
     * @param energy
     * @return
     */
    public Result updateDeviceEnergy(String ipadUUID, Integer energy)
    {
        SubAppUser subAppUser = new SubAppUser();
        subAppUser.setIpad_uuid(ipadUUID);;
        List<SubAppUser> appList = subAppUserMapper.findSubAppUser(subAppUser);
        if (appList == null || appList.isEmpty())
        {
            return Result.error(APIConstants.DEVICE_NOT_EXIST.getCode(), APIConstants.DEVICE_NOT_EXIST.getName());
        }
        int count = 0;
        subAppUser.setEnergy(energy);
        try
        {
            count = subAppUserMapper.setEnergyByIpadUUID(subAppUser);
            if (count < 1)
            {
                return Result.error(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
            }
            
        }
        catch (Exception e)
        {
            logger.error(logger.getName()+"/updateDeviceEnergy,"+e.getMessage()+","+e.toString());
            return Result.error(SystemConst.MODIFY_FAIL,SystemConst.MODIFY_FAIL_MSG);
        
        }
        return Result.success();
    }
    

    //判断设备编码是否和设备中记录的编码匹配
    private boolean isDeviceCodeMatch(MTPFileManager fileManager, PortableDevice device, String deviceCode)
    {
//        MTPFileManager fileManager = new MTPFileManager();
//        fileManager.openDevice(device);
      //设备编码在 \\shenhua\\devicecode.txt中
        PortableDeviceObject pdo;
        try
        {
            pdo = fileManager.findFile("devicecode.txt", "\\shenhua");
            if (pdo != null)
            {
                //将devicecode.txt从设备中拷贝到服务器的临时目录下
                String tmpDirPath = System.getProperty("shenhua.root") + "tmp"
                    + File.separator + System.currentTimeMillis();
                File file = new File(tmpDirPath);
                if (!file.exists())
                {
                    file.mkdirs();
                }
                fileManager.getFile(pdo.getID(), tmpDirPath.replace("\\", "\\\\"));
                //拷贝完成后读取txt中的设备编码
                File deviceCodeFile = new File(tmpDirPath + File.separator + "devicecode.txt");
                String deviceInfoStr = FileUtils.readFileToString(deviceCodeFile, "UTF-8");
                //删除临时的文件
                FileUtils.deleteDirectory(file);
                
                JSONObject deviceInfoJson = JSONObject.fromObject(deviceInfoStr);
                return deviceInfoJson.getString("devicecode").equals(deviceCode);
            }
        }
        catch(IOException e)
        {
            logger.error("read temp code file error", e);
            return false;
        }
        catch(COMException e)
        {
            logger.error("read device code error", e);
            return false;
        }
        finally 
        {
//            fileManager.closeDevice();
        }
        return false;
    }
}
