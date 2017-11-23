/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yingxiangxinxi.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 藏品影像信息Entity
 * @author 韩欣益
 * @version 2017-06-26
 */
public class HuaYuanXinXi extends DataEntity<HuaYuanXinXi> {
	
	private static final long serialVersionUID = 1L;
	private String collectionid;		// 藏品编号
	private String name;		// 藏品影像文件名
	private String smallimg;	//藏品影像小图
	private String paishejiaodu;		// 拍摄角度
	private String guige;		// 规格
	private String zhizuoren;		// 制作人
	
	public HuaYuanXinXi() {
		super();
	}

	public HuaYuanXinXi(String id){
		super(id);
	}

	@ExcelField(title="藏品编号", align=2, sort=1)
	public String getCollectionid() {
		return collectionid;
	}

	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}
	
	@ExcelField(title="藏品影像文件名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="拍摄角度", dictType="jiaodu", align=2, sort=3)
	public String getPaishejiaodu() {
		return paishejiaodu;
	}

	public void setPaishejiaodu(String paishejiaodu) {
		this.paishejiaodu = paishejiaodu;
	}
	
	@ExcelField(title="规格", align=2, sort=4)
	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}
	
	@ExcelField(title="制作人", align=2, sort=5)
	public String getZhizuoren() {
		return zhizuoren;
	}

	public void setZhizuoren(String zhizuoren) {
		this.zhizuoren = zhizuoren;
	}

	public String getSmallimg() {
		return smallimg;
	}

	public void setSmallimg(String smallimg) {
		this.smallimg = smallimg;
	}
	
}