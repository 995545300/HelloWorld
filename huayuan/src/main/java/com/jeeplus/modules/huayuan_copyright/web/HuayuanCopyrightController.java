/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuan_copyright.web;

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
import com.jeeplus.modules.huayuan_copyright.entity.HuayuanCopyright;
import com.jeeplus.modules.huayuan_copyright.service.HuayuanCopyrightService;

/**
 * 藏品著作权归属Controller
 * @author 李学杰
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/huayuan_copyright/huayuanCopyright")
public class HuayuanCopyrightController extends BaseController {

	@Autowired
	private HuayuanCopyrightService huayuanCopyrightService;
	
	@ModelAttribute
	public HuayuanCopyright get(@RequestParam(required=false) String id) {
		HuayuanCopyright entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = huayuanCopyrightService.get(id);
		}
		if (entity == null){
			entity = new HuayuanCopyright();
		}
		return entity;
	}
	
	/**
	 * 藏品著作权归属列表页面
	 */
	@RequiresPermissions("huayuan_copyright:huayuanCopyright:list")
	@RequestMapping(value = {"list", ""})
	public String list(HuayuanCopyright huayuanCopyright, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HuayuanCopyright> page = huayuanCopyrightService.findPage(new Page<HuayuanCopyright>(request, response), huayuanCopyright); 
		model.addAttribute("page", page);
		return "modules/huayuan_copyright/huayuanCopyrightList";
	}

	/**
	 * 查看，增加，编辑藏品著作权归属表单页面
	 */
	@RequiresPermissions(value={"huayuan_copyright:huayuanCopyright:view","huayuan_copyright:huayuanCopyright:add","huayuan_copyright:huayuanCopyright:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HuayuanCopyright huayuanCopyright, Model model) {
		model.addAttribute("huayuanCopyright", huayuanCopyright);
		return "modules/huayuan_copyright/huayuanCopyrightForm";
	}

	/**
	 * 保存藏品著作权归属
	 */
	@RequiresPermissions(value={"huayuan_copyright:huayuanCopyright:add","huayuan_copyright:huayuanCopyright:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HuayuanCopyright huayuanCopyright, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, huayuanCopyright)){
			return form(huayuanCopyright, model);
		}
		if(!huayuanCopyright.getIsNewRecord()){//编辑表单保存
			HuayuanCopyright t = huayuanCopyrightService.get(huayuanCopyright.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(huayuanCopyright, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			huayuanCopyrightService.save(t);//保存
		}else{//新增表单保存
			huayuanCopyrightService.save(huayuanCopyright);//保存
		}
		addMessage(redirectAttributes, "保存藏品著作权归属成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_copyright/huayuanCopyright/?repage";
	}
	
	/**
	 * 删除藏品著作权归属
	 */
	@RequiresPermissions("huayuan_copyright:huayuanCopyright:del")
	@RequestMapping(value = "delete")
	public String delete(HuayuanCopyright huayuanCopyright, RedirectAttributes redirectAttributes) {
		huayuanCopyrightService.delete(huayuanCopyright);
		addMessage(redirectAttributes, "删除藏品著作权归属成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_copyright/huayuanCopyright/?repage";
	}
	
	/**
	 * 批量删除藏品著作权归属
	 */
	@RequiresPermissions("huayuan_copyright:huayuanCopyright:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			huayuanCopyrightService.delete(huayuanCopyrightService.get(id));
		}
		addMessage(redirectAttributes, "删除藏品著作权归属成功");
		return "redirect:"+Global.getAdminPath()+"/huayuan_copyright/huayuanCopyright/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("huayuan_copyright:huayuanCopyright:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(HuayuanCopyright huayuanCopyright, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "藏品著作权归属"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HuayuanCopyright> page = huayuanCopyrightService.findPage(new Page<HuayuanCopyright>(request, response, -1), huayuanCopyright);
    		new ExportExcel("藏品著作权归属", HuayuanCopyright.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出藏品著作权归属记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuan_copyright/huayuanCopyright/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("huayuan_copyright:huayuanCopyright:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HuayuanCopyright> list = ei.getDataList(HuayuanCopyright.class);
			for (HuayuanCopyright huayuanCopyright : list){
				try{
					huayuanCopyrightService.save(huayuanCopyright);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条藏品著作权归属记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条藏品著作权归属记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入藏品著作权归属失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuan_copyright/huayuanCopyright/?repage";
    }
	
	/**
	 * 下载导入藏品著作权归属数据模板
	 */
	@RequiresPermissions("huayuan_copyright:huayuanCopyright:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "藏品著作权归属数据导入模板.xlsx";
    		List<HuayuanCopyright> list = Lists.newArrayList(); 
    		new ExportExcel("藏品著作权归属数据", HuayuanCopyright.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuan_copyright/huayuanCopyright/?repage";
    }
	
	
	

}