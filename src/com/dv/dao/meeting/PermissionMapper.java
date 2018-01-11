package com.dv.dao.meeting;

import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.meeting.Permission;

/**
 * 
 * @classDesc ：
 *	权限mapper
 * @creater: 李梦婷
 * @creationDate:2017年5月8日 下午3:29:31
 */
@MyBatisRepository
public interface PermissionMapper {
	//查找权限
	public List<Permission> findPermission(Permission permission);
	//根据权限类型删除权限信息
	public int delPermissionByType(Permission permission);
	//批量添加权限信息
	public int batchAddPermission(List<Permission> list);
	//根据用户id删除权限信息
	public int delPermissionByUserIds(List<String> list);
}
