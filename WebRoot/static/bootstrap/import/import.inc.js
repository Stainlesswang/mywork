/**
 * 系统公共JS、CSS文件统一引用入口
 * @author lmt
 * @data 2017-01-20
 */

/*HTML5 Shiv 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询*/
/* 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果*/

//系统公共JS文件对象
var common_js_files = [
    //'/assets/bootstrap/3.3/js/jquery-2.0.0.min.js',
//    '/static/bootstrap/3.3/js/bootstrap.min.js',
    '/static/bootstrap/3.3/plus/datetime/bootstrap-datetimepicker.min.js',
    '/static/bootstrap/3.3/plus/datetime/bootstrap-datetimepicker.zh-CN.js',
    '/static/bootstrap/3.3/plus/table/bootstrap-table.min.js',
    '/static/bootstrap/3.3/plus/table/bootstrap-table-zh-CN.min.js'
];
//系统CSS文件对象
var common_css_files = [
    '/static/bootstrap/3.3/css/bootstrap.css',
    '/static/bootstrap/3.3/plus/datetime/bootstrap-datetimepicker.min.css',
    '/static/bootstrap/3.3/plus/table/bootstrap-table.min.css'
];

/**
 * 导入CSS文件
 */
for (var i = 0; i < common_css_files.length; i++) {
    document.write("<link rel='stylesheet' type='text/css' href='" + getRootPath() + common_css_files[i] + "'>");
}
/**
 * 导入JS文件
 */
for (var i = 0; i < common_js_files.length; i++) {
    document.write("<script type='text/javascript' src='" + getRootPath() + common_js_files[i] + "'></script>");
}

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
//	return(localhostPath+projectName);
    
}
