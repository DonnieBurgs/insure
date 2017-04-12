<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,com.web.servlet.LoginManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String authorityno = (String)request.getAttribute("authorityno") ;
if(authorityno==null) authorityno = "" ;
%>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link type="text/css" rel="stylesheet" href="/admin/style/base.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/global.css" />
</head>

<body>
<div class="header fs-14 p-f w1">
	<div class="logo f-l"><!-- <a class="d-ib w1" href="/"><i class="iconfont c-f fs-34">&#xe676;</i><span class="fs-20 fw-6">医疗快线</span></a> --></div>
    <ul class="nav f-r lif-l">
    	<li><a href="/">首页</a></li>
        <li><a href="#">${username }</a></li>
        <li><a href="javascript:toLogout();">退出</a></li>
    </ul>
</div>
<div id="menu-scroll" class="menu-scroll p-f o-h">
    <ul id="menu" class="menu">
        <ol class="fs-12">管理</ol>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe654;</i><span class="fw-6 fs-14">方案管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="投保公司" target="iframe" href="/emInsurerCompany.do?method=list"><span class="fs-14">投保公司</span></a>
                    <a class="d-ib" data-cookie="方案管理" target="iframe" href="/emPolicy.do?method=list"><span class="fs-14">方案管理</span></a>
                    <a class="d-ib" data-cookie="团保方案" target="iframe" href="/emGroupInsurancePolicy.do?method=list"><span class="fs-14">团保方案</span></a>
                    <a class="d-ib" data-cookie="个人保单管理" target="iframe" href="/emInsurerPolicy.do?method=list"><span class="fs-14">个人保单管理</span></a>
                    <a class="d-ib" data-cookie="承保公司" target="iframe" href="/emApplicantCompany.do?method=list"><span class="fs-14">承保公司</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe6bc;</i><span class="fw-6 fs-14">数据管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">保全导入</span></a>
                    <a class="d-ib" data-cookie="报表导出" target="iframe" href="/emOrder.do?method=list&key_status=1"><span class="fs-14">报表导出</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe6bc;</i><span class="fw-6 fs-14">理赔管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="案卷列表" target="iframe" href="/emClaimArchive.do?method=list"><span class="fs-14">案卷列表</span></a>
                    <a class="d-ib" data-cookie="报表导出" target="iframe" href="/emOrder.do?method=list&key_status=1"><span class="fs-14">案件受理</span></a>
                    <a class="d-ib" data-cookie="理算录入" target="iframe" href="/emDetermine.do?method=list"><span class="fs-14">理算录入</span></a>
                    <a class="d-ib" data-cookie="理算复核" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">理算复核</span></a>
                    <a class="d-ib" data-cookie="案件列表" target="iframe" href="/emClaim.do?method=list"><span class="fs-14">案件列表</span></a>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">理赔列表</span></a>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">影像件上传</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe6bc;</i><span class="fw-6 fs-14">医保信息</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">拒赔原因</span></a>
                    <a class="d-ib" data-cookie="疾病库" target="iframe" href="/emDisease.do?method=list"><span class="fs-14">疾病库</span></a>
                    <a class="d-ib" data-cookie="医院" target="iframe" href="/emHospital.do?method=list"><span class="fs-14">医院</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe6bc;</i><span class="fw-6 fs-14">用户管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">在线用户</span></a>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">菜单</span></a>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">角色</span></a>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">用户组</span></a>
                    <a class="d-ib" data-cookie="保全导入" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">用户</span></a>
                </li>
            </ul>
        </li>
        <!--  
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe6bc;</i><span class="fw-6 fs-14">订单管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="全部订单" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">全部订单</span></a>
                    <a class="d-ib" data-cookie="未支付订单" target="iframe" href="/emOrder.do?method=list&key_status=1"><span class="fs-14">未支付订单</span></a>
                    <a class="d-ib" data-cookie="已支付订单" target="iframe" href="/emOrder.do?method=list&key_status=2"><span class="fs-14">已支付订单</span></a>
                    <a class="d-ib" data-cookie="订单派发" target="iframe" href="/emOrder.do?method=list&key_status=4"><span class="fs-14">订单派发</span></a>
                    <a class="d-ib" data-cookie="订单报表" target="iframe" href="/emOrder.do?method=list"><span class="fs-14">订单报表</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe60c;</i><span class="fw-6 fs-14">服务供应商管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="服务供应商管理" target="iframe" href="/emCompany.do?method=list&key_companytypeid=1"><span class="fs-14">服务供应商管理</span></a>
                    <a class="d-ib" data-cookie="供应商入驻申请" target="iframe" href="/emCompany.do?method=list&key_isaudit=0&key_companytypeid=1"><span class="fs-14">供应商入驻申请</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe601;</i><span class="fw-6 fs-14">企业管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="企业管理" target="iframe" href="/emCompany.do?method=list&key_companytypeid=0"><span class="fs-14">企业管理</span></a>
                    <a class="d-ib" data-cookie="企业接入申请" target="iframe" href="/emCompany.do?method=list&key_isaudit=0&key_companytypeid=0"><span class="fs-14">企业接入申请</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe649;</i><span class="fw-6 fs-14">营销管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="救援金券" target="iframe" href="/emCoupon.do?method=list"><span class="fs-14">救援金券</span></a>
                    <a class="d-ib" data-cookie="联盟链接管理" target="iframe" href="/emUnionLink.do?method=list"><span class="fs-14">联盟链接管理</span></a>
                    <a class="d-ib" data-cookie="资讯管理" target="iframe" href="/emInformation.do?method=list"><span class="fs-14">资讯管理</span></a>
                    <a class="d-ib" data-cookie="新闻管理" target="iframe" href="/emNews.do?method=list"><span class="fs-14">新闻管理</span></a>
                </li>
            </ul>
    
        </li>

        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe600;</i><span class="fw-6 fs-14">常规管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="救护车类型" target="iframe" href="/emCarType.do?method=list"><span class="fs-14">救护车类型</span></a>
                    <a class="d-ib" data-cookie="救援类型" target="iframe" href="/emPackType.do?method=list"><span class="fs-14">救援类型</span></a>
                    <a class="d-ib" data-cookie="交通工具类型" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">交通工具类型</span></a>
                    <a class="d-ib" data-cookie="配套服务类型" target="iframe" href="/emServiceOptionType.do?method=list"><span class="fs-14">配套服务类型</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe657;</i><span class="fw-6 fs-14">医生资料管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="医生管理" target="iframe" href="/emDoctor.do?method=list"><span class="fs-14">医生管理</span></a>
                    <a class="d-ib" data-cookie="医院管理" target="iframe" href="/emHospital.do?method=list"><span class="fs-14">医院管理</span></a>
                    <a class="d-ib" data-cookie="医护类型" target="iframe" href="/emDoctorType.do?method=list"><span class="fs-14">医护类型</span></a>
                    <a class="d-ib" data-cookie="科室管理" target="iframe" href="/emSpecialty.do?method=list"><span class="fs-14">科室管理</span></a>
                </li>
            </ul>
        </li>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe66d;</i><span class="fw-6 fs-14">项目收费管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="订单费用参数" target="iframe" href="/emOrderType.do?method=list"><span class="fs-14">订单费用参数</span></a>
                    <a class="d-ib" data-cookie="航空转运费用" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">航空转运费用</span></a>
                    <a class="d-ib" data-cookie="高铁转运费用" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">高铁转运费用</span></a>
                    <a class="d-ib" data-cookie="地面转运费用" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">地面转运费用</span></a>
                </li>
            </ul>
    
        </li>-->
        <ol class="fs-12">管理</ol>
        <li class="twoLevel-menu-box">
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe638;</i><span class="fw-6 fs-14">消息中心</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="系统消息" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">系统消息</span></a>
                    <a class="d-ib" data-cookie="推广消息" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">推广消息</span></a>
                    <a class="d-ib" data-cookie="订单通知" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">订单通知</span></a>
                    <a class="d-ib" data-cookie="客服消息" target="iframe" href="/emMessageMan.do?method=list"><span class="fs-14">客服消息</span></a>
                    <a class="d-ib" data-cookie="公告" target="iframe" href="/emNotice.do?method=list"><span class="fs-14">公告</span></a>
                </li>
            </ul>
        </li>
        <li><a class="d-ib" data-cookie="统计" target="iframe" href="/emTransType.do?method=list"><i class="iconfont list">&#xe7a5;</i><span class="fw-6 fs-14">统计</span></a></li>
        <ol class="fs-12">管理</ol>
        <li>
            <a class="d-ib p-r twoLevel-menu-sub" href="javascript:;">
                <i class="iconfont list">&#xe726;</i><span class="fw-6 fs-14">系统管理</span>
                <i class="iconfont icon-arrow p-a">&#xe687;</i>
            </a>
            <ul class="twoLevel-menu o-h">
                <li>
                    <a class="d-ib" data-cookie="管理员管理" target="iframe" href="/emAdmin.do?method=list"><span class="fs-14">管理员管理</span></a>
                    <a class="d-ib" data-cookie="权限设置" target="iframe" href="/emAuthority.do?method=list"><span class="fs-14">权限管理</span></a>
                    <a class="d-ib" data-cookie="系统参数" target="iframe" href="/emTransType.do?method=list"><span class="fs-14">系统参数</span></a>
                </li>
            </ul>
        </li>
    </ul>
</div>
<div class="iframe-box">
	<iframe src="" class="iframe d-ib" frameborder="0" name="iframe" id="iframe" scrolling="no"></iframe>
</div>
</body>
<script>
$(function(){
/*页面刷新加载一次刷新前的url*/
var username=document.cookie.split(";")[0].split("=")[1]; //读取路径cookie
$('.menu a').each(function(){
	var active = $(this).attr('href')
	if (username ==undefined){
		$('.menu li:first,.menu li:first .twoLevel-menu a:first').addClass('active')
		$('.menu li:first .twoLevel-menu a:first').trigger('click')
		$('#iframe').attr('src',$('.menu li:first .twoLevel-menu a:first').attr('href'))
	}else if(active===username){
		$('#iframe').attr('src',username)
		$(this).parents('.menu').find('a').removeClass('active')
		$(this).addClass('active');
		$(this).parents('li').addClass('active').siblings().removeClass('active')
		$(this).trigger('click')
	}

})
//iframe高度
	var bodyHeight = document.documentElement.clientHeight;//获取窗口可视宽度
	var menuHeight = document.getElementById('menu')//获取菜单
	menuHeight.style.height = bodyHeight-110+'px';//菜单高度
	document.getElementById('iframe').style.height = bodyHeight-110+'px';
	$("#iframe").load(function(){
		var iframeTitle = $(this).contents().find("title").text();//获取框架标题
		$('title').text(iframeTitle)
    	var mainheight = $(this).contents().find("#right").height()+100;//获取框架高度
		if(mainheight<(bodyHeight-110))
    		$(this).height(bodyHeight-110);
		else
			$(this).height(mainheight);
	});
//iframe跳转
	$('.menu a').live('click',function(){//加载内联框架
		$(this).parents('.menu').find('a').removeClass('active')
		$(this).addClass('active');
		$(this).parents('li').siblings().removeClass('active')
		var urlcookie = $(this).attr('href');
		if(urlcookie !=''){
			document.cookie="url="+urlcookie; //写入路径cookia
		}
	})
	$('.menu .twoLevel-menu-sub').live('click',function(){//点击二级菜单
		$(this).parent().toggleClass('active').siblings().removeClass('active')
	})
	

})



	function toLogout() {
		$.ajax({ 
		    type: "post", 
		    url: "/emApp.do", 
		    dataType: "json",
		    data:{"method": "userLogout"},
		    success: function (data) {
				location.href="/";
		    }, 
		    error: function (XMLHttpRequest, textStatus, errorThrown) { 
		    	$('#loginAlertStr').html("操作失败，请稍后再试。");
		    } 
		});


	}

//var lBox_h = $(window.frames["iframe"].document).find("#lBox").offset().top;
//var lBox_h = $(document.getElementById('iframe').document).find("#lBox").offset().top;
//alert(document.getElementById('iframe'));
$(window).scroll(function(){
	var sHeight = screen.height;
	var scrollH = 0;
	if(sHeight<=850) {
		scrollH = $(window).scrollTop()+10;
	} else {
		scrollH = $(window).scrollTop()+100;
	}
	if(window.frames["iframe"].document && $(window.frames["iframe"].document).find("#key_top")) $(window.frames["iframe"].document).find("#key_top").val(scrollH);
	$(window.frames["iframe"].document).find("#lBox").css({'top':scrollH});
})
</script>
</html>
