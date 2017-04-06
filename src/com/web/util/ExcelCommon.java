package com.web.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCommon {

    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String LIB_PATH = "lib";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";
    
    public static XSSFCellStyle titleStyle;
    public static XSSFCellStyle titleStyle1; //无边框
    public static XSSFCellStyle leftStyle;
    public static XSSFCellStyle leftStyle1; //无边框
    public static XSSFCellStyle rightStyle;
    public static XSSFCellStyle centerStyle;
    public static XSSFFont font14;
    public static XSSFFont font16Bold;
    public static XSSFFont font12;
    public static XSSFFont font12Bold;
    
    public static XSSFCell setCell(XSSFSheet s, XSSFRow row, int colnum, String value) {
		return setCell(s, row, colnum, value, centerStyle, font12);
    }
    
    public static XSSFCell setCell(XSSFSheet s, XSSFRow row, int colnum, String value, XSSFCellStyle style) {
		return setCell(s, row, colnum, value, style, font12);
    }
    
    public static XSSFCell setCell(XSSFSheet s, XSSFRow row, int colnum, String value, XSSFCellStyle style, XSSFFont font) {
    	XSSFCell cell = null ;
		cell = row.createCell(colnum) ;
		cell.setCellValue(value);
		style.setFont(font);
		cell.setCellStyle(style);
		
		return cell;
    }
    
    public static void initStyle(XSSFWorkbook wb) {
    	titleStyle = wb.createCellStyle(); // 样式对象    
    	titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
    	titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
    	titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	titleStyle.setWrapText(true);
    	titleStyle1 = wb.createCellStyle(); // 样式对象    
    	titleStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
    	titleStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
    	titleStyle1.setWrapText(true);
    	leftStyle = wb.createCellStyle(); // 样式对象    
    	leftStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
    	leftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平   
    	leftStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	leftStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	leftStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	leftStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	leftStyle.setWrapText(true);
    	rightStyle = wb.createCellStyle(); // 样式对象    
    	rightStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
    	rightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 水平   
    	rightStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	rightStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	rightStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	rightStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	rightStyle.setWrapText(true);
    	centerStyle = wb.createCellStyle(); // 样式对象    
    	centerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
    	centerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平   
    	centerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	centerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	centerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	centerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	centerStyle.setWrapText(true);
    	
    	leftStyle1 = wb.createCellStyle(); // 样式对象    
    	leftStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
    	leftStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平   
    	leftStyle1.setWrapText(false);

    	font16Bold=wb.createFont();
    	font16Bold.setFontHeightInPoints((short)16);
    	font16Bold.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
    	font14=wb.createFont();
    	font14.setFontHeightInPoints((short)14);
    	font12=wb.createFont();
    	font12.setFontHeightInPoints((short)12);
    	font12Bold=wb.createFont();
    	font12Bold.setFontHeightInPoints((short)12);
    	font12Bold.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
       
    }
    
    

}