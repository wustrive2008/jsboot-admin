package com.wubaoguo.springboot.manage.domain;

/**
 *
 * Description: 
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/21 9:01
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
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
