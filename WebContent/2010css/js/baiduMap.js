var locations = new Array();
var serachLocations = new Array();
var map = null;
var mySquare = null; // 自定义覆盖物
var driving = null; // 驾驶导航
var start = null; // 导航起点
var end = null; // 导航终点
var tempMarker = ""; // 存marker data
var menu = null; // 右键菜单
var markers = new Array(); // marker集合
var clubMarkerClusterer = null;
var trainingMarkerClusterer = null;
var jiudiankerClusterer = null;
var jingdianbMarkerClusterer = null;
var shanghuMarkerClusterer = null;

var pt = null; // 坐标点
var myIcon = null; // 图标
var marker = null; // 标注 marker
var opts = null; // 信息窗体参数
var infoWindow = null; // 信息窗体

var club = null; // 球场
var training = null; // 练习场
var jiudian = null; // 酒店
var jingdian = null; // 景点
var shanghu = null; // 商户
var allMarker = new Array(); //整合

function markClub(zoom, latitude, longitude, clubId, city, searchValue, staus) {
	jQuery("#method").attr("value", "json");	
	jQuery.ajax({
		url: "/map.do",
		data: jQuery("#mapForm").serialize(), 
//			{			
//			clubId: clubId,
//			city: city,
//			searchValue: searchValue,
//			staus: staus,
//			method: "json"
//			} + jQuery("#mapForm").serialize(),
		dataType: "json",
		type: "post",
		cache: true,
		async: false, 
		success: function(allMapList){
			$("#c").val('');
			// 中心点
			map = new BMap.Map("map_canvas");            		// 创建Map实例
			map.centerAndZoom(new BMap.Point(longitude, latitude), zoom);  // 初始化地图,设置中心点坐标和地图级别。
			
			packageMapMarker(allMapList);
//			clubMarkerClusterer = new MarkerClusterer(map, markers); // 批量加载marker
			
	    	addMapEvent(); // 添加map监听事件
	    	addRightKeyMenu(); // 添加右键菜单
	    	addMapPlugin(); // 添加地图插件
	    			
	    	// 初始化导航对象
	    	driving = new BMap.DrivingRoute(map, {renderOptions: {map: map, panel: "results", autoViewport: true}});
		}
	});
}

// 在地图上添加备注
function addMarkerToMap (tempMarker, type) {
//	map.clearOverlays(); // 清除地图上所有覆盖物。
	markers = [];
	var qMoveCenter = true;
	if (tempMarker != null && tempMarker.length > 0) {
		jQuery.each(tempMarker, function(i, club){
			/*
			var imageUrl = "http://211.147.253.131:8888/images/map/";
			// 球场
			if (type == 4) {
				imageUrl += "club/" + club.id + "-1.png";
			}
			// 练习场
			else if (type == 5) {
				imageUrl += "training/" + club.id + "-1.png";
			}
			// 酒店
			else if (type == 1) {
				imageUrl += "jiudian/" + club.id + "-1.png";
			}
			// 景点
			else if (type == 2) {
				imageUrl += "jingdian/" + club.id + "-1.png";
			}
			// 商户
			else if (type == 3) {
				imageUrl += "shanghu/" + club.id + "-1.png";
			}
			*/
			// 切换坐标
			if (qMoveCenter) {
				var q = jQuery("#q").attr("value");
				if (q != null && q != "" && q.length > 0) {
					changeCenter(club.latitude, club.longitude, "", "false");
					changeMapCenterValue(club.latitude, club.longitude);
				}
				qMoveCenter = false;
			}			
			
			// 添加坐标点
			pt = new BMap.Point(club.latitude, club.longitude);
//			myIcon = new BMap.Icon(imageUrl, new BMap.Size(100, 68));
//			myIcon.setImageOffset(new BMap.Size(0, -34)); // 设置图片相对于可视区域的偏移值。
			marker = new BMap.Marker(pt); // , {icon: myIcon}
			if (club.short_name != null && club.short_name.length > 0) {
				marker.setTitle(club.short_name);
			}
			else {
				marker.setTitle(club.product_name);
			}
			marker.enableMassClear(); // 允许覆盖物在map.clearOverlays方法中被清除
//			map.addOverlay(marker);
//			markers.push(marker);
			
		    // 信息窗体
		    opts = {
				// width : 250,     // 信息窗口宽度
				// height: 100,     // 信息窗口高度
				title : club.club_name  // 信息窗口标题
			};
		    infoWindow = new BMap.InfoWindow(club.advertise_message, opts);  // 创建信息窗口对象
			addClickEvent(marker, infoWindow); // 监听marker infoWindows事件
			
			// 添加自定义覆盖物
			var tempUrl = "";
			// 球场
			if (type == 4) {
				tempUrl = "/images/map/club/qiuchang.png";
			}
			// 练习场
			else if (type == 5) {
				tempUrl = "/images/map/club/qiuchang.png";
			}
			// 酒店
			else if (type == 1) {
				tempUrl = "/images/map/jiudian/jiudian.png";
			}
			// 景点
			else if (type == 2) {
				tempUrl = "/images/map/jingdian/jingdian.png";
			}
			// 商户
			else if (type == 3) {
				tempUrl = "/images/map/shanghu/shanghu.png";
			}
			
			mySquare = new SquareOverlay(club.id, pt, marker.getTitle(), tempUrl, 140, 34);
			map.addOverlay(mySquare);
			
//			var nowZoom = map.getZoom(); // 当前倍率
//			if (nowZoom >= 12) {
//				map.addOverlay(marker);
//			}
		});
		
	}
}

// 获取可视化区域的坐标值
function getEastWestSouthNorth () {
	var bounds = map.getBounds();
	var southWest = bounds.getSouthWest(); // 西南
	var northEast = bounds.getNorthEast(); // 东北
	var minX = southWest.lat;
	var minY = southWest.lng;
	var maxX = northEast.lat;
	var maxY = northEast.lng;
	jQuery("#minX").attr("value", minX);
	jQuery("#minY").attr("value", minY);
	jQuery("#maxX").attr("value", maxX);
	jQuery("#maxY").attr("value", maxY);
}

// 根据可视化区域寻找球场
function searchClubByVisual () {
	this.getEastWestSouthNorth();
	jQuery.ajax({
		url: "/map.do",
		data: jQuery("#mapForm").serialize(),
			// markerValues: buildMarkerValues()
		dataType: "json",
		type: "post",
		cache: true,
		async: false, 
		success: function(allMapList){
			// markerClusterer.clearMarkers(); // 清除所有标注
			// addMarkerToMap(allMapList);
			map.clearOverlays(); // 清空地图所有标注
			packageMapMarker(allMapList);
			
//			for (var i = 1; i <= 5; i++) {
//				if (allMarker[i] != null && allMarker[i] != "") {
//					addMarkerToMap(allMarker[i], i);
//				}				
//			}
			
//			for(var i = 1; i <= 5; i++){
//		  		if(allMarker[i] != null && allMarker[i].length > 0 && cBmarkers[tempSort[i-1]].checked == true) {
//		  			addMarkerToMap(allMarker[i], i);
//		  		}
//		  	}
		}
	});
}

// 拼接勾选标签字符串
function buildMarkerValues () {
	var CBmarkers = document.getElementsByName("CBmarker");
  	var temp = ""; var v = "";
  	for(var i = 0; i < CBmarkers.length; i++){
  		if(CBmarkers[i].checked == true){
  	  		v = CBmarkers[i].value;
  			if(i == 0){
	      		temp += v;
	      	}else{
	      		if(temp == ""){
	      			temp += v;
	      		}else{
	      			temp += ", " + v;
	      		}
	      	}
  		}
  	}
  	return temp;
}

// 封装MapMarker
function packageMapMarker(allMapList) {
	allMarker = [];

	if (allMapList.clubs != null && allMapList.clubs != "") {
		allMarker[4] =  allMapList.clubs; // 球场
	}
	if (allMapList.training != null && allMapList.training != "") {
		training = allMapList.training; // 练习场
		allMarker[5] = training;
	}
	if (allMapList.jiudianList != null && allMapList.jiudianList != "") {
		allMarker[1] = allMapList.jiudianList; // 酒店
	}
	if (allMapList.jingdianList != null && allMapList.jingdianList != "") {
		allMarker[2] = allMapList.jingdianList; // 景点
	}
	if (allMapList.shanghuList != null && allMapList.shanghuList != "") {
		allMarker[3] = allMapList.shanghuList; // 商户
	}
	
	for (var i = 1; i <= 5; i++) {
		if (allMarker[i] != null && allMarker[i] != "") {
			addMarkerToMap(allMarker[i], i);
		}				
	}
}

// 转移中心
function changeCenter (latitude, longitude, zoom, serachBool) {
	pt = new BMap.Point(latitude, longitude);
	map.setCenter(pt);
	if (zoom != null && zoom != "") {
		map.setZoom(zoom);
	}
	if (serachBool != null && serachBool != "" && serachBool.length > 0 && serachBool == "true") {
		this.searchClubByVisual();
	}
}

// 获取导航
function getDriving () {
	driving.search(start.getPosition(), end.getPosition());
}

// 增加监听事件
function addClickEvent(marker, infoWindow) {
//	marker.addEventListener("rightclick", function(){
//		menu.show(); // 菜单显示
//	});
	
	marker.addEventListener("click", function(){
//		alert(marker.getPoint());  // v1.1
//		alert(marker.getPosition());  // v1.2
		map.panTo(marker.getPosition()); // 平移中心
		if(tempMarker == "") {
			marker.openInfoWindow(infoWindow);
			tempMarker = marker;
		}
		else if(tempMarker == marker) {
			marker.closeInfoWindow();
			tempMarker = "";
		}
		else {
			marker.closeInfoWindow();
			marker.openInfoWindow(infoWindow);
			tempMarker = marker;
		}
	});
	
	/*
	marker.addEventListener("mouseover", function(){
		marker.setTop(false);
		
		// 添加自定义覆盖物
		mySquare = new SquareOverlay(marker.getPosition(), marker.getTitle(), "/images/map/beijing_1.png", 100, 34);
		map.addOverlay(mySquare);
	});
	
	marker.addEventListener("mouseout", function(){
		marker.setTop(false);
		
		// 删除自定义覆盖物
		map.removeOverlay(mySquare);
		mySquare = null;
	});
	*/
	
	infoWindow.addEventListener("close", function(){
		tempMarker = "";
	});
}

// 添加map监听事件
function addMapEvent () {
	// 地图更改缩放级别开始时触发触发此事件。
	map.addEventListener("zoomstart", function(){
		map.clearOverlays();
	});	
	// 地图更改缩放级别结束时触发触发此事件。
	map.addEventListener("zoomend", function(){
		searchClubByVisual();
	});	
	// 拖拽地图过程中触发。
	map.addEventListener("dragging", function(){
		map.clearOverlays();
	});	
	// 停止拖拽地图时触发。
	map.addEventListener("dragend", function(){
		searchClubByVisual();
	});	
}

// 添加地图插件
function addMapPlugin () {
	// 地图插件
	map.addControl(new BMap.NavigationControl());   // 添加平移缩放控件
	map.addControl(new BMap.ScaleControl());        // 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl());  //添加缩略地图控件
	// 设置事件
	map.enableDragging();							//启用地图拖拽事件，默认启用(可不写)
	map.enableScrollWheelZoom();					//启用地图滚轮放大缩小
    map.enableDoubleClickZoom();					//启用鼠标双击放大，默认启用(可不写)
    map.enableKeyboard();							//启用键盘上下左右键移动地图
}

// 添加右键菜单
function addRightKeyMenu () {
	menu = new BMap.ContextMenu();
	var txtMenuItem = [
		{
			text:'放大',
			callback:function(){map.zoomIn()}
		},
		{
			text:'缩小',
			callback:function(){map.zoomOut()}
		},
		{
			text:'放置到最大级',
			callback:function(){map.zoomTo(18)}
		},
		{
			text:'查看全国',
			callback:function(){map.zoomTo(4)}
		}
		/*,
		{
			text:'设置起点',
			callback:function(e) {
				if (start != null) {
					map.removeOverlay(start);
					start = null;
				}
				pt = new BMap.Point(e.lng, e.lat);
				var marker = new BMap.Marker(pt);
				start = marker;
				map.addOverlay(marker);
				
				// 获取导航
				if (start != null && end != null) {
					getDriving(); 
				}
			}
		},
		{
			text:'设置终点',
			callback:function(e) {
				if (end != null) {
					map.removeOverlay(end);
					end = null;
				}
				pt = new BMap.Point(e.lng, e.lat);
				var marker = new BMap.Marker(pt);
				end = marker;
				map.addOverlay(marker);
				
				// 获取导航
				if (start != null && end != null) {
					getDriving(); 
				}						
			}
		}*/];
	
		for(var i=0; i < txtMenuItem.length; i++){
			menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,100));
			if(i == 1 || i == 3){
				menu.addSeparator();
			}
	    }
		map.addContextMenu(menu);			
}
