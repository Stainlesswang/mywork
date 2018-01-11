package com.dv.entity;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @classDesc ：
 *  系统配置封装类，主要是jdbc中的数据封装
 * @creater: 李梦婷
 * @creationDate:2017年5月25日 上午9:21:41
 */
public class SystemConfig {

    private static String url;
    private static String dbName;
    private static String urlAppend;
    private static String driver;
    private static String user;
    private static String password;

    private static String mysqlExistDataBase;

    private static String adminUsername;

    private static String adminPwd;

    private static String adminRealName;

    private static String adminSex;

    private static String orgName;//组织名称

    private static String tempOrgName;//临时用户部门，用于存放临时用户的


    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        SystemConfig.url = url;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        SystemConfig.dbName = dbName;
    }

    public static String getUrlAppend() {
        return urlAppend;
    }

    public static void setUrlAppend(String urlAppend) {
        SystemConfig.urlAppend = urlAppend;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        SystemConfig.driver = driver;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        SystemConfig.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SystemConfig.password = password;
    }

    public static String getMysqlExistDataBase() {
        return mysqlExistDataBase;
    }

    public static void setMysqlExistDataBase(String mysqlExistDataBase) {
        SystemConfig.mysqlExistDataBase = mysqlExistDataBase;
    }

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static void setAdminUsername(String adminUsername) {
        SystemConfig.adminUsername = adminUsername;
    }

    public static String getAdminPwd() {
        return adminPwd;
    }

    public static void setAdminPwd(String adminPwd) {
        SystemConfig.adminPwd = adminPwd;
    }

    public static String getAdminRealName() {
        return adminRealName;
    }

    public static void setAdminRealName(String adminRealName) {
        SystemConfig.adminRealName = adminRealName;
    }

    public static String getAdminSex() {
        return adminSex;
    }

    public static void setAdminSex(String adminSex) {
        SystemConfig.adminSex = adminSex;
    }

    public static String getOrgName() {
        return orgName;
    }

    public static void setOrgName(String orgName) {
        SystemConfig.orgName = orgName;
    }

    public static String getTempOrgName()
    {
        return tempOrgName;
    }

    public static void setTempOrgName(String tempOrgName)
    {
        SystemConfig.tempOrgName = tempOrgName;
    }

    public static void main(String[] args)
    {
        ApplicationContext context =  new ClassPathXmlApplicationContext("applicationContext.xml");
        SystemConfig config = (SystemConfig)context.getBean("systemConfigBean");
        System.out.println(config.getAdminRealName());
        System.out.println(config.getOrgName());;
        System.out.println(config.getTempOrgName());

    }
}
