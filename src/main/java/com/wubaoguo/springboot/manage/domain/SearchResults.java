package com.wubaoguo.springboot.manage.domain;
/**
 * @Title: SearchResults.java
 * @ClassName: com.vcxx.manage.vo.SearchResults
 * @Description: 搜索结果对象
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author zhaoqt
 * @date May 3, 2016 5:06:01 AM
 */
public class SearchResults {

	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 连接
	 */
	private String link;
	
	/**
	 * 描述文字
	 */
	private String details;

	
	public SearchResults() {}
	
	
	public SearchResults(String title, String link, String details) {
		super();
		this.title = title;
		this.link = link;
		this.details = details;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
