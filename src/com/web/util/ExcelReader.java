package com.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public static String preStr1 = "em";
	public static String preStr2 = "Em";
    
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public void readExcel(String path) throws IOException {
        if (path == null || ExcelCommon.EMPTY.equals(path)) {
            return;
        } else {
            String postfix = getPostfix(path);
            if (!ExcelCommon.EMPTY.equals(postfix)) {
                if (ExcelCommon.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    readXls(path);
                } else if (ExcelCommon.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    readXlsx(path);
                }
            } else {
                System.out.println(path + ExcelCommon.NOT_EXCEL_FILE);
            }
        }
        return;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public void readXlsx(String path) throws IOException {
        System.out.println(ExcelCommon.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        String names[] = new String[50];
        String types[] = new String[50];
        String nameZns[] = new String[50];
        int nameCount = 0 ;
        String tablename = "", tablenameZn = "", author = "";
        // Read the Sheet
//        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if (xssfSheet == null) {
                return;
            }
        	File f = new File("doc/sql/db.sql") ;
        	if(!f.getParentFile().exists()) f.getParentFile().mkdirs();
    		FileWriter fo_sql = null;
        	try {
        		fo_sql = new FileWriter(f);
        	} catch(Exception e) {
        		Putil.printLog(e.toString());
        	}
        	File f_servlet = new File("doc/servlet") ;
        	if(!f_servlet.exists()) f_servlet.mkdirs();
        	File f_jsp = new File("doc/jsp") ;
        	if(!f_jsp.exists()) f_jsp.mkdirs();
        	File f_model = new File("doc/model") ;
        	if(!f_model.exists()) f_model.mkdirs();
            // Read the Row
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	XSSFCell tmp = xssfRow.getCell(0);
                    String tmps = getValue(tmp).trim() ;
                    if(tmps.equals("表名：")) {
                    	if(nameCount>0) {
                    		createTable(tablename, names, types, nameCount, fo_sql) ;
                    		createServlet(tablename, names, types, nameCount, author) ;
                      		createModel(tablename, names, types, nameCount, author) ;
                       		createList(tablename, tablenameZn, names, nameZns, types, nameCount, author) ;
                       		createAdd(tablename, tablenameZn, names, nameZns, types, nameCount, author) ;
                    		names = new String[50];
                    		types = new String[50];
                    		nameZns = new String[50];
                    		tablename = "";
                    		tablenameZn = "" ;
                    		author = "" ;
                    		nameCount = 0 ;
                    	}
                		tablenameZn = getValue(xssfRow.getCell(1)).trim();
                		tablename = getValue(xssfRow.getCell(2)).trim();
                		author = getValue(xssfRow.getCell(3)).trim();
                        Putil.printLog("point.1."+tablename);
                    } else if(tmps.length()==0) {
                    	if(nameCount>0) {
                    		createTable(tablename, names, types, nameCount, fo_sql) ;
                       		createServlet(tablename, names, types, nameCount, author) ;
                      		createModel(tablename, names, types, nameCount, author) ;
                       		createList(tablename, tablenameZn, names, nameZns, types, nameCount, author) ;
                       		createAdd(tablename, tablenameZn, names, nameZns, types, nameCount, author) ;
                       		names = new String[50];
                    		types = new String[50];
                    		nameZns = new String[50];
                    		tablename = "";
                    		tablenameZn = "" ;
                    		nameCount = 0 ;
                    	}
                    } else {
                    	names[nameCount] = tmps.toLowerCase();
                    	types[nameCount] = getValue(xssfRow.getCell(1)).trim();
                    	nameZns[nameCount] = getValue(xssfRow.getCell(3)).trim();
                    	nameCount++;
                    }
                    
                	
                } else {
                	if(nameCount>0) {
                		createTable(tablename, names, types, nameCount, fo_sql) ;
                   		createServlet(tablename, names, types, nameCount, author) ;
                  		createModel(tablename, names, types, nameCount, author) ;
                   		createList(tablename, tablenameZn, names, nameZns, types, nameCount, author) ;
                   		createAdd(tablename, tablenameZn, names, nameZns, types, nameCount, author) ;
                		names = new String[50];
                		types = new String[50];
                		nameZns = new String[50];
                		tablename = "";
                		tablenameZn = "" ;
                		author = "" ;
                		nameCount = 0 ;
                	}
                	
                }
            }
        	try {
        		fo_sql.close();
        	} catch(Exception e) {
        		Putil.printLog(e.toString());
        		
        	}
//        }
    }
    
    public void createAdd(String tablename, String tablenameZn, String names[], String nameZns[], String types[], int nameCount, String author) {
    	try {
    		File f = new File("doc/jsp/" + tablename + "Add.jsp");
    		f.createNewFile();
    		FileWriter fo = new FileWriter(f);
    		File f1 = new File("doc/jsp/" + tablename + "Edit.jsp");
    		f1.createNewFile();
    		FileWriter fo1 = new FileWriter(f1);
    		
    		String templateStr = Putil.readhtm(new File("templateAdd.jsp"), "utf-8") ;
    		String templateEditStr = Putil.readhtm(new File("templateEdit.jsp"), "utf-8") ;
    		templateStr = templateStr.replace("##ClassName##", tablename);
    		templateStr = templateStr.replace("##ClassNameLower##", tablename.toLowerCase());
    		templateStr = templateStr.replace("##TableNameZN##", tablenameZn);
    		templateStr = templateStr.replace("##AuthorNum1##", ((int)(Putil.getFloat(author))) + "");
    		templateStr = templateStr.replace("##AuthorNum2##", (((int)(Putil.getFloat(author)))+1) +"");
    		templateEditStr = templateEditStr.replace("##ClassName##", tablename);
    		templateEditStr = templateEditStr.replace("##ClassNameLower##", tablename.toLowerCase());
    		templateEditStr = templateEditStr.replace("##TableNameZN##", tablenameZn);
    		templateEditStr = templateEditStr.replace("##AuthorNum1##", ((int)(Putil.getFloat(author))) + "");
    		templateEditStr = templateEditStr.replace("##AuthorNum2##", (((int)(Putil.getFloat(author)))+1) +"");
    		String parametersAdd = "";
    		String parametersEdit = "";
    		String photoHidden = "";
    		String photoFile = "";
    		String parametersCheck = "";
    		int photocount = 0;
    		for(int i=0;i<nameCount;i++) {
    			String t = "", t1 = "", t2 = "";
    			if(types[i].startsWith("INT")) {
    				t = "1";
    			} else if(types[i].startsWith("FLOAT")) {
    				t = "2";
    			} else if(types[i].startsWith("DATETIME")) {
    				t = "3";
    			} else if(names[i].indexOf("photo")>=0 || names[i].indexOf("img")>=0 || names[i].indexOf("pic")==0 || names[i].indexOf("image")>=0 || names[i].indexOf("picture")>=0 || names[i].indexOf("icon")>=0) {
    				t = "4";
    			} else {
    				t = "";
    			}
    			if(i>0) {
    				if(t.equals("") || t.equals("1") || t.equals("2")) {
        				parametersAdd += "<tr>\r\n<td width=\"120\" class=\"right\"><font color=\"red\">*</font>" + nameZns[i] + "：</td>\r\n<td><input type=\"text\" id=\"" + names[i] + "\" name=\"" + names[i] + "\" value=\"\" size=50></td>\r\n</tr>\r\n" ;
        				parametersEdit += "<tr>\r\n<td width=\"120\" class=\"right\"><font color=\"red\">*</font>" + nameZns[i] + "：</td>\r\n<td><input type=\"text\" id=\"" + names[i] + "\" name=\"" + names[i] + "\" value=\"${fn:replace(item." + names[i] + ",'\"','&quot;')}\" size=50></td>\r\n</tr>\r\n" ;
    				} else if(t.equals("3")) {
        				parametersAdd += "<tr>\r\n<td width=\"120\" class=\"right\"><font color=\"red\">*</font>" + nameZns[i] + "：</td>\r\n<td><input type=\"text\" id=\"" + names[i] + "\" name=\"" + names[i] + "\" readonly=\"readonly\" value=\"\" onclick=\"WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})\" style=\"width: 70px;\"/></td>\r\n</tr>\r\n" ;
        				parametersEdit += "<tr>\r\n<td width=\"120\" class=\"right\"><font color=\"red\">*</font>" + nameZns[i] + "：</td>\r\n<td><input type=\"text\" id=\"" + names[i] + "\" name=\"" + names[i] + "\" readonly=\"readonly\" value=\"${item." + names[i] + "_}\" onclick=\"WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})\" style=\"width: 70px;\"/></td>\r\n</tr>\r\n" ;
    				} else if(t.equals("4")) {
    					parametersAdd += "<tr>\r\n<td width=\"120\" class=\"right\">" + nameZns[i] + "：</td>\r\n<td><a class=\"mya\" href=\"javascript:f_photo" + photocount + ".click();\"><img id=\"imgphoto" + photocount + "\" src=\"/resources/images/folder2.png\"></a></td>\r\n</tr>\r\n" ;
    					parametersEdit += "<tr>\r\n<td width=\"120\" class=\"right\">" + nameZns[i] + "：</td>\r\n<td><a class=\"mya\" href=\"javascript:f_photo" + photocount + ".click();\"><img id=\"imgphoto" + photocount + "\" src=\"<c:if test=\"${empty item." + names[i] + "}\">/resources/images/folder2.png</c:if><c:if test=\"${not empty item." + names[i] + "}\">/upload/images/${item." + names[i] + "}</c:if>\"></a></td>\r\n</tr>\r\n" ;
    					photoHidden += "<input type=\"hidden\" id=\"photo" + photocount + "\" name=\"photo" + photocount + "\"/>";
    					photoFile += "<input type=\"file\" id=\"f_photo" + photocount + "\" name=\"f_photo" + photocount + "\" onchange='_compress(this,$(\"#photo" + photocount + "\"),$(\"#imgphoto" + photocount + "\"));' style=\"width:0px;\"/>";
    					photocount++;
    				}
    				if(t.equals("") || t.equals("3")) {
    					parametersCheck += "	if(commonForm." + names[i] + ".value==\"\") {alert(\"请正确输入" + nameZns[i] + "！\");return false;}\r\n";
    				} else if(t.equals("1")) {
    					parametersCheck += "	if(commonForm." + names[i] + ".value==\"\" || commonForm." + names[i] + ".value!=\"\" && !is_int(commonForm." + names[i] + ".value)) {alert(\"请正确输入" + nameZns[i] + "！\");return false;}\r\n";
    				} else if(t.equals("2")) {
    					parametersCheck += "	if(commonForm." + names[i] + ".value==\"\" || commonForm." + names[i] + ".value!=\"\" && !(is_float(commonForm." + names[i] + ".value) || is_int(commonForm." + names[i] + ".value))) {alert(\"请正确输入" + nameZns[i] + "！\");return false;}\r\n";
    				} 
    			}
    		}
    		
    		templateStr = templateStr.replace("##ParametersAdd##", parametersAdd);
    		templateStr = templateStr.replace("##PhotoHidden##", photoHidden);
    		templateStr = templateStr.replace("##PhotoFile##", photoFile);
    		templateStr = templateStr.replace("##ParametersCheck##", parametersCheck);
    		templateEditStr = templateEditStr.replace("##ParametersEdit##", parametersEdit);
    		templateEditStr = templateEditStr.replace("##PhotoHidden##", photoHidden);
    		templateEditStr = templateEditStr.replace("##PhotoFile##", photoFile);
    		templateEditStr = templateEditStr.replace("##ParametersCheck##", parametersCheck);
    		
    		
    		fo.write(templateStr);
    		fo.flush();
    		fo.close();
    		fo1.write(templateEditStr);
    		fo1.flush();
    		fo1.close();
    	} catch(Exception e) {
    		Putil.printLog(e.toString());
    		
    	}
    	
    	
    }
    
    public void createList(String tablename, String tablenameZn, String names[], String nameZns[], String types[], int nameCount, String author) {
    	try {
    		File f = new File("doc/jsp/" + tablename + "List.jsp");
    		f.createNewFile();
    		FileWriter fo = new FileWriter(f);
    		
    		String templateStr = Putil.readhtm(new File("templateList.jsp"), "utf-8") ;
    		templateStr = templateStr.replace("##ClassName##", tablename);
    		templateStr = templateStr.replace("##ClassNameLower##", tablename.toLowerCase());
    		templateStr = templateStr.replace("##TableNameZN##", tablenameZn);
    		templateStr = templateStr.replace("##AuthorNum1##", ((int)(Putil.getFloat(author))) + "");
    		templateStr = templateStr.replace("##AuthorNum2##", (((int)(Putil.getFloat(author)))+1) +"");
    		String parametersTitle = "";
    		String parametersValue = "";
    		for(int i=0;i<nameCount;i++) {
    			String t = "", t1 = "", t2 = "";
    			if(types[i].startsWith("INT")) {
    				t = "";
    			} else if(types[i].startsWith("FLOAT")) {
    				t = "";
    			} else if(types[i].startsWith("DATETIME")) {
    				t = "d";
    			} else {
    				t = "";
    			}
    			if(i>0) {
    				if(i<6) {
        				parametersTitle += "<th>" + nameZns[i] + "</th>\r\n" ;
        				parametersValue += "<td>${item." + names[i] + (t.equals("d")?"_":"") + "}</td>\r\n" ;
    				}
    			}
    		}
    		
    		templateStr = templateStr.replace("##ParametersTitle##", parametersTitle);
    		templateStr = templateStr.replace("##ParametersValue##", parametersValue);
    		
    		
    		fo.write(templateStr);
    		fo.flush();
    		fo.close();
    	} catch(Exception e) {
    		Putil.printLog(e.toString());
    		
    	}
    	
    	
    }
    
    public void createServlet(String tablename, String names[], String types[], int nameCount, String author) {
    	try {
    		File f = new File("doc/servlet/" + preStr2 + "" + tablename + "Servlet.java");
    		f.createNewFile();
    		FileWriter fo = new FileWriter(f);
    		
    		String templateStr = Putil.readhtm(new File("templateServlet.java"), "utf-8") ;
    		templateStr = templateStr.replace("##ClassName##", tablename);
    		templateStr = templateStr.replace("##ClassNameLower##", tablename.toLowerCase());
    		templateStr = templateStr.replace("##Serial##", new Date().getTime()+"");
    		templateStr = templateStr.replace("##AuthorNum1##", ((int)(Putil.getFloat(author))) + "");
    		templateStr = templateStr.replace("##AuthorNum2##", (((int)(Putil.getFloat(author)))+1) +"");
    		String parameters = "";
    		String columnname = "";
    		String values = "";
    		String updateColumns = "";
    		String str_filename = "";
    		String str_fileSuc = "";
    		String str_fileSucA = "";
    		String str_if = "";
    		String str_if_update = "";
    		int photocount = 0;
    		for(int i=0;i<nameCount;i++) {
    			String t = "", t1 = "", t2 = "", t3 = "";
    			if(types[i].startsWith("INT")) {
    				t = "Putil.getInt";
    				t1 = "int";
    				t2 = "";
    			} else if(types[i].startsWith("FLOAT")) {
    				t = "Putil.getFloat";
    				t1 = "float";
    				t2 = "";
    			} else {
    				t = "Putil.getString";
    				t1 = "String";
    				t2 = "'";
    			}
    			t3 = "";
    			if(names[i].indexOf("photo")>=0 || names[i].indexOf("img")>=0 || names[i].indexOf("pic")==0 || names[i].indexOf("image")>=0 || names[i].indexOf("picture")>=0 || names[i].indexOf("icon")>=0) {
        			t3 = "4";
    			}
    			if(i>0) {
        			columnname += (columnname.length()>0?",":"") + names[i] ;
    				if(t3.equals("")) {
            			parameters += "			" + t1 + " " + names[i] + " = " + t + "(request.getParameter(\"" + names[i] + "\"));\r\n" ;
            			values += "				+ \"" + (values.length()>0?",":"") + t2 + "\" + " + names[i] + "" + (t2.equals("'")?".replace(\"'\", \"''\")":"") + " + \"" + t2 + "\"\r\n" ;
            			updateColumns += "					+ \"" + (updateColumns.length()>0?",":"") + names[i] + "=" + t2 + "\" + " + names[i] + "" + (t2.equals("'")?".replace(\"'\", \"''\")":"") + " + \"" + t2 + "\"\r\n" ;
        			} else if(t3.equals("4")) {
            			parameters += "			String photo" + photocount + " = Putil.getString(request.getParameter(\"photo" + photocount + "\")) ;\r\n";
            			values += "				+ \"" + (values.length()>0?",":"") + t2 + "\" + (fileSuc[" + photocount + "].length()>0?fileSuc[" + photocount + "]:\"\") + \"" + t2 + "\"\r\n" ;
            			updateColumns += "					+ (fileSuc" + photocount + "?\"," + names[i] + " = '\" + filename" + photocount + " + \"'\":\"\")\r\n" ;
            			str_filename += "			String filename" + photocount + " = \"p" + photocount + "\" + date.getTime()+\"\";\r\n";
            			str_fileSuc += "			boolean fileSuc" + photocount + " = false ;\r\n";
            			str_fileSucA += (str_fileSucA.length()>0?",":"") + "\"\"" ;
            			str_if += "			if(photo" + photocount + ".length()>10) {\r\n"
            				+ "				int iS = 0, iE = 0 ;\r\n"
            				+ "				iS = photo" + photocount + ".indexOf(\"image/\") ;\r\n"
            				+ "				iE = photo" + photocount + ".indexOf(\";\", iS) ;\r\n"
            				+ "				String fileEx = \"\" ;\r\n"
            				+ "				if(iS>0 && iE>iS) {\r\n"
            				+ "					fileEx = photo" + photocount + ".substring(iS+6, iE) ;\r\n"
            				+ "					if(fileEx.equals(\"jpeg\")) fileEx = \"jpg\" ;\r\n"
            				+ "					filename" + photocount + " += \".\" + fileEx ;\r\n"
            				+ "					iS = photo" + photocount + ".indexOf(\"base64,\") ;\r\n"
            				+ "					fileSuc" + photocount + " = PhotoUpload.savePhoto(request, photo" + photocount + ".substring(iS + 7), filename" + photocount + ");\r\n"
            				+ "					if(fileSuc" + photocount + ") {\r\n"
            				+ "						fileSuc[fileCount] = filename" + photocount + ";\r\n"
            				+ "						fileCount++;\r\n"
            				+ "					}\r\n"
            				+ "				}\r\n"
            				+ "			}\r\n";
            			str_if_update += "			if(photo" + photocount + ".length()>10) {\r\n"
                				+ "				int iS = 0, iE = 0 ;\r\n"
                				+ "				iS = photo" + photocount + ".indexOf(\"image/\") ;\r\n"
                				+ "				iE = photo" + photocount + ".indexOf(\";\", iS) ;\r\n"
                				+ "				String fileEx = \"\" ;\r\n"
                				+ "				if(iS>0 && iE>iS) {\r\n"
                				+ "					fileEx = photo" + photocount + ".substring(iS+6, iE) ;\r\n"
                				+ "					if(fileEx.equals(\"jpeg\")) fileEx = \"jpg\" ;\r\n"
                				+ "					filename" + photocount + " += \".\" + fileEx ;\r\n"
                				+ "					iS = photo" + photocount + ".indexOf(\"base64,\") ;\r\n"
                				+ "					fileSuc" + photocount + " = PhotoUpload.savePhoto(request, photo" + photocount + ".substring(iS + 7), filename" + photocount + ");\r\n"
                				+ "				}\r\n"
                				+ "			}\r\n";
            			photocount++ ;
           			}
    			}
    		}
    		
    		templateStr = templateStr.replace("##Parameter##", parameters);
    		templateStr = templateStr.replace("##ColumnName##", columnname);
    		templateStr = templateStr.replace("##Values##", values);
    		templateStr = templateStr.replace("##UpdateColumns##", updateColumns);
    		if(photocount>0) {
    			templateStr = templateStr.replace("##str_date##", "			Date date = new Date();\r\n");
    			templateStr = templateStr.replace("##str_filename##", str_filename);
    			templateStr = templateStr.replace("##str_fileSuc##", str_fileSuc);
    			templateStr = templateStr.replace("##str_fileSucA##", "			String fileSuc[] = {" + str_fileSucA + "};\r\n");
    			templateStr = templateStr.replace("##str_fileCount##", "			int fileCount = 0 ;\r\n");
    			templateStr = templateStr.replace("##str_if##", str_if);
    			templateStr = templateStr.replace("##str_if_update##", str_if_update);
    		} else {
    			templateStr = templateStr.replace("##str_date##", "");
    			templateStr = templateStr.replace("##str_filename##", "");
    			templateStr = templateStr.replace("##str_fileSuc##", "");
    			templateStr = templateStr.replace("##str_fileSucA##", "");
    			templateStr = templateStr.replace("##str_fileCount##", "");
    			templateStr = templateStr.replace("##str_if##", "");
    			templateStr = templateStr.replace("##str_if_update##", "");
        		
    		}
    		
    		fo.write(templateStr);
    		fo.flush();
    		fo.close();
    	} catch(Exception e) {
    		Putil.printLog(e.toString());
    		
    	}
    	
    	
    }
    
    public void createModel(String tablename, String names[], String types[], int nameCount, String author) {
    	try {
    		File f = new File("doc/model/" + preStr2 + "" + tablename + ".java");
    		f.createNewFile();
    		FileWriter fo = new FileWriter(f);
    		
    		String str = "";
    		for(int i=0;i<nameCount;i++) {
    			if(types[i].startsWith("INT")) {
    				if(names[i].toLowerCase().startsWith("is")) {
    					str += "\r\n	public boolean " + names[i].toLowerCase() + ";" ;
    				} else {
    					str += "\r\n	public long " + names[i].toLowerCase() + ";" ;
    				}
    			} else if(types[i].startsWith("FLOAT")) {
					str += "\r\n	public float " + names[i].toLowerCase() + ";" ;
    			} else {
					str += "\r\n	public String " + names[i].toLowerCase() + ";" ;
    			}
    		}
    		
    		str = "package com.juyou.app.model;\r\n\r\npublic class " + preStr2 + "" + tablename + " extends BaseModel {" + str + "\r\n}\r\n" ;
    		
    		fo.write(str);
    		fo.flush();
    		fo.close();
    	} catch(Exception e) {
    		Putil.printLog(e.toString());
    		
    	}
    	
    	
    }
    
    public void createTable(String tablename, String names[], String types[], int nameCount, FileWriter fo) {
    	try {
    		fo.write("CREATE TABLE `" + preStr1 + "_" + tablename.toLowerCase() + "` (\n");
    		fo.write("	`" + names[0] + "`    INT(10) NOT NULL AUTO_INCREMENT,\n");
    		for(int i=1;i<nameCount;i++) {
    			String t = "";
    			if(types[i].startsWith("INT") || types[i].startsWith("FLOAT")) {
    				t = " NOT NULL DEFAULT 0";
    			} else if(types[i].startsWith("VARCHAR")) {
    				t = " NULL";
    			} else if(types[i].startsWith("DATETIME")) {
    				t = " NULL";
    			}
    			fo.write("	`" + names[i] + "`    " + types[i] + t + ",\n");
    		}
    		fo.write("   primary key (`" + names[0] + "`),\n");
    		fo.write("   UNIQUE INDEX `" + names[0] + "` (`" + names[0] + "`)\n");
    		fo.write(") engine=myisam AUTO_INCREMENT=10001;\r\n\r\n");
    		fo.flush();
    	} catch(Exception e) {
    		Putil.printLog(e.toString());
    		
    	}
    	
    	
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public void readXls(String path) throws IOException {
        System.out.println(ExcelCommon.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        /*
        Student student = null;
        List<Student> list = new ArrayList<Student>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    student = new Student();
                    HSSFCell no = hssfRow.getCell(0);
                    HSSFCell name = hssfRow.getCell(1);
                    HSSFCell age = hssfRow.getCell(2);
                    HSSFCell score = hssfRow.getCell(3);
                    student.setNo(getValue(no));
                    student.setName(getValue(name));
                    student.setAge(getValue(age));
                    student.setScore(Float.valueOf(getValue(score)));
                    list.add(student);
                }
            }
        }
        return list;
        */
        
    }

//    private String getCellValue(XSSFRow xssfRow) {
//    	return getValue(xssfRow.get) ;
//    	
//    }
    
    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
    	if(xssfRow==null) return "";
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    public static String getPostfix(String path) {
        if (path == null || ExcelCommon.EMPTY.equals(path.trim())) {
            return ExcelCommon.EMPTY;
        }
        if (path.contains(ExcelCommon.POINT)) {
            return path.substring(path.lastIndexOf(ExcelCommon.POINT) + 1, path.length());
        }
        return ExcelCommon.EMPTY;
    }

    public static void main(String [] args) throws Exception {
    	new ExcelReader().readXlsx("保险数据字典.xlsx");


    }
    
}