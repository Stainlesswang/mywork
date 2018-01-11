package com.dv.dao.user;

import java.util.List;
import java.util.Map;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.user.Organize;

/**
 * 
 * @classDesc ：
 *	组织mapper
 * @creater: 李梦婷
 * @creationDate:2017年5月3日 下午2:22:18
 */
@MyBatisRepository
public interface OrganizeMapper {
	// 查询组织所有信息
	public List<Organize> findOrganize(Organize organize);

	// 查询组织信息总体数
	public int findOrganizeCount(Organize organize);

	// 增加组织信息
	public int addOrganize(Organize organize);

	// 修改组织信息
	public int modOrganize(Organize organize);

	// 根据id删除组织信息
	public int delOrganizeByIds(List<String> ids);
	//根据组织id删除组织以及下级组织
	public int delOrganizeById(Organize organize);
	//查询顶层组织
	public List<Organize> findTopOrganize(Organize organize);
	//根据id集合查询组织信息
	public List<Organize> findOrganizeByIds(List<String> ids);
	//修改子级组织的pcode
	public int modChildOrgPCode(Map<String,String> paramMap);
	
	//根据组织id查询组织以及下级组织
	public List<Organize> findOrgAndSubByIds(Organize organize);
	//获取数据库中最大的组织id
	public int findMaxOrgId();
}
