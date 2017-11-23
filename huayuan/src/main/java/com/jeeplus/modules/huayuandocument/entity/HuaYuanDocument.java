/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuandocument.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 文档资料Entity
 * @author 韩欣益
 * @version 2017-07-08
 */
public class HuaYuanDocument extends DataEntity<HuaYuanDocument> {
	
	private static final long serialVersionUID = 1L;
	private String documentName;		// 文档名称
	private String url;		// 下载路径
	
	public HuaYuanDocument() {
		super();
	}

	public HuaYuanDocument(String id){
		super(id);
	}

	@ExcelField(title="文档名称", align=2, sort=1)
	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	@ExcelField(title="下载路径", align=2, sort=2)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}