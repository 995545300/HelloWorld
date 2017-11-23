/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_copyright.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 藏品著作权归属Entity
 * @author 李学杰
 * @version 2017-06-27
 */
public class HuayuanCopyright extends DataEntity<HuayuanCopyright> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 著作权
	
	public HuayuanCopyright() {
		super();
	}

	public HuayuanCopyright(String id){
		super(id);
	}

	@ExcelField(title="著作权", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}