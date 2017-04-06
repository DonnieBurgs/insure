function is_email(e){
	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	return reg.test(e);
}
function isnum(tmps) {
	if(isNaN(tmps)) return false ;
	return tmps.match(/^[0-9].*$/) ;
}
function is_int(tmps) {
	if(isNaN(tmps)) return false ;
	return tmps.match(/^[0-9]\d*$/) ;
}
function is_int1(tmps) {
	if(isNaN(tmps)) return false ;
	return tmps.match(/^-?[0-9]\d*$/) ;
}
function is_float(tmps) {
	if(isNaN(tmps)) return false ;
	return tmps.match(/^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/) ;
}
function checkmobile(value)     { 
	var myreg = /^(((13[0-9]{1})|159|177|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(myreg.test(value)) { 
		return true;
	} else {  
		return false;
	}
}
