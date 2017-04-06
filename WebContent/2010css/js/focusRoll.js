/**
 * @author 愚人码头
 * 文章地址：http://www.css88.com/archives/2216
 * 演示地址：http://www.css88.com/demo/ADSlide/index2.html
 * 下载地址：http://www.css88.com/demo/ADSlide/ADRoll.rar
 */
(function($){
	$.fn.ADRoll=function(settings){
		settings = jQuery.extend({
        	speed : "normal",
			num : 4,
			timer : 1000,
			direction:"top",
			imgHeight:"289"
    	}, settings);
		return this.each(function() {
			$.fn.ADRoll.scllor( $(this), settings );
    	});
	};
	
	$.fn.ADRoll.scllor=function($this,settings){
		var index=0;
		var li=$(".flash_item li");
		var showBox=$(".img-box")
		li.hover(function(){
			if(intervalTime){
				clearInterval(intervalTime);
			}
			index=li.index(this);
			intervalTime = setTimeout(function(){
				ShowAD(index);
			},100);
		},function(){
			clearInterval(intervalTime);
			intervalTime = setInterval(function(){
				ShowAD(index);
				index++;
				if(index==settings.num){
					index=0;
				}
			},settings.timer)
		});
		showBox.hover(function(){
			if(intervalTime){
				clearInterval(intervalTime);
			}
		},function(){
			clearInterval(intervalTime);
			intervalTime=setInterval(function(){
				ShowAD(index);
				index++;
				if (index == settings.num) {
					index = 0;
				}
			}, settings.timer);
		});
		var intervalTime= setInterval(function(){
			ShowAD(index);
			index++;
			if(index==settings.num){
				index=0;
			}
		},settings.timer);
		var ShowAD=function(i){
			showBox.animate({"top":-i*settings.imgHeight},settings.speed);
			$(".flash_item li").eq(i).addClass("on").siblings().removeClass("on");
		};
	};
})(jQuery);
