if(typeof PicFocusManager == "undefined")
	{
		PicFocusManager =[];
	}
	function PicFocus(imageContainerID,textContainerID,buttonContainerID,intervarTime){
		this.Id = function (id){return document.getElementById(id)}
		this.index = PicFocusManager.length;
		PicFocusManager[PicFocusManager.length] = this;
		this.imageContainer = this.Id(imageContainerID);
		this.textContainer = this.Id(textContainerID);
		this.buttonContainer = this.Id(buttonContainerID);
		this.firstShow = 0; //默认显示项
		this.interval = (isNaN(intervarTime)?0:intervarTime) || 5000;
		 //切换时间
		this.canAutoPlay = true; //是否可以自动切换
		this.currentPosition = this.firstShow;
		this.timer;
		this.images = [];
		this.texts = [];
		this.buttons = [];
		this.callback=function(){};
		this.bindEvent = function(){
			var _self = this;
			for(var i=0;i<this.images.length;i++){
				this.images[i].onmouseover = function(){
					_self.stop();
				}
				this.images[i].onmouseout = function(){
					_self.timer = setTimeout('PicFocusManager[' + _self.index + '].play()' , _self.interval )
				}
			}
			for(var i=0;i<this.buttons.length;i++){
				this.buttons[i].onclick = function(){
					_self.focus(this);
				}
			}
		}
		this.play = function(){
			this.stop();
			if(this.canAutoPlay){
				this.setFocus(this.currentPosition ++ )
				if(this.currentPosition >= this.images.length)this.currentPosition =0 ;
			}
			this.timer = setTimeout('PicFocusManager[' + this.index + '].play()' , this.interval )
		}
		this.stop = function(){
			clearTimeout( this.timer );
		}
		this.focus = function(button){
			for(var i=0;i<this.buttons.length;i++){
				if(this.buttons[i] == button){
					this.currentPosition = i;
					this.setFocus(this.currentPosition);
					break;
				}
			}
		}
		this.setFocus = function(i){
			if(/Microsoft/.test(navigator.appName)){
				this.imageContainer.filters[0].apply();
				this.imageContainer.filters[0].play();
			}
			for(var j=0;j<this.images.length;j++){
				this.images[j].style.display = (i==j)?"":"none";
			}
			for(var j=0;j<this.texts.length;j++){
				this.texts[j].style.display = (i==j)?"":"none";
			}
			for(var j=0;j<this.buttons.length;j++){
				this.buttons[j].className = (i==j)? this.buttons[j].getAttribute("focusClass") :this.buttons[j].getAttribute("normalClass");
			}
			if(/Microsoft/.test(navigator.appName)){  //滤镜版本
				new ActiveXObject("DXImageTransform.Microsoft.Fade");
				this.imageContainer.filters[0].play();
			}
			this.callback(i);
		}
		this.init = function(){
			if(this.imageContainer && this.buttonContainer){
				//init
				this.images=this.imageContainer.getElementsByTagName("img");
				if(this.textContainer) this.texts=this.textContainer.getElementsByTagName("label");
				this.buttons=this.buttonContainer.getElementsByTagName("a");
				this.bindEvent();
				for(var i=0;i<this.images.length;i++){
					this.images[i].style.display = "none";
					if(i<this.texts.length) this.texts[i].style.display = "none";
					this.buttons[i].className = this.buttons[i].getAttribute("normalClass");
					this.buttons[i].target="_self";
				}
				this.images[this.firstShow].style.display = "";
				if(this.firstShow<this.texts.length) this.texts[this.firstShow].style.display = "";
				this.buttons[this.firstShow].className = this.buttons[this.firstShow].getAttribute("focusClass");
			}else{
				alert("no provide correct parameter")
			}
		}
	}