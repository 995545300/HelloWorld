/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuanfenlei.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.huayuanfenlei.entity.HuaYuanFenLei;
import com.jeeplus.modules.huayuanfenlei.service.HuaYuanFenLeiService;

/**
 * 分类Controller
 * @author 郝增杰
 * @version 2017-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/huayuanfenlei/huaYuanFenLei")
public class HuaYuanFenLeiController extends BaseController {

	@Autowired
	private HuaYuanFenLeiService huaYuanFenLeiService;
	
	@ModelAttribute
	public HuaYuanFenLei get(@RequestParam(required=false) String id) {
		HuaYuanFenLei entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = huaYuanFenLeiService.get(id);
		}
		if (entity == null){
			entity = new HuaYuanFenLei();
		}
		return entity;
	}
	
	/**
	 * 分类列表页面
	 */
	@RequiresPermissions("huayuanfenlei:huaYuanFenLei:list")
	@RequestMapping(value = {"list", ""})
	public String list(HuaYuanFenLei huaYuanFenLei, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<HuaYuanFenLei> list = huaYuanFenLeiService.findList(huaYuanFenLei); 
		model.addAttribute("list", list);
		return "modules/huayuanfenlei/huaYuanFenLeiList";
	}

	/**
	 * 查看，增加，编辑分类表单页面
	 */
	@RequiresPermissions(value={"huayuanfenlei:huaYuanFenLei:view","huayuanfenlei:huaYuanFenLei:add","huayuanfenlei:huaYuanFenLei:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HuaYuanFenLei huaYuanFenLei, Model model) {
		if (huaYuanFenLei.getParent()!=null && StringUtils.isNotBlank(huaYuanFenLei.getParent().getId())){
			huaYuanFenLei.setParent(huaYuanFenLeiService.get(huaYuanFenLei.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(huaYuanFenLei.getId())){
				HuaYuanFenLei huaYuanFenLeiChild = new HuaYuanFenLei();
				huaYuanFenLeiChild.setParent(new HuaYuanFenLei(huaYuanFenLei.getParent().getId()));
				List<HuaYuanFenLei> list = huaYuanFenLeiService.findList(huaYuanFenLei); 
				if (list.size() > 0){
					huaYuanFenLei.setSort(list.get(list.size()-1).getSort());
					if (huaYuanFenLei.getSort() != null){
						huaYuanFenLei.setSort(huaYuanFenLei.getSort() + 30);
					}
				}
			}
		}
		if (huaYuanFenLei.getSort() == null){
			huaYuanFenLei.setSort(30);
		}
		model.addAttribute("huaYuanFenLei", huaYuanFenLei);
		return "modules/huayuanfenlei/huaYuanFenLeiForm";
	}

	/**
	 * 保存分类
	 */
	@RequiresPermissions(value={"huayuanfenlei:huaYuanFenLei:add","huayuanfenlei:huaYuanFenLei:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HuaYuanFenLei huaYuanFenLei, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, huaYuanFenLei)){
			return form(huaYuanFenLei, model);
		}
		if(!huaYuanFenLei.getIsNewRecord()){//编辑表单保存
			HuaYuanFenLei t = huaYuanFenLeiService.get(huaYuanFenLei.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(huaYuanFenLei, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			huaYuanFenLeiService.save(t);//保存
		}else{//新增表单保存
			huaYuanFenLeiService.save(huaYuanFenLei);//保存
		}
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/huayuanfenlei/huaYuanFenLei/?repage";
	}
	
	/**
	 * 删除分类
	 */
	@RequiresPermissions("huayuanfenlei:huaYuanFenLei:del")
	@RequestMapping(value = "delete")
	public String delete(HuaYuanFenLei huaYuanFenLei, RedirectAttributes redirectAttributes) {
		huaYuanFenLeiService.delete(huaYuanFenLei);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/huayuanfenlei/huaYuanFenLei/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<HuaYuanFenLei> list = huaYuanFenLeiService.findList(new HuaYuanFenLei());
		for (int i=0; i<list.size(); i++){
			HuaYuanFenLei e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}