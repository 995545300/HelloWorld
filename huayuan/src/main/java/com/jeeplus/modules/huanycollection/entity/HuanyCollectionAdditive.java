/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huanycollection.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 藏品附加信息Entity
 * @author 韩欣益
 * @version 2017-07-11
 */
public class HuanyCollectionAdditive extends DataEntity<HuanyCollectionAdditive> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 标题
	private String text;		// 内容
	
	private HuaYCollection huaYCollection;	// 业务主表id 父类
	public HuaYCollection getHuaYCollection() {
		return huaYCollection;
	}

	public void setHuaYCollection(HuaYCollection huaYCollection) {
		this.huaYCollection = huaYCollection;
	}

	public HuanyCollectionAdditive() {
		super();
	}

	public HuanyCollectionAdditive(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HuanyCollectionAdditive(HuaYCollection huaYCollection) {
		super();
		this.huaYCollection = huaYCollection;
	}
	
}