package com.dv.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.converter.docConverter.DocConverter;
import com.dv.constants.APIConstants;
import com.dv.entity.FileAttach;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.MeetingFileDownload;
import com.dv.entity.meeting.SubAppMeeting;
import com.dv.entity.meeting.SubAppMeetingFile;
import com.dv.entity.user.User;
import com.dv.service.meeting.SubAppMeetingFileService;
import com.dv.service.meeting.SubAppMeetingService;
import com.dv.service.meeting.SubAppUserService;
import com.dv.service.user.UserManageService;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
import com.dv.util.PropertiesUtil;
import com.dv.util.UploadFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 提供给移动端的接口
 * 
 * @author wch
 * @date 2017-5-22 15:18:36
 *
 */
@ Controller
@ RequestMapping(value = "/api")
public class APIController extends FnfhBaseController
{
    @ Resource
    private UserManageService userInfoService;

    @ Resource
    private SubAppMeetingService subAppMeetingService;

    @ Resource
    private SubAppMeetingFileService subAppMeetingFileService;

    @ Resource
    private SubAppUserService subAppUserService;
    Logger logger = Logger.getLogger(getClass());

    /**
     * 授权码登录
     * 
     * @param request
     * @param authCode
     *            授权码
     * @param ipadUUID
     *            ipad编码
     * @return
     * 
     */
    @ RequestMapping(value = "/regist")
    @ ResponseBody
    public Result authCodeLogin(HttpServletRequest request, String authCode, String ipadUUID)
    {
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(authCode))
        {
            return new Result(APIConstants.AUTH_CODE_NULL);
        }
        if (StringUtils.isEmpty(ipadUUID))
        {
            return new Result(APIConstants.IPAD_NO_NULL);
        }
        try
        {
            Result result = this.userInfoService.authCodeLogin(authCode, ipadUUID);
            // 成功
            if (result.getStatus() == 0)
            {
                User userInfo = (User)result.getData();
                session.setAttribute("loginUser", userInfo);

                //重新组装用户信息
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user_id", userInfo.getUser_id());
                map.put("user_name", userInfo.getUser_name());
                map.put("real_name", userInfo.getReal_name());
                //性别：0男1女2未知
                map.put("sex", userInfo.getSex());
                //职务
                map.put("position", userInfo.getPosition());
                //手机号
                map.put("mobile", userInfo.getMobile());
                //部门id
                map.put("org_id", userInfo.getOrg_id());
                //email
                map.put("email", userInfo.getEmail());
                //管理员类型，0普通人员，2普通管理员
                map.put("admin_type", userInfo.getAdmin_type());
                //电话
                map.put("tel", userInfo.getTel());
                //住址
                map.put("addr", userInfo.getAddr());
                //是否是临时用户 0不是1是
                map.put("is_temp", userInfo.getIs_temp());
                result.setData(map);
            }
            return result;
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/authCodeLogin, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
        catch(Exception e)
        {
            this.logger.error(this.logger.getName() + "/authCodeLogin, " + e.getMessage());
            return Result.errorUnknow();
        }
    }

    private String getFileSufix(String fileName)
    {
        int splitIndex = fileName.lastIndexOf(".");
        if (splitIndex < 0)
        {
            return null;
        }
        return fileName.substring(splitIndex + 1);
    }

    /**
     * 获取当前登录用户参会的会议列表
     * 
     * @param request
     * @return
     */
    @ RequestMapping(value = "/showMeetings")
    @ ResponseBody
    public Result showMeetings(HttpServletRequest request)
    {
        try
        {
            // 判断用户是否登录
            validateAdminAction(request.getSession());
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/showMeetings, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
        User loginUser = (User)request.getSession().getAttribute("loginUser");

        FnfhPageData pageData = new FnfhPageData(request);
        try
        {
            Result ret = this.subAppMeetingService.findMeetingsOfAttendee(pageData, loginUser);
            List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
            if (!ret.getRows().isEmpty())
            {
                String rootPath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath();
                for (SubAppMeeting a : (List<SubAppMeeting>)ret.getRows())
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    //会议标题
                    map.put("meet_name", a.getMeet_name());
                    //会议类型
                    map.put("meet_type", a.getMeet_type());
                    //会议类型名称
                    map.put("meet_type_name", a.getMeet_type_name());
                    // 会议id
                    map.put("mid", a.getMid());
                    // 会议起始时间
                    map.put("meet_time", a.getMeet_time());
                    // 会议结束时间
                    map.put("end_time", a.getEnd_time());
                    // 会议地点
                    map.put("meet_addr", a.getMeet_addr());
                    // 参会人员
                    map.put("meet_attend", a.getMeet_attend());
                    //最后更新时间
                    map.put("last_update_time", a.getLast_update_time());
                    // 备注
                    map.put("remark", a.getRemark());

                    List<SubAppMeetingFile> meetingFileList = a.getFileList();
                    int fileCount = meetingFileList.size();
                    if (fileCount > 1)
                    {
                        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
                        //最后一合并的会议文件
                        Map<String, Object> lastMergeFileMap = null;
                        for (SubAppMeetingFile file : meetingFileList)
                        {
                            //只需要合并的文件
                            if (file.getAttach_type() != 1)
                            {
                                continue;
                            }
                            Map<String, Object> fileMap = new HashMap<String, Object>();
                            // 文件名称
                            fileMap.put("attach_name", file.getAttach_name());
                            // 路径
                            fileMap.put("attach_path", rootPath + "/" + file.getAttach_path());
//                            fileMap.put("attach_path", rootPath + "/api/waterMarkFile/download/" + file.getToken());
                            // 文件类型：0普通附件；1合并附件
                            fileMap.put("attach_type", file.getAttach_type());
                            //最后更新时间
                            fileMap.put("last_update_time", file.getLast_update_time());

                            if (lastMergeFileMap != null && file.getAttach_type() == 1)
                            {
                                //如果合并的会议文件不是最新的
                                if (file.getLast_update_time().compareTo(
                                    lastMergeFileMap.get("last_update_time").toString()) < 0)
                                {
                                    continue;
                                }
                                else
                                {
                                    fileList.remove(lastMergeFileMap);
                                }
                            }

                            if (file.getAttach_type() == 1)
                            {
                                lastMergeFileMap = fileMap;
                            }

                            fileList.add(fileMap);
                        }
                        map.put("fileList", fileList);
                    }
                    else if (fileCount == 1)//如果只有一个附件，且附件是pdf，则推送。
                    {
                        SubAppMeetingFile file = meetingFileList.get(0);

                        String sufix = getFileSufix(file.getAttach_name());
                        if ("pdf".equalsIgnoreCase(sufix))
                        {
                            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
                            Map<String, Object> fileMap = new HashMap<String, Object>();
                            // 文件名称
                            fileMap.put("attach_name", file.getAttach_name());
                            // 路径
                            fileMap.put("attach_path", rootPath + "/" + file.getAttach_path());
//                            fileMap.put("attach_path", rootPath + "/waterMarkFile/download/" + file.getToken());
                            // 文件类型：0普通附件；1合并附件
                            fileMap.put("attach_type", file.getAttach_type());
                            //最后更新时间
                            fileMap.put("last_update_time", file.getLast_update_time());

                            fileList.add(fileMap);

                            map.put("fileList", fileList);
                        }
                    }
                    else
                    {
                        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
                        map.put("fileList", fileList);
                    }
                    retList.add(map);
                }
                
                ret.setRows(retList);
            }
            return ret;
        }
        catch(Exception e)
        {
            this.logger.error(this.logger.getName() + "/showMeetings," + e.getMessage());
            return Result.errorUnknow();
        }
    }

    /**
     * 推送ipad
     * 
     * @param mid 会议id
     * @return
     */
    @ RequestMapping(value = "/pushToIpad")
    @ ResponseBody
    public Result pushToIpad(String mid)
    {
        if (StringUtils.isEmpty(mid))
        {
            return new Result(APIConstants.MEETING_ID_NULL);
        }
        try
        {
            return subAppMeetingService.updatedMeeting(mid, Integer.valueOf(1));
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/pushToIpad, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
    }

    /**
     * 推送ipad（ipad 无网络）
     * ipad无网络时，会议信息是无法推送到ipad上的，ipad可以通过读存储卡上的内容来获取会议信息
     */
    @ RequestMapping(value = "/pushWithoutWifi")
    @ ResponseBody
    public Result pushWithoutWifi(HttpServletRequest request, String mid)
    {
        if (StringUtils.isEmpty(mid))
        {
            return new Result(APIConstants.MEETING_ID_NULL);
        }
        try
        {
            //新增的系统盘符
            List<String> driverLetterList = getnewDriver(request);
            if (driverLetterList == null || driverLetterList.isEmpty())
            {
                return subAppMeetingService.updatedMeeting(mid, Integer.valueOf(1));
            }
            else
            {
                logger.error("new add driver letter:" + driverLetterList.toString());
                List<String> canWriteDriverList = new ArrayList<String>();
                //遍历盘符，判断盘符是否可写
                for (String driverLetter : driverLetterList)
                {
                    File rootFile = new File(driverLetter);
                    logger.error(driverLetter + "pan can write: " + rootFile.canWrite());
                    if (rootFile.canWrite())
                    {
                        //向盘符中写一个临时测试文件试试看能否写成功
                        String testFileName = "testFile_" + System.currentTimeMillis() + ".json";
                        File testFile = new File(driverLetter + File.separator + testFileName);
                        try
                        {
                            FileUtils.writeStringToFile(testFile, "this is a test file", "UTF-8");
                            //若写文件成功，记录下可写的盘符
                            canWriteDriverList.add(driverLetter);
                            //删除临时的测试文件
                            testFile.delete();
                            //只需要写一个盘符即可
                            break;
                        }
                        catch(IOException e)
                        {
                            logger.error("write file error" + e.getMessage(), e);
                        }
                    }
                }
                if (canWriteDriverList.isEmpty())
                {
                    return Result.error(APIConstants.NO_WRITE_PERMISSION.getCode(), APIConstants.NO_WRITE_PERMISSION.getName());
                }
                return subAppMeetingService.updatedMeetingAndWriteFile(mid, Integer.valueOf(1),
                    canWriteDriverList, request);
            }
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/pushToIpad, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
    }
    
    /**
     * 自动推送：如果服务器上插有移动设备，则推送到移动设备；如果没有直接不向移动设备推送
     * @param request
     * @param mid
     * @return
     */
    @ RequestMapping(value = "/autoPush")
    @ ResponseBody
    public Result autoPush(HttpServletRequest request, String mid)
    {
        if (StringUtils.isEmpty(mid))
        {
            return new Result(APIConstants.MEETING_ID_NULL);
        }
        try
        {
            System.out.println( System.getProperty("java.library.path") );
            //新增的系统盘符
           // List<String> driverLetterList = getnewDriver(request);
            return subAppMeetingService.pushToPortableDevice(mid, Integer.valueOf(1), request);
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/autoPush, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
        catch(Exception e )
        {
            this.logger.error(this.logger.getName() + "/autoPush" + e.getMessage(), e);
            return Result.errorUnknow();
        }
    }

    //无网络回送
    @ RequestMapping(value = "noWifiBackFile")
    @ ResponseBody
    public Result NoWifiBackFile(HttpServletRequest request)
    {
        Result result = null;
        //新增的系统盘符
        List<String> driverLetterList = getnewDriver(request);
        if (driverLetterList == null || driverLetterList.isEmpty())
        {
            return new Result(APIConstants.NO_SDCARD_ERROR);
        }
        for (String driverLetter : driverLetterList)
        {
            //u盘的路径
            String uPath;
            try
            {
                uPath = driverLetter + "/" + PropertiesUtil.getProperty("back_base_path");
                File jsonFile = new File(uPath + "/" + "meetingInfo.json");
                if (jsonFile.exists())
                {
                    String meetingInfoStr = FileUtils.readFileToString(jsonFile, "UTF-8");
                    JSONArray meetingInfoArray = JSONArray.fromObject(meetingInfoStr);
                    if (meetingInfoArray != null && !meetingInfoArray.isEmpty())
                    {
                        for (int i = 0; i < meetingInfoArray.size(); i++)
                        {
                            JSONObject meetingObject = meetingInfoArray.getJSONObject(i);
                            //会议id
                            String mid = meetingObject.getString("mid");
                            //上传人id
                            //                            String uploader_id = meetingObject.getString("uploader_id");
                            //回送路径  uploadFile/backFile/18596644566/
                            if (meetingObject.get("fileList") != null)
                            {
                                JSONArray fileList = meetingObject.getJSONArray("fileList");
                                if (fileList != null && !fileList.isEmpty())
                                {
                                    
                                    String back_path = fileList.getJSONObject(0).getString("back_path");
                                    //回送路径下是否存在回送文件
                                    File backFileFolder = new File(driverLetter + "/" + back_path);
                                    if (backFileFolder.exists())
                                    {
                                        File[] backFiles = backFileFolder.listFiles();
                                        if (backFiles.length > 0)
                                        {
                                            FileAttach fileAttach = copyBackFileToServer(backFiles[0],
                                                request, PropertiesUtil.getProperty("back_path"));
                                            if (fileAttach == null)
                                            {
                                                return new Result(APIConstants.UPLOAD_FAILED);
                                            }
                                            
                                            List<SubAppMeetingFile> existFileList = subAppMeetingFileService
                                                .getSubAppMeetingFile(new SubAppMeetingFile(mid,
                                                    fileAttach.getUploader_id()));
                                            String file_mid = null;
                                            if (existFileList != null && !existFileList.isEmpty())
                                            {
                                                file_mid = existFileList.get(0).getFile_mid();
                                            }
                                            result = subAppMeetingFileService
                                                .addOrModsubAppMeetingFile(new SubAppMeetingFile(mid,
                                                    file_mid, fileAttach.getAttach_name(),
                                                    fileAttach.getAttach_size(),
                                                    fileAttach.getAttach_realname(),
                                                    fileAttach.getPrefix(), fileAttach.getSuffix(), 3,
                                                    fileAttach.getToken(), fileAttach.getAttach_path(),
                                                    fileAttach.getUploader_id()));
                                            
                                            if (result.getStatus() != 0)
                                            {
                                                return result;
                                            }
                                            //回送完成后删除源文件
                                            backFiles[0].delete();
                                        }
                                        
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch(FnfhException e)
            {
                this.logger.error(this.logger.getName() + "/NoWifiBackFile, " + e.getMessage());
                return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
            }
            catch(IOException e)
            {
                this.logger.error(this.logger.getName() + "/NoWifiBackFile, " + e.getMessage());
                return Result.error(APIConstants.WRITE_FILE_ERROR.getCode(),
                    APIConstants.WRITE_FILE_ERROR.getName());
            }

        }
        return Result.success();
    }

    /**
     * 回传会议附件
     * 用户在移动设备端对文件进行手写签批后上传到服务器。用户可以在PC端浏览签批的文件
     * @param request
     * @param uploadFiles 需要上传的文件：参数名称为file
     * @param mid 会议id
     * @param uploader_id  上传者id
     * @return
     */
    @ RequestMapping(value = "/uploadFile")
    @ ResponseBody
    public Result uploadFile(HttpServletRequest request)
    {
        String mid = request.getParameter("mid");

        if (StringUtils.isEmpty(mid))
        {
            return new Result(APIConstants.MEETING_ID_NULL);
        }
        String uploader_id = request.getParameter("uploader_id");
        if (StringUtils.isEmpty(uploader_id))
        {
            return new Result(APIConstants.UPLOADER_ID_NULL);
        }
        MultipartFile uploadFiles = ((MultipartHttpServletRequest)request).getFile("file");
        if (uploadFiles != null && (uploadFiles.getSize() > 0L))
        {
            UploadFile uploadFile = new UploadFile();
            try
            {
                FileAttach fileAttach = uploadFile.uploadFileByAjax(uploadFiles, request,
                    PropertiesUtil.getProperty("back_path"));
                if (fileAttach != null)
                {
                    List<SubAppMeetingFile> existFileList = subAppMeetingFileService
                        .getSubAppMeetingFile(new SubAppMeetingFile(mid, uploader_id));
                    String file_mid = null;
                    if (existFileList != null && !existFileList.isEmpty())
                    {
                        file_mid = existFileList.get(0).getFile_mid();
                    }
                    return subAppMeetingFileService.addOrModsubAppMeetingFile(
                        new SubAppMeetingFile(mid, file_mid, fileAttach.getAttach_name(),
                            fileAttach.getAttach_size(), fileAttach.getAttach_realname(),
                            fileAttach.getPrefix(), fileAttach.getSuffix(), 3,
                            fileAttach.getToken(), fileAttach.getAttach_path(), uploader_id));
                }
                else
                {
                    return Result.error(APIConstants.UPLOAD_FAILED.getCode(),
                        APIConstants.UPLOAD_FAILED.getName());
                }
            }
            catch(FnfhException e)
            {
                this.logger.error(this.logger.getName() + "/uploadFile, " + e.getMessage());
                return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
            }
        }
        else
        {
            if (uploadFiles == null)
            {
                return new Result(APIConstants.UPLOAD_FILE_NULL);
            }
            else
            {
                return new Result(APIConstants.FILE_CONTENT_0);
            }
        }
    }

    /**
     * 统计会议文件的下载情况
     * 此接口用于服务端统计会议文档下载情况，移动端在下载完会议文件后调用此接口
     * @param mid 会议id
     * @param userId  下载人id
     * @return
     */
    @ RequestMapping("/recordDownload")
    @ ResponseBody
    public Result recordDownload(String mid, String userId)
    {
        if (StringUtils.isEmpty(mid))
        {
            return new Result(APIConstants.MEETING_ID_NULL);
        }
        if (StringUtils.isEmpty(userId))
        {
            return new Result(APIConstants.USER_ID_NULL);
        }
        return subAppMeetingService
            .addMeetingFileDownload(new MeetingFileDownload(CommonMethod.getUID(), userId, mid));
        //        return new Result();
    }

    /**
     * 获取会议资料的下载信息
     * @param mid
     * @return
     */
    @ RequestMapping(value = "getMeetingFileDownload")
    @ ResponseBody
    public Result getDownloaderName(String mid)
    {
        List<MeetingFileDownload> downloadList = new ArrayList<MeetingFileDownload>();
        if (!StringUtils.isEmpty(mid))
        {
            downloadList = subAppMeetingService
                .getMeetingFileDownload(new MeetingFileDownload(mid));
        }
        Result result = Result.success();
        result.setData(downloadList);
        return result;
    }

    /**
     * 更新设备电量
     * @param request
     * @param ipadUUID 设备唯一标识符
     * @param energy 电量，1-100的整数
     * @return
     */
    @ RequestMapping(value = "updateDeviceEnergy")
    @ ResponseBody
    public Result updateDeviceEnergy(HttpServletRequest request, String ipadUUID, String energy)
    {
        try
        {
            // 判断用户是否登录
            validateAdminAction(request.getSession());
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/showMeetings, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
        if (StringUtils.isEmpty(ipadUUID))
        {
            return new Result(APIConstants.IPAD_NO_NULL);
        }
        if (StringUtils.isEmpty(energy))
        {
            return new Result(APIConstants.ENERGY_NO_NULL);
        }
        Pattern pattern = Pattern.compile("^([1-9]?[0-9]|100)$");
        if (!pattern.matcher(energy).matches())
        {
            return new Result(APIConstants.NUMBER_IN_100);
        }
        return subAppUserService.updateDeviceEnergy(ipadUUID, Integer.valueOf(energy));
    }
    /**
     * 更新设备存储信息
     * @param request
     * @param ipadUUID 设备唯一标识符
     * @param storage 存储信息，由移动端提供处理后的字符串
     * @return
     */
    @ RequestMapping(value = "updateDeviceStorage")
    @ ResponseBody
    public Result updateDeviceStorage(HttpServletRequest request, String ipadUUID, String storage)
    {
        try
        {
            // 判断用户是否登录
            validateAdminAction(request.getSession());
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/showMeetings, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
        if (StringUtils.isEmpty(ipadUUID))
        {
            return new Result(APIConstants.IPAD_NO_NULL);
        }
        if (StringUtils.isEmpty(storage))
        {
            return new Result(APIConstants.STORAGE_NO_NULL);
        }
        return subAppUserService.updateDeviceStorage(ipadUUID, storage);
    }
    @ RequestMapping("/waterMarkFile/download/{token}")
    public ResponseEntity<?> download(HttpServletRequest request, @PathVariable ("token") String token, String filePath)
        throws IOException
    {
        try
        {
            // 判断用户是否登录
            validateAdminAction(request.getSession());
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/showMeetings, " + e.getMessage());
            return new ResponseEntity<Result>(Result.error(Integer.valueOf(e.getCode()), e.getMessage()),HttpStatus.OK);
        }
        User loginUser = (User)request.getSession().getAttribute("loginUser");
        SubAppMeetingFile subAppMeetingFile = new SubAppMeetingFile();
        subAppMeetingFile.setToken(token);
        List<SubAppMeetingFile> fileList = subAppMeetingFileService.getSubAppMeetingFile(subAppMeetingFile);
        if (fileList != null && !fileList.isEmpty())
        {
            SubAppMeetingFile sourceFile = fileList.get(0);
            String fileName = sourceFile.getAttach_path().substring(sourceFile.getAttach_path().lastIndexOf("/") + 1);
            DocConverter converter = new DocConverter();
            String destFolderPath = System.getProperty("shenhua.root") + "/uploadFile/waterMarkFile" + "/" + System.currentTimeMillis() ;
            File destFolderFile = new File(destFolderPath);
            if (!destFolderFile.exists())
            {
                destFolderFile.mkdirs();
            }
            String destFilePath = destFolderPath + "/" + fileName;
            converter.addWaterMark(System.getProperty("shenhua.root") + "/" + sourceFile.getAttach_path(), destFilePath, loginUser.getReal_name() == null ? loginUser.getUser_name() : loginUser.getReal_name());
//        String fileName = filePath.substring(filePath.lastIndexOf("/"));
//        String rootPath = request.getServletContext().getRealPath("/");
            File file = new File(destFilePath);
            HttpHeaders headers = new HttpHeaders();
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题  
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
                HttpStatus.CREATED);
        }
        return null;
    }
    
    /**
     * 结束会议
     * 
     * @param mid 会议id
     * @return
     */
    @ RequestMapping(value = "/endMeeting")
    @ ResponseBody
    public Result endMeeting(String mid)
    {
        if (StringUtils.isEmpty(mid))
        {
            return new Result(APIConstants.MEETING_ID_NULL);
        }
        try
        {
            return subAppMeetingService.updateMeetingStatus(mid, Integer.valueOf(2));
        }
        catch(FnfhException e)
        {
            this.logger.error(this.logger.getName() + "/endMeeting, " + e.getMessage());
            return Result.error(Integer.valueOf(e.getCode()), e.getMessage());
        }
    }
    
    /**
     * 获取服务器当前是否有新增的盘符
     * 
     */
    public List<String> getnewDriver(HttpServletRequest request)
    {
        //系统初始化时已将所有盘符记录
        List<String> oldDriverList = (List<String>)request.getServletContext()
            .getAttribute("driverLetter");
        List<String> driverList = new ArrayList<String>();
        //现在系统的盘符
        File[] rootFiles = File.listRoots();
        for (int i = 0; i < rootFiles.length; i++)
        {
            String driverLetter = rootFiles[i].getAbsolutePath();
            //不包含这个盘符,表示此盘符是新增盘符
            if (!oldDriverList.contains(driverLetter))
            {
                //return driverLetter;
                driverList.add(driverLetter);
            }
        }
        //没找到新的盘符
        return driverList;
    }

    /**
     * 将sd卡中的回送文件拷贝至系统目录下
     * @param srcFile 源文件
     * @param request request
     * @param relativePath  相对路径
     * @return
     */
    public FileAttach copyBackFileToServer(File srcFile, HttpServletRequest request,
        String relativePath)
    {
        FileAttach fileAttach = new FileAttach();
        String token = System.currentTimeMillis() + "";
        relativePath += token + "/";
        String path = request.getSession().getServletContext().getRealPath(relativePath);
        String tempFileName = srcFile.getName();
        //文件名错误
        if (tempFileName.lastIndexOf("_") < 0)
        {
            return null;
        }
        //从文件名中得出上传者的id
        String uploader_id = tempFileName.substring(tempFileName.lastIndexOf("_") + 1,
            tempFileName.lastIndexOf("."));
        long fileSize = srcFile.length();
        System.out.println("tempFileName=" + tempFileName);
        File filePath = new File(path);
        if (!filePath.exists())
        {
            filePath.mkdirs();
        }
        //去掉文件名中的用户id
        tempFileName = tempFileName.substring(0, tempFileName.lastIndexOf("_"))
            + tempFileName.substring(tempFileName.lastIndexOf("."));
        File targetFile = new File(path, tempFileName);
        //保存  
        try
        {
            FileUtils.copyFile(srcFile, targetFile);
            fileAttach.setAttach_name(tempFileName);//文件原始名
            fileAttach.setAttach_realname(tempFileName);//文件存储名
            fileAttach.setPrefix(tempFileName.substring(0, tempFileName.lastIndexOf(".")));//前缀
            fileAttach.setSuffix(tempFileName.substring(tempFileName.lastIndexOf(".") + 1));//后缀
            fileAttach.setAttach_path(relativePath + tempFileName);
            fileAttach.setToken(token);
            fileAttach.setAttach_size(fileSize + "");
            fileAttach.setUploader_id(uploader_id);
            return fileAttach;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
