/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.collectioninout.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.collectioninout.entity.CollectionInOut;
import com.jeeplus.modules.collectioninout.dao.CollectionInOutDao;

/**
 * 收藏品出入登记Service
 * @author 韩欣益
 * @version 2017-07-01
 */
@Service
@Transactional(readOnly = true)
public class CollectionInOutService extends CrudService<CollectionInOutDao, CollectionInOut> {

	public CollectionInOut get(String id) {
		return super.get(id);
	}
	
	public List<CollectionInOut> findList(CollectionInOut collectionInOut) {
		return super.findList(collectionInOut);
	}
	
	public Page<CollectionInOut> findPage(Page<CollectionInOut> page, CollectionInOut collectionInOut) {
		return super.findPage(page, collectionInOut);
	}
	
	@Transactional(readOnly = false)
	public void save(CollectionInOut collectionInOut) {
		super.save(collectionInOut);
	}
	
	@Transactional(readOnly = false)
	public void delete(CollectionInOut collectionInOut) {
		super.delete(collectionInOut);
	}
	
	
	
	
}