package com.web.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class Excel {

	public Excel() {
		super();
		// TODO Auto-generated constructor stub
	}

    /**
     * ����1
     */
    public static HSSFCell setCell_1(HSSFWorkbook wb, HSSFSheet s, HSSFRow row, short col, String val) {
		HSSFCellStyle cstyle =  wb.createCellStyle() ;
		HSSFFont font = wb.createFont() ;
    	HSSFCell cell = row.createCell(col) ;
		HSSFRichTextString t = new HSSFRichTextString(val) ;
		cell.setCellValue(t) ;
		
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD) ;
		font.setFontHeightInPoints((short)14) ;
		cstyle.setFont(font) ;
		cstyle.setAlignment(HSSFCellStyle.VERTICAL_BOTTOM) ;
		cstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER) ;
		cstyle.setWrapText(true) ;
		cell.setCellStyle(cstyle) ;
		
		return cell ;
    }

    /**
     * ����2
     */
    public static HSSFCell setCell_2(HSSFWorkbook wb, HSSFSheet s, HSSFRow row, short col, String val) {
		HSSFCellStyle cstyle =  wb.createCellStyle() ;
		HSSFFont font = wb.createFont() ;
    	HSSFCell cell = row.createCell(col) ;
		HSSFRichTextString t = new HSSFRichTextString(val) ;
		cell.setCellValue(t) ;
		
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD) ;
		cstyle.setFont(font) ;
		cstyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT) ;
		cstyle.setWrapText(true) ;
		cstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN) ;
		cstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN) ;
		cstyle.setBorderRight(HSSFCellStyle.BORDER_THIN) ;
		cstyle.setBorderTop(HSSFCellStyle.BORDER_THIN) ;
		cell.setCellStyle(cstyle) ;
		
		return cell ;
    }

    /**
     * ����3
     */
    public static HSSFCell setCell_3(HSSFWorkbook wb, HSSFSheet s, HSSFRow row, short col, String val) {
		HSSFCellStyle cstyle =  wb.createCellStyle() ;
    	HSSFCell cell = row.createCell(col) ;
		HSSFRichTextString t = new HSSFRichTextString(val) ;
		cell.setCellValue(t) ;
		
		cstyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT) ;
		cstyle.setWrapText(true) ;
		cstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN) ;
		cstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN) ;
		cstyle.setBorderRight(HSSFCellStyle.BORDER_THIN) ;
		cstyle.setBorderTop(HSSFCellStyle.BORDER_THIN) ;
		cell.setCellStyle(cstyle) ;
		
		return cell ;
    }

    /**
     * ����4
     */
    public static HSSFCell setCell_4(HSSFWorkbook wb, HSSFSheet s, HSSFRow row, short col, String val) {
		HSSFCellStyle cstyle =  wb.createCellStyle() ;
    	HSSFCell cell = row.createCell(col) ;
		HSSFRichTextString t = new HSSFRichTextString(val) ;
		cell.setCellValue(t) ;
		
		cstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT) ;
		cstyle.setWrapText(true) ;
		cell.setCellStyle(cstyle) ;
		
		return cell ;
    }
    
    public static void setHeader(HSSFSheet s
    		, String[] title) {
		HSSFRow row = s.createRow(0);
		HSSFCell cell = null ;
		for(short i=0;i<title.length;i++) {
			cell = row.createCell(i) ;
			HSSFRichTextString t = new HSSFRichTextString(title[i]) ;
			cell.setCellValue(t) ;
		}
    }
    
    public static void setCellValue(HSSFCell cell, String val) {
		HSSFRichTextString t = new HSSFRichTextString(val) ;
		cell.setCellValue(t) ;
    }
    
    public static void createCell(HSSFRow row, int col, String val) {
    	HSSFCell cell = row.createCell(col, HSSFCell.CELL_TYPE_STRING) ;
		HSSFRichTextString t = new HSSFRichTextString(val) ;
		cell.setCellValue(t) ;
    }
    
    public static void createCell_Double(HSSFRow row, int col, String val) {
    	HSSFCell cell = row.createCell(col, HSSFCell.CELL_TYPE_NUMERIC) ;
    	cell.setCellValue(Putil.getDouble(val)) ;
    }
    
    /*
     * type,  0-����,1-Integer,2-Long,3-Float,4-Double
     */
    public static String getValueFromCell(HSSFCell cell, int type) {
    	if(cell == null)
    		return "";
    	if(cell.getCellType()==HSSFCell.CELL_TYPE_BLANK) {
      		return "" ;
      	} else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN) {
      		return new Boolean(cell.getBooleanCellValue()).toString() ;
      	} else if(cell.getCellType()==HSSFCell.CELL_TYPE_ERROR) {
      		return "" ;
      	} else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA) {
      		return Putil.getString(cell.getCellFormula()) ;
      	} else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC) {
      		if(type==1)
      			return new Integer((int)cell.getNumericCellValue()).toString() ;
      		else if(type==2)
      			return new Long((long)cell.getNumericCellValue()).toString() ;
      		else if(type==3)
      			return new Float((float)cell.getNumericCellValue()).toString() ;
      		else if(type==4)
      			return new Double((double)cell.getNumericCellValue()).toString() ;
      		else if(type==5)  //date
      			return Putil.format4(Putil.toDate((long)cell.getNumericCellValue())) ;
      		else
      			return new Long((long)cell.getNumericCellValue()).toString() ;
      			
    	} else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING) {
    		return Putil.getString(cell.getRichStringCellValue().getString()) ;
      	}
      	return "" ;
    }
    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
