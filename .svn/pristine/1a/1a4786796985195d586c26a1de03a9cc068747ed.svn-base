package com.dv.servlet.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dv.dao.database.DataBaseInitMapper;
import com.dv.dao.user.OrganizeMapper;
import com.dv.dao.user.UserMapper;
import com.dv.entity.SystemConfig;
import com.dv.entity.user.Organize;
import com.dv.entity.user.User;
import com.dv.test.TestRead;
import com.dv.util.CommonMethod;
import com.dv.util.Md5Util;
import com.dv.util.SQLUtil;

/**
 * 
 * @classDesc ：
 *	监听初始化类，用于初始化一些系统数据
 * @creater: 李梦婷
 * @creationDate:2017年3月13日 下午1:29:49
 */
@Repository
public class InitDataListener implements ApplicationListener<ContextRefreshedEvent>{
	private static boolean isStart = false;
	public final static String SYSTEM_INIT_TABLE = "sql/create_table_mysql.sql";
	
	private String defaultDbName="shenhua";//默认的数据库名
	private String defaultExistDbName="mysql";//默认的mysql数据库中已有的数据库表
	private String defaultUserName="admin";//默认管理员名
	private String defaultUserPwd="123456";
	private String defauleRealName="超级管理员";
	private String defaultOrgName="信息公司";
	private String defaultSex="0";
	private String defaultTempOrgName = "临时用户组";
	
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	@Resource
	private DataBaseInitMapper dataBaseInitMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private OrganizeMapper organizeMapper;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.  
	         //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			if(!isStart)
			{
				isStart=true;
				//逻辑代码在此处写。。。。。。
				
				try {
					handleSystemConfigData();
					Class.forName(SystemConfig.getDriver());
					 
					//一开始必须填一个已经存在的数据库
//					String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8";   
					String url =SystemConfig.getUrl()+SystemConfig.getMysqlExistDataBase()+SystemConfig.getUrlAppend();
					Connection conn = DriverManager.getConnection(url, SystemConfig.getUser(), SystemConfig.getPassword());
					Statement stat = conn.createStatement();
					//判断数据库是否存在
			        ResultSet result = stat.executeQuery("SELECT count(*) as num FROM information_schema.SCHEMATA where SCHEMA_NAME='"+SystemConfig.getDbName()+"'");
			        boolean flag=false;
			        while (result.next())
			        {
			            if(result.getInt("num")==0)
			            {
			            	flag=true;
							break;
			            }
			        }
					if(flag)
					{
		            	//创建数据库
						stat.executeUpdate("CREATE DATABASE IF NOT EXISTS "+SystemConfig.getDbName()+" DEFAULT CHARSET utf8 COLLATE utf8_general_ci;");
					}
					//关闭创建的数据库
					stat.close();
					conn.close();
					
					//创建表
					if(flag)
					{
						createTables();
						initTableData();
					}
					
				} catch (ClassNotFoundException e) {
					System.out.println("数据库初始化失败，原因:"+e.getMessage());
					logger.error("ClassNotFoundException数据库初始化失败，原因:"+e.getMessage());
				} catch (SQLException e) {
					System.out.println("数据库初始化失败，原因:"+e.getMessage());
					logger.error("SQLException数据库初始化失败，原因:"+e.getMessage());
				} catch(Exception e)
				{
					System.out.println("数据库初始化失败，原因:"+e.getMessage());
					logger.error("Exception数据库初始化失败，原因:"+e.getMessage());
				}
				
				System.out.println("-------InitDataListener");
			}
	    }
		
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 处理系统配置数据 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月26日 下午1:12:24
	 */
	private void handleSystemConfigData()
	{
		if(StringUtils.isEmpty(SystemConfig.getDbName()))
		{
			SystemConfig.setDbName(defaultDbName);
		}
		if(StringUtils.isEmpty(SystemConfig.getMysqlExistDataBase()))
		{
			SystemConfig.setMysqlExistDataBase(defaultExistDbName);
		}
		if(StringUtils.isEmpty(SystemConfig.getAdminUsername()))
		{
			SystemConfig.setAdminUsername(defaultUserName);
		}
		if(StringUtils.isEmpty(SystemConfig.getAdminPwd()))
		{
			SystemConfig.setAdminPwd(defaultUserPwd);
		}
		String userRealName=SystemConfig.getAdminRealName();
		if(StringUtils.isEmpty(userRealName))
		{
			userRealName=defauleRealName;
		}
		else
		{
			try {
				userRealName=new String(userRealName.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				userRealName=defauleRealName;
			}
		}
		SystemConfig.setAdminRealName(userRealName);
		
		if(StringUtils.isEmpty(SystemConfig.getAdminSex()))
		{
			SystemConfig.setAdminSex(defaultSex);
		}
		//组织名称
		String orgName=SystemConfig.getOrgName();
		if(StringUtils.isEmpty(orgName))
		{
			orgName=defaultOrgName;
		}
		else
		{
			try {
				orgName=new String(orgName.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				orgName=defaultOrgName;
			}
		}
		SystemConfig.setOrgName(orgName);
		//临时用户组
		String tempOrgName =  SystemConfig.getTempOrgName();
		if (StringUtils.isEmpty(tempOrgName))
		{
		    tempOrgName = defaultTempOrgName;
		}
		else
		{
		    try
            {
                tempOrgName = new String(tempOrgName.getBytes("ISO-8859"), "UTF_8");
            }
            catch(UnsupportedEncodingException e)
            {
                tempOrgName = defaultTempOrgName;
            }
		}
		SystemConfig.setTempOrgName(tempOrgName);
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 读取配置文件数据， 
	 * @creater: 李梦婷
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月26日 下午1:14:22
	 */
	private void readProperties()
	{
		try {
			//使用io流读取，解决中文乱码
			Properties properties = new Properties();
			InputStream inputStream = TestRead.class.getResourceAsStream("/base.properties");
			BufferedReader bf = new BufferedReader(new  InputStreamReader(inputStream, "UTF-8"));
			properties.load(bf);
			String org_name = properties.getProperty("org_name");// 获取预先配置的内容 ，contentProperty是配置文件里对应的标识名
			String admin_real_name=properties.getProperty("admin_real_name");
			SystemConfig.setAdminRealName(admin_real_name);
			SystemConfig.setOrgName(org_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 初始化建表 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月25日 上午10:29:39
	 */
	private void createTables() {
		executeSql(SYSTEM_INIT_TABLE);
	}
	
	/**
	 * 执行sql资源
	 * @param sqls
	 * @throws IOException
	 */
	private void executeSql(String sqlPath) {
		List<String> sqls = SQLUtil.findSql(sqlPath);
		if (CollectionUtils.isEmpty(sqls)) {
			return;
		}
		dataBaseExecuteSql(sqls);
	}
	
	/**
	 * 执行sql
	 * @param sqls
	 */
	private void dataBaseExecuteSql(List<String> sqls){
		for (String sql : sqls) {
			if(!StringUtils.isEmpty(sql))
			{
//				System.out.println("数据库sql:"+sql);
				dataBaseInitMapper.executeSql(sql);
			}
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 *  初始化表数据
	 * @creater: 李梦婷
	 * @creationDate:2017年5月26日 下午1:10:39
	 */
	private void initTableData()
	{
		//插入超级管理员信息
		User adminUser=new User();
		adminUser.setUser_id(CommonMethod.getUID());
		adminUser.setUser_name(SystemConfig.getAdminUsername());
		adminUser.setPassword(Md5Util.md5(SystemConfig.getAdminPwd()));
		int _sex=CommonMethod.StringToInt(SystemConfig.getAdminSex(), 0);
		_sex=_sex!=1?0:1;
		adminUser.setSex(_sex);
		adminUser.setReal_name(SystemConfig.getAdminRealName());
		adminUser.setIs_super(1);
//		System.out.println("数据库初始化完成");
		userMapper.addAdminInfo(adminUser);
		//插入组织信息
		Organize orgInfo=new Organize();
		orgInfo.setOrg_name(SystemConfig.getOrgName());
		organizeMapper.addOrganize(orgInfo);
		//插入一个临时部门
		Organize tempOrgInfo = new Organize();
		tempOrgInfo.setOrg_name(SystemConfig.getTempOrgName());
		tempOrgInfo.setParent_id(orgInfo.getOrg_id());
		tempOrgInfo.setParent_code(orgInfo.getOrg_id()+"-");
		organizeMapper.addOrganize(tempOrgInfo);
	}
	
}
