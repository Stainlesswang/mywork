/*
 * 文件名称：          WebServletContextListener.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 下午9:41:54
 * 版权所有：          
 */

package com.dv.servlet.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听系统初始化
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年5月24日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
public class WebServletContextListener implements ServletContextListener
{

    @ Override
    public void contextDestroyed(ServletContextEvent arg0)
    {

        
    }

    @ Override
    public void contextInitialized(ServletContextEvent event)
    {
        ServletContext context = event.getServletContext();
        File[] rootFiles = File.listRoots();
        //系统盘符
        List<String> driverLetterList = new ArrayList<String>();
        for (int i = 0; i < rootFiles.length; i++)
        {
            driverLetterList.add(rootFiles[i].getAbsolutePath());
        }
        //系统盘符
        context.setAttribute("driverLetter", driverLetterList);


        //加载jmtp动态库
        String libName = "/jmtp64.dll";
        File libFile = new File(com.converter.utils.FileUtils.getJarPath() + libName);
        System.out.println(libFile.getAbsolutePath());
        System.load(libFile.getAbsolutePath());
        System.out.println("jmtp.dll loaded successfully! ");

    }

}
