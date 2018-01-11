package com.dv.dao.database;

import org.apache.ibatis.annotations.Param;

import com.dv.dao.MyBatisRepository;

/**
 * 
 * @classDesc ：
 *	数据库初始化mapper
 * @creater: 李梦婷
 * @creationDate:2017年5月25日 上午10:19:48
 */
@MyBatisRepository
public interface DataBaseInitMapper {
	
	public int findTableCount();
	
	/**
	 * 执行sql
	 * @param sql
	 */
	public int executeSql(@Param("sql")String sql);
}
