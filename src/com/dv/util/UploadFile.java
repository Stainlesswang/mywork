package com.dv.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.dv.entity.FileAttach;






/**
 * 
 * @classDesc ：
 *	用于保存上传图片信息的方法
 * @creater: 陈伏宝
 * @creationDate:2016-5-26 下午2:38:44
 */
public class UploadFile {

	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	
//	private static  String ACCESS_KEY = SystemConfig.getAccesskey();
//    private static  String SECRET_KEY = SystemConfig.getSecretkey();
//    private static  String BUCKET_NAME = SystemConfig.getBucketname();
//    private static  String QINIUFILE_URL=SystemConfig.getQiniufileurl();//七牛文件访问路径
	
	public void importPic(HttpServletRequest request,String imageUrl,String imageName){
		

		try
		{
			String sourcePath = request.getRealPath("/")+imageUrl+"/";
			saveImage(sourcePath,imageName);
		}
		catch(Exception e)
		{
			logger.error("保存图片路径出现错误");
		}
		
		
	}
	
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 保存图片信息
	 * @param sourcePath
	 * @param saveurl
	 * @creater: 陈伏宝
	 * @creationDate:2016-5-26 下午4:42:41
	 */
	public void saveImage(String sourcePath,String saveurl){         //将目标图片缩小成256*256并保存
        File file1=new File(sourcePath);             //用file1取得图片名字
        String name=file1.getName();

        try{
        BufferedImage input = ImageIO.read(file1);
      //  Image big = input.getScaledInstance(256, 256,Image.SCALE_DEFAULT);
        BufferedImage inputbig = new BufferedImage(256, 256,BufferedImage.TYPE_INT_BGR);
        inputbig.getGraphics().drawImage(input, 0, 0, 256, 256, null); //画图


            File file2 =new File(saveurl);            //此目录保存缩小后的关键图
            if(file2.exists()){
                 System.out.println("多级目录已经存在不需要创建！！");
            }else{
              //如果要创建的多级目录不存在才需要创建。
               file2.mkdirs();
             }
            ImageIO.write(inputbig, "jpg", new File(saveurl+name));   //将其保存在C:/imageSort/targetPIC/下
        } catch (Exception ex) {ex.printStackTrace();}
    }

	
	/**
	 * 
	 * @methodDesc:  
	 * 表单文件上传(单个文件)
	 * @param file 文件
	 * @param relativePath 相对路径 如upload
	 * @return
	 * @creater: 李梦婷
	 * @creationDate:2016年6月13日 上午9:08:56
	 */
	public String uploadFileByForm(MultipartFile file, HttpServletRequest request,String relativePath,String fileName)
	{
		String path = request.getSession().getServletContext().getRealPath(relativePath); 
		String tempFileName=file.getOriginalFilename();
		fileName += tempFileName.substring(tempFileName.lastIndexOf(".") + 1);  
		System.out.println("fileName="+fileName);
		File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile); 
//            return request.getContextPath()+File.separator+relativePath+ File.separator+fileName;
            return relativePath+ "/"+fileName;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return null;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 文件上传
	 * @param file
	 * @param request
	 * @param relativePath
	 * @param fileName
	 * @return
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年1月13日 下午1:19:14
	 */
	public FileAttach uploadFileByAjax(MultipartFile file, HttpServletRequest request,String relativePath,String fileName)
	{
		FileAttach fileAttach=new FileAttach();
		
		String path = request.getSession().getServletContext().getRealPath(relativePath); 
		String tempFileName=file.getOriginalFilename();
		
		fileName += tempFileName.substring(tempFileName.lastIndexOf(".") + 1);  
		System.out.println("fileName="+fileName);
		File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile); 
            fileAttach.setAttach_name(tempFileName);//文件原始名
            fileAttach.setAttach_realname(fileName);//文件存储名
//            fileAttach.setSuffix(tempFileName.substring(tempFileName.lastIndexOf(".") + 1));
            fileAttach.setAttach_path(relativePath+fileName);
            return fileAttach;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return null;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 上传文件，原文件不重新命名
	 * @param file
	 * @param request
	 * @param relativePath
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月10日 下午4:08:47
	 */
	public FileAttach uploadFileByAjax(MultipartFile file, HttpServletRequest request,String relativePath)
	{
		FileAttach fileAttach=new FileAttach();
		String token=System.currentTimeMillis()+"";
		relativePath+=token+"/";
		String path = request.getSession().getServletContext().getRealPath(relativePath);
		String tempFileName=file.getOriginalFilename();
		long fileSize=file.getSize();
		System.out.println("uploadFileByAjax：OriginalFilename="+tempFileName+", fileName="+file.getName()+",path="+path);
		File targetFile = new File(path, tempFileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile); 
            fileAttach.setAttach_name(tempFileName);//文件原始名
            fileAttach.setAttach_realname(tempFileName);//文件存储名
            fileAttach.setPrefix(tempFileName.substring(0,tempFileName.lastIndexOf(".")));//前缀
            fileAttach.setSuffix(tempFileName.substring(tempFileName.lastIndexOf(".") + 1));//后缀
            fileAttach.setAttach_path(relativePath+tempFileName);
            fileAttach.setToken(token);
            fileAttach.setAttach_size(fileSize+"");
            fileAttach.setLast_update_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return fileAttach;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return null;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 上传文件至七牛
	 * @param file
	 * @param request
	 * @param relativePath
	 * @param fileName
	 * @return
	 * @creater: 李梦婷
	 * @creationDate:2016年7月3日 下午1:28:35
	 */
	public  String uploadToQiniu(MultipartFile file, HttpServletRequest request,String relativePath,String fileName)
	{
		//以下为七牛
		String uptoken;
//        try {
//        	String tempFileName=file.getOriginalFilename();
//    		fileName += tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
//            Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
//            PutPolicy putPolicy = new PutPolicy(BUCKET_NAME);
//            uptoken = putPolicy.token(mac);
//            PutRet putRet=IoApi.Put(uptoken, relativePath+"/"+fileName, file.getInputStream(), null);
//            System.out.println(putRet.getKey());
//            return QINIUFILE_URL+putRet.getKey();
//        } catch (AuthException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}
	
	
	
}
