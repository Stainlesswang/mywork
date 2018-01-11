package com.dv.service.meeting;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import com.dv.constants.SystemConst;
import com.dv.dao.meeting.AuthcodeMapper;
import com.dv.dao.meeting.SubAppUserMapper;
import com.dv.entity.FnfhPageData;
import com.dv.entity.Result;
import com.dv.entity.meeting.Authcode;
import com.dv.util.CommonMethod;
import com.dv.util.FnfhException;
import com.dv.util.Validator;

import net.sf.json.JSONArray;

/**
 * 
 * @classDesc ：
 *	授权码service
 * @creater: 杨群山
 * @creationDate:2017年5月6日 下午2:58:12
 */
@Service
public class AuthcodeService {
	Logger logger = Logger.getLogger(this.getClass()); // 记录日志

	@Resource
	private AuthcodeMapper authcodeMapper;//授权码
	
	@Resource
	private PlatformTransactionManager ptm;
	
	@Resource
	private SubAppUserMapper subAppUserMapper;//设备
	
	/**
	 * 
	 * @methodDesc:  
	 * 查询授权码信息
	 * @param authcode
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 杨群山
	 * @creationDate:2017年5月6日 下午3:00:43
	 */
	public Result findauthcode(Authcode authcode,FnfhPageData pageData)throws FnfhException{
		Result result=new Result();
		int totalCount=0;
		authcode.setStart(pageData.getOffset());
		authcode.setLimit(pageData.getLimit());
		String search=pageData.getSearch();
		String status=pageData.getString("status");
		if(!StringUtils.isEmpty(search))
		{
			authcode.setSearch(search);
		}
		if(!StringUtils.isEmpty(status))
		{
			authcode.setStatus(CommonMethod.StringToInt(status, -1));
		}
		List<Authcode> list=authcodeMapper.findAuthcode(authcode);
		totalCount=authcodeMapper.findAuthcodeCount(authcode);
		result.setRows(list);
		result.setTotal(totalCount);
		return result;	
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 创建授权码
	 * @param authcode
	 * @param pgData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月9日 下午5:15:58
	 */
	public Result addAuthCode(Authcode authcode,FnfhPageData pgData) throws FnfhException{
		int result=0;
		try {
			String prefix=pgData.getString("prefix");//前缀
			String num=pgData.getString("num");//数量
//			int numLength=4;//四位数字，最大9999，最小0001。
			if(StringUtils.isEmpty(prefix))
			{
				return Result.error(SystemConst.NOT_NULL, "授权码前缀不能为空");
			}
			if(StringUtils.isEmpty(num))
			{
				return Result.error(SystemConst.NOT_NULL, "授权码数量不能为空");
			}
			
			if(!Validator.isLetter(prefix))
			{
				return Result.error(SystemConst.DATA_ERROR, "前缀必须是1-16位字母");
			}
			if(num.length()>3)
            {
                return Result.error(SystemConst.NOT_NULL, "请输入1-999的授权码数量");
            }
			
			
			prefix=prefix.toLowerCase();//转换成小写
			int _num=CommonMethod.StringToInt(num, 1);//默认为1
			if(_num>999||_num<=0)
            {
                return Result.error(SystemConst.NOT_NULL, "请输入1-999的授权码数量");
            }
			//从数据库中查询是否有该前缀的存在
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("prefix", prefix);
			List<Authcode> codeList=authcodeMapper.findAuthCodeByPrefix(map);
			
			DecimalFormat df = new DecimalFormat("0000");
			List<Authcode> addList=new ArrayList<Authcode>();//添加的授权码集合
			if(codeList.isEmpty()||StringUtils.isEmpty(codeList.get(0)))
			{
				for(int i=1;i<_num+1;i++)
				{
					Authcode authCode=new Authcode();
					String code=df.format(i);
					authCode.setAuthcode(prefix+code);
					authCode.setPrefix(prefix);
					addList.add(authCode);
				}
				//批量插入
				result=authcodeMapper.batchAddAuthCode(addList);
			}
			else //原来已经存在的前缀
			{
				authcode=codeList.get(0);
				
				String maxCode=authcode.getMaxcode();
				int _maxCode=CommonMethod.StringToInt(maxCode, 0);
				for(int i=_maxCode+1;i<_maxCode+_num+1;i++)
				{
					Authcode authCode=new Authcode();
					String code=df.format(i);
					authCode.setAuthcode(prefix+code);
					authCode.setPrefix(prefix);
					addList.add(authCode);
				}
				//批量插入
				result=authcodeMapper.batchAddAuthCode(addList);
			}
			return new Result(result);//插入的n条数据
			
		} catch (Exception e) {
			logger.error(logger.getName()+"/addauthcode,"+e.getMessage()+","+e.toString());
			if (e.toString().indexOf("Duplicate entry")>=0) {
				throw new FnfhException(SystemConst.DUP_KEY,SystemConst.DUP_KEY_MSG);
			}else {
				throw new FnfhException(SystemConst.SAVE_FAIL,SystemConst.SAVE_FAIL_MSG);
			}
		}	
		
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据授权码集合删除授权码信息
	 * @param pgData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月10日 上午9:27:50
	 */
	public Result delAuthCodeByCodes(FnfhPageData pgData) throws FnfhException{
		int result=0;
		String jsonStr=pgData.getString("codes");//被删除的授权码集合
		if (StringUtils.isEmpty(jsonStr)) {
			return Result.error(SystemConst.NOT_NULL, SystemConst.NOT_NULL_MSG);
		}
		try {
			JSONArray jsonArray2=JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			
			//记录入库
			DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		    TransactionStatus status = ptm.getTransaction(def);
		    try {
				//将绑定的设备接触绑定,根据授权码集合查询
		    	result=subAppUserMapper.clearBindInfoByAuthCodes(idsList);
				result=authcodeMapper.delAuthcodeByCodes(idsList);
				if (result<1) {
					ptm.rollback(status);
					return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
				}
				ptm.commit(status);
			} catch (Exception e) {
				ptm.rollback(status);
				return Result.error(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
			}
		} catch (Exception ex) {
			logger.error(logger.getName()+"/delauthcodeById,"+ex.getMessage()+","+ex.toString());
			if (ex.toString().indexOf("Cannot delete or update a parent row:a foreign key constraint fails") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}else {
				throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 根据id删除授权码信息
	 * @param authcode
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 杨群山
	 * @deprecated 暂未使用
	 * @creationDate:2017年5月8日 下午3:00:43
	 */
	public Result delauthcodeById(String jsonStr) throws FnfhException{
		int result=0;
		if (StringUtils.isEmpty(jsonStr)) {
			return Result.error(SystemConst.NOT_NULL, SystemConst.NOT_NULL_MSG);
		}
		try {
			JSONArray jsonArray2=JSONArray.fromObject(jsonStr);
			List<String> idsList=JSONArray.toList(jsonArray2);
			result=authcodeMapper.delAuthcodeById(idsList);
			if (result<1) {
				return Result.error(SystemConst.DELETE_FAIL, SystemConst.DELETE_FAIL_MSG);
			}
		} catch (Exception ex) {
			logger.error(logger.getName()+"/delauthcodeById,"+ex.getMessage()+","+ex.toString());
			if (ex.toString().indexOf("Cannot delete or update a parent row:a foreign key constraint fails") >= 0) {
				throw new FnfhException(SystemConst.DATA_INUSE,SystemConst.DATA_INUSE_MSG);
			}else {
				throw new FnfhException(SystemConst.DELETE_FAIL,SystemConst.DELETE_FAIL_MSG);
			}
		}
		return Result.success();
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取单个对象
	 * @param authcode
	 * @param pgdata
	 * @return
	 * @throws FnfhException 
	 * @creater: 杨群山
	 * @creationDate:2017年5月8日 下午3:00:43
	 */
	public Result getObjInstane(Authcode authcode)throws FnfhException{
		Result result =new Result();
		if (StringUtils.isEmpty(authcode.getMid())) {
			return Result.error(SystemConst.NO_PARAM, SystemConst.NO_PARAM_MSG);
		}
		authcode.setStart(0);
		authcode.setLimit(1);
		List<Authcode> list=authcodeMapper.findAuthcode(authcode);
		if (list!=null&&list.size()>0) {
			result.setData(list.get(0));
			return result;
		}else{
			return Result.error(SystemConst.QUERY_FAIL, SystemConst.QUERY_FAIL_MSG);
		}
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 查找没有使用的授权码
	 * @param authcode
	 * @param fnfhPageData
	 * @return
	 * @throws FnfhException 
	 * @creater: 李梦婷
	 * @creationDate:2017年5月8日 上午10:31:16
	 */
	public Result findNoUseCode(Authcode authcode,FnfhPageData fnfhPageData)throws FnfhException{
		Result result=new Result();
		authcode.setStatus(0);
		List<Authcode> list=authcodeMapper.findAuthcode(authcode);
		result.setRows(list);
		return result;	
	}
	
	/*
     * 查找授权码的数量
     */
    public int findAuthcodeNum(Authcode authcode)
    {
        return authcodeMapper.findAuthcodeCount(authcode);
    }
}
