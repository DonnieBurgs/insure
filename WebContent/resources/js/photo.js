function _compress(f, oob, ooi){
    var file = f.files['0'];
    var URL = URL || webkitURL;
    var blob = URL.createObjectURL(file);
    var w=800 ;
    var h=400 ;
    var quality = 0.8 ;

    var img = new Image();
    img.src = blob;
    img.onload = function(){
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        var sourceWidth = this.width ;
        var sourceHeight = this.height ;
        if(sourceWidth<800) {
        	w = sourceWidth ;
        	h = sourceHeight;
        } else {
            if(sourceWidth>sourceHeight) {
            	w = 800 ;
            	h = w/sourceWidth*sourceHeight;
            } else {
            	h = 800 ;
            	w = h/sourceHeight*sourceWidth ;
            }
        }

        $(canvas).attr({width : w, height : h});
        ctx.drawImage(this, 0, 0, w, h);
        var base64 = canvas.toDataURL(file.type, (quality || 0.8)*1 );
//                    alert(navigator.userAgent);
//                    alert($("#namecard").val());
//                if( navigator.userAgent.match(/iphone/i) ) {
//                    var mpImg = new MegaPixImage(img);
//                    mpImg.render(canvas, { maxWidth: w, maxHeight: h, quality: o.quality || 0.8, orientation: 6 });
//                    base64 = canvas.toDataURL(file.type, o.quality || 0.8 );
//                }
//
//                // 修复android
//                if( navigator.userAgent.match('ndroid') ) {
//                    var encoder = new JPEGEncoder();
//                    base64 = encoder.encode(ctx.getImageData(0,0,w,h), o.quality * 100 || 80 );
//                    alert(base64);
//                }
				oob.val(base64);
				ooi.attr("src", blob);
//				ooi.attr("src", "/resources/images/folder1.png");
//				alert(oob.val()) ;
//$("#namecard").val(base64);
		};
}
