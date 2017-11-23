/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_kufang.web;

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
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;
import com.jeeplus.modules.huayuan_kufang.service.HuaYuanKuFangService;

/**
 * 库房Controller
 * @author 李学杰
 * @version 2017-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/huayuan_kufang/huaYuanKuFang")
public class HuaYuanKuFangController extends BaseController {

	@Autowired
	private HuaYuanKuFangService huaYuanKuFangService;
	
	@ModelAttribute
	public HuaYuanKuFang get(@RequestParam(required=false) String id) {
		HuaYuanKuFang entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = huaYuanKuFangService.get(id);
		}
		if (entity == null){
			entity = new HuaYuanKuFang();
		}
		return entity;
	}
	
	/**
	 * 库房管理列表页面
	 */
	@RequiresPermissions("huayuan_kufang:huaYuanKuFang:list")
	@RequestMapping(value = {"list", ""})
	public String list(HuaYuanKuFang huaYuanKuFang, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<HuaYuanKuFang> list = huaYuanKuFangService.findList(huaYuanKuFang); 
		model.addAttribute("list", list);
		return "modules/huayuan_kufang/huaYuanKuFangList";
	}

	/**
	 * 查看，增加，编辑库房管理表单页面
	 */
	@RequiresPermissions(value={"huayuan_kufang:huaYuanKuFang:view","huayuan_kufang:huaYuanKuFang:add","huayuan_kufang:huaYuanKuFang:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HuaYuanKuFang huaYuanKuFang, Model model) {
		if (huaYuanKuFang.getParent()!=null && StringUtils.isNotBlank(huaYuanKuFang.getParent().getId())){
			huaYuanKuFang.setParent(huaYuanKuFangService.get(huaYuanKuFang.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(huaYuanKuFang.getId())){
				HuaYuanKuFang huaYuanKuFangChild = new HuaYuanKuFang();
				huaYuanKuFangChild.setParent(new HuaYuanKuFang(huaYuanKuFang.getParent().getId()));
				List<HuaYuanKuFang> list = huaYuanKuFangService.findList(huaYuanKuFang); 
				if (list.size() > 0){
					huaYuanKuFang.setSort(list.get(list.size()-1).getSort());
					if (huaYuanKuFang.getSort() != null){
						huaYuanKuFang.setSort(huaYuanKuFang.getSort() + 30);
					}
				}
			}
		}
		if (huaYuanKuFang.getSort() == null){
			huaYuanKuFang.setSort(30);
		}
		model.addAttribute("huaYuanKuFang", huaYuanKuFang);
		return "modules/huayuan_kufang/huaYuanKuFangForm";
	}

	/**
	 * 保存库房管理
	 */
	@RequiresPermissions(value={"huayuan_kufang:huaYuanKuFang:add","huayuan_kufang:huaYuanKuFang:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HuaYuanKuFang huaYuanKuFang, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, huaYuanKuFang)){
			return form(huaYuanKuFang, model);
		}
		if(!huaYuanKuFang.getIsNewRecord()){//编辑表单保存
			HuaYuanKuFang t = huaYuanKuFangService.get(huaYuanKuFang.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(huaYuanKuFang, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			huaYuanKuFangService.save(t);//保存
		}else{//新增表单保存
			huaYuanKuFangService.save(huaYuanKuFang);//保存
		}
		addMessage(redirectAttributes, "保存库房管理成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_kufang/huaYuanKuFang/?repage";
	}
	
	/**
	 * 删除库房管理
	 */
	@RequiresPermissions("huayuan_kufang:huaYuanKuFang:del")
	@RequestMapping(value = "delete")
	public String delete(HuaYuanKuFang huaYuanKuFang, RedirectAttributes redirectAttributes) {
		huaYuanKuFangService.delete(huaYuanKuFang);
		addMessage(redirectAttributes, "删除库房管理成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_kufang/huaYuanKuFang/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<HuaYuanKuFang> list = huaYuanKuFangService.findList(new HuaYuanKuFang());
		for (int i=0; i<list.size(); i++){
			HuaYuanKuFang e = list.get(i);
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