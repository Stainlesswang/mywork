/*
 * 文件名称：          PropertiesUtil.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 上午10:35:43
 * 版权所有：          
 */

package com.dv.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dv.constants.APIConstants;

/**
 * 属性工具类
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  wch
 * <p>
 * @日期：                  2017年5月24日
 * <p>
 * @负责人：              wch
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
public class PropertiesUtil
{
    // 属性文件名
    private static String propertiesFileName = "/config.properties";
    // 属性文件
    private static Properties properties;
    
    private static Logger log = Logger.getLogger(PropertiesUtil.class);

    /**
     * 得到属性值
     * 
     * @param key 属性名称
     * @return String 属性值
     * @throws FnfhException 
     */
    public static String getProperty(String key) throws FnfhException 
    {
        if (properties == null)
        {
            properties = readProperties(propertiesFileName);
        }

        if (properties == null)
        {
            return "";
        }

        return properties.getProperty(key);
    }

    /**
     * 读取Properties配置文件
     * 
     * @param file 文件路径(比如：/ace.properties)
     * @return Properties Properties配置文件
     * @throws FnfhException 
     */
    public static Properties readProperties(String file) throws FnfhException
    {
        InputStream reader = null;
        try
        {
            reader = PropertiesUtil.class.getResourceAsStream(file);
            if (reader == null)
            {
                return null;
            }

            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        }
        catch(Exception e)
        {
            log.error("read properties error");
            throw new FnfhException(APIConstants.PROPERTIES_ERROR.getCode(), APIConstants.PROPERTIES_ERROR.getName());
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch(Exception e)
                {
                    throw new FnfhException(APIConstants.PROPERTIES_ERROR.getCode(), APIConstants.PROPERTIES_ERROR.getName());
                }
            }
        }
    }

}
