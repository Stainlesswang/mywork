package com.dv.controller;

import javax.servlet.http.HttpSession;

import com.dv.constants.SystemConst;
import com.dv.entity.user.User;
import com.dv.util.FnfhException;

public class FnfhBaseController {
	
	/**
	 * 
	 * @methodDesc:  
	 * 校验管理员
	 * @param session
	 * @throws ParkException
	 * @return  
	 * @creater: 李梦婷
	 * @creationDate:2017年3月10日 上午9:52:02
	 */
	public void validateAdminAction(HttpSession session) throws FnfhException
	{
		Object obj=session.getAttribute(SystemConst.LOGIN_USER);
		if(obj!=null&&obj instanceof User)
		{
			return;
		}
		else
		{
			if(obj==null)
			{
				throw new FnfhException(SystemConst.OFFLINE,SystemConst.OFFLINE_MSG);
			}
			else
			{
				throw new FnfhException(SystemConst.RESTRICTED_ERROR,SystemConst.RESTRICTED_ERROR_MSG);
			}
			
		}
	}
	
}
