/*
 * 文件名称：          DeviceController.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 下午1:55:44
 * 版权所有：          
 */

package com.dv.controller.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dv.entity.Result;
import com.dv.util.MTPFileManager;

import be.derycke.pieter.com.COMException;
import jmtp.PortableDevice;
import jmtp.PortableDeviceManager;
import jmtp.PortableDeviceObject;
import net.sf.json.JSONObject;

/**
 * TODO: 类注释
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年11月26日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
@Controller
@RequestMapping(value="device")
public class DeviceController
{

    Logger logger = Logger.getLogger(getClass());
    
    /**
     * 获取未绑定设备的设备编码
     * @return
     */
    @RequestMapping(value="getDeviceCodeList")
    @ResponseBody
    public Result getDeviceCodeList()
    {
        PortableDeviceManager manager = new PortableDeviceManager();
        PortableDevice[] devices = manager.getDevices();
        //设备编码list
        List<Map<String, String>> deviceCodeList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < devices.length; i++)
        {
            MTPFileManager fileManager = new MTPFileManager();
            fileManager.openDevice(devices[i]);
            //设备编码在 \\shenhua\\devicecode.txt中
            PortableDeviceObject pdo;
            try
            {
                pdo = fileManager.findFile("userinfo.json", "\\shenhua");
                String userinfoStr = null;
                if (pdo != null)
                {
                  //将userinfo.json从设备中拷贝到服务器的临时目录下
                    String tmpDirPath = System.getProperty("shenhua.root") + "tmp"
                        + File.separator + System.currentTimeMillis();
                    File file = new File(tmpDirPath);
                    if (!file.exists())
                    {
                        file.mkdirs();
                    }
                    fileManager.getFile(pdo.getID(), tmpDirPath.replace("\\", "\\\\"));
                    //拷贝完成后读取内容
                    File userinfoFile = new File(tmpDirPath + File.separator + "userinfo.json");
                    userinfoStr = FileUtils.readFileToString(userinfoFile, "UTF-8");
                    FileUtils.deleteDirectory(file);
                }
                //如果userinfo.json不存在，或者存在但是内容为空，表示此设备是没绑定过的
                if (pdo == null || StringUtils.isEmpty(userinfoStr))
                {
                    //寻找devicecode.txt文件
                    pdo = fileManager.findFile("devicecode.txt", "\\shenhua");
                    if (pdo != null)
                    {
                        //将devicecode.txt从设备中拷贝到服务器的临时目录下
                        String tmpDirPath = System.getProperty("shenhua.root") + "tmp"
                            + File.separator + System.currentTimeMillis();
                        File file = new File(tmpDirPath);
                        if (!file.exists())
                        {
                            file.mkdirs();
                        }
                        fileManager.getFile(pdo.getID(), tmpDirPath.replace("\\", "\\\\"));
                        //拷贝完成后读取txt中的设备编码
                        File deviceCodeFile = new File(tmpDirPath + File.separator + "devicecode.txt");
                        String deviceInfo = FileUtils.readFileToString(deviceCodeFile, "UTF-8");
                        //deviceINfo为json字符串，需要解析
                        JSONObject deviceInfoJson = JSONObject.fromObject(deviceInfo);
                        Map<String, String> deviceInfoMap = new HashMap<String, String>();
                        deviceInfoMap.put("deviceName", deviceInfoJson.getString("devicename"));
                        deviceInfoMap.put("deviceCode", deviceInfoJson.getString("devicecode"));
                        deviceCodeList.add(deviceInfoMap);
                        //删除临时的文件
                        FileUtils.deleteDirectory(file);
                    }
                }
            }
            catch(IOException e)
            {
                logger.error("read temp code file error", e);
                return Result.error(90008, "读取临时文件出错");
            }
            catch(COMException e)
            {
                logger.error("read device code error", e);
                return Result.error(90009, "获取便携式设备编码错误");
            }
            finally 
            {
                fileManager.closeDevice();
            }
            
        }
        Result result = Result.success();
        result.setData(deviceCodeList);
        return result;
    }
}
