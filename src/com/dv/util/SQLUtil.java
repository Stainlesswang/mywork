package com.dv.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public class SQLUtil {
	
	/**
	 * 从资源文件中获取sql
	 * @param path 资源文件路径
	 * @return
	 * @throws IOException
	 */
	public static List<String> findSql(String path){
		String str = null;
		try {
			str = findFileContent(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] strs = str.split(";");
		return findSql(strs);
	}
	/**
	 * 获取存储过程sql
	 * @param path
	 * @return
	 */
	public static List<String> findProcedureSql(String path){
		String str = null;
		try {
			str = findFileContent(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] strs = str.split("#");
		return findSql(strs);
	}
	
	/**
	 * 从资源文件中获取sql集合
	 * @param path 资源文件路径
	 * @return
	 * @throws IOException
	 */
	private static String findFileContent(String path) throws IOException{
		Resource resource = new ClassPathResource(path);
		InputStream is = resource.getInputStream();
		byte[] bs = new byte[is.available()];
		is.read(bs);
		return new String(bs, "utf-8");
	}
	
	/**
	 * 获取sql
	 * @param strs
	 * @return
	 */
	private static List<String> findSql(String[] strs){
		List<String> sqls = new ArrayList<String>(strs.length);
		for (String sql : strs) {
			if (StringUtils.isEmpty(sql)) {
				continue;
			}
			sqls.add(sql);
		}
		return sqls;
	}
}
