//markercluster 2011/3  参考:
//http://hi.baidu.com/liongg/blog/item/23d381cbe88d17f752664f0e.html
//http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/
//http://gmaps-utility-library-dev.googlecode.com/svn/tags/markerclusterer/

//标记聚合器 类及其相关接口
function MarkerClusterer(map, opt_markers, opt_options){
    this.map = map;

    this.markers = [];
    this.clusters = [];
    
    var options = opt_options || {};
    this.gridSize = options["gridSize"] || 60;
    this.minClusterSize = options["minClusterSize"] || 2;
    this.maxZoom = options["maxZoom"] || 18;
    this.averageCenter = false;
    if (options['averageCenter'] != undefined) {
        this.averageCenter = options['averageCenter'];
    }
    
    this.styles = options["styles"] || [];
        
    var that = this;
    this.map.addEventListener("zoomend",function(){
        that._clearLastClusters();   
        that._createClusters();      
    });
    
    this.map.addEventListener("moveend",function(){
        that._clearLastClusters();   
        that._createClusters();      
    });
   
    if(opt_markers && opt_markers.length){
        this.addMarkers(opt_markers, false);
    }   
    
}

MarkerClusterer.prototype.addMarkers = function(markers, opt_noRedraw){
    for(var i = 0; i < markers.length; i++){
        this._pushMarkerTo(markers[i]);
    }
    if(!opt_noRedraw){
        this._createClusters();
    }
};

MarkerClusterer.prototype._pushMarkerTo = function(marker){
    marker.isInCluster = false;
    this.markers.push(marker);//Marker拖放后enableDragging不做变化，忽略
};

MarkerClusterer.prototype.addMarker = function(marker, opt_noRedraw) {
    this._pushMarkerTo(marker);
    if (!opt_noRedraw) {
        this._createClusters();
    }
};//加一个，默认需要重绘的，除非显示声明不需要

MarkerClusterer.prototype._createClusters = function(){
    var mapBounds = this.map.getBounds();
    var extendedBounds = this.getExtendedBounds(mapBounds);
    for(var i = 0, marker; marker = this.markers[i]; i++){
        if(!marker.isInCluster && extendedBounds.containsPoint(marker.getPosition()) ){           // //全部一次性算完 稳定算法
            this._addToClosestCluster(marker);
        }
    }   
};

MarkerClusterer.prototype._addToClosestCluster = function(marker){
    var distance = 4000;
    var clusterToAddTo = null;
    var position = marker.getPosition();
    for(var i = 0, cluster; cluster = this.clusters[i]; i++){
        var center = cluster.getCenter();
        if(center){
            var d = this.distanceBetweenPoints(center, marker.getPosition());
            if(d < distance){
                distance = d;
                clusterToAddTo = cluster;
            }
        }
    }
    
    if(clusterToAddTo && clusterToAddTo.isMarkerInClusterBounds(marker)){
        clusterToAddTo.addMarker(marker);
    }else{
        var cluster = new Cluster(this);
        cluster.addMarker(marker);
        this.clusters.push(cluster);
    }    
};

MarkerClusterer.prototype.distanceBetweenPoints = function(p1, p2) {
    if (!p1 || !p2) {
        return 0;
    }
    var R = 6371; 
    var dLat = (p2.lat - p1.lat) * Math.PI / 180;
    var dLon = (p2.lng - p1.lng) * Math.PI / 180;
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(p1.lat * Math.PI / 180) * Math.cos(p2.lat * Math.PI / 180) *
    Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c;
    return d;
};//BMap的扩展 TODO：直接调用BMap的方法？

MarkerClusterer.prototype.getExtendedBounds = function(bounds){
    bounds = this.cutBoundsInRange(bounds);
    var pixelNE = this.map.pointToPixel(bounds.getNorthEast());
    var pixelSW = this.map.pointToPixel(bounds.getSouthWest());
    
    pixelNE.x += this.gridSize;
    pixelNE.y -= this.gridSize;
    pixelSW.x -= this.gridSize;
    pixelSW.y += this.gridSize;

    var newNE = this.map.pixelToPoint(pixelNE);
    var newSW = this.map.pixelToPoint(pixelSW);

    return new BMap.Bounds(newSW, newNE);
};//BMap.Map的扩展

MarkerClusterer.prototype.cutBoundsInRange = function (bounds) {
    var maxX = this.getRange(bounds.getNorthEast().lng, -180, 180);
    var minX = this.getRange(bounds.getSouthWest().lng, -180, 180);
    var maxY = this.getRange(bounds.getNorthEast().lat, -74, 74);
    var minY = this.getRange(bounds.getSouthWest().lat, -74, 74);
    return new BMap.Bounds(new BMap.Point(minX, minY), new BMap.Point(maxX, maxY));
}; //BMap.Map的扩展

MarkerClusterer.prototype.getRange = function (i, mix, max) {
    mix && (i = Math.max(i, mix));
    max && (i = Math.min(i, max));
    return i;
}; //JS函数扩展

MarkerClusterer.prototype._clearLastClusters = function(){
    for(var i = 0, cluster; cluster = this.clusters[i]; i++){
        cluster.remove();
    }
    this.clusters = [];
    this._removeMarkersFromCluster();
};

MarkerClusterer.prototype._removeMarkersFromCluster = function(){
    for(var i = 0, marker; marker = this.markers[i]; i++){
        marker.isInCluster = false;
    }
};

MarkerClusterer.prototype._removeMarkersFromMap = function(){
    for(var i = 0, marker; marker = this.markers[i]; i++){
        marker.isInCluster = false;
        this.map.removeOverlay(marker);       
    }
};

MarkerClusterer.prototype._removeMarker = function(marker) {
    var index = -1;
    if (this.markers.indexOf) {
        index = this.markers.indexOf(marker);
    } else {
        for (var i = 0, m; m = this.markers[i]; i++) {
            if (m == marker) {
            index = i;
            break;
            }
        }
    }

    if (index == -1) {
        return false;
    }

    this.markers.splice(index, 1);
    return true;
};

MarkerClusterer.prototype.removeMarker = function(marker, opt_nodraw) {
    var removed = this._removeMarker(marker);
    if (!opt_nodraw && removed) {
        this._clearLastClusters();
        this._createClusters();
        return true;
    } else {
        return false;
    }
};//删除一个Marker后，默认重绘，除非显示设置为true，不进行重绘。

MarkerClusterer.prototype.removeMarkers = function(markers, opt_nodraw) {
    var removed = false;
    for (var i = 0, marker; marker = markers[i]; i++) {
        var r = this._removeMarker(marker);
        removed = removed || r;
    }

    if (!opt_nodraw && removed) {
        this._clearLastClusters();
        this._createClusters();
        return true;
    }
};

MarkerClusterer.prototype.clearMarkers = function() {
  this._clearLastClusters();
  this._removeMarkersFromMap();
  this.markers = [];
};

MarkerClusterer.prototype.redraw = function () {
    this._clearLastClusters();
    this._createClusters();
};
//开放属性，设置后需调用 重绘接口
MarkerClusterer.prototype.getGridSize = function() {
    return this.gridSize;
};
MarkerClusterer.prototype.setGridSize = function(size) {
    this.gridSize = size;
};

MarkerClusterer.prototype.setMaxZoom = function(maxZoom) {
  this.maxZoom = maxZoom;
};
MarkerClusterer.prototype.getMaxZoom = function() {
  return this.maxZoom;
};

MarkerClusterer.prototype.setStyles = function(styles) {
  this.styles = styles;
};
MarkerClusterer.prototype.getStyles = function() {
  return this.styles;
};

MarkerClusterer.prototype.getMinClusterSize = function() {
    return this.minClusterSize;
};
MarkerClusterer.prototype.setMinClusterSize = function(size) {
    this.minClusterSize = size;
};

//为Cluster类提供一些方法

MarkerClusterer.prototype.isAverageCenter = function() {
  return this.averageCenter;
};

MarkerClusterer.prototype.getMap = function() {
  return this.map;
};

//为用户提供一些便利方法
MarkerClusterer.prototype.getMarkers = function() {
    return this.markers;
};

MarkerClusterer.prototype.getMarkersCount = function() {
    return this.markers.length;
};

MarkerClusterer.prototype.getClustersCount = function() {
    return this.clusters.length;
};

//..........................................
//Cluster类，链接MarkerClusterer和ClusterMarker：一个Cluster中有多个初始Marker
function Cluster(markerClusterer){
    this.markerClusterer = markerClusterer;
    this.map = markerClusterer.getMap();
    this.minClusterSize = markerClusterer.getMinClusterSize();
    this.averageCenter = markerClusterer.isAverageCenter();
    this.center = null;
    this.markers = [];
    this.bounds = null;
    
    this.clusterMarker = new ClusterMarker(this.center, this.markers.length, {"styles":this.markerClusterer.getStyles()});
    this.map.addOverlay(this.clusterMarker);
}

Cluster.prototype.addMarker = function(marker){
    if(this.isMarkerInCluster(marker)){
        return false;
    }//也可用marker.isInCluster判断,外面判断OK，这里基本不会命中
    
    if(!this.center){
        this.center = marker.getPosition();
        this.calculateBounds();
    }else{
        if(this.averageCenter){
            var l = this.markers.length + 1;
            var lat = (this.center.lat * (l - 1) + marker.getPosition().lat) / l;
            var lng = (this.center.lng * (l - 1) + marker.getPosition().lng) / l;
            this.center = new BMap.Point(lng,lat);
            this.calculateBounds();
        }//计算新的Center
    }
    
    marker.isInCluster = true;
    this.markers.push(marker);
    
    var len = this.markers.length;
    if(len < this.minClusterSize ){      //marker.isAddedToMap 搞个程序判断一下？Baidu自己判断
        this.map.addOverlay(marker);
    }
    if (len == this.minClusterSize) {
        for (var i = 0; i < len; i++) {
            this.map.removeOverlay(this.markers[i]);
        }
    }
    if (len >= this.minClusterSize) {
        this.map.removeOverlay(marker);
    }

    this.updateClusterMarker();
    return true;
};
//可以加个标记的 marker.isInCluster = true;
Cluster.prototype.isMarkerInCluster= function(marker){
    if (this.markers.indexOf) {
        return this.markers.indexOf(marker) != -1;
    } else {
        for (var i = 0, m; m = this.markers[i]; i++) {
            if (m == marker) {
                return true;
            }
        }
    }
    return false;
};//JS函数扩展

Cluster.prototype.isMarkerInClusterBounds = function(marker) {
    return this.bounds.containsPoint(marker.getPosition());
};

Cluster.prototype.calculateBounds = function() {
    var bounds = new BMap.Bounds(this.center, this.center);
    this.bounds = this.markerClusterer.getExtendedBounds(bounds);
};

Cluster.prototype.updateClusterMarker = function () {
    if (this.map.getZoom() > this.markerClusterer.getMaxZoom()) {
        for (var i = 0, marker; marker = this.markers[i]; i++) {
            if (this.clusterMarker) {
                this.map.removeOverlay(this.clusterMarker);
            }
            this.map.addOverlay(marker);
        }
        return;
    }

    if (this.markers.length < this.minClusterSize) {
        this.clusterMarker.hide();
        return;
    }

    this.clusterMarker.setPosition(this.center);
    this.clusterMarker.setText(this.markers.length);
    this.clusterMarker.show();
    this.clusterMarker.setBounds(this.getBounds()); //更新Bounds
};

Cluster.prototype.remove = function(){
    this.map.removeOverlay(this.clusterMarker);
    this.markers.length = 0;
    delete this.markers;
}

Cluster.prototype.getBounds = function() {
    var bounds = new BMap.Bounds(this.center,this.center);
    for (var i = 0, marker; marker = this.markers[i]; i++) {
        bounds.extend(marker.getPosition());
    }
    return bounds;
};

Cluster.prototype.getCenter = function() {
    return this.center;
};

//..........................................
//自定义Overlay类，类似Marker，但比Marker复杂
function ClusterMarker(point, text, opt_options){           
    this.text = text;
    this.point = point;
    
    var options = opt_options || {};
    this.styles = options["styles"] || [];
    if(this.styles.length == 0){
        this._setupDefaultStyles();       
    }
    
    this.bounds = options["bounds"] || null;
}

ClusterMarker.prototype = new BMap.Overlay(); 
ClusterMarker.prototype.IMAGE_PATH = 'http://211.147.253.131:8888/images/map/m/';
ClusterMarker.prototype.IMAGE_EXTENSION  = 'png';

ClusterMarker.prototype.initialize = function(map){
    this.map = map;
    
    this.div = document.createElement('div');     
    this._updateCss();    
    this._updateText();
    this._updatePoint();
    
    var that = this;
    this.div.onclick = function(event){
        that._clickHandler(event);
        if(that.bounds){
           that.fitBounds(that.map, that.bounds);
        }
    };
    
    map.getPanes().markerMouseTarget.appendChild(this.div);
    return this.div;   
};

ClusterMarker.prototype.fitBounds = function(map, bounds){
    map.setViewport([bounds.getNorthEast(),
            new BMap.Point(bounds.getNorthEast().lng, bounds.getSouthWest().lat),
            new BMap.Point(bounds.getSouthWest().lng, bounds.getNorthEast().lat),
            new bounds.getSouthWest()
        ]);
    
};//对Baidu.Map的扩展

ClusterMarker.prototype.draw = function(){
     this._updatePoint();
};

ClusterMarker.prototype.getText = function(){
    return this.text;
};
ClusterMarker.prototype.setText = function(text){
    if(text && (!this.text || (this.text.toString() != text.toString()))){
        this.text = text;
        this._updateText();
        this._updateCss();
        this._updatePoint(); 
    }
};

ClusterMarker.prototype.getPosition = function () {
    return this.point;
};
ClusterMarker.prototype.setPosition = function (point) {
    if(point && (!this.point || !this.point.equals(point))){
        this.point = point;  
        this._updatePoint();
    }
};

ClusterMarker.prototype._calculator = function(text, stylesLength){
    var count = parseInt(text);
    var index = parseInt(count / 10);
    index = Math.min(index, stylesLength - 1);
    return index;
};
ClusterMarker.prototype.setCalculator = function(calculator) {
    this._calculator = calculator;
};
ClusterMarker.prototype.getCalculator = function() {
    return this._calculator;
};

ClusterMarker.prototype.setBounds = function(bounds) {
    this.bounds = bounds;
};
ClusterMarker.prototype.getBounds = function() {
    return this.bounds;
};

ClusterMarker.prototype._clickHandler = function(event){
}
ClusterMarker.prototype.addEventListener = function(type, handler){
    if(type === "click"){
        this._clickHandler = handler;
    }
}

//更新文字的"CSS"
ClusterMarker.prototype._updateText = function(){
    if (this.div) {
        this.div.innerHTML = this.text;
    }
};
//更新位置信息的CSS
ClusterMarker.prototype._updatePoint = function(){
    if (this.div && this.point) {
        var pixelPosition= this.map.pointToOverlayPixel(this.point); 
        pixelPosition.x -= parseInt(this.width / 2, 10);
        pixelPosition.y -= parseInt(this.height / 2, 10);
        this.div.style.left = pixelPosition.x + "px";
        this.div.style.top = pixelPosition.y + "px";
    }
};
//更新不含位置信息的CSS，也即没有设置top left
ClusterMarker.prototype._updateCss = function(){
    var styleIndex = this.getCalculator()(this.text,this.styles.length);
    var css = this._buildCssText(styleIndex);
    this.div.style.cssText = css;
};

ClusterMarker.prototype._setupDefaultStyles = function(){  
    this.sizes = [53, 56, 66, 78, 90];
    this.imagePath = this.IMAGE_PATH + "m";
    this.imageExtension = this.IMAGE_EXTENSION;
    for(var i = 0, size; size = this.sizes[i]; i++){
        this.styles.push({
            url:this.imagePath + i + '.' + this.imageExtension,
            height:size,
            width:size
        });
    }
};

ClusterMarker.prototype._buildStyle = function(index) {
    index = Math.max(0, index);
    index = Math.min(this.styles.length - 1, index);
    var style = this.styles[index];
    this.url = style['url'];
    this.height = style['height'];
    this.width = style['width'];
    this.anchor = style['anchor'];
    this.textColor = style['textColor'];
    this.textSize = style['textSize'];
    this.backgroundPosition = style['backgroundPosition'];
};

ClusterMarker.prototype._buildCssText = function(index) {    
    this._buildStyle(index);
    var csstext = [];
    if (document.all) {
        csstext.push('filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(' +
            'sizingMethod=scale,src="' + this.url + '");');
    } else {
        csstext.push('background-image:url(' + this.url + ');');
        var backgroundPosition = this.backgroundPosition ? this.backgroundPosition : '0 0';
        csstext.push('background-position:' + backgroundPosition + ';');
    }

    if (typeof this.anchor === 'object') {
        if (typeof this.anchor[0] === 'number' && this.anchor[0] > 0 &&
            this.anchor[0] < this.height_) {
              csstext.push('height:' + (this.height - this.anchor[0]) +
                  'px; padding-top:' + this.anchor[0] + 'px;');
    } else {
        csstext.push('height:' + this.height + 'px; line-height:' + this.height + 'px;');
    }
    if (typeof this.anchor[1] === 'number' && this.anchor[1] > 0 && this.anchor[1] < this.width) {
        csstext.push('width:' + (this.width - this.anchor[1]) + 'px; padding-left:' + this.anchor[1] + 'px;');
    } else {
        csstext.push('width:' + this.width + 'px; text-align:center;');
    }
    } else {
        csstext.push('height:' + this.height + 'px; line-height:' +
            this.height + 'px; width:' + this.width + 'px; text-align:center;');
    }

    var txtColor = this.textColor ? this.textColor : 'black';
    var txtSize = this.textSize ? this.textSize : 11;
    
    csstext.push('cursor:pointer; color:' + txtColor + '; position:absolute; font-size:' +
        txtSize + 'px; font-family:Arial,sans-serif; font-weight:bold');
    return csstext.join('');
};
