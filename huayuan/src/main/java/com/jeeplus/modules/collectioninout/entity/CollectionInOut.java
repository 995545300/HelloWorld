/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.collectioninout.entity;

import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 收藏品出入登记Entity
 * @author 韩欣益
 * @version 2017-07-01
 */
public class CollectionInOut extends DataEntity<CollectionInOut> {
	
	private static final long serialVersionUID = 1L;
	private HuaYCollection collection;		// 藏品id
	private String name;		// 操作人姓名
	private Date datetime;		// 出入库时间
	private String work;		// 事务
	private Date beginDatetime;		// 开始 出入库时间
	private Date endDatetime;		// 结束 出入库时间
	private String collectionname;//藏品名称
	private String cid;//藏品id串
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCollectionname() {
		return collectionname;
	}

	public void setCollectionname(String collectionname) {
		this.collectionname = collectionname;
	}

	public CollectionInOut() {
		super();
	}

	public CollectionInOut(String id){
		super(id);
	}

	
	@ExcelField(title="藏品名称", align=2, sort=1)
	public String getCollectionnames() {
		return collection.getCollectionname();
	}
	
	public HuaYCollection getCollection() {
		return collection;
	}

	public void setCollection(HuaYCollection collection) {
		this.collection = collection;
	}
	
	@ExcelField(title="操作人姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出入库时间", align=2, sort=3)
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	@ExcelField(title="事务", dictType="inout", align=2, sort=4)
	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}
	
	public Date getBeginDatetime() {
		return beginDatetime;
	}

	public void setBeginDatetime(Date beginDatetime) {
		this.beginDatetime = beginDatetime;
	}
	
	public Date getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
		
}