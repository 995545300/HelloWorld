/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuandocument.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.huayuandocument.entity.HuaYuanDocument;
import com.jeeplus.modules.huayuandocument.dao.HuaYuanDocumentDao;

/**
 * 文档资料Service
 * @author 韩欣益
 * @version 2017-07-08
 */
@Service
@Transactional(readOnly = true)
public class HuaYuanDocumentService extends CrudService<HuaYuanDocumentDao, HuaYuanDocument> {

	public HuaYuanDocument get(String id) {
		return super.get(id);
	}
	
	public List<HuaYuanDocument> findList(HuaYuanDocument huaYuanDocument) {
		return super.findList(huaYuanDocument);
	}
	
	public Page<HuaYuanDocument> findPage(Page<HuaYuanDocument> page, HuaYuanDocument huaYuanDocument) {
		return super.findPage(page, huaYuanDocument);
	}
	
	@Transactional(readOnly = false)
	public void save(HuaYuanDocument huaYuanDocument) {
		super.save(huaYuanDocument);
	}
	
	@Transactional(readOnly = false)
	public void delete(HuaYuanDocument huaYuanDocument) {
		super.delete(huaYuanDocument);
	}
	
	
	
	
}