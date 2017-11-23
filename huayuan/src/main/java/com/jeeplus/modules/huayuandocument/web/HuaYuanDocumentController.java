/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huayuandocument.web;

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
import com.jeeplus.modules.huayuandocument.entity.HuaYuanDocument;
import com.jeeplus.modules.huayuandocument.service.HuaYuanDocumentService;

/**
 * 文档资料Controller
 * @author 韩欣益
 * @version 2017-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/huayuandocument/huaYuanDocument")
public class HuaYuanDocumentController extends BaseController {

	@Autowired
	private HuaYuanDocumentService huaYuanDocumentService;
	
	@ModelAttribute
	public HuaYuanDocument get(@RequestParam(required=false) String id) {
		HuaYuanDocument entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = huaYuanDocumentService.get(id);
		}
		if (entity == null){
			entity = new HuaYuanDocument();
		}
		return entity;
	}
	
	/**
	 * 文档资料列表页面
	 */
	@RequiresPermissions("huayuandocument:huaYuanDocument:list")
	@RequestMapping(value = {"list", ""})
	public String list(HuaYuanDocument huaYuanDocument, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HuaYuanDocument> page = huaYuanDocumentService.findPage(new Page<HuaYuanDocument>(request, response), huaYuanDocument); 
		model.addAttribute("page", page);
		return "modules/huayuandocument/huaYuanDocumentList";
	}

	/**
	 * 查看，增加，编辑文档资料表单页面
	 */
	@RequiresPermissions(value={"huayuandocument:huaYuanDocument:view","huayuandocument:huaYuanDocument:add","huayuandocument:huaYuanDocument:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HuaYuanDocument huaYuanDocument, Model model) {
		model.addAttribute("huaYuanDocument", huaYuanDocument);
		return "modules/huayuandocument/huaYuanDocumentForm";
	}

	/**
	 * 保存文档资料
	 */
	@RequiresPermissions(value={"huayuandocument:huaYuanDocument:add","huayuandocument:huaYuanDocument:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HuaYuanDocument huaYuanDocument, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, huaYuanDocument)){
			return form(huaYuanDocument, model);
		}
		if(!huaYuanDocument.getIsNewRecord()){//编辑表单保存
			HuaYuanDocument t = huaYuanDocumentService.get(huaYuanDocument.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(huaYuanDocument, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			huaYuanDocumentService.save(t);//保存
		}else{//新增表单保存
			huaYuanDocumentService.save(huaYuanDocument);//保存
		}
		addMessage(redirectAttributes, "保存文档资料成功");
		return "redirect:"+Global.getAdminPath()+"/huayuandocument/huaYuanDocument/?repage";
	}
	
	/**
	 * 删除文档资料
	 */
	@RequiresPermissions("huayuandocument:huaYuanDocument:del")
	@RequestMapping(value = "delete")
	public String delete(HuaYuanDocument huaYuanDocument, RedirectAttributes redirectAttributes) {
		huaYuanDocumentService.delete(huaYuanDocument);
		addMessage(redirectAttributes, "删除文档资料成功");
		return "redirect:"+Global.getAdminPath()+"/huayuandocument/huaYuanDocument/?repage";
	}
	
	/**
	 * 批量删除文档资料
	 */
	@RequiresPermissions("huayuandocument:huaYuanDocument:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			huaYuanDocumentService.delete(huaYuanDocumentService.get(id));
		}
		addMessage(redirectAttributes, "删除文档资料成功");
		return "redirect:"+Global.getAdminPath()+"/huayuandocument/huaYuanDocument/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("huayuandocument:huaYuanDocument:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(HuaYuanDocument huaYuanDocument, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文档资料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HuaYuanDocument> page = huaYuanDocumentService.findPage(new Page<HuaYuanDocument>(request, response, -1), huaYuanDocument);
    		new ExportExcel("文档资料", HuaYuanDocument.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出文档资料记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuandocument/huaYuanDocument/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("huayuandocument:huaYuanDocument:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HuaYuanDocument> list = ei.getDataList(HuaYuanDocument.class);
			for (HuaYuanDocument huaYuanDocument : list){
				try{
					huaYuanDocumentService.save(huaYuanDocument);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文档资料记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条文档资料记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入文档资料失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuandocument/huaYuanDocument/?repage";
    }
	
	/**
	 * 下载导入文档资料数据模板
	 */
	@RequiresPermissions("huayuandocument:huaYuanDocument:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文档资料数据导入模板.xlsx";
    		List<HuaYuanDocument> list = Lists.newArrayList(); 
    		new ExportExcel("文档资料数据", HuaYuanDocument.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/huayuandocument/huaYuanDocument/?repage";
    }
	
	
	

}