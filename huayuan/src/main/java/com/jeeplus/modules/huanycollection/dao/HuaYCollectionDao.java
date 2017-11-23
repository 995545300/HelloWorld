/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huanycollection.dao;

import com.jeeplus.modules.huayuan_copyright.entity.HuayuanCopyright;
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;

/**
 * 收藏品DAO接口
 * @author 韩欣益
 * @version 2017-06-27
 */
@MyBatisDao
public interface HuaYCollectionDao extends CrudDao<HuaYCollection> {

	public List<HuayuanCopyright> findListBycopyright(HuayuanCopyright copyright);
	public List<HuaYuanKuFang> findListBykufang(HuaYuanKuFang kufang);
	public int getNum();
	public int getNumInKuFang();
	public int getNumOutKuFang();
	public int getNumNoDel();
}