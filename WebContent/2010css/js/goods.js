// JavaScript Document
$(document).ready(function(){
//商品４个大图点击轮回
	$('.pic_index').find('li').hover(function(){
		$(this).addClass('pic_on').siblings().removeClass('pic_on');									  
		$('#pic_box').find('div').eq($('.pic_index').find('li').index(this)).fadeIn().siblings().hide();	
	}, function(){			
	});
	// 关闭弹出层	
	$(".close").live("click", function(){
	$("#popbg").fadeOut();
	$(this).parent().parent().parent().fadeOut();
	});	
	$(".jqzoom").jqueryzoom({xzoom:200,yzoom:200,offset:5,position:"right",preload:1,lens:1});
	$(".othlist").find("img").lazyload({ effect : "fadeIn",placeholder : "http://www.ugolf.com.cn/2010css/global/grey.gif"});

	// 分享给好友
	//$('.share').click(function(){	
	//	$('#share_pop').fadeIn();
	//});
	// 收藏弹出层
	//$('.collect').click(function(){	
	//	$('#coll_pop').fadeIn();
	//});

	
	//组合商品板块切换(需要添加具体显示隐藏的板块操作)
	$('.cscrip_1').click(function(){
		$(this).addClass('cscrip_on');
		$('#combin_1').show();
		$('#combin_2').hide();
		$('#combin_3').hide();
		$('#combin_4').hide();
		$('#combin_5').hide();
		$('.cscrip_2').removeClass('cscrip_on')	
		$('.cscrip_3').removeClass('cscrip_on')
		$('.cscrip_4').removeClass('cscrip_on')
		$('.cscrip_5').removeClass('cscrip_on')
		
	});	
	$('.cscrip_2').click(function(){
		$(this).addClass('cscrip_on');
		$('#combin_1').hide();
		$('#combin_2').show();
		$('#combin_3').hide();
		$('#combin_4').hide();
		$('#combin_5').hide();
		$('.cscrip_1').removeClass('cscrip_on')	
		$('.cscrip_3').removeClass('cscrip_on')	
		$('.cscrip_4').removeClass('cscrip_on')
		$('.cscrip_5').removeClass('cscrip_on')
	});	
	$('.cscrip_3').click(function(){
		$(this).addClass('cscrip_on');
		$('#combin_1').hide();
		$('#combin_2').hide();
		$('#combin_3').show();
		$('#combin_4').hide();
		$('#combin_5').hide();
		$('.cscrip_1').removeClass('cscrip_on')	
		$('.cscrip_2').removeClass('cscrip_on')	
		$('.cscrip_4').removeClass('cscrip_on')
		$('.cscrip_5').removeClass('cscrip_on')
	});
	$('.cscrip_4').click(function(){
		$(this).addClass('cscrip_on');
		$('#combin_1').hide();
		$('#combin_2').hide();
		$('#combin_3').hide();
		$('#combin_4').show();
		$('#combin_5').hide();
		$('.cscrip_1').removeClass('cscrip_on')	
		$('.cscrip_2').removeClass('cscrip_on')	
		$('.cscrip_3').removeClass('cscrip_on')
		$('.cscrip_5').removeClass('cscrip_on')
	});
	$('.cscrip_5').click(function(){
		$(this).addClass('cscrip_on');
		$('#combin_1').hide();
		$('#combin_2').hide();
		$('#combin_3').hide();
		$('#combin_4').hide();
		$('#combin_5').show();
		$('.cscrip_1').removeClass('cscrip_on')	
		$('.cscrip_2').removeClass('cscrip_on')	
		$('.cscrip_3').removeClass('cscrip_on')
		$('.cscrip_4').removeClass('cscrip_on')
	});

	
	
	//商品详情、买家评论、购买咨询、服务承诺、商品图片板块切换(需要添加具体显示隐藏的板块操作)
	$('.scrip_1').click(function(){
		$(this).addClass('scrip_on');
		$('.scrip_2').removeClass('scrip_on');
		$('.scrip_3').removeClass('scrip_on');
		$('.scrip_4').removeClass('scrip_on');
		$('.scrip_5').removeClass('scrip_on');
		$('.details1').show();
		$('.details2').show();
		$('.details3').show();
		$('.details4').hide();
		$('.details5').hide();
		$('#pl1').addClass('zixun_on');
		$('#pl2').removeClass('zixun_on')
		$('#zx1').addClass('zixun_on');
		$('#zx2').removeClass('zixun_on')		
		//$('#page_box_pl').hide();
		// 获取评论
		//get_pl($('#goods_id').text(), 0, 1,0);
		// 获取咨询
		//getcomment($('#goods_id').text(),'1',0,0);	
	});	
	$('.scrip_2').click(function(){
		$(this).addClass('scrip_on');
		$('.scrip_1').removeClass('scrip_on');	
		$('.scrip_3').removeClass('scrip_on');	
		$('.scrip_4').removeClass('scrip_on');
		$('.scrip_5').removeClass('scrip_on');
		$('.details1').hide();
		$('.details2').show();
		$('.details3').hide();
		$('.details4').hide();
		$('.details5').hide();
		//$('#page_box_pl').show();
		$('#pl1').addClass('zixun_on');
		$('#pl2').removeClass('zixun_on')
		// 获取评论
		//get_pl($('#goods_id').text(), 0, 1,0);
	});	
	$('.scrip_3').click(function(){
		$(this).addClass('scrip_on');
		$('.scrip_1').removeClass('scrip_on');	
		$('.scrip_2').removeClass('scrip_on');	
		$('.scrip_4').removeClass('scrip_on');
		$('.scrip_5').removeClass('scrip_on');
		$('.details1').hide();
		$('.details2').hide();
		$('.details3').show();
		$('.details4').hide();
		$('.details5').hide();
		$('#zx1').addClass('zixun_on');
		$('#zx2').removeClass('zixun_on')
		// 获取咨询
		//getcomment($('#goods_id').text(),'1',0,0);
	});
	$('.scrip_4').click(function(){
		$(this).addClass('scrip_on');
		$('.scrip_1').removeClass('scrip_on');	
		$('.scrip_2').removeClass('scrip_on');	
		$('.scrip_3').removeClass('scrip_on');
		$('.scrip_5').removeClass('scrip_on');
		$('.details1').hide();
		$('.details2').hide();
		$('.details3').hide();
		$('.details4').show();
		$('.details5').hide();
	});
	$('.scrip_5').click(function(){
		$(this).addClass('scrip_on');
		$('.scrip_1').removeClass('scrip_on');	
		$('.scrip_2').removeClass('scrip_on');	
		$('.scrip_3').removeClass('scrip_on');
		$('.scrip_4').removeClass('scrip_on');
		$('.details1').hide();
		$('.details2').hide();
		$('.details3').hide();
		$('.details4').hide();
		$('.details5').show();
		// 获取用户传图
		//getgoodspic($('#goods_id').text(),'1',0);
	});


	//全部评论，最有价值评论标签切换
	$('#pl1').click(function(){															   
		if($(this).hasClass("zixun_on"))
		{
			$('#pl2').removeClass('zixun_on');			
		}
		else
		{
			$(this).addClass('zixun_on');
			$('#pl2').removeClass('zixun_on')
		}
		//get_pl($('#goods_id').text(), 0, 1,0);
	});	
	$('#pl2').click(function(){															   
		if($(this).hasClass("zixun_on"))
		{
			$('#pl1').removeClass('zixun_on');			
		}
		else
		{
			$(this).addClass('zixun_on');
			$('#pl1').removeClass('zixun_on');	
		}
		//get_pl($('#goods_id').text(), 0, 1,1);
	});
	
	
	//全部咨询，最有价值咨询标签切换
	$('#zx1').click(function(){															   
		if($(this).hasClass("zixun_on"))
		{
			$('#zx2').removeClass('zixun_on');			
		}
		else
		{
			$(this).addClass('zixun_on');
			$('#zx2').removeClass('zixun_on')
		}
		//getcomment($('#goods_id').text(),'1',0,0);
	});	
	$('#zx2').click(function(){															   
		if($(this).hasClass("zixun_on"))
		{
			$('#zx1').removeClass('zixun_on');			
		}
		else
		{
			$(this).addClass('zixun_on');
			$('#zx1').removeClass('zixun_on');	
		}
		////getcomment($('#goods_id').text(),'1',0,1);
	});
	
	//创意，功能，做工星级查看
	$('.star_in').hover(function(){											 
		$(this).find('.starpop').fadeIn();	
	}, function(){	
		$(this).find('.starpop').hide();		
	});

});
function addToCart(id, pop){
	var select = $("#choose").text();
	var number = parseInt($("#buynum").val(),10);
	$.ajax({
		type: "get", cache: false, dataType: "json", url: "/cartServlet.do",
		data: {method: "add", id: id, n: number, addon: select},
	    success: function(cart) {
			var amount = cart.itemAmount;
			if(amount > 0){
				$('#' + pop).fadeIn();
				$("#js_cart_goods_number").text(cart.itemAmount);
			}
		}
    });
}