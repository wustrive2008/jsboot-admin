package com.wubaoguo.springboot.core.dao.jdbc;

import java.io.Serializable;

public class BaseCommond implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orderBy;// 排序字段
	private String orderTypeStr;
	private boolean isPager = false;    
	private String searchId;
	private String selector;
	private String orderByClause;
	
	private Pager pager;
	/**
	 * @return the orderByClause
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * @param orderByClause the orderByClause to set
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public BaseCommond() {
		this.pager = new Pager();
	}
	
	public Integer getPageNumber() {
		return pager.getPageNumber();
	}

	public void setPageNumber(Integer pageNumber) {
		pager.setPageNumber(pageNumber);
	}

	public Integer getPageSize() {
		return pager.getPageSize();
	}

	public void setPageSize(Integer pageSize) {
		pager.setPageSize(pageSize);
	}

	public Integer getTotalCount() {
		return pager.getTotalCount();
	}

	public void setTotalCount(Integer totalCount) {
		pager.setTotalCount(totalCount);
	}

	public Integer getPageCount() {
		return pager.getPageCount();
	}

	public void setPageCount(Integer pageCount) {
		pager.setPageCount(pageCount);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderTypeStr() {
		return orderTypeStr;
	}

	public void setOrderTypeStr(String orderTypeStr) {
		this.orderTypeStr = orderTypeStr;
	}

	/**
	 * @return the searchId
	 */
	public String getSearchId() {
		return searchId;
	}

	/**
	 * @param searchId the searchId to set
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	/**
	 * @return the limitStart
	 */
	public int getLimitStart() {
		if(isPager) {
			return pager.getPageStart();
		}
		return -1;
	}

	/**
	 * @return the limitEnd
	 */
	public int getLimitEnd() {
		if(isPager) {
			return pager.getPageSize();
		}
		return -1;
	}

	/**
	 * @return the isPager
	 */
	public boolean isPager() {
		return isPager;
	}

	/**
	 * @param isPager the isPager to set
	 */
	public void setPager(boolean isPager) {
		this.isPager = isPager;
	}

	/**
	 * @return the selector
	 */
	public String getSelector() {
		return selector;
	}

	/**
	 * @param selector the selector to set
	 */
	public void setSelector(String selector) {
		this.selector = selector;
	}
	
}
