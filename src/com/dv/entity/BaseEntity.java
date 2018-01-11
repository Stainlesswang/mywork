package com.dv.entity;

import java.io.Serializable;

public class BaseEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2857766292047550514L;
	private int start;
	private int limit;
	private String url;//路径
	
	private String sort;//排序
	private String order;//排序
	
	private String sortColumn;//排序字段
	private String symbol;//符号 <、>、=
	
	private String search;//搜索
	
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
}
