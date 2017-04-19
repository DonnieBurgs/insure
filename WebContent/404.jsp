<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后台管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="<%=request.getContextPath()%>/resources/images/skin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formlogin.js"></script>
<script type="text/javascript">
	function handleLogin() {
		$.cookie('login-tel', $("#login-tel").val());
		return true;
	}
	$(function() {
		var name = $.cookie('login-tel');
		if (name != "" && name != "null" && name != null)
			$("#login-tel").val(name);

		$("#login-tel").get(0).focus();
	});

</script>
</head>
<body>
<table width="100%" height="166" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="42" valign="top"><table width="100%" height="42" border="0" cellpadding="0" cellspacing="0" class="login_top_bg">
      <tr>
        <td width="1%" height="21">&nbsp;</td>
        <td height="42">&nbsp;</td>
        <td width="17%">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="532" border="0" cellpadding="0" cellspacing="0" class="login_bg">
      <tr>
        <td width="49%" align="right"><table width="91%" height="532" border="0" cellpadding="0" cellspacing="0" class="login_bg2">
            <tr>
              <td height="138" valign="top"><table width="89%" height="427" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="149">&nbsp;</td>
                </tr>
                <tr>
                  <td height="80" align="right" valign="top"><img src="<%=request.getContextPath()%>/resources/images/logo.png" width="279"/></td>
                </tr>
                <tr>
                  <td height="198" align="right" valign="top"><table width="400" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                      <td width="30%" height="40" class="left_txt"><img src="<%=request.getContextPath()%>/resources/images/icon-demo.gif" width="16" height="16" align="absmiddle"/> <a href="help.doc" target="_blank" class="left_txt3">使用说明</a></td>
                      <td width="35%" class="left_txt"><img src="<%=request.getContextPath()%>/resources/images/icon-login-seaver.gif" width="16" height="16" align="absmiddle"/> <a href="tencent://message/?uin=80000000&Site=后台管理系统&Menu=yes" target="_blank class="left_txt3">在线客服</a></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            
        </table></td>
        <td width="1%" >&nbsp;</td>
        <td width="50%" valign="bottom"><table width="100%" height="59" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%">&nbsp;</td>
              <td width="96%" height="38"><span class="login_txt_bt">康群健康后台管理系统</span></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="21"><table cellspacing="0" cellpadding="0" width="100%" border="0" id="table211" height="328">
              	
						<tr>
							<td align="left" colspan="2" ><font color="red" id="loginAlertStr"></font></td>
						</tr>
                  <tr>
                    <td height="164" colspan="2" align="left">
                    	很抱歉,您所访问的内容已被删除或不存在！&nbsp;&nbsp;&nbsp;<a href="javascript:history.back();">返回</a>&nbsp;&nbsp;&nbsp;<a href="/index.do">返回主页</a>

                    	
                    </td>
                  </tr>
                  <tr>
                    <td width="433" height="164" align="right" valign="bottom"><img src="<%=request.getContextPath()%>/resources/images/login-wel.jpg" width="242" height="138"/></td>
                    <td width="57" align="right" valign="bottom">&nbsp;</td>
                  </tr>
              </table></td>
            </tr>
          </table>
          </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="login-buttom-bg">
      <tr>
        <td align="center"><span class="login-buttom-txt">Copyright &copy; 2017-2017 康群健康有限公司</span></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>