String.prototype.trim=function(){return this.replace(/(^\s+)|(\s+$)/g,"")};String.prototype.format=function(){var a=arguments;return this.replace(/\{(\d+)\}/g,function(c,b){return a[b]})};function StringBuilder(){this.arr=[]}StringBuilder.prototype.append=function(a){this.arr.push(a)};StringBuilder.prototype.appendFormat=function(){for(var a=arguments[0],c=0;c<arguments.length-1;c++)a=a.replace(new RegExp("\\{"+c+"\\}"),arguments[c+1]);this.arr.push(a)};StringBuilder.prototype.toString=function(){return this.arr.join("")};
function citytab(a){var c=document.getElementById("cityhead").getElementsByTagName("li");if(c){for(var b=0;b<c.length;b++)c[b].className="search_li01";if(b=document.getElementById("li"+a))b.className="search_li02"}if(c=document.getElementById("city_box").getElementsByTagName("div")){for(b=1;b<c.length;b++)c[b].className="list_main unshow";if(b=document.getElementById("city"+a))b.className="list_main"}document.getElementById("top_getiframe").style.height=document.getElementById("city_box").offsetHeight+2+"px";}
function prefixTab(a){var c=document.getElementById("cityall").getElementsByTagName("ul");if(c){for(var b=1;b<c.length;b++)c[b].className="city_sugg unshow";if(a=document.getElementById("ul"+a))a.className="city_sugg"}document.getElementById("top_getiframe").style.height=document.getElementById("city_box").offsetHeight+2+"px"};

function replaceHtml(el, html) {
    var oldEl = typeof el == "string" ? document.getElementById(el) : el;
    var newEl = oldEl.cloneNode(false);
    newEl.innerHTML = html;
    oldEl.parentNode.replaceChild(newEl, oldEl);
    return newEl;
};

var parentbject;
window.city_suggest = function(){
    this.pro = '';
	this.object = '';
	this.id2 = '';
	this.taskid = 0;
	this.delaySec = 10; // 默认延迟多少毫秒出现提示框
	this.hot= [];
	this.letter = [];
	this.hotelcity ={};
	/**
	* 初始化类库
	*/
		this.init_zhaobussuggest=  function(){
		var objBody = document.getElementsByTagName("body").item(0);
		var objiFrame = document.createElement("iframe");
		var objplatform = document.createElement("div");
		objiFrame.setAttribute('id','top_getiframe');
		objiFrame.setAttribute("src","about:blank");
		objiFrame.style.zIndex='10';
		objiFrame.style.border='0';
		objiFrame.style.width='0px';
		objiFrame.style.height='0px';
		objiFrame.style.position = 'absolute';
		objplatform.setAttribute('id','top_getplatform');
		objplatform.setAttribute('align','left');
		objplatform.style.zIndex='10';
		objplatform.style.position = 'absolute';
		objplatform.style.border = 'solid 1px #7f9db9';
		objplatform.style.background = '#ffffff';
		if(objBody){
		    objBody.insertBefore(objiFrame,document.getElementById("form1"));
		    if(objiFrame){
		        objiFrame.ownerDocument.body.insertBefore(objplatform,document.getElementById("form1"));
		    }
		}
		if(!document.all) {
			window.document.addEventListener("click",this.hidden_suggest,false);
		}else{
			window.document.attachEvent("onclick",this.hidden_suggest);
		}
	}


	/***************************************************fill_div()*********************************************/
	//函数功能：动态填充div的内容，该div显示所有的提示内容
	//函数参数：allplat 一个字符串数组，包含了所有可能的提示内容
	this.fill_div = function(allplat){
		var _html=new StringBuilder;
		_html.append('<div id="city_box" class="choose_frame">');_html.append('\t<div id="cityhead" class="list_head">');_html.append('\t\t<ul class="fleft">');_html.append('\t\t\t<li id="lihot" class="search_li02" onclick="citytab(\'hot\');document.getElementById(\'span_sort\').style.display=\'none\';">\u70ed\u95e8\u57ce\u5e02</li>');_html.append('\t\t\t<li id="liall" class="search_li01" onclick="citytab(\'all\');document.getElementById(\'span_sort\').style.display=\'block\';">\u66f4\u591a\u57ce\u5e02</li>');_html.append("\t\t</ul>");_html.append('\t\t<span id="span_sort" style="display:none;" class="fcenter">\uff08\u6309\u62fc\u97f3\u9996\u5b57\u6bcd\uff09</span>');
        _html.append('\t\t<a onclick="parentbject.hidden();" style="cursor:pointer;" class="fright" title="\u5173\u95ed"><img src="http://www.ugolf.com.cn/2010css/club/search_img04.gif" /></a>');_html.append("\t</div>");_html.append('\t<div id="cityhot" class="list_main ">');_html.append('\t\t<ul class="city_sugg ');if(this.pro=='flight'){_html.append('liWidth50');};_html.append('">');for(var i=0;i<this.hot.length;i++)_html.appendFormat("\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"parentbject.add_input_text('{0}','{1}');\">{2}</a></li>",this.hot[i],this.hot[i],this.hot[i]);_html.append("\t\t</ul>");_html.append('\t\t<span class="more_city link01"><a href="javascript:void(0);" onclick="citytab(\'all\');document.getElementById(\'span_sort\').style.display=\'block\';" style="text-decoration:underline;">\u66f4\u591a\u57ce\u5e02</a></span>');
        _html.append("\t</div>");_html.append('\t<div id="cityall" class="list_main unshow">');_html.append('\t\t<ul class="city_list2 link01">');for(i=0;i<this.letter.length;i++)_html.appendFormat("\t\t\t<li><a href=\"javascript:prefixTab('{0}');\">{1}</a></li>",this.letter[i],this.letter[i]);_html.append("\t\t</ul>");
        for(i=0;i<this.letter.length;i++){i==0?_html.appendFormat('\t\t<ul id="ul{0}" class="city_sugg">',this.letter[i]):_html.appendFormat('\t\t<ul id="ul{0}" class="city_sugg unshow">',this.letter[i]);var c=this.hotelcity[this.letter[i]];if(c)for(var j=0;j<c.length;j++)_html.appendFormat("\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"parentbject.add_input_text('{0}','{1}');\">{2}</a></li>",c[j],c[j],c[j]);_html.appendFormat("\t\t</ul>")}_html.append("\t</div>");_html.append("</div>");
        msgplat = _html.toString();
        
        var el = document.getElementById("top_getplatform");

        window.setTimeout(function(){
            replaceHtml(el, msgplat);
            
            document.getElementById("top_getiframe").style.width = document.getElementById("top_getplatform").clientWidth+2;
            document.getElementById("top_getiframe").style.height = document.getElementById("top_getplatform").clientHeight+2;
        },10);
		
		
	}

	/***************************************************fix_div_coordinate*********************************************/
	//函数功能：控制提示div的位置，使之刚好出现在文本输入框的下面
	this.fix_div_coordinate = function(){
		var leftpos=0;
		var toppos=0;
		
		var aTag = this.object;
		do {
			aTag = aTag.offsetParent;
			leftpos	+= aTag.offsetLeft;
			toppos += aTag.offsetTop;
		}while(aTag.tagName!="BODY"&&aTag.tagName!="HTML");
		document.getElementById("top_getiframe").style.width = this.object.offsetWidth+50 + 'px';
		if(document.layers){
			document.getElementById("top_getiframe").style.left = this.object.offsetLeft	+ leftpos + "px";
			document.getElementById("top_getiframe").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 2 + "px";
		}else{
			document.getElementById("top_getiframe").style.left =this.object.offsetLeft	+ leftpos  +"px";
			document.getElementById("top_getiframe").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 'px';
		}
		
		if(document.layers){
			document.getElementById("top_getplatform").style.left = this.object.offsetLeft	+ leftpos + "px";
			document.getElementById("top_getplatform").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 2 + "px";
		}else{
			document.getElementById("top_getplatform").style.left =this.object.offsetLeft	+ leftpos  +"px";
			document.getElementById("top_getplatform").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 'px';
		}
	}

    /***************************************************hidden_suggest*********************************************/
	//函数功能：隐藏提示框
	this.hidden_suggest = function (event){
		if (event.target) targ = event.target;  else if (event.srcElement) targ = event.srcElement;
		if(targ.tagName!='LI' && targ.tagName!='A'){	
		    document.getElementById("top_getiframe").style.visibility = "hidden";
		    document.getElementById("top_getplatform").style.visibility = "hidden";
		}
	}
	this.hidden = function(){if(document.getElementById("top_getiframe")){document.getElementById("top_getiframe").style.visibility = "hidden";document.getElementById("top_getplatform").style.visibility = "hidden";}}

	/***************************************************show_suggest*********************************************/
	//函数功能：显示提示框
	this.show_suggest = function (){
		document.getElementById("top_getiframe").style.visibility = "visible";
		document.getElementById("top_getplatform").style.visibility = "visible";
	}

	this.is_showsuggest= function (){
		if(document.getElementById("top_getplatform").style.visibility == "visible") return true;else return false;
	}

	this.sleep = function(n){
		var start=new Date().getTime(); //for opera only
		while(true) if(new Date().getTime()-start>n) break;
	}

	this.ltrim = function (strtext){
		return strtext.replace(/[\$&\|\^*%#@! ]+/, '');
	}

    /***************************************************add_input_text*********************************************/
	//函数功能：当用户选中时填充相应的城市名字

	this.add_input_text = function (keys,szm){
		keys=this.ltrim(keys)
		this.object.value = keys;
		var id=this.object.id;
		var id2 = this.id2;
		if(document.id2){
			document.getElementById(this.id2).value = szm;
		}
		document.getElementById(id).style.color="#000000";
		document.getElementById(id).value=keys;
		
		document.getElementById("top_getiframe").style.visibility = "hidden";
		document.getElementById("top_getplatform").style.visibility = "hidden";
		
		if(id.indexOf('UCDefaultSearchCar1')>-1){
		    FillRentData(keys,id);
		}
     }
     
     this.ajaxac_getkeycode = function (e){
		var code;
		if (!e) var e = window.event;
		if (e.keyCode) code = e.keyCode;
		else if (e.which) code = e.which;
		return code;
	}

    /***************************************************display*********************************************/
	//函数功能：入口函数，将提示层div显示出来
	//输入参数：object 当前输入所在的对象，如文本框
	//输入参数：e IE事件对象
	this.display = function (object,id2,e){

		this.id2 = id2;
		if(!document.getElementById("top_getplatform")) this.init_zhaobussuggest();
		if (!e) e = window.event;
		e.stopPropagation;
		e.cancelBubble = true;
		if (e.target) targ = e.target;  else if (e.srcElement) targ = e.srcElement;
		if (targ.nodeType == 3)  targ = targ.parentNode;

		this.object = object;
		if(window.opera) this.sleep(100);//延迟0.1秒
		parentbject = this;
		if(this.taskid) window.clearTimeout(this.taskid);
        this.taskid=setTimeout("parentbject.localtext();" , this.delaySec);
	}

	//函数功能：从本地js数组中获取要填充到提示层div中的文本内容
	this.localtext = function(){
		var id=this.object.id;
		parentbject.show_suggest();
		parentbject.fill_div('');
		parentbject.fix_div_coordinate();
	}
};

var letterH=['A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','W','X','Y','Z'];
var hotH=['北京','上海','广州','深圳','三亚','海口','东莞','珠海','青岛','天津','苏州','昆明','大连','沈阳','重庆','杭州','福州','成都','南京','南宁'];
var citysH={A:['阿坝','安康','安庆','鞍山','安顺','安阳'],B:['白城','百色','白山','白银','保定','宝鸡','保山','保亭','包头','巴彦淖尔市','巴中','北海','北京','蚌埠','本溪','毕节','滨州','亳州'],C:['沧州','长白山','长春','常德','昌都','长沙','长治','常州','巢湖','潮州','承德','成都','澄迈县','郴州','赤峰','池州','重庆','楚雄','滁州','崇左'],D:['大理','大连','丹东','儋州','大庆','大同','大兴安岭','达州','德宏','德阳','德州','迪庆','东莞','东营','定安县'],E:['鄂尔多斯','恩施','鄂州'],F:['防城港','佛山','抚顺','阜新','阜阳','福州','抚州'],G:['赣州','甘孜','广安','广元','广州','贵港','桂林','贵阳'],H:['海口','海西','邯郸','杭州','汉中','哈尔滨','鹤壁','合肥','鹤岗','黑河','衡水','衡阳','河源','菏泽','贺州','香港','淮安','淮北','怀化','淮南','黄冈','黄山','黄石','呼和浩特','惠州','葫芦岛','呼伦贝尔','湖州'],J:['佳木斯','吉安','江门','焦作','嘉兴','嘉峪关','揭阳','吉林','济南','晋城','景德镇','荆门','荆州','金华','济宁','晋中','锦州','九江','酒泉','济源'],K:['开封','喀什','克拉玛依','昆明'],L:['莱芜','廊坊','兰州','拉萨','乐山','凉山','连云港','聊城','丽江','临汾','陵水','临沂','林芝','丽水','六安','柳州','龙岩','娄底','漯河','洛阳','泸州'],M:['马鞍山','茂名','眉山','梅州','绵阳','牡丹江'],N:['南昌','南充','南京','南宁','南平','南通','南阳','那曲','内江','宁波','宁德','怒江'],P:['盘锦','攀枝花','平顶山','萍乡','莆田','濮阳'],Q:['黔东南','潜江','黔南','黔西南','青岛','清远','秦皇岛','钦州','琼海','齐齐哈尔','泉州','曲靖','衢州'],R:['日喀则','日照'],S:['三门峡','三明','三亚','上海','上饶','山南','汕头','汕尾','韶关','绍兴','邵阳','沈阳','深圳','石家庄','十堰','遂宁','松原','随州','宿迁','苏州','宿州'],T:['泰安','太原','台州','泰州','唐山','天津','天水','铁岭','通化','通辽','铜陵','吐鲁番'],W:['万宁','潍坊','威海','渭南','文昌','温州','乌海','武汉','芜湖','乌鲁木齐','无锡','五指山','吴忠','梧州'],X:['厦门','西安','襄樊','湘潭','湘西','咸宁','仙桃','咸阳','孝感','锡林郭勒盟','邢台','西宁','新乡','信阳','新余','忻州','西双版纳','宣城','许昌','徐州'],Y:['雅安','延安','延边','盐城','阳江','阳泉','扬州','烟台','宜宾','宜昌','宜春','伊犁','银川','营口','鹰潭','益阳','永州','岳阳','玉林','榆林','运城','玉树','玉溪'],Z:['枣庄','张家界','张家口','张掖','漳州','湛江','肇庆','郑州','镇江','中山','周口','舟山','珠海','驻马店','株洲','淄博','遵义']};
var suggestH = new city_suggest();suggestH.letter = letterH;suggestH.hot = hotH;suggestH.hotelcity = citysH;

var letterF=['A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','S','T','W','X','Y','Z'];
var hotF=["北京","北京首都","北京南苑","上海","上海虹桥","上海浦东","深圳","杭州","广州","成都","南京","武汉","呼和浩特","重庆","长沙","昆明","西安","青岛","天津","宁波","厦门","太原","大连","济南"];
var citysF={A:['阿克苏','阿勒泰','安康','安庆','安顺','鞍山'],B:['百色','蚌埠','包头','保山','北海','北京首都','北京南苑'],C:['昌都','长春','长海','长沙','长治','常德','常州','朝阳','成都','赤峰','重庆'],D:['达县','大理','大连','大庆','大同','丹东','德宏','迪庆','东营','敦煌'],E:['鄂尔多斯','恩施'],F:['佛山','福州','阜阳','富蕴'],G:['赣州','格尔木','光化','广汉','广州','贵阳','桂林'],H:['哈尔滨','哈密','海口','海拉尔','邯郸','汉中','杭州','合肥','和田','黑河','衡阳','呼和浩特','怀化','黄山','黄岩','徽州'],J:['吉安','吉林','济南','济宁','佳木斯','嘉峪关','锦州','晋江','井冈山','景德镇','景洪','九江','九寨沟','酒泉'],K:['喀纳斯','喀什','克拉玛依','库车','库尔勒','昆明'],L:['拉萨','兰州','黎平','丽江','连城','连云港','梁平','林西','林芝','临沧','临沂','柳州','龙岩','庐山','泸州','路桥','洛阳'],M:['满洲里','芒市','梅县','绵阳','漠河','牡丹江'],N:['那拉提','南昌','南充','南京','南宁','南通','南阳','宁波'],P:['攀枝花','普洱'],Q:['齐齐哈尔','且末','秦皇岛','青岛','庆阳','衢州','泉州'],S:['三亚','沙市','汕头','鄯善','上海虹桥','上海浦东','深圳','沈阳','石家庄','思茅'],T:['塔城','台州','太原','天津','通化','通辽','铜仁'],W:['万州','威海','潍坊','温州','文山','乌海','无锡','梧州','武汉','武夷山','乌兰浩特','乌鲁木齐'],X:['西安','西昌','西宁','西双版纳','锡林浩特','厦门','香格里拉','襄樊','兴城','兴宁','兴义','邢台','徐州'],Y:['烟台','延安','延吉','盐城','伊宁','宜宾','宜昌','义乌','银川','永州零陵','榆林','元谋','运城'],Z:['湛江','张家界','昭通','郑州','芷江','中甸','舟山','珠海','遵义']};
var suggestF = new city_suggest();suggestF.letter = letterF;suggestF.hot = hotF;suggestF.hotelcity = citysF;suggestF.pro='flight';

var letterS=['A','B','C','D','E','F','G','H','J','L','M','N','P','Q','R','S','T','W','X','Y','Z'];
var hotS=['杭州','苏州','黄山','宜昌','湖州','洛阳','宣城','清远','无锡','上海','三亚','广州','大连','南京','常州','哈尔滨'];
var citysS={A:['阿坝','定安县','安庆','鞍山','安顺'],B:['百色','保定','宝鸡','保亭','北海','北京','本溪','毕节','滨州','亳州'],C:['长白山','常德','长沙','常州','巢湖','潮州','承德','郴州','池州','重庆','楚雄','滁州','崇左'],D:['大理','大连','丹东','德州','东莞'],E:['恩施'],F:['佛山','阜新','阜阳','福州','抚州'],G:['赣州','甘孜','广安','广元','广州','桂林','贵阳','固原'],H:['海口','杭州','哈尔滨','河池','合肥','河源','菏泽','贺州','淮安','怀化','淮南','黄山','黄石','呼和浩特','惠州','湖州'],J:['吉安','江门','焦作','嘉兴','吉林','济南','晋城','景德镇','金华','济宁','晋中','锦州','九江'],L:['来宾','莱芜','乐山','连云港','临沂','丽水','六安','柳州','龙岩','娄底','洛阳','吕梁'],M:['马鞍山','茂名','眉山','牡丹江'],N:['南昌','南京','南宁','南平','南通','南阳','宁波','宁德'],P:['平顶山','濮阳'],Q:['黔东南','黔南','青岛','清远','秦皇岛','琼海','泉州','衢州'],R:['日照'],S:['三门峡','三明','三亚','上海','商洛','商丘','上饶','汕头','韶关','绍兴','沈阳','深圳','石家庄','十堰','遂宁','随州','宿迁','宿州','苏州'],T:['太原','台州','泰州','唐山','天津','铁岭','铜川','吐鲁番'],W:['万宁','潍坊','威海','渭南','文昌','温州','武汉','芜湖','无锡','梧州'],X:['厦门','西安','湘西','咸宁','咸阳','兴安盟','新乡','忻州','西双版纳','宣城','许昌','徐州'],Y:['雅安','盐城','阳江','阳泉','扬州','烟台','宜昌','伊春','宜春','银川','永州','岳阳','运城','云浮','玉溪'],Z:['枣庄','张家界','张家口','张掖','漳州','湛江','肇庆','郑州','镇江','中山','舟山','珠海','株洲','淄博','遵义']};
var suggestS = new city_suggest();suggestS.letter = letterS;suggestS.hot = hotS;suggestS.hotelcity = citysS;

//Crz:日租|Cjc:机场|Cdc:抵达,出发|Ccjs:城际出发城市|Czj:自驾|
//具体内容已经移至'/hotel/CarCityHandler.ashx?javascript=true'&'\js\Default\CarCityHandler.js'
var letterC=['B','C','D','F','G','H','J','K','L','N','P','Q','S','T','W','X','Y','Z'];
//var hotC=['北京','成都','重庆','大连','广州','杭州','南京','宁波','青岛','深圳','三亚','沈阳','上海','武汉','温州','厦门','西安','珠海'];
var hotCrz=['北京','上海','西安','广州','杭州','南京','苏州','合肥','成都','重庆','昆明','沈阳','武汉','南昌','宁波','青岛','深圳','三亚','无锡','厦门'];
var hotCjc=['上海','北京','广州','杭州','南京','西安','沈阳','昆明','武汉','重庆','成都','南昌','贵阳','青岛','三亚','无锡','深圳','宁波','厦门'];
var hotCdc=['上海','北京','广州','杭州','南京','西安','沈阳','昆明','武汉','重庆','成都','南昌','贵阳','青岛','三亚','无锡','深圳','宁波','厦门','苏州'];
var hotCzj=['北京','上海','南京','成都','三亚','广州','杭州','苏州','厦门','无锡','宁波','深圳','南通','青岛','长沙','温州','重庆','福州','黄山','海口','昆明','丽江','太原','台州'];
var hotCcjs=['北京','上海','西安','广州','杭州','南京','苏州','合肥','成都','重庆','昆明','沈阳','武汉','南昌','宁波','青岛','深圳','三亚','无锡','厦门'];
//
var citysC={B:['北京','北海','包头','宝鸡'],C:['长沙','长春','常州','成都','重庆','常熟市'],D:['东莞','大连','都江堰市'],F:['福州','佛山'],G:['广州','桂林','贵阳','格尔木市'],H:['合肥','黄山','惠州','海口','哈尔滨','黄石','杭州','湖州','海宁市'],J:['吉安','九江','济南','嘉兴','金华','晋江市'],K:['昆明','昆山市'],L:['兰州','洛阳','连云港','泸州','丽江'],N:['南宁','南阳','南京','南通','南昌','南充','宁波'],P:['攀枝花'],Q:['泉州','秦皇岛','青岛','衢州'],S:['深圳','三亚','石家庄','苏州','宿迁','沈阳','上海','绍兴'],T:['泰州','泰安','太原','天津','台州','太仓市'],W:['武汉','无锡','潍坊','乌鲁木齐','温州','武夷山市','乌兰浩特市'],X:['厦门','襄樊','湘潭','徐州','西宁','西安','香格里拉县'],Y:['宜昌','盐城','扬州','银川','烟台','榆林','宜宾','义乌市','余姚市'],Z:['湛江','中山','珠海','张家口','郑州','张家界','镇江','淄博','舟山']};
//var suggestC = new city_suggest();suggestC.letter = letterC;suggestC.hot = hotC;suggestC.hotelcity = citysC;
var suggestCjc = new city_suggest();suggestCjc.letter = letterCjc;suggestCjc.hot = hotCjc;suggestCjc.hotelcity = citysCjc;
var suggestCdc = new city_suggest();suggestCdc.letter = letterCdc;suggestCdc.hot = hotCdc;suggestCdc.hotelcity = citysCdc;
var suggestCrz = new city_suggest();suggestCrz.letter = letterCrz;suggestCrz.hot = hotCrz;suggestCrz.hotelcity = citysCrz;
var suggestCzj = new city_suggest();suggestCzj.letter = letterCzj;suggestCzj.hot = hotCzj;suggestCzj.hotelcity = citysCzj;
var suggestCcjs = new city_suggest();suggestCcjs.letter = letterCcjs;suggestCcjs.hot = hotCcjs;suggestCcjs.hotelcity = citysCcjs;

var letterCBt=['B','C','D','F','G','H','J','K','L','N','Q','S','T','W','X','Y','Z'];
var hotCBt=['苏州','上海','南京','扬州','杭州','北京','天津','常州'];
var citysCBt={B:['保定','北京'],C:['长春','长沙','常州','成都','重庆'],D:['大连','东莞'],F:['佛山','福州'],G:['广州'],H:['海口','杭州','哈尔滨','合肥','黄山','湖州'],J:['嘉兴','济南','金华','九江'],K:['昆明'],L:['丽江'],N:['南昌','南京','南平','南通','宁波'],Q:['青岛'],S:['三亚','上海','绍兴','沈阳','深圳','石家庄','苏州'],T:['太原','天津'],W:['温州','武汉','无锡'],X:['厦门','西安'],Y:['扬州','宜昌'],Z:['镇江','中山','珠海']};

var letterD=['B','C','H','N','Q','S','T','W'];
//var hotD=['杭州','无锡','常州','北京','上海','太原','成都','苏州','南京','深圳','青岛','温州'];
var citysD={B:['北京'],C:['常州','成都'],H:['杭州'],N:['南京'],Q:['青岛'],S:['上海','苏州','深圳'],T:['太原'],W:['无锡','温州']};
var suggestD = new city_suggest();suggestD.letter = letterD;suggestD.hotelcity = citysD;

//兼容的onclick
function OnClickCompatible(id)
{ 
   var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false;
   if(ie)
   {
       document.getElementById(id).click();
   }
   else
   {
       var a=document.createEvent('MouseEvents');
       a.initEvent('click', true, true);
       document.getElementById(id).dispatchEvent(a);
   }
}