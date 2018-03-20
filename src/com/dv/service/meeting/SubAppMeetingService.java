package com.dv.service.meeting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;

import org.apache.batik.css.engine.value.svg.FilterManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.dao.meeting.MeetingFileDownloadMapper;
import com.dv.dao.meeting.SubAppMeetingFileMapper;
import com.dv.dao.meeting.SubAppMeetingMapper;
import com.dv.dao.meeting.SubAppUserMapper;
import com.dv.dao.user.UserMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.MeetingFileDownload;
import com.dv.entity.meeting.SubAppMeeting;
import com.dv.entity.meeting.SubAppMeetingFile;
import com.dv.entity.meeting.SubAppUser;
import com.dv.entity.user.User;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
import com.dv.util.MTPFileManager;
import com.dv.util.PropertiesUtil;

import be.derycke.pieter.com.COMException;
import jmtp.PortableDevice;
import jmtp.PortableDeviceManager;
import jmtp.PortableDeviceObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @classDesc ：
 * 会议管理service
 * @creater: 杨群山
 * @creationDate:2017年5月3日 下午2:58:12
 */
@Service
public class SubAppMeetingService {
    Logger logger = Logger.getLogger(this.getClass()); // 记录日志

    @Resource
    private SubAppMeetingMapper subAppMeetingMapper;

    @Resource
    private SubAppMeetingFileMapper subAppMeetingFileMapper;//附件mapper

    @Resource
    private PlatformTransactionManager ptm;

    @Resource
    private UserMapper userMapper;//用户mapper

    @Resource
    private MeetingFileDownloadMapper meetingFileDownloadMapper;

    @Resource
    private SubAppUserMapper subAppUserMapper;

    /**
     * @param subAppMeeting
     * @param fnfhPageData
     * @param loginUser
     * @return
     * @throws FnfhException
     * @methodDesc: 查询会议信息, 超级管理员可以查看所有会议，其他管理员只能查看自己创建的会议
     * @creater: 李梦婷
     * @creationDate:2017年5月9日 上午11:17:21
     */
    public Result findsubAppMeeting(SubAppMeeting subAppMeeting, FnfhPageData fnfhPageData, User loginUser) throws FnfhException {
        Result result = new Result();
        int totalCount = 0;
        //将
        subAppMeeting.setStart(fnfhPageData.getOffset());
        subAppMeeting.setLimit(fnfhPageData.getLimit());
        subAppMeeting.setMeet_attend(fnfhPageData.getString("meet_attend"));
        String is_paid = fnfhPageData.getString("is_paid");
        String search = fnfhPageData.getSearch();
        String order = fnfhPageData.getOrder();
        String sort = fnfhPageData.getSort();

        if (!StringUtils.isEmpty(order)) {
            subAppMeeting.setOrder(order);
        }
        if (!StringUtils.isEmpty(sort)) {
            subAppMeeting.setSort(sort);
        }
        if (!StringUtils.isEmpty(is_paid)) {
            subAppMeeting.setIs_ipad((CommonMethod.StringToInt(is_paid, -1)));
        }
        if (!StringUtils.isEmpty(search)) {
            subAppMeeting.setSearch(search);
        }
        if (loginUser.getIs_super() != 1)//不是超级管理员
        {
            subAppMeeting.setUser_id(loginUser.getUser_id());
        }
        List<SubAppMeeting> list = subAppMeetingMapper.findSubAppMeeting(subAppMeeting);
        totalCount = subAppMeetingMapper.findSubAppMeetingCount(subAppMeeting);
        result.setRows(list);
        result.setTotal(totalCount);
        return result;
    }

    /**
     * @param subAppMeeting
     * @param pgdata
     * @return
     * @throws FnfhException
     * @methodDesc: 新增会议信息
     * @creater: 李梦婷
     * @creationDate:2017年5月5日 下午5:01:30
     */
    public Result addOrModifySubAppMeeting(SubAppMeeting subAppMeeting, FnfhPageData pgdata, User loginUser, Integer attendeesLimit) throws FnfhException {
        int result = 0;
        boolean ispush=false;
        try {
            subAppMeeting.setUser_id(loginUser.getUser_id());//拟稿人
            if ("1"==pgdata.getString("pushflag")){
                ispush=true;
            }

            if (StringUtils.isEmpty(subAppMeeting.getMeet_name())) {
                return Result.error(SystemConst.NOT_NULL, "会议标题不能为空");
            }
            if (subAppMeeting.getMeet_name().length()>50) {
                return Result.error(SystemConst.NOT_NULL, "会议标题不能大于50个字符");
            }
            if (StringUtils.isEmpty(subAppMeeting.getMeet_addr())&&ispush) {
                return Result.error(SystemConst.NOT_NULL, "会议地点不能为空");
            }
            if (StringUtils.isEmpty(subAppMeeting.getMeet_time())) {
                return Result.error(SystemConst.NOT_NULL, "会议开始时间为必选项目");
            }
            String attends = subAppMeeting.getMeet_attend();
            if (StringUtils.isEmpty(attends)&&ispush) {
                return Result.error(SystemConst.NOT_NULL, "参会领导不能为空");
            }
//            保存会议的时候检测参会人数是否操作会议最大授权人数
            String[] attendArr = attends.split(";");
            //System.out.println("参会人员："+attends+",人员数量："+attendArr.length+",授权数量："+attendeesLimit);
            if (attendArr.length > attendeesLimit) {
                return Result.error(SystemConst.CHECKATTENDEESLIMIT_ERROR, SystemConst.CHECKATTENDEESLIMIT_ERROR_MSG + ":" + attendeesLimit.toString());
            }
            //会议结束时间如果为空字符串会报错
            if (StringUtils.isEmpty(subAppMeeting.getEnd_time())) {
                subAppMeeting.setEnd_time(null);
            }
            String attachs = pgdata.getString("attachsList");//添加的附件信息
            String delatchs = pgdata.getString("delatchsList");//删除的附件信息

            //记录入库
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = ptm.getTransaction(def);

            if (StringUtils.isEmpty(subAppMeeting.getMid()))//新增
            {
                try {
                    String mid = CommonMethod.getUID();
                    subAppMeeting.setMid(mid);
                    //保存会议信息
                    result = subAppMeetingMapper.addSubAppMeeting(subAppMeeting);
                    List<SubAppMeetingFile> attachsList = getAttachList(attachs, mid, loginUser.getUser_id());
                    //保存附件信息
                    if (attachsList != null && attachsList.size() > 0) {
                        result = subAppMeetingFileMapper.batchAddMeetFile(attachsList);
                    }
                    ptm.commit(status);//提交
                    if (result < 1) {
                        return Result.error(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ptm.rollback(status);//回滚
                    return Result.error(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG);
                }


            } else//修改
            {
                try {
                    result = subAppMeetingMapper.modSubAppMeetingById(subAppMeeting);
                    if (result > 0) {
                        List<SubAppMeetingFile> attachsList = getAttachList(attachs, subAppMeeting.getMid(), loginUser.getUser_id());
                        List<String> delAttachList = getList(delatchs);//删除的附件
                        //保存附件信息
                        if (attachsList != null && attachsList.size() > 0) {
                            result = subAppMeetingFileMapper.batchAddMeetFile(attachsList);
                        }
                        //删除附件信息
                        if (delAttachList != null && delAttachList.size() > 0) {
                            result = subAppMeetingFileMapper.delSubAppMeetingFileByIds(delAttachList);
                        }
                        ptm.commit(status);//提交
                        if (result < 1) {
                            Result.error(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG);
                        }
                    } else {
                        Result.error(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ptm.rollback(status);//回滚
                    return Result.error(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error(logger.getName() + "/addsubAppMeeting," + e.getMessage() + "," + e.toString());
            if (e.toString().indexOf("Duplicate entry") >= 0) {
                throw new FnfhException(SystemConst.DUP_KEY, SystemConst.DUP_KEY_MSG);
            } else {
                throw new FnfhException(SystemConst.SAVE_FAIL, SystemConst.SAVE_FAIL_MSG);
            }
        }


        return new Result(subAppMeeting.getMid());
    }

    /**
     * @param parseStr
     * @return
     * @methodDesc: 获取集合
     * @creater: 李梦婷
     * @creationDate:2017年5月5日 下午5:53:06
     */
    private List<String> getList(String parseStr) {
        if (parseStr == null || parseStr.equals("") || parseStr.equals("[]") || parseStr.equals("null")) {
            return null;
        }
        try {
            JSONArray objArray = JSONArray.fromObject(parseStr);
            Iterator iter = objArray.iterator();
            List<String> list = new ArrayList<String>();
            while (iter.hasNext()) {
                list.add(iter.next().toString());
            }
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("getList,解析出错，" + e.getMessage() + "解析内容:parseStr=" + parseStr);
            return null;
        }
    }


    /**
     * @param parseStr
     * @param mid
     * @return
     * @methodDesc: 获取附件信息
     * @creater: 李梦婷
     * @creationDate:2017年5月5日 下午5:35:49
     */
    private List<SubAppMeetingFile> getAttachList(String parseStr, String mid, String uploader_id) {
        if (parseStr == null || parseStr.equals("") || parseStr.equals("[]") || parseStr.equals("null")) {
            return null;
        }
        try {
            JSONArray objArray = JSONArray.fromObject(parseStr);
            List<SubAppMeetingFile> list = new ArrayList<SubAppMeetingFile>();
            for (int i = 0; i < objArray.size(); i++) {
                JSONObject obj = objArray.getJSONObject(i);
                SubAppMeetingFile meetFile = new SubAppMeetingFile();
                meetFile.setMeeting_mid(mid);
                meetFile.setUploader_id(uploader_id);
                String fileId = CommonMethod.getUID();
                meetFile.setFile_mid(fileId);//文件id
                if (obj.containsKey("attach_name"))//附件原始名称
                {
                    meetFile.setAttach_name(obj.getString("attach_name"));
                } else {
                    return null;
                }
                if (obj.containsKey("attach_size"))//附件大小
                {
                    meetFile.setAttach_size(obj.getString("attach_size"));
                }
                if (obj.containsKey("attach_realname"))//附件的真实名称
                {
                    meetFile.setAttach_realname(obj.getString("attach_realname"));
                }
                if (obj.containsKey("attach_path"))//附件保存的相对路径
                {
                    meetFile.setAttach_path(obj.getString("attach_path"));
                } else {
                    return null;
                }
                if (obj.containsKey("prefix"))//附件前缀
                {
                    meetFile.setPrefix(obj.getString("prefix"));
                }
                if (obj.containsKey("suffix"))//附件后缀
                {
                    meetFile.setSuffix(obj.getString("suffix"));
                }
                if (obj.containsKey("attach_type"))//附件类型
                {
                    meetFile.setAttach_type(obj.getInt("attach_type"));
                }
                if (obj.containsKey("token")) {
                    meetFile.setToken(obj.getString("token"));
                }
                list.add(meetFile);
            }
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("getAttachList,解析出错，" + e.getMessage() + "解析内容:parseStr=" + parseStr);
            return null;
        }

    }

    /**
     * @param pgdata
     * @return
     * @throws FnfhException
     * @methodDesc: 根据会议id集合删除会议信息
     * @creater: 李梦婷
     * @creationDate:2017年5月6日 下午1:26:53
     */
    public Result delSubAppMeeting(FnfhPageData pgdata) throws FnfhException {
        int result = 0;
        String jsonStr = pgdata.getString("ids");
        if (StringUtils.isEmpty(jsonStr)) {
            return Result.error(SystemConst.NOT_NULL, SystemConst.NOT_NULL_MSG);
        }
        try {
            JSONArray jsonArray2 = JSONArray.fromObject(jsonStr);
            List<String> idsList = JSONArray.toList(jsonArray2);
            //记录入库
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = ptm.getTransaction(def);
            try {
                //先根据会议id删除附件信息
                result = subAppMeetingFileMapper.delSubAppMeetingFileByIds(idsList);
                result = subAppMeetingMapper.delSubAppMeetingByIds(idsList);
                ptm.commit(status);//提交
            } catch (Exception e) {
                ptm.rollback(status);//回滚
                return Result.error(SystemConst.SYS_ERROR, SystemConst.SYS_ERROR_MSG);
            }
            if (result < 1) {
                return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
            }
        } catch (Exception ex) {
            logger.error(logger.getName() + "/delSubAppMeeting," + ex.getMessage() + "," + ex.toString());
            if (ex.toString().indexOf("Cannot delete or update a parent row:a foreign key constraint fails") >= 0) {
                throw new FnfhException(SystemConst.DATA_INUSE, SystemConst.DATA_INUSE_MSG);
            } else {
                throw new FnfhException(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
            }
        }
        return Result.success();
    }

    /**
     * @param subAppMeeting
     * @param
     * @return
     * @throws FnfhException
     * @methodDesc: 修改会议信息
     * @creater: 杨群山
     * @creationDate:2017年5月4日 下午3:00:43
     * @deprecated 暂不使用
     */
    public Result modsubAppMeeting(SubAppMeeting subAppMeeting) throws FnfhException {
        int result = 0;
        try {
            if (subAppMeeting == null || StringUtils.isEmpty(subAppMeeting.getMid())) {
                return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
            }
            result = subAppMeetingMapper.modSubAppMeetingById(subAppMeeting);
            if (result < 1) {
                return Result.error(SystemConst.MODIFY_FAIL, SystemConst.MODIFY_FAIL_MSG);
            }
        } catch (Exception ex) {
            logger.error(logger.getName() + "/modsubAppMeeting," + ex.getMessage() + "," + ex.toString());
            if (ex.toString().indexOf("Dupilcate entry") >= 0) {
                throw new FnfhException(SystemConst.DUP_KEY, SystemConst.DUP_KEY_MSG);
            } else {
                throw new FnfhException(SystemConst.MODIFY_FAIL, SystemConst.MODIFY_FAIL_MSG);
            }
        }
        return Result.success();
    }

    /**
     * @param subAppMeeting
     * @return
     * @throws FnfhException
     * @methodDesc: 获取单个对象
     * @creater: 李梦婷
     * @creationDate:2017年5月5日 下午7:16:38
     */
    public Result getObjInstane(SubAppMeeting subAppMeeting) throws FnfhException {
        Result result = new Result();
        if (StringUtils.isEmpty(subAppMeeting.getMid())) {
            return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
        }
        subAppMeeting.setStart(0);
        subAppMeeting.setLimit(1);
        List<SubAppMeeting> list = subAppMeetingMapper.findSubAppMeeting(subAppMeeting);
        if (list != null && list.size() > 0) {
            subAppMeeting = list.get(0);
            //获取附件信息
            SubAppMeetingFile meetFile = new SubAppMeetingFile();
            meetFile.setMeeting_mid(subAppMeeting.getMid());
            List<SubAppMeetingFile> fileList = subAppMeetingFileMapper.findSubAppMeetingFile(meetFile);
            subAppMeeting.setFileList(fileList);
            //20170509 获取参会人员信息
            String userIdsStr = subAppMeeting.getMeet_attend_ids();
            if (!StringUtils.isEmpty(userIdsStr)) {
                List<User> sortUserList;
                try {
                    String[] _ids = userIdsStr.split(";");
                    List<String> ids = Arrays.asList(_ids);
                    ids = new ArrayList(ids);//不然remove的时候会报错
                    List<User> userList = userMapper.findUserByUserIds(ids);
//					userList=getUserList(userList);
                    sortUserList = new ArrayList<User>();
                    for (int i = 0; i < ids.size(); i++) {
                        String id = ids.get(i);
                        for (int j = userList.size() - 1; j >= 0; j--) {
                            User user = userList.get(j);
                            if (id.equals(user.getUser_id())) {
                                sortUserList.add(user);
                                ids.remove(i);
                                userList.remove(j);
                                i--;
                                break;
                            }
                        }
                    }

                    subAppMeeting.setAttendUserList(sortUserList);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            result.setData(subAppMeeting);
            return result;
        } else {
            return Result.error(SystemConst.QUERY_FAIL, SystemConst.QUERY_FAIL_MSG);
        }
    }

    private List<User> getUserList(List<User> userList) {
        List<User> list = new ArrayList<User>();
        for (User user : userList) {
            list.add(user);
        }
        return list;
    }

    /**
     * 获取当前用户所出席的会议
     *
     * @param pageData  分页信息
     * @param loginUser 当前登录用户
     * @return
     */
    public Result findMeetingsOfAttendee(FnfhPageData pageData, User loginUser) {
        Result result = new Result();
        SubAppMeeting subAppMeeting = new SubAppMeeting();
        int totalCount = 0;
        subAppMeeting.setStart(pageData.getOffset());
        subAppMeeting.setLimit(pageData.getLimit());
        String is_paid = pageData.getString("is_paid");
        String search = pageData.getSearch();
        String order = pageData.getOrder();
        String sort = pageData.getSort();
        String meet_type = pageData.getString("meet_type");
//        String  status=pageData.getString("status");
        if (!StringUtils.isEmpty(order)) {
            subAppMeeting.setOrder(order);
        }
        if (!StringUtils.isEmpty(sort)) {
            subAppMeeting.setSort(sort);
        }
        if (!StringUtils.isEmpty(is_paid)) {
            subAppMeeting.setIs_ipad(Integer.valueOf(CommonMethod.StringToInt(is_paid, -1)));
        }
        if (!StringUtils.isEmpty(meet_type)) {
            subAppMeeting.setMeet_type(meet_type);
        }
        //只查询推送的会议
        subAppMeeting.setIs_ipad(Integer.valueOf(1));
        if (!StringUtils.isEmpty(search)) {
            subAppMeeting.setSearch(search);
        }
        subAppMeeting.setMeet_attend_ids(loginUser.getUser_id());
        //如果用户是临时用户则只能查询到当前未结束的会议
        if (loginUser.getIs_temp() == 1) {
            subAppMeeting.setStatus(0);
        }
        List<SubAppMeeting> subAppMeetingList = this.subAppMeetingMapper.findSubAppMeeting(subAppMeeting);
        totalCount = this.subAppMeetingMapper.findSubAppMeetingCount(subAppMeeting);
        if (subAppMeetingList != null && !subAppMeetingList.isEmpty()) {
            for (SubAppMeeting meeting : subAppMeetingList) {
                SubAppMeetingFile meetFile = new SubAppMeetingFile();
                meetFile.setMeeting_mid(meeting.getMid());
                List<SubAppMeetingFile> fileList = this.subAppMeetingFileMapper.findSubAppMeetingFile(meetFile);
                meeting.setFileList(fileList);
            }
        }
        result = Result.success();
        result.setRows(subAppMeetingList);
        result.setTotal(Integer.valueOf(totalCount));
        return result;

    }

    /**
     * 更新会议的推送状态
     *
     * @param mid     会议id
     * @param is_ipad 是否推送ipad 0：未推送；1：已推送
     * @return
     * @throws FnfhException
     */
    public Result updatedMeeting(String mid, Integer is_ipad) throws FnfhException {
        SubAppMeeting meeting = new SubAppMeeting(mid);
        int count = subAppMeetingMapper.findSubAppMeetingCount(meeting);
        if (count < 1) {
            return Result.error(APIConstants.MEETING_NOT_EXIST.getCode(), APIConstants.MEETING_NOT_EXIST.getName());
        } else {
            meeting.setIs_ipad(is_ipad);
            meeting.setPush_time(CommonMethod.getDateFormat(new Date()));
            int result = subAppMeetingMapper.modSubAppMeetingById(meeting);
            if (result < 1) {
                throw new FnfhException(-30003, "数据更新失败");
            }
        }
        return Result.success();
    }

    /**
     * 更新会议的推送状态
     *
     * @param mid    会议id
     * @param status 会议状态 0未启用 1已启用 2已结束
     * @return
     * @throws FnfhException
     */
    public Result updateMeetingStatus(String mid, Integer status) throws FnfhException {
        SubAppMeeting meeting = new SubAppMeeting(mid);
        int count = subAppMeetingMapper.findSubAppMeetingCount(meeting);
        if (count < 1) {
            return Result.error(APIConstants.MEETING_NOT_EXIST.getCode(), APIConstants.MEETING_NOT_EXIST.getName());
        } else {
            meeting.setStatus(status);
//            meeting.setPush_time(CommonMethod.getDateFormat(new Date()));
            int result = subAppMeetingMapper.modSubAppMeetingById(meeting);
            if (result < 1) {
                throw new FnfhException(-30003, "数据更新失败");
            }
        }
        return Result.success();
    }

    /**
     * 更新会议的推送状态并写文件到u盘
     *
     * @param mid              会议id
     * @param is_ipad          是否推送ipad 0：未推送；1：已推送
     * @param driverLetterList 系统盘符，u盘的盘符。
     * @return
     * @throws FnfhException
     */
    public Result updatedMeetingAndWriteFile(String mid, Integer is_ipad, List<String> driverLetterList, HttpServletRequest request) throws FnfhException {
        SubAppMeeting meeting = new SubAppMeeting(mid);
        // int count = subAppMeetingMapper.findSubAppMeetingCount(meeting);
        List<SubAppMeeting> subAppMeetingList = subAppMeetingMapper.findSubAppMeeting(meeting);
        if (subAppMeetingList == null || subAppMeetingList.isEmpty()) {
            return Result.error(APIConstants.MEETING_NOT_EXIST.getCode(), APIConstants.MEETING_NOT_EXIST.getName());
        } else {
            meeting.setIs_ipad(is_ipad);
            meeting.setPush_time(CommonMethod.getDateFormat(new Date()));
            int result = subAppMeetingMapper.modSubAppMeetingById(meeting);
            if (result < 1) {
                throw new FnfhException(-30003, "数据更新失败");
            }
        }

        //当driverLetterList不为空是表示要写U盘
        if (driverLetterList != null && !driverLetterList.isEmpty()) {
            meeting = subAppMeetingList.get(0);
            //获取会议文件
            List<SubAppMeetingFile> fileList = this.subAppMeetingFileMapper.findSubAppMeetingFile(new SubAppMeetingFile(meeting.getMid()));
            meeting.setFileList(fileList);
            for (String driverLetter : driverLetterList) {
                //u盘的路径
                String uPath = driverLetter + "/" + PropertiesUtil.getProperty("back_base_path");
                File uFile = new File(uPath);
                if (!uFile.exists()) {
                    uFile.mkdirs();
                }
                File jsonFile = new File(uPath + "/" + "meetingInfo.json");
                try {
                    JSONArray meetingInfoArray = null;
                    Map<String, Object> meetingInfoMap = assembleMeetingInfo(meeting, uPath);
                    //如果json文件已经存在
                    if (jsonFile.exists()) {
                        String meetingInfoStr = FileUtils.readFileToString(jsonFile, "UTF-8");
                        meetingInfoArray = JSONArray.fromObject(meetingInfoStr);
                        if (meetingInfoMap.get("mid") != null) {
                            //遍历数组，判断会议信息是否已经存在
                            boolean meetingExist = false;
                            int index = 0;
                            for (int i = 0; i < meetingInfoArray.size(); i++) {
                                JSONObject object = meetingInfoArray.getJSONObject(i);
                                if (object.getString("mid").equals(meetingInfoMap.get("mid"))) {
                                    meetingExist = true;
                                    index = i;
                                    break;
                                }
                            }
                            //会议信息不存在
                            if (!meetingExist) {
                                meetingInfoArray.add(meetingInfoMap);
                            } else {
                                //替换
                                meetingInfoArray.remove(index);
                                meetingInfoArray.add(meetingInfoMap);
                            }
                        }

                    } else {
                        meetingInfoArray = JSONArray.fromObject(meetingInfoMap);
                    }
                    //写json文件 - 会议信息列表
                    FileUtils.writeStringToFile(jsonFile, meetingInfoArray.toString(), "UTF-8");
                } catch (IOException e) {
                    logger.error("文件路径为：" + jsonFile.getAbsolutePath());
                    logger.error("updatedMeetingAndWriteFile error! " + e.getMessage(), e);
                    throw new FnfhException(APIConstants.WRITE_FILE_ERROR.getCode(), APIConstants.WRITE_FILE_ERROR.getName());
                }
                if (fileList != null && !fileList.isEmpty()) {
                    for (SubAppMeetingFile meetingFile : fileList) {
                        //源文件的相对目录
                        String srcRelativePath = meetingFile.getAttach_path();
                        //目标文件的绝对路径
                        String destAbsolutePath = uPath + "/" + srcRelativePath;
                        //源文件的绝对路径
                        String srcAbsolutePath = request.getSession().getServletContext().getRealPath(srcRelativePath);
                        //复制文件到U盘
                        try {
                            FileUtils.copyFile(new File(srcAbsolutePath), new File(destAbsolutePath));
                        } catch (IOException e) {
                            logger.error("copyFile error!", e);
                            logger.error("source file path: " + srcAbsolutePath + " dest file path: " + destAbsolutePath);
                            throw new FnfhException(APIConstants.COPY_FILE_ERROR.getCode(), APIConstants.COPY_FILE_ERROR.getName());
                        }

                    }
                }
            }
        }
        return Result.success();
    }

    //推送到便携式设备
    public Result pushToPortableDevice(String mid, Integer is_ipad, HttpServletRequest request) throws FnfhException {
        SubAppMeeting meeting = new SubAppMeeting(mid);
        // int count = subAppMeetingMapper.findSubAppMeetingCount(meeting);
        List<SubAppMeeting> subAppMeetingList = subAppMeetingMapper.findSubAppMeeting(meeting);
        if (subAppMeetingList == null || subAppMeetingList.isEmpty()) {
            return Result.error(APIConstants.MEETING_NOT_EXIST.getCode(), APIConstants.MEETING_NOT_EXIST.getName());
        } else {
            meeting.setIs_ipad(is_ipad);
            meeting.setPush_time(CommonMethod.getDateFormat(new Date()));
            int result = subAppMeetingMapper.modSubAppMeetingById(meeting);
            if (result < 1) {
                throw new FnfhException(-30003, "数据更新失败");
            }
        }

        meeting = subAppMeetingList.get(0);
        //获取会议文件
        List<SubAppMeetingFile> fileList = this.subAppMeetingFileMapper.findSubAppMeetingFile(new SubAppMeetingFile(meeting.getMid()));
        meeting.setFileList(fileList);
        //开始对便携式设备操作
        System.out.println("start to operate wpd!");
        PortableDeviceManager manager = new PortableDeviceManager();
        System.out.println("portable device init sucessfully");
        PortableDevice[] devices = manager.getDevices();
        System.out.println("device num:" + devices.length);
        for (int i = 0; i < devices.length; i++) {
            MTPFileManager fileManager = new MTPFileManager();
            fileManager.openDevice(devices[i]);
            //判断会议是否推送到此设备
            if (canPushMeetToDevice(fileManager, devices[i], meeting)) {
                //暂时把设备信息输出到error中
                logger.error("device " + i + 1 + "info: model = " + devices[i].getModel() + " " + devices[i].toString());
                //先将会议信息写入设备中
                Map<String, Object> meetingInfoMap = assembleMeetingInfo(meeting, null);
                JSONArray meetingInfoArray = new JSONArray();
                meetingInfoArray.add(meetingInfoMap);
                File jsonFile = new File(System.getProperty("shenhua.root") + File.separator + "tmp" + File.separator + System.currentTimeMillis() + File.separator + "meetingInfo.json");
                try {
                    FileUtils.writeStringToFile(jsonFile, meetingInfoArray.toString(), "UTF-8");
                    //将jsonFile 发送到设备中
                    try {
                        fileManager.createFolder("\\shenhua\\uploadFile");
                        fileManager.addFile(jsonFile, "\\shenhua");
                        //删除掉json文件及其上一层文件夹
                        FileUtils.deleteDirectory(jsonFile.getParentFile());

                        //再将会议文件写入设备
                        if (fileList != null && !fileList.isEmpty()) {
                            for (SubAppMeetingFile meetingFile : fileList) {
                                //源文件的相对路径
                                String srcRelativePath = meetingFile.getAttach_path();
                                //源文件的绝对路径
                                String srcAbsolutePath = request.getSession().getServletContext().getRealPath(srcRelativePath);
                                //目标文件的绝对路径
                                String destAbsolutePath = "\\shenhua\\" + srcRelativePath.substring(0, srcRelativePath.lastIndexOf("/")).replaceAll("/", "\\\\");
                                fileManager.addFile(new File(srcAbsolutePath), destAbsolutePath);
                            }
                        }
                    } catch (COMException e) {
                        logger.error("send file to device error！" + e.getMessage(), e);
                        fileManager.closeDevice();
                    }
                } catch (IOException e) {
                    logger.error("jsonFile error：" + e.getMessage(), e);
                    fileManager.closeDevice();
                }
            }
            fileManager.closeDevice();

        }

        return Result.success();
    }


    /**
     * 添加会议资料下载信息
     *
     * @param meetingFileDownload
     * @return
     */
    public Result addMeetingFileDownload(MeetingFileDownload meetingFileDownload) {
        List<SubAppMeeting> meeting = subAppMeetingMapper.findSubAppMeeting(new SubAppMeeting(meetingFileDownload.getMid()));
        if (meeting == null || meeting.isEmpty()) {
            return new Result(APIConstants.MEETING_NOT_EXIST);
        }
        User user = userMapper.findUserByUserId(meetingFileDownload.getUser_id());
        if (user == null) {
            return new Result(APIConstants.USER_NOT_EXIST);
        }
        int result = 0;
        try {
            result = meetingFileDownloadMapper.addMeetingFileDownload(meetingFileDownload);
            if (result < 1) {
                return Result.error(Integer.valueOf(-30002), "数据保存失败");
            }
        } catch (Exception e) {
            logger.error(this.logger.getName() + "/addMeetingFileDownload," + e.getMessage());
            return Result.error(Integer.valueOf(-30002), "数据保存失败");
        }
        return Result.success();

    }

    /**
     * 查找下载记录
     *
     * @param meetingFileDownload
     * @return
     */
    public List<MeetingFileDownload> getMeetingFileDownload(MeetingFileDownload meetingFileDownload) {
        return meetingFileDownloadMapper.findMeetingFileDownload(meetingFileDownload);
    }

    //装配会议信息
    public Map<String, Object> assembleMeetingInfo(SubAppMeeting meeting, String rootPath) throws FnfhException {

        Map<String, Object> map = new HashMap<String, Object>();
        //会议标题
        map.put("meet_name", meeting.getMeet_name());
        // 会议id
        map.put("mid", meeting.getMid());
        // 会议起始时间
        map.put("meet_time", meeting.getMeet_time());
        // 会议结束时间
        map.put("end_time", meeting.getEnd_time());
        // 会议地点
        map.put("meet_addr", meeting.getMeet_addr());
        // 参会人员
        map.put("meet_attend", meeting.getMeet_attend());
        //最后更新时间
        map.put("last_update_time", meeting.getLast_update_time());
        // 备注
        map.put("remark", meeting.getRemark());
        //会议状态0表示未结束2表示结束
        map.put("status", meeting.getStatus());

        List<SubAppMeetingFile> meetingFileList = meeting.getFileList();
        if (meetingFileList != null) {
            int fileCount = meetingFileList.size();
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            if (fileCount > 1) {
                //最后一合并的会议文件
                Map<String, Object> lastMergeFileMap = null;
                for (SubAppMeetingFile file : meetingFileList) {
                    //只需要合并的文件
                    if (file.getAttach_type() != 1) {
                        continue;
                    }
                    Map<String, Object> fileMap = new HashMap<String, Object>();
                    // 文件名称
                    fileMap.put("attach_name", file.getAttach_name());
                    // 路径
                    fileMap.put("attach_path", PropertiesUtil.getProperty("back_base_path") + "/" + file.getAttach_path());
                    // 文件类型：0普通附件；1合并附件
                    fileMap.put("attach_type", file.getAttach_type());
                    //最后更新时间
                    fileMap.put("last_update_time", file.getLast_update_time());
                    //回送路径
                    fileMap.put("back_path", PropertiesUtil.getProperty("back_base_path") + "/" + PropertiesUtil.getProperty("nowifi_back_path") + System.currentTimeMillis());
                    //

                    if (lastMergeFileMap != null && file.getAttach_type() == 1) {
                        //如果合并的会议文件不是最新的
                        if (file.getLast_update_time().compareTo(lastMergeFileMap.get("last_update_time").toString()) < 0) {
                            continue;
                        } else {
                            fileList.remove(lastMergeFileMap);
                        }
                    }

                    if (file.getAttach_type() == 1) {
                        lastMergeFileMap = fileMap;
                    }

                    fileList.add(fileMap);
                }
                map.put("fileList", fileList);
            } else if (fileCount == 1)//如果只有一个附件，且附件是pdf，则推送。
            {
                SubAppMeetingFile file = meetingFileList.get(0);

                String sufix = getFileSufix(file.getAttach_name());
                if ("pdf".equalsIgnoreCase(sufix)) {
                    Map<String, Object> fileMap = new HashMap<String, Object>();
                    // 文件名称
                    fileMap.put("attach_name", file.getAttach_name());
                    // 路径
                    fileMap.put("attach_path", PropertiesUtil.getProperty("back_base_path") + "/" + file.getAttach_path());
                    // 文件类型：0普通附件；1合并附件
                    fileMap.put("attach_type", file.getAttach_type());
                    //最后更新时间
                    fileMap.put("last_update_time", file.getLast_update_time());
                    //回送路径
                    fileMap.put("back_path", PropertiesUtil.getProperty("back_base_path") + "/" + PropertiesUtil.getProperty("nowifi_back_path") + System.currentTimeMillis());

                    fileList.add(fileMap);

                    map.put("fileList", fileList);
                }
            } else {
                map.put("fileList", fileList);
            }
        }
        return map;

    }


    //判断会议是否需要推送到设备:如果此设备绑定的人参加了此会议则推送，否则不推送
    private boolean canPushMeetToDevice(MTPFileManager fileManager, PortableDevice device, SubAppMeeting meeting) {
//        MTPFileManager fileManager = new MTPFileManager();
//        fileManager.openDevice(device);
        //设备编码在 \\shenhua\\devicecode.txt中
        PortableDeviceObject pdo;
        try {
            pdo = fileManager.findFile("devicecode.txt", "\\shenhua");
            if (pdo != null) {
                //将devicecode.txt从设备中拷贝到服务器的临时目录下
                String tmpDirPath = System.getProperty("shenhua.root") + "tmp" + File.separator + System.currentTimeMillis();
                File file = new File(tmpDirPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                fileManager.getFile(pdo.getID(), tmpDirPath.replace("\\", "\\\\"));
                //拷贝完成后读取txt中的设备编码
                File deviceCodeFile = new File(tmpDirPath + File.separator + "devicecode.txt");
                String deviceInfoStr = FileUtils.readFileToString(deviceCodeFile, "UTF-8");
                JSONObject deviceInfoJson = JSONObject.fromObject(deviceInfoStr);
                String deviceCode = deviceInfoJson.getString("devicecode");
                //删除临时的文件
                deviceCodeFile.getParentFile().delete();
                FileUtils.deleteDirectory(file);
                //通过设备编码查找人员
                SubAppUser subAppUser = new SubAppUser();
                subAppUser.setIpad_uuid(deviceCode);
                List<SubAppUser> userList = subAppUserMapper.findSubAppUser(subAppUser);
                if (userList != null && !userList.isEmpty()) {
                    SubAppUser user = userList.get(0);
                    if (!StringUtils.isEmpty(meeting.getMeet_attend_ids())) {
                        String[] attenderIds = meeting.getMeet_attend_ids().split(";");
                        return Arrays.asList(attenderIds).contains(user.getUser_id());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("read temp code file error", e);
            return false;
        } catch (COMException e) {
            logger.error("read device code error", e);
            return false;
        } finally {
//            fileManager.closeDevice();
        }
        return false;
    }

    private String getFileSufix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        if (splitIndex < 0) {
            return null;
        }
        return fileName.substring(splitIndex + 1);
    }

    public static void main(String[] args) {
        FileSystemView sys = FileSystemView.getFileSystemView();
        File[] files = File.listRoots();
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i] + " -- " + sys.getSystemTypeDescription(files[i]));
        }

        List<String> ls = new ArrayList<String>();
        ls.add("a");
        ls.add("b");
        System.out.println(ls.toString());
    }

}
