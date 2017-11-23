/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuanfenlei.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.huayuanfenlei.entity.HuaYuanFenLei;
import com.jeeplus.modules.huayuanfenlei.dao.HuaYuanFenLeiDao;

/**
 * 分类Service
 * @author 郝增杰
 * @version 2017-06-22
 */
@Service
@Transactional(readOnly = true)
public class HuaYuanFenLeiService extends TreeService<HuaYuanFenLeiDao, HuaYuanFenLei> {

	public HuaYuanFenLei get(String id) {
		return super.get(id);
	}
	
	public List<HuaYuanFenLei> findList(HuaYuanFenLei huaYuanFenLei) {
		if (StringUtils.isNotBlank(huaYuanFenLei.getParentIds())){
			huaYuanFenLei.setParentIds(","+huaYuanFenLei.getParentIds()+",");
		}
		return super.findList(huaYuanFenLei);
	}
	
	@Transactional(readOnly = false)
	public void save(HuaYuanFenLei huaYuanFenLei) {
		super.save(huaYuanFenLei);
	}
	
	@Transactional(readOnly = false)
	public void delete(HuaYuanFenLei huaYuanFenLei) {
		super.delete(huaYuanFenLei);
	}

	public List<HuaYuanFenLei> getclasslist(String parentid) {
		return dao.getclasslist(parentid);
	}


	public HuaYuanFenLei getByName(String name) {
		return dao.getByName(name);
	}

	
}