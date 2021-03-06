package com.dv.controller.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.converter.docConverter.DocConverter;
import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.entity.FileAttach;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.SysFile;
import com.dv.util.CommonMethod;
import com.dv.util.FileOperateUtil;
import com.dv.util.FnfhException;
import com.dv.util.PropertiesUtil;
import com.dv.util.UploadFile;
import com.dv.util.Validator;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @classDesc ：
 *	文档操作
 * @creater: 李梦婷
 * @creationDate:2017年5月4日 下午3:31:22
 */
@Controller
@RequestMapping("/fileUtil")
public class FileUtilController {
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 下载模板
	 * @param req
	 * @param response
	 * @throws ServletException
	 * @throws IOException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月4日 下午3:34:42
	 */
	@RequestMapping(value="/downloadUserTemplate.action",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void downloadUserTemplate(HttpServletRequest req,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("下载附件路径filePath:"+req.getParameter("filePath"));
		FnfhPageData pgdata=new FnfhPageData(req);	
		String filePath=pgdata.getString("filePath");
		String fileName=pgdata.getString("fileName");
		if(StringUtils.isEmpty(filePath))//文件路径为空
		{
			req.setAttribute("msg", "您所请求的资源不存在，请重试");
			req.getRequestDispatcher(SystemConst.ERROR_PAGE_404_TIPS).forward(req,response);
		}
		else
		{
			//下载
			// path是指欲下载的文件的路径。
			try {
//				filePath=new String(filePath.getBytes("ISO-8859-1"), "UTF8");
				filePath=req.getSession().getServletContext().getRealPath(filePath);
//				String realPath=req.getSession().getServletContext().getRealPath(filePath);
				File file = new File(filePath);//文件的完整路径
				// 取得文件名。
				String filename = file.getName();
				if(!StringUtils.isEmpty(fileName))
				{
					filename=fileName;
				}
				// 取得文件的后缀名。
				String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

				// 以流的形式下载文件。
				InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();
				// 设置response的Header
//				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf8"), "ISO8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			} catch (FileNotFoundException e) {
				req.setAttribute("msg", "您所请求的资源不存在，请重试");
				req.getRequestDispatcher(SystemConst.ERROR_PAGE_404_TIPS).forward(req,response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 上传附件信息
	 * @param req
	 * @param resp
	 * @param uploadFiles
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月5日 下午3:13:15
	 */
	@RequestMapping(value="/uploadAttactFile.action",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadAttactFile(HttpServletRequest req,
			HttpServletResponse resp,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFiles){
		Result result=new Result();
		String message="文件上传成功！";
		int status=0;
//		System.out.println(uploadFiles.getSize());
		try {
			//上传图片 start
			UploadFile uploadFile=new UploadFile();
			if(uploadFiles!=null&&uploadFiles.getSize()>0)
			{
			    if (uploadFiles.getSize() > SystemConst.MAX_UPLOAD_FILE_SIZE * 1000 * 1000)
			    {
			        result = new Result(APIConstants.UPLOAD_FILE_CANNOT_GTR_50M);
			        return String.valueOf(JSONObject.fromObject(result));
			    }
				FileAttach fileAttach=uploadFile.uploadFileByAjax(uploadFiles, req, SystemConst.SYS_FILE_MEET_FILE_PATH);
				result.setData(fileAttach);
			}
			else
			{
				status=1;
				if(uploadFiles==null)
				{
					message="请选择要上传的文件";
				}
				else
				{
					message="上传的文件内容不能为空";
				}
			}
			//上传图片end
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			logger.error(logger.getName()+"/uploadAttactFile,"+ex.getMessage()+","+ex.toString());
			message="文件上传失败";
			status=-1;
		}
		result.setStatus(status);
		result.setMessage(message);
		String jsonStr="[]";
		jsonStr = String.valueOf(JSONObject.fromObject(result));
//		System.out.println(jsonStr);
//		return result;
		logger.info(jsonStr);
		return jsonStr;
	}
	
	
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 合并附件
	 * @param req
	 * @param resp
	 * @return 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月11日 上午8:42:04
	 */
	@RequestMapping("/background_mergeFile.action")
	@ResponseBody
	public Result mergeFile(HttpServletRequest req,HttpServletResponse resp){
		String delPath="";
		try {
			FnfhPageData pgdata=new FnfhPageData(req);
			String fileName=pgdata.getString("fileName");
			if(StringUtils.isEmpty(fileName))
			{
				return Result.error(SystemConst.NOT_NULL, "合并后的文件名不能为空");
			}
			if(!Validator.isValidFileName(fileName))
			{
				return Result.error(SystemConst.NOT_NULL, "请输入有效的文件名称(2-255位不带有非法字符)");
			}
			//合并附件
			String fileList=pgdata.getString("fileList");
			if(StringUtils.isEmpty(fileList)||"[]".equals(fileList))
			{
				return Result.error(SystemConst.NOT_NULL, "请选择要合并的文件");
			}
			JSONArray jsonArray2=JSONArray.fromObject(fileList);
			String token=System.currentTimeMillis()+"";

			String basePath = req.getSession().getServletContext().getRealPath("/");
			String mergeFilePath=SystemConst.SYS_FILE_MERGE_FILE_PATH+token + File.separator + fileName+".pdf";
			String mergeFileAllPath=basePath+mergeFilePath;
			
			Object[] _sourceFiles=(Object[])jsonArray2.toArray();
//			List<String> filePathList=JSONArray.toList(jsonArray2);
			String[] sourceFiles=new String[_sourceFiles.length];
			
			for (int i=0;i<_sourceFiles.length;i++) {
				Object path = _sourceFiles[i];
				sourceFiles[i]=CommonMethod.replaceFileSeparator(basePath+path);
			}
			File mergeFile=new File(basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token);
			delPath=basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token;//异常时删除的文件夹
	        if(!mergeFile.exists()){  
	        	mergeFile.mkdirs();  
	        }
			DocConverter converter = new DocConverter();
//			long c1 = System.currentTimeMillis();
			converter.mergePDF(sourceFiles, CommonMethod.replaceFileSeparator(mergeFileAllPath), null);
//			long c2 = System.currentTimeMillis();
			//System.out.println("转换用了times:"+(c2-c1) + "毫秒");
			//获取转换后的文件信息
			mergeFile=new File(mergeFileAllPath);
			if(mergeFile.exists())
			{
				FileAttach fileAttach=new FileAttach();
				fileAttach.setAttach_name(fileName+".pdf");
				fileAttach.setAttach_realname(fileName+".pdf");
				fileAttach.setToken(token);
				fileAttach.setAttach_path(mergeFilePath.replace("\\", "/"));
				fileAttach.setSuffix("pdf");
				fileAttach.setPrefix(fileName);
				fileAttach.setAttach_size(mergeFile.length()+"");
				fileAttach.setLast_update_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				return new Result(fileAttach);
			}
			else
			{
				//删除新建的文件夹
				mergeFile=new File(basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token);
				if(mergeFile.exists())
				{
					mergeFile.delete();
				}
			}
			return Result.error(SystemConst.SYS_ERROR, "文件转换失败");
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error(logger.getName()+"/mergeFile,"+e.getMessage());
			//删除新建的文件夹
			File mergeFile=new File(delPath);
			if(mergeFile.exists())
			{
				mergeFile.delete();
			}
			return Result.error(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG);
		}
	}
	
	
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 合并附件,设置超时时间
	 * @param req
	 * @param resp
	 * @return 
	 * @creater: 李梦婷
	 * @deprecated 暂未启用
	 * @creationDate:2017年5月11日 上午8:42:04
	 */
	public Result mergeFileByLimitTime(HttpServletRequest req,HttpServletResponse resp){
		String delPath="";
		try {
			FnfhPageData pgdata=new FnfhPageData(req);
			String fileName=pgdata.getString("fileName");
			if(StringUtils.isEmpty(fileName))
			{
				return Result.error(SystemConst.NOT_NULL, "合并后的文件名不能为空");
			}
			if(!Validator.isValidFileName(fileName))
			{
				return Result.error(SystemConst.NOT_NULL, "请输入有效的文件名称(1-255位不带有非法字符)");
			}
			//合并附件
			String fileList=pgdata.getString("fileList");
			if(StringUtils.isEmpty(fileList)||"[]".equals(fileList))
			{
				return Result.error(SystemConst.NOT_NULL, "请选择要合并的文件");
			}
			JSONArray jsonArray2=JSONArray.fromObject(fileList);
			String token=System.currentTimeMillis()+"";

			String basePath = req.getSession().getServletContext().getRealPath("/");
			String mergeFilePath=SystemConst.SYS_FILE_MERGE_FILE_PATH+token+"/"+fileName+".pdf";
			final String mergeFileAllPath=basePath+mergeFilePath;
			
			Object[] _sourceFiles=(Object[])jsonArray2.toArray();
//			List<String> filePathList=JSONArray.toList(jsonArray2);
			final String[] sourceFiles=new String[_sourceFiles.length];
			
			for (int i=0;i<_sourceFiles.length;i++) {
				Object path = _sourceFiles[i];
				sourceFiles[i]=basePath+path;
			}
			File mergeFile=new File(basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token);
			delPath=basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token;//异常时删除的文件夹
	        if(!mergeFile.exists()){  
	        	mergeFile.mkdirs();  
	        }
	        final DocConverter converter = new DocConverter();
	        //这段代码需要设置超时时间 start
	        final ExecutorService exec = Executors.newFixedThreadPool(1);  
			  
			Callable<String> call = new Callable<String>() {  
			    public String call() throws Exception {  
			        //开始执行耗时操作  
			    	
					long c1 = System.currentTimeMillis();
					converter.mergePDF(sourceFiles, mergeFileAllPath, null);
					long c2 = System.currentTimeMillis();
					System.out.println("转换用了times:"+(c2-c1)/1000);
					return "success";
			    }  
			};  
			  
			try {  
			    Future<String> future = exec.submit(call);  
			    String obj = future.get(1000 * 600, TimeUnit.MILLISECONDS); //任务处理超时时间设为 10分钟  
//			    System.out.println("任务成功返回:" + obj);  
			} catch (TimeoutException ex) {  
//			    System.out.println("处理超时啦....");  
//			    ex.printStackTrace(); 
				converter.shutdown();
				logger.error(logger.getName()+"/mergeFile,"+ex.getMessage());
				return Result.error(SystemConst.SYS_ERROR, "处理超时");
			} catch (Exception e) {  
//			    System.out.println("处理失败."); 
//			    e.printStackTrace();
			    logger.error(logger.getName()+"/mergeFile,"+e.getMessage());
			    return Result.error(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG);
			      
			}  
			// 关闭线程池  
			exec.shutdown();
	        
	        
	        
			
			//这段代码需要设置超时时间 end
			
			//获取转换后的文件信息
			mergeFile=new File(mergeFileAllPath);
			if(mergeFile.exists())
			{
				FileAttach fileAttach=new FileAttach();
				fileAttach.setAttach_name(fileName+".pdf");
				fileAttach.setAttach_realname(fileName+".pdf");
				fileAttach.setToken(token);
				fileAttach.setAttach_path(mergeFilePath);
				fileAttach.setSuffix(".pdf");
				fileAttach.setPrefix(fileName);
				fileAttach.setAttach_size(mergeFile.length()+"");
				return new Result(fileAttach);
			}
			else
			{
				//删除新建的文件夹
				mergeFile=new File(basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token);
				if(mergeFile.exists())
				{
					mergeFile.delete();
				}
			}
			return Result.error(SystemConst.SYS_ERROR, "文件转换失败");
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error(logger.getName()+"/mergeFile,"+e.getMessage());
			//删除新建的文件夹
			File mergeFile=new File(delPath);
			if(mergeFile.exists())
			{
				mergeFile.delete();
			}
			return Result.error(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG);
		}
	}
	
	
	/**
     * 
     * @methodDesc:  
     * 下载会议附件
     * @param req
     * @param response
     * @throws ServletException
     * @throws IOException 
     * @creater: 李梦婷
     * @creationDate:2017年6月6日 上午9:24:37
     */
    @RequestMapping(value="/downloadMeetFileByToken.action",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void downloadMeetFileByToken(HttpServletRequest req,HttpServletResponse response) throws ServletException, IOException
    {
        
        FnfhPageData pgdata=new FnfhPageData(req);  
        String token=pgdata.getString("token");
        String type=pgdata.getString("type");//0表示原始文件，1表示合并后的附件，因为存放的路径不一样，所以区分一下
        System.out.println("token:"+token+",type:"+type);
        String fileName=pgdata.getString("fileName");
         
        //根据token获取文件
        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(type)||(!type.equals("0")&&!type.equals("1")&&!type.equals("3")))//缺少参数
        {
            req.setAttribute("msg", "文件不存在");
            req.getRequestDispatcher(SystemConst.ERROR_PAGE_404_TIPS).forward(req,response);
            return;
        }
        else
        {
            //下载
            // path是指欲下载的文件的路径。
            try {
//              filePath=new String(filePath.getBytes("ISO-8859-1"), "UTF8");
                String basePath = req.getSession().getServletContext().getRealPath("/");
                String filePath=basePath+SystemConst.SYS_FILE_MEET_FILE_PATH+token;//原始会议附件路径
                if(type.equals("1"))//合并后的附件
                {
                    filePath=basePath+SystemConst.SYS_FILE_MERGE_FILE_PATH+token;
                }
                else if (type.equals("3"))//会送的文件
                {
                    filePath = basePath +  PropertiesUtil.getProperty("back_path") + token;
                }
                //获取文件信息
                SysFile sysFile=FileOperateUtil.getFileByOne(new File(filePath));
//              String realPath=req.getSession().getServletContext().getRealPath(filePath);
                if(sysFile==null)
                {
                    req.setAttribute("msg", "文件不存在");
                    req.getRequestDispatcher(SystemConst.ERROR_PAGE_404_TIPS).forward(req,response);
                    return;
                }
                filePath=sysFile.getFile_path();
                File file = new File(filePath);//文件的完整路径
                // 取得文件名。
                String filename = file.getName();
                if(!StringUtils.isEmpty(fileName))
                {
                    filename=fileName;
                }
                String userAgent = req.getHeader("User-Agent");
                // 针对IE或者以IE为内核的浏览器：  
                if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {  
                    filename = java.net.URLEncoder.encode(filename, "UTF-8");  
//                  fileName=new String(filename.getBytes(), "ISO-8859-1");
                } else {  
                    // 非IE浏览器的处理：  
                    filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");  
                }

                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
//              System.out.println(new String(filename.getBytes(), "ISO8859-1"));
                // 设置response的Header
//              response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//              response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "ISO8859-1"));
//              response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
                response.addHeader("Content-Disposition", "attachment;filename=" + filename);
                response.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("UTF-8");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (FileNotFoundException e) {
                req.setAttribute("msg", "文件不存在");
                req.getRequestDispatcher(SystemConst.ERROR_PAGE_404_TIPS).forward(req,response);
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            } catch (FnfhException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }catch(Exception e)
            {
                logger.error("附件下载失败");
            }
        }
        
    }
	
	/**
	 * 
	 * @methodDesc:  
	 * 原始上传附件的方法
	 * @param req
	 * @param resp
	 * @param uploadFiles
	 * @return 
	 * @creater: 李梦婷
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月11日 上午8:40:48
	 */
	@RequestMapping("/uploadAttactFile_cpy.action")
	@ResponseBody
	public Result uploadAttactFile_cpy(HttpServletRequest req,
			HttpServletResponse resp,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFiles){
		Result result=new Result();
		String message="文件上传成功！";
		int status=0;
		try {
			//上传图片 start
			UploadFile uploadFile=new UploadFile();
			String fileName=System.currentTimeMillis()+".";
			long fileSize=0;
			if(uploadFiles!=null&&uploadFiles.getSize()>0)
			{
				fileSize=uploadFiles.getSize();
				FileAttach fileAttach=uploadFile.uploadFileByAjax(uploadFiles, req, SystemConst.SYS_FILE_MEET_FILE_PATH, fileName);
				if(fileAttach!=null)
				{
					fileAttach.setAttach_size(fileSize+"");
					String prefix=fileName.replace(".", "");
					fileAttach.setPrefix(prefix);
					String suffix=fileAttach.getAttach_realname().replace(prefix, "");
					fileAttach.setSuffix(suffix);
				}
				result.setData(fileAttach);
			}
			//上传图片end
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			logger.error(logger.getName()+"/uploadAttactFile,"+ex.getMessage()+","+ex.toString());
			message="文件上传失败";
			status=-1;
		}
		result.setStatus(status);
		result.setMessage(message);
		return result;
	}
	
	
	
	@Test
	public void test()
	{
		File file=new File("F:\\2\\userTemplate.xls");
		try
		{
		  //实例化一个工作簿对象
		  Workbook workBook=Workbook.getWorkbook(file);
		  //获取该工作表中的第一个工作表
		  Sheet sheet=workBook.getSheet(0);
		  //获取该工作表的行数，以供下面循环使用
		  int rowSize=sheet.getRows();
		  for(int i=0;i<rowSize;i++)
		  {
			  //姓名
			  String real_name=sheet.getCell(0,i).getContents();
			  //登陆账号
			  String user_name=sheet.getCell(1,i).getContents();
			  //密码
			  String password=sheet.getCell(2,i).getContents();			  
			  //邮箱
			  String email=sheet.getCell(3,i).getContents();
			  //移动电话
			  String mobile=sheet.getCell(4,i).getContents();
			  
//			  Date reg_time = java.sql.Date.valueOf(sheet.getCell(5,i).getContents());
			  //固定电话
			  String tel=sheet.getCell(5,i).getContents();
			  //性别
			  String sex=sheet.getCell(6,i).getContents();
			  //职务
			  String position=sheet.getCell(7,i).getContents();
			  //住址
			  String addr=sheet.getCell(8,i).getContents();
			  //用户说明
			  String remark=sheet.getCell(9,i).getContents();
			  //是否为管理员
			  String admin_type=sheet.getCell(10,i).getContents();
			  System.out.println(real_name);
			  
		  }
//		  return mapping.findForward("import_success");
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
//			return mapping.findForward("import_faile");
		}

		
		
		
	}
	
	

}
