/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_person_come_go.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.huayuan_person_come_go.entity.HuayuanPersonComeGo;
import com.jeeplus.modules.huayuan_person_come_go.dao.HuayuanPersonComeGoDao;

/**
 * 人员出入Service
 * @author 李学杰
 * @version 2017-06-28
 */
@Service
@Transactional(readOnly = true)
public class HuayuanPersonComeGoService extends CrudService<HuayuanPersonComeGoDao, HuayuanPersonComeGo> {

	public HuayuanPersonComeGo get(String id) {
		return super.get(id);
	}
	
	public List<HuayuanPersonComeGo> findList(HuayuanPersonComeGo huayuanPersonComeGo) {
		return super.findList(huayuanPersonComeGo);
	}
	
	public Page<HuayuanPersonComeGo> findPage(Page<HuayuanPersonComeGo> page, HuayuanPersonComeGo huayuanPersonComeGo) {
		return super.findPage(page, huayuanPersonComeGo);
	}
	
	@Transactional(readOnly = false)
	public void save(HuayuanPersonComeGo huayuanPersonComeGo) {
		super.save(huayuanPersonComeGo);
	}
	
	@Transactional(readOnly = false)
	public void delete(HuayuanPersonComeGo huayuanPersonComeGo) {
		super.delete(huayuanPersonComeGo);
	}
	
	
	
	
}