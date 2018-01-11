package com.dv.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDataBase {
	public static void main(String[] args) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
         
        //一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true";   
        Connection conn = DriverManager.getConnection(url, "root", "root");
        Statement stat = conn.createStatement();
         
        //创建数据库hello
        stat.executeUpdate("CREATE DATABASE IF NOT EXISTS hello DEFAULT CHARSET utf8 COLLATE utf8_general_ci;");
        
        
        //打开创建的数据库
        stat.close();
        conn.close();
//        url = "jdbc:mysql://localhost:3306/hello?useUnicode=true&characterEncoding=utf-8";
//        conn = DriverManager.getConnection(url, "root", "123456");
//        stat = conn.createStatement();
//         
//        //创建表test
//        stat.executeUpdate("create table test(id int, name varchar(80))");
//         
//        //添加数据
//        stat.executeUpdate("insert into test values(1, '张三')");
//        stat.executeUpdate("insert into test values(2, '李四')");
//         
//        //查询数据
//        ResultSet result = stat.executeQuery("select * from test");
//        while (result.next())
//        {
//            System.out.println(result.getInt("id") + " " + result.getString("name"));
//        }
//         
//        //关闭数据库
//        result.close();
//        stat.close();
//        conn.close();
    }
}
