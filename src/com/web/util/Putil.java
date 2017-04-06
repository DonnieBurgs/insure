package com.web.util;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.db.DbUtils;



public class Putil {

  /**
   * <h3>.Putil封装一些常用方法操作</h3>
   * 所有方法都是静态方法，不需要生成此类的实例，
   * 为避免生成此类的实例，构造方法被申明为private类型的。
   */
  private Putil(){
  }

  public static HashMap<String,String> getOpenid(String code, String appid, String appSecret) {
	  	HashMap<String,String> r = new HashMap<String,String>() ;
		String tmp = Putil.downloadAfile("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code", "utf-8") ;
//		Putil.printLog("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code");
//		Putil.printLog(tmp);
		String openid = "",access_token = "" ;
		if(tmp!=null) {
			int i = tmp.indexOf("\"openid\"") ;
			int e = tmp.indexOf("\"", i+11) ;
			if(i>0 && e>i) {
				openid = tmp.substring(i+10, e) ;
			}
			i = tmp.indexOf("\"access_token\"") ;
			e = tmp.indexOf("\"", i+16) ;
			if(i>0 && e>i) {
				access_token = tmp.substring(i+16, e) ;
			}
		}
		r.put("openid", openid) ;
		r.put("access_token", access_token) ;
		
		return r ;
  }

  public static String getNickname(String openid, String access_token) {
	  	String nickname = "" ;
		if(access_token.length()>0) {
//			Putil.printLog(access_token);
//			Putil.printLog(openid);
			//String tmp = Putil.downloadAfile("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN", "utf-8") ;
			String tmp = Putil.downloadAfile("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN", "utf-8") ;
//			Putil.printLog(tmp);
			if(tmp!=null) {
				int i = tmp.indexOf("\"nickname\"") ;
				int e = tmp.indexOf("\"", i+13) ;
				if(i>0 && e>i) {
					nickname = tmp.substring(i+12, e) ;
//					Putil.printLog(nickname);
				}
			}
			
		}
		
		return nickname ;
  }
  
  
	//取相关职位，，allpos==1:包括上级，下级,,allpos==0:只取同级和下级
	public static List<Map<String, Object>> getPositionList(int companyid, int positionid, int allpos, int isdelete) {
		//向下查找
		List<Map<String, Object>> posList = DbUtils.query("select p.* from sc_position p where p.positionid>0"
				+ " and p.companyid=" + companyid
				+ (isdelete==99?"":" and p.isdelete=" + isdelete)
				+ (positionid>0?" and (p.positionid=" + positionid + " or p.path like '%," + positionid + ",%')":"")
				+ " order by p.path,p.seq desc");
		
		if(allpos==1) {
			//向上查找
			List<Map<String, Object>> poss = getPositionList_up(companyid, positionid);
			if(poss!=null) {
				if(posList==null) posList = new ArrayList<Map<String, Object>>();
				posList.addAll(poss);
			}
		}
		
		return posList;
		
	}
	
	public static List<Map<String, Object>> getPositionList_up(int companyid, int positionid) {
		if(positionid==0) return null;
		Map<String, Object> pos = DbUtils.queryOne("select * from sc_position where companyid=" + companyid + " and positionid=" + positionid);
		int parentid = Putil.getInt(pos.get("parentid")) ;
		if(parentid==0) return null;
		
		List<Map<String, Object>> posList = new ArrayList<Map<String, Object>>();
		int count=0;
		while(count++<=10) {
			pos = DbUtils.queryOne("select * from sc_position where companyid=" + companyid + " and positionid=" + parentid);
			parentid = Putil.getInt(pos.get("parentid")) ;
			posList.add(pos);
			if(parentid==0) break;
		}
		return posList;
	}
	
  	//取相关项目
	public static List<Map<String, Object>> getProjectList(int companyid, int positionid, int isdelete) {
		//List<Map<String, Object>> pList = new ArrayList<Map<String, Object>>();
		//Map<String, Map<String, Object>> pMap = new HashMap<String, Map<String, Object>>();
		//positionid ==0,查全部
		
		//向下查找
		List<Map<String, Object>> posList = DbUtils.query("select p.* from sc_position p where p.isproject=1"
				+ " and p.companyid=" + companyid
				+ (isdelete==99?"":" and p.isdelete=" + isdelete)
				+ (positionid>0?" and (p.positionid=" + positionid + " or p.path like '%," + positionid + ",%')":"")
				+ " order by p.path,p.seq desc");
		boolean hit = true;
		while(hit) {
			hit = false;
			for(Map<String, Object> p: posList) {
				int pid = Putil.getInt(p.get("parentid"));
				for(Map<String, Object> p1: posList) {
					if(Putil.getInt(p1.get("positionid"))==pid) {
						hit = true;
						posList.remove(p1);
						break;
					}
				}
				if(hit) break;
			}
			
		}
		
		//向上查找
		if(posList==null || posList.size()==0) {
			Map<String, Object> pos = getProjectList_up(companyid, positionid);
			if(pos!=null) {
				if(posList==null) posList = new ArrayList<Map<String, Object>>();
				posList.add(pos);
			}
		}
		
		return posList;
		
	}
	
	public static Map<String, Object> getProjectList_up(int companyid, int positionid) {
		if(positionid==0) return null;
		Map<String, Object> pos = DbUtils.queryOne("select * from sc_position where companyid=" + companyid + " and positionid=" + positionid);
		if(pos==null) return null;
		int parentid = Putil.getInt(pos.get("parentid")) ;
		if(parentid==0) return null;
		
		int count=0;
		while(count++<=10) {
			pos = DbUtils.queryOne("select * from sc_position where companyid=" + companyid + " and positionid=" + parentid);
			parentid = Putil.getInt(pos.get("parentid")) ;
			if(parentid==0) break;
			if(Putil.getInt(pos.get("isproject"))==1) {
				return pos;
			}
			
		}
		return null;
	}
	
	//取项目下的所有用户，并且为当前用户的同级或下级
	public static List<Map<String, Object>> getProjectUserList(int companyid, int userpositionid, int projectid, String usertype, int isdelete) {
		List<Map<String, Object>> userRows = DbUtils.query("select p.*,po.positionname,po.path,po.seq positionseq,c.companyname from sc_user p,sc_position po,sc_company c where p.positionid=po.positionid and p.companyid=c.companyid"
				+ " and p.positionid in (select positionid from sc_position where companyid=" + companyid + " and (positionid=" + projectid + " or path like '%," + projectid + ",%')"
				+ (isdelete==99?"":" and isdelete=" + isdelete)
				+ ")"
				+ (userpositionid>0?" and po.path like '%,"+userpositionid+",%'":"")
				+ (usertype.length()==0?"":" and p.usertype="+usertype)
				+ (isdelete==99?"":" and p.isdelete=" + isdelete)
				+ " and p.isvalid=1 and p.companyid=" + companyid + " order by po.seq desc,p.seq desc,p.userid desc");

		
		return userRows;
		
	}
	
	//取跟职位相关的人，allpos==1:包括上级，下级,,allpos==0:只取同级和下级
	public static List<Map<String, Object>> getPositionUserList(int companyid, int positionid, int allpos, String usertype, int isdelete) {
		String posStr = "";
		List<Map<String, Object>> posList = getPositionList(companyid, positionid, allpos, isdelete);
		if(posList!=null) {
			for(Map<String, Object> pos:posList) {
				posStr += (posStr.length()>0?",":"") + Putil.getString(pos.get("positionid")) ;
			}
		}
		if(posStr.length()>0) {
			List<Map<String, Object>> userRows = DbUtils.query("select p.*,po.positionname,po.path,po.seq positionseq,c.companyname from sc_user p,sc_position po,sc_company c where p.positionid=po.positionid and p.companyid=c.companyid"
					+ " and p.positionid in (" + posStr + ")"
					+ (usertype.length()==0?"":" and p.usertype="+usertype)
					+ (isdelete==99?"":" and p.isdelete=" + isdelete)
					+ " and p.isvalid=1 and p.companyid=" + companyid + " order by po.seq desc,p.seq desc,p.userid desc");

			
			return userRows;
			
		} else {
			return null;
		}
		
	}
	
	//取上级职位的人，
	public static List<Map<String, Object>> getParentUserList(int companyid, int positionid, String usertype, int isdelete) {
		if(positionid==0) return null;
		Map<String, Object> pos = DbUtils.queryOne("select * from sc_position where companyid=" + companyid + " and positionid=" + positionid);
		int parentid = Putil.getInt(pos.get("parentid")) ;

		if(parentid>0) {
			List<Map<String, Object>> userRows = DbUtils.query("select p.*,po.positionname,po.path,po.seq positionseq,c.companyname from sc_user p,sc_position po,sc_company c where p.positionid=po.positionid and p.companyid=c.companyid"
					+ " and p.positionid =" + parentid + ""
					+ (usertype.length()==0?"":" and p.usertype="+usertype)
					+ (isdelete==99?"":" and p.isdelete=" + isdelete)
					+ " and p.isvalid=1 and p.companyid=" + companyid + " order by po.seq desc,p.seq desc,p.userid desc");

			
			return userRows;
			
		} else {
			return null;
		}
		
	}
	
	public static Map<String, Object> getUser(String userid) {
		return getUser(userid, 1);
	}
	
	public static Map<String, Object> getUser(String userid, int isValid) {
		Map<String, Object> user = DbUtils.queryOne("select p.*,c.companyname,po.positionname from sc_user p, sc_company c, sc_position po"
				+ " where p.companyid=c.companyid and p.positionid=po.positionid and p.userid="+userid
				+ (isValid==99?"":" and p.isvalid=" + isValid) + "");
		return user ;
		
	}
	
	public static Map<String, Object> getSystemUser(String companyid, int isValid) {
		List<Map<String, Object>> userList = DbUtils.query("select p.*,c.companyname,po.positionname from sc_user p, sc_company c, sc_position po"
				+ " where p.companyid=c.companyid and p.positionid=po.positionid"
				+ " and po.parentid=0"
				+ " and p.companyid="+companyid
				+ (isValid==99?"":" and p.isvalid=" + isValid) + ""
				+ " order by p.userid");
		Map<String, Object> user = null;
		if(userList!=null && userList.size()>0) {
			user = userList.get(0);
		}
		return user ;
		
	}

    public static boolean isDateBetween(String startdate, String enddate) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        Calendar cend = Calendar.getInstance();
        cstart.setTime(Putil.toDate(startdate));
        cend.setTime(Putil.toDate(enddate));
        if(now.getTimeInMillis()<cstart.getTimeInMillis() || now.getTimeInMillis()>cend.getTimeInMillis())
            return false;
        else
            return true;

    }

    public static boolean isNowBefore(String date) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(Putil.toDate(date));

        if(now.getTimeInMillis()>=cstart.getTimeInMillis())
            return false;
        else
            return true;

    }

    public static boolean isNowBefore(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(date);

        if(now.getTimeInMillis()>=cstart.getTimeInMillis())
            return false;
        else
            return true;

    }

    public static boolean isNowBefore(String date, int minute) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(Putil.toDate(date));
        cstart.add(Calendar.MINUTE, minute);

        if(now.getTimeInMillis()>=cstart.getTimeInMillis())
            return false;
        else
            return true;

    }

    public static boolean isNowBefore(Date date, int minute) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(date);
        cstart.add(Calendar.MINUTE, minute);

        if(now.getTimeInMillis()>=cstart.getTimeInMillis())
            return false;
        else
            return true;

    }

    public static boolean isNowAfter(String date) {
        Calendar now = Calendar.getInstance();
        if(now.getTimeInMillis()<=Putil.toDate(date).getTime())
            return false;
        else
            return true;

    }

    public static boolean isNowAfter(Date date) {
        Calendar now = Calendar.getInstance();
        if(now.getTimeInMillis()<=date.getTime())
            return false;
        else
            return true;

    }

    public static boolean isNowAfter(String date, int minute) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(Putil.toDate(date));
        cstart.add(Calendar.MINUTE, minute);

        if(now.getTimeInMillis()<=cstart.getTimeInMillis())
            return false;
        else
            return true;


    }

    public static boolean isNowAfter(Date date, int minute) {
        Calendar now = Calendar.getInstance();
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(date);
        cstart.add(Calendar.MINUTE, minute);

        if(now.getTimeInMillis()<=cstart.getTimeInMillis())
            return false;
        else
            return true;

    }

	public static String downloadAfile(String urlstr) {
		return downloadAfile(urlstr, "gbk") ;
		
	}
	
	public static String downloadAfile(String urlstr, String encode, int retry) {
		while(true) {
			String tmp = downloadAfile(urlstr, encode) ;
			if(tmp.length()>0) return tmp ;
			retry -= 1 ;
			if(retry<=0)
				return "" ;
			else {
				try{
		          	Thread.sleep(400);
		        } catch(Exception e2){
		        }
			}
		}
	}
		
	public static String downloadAfile(String urlstr, String encode) {
		URL    url = null;
		try{
		   url = new URL(urlstr);
		}catch(Exception e){
		   return "" ;
		}
		
		StringBuffer sb = new StringBuffer();
		byte buf[] = new byte[8000000];
		int buf_pos = 0 ;
		try {
			InputStream ins = url.openStream();
			// FileInputStream fin = new FileInputStream(url) ;
			byte b[] = new byte[800000];
			int rleng = ins.read(b);
			while (rleng != -1) {
				buf = b_append(buf, buf_pos, b, rleng) ;
				buf_pos += rleng ;
				rleng = ins.read(b);
			}
			sb.append(new String(buf, 0, buf_pos, encode));
			buf = null ;
		} catch (Exception e) {
			System.out.println("downloadAfile:" + e.toString());
			return "";
		}

		return sb.toString();
	}
	
	public static String urlEncode(String str) {
		try{
			return java.net.URLEncoder.encode(str,"utf-8") ;
			
		}catch(Exception e) {}
		return "" ;
	}

	public static void printLog(String log) {
		System.out.println("[" + format2() + "]" + log) ;
	}
  
	public static String removeHtml(String content) {
	    content = content.replaceAll("</li>", "brbr").replaceAll("</p>", "brbr").replaceAll("</td>", "brbr").replaceAll("</table>", "brbr").replaceAll("</ul>", "brbr").replaceAll("\r\n", "brbr").replaceAll("\r", "brbr").replaceAll("\n", "brbr").replaceAll("<br>", "brbr").trim() ;
	    content = removeHtmlTab(content, "<", ">", 2000) ;
	    content = removeHtmlTab(content, "&", ";", 10) ;
	    content = content.replaceAll("brbrbrbrbrbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbr", "\n") ;
	    return content ;
	}
	
	public static String removeHtmlbutImg(String content) {
	    content = renameHtmlTab(content,"<img", ">") ;
	    content = content.replaceAll("</li>", "brbr").replaceAll("</p>", "brbr").replaceAll("</td>", "brbr").replaceAll("</table>", "brbr").replaceAll("</ul>", "brbr").replaceAll("\r\n", "brbr").replaceAll("\r", "brbr").replaceAll("\n", "brbr").replaceAll("<br>", "brbr").trim() ;
	    content = removeHtmlTab(content, "<", ">", 2000) ;
	    content = removeHtmlTab(content, "&", ";", 10) ;
	    content = content.replaceAll("imgstart", "<img").replaceAll("imgend", ">").replaceAll("brbrbrbrbrbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbrbrbr", "brbr").replaceAll("brbr", "\n") ;
	    return content ;
	}
	
	public static String removeHtmlTab(String content, String s, String e, int maxlen) {
	    int iS=0, iE=0;
	    while(true) {
	    	iS = content.indexOf(s) ;
	    	iE = content.indexOf(e, iS) ;
	    	if(iS>=0 && iE>0 && iE>iS && iE<(iS+maxlen)) {
	    		content = content.substring(0, iS) + content.substring(iE+e.length()) ;
	    	} else
	    		break ;
	    }
	    return content ;
	}
	
	public static String renameHtmlTab(String content, String s, String e) {
	    int iS=0, iE=0;
	    while(true) {
	    	iS = content.indexOf(s) ;
	    	iE = content.indexOf(e, iS) ;
	    	if(iS>=0 && iE>0 && iE>iS) {
	    		content = content.substring(0, iS) + "imgstart" + content.substring(iS+4, iE) + "imgend" + content.substring(iE+1) ;
	    	} else
	    		break ;
	    }
	    return content ;
	}
	
	public static String getPositionPath(int parentid) {
		String path = "," ;
		int count = 0 ;
		while(parentid>0 && count<20) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from sc_position p where p.positionid="+parentid);
			parentid = Putil.getInt(row.get("parentid")) ;
			path = "," + Putil.getInt(row.get("positionid")) + path;
			count++;
		}
		
		return ",0" + path+"" ;

	}

	
	public static String getAnyPath(String something, int parentid) {
		String path = "," ;
		int count = 0 ;
		while(parentid>0 && count<20) {
			Map<String, Object> row = DbUtils.queryOne("select p.* from sc_" + something + " p where p." + something + "id="+parentid);
			parentid = Putil.getInt(row.get("parentid")) ;
			path = "," + Putil.getInt(row.get("" + something + "id")) + path;
			count++;
		}
		
		return ",0" + path+"" ;

	}

  /**
   * 单个文件拷贝,从source到object
   */
  public static synchronized void fileCopy(java.io.File source,
                                           java.io.File object) throws java.
      io.IOException {
    if (!object.getParentFile().exists()) {
      object.getParentFile().mkdirs();
    }
    byte[] bt = new byte[1024];
    java.io.BufferedInputStream bi = new java.io.BufferedInputStream(new
        java.io.FileInputStream(source));
    java.io.BufferedOutputStream bo = new java.io.BufferedOutputStream(new
        java.io.FileOutputStream(object));
    for (; ; ) {
      int c = bi.read(bt);
      if (c == -1) {
        break;
      }
      bo.write(bt);
    }
  }

	
	public static String readhtm(File f) {
		return readhtm(f, "gbk") ;
	}
	
	public static String readhtm(File f, String encode) {
		boolean r = false ;
		
		StringBuffer sb = new StringBuffer();
		byte buf[] = new byte[8000000];
		int buf_pos = 0 ;
		try {
			FileInputStream ins = new FileInputStream(f) ;
			// FileInputStream fin = new FileInputStream(url) ;
			byte b[] = new byte[800000];
			int rleng = ins.read(b);
			while (rleng != -1) {
				r = true;
				buf = b_append(buf, buf_pos, b, rleng) ;
				buf_pos += rleng ;
				rleng = ins.read(b);
			}
			sb.append(new String(buf, 0, buf_pos, encode));
			buf = null ;
		} catch (Exception e) {
			System.out.println("downloadAfile:" + e.toString());
			return "";
		}

		return sb.toString();
	}
	
  /**
   * 得到一个文件夹内的所有文件列表
   * @param dir File给定要操作的文件目录
   * @param arr ArrayList 操作dir目录时要填充的集合
   * <br>
   * 本方法对调用者并不实用，实用的是下一个list方法.
   */
  public static void listDir(java.io.File dir, java.util.ArrayList arr) {
    if (dir.isDirectory()) {
      java.io.File[] filenames = dir.listFiles();
      if (filenames != null) {
        for (int i = 0; i < filenames.length; i++) {
          listDir(filenames[i], arr);
        }
      }
    }
    else {
      arr.add(dir);
    }
  }

  /**
   * 得到一个文件夹内的所有文件列表.
   * @param dir String 指定要操作的目录字串
   * @return java.io.File[] 返回一个文件列表，
   * 包括目录及子目录下的所有文件.<br>
   * 不包括子目录本身
   * 客户端需调用本方法
   * <code>null</code> is return if String dir is not exist;
   */
  public static java.io.File[] list(String dir) {
    java.io.File file = new java.io.File(dir);
    if (!file.exists()) return null;
    java.util.ArrayList arrlist = new java.util.ArrayList();
    listDir(file, arrlist);
    java.io.File[] files = new java.io.File[arrlist.size()];
    files = (java.io.File[]) arrlist.toArray(files);
    return files;
  }

  /**
   * 删除一个文件或一个文件夹内的所有内容.
   * @param dir File 指定要操作的目录或文件
   */
  public static void delFile(java.io.File dir) throws java.io.IOException {
    if ( (dir == null)) {
      throw new IllegalArgumentException("Argument " + dir +
                                         " is not a directory. ");
    }
    if (!dir.isDirectory()){
      dir.delete();
      return;
    }
    java.io.File[] entries = dir.listFiles();
    int sz = entries.length;
    for (int i = 0; i < sz; i++) {
      if (entries[i].isDirectory()) {
        delFile(entries[i]);
      }
      else {
        entries[i].delete();
      }
    }
    dir.delete();
  }


  /**
   *得到当前硬盘盘符,即pysh.Putil这个类所在硬盘的盘符。
   */
  public static String getDiskVOL() {
    String[] command = {
        "cmd.exe", "/C", "vol"};
    String line;
    StringBuffer retn = new StringBuffer();
    try {
      Process process = Runtime.getRuntime().exec(command);
      java.io.InputStreamReader ir = new java.io.InputStreamReader(
          process.getInputStream());
      java.io.LineNumberReader Input = new java.io.LineNumberReader(ir);
      while ( (line = Input.readLine()) != null) {
        retn = retn.append(line);
      }
    }
    catch (java.io.IOException e) {
      retn = retn.delete(0, retn.length());
      retn = retn.append("error");
    }
    return retn.substring(retn.length() - 9, retn.length());
  }

  /**
   * 把对象转换为int数值.
   * @param obj 包含数字的对象.
   * @return int 转换后的数值,对不能转换的对象返回0。
   */
  public static int getInt(Object obj) {
      int val;
      val = 0;
      try {
        if(obj == null)
           return val;
        if(obj instanceof String) {
          val = Integer.parseInt((String) obj);
        } else if (obj instanceof Integer) {
            val = ( (Integer) obj).intValue();
        } else if (obj instanceof Long) {
            val = ( (Long) obj).intValue();
        } else if (obj instanceof Float) {
            val = ( (Float) obj).intValue();
        } else {
        	val = Integer.parseInt(obj.toString());
        }
//        val = Integer.parseInt(obj.toString());
        return val;
      }
      catch (Exception e) {
//      	System.out.println(e.toString()) ;
        return 0 ;
      }
  }

  /**
   * 把对象转换为long数值.
   * @param obj 包含数字的对象.
   * @return long 转换后的数值,对不能转换的对象返回0。
   */
  public static long getLong(Object obj) {
  	long val;
      val = 0;
      try {
        if(obj == null)
           return val;
        if(obj instanceof String) {
          val = Long.parseLong((String) obj);
        } else if (obj instanceof Integer) {
            val = ( (Integer) obj).intValue();
        } else if (obj instanceof Long) {
            val = ( (Long) obj).longValue();
        } else if (obj instanceof Float) {
            val = ( (Float) obj).longValue();
        } else {
        	val = Long.parseLong(obj.toString());
        }
        return val;
      }
      catch (Exception e) {
//      	System.out.println(e.toString()) ;
        return 0 ;
      }
  }
  
  public static float getFloat(Object obj) {
	  float val;
      val = 0;
      try {
        if(obj == null)
           return val;
        if(obj instanceof String) {
          val = Float.parseFloat((String) obj);
        } else if (obj instanceof Integer) {
            val = ( (Integer) obj).intValue();
        } else if (obj instanceof Long) {
            val = ( (Long) obj).longValue();
        } else if (obj instanceof Float) {
            val = ( (Float) obj).floatValue();
        } else {
        	val = Float.parseFloat(obj.toString());
        }
        return val;
      }
      catch (Exception e) {
//      	System.out.println(e.toString()) ;
        return 0 ;
      }
  }
  
  public static double getDouble(Object obj) {
	  double val;
      val = 0;
      try {
        if(obj == null)
           return val;
        if(obj instanceof String) {
          val = Double.parseDouble((String) obj);
        } else if (obj instanceof Integer) {
            val = ( (Integer) obj).intValue();
        } else if (obj instanceof Long) {
            val = ( (Long) obj).longValue();
        } else if (obj instanceof Float) {
            val = ( (Float) obj).floatValue();
        } else {
        	val = Double.parseDouble(obj.toString());
        }
        return val;
      }
      catch (Exception e) {
//      	System.out.println(e.toString()) ;
        return 0 ;
      }
  }

  /**
   * 计算对象大小,即占用内存的大小.
   * @param obj 包含数字的对象.
   * @return int 对象大小,对计算异常的对象返回-1。
   */
  public static int getObjectSize(Object o)
  {
      int size;
      try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.flush();
        size = baos.size();
        oos.close();
        return size;
      }
      catch (Exception e) {
        return -1;
      }
  }

  /**
   * 把对象转换为字符串。
   * @param obj 需转换的对象。
   * @return String 转换后的字符串。
   */
  public static String getString(Object obj)
  {
      String str = "";
      if(obj==null) return str ;
//      if((obj instanceof String))
          str = obj.toString();
      return str;
  }

  /**
   * 判断一字符串是否手机号码。长度0为合法
   * @param mobile 手机号码。
   * @return boolean 是手机号码返回true,否则返回false。
   */
  public static boolean isMobile(String mobile)
  {
	  if(mobile==null) return false ;
	  if(!mobile.startsWith("1")) return false ;
	  if(mobile.startsWith("130") || mobile.startsWith("131") || mobile.startsWith("132") || mobile.startsWith("133")) return false ;
	  if(!(mobile.length() == 11 || mobile.length()==0)) return false ;
	  if(!isNumber(mobile)) return false ;
      return true ;
  }
  
  /**
   * 判断一字符串是否手机号码。长度0为非法
   * @param mobile 手机号码。
   * @return boolean 是手机号码返回true,否则返回false。
   */
  public static boolean isMobile1(String mobile)
  {
      return mobile != null && (mobile.startsWith("134") || mobile.startsWith("135") || mobile.startsWith("136") || mobile.startsWith("137") || mobile.startsWith("138") || mobile.startsWith("139") || mobile.startsWith("150") || mobile.startsWith("152") || mobile.startsWith("157") || mobile.startsWith("158") || mobile.startsWith("159") || mobile.startsWith("188")) && mobile.length() == 11 && isNumber(mobile);
  }

  /**
   * 作用:取出一个指定长度大小的随机正整数.
   * @param length int 设定所取出随机数的长度。length小于11
   * @return int 返回生成的随机数。
   */
  public static int buildRandom(int length) {
    int num = 1;
    double random = Math.random();
    if (random < 0.1) {
      random = random + 0.1;
    }
    for (int i = 0; i < length; i++) {
      num = num * 10;
    }
    return (int) ( (random * num));
  }

  /**
   * 对一个double型数据按四舍五入的方法得到另一个double数据。
   * @param d 指定的double数据。
   * @param dot 指定取小数点以后的几位小数。
   * @return double
   * 如 toRound(3.145,2) 返回 3.14
   */
  public static double toRound(double d, int dot) {
    java.math.BigDecimal big = new java.math.BigDecimal(d);
    big = big.setScale(dot, java.math.BigDecimal.ROUND_HALF_UP);
    return big.doubleValue ();
  }
  
  public static float toRound(float d, int dot) {
	    java.math.BigDecimal big = new java.math.BigDecimal(d);
	    big = big.setScale(dot, java.math.BigDecimal.ROUND_HALF_UP);
	    return big.floatValue() ;
  }

  /**
   * 判断一个整数的奇偶性
   * @param num int 整数。
   * @return int 奇数返回false,偶数返回true。
   */
  public static boolean isOdd(int num) {
    java.math.BigInteger b = new java.math.BigInteger("2");
    java.math.BigInteger a = new java.math.BigInteger("" + num);
    return (a.mod(b).intValue() == 0 ? true : false);
  }

  /**
   * 格式化日期和时间 字符表示法
   * @return String 返回日期字符串
   */
  public static String format()
  {
      return format(null);
  }

  /**
   * 格式化日期和时间 字符表示法
   * @return dateString 如 2001-3-15 23:29:39
   */
  /*
  public static String format(java.util.Date date) {
    java.text.DateFormat df = java.text.SimpleDateFormat.getDateTimeInstance(
        java.text.DateFormat.MEDIUM, java.text.DateFormat.SHORT,
        java.util.Locale.CHINESE);
    return df.format(date);
  }
  */
  public static String format(Date date)
  {
      if(date == null)
          date = new Date();
      DateFormat df = DateFormat.getDateInstance(2, Locale.CHINESE);
      StringBuffer s_date = new StringBuffer(df.format(date));
      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      s_date.append(" ").append(cal.get(11)).append(":").append(cal.get(12));
      return s_date.toString();
  }

  /**
   * 格式化日期和时间 字符表示法
   * @return String 返回日期字符串
   */
  public static String format2()
  {
      return format2(null);
  }

  /**
   * 格式化日期和时间 字符表示法
   * @param date 日期对象。
   * @return String 返回日期字符串,如：2002-10-03 12:45:01。
   */
  public static String format2(Date date)
  {
      StringBuffer s_date = new StringBuffer();
      Calendar cal = new GregorianCalendar();
      if(date != null)
          cal.setTimeInMillis(date.getTime());
      int year = cal.get(1);
      s_date.append(year).append("-");
      int month = cal.get(2) + 1;
      s_date.append(month >= 10 ? "" : "0").append(month).append("-");
      int day = cal.get(5);
      s_date.append(day >= 10 ? "" : "0").append(day).append(" ");
      int hour = cal.get(Calendar.HOUR_OF_DAY);
      s_date.append(hour >= 10 ? "" : "0").append(hour).append(":");
      int minute = cal.get(Calendar.MINUTE);
      s_date.append(minute >= 10 ? "" : "0").append(minute).append(":");
      int second = cal.get(Calendar.SECOND);
      s_date.append(second >= 10 ? "" : "0").append(second);
      return s_date.toString();
  }
  
  public static String format2(long date)
  {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return format.format(new Date(date));
  }
  public static String format4(long date)
  {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      return format.format(new Date(date));
  }
  public static String format5(long date)
  {
	  SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
      return format.format(new Date(date));
  }

  /**
   * 格式化日期和时间 字符表示法
   * @return String 返回日期字符串
   */
  public static String format3()
  {
      return format3();
  }

  /**
   * 格式化日期和时间 字符表示法
   * @param date 日期对象。
   * @return String 返回日期字符串
   */
  public static String format3(Date date)
  {
      if(date == null)
          date = new Date();
      DateFormat df = DateFormat.getDateInstance(2, Locale.CHINESE);
      StringBuffer s_date = new StringBuffer(df.format(date));
      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      s_date.append(" ").append(cal.get(11)).append(":").append(cal.get(12)).append(cal.get(13));
      return s_date.toString();
  }

  /**
   * 格式化日期和时间 字符表示法
   * @return String 返回日期字符串
   */
  public static String format4()
  {
      return format4(null);
  }

  /**
   * 格式化日期和时间 字符表示法
   * @param date 日期对象。
   * @return String 返回日期字符串 如 2001-3-15
   */
  public static String format4(Date date)
  {
      if(date == null)
          date = new Date();
      StringBuffer s_date = new StringBuffer();
      Calendar cal = new GregorianCalendar();
      cal.setTime(date) ;

      int year = cal.get(1);
      s_date.append(year).append("-");
      Calendar _tmp1 = cal;
      int month = cal.get(2) + 1;
      s_date.append(month >= 10 ? "" : "0").append(month).append("-");
      Calendar _tmp2 = cal;
      int day = cal.get(5);
      s_date.append(day >= 10 ? "" : "0").append(day);
      return s_date.toString();
  }
 
  /**
   * 格式化日期(FULL) 舍弃时间  字符表示法
   * @param java.util.Date date
   * @return java.util.String
   * 如 <b>2003-3-15</b>
   */
  public static String formatShort(java.util.Date date) {
    java.text.DateFormat df = java.text.SimpleDateFormat.getDateInstance(
        java.text.DateFormat.MEDIUM, java.util.Locale.CHINA);
    return df.format(date);
  }

  /**
   * 格式化日期(FnLL)和时间(FnLL) 中文表示法
   * @param java.util.Date date
   * @return java.util.String
   * 日期和时间均采用中国地区12小时制表示法<br>
   * 如 <b>2003年3月15日 下午11时41分52秒</b>
   *
   */
  public static String formatFULL(java.util.Date date) {
    java.text.DateFormat df = java.text.SimpleDateFormat.
        getDateTimeInstance(
        java.text.DateFormat.MEDIUM, java.text.DateFormat.LONG,
        java.util.Locale.CHINA);
    return df.format(date);
  }

  /**
   * 格式化日期(FULL) 舍弃时间 带星期 中文表示法
   * @param java.util.Date date
   * @return java.util.String
   * 如 <b>2003年3月16日 星期日</b>
   */
  public static String formatWeek(java.util.Date date) {
    java.text.DateFormat df = java.text.SimpleDateFormat.getDateInstance(
        java.
        text.DateFormat.FULL, java.util.Locale.CHINA);
    return df.format(date);
  }
	
	public static String getCookie(HttpServletRequest req, HttpServletResponse res, String attr) {
		
	    Cookie[] cookies = req.getCookies();
	    String name = "";
	    String value = "";
	    for (int i = 0; cookies!=null && i < cookies.length; i++) {
	      Cookie cookie = cookies[i];
	      name = Putil.getString(cookie.getName());

	      if(attr.equals(name)){
	    	  value = Putil.getString(cookie.getValue());
//			    System.out.println(name + "," + (Parameter.p_ckid[web_id] + attr) + "," +value) ;
	    	  break ;
	      }
	    }
	    return value ;
	}
	
	public static void setCookie(HttpServletRequest req, HttpServletResponse res, String attr, String value, int term) {

		Cookie cookie  = null;
	    cookie = new Cookie(attr, value);
	    if(term<=0) term = 4*60*60 ;
	    cookie.setMaxAge(term);
//	    System.out.println((Parameter.p_ckid + attr) + "," +value) ;
	    res.addCookie(cookie);
//	    System.out.println(Putil.getCookie(req,res,"_cart_ojlist")+ "ssss");
	}

  /**
   * 把对象转换为boolean数值.
   * @param obj 包含数字的对象.
   * @return boolean "Y"-true,"N"-false,"1"-true,other-false。
   */
  public static boolean getBoolean(Object obj)
  {
      if(obj == null)
          return false;
      String str = getString(obj);
      if("1".equals(str) || "Y".equalsIgnoreCase(str))
          return true;
      if("0".equals(str) || "N".equalsIgnoreCase(str))
          return false;
      int var = getInt(obj);
      return var == 1;
  }
  
  /**
   * 分割字符串得到List
   * @param str 传入的字串
   * @param c 分隔符 如 ','
   * @return ArrayList 其中包含了 str 以 ',' 分隔的字串值
   * <p>例如 toList("a,b,c,d",',')进行转换后得到ArrayList的值为
   * <p> "a"
   * <p> "b"
   * <p> "c"
   * <p> "d"
   */
  public static java.util.ArrayList<String> splitStringToList(String str, String delim) {
	  java.util.ArrayList<String> list = new java.util.ArrayList<String>();
	  String[] arryStr = str.split(delim);
	  for(int i = 0; i < arryStr.length; i++) {
		  if(arryStr[i].length()>0) list.add(arryStr[i]);
	  }
	  return list;
  }

  /**
   * 格式化日期和时间 字符表示法
   * @param obj 日期对象。
   * @return String 返回日期字符串
   */
  public static String getDateString(Object obj)
  {
      String date = "";
      if(obj == null)
          return date;
      if(obj instanceof String)
      {
          date = obj.toString();
          return date;
      }
      if(obj instanceof Timestamp)
      {
          Timestamp ts = (Timestamp)obj;
          return ts.toString();
      } else
      {
          return date;
      }
  }
  
  public static long getDateSeq(Date d) {
	  if(d==null) new Date().getTime() ;
	  return d.getTime() ;
  }
  
  public static long getDateSeq(String d) {
	  if(d==null) new Date().getTime() ;
	  return toDate(d).getTime() ;
  }

	public static String formatDateStr(int year, int month, int day) {
		return year + "-" + (month>9?month+"":"0"+month) + "-" + (day>9?day+"":"0"+day);
	}
  
	public static java.util.Date toAnyDate(String issuedate, String hour, String minute) {
		Calendar c = Calendar.getInstance() ;
		ArrayList<String> list = Putil.splitStringToList(issuedate, "-");
		
		int year = Putil.toInt(list.get(0));
		int month = Putil.toInt(list.get(1));
		int day = Putil.toInt(list.get(2));
		int hourOfDay = Putil.toInt(hour);
		int m = Putil.toInt(minute);
		
		c.set(Calendar.YEAR,year);
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DATE, day);
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, m);
		c.set(Calendar.SECOND, 0);		
		
		Date date = c.getTime();
		
		return date;
	}

  /**
   * 输入日期型字串，对其解析成日期型对象
   * @param dateString 如 2001-3-15 23:29:39
   * @return java.util.Date
   */
  public static java.util.Date toDate(String dateString) {
	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  
    java.util.Date date = null;
    try {
    	if(dateString.indexOf(":")>0)
    		date = timeFormat.parse(dateString);
    	else
    		date = dateFormat.parse(dateString);
    }
    catch (Exception e) {
    	e.printStackTrace() ;
    }
    if(date==null) return new java.util.Date() ;
    return date;
  }
  
  public static java.util.Date toDate(long dateseq) {
	  Calendar c = Calendar.getInstance() ;
	  if(dateseq<=10226)
		  c.setTimeInMillis(-2177481951000l + (dateseq-368+1)*86400000) ;
	  else
		  c.setTimeInMillis(-2177481951000l + (dateseq-368+2)*86400000) ;
	  return c.getTime() ;
  }

  public static java.sql.Date toSQLDate(String dateString) {
    if (dateString == null || dateString.equals("")) {
      return new java.sql.Date(new java.util.Date().getTime()) ;
    }
    if(dateString.length()<=10) dateString = dateString + " 00:00:01" ;
    java.text.DateFormat df = java.text.SimpleDateFormat.
        getDateTimeInstance();
    java.util.Date date = null;
    try {
      date = df.parse(dateString);
    }
    catch (Exception e) {
    	e.printStackTrace() ;
    }
    if(date==null)
    	return new java.sql.Date(new java.util.Date().getTime()) ;
    else
    	return new java.sql.Date(date.getTime()) ;
  }

  /**
   * 输入年，月，日，时，分，秒 构成日期型对象
   * @return java.util.Date
   */
  public static java.util.Date toDate(String year, String MONTH, String day,
                                      String HOUR,
                                      String miunte, String SECOND) {
    return toDate(toDateString(year, MONTH, day, HOUR, miunte, SECOND));
  }

  /**
   * 输入年，月，日 构成日期型对象
   * @return java.util.Date
   */
  public static java.util.Date toDate(String year, String MONTH, String day) {
    return toDate(toDateString(year, MONTH, day));
  }

  /**
   * 输入年，月，日，时，分，秒 构成日期样式的日期字串
   * @return dataString 如 2001-3-15 23:29:39
   */
  public static String toDateString(String year, String MONTH, String day,
                                    String HOUR, String minute, String SECOND) {
    String dateString = year + "-" + MONTH + "-" + day;
    if (HOUR != null && minute != null && SECOND != null) {
      dateString += " " + HOUR + ":" + minute + ":" + SECOND;
    }
    return dateString;
  }

  /**
   * 输入年，月，日 构成日期样式的日期字串
   * @return dataString 如 2001-3-15
   */
  public static String toDateString(String year, String MONTH, String day) {
    return toDateString(year, MONTH, day, null, null, null);
  }

  /**
   * 把日期型对象转换成SQL语句能理解的更新形式
   * @param date java.util.Date
   * @return String
   * <code><br>
   * 对于SQLServer
   * date: new Date();
   * SQL : convert(datetime,'2001-3-15 23:29:39')
   * </code>
   */
  public static String toSql(java.util.Date date) {
    if (date == null) {
      return "convert(datetime,null)";
    }
    return "convert(datetime,'" + format(date) + "')";
  }

  /**
   * 把对象转换为int数值.
   * @param obj 包含数字的对象.
   * @return int 转换后的数值,对不能转换的对象返回0。
   */
  public static int toInt(Object obj)
  {
      int a = 0;
 	  try{
 	        if(obj != null)
            a = Integer.parseInt(obj.toString());
	  } catch(Exception e) {
	  
	  }
      return a;
  }

  public static float toFloat(Object obj)
  {
  	  float a = 0;
  	  try {
  	  	if(obj != null)
          a = Float.parseFloat(obj.toString());
  	  } catch(Exception e) {
  	  
  	  }
      return a;
  }

  /**
   * 把对象转换为字符串。
   * @param obj 需转换的对象。
   * @return String 转换后的字符串。
   */
  public static String toString(Object obj)
  {
      String str = "";
      if(obj != null && (obj instanceof String))
          str = obj.toString();
      return str;
  }

  /**
   * 把字符串第一个字母转为大写。
   * @param str 需转换的对象。
   * @return String 转换后的字符串。
   */
  public static String upperFirstChar(String str)
  {
      if(str == null)
      {
          return null;
      } else
      {
          String a = str.substring(0, 1).toUpperCase();
          String b = str.substring(1, str.length());
          return a + b;
      }
  }

  /**
   * 以下几个方法用于取出当前时间的年月日分秒。
   * 由于java.util.Date 中 get???方法已经不推荐使用，所以尽量使用本类的方法
   */
  private static java.util.Calendar cal = new java.util.GregorianCalendar();

  /**
   * 以下几个方法用于取出当前时间的年。
   * 由于java.util.Date 中 get???方法已经不推荐使用，所以尽量使用本类的方法
   */
  public static int getYear(java.util.Date date) {
    cal.setTime(date);
    return cal.get(java.util.Calendar.YEAR);
  }

  /**
   * 以下几个方法用于取出当前时间的月。
   * 由于java.util.Date 中 get???方法已经不推荐使用，所以尽量使用本类的方法
   */
  public static int getMONTH(java.util.Date date) {
    cal.setTime(date);
    return cal.get(java.util.Calendar.MONTH)+1;
  }

  /**
   * 以下几个方法用于取出当前时间的日。
   * 由于java.util.Date 中 get???方法已经不推荐使用，所以尽量使用本类的方法
   */
  public static int getDay(java.util.Date date) {
    cal.setTime(date);
    return cal.get(java.util.Calendar.DATE);
  }

  /**
   * 以下几个方法用于取出当前时间的时。
   * 由于java.util.Date 中 get???方法已经不推荐使用，所以尽量使用本类的方法
   */
  public static int getHOUR(java.util.Date date) {
    cal.setTime(date);
    return cal.get(cal.HOUR_OF_DAY);
  }
  public static int getMINUTE(java.util.Date date) {
	    cal.setTime(date);
	    return cal.get(cal.MINUTE);
  }  

  /**
   * 以下几个方法用于取出当前时间的秒。
   * 由于java.util.Date 中 get???方法已经不推荐使用，所以尽量使用本类的方法
   */
  public static int getSECOND(java.util.Date date) {
    cal.setTime(date);
    return cal.get(cal.SECOND);
  }  
  /**
   * @param str String对象 表示要转换内部编码格式的字串
   * @return String 返回试图能正常显示的中文 若未能成功则返回null
   * <p>采用"ISO-8859-1"格式
   */
  public static String convert(String str) {
    try {
      return conver(str, "ISO-8859-1");
    }
    catch (java.io.UnsupportedEncodingException ex) {
      return null;
    }
  }

  /**
   * 字符串格式转换为GBK
   * @param str String对象 表示要转换内部编码格式的字串
   * @return String 返回试图能正常显示的中文 若未能成功则返回null
   * <p>采用"GBK"格式
   */
  public static String convert2(String str) {
    try {
      return conver(str, "GBK");
    }
    catch (java.io.UnsupportedEncodingException ex) {
      return null;
    }
  }

  /**
   * iso8859_1格式转换为GBK
   * @param str String对象 表示要转换内部编码格式的字串
   * @return String 返回试图能正常显示的中文 若未能成功则返回null
   * <p>采用"GBK"格式
   */
  public static String convert3(String str) {
    try {
      return new String(str.getBytes("iso8859_1"), "GBK");
    }
    catch (java.io.UnsupportedEncodingException ex) {
      return null;
    }
  }

  /**
   * @param str String对象 表示要转换内部编码格式的字串
   * @param type 编码格式
   * @return String 返回试图能正常显示的中文 若未能成功则返回null
   */
  public static String conver(String str, String type) throws
      java.io.UnsupportedEncodingException {
    return new String(str.getBytes(type));
  }

  /**
   * @param str String对象 表示要转换内部编码格式的字串
   * @param type 编码格式
   * @return String 返回试图能正常显示的中文 若未能成功则返回null
   */
//  public static String encode(String source) {
//    return new sun.misc.BASE64Encoder().encode(source.getBytes());
//  }

  /**
   * @param str String对象 表示要转换内部编码格式的字串
   * @param type 编码格式
   * @return String 返回试图能正常显示的中文 若未能成功则返回null
   */
//  public static String decode(String source) throws java.io.IOException {
//    return new String(new sun.misc.BASE64Decoder().decodeBuffer(source));
//  }

  /**
   * @param str 传入的字串
   * @param c 分隔符 如 ','
   * @return Vector 其中包含了 str 以 ',' 分隔的字串值
   * <p>例如 toArray("a,b,c,d",',')进行转换后得到String[]的值为
   * <p> "a"
   * <p> "b"
   * <p> "c"
   * <p> "d"
   * @deprecated As of PYSH version 1.0
   * 被<code>TranStr.split(String str, String delim)</code>代替.
   */
  public static String[] toArray(String str, String delim) {
    java.util.StringTokenizer kenizer = new java.util.StringTokenizer(str,
        delim);
    String[] result = new String[kenizer.countTokens()];
    int i = 0;
    while (kenizer.hasMoreElements()) {
      result[i] = (String) kenizer.nextToken();
      i++;
    }
    return result;
  }

  /**
   * jdk1.4方法。
   */
  public static String[] split(String str, String delim) {
	  	StringTokenizer st = new StringTokenizer(str,delim) ;
		int ojcount = st.countTokens() ;
		String s[] = new String[ojcount] ;
		int i = 0 ;
		while(st.hasMoreTokens()) {
			s[i] = st.nextToken() ;
			i++ ;
		}
		return s ;
  }

  /**
   * @param str 传入的字串
   * @param c 分隔符 如 ','
   * @return ArrayList 其中包含了 str 以 ',' 分隔的字串值
   * <p>例如 toList("a,b,c,d",',')进行转换后得到ArrayList的值为
   * <p> "a"
   * <p> "b"
   * <p> "c"
   * <p> "d"
   */
  public static java.util.ArrayList toList(String str, String delim) {
    java.util.StringTokenizer kenizer = new java.util.StringTokenizer(str,
        delim);
    java.util.ArrayList list = new java.util.ArrayList();
    int i = 0;
    while (kenizer.hasMoreElements()) {
      list.add(kenizer.nextElement());
    }
    return list;
  }

  /**
   * @param source 输入源 类型为String对象
   * @return verifynull 要么是""，要么是((String)(source))的值
   * 在JSP页面中 用于处理上一个表单所提交的值 若为空(null)则用""代替
   */
  public static String verifynull(Object source) {
    return source == null ? "" : ( (String) source).trim();
  }

  /**
   * 私有方法供 toHtmlTag和convertTextAreaTag 使用
   * @param text 要处理的字串值
   * @param repl 目标字串
   * @param with 用来代替目标字串的字串
   * @return String 处理结果
   * @see #toHtmlTag(String str)
   */
  public static String replace(String text, String repl, String with) {
    if (text == null || repl == null || with == null || repl.length() == 0) {
      return text;
    }
    StringBuffer buf = new StringBuffer(text.length());
    int start = 0, end = 0;
    while ( (end = text.indexOf(repl, start)) != -1) {
      buf.append(text.substring(start, end)).append(with);
      start = end + repl.length();
    }
    buf.append(text.substring(start));
    return buf.toString();
  }

  /**
   * @param str 要处理的字串值
   * @return String 处理结果
   * 用途，存储在数据库中的含有\,<,>,\n,'等特殊字串用来在.html页面表格中显示的时候需要用此方法转换。
   */
  public static String convertHtmlTag(String str) {
    str = replace(str, "\"","“");
    str = replace(str, "<", "＜");
    str = replace(str, ">", "＞");
    str = replace(str, "\n", "<BR>");
    str = replace(str, " ", " ");
    return str;
  }
  
  /**
   * @param str 要处理的字串值
   * @return String 处理结果
   * 格式化字符串,使之输出与输入(<textarea>)时格式相同.用途，存储在数据库中的含有' ' ,\n,'等特殊字串用来在.html页面表格中显示的时候需要用此方法转换。
   */
  public static String convertTextAreaTag(String str) {
    str = replace(str, "\n", "<BR>");
    str = replace(str, " ", "&nbsp;");
    return str;
  }

  /**
   * @param str 要处理的字串值
   * @return String 处理结果
   * 格式化字符串,使之输出与输入(<textarea>)时格式相同.用途，存储在数据库中的含有' ' ,\n,'等特殊字串用来在.html页面表格中显示的时候需要用此方法转换。
   */
  public static String convertText(String str) {
  	if(str==null) return "" ;
    str = str.replaceAll("\n", "<BR>");
    str = str.replaceAll(" ", "&nbsp;");
    return str;
  }

  public static String cut(int maxLength, String str)
  {
      if(str.length() <= maxLength)
          return str;
      else
          return str.substring(0, maxLength);
  }

  public static Date dateRoll(Date date, int minute)
  {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      Calendar _tmp = c;
      c.add(12, minute);
      return c.getTime();
  }

  /**
   * 对一个字串取指定的最大长度
   * @param maxLength 想得到的最长的位数，如对于一个长串只想得到前20个字符，则可以指定为20
   * @param str 要处理的字串
   * @return str
   */
  public static String cnt(int maxLength, String str) {
    if (str.length() <= maxLength) {
      return str;
    }
    else {
      return str.substring(0, maxLength);
    }
  }

  /**
   * 对一个字串把大小写变成与此字符串原来的样子相反
   * @param str 源字串
   * @return str处理过的字串 如果str为null则返回null
   */
  public static String swapCase(String str) {
    if (str == null) {
      return null;
    }
    int sz = str.length();
    StringBuffer buffer = new StringBuffer(sz);

    boolean whitespace = false;
    char ch = 0;
    char tmp = 0;

    for (int i = 0; i < sz; i++) {
      ch = str.charAt(i);
      if (Character.isUpperCase (ch)) {
        tmp = Character.toLowerCase(ch);
      }
      else if (Character.isTitleCase(ch)) {
        tmp = Character.toLowerCase(ch);
      }
      else if (Character.isLowerCase(ch)) {
        if (whitespace) {
          tmp = Character.toTitleCase(ch);
        }
        else {
          tmp = Character.toUpperCase(ch);
        }
      }
      else {
        tmp = ch;
      }
      buffer.append(tmp);
      whitespace = Character.isWhitespace(ch);
    }
    return buffer.toString();
  }

  /**
   * <p>水平转换一个字串.</p>
   *
   * <p><code>null</code> String 返回 <code>null</code>.</p>
   *
   * @param str 目标字串
   * @return String 转换过的字串
   */
  public static String reverse(String str) {
    if (str == null) {
      return null;
    }
    return new StringBuffer(str).reverse().toString();
  }

  /**
   * 检查一个字串是否是Email形式。<br>
   * 符合email的条件是转成大写字母时字符在[65,90]之内。且含有"@"及"."字符
   * <br>
   * char [A,Z] : [65,90] <br>
   * char [0,9] : [48,57] <br>
   * char @ : 64          <br>
   * char . : 46          <br>
   */
  public static boolean isEmail(String str) {
    str = str.trim().toUpperCase();
    char[] chares = str.toCharArray();
    if (chares[0] == 46 || chares[0] == 64) {
      return false;
    }
    if (chares[chares.length - 1] == 46 || chares[chares.length - 1] == 64) {
      return false;
    }
    for (int i = 0; i < chares.length; i++) {
      if ( ( (int) chares[i] < 48) && ( (int) chares[i] != 46)) {
        return false;
      }
      if ( ( (int) chares[i] < 65) && ( (int) chares[i] > 57) &&
          ( (int) chares[i] != 64)) {
        return false;
      }
      if ( ( (int) chares[i] > 90)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查一个字串是都是0-9这几个数字组成
   */
  public static boolean isNumber(String str) {
    char[] chares = str.toCharArray();
    for (int i = 0; i < chares.length; i++) {
      if ( ( (int) chares[i] > 57) || ( (int) chares[i] < 48)) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * 检查一个字串是都是0-9这几个数字组成
   */
  public static boolean isFloat(String str) {
    char[] chares = str.toCharArray();
    for (int i = 0; i < chares.length; i++) {
      if ( ( (int) chares[i] > 57) || ( (int) chares[i] < 48) && ( (int) chares[i] != 46)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查一个字串是一个符合java.net.URL要求的字串
   */
  public static boolean isHttp(String str) {
    try {
      new java.net.URL(str.trim());
      return true;
    }
    catch (java.net.MalformedURLException urlex) {
      return false;
    }
  }
  /**
   * dateStr,"YYYY-MM-DD"
   * @param date
   * @return "YYYY-MM-DD"
   */
  public static String rollDate(String dateStr,int field,int amount){
    Date date = toDate(dateStr) ;
    Calendar c = Calendar.getInstance() ;
    c.setTime(date) ;
    long tbak = c.getTimeInMillis() ;
    c.roll(field,amount) ;

    if(c.getTimeInMillis() > tbak) c.roll(Calendar.YEAR, -1) ;

    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    
    dateStr = year + "-" ;
    dateStr = dateStr + (month >= 10 ? "" : "0") + month + "-" ;
    dateStr = dateStr + (day >= 10 ? "" : "0") + day ;
    return dateStr ;
  }
  
  public static String rollDate_year(String dateStr,int field,int amount){
	    Date date = toDate(dateStr) ;
	    Calendar c = Calendar.getInstance() ;
	    c.setTime(date) ;
	    long tbak = c.getTimeInMillis() ;
	    c.roll(field,amount) ;

	    int year = c.get(Calendar.YEAR);
	    int month = c.get(Calendar.MONTH) + 1;
	    int day = c.get(Calendar.DAY_OF_MONTH);
	    
	    dateStr = year + "-" ;
	    dateStr = dateStr + (month >= 10 ? "" : "0") + month + "-" ;
	    dateStr = dateStr + (day >= 10 ? "" : "0") + day ;
	    return dateStr ;
	  }
	  
  /**
   * date
   * @param date
   * @return "YYYY-MM-DD"
   */
  public static String rollDate(Date date,int field,int amount){
    Calendar c = Calendar.getInstance() ;
    c.setTime(date) ;
    long tbak = c.getTimeInMillis() ;
    c.roll(field,amount) ;

//    if(c.getTimeInMillis() > tbak) c.roll(Calendar.YEAR, -1) ;

    String dateStr = "" ;
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);
    
    dateStr = year + "-" ;
    dateStr = dateStr + (month >= 10 ? "" : "0") + month + "-" ;
    dateStr = dateStr + (day >= 10 ? "" : "0") + day ;

    return dateStr ;
  }
  
  /**
   * date
   * @param date
   * @return "YYYY-MM-DD"
   */
  public static Calendar rollDate_Cal(Calendar c,int field,int amount){
    c.roll(field,amount) ;

    return c ;
  }

  /**
   * 本方法对一个字符串取其长度，规则是:
   * 一个ascall码算一个长度
   * 一个nnicon算二个长度
   */
  public static int getSize(String str){
    byte[] b = str.getBytes();
    int n = 0;   //ASCALL char
    int m = 0;   //nnICOn char
    int v = 0;   //temp var
    for (int i = 0; i < b.length; i++) {
      v = b[i];
      if ( v!=63 ){
        n ++;
      }else{
        m ++;
      }
    }
    return n + m*2;
  }
  
  public static boolean isAfterADate(Date date) {
	  if(date==null) return false ;
		Calendar c = Calendar.getInstance() ;
		long now = c.getTimeInMillis() ;
		c.setTime(date) ;
		long d = c.getTimeInMillis() ;
		return now-d>86400000 ;
	  
  }
	
	public static String download_html(String urlstr) {
		return download_html(urlstr, "gbk", "") ;
	}

	public static String download_html(String urlstr, String charset, String tmpppppp) {
		// System.out.println(urlstr + "," + path + "," + filename) ;
//		String urltmp = "http://product.dangdang.com/product.aspx?product_id=";
		boolean r = false;
		String sURLAddress = new String(urlstr);
		URL url = null;
		try {
			url = new URL(sURLAddress);
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}

		StringBuffer sb = new StringBuffer();
		byte buf[] = new byte[8000000];
		int buf_pos = 0 ;
		try {
			InputStream ins = url.openStream();
			// FileInputStream fin = new FileInputStream(url) ;
			byte b[] = new byte[800000];
			int rleng = ins.read(b);
			while (rleng != -1) {
				r = true;
				buf = b_append(buf, buf_pos, b, rleng) ;
				buf_pos += rleng ;
				
				// sb.append(new String(b,0,rleng,"gbk")) ; //"utf-8")) ;
				rleng = ins.read(b);
			}
			sb.append(new String(buf, 0, buf_pos, charset));
			buf = null ;
		} catch (Exception e) {
			System.out.println(e.toString());
			return "";
		}

		return sb.toString();

	}
	
	public static byte[] b_append(byte buf[], int buf_pos, byte b[], int rleng) {
		for(int i=0;i<rleng;i++) {
			buf[buf_pos] = b[i] ;
			buf_pos++ ;
		}
		return buf ;
	}
    
    public static void saveMyObject(String filename, Object obj) {
    	File myFile = new File(filename);
    	try {
    		FileOutputStream fileOutStr = new FileOutputStream(myFile);
    		ObjectOutputStream outStr = new ObjectOutputStream(fileOutStr);
    		outStr.writeObject(obj);
    		outStr.close();
    	}catch (IOException e){
    	}
    }
    
    public static Object getMyObject(String filename) {
    	File myFile = new File(filename);
    	try {
    		if(myFile.exists()) {
        		FileInputStream fileOutStr = new FileInputStream(myFile);
        		ObjectInputStream outStr = new ObjectInputStream(fileOutStr);
        		return outStr.readObject();
    		}
    	}catch (Exception e){
    	}
    	return null ;
    }

	public static String getArea(String mobile) {
		String tmp = Putil.download_html("http://tool.httpcn.com/Sj/?word=" + mobile) ;
//		System.out.println(tmp) ;
		int s = tmp.indexOf("手机号码归属地：") ;
		int e = 0 ;
		String p = "", c = "" ;
		if(s>0) {
			s = tmp.indexOf(">", s) ;
			e = tmp.indexOf("<", s) ;
			if(s>0 && e>0) {
				tmp = tmp.substring(s+1, e).replace("&nbsp;", " ") ;
//				if(tmp.length()>0) {
//					s = tmp.indexOf("-") ;
//					if(s>0) {
//						p = tmp.substring(0,s) ;
//						c = tmp.substring(s+1) ;
//					}
					
//				}
				return tmp ;
			}
		}
		return "" ;
	}
  
  public static String dateToString(java.util.Date date) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    return sdf.format(date);
  }
  


  public static void main(String [] args) throws Exception {
  


  }
  


}
