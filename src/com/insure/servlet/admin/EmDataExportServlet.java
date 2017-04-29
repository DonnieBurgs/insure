package com.insure.servlet.admin;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.web.db.DbUtils;
import com.web.db.DbUtils2;
import com.web.servlet.Dispatcher;
import com.web.util.*;

public class EmDataExportServlet extends Dispatcher {

	private static final long serialVersionUID = 1491580695876L;

	public void blank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid"));
		request.setAttribute("uf_parentid", uf_parentid);
		String claimid = Putil.getString(request.getParameter("claimid"));
		request.setAttribute("claimid", claimid);
		String keyword = Putil.getString(request.getParameter("keyword"));
		int s = Putil.getInt(request.getParameter("s"));
		int m = Putil.getInt(request.getParameter("m"));
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m + "");
		request.setAttribute("s", s + "");
		request.setAttribute("claimtypeMap", EmClaimInfoServlet.claimtypeMap);

		forward(request, response, "/admin/DataExportAdd.jsp");

	}

	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String claimarchivenumber = Putil.getString(request.getParameter("claimarchivenumber"));
		String serialnumber = Putil.getString(request.getParameter("serialnumber"));
		String acceptdate = Putil.getString(request.getParameter("acceptdate"));
		String insuredname = Putil.getString(request.getParameter("insuredname"));
		String claimtype = Putil.getString(request.getParameter("claimtype"));

		String querySql = "select p.* from em_claimarchive ca, em_claim p, em_insured i, em_claiminfo ci where p.id>=0"
				+ " and ca.id = p.claimarchiveid and p.insuredid = i.id and p.id = ci.claimid"
				+ (claimarchivenumber.length() > 0 ? " and ca.claimarchivenumber like '%" + claimarchivenumber + "%'"
						: "")
				+ (acceptdate.length() > 0 ? " and ca.acceptdate = '" + acceptdate + "'" : "")
				+ (serialnumber.length() > 0 ? " and p.serialnumber like '%" + serialnumber + "%'" : "")
				+ (insuredname.length() > 0 ? " and i.insuredname like '%" + insuredname + "%'" : "")
				+ (claimtype.length() > 0 ? " and ci.claimtype '" + claimtype + "'" : "") + " order by p.id desc";

		System.out.println(querySql);
		List<Map<String, Object>> emClaimList = DbUtils.query(querySql);
		System.out.println(emClaimList.size());

		if (emClaimList.isEmpty()) {
			prompt(response, "找不到相关记录");
			return;
		}

		String filepath = "/export/" + System.currentTimeMillis() + "." + ExcelCommon.OFFICE_EXCEL_2003_POSTFIX;
		File uploadedFile = new File(request.getSession().getServletContext().getRealPath(filepath));
		if (!uploadedFile.getParentFile().exists()) {
			uploadedFile.getParentFile().mkdirs();
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet1 = workbook.createSheet("被保险人信息表");
		XSSFSheet sheet2 = workbook.createSheet("帐单表");
		XSSFSheet sheet3 = workbook.createSheet("帐单明细表");

		String[] sheet1Header = "被保险人序号（扫描号）,被保险人姓名,性别,证件类型,证件号码,出险原因,出险日期,保险金支付方式,出险类型1,出险类型2,出险类型3,索赔金额,疾病代码,就诊次数,单证张数,备注,在职/退休标记,出险人手机号,银行编码,银行账户名,银行账号,开户行所在省,开户行所在地(市),银行具体信息"
				.split(",");
		String[] sheet2Header = "帐单序号,被保险人序号,帐单类型,收据号/住院号,医院代码,医院名称,就诊日期/结帐日期/费用发生日期,费用类型（社保分割单）,入院日期,出院日期".split(",");
		String[] sheet3Header = "序号,帐单序号,费用项目代码,费用项目名称,费用金额,自费描述,自费金额,部分自付描述,部分自付金额,医保不支付金额,医保不支付金额原因,医保支付金额,医保支付金额原因"
				.split(",");

		XSSFRow row = sheet1.createRow(0);
		for (int i = 0; i < sheet1Header.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(sheet1Header[i]);
		}
		row = sheet2.createRow(0);
		for (int i = 0; i < sheet2Header.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(sheet2Header[i]);
		}
		row = sheet3.createRow(0);
		for (int i = 0; i < sheet3Header.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(sheet3Header[i]);
		}

		int rownum1 = 0, rownum2 = 0, rownum3 = 0;
		for (Map<String, Object> emClaim : emClaimList) {

			Map<String, Object> emInsured = DbUtils
					.queryOne("select p.* from em_insured p where p.id=" + emClaim.get("insuredid"));
			List<Map<String, Object>> emClaimInfoList = DbUtils
					.query("select p.* from em_claiminfo p where p.claimid=" + emClaim.get("id"));
			System.out.println("emClaimList: " + emClaimList.size());
			for (Map<String, Object> emClaimInfo : emClaimInfoList) {
				row = sheet1.createRow(++rownum1);

				short cellnum = 0;
				XSSFCell cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaim.get("serialnumber")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("insuredname")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("gender")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("idtype")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("idnumber")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("claimreason")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("claimdate_")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("paytype")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("insuretype1")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("insuretype2")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("insuretype3")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("spamount")));

				Map<String, Object> em_fee = QueryUtils.queryObject("em_disease", "id",
						String.valueOf(emClaimInfo.get("em_diseaseid")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(em_fee.get("diseasecode"))); ////

				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("determinecount")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("receiptcount")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaimInfo.get("mark")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("insuredtype")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("mobile")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("bankname")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("accountname")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("accountnumber")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("bankprovince")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("bankcity")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emInsured.get("subbranch")));
			}

			List<Map<String, Object>> emReceiptList = DbUtils
					.query("select p.* from em_receipt p where p.claimid=" + emClaim.get("id"));
			System.out.println("emReceiptList: " + emClaimList.size());
			for (Map<String, Object> emReceipt : emReceiptList) {

				row = sheet2.createRow(++rownum2);

				short cellnum = 0;
				XSSFCell cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("receiptno")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emClaim.get("serialnumber")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("receipttype")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("receiptnumber")));

				Map<String, Object> em_hospital = QueryUtils.queryObject("em_hospital", "id",
						String.valueOf(emReceipt.get("hospitalid")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(em_hospital.get("hospitalcode")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(em_hospital.get("hospitalname")));

				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("visitdate_")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("feetype")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("hospitaldate_")));
				cell = row.createCell(cellnum++);
				cell.setCellValue(String.valueOf(emReceipt.get("dischargedate_")));

				// sheet3

				List<Map<String, Object>> emReceiptInfoList = DbUtils
						.query("select p.* from em_receiptinfo p where p.receiptid=" + emReceipt.get("id"));
				for (Map<String, Object> emReceiptInfo : emReceiptInfoList) {
					row = sheet3.createRow(++rownum3);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(rownum3));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceipt.get("receiptno")));

					Map<String, Object> em_fee = QueryUtils.queryObject("em_fee", "id",
							String.valueOf(emReceiptInfo.get("feeid")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(em_fee.get("feecode")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(em_fee.get("feename")));

					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("fee")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("zfmark")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("zfamount")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("bfzfmark")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("bfzfamount")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("ybbzfamount")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("ybbzfmark")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("ybzfamount")));
					cell = row.createCell(cellnum++);
					cell.setCellValue(String.valueOf(emReceiptInfo.get("ybzfmark")));
				}
			}

		}

		workbook.write(new FileOutputStream(uploadedFile));

		request.setAttribute("filepath", filepath);

		forward(request, response, "/admin/DataExportResult.jsp");
	}

}
