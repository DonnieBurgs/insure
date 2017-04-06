package com.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;

public class PhotoUpload {
	public static String FILE_PATH = "/upload/images/" ;
	
    //base64字符串转化成图片
    public static boolean savePhoto(HttpServletRequest request, String imgStr, String filename)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try 
        {
    		String filepath = FILE_PATH + filename ;
    		File tempFile = new File(request.getSession().getServletContext().getRealPath(filepath));
    		if (!tempFile.getParentFile().exists()) {
    			tempFile.getParentFile().mkdirs();
    		}
        	
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }

    		OutputStream out = new FileOutputStream(tempFile);    
            out.write(b);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {
        	e.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean savePhotoTxt(HttpServletRequest request, String imgStr, String filename)
    {   
        if (imgStr == null) //图像数据为空
            return false;
        try 
        {
    		String filepath = FILE_PATH + filename ;
    		File tempFile = new File(request.getSession().getServletContext().getRealPath(filepath));
    		if (!tempFile.getParentFile().exists()) {
    			tempFile.getParentFile().mkdirs();
    		}
        	
    		FileWriter out = new FileWriter(tempFile);    
            out.write(imgStr);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {
        	e.printStackTrace(System.out);
            return false;
        }
    }

}
