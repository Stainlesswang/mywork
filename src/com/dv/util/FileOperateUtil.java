package com.dv.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.dv.constants.SystemConst;
import com.dv.entity.SysFile;


public class FileOperateUtil {
	
	/**
	 *  
	 * @methodDesc:  
	 * 创建目录,需要判断文件夹是否存在
	 * @param dirPath 文件路径
	 * @return
	 * @return  
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年3月20日 下午3:15:22
	 */
	 public static Boolean createDirWithJudge(String dirPath) throws FnfhException{  
		  File dirFile = new File(dirPath);  
		  if(dirFile.exists())
		  {
			  throw new FnfhException(SystemConst.FILE_EXIST_ERROR,SystemConst.FILE_EXIST_ERROR_MSG);
		  }
		  return dirFile.mkdirs();  
	 } 
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 创建文件夹，不需要判断是否存在
	  * @param dirPath
	  * @return
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月22日 上午9:07:34
	  */
	 public static Boolean createDir(String dirPath){  
		  File dirFile = new File(dirPath);  
		  return dirFile.mkdirs();  
	 }
	 
	 /**
	  *  
	  * @methodDesc:  
	  * 创建文件
	  * @param filePath
	  * @param content
	  * @return
	  * @return  
	 * @throws FnfhException 
	  * @creater: 李梦婷
	  * @creationDate:2017年3月20日 下午7:10:59
	  */
	 public static Boolean createFile(String filePath,String content){
		 Writer writer = null;  
		  File file = new File(filePath);  
		  File temp = new File(file.getParent());  
		  if(!temp.exists()){  
			  createDir(file.getParent());  
		  }  
		  try{  
			   writer = new OutputStreamWriter(new FileOutputStream(file));  
			   writer.write( content );  
		  }catch(Exception e){  
			  e.printStackTrace();  
			  return false;  
		  }finally{  
			   try{  
				    if(writer!=null){  
				    	writer.close();  
				    }  
			   }catch(Exception e1){  
			    e1.printStackTrace();  
			   }  
		  }  
		  return true;
	 }  
	 
	/**
	 *   
	 * @methodDesc:  
	 * 递归查找函数，参数为file对象
	 * @param f
	 * @return  
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年3月20日 下午7:11:48
	 */
	 public static List<SysFile>  getAllFileList(File file) throws FnfhException {
		 try {
				List<SysFile> list=new ArrayList<SysFile>();
				 if(file.isFile())
				 {
					 list.add(getFileInfo(file));
				 }
				 else
				 {
					// File 数组 
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						// 递归出子目录  
						if (files[i].isDirectory()) {  
							System.out.println("子目录是：" + files[i].getName());  
							getAllFileList(files[i]);  
							// 递归出子文件   
						} else {  
							list.add(getFileInfo(files[i]));  
						} 
						
					} 
				 }
				 return list;
			} catch (NullPointerException e) {
				e.printStackTrace();
				throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG,e);
			} catch (Exception e) {
				throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e);
			}
		
	 } 
	
	 /**
	  * 
	  * @methodDesc:  
	  * 获取某一文件夹下
	  * @param file
	  * @param searchFile
	  * @return  
	 * @throws FnfhException 
	  * @creater: 李梦婷
	  * @creationDate:2017年3月21日 上午10:39:14
	  */
	 public static List<SysFile> getFileList(File file) throws FnfhException {
		 try {
			List<SysFile> list=new ArrayList<SysFile>();
			 if(file.isFile())
			 {
				 list.add(getFileInfo(file));
			 }
			 else
			 {
				// File 数组 
				File[] files = file.listFiles();
				if(files!=null&&files.length>0)
				{
					for (int i = 0; i < files.length; i++) {
						list.add(getFileInfo(files[i]));
					} 
				}
				
			 }
			 return list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG,e);
		} catch (Exception e) {
			throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e);
		}
	 } 
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 获取文件夹下的一个文件，主要用于会议附件的下载
	  * @param file
	  * @return
	  * @throws FnfhException 
	  * @creater: 李梦婷
	  * @creationDate:2017年6月6日 上午9:32:01
	  */
	 public static SysFile getFileByOne(File file) throws FnfhException {
		 try {
			 if(file.isFile())
			 {
				 return getFileInfo(file);
			 }
			 else
			 {
				// File 数组 
				File[] files = file.listFiles();
				if(files!=null&&files.length>0)
				{
					for (int i = 0; i < files.length; i++) {
						return getFileInfo(files[i]);
					} 
				}
				
			 }
			 return null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG,e);
		} catch (Exception e) {
			throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e);
		}
	 } 
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 获取文件夹集合
	  * @param file
	  * @return
	  * @throws FnfhException
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月23日 下午4:27:12
	  */
	 public static List<SysFile> getFolderList(File file) throws FnfhException {
		 try {
			List<SysFile> list=new ArrayList<SysFile>();
			 if(file.isFile())
			 {
				 return list;
			 }
			 else
			 {
				// File 数组 
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if(file.isDirectory())
					{
						list.add(getFileInfo(files[i]));
					}
				} 
			 }
			 return list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG,e);
		} catch (Exception e) {
			throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e);
		}
	 }
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 获取文件信息
	  * @param file
	  * @return
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月21日 上午11:19:49
	  */
	 private static SysFile getFileInfo(File file)
	 {
		 SysFile sysFile=new SysFile();
		 String fileName=file.getName();
		 sysFile.setFile_realname(fileName);//文件名
		 sysFile.setParentFileName(file.getParent());
		 sysFile.setFile_path(file.getPath());
		 Long lastModified = file.lastModified();
	     Date date = new Date(lastModified);
		 sysFile.setSavetime(CommonMethod.getDateFormat(date));
		 if(file.isFile())
		 {
			 String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
//			 sysFile.setFile_name(fileName);//文件名
			 sysFile.setFile_type(prefix);//文件后缀名
			 sysFile.setFileSize(file.length());//文件大小
		 }
		 System.out.println("文件名"+fileName);
		 return sysFile;
	 }
	 
	 
	  
	 /**
	  *   
	  * @methodDesc:  
	  * 重命名
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月20日 下午7:13:34
	  */
	 public void rename(){  
		 File fl=new File("E://文件夹");  // 这里写上发替换的文件夹路径,注意使用双斜杠  
		 String[] files=fl.list();  
		 File f=null;  
		 String filename="";  
		 for(String file:files)  
		 {  
			 f=new File(fl,file);// 注意,这里一定要写成File(fl,file)如果写成File(file)是行不通的,一定要全路径  
			 filename=f.getName();  
			 //System.out.println(filename);  
			 f.renameTo(new File(fl.getAbsolutePath()+"//"+filename.replace("要替换掉的内容", "替换成的内容")));// 这里可以反复使用replace替换,当然也可以使用正则表达式来替换了  
	    }  
	 }  
	   
	 /**
	  * 
	  * @methodDesc:  
	  * 删除文件夹  
	  * param folderPath 文件夹完整绝对路径  
	  * @param folderPath
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月20日 下午7:14:05
	  */
	 public static void delFolder(String folderPath) {
		 try {  
				   delAllFile(folderPath); //删除完里面所有内容  
				   String filePath = folderPath;  
				   filePath = filePath.toString();  
				   java.io.File myFilePath = new java.io.File(filePath);  
				   myFilePath.delete(); //删除空文件夹  
			  } catch (Exception e) {  
				  e.printStackTrace();  
			  } 
	 }  
	  
	 /**
	  * 
	  * @methodDesc:  
	  * 删除指定文件夹下所有文件  
	  * param path 文件夹完整绝对路径  
	  * @param path
	  * @return
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月20日 下午7:15:06
	  */
	 public static boolean delAllFile(String path) {  
		  boolean flag = false;  
		  File file = new File(path);  
		  if (!file.exists()) {  
			  return flag;  
		  }  
		  if (!file.isDirectory()) {  
			  return flag;  
		  }  
		  String[] tempList = file.list();  
		  File temp = null;  
		  for (int i = 0; i < tempList.length; i++) {  
			   if (path.endsWith(File.separator)) {  
			    temp = new File(path + tempList[i]);  
			   } else {  
			    temp = new File(path + File.separator + tempList[i]);  
			   }  
			   if (temp.isFile()) {  
			    temp.delete();  
			   }  
			   if (temp.isDirectory()) {  
			    delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
			    delFolder(path + "/" + tempList[i]);//再删除空文件夹  
			    flag = true;  
			   }  
		  }  
		  return flag;  
	 } 
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 删除文件
	  * @param path
	  * @return
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月22日 上午10:28:38
	  */
	 public static boolean delFile(String path) {
		 boolean flag = false;  
		 File file = new File(path);  
		 if (!file.exists()) {  
			 return flag;  
		 }  
		 if (file.isFile()) {
			 file.delete();
			  return true;  
		 }
		 return flag;
	 }
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 文件移动
	  * @param srcFile
	  * @param destPath
	  * @param fileName
	  * @return
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月22日 下午2:06:34
	  */
	 public static boolean moveFile(String srcFile, String destPath,String fileName)
	 {
		 // File (or directory) to be moved
	     File file = new File(srcFile);
	     // Destination directory
	     File dir = new File(destPath);
	     if(!dir.exists())
	     {
	    	 dir.mkdirs();
	     }
	     // Move file to new directory
	     boolean success = file.renameTo(new File(dir, fileName));
	     return success;
	 }
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 移动文件
	  * @param srcFile
	  * @param destPath
	  * @return
	  * @return  
	 * @throws FnfhException 
	  * @creater: 李梦婷
	  * @creationDate:2017年3月22日 下午2:13:06
	  */
	 public static boolean move(String srcFile, String destPath) throws FnfhException
	 {
		 // File (or directory) to be moved
	     File file = new File(srcFile);
	     if(!file.exists())
	     {
	    	 throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG);
	     }
	     // Destination directory
	     File dir = new File(destPath);
	     if(!dir.exists())
	     {
	    	 throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, "目标文件不存在");
	     }
	     // Move file to new directory
	     boolean success = file.renameTo(new File(dir, file.getName()));
	     return success;
	 }
	 
	 /**
	  * 
	  * @methodDesc:  
	  * 文件重命名
	  * @param path 文件目录 
	  * @param oldname  原来的文件名
	  * @param newname 新文件名
	  * @return
	  * @return  
	 * @throws FnfhException 
	  * @creater: 李梦婷
	  * @creationDate:2017年3月22日 下午2:18:02
	  */
	  public static boolean renameFile(String path,String oldname,String newname) throws FnfhException{ 
	      if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
	          File oldfile=new File(path+"/"+oldname); 
	          File newfile=new File(path+"/"+newname); 
	          if(!oldfile.exists()){
	              return false;//重命名文件不存在
	          }
	          if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
	          {
	              System.out.println(newname+"已经存在！");
	              throw new FnfhException(SystemConst.FILE_EXIST_ERROR, SystemConst.FILE_EXIST_ERROR_MSG); 
//	              return false;
	          }
	          else{ 
	        	  return oldfile.renameTo(newfile);
	          } 
	      }else{
	          System.out.println("新文件名和旧文件名相同...");
//	          return false;
	    	  throw new FnfhException(SystemConst.FILENAME_SAME_ERROR, SystemConst.FILENAME_SAME_ERROR_MSG);
	      }
	  }
	  
	  /**
	   * 
	   * @methodDesc:  
	   * 文件、文件夹复制
	   * @param src 源文件
	   * @param des 目标路径
	   * @return  
	 * @throws FnfhException 
	   * @creater: 李梦婷
	   * @creationDate:2017年3月28日 上午9:00:37
	   */
	  public static void copy(String src, String des) throws FnfhException {  
	        File file1=new File(src);  
	        File[] fs=file1.listFiles();  
	        File file2=new File(des);  
	        if(!file2.exists()){  
	            file2.mkdirs();  
	        }  
	        for (File f : fs) {  
	            if(f.isFile()){  
	                fileCopy(f.getPath(),des+File.separator+f.getName()); //调用文件拷贝的方法  
	            }else if(f.isDirectory()){  
	                copy(f.getPath(),des+File.separator+f.getName());  
	            }  
	        }  
	          
	    }  
	  
	    /**
	     *  
	     * @methodDesc:  
	     * 文件拷贝的方法 
	     * @param src
	     * @param des
	     * @return  
	     * @throws FnfhException 
	     * @creater: 李梦婷
	     * @creationDate:2017年3月28日 上午9:01:30
	     */
	    public static void fileCopy(String src, String des) throws FnfhException {  
	        BufferedReader br=null;  
	        PrintStream ps=null;  
	        try {  
	            br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
	            ps=new PrintStream(new FileOutputStream(des));  
	            String s=null;  
	            while((s=br.readLine())!=null){  
	                ps.println(s);  
	                ps.flush();  
	            }  
	        } catch (FileNotFoundException e) {  
	        	throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG+",src="+src+",des="+des,e.getMessage()); 
	        } catch (IOException e) {  
	        	throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e.getMessage());
	        }finally{  
	                try {  
	                    if(br!=null)  br.close();  
	                    if(ps!=null)  ps.close();  
	                } catch (IOException e) {  
	                	throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e.getMessage());
	                }  
	        }  
	    }  
	  
	  
	    /**
	     * 
	     * @methodDesc:  
	     * 根据参数获取所有文件集合
	     * @param file
	     * @param searchFile
	     * @return
	     * @throws FnfhException
	     * @return  
	     * @creater: 李梦婷
	     * @creationDate:2017年3月28日 下午2:18:00
	     */
	    public static List<SysFile>  getAllFileListByParam(File file,SysFile searchFile) throws FnfhException {
			 try {
					List<SysFile> list=new ArrayList<SysFile>();
					SysFile temp=new SysFile();
					 if(file.isFile())
					 {
						 temp=getFileInfoByParam(file,searchFile);
						 if(temp!=null)
						 {
							 list.add(temp);
						 }
					 }
					 else
					 {
						// File 数组 
						File[] files = file.listFiles();
						for (int i = 0; i < files.length; i++) {
							// 递归出子目录  
							if (files[i].isDirectory()) {  
								System.out.println("子目录是：" + files[i].getName());  
								getAllFileListByParam(files[i],searchFile);  
								// 递归出子文件   
							} else {  
//								list.add(getFileInfo(files[i]));  
								temp=getFileInfoByParam(files[i],searchFile);
								if(temp!=null)
								{
									list.add(temp);
								}
							} 
							
						} 
					 }
					 return list;
				} catch (NullPointerException e) {
					e.printStackTrace();
					throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG,e);
				} catch (Exception e) {
					throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e);
				}
			
		 }
	    
	    /**
		  * 
		  * @methodDesc:  
		  * 获取某一文件夹下
		  * @param file
		  * @param searchFile
		  * @return  
		 * @throws FnfhException 
		  * @creater: 李梦婷
		  * @creationDate:2017年3月21日 上午10:39:14
		  */
		 public static List<SysFile> getFileListByParam(File file,SysFile searchFile) throws FnfhException {
			 try {
				List<SysFile> list=new ArrayList<SysFile>();
				SysFile temp=new SysFile();
				 if(file.isFile())
				 {
					 temp=getFileInfoByParam(file,searchFile);
					 if(temp!=null)
					 {
						 list.add(temp);
					 }
					 
				 }
				 else
				 {
					// File 数组 
					File[] files = file.listFiles();
					if(files!=null&&files.length>0)
					{
						for (int i = 0; i < files.length; i++) {
							temp=getFileInfoByParam(files[i],searchFile);
							if(temp!=null)
							{
								list.add(temp);
							}
						} 
					}
					
				 }
				 return list;
			} catch (NullPointerException e) {
				e.printStackTrace();
				throw new FnfhException(SystemConst.FILE_NOTEXIST_ERROR, SystemConst.FILE_NOTEXIST_ERROR_MSG,e);
			} catch (Exception e) {
				throw new FnfhException(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG,e);
			}
		 } 
	    
	    
		 /**
		  * 
		  * @methodDesc:  
		  * 根据参数筛选文件信息
		  * @param file
		  * @param searchFile
		  * @return
		  * @return  
		  * @creater: 李梦婷
		  * @creationDate:2017年3月28日 下午1:41:42
		  */
		 private static SysFile getFileInfoByParam(File file,SysFile searchFile)
		 {
			 SysFile sysFile=new SysFile();
			 
			 String fileName=file.getName();
			 if(!StringUtils.isEmpty(searchFile.getFile_name()))
			 {
				 if(!fileName.contains(searchFile.getFile_name()))
				 {
					 return null;
				 }
			 }
			 sysFile.setFile_realname(fileName);//文件名
			 sysFile.setParentFileName(file.getParent());
			 sysFile.setFile_path(file.getPath());
			 Long lastModified = file.lastModified();
		     Date date = new Date(lastModified);
			 sysFile.setSavetime(CommonMethod.getDateFormat(date));
			 if(!StringUtils.isEmpty(searchFile.getDateStart()))
			 {
				 if(isDateBefore(sysFile.getSavetime(),searchFile.getDateStart()))
				 {
					 return null;
				 }
			 }
			 if(!StringUtils.isEmpty(searchFile.getDateEnd()))
			 {
				 if(!isDateBefore(sysFile.getSavetime(),searchFile.getDateEnd()))
				 {
					 return null;
				 }
			 }
			 
			 if(file.isFile())
			 {
				 String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
//				 sysFile.setFile_name(fileName);//文件名
				 sysFile.setFile_type(prefix);//文件后缀名
				 sysFile.setFileSize(file.length());//文件大小
			 }
			 System.out.println("文件名"+fileName);
			 return sysFile;
		 }
	    
	    
	    
	    
	    
		 public static boolean isDateBefore(String date1,String date2){
			 try{
				 if(date2.length()<19)
				 {
					 date2+=" 00:00:00";
				 }
				 DateFormat df = DateFormat.getDateTimeInstance();
				 return df.parse(date1).before(df.parse(date2));
			  }catch(ParseException e){
				 System.out.print("[SYS] " + e.getMessage());
				 return false;
			  }
		 }
	    
	    
	  
	  
	  
	  
	 /**
	  * 
	  * @methodDesc:  
	  * 获取文件的绝对路径
	  * @param path
	  * @param req
	  * @return
	  * @return  
	  * @creater: 李梦婷
	  * @creationDate:2017年3月22日 下午1:47:40
	  */
	 public static String getAbsolutePath(String path,HttpServletRequest req)
	 {
		 String realPath=req.getSession().getServletContext().getRealPath(path);
		 return realPath;
	 }
	public static void main(String[] args) {
//		 File f=new File("E:\\电脑权限管理2017\\src");
//		 Boolean s;
//		 s = createDir("E:\\电脑权限管理2017\\src1");
//		 System.out.println(s);
		
//		File fl=new File("E:\\电脑权限管理2017\\src");  // 这里写上发替换的文件夹路径,注意使用双斜杠  
//		String[] files=fl.list();  
//		File f=null;  
//		String filename="";  
//		for(String file:files)  
//		{  
//			f=new File(fl,file);// 注意,这里一定要写成File(fl,file)如果写成File(file)是行不通的,一定要全路径  
//			filename=f.getName();  
//			System.out.println(filename+","+fl.getAbsolutePath());  
//			
//			f.renameTo(new File(fl.getAbsolutePath()+"//src1//"+filename.replace("新建文本文档", "11111")));// 这里可以反复使用replace替换,当然也可以使用正则表达式来替换了  
//	    }  
		
//		move("E:\\电脑权限管理2017\\src","E:\\电脑权限管理2017\\src1");
//		boolean s=moveFile("D:\\apache-tomcat-7.0.72-parking\\webapps\\edms\\admin_123456\\66\\findpwd.js",
//				"D:\\apache-tomcat-7.0.72-parking\\webapps\\edms\\edmsDocument\\copy_cc05d775e36b4ba0815410fe84084ac0\\admin_123456",
//				"82481_findpwd.js");
//		System.out.println(s);
//		 System.out.println(f.getPath()+",,,"+f.getAbsolutePath()+";;;"+f.getParent());
		
		
//		File file=new File("D:\\apache-tomcat-7.0.72-parking\\webapps\\edms\\edmsDocument\\admin_123456\\ceshi.doc");
//		if(!file.exists())
//		{
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
}
