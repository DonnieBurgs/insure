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

public class EmDataServlet extends Dispatcher {

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

		forward(request, response, "/admin/DataAdd.jsp");

	}

	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uf_parentid = Putil.getString(request.getParameter("uf_parentid"));
		request.setAttribute("uf_parentid", uf_parentid);

		String keyword = Putil.getString(request.getParameter("keyword"));
		int s = Putil.getInt(request.getParameter("s"));
		int m = Putil.getInt(request.getParameter("m"));
		request.setAttribute("keyword", keyword);
		request.setAttribute("m", m + "");
		request.setAttribute("s", s + "");

		Map<String, Object> admin = LoginManager.getUser(request);
		if (admin == null) {
			return;
		}
		try {
			// Check that we have a file upload requestss
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				return;
			}

			int result = 0;
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraintsx
			factory.setSizeThreshold(1024); // yourMaxMemorySize
			String tempfilepath = "/upload/data/temp/";
			File tempPath = new File(request.getSession().getServletContext().getRealPath(tempfilepath));
			if (!tempPath.getParentFile().exists()) {
				tempPath.getParentFile().mkdirs();
			}
			tempPath.mkdir();
			System.out.println(tempPath.getAbsolutePath());
			factory.setRepository(tempPath); // yourTempDirectory

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// setProgressListener
			// upload.setProgressListener(new FileUploadProgressListener());

			// Parse the request
			List items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (items == null)
				return;
			// Process the uploaded items
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				// 整个表单的所有域都会被解析，要先判断一下是普通表单域还是文件上传域
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
					System.out.println(name + ":" + value);
				} else {
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					boolean isInMem = item.isInMemory();
					long sizeInBytes = item.getSize();
					System.out.println(fieldName + ":" + fileName);
					System.out.println("类型：" + contentType);
					System.out.println("是否在内在：" + isInMem);
					System.out.println("文件大小" + sizeInBytes);

					String filepath = "/upload/data/" + System.currentTimeMillis()
							+ ExcelCommon.OFFICE_EXCEL_2010_POSTFIX;
					File uploadedFile = new File(request.getSession().getServletContext().getRealPath(filepath));
					if (!uploadedFile.getParentFile().exists()) {
						uploadedFile.getParentFile().mkdirs();
					}
					try {
						System.out.println(uploadedFile.getAbsolutePath());
						item.write(uploadedFile);
						result = readData(uploadedFile);
						request.setAttribute("result", 0);
						request.setAttribute("msg", "成功处理：" + result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute("msg", e.getMessage());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			request.setAttribute("msg", e.getMessage());
		}

		forward(request, response, "/admin/DataResult.jsp");
	}

	int readData(File file) throws IOException, InvalidFormatException, SQLException {
		InputStream is = new FileInputStream(file.getAbsolutePath());
		Workbook workbook = WorkbookFactory.create(is);
		Sheet sheet = workbook.getSheetAt(0); // 示意访问sheet
		if (sheet == null) {
			return 0;
		}
		int max = sheet.getLastRowNum();
		int i = 1;
		Connection connection = DbUtils2.getConnection();
		StringBuilder sqlInsured = new StringBuilder(
				"insert into em_insured (insuredname,gender,idtype,idnumber,passport,birthdate,insuredtype,age,workage,employer,jobnumber,bankname,bankprovince,bankcity,subbranch,accountname,accountnumber,mobile,email,department,title) values ("
						+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		StringBuilder sqlInsurerPolicy = new StringBuilder(
				"insert into em_insurerpolicy (groupinsurancepolicyid,insuredid,insurancegroupid, insuranceid, attachedtoid,periodbegin,periodend,policystate,shistate,shiarea,joblocal,relation,jobnumber,bankname,accountname,accountnumber,clientid) values ("
						+ "?,?,?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement psInsured = connection.prepareStatement(sqlInsured.toString(),
				PreparedStatement.RETURN_GENERATED_KEYS);
		PreparedStatement psInsuredPolicy = connection.prepareStatement(sqlInsurerPolicy.toString());
		connection.setAutoCommit(false);
		for (; i < max; i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;

			Map<String, String> rowData = parseRow(row);
			if (rowData.isEmpty() || rowData.size() != columns.length)
				return -999;

			long[] ids = queryInfos(rowData.get(column_0), rowData.get(column_1), rowData.get(column_2),
					rowData.get(column_3), rowData.get(column_4));
			if (ids == null || ids.length == 0)
				return -998;

			psInsured.setString(1, rowData.get(column_5));
			psInsured.setString(2, rowData.get(column_8));
			psInsured.setString(3, rowData.get(column_6));
			psInsured.setString(4, rowData.get(column_7));
			psInsured.setString(5, "");
			psInsured.setDate(6, birthday(rowData.get(column_9)));
			psInsured.setString(7, "");
			psInsured.setString(8, rowData.get(column_9));
			psInsured.setString(9, rowData.get(column_10));
			psInsured.setString(10, "");
			psInsured.setString(11, rowData.get(column_11));
			psInsured.setString(12, rowData.get(column_16));
			psInsured.setString(13, rowData.get(column_17));
			psInsured.setString(14, rowData.get(column_18));
			psInsured.setString(15, rowData.get(column_19));
			psInsured.setString(16, rowData.get(column_14));
			psInsured.setString(17, rowData.get(column_15));
			psInsured.setString(18, rowData.get(column_20));
			psInsured.setString(19, rowData.get(column_21));
			psInsured.setString(20, rowData.get(column_12));
			psInsured.setString(21, rowData.get(column_13));
			psInsured.executeUpdate();
			long idInsured = -1;
			ResultSet rs = psInsured.getGeneratedKeys();
			if (rs.next()) {
				idInsured = rs.getLong(1);
			}
			if (idInsured == -1)
				return -997;

			System.out.println(ids.length);
			for(long df: ids){
				System.out.println(df);
			}
			psInsuredPolicy.setLong(1, ids[0]);
			psInsuredPolicy.setLong(2, idInsured);
			psInsuredPolicy.setLong(3, ids[1]);
			psInsuredPolicy.setLong(4, ids[2]);
			psInsuredPolicy.setLong(5, 0);
			psInsuredPolicy.setDate(6, parseDate(rowData.get(column_22)));
			psInsuredPolicy.setDate(7, parseDate(rowData.get(column_23)));
			psInsuredPolicy.setString(8, rowData.get(column_24));
			psInsuredPolicy.setString(9, "");
			psInsuredPolicy.setString(10, "");
			psInsuredPolicy.setString(11, "");
			psInsuredPolicy.setString(12, "");
			psInsuredPolicy.setString(13, rowData.get(column_11));
			psInsuredPolicy.setString(14, rowData.get(column_16));
			psInsuredPolicy.setString(15, rowData.get(column_14));
			psInsuredPolicy.setString(16, rowData.get(column_15));
			psInsuredPolicy.setString(17, "");
			psInsuredPolicy.executeUpdate();

		}
		connection.commit();
		connection.setAutoCommit(true);
		connection.close();
		return i;
	}

	Map<String, String> parseRow(Row row) {
		Map<String, String> map = new HashMap<>();
		int count = row.getLastCellNum();
		for (int i = 0; i < count; i++) {
			Cell cell = row.getCell(i);
			map.put(columns[i], ExcelReader.getValue(cell));
		}
		return map;
	}

	Date birthday(String age) {
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -Integer.parseInt(age));
		return new Date(calendar.getTimeInMillis());
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	Date parseDate(String d) {
		try {
			return new Date(dateFormat.parse(d).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date(0);
	}

	long[] queryInfos(String applicantcompanyname, String policynumber, String ywxpolicynumb, String insurancegroupname,
			String insurancename) {
		String sql = "SELECT a.id aid, b.id bid, c.id cid FROM emt.em_applicantCompany d, emt.em_groupinsurancepolicy a, emt.em_insurancegroup b, emt.em_insurance c \n"
				+ "where d.id = a.applicantcompanyid and a.id = b.policyid and a.id = c.policyid \n"
				+ " and d.applicantcompanyname = '" + applicantcompanyname + "'" + " and a.policynumber = '"
				+ policynumber + "'" + " and a.ywxpolicynumb = '" + ywxpolicynumb + "'"
				+ " and b.insurancegroupname = '" + insurancegroupname + "'" + " and c.insurancename = '"
				+ insurancename + "'";
//		System.out.println(String.format("queryInfos: %s", sql));
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		long[] result = null;
		try {
			connection = DbUtils.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				result = new long[] { rs.getLong("aid"), rs.getLong("bid"), rs.getLong("cid") };
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	static String column_0 = "em_applicantcompany.applicantcompanyname";
	static String column_1 = "em_groupInsurancepolicy.policynumber";
	static String column_2 = "em_groupInsurancepolicy.ywxpolicynumb";
	static String column_3 = "em_insurance.insurancetype";
	static String column_4 = "em_insurancegroup.insurancegroupname";
	static String column_5 = "em_insured.insuredname";
	static String column_6 = "em_insured.idtype";
	static String column_7 = "em_insured.idnumber";
	static String column_8 = "em_insured.gender";
	static String column_9 = "em_insured.age";
	static String column_10 = "em_insured.workage";
	static String column_11 = "em_insured.jobnumber";
	static String column_12 = "em_insured.department";
	static String column_13 = "em_insured.title";
	static String column_14 = "em_insured.accountname";
	static String column_15 = "em_insured.accountnumber";
	static String column_16 = "em_insured.bankname";
	static String column_17 = "em_insured.bankprovince";
	static String column_18 = "em_insured.bankcity";
	static String column_19 = "em_insured.subbranch";
	static String column_20 = "em_insured.mobile";
	static String column_21 = "em_insured.email";
	static String column_22 = "em_insurerPolicy.periodbegin";
	static String column_23 = "em_insurerPolicy.periodend";
	static String column_24 = "em_insurerPolicy.policystate";

	final String[] columns = { column_0, column_1, column_2, column_3, column_4, column_5, column_6, column_7, column_8,
			column_9, column_10, column_11, column_12, column_13, column_14, column_15, column_16, column_17, column_18,
			column_19, column_20, column_21, column_22, column_23, column_24 };
}
