/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huanycollection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.huayuan_copyright.entity.HuayuanCopyright;
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import com.jeeplus.modules.huanycollection.entity.HuanyCollectionAdditive;
import com.jeeplus.modules.huanycollection.dao.HuaYCollectionDao;
import com.jeeplus.modules.huanycollection.dao.HuanyCollectionAdditiveDao;

/**
 * 收藏品Service
 * @author 韩欣益
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class HuaYCollectionService extends CrudService<HuaYCollectionDao, HuaYCollection> {
	
	@Autowired
	private HuanyCollectionAdditiveDao huanyCollectionAdditiveDao;
	
	public HuaYCollection get(String id) {
		HuaYCollection huaYCollection = super.get(id);
		System.out.println(huaYCollection);
		huaYCollection.setHuanyCollectionAdditiveList(huanyCollectionAdditiveDao.findList(new HuanyCollectionAdditive(huaYCollection)));
		System.out.println(huaYCollection.getHuanyCollectionAdditiveList()+"555555555555555555555555555555555获取list");
		return huaYCollection;
	}
	public int getNum(){
		return dao.getNum();
	}
	public int getNumInKuFang(){
		return dao.getNumInKuFang();
	}
	public int getNumOutKuFang(){
		return dao.getNumOutKuFang();
	}
	public int getNumNoDel(){
		return dao.getNumNoDel();
	}
	public List<HuaYCollection> findList(HuaYCollection huaYCollection) {
		return super.findList(huaYCollection);
	}
	
	public Page<HuaYCollection> findPage(Page<HuaYCollection> page, HuaYCollection huaYCollection) {
		return super.findPage(page, huaYCollection);
	}
	
	@Transactional(readOnly = false)
	public void save(HuaYCollection huaYCollection) {
		super.save(huaYCollection);
		for (HuanyCollectionAdditive huanyCollectionAdditive : huaYCollection.getHuanyCollectionAdditiveList()){
			if (huanyCollectionAdditive.getId() == null){
				continue;
			}
			if (HuanyCollectionAdditive.DEL_FLAG_NORMAL.equals(huanyCollectionAdditive.getDelFlag())){
				if (StringUtils.isBlank(huanyCollectionAdditive.getId())){
					huanyCollectionAdditive.setHuaYCollection(huaYCollection);
					huanyCollectionAdditive.preInsert();
					huanyCollectionAdditiveDao.insert(huanyCollectionAdditive);
				}else{
					huanyCollectionAdditive.preUpdate();
					huanyCollectionAdditiveDao.update(huanyCollectionAdditive);
				}
			}else{
				huanyCollectionAdditiveDao.delete(huanyCollectionAdditive);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(HuaYCollection huaYCollection) {
		super.delete(huaYCollection);
		huanyCollectionAdditiveDao.delete(new HuanyCollectionAdditive(huaYCollection));
	}
	@Transactional(readOnly = false)
	public void deleteByLogic(HuaYCollection huaYCollection) {
		// TODO Auto-generated method stub
		super.deleteByLogic(huaYCollection);
	}
	
	public Page<HuayuanCopyright> findPageBycopyright(Page<HuayuanCopyright> page, HuayuanCopyright copyright) {
		copyright.setPage(page);
		page.setList(dao.findListBycopyright(copyright));
		return page;
	}
	public Page<HuaYuanKuFang> findPageBykufang(Page<HuaYuanKuFang> page, HuaYuanKuFang kufang) {
		kufang.setPage(page);
		page.setList(dao.findListBykufang(kufang));
		return page;
	}
	
	
	
}