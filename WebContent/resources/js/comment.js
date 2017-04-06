function reloadImage(id) {
	document.getElementById(id).src = path + "code?code=" + Math.round(Math.random() * 10000);
}
function submitComment(o) {

	if (jQuery("#content").val() == "") {
		alert("\u8bc4\u8bba\u5185\u5bb9\u4e0d\u80fd\u4e3a\u7a7a\uff01");
		return false;
	}
	var code = jQuery("#code").val();
	var result = false;
	if (code == "") {
		alert("\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801!");
	} else {
		jQuery.ajax( {
			type : "get",
			url : path + "vailidate_code",
			cache : false,
			async : false,
			data : {
				code : jQuery("#code").val()
			},
			dataType : "json",
			success : function(json) {
				if (json == 1) {
					jQuery.ajax( {
						type : "post",
						url : path + "comment_submit",
						data : jQuery(o).serialize(),
						dataType : "json",
						success : function(data) {
							if (data !== "") {
								loadCommect(1, data.objectType, data.objectId);
								o.reset();
								reloadImage("code-img");
								window.location.href = "#rows";
							}
						}
					});
				} else {
					alert("验证码输入错误!");
				}
			}
		});
	}
	return false;
}
function loadCommect(index, type, id, size) {
	if(size == 'undefined')
		size = 10;
	
	jQuery.ajax( {
		type : "get",
		url : path + "comment_list",
		cache : false,
		data : {
			objectType : type,
			objectId : id,
			page : index,
			rows : size
		},
		dataType : "json",
		success : function(data) {
			jQuery("#rows").empty();
			jQuery.each(data.items, function(i, row) {
				jQuery("#rows").append(getRow(row));
			});
			var foot = "";
			foot += "<div class='bar'>";
			if (data.pageCount > 1) {
				foot += "<a href=\"javascript:loadCommect(" + data.firstIndex + ", " + type + ", " + id + ", " + size + ");\">\u9996\u9875</a></li>";
				foot += "<a href=\"javascript:loadCommect(" + data.previousIndex + ", " + type + ", " + id + ", " + size + ");\">\u4e0a\u4e00\u9875</a></li>";
				if (data.pageCount > 1) {
					for ( var i in data.indexs) {
						var index = parseInt(i) + 1;
						if (data.startIndex == index) {
							foot += "<span class='current'>" + index + "</span>";
						} else {
							foot += "<a href=\"javascript:loadCommect(" + index + ", " + type + ", " + id + ", " + size + ");\">" + index + "</a>";
						}
					}
				}
				foot += "<a href=\"javascript:loadCommect(" + data.nextIndex + ", " + type + ", " + id + ", " + size + ");\">\u4e0b\u4e00\u9875</a></li>";
				foot += "<a href=\"javascript:loadCommect(" + data.lastIndex + ", " + type + ", " + id + ", " + size + ");\">\u5c3e\u9875</a></li>";
				if (data.pageCount > 5) {
					foot += "<select onChange=\"loadCommect(this.value, " + type + ", " + id + ", " + size + ");\">";
					for ( var i = 1; i <= data.pageCount; i++) {
						foot += "<option value=\"" + i + "\"";
						if (data.startIndex == i) {
							foot += " selected";
						}
						foot += ">";
						foot += "\u7b2c" + i + " \u9875";
						foot += "</option>";
					}
					foot += "</select>";
				}
			}
			foot += "</div>";
			jQuery("#pageFoot").empty();
			jQuery("#pageFoot").append(foot);
		}
	});
}
function getRow(obj) {
	var result = "";
	result += "<li style='border-bottom: 1px dashed #CCCCCC; margin: 10px 0; padding: 10px;'>";
	result += "<div class=\"user\">";
	result += "<div class=\"iName\" style='font-size: 14px; margin: 3px 0;'>" + obj.userName + "      <img src='" +path + "resources/themes/blueglass/images/stars" + obj.score + ".gif' /></div>";
	result += "</div>";
	result += "<div class=\"userText\">";
	result += "<p><span style=\"float: left;\">\u5185\u5bb9\uff1a</span><span style=\"float: left;\">" + obj.content + "</span></p>";
	if (obj.replyContent != "" && obj.replyContent != null) {
		result += "<p><span style=\"float: left;\">\u56de\u590d\uff1a</span><span style=\"float: left;\">" + obj.replyContent + "</span></p>";
	}
	result += "<i class=\"iTitle\" style='font-size: 10px; color:  #CCCCCC; margin: 0 0 0 10px;'>\u8bc4\u8bba\u65f6\u95f4\uff1a" + obj.createTime + "</i>";
	result += "</div>";
	result += "<div class=\"clear\"></div>";
	result += "</li>";
	return result;
}