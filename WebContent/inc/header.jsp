
<div class="header">
	<div class="wrap">
		<div class="logo f-l">
			<a href="/index.do" class="fs-26 c-3"><img src="/image/logo.png" /> | 医疗转运开放平台</a>
		</div>
		<ul class="nav f-r lif-l fs-22">
			<li class="nav1"><a href="/index.do">首页</a></li>
			<li class="nav2"><a href="/apply.do">申请</a></li>
			<li class="nav3"><a href="/aboutus.do">我们</a></li>
			<li class="nav4"><a href="/emt" target="_blank">预约</a></li>
			<li class="nav5">
			<c:if test="${islogin ne '1'}"><a class="loginPupop" id="btnLogin" href="javascript:toLogin();">登录</a></c:if>
			<c:if test="${islogin eq '1'}"><a class="" id="btnLogin" href="javascript:toMan();">控制中心</a></c:if>
			</li>
			<li class="nav5">
			<c:if test="${islogin ne '1'}"><a style="display:none;" id="btnLogin2" href="javascript:toLogout();">退出</a></c:if>
			<c:if test="${islogin eq '1'}"><a style="" id="btnLogin2" href="javascript:toLogout();">退出</a></c:if>
			</li>
		</ul>
	</div>
</div>