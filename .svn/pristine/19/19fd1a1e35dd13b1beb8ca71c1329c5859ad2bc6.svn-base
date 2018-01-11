package com.dv.dao.meeting;

import java.util.List;
import java.util.Map;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.Authcode;
@MyBatisRepository
public interface AuthcodeMapper {
	//查询所有授权码
	public List<Authcode> findAuthcode(Authcode authcode);
	//查询授权码总条数
	public int findAuthcodeCount(Authcode authcode);
	//根据id删除授权码
	public int delAuthcodeById(List<String> ids);
	
	//根据授权码集合删除授权码信息
	public int delAuthcodeByCodes(List<String> ids);
	
	//修改授权码
	public int modAuthcode(Authcode authcode);
	//增加授权码
	public int addAuthcode(Authcode authcode);
	//根据授权码号修改状态
	public int modAuthcodeStatusByCode(Authcode authcode);
	
	//根据用户设备id批量更新授权码状态
	public int batchUpdateCodeStatusByDeviceId(Map<String,Object> param);
	//根据用户id批量更新授权码状态20170509
	public int batchUpdateCodeStatusByUserId(Map<String,Object> param);
	//根据授权码id集合批量更新授权码状态
	public int batchUpdateCodeStatusByCode(Map<String,Object> param);
	//根据前缀查询授权码
	public List<Authcode> findAuthCodeByPrefix(Map<String,Object> param);
	//批量添加授权码
	public int batchAddAuthCode(List<Authcode> list);
}
