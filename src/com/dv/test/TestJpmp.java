/*
 * 文件名称：          TestJpmp.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 上午10:22:12
 * 版权所有：          
 */

package com.dv.test;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.dv.mc.FileUtils;

import jpmp.device.UsbDevice;
import jpmp.manager.DeviceManager;

/**
 * TODO: 类注释
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年11月8日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
public class TestJpmp
{

    public static void main(String[] args)
    {
        try
        {
            /*String libName = "/jusbpmp.dll";
            File libFile = new File(FileUtils.getJarPath() + libName);
            System.out.println(libFile.getAbsolutePath());

            System.load(libFile.getAbsolutePath());
            DeviceManager dm = DeviceManager.getInstance();
            dm.createInstance();
            dm.scanDevices();
            Iterator it = dm.getDeviceList().keySet().iterator();
            System.out.println(dm.getDeviceList().size());
            while(it.hasNext())
            {
                String devkey = (String)it.next();
                UsbDevice usbdev = (UsbDevice)dm.getDeviceList().get(devkey);
                usbdev.parseFolder("/", new ParseTreeNotifier());
                // System.out.println(usbdev.dump());
                System.out.println(usbdev.getFreeSize());
                System.out.println(usbdev.getManufacturer());
                System.out.println(usbdev.getTotalSize());
                System.out.println(usbdev.getName());
            }*/
            System.out.println(System.getProperty("java.library.path"));
            DeviceManager dm = DeviceManager.getInstance();
            dm.createInstance();
            dm.scanDevices();
            
            Set<Entry<String, UsbDevice>> devices = dm.getDeviceList().entrySet();
            for(Entry<String, UsbDevice> device : devices)
            {
                UsbDevice usbdev = device.getValue();
                System.out.println(usbdev.existFile("/test/offline_FtnInfo.txt"));
                //usbdev.getFile("c:/file.mp3", "/Music/Aalto - Resolution (Original Mix).mp3", new Notifier());
                long success = usbdev.parseFolder("/", new ParseTreeNotifier());
                System.out.println(success == 0 ? "parse success" : "parse failed");
                System.out.println(usbdev.sendFile("c:/testfile.txt", "/test", null, null));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }
}
