/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_kufang.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;
import com.jeeplus.modules.huayuan_kufang.dao.HuaYuanKuFangDao;

/**
 * 库房Service
 * @author 李学杰
 * @version 2017-06-22
 */
@Service
@Transactional(readOnly = true)
public class HuaYuanKuFangService extends TreeService<HuaYuanKuFangDao, HuaYuanKuFang> {

	public HuaYuanKuFang get(String id) {
		return super.get(id);
	}
	
	public List<HuaYuanKuFang> findList(HuaYuanKuFang huaYuanKuFang) {
		if (StringUtils.isNotBlank(huaYuanKuFang.getParentIds())){
			huaYuanKuFang.setParentIds(","+huaYuanKuFang.getParentIds()+",");
		}
		return super.findList(huaYuanKuFang);
	}
	
	@Transactional(readOnly = false)
	public void save(HuaYuanKuFang huaYuanKuFang) {
		super.save(huaYuanKuFang);
	}
	
	@Transactional(readOnly = false)
	public void delete(HuaYuanKuFang huaYuanKuFang) {
		super.delete(huaYuanKuFang);
	}
	
}