<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,com.web.util.Putil,javax.servlet.http.Cookie" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/style/base.css" />
<link rel="stylesheet" type="text/css" href="/style/global.css" />
<script src="js/screen.js"></script>
<title>首页</title>
</head>

<body class="index nav1">
<%@include file="/inc/header.jsp"%>
<div class="banner swiper-container p-r o-h">
	<div class="btn w1 p-a z-5">
        <div class="wrap">
            <a href="javascript:;" class="f-l iconfont c-f br-50 ta-c" id="btn1">&#xe601;</a>
            <a href="javascript:;" class="f-r iconfont c-f br-50 ta-c" id="btn2">&#xe602;</a>
        </div>
    </div>
    <ul class="swiper-wrapper p-r lif-l">
        <li class="swiper-slide"><img class="w1" src="/image/indexBanner01.jpg" /></li>
        <li class="swiper-slide"><img class="w1" src="/image/indexBanner02.jpg" /></li>
    </ul>
	
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
                    <span class="f-r d-ib">2017-01-23</span>
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
<div class="indexContent content1 ta-c">
	<div class="wrap o-h">
    	<h2 class="c-0">易医通开放平台为您提供的服务能力</h2>
    	<a href="/join.do" class="d-ib fs-20">
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-1.png" /></span></p>救护车救援</div>
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-2.png" /></span></p>航空医疗护送</div>
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-3.png" /></span></p>高铁医疗护送</div>
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-4.png" /></span></p>直升机救援</div>
        </a>
    </div>
</div>
<div class="indexContent content2 ta-c">
	<div class="wrap o-h">
    	<h2 class="c-0">服务机构入驻</h2>
    	<a href="/join.do?t=2" class="d-ib fs-20">
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-5.png" /></span></p>救护车托管</div>
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-6.png" /></span></p>国内医陪</div>
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-7.png" /></span></p>海外医陪</div>
        	<div class="d-ib f-l"><p class="ta-c"><span><img src="image/icon-8.png" /></span></p>港澳医陪</div>
        </a>
    </div>
</div>
<div class="indexAbout ">
	<div class="wrap">
    	<h2 class="p-r ta-c fs-30"><p>ABOUT US</p><span>关于我们</span></h2>
        <div class="indexAbout-content">
        	<img src="/image/indexAbout-1.jpg" class="w1"/>
            <p class="fs-22">　　广东民航医疗快线有限公司成立于2015年9月，是由广东省国资委批准成立、隶属于广东省机场管理集团有限公司，是目前唯一获得广东省卫生计生委正式批复、合法开展非120急救转运业务的公司，是广东省首家立体医疗转运提供商和行业规范制订者。
广东民航医疗快线与广东省人民医院、中山大学附属第三医院等省内十多家顶级三甲医院建立全面战略合作关系，为三甲医院提供非120急救转运定点服务，共同规范医疗转运市场，成为三甲医院重要的服务提供商，与大多数的各级医院建立转运业务关系。
<br/>
　　易医通是广东民航医疗快线有限公司唯一授权的医疗转运线上平台，开展医疗救援预约和相关医疗陪护服务。易医通平台把医疗救援服务标准化并实现救援能力输出，企事业单位可便捷获取广东民航医疗快线有限公司的救护车、航空、高铁和直升飞机等医疗救援能力为客户提供服务；
同时易医通平台将吸纳医疗转运产业链各环节不同形态不同地域的服务进驻平台，打造能力共享平台，为广大用户提供无缝衔接的医疗转运及“医陪”服务。
</p>
        </div>
<c:if test="${not empty newsList}">
        <ul class="indexAbout-list fs-22">
<c:forEach var="item" items="${newsList}">
        	<li><a href="/news.do?id=${item.newsid}" class="c-3"><i class="iconfont fs-28">&#xe603;</i>${item.title}<span class="f-r">${item.createdate_}</span></a></li>
</c:forEach>
        </ul>
</c:if>
    </div>
</div>
<div class="indexFooter ta-c">
	
</div>
<script src="/js/login.js"></script>
</body>
<script src="/js/formlogin.js"></script>
<script src="/js/idangerous.swiper.min.js"></script>
<script type="text/javascript">
//banner轮播
var mySwiper = new Swiper('.swiper-container',{
	autoplay : 5000,
	loop: true,
	calculateHeight : true,
});
$('#btn1').click(function(){
	mySwiper.swipePrev(); 
})
$('#btn2').click(function(){
	mySwiper.swipeNext(); 
})
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
