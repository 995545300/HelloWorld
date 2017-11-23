/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.collectioninout.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.cxf.binding.corba.wsdl.Array;
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
import com.jeeplus.modules.collectioninout.entity.CollectionInOut;
import com.jeeplus.modules.collectioninout.service.CollectionInOutService;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import com.jeeplus.modules.huanycollection.service.HuaYCollectionService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 收藏品出入登记Controller
 * @author 韩欣益
 * @version 2017-07-01
 */
@Controller
@RequestMapping(value = "${adminPath}/collectioninout/collectionInOut")
public class CollectionInOutController extends BaseController {

	@Autowired
	private CollectionInOutService collectionInOutService;
	@Autowired
	private HuaYCollectionService huaYCollectionService ;
	
	@ModelAttribute
	public CollectionInOut get(@RequestParam(required=false) String id) {
		CollectionInOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = collectionInOutService.get(id);
		}
		if (entity == null){
			entity = new CollectionInOut();
		}
		return entity;
	}
	
	/**
	 * 收藏品出入登记列表页面
	 */
	@RequiresPermissions("collectioninout:collectionInOut:list")
	@RequestMapping(value = {"list", ""})
	public String list(@RequestParam (required=false) String cid,CollectionInOut collectionInOut, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		System.out.println(cid);
		if(StringUtils.isNotBlank(cid)){
			HuaYCollection collection= new HuaYCollection();
			//collection=collectionInOut.getCollection();
			collection.setId(cid);
			collectionInOut.setCollection(collection);
		}
		Page<CollectionInOut> page = collectionInOutService.findPage(new Page<CollectionInOut>(request, response), collectionInOut); 
		model.addAttribute("page", page);
		return "modules/collectioninout/collectionInOutList";
	}

	/**
	 * 查看，增加，编辑收藏品出入登记表单页面
	 */
	@RequiresPermissions(value={"collectioninout:collectionInOut:view"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CollectionInOut collectionInOut, Model model) {
		model.addAttribute("collectionInOut", collectionInOut);
		return "modules/collectioninout/collectionInOutForm";
	}
	/**
	 *增加收藏品出入登记表单页面
	 */
	@RequiresPermissions(value={"collectioninout:collectionInOut:add","collectioninout:collectionInOut:edit"},logical=Logical.OR)
	@RequestMapping(value = "add")
	public String add(String cid,CollectionInOut collectionInOut, Model model) {
		
		HuaYCollection huaYCollection=huaYCollectionService.get(cid);//报错
		System.out.println(cid+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		model.addAttribute("cid", cid);
		model.addAttribute("collectionInOut", collectionInOut);
		model.addAttribute("huaYCollection", huaYCollection);
		return "modules/collectioninout/collectionInOutAddin";
	}
	/**
	 *增加收藏品出入登记表单页面
	 */
	@RequiresPermissions(value={"collectioninout:collectionInOut:add","collectioninout:collectionInOut:edit"},logical=Logical.OR)
	@RequestMapping(value = "addAll")
	public String addAll(String cid,CollectionInOut collectionInOut, Model model) {
		String [] cids = cid.split(",");
		/*List <HuaYCollection> huaYCollections=new ArrayList();
		for(int i=0;i<cids.length;i++){
			HuaYCollection huaYCollection=huaYCollectionService.get(cids[i]);
			huaYCollections.add(huaYCollection);
		}*/
		HuaYCollection huaYCollection=huaYCollectionService.get(cids[0]);
		System.out.println(cid+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		model.addAttribute("cid", cid);
		model.addAttribute("collectionInOut", collectionInOut);
		model.addAttribute("huaYCollection", huaYCollection);
		return "modules/collectioninout/collectionInOutAddin";
	}

	/**
	 * 保存收藏品出入登记
	 */
	@RequiresPermissions(value={"collectioninout:collectionInOut:add","collectioninout:collectionInOut:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CollectionInOut collectionInOut, Model model, RedirectAttributes redirectAttributes) throws Exception{
		HuaYCollection h=new HuaYCollection();
		if (!beanValidator(model, collectionInOut)){
			return form(collectionInOut, model);
		}
		if(!collectionInOut.getIsNewRecord()){//编辑表单保存
			CollectionInOut t = collectionInOutService.get(collectionInOut.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(collectionInOut, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			collectionInOutService.save(t);//保存
		}else{//新增表单保存
			
			String cid=collectionInOut.getCid();
			String[] cids= cid.split(",");
			System.out.println(cids);
			for(int i=0;i<cids.length;i++){
				System.out.println(cids[i]+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				h= huaYCollectionService.get(cids[i]);
				CollectionInOut collectionInOut1=new CollectionInOut();
				collectionInOut1.setWork(h.getIsinkufang());
				collectionInOut1.setCollectionname(h.getCollectionname());
				collectionInOut1.setCollection(h);
				collectionInOut1.setName(UserUtils.getUser().getName());
				collectionInOut1.setRemarks(collectionInOut.getRemarks());
				Date day=new Date(); 
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				collectionInOut1.setDatetime(day);
				String work=h.getIsinkufang();
				//collectionInOut.getCollection().getId();
				if(work=="0"||"0".equals(work)){
					collectionInOut1.setWork("1");
				}else{
					collectionInOut1.setWork("0");
				}
				collectionInOutService.save(collectionInOut1);//保存  
			}
		}
		addMessage(redirectAttributes, "保存收藏品出入登记成功");
		System.out.println(h.getIsinkufang()+"***********************");
		if(h.getIsinkufang()=="1"||"1".equals(h.getIsinkufang())){
			System.out.println("redirect:"+Global.getAdminPath()+"/huanycollection/huaYCollection/outKuFangAll?ids="+collectionInOut.getCid());
			return "redirect:"+Global.getAdminPath()+"/huanycollection/huaYCollection/outKuFangAll?ids="+collectionInOut.getCid();
		}else{
			System.out.println("redirect:"+Global.getAdminPath()+"/huanycollection/huaYCollection/inKuFangAll?ids="+collectionInOut.getCid());
			return "redirect:"+Global.getAdminPath()+"/huanycollection/huaYCollection/inKuFangAll?ids="+collectionInOut.getCid();
		}
	}
	
	/**
	 * 删除收藏品出入登记
	 */
	@RequiresPermissions("collectioninout:collectionInOut:del")
	@RequestMapping(value = "delete")
	public String delete(CollectionInOut collectionInOut, RedirectAttributes redirectAttributes) {
		collectionInOutService.delete(collectionInOut);
		addMessage(redirectAttributes, "删除收藏品出入登记成功");
		return "redirect:"+Global.getAdminPath()+"/collectioninout/collectionInOut/?repage";
	}
	
	/**
	 * 批量删除收藏品出入登记
	 */
	@RequiresPermissions("collectioninout:collectionInOut:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			collectionInOutService.delete(collectionInOutService.get(id));
		}
		addMessage(redirectAttributes, "删除收藏品出入登记成功");
		return "redirect:"+Global.getAdminPath()+"/collectioninout/collectionInOut/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("collectioninout:collectionInOut:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CollectionInOut collectionInOut, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收藏品出入登记"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CollectionInOut> page = collectionInOutService.findPage(new Page<CollectionInOut>(request, response, -1), collectionInOut);
    		new ExportExcel("收藏品出入登记", CollectionInOut.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出收藏品出入登记记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/collectioninout/collectionInOut/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("collectioninout:collectionInOut:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CollectionInOut> list = ei.getDataList(CollectionInOut.class);
			for (CollectionInOut collectionInOut : list){
				try{
					collectionInOutService.save(collectionInOut);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条收藏品出入登记记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条收藏品出入登记记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入收藏品出入登记失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/collectioninout/collectionInOut/?repage";
    }
	
	/**
	 * 下载导入收藏品出入登记数据模板
	 */
	@RequiresPermissions("collectioninout:collectionInOut:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收藏品出入登记数据导入模板.xlsx";
    		List<CollectionInOut> list = Lists.newArrayList(); 
    		new ExportExcel("收藏品出入登记数据", CollectionInOut.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/collectioninout/collectionInOut/?repage";
    }
	
	
	

}