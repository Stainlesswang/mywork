/*
 * 文件名称：          Jmtp.java
 * 编译器：              JDK1.8.0_05
 * 时间：                 下午1:33:28
 * 版权所有：          
 */

package com.dv.test;

import java.io.File;
import java.math.BigInteger;

import com.converter.utils.FileUtils;

import be.derycke.pieter.com.COMException;
import jmtp.PortableDevice;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceManager;
import jmtp.PortableDeviceObject;
import jmtp.PortableDeviceStorageObject;
import jmtp.PortableDeviceToHostImpl32;

/**
 * TODO: 类注释
 * 
 * <p>
 * <p>
 * @版本：                  v1.0
 * <p>
 * @作者：                  Administrator
 * <p>
 * @日期：                  2017年11月7日
 * <p>
 * @负责人：              Administrator
 * <p>
 * @负责团队：          
 * <p>
 * <p>
 */
public class Jmtp
{
    /*public static void main(String[] args)
    {
        String libName = "/jmtp.dll";
        File libFile = new File(FileUtils.getJarPath() + libName);
        System.out.println(libFile.getAbsolutePath());

        System.load(libFile.getAbsolutePath());
        PortableDeviceManager manager = new PortableDeviceManager();
        PortableDevice device = manager.getDevices()[1];
        device.open();
        System.out.println(device.getModel());
        System.out.println("---------------");
        //Iterate over deviceObjects
        for (PortableDeviceObject object : device.getRootObjects())
        {
            //If the object is a storage object
            if (object instanceof PortableDeviceStorageObject)
            {
                PortableDeviceStorageObject storage = (PortableDeviceStorageObject)object;
                for (PortableDeviceObject o2 : storage.getChildObjects())
                {
                    System.out.println(o2.getOriginalFileName());
                }
            }
        }
        manager.getDevices()[1].close();
    }*/
    
    
    public static void main(String[] args) throws Throwable {
       // jMTPeMethode();
        
        String libName = "/jmtp.dll";
        File libFile = new File(FileUtils.getJarPath() + libName);
        System.out.println(libFile.getAbsolutePath());

        System.load(libFile.getAbsolutePath());
        
        PortableDeviceManager manager = new PortableDeviceManager();
        PortableDevice[] devices = manager.getDevices();
        for (int i = 0; i < devices.length; i++)
        {
            MTPFileManager fileManager = new MTPFileManager();
            fileManager.openDevice(devices[i]);
            fileManager.closeDevice();
            fileManager.openDevice(devices[i]);
            fileManager.closeDevice();
            System.out.println(devices[i].getModel());
            System.out.println(new String(devices[i].getDescription().getBytes(), "utf-8"));
            System.out.println(devices[i].getFriendlyName() + "--friendlyName");
            System.out.println(devices[i].getFirmwareVersion() + "--firmwareVersion");
            System.out.println(devices[i].getManufacturer() + "--manufacturer");
            System.out.println(devices[i].getProtocol() + "--protocol");
            System.out.println(devices[i].toString());
            //System.out.println(fileManager.getAllFilesByName("\\testFolder"));
//            File file = new File("C:\\GettingJMTP.pdf");
//            fileManager.createFolder("\\testFolder\\uploadfile");
//            fileManager.addFile(file, "\\testFolder");
//            List<PortableDeviceObject> pdoList = fileManager.getFiles("\\testFolder\\test1\\GettingJMTP.pdf");
            PortableDeviceObject pdo = fileManager.findFile("GettingJMTP.pdf", "\\testFolder\\test1");
            fileManager.getFile(pdo.getID(), "c://test");
            fileManager.closeDevice();
            
        }
    }

    private static void jMTPeMethode() 
    {
        PortableDeviceFolderObject targetFolder = null;
        PortableDeviceManager manager = new PortableDeviceManager();
        PortableDevice device = manager.getDevices()[1];
        // Connect to USB tablet
        device.open();
        System.out.println(device.getModel());

        System.out.println("---------------");

        // Iterate over deviceObjects
        for (PortableDeviceObject object : device.getRootObjects()) 
        {
            // If the object is a storage object
            if (object instanceof PortableDeviceStorageObject) 
            {
                PortableDeviceStorageObject storage = (PortableDeviceStorageObject) object;

                for (PortableDeviceObject o2 : storage.getChildObjects()) 
                {
                    if(o2.getOriginalFileName().equalsIgnoreCase("testFolder"))
                    {
                        targetFolder = (PortableDeviceFolderObject) o2;
                    }

                    System.out.println(o2.getOriginalFileName());
                }

                if (targetFolder != null)
                {
                    copyFileFromComputerToDeviceFolder(targetFolder);
                    PortableDeviceObject[] folderFiles = targetFolder.getChildObjects();
                    for (PortableDeviceObject pDO : folderFiles) {
                        copyFileFromDeviceToComputerFolder(pDO, device);
                    }
                }

            }
        }

        manager.getDevices()[1].close();
    }

    private static void copyFileFromDeviceToComputerFolder(PortableDeviceObject pDO, PortableDevice device)
    {
        PortableDeviceToHostImpl32 copy = new PortableDeviceToHostImpl32();
        try {
            copy.copyFromPortableDeviceToHost(pDO.getID(), "C:\\TransferTest", device);
        } catch (COMException ex) {
            ex.printStackTrace();
        }

    }

    private static void copyFileFromComputerToDeviceFolder(PortableDeviceFolderObject targetFolder) 
    {
        BigInteger bigInteger1 = new BigInteger("123456789");
        File file = new File("C:\\GettingJMTP.pdf");
        try {
            targetFolder.addAudioObject(file, "jj", "jj", bigInteger1);
        } catch (Exception e) {
            System.out.println("Exception e = " + e);
        }
    }
}
