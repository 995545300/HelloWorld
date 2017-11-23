/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_person_come_go.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.huayuan_person_come_go.entity.HuayuanPersonComeGo;
import com.jeeplus.modules.huayuan_person_come_go.service.HuayuanPersonComeGoService;

/**
 * 人员出入Controller
 * @author 李学杰
 * @version 2017-06-28
 */
@Controller
@RequestMapping(value = "${adminPath}/huayuan_person_come_go/huayuanPersonComeGo")
public class HuayuanPersonComeGoController extends BaseController {

	@Autowired
	private HuayuanPersonComeGoService huayuanPersonComeGoService;
	
	@ModelAttribute
	public HuayuanPersonComeGo get(@RequestParam(required=false) String id) {
		HuayuanPersonComeGo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = huayuanPersonComeGoService.get(id);
		}
		if (entity == null){
			entity = new HuayuanPersonComeGo();
		}
		return entity;
	}
	
	/**
	 * 人员出入列表页面
	 */
	@RequiresPermissions("huayuan_person_come_go:huayuanPersonComeGo:list")
	@RequestMapping(value = {"list", ""})
	public String list(HuayuanPersonComeGo huayuanPersonComeGo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HuayuanPersonComeGo> page = huayuanPersonComeGoService.findPage(new Page<HuayuanPersonComeGo>(request, response), huayuanPersonComeGo); 
		model.addAttribute("page", page);
		return "modules/huayuan_person_come_go/huayuanPersonComeGoList";
	}

	/**
	 * 查看，增加，编辑人员出入表单页面
	 */
	@RequiresPermissions(value={"huayuan_person_come_go:huayuanPersonComeGo:view","huayuan_person_come_go:huayuanPersonComeGo:add","huayuan_person_come_go:huayuanPersonComeGo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HuayuanPersonComeGo huayuanPersonComeGo, Model model) {
		model.addAttribute("huayuanPersonComeGo", huayuanPersonComeGo);
		return "modules/huayuan_person_come_go/huayuanPersonComeGoForm";
	}

	/**
	 * 保存人员出入
	 */
	@RequiresPermissions(value={"huayuan_person_come_go:huayuanPersonComeGo:add","huayuan_person_come_go:huayuanPersonComeGo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HuayuanPersonComeGo huayuanPersonComeGo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, huayuanPersonComeGo)){
			return form(huayuanPersonComeGo, model);
		}
		if(!huayuanPersonComeGo.getIsNewRecord()){//编辑表单保存
			HuayuanPersonComeGo t = huayuanPersonComeGoService.get(huayuanPersonComeGo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(huayuanPersonComeGo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			huayuanPersonComeGoService.save(t);//保存
		}else{//新增表单保存
			huayuanPersonComeGoService.save(huayuanPersonComeGo);//保存
		}
		addMessage(redirectAttributes, "保存人员出入成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_person_come_go/huayuanPersonComeGo/?repage";
	}
	
	/**
	 * 删除人员出入
	 */
	@RequiresPermissions("huayuan_person_come_go:huayuanPersonComeGo:del")
	@RequestMapping(value = "delete")
	public String delete(HuayuanPersonComeGo huayuanPersonComeGo, RedirectAttributes redirectAttributes) {
		huayuanPersonComeGoService.delete(huayuanPersonComeGo);
		addMessage(redirectAttributes, "删除人员出入成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_person_come_go/huayuanPersonComeGo/?repage";
	}
	
	/**
	 * 批量删除人员出入
	 */
	@RequiresPermissions("huayuan_person_come_go:huayuanPersonComeGo:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			huayuanPersonComeGoService.delete(huayuanPersonComeGoService.get(id));
		}
		addMessage(redirectAttributes, "删除人员出入成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_person_come_go/huayuanPersonComeGo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("huayuan_person_come_go:huayuanPersonComeGo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(HuayuanPersonComeGo huayuanPersonComeGo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "人员出入"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HuayuanPersonComeGo> page = huayuanPersonComeGoService.findPage(new Page<HuayuanPersonComeGo>(request, response, -1), huayuanPersonComeGo);
    		new ExportExcel("人员出入", HuayuanPersonComeGo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出人员出入记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuan_person_come_go/huayuanPersonComeGo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("huayuan_person_come_go:huayuanPersonComeGo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HuayuanPersonComeGo> list = ei.getDataList(HuayuanPersonComeGo.class);
			for (HuayuanPersonComeGo huayuanPersonComeGo : list){
				try{
					huayuanPersonComeGoService.save(huayuanPersonComeGo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条人员出入记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条人员出入记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入人员出入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuan_person_come_go/huayuanPersonComeGo/?repage";
    }
	
	/**
	 * 下载导入人员出入数据模板
	 */
	@RequiresPermissions("huayuan_person_come_go:huayuanPersonComeGo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "人员出入数据导入模板.xlsx";
    		List<HuayuanPersonComeGo> list = Lists.newArrayList(); 
    		new ExportExcel("人员出入数据", HuayuanPersonComeGo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuan_person_come_go/huayuanPersonComeGo/?repage";
    }
	
	
	

}