/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_person_come_go.entity;

import java.util.Date;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 人员出入Entity
 * @author 李学杰
 * @version 2017-06-28
 */
public class HuayuanPersonComeGo extends DataEntity<HuayuanPersonComeGo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String card;		// 身份证
	private String type;		// 类型
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public HuayuanPersonComeGo() {
		super();
	}

	public HuayuanPersonComeGo(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="身份证", align=2, sort=2)
	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
	@ExcelField(title="类型", dictType="come_go", align=2, sort=3)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}