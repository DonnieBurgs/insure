/*************    方法     **************/
		var day1 = new Array();
		var day2 = new Array();

	//判断是否是闰年,计算每个月的天数
	function leapYear(year){
		var isLeap = year%100==0 ? (year%400==0 ? 1 : 0) : (year%4==0 ? 1 : 0);
		return new Array(31,28+isLeap,31,30,31,30,31,31,30,31,30,31);
	}

	//获得某月第一天是周几
	function firstDay(day){
		return day.getDay();
	}

	//获得当天的相关日期变量
	function dateNoneParam(){
		var day = new Date();
		var today = new Array();
		today['year'] = day.getFullYear();
		today['month'] = day.getMonth();
		today['date'] = day.getDate();
		today['hour'] = day.getHours();
		today['minute'] = day.getMinutes();
		today['second'] = day.getSeconds();
		today['week'] = day.getDay();
		today['firstDay'] = firstDay(new Date(today['year'],today['month'],1)); 
		return today;
	}

	//获得所选日期的相关变量
	function dateWithParam(year,month){
		var day = new Date(year,month);
		var date = new Array();
		date['year'] = day.getFullYear();
		date['month'] = day.getMonth();
		date['firstDay'] = firstDay(new Date(date['year'],date['month'],1));
		return date;
	}

	function makeKalTitle(codeYear,codeMonth){
		kalendar_html = "    <section class=\"bgwhite\" style=\"border-bottom:1px solid #dddddd;\" onclick=\"projectClick(" + codeYear + "年" + codeMonth + "月);\">";
		kalendar_html += "    <div class=\"divwrap\"> ";
		kalendar_html += "	  <div class=\"divmain\"> ";
		kalendar_html += "      	<div class=\"kaltoolscenter\">";
		kalendar_html += "      		<p style=\"vertical-align:middle;height:18px;line-height:50px;\"><font id=\"fontMonth\" color=\"#444444\" style=\"text-align:center;font-size:18px;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;word-break: break-all;overflow: hidden;\">" + codeYear + "年" + (codeMonth+1) + "月</font></p>";
		kalendar_html += "      	</div> ";
		kalendar_html += "	  </div> ";
		kalendar_html += "   	  <div class=\"kaltoolsleft\">";
		kalendar_html += "      	<p style=\"margin-top:15px;margin-right:15px;text-align:right;line-height:16px;vertical-align:middle;\">";
		kalendar_html += "			<img id=\"monthPrev\" onclick=\"monthPrevClick();\" src=\"/weixin/images/date_left.png\" style=\"width:20px;\"/>";
		kalendar_html += "		</p>";
		kalendar_html += "	  </div> ";
		kalendar_html += "	  <div class=\"kaltoolsright\">";
		kalendar_html += "      	<p style=\"margin-top:15px;margin-right:15px;text-align:right;line-height:16px;vertical-align:middle;\">";
		kalendar_html += "			<img id=\"monthNext\" onclick=\"monthNextClick();\" src=\"/weixin/images/date_right.png\" style=\"width:20px;\"/>";
		kalendar_html += "		</p>";
		kalendar_html += "	  </div> ";
		kalendar_html += "    </div>";
		kalendar_html += "    </section> ";

		kalendar_html += "<div class=\"kalTitle\"\>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalWeekend\" onclick=\"\">日</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalOther\" onclick=\"\">一</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalOther\" onclick=\"\">二</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalOther\" onclick=\"\">三</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalOther\" onclick=\"\">四</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalOther\" onclick=\"\">五</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "    <div class=\"kalTitleBox kalWeekend\" onclick=\"\">六</div>";
		kalendar_html += "    <div class=\"block\"></div>";
		kalendar_html += "</div>";
		return kalendar_html;
	}
	
	//生成日历代码 的方法
	//参数依次为 年 月 日 当月第一天(是星期几)
	function kalendarCode(codeYear,codeMonth,codeDay,codeFirst){
		kalendar_html = "";
		$('#fontMonth').html("" + codeYear + "年" + (codeMonth+1) + "月");

		//日 列表
		for(var m=0;m<6;m++){//日期共 4-6 行
			if(m >= Math.ceil((codeFirst+monthDays[codeMonth])/7)){//第五、六行是否隐藏				
				kalendar_html += "<div class=\"kalDate kalHidden\"\>";
				kalendar_html += "    <div class=\"block\"></div>";
			}else{
				kalendar_html += "<div class=\"kalDate\"\>";
				kalendar_html += "    <div class=\"block\"></div>";
			}	

			for(var n=0;n<7;n++){//列
				if((7*m+n) < codeFirst || (7*m+n) >= (codeFirst+monthDays[codeMonth])){//某月日历中不存在的日期
					if(((7*m+n)%7 == 0) || ((7*m+n)%7 == 6)){//仅是周末
						kalendar_html += "    <div class=\"kalDateBox kalWeekend\" onclick=\"\"></div>";
					}else
						kalendar_html += "    <div class=\"kalDateBox kalOther\" onclick=\"\"></div>";
				}else{
					//if((7*m+n+1-codeFirst == today['date'])&&(((7*m+n)%7 == 0) || ((7*m+n)%7 == 6))){//当天是周末
					//	kalendar_html += "    <div class=\"kalDateBox kalTodayWeekend\" onclick=\"\">"+(7*m+n+1-codeFirst)+"<br><font style=\"font-size:14px;line-height:15px;\">休息</font></div>";
					//}else 
					if((7*m+n+1-codeFirst == today['date']) && (codeYear==today['year']) && (codeMonth==today['month'])){//仅是当天
						kalendar_html += "    <div class=\"kalDateBox kalToday\" onclick=\"dateClick(this, "+(7*m+n+1-codeFirst)+", "+n+");\">"+(7*m+n+1-codeFirst)+"<br><font class=\"kalDateFont\" id=\"kfont"+(7*m+n+1-codeFirst)+"\">-</font></div>";
					}else if(((7*m+n)%7 == 0) || ((7*m+n)%7 == 6)){//仅是周末
						kalendar_html += "    <div class=\"kalDateBox kalWeekend\" onclick=\"dateClick(this, "+(7*m+n+1-codeFirst)+", "+n+");\">"+(7*m+n+1-codeFirst)+"<br><font class=\"kalDateFont\" id=\"kfont"+(7*m+n+1-codeFirst)+"\">-</font></div>";
					}else {//其他日期
						kalendar_html += "    <div class=\"kalDateBox kalOther\" onclick=\"dateClick(this, "+(7*m+n+1-codeFirst)+", "+n+");\">"+(7*m+n+1-codeFirst)+"<br><font class=\"kalDateFont\" id=\"kfont"+(7*m+n+1-codeFirst)+"\">-</font></div>";
					}
				}
				kalendar_html += "    <div class=\"block\"></div>";
			}
			kalendar_html += "</div>";
		}
		kalendar_html += "    <div style=\"width:100%;height:2px;\"></div>";
		return kalendar_html;
	}

	//年-月select框改变数值 的方法
	//参数依次为 1、操作对象(年下拉菜单 或 月下拉菜单) 2、被选中的下拉菜单值
	function y_mChange(obj,stopId){
		obj.val(stopId);
	}

	//修改日历列表 的方法
	//参数依次为 操作对象(每一天) 月份 修改后的第一天是星期几 修改后的总天数 当天的具体日期
	function dateChange(dateObj,dateYear,dateMonth,dateFirstDay,dateTotalDays,dateCurrentDay){
		//判断新日历有几行,需要显示或隐藏
		var newLine = Math.ceil((dateFirstDay+monthDays[dateMonth])/7);//新行数
		if(newLine > dateLine){//增加行
			for(var i=dateLine;i<newLine;i++){
				$('.dayListHide'+i).show();
			}
		}else if(newLine < dateLine){//减少行
			for(var i=dateLine-1;i>=newLine;i--){
				$('.dayListHide'+i).hide();
			}
		}
		//重置日期排序
		dateLine = newLine;
		/*如果改变 月 后，选中月的总天数小于当前日期，
		*如当前3.31，选中2月，可2月最多29天，则将当前日期改为选中月的最后一天，即2.39
		*/
		if(dateTotalDays < dateCurrentDay){
			dateCurrentDay = dateTotalDays;
		}
		
		for(var i=0;i<7*newLine;i++){
			if(i < dateFirstDay || i> (dateTotalDays+dateFirstDay-1)){//日历中 当月不存在的日期
				dateObj.eq(i).text('').removeClass();
			}else{
				if((i+1-dateFirstDay == dateCurrentDay) && (i%7 == 0 || i%7 == 6)){
					dateObj.eq(i).removeClass().addClass('todayWeekend');
				}else if(i%7 == 0 || i%7 == 6){//仅周末
					dateObj.eq(i).removeClass().addClass('weekend');
				}else if(i+1-dateFirstDay == dateCurrentDay){//仅当天
					dateObj.eq(i).removeClass().addClass('today');
				}else{//其他日期
					dateObj.eq(i).removeClass();
				}
				//dateObj.eq(i).text(i+1-dateFirstDay);
				dateObj.eq(i).html("<span style='line-height:30px;width:100%;height:100%;font-size:22px;' onclick='scheduleList("+dateYear+","+dateMonth+","+(i+1-dateFirstDay)+")'>"+(i+1-dateFirstDay)+"<br></span>");

			}
			
		}
	}
	
	function loadSchedule(year,month,mdays) {
		var argument = "year=" +year+"&month="+month+"&monthdays="+mdays+"&id="+$("#doctorid").val();
		$.ajax({  
	    url: 'anSchedule.do?method=blank&'+ encodeURI(argument),  
	    dataType: 'json',  
	    success: function(data){  
	        var xrray = data['d'].split("");
	        for(var i=0;i<mdays;i++) {
	        	day1[i+1] = xrray[i];
	        	day2[i+1] = xrray[i+mdays] ;
	        }
//			dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	


	    }  
	});  
	}
	
	function saveSchedule(year,month,mdays,checkstr) {
		var argument = "year=" +year+"&month="+month+"&monthdays="+mdays+"&id="+$("#doctorid").val()+"&checkstr="+checkstr;
		$.ajax({  
	    url: 'anSchedule.do?method=update&'+ encodeURI(argument),  
	    dataType: 'json',  
	    success: function(data){  
	        var xrray = data['d'].split("");
	        alert("保存成功。") ;
	        for(var i=0;i<mdays;i++) {
	        	day1[i+1] = xrray[i];
	        	day2[i+1] = xrray[i+mdays] ;
	        }
			dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	


	    }  
	});  
	}

	function setupCal() {
		rili_location = $('#kalendar');//日历代码的位置
		kalendar_html = '';//记录日历自身代码 的变量

		someDay = dateNoneParam();//修改后的某一天,默认是当天
		yearChange = someDay['year'];//改变后的年份，默认当年
		monthChange = someDay['month'];//改变后的年份，默认当月
		$('#kalendarTitle').html(makeKalTitle(someDay['year'],someDay['month']));
		
		kalendar_html = kalendarCode(someDay['year'],someDay['month'],someDay['date'],someDay['firstDay']);
		rili_location.html(kalendar_html);
		closeMenu();
	}

	function setupReportCal() {
		someDay = dateNoneParam();//修改后的某一天,默认是当天
		yearChange = someDay['year'];//改变后的年份，默认当年
		monthChange = someDay['month'];//改变后的年份，默认当月
		
		kalendar_html = "    <section class=\"bgwhite\" style=\"border-bottom:1px solid #dddddd;\">";
		kalendar_html += "    <div class=\"divwrap\"> ";
		kalendar_html += "	  <div class=\"divmain\"> ";
		kalendar_html += "      	<div class=\"kaltoolscenter\">";
		kalendar_html += "      		<p style=\"vertical-align:middle;height:18px;line-height:50px;\"><font id=\"fontMonth\" color=\"#444444\" style=\"text-align:center;font-size:18px;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;word-break: break-all;overflow: hidden;\">" + yearChange + "年" + (monthChange+1) + "月</font></p>";
		kalendar_html += "      	</div> ";
		kalendar_html += "	  </div> ";
		kalendar_html += "   	  <div class=\"kaltoolsleft\">";
		kalendar_html += "      	<p style=\"margin-top:15px;margin-right:15px;text-align:right;line-height:16px;vertical-align:middle;\">";
		kalendar_html += "			<img id=\"monthPrev\" onclick=\"monthPrevClick();\" src=\"/weixin/images/date_left.png\" style=\"width:20px;\"/>";
		kalendar_html += "		</p>";
		kalendar_html += "	  </div> ";
		kalendar_html += "	  <div class=\"kaltoolsright\">";
		kalendar_html += "      	<p style=\"margin-top:15px;margin-right:15px;text-align:right;line-height:16px;vertical-align:middle;\">";
		kalendar_html += "			<img id=\"monthNext\" onclick=\"monthNextClick();\" src=\"/weixin/images/date_right.png\" style=\"width:20px;\"/>";
		kalendar_html += "		</p>";
		kalendar_html += "	  </div> ";
		kalendar_html += "    </div>";
		kalendar_html += "    </section> ";


		$('#kalendarTitle').html(kalendar_html);
	}

	function dateClick(o, dnum, n) { //n横向第几格
		var x = 0, y = 0; 
	    do { 
	        x += o.offsetLeft; 
	        y += o.offsetTop; 
	    } while (o = o.offsetParent);
	    menuDate = dnum;
	    //alert(menuDate + ","+wtSel[menuDate]);
	    if(menuDate>0) {
	    	if(!wtSel[menuDate]) wtSel[menuDate] = ",";
	    	for(i=0;i<wtListLength;i++) {
	    		if(wtSel[menuDate].indexOf(","+i+",")>=0) {
	    			$('#menuItem'+i).css('background','#FF676C');
	    		} else {
	    			$('#menuItem'+i).css('background','#636871');
	    		}
	    	}
	    }
	    if(n<5) {
		    $('#kMenu').css('top',(y-10)+'px').css('left',(x+50)+'px').show();
	    	
	    } else {
		    $('#kMenu').css('top',(y-10)+'px').css('left',(x-80)+'px').show();
	    	
	    }
	}

	function closeMenu() {
		$('#kMenu').hide();
	}
	
	function menuItemClick(wtid, x) { //x menuItem的序号
		if(!wtSel[menuDate]) wtSel[menuDate] = ",";
		if(wtSel[menuDate].indexOf(","+x+",")>=0) {
			wtSel[menuDate] = wtSel[menuDate].replace(","+x+",",",");
			$('#menuItem'+x).css('background','#636871');
		} else {
			wtSel[menuDate] += x+",";
			$('#menuItem'+x).css('background','#FF676C');
		}
		var fonttmp = "";
    	for(i=0;i<wtListLength;i++) {
    		if(wtSel[menuDate].indexOf(","+i+",")>=0) {
    			fonttmp += wtList[i]['worktypename'].substring(0,1);
    		}
    	}
    	if(fonttmp!="") {
    		$('#kfont'+menuDate).css('color','#FF3B2F').html(fonttmp);
    	} else {
    		$('#kfont'+menuDate).css('color','#555555').html("-");
    	}
	}
	
	function monthPrevClick(){
		monthChange --;
		if(monthChange >= 0){//仍在本年内
			if(yearChange == 2016 && monthChange<2){
				monthChange = 2 ;
				return false;
			}
			//修改月份
//			y_mChange(selectMonth,monthChange);			
		}else{
			monthChange = 11;//前一年的最后一个月
			yearChange --;
			if(yearChange < 2016){
				yearChange = 2016;
				monthChange = 0;
				return false;
			}
			//修改月份
//			y_mChange(selectMonth,monthChange);
			//修改年份
//			y_mChange(selectYear,yearChange);
			//重新获得 每月天数
			monthDays = leapYear(yearChange);
		}
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		kalendar_html = kalendarCode(someDay['year'],someDay['month'],someDay['date'],someDay['firstDay']);
		rili_location.html(kalendar_html);
//		dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	
//		loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;
		wtSel = new Array();
		menuDate = 0;

		loadScheduleListData(someDay['year'], someDay['month']+1, false);
		if(boxSel==9) {
			fillReportData();
		} else if(boxSel>=4 || boxSel<=8) {
			fillCalData();
		}

	}
	
	function monthNextClick(){
		monthChange ++;
		if(monthChange <= 11){//仍在本年内
			//修改月份
//			y_mChange(selectMonth,monthChange);
		}else{
			monthChange = 0;//下一年的第一个月
			yearChange ++;
			if(yearChange >= someDay['year']+yearfloor){
				yearChange = someDay['year']+yearfloor;
				alert('太久远也没意义了...');
				return false;
			}
			//修改月份
//			y_mChange(selectMonth,monthChange);
			//修改年份
//			y_mChange(selectYear,yearChange);
			//重新获得 每月天数
			monthDays = leapYear(yearChange);
		}
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		kalendar_html = kalendarCode(someDay['year'],someDay['month'],someDay['date'],someDay['firstDay']);
		rili_location.html(kalendar_html);
//		dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	
		
//		loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;
		wtSel = new Array();
		menuDate = 0;
		
		loadScheduleListData(someDay['year'], someDay['month']+1, false);
		if(boxSel>=4 || boxSel<=8) {
			fillCalData();
		}

	}
	
/*************    缓存节点和变量     **************/
	var rili_location = $('#kalendar');//日历代码的位置
	var kalendar_html = '';//记录日历自身代码 的变量
	var yearfloor = 10;//选择年份从1970到当前时间的后10年

	var someDay = dateNoneParam();//修改后的某一天,默认是当天
	var yearChange = someDay['year'];//改变后的年份，默认当年
	var monthChange = someDay['month'];//改变后的年份，默认当月

/*************   将日历代码放入相应位置，初始时显示此处内容      **************/

	//获取时间，确定日历显示内容
	var today = dateNoneParam();//当天

	/*月-日 设置*/
	var month = new Array('一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月');
	var monthDays = leapYear(today['year']);//返回数组，记录每月有多少天
	var weekDay = new Array('日','一','二','三','四','五','六');
	// alert('年:'+someDay['year']+'\n月:'+someDay['month']+'\n日:'+someDay['date']+'\n星期:'+someDay['week']+'\n本月第一天星期:'+someDay['firstDay']);return false;
	
//	$('#kalendarTitle').html(makeKalTitle(today['year'],today['month']));
	
//	kalendar_html = kalendarCode(today['year'],today['month'],today['date'],today['firstDay'],today['date']);
//	rili_location.html(kalendar_html);

/*************   js写的日历代码中出现的节点     **************/
	var yearPrev = $('#yearPrev');//上一年按钮
	var yearNext = $('#yearNext');//下一年按钮
	var monthPrev = $('#monthPrev');//上一月按钮
	var monthNext = $('#monthNext');//下一月按钮
	var btn1 = $('#btn1');//提交
	var selectYear = $('#year .selectChange select');//选择年份列表
	var listYear = $('#year .selectChange select option');//所有可选年份
	var selectMonth = $('#month .selectChange select');//选择月份列表
	var listMonth = $('#month .selectChange select option');//所有可选月份
	var dateLine = Math.ceil((monthDays[today['month']]+today['firstDay'])/7);;//当前日历中有几行日期，默认是 当年当月
	var dateDay = $('#kalendar tr#day td ul.dayList li');//日历中的每一天

//	dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	
//	loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;

/***************************/


	//年 按钮事件
	yearPrev.bind('click',function(){
		yearChange --;
		if(yearChange < 2016){
			yearChange = 2016;
			return false;
		}
		//修改年份
		y_mChange(selectYear,yearChange);
		//重新获得 每月天数
		monthDays = leapYear(yearChange);//alert(monthDays);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	
//		loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;
	});

	yearNext.bind('click',function(){
		yearChange ++;
		if(yearChange >= today['year']+yearfloor){
			yearChange = today['year']+yearfloor;
			alert('太后也没意义了...');
			return false;
		}
		//修改年份
		y_mChange(selectYear,yearChange);
		//重新获得 每月天数
		monthDays = leapYear(yearChange);//alert(monthDays);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	
//		loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;

	});


	// 年 选择事件
	selectYear.bind('change',function(){
		//获得年份
		yearChange = $(this).val();
		//修改年份
		y_mChange(selectYear,yearChange);
		//重新获得 每月天数
		monthDays = leapYear(yearChange);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
//		loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;

	});

	// 月 选择事件
	selectMonth.bind('change',function(){
		//获得 月
		monthChange = $(this).val();
		//修改月份
		y_mChange(selectMonth,monthChange);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['year'],someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
//		loadSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']]) ;

	});

	/*日 鼠标事件*/
	dateDay.hover(function(){
		$(this).addClass('mouseFloat');
	},function(){
		$(this).removeClass('mouseFloat');
	});
	
	//提交
	btn1.bind('click',function(){
		checkstr = "" ;
		for(var i=1;i<=monthDays[someDay['month']];i++) {
			if($('#c1' + i).attr("checked")==true)
				checkstr += "1";
			else
				checkstr += "0";
		}
		for(var i=1;i<=monthDays[someDay['month']];i++) {
			if($('#c2' + i).attr("checked")==true)
				checkstr += "1";
			else
				checkstr += "0";
		}
		saveSchedule(someDay['year'],someDay['month'],monthDays[someDay['month']],checkstr) ;
	});


	
