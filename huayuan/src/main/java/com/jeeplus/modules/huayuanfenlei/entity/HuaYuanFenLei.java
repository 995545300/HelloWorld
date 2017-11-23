/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuanfenlei.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.TreeEntity;

/**
 * 分类Entity
 * @author 郝增杰
 * @version 2017-06-22
 */
public class HuaYuanFenLei extends TreeEntity<HuaYuanFenLei> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名字
	private String daimahao;		// 代码
	private String jibie;		// 级别
	private String beizhu;		// 备注
	private HuaYuanFenLei parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private Integer sort;		// 排序
	
	public HuaYuanFenLei() {
		super();
	}

	public HuaYuanFenLei(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDaimahao() {
		return daimahao;
	}

	public void setDaimahao(String daimahao) {
		this.daimahao = daimahao;
	}
	
	public String getJibie() {
		return jibie;
	}

	public void setJibie(String jibie) {
		this.jibie = jibie;
	}
	
	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	
	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public HuaYuanFenLei getParent() {
		return parent;
	}

	public void setParent(HuaYuanFenLei parent) {
		this.parent = parent;
	}
	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}