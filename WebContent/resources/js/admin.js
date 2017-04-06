/**
 * 打开会员视图
 */
function openMemberView(id, name){
	var url = root + "/crm/member/view/" + id;
	var dlg = new J.dialog({
		id: id,
        title:name,
        page:url,
        width:800,
        height:480,
        height:510,resize: true,cancelBtnTxt: "关闭"
    });
	dlg.ShowDialog();
}
/**
 * 打开通话视图
 */
function openMemberCallView(name){
	var url = root + "/crm/member/callview";
	var dlg = new J.dialog({
		id: 0,
        title:name,
        page:url,
        width:800,
        height:480,
        height:510,resize: true,cancelBtnTxt: "关闭"
    });
	dlg.ShowDialog();
}
function openMemberCallView(mobile, t, calltaskId, name){
	var url = root + "/crm/member/callview?mobile=" + mobile + "&t=" + t + "&calltaskId=" + calltaskId;
	var dlg = new J.dialog({
		id: 0,
        title:name,
        page:url,
        width:800,
        height:480,
        height:510,resize: true,cancelBtnTxt: "关闭"
    });
	dlg.ShowDialog();
}
/**
 * 打开会员视图
 */
function openMemberViewTab(id, name, tab){
	var url = root + "/crm/member/view/" + id +"?tabs=" + tab;
	var dlg = new J.dialog({
		id: id,
        title:name,
        page:url,
        width:800,
        height:480,
        height:510,resize: true,cancelBtnTxt: "关闭"
    });
	dlg.ShowDialog();
}
/**
 * 打开会员视图
 */
function openMemberViewByUser(user){
	var url = root + "/crm/member/view?user=" + user;
	var dlg = new J.dialog({
		id: "memberView",
        title:user,
        page:url,
        width:800,
        height:510,resize: true,cancelBtnTxt: "关闭"
    });
	dlg.ShowDialog();
}
/**
 * 在openview 的基础上再打开会员视图
 */
function openMemberInnerView(id, name){
	var dg = frameElement.lhgDG;
	var url = root + "/crm/member/view/" + id;
	var inner = new dg.curWin.J.dialog({
		id: id,
        title:name,
        page:url,
        width:800,
        height:480,
        cancelBtnTxt: "关闭",
		parent: dg
    });
	inner.ShowDialog();
}
/**
 * 在openview 的基础上再打开会员视图，并带tabs参数显示指定页
 * @param id
 * @param name
 * @param tab
 * @return
 */
function openMemberInnerViewTab(id, name, tab){
	var dg = frameElement.lhgDG;
	var url = root + "/crm/member/view/" + id + "?tabs=" + tab;
	var inner = new dg.curWin.J.dialog({
		id: id,
        title:name,
        page:url,
        width:800,
        height:480,
        cancelBtnTxt: "关闭",
		parent: dg
    });
	inner.ShowDialog();
}
/**
 * 在openview 的基础上 打开产品视图
 */
function openProductInnerView(id, name){
	var dg = frameElement.dg;
	var url = root + "/mall/product/form/" + id;
	var inner = new dg.curWin.J.dialog({
		id: id,
        title:name,
        page:url,
        width:850,
        height:550,
        rang: true,cover:true,resize:true,btns: false,
		parent: dg
    });
	inner.ShowDialog();
}
/**
 * input:checkbox 名字 为 id 的全选
 * 
 * @return
 */
function checkAll() {
	var ck = jQuery("#checkall").attr("checked");
	$("#op :checkbox[name=id]").each(function(i, o) {
		$(this).attr("checked", ck);
	});
}
/**
 * 删除所选记录
 * 
 * @return
 */
function deleteSelected() {
	if($("#op :checkbox:checked").size() == 0){
		alert("请选勾选要操作的数据");
		return false;
	}

	if (confirm("确定要删除所选数据！？")) {
		$.ajax( {
			type : "post",
			cache : false,
			url : "delete",
			data : $("#op").serialize(),
			dataType : "json",
			success : function(msg) {
				window.location.reload();
			}
		});
	}
}
/**
 * 删除单行记录
 * @param id
 * @return
 */
function deleteById(id) {
	if (confirm("确定要删除所选数据！？")) {
		$.ajax( {
			type : "post",
			cache : false,
			url : "delete",
			data : {id: id},
			dataType : "json",
			success : function(msg) {
            	$("#tr_" + id).css("background-color", "#FFF5FA");
			}
		});
	}
}
/**
 * 排序操作
 * 
 * @return
 */
function sort(s, o) {
	$("#sort").val(s);
	$("#order").val(o);
	document.search.submit();
}
/**
 * 删除服务器上的文件
 * @param id
 * @return
 */
function deletefile(id){
	var v = $("#" + id).val();
	$.ajax({
		type: "post", cache: false, url : root + "/file/delete", data: {fileName: v}, success: function(o){$("#" + id).val('');$("#" + id + "Img").attr('src', '');}
	});
}
/**
 * 清空当前控件的值
 * @param id
 * @return
 */
function clearfile(id){
	$("#" + id).val('');
}
/**
 * 搜索表单提交
 * @param method
 * @param target
 * @return
 */
function submitform(method, target){
	var frm = document.all.search;
	frm.action = method;
	frm.target = target;
	frm.submit();
}
/**
 * 构建分页脚
 * @param func
 * @param start
 * @param rows
 * @return
 */
function bar(func, page){
	var bar = "";
	if(page.pageCount > 1){
		bar += "<div class='bar'>";
		bar += "<span class=\"disabled\">页码: </span>";
		if (page.startIndex > 1) {
			bar += "<a href=\"javascript:" + func + "("+ page.previousIndex + ", " + page.pageSize + ");\">上一页</a>";
			if (page.startIndex > page.firstIndex + 5 && page.indexs.length < page.pageCount) {
				bar += "<a href=\"javascript:" + func + "("+ page.firstIndex + ", " + page.pageSize + ");\">"+ page.firstIndex + " ...</a>";
			}
		}
		for (var index = 1; index <= page.indexs.length; index ++) {
			if (page.startIndex == index) {
				bar += "<span class=\"current\">" + index + "</span>";
			} else {
				bar += "<a href=\"javascript:" + func + "("+ index + ", " + page.pageSize + ");\">" + index + "</a>";
			}
		}
	
		if (page.startIndex < page.pageCount) {
			if (page.tartIndex < page.lastIndex - 5 && page.indexs.length < page.pageCount) {
				bar += "<a href=\"javascript:" + func + "("+ page.lastIndex + ", " + page.pageSize + ");\">"+ page.lastIndex + " ...</a>";
			}
			bar += "<a href=\"javascript:" + func + "("+ page.nextIndex + ", " + page.pageSize + ");\">下一页</a>";
		}
		bar += "</div>";
	}
	return bar;
}