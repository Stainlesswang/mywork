package com.dv.entity;

import java.io.Serializable;
import java.util.List;

import com.dv.constants.APIConstants;
import com.dv.constants.SystemConst;

/**
 * 
 * @classDesc ：
 *	用于处理各种请求的返回结果
 * @creater: 李梦婷
 * @creationDate:2016年8月17日 上午11:01:37
 */
public class Result  implements Serializable {

	private static final long serialVersionUID = 7250094751892166542L;

	/**
	 * 状态 0：成功  其他 失败
	 */
	private int status;

	/**
	 * 提示信息
	 */
	private String message;

	/**
	 * 相关数据
	 */
	private Object data;
	
	//以下供bootstrap使用
	private Integer total;
	/**
	 * 当返回结果包含单个表，或者多个明细表的第一个明细表，通过此成员返回
	 * BootstrapTable会用到这个返回值
	 */
	private List<?> rows;
	

	public Result() {
		super();
	}

	public Result(Object data) {
		this.data = data;
	}
	
	public Result(APIConstants constants)
	{
	      this.status = constants.getCode();
	      this.message = constants.getName();
	}

	public Result(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public static Result success() {
    	return new Result(SystemConst.SYS_SUCCESS,SystemConst.SYS_SUCCESS_MSG);
    }
	
	public static Result error(Integer errCode, String errInfo) {
    	return new Result(errCode, errInfo);
    }
    
    public static Result errorUnknow() {
    	return new Result(SystemConst.SYS_ERROR,SystemConst.SYS_ERROR_MSG);
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	
}
