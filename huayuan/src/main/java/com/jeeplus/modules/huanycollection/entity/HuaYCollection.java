/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huanycollection.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.huayuan_copyright.entity.HuayuanCopyright;
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;
import com.jeeplus.modules.yingxiangxinxi.entity.HuaYuanXinXi;

/**
 * 收藏品Entity
 * @author 韩欣益
 * @version 2017-06-27
 */
public class HuaYCollection extends DataEntity<HuaYCollection> {
	
	private static final long serialVersionUID = 1L;
	private String datastatus;		// 数据状态
	private String code;		// 藏品编码
	

	private String registercode;		// 藏品登记号
	private String collectionname;		// 藏品名称
	private String name;		// 原名
	private String classone;		// 一级类别
	private String classtwo;		// 二级类别
	private String classthree;		// 三级类别
	private String createyear;		// 创作年代
	private String characterway;		// 质地
	private String measure;		// 尺寸
	private String author;		// 作者
	private String workway;		// 工艺技法
	private String morphologicalshape;		// 形态形制
	private String method;		// 主题
	private String autograph;		// 题名
	private String inscriptions;		// 款识
	private String preface;		// 题跋
	private String epigraph;		// 铭文
	private String titlepiece;		// 题签
	private String publicseal;		// 印鉴
	private String mass;		// 质量
	private String massnote;		// 质量填写
	private String degreeofresidual;		// 完残程度
	private String degreeofresidualnote;		// 完残状况
	private String savestatus;		// 保存状态
	private Integer num;		// 实际数量
	private String source;		// 来源
	private String incollectiondate;		// 入藏日期
	private HuayuanCopyright copyright;		// 藏品著作权归属
	private List<HuayuanCopyright> copyrightlist;  // 藏品著作权归属列表
	private String copyrights;//存入数据库的藏品著作权归属数据
	private String copyrightsecond;		// 藏品著作权归属第二子项

	private String mark;		// 备注
	private String unitcode;		// 单位代码
	private String inputname;		// 录入人
	private Date inputtime;		// 录入时间
	private String checkname;		// 审核人
	private Date checktime;		// 审核时间
	

	private String backreason;		// 退回原因
	private String collectorganization;		// 收藏单位
	private HuaYuanKuFang kufang;		// 搜索库房
	private HuaYuanXinXi xinxi;		//搜索影像信息
	private String isinkufang;		//是否在库房
	private Date beginIncollectiondate;		// 开始 入藏日期
	private Date endIncollectiondate;		// 结束 入藏日期
	
	private String incollectionDateStr;//入藏日期  生成word用
	
	private String impactFileCount;//影响文件数量
	private String collectionFile;//word文件路径
	private String classtype;//类别
	private String autographContent;//题识和印
	private List<HuanyCollectionAdditive> huanyCollectionAdditiveList = Lists.newArrayList();		// 附加项目
	
	public List<HuanyCollectionAdditive> getHuanyCollectionAdditiveList() {
		return huanyCollectionAdditiveList;
	}

	public void setHuanyCollectionAdditiveList(
			List<HuanyCollectionAdditive> huanyCollectionAdditive) {
		this.huanyCollectionAdditiveList = huanyCollectionAdditive;
	}

	public HuaYCollection() {
		super();
	}

	public HuaYCollection(String id){
		super(id);
	}

	//@ExcelField(title="数据状态", align=2, sort=1)
	public String getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}
	
	@ExcelField(title="藏品编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="藏品登记号", align=2, sort=2)
	public String getRegistercode() {
		return registercode;
	}

	public void setRegistercode(String registercode) {
		this.registercode = registercode;
	}
	
	@ExcelField(title="藏品名称", align=2, sort=3)
	public String getCollectionname() {
		return collectionname;
	}

	public void setCollectionname(String collectionname) {
		this.collectionname = collectionname;
	}
	
	@ExcelField(title="原名", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="一级类别", dictType="", align=2, sort=5)
	public String getClassone() {
		return classone;
	}

	public void setClassone(String classone) {
		this.classone = classone;
	}
	
	@ExcelField(title="二级类别", dictType="", align=2, sort=6)
	public String getClasstwo() {
		return classtwo;
	}

	public void setClasstwo(String classtwo) {
		this.classtwo = classtwo;
	}
	
	@ExcelField(title="三级类别", dictType="", align=2, sort=7)
	public String getClassthree() {
		return classthree;
	}

	public void setClassthree(String classthree) {
		this.classthree = classthree;
	}
	
	@ExcelField(title="创作年代", align=2, sort=8)
	public String getCreateyear() {
		return createyear;
	}

	public void setCreateyear(String createyear) {
		this.createyear = createyear;
	}
	
	@ExcelField(title="质地", align=2, sort=9)
	public String getCharacterway() {
		return characterway;
	}

	public void setCharacterway(String characterway) {
		this.characterway = characterway;
	}
	
	@ExcelField(title="尺寸", align=2, sort=10)
	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
	@ExcelField(title="作者", align=2, sort=11)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@ExcelField(title="工艺技法", align=2, sort=12)
	public String getWorkway() {
		return workway;
	}

	public void setWorkway(String workway) {
		this.workway = workway;
	}
	
	@ExcelField(title="形态形制", align=2, sort=13)
	public String getMorphologicalshape() {
		return morphologicalshape;
	}

	public void setMorphologicalshape(String morphologicalshape) {
		this.morphologicalshape = morphologicalshape;
	}
	
	@ExcelField(title="主题", align=2, sort=14)
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@ExcelField(title="题名", align=2, sort=15)
	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	
	@ExcelField(title="款识", align=2, sort=16)
	public String getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(String inscriptions) {
		this.inscriptions = inscriptions;
	}
	
	@ExcelField(title="题跋", align=2, sort=17)
	public String getPreface() {
		return preface;
	}

	public void setPreface(String preface) {
		this.preface = preface;
	}
	
	@ExcelField(title="铭文", align=2, sort=18)
	public String getEpigraph() {
		return epigraph;
	}

	public void setEpigraph(String epigraph) {
		this.epigraph = epigraph;
	}
	
	@ExcelField(title="题签", align=2, sort=19)
	public String getTitlepiece() {
		return titlepiece;
	}

	public void setTitlepiece(String titlepiece) {
		this.titlepiece = titlepiece;
	}
	
	@ExcelField(title="印鉴", align=2, sort=20)
	public String getPublicseal() {
		return publicseal;
	}

	public void setPublicseal(String publicseal) {
		this.publicseal = publicseal;
	}
	
	@ExcelField(title="质量", align=2, sort=21)
	public String getMass() {
		return mass;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}
	
	@ExcelField(title="质量填写", align=2, sort=22)
	public String getMassnote() {
		return massnote;
	}

	public void setMassnote(String massnote) {
		this.massnote = massnote;
	}
	
	@ExcelField(title="完残程度", dictType="chengdu", align=2, sort=23)
	public String getDegreeofresidual() {
		return degreeofresidual;
	}

	public void setDegreeofresidual(String degreeofresidual) {
		this.degreeofresidual = degreeofresidual;
	}
	
	@ExcelField(title="完残状况", align=2, sort=24)
	public String getDegreeofresidualnote() {
		return degreeofresidualnote;
	}

	public void setDegreeofresidualnote(String degreeofresidualnote) {
		this.degreeofresidualnote = degreeofresidualnote;
	}
	
	@ExcelField(title="保存状态", dictType="saved_state", align=2, sort=25)
	public String getSavestatus() {
		return savestatus;
	}

	public void setSavestatus(String savestatus) {
		this.savestatus = savestatus;
	}
	
	@ExcelField(title="实际数量", align=2, sort=26)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="来源", dictType="source", align=2, sort=27)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="入藏日期", align=2, sort=28)
	public String getIncollectiondate() {
		return incollectiondate;
	}

	public void setIncollectiondate(String incollectiondate) {
		this.incollectiondate = incollectiondate;
	}
	
	
	public HuayuanCopyright getCopyright() {
		return copyright;
	}

	public void setCopyright(HuayuanCopyright copyright) {
		this.copyright = copyright;
	}
	
	public List<HuayuanCopyright> getCopyrightlist() {
		return copyrightlist;
	}

	public void setCopyrightlist(List<HuayuanCopyright> copyrightlist) {
		this.copyrightlist = copyrightlist;
	}
	@ExcelField(title="藏品著作权归属", align=2, sort=29)
	public String getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}
	//@ExcelField(title="藏品著作权归属第二子项", align=2, sort=30)
	public String getCopyrightsecond() {
		return copyrightsecond;
	}

	public void setCopyrightsecond(String copyrightsecond) {
		this.copyrightsecond = copyrightsecond;
	}
	
	@ExcelField(title="备注", align=2, sort=30)
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	//@ExcelField(title="单位代码", align=2, sort=33)
	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	
	//@ExcelField(title="录入人", align=2, sort=34)
	public String getInputname() {
		return inputname;
	}

	public void setInputname(String inputname) {
		this.inputname = inputname;
	}
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ExcelField(title="录入时间", align=2, sort=35)
	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	
	//@ExcelField(title="审核人", align=2, sort=36)
	public String getCheckname() {
		return checkname;
	}

	public void setCheckname(String checkname) {
		this.checkname = checkname;
	}
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ExcelField(title="审核时间", align=2, sort=37)
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}
	
	//@ExcelField(title="退回原因", align=2, sort=38)
	public String getBackreason() {
		return backreason;
	}

	public void setBackreason(String backreason) {
		this.backreason = backreason;
	}
	
	@ExcelField(title="收藏单位", align=2, sort=31)
	public String getCollectorganization() {
		return collectorganization;
	}

	public void setCollectorganization(String collectorganization) {
		this.collectorganization = collectorganization;
	}
	
	@ExcelField(title="库房", align=2, sort=32)
	public String kufangname(){
		return kufang.getName();
	}
	
	public HuaYuanKuFang getKufang() {
		return kufang;
	}

	public void setKufang(HuaYuanKuFang kufang) {
		this.kufang = kufang;
	}
	//@ExcelField(title="是否在库房",  dictType="come_go",align=2, sort=33)
	public String getIsinkufang() {
		return isinkufang;
	}

	public void setIsinkufang(String isinkufang) {
		this.isinkufang = isinkufang;
	}

	public Date getBeginIncollectiondate() {
		return beginIncollectiondate;
	}

	public void setBeginIncollectiondate(Date beginIncollectiondate) {
		this.beginIncollectiondate = beginIncollectiondate;
	}

	public Date getEndIncollectiondate() {
		return endIncollectiondate;
	}

	public void setEndIncollectiondate(Date endIncollectiondate) {
		this.endIncollectiondate = endIncollectiondate;
	}

	public HuaYuanXinXi getXinxi() {
		return xinxi;
	}

	public void setXinxi(HuaYuanXinXi xinxi) {
		this.xinxi = xinxi;
	}

	public String getIncollectionDateStr() {
		return incollectionDateStr;
	}

	public void setIncollectionDateStr(String incollectionDateStr) {
		this.incollectionDateStr = incollectionDateStr;
	}

	public String getImpactFileCount() {
		return impactFileCount;
	}

	public void setImpactFileCount(String impactFileCount) {
		this.impactFileCount = impactFileCount;
	}

	public String getCollectionFile() {
		return collectionFile;
	}

	public void setCollectionFile(String collectionFile) {
		this.collectionFile = collectionFile;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getAutographContent() {
		return autographContent;
	}

	public void setAutographContent(String autographContent) {
		this.autographContent = autographContent;
	}
	
	
}