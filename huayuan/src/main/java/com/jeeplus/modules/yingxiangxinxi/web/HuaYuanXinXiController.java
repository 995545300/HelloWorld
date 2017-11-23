/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yingxiangxinxi.web;

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
import com.jeeplus.common.utils.ImageYaSuo;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.UrlUtil;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import com.jeeplus.modules.huanycollection.web.HuaYCollectionController;
import com.jeeplus.modules.yingxiangxinxi.entity.HuaYuanXinXi;
import com.jeeplus.modules.yingxiangxinxi.service.HuaYuanXinXiService;

/**
 * 藏品影像信息Controller
 * @author 韩欣益
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/yingxiangxinxi/huaYuanXinXi")
public class HuaYuanXinXiController extends BaseController {

	@Autowired
	private HuaYuanXinXiService huaYuanXinXiService;
	@Autowired
	private HuaYCollectionController huaYCollectionController;
	
	@ModelAttribute
	public HuaYuanXinXi get(@RequestParam(required=false) String id) {
		HuaYuanXinXi entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = huaYuanXinXiService.get(id);
		}
		if (entity == null){
			entity = new HuaYuanXinXi();
		}
		return entity;
	}
	
	/**
	 * 藏品影像信息列表页面
	 */
	@RequiresPermissions("yingxiangxinxi:huaYuanXinXi:list")
	@RequestMapping(value = {"list", ""})
	public String list(@RequestParam (required=false) String cid,HuaYuanXinXi huaYuanXinXi,HttpServletRequest request, HttpServletResponse response, Model model) {
		huaYuanXinXi.setCollectionid(cid);
		//huaYCollection=huaYCollectionController.get(id);
		Page<HuaYuanXinXi> page = huaYuanXinXiService.findPage(new Page<HuaYuanXinXi>(request, response), huaYuanXinXi); 
		model.addAttribute("page", page);
		return "modules/yingxiangxinxi/huaYuanXinXiList";
	}

	/**
	 * 查看，增加，编辑藏品影像信息表单页面
	 */
	@RequiresPermissions(value={"yingxiangxinxi:huaYuanXinXi:view","yingxiangxinxi:huaYuanXinXi:add","yingxiangxinxi:huaYuanXinXi:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam (required=false)String cid,HuaYuanXinXi huaYuanXinXi, Model model) {
		huaYuanXinXi.setCollectionid(cid);
		model.addAttribute("huaYuanXinXi", huaYuanXinXi);
		return "modules/yingxiangxinxi/huaYuanXinXiForm";
	}
	/**
	 * 查看，增加，编辑藏品影像信息表单页面
	 */
	@RequiresPermissions(value={"yingxiangxinxi:huaYuanXinXi:view","yingxiangxinxi:huaYuanXinXi:add","yingxiangxinxi:huaYuanXinXi:edit"},logical=Logical.OR)
	@RequestMapping(value = "imagelook")
	public String imagelook(@RequestParam (required=false)String cid,HuaYuanXinXi huaYuanXinXi, Model model) {
		huaYuanXinXi.setName(cid);
		model.addAttribute("huaYuanXinXi", huaYuanXinXi);
		return "modules/yingxiangxinxi/imagelook";
	}

	/**
	 * 保存藏品影像信息
	 */
	@RequiresPermissions(value={"yingxiangxinxi:huaYuanXinXi:add","yingxiangxinxi:huaYuanXinXi:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@RequestParam String cid,HuaYuanXinXi huaYuanXinXi, Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		if (!beanValidator(model, huaYuanXinXi)){
			return form(cid,huaYuanXinXi, model);
		}
		if(!huaYuanXinXi.getIsNewRecord()){//编辑表单保存
			HuaYuanXinXi t = huaYuanXinXiService.get(huaYuanXinXi.getId());//从数据库取出记录的值
			String bigimg1=huaYuanXinXi.getName();//获取大图访问地址
			String bigimg=UrlUtil.getURLDecoderString(bigimg1);
			//System.out.println(bigimg);
			int i=bigimg.lastIndexOf("/");
			String url=bigimg.substring(0, i);//截取地址
			String name=bigimg.substring(i+1);//截取图片名称
			String url1=url+"/smallimg/";//设置压缩图片路径
			String name1="S"+name;//设置压缩图片名称
			ImageYaSuo imageYaSuo=new ImageYaSuo();
			String str=imageYaSuo.compressPic(url, url1, name, name1);
			//System.out.println(str);
			String smallimg=url1+name1;//拼接小图访问地址
			huaYuanXinXi.setSmallimg(smallimg);//添加地址到实体类
			MyBeanUtils.copyBeanNotNull2Bean(huaYuanXinXi, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			huaYuanXinXiService.save(t);//保存
		}else{//新增表单保存
			
			
			String bigimg1=new String(huaYuanXinXi.getName());//获取大图访问地址
			String bigimg=UrlUtil.getURLDecoderString(bigimg1);
			//System.out.println(bigimg);
			//System.out.println(bigimg+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			int i=bigimg.lastIndexOf("/");
			String url=bigimg.substring(0, i);//截取地址
			String name=bigimg.substring(i+1);//截取图片名称
			String url1=url+"/smallimg/";//设置压缩图片路径
			String name1="S"+name;//设置压缩图片名称
			ImageYaSuo imageYaSuo=new ImageYaSuo();
			String str=imageYaSuo.compressPic(url,url1, name, name1);
			System.out.println(str);
			String smallimg=url1+name1;//拼接小图访问地址
			
			
			huaYuanXinXi.setSmallimg(smallimg);//添加地址到实体类
			huaYuanXinXiService.save(huaYuanXinXi);//保存
		}
		addMessage(redirectAttributes, "保存藏品影像信息成功");
		return "redirect:"+Global.getAdminPath()+"/yingxiangxinxi/huaYuanXinXi/list?cid="+cid;
	}
	
	/**
	 * 删除藏品影像信息
	 */
	@RequiresPermissions("yingxiangxinxi:huaYuanXinXi:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam String cid,HuaYuanXinXi huaYuanXinXi, RedirectAttributes redirectAttributes) {
		huaYuanXinXiService.delete(huaYuanXinXi);
		addMessage(redirectAttributes, "删除藏品影像信息成功");
		return "redirect:"+Global.getAdminPath()+"/yingxiangxinxi/huaYuanXinXi/list?cid="+cid;
	}
	
	/**
	 * 批量删除藏品影像信息
	 */
	@RequiresPermissions("yingxiangxinxi:huaYuanXinXi:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(@RequestParam(required=false) String cid,String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		HuaYuanXinXi huaYuanXinXi =huaYuanXinXiService.get(idArray[0]); 
		String cid2=huaYuanXinXi.getCollectionid();
		for(String id : idArray){
			huaYuanXinXiService.delete(huaYuanXinXiService.get(id));
		}
		addMessage(redirectAttributes, "删除藏品影像信息成功");
		return "redirect:"+Global.getAdminPath()+"/yingxiangxinxi/huaYuanXinXi/list?cid="+cid2;
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("yingxiangxinxi:huaYuanXinXi:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(HuaYuanXinXi huaYuanXinXi, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "藏品影像信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HuaYuanXinXi> page = huaYuanXinXiService.findPage(new Page<HuaYuanXinXi>(request, response, -1), huaYuanXinXi);
    		new ExportExcel("藏品影像信息", HuaYuanXinXi.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出藏品影像信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yingxiangxinxi/huaYuanXinXi/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("yingxiangxinxi:huaYuanXinXi:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HuaYuanXinXi> list = ei.getDataList(HuaYuanXinXi.class);
			for (HuaYuanXinXi huaYuanXinXi : list){
				try{
					huaYuanXinXiService.save(huaYuanXinXi);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条藏品影像信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条藏品影像信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入藏品影像信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yingxiangxinxi/huaYuanXinXi/?repage";
    }
	
	/**
	 * 下载导入藏品影像信息数据模板
	 */
	@RequiresPermissions("yingxiangxinxi:huaYuanXinXi:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "藏品影像信息数据导入模板.xlsx";
    		List<HuaYuanXinXi> list = Lists.newArrayList(); 
    		new ExportExcel("藏品影像信息数据", HuaYuanXinXi.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yingxiangxinxi/huaYuanXinXi/?repage";
    }
	
	
	

}