package com.dv.service.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;
import com.dv.dao.meeting.SubAppMeetingFileMapper;
import com.dv.dao.meeting.SubAppMeetingMapper;
import com.dv.dao.user.UserMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.SubAppMeeting;
import com.dv.entity.meeting.SubAppMeetingFile;
import com.dv.entity.user.User;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;

import net.sf.json.JSONArray;
 

/**
 * 
 * @classDesc ：
 *	会议附件service
 * @creater: 杨群山
 * @creationDate:2017年5月3日 上午10:51:45
 */
@Service
public class SubAppMeetingFileService {
	@Resource
	private SubAppMeetingFileMapper subAppMeetingFileMapper;
	
	@ Resource
    private SubAppMeetingMapper subAppMeetingMapper;
	
	@ Resource
	private UserMapper userMapper;
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志
	/**
	 * 
	 * @methodDesc:  
	 * 查询会议附件信息
	 * @param subAppMeetingFile
	 * @param pgdata
	 * @return
	 * @throws FnfhException
	 * @return  
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 上午10:53:51
	 */
	public Result findsubAppMeetingFile(SubAppMeetingFile subAppMeetingFile,FnfhPageData fnfhPageData) throws FnfhException{
		Result result=new Result();
		int totalCount=0;
		
		String attach_type = fnfhPageData.getString("attach_type");
		if (!StringUtils.isEmpty(attach_type))
		{
		    subAppMeetingFile.setAttach_type(Integer.valueOf(attach_type));
		}
		
		String meeting_mid = fnfhPageData.getString("meeting_mid");
		if (!StringUtils.isEmpty(meeting_mid))
		{
		    subAppMeetingFile.setMeeting_mid(meeting_mid);
		}
		String order=fnfhPageData.getOrder();
        String sort=fnfhPageData.getSort();
        
        if (!StringUtils.isEmpty(order)) {
            subAppMeetingFile.setOrder(order);
        }
        if (!StringUtils.isEmpty(sort)) {
            subAppMeetingFile.setSort(sort);
        }
		
		subAppMeetingFile.setStart(fnfhPageData.getOffset());
		subAppMeetingFile.setLimit(fnfhPageData.getLimit());
		List<SubAppMeetingFile> list=subAppMeetingFileMapper.findSubAppMeetingFile(subAppMeetingFile);
		totalCount=subAppMeetingFileMapper.findSubAppMeetingFileCount(subAppMeetingFile);
		result.setRows(list);
		result.setTotal(totalCount);
		return result;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 添加附件信息
	 * @param subAppMeetingFile
	 * @return
	 * @throws FnfhException
	 * @return  
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 上午10:56:05
	 */
	public Result addsubAppMeetingFile(SubAppMeetingFile subAppMeetingFile) throws FnfhException
    {
        int result = 0;
        if (subAppMeetingFile.getMeeting_mid() != null)
        {
            int count = subAppMeetingMapper
                .findSubAppMeetingCount(new SubAppMeeting(subAppMeetingFile.getMeeting_mid()));
            if (count < 1)
            {
                throw new FnfhException(APIConstants.MEETING_NOT_EXIST.getCode(),
                    APIConstants.MEETING_NOT_EXIST.getName());
            }
            User uploader = userMapper.findUserByUserId(subAppMeetingFile.getUploader_id());
            if (uploader == null)
            {
                throw new FnfhException(APIConstants.USER_NOT_EXIST.getCode(), APIConstants.USER_NOT_EXIST.getName());
            }
            try
            {
                result = this.subAppMeetingFileMapper.addSubAppMeetingFile(subAppMeetingFile);
            }
            catch(Exception ex)
            {
                this.logger.error(this.logger.getName() + "/addsubAppMeetingFile," + ex.getMessage()
                    + "," + ex.toString());
                if (ex.toString().indexOf("Duplicate entry") >= 0)
                {
                    throw new FnfhException(-30001, "存在重复的值");
                }
                throw new FnfhException(-30002, "数据保存失败");
            }
        }
        else
        {
            throw new FnfhException(APIConstants.MEETING_ID_NULL.getCode(),
                APIConstants.MEETING_ID_NULL.getName());
        }
        if (result < 1)
        {
            return Result.error(Integer.valueOf(-30002), "数据保存失败");
        }

        return Result.success();
    }
	/**
	 * 
	 * @methodDesc:  
	 * 根据id集合删除附件信息
	 * @param jsonStr
	 * @return
	 * @throws FnfhException
	 * @return  
	 * @creater: 杨群山
	 * @creationDate:2017年5月3日 下午4:02:06
	 */
	public Result delsubAppMeetingFile(String jsonStr) throws FnfhException{
		int result=0;
		if (StringUtils.isEmpty(jsonStr)) {
			return Result.error(SystemConst.NOT_NULL, SystemConst.NOT_NULL_MSG);
		}
		try {
			JSONArray jsonArray2=JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			result=subAppMeetingFileMapper.delSubAppMeetingFileByIds(idsList);
			if (result<1) {
				return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
			}
		} catch (Exception ex) {
			logger.error(logger.getName()+"/delsubAppMeetingFile,"+ex.getMessage()+","+ex.toString());
			if(ex.toString().indexOf("Cannot delete or update a parent row: a foreign key constraint fails ") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}
			else
			{
				throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	
	/**
     * 
     * @methodDesc:  
     * 添加或者更新附件信息
     * @param subAppMeetingFile
     * @return
     * @throws FnfhException
     * @return  
     * @creater: 杨群山
     * @creationDate:2017年5月3日 上午10:56:05
     */
    public Result addOrModsubAppMeetingFile(SubAppMeetingFile subAppMeetingFile) throws FnfhException
    {
        int result = 0;
        if (subAppMeetingFile.getMeeting_mid() != null)
        {
            int count = subAppMeetingMapper
                .findSubAppMeetingCount(new SubAppMeeting(subAppMeetingFile.getMeeting_mid()));
            if (count < 1)
            {
                throw new FnfhException(APIConstants.MEETING_NOT_EXIST.getCode(),
                    APIConstants.MEETING_NOT_EXIST.getName());
            }
            User uploader = userMapper.findUserByUserId(subAppMeetingFile.getUploader_id());
            if (uploader == null)
            {
                throw new FnfhException(APIConstants.USER_NOT_EXIST.getCode(), APIConstants.USER_NOT_EXIST.getName());
            }
            try
            {
                if (subAppMeetingFile.getFile_mid() == null)
                {
                    subAppMeetingFile.setFile_mid(CommonMethod.getUID());
                    //添加
                    result = this.subAppMeetingFileMapper.addSubAppMeetingFile(subAppMeetingFile);
                }
                else
                {
                    //更新
                    result = this.subAppMeetingFileMapper.modSubAppMeetingFileById(subAppMeetingFile);
                }
            }
            catch(Exception ex)
            {
                this.logger.error(this.logger.getName() + "/addsubAppMeetingFile," + ex.getMessage()
                    + "," + ex.toString());
                if (ex.toString().indexOf("Duplicate entry") >= 0)
                {
                    throw new FnfhException(-30001, "存在重复的值");
                }
                throw new FnfhException(-30002, "数据保存失败");
            }
        }
        else
        {
            throw new FnfhException(APIConstants.MEETING_ID_NULL.getCode(),
                APIConstants.MEETING_ID_NULL.getName());
        }
        if (result < 1)
        {
            return Result.error(Integer.valueOf(-30002), "数据保存失败");
        }

        return Result.success();
    }
    
    
    public List<SubAppMeetingFile> getSubAppMeetingFile(SubAppMeetingFile subAppMeetingFile)
    {
        return subAppMeetingFileMapper.findSubAppMeetingFile(subAppMeetingFile);
    }
    
    //更新会议文件
    public void updateSubAppMeetingFile(SubAppMeetingFile subAppMeetingFile)
    {
        subAppMeetingFileMapper.modSubAppMeetingFileById(subAppMeetingFile);
    }
 
}
