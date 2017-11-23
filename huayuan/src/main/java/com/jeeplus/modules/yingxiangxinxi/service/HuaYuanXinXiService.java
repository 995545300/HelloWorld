/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yingxiangxinxi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import com.jeeplus.modules.yingxiangxinxi.entity.HuaYuanXinXi;
import com.jeeplus.modules.yingxiangxinxi.dao.HuaYuanXinXiDao;

/**
 * 藏品影像信息Service
 * @author 韩欣益
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class HuaYuanXinXiService extends CrudService<HuaYuanXinXiDao, HuaYuanXinXi> {

	public HuaYuanXinXi get(String id) {
		return super.get(id);
	}
	
	public List<HuaYuanXinXi> findList(HuaYuanXinXi huaYuanXinXi) {
		return super.findList(huaYuanXinXi);
	}
	public List<HuaYuanXinXi> findList(HuaYuanXinXi huaYuanXinXi,HuaYCollection huaYCollection) {
		List<HuaYuanXinXi> list =super.findList(huaYuanXinXi);
		List<HuaYuanXinXi> listbycid=null;
		for(int i = 0 ; i < list.size() ; i++) {
			HuaYuanXinXi huaYuanXinXi2=list.get(i);
			  if(huaYuanXinXi2.getCollectionid()==huaYCollection.getId()){
				  listbycid.add(list.get(i));
			  }
			}
		return listbycid;
	}
	
	public Page<HuaYuanXinXi> findPage(Page<HuaYuanXinXi> page, HuaYuanXinXi huaYuanXinXi) {
		return super.findPage(page, huaYuanXinXi);
	}
	
	
	@Transactional(readOnly = false)
	public void save(HuaYuanXinXi huaYuanXinXi) {
		super.save(huaYuanXinXi);
	}
	
	@Transactional(readOnly = false)
	public void delete(HuaYuanXinXi huaYuanXinXi) {
		super.delete(huaYuanXinXi);
	}
	
	public int findCountByCollectionId(HuaYuanXinXi huaYuanXinXi){
		return dao.findCountByCollectionId(huaYuanXinXi);
		
	}

	public String getImgurl(String id) {
		return dao.getImgurl(id);
	}

	public String getXinxinum(String id) {
		return dao.getXinxinum(id);
	}
	
	
	
	
}