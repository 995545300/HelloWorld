package com.jeeplus.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.jeeplus.modules.collectioninout.entity.CollectionInOut;
import com.jeeplus.modules.huanycollection.entity.HuaYCollection;
import com.jeeplus.modules.huanycollection.entity.HuanyCollectionAdditive;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.rtf.RtfWriter2;


public class ToWord extends XWPFDocument{  
    public  void testWrite() throws Exception {  
       String templatePath = "D:\\word\\template2.doc";  
       InputStream is = new FileInputStream(templatePath);  
       HWPFDocument doc = new HWPFDocument(is);  
       Range range = doc.getRange();  
       
       range.replaceText("${testId}", "300.00");  
       //把range范围内的${reportDate}替换为当前的日期  
       range.replaceText("${userName}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));  
       range.replaceText("${userId}", "100sd.00");  
       range.replaceText("${createDate}", "23230.00");  
       range.replaceText("${classId}", "23230.00");  
       range.replaceText("${theoryGrade}", "23230.00");  
       range.replaceText("${testGrade}", "23230.00");  
       range.replaceText("${purpose}", "\r23230.00");  
       range.replaceText("${tools}", "23230.00");
       range.replaceText("${step}", "23230.00"); 
        range.replaceText("${steel1s1}", "单元格1");
        range.replaceText("${steel1s1}", "23230.00");
        range.replaceText("${steel2s1}", "23230.00");
       
        range.replaceText("${steels1a}", "23230.00");
        range.replaceText("${steel1s2}", "23230.00");
        range.replaceText("${steel2s2}", "23230.00");
       
        range.replaceText("${steels2a}", "23230.00");
        range.replaceText("${steel1s3}", "23230.00");
        range.replaceText("${steel2s3}", "23230.00");
        range.replaceText("${steels3a}", "23230.00");
        range.replaceText("${steelcalc}", "23230.00");
        range.replaceText("${steelcalc2435}", "23230.00");
        TableIterator it = new TableIterator(range);  
        //迭代文档中的表格  
         while (it.hasNext()) {     
             Table tb = (Table) it.next();     
             //迭代行，默认从0开始  
             for (int i = 0; i < tb.numRows(); i++) {     
                 TableRow tr = tb.getRow(i);     
                 //迭代列，默认从0开始  
                 for (int j = 0; j < tr.numCells(); j++) {     
                     org.apache.poi.hwpf.usermodel.TableCell td = tr.getCell(j);//取得单元格  
                     //取得单元格的内容  
                     
                     for(int k=0;k<td.numParagraphs();k++){     
                         Paragraph para =td.getParagraph(k); 
                       
                         String s = para.text();     
                         System.out.println("哈哈："+s);  
                     } //end for      
                 }   //end for  
             }   //end for  
         }
       ByteArrayOutputStream ostream = new ByteArrayOutputStream();
       OutputStream os = new FileOutputStream("D:\\word\\write.doc");  
       //把doc输出到输出流中  
      doc.write(ostream); 
      os.write(ostream.toByteArray());
      os.close();
      ostream.close();
      is.close();
    }  
    public  static void testWrite(String newfilepath,Range range,HWPFDocument doc,InputStream is ) throws Exception {  
         TableIterator it = new TableIterator(range);  
         //迭代文档中的表格  
          while (it.hasNext()) {     
              Table tb = (Table) it.next();     
              //迭代行，默认从0开始  
              for (int i = 0; i < tb.numRows(); i++) {     
                  TableRow tr = tb.getRow(i);     
                  //迭代列，默认从0开始  
                  for (int j = 0; j < tr.numCells(); j++) {     
                      org.apache.poi.hwpf.usermodel.TableCell td = tr.getCell(j);//取得单元格  
                      //取得单元格的内容  
                      
                      for(int k=0;k<td.numParagraphs();k++){     
                          Paragraph para =td.getParagraph(k); 
                        
                          String s = para.text();     
                          System.out.println("哈哈："+s);  
                      } //end for      
                  }   //end for  
              }   //end for  
          }
          ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        OutputStream os = new FileOutputStream(newfilepath);  
        //把doc输出到输出流中  
       doc.write(ostream); 
       os.write(ostream.toByteArray());
       os.close();
       ostream.close();
       is.close();
     }  
     
    /** 
     * 关闭输入流 
     * @param is 
     */  
    public void closeStream(InputStream is) {  
       if (is != null) {  
          try {  
             is.close();  
          } catch (IOException e) {  
             e.printStackTrace();  
          }  
       }  
    }  
    
    /** 
     * 关闭输出流 
     * @param os 
     */  
    public void closeStream(OutputStream os) {  
       if (os != null) {  
          try {  
             os.close();  
          } catch (IOException e) {  
             e.printStackTrace();  
          }  
       }  
    }  
    
    //////////////////////////////////////////////////////////////////////////////////
    public static   void testTemplateWrite(Map<String, Object> map,String templetPath,String newFilePath) throws Exception {  
        Map<String, Object> params = new HashMap<String, Object>();  
        
        
        InputStream is = new FileInputStream(templetPath);  
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量  
        replaceInPara(doc, map);  
        //替换表格里面的变量  
        replaceInTable(doc, map);  
        
        OutputStream os = new FileOutputStream(newFilePath);  
        doc.write(os);  
        close(os);  
        close(is);
     }  
      
     /** 
      * 替换段落里面的变量 
      * @param doc 要替换的文档 
      * @param params 参数 
      */  
     public static  void replaceInPara(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
        XWPFParagraph para;  
        while (iterator.hasNext()) {  
           para = iterator.next();  
           replaceInPara(para, params);  
        }  
     }  
      
     /** 
      * 替换段落里面的变量 
      * @param para 要替换的段落 
      * @param params 参数 
      */  
     public static  void replaceInPara(XWPFParagraph para, Map<String, Object> params) {  
        List<XWPFRun> runs;  
        Matcher matcher;  
        if (matcher(para.getParagraphText()).find()) {
           runs = para.getRuns();
           
           int projectNum=0;
           int bstageNum=0;
           for (int i=0; i<runs.size(); i++) { 
        	   boolean flag=false;
        	   boolean flagdate=false;
              XWPFRun run = runs.get(i);  
              String runText = run.toString();  
              if(runText.equals("${monthStr}")){
            	  flag=true;
              }
              if(runText.equals("${startdate}")){
            	  flagdate=true;
              }
              if(runText.equals("${projectName}")){
            	  flagdate=true;
              }
              if(runText.equals("${enddate}")){
            	  flagdate=true;
              }
              matcher = matcher(runText);  
              if (matcher.find()) {  
                  while ((matcher = matcher(runText)).find()) {
                		  runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1)))); 
                  }  
                  //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，  
                  //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。  
                  para.removeRun(i);  
                  XWPFRun r1= para.insertNewRun(i);  
                  r1.setText(runText);
                  if(flag){
                      r1.setFontSize(16);
                      r1.setBold(true);
                      flag=false;
                  }
                  if(flagdate){
                      r1.setFontSize(12);
                      r1.setBold(true);
                      flag=false;
                  }
              }  
           }  
        }  
     }  
      
     /** 
      * 替换表格里面的变量 
      * @param doc 要替换的文档 
      * @param params 参数 
      */  
     public static  void replaceInTable(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFTable> iterator = doc.getTablesIterator();  
        XWPFTable table;  
        List<XWPFTableRow> rows;  
        List<XWPFTableCell> cells;  
        List<XWPFParagraph> paras;  
        while (iterator.hasNext()) {  
           table = iterator.next();  
           rows = table.getRows();  
           for (XWPFTableRow row : rows) {  
              cells = row.getTableCells();  
              for (XWPFTableCell cell : cells) {  
                  paras = cell.getParagraphs();  
                  for (XWPFParagraph para : paras) {  
                     replaceInPara(para, params);  
                  }  
              }  
           }  
        }  
     }  
     
      
     /** 
      * 正则匹配字符串 
      * @param str 
      * @return 
      */  
     public static Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
     }  
      
     /** 
      * 关闭输入流 
      * @param is 
      */  
     public static void close(InputStream is) {  
        if (is != null) {  
           try {  
              is.close();  
           } catch (IOException e) {  
              e.printStackTrace();  
           }  
        }  
     }  
      
     /** 
      * 关闭输出流 
      * @param os 
      */  
     public  static void close(OutputStream os) {  
        if (os != null) {  
           try {  
              os.close();  
           } catch (IOException e) {  
              e.printStackTrace();  
           }  
        }  
     }  
   /**
    * 
    * 功能描述:
    * @param 
    * @param templetPath
    * @param newFilePath
 * @param imgPath 
    * @return
    * @throws Exception
    */
  public static   String CollectionWriteTable(HuaYCollection huaYCollection,String templetPath,String newFilePath,List<CollectionInOut>  listinout, String imgPath) throws Exception {  
         Map<String, Object> params = new HashMap<String, Object>();  
         
		 InputStream is = new FileInputStream(templetPath);  
         XWPFDocument doc = new XWPFDocument(is);
         params= JsonUtil.toMap(huaYCollection);
         replaceInTable(doc, params);
         replaceInPara(doc, params);
         XWPFTable table= doc.getTables().get(1);
         List<HuanyCollectionAdditive> listHuanyCollectionAdditive=huaYCollection.getHuanyCollectionAdditiveList();
         if(null!=listHuanyCollectionAdditive&&listHuanyCollectionAdditive.size()>0){
        	 for(int i=0;i<listHuanyCollectionAdditive.size();i++){
        		 HuanyCollectionAdditive bs=listHuanyCollectionAdditive.get(0);
        		//替换表格里面的变量  
 	        	XWPFTableRow row=table.createRow();
        		if(StringUtils.isNotEmpty(bs.getName())){
	        		row.getCell(0).setText(bs.getName());
	        	}
        		if(StringUtils.isNotEmpty(bs.getText())){
        			row.getCell(1).setText(bs.getText());
        		}
        		
        	 }
         }
         XWPFTable table2= doc.getTables().get(2);
         if(null!=listinout&&listinout.size()>0){
        	 for(int i=0;i<listinout.size();i++){
        		 CollectionInOut bs=listinout.get(0);
         		//替换表格里面的变量  
  	        	XWPFTableRow row=table2.createRow();
         		
         		if(StringUtils.isNotEmpty(bs.getWork())){
         			if(bs.getWork().equals("1")){
         				row.getCell(0).setText("入库");
         			}else if(bs.getWork().equals("0")){
         				row.getCell(0).setText("出库");
         			}
         		}
         		 if(null!=bs.getDatetime()){
	        		 SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
		        	String dateStr=DateUtils.formatDate(bs.getDatetime(),"yyyy-MM-dd");
		        	row.getCell(1).setText(dateStr);
		         }
         		if(StringUtils.isNotEmpty(bs.getName())){
         			row.getCell(2).setText(bs.getName());
         		}
        	 }
         }
         /*Document docment = new Document(PageSize.A4); 
         RtfWriter2.getInstance(docment, new FileOutputStream(newFilePath));
         docment.open();
         
		Image png = Image.getInstance("C:/1.jpg");png.setSpacingAfter(10);
		png.setAbsolutePosition(0, 0); 
		docment.add(png);
		docment.close();*/
         OutputStream os = new FileOutputStream(newFilePath);  
         doc.write(os);  
         close(os);  
         close(is);
         
		return "1";
      }  
  
//word跨行并单元格  
  public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {    
      for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {    
          XWPFTableCell cell = table.getRow(rowIndex).getCell(col);    
          if ( rowIndex == fromRow ) {    
              // The first merged cell is set with RESTART merge value    
              cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);    
          } else {    
              // Cells which join (merge) the first one, are set with CONTINUE    
              cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);    
          }    
      }    
  } 
  public static  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {    
      for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {    
          XWPFTableCell cell = table.getRow(row).getCell(cellIndex);    
          if ( cellIndex == fromCell ) {    
              // The first merged cell is set with RESTART merge value    
              cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);    
          } else {    
              // Cells which join (merge) the first one, are set with CONTINUE    
              cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);    
          }    
      }    
  }
 /** 
  * @param id 
  * @param width 宽 
  * @param height 高 
  * @param paragraph  段落 
  */ 
 public void createPicture(int id, int width, int height,XWPFParagraph paragraph) {    
     final int EMU = 9525;    
     width *= EMU;    
     height *= EMU;    
     String blipId = getAllPictures().get(id).getPackageRelationship().getId();    
     CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();    
     String picXml = ""   
             + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"   
             + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"   
             + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"   
             + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""   
             + id    
             + "\" name=\"Generated\"/>"   
             + "            <pic:cNvPicPr/>"   
             + "         </pic:nvPicPr>"   
             + "         <pic:blipFill>"   
             + "            <a:blip r:embed=\""   
             + blipId    
             + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"   
             + "            <a:stretch>"   
             + "               <a:fillRect/>"   
             + "            </a:stretch>"   
             + "         </pic:blipFill>"   
             + "         <pic:spPr>"   
             + "            <a:xfrm>"   
             + "               <a:off x=\"0\" y=\"0\"/>"   
             + "               <a:ext cx=\""   
             + width    
             + "\" cy=\""   
             + height    
             + "\"/>"   
             + "            </a:xfrm>"   
             + "            <a:prstGeom prst=\"rect\">"   
             + "               <a:avLst/>"   
             + "            </a:prstGeom>"   
             + "         </pic:spPr>"   
             + "      </pic:pic>"   
             + "   </a:graphicData>" + "</a:graphic>";    
  
     inline.addNewGraphic().addNewGraphicData();    
     XmlToken xmlToken = null;    
     try {    
         xmlToken = XmlToken.Factory.parse(picXml);    
     } catch (XmlException xe) {    
         xe.printStackTrace();    
     }    
     inline.set(xmlToken);   
        
     inline.setDistT(0);      
     inline.setDistB(0);      
     inline.setDistL(0);      
     inline.setDistR(0);      
        
     CTPositiveSize2D extent = inline.addNewExtent();    
     extent.setCx(width);    
     extent.setCy(height);    
        
     CTNonVisualDrawingProps docPr = inline.addNewDocPr();      
     docPr.setId(id);      
     docPr.setName("图片" + id);      
     docPr.setDescr("测试");   
 }  
	 /** 
	  * 将输入流中的数据写入字节数组 
	  * @param in 
	  * @return 
	  */ 
	 public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){  
	     byte[] byteArray = null;  
	     try {  
	         int total = in.available();  
	         byteArray = new byte[total];  
	         in.read(byteArray);  
	     } catch (IOException e) {  
	         e.printStackTrace();  
	     }finally{  
	         if(isClose){  
	             try {  
	                 in.close();  
	             } catch (Exception e2) {  
	                 System.out.println("关闭流失败");  
	             }  
	         }  
	     }  
	     return byteArray;  
	 }  
 }  
