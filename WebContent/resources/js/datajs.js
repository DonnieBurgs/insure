function init(obj_1, val_1, obj_2, val_2, obj_3, val_3) {

	// 定义默认数据
	var ar = [ "请选择省份", "请选择城市", "请选择区县" ];
	var pindex = 0;
	var cindex = 0;

	// 初始化
	jQuery("<option value=''>" + ar[0] + "</option>").appendTo(
			jQuery("#" + obj_1)); // 省
	jQuery("<option value=''>" + ar[1] + "</option>").appendTo(
			jQuery("#" + obj_2)); // 市
	jQuery("<option value=''>" + ar[2] + "</option>").appendTo(
			jQuery("#" + obj_3)); // 区

	// 初始化省
	jQuery.ajax( {
		url : projectLink + "/crm/member/view/province",
		dataType : "json",
		type: "POST",
		success : function(provinceList) {
			jQuery.each(provinceList, function(i, province) {
				if (province.provinceId == val_1) {
					jQuery(
							"<option value=\"" + province.provinceId + "\" selected>"
									+ province.name + "</option>").appendTo(
							jQuery("#" + obj_1));
				} else {
					jQuery(
							"<option value=\"" + province.provinceId + "\">"
									+ province.name + "</option>").appendTo(
							jQuery("#" + obj_1));
				}
			});
		}
	});

	if (val_1 != 0) {
		jQuery.ajax( {
			url : projectLink + "/crm/member/view/province",
			type: "POST",
			dataType : "json",
			success : function(provinceList) {
				jQuery.each(provinceList, function(i, province) {
					if (province.provinceId == val_1) {
						pindex = i;
						jQuery(
								"<option value=\"" + province.provinceId
										+ "\" selected>" + province.name
										+ "</option>").appendTo(
								jQuery("#" + obj_1));
					} else {
						jQuery(
								"<option value=\"" + province.provinceId + "\">"
										+ province.name + "</option>")
								.appendTo(jQuery("#" + obj_1));
					}
				});
			}
		});
	}

	if (val_2 != 0) {
		jQuery.ajax( {
			url : projectLink + "/crm/member/view/city",
			type: "POST",
			data : { provinceId : val_1 },
			dataType : "json",
			success : function(cityList) {
				jQuery.each(cityList, function(i, city) {
					if (city.cityId == val_2) {
						cindex = i;
						jQuery(
								"<option value=\"" + city.cityId + "\" selected>"
										+ city.name + "</option>").appendTo(
								jQuery("#" + obj_2));
					} else {
						jQuery(
								"<option value=\"" + city.cityId + "\">"
										+ city.name + "</option>").appendTo(
								jQuery("#" + obj_2));
					}
				});
			}
		});
	}

	if (val_3 != 0) {
		jQuery.ajax( {
			url : projectLink + "/crm/member/view/area",
			type: "POST",
			data : {cityId : val_2},
			dataType : "json",
			success : function(areaList) {
				jQuery.each(areaList, function(i, area) {
					if (area.areaId == val_3) {
						jQuery(
								"<option value=\"" + area.areaId + "\" selected>"
										+ area.name + "</option>").appendTo(
								jQuery("#" + obj_3));
					} else {
						jQuery(
								"<option value=\"" + area.areaId + "\">"
										+ area.name + "</option>").appendTo(
								jQuery("#" + obj_3));
					}
				});
			}
		});
	}

	// 响应 省 的change事件
	jQuery("#" + obj_1).change(
			function() {
				pindex = jQuery("#" + obj_1).get(0).options[jQuery("#" + obj_1)
						.get(0).selectedIndex].value;
				jQuery.ajax( {
					url : projectLink + "/crm/member/view/city",
					type: "POST",
					data : {provinceId : pindex},
					dataType : "json",
					success : function(cityList) {
						// 清空市
					jQuery("#" + obj_2).empty();
					// 重新给市填充内容
					jQuery("<option>" + ar[1] + "</option>").appendTo(
							jQuery("#" + obj_2));

					jQuery.each(cityList, function(i, city) {
						jQuery(
								"<option value=\"" + city.cityId + "\">"
										+ city.name + "</option>").appendTo(
								jQuery("#" + obj_2));
					});

					// 清空区
					jQuery("#" + obj_3).empty();
					jQuery("<option>" + ar[2] + "</option>").appendTo(
							jQuery("#" + obj_3));
				}
				});
			});

	// 响应 市 的change事件
	jQuery("#" + obj_2).change(
			function() {
				cindex = jQuery("#" + obj_2).get(0).options[jQuery("#" + obj_2)
						.get(0).selectedIndex].value;
				// 清空区
				jQuery("#" + obj_3).empty();
				jQuery("<option>" + ar[2] + "</option>").appendTo(
						jQuery("#" + obj_3));

				// 重新给区填充内容
				jQuery.ajax( {
					url : projectLink + "/crm/member/view/area",
					type: "POST",
					data : {cityId : cindex},
					dataType : "json",
					success : function(areaList) {
						jQuery.each(areaList, function(i, area) {
							jQuery(
									"<option value=\"" + area.areaId + "\">"
											+ area.name + "</option>")
									.appendTo(jQuery("#" + obj_3));
						});
					}
				});
			});
}