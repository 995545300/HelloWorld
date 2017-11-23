/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_kufang.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.TreeEntity;

/**
 * 库房Entity
 * @author 李学杰
 * @version 2017-06-22
 */
public class HuaYuanKuFang extends TreeEntity<HuaYuanKuFang> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 库房名字
	private String liexin;		// 类型
	private String number;		// 库房编号
	private HuaYuanKuFang parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private Integer sort;		// 排序
	
	public HuaYuanKuFang() {
		super();
	}

	public HuaYuanKuFang(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLiexin() {
		return liexin;
	}

	public void setLiexin(String liexin) {
		this.liexin = liexin;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@JsonBackReference
	public HuaYuanKuFang getParent() {
		return parent;
	}

	public void setParent(HuaYuanKuFang parent) {
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