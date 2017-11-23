/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_copyright.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.huayuan_copyright.entity.HuayuanCopyright;
import com.jeeplus.modules.huayuan_copyright.dao.HuayuanCopyrightDao;

/**
 * 藏品著作权归属Service
 * @author 李学杰
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class HuayuanCopyrightService extends CrudService<HuayuanCopyrightDao, HuayuanCopyright> {

	public HuayuanCopyright get(String id) {
		return super.get(id);
	}
	
	public List<HuayuanCopyright> findList(HuayuanCopyright huayuanCopyright) {
		return super.findList(huayuanCopyright);
	}
	
	public Page<HuayuanCopyright> findPage(Page<HuayuanCopyright> page, HuayuanCopyright huayuanCopyright) {
		return super.findPage(page, huayuanCopyright);
	}
	
	@Transactional(readOnly = false)
	public void save(HuayuanCopyright huayuanCopyright) {
		super.save(huayuanCopyright);
	}
	
	@Transactional(readOnly = false)
	public void delete(HuayuanCopyright huayuanCopyright) {
		super.delete(huayuanCopyright);
	}
	
	
	
	
}