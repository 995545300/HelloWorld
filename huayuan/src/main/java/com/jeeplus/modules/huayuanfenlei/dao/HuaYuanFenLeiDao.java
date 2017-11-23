/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuanfenlei.dao;

import java.util.List;

import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.huayuanfenlei.entity.HuaYuanFenLei;

/**
 * 分类DAO接口
 * @author 郝增杰
 * @version 2017-06-22
 */
@MyBatisDao
public interface HuaYuanFenLeiDao extends TreeDao<HuaYuanFenLei> {

	List<HuaYuanFenLei> getclasslist(String parentid);

	HuaYuanFenLei getByName(String name);//根据名字查询类目
	
}