function addToCart(id, title) {
	jQuery
			.ajax( {
				type : "post",
				url : path + "cart_add!ajax",
				cache : false,
				data : {
					id : id,
					remark : jQuery("#choose").text()
				},
				dataType : "json",
				success : function(data) {
					if (data.result == true) {
						$(".cartAmount").html(data.amount);
						$(".cartCost").html(data.price);
						$.messager
								.show( {
									title : '系统消息',
									msg : '<div>成功添加'
											+ title
											+ '至购物车！</div><div style="text-align:right;"><a href="'
											+ path
											+ 'cart" target="_blank">去结算</a></div>'
								});
					} else {
						$.messager.show( {
							title : '系统消息',
							msg : '添加至购物车失败！'
						});
					}
				}
			});
}
function addToCartBatch(id) {
	jQuery.ajax( {
		type : "get",
		url : path + "cart_add!ajax!bacth",
		cache : false,
		data : {
			id : id
		},
		dataType : "json",
		success : function(data) {
			if (data == true) {
				$.messager.show( {
					title : '系统消息',
					msg : '成功添加所选至购物车！'
				});
			} else {
				$.messager.show( {
					title : '系统消息',
					msg : '添加至购物车失败！'
				});
			}
		}
	});
}
function updateQuantityAjax(id, o) {
	if (o.value == "") {
		alert("请填写数据");
		o.focus();
	} else if (isNaN(o.value)) {
		alert("数量填写错误");
		o.focus();
	} else {
		jQuery.ajax( {
			type : "get",
			url : path + "cart_update!ajax",
			cache : false,
			data : {
				id : id,
				quantity : o.value
			},
			dataType : "json",
			success : function(data) {
				
			}
		});
	}
}

function removeItemsAjax(id) {
	jQuery.ajax( {
		type : "get",
		url : path + "cart_remove!ajax",
		cache : false,
		data : {
			id : id
		},
		dataType : "json",
		success : function(data) {
			if (data != "") {
				jQuery("#shopItem_" + id).remove();
				jQuery("#totalCost").html(data + "&nbsp;");
			}
		}
	});
}

function clearCartAjax() {
	jQuery.ajax( {
		type : "get",
		url : path + "cart_clear!ajax.htm",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data != "") {
				jQuery("#shopItems").empty();
				jQuery("#totalCost").html(data + "&nbsp;");
			}
		}
	});
}

function collect(id) {
	jQuery.ajax( {
		type : "get",
		url : path + "product/collect/" + id,
		cache : false,
		dataType : "json",
		success : function(data) {
			var msg = "";
			if (data == -1) {
				msg = "您还没<a href='/login.htm'>登录</a>，登录后才能收藏该产品！";
			} else if (data == 1) {
				msg = "成功收藏该产品！";
			} else if (data == 2) {
				msg = "该产品已被收藏！";
			}
			$.messager.show( {
				title : '系统消息',
				msg : msg
			});
		}
	});
}