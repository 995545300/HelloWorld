/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.huanycollection.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.store.fs.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.modules.collectioninout.entity.CollectionInOut;
import com.jeeplus.modules.collectioninout.service.CollectionInOutService;
import com.jeeplus.modules.huayuan_copyright.entity.HuayuanCopyright;
import com.jeeplus.modules.huayuan_copyright.service.HuayuanCopyrightService;
import com.jeeplus.modules.huayuan_kufang.entity.HuaYuanKuFang;
import com.jeeplus.modules.huayuan_kufang.service.HuaYuanKuFangService;
import com.jeeplus.modules.huayuan_person_come_go.entity.HuayuanPersonComeGo;
import com.jeeplus.modules.huayuan_person_come_go.service.HuayuanPersonComeGoService;
import com.jeeplus.modules.huayuanfenlei.entity.HuaYuanFenLei;
import com.jeeplus.modules.huayuanfenlei.service.HuaYuanFenLeiService;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.ToWord;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import com.jeeplus.modules.huanycollection.service.HuaYCollectionService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.yingxiangxinxi.entity.HuaYuanXinXi;
import com.jeeplus.modules.yingxiangxinxi.service.HuaYuanXinXiService;

/**
 * 收藏品Controller
 * 
 * @author 韩欣益
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/huanycollection/huaYCollection")
public class HuaYCollectionController extends BaseController {
	@Autowired
	private HuaYuanKuFangService huaYuanKuFangService;
	@Autowired
	private HuaYuanXinXiService huaYuanXinXiService;
	@Autowired
	private HuaYuanFenLeiService huaYuanFenLeiService;
	@Autowired
	private CollectionInOutService collectionInOutService;
	private HuaYuanFenLei huaYuanFenLei;

	public HuaYuanFenLei getHuaYuanFenLei() {
		return huaYuanFenLei;
	}

	public void setHuaYuanFenLei(HuaYuanFenLei huaYuanFenLei) {
		this.huaYuanFenLei = huaYuanFenLei;
	}

	@Autowired
	private HuaYCollectionService huaYCollectionService;
	@Autowired
	private HuayuanCopyrightService huayuanCopyrightService;

	@ModelAttribute
	public HuaYCollection get(@RequestParam(required = false) String id) {
		HuaYCollection entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = huaYCollectionService.get(id);
		}
		if (entity == null) {
			entity = new HuaYCollection();
		}
		return entity;
	}

	/**
	 * 统计查询所有
	 */
	@RequiresPermissions("huanycollection:huaYCollection:list")
	@RequestMapping(value = "count")
	public String count(HuaYCollection huaYCollection,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String s = Integer.toString(huaYCollectionService.getNumNoDel());
		String s1 = Integer.toString(huaYCollectionService.getNumInKuFang());
		String s2 = Integer.toString(huaYCollectionService.getNumOutKuFang());
		System.out.println(s + s1 + s2 + "8888888888888888888888888888888888");
		List list = new ArrayList();
		list.add(s);
		list.add(s1);
		list.add(s2);
		model.addAttribute("list", list);
		return "modules/huanycollection/collectionCount";
	}

	/**
	 * 收藏品列表页面
	 */
	@RequiresPermissions("huanycollection:huaYCollection:list")
	@RequestMapping(value = { "list", "" })
	public String list(HuaYCollection huaYCollection,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<HuaYCollection> page = huaYCollectionService.findPage(
				new Page<HuaYCollection>(request, response), huaYCollection);
		model.addAttribute("page", page);
		return "modules/huanycollection/huaYCollectionList";
	}

	/**
	 * 查看，增加，编辑收藏品表单页面
	 */
	@RequiresPermissions(value = { "huanycollection:huaYCollection:view",
			"huanycollection:huaYCollection:add",
			"huanycollection:huaYCollection:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(HuaYCollection huaYCollection,
			HttpServletRequest request, HttpServletResponse response,
			HuayuanCopyright huayuanCopyright, Model model) {
		String copyrights = huaYCollection.getCopyrights();
		if (copyrights != null) {
			List<HuayuanCopyright> list = new ArrayList<HuayuanCopyright>(10);
			String str[] = copyrights.split(",");
			for (int i = 0; i < str.length; i++) {
				HuayuanCopyright huayuanCopyright1 = new HuayuanCopyright();
				huayuanCopyright1.setId(str[i]);
				list.add(huayuanCopyright1);
			}
			huaYCollection.setCopyrightlist(list);
		}
		List<HuayuanCopyright> copyright = huayuanCopyrightService
				.findList(huayuanCopyright);
		List<HuaYuanFenLei> classone = huaYuanFenLeiService.getclasslist("0");
		model.addAttribute("classone", classone);
		model.addAttribute("copyright", copyright);
		model.addAttribute("huaYCollection", huaYCollection);
		return "modules/huanycollection/huaYCollectionForm";
	}

	/**
	 * 
	 * 功能描述:获取二级分类
	 * 
	 * @param type
	 * @param parentid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "gettwo")
	public List<Map> gettwo(String name) {
		List<Map> mapList = new ArrayList();
		HuaYuanFenLei huaYuanFenLei = huaYuanFenLeiService.getByName(name);
		List<HuaYuanFenLei> listtwo = huaYuanFenLeiService
				.getclasslist(huaYuanFenLei.getId());
		for (int i = 0; i < listtwo.size(); i++) {
			HuaYuanFenLei huaYuanFenLeiItem = listtwo.get(i);
			Map map = new HashMap();
			map.put("name", huaYuanFenLeiItem.getName());
			mapList.add(map);
		}
		System.out.println("mapList:" + mapList.size());
		return mapList;
	}

	/**
	 * 保存收藏品
	 */
	@RequiresPermissions(value = { "huanycollection:huaYCollection:add",
			"huanycollection:huaYCollection:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(HuaYCollection huaYCollection,
			HttpServletRequest request, HttpServletResponse response,
			HuayuanCopyright huayuanCopyright, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, huaYCollection)) {
			return form(huaYCollection, request, response, huayuanCopyright,
					model);
		}
		if (!huaYCollection.getIsNewRecord()) {// 编辑表单保存
			HuaYCollection t = huaYCollectionService
					.get(huaYCollection.getId());// 从数据库取出记录的值
			String code = huaYCollection.getCode();
			List list = huaYCollection.getCopyrightlist();
			String copyright = new String();
			for (int i = 0; i < list.size(); i++) {

				HuayuanCopyright c = (HuayuanCopyright) list.get(i);
				if (i == (list.size() - 1)) {
					copyright += c.getId();
				} else {
					copyright += c.getId() + ",";
				}
			}
			String str1 = "TYHY";
			HuaYuanFenLei huaYuanFenLei = new HuaYuanFenLei();
			String strc1 = huaYCollection.getClassone();
			String strc2 = huaYCollection.getClasstwo();
			String strc3 = huaYCollection.getClassthree();
			HuaYuanFenLei huac1 = huaYuanFenLeiService.getByName(strc1);
			String c1 = new String();// 分类1编号
			String c2 = new String();// 分类2编号
			String c3 = new String();// 分类3编号
			String str4 = new String();// 拼接用代码号
			c1 = huac1.getDaimahao();
			if (!(strc2 == null || strc2.equals(""))) {
				HuaYuanFenLei huac2 = huaYuanFenLeiService.getByName(strc2);
				c2 = huac2.getDaimahao();
				if (!(strc3 == null || strc3.equals(""))) {
					if (strc3.equals("人物")) {
						if (c2 == "0101") {
							c3 = "010101";
							str4 = c3;
						} else if (c2 == "0102") {
							c3 = "010201";
							str4 = c3;
						}
					} else {
						HuaYuanFenLei huac3 = huaYuanFenLeiService
								.getByName(strc3);
						c3 = huac3.getDaimahao();
						str4 = c3;
					}
				} else {

					str4 = c2 + "00";
				}
			} else {
				str4 = c1 + "0000";
			}
			System.out.println(str4 + "111111111111111");

			String str3 = huaYCollection.getIncollectiondate();
			String str5 = str1 + str4 + str3 + code.substring(18);
			huaYCollection.setCode(str5);
			huaYCollection.setCopyrights(copyright);

			System.out.println(huaYCollection.getCode());
			huaYCollectionService.save(huaYCollection);// 保存

			addMessage(redirectAttributes, "保存收藏品成功");
			return "redirect:" + Global.getAdminPath()
					+ "/huanycollection/huaYCollection/?repage";
		} else {// 新增表单保存
			List list = huaYCollection.getCopyrightlist();
			String copyright = new String();
			for (int i = 0; i < list.size(); i++) {

				HuayuanCopyright c = (HuayuanCopyright) list.get(i);
				if (i == (list.size() - 1)) {
					copyright += c.getId();
				} else {
					copyright += c.getId() + ",";
				}
			}
			String str1 = "TYHY";
			HuaYuanFenLei huaYuanFenLei = new HuaYuanFenLei();
			String strc1 = huaYCollection.getClassone();
			String strc2 = huaYCollection.getClasstwo();
			String strc3 = huaYCollection.getClassthree();
			String strnum = new String();// 追后六位数字
			// String str4=huaYuanFenLei.getDaimahao();

			HuaYuanFenLei huac1 = huaYuanFenLeiService.getByName(strc1);
			String c1 = new String();
			String c2 = new String();
			String c3 = new String();
			String str4 = new String();// 拼接用代码号
			int num = huaYCollectionService.getNum() + 1;
			System.out.println(num + "表数据量！！！！！！！！！！！！！！！！！！！！！！！！！！");
			int i = num;
			Integer nums = i;
			int longs = nums.toString().length();
			switch (longs) {
			case 1:
				strnum = "00000" + nums;
				break;
			case 2:
				strnum = "0000" + nums;
				break;
			case 3:
				strnum = "000" + nums;
				break;
			case 4:
				strnum = "00" + nums;
				break;
			case 5:
				strnum = "0" + nums;
				break;
			case 6:
				strnum = nums + "";
				break;

			}
			c1 = huac1.getDaimahao();
			if (!(strc2 == null || strc2.equals(""))) {
				HuaYuanFenLei huac2 = huaYuanFenLeiService.getByName(strc2);
				c2 = huac2.getDaimahao();
				if (!(strc3 == null || strc3.equals(""))) {
					if (strc3.equals("人物")) {
						if (c2 == "0101" || c2.equals("0101")) {
							c3 = "010101";
							str4 = c3;
						} else if (c2 == "0102" || c2.equals("0101")) {
							c3 = "010201";
							str4 = c3;
						}
					} else {
						HuaYuanFenLei huac3 = huaYuanFenLeiService
								.getByName(strc3);
						c3 = huac3.getDaimahao();
						str4 = c3;
					}
				} else {

					str4 = c2 + "00";
				}
			} else {
				str4 = c1 + "0000";
			}
			System.out.println(str4 + "111111111111111");
			// Date date = huaYCollection.getIncollectiondate();
			String str3 = huaYCollection.getIncollectiondate();

			String str5 = str1 + str4 + str3 + strnum;
			huaYCollection.setCode(str5);
			huaYCollection.setCopyrights(copyright);

			System.out.println(huaYCollection.getCode());
			huaYCollectionService.save(huaYCollection);// 保存
		}
		addMessage(redirectAttributes, "保存收藏品成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 删除收藏品
	 */
	@RequiresPermissions("huanycollection:huaYCollection:del")
	@RequestMapping(value = "delete")
	public String delete(HuaYCollection huaYCollection,
			RedirectAttributes redirectAttributes) {
		huaYCollectionService.deleteByLogic(huaYCollection);
		addMessage(redirectAttributes, "删除收藏品成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 批量生成word
	 */
	@RequiresPermissions("huanycollection:huaYCollection:del")
	@RequestMapping(value = "generateWords")
	public String generateWords(HttpServletRequest request,
			HuaYCollection huaYCollection, String ids,
			RedirectAttributes redirectAttributes) {
		int successNum = 0;
		String tompath = request.getServletContext().getRealPath("/");
		String templetpath = tompath + "static\\template\\collectionmodel.docx";
		if (StringUtils.isNotEmpty(ids)) {
			String[] idsArray = ids.split(",");
			HuaYuanXinXi huayuanxinxi = new HuaYuanXinXi();
			for (String id : idsArray) {
				if (StringUtils.isNotEmpty(id)) {

					HuaYCollection hyc = huaYCollectionService.get(id);
					// 设置入藏日期
					if (null != hyc) {
						if (null != hyc.getIncollectiondate()) {
							hyc.setIncollectionDateStr(hyc
									.getIncollectiondate());
						}
						// 题识和印 题名：1,款识：1,题跋：0,铭文：0,题签：0,印鉴：2
						StringBuffer sb = new StringBuffer();
						if (StringUtils.isNotEmpty(hyc.getAutograph())) {
							sb.append("题名：" + hyc.getAutograph() + ",");
						}
						if (StringUtils.isNotEmpty(hyc.getInscriptions())) {
							sb.append("款识：" + hyc.getInscriptions() + ",");
						}
						if (StringUtils.isNotEmpty(hyc.getPreface())) {
							sb.append("题跋：" + hyc.getPreface() + ",");
						}
						if (StringUtils.isNotEmpty(hyc.getEpigraph())) {
							sb.append("铭文：" + hyc.getEpigraph() + ",");
						}
						if (StringUtils.isNotEmpty(hyc.getTitlepiece())) {
							sb.append("题签：" + hyc.getTitlepiece() + ",");
						}
						if (StringUtils.isNotEmpty(hyc.getPublicseal())) {
							sb.append("印鉴：" + hyc.getPublicseal());
						}
						hyc.setAutographContent(sb.toString());
						// 著作归属权
						HuayuanCopyright copyright = hyc.getCopyright();
						copyright = huayuanCopyrightService.get(copyright);
						if (null != copyright
								&& StringUtils.isNotEmpty(copyright.getName())) {
							hyc.setCopyrights(copyright.getName());
						}
						// 设置类别， 一级和二级
						String classone = "";
						String onetype = hyc.getClassone();
						if (StringUtils.isNotEmpty(onetype)) {
							classone += onetype;
						}
						String towtype = hyc.getClasstwo();
						if (StringUtils.isNotEmpty(towtype)) {
							classone += "：" + towtype;
						}
						hyc.setClasstype(classone);
						// 查询并设置影像文件数量
						huayuanxinxi.setCollectionid(hyc.getId());
						int count = huaYuanXinXiService
								.findCountByCollectionId(huayuanxinxi);
						List<HuaYuanXinXi> listxx = huaYuanXinXiService
								.findList(huayuanxinxi);
						String imgPath = "";
						if (null != listxx && listxx.size() > 0) {
							imgPath = request.getServletContext()
									.getRealPath("").replace("huayuan", "")
									+ listxx.get(0).getSmallimg();
						}
						hyc.setImpactFileCount(count + "");
						String newFilePath = tompath
								+ "static\\template\\collectionfile\\collection_"
								+ hyc.getId() + ".docx";
						CollectionInOut collectionInOut = new CollectionInOut();
						collectionInOut.setCollection(hyc);
						List<CollectionInOut> listinout = collectionInOutService
								.findList(collectionInOut);
						try {
							ToWord.CollectionWriteTable(hyc, templetpath,
									newFilePath, listinout, imgPath);
							hyc.setCollectionFile(newFilePath);
							huaYCollectionService.save(hyc);
							successNum++;
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("生成失败" + hyc.getId());
						}

					}

				}

			}

		}

		addMessage(redirectAttributes, successNum + "个word生成成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}
	
	
	/**
	 * 
	 * 功能描述:打印藏品信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "print")
	public String print(HttpServletRequest request,
			HttpServletResponse response,String id,Model model) {
		
		HuaYCollection huaYCollection = huaYCollectionService.get(id);
		String imgurl = huaYuanXinXiService.getImgurl(id);//获取最先上传的正面影像信息
		String yingxiangnum = huaYuanXinXiService.getXinxinum(id);//获取影像信息的数量
		
		CollectionInOut collectionInOut = new CollectionInOut();
		collectionInOut.setCollection(huaYCollection);
		List<CollectionInOut> listinout = collectionInOutService.findList(collectionInOut);
		
		//查询藏品库房
		String kufangname="";
		kufangname=getKufangName(null!=huaYCollection.getKufang()?huaYCollection.getKufang().getId():"");
		
		
		model.addAttribute("kufangname", kufangname);
		model.addAttribute("imgurl", imgurl);
		model.addAttribute("huaYCollection", huaYCollection);
		model.addAttribute("yingxiangnum", yingxiangnum);
		model.addAttribute("listinout", listinout);
		return "modules/huanycollection/print";
	}
	
	/**
	 * 
	 * 功能描述:根据id获取库房名称
	 * @param id
	 * @return
	 */
	public String getKufangName(String id){
		HuaYuanKuFang huaYuanKuFang = huaYuanKuFangService.get(id);
		String kufangname=null!=huaYuanKuFang?huaYuanKuFang.getName():"";
		if(null!=huaYuanKuFang.getParent()&&!"0".equals(huaYuanKuFang.getParent().getId())){
			kufangname=getKufangName(huaYuanKuFang.getParent().getId())+kufangname;
		}
		return kufangname;
	}
	
	
	
	/**
	 * 下载收藏品word文件
	 */
	@RequestMapping(value = "downCollectionFile")
	public void downCollectionFile(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, String id) {
		HuaYCollection huaYCollection = huaYCollectionService.get(id);
		String projectName = "";
		if (null != huaYCollection) {
			String code = huaYCollection.getCode();
			// projectName+=month.getMonth()+"月质量月报报表";
			projectName += code;

		}
		try {
			String fileName = "collection_" + id + ".docx";
			String tompath = request.getServletContext().getRealPath("/");
			String filepatn = tompath + "static\\template\\collectionfile\\"
					+ fileName;
			if (FileUtils.exists(filepatn)) {
				try {
					// path是指欲下载的文件的路径。
					File file = new File(filepatn);
					// 取得文件名。
					String filename = file.getName();
					// 取得文件的后缀名。
					String ext = filename.substring(
							filename.lastIndexOf(".") + 1).toUpperCase();

					// 以流的形式下载文件。
					InputStream fis = new BufferedInputStream(
							new FileInputStream(filepatn));
					byte[] buffer = new byte[fis.available()];
					String newName = projectName + "." + ext;
					fis.read(buffer);
					fis.close();
					// 清空response
					response.reset();
					// 设置response的Header
					response.addHeader(
							"Content-Disposition",
							"attachment;filename="
									+ new String(newName.getBytes("GB2312"),
											"iso8859-1"));
					// response.addHeader("Content-Disposition",
					// "attachment;filename=" + new
					// String(filename.getBytes("utf-8")));
					response.addHeader("Content-Length", "" + file.length());
					OutputStream toClient = new BufferedOutputStream(
							response.getOutputStream());
					response.setContentType("application/octet-stream");
					toClient.write(buffer);
					toClient.flush();
					toClient.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息：" + e.getMessage());
		}
	}

	/**
	 * 批量删除收藏品
	 */
	@RequiresPermissions("huanycollection:huaYCollection:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			huaYCollectionService.deleteByLogic(huaYCollectionService.get(id));
		}
		addMessage(redirectAttributes, "删除收藏品成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 收藏品入库
	 */
	@RequiresPermissions("huanycollection:huaYCollection:edit")
	@RequestMapping(value = "inKuFang")
	public String inKuFang(HuaYCollection huaYCollection,
			RedirectAttributes redirectAttributes) {
		huaYCollection.setIsinkufang("1");
		huaYCollectionService.save(huaYCollection);
		addMessage(redirectAttributes, "收藏品入库成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 收藏品批量入库
	 */
	@RequiresPermissions("huanycollection:huaYCollection:edit")
	@RequestMapping(value = "inKuFangAll")
	public String inKuFangAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			HuaYCollection huaYCollection1 = huaYCollectionService.get(id);
			huaYCollection1.setIsinkufang("1");
			huaYCollectionService.save(huaYCollection1);
		}
		addMessage(redirectAttributes, "收藏品入库成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 收藏品出库
	 */
	@RequiresPermissions("huanycollection:huaYCollection:edit")
	@RequestMapping(value = "outKuFang")
	public String outKuFang(HuaYCollection huaYCollection,
			RedirectAttributes redirectAttributes) {
		huaYCollection.setIsinkufang("0");
		huaYCollectionService.save(huaYCollection);
		addMessage(redirectAttributes, "收藏品出库成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 收藏品批量出库
	 */
	@RequiresPermissions("huanycollection:huaYCollection:edit")
	@RequestMapping(value = "outKuFangAll")
	public String outKuFangAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			HuaYCollection huaYCollection1 = huaYCollectionService.get(id);
			huaYCollection1.setIsinkufang("0");
			huaYCollectionService.save(huaYCollection1);
		}
		addMessage(redirectAttributes, "收藏品出库成功");
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("huanycollection:huaYCollection:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(HuaYCollection huaYCollection,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "收藏品" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<HuaYCollection> page = huaYCollectionService.findPage(
					new Page<HuaYCollection>(request, response, -1),
					huaYCollection);
			new ExportExcel("收藏品", HuaYCollection.class)
					.setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出收藏品记录失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("huanycollection:huaYCollection:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HuaYCollection> list = ei.getDataList(HuaYCollection.class);
			for (HuaYCollection huaYCollection : list) {
				try {
					huaYCollectionService.save(huaYCollection);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条收藏品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条收藏品记录"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入收藏品失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 下载导入收藏品数据模板
	 */
	@RequiresPermissions("huanycollection:huaYCollection:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "收藏品数据导入模板.xlsx";
			List<HuaYCollection> list = Lists.newArrayList();
			new ExportExcel("收藏品数据", HuaYCollection.class, 1).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/huanycollection/huaYCollection/?repage";
	}

	/**
	 * 选择藏品著作权归属
	 */
	@RequestMapping(value = "selectcopyright")
	public String selectcopyright(HuayuanCopyright copyright, String url,
			String fieldLabels, String fieldKeys, String searchLabel,
			String searchKey, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<HuayuanCopyright> page = huaYCollectionService
				.findPageBycopyright(new Page<HuayuanCopyright>(request,
						response), copyright);
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabel = URLDecoder.decode(searchLabel, "UTF-8");
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("labelNames", fieldLabels.split("\\|"));
		model.addAttribute("labelValues", fieldKeys.split("\\|"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", copyright);
		model.addAttribute("page", page);
		return "modules/huayuan_copyright/huayuanCopyrightSelect";
	}

	/**
	 * 选择搜索库房
	 */
	@RequestMapping(value = "selectkufang")
	public String selectkufang(HuaYuanKuFang kufang, String url,
			String fieldLabels, String fieldKeys, String searchLabel,
			String searchKey, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<HuaYuanKuFang> page = huaYCollectionService.findPageBykufang(
				new Page<HuaYuanKuFang>(request, response), kufang);
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabel = URLDecoder.decode(searchLabel, "UTF-8");
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("labelNames", fieldLabels.split("\\|"));
		model.addAttribute("labelValues", fieldKeys.split("\\|"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", kufang);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}

}