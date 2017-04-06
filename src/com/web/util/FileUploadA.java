/*
 * Created on 2005-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.web.util;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.ServletRequestContext;


/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileUploadA {

	/**
	 * 
	 */
	public HashMap parameters = new HashMap();

	public HashMap files = new HashMap();

	public FileUploadA() {
	}

	public void parseRequest(HttpServletRequest req) {
		// FileUpload upload = new FileUpload();

		// Set upload parameters
		// upload.setSizeThreshold(yourMaxMemorySize);
		// upload.setRepositoryPath(yourTempDirectory);
		System.out.println("upload start.") ;
		
		RequestContext requestContext = new ServletRequestContext(req);
		// 判断是否包含 multipart 内容
		// if (ServletFileUpload.isMultipartContent(requestContext)) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(10000000);
		try {
			List items = upload.parseRequest(requestContext);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					// Log.info("formfield" + item.getFieldName() + "," + new
					// String(item.getString().getBytes("GBK"),"UTF-8")) ;
					addParameter(item);
				} else {
					addFile(item);
				}
			}

			upload = null;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	public void addParameter(FileItem item) {
		try {
			parameters.put(item.getFieldName(), item.getString("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getParameter(String name) {
		return Putil.getString(parameters.get(name));
	}

	public void addFile(FileItem item) {
		files.put(item.getFieldName(), item);
	}

	public FileItem getFile(String name) {
		return (FileItem) files.get(name);
	}

	public InputStream getInputStream(String name) {
		try {
			return ((FileItem) files.get(name)).getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String saveFile(String name, String filepath, String filename) {
		if (files.get(name) == null)
			return "";
		else
			return processUploadedFile((FileItem) files.get(name), filepath, filename);
	}

	public String saveFile(String name, String filepath) {
		if (files.get(name) == null)
			return "";
		else
			return processUploadedFile((FileItem) files.get(name), filepath);
	}

	public String processUploadedFile(FileItem item, String filepath) {
		String fieldName = item.getFieldName();
		String fileName = item.getName();
		String contentType = item.getContentType();
		String rValue = "";
		boolean isInMemory = item.isInMemory();
		long sizeInBytes = item.getSize();
		if (fileName.length() == 0)
			return "";
		// 限制文件大小
		if (sizeInBytes > 5000000)
			return "-1" + fileName;
		
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName
				.length());
		File fPath = new File(filepath);
		if (!fPath.exists())
			fPath.mkdirs();
		fPath = null;
		File uploadedFile = new File(filepath + fileName);
		try {
			item.write(uploadedFile);
		} catch (Exception e) {
			return "-2" + fileName;
		}

		rValue = fileName;

		return rValue;

	}

	public String processUploadedFile(FileItem item, String filepath, String desc_filename) {
		String fileName = "" ;
		String contentType = item.getContentType();
		String rValue = "";
		boolean isInMemory = item.isInMemory();
		long sizeInBytes = item.getSize();
		String tmpname = item.getName() ;
		if (tmpname.length() == 0)
			return "";
		String extname = "";
		if(tmpname.indexOf(".")>0) {
			extname = tmpname.substring(tmpname.indexOf(".")) ;
		}
		fileName = filepath + desc_filename + extname ;
		
		// 限制文件大小
		if (sizeInBytes > 5000000)
			return "-1" + fileName;
		System.out.println("fileName1:" + fileName) ;
		filepath = fileName.substring(0,fileName.lastIndexOf("/"));
		File fPath = new File(filepath);
		if (!fPath.exists())
			fPath.mkdirs();
		fPath = null;
		File uploadedFile = new File(fileName);
		try {
			item.write(uploadedFile);
		} catch (Exception e) {
			return "-2" + desc_filename;
		}

		rValue = desc_filename + extname;

		return rValue;

	}
}
