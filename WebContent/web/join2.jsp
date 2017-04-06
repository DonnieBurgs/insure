<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务进驻</title>
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/style/base.css" />
<link rel="stylesheet" type="text/css" href="/style/global.css" />
<script src="js/screen.js"></script>
</head>

<body class="in nav2">
<%@include file="/inc/header.jsp"%>
<div class="inBanner ta-c">
    <a href="/apply.do#in"><img src="/image/inBanner-sub.png" /></a>
</div>
<div class="rollNotice-box bg-f">
    <div class="fs-12 o-h wrap p-r c-6">
        <div class="rollNotice-title f-l fs-26 c-3"><i class="iconfont">&#xe605;</i>公告</div>
        <div class="rollNotice f-l o-h fs-22" id="rolling_single">
            <ul id="rolling_single1">
                <li class="o-h">
                    <a class="c-6" target="_blank" title="2016,感动中国的9张医生照片" href="to/s2-12-155-767.htmls">
                    <span class="f-l d-ib to-e">2016,感动中国的9张医生照片</span>
                    <span class="f-r d-ib">2017-02-17</span>
                    </a>
                </li> 
                <li class="o-h">
                    <a class="c-6" target="_blank" title="国家大力发展支持救援医疗发展" href="to/s2-12-155-759.htmls">
                    <span class="f-l d-ib to-e">国家大力发展支持救援医疗发展</span>
                    <span class="f-r d-ib">2017-02-16</span>
                    </a>
                </li> 
                </ul>
            <ul id="rolling_single2"></ul>
        </div>
         <a href="to/s2-12-156.htmls?=dt" class="rollNotice-more d-ib f-r fs-22 c-9">
         	查看更多  <i class="iconfont">&#xe602;</i>
         </a>
    </div>
</div>
<div class="inIntroduce">
	<div class="wrap o-h">
    	<div class="inIntroduce-list s1 ta-c f-l">
        	<i class="iconfont">&#xe702;</i>
            <h3 class="fs-24 c-3">医疗救援官方授权</h3>
            <p class="fs-18">政府批准许可、标准规范的医疗救援服务平台，助力医疗服务业</p>
        </div>
    	<div class="inIntroduce-list s2 ta-c f-l">
        	<i class="iconfont">&#xe703;</i>
            <h3 class="fs-24 c-3">单资源灵活打包</h3>
            <p class="fs-18">完善的医疗服务产品打包售卖策略，为碎片化资源拼接闭环服务</p>
        </div>
    	<div class="inIntroduce-list s3 ta-c f-r">
        	<i class="iconfont">&#xe701;</i>
            <h3 class="fs-24 c-3">医疗救援产业链协同</h3>
            <p class="fs-18">平台聚合医疗救援产业链上下游各环节服务机构，发挥协同效应，构建生态竞争力</p>
        </div>
    </div>
</div>
<div class="inProcess">
    <h2 class="ta-c c-3">申请流程</h2>
	<div class="wrap">
        <table class="fs-24 c-3">
        	<tr height="100">
            	<td width="542"></td>
                <td class="p-r inProcess-line" width="6" rowspan="6"><i class="iconfont p-a top">&#xe62e;</i><i class="iconfont p-a bottom">&#xe62e;</i></td>
                <td width="552"></td>
            </tr>
        	<tr height="184">
            	<td class="ta-r p-r">
                	<div class="inProcess-step p-a c-f br-10">01进驻申请<i class="iconfont right p-a fs-12">&#xe692;</i><i class="iconfont p-a circular">&#xe632;</i></div>
                    <span class="d-ib ta-l">注册并提交入驻申请，按要求上传企业资质材料进行认证</span>
                </td>
                <td></td>
            </tr>
        	<tr height="184">
            	<td></td>
                <td class="ta-l p-r">
                	<div class="inProcess-step p-a c-f br-10">02服务核实<i class="iconfont left p-a fs-12">&#xe689;</i><i class="iconfont p-a circular">&#xe632;</i></div>
                    <span class="d-ib">平台对提交企业资质材料的服务商进行审核，通过后完成账户验证，并在线签署合作协议</span>
                </td>
            </tr>
        	<tr height="184">
            	<td class="ta-r p-r">
                	<div class="inProcess-step p-a c-f br-10">03服务产品打包<i class="iconfont right p-a fs-12">&#xe692;</i><i class="iconfont p-a circular">&#xe632;</i></div>
                    <span class="d-ib ta-l">服务商登录平台管理页面，完善资料，提交服务内容、形式和报价，平台匹配形成服务产品包</span>
                </td>
                <td></td>
            </tr>
        	<tr height="100">
            	<td></td>
                <td class="ta-l p-r"><div class="inProcess-step p-a c-f br-10">OK<i class="iconfont left p-a fs-12">&#xe689;</i><i class="iconfont p-a circular">&#xe632;</i></div></td>
            </tr>
        </table>
    </div>
</div>
<script src="/js/login.js"></script>
<script src="/js/formlogin.js"></script>
</body>
<script>
//滚动公告
$(function () {
   PixelScroll=function(Pixel){
       Pixel=$.extend({
          Id:"",//第一个ID
          IdOne:"",//第二个ID
          IdTwo:"",//第三个ID
          Space:0,//每次滚动像素
          Direction:"",//移动方向
          Speed:5000//滚动速度
       },Pixel);
       var lst=$("#"+Pixel.Id);
       var lst1=$("#"+Pixel.IdOne);
       var lst2=$("#"+Pixel.IdTwo);
       lst2.html(lst1.html());
       function Top(){
          if(lst.scrollTop()>=lst1.height()){
             lst.scrollTop(0);
          }lst.animate({scrollTop:lst.scrollTop()+ Pixel.Space},{ duration:600 , queue:false });
       }
       function Marquee(){
          switch(Pixel.Direction){
              case "top":Top();break;
          }
       }
       var MyMar=setInterval( function(){ Marquee()},Pixel.Speed)
       lst.mouseover(function(){
           clearInterval(MyMar);
       });
       lst.mouseout(function(){
           MyMar=setInterval(Marquee,Pixel.Speed);
       });
   }
   PixelScroll({
		Id:"rolling_single",//第一个ID
		IdOne:"rolling_single1",//第二个ID
		IdTwo:"rolling_single2",//第三个ID
		Space:36,//每次滚动像素
		Direction:"top",//滚动方向
		Speed:4444//滚动速度
	});
});

</script>
</html>
