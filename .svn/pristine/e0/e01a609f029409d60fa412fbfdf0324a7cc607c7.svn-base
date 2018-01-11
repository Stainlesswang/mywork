package com.dv.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前后台交互的返回值类型
 * json
  {
    code:"0执行成功，<小于0的错误代码>执行不成功",
    info:"提示信息",
    datas: {"键":"值", "键":"值", ......}， / *一般用于返回简单数据，如果需要传输多个表数据是，在此传输* /
    total: "BootstrapTable分页使用的总数据行数",
    rows:[
          {datas:{"字段":"值", "字段":"值", ......}},
          {datas:{"字段":"值", "字段":"值", ......}},
          ......
    ] / *BootstrapTable使用的数据表，其他ajax调用只返回一个表是也通过这个对象返回* /
  }
 * @author zsuny
 */
public class FnfhResult {
	/**
	 * 接口调用的执行结果
	 * 0：表示接口执行成功
	 * 小于0：表示接口返回了执行不成功，数值为错误代码
	 */ 
	private Integer code;
	
	/**
	 * 接口执行时返回的提示信息，一般当code小于0时，会将错误信息通过此成员返回
	 */
	private String info;
	
	/**
	 * 接口返回的键值对数据
	 * 一般是临时性数据，例如{BillNo:"单据号"}
	 * 也可以传入对象，例如{object:{....}}
	 * 如果要传递多张表的数据，可以将List<?>同过此成员返回，接口约定表数据的key
	 */
	private Map<String, Object> datas;
	
	/**
	 * 当使用BootstrapTable的分页功能时，需要通过此成员返回总记录数
	 * 其他接口忽略此成员
	 */
    private Integer total;
    /**
     * 当返回结果包含单个表，或者多个明细表的第一个明细表，通过此成员返回
     * BootstrapTable会用到这个返回值
     */
    private List<?> rows;

	public FnfhResult() {
		this.code = 0;
	}
	
	public FnfhResult(Integer code) {
		this.code = code;
	}
	
	public FnfhResult(Integer code, String info) {
		this.code = code;
		this.info = info;
	}
	
    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
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
	
	public FnfhResult addData(String key, Object value) {
		if (null == datas)
			datas = new HashMap<String, Object>();
		datas.put(key, value);
		return this;
	}
	
	public Object getData(String key) {
		if (null == datas)
			return null;
		return datas.get(key);
	}

//	@Override
//    public String toString() {
//    	return JSONObject.toJSONString(this);
//	}
    
    public static FnfhResult success() {
    	return new FnfhResult(0);
    }
    
    public static FnfhResult error(Integer errCode, String errInfo) {
    	return new FnfhResult(errCode, errInfo);
    }
    
    public static FnfhResult errorUnknow() {
    	return new FnfhResult(-1, "服务处理不成功。");
    }
}
