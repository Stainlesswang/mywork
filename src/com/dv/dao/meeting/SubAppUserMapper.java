package com.dv.dao.meeting;

import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.SubAppUser;
 
/**
 * 
 * @classDesc ：
 *	设备使用人表 mapper
 * @creater: 杨群山
 * @creationDate:2017年5月2日 下午1:32:18
 */
@MyBatisRepository
public interface SubAppUserMapper {
	//查找所有设备使用人
	public List<SubAppUser> findSubAppUser(SubAppUser subAppUser);
	//查找设备使用人总条数
	public int findSubAppUserCount(SubAppUser subAppUser);
	//增加设备使用人
	public int addSubAppUser(SubAppUser subAppUser);
	//根据id删除设备使用人
	public int delSubAppUserByIds(List<String> ids);
	
	//修改设备使用人
	public int modSubAppUserById(SubAppUser subAppUser);
	//清除绑定信息，设置授权码以及pad编号为空
	public int clearBindInfoByIds(List<String> ids);
	
	//根据用户id清除注册人员信息20170509
	public int batchClearRegisterByUserIds(List<String> ids);
	//根据用户id删除设备使用人20170509
	public int delSubAppUserByUserIds(List<String> ids);
	
	//根据授权码集合清除绑定信息，设置授权码以及pad编号为空
	public int clearBindInfoByAuthCodes(List<String> ids);
	
	//根据设备编号，设置设备电量
	public int setEnergyByIpadUUID(SubAppUser subAppUser);
	//根据设备编号，设置设备存储信息
	public int setStorageByIpadUUID(SubAppUser subAppUser);

}
