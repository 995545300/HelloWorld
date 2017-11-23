/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_kufang.dao;

import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;

/**
 * 库房DAO接口
 * @author 李学杰
 * @version 2017-06-22
 */
@MyBatisDao
public interface HuaYuanKuFangDao extends TreeDao<HuaYuanKuFang> {
	
}