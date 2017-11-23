/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yingxiangxinxi.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.yingxiangxinxi.entity.HuaYuanXinXi;

/**
 * 藏品影像信息DAO接口
 * @author 韩欣益
 * @version 2017-06-26
 */
@MyBatisDao
public interface HuaYuanXinXiDao extends CrudDao<HuaYuanXinXi> {

	int findCountByCollectionId(HuaYuanXinXi huaYuanXinXi);

	String getImgurl(String id);

	String getXinxinum(String id);
	
	
	
}