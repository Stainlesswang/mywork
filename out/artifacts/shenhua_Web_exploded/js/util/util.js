function getRootPath() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPath=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    if(projectName!='/shenhua')
    {
    	return localhostPath;
    }
    else
    {
    	return(localhostPath+projectName);
    }
}

function getParameter(parName){
	var str = parName.toLowerCase() + "=";
	var gvalue = "";
	var HREF = location.href;
	var upperHREF = location.href.toLowerCase();
	if(upperHREF.indexOf(str)>0){
		gvalue = HREF.substring(upperHREF.indexOf(str) + str.length,upperHREF.length);
		if(gvalue.indexOf('&')>0) gvalue = gvalue.substring(0,gvalue.indexOf('&'));
		if(gvalue.indexOf("#")>0) gvalue = gvalue.split("#")[0];
	}
	return gvalue;
}
//加载页面
function loadPage(id,url)
{
	$("#"+id).load(url);
}

//去除字符串的首尾的空格
function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function genTimestamp() { 
	var time = new Date();
	return time.getTime();
}



//校验是否为空
function validate_notNull($element,tipsPos, message){
	$element.val($.trim($element.val()));
	if($element.val() == ""){
		layer.tips(message, tipsPos,
			{
				tips: [2, '#FF5080']
			}		
		);
//		$element.focus();
		return false;
	}
	return true;
}

function validate_isChecked($element, tipsPos,message){
	if(!$element.is(':checked')){
//		layer.tips('左边么么哒', '#id或者.class', {
//			  tips: [4, '#78BA32']
//		});
		layer.tips(message, tipsPos, {
			tips: [2, '#FF5080']
		});
		return false;
	}
	return true;
}

//比较两个元素是否相等
function clientCheck_compare($elementA, $elementB,tipsPos, message){
	if($elementA.val() != $elementB.val()){
		layer.tips(message, tipsPos, {
			tips: [2, '#FF5080']
		});
		return false;
	}
	return true;
}

//正则校验
var clientCheck_pattern = function($element,pattern,tipsPos, message){
//	var patt1 = new RegExp("^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-x7e]{6,12}$");
	var patt1 = new RegExp(pattern);
	 if (!(patt1.test($element.val()))) {
	   	layer.tips(message, tipsPos, {
			tips: [2, '#AE81FF']
		});
	     return false;
	 }
	 return true;
}