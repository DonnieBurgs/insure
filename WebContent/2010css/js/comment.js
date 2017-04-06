var mobile = "^(13|15|18)[0-9]{9}$";
function isMobile(str){
	return new RegExp(mobile).test(str);
}
function changValue(v){
	var s = v + "";
	if(s.indexOf(".") > -1){
		return s.replace(".", "d");
	}else{
		return s + "d0";
	}
}

function listReview(pid, s, m){
	$.ajax({
		type: "get", cache: false, dataType: "json",
		url: "/clubServlet.do",
		data: {method: "list", pid: pid, s: s, m: m}, 
		success: function(items){
			var rows = $("#reviewRows");
			if(items && $(items).size() > 0){
				$.each(items, function(i, o){
					var r = (o.review == 5 ? "好评" : o.review == 4 ? "中评" : "差评");
					var html = "";
					html += "<div class='zyleft_tr2'>";
					html += " <div class='trsp1'><span class='trsp1_sp1'>" + o.review + "分</span><span class='trsp1_sp2'>" + r + "</span></div>";
					html += " <div class='trsp2'>";
					html += "  <ul>";
					var name = o.user_name;
					if(name.length == 11 && isMobile(name)){
						name = name.substring(0, 8) + "***";
					}
					html += "   <li class='li01'>" + name + "于 " + o.create_time + " 发表</span><span class='sp01'>" + r + "</span></li>";
					html += "   <li class='li01'>服务质量：<span class='c-value-no c-value-" + o.service + "d0' title='" + o.service + ".0/5.0'></span>&nbsp;球场质量：<span class='c-value-no c-value-" + o.court + "d0' title='3.0/5.0'></span></li>";
					html += "   <li class='li01'>内容：" + o.suggest + "</li>";
					html += "  </ul>";
					html += " </div>";
					html += "</div>";
					rows.append(html);
					
				});
				if(undefined != typeof(index) && 'undefined' != typeof(index) && index){
					index += 1;
				}
			}
		}
	});
}

function countReview(pid){
	$.ajax({
		type: "get", cache: false, dataType: "json",
		url: "/clubServlet.do",
		data: {method: "count", pid: pid, s: 1, m: 5}, 
		success: function(items){
			if(items && $(items).size()>0){
				var map = items[0];
				var total = map.good + map.mid + map.bad;
				$("#reviewTotal").text(total);
				$("#reviewGood").text(map.good);
				$("#reviewMid").text(map.mid);
				$("#reviewBad").text(map.bad);
				var good = 0;
				if(map.good > 0)
					good = Math.round((map.good / total * 100) * 10 /10) + "%";
				$("#reviewGoodCss").css({width: good});
				$("#reviewGoodCssV").text(good);
				var mid = 0;
				if(map.mid > 0)
					mid = Math.round((map.mid / total * 100) * 10 /10) + "%";
				$("#reviewMidCss").css({width: mid});
				$("#reviewMidCssV").text(mid);
				var bad = 0;
				if(map.bad > 0)
					bad = Math.round((map.bad / total * 100) * 10 /10) + "%";
				$("#reviewBadCss").css({width: bad});
				$("#reviewBadCssV").text(bad);
				$("#servicev").addClass("c-value-" + changValue(map.service)).next().text(map.service);
				$("#courtv").addClass("c-value-" + changValue(map.court)).next().text(map.court);
				$("#reviewv").addClass("c-value-" + changValue(map.review)).next().text(map.review);

				$("#totalScore").text(map.review);
				$("#totalReview").text(total);
			}
		}
	});
}