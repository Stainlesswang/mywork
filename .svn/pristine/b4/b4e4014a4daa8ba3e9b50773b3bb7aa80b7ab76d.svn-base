package com.dv.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class TestRead {
	public static void main(String[] args) {
		try {
			String content = "";
			  //使用io流读取，解决中文乱码
			  Properties properties = new Properties();
			  InputStream inputStream = TestRead.class.getResourceAsStream("/base.properties");
			  BufferedReader bf = new BufferedReader(new  InputStreamReader(inputStream, "UTF-8"));
			  properties.load(bf);
			  content = properties.getProperty("org_name");// 获取预先配置的内容 ，contentProperty是配置文件里对应的标识名
			  System.out.println(content);
			  System.out.println(properties.getProperty("admin_real_name"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
