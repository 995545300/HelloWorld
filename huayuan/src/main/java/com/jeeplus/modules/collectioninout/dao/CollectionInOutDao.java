/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.collectioninout.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.collectioninout.entity.CollectionInOut;

/**
 * 收藏品出入登记DAO接口
 * @author 韩欣益
 * @version 2017-07-01
 */
@MyBatisDao
public interface CollectionInOutDao extends CrudDao<CollectionInOut> {

	
}