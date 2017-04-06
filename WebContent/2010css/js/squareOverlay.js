// 定义自定义覆盖物的构造函数   
function SquareOverlay(id, center, content, imageUrl, width, height) {
	this._id = id;
	this._center = center;
	this._content = content;
	this._imageUrl = imageUrl;
	this._width = width;
	this._height = height;
}   
  
// 继承API的BMap.Overlay   
SquareOverlay.prototype = new BMap.Overlay();

// 实现初始化方法   
SquareOverlay.prototype.initialize = function (map) {
	
  	// 保存map对象实例   
	this._map = map;   
  	// 创建div元素，作为自定义覆盖物的容器   
	var div = document.createElement("div");
	div.innerHTML = this._content; // 填充内容
	div.style.position = "absolute";
	
	
  	// 可以根据参数设置元素外观
	var tempId = this._id;
	div.id = "club_" + tempId;
	div.align = "center";
	div.style.width = this._width + "px";
	div.style.height = this._height + "px";
//	div.style.backgroundColor = "";
	div.style.backgroundImage = "url(" + this._imageUrl + ")";
	div.style.fontFamily = "宋体";
	div.style.lineHeight = "28px";
	div.style.fontSize = "12px";
//	div.style.color = "#FFFFFF";
	div.style.color = "#000000";
	div.style.fontWeight = "bold";
	div.style.zindex = 1;
	div.onclick = function(){opdlg(tempId); return;}; // 单击事件
	div.onmouseover = function(){document.getElementById('club_' + tempId).style.zindex = 9999999; return;};
	div.onmouseout = function(){document.getElementById('club_' + tempId).style.zindex = 1; return;};

  	// 将div添加到覆盖物容器中   
	map.getPanes().markerPane.appendChild(div);   
  
	// 保存div实例   
	this._div = div;
  
  	// 需要将div元素作为方法的返回值，当调用该覆盖物的show、
  	// hide方法，或者对覆盖物进行移除时，API都将操作此元素。   
	return div;
};

// 实现绘制方法   
SquareOverlay.prototype.draw = function () {
  	// 根据地理坐标转换为像素坐标，并设置给容器   
	var position = this._map.pointToOverlayPixel(this._center);
	this._div.style.left = position.x - this._width / 2 + "px";
	this._div.style.top = position.y - this._height + "px";
};  

// 实现显示方法   
SquareOverlay.prototype.show = function () {
	if (this._div) {
		this._div.style.display = "";
	}
};   
  
// 实现隐藏方法   
SquareOverlay.prototype.hide = function () {
	if (this._div) {
		this._div.style.display = "none";
	}
};

