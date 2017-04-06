	var delta = 0.08;
	var collection;
	function floaters() {
		this.items = [];
		this.addItem = function(id,x,y,content)
				  {
					document.write('<DIV id="'
						+ id
						+ '" style="Z-INDEX: 10; POSITION: absolute; width:150px; height:60px; left:'
						+ (typeof (x) == 'string' ? eval(x) : x) + '; top:'
						+ (typeof (y) == 'string' ? eval(y) : y) + '">'
						+ content + '</DIV>');
					
					var newItem	= {};
					newItem.object = document.getElementById(id);
					newItem.x = x;
					newItem.y = y;

					this.items[this.items.length] = newItem;
				  };
		this.play = function()
				  {
					collection = this.items;
					setInterval('play()', 10);
				  };
		}
	
		function play()
		{
			for(var i = 0; i < collection.length; i++)
			{
				var followObj = collection[i].object;
				var mt = jQuery(document).scrollTop();
				jQuery("#" + followObj.id).css({"left":6, "top":mt + 80});
			}
		}	
		
	var theFloaters	= new floaters();
	//右面

	//左面
	var temp = "<link href=\"http://mall.ugolf.cn/2010css/css/mallleft.css\" rel=\"stylesheet\" type=\"text/css\" />" +
			"<div class=\"uBox\"style=\"border:2px solid #D5E2B7;\">" +
				"<div class=\"tit\">" +
					"<span class=\"mark\">商品分类</span>" +
					"<span class=\"subMark\"></span>" +
					
					"</div><div class=\"cont leftsortf\">" +
					"<h4 class=\"top\"><a href=\"http://mall.ugolf.cn/list-c10.html\">球杆</a></h4>" +
					
					"<ul>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c1.html\">一号木</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c47.html\">套杆</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c2.html\">铁杆</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c4.html\">挖起杆</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c52.html\">铁木杆</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c3.html\">推杆</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c49.html\">球道木</a></li>" +
					"</ul>" +
					"<div class=\"clear\"></div>" +
					
					"<h4><a href=\"http://mall.ugolf.cn/list-c45.html\">高尔夫球</a></h4>" +
					"<ul>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c35.html\">二层球</a></li>" +
					"</ul>" +
					"<div class=\"clear\"></div>" +
					
					"<h4><a href=\"http://mall.ugolf.cn/list-c12.html\">球袋</a></h4>" +
					"<ul>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c13.html\">球包</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c14.html\">衣物包</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c15.html\">枪包</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c27.html\">航空包</a></li>" +
					"</ul>" +
					"<div class=\"clear\"></div>" +
						
					"<h4><a href=\"http://mall.ugolf.cn/list-c7.html\">服饰</a></h4>" +
					"<ul>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c19.html\">上衣</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c20.html\">T恤</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c21.html\">裤子</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c22.html\">高尔夫鞋</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c23.html\">手套</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c24.html\">帽子</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c25.html\">袜子</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c10224.html\">袖套</a></li>" +
					"</ul><div class=\"clear\"></div>" +
					
					"<h4><a href=\"http://mall.ugolf.cn/list-c8.html\">配件</a></h4>" +
					"<ul>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c60.html\">球杆帽套</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c42.html\">高尔夫伞</a></li>" +
						"<li><a href=\"http://mall.ugolf.cn/list-c10314.html\">训练辅助</a></li>" +
					"</ul>" +
					"<div class=\"clear\"></div>" +
				"</div>" +
			"</div>";
	theFloaters.addItem('followDiv2', 6, 80, temp);
	theFloaters.play();
	
//图片格式调用方法
//<a href=http://www.lanrentuku.com/ target=_blank><img src=images/ad_100x300.jpg border=0></a>
	