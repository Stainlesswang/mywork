package com.dv.dao.user;

import java.sql.SQLException;
import java.util.List;

import com.dv.dao.MyBatisRepository;
import com.dv.entity.user.Organize;
import com.dv.entity.user.User;

@MyBatisRepository
public interface UserMapper {
	// 查询所有用户信息
	public List<User> findUser(User user);
	// 查询用户信息总条数
	public int findUserCount(User user);
	// 增加用户信息
	public int addUser(User user);
	// 修改用户信息
	public int modUser(User user);
	// 根据id删除用户信息
	public int delUserByIds(List<String> ids);
	
	//根据组织id查询用户数据，查询该组织以下的所有人员数据信息
	public List<User> findUserByOrgId(User user);
	public int findUserCountByOrgId(User user);
	//根据组织id查询用户数据，查询该组织以下的所有人员数据信息，多加一个条件is_blinding
	public List<User> findUserByOrgIdAndisblinding(User user);
	//校验账号是否存在
	public int isExistName(User userInfo);
	
	//修改用户密码
	public int updateUserPwd(User userInfo) throws SQLException;
	//批量插入用户数据
	public int batchAddUserInfo(List<User> list);
	//批量修改用户信息20170524
	public int batchModUserInfo(List<User> list);
	
	//根据用户名查找用户
	public List <User> findUserInfoByName(User userInfo);
	
	//根据用户id集合查询用户信息，主要用于参会人员的查询
	public List<User> findUserByUserIds(List<String> ids);
	
	//根据组织集合查询人员信息20170510
	public List<User> findUserByOrgIds(List<Organize> orgList);
	
	//添加超级管理员信息
	public int addAdminInfo(User user);
	
	//通过用户id查询用户
	public abstract User findUserByUserId(String user_id);
}
