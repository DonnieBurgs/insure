$$.module.pkgInterface = {};
$$.module.pkgInterface.BaseInfo = {};


function extend(target, src){
	for (var s in src){
		target[s] = src[s];
	}
	return target;
};

function using(namespaces, target){
	target = target || window;
	var namespace,
		nameList;
	for(var i = 0, l = namespaces.length; i < l; i ++){
		namespace = namespaces[i];
		if($type(namespace) == "array" && $type(namespace[1]) == "array"){
			nameList = namespace[1];
			namespace = namespace[0];
			for(var j = 0, ll = nameList.length; j < ll; j ++)
				target[nameList[j]] = namespace[nameList[j]];
		}else{
			extend(target, namespace);
		}
	}
}


//*****************************
//推荐给朋友 cdchu 090911
//*****************************
$r("domReady", function () {
    var sendToFriendsDiv = $("sendToFriendsDiv");
    if (!sendToFriendsDiv || !$$.module.pkgInterface.sendToFriendsAjax)
        return;
    var sendToFriendsBtn = $("sendToFriendsBtn");
    var sendToFriendsTable = $("sendToFriendsTable");
    var sendToFriendsSucceed = $("sendToFriendsSucceed");
    var sendToFriendsFailed = $("sendToFriendsFailed");
    var username = $("username");
    var inputs = $(sendToFriendsBtn.parentNode).$("input");
    var strProductName = inputs[2];
    var strProductURL = inputs[3];
    var email = [];
    var ajaxFlag = false;
    var i = 1;
    while (1) {
        var tmpObj = $("email" + i);
        if (tmpObj) {
            email.push(tmpObj);
            i++;
        } else
            break;
    }
    sendToFriendsBtn.$r("click", function () {
        var usernameStr = username.value.trim();
        if (!usernameStr) {
            $alert(username, "请输入您的姓名", false);
            return false;
        }
        var arr = [];
        for (var i = 0; i < email.length; i++) {
            var tmpStr = email[i].isNull() ? "" : email[i].value.trim();
            if (tmpStr) {
                if (!tmpStr.isEmail()) {
                    $alert(email[i], "电子邮箱地址格式错误<br />格式例如：name@ctrip.com", false);
                    return false;
                }
                arr.push(tmpStr);
            }
        }
        if (!arr.length) {
            $alert(email[0], "请输入至少一个电子邮箱地址", false);
            return false;
        }
        //var str = "username=" + escape(usernameStr) + "&email=" + escape(arr.join(";")) + "&" + ($$.module.pkgInterface.sendToFriendsExt || "");
        var str = "username=" + escape(usernameStr) + "&email=" + escape(arr.join(";")) + "&PkgName=" + escape(strProductName.value.trim()) +
                  "&PkgUrl=" + escape(strProductURL.value.trim());
        sendToFriendsBtn.disabled = true;
        sendToFriendsSucceed.style.display = "none";
        sendToFriendsFailed.style.display = "none";
        ajaxFlag = true;
        $ajax($$.module.pkgInterface.sendToFriendsAjax, str, function (str) {
            if (/^true$/i.test(str || "")) {
                sendToFriendsTable.style.display = "none";
                sendToFriendsSucceed.style.display = "";
                for (var i = 0; i < email.length; i++) {
                    email[i].value = "";
                    if (email[i].module.notice)
                        email[i].module.notice.check();
                }
            } else
                sendToFriendsFailed.style.display = "";
            sendToFriendsBtn.disabled = false;
            ajaxFlag = false;
        });
    });

    //关闭浮出层
    var sendToFriendsClose = $("sendToFriendsClose");
    sendToFriendsClose.$r("click", function () {
        maskShow();
    });

    //初始化链接
    var sendToFriendLink = $("sendToFriendLink");
    if (sendToFriendLink) {
        sendToFriendLink.$r("click", function () {
            maskShow(sendToFriendsDiv, true);
            if (!ajaxFlag) {
                sendToFriendsTable.style.display = "";
                sendToFriendsSucceed.style.display = "none";
                sendToFriendsFailed.style.display = "none";
            }
        });
    }
});





/**
* @namespace Func 自定义函数空间
*/
var Func = {};

/**
* @namespace Func.Business 业务函数空间
*/
Func.Business = {};


/**
* @namespace Func.Base 基础函数空间
*/
Func.Base = {};

/**
* @namespace Func.Base.DateLib 日期相关操作函数空间
*/
Func.Base.DateLib = {
	compareDate : function(date1, date2){
		date1 = this.strToDate(date1);
		date2 = this.strToDate(date2);
		return (date1 - date2)/24/3600/1000;
	},
	addDate : function(date, addDays){
		date = this.strToDate(date);
		date = new Date(date.getFullYear(), date.getMonth(), date.getDate() + addDays*1);
		return date;
	},
	maxDate : function(date1, date2){//返回两个日期较晚的一个
		date1 = this.strToDate(date1);
		date2 = this.strToDate(date2);
		var date = new Date(Math.max(date1, date2));
		return date;
	},
	minDate : function(date1, date2){//返回两个日期较早的一个
		date1 = this.strToDate(date1);
		date2 = this.strToDate(date2);
		var date = new Date(Math.min(date1, date2));
		return date;
	},
	isInDate: function(date, dateStart, dateEnd){
		date = this.strToDate(date);
		dateStart = this.strToDate(dateStart);
		dateEnd = this.strToDate(dateEnd);
		return date >= dateStart && date <= dateEnd ? true : false;
	},
	strToDate: function(str){
		if(typeof(str) == "string"){
			str = str.split("-");
			return new Date(str[0], str[1]-1, str[2]);
		}else{
			return str;
		}
	},
	dateToStr: function(date){
		return typeof(date) == "string" ? date : date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	}
};

/**
* @namespace Func.Dom 自定义Dom处理空间
*/
Func.Dom = {};

/**
* @namespace Func.Dom.Class 自定义Class处理函数空间
*/
Func.Dom.Class = {
	hasClass: function(el, className){
		var regex = new RegExp("(^| )" + className + "( |$)");
		return regex.test(el.className);
	},
	addClass: function(el, className){
		if(!this.hasClass(el, className)){
			el.className = el.className ? el.className + " " + className : className;
		}
	},
	removeClass: function(el, className){
		if(this.hasClass(el, className)){
		    var regex = new RegExp("^" + className + "( |$)");
		    if(regex.test(el.className)){
			    el.className = el.className.replace(className, "").trim();
		    }else{
			    el.className = el.className.replace(" " + className, "");
		    }
		}
	}
};

Func.Dom.Query = {
    childNodes: function(el, condition){
        condition = condition || function(el){
            if(el && el.nodeType == 1)
                return true;
            else
                return false;
        };
        
        var _childNodes = el.childNodes,
            childNodes = [];
        
        for(var i = 0, l = _childNodes.length; i < l; i ++){
            if(condition(_childNodes[i])){
                childNodes.push(_childNodes[i]);
            }
        }
        return childNodes;
    },
    parentNode: function(el, condition){
        condition = condition || function(el){
            if(el)
                return true;
            else
                return false;
        };
        
        do{
            el = el.parentNode;
        }while(el && !condition(el));
        
        return el ? el : null;
    }
};

/**
* @namespace Func.Struct 自定义Dom处理空间
*/
Func.Struct = {
	Tab: function(btns, pages, selectedIndex, btnClass, callback){//btnClass[0]:不选中类名；btnClass[1]:选中类名
		selectedIndex = selectedIndex || 0;
		for(var i = 0, l = btns.length; i < l; i ++){
			(function(i){
				btns[i].$r("click", function(){
					//if(i == selectedIndex) return;
					if(pages[selectedIndex])pages[selectedIndex].style.display = "none";
					if(pages[i])pages[i].style.display = "";
					if(btnClass){
						btns[selectedIndex].className = btns[selectedIndex].className.replace(btnClass[1], "");
						btns[selectedIndex].className += btnClass[0];
						btns[i].className = btns[i].className.replace(btnClass[0], "");
						btns[i].className += btnClass[1];
					}
					if(callback)callback(i, selectedIndex);
					selectedIndex = i;
				});
			})(i);
		}
		$(btns[selectedIndex]).$click();
	},
	Tab2: function(list, selectedIndex, btnClass){
		selectedIndex = selectedIndex || 0;
		function checkTabPage(el){
			if(el){
				var tabPage =  el.getAttribute("tabpage");
				if(tabPage){
					return $(tabPage);
				}
			}
			return null;
		}
		for(var i = 0, l = list.length; i < l; i ++){
			(function(i){
				$(list[i]).$r("click", function(){
					var tabPage = checkTabPage(list[selectedIndex]);
					if(tabPage)tabPage.style.display = "none";
					tabPage = checkTabPage(this);
					if(tabPage)tabPage.style.display = "";
					if(btnClass){
						removeClass(list[selectedIndex], btnClass[1]);
						addClass(list[selectedIndex], btnClass[0]);
						removeClass(this,btnClass[0]);
						addClass(this, btnClass[1]);
					}
					selectedIndex = i;
				});
			})(i);
			removeClass(list[i], btnClass[1]);
			addClass(list[i], btnClass[0]);
			var tabPage = checkTabPage(list[i]);
			if(tabPage)tabPage.style.display = "none";
		}
		list[selectedIndex].$click();
	}
};

/**
* @namespace Obj 自定义对象空间
*/
var Obj = {};

/**
* @class Fselect 选择框
* @constructor
* @param {object} opts 配置项
* @returns {object} 一个选择框对象
* @userList: [
	"private_package_searchresult_100916.js",
	"private_specialtyshop.js"
  ]
*/
Obj.Fselect = function(opts){
	var defaults = {
		viewElement : null,
		showEvent : "focus",
		hiddenEvent : "blur",
		showDiv : null,
		selectEvent : "mousedown",
		testItem : function(element){
			if(element.tagName == "A"){
				return element.innerHTML;
			}else{
				return false;
			}
		},
	    onchanges : []
	};
	$extend(defaults, opts);
	this.initialize(defaults);
};
Obj.Fselect.prototype = {
	initialize : function(opts){
		this.setOptions(opts);
		this.bindEvents();
		this.showDiv.style.position = "absolute";
	},
	setOptions : function(opts){
		$extend(this, opts);
		$(this.viewElement);
		$(this.showDiv);
	},
	bindEvents : function(){
		var _this = this;
		this.viewElement.$r(_this.showEvent, function(){
			_this.showDiv.style.display = "block";
			var pos = _this.viewElement.$getPos();
			_this.showDiv.style.left = pos[0] + "px";
			_this.showDiv.style.top = pos[1] + _this.viewElement.offsetHeight + "px";
			_this.showDiv.style.margin = 0;
			_this.showDiv.$setIframe();
		});
		this.viewElement.$r(_this.hiddenEvent, function(){
			_this.showDiv.style.display = "none";
			_this.showDiv.$clearIframe();
		});
		this.showDiv.$r(_this.selectEvent, function(e){
			e = e || window.event;
			var target = e.target || e.srcElement;
			var value = _this.testItem(target);
			if(value){
				value = value.trim();
				_this.viewElement.value = value;
				for(var i = 0, l = _this.onchanges.length; i < l; i ++){
					_this.onchanges[i](value);
				}
				_this.viewElement.blur();
			}else{
				$stopEvent(e,1);
			}
		});
	}
};

/**
* @class displayIndex 展示索引
* @constructor
* @param {object} opts 配置项
* @returns {object} 一个展示索引对象
* @userList: [
	"private_package_details_freewalker.js",
	"private_package_details_destination.js",
	"private_package_details_team.js"
  ]
*/
Obj.displayIndex = function(opts){
	var defaults = {
		lists : [],
		selectedIndex : 0,
		scrollIndex : 0,
		displayLength : 4,
		scrollLeft : null,
		scrollRight : null,
		scrollCallback : function(){},
		callback : function(){}
	};
	this.extend(defaults, opts);
	this.initialize(defaults);
};
Obj.displayIndex.prototype = {
	initialize : function(opts){
		this.setOptions(opts);
		if(this.length){
		    this.bindEvent();
		    this.callback(this.lists[this.selectedIndex], this.lists[this._selectedIndex]);
		    this.scrollCallback(this.scrollIndex, Math.min(this.scrollIndex + this.displayLength, this.length));
		}
	},
	setOptions : function(opts){
		this.extend(this, opts);
		this._selectedIndex = this.selectedIndex;
		this.length = this.lists.length;
		for(var i = 0; i < this.length; i ++){
			this.lists[i].dIndex = i;
		}
	},
	bindEvent : function(){
		var _this = this;
		this.scrollLeft.$r("click", function(){
			if(_this.scrollIndex > 0)_this.scrollIndex --;
			_this.displayRange(_this.scrollIndex, Math.min(_this.scrollIndex + _this.displayLength, _this.length));
		});
		this.scrollRight.$r("click", function(){
			if(_this.scrollIndex + _this.displayLength < _this.length)_this.scrollIndex ++;
			_this.displayRange(_this.scrollIndex, Math.min(_this.scrollIndex + _this.displayLength, _this.length));
		});
		function listClick(){
			_this._selectedIndex = _this.selectedIndex;
			_this.selectedIndex = this.dIndex;
			_this.callback(_this.lists[_this.selectedIndex], _this.lists[_this._selectedIndex]);
		}
		for(var i = 0; i < this.length; i ++){
			this.lists[i].$r("click", listClick);
		}
	},
	displayRange : function(rangeStart, rangeEnd){
		for(var i = 0; i < rangeStart; i ++){
			this.lists[i].style.display = "none";
		}
		for(var i = rangeStart; i < rangeEnd; i ++){
			this.lists[i].style.display = "";
		}
		for(var i = rangeEnd; i < this.length; i ++){
			this.lists[i].style.display = "none";
		}
		this.scrollCallback(rangeStart, rangeEnd);
	},
	extend : function(target, src){
		for(var s in src){
			target[s] = src[s];
		}
	}
};

/**
* @class Clock 时钟类
* @constructor
* @param {int} t 时间间隔
* @param {function} callback 执行函数
* @returns {object} 一个时钟对象
* @userList: [
	"private_specialtyshop.js"
  ]
*/
Obj.Clock = function(t,callback){
	this.initialize(t,callback);
};
Obj.Clock.prototype = {
	initialize : function(t,callback){
		this.t = t;
		this.callback = callback;
		this.timer = null;
		this.index = 0;
		this.direction = true;
	},
	play : function(){
		if(this.timer)clearTimeout(this.timer);
		var _this = this;
		if(this.direction){
			this.timer = setInterval(function(){_this.callback(++_this.index)},_this.t);
		}else{
			this.timer = setInterval(function(){_this.callback(--_this.index)},_this.t);
		}
	},
	stop : function(){
		if(this.timer)clearTimeout(this.timer);
		this.timer = null;
	},
	goto : function(index){
		this.index = index;
		this.callback(this.index);
	},
	setDirection : function(flag){
		this.direction = flag;
		if(this.timer)this.play();
	}
};

/**
* @class Animate 封闭式动画类
* @constructor
* @param {object} opts 配置项
* @returns {object} 一个动画对象
* @dependList: [
	Obj.Clock
  ]
* @userList: [
	"private_specialtyshop.js"
  ]
*/
Obj.Animate = function(opts){
	var defaults = {
		fps : 60,
		target : null,
		cycle : 0,
		transform : []
	}
	this.extend(defaults, opts);
	this.initialize(defaults);
};
Obj.Animate.prototype = {
	initialize : function(opts){
		this.setOptions(opts);
	},
	setOptions : function(opts){
		this.extend(this, opts);
		this.transform = this.compileAll(this.target,this.transform);
		this.length = this.transform[0]-1;
		this.transform = this.transform[1];
		this.clock = new Obj.Clock(1000/this.fps,this.frame());
	},
	frame : function(){
		var _this = this;
		return function(index){
			for(var i =0, l = _this.transform.length; i < l; i ++)_this.transform[i](index);
			_this.onChange(index);
			if(_this.script[index])_this.script[index]();
			if(_this.clock.timer)
				if(index >= _this.length){
					switch(_this.cycle){
						case 0:
							_this.stop();
							_this.onEnd();
							break;
						case 1:
							if(_this.getDirection())_this.clock.index = -1;
							break;
						case 2:
							_this.setDirection(false);
							break;
					}
					_this.onMax();
				}else if(index <= 0){
					switch(_this.cycle){
						case 0:
							_this.stop();
							_this.onEnd();
							break;
						case 1:
							if(!_this.getDirection())_this.clock.index = _this.length + 1;
							break;
						case 2:
							_this.setDirection(true);
							break;
					}
					_this.onMin();
				}
		}
	},
	compileAll : function(target, transform){
		var result = [],
			trans = {},
			length = 0;
		for(var i = 0, l = transform.length; i < l; i ++){
			trans = transform[i];
			trans = this.compile(target, trans.property, trans.process, trans.unit);
			if(trans[1] > length)length = trans[1];
			result[i] = trans[0];
		}
		return [length,result];
	},
	compile : function(target, property, process, unit, flag){
		var unit_process = process[0],
			unit_result = [],
			result = this.tween(unit_process[0], unit_process[1], unit_process[2], unit, unit_process[3]);
		for(var i = 1, l = process.length; i < l; i ++){
			unit_process = process[i];
			unit_result = this.tween(unit_process[0], unit_process[1], unit_process[2], unit, unit_process[3]);
			unit_result.shift();
			result = result.concat(unit_result);
		}
		var length = result.length;
		if(flag){
			return result;
		}else{
			return [eval("(function(){return function(index){if(index<"+length+")target"+property+" = ['"+result.join("','")+"'][index];}})()"), length];
		}
	},
	tween : function(start, end, step, unit, path){
		var unit = unit || 0,
			result = [start + unit];
		if(path){
			var F = path[0], Xs = path[1][0], Xe = path[1][1], Ys = F(Xs), Ye = F(Xe),
				m = (start == end || Ys == Ye) ? 1 : (end - start) / (Ye-Ys),
				n = start - Ys * m,
				i = (Xe - Xs) / step,
				j = Xs;
			for(var x = 1; x < step; x ++){
				result[x] = F(x * i + j) * m + n + unit;
			}
		}else{
			var spacing = (end - start) / step;
			for(var i = 1; i < step; i ++){
				start += spacing;
				result[i] = start + unit;
			}
		}
		result[step] = end + unit;
		return result;
	},
	extend : function(a,b){
		for(var c in b){
			a[c] = b[c];
		}
	},
	play : function(){
		this.clock.play();
		this.onPlay();
		if(!this.start){
			this.onStart();
			this.start = true;
			this.onMin();
		}
	},
	stop : function(){
		this.clock.stop();
		this.onStop();
	},
	delay : function(t){
		this.stop();
		var _this = this;
		setTimeout(_this.play, t);
	},
	goto : function(index){
		this.clock.goto(index);
	},
	getIndex : function(){
		return this.clock.index;
	},
	getDirection : function(){
		return this.clock.direction;
	},
	setDirection : function(flag){
		this.clock.setDirection(flag);
	},
	onPlay : function(){
	},
	onStop : function(){
	},
	onStart : function(){
	},
	onEnd : function(){
	},
	onMax : function(){
	},
	onMin : function(){
	},
	onChange : function(){
	},
	script : {
	}
};

/**
* @namespace Sandbox 页面中模块的命名空间
*/
var Sandbox = {};

/**
* @description 产品介绍页图片和视频展示模块
* @dependList: [
	Obj.Animate
  ]
*/
Sandbox.DetailPageDisplay = {
	config: {
		tabButton: null,
		tabClass: {
			pic: "player_pic",
			tv: "player_view",
			tabPic: "package_viewscreen",
			tabTv: "package_seepic"
		},
		picNodes: {
			pageBox: null,
			pageList: [],
			indexList: [],
			indexClass: ["", ""],
			scrollLeftButton: null,
			scrollLeftClass: ["", ""],
			scrollRightButton: null,
			scrollRightClass: ["", ""],
			pevButton: null,
			nextButton: null,
			bigPicBox: null,
			bigPicTitle: null,
			bigPicImg: null
		},
		tvNodes: {
			pageBox: null,
			pageList: [],
			indexList: [],
			indexClass: ["", ""],
			scrollLeftButton: null,
			scrollLeftClass: ["", ""],
			scrollRightButton: null,
			scrollRightClass: ["", ""],
			tvBox: null
		}
	},
	scrollPage: (function(){
		function scrollPage(opts){
			var defaults = {
				pageBox: null,
				unitWidth: 81,
				fps: 60,
				unitStep: 10,
				pageNum: 1,
				scrollLeftButton: null,
				scrollLeftClass: ["", ""],
				scrollRightButton: null,
				scrollRightClass: ["", ""]
			};
			extend(defaults, opts);
			this.init(defaults);
		}
		scrollPage.prototype = {
			init: function(opts){
				this.setOptions(opts);
				this.bindEvents();
				this.setScrollButtonClass();
			},
			setOptions: function(opts){
				extend(this, opts);
				extend(this, {
					nowPageIndex: 0,
					animate: new Obj.Animate({
						fps: this.fps,
						target: this.pageBox,
						cycle: 0,
						transform: [{
							property: ".scrollLeft",
							process: [[0, this.unitWidth * (this.pageNum - 1), this.unitStep * (this.pageNum - 1)]],
							unit: ""
						}]
					})
				});
				if(this.pageBox === null){
					this.scrollLeft = function(){};
					this.scrollRight = function(){};
				}
			},
			bindEvents: function(){
				var _this = this;
				if(this.scrollLeftButton){
					$(this.scrollLeftButton).$r("click", function(){
						_this.scrollLeft();
						return false;
					});
				}
				if(this.scrollRightButton){
					$(this.scrollRightButton).$r("click", function(){
						_this.scrollRight();
						return false;
					});
				}
			},
			gotoPage: function(index){
				if(index != this.nowPageIndex && index >= 0 && index < this.pageNum){
					var targetStep = this.unitStep * index,
						direction = index > this.nowPageIndex;
					this.animate.onChange = function(index){
						if(index == targetStep){
							this.stop();
							this.onChange = function(){};
						}
					};
					this.animate.setDirection(direction);
					this.animate.play();
					this.nowPageIndex = index;
					this.setScrollButtonClass();
				}
				
			},
			scrollLeft: function(){
				if(this.nowPageIndex > 0){
					this.gotoPage(this.nowPageIndex - 1);
				}
			},
			scrollRight: function(){
				if(this.nowPageIndex < this.pageNum - 1){
					this.gotoPage(this.nowPageIndex + 1);
				}
			},
			setScrollButtonClass: function(){
					if(this.scrollLeftButton)
						this.scrollLeftButton.className = this.nowPageIndex == 0 ?
								this.scrollLeftClass[1] :
								this.scrollLeftClass[0];
					if(this.scrollRightButton)
						this.scrollRightButton.className = this.nowPageIndex == this.pageNum - 1 ?
								this.scrollRightClass[1] :
								this.scrollRightClass[0];
			}
		};
		return scrollPage;
	})(),
	displayPic: (function(){
		function displayPic(opts){
			var defaults = {
				indexList: [],
				indexClass: ["", ""],
				pevButton: null,
				nextButton: null,
				bigPicTitle: null,
				bigPicImg: null,
				onChange: function(index){}
			};
			extend(defaults, opts);
			this.init(defaults);
		}
		displayPic.prototype = {
			init: function(opts){
				this.setOptions(opts);
				this.bindEvents();
				this.switchIndex(this.nowIndex);
			},
			setOptions: function(opts){
				extend(this, opts);
				extend(this, {
					nowIndex: 0,
					total: this.indexList.length
				});
			},
			bindEvents: function(){
				var _this = this;
				if(this.indexList)
					this.indexList.each(function(item, i){
						$(item).$r("click", function(){
							_this.switchIndex(i);
						});
					});
				if(this.pevButton)
					$(this.pevButton).$r("click", function(){
						_this.switchPev();
						return false;
					});
				if(this.nextButton)
					$(this.nextButton).$r("click", function(){
						_this.switchNext();
						return false;
					});
			},
			switchIndex: function(index){
				if(index >= 0 && index < this.total){
					var item = this.indexList[index],
						title = item.getAttribute("title"),
						bsrc = item.getAttribute("bsrc");
					
					this.bigPicTitle.innerHTML = (index + 1) + "/" + this.total + "&nbsp;" + title;
					this.bigPicImg.src = bsrc;
					this.indexList[this.nowIndex].className = this.indexClass[1];
					item.className = this.indexClass[0];
					this.nowIndex = index;
					this.onChange(index);
				}
			},
			switchPev: function(){
				if(this.nowIndex > 0){
					this.switchIndex(this.nowIndex - 1);
				}
			},
			switchNext: function(){
				if(this.nowIndex < this.total - 1){
					this.switchIndex(this.nowIndex + 1);
				}
			}
		};
		return displayPic;
	})(),
	displayTv: (function(){
		function displayTv(opts){
			var defaults = {
				indexList: [],
				indexClass: ["", ""],
				tvBox: null
			};
			extend(defaults, opts);
			this.init(defaults);
		}
		displayTv.prototype = {
			init: function(opts){
				this.setOptions(opts);
				this.bindEvents();
				this.switchIndex(this.nowIndex);
			},
			setOptions: function(opts){
				extend(this, opts);
				extend(this, {
					nowIndex: 0
				});
			},
			bindEvents: function(){
				var _this = this;
				if(this.indexList)
					this.indexList.each(function(item, i){
						$(item).$r("click", function(){
							_this.switchIndex(i);
						});
					});
			},
			switchIndex: function(index){
				if(index >= 0 && index < this.indexList.length){
					var item = this.indexList[index],
						bsrc = item.getAttribute("bsrc");
					
					this.tvBox.innerHTML = bsrc;
					this.indexList[this.nowIndex].className = this.indexClass[1];
					item.className = this.indexClass[0];
					this.nowIndex = index;
				}
			}
		};
		return displayTv;
	})(),
	switchPicAndTv: function(opts){
		var defaults = {
			tabButton: null,
			tabClass: {
				pic: "",
				tv: "",
				tabPic: "",
				tabTv: ""
			},
			picTabPages: {
				pageBox: null,
				others: []
			},
			tvTabPages: {
				pageBox: null,
				others: []
			}
		};
		extend(defaults, opts);
		
		if(defaults.tabButton === null) return false;
		
		var picTabPages = defaults.picTabPages,
			tvTabPages = defaults.tvTabPages;
		
		if(picTabPages.pageBox !== null && tvTabPages.pageBox !== null){
			var tabButtons = $(defaults.tabButton).$("li"),
				tabButtonPic = tabButtons[0],
				tabButtonTv = tabButtons[1];
			
			tabButtonPic.$r("click", function(){
				defaults.tabButton.className = defaults.tabClass.tabPic;
				picTabPages.pageBox.style.display = "";
				tvTabPages.pageBox.style.display = "none";
				picTabPages.others.each(function(item){
					if(item)
						item.style.display = "";
				});
				tvTabPages.others.each(function(item){
					if(item)
						item.style.display = "none";
				});
			});
			tabButtonTv.$r("click", function(){
				defaults.tabButton.className = defaults.tabClass.tabTv;
				picTabPages.pageBox.style.display = "none";
				tvTabPages.pageBox.style.display = "";
				picTabPages.others.each(function(item){
					if(item)
						item.style.display = "none";
				});
				tvTabPages.others.each(function(item){
					if(item)
						item.style.display = "";
				});
			});
			
			tabButtonPic.$click();
		}else if(picTabPages.pageBox === null && tvTabPages.pageBox !== null){
			defaults.tabButton.className = defaults.tabClass.tv;
			picTabPages.others.each(function(item){
				if(item)
					item.style.display = "none";
			});
			tvTabPages.others.each(function(item){
				if(item)
					item.style.display = "";
			});
		}else{
			defaults.tabButton.className = defaults.tabClass.pic;
			picTabPages.others.each(function(item){
				if(item)
					item.style.display = "";
			});
			tvTabPages.others.each(function(item){
				if(item)
					item.style.display = "none";
			});
		}
		return true;
	},
	init: function(){
		var _this = this;
		
		var picNodes = this.config.picNodes,
			tvNodes = this.config.tvNodes;
		//图片索引翻页对象
		this.scrollPagePic = new this.scrollPage({
			pageBox: picNodes.pageBox,
			pageNum: picNodes.pageList.length,
			scrollLeftButton: picNodes.scrollLeftButton,
			scrollLeftClass: picNodes.scrollLeftClass,
			scrollRightButton: picNodes.scrollRightButton,
			scrollRightClass: picNodes.scrollRightClass
		});
		//视频索引翻页对象
		this.scrollPageTv = new this.scrollPage({
			pageBox: tvNodes.pageBox,
			pageNum: tvNodes.pageList.length,
			scrollLeftButton: tvNodes.scrollLeftButton,
			scrollLeftClass: tvNodes.scrollLeftClass,
			scrollRightButton: tvNodes.scrollRightButton,
			scrollRightClass: tvNodes.scrollRightClass
		});
		//图片切换对象
		this.displayPic = new this.displayPic({
			indexList: picNodes.indexList,
			indexClass: picNodes.indexClass,
			pevButton: picNodes.pevButton,
			nextButton: picNodes.nextButton,
			bigPicTitle: picNodes.bigPicTitle,
			bigPicImg: picNodes.bigPicImg,
			onChange: function(index){
				var page = parseInt(index / 4);
				if(page != _this.scrollPagePic.nowPageIndex)
					_this.scrollPagePic.gotoPage(page);
			}
		});
		//视频切换对象
		this.displayTv = new this.displayTv({
			indexList: tvNodes.indexList,
			indexClass: tvNodes.indexClass,
			tvBox: tvNodes.tvBox
		});
		//图片展示与视频展示切换
		this.switchPicAndTv({
			tabButton: this.config.tabButton,
			tabClass: this.config.tabClass,
			picTabPages: {
				pageBox: picNodes.pageBox,
				others: [
					picNodes.scrollLeftButton,
					picNodes.scrollRightButton,
					picNodes.bigPicBox
				]
			},
			tvTabPages: {
				pageBox: tvNodes.pageBox,
				others: [
					tvNodes.scrollLeftButton,
					tvNodes.scrollRightButton,
					tvNodes.tvBox
				]
			}
		});
	}
};

// 由于一些老的页面直接使用的window下的某些函数或对象，为兼容这些老的页面，把这些函数或对象释放到window下
using([
	Func.Business.Address,
	[Func.Base,["DateLib"]],
	[Obj,["Fselect", "displayIndex"]],
	Func.Dom.Class,
	[Func.Struct,["Tab", "Tab2"]]
]);



using([
	[Sandbox, ["DetailPageDisplay"]]
]);

//配置图片视频展示沙箱环境
$r("domready", function(){
	var player_pic_list = $("player-pic-list"),
		player_tv_list = $("player-tv-list"),
		bPic = $("bPic"),
			title = bPic ? bPic.$("div")[0] : null,
			img = bPic ? bPic.$("img")[0] : null,
			switchButtons = bPic ? bPic.$("a") : [null, null];
	
	if(player_pic_list)
	    player_pic_list.$("ul")[0].style.width = 81 * player_pic_list.$("li").length + "px";
	if(player_tv_list)
	    player_tv_list.$("ul")[0].style.width = 81 * player_tv_list.$("li").length + "px";
		
	DetailPageDisplay.config.tabButton = $("player-tab");
	
	DetailPageDisplay.config.tabClass = {
		pic: "player_pic",
		tv: "player_view",
		tabPic: "package_viewscreen",
		tabTv: "package_seepic"
	};
	
	extend(DetailPageDisplay.config.picNodes, {
		pageBox: player_pic_list,
		pageList: player_pic_list ? player_pic_list.$("li") : [],
		indexList: player_pic_list ? player_pic_list.$g("img") : [],
		indexClass: ["on", ""],
		scrollLeftButton: $("player-pic-scrollleft"),
		scrollLeftClass: ["player_left_on", "player_left_off"],
		scrollRightButton: $("player-pic-scrollright"),
		scrollRightClass: ["player_right_on", "player_right_off"],
		pevButton: switchButtons[0],
		nextButton: switchButtons[1],
		bigPicBox: bPic,
		bigPicTitle: title,
		bigPicImg: img
	});
	
	extend(DetailPageDisplay.config.tvNodes, {
		pageBox: player_tv_list,
		pageList: player_tv_list ? player_tv_list.$("li") : [],
		indexList: player_tv_list ? player_tv_list.$g("img") : [],
		indexClass: ["on", ""],
		scrollLeftButton: $("player-tv-scrollleft"),
		scrollLeftClass: ["player_left_on", "player_left_off"],
		scrollRightButton: $("player-tv-scrollright"),
		scrollRightClass: ["player_right_on", "player_right_off"],
		tvBox: $("Video")
	});
	
	DetailPageDisplay.init();
});


$r("domready", function(){
	//Tab切换
	var lines = $("lines");
	if(lines && (lines = lines.$("ul")) && (lines = lines[0].$g("li")) && lines.length){
		lines.pop();
		Tab2(lines, 0, ["select_nocurrent","select_current"]);
	}
	
	var	line = null;
	for(var i = 0; true; i ++){
		if(line = $("line" + i))
			Tab2(line.$("ul")[0].$("li"), 0, ["package_nocurrent","package_current"]);
		else break;
	}
});